/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.service
 *
 * @Description : Application service for incident lifecycle commands.
 *
 */
package dz.sh.hidra.modules.incident.application.service;

import org.springframework.stereotype.Service;

import dz.sh.hidra.modules.incident.application.command.CloseIncidentCommand;
import dz.sh.hidra.modules.incident.application.command.OpenIncidentCommand;
import dz.sh.hidra.modules.incident.application.command.RecordIncidentResponseActionCommand;
import dz.sh.hidra.modules.incident.application.dto.IncidentSummaryDto;
import dz.sh.hidra.modules.incident.application.mapper.IncidentApplicationMapper;
import dz.sh.hidra.modules.incident.application.port.in.CloseIncidentUseCase;
import dz.sh.hidra.modules.incident.application.port.in.OpenIncidentUseCase;
import dz.sh.hidra.modules.incident.application.port.in.RecordIncidentResponseActionUseCase;
import dz.sh.hidra.modules.incident.application.port.out.IncidentClosureRepositoryPort;
import dz.sh.hidra.modules.incident.application.port.out.IncidentRepositoryPort;
import dz.sh.hidra.modules.incident.application.port.out.IncidentResponseActionRepositoryPort;
import dz.sh.hidra.modules.incident.domain.model.Incident;
import dz.sh.hidra.modules.incident.domain.model.IncidentClosure;
import dz.sh.hidra.modules.incident.domain.model.IncidentResponseAction;
import dz.sh.hidra.modules.incident.domain.value.IncidentId;
import dz.sh.hidra.modules.incident.domain.value.IncidentSourceType;
import dz.sh.hidra.modules.incident.domain.value.IncidentStatus;
import dz.sh.hidra.modules.incident.domain.value.ResponseActionStatus;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for incident lifecycle commands.
 */
@Service
public final class IncidentApplicationService implements OpenIncidentUseCase, RecordIncidentResponseActionUseCase, CloseIncidentUseCase {

    private final IncidentRepositoryPort incidentRepositoryPort;
    private final IncidentResponseActionRepositoryPort responseActionRepositoryPort;
    private final IncidentClosureRepositoryPort closureRepositoryPort;

    public IncidentApplicationService(
            IncidentRepositoryPort incidentRepositoryPort,
            IncidentResponseActionRepositoryPort responseActionRepositoryPort,
            IncidentClosureRepositoryPort closureRepositoryPort
    ) {
        this.incidentRepositoryPort = Objects.requireNonNull(incidentRepositoryPort, "Incident repository port must not be null.");
        this.responseActionRepositoryPort = Objects.requireNonNull(responseActionRepositoryPort, "Incident response action repository port must not be null.");
        this.closureRepositoryPort = Objects.requireNonNull(closureRepositoryPort, "Incident closure repository port must not be null.");
    }

    @Override
    public IncidentSummaryDto openIncident(OpenIncidentCommand command) {
        Objects.requireNonNull(command, "Open incident command must not be null.");
        Instant now = Instant.now();
        Incident incident = new Incident(
                IncidentId.newId().value(),
                command.incidentNumber(),
                command.title(),
                command.description(),
                command.classificationId(),
                command.severityId(),
                command.priorityId(),
                IncidentStatus.OPEN,
                command.sourceType() == null ? IncidentSourceType.MANUAL : command.sourceType(),
                command.sourceReferenceId(),
                command.sourceReferenceCode(),
                command.detectedAt() == null ? now : command.detectedAt(),
                now,
                command.occurredAt(),
                command.topologyAssetTypeCode(),
                command.topologyAssetId(),
                command.topologyAssetCode(),
                command.topologyAssetNameSnapshot(),
                command.locationDescriptionAr(),
                command.locationDescriptionLt(),
                command.latitude(),
                command.longitude(),
                command.responsibleOrganizationUnitId(),
                command.responsibleOrganizationUnitCode(),
                command.responsibleOrganizationUnitNameSnapshot(),
                null,
                null,
                null,
                0,
                null,
                null,
                null,
                null,
                command.createdByActorId(),
                command.createdByActorNameSnapshot(),
                now,
                now
        );
        return IncidentApplicationMapper.toSummary(incidentRepositoryPort.save(incident));
    }

    @Override
    public String recordResponseAction(RecordIncidentResponseActionCommand command) {
        Objects.requireNonNull(command, "Record incident response action command must not be null.");
        Instant now = Instant.now();
        IncidentResponseAction action = new IncidentResponseAction(
                IncidentId.newId().value(),
                command.incidentId(),
                command.actionTypeId(),
                command.actionStatus() == null ? ResponseActionStatus.PLANNED : command.actionStatus(),
                command.description(),
                command.targetType(),
                command.targetReferenceId(),
                command.targetReferenceCode(),
                command.plannedStartAt(),
                command.plannedEndAt(),
                command.startedAt(),
                command.completedAt(),
                command.performedByActorId(),
                command.performedByActorNameSnapshot(),
                command.organizationUnitId(),
                command.resultSummary(),
                command.failureReason(),
                now,
                now
        );
        return responseActionRepositoryPort.save(action).id();
    }

    @Override
    public String closeIncident(CloseIncidentCommand command) {
        Objects.requireNonNull(command, "Close incident command must not be null.");
        IncidentClosure closure = new IncidentClosure(
                IncidentId.newId().value(),
                command.incidentId(),
                command.closureSummary(),
                command.resolutionVerified(),
                command.evidenceReviewed(),
                command.rootCauseReviewed(),
                command.followUpActionsCreated(),
                command.closedByActorId(),
                command.closedByActorNameSnapshot(),
                Instant.now(),
                command.workflowInstanceId()
        );
        return closureRepositoryPort.save(closure).id();
    }
}
