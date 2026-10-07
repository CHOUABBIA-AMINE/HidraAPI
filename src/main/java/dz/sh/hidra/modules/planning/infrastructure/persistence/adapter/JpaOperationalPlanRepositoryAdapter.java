/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaOperationalPlanRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for OperationalPlan.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.planning.application.port.out.OperationalPlanRepositoryPort;
import dz.sh.hidra.modules.planning.domain.model.OperationalPlan;
import dz.sh.hidra.modules.planning.infrastructure.persistence.mapper.PlanningPersistenceMapper;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.OperationalPlanJpaRepository;
import org.springframework.stereotype.Component;

import dz.sh.hidra.modules.planning.application.port.out.PlanningCatalogEligibilityPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanRevisionRepositoryPort;
import dz.sh.hidra.modules.topology.application.contract.planning.PlanningTopologyScopeContract;
import dz.sh.hidra.modules.identity.application.contract.planning.PlanningCreatorContract;
import dz.sh.hidra.modules.organization.application.contract.planning.PlanningResponsibleUnitContract;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for OperationalPlan.
 */
@Component
public class JpaOperationalPlanRepositoryAdapter implements OperationalPlanRepositoryPort {

    private final OperationalPlanJpaRepository repository;

    private final PlanningCatalogEligibilityPort catalogs;
    private final PlanningTopologyScopeContract topology;
    private final PlanningCreatorContract creators;
    private final PlanningResponsibleUnitContract units;
    private final PlanRevisionRepositoryPort revisions;

    public JpaOperationalPlanRepositoryAdapter(OperationalPlanJpaRepository repository,
            PlanningCatalogEligibilityPort catalogs, PlanningTopologyScopeContract topology,
            PlanningCreatorContract creators, PlanningResponsibleUnitContract units, PlanRevisionRepositoryPort revisions) {
        this.repository = Objects.requireNonNull(repository, "OperationalPlanJpaRepository must not be null.");
        this.catalogs=Objects.requireNonNull(catalogs);this.topology=Objects.requireNonNull(topology);
        this.creators=Objects.requireNonNull(creators);this.units=Objects.requireNonNull(units);
        this.revisions=Objects.requireNonNull(revisions);
    }

    @Override
    @Transactional
    public OperationalPlan save(OperationalPlan model) {
        Objects.requireNonNull(model);Instant now=Instant.now();
        catalogs.requireActive(model.planTypeId(),"PLAN_TYPE");
        topology.resolve(model.topologyScopeType(),model.topologyScopeId()).filter(s -> model.topologyScopeId().equals(s.id()))
                .orElseThrow(() -> new IllegalArgumentException("Existing supported Topology scope required."));
        creators.eligibleCreator(model.createdByActorId(),now).filter(c -> model.createdByActorId().equals(c.id()))
                .orElseThrow(() -> new IllegalArgumentException("Eligible Identity creator required."));
        if(model.responsibleOrganizationUnitId()!=null) units.availableUnit(model.responsibleOrganizationUnitId(),now)
                .filter(u -> model.responsibleOrganizationUnitId().equals(u.id()))
                .orElseThrow(() -> new IllegalArgumentException("Available responsible Organization unit required."));
        requireRevision(model.id(),model.currentRevisionId());requireRevision(model.id(),model.approvedRevisionId());
        return PlanningPersistenceMapper.toDomain(repository.saveAndFlush(PlanningPersistenceMapper.toEntity(model)));
    }

    private void requireRevision(String planId,String revisionId) {
        if(revisionId==null) return;
        revisions.findById(revisionId).filter(r -> planId.equals(r.planId()))
                .orElseThrow(() -> new IllegalArgumentException("Revision pointer must belong to this plan."));
    }

    @Override
    public Optional<OperationalPlan> findById(String id) {
        return repository.findById(id).map(PlanningPersistenceMapper::toDomain);
    }
}
