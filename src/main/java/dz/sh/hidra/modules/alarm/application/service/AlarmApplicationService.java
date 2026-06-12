/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmApplicationService
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.service
 *
 * @Description : Application service for alarm lifecycle commands.
 *
 */
package dz.sh.hidra.modules.alarm.application.service;

import dz.sh.hidra.modules.alarm.application.command.AcknowledgeAlarmCommand;
import dz.sh.hidra.modules.alarm.application.command.CloseAlarmCommand;
import dz.sh.hidra.modules.alarm.application.command.RaiseAlarmCommand;
import dz.sh.hidra.modules.alarm.application.dto.AlarmSummaryDto;
import dz.sh.hidra.modules.alarm.application.mapper.AlarmApplicationMapper;
import dz.sh.hidra.modules.alarm.application.port.in.AcknowledgeAlarmUseCase;
import dz.sh.hidra.modules.alarm.application.port.in.CloseAlarmUseCase;
import dz.sh.hidra.modules.alarm.application.port.in.RaiseAlarmUseCase;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmAcknowledgementRepositoryPort;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmClosureRepositoryPort;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmRepositoryPort;
import dz.sh.hidra.modules.alarm.domain.model.Alarm;
import dz.sh.hidra.modules.alarm.domain.model.AlarmAcknowledgement;
import dz.sh.hidra.modules.alarm.domain.model.AlarmClosure;
import dz.sh.hidra.modules.alarm.domain.value.AlarmId;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSourceType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmState;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for alarm lifecycle commands.
 */
public class AlarmApplicationService implements RaiseAlarmUseCase, AcknowledgeAlarmUseCase, CloseAlarmUseCase {

    private final AlarmRepositoryPort alarmRepositoryPort;
    private final AlarmAcknowledgementRepositoryPort acknowledgementRepositoryPort;
    private final AlarmClosureRepositoryPort closureRepositoryPort;

    public AlarmApplicationService(
            AlarmRepositoryPort alarmRepositoryPort,
            AlarmAcknowledgementRepositoryPort acknowledgementRepositoryPort,
            AlarmClosureRepositoryPort closureRepositoryPort
    ) {
        this.alarmRepositoryPort = Objects.requireNonNull(alarmRepositoryPort, "Alarm repository port must not be null.");
        this.acknowledgementRepositoryPort = Objects.requireNonNull(acknowledgementRepositoryPort, "Alarm acknowledgement repository port must not be null.");
        this.closureRepositoryPort = Objects.requireNonNull(closureRepositoryPort, "Alarm closure repository port must not be null.");
    }

    @Override
    public AlarmSummaryDto raiseAlarm(RaiseAlarmCommand command) {
        Objects.requireNonNull(command, "Raise alarm command must not be null.");
        Instant now = Instant.now();
        Alarm alarm = new Alarm(
                AlarmId.newId().value(),
                command.alarmNumber(),
                command.alarmTypeId(),
                command.severityId(),
                command.priorityId(),
                command.titleAr(),
                command.titleFr(),
                command.titleEn(),
                command.descriptionAr(),
                command.descriptionFr(),
                command.descriptionEn(),
                command.sourceType() == null ? AlarmSourceType.MANUAL : command.sourceType(),
                command.sourceReferenceId(),
                command.monitoringAlertCandidateId(),
                command.monitoringEvaluationId(),
                command.telemetryReadingId(),
                command.planningTargetId(),
                command.topologyAssetTypeCode(),
                command.topologyAssetId(),
                command.topologyAssetCode(),
                command.topologyAssetNameSnapshot(),
                AlarmState.RAISED,
                now,
                command.firstDetectedAt(),
                now,
                null,
                null,
                null,
                null,
                command.owningOrganizationUnitId(),
                command.owningOrganizationUnitCode(),
                command.owningOrganizationUnitNameSnapshot(),
                command.workflowInstanceId(),
                null,
                command.correlationId(),
                now,
                now
        );
        return AlarmApplicationMapper.toSummary(alarmRepositoryPort.save(alarm));
    }

    @Override
    public String acknowledgeAlarm(AcknowledgeAlarmCommand command) {
        Objects.requireNonNull(command, "Acknowledge alarm command must not be null.");
        AlarmAcknowledgement acknowledgement = new AlarmAcknowledgement(
                AlarmId.newId().value(),
                command.alarmId(),
                command.acknowledgedByActorId(),
                command.acknowledgedByDisplayName(),
                command.organizationUnitId(),
                command.organizationUnitCode(),
                Instant.now(),
                command.comment(),
                command.correlationId()
        );
        return acknowledgementRepositoryPort.save(acknowledgement).id();
    }

    @Override
    public String closeAlarm(CloseAlarmCommand command) {
        Objects.requireNonNull(command, "Close alarm command must not be null.");
        AlarmClosure closure = new AlarmClosure(
                AlarmId.newId().value(),
                command.alarmId(),
                command.closureType(),
                command.closureReasonId(),
                command.closureComment(),
                command.closedByActorId(),
                Instant.now(),
                command.requiresReview(),
                command.reviewWorkflowInstanceId(),
                command.correlationId()
        );
        return closureRepositoryPort.save(closure).id();
    }
}
