/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JdbcIncidentSourceEscalationAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.adapter
 * @Description : T14 protected operator-origin Incident case escalation.
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.incident.application.port.out.IncidentEscalationStorePort;
import dz.sh.hidra.modules.incident.domain.model.IncidentSourceEscalation;
import dz.sh.hidra.modules.incident.domain.value.IncidentEscalationSourceType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcIncidentSourceEscalationAdapter implements IncidentEscalationStorePort {
    private final JdbcTemplate db;
    public JdbcIncidentSourceEscalationAdapter(JdbcTemplate db) { this.db=java.util.Objects.requireNonNull(db); }

    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void serialize(String evaluationId) {
        db.queryForObject("select pg_advisory_xact_lock(hashtextextended(?, 0))",
                Object.class, "hidra:t14:" + evaluationId);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Correlation> byEvaluation(String evaluationId) {
        return db.query("select * from hidra_incident_evaluation_case where evaluation_id=?", (rs,n)->
            new Correlation(rs.getString("evaluation_id"),rs.getString("incident_id"),
                rs.getString("company_id"),rs.getString("asset_type"),rs.getString("asset_id"),
                rs.getString("classification_id"),rs.getString("severity_id"),
                rs.getString("title_fr"),rs.getString("reason"),rs.getString("actor_id"),
                rs.getTimestamp("created_at").toInstant()),evaluationId).stream().findFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Correlation> byIncident(String incidentId) {
        return db.query("select * from hidra_incident_evaluation_case where incident_id=?", (rs,n)->
            new Correlation(rs.getString("evaluation_id"),rs.getString("incident_id"),
                rs.getString("company_id"),rs.getString("asset_type"),rs.getString("asset_id"),
                rs.getString("classification_id"),rs.getString("severity_id"),
                rs.getString("title_fr"),rs.getString("reason"),rs.getString("actor_id"),
                rs.getTimestamp("created_at").toInstant()),incidentId).stream().findFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public List<Correlation> correlationsForAssets(List<dz.sh.hidra.modules.incident.application.port.out.IncidentEscalationAuthorizationPort.AssetRef> assets) {
        if(assets==null||assets.isEmpty())return List.of();
        // Only already authorized typed assets enter the repository query. SQL does not
        // materialize unrelated Incident source IDs, evaluation identities, or totals.
        String predicates=String.join(" or ",java.util.Collections.nCopies(assets.size(),
                "(asset_type=? and asset_id=?)"));
        java.util.List<Object> args=new java.util.ArrayList<>();
        for(var a:assets){args.add(a.type());args.add(a.id());}
        return db.query("select * from hidra_incident_evaluation_case where "+predicates,
                (rs,n)->new Correlation(rs.getString("evaluation_id"),rs.getString("incident_id"),
                    rs.getString("company_id"),rs.getString("asset_type"),rs.getString("asset_id"),
                    rs.getString("classification_id"),rs.getString("severity_id"),
                    rs.getString("title_fr"),rs.getString("reason"),rs.getString("actor_id"),
                    rs.getTimestamp("created_at").toInstant()),args.toArray());
    }
    private static IncidentSourceEscalation from(java.sql.ResultSet rs) throws java.sql.SQLException {
        return new IncidentSourceEscalation(rs.getString("incident_id"),rs.getString("evaluation_id"),
                IncidentEscalationSourceType.valueOf(rs.getString("source_type")),
                rs.getString("source_id"),rs.getString("actor_id"),rs.getString("reason"),
                rs.getTimestamp("escalated_at").toInstant());
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<IncidentSourceEscalation> bySource(String type,String id) {
        return db.query("select * from hidra_incident_escalated_source where source_type=? and source_id=?",
                (rs,n)->from(rs),type,id).stream().findFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public List<IncidentSourceEscalation> timeline(String incidentId) {
        return db.query("select * from hidra_incident_escalated_source where incident_id=? order by escalated_at,source_type",
                (rs,n)->from(rs),incidentId);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void create(Correlation c) {
        db.update("""
            insert into hidra_incident_evaluation_case(evaluation_id,incident_id,company_id,
              asset_type,asset_id,classification_id,severity_id,title_fr,reason,actor_id,created_at)
            values (?,?,?,?,?,?,?,?,?,?,?)
            """,c.evaluationId(),c.incidentId(),c.companyId(),c.assetType(),c.assetId(),
            c.classificationId(),c.severityId(),c.title(),c.reason(),c.actorId(),
            java.sql.Timestamp.from(c.createdAt()));
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void link(IncidentSourceEscalation e) {
        db.update("""
             insert into hidra_incident_escalated_source(source_type,source_id,evaluation_id,
                 incident_id,actor_id,reason,escalated_at)
             values (?,?,?,?,?,?,?)
             """,e.sourceType().name(),e.sourceId(),e.evaluationId(),e.incidentId(),
             e.actorId(),e.reason(),java.sql.Timestamp.from(e.at()));
        db.update("""
            insert into hidra_incident_evidence_link(id,incident_id,evidence_type,
                evidence_reference_id,evidence_title,attached_by_actor_id,attached_at)
            values (?,?,?,?,?,?,?)
            """,UUID.randomUUID().toString(),e.incidentId(),e.sourceType().name(),e.sourceId(),
            "Qualified human escalation source",e.actorId(),java.sql.Timestamp.from(e.at()));
        db.update("""
            insert into hidra_incident_timeline_entry(id,incident_id,entry_type_id,
                status_before,status_after,title,description,actor_id,occurred_at,recorded_at,correlation_id)
            values (?,?,?,NULL, 'OPEN',?,?,?,?,?,?)
            """,UUID.randomUUID().toString(),e.incidentId(),"t14-incident-escalation",
            "Human escalation from "+e.sourceType().name(),e.reason(),e.actorId(),
            java.sql.Timestamp.from(e.at()),java.sql.Timestamp.from(e.at()),e.evaluationId());
    }
}
