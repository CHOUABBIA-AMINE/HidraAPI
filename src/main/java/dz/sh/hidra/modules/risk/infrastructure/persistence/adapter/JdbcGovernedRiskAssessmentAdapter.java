/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JdbcGovernedRiskAssessmentAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 * @Description : Store immutable T22 Risk snapshots and decisions under the active owner transaction.
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.GovernedRiskAssessmentPort;
import dz.sh.hidra.modules.risk.domain.model.GovernedRiskAssessment;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public final class JdbcGovernedRiskAssessmentAdapter implements GovernedRiskAssessmentPort {
    private final JdbcTemplate db;
    public JdbcGovernedRiskAssessmentAdapter(JdbcTemplate db) { this.db=db; }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Scope> scope(String id) {
        if(id==null||id.isBlank()) return Optional.empty();
        return db.query("SELECT assessment_id,company_party_id,facility_id FROM hidra_risk_governed_scope WHERE assessment_id=?",
                (rs,n)->new Scope(rs.getString(1),rs.getString(2),rs.getString(3)),id).stream().findFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void enroll(Scope scope) {
        if(scope==null)throw new IllegalArgumentException("Missing assessment scope.");
        db.update("INSERT INTO hidra_risk_governed_scope(assessment_id,company_party_id,facility_id) VALUES(?,?,?)",
                scope.assessmentId(),scope.companyPartyId(),scope.facilityId());
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void freeze(Submission s) {
        db.update("""
            INSERT INTO hidra_risk_governed_submission(
            assessment_id,inherent_cell_id,inherent_matrix_id,inherent_matrix_version,
            inherent_score,inherent_rating_id,alarm_id,alarm_code_snapshot,alarm_raised_at,
            digest_format,digest,submitted_at) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)
            """,s.assessmentId(),s.cellId(),s.matrixId(),s.matrixVersion(),s.score(),
            s.ratingId(),s.alarmId(),s.alarmCode(),java.sql.Timestamp.from(s.alarmRaisedAt()),
            s.digestFormat(),s.digest(),java.sql.Timestamp.from(s.submittedAt()));
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Submission> submission(String id) {
        return db.query("SELECT * FROM hidra_risk_governed_submission WHERE assessment_id=?",
                (rs,n)->new Submission(rs.getString("assessment_id"),
                        rs.getString("inherent_cell_id"),rs.getString("inherent_matrix_id"),
                        rs.getString("inherent_matrix_version"),rs.getBigDecimal("inherent_score"),
                        rs.getString("inherent_rating_id"),rs.getString("alarm_id"),
                        rs.getString("alarm_code_snapshot"),moment(rs,"alarm_raised_at"),
                        rs.getString("digest_format"),rs.getString("digest"),
                        moment(rs,"submitted_at")),id).stream().findFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void finalizeApproved(Receipt r) {
        var submitted=submission(r.assessmentId()).orElseThrow();
        if(!submitted.digest().equals(r.digest())
                ||r.reviewerId().equals(r.approverId()))throw new IllegalArgumentException("Unmatched Risk approval receipt.");
        db.update("""
            INSERT INTO hidra_risk_governed_approval(
            assessment_id,workflow_instance_id,review_task_id,review_action_id,
            reviewer_id,reviewed_at,approval_task_id,approval_action_id,
            approver_id,approved_at,audit_receipt_id,submission_digest)
            VALUES(?,?,?,?,?,?,?,?,?,?,?,?)
            """,r.assessmentId(),r.instanceId(),r.reviewTaskId(),r.reviewActionId(),
            r.reviewerId(),java.sql.Timestamp.from(r.reviewedAt()),r.approvalTaskId(),
            r.approvalActionId(),r.approverId(),java.sql.Timestamp.from(r.approvedAt()),
            r.auditReceiptId(),r.digest());
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<GovernedRiskAssessment> projection(String id) {
        var rows=db.query("""
            SELECT a.id,a.status,a.assessed_by_actor_id,s.company_party_id,s.facility_id,
            t.inherent_score,t.inherent_rating_id,t.inherent_cell_id,t.inherent_matrix_id,
            t.inherent_matrix_version,t.alarm_id,t.alarm_code_snapshot,t.alarm_raised_at,
            t.digest_format,t.digest,t.submitted_at
            FROM hidra_risk_governed_scope s JOIN hidra_risk_assessment a ON a.id=s.assessment_id
            LEFT JOIN hidra_risk_governed_submission t ON t.assessment_id=a.id
            WHERE a.id=?
            """,(rs,n)->new GovernedRiskAssessment(rs.getString("id"),
                    rs.getString("company_party_id"),rs.getString("facility_id"),
                    rs.getString("status"),rs.getString("assessed_by_actor_id"),
                    rs.getBigDecimal("inherent_score"),rs.getString("inherent_rating_id"),
                    rs.getString("inherent_cell_id"),rs.getString("inherent_matrix_id"),
                    rs.getString("inherent_matrix_version"),rs.getString("alarm_id"),
                    rs.getString("alarm_code_snapshot"),moment(rs,"alarm_raised_at"),
                    rs.getString("digest_format"),rs.getString("digest"),moment(rs,"submitted_at"),
                    decisions(id)),id);
        return rows.stream().findFirst();
    }
    private List<GovernedRiskAssessment.Decision> decisions(String id) {
        return db.query("SELECT * FROM hidra_risk_governed_approval WHERE assessment_id=?",
                (rs,n)->new GovernedRiskAssessment.Decision(
                        rs.getString("workflow_instance_id"),rs.getString("review_task_id"),
                        rs.getString("review_action_id"),rs.getString("reviewer_id"),
                        moment(rs,"reviewed_at"),rs.getString("approval_task_id"),
                        rs.getString("approval_action_id"),rs.getString("approver_id"),
                        moment(rs,"approved_at"),rs.getString("audit_receipt_id"),
                        rs.getString("submission_digest")),id);
    }
    private static Instant moment(ResultSet rs,String column) throws SQLException {
        var value=rs.getTimestamp(column);
        return value==null?null:value.toInstant();
    }
}
