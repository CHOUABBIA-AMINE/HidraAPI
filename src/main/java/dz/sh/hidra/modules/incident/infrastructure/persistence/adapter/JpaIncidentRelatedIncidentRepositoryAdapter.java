/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIncidentRelatedIncidentRepositoryAdapter
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

import dz.sh.hidra.modules.incident.application.port.out.IncidentRelatedIncidentRepositoryPort;
import dz.sh.hidra.modules.incident.domain.model.IncidentRelatedIncident;
import dz.sh.hidra.modules.incident.infrastructure.persistence.mapper.IncidentPersistenceMapper;
import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.IncidentRelatedIncidentJpaRepository;
import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.IncidentJpaRepository;
import jakarta.persistence.EntityManager;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
public class JpaIncidentRelatedIncidentRepositoryAdapter implements IncidentRelatedIncidentRepositoryPort {
    private final IncidentRelatedIncidentJpaRepository repository;
    private final IncidentJpaRepository parents;
    private final IncidentCatalogValidation catalogs;
    private final EntityManager entityManager;
    public JpaIncidentRelatedIncidentRepositoryAdapter(IncidentRelatedIncidentJpaRepository repository,
            IncidentJpaRepository parents,IncidentCatalogValidation catalogs,EntityManager entityManager) {
        this.repository=Objects.requireNonNull(repository);this.parents=Objects.requireNonNull(parents);
        this.catalogs=Objects.requireNonNull(catalogs);this.entityManager=Objects.requireNonNull(entityManager);
    }
    @Override @Transactional
    public IncidentRelatedIncident save(IncidentRelatedIncident model) {
        String low=model.incidentId().compareTo(model.relatedIncidentId())<0 ? model.incidentId() : model.relatedIncidentId();
        String high=low.equals(model.incidentId()) ? model.relatedIncidentId() : model.incidentId();
        parents.findByIdForUpdate(low).orElseThrow(() -> new IllegalArgumentException("Missing related Incident."));
        parents.findByIdForUpdate(high).orElseThrow(() -> new IllegalArgumentException("Missing related Incident."));
        catalogs.require(model.relationshipTypeId(),"RELATED_INCIDENT_RELATIONSHIP_TYPE",true);
        var policies=entityManager.createNativeQuery("select direction,reciprocal_type_id from hidra_incident_relationship_policy where relationship_type_id=:type and active=true for share")
                .setParameter("type",model.relationshipTypeId()).getResultList();
        if(policies.size()!=1) throw new IllegalArgumentException("Explicit Incident relationship policy required.");
        Object[] policy=(Object[])policies.get(0);
        boolean symmetric="SYMMETRIC".equals(policy[0]);
        if(!symmetric && !"DIRECTIONAL".equals(policy[0])) throw new IllegalArgumentException("Unsupported relationship direction.");
        var canonical=symmetric ? new IncidentRelatedIncident(model.id(),low,high,model.relationshipTypeId(),model.comment(),model.createdByActorId(),model.createdAt()) : model;
        if(repository.existsByIncidentIdAndRelatedIncidentIdAndRelationshipTypeId(canonical.incidentId(),canonical.relatedIncidentId(),canonical.relationshipTypeId())
                || (policy[1]!=null && repository.existsByIncidentIdAndRelatedIncidentIdAndRelationshipTypeId(canonical.relatedIncidentId(),canonical.incidentId(),policy[1].toString())))
            throw new IllegalArgumentException("Duplicate or configured inverse Incident relationship.");
        entityManager.persist(IncidentPersistenceMapper.toEntity(canonical));entityManager.flush();return canonical;
    }
    @Override public Optional<IncidentRelatedIncident> findById(String id) {return repository.findById(id).map(IncidentPersistenceMapper::toDomain);}
}
