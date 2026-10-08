/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIncidentResponseActionRepositoryAdapter
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

import dz.sh.hidra.modules.incident.application.port.out.IncidentResponseActionRepositoryPort;
import dz.sh.hidra.modules.incident.domain.model.IncidentResponseAction;
import dz.sh.hidra.modules.incident.domain.service.IncidentLifecycleGuard;
import dz.sh.hidra.modules.incident.infrastructure.persistence.mapper.IncidentPersistenceMapper;
import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.IncidentResponseActionJpaRepository;
import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.IncidentJpaRepository;
import dz.sh.hidra.modules.identity.application.contract.incident.IncidentActorContract;
import dz.sh.hidra.modules.organization.application.contract.incident.IncidentOrganizationContract;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
public class JpaIncidentResponseActionRepositoryAdapter implements IncidentResponseActionRepositoryPort {
    private final IncidentResponseActionJpaRepository repository;
    private final IncidentJpaRepository parents;
    private final IncidentCatalogValidation catalogs;
    private final IncidentActorContract actors;
    private final IncidentOrganizationContract units;
    public JpaIncidentResponseActionRepositoryAdapter(IncidentResponseActionJpaRepository repository,
            IncidentJpaRepository parents,IncidentCatalogValidation catalogs,IncidentActorContract actors,IncidentOrganizationContract units) {
        this.repository=Objects.requireNonNull(repository);this.parents=Objects.requireNonNull(parents);
        this.catalogs=Objects.requireNonNull(catalogs);this.actors=Objects.requireNonNull(actors);this.units=Objects.requireNonNull(units);
    }
    @Override @Transactional
    public IncidentResponseAction save(IncidentResponseAction x) {
        var parent=parents.findByIdForUpdate(x.incidentId()).orElseThrow(() -> new IllegalArgumentException("Missing Incident."));
        new IncidentLifecycleGuard().ensureResponseActionAllowed(IncidentPersistenceMapper.toDomain(parent));
        catalogs.require(x.actionTypeId(),"RESPONSE_ACTION_TYPE",true);
        if(x.organizationUnitId()!=null && units.resolve(x.organizationUnitId()).isEmpty()) throw new IllegalArgumentException("Missing Organization unit.");
        String display=x.performedByActorNameSnapshot();
        if(x.performedByActorId()!=null) display=actors.eligibleActor(x.performedByActorId(),java.time.Instant.now())
                .orElseThrow(() -> new IllegalArgumentException("Missing eligible response actor.")).displayName();
        var canonical=new IncidentResponseAction(x.id(),x.incidentId(),x.actionTypeId(),x.actionStatus(),x.description(),x.targetType(),x.targetReferenceId(),x.targetReferenceCode(),
                x.plannedStartAt(),x.plannedEndAt(),x.startedAt(),x.completedAt(),x.performedByActorId(),display,x.organizationUnitId(),x.resultSummary(),x.failureReason(),x.createdAt(),x.updatedAt());
        return IncidentPersistenceMapper.toDomain(repository.saveAndFlush(IncidentPersistenceMapper.toEntity(canonical)));
    }
    @Override public Optional<IncidentResponseAction> findById(String id) {return repository.findById(id).map(IncidentPersistenceMapper::toDomain);}
}
