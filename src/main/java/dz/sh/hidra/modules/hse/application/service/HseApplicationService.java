/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.service
 *
 * @Description : Application service for HSE case and CAPA workflows.
 *
 */
package dz.sh.hidra.modules.hse.application.service;

import org.springframework.stereotype.Service;

import dz.sh.hidra.modules.hse.application.command.CloseHseCaseCommand;
import dz.sh.hidra.modules.hse.application.command.CreateHseCapaCommand;
import dz.sh.hidra.modules.hse.application.command.OpenHseCaseCommand;
import dz.sh.hidra.modules.hse.application.dto.HseCapaSummaryDto;
import dz.sh.hidra.modules.hse.application.dto.HseCaseSummaryDto;
import dz.sh.hidra.modules.hse.application.mapper.HseApplicationMapper;
import dz.sh.hidra.modules.hse.application.port.in.CloseHseCaseUseCase;
import dz.sh.hidra.modules.hse.application.port.in.CreateHseCapaUseCase;
import dz.sh.hidra.modules.hse.application.port.in.OpenHseCaseUseCase;
import dz.sh.hidra.modules.hse.application.port.out.HseCaseRepositoryPort;
import dz.sh.hidra.modules.hse.application.port.out.HseClosureRepositoryPort;
import dz.sh.hidra.modules.hse.application.port.out.HseCorrectivePreventiveActionRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.HseCase;
import dz.sh.hidra.modules.hse.domain.model.HseClosure;
import dz.sh.hidra.modules.hse.domain.model.HseCorrectivePreventiveAction;
import dz.sh.hidra.modules.hse.domain.value.CapaStatus;
import dz.sh.hidra.modules.hse.domain.value.HseCaseSourceType;
import dz.sh.hidra.modules.hse.domain.value.HseCaseStatus;
import dz.sh.hidra.modules.hse.domain.value.HseId;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for HSE case and CAPA workflows.
 */
@Service
public final class HseApplicationService implements OpenHseCaseUseCase, CreateHseCapaUseCase, CloseHseCaseUseCase {

    private final HseCaseRepositoryPort hseCaseRepositoryPort;
    private final HseCorrectivePreventiveActionRepositoryPort capaRepositoryPort;
    private final HseClosureRepositoryPort closureRepositoryPort;

    public HseApplicationService(
            HseCaseRepositoryPort hseCaseRepositoryPort,
            HseCorrectivePreventiveActionRepositoryPort capaRepositoryPort,
            HseClosureRepositoryPort closureRepositoryPort
    ) {
        this.hseCaseRepositoryPort = Objects.requireNonNull(hseCaseRepositoryPort, "HSE case repository port must not be null.");
        this.capaRepositoryPort = Objects.requireNonNull(capaRepositoryPort, "HSE CAPA repository port must not be null.");
        this.closureRepositoryPort = Objects.requireNonNull(closureRepositoryPort, "HSE closure repository port must not be null.");
    }

    @Override
    public HseCaseSummaryDto openHseCase(OpenHseCaseCommand command) {
        Objects.requireNonNull(command, "Open HSE case command must not be null.");
        Instant now = Instant.now();
        HseCase hseCase = new HseCase(
                HseId.newId().value(),
                command.caseNumber(),
                command.title(),
                command.description(),
                command.caseTypeId(),
                command.severityId(),
                command.priorityId(),
                HseCaseStatus.OPEN,
                command.sourceType() == null ? HseCaseSourceType.MANUAL : command.sourceType(),
                command.incidentReferenceId(),
                command.incidentCodeSnapshot(),
                command.incidentTitleSnapshot(),
                command.targetModule(),
                command.targetTypeCode(),
                command.targetId(),
                command.targetCodeSnapshot(),
                command.targetLabelSnapshot(),
                command.occurredAt(),
                now,
                command.reportedByActorId(),
                command.reportedByDisplayNameSnapshot(),
                command.responsibleOrganizationUnitId(),
                command.responsibleOrganizationUnitNameSnapshot(),
                command.workflowInstanceId(),
                null,
                null,
                null,
                null,
                now,
                now
        );
        return HseApplicationMapper.toSummary(hseCaseRepositoryPort.save(hseCase));
    }

    @Override
    public HseCapaSummaryDto createHseCapa(CreateHseCapaCommand command) {
        Objects.requireNonNull(command, "Create HSE CAPA command must not be null.");
        Instant now = Instant.now();
        HseCorrectivePreventiveAction action = new HseCorrectivePreventiveAction(
                HseId.newId().value(),
                command.hseCaseId(),
                command.actionNumber(),
                command.actionTypeId(),
                command.title(),
                command.description(),
                command.ownerActorId(),
                command.ownerDisplayNameSnapshot(),
                command.ownerOrganizationUnitId(),
                command.ownerOrganizationUnitNameSnapshot(),
                command.targetDate(),
                null,
                command.verificationRequired(),
                null,
                null,
                CapaStatus.PROPOSED,
                command.linkedWorkOrderId(),
                command.workflowTaskId(),
                now,
                now
        );
        return HseApplicationMapper.toSummary(capaRepositoryPort.save(action));
    }

    @Override
    public String closeHseCase(CloseHseCaseCommand command) {
        Objects.requireNonNull(command, "Close HSE case command must not be null.");
        HseClosure closure = new HseClosure(
                HseId.newId().value(),
                command.hseCaseId(),
                command.closureSummary(),
                command.impactAssessed(),
                command.capaCompleted(),
                command.evidenceReviewed(),
                command.regulatoryReviewed(),
                command.closedByActorId(),
                command.closedByDisplayNameSnapshot(),
                Instant.now(),
                command.workflowInstanceId()
        );
        return closureRepositoryPort.save(closure).id();
    }
}
