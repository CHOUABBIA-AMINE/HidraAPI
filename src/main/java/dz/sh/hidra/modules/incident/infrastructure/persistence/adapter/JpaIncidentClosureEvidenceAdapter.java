/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIncidentClosureEvidenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.adapter
 *
 * @Description : Enforces Incident lifecycle integrity and owner-controlled evidence.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.incident.application.port.out.IncidentClosureEvidencePort;
import dz.sh.hidra.modules.incident.domain.model.Incident;
import jakarta.persistence.EntityManager;
import java.util.Objects;
import org.springframework.stereotype.Component;
@Component
public final class JpaIncidentClosureEvidenceAdapter implements IncidentClosureEvidencePort {
    private final EntityManager entityManager;
    public JpaIncidentClosureEvidenceAdapter(EntityManager entityManager) {this.entityManager=Objects.requireNonNull(entityManager);}
    private boolean exists(String sql,String id) {return !entityManager.createNativeQuery(sql).setParameter("id",id).getResultList().isEmpty();}
    public Evidence inspect(Incident incident) {
        var policies=entityManager.createNativeQuery("select evidence_required,workflow_required,root_cause_required,corrective_action_type_id,preventive_action_type_id from hidra_incident_closure_policy where classification_id=:classification and severity_id=:severity and active=true for share")
                .setParameter("classification",incident.classificationId()).setParameter("severity",incident.severityId()).getResultList();
        if(policies.size()!=1) throw new IllegalArgumentException("Explicit active Incident closure policy required.");
        Object[] policy=(Object[])policies.get(0);
        var resolutions=entityManager.createNativeQuery("select corrective_action_required,preventive_action_required,resolution_summary,resolved_at from hidra_incident_resolution where incident_id=:id and exists(select 1 from hidra_incident_catalog_entry c where c.id=resolution_type_id and c.catalog_name='RESOLUTION_TYPE') and btrim(resolved_by_actor_id)<>'' for share")
                .setParameter("id",incident.id()).getResultList();
        boolean valid=resolutions.size()==1;boolean corrective=false,preventive=false;
        if(valid) {Object[] resolution=(Object[])resolutions.get(0);valid=resolution[2]!=null && !resolution[2].toString().isBlank() && resolution[3]!=null && incident.resolvedAt()!=null && incident.resolvedAt().equals(toInstant(resolution[3]));
            corrective=Boolean.TRUE.equals(resolution[0]);preventive=Boolean.TRUE.equals(resolution[1]);}
        boolean followUp=(!corrective || followUp(incident.id(),policy[3])) && (!preventive || followUp(incident.id(),policy[4]));
        return new Evidence(valid,exists("select id from hidra_incident_evidence_link where incident_id=:id and btrim(evidence_reference_id)<>'' for share",incident.id()),
                Boolean.TRUE.equals(policy[0]),Boolean.TRUE.equals(policy[1]),Boolean.TRUE.equals(policy[2]),
                exists("select id from hidra_incident_root_cause_analysis where incident_id=:id and btrim(summary)<>'' for share",incident.id()),corrective || preventive,followUp);
    }
    private java.time.Instant toInstant(Object value) {
        if(value instanceof java.sql.Timestamp t) return t.toInstant();
        if(value instanceof java.time.OffsetDateTime t) return t.toInstant();
        if(value instanceof java.time.Instant t) return t;
        throw new IllegalArgumentException("Unsupported persisted resolution timestamp.");
    }
    private boolean followUp(String id,Object type) {
        if(type==null) return false;
        return !entityManager.createNativeQuery("select id from hidra_incident_response_action where incident_id=:id and action_type_id=:type and action_status in ('PLANNED','IN_PROGRESS','COMPLETED') for share")
                .setParameter("id",id).setParameter("type",type).getResultList().isEmpty();
    }
}
