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

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dz.sh.hidra.modules.planning.application.command.CreatePlanningPeriodCommand;
import dz.sh.hidra.modules.planning.application.dto.PlanningPeriodSummaryDto;
import dz.sh.hidra.modules.planning.application.mapper.PlanningApplicationMapper;
import dz.sh.hidra.modules.planning.application.port.in.CreatePlanningPeriodUseCase;
import dz.sh.hidra.modules.planning.application.port.out.PlanningPeriodRepositoryPort;
import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import dz.sh.hidra.modules.planning.domain.exception.PlanningPeriodCodeConflictException;
import dz.sh.hidra.modules.planning.domain.model.PlanningPeriod;
import dz.sh.hidra.modules.planning.domain.value.PlanningId;
import dz.sh.hidra.modules.planning.domain.value.PlanningPeriodStatus;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for planning periods.
 */
@Service
public class PlanningPeriodApplicationService implements CreatePlanningPeriodUseCase {

    private final PlanningPeriodRepositoryPort repositoryPort;

    private final PlanningAccessService access;

    public PlanningPeriodApplicationService(PlanningPeriodRepositoryPort repositoryPort, PlanningAccessService access) {
        this.repositoryPort = Objects.requireNonNull(repositoryPort, "Planning period repository port must not be null.");
        this.access = Objects.requireNonNull(access);
    }

    @Override
    public PlanningPeriodSummaryDto createPlanningPeriod(CreatePlanningPeriodCommand command) {
        throw new AccessDeniedException("Planning operation unavailable.");
    }

    @Override
    @Transactional
    public PlanningPeriodSummaryDto createCompanyPlanningPeriod(String companyPartyId, CreatePlanningPeriodCommand command) {
        Objects.requireNonNull(command, "Create planning period command must not be null.");
        String actor = access.periodCreator(companyPartyId, command.createdByActorId());
        if (!repositoryPort.isActivePeriodType(command.periodTypeId())) {
            throw new InvalidPlanningValueException(
                    "PlanningPeriod period type must reference an active PERIOD_TYPE catalog entry."
            );
        }
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
                command.timeZone() == null || command.timeZone().isBlank()
                        ? "Africa/Algiers"
                        : command.timeZone(),
                PlanningPeriodStatus.OPEN,
                actor,
                now,
                now,
                companyPartyId
        );
        if (repositoryPort.existsByCompanyPartyIdAndCode(period.companyPartyId(), period.code())) {
            throw new PlanningPeriodCodeConflictException();
        }
        return PlanningApplicationMapper.toSummary(repositoryPort.save(period));
    }
}
