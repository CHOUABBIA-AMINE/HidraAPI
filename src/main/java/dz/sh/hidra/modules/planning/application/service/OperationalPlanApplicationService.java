/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalPlanApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Application service for operational plans.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import org.springframework.stereotype.Service;

import dz.sh.hidra.modules.planning.application.command.CreateOperationalPlanCommand;
import dz.sh.hidra.modules.planning.application.dto.OperationalPlanSummaryDto;
import dz.sh.hidra.modules.planning.application.mapper.PlanningApplicationMapper;
import dz.sh.hidra.modules.planning.application.port.in.CreateOperationalPlanUseCase;
import dz.sh.hidra.modules.planning.application.port.out.OperationalPlanRepositoryPort;
import dz.sh.hidra.modules.planning.domain.model.OperationalPlan;
import dz.sh.hidra.modules.planning.domain.value.OperationalPlanStatus;
import dz.sh.hidra.modules.planning.domain.value.PlanningId;

import dz.sh.hidra.modules.planning.application.port.out.PlanningCatalogEligibilityPort;
import dz.sh.hidra.modules.topology.application.contract.planning.PlanningTopologyScopeContract;
import dz.sh.hidra.modules.identity.application.contract.planning.PlanningCreatorContract;
import dz.sh.hidra.modules.organization.application.contract.planning.PlanningResponsibleUnitContract;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import org.springframework.transaction.annotation.Transactional;
import java.util.Locale;
import java.time.Instant;
import java.util.Objects;

/**
 * Application service for operational plans.
 */
@Service
public class OperationalPlanApplicationService implements CreateOperationalPlanUseCase {

    private final OperationalPlanRepositoryPort repositoryPort;

    private final PlanningCatalogEligibilityPort catalogs;
    private final PlanningTopologyScopeContract topology;
    private final PlanningCreatorContract creators;
    private final PlanningResponsibleUnitContract units;
    private final CurrentSecurityContext security;

    public OperationalPlanApplicationService(OperationalPlanRepositoryPort repositoryPort,
            PlanningCatalogEligibilityPort catalogs, PlanningTopologyScopeContract topology,
            PlanningCreatorContract creators, PlanningResponsibleUnitContract units, CurrentSecurityContext security) {
        this.repositoryPort = Objects.requireNonNull(repositoryPort, "Operational plan repository port must not be null.");
        this.catalogs=Objects.requireNonNull(catalogs);this.topology=Objects.requireNonNull(topology);
        this.creators=Objects.requireNonNull(creators);this.units=Objects.requireNonNull(units);
        this.security=Objects.requireNonNull(security);
    }

    @Override
    @Transactional
    public OperationalPlanSummaryDto createOperationalPlan(CreateOperationalPlanCommand command) {
        Objects.requireNonNull(command, "Create operational plan command must not be null.");
        Instant now = Instant.now();
        String actorId=security.currentPrincipal().filter(p -> p.authenticated())
                .map(p -> p.actorId().value()).orElseThrow(() -> new SecurityException("Authenticated creator required."));
        if (command.createdByActorId()==null || !actorId.equals(command.createdByActorId().trim())) {
            throw new SecurityException("Creator must match the authenticated principal.");
        }
        creators.eligibleCreator(actorId,now).filter(c -> actorId.equals(c.id()))
                .orElseThrow(() -> new IllegalArgumentException("Eligible Identity creator required."));
        catalogs.requireActive(command.planTypeId(),"PLAN_TYPE");
        String scopeType=command.topologyScopeType()==null ? null : command.topologyScopeType().trim().toUpperCase(Locale.ROOT);
        String scopeId=command.topologyScopeId()==null ? null : command.topologyScopeId().trim();
        var scope=topology.resolve(scopeType,scopeId).filter(v -> v.id().equals(scopeId))
                .orElseThrow(() -> new IllegalArgumentException("Existing supported Topology scope required."));
        String unitId=command.responsibleOrganizationUnitId()==null || command.responsibleOrganizationUnitId().isBlank()
                ? null : command.responsibleOrganizationUnitId().trim();
        if(unitId!=null) units.availableUnit(unitId,now).filter(u -> unitId.equals(u.id()))
                .orElseThrow(() -> new IllegalArgumentException("Available responsible Organization unit required."));
        OperationalPlan plan = new OperationalPlan(
                PlanningId.newId().value(),
                command.periodId(),
                command.code(),
                command.nameAr(),
                command.nameFr(),
                command.nameEn(),
                command.planTypeId(),
                command.productTypeId(),
                scopeType,
                scope.id(),
                scope.code(),
                scope.name(),
                unitId,
                OperationalPlanStatus.DRAFT,
                null,
                null,
                actorId,
                now,
                now
        );
        return PlanningApplicationMapper.toSummary(repositoryPort.save(plan));
    }
}
