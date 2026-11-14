/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JdbcLeakEvaluationTriggerAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter
 * @Description : T13 atomic suspected-leak evidence and ownership handling.
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.leakdetection.application.port.out.LeakEvaluationTriggerPort;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakEvaluationTrigger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcLeakEvaluationTriggerAdapter implements LeakEvaluationTriggerPort {
    private final JdbcTemplate jdbc;
    private static final String TABLE="public.hidra_leak_detection_evaluation_trigger";

    public JdbcLeakEvaluationTriggerAdapter(JdbcTemplate jdbc) { this.jdbc=jdbc; }

    @Override public void serialize(String evaluationId) {
        jdbc.queryForObject("select pg_advisory_xact_lock(hashtextextended(?,0))",
                Object.class, "hidra:t13:"+evaluationId);
    }
    @Override public Optional<LeakEvaluationTrigger> byEvaluation(String evaluationId) {
        return jdbc.query("select * from "+TABLE+" where evaluation_id=?",
                JdbcLeakEvaluationTriggerAdapter::map,evaluationId).stream().findFirst();
    }
    @Override public Optional<LeakEvaluationTrigger> byCandidate(String candidateId) {
        return jdbc.query("select * from "+TABLE+" where candidate_id=?",
                JdbcLeakEvaluationTriggerAdapter::map,candidateId).stream().findFirst();
    }
    @Override public void save(LeakEvaluationTrigger v) {
        jdbc.update("""
            insert into public.hidra_leak_detection_evaluation_trigger(
             evaluation_id,candidate_id,rule_id,rule_revision,company_party_id,asset_type,asset_id,unit_id,
             target_id,approval_id,trusted_reading_id,evaluated_at,profile_id,declared_confidence,reason,actor_id,created_at)
            values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
            """,v.evaluationId(),v.candidateId(),v.ruleId(),v.ruleRevision(),v.companyPartyId(),
                v.assetType(),v.assetId(),v.unitId(),v.targetId(),v.approvalId(),v.trustedReadingId(),
                Timestamp.from(v.evaluatedAt()),v.profileId(),v.declaredConfidence(),v.reason(),
                v.actorId(),Timestamp.from(v.createdAt()));
    }
    private static LeakEvaluationTrigger map(ResultSet r,int ignored) throws SQLException {
        return new LeakEvaluationTrigger(r.getString("evaluation_id"),r.getString("candidate_id"),
            r.getString("rule_id"),r.getLong("rule_revision"),r.getString("company_party_id"),
            r.getString("asset_type"),r.getString("asset_id"),r.getString("unit_id"),r.getString("target_id"),
            r.getString("approval_id"),r.getString("trusted_reading_id"),
            r.getTimestamp("evaluated_at").toInstant(),r.getString("profile_id"),
            r.getBigDecimal("declared_confidence"),r.getString("reason"),r.getString("actor_id"),
            r.getTimestamp("created_at").toInstant());
    }
}
