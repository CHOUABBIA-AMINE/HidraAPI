/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalPlanApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for operational plans.
 */
@Service
public final class OperationalPlanApplicationService implements CreateOperationalPlanUseCase {

    private final OperationalPlanRepositoryPort repositoryPort;

    public OperationalPlanApplicationService(OperationalPlanRepositoryPort repositoryPort) {
        this.repositoryPort = Objects.requireNonNull(repositoryPort, "Operational plan repository port must not be null.");
    }

    @Override
    public OperationalPlanSummaryDto createOperationalPlan(CreateOperationalPlanCommand command) {
        Objects.requireNonNull(command, "Create operational plan command must not be null.");
        Instant now = Instant.now();
        OperationalPlan plan = new OperationalPlan(
                PlanningId.newId().value(),
                command.periodId(),
                command.code(),
                command.nameAr(),
                command.nameFr(),
                command.nameEn(),
                command.planTypeId(),
                command.productTypeId(),
                command.topologyScopeType(),
                command.topologyScopeId(),
                command.topologyScopeCode(),
                command.topologyScopeNameSnapshot(),
                command.responsibleOrganizationUnitId(),
                OperationalPlanStatus.DRAFT,
                null,
                null,
                command.createdByActorId(),
                now,
                now
        );
        return PlanningApplicationMapper.toSummary(repositoryPort.save(plan));
    }
}
