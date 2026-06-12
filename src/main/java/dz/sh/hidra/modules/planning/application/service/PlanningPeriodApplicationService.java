/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningPeriodApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Application service for planning periods.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import dz.sh.hidra.modules.planning.application.command.CreatePlanningPeriodCommand;
import dz.sh.hidra.modules.planning.application.dto.PlanningPeriodSummaryDto;
import dz.sh.hidra.modules.planning.application.mapper.PlanningApplicationMapper;
import dz.sh.hidra.modules.planning.application.port.in.CreatePlanningPeriodUseCase;
import dz.sh.hidra.modules.planning.application.port.out.PlanningPeriodRepositoryPort;
import dz.sh.hidra.modules.planning.domain.model.PlanningPeriod;
import dz.sh.hidra.modules.planning.domain.value.PlanningId;
import dz.sh.hidra.modules.planning.domain.value.PlanningPeriodStatus;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for planning periods.
 */
public final class PlanningPeriodApplicationService implements CreatePlanningPeriodUseCase {

    private final PlanningPeriodRepositoryPort repositoryPort;

    public PlanningPeriodApplicationService(PlanningPeriodRepositoryPort repositoryPort) {
        this.repositoryPort = Objects.requireNonNull(repositoryPort, "Planning period repository port must not be null.");
    }

    @Override
    public PlanningPeriodSummaryDto createPlanningPeriod(CreatePlanningPeriodCommand command) {
        Objects.requireNonNull(command, "Create planning period command must not be null.");
        Instant now = Instant.now();
        PlanningPeriod period = new PlanningPeriod(
                PlanningId.newId().value(),
                command.code(),
                command.nameAr(),
                command.nameFr(),
                command.nameEn(),
                command.periodTypeId(),
                command.periodStart(),
                command.periodEnd(),
                command.timeZone() == null ? "Africa/Algiers" : command.timeZone(),
                PlanningPeriodStatus.OPEN,
                command.createdByActorId(),
                now,
                now
        );
        return PlanningApplicationMapper.toSummary(repositoryPort.save(period));
    }
}
