/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIncidentClosureRepositoryAdapter
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

import dz.sh.hidra.modules.incident.application.port.out.IncidentClosureRepositoryPort;
import dz.sh.hidra.modules.incident.application.port.out.IncidentClosureEvidencePort;
import dz.sh.hidra.modules.incident.domain.model.IncidentClosure;
import dz.sh.hidra.modules.incident.domain.value.IncidentStatus;
import dz.sh.hidra.modules.incident.infrastructure.persistence.mapper.IncidentPersistenceMapper;
import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.IncidentClosureJpaRepository;
import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.IncidentJpaRepository;
import dz.sh.hidra.modules.identity.application.contract.incident.IncidentActorContract;
import dz.sh.hidra.modules.workflow.application.contract.incident.IncidentWorkflowContract;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
public class JpaIncidentClosureRepositoryAdapter implements IncidentClosureRepositoryPort {
    private final IncidentClosureJpaRepository repository;
    private final IncidentJpaRepository parents;
    private final IncidentClosureEvidencePort evidence;
    private final IncidentActorContract actors;
    private final IncidentWorkflowContract workflows;
    private final EntityManager entityManager;
    public JpaIncidentClosureRepositoryAdapter(IncidentClosureJpaRepository repository,IncidentJpaRepository parents,
            IncidentClosureEvidencePort evidence,IncidentActorContract actors,IncidentWorkflowContract workflows,EntityManager entityManager) {
        this.repository=Objects.requireNonNull(repository);this.parents=Objects.requireNonNull(parents);this.evidence=Objects.requireNonNull(evidence);
        this.actors=Objects.requireNonNull(actors);this.workflows=Objects.requireNonNull(workflows);this.entityManager=Objects.requireNonNull(entityManager);
    }
    @Override @Transactional
    public IncidentClosure save(IncidentClosure x) {
        var entity=parents.findByIdForUpdate(x.incidentId()).orElseThrow(() -> new IllegalArgumentException("Unknown Incident."));
        var parent=IncidentPersistenceMapper.toDomain(entity);
        if(parent.status()!=IncidentStatus.RESOLVED || parent.resolvedAt()==null || repository.existsByIncidentId(parent.id())) throw new IllegalArgumentException("Only unresolved closure of a RESOLVED Incident is allowed.");
        Instant at=Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS);
        var actor=actors.currentActor(at);
        if(!actor.id().equals(x.closedByActorId())) throw new SecurityException("Closer must be the authenticated eligible actor.");
        if(!((parent.responsibleActorId()!=null && parent.responsibleActorNameSnapshot()!=null)
                || (parent.responsibleOrganizationUnitId()!=null && parent.responsibleOrganizationUnitNameSnapshot()!=null))) throw new IllegalArgumentException("Closure requires responsible-owner snapshot.");
        var checked=evidence.inspect(parent);
        if(!checked.hasResolution() || (checked.evidenceRequired() && !checked.hasEvidence())
                || (checked.rootCauseRequired() && (!checked.rootCausePresent() || !x.rootCauseReviewed()))
                || (checked.followUpRequired() && (!checked.followUpPresent() || !x.followUpActionsCreated()))) throw new IllegalArgumentException("Persisted Incident closure evidence is incomplete.");
        if((checked.approvalRequired() || x.workflowInstanceId()!=null)
                && !workflows.closureApproved(x.workflowInstanceId(),parent.id(),actor.id(),at)) throw new SecurityException("Actual target-specific Workflow closure approval required.");
        var closure=new IncidentClosure(x.id(),x.incidentId(),x.closureSummary(),x.resolutionVerified(),x.evidenceReviewed(),x.rootCauseReviewed(),x.followUpActionsCreated(),actor.id(),actor.displayName(),at,x.workflowInstanceId());
        entityManager.persist(IncidentPersistenceMapper.toEntity(closure));entityManager.flush();
        int changed=entityManager.createNativeQuery("update hidra_incident set status='CLOSED',closed_at=:at,updated_at=:at where id=:id and status='RESOLVED'")
                .setParameter("at",at).setParameter("id",parent.id()).executeUpdate();
        if(changed!=1) throw new IllegalStateException("Incident closure lost its locked parent.");
        entityManager.refresh(entity);return closure;
    }
    @Override public Optional<IncidentClosure> findById(String id) {return repository.findById(id).map(IncidentPersistenceMapper::toDomain);}
}
