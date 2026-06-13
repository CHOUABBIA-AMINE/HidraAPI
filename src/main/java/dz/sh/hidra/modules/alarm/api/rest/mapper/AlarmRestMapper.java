/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest.mapper
 *
 * @Description : Maps alarm REST models to application models.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest.mapper;
import dz.sh.hidra.modules.alarm.api.rest.request.AcknowledgeAlarmRequest;
import dz.sh.hidra.modules.alarm.api.rest.request.CloseAlarmRequest;
import dz.sh.hidra.modules.alarm.api.rest.request.RaiseAlarmRequest;
import dz.sh.hidra.modules.alarm.api.rest.response.AlarmResponse;
import dz.sh.hidra.modules.alarm.application.command.AcknowledgeAlarmCommand;
import dz.sh.hidra.modules.alarm.application.command.CloseAlarmCommand;
import dz.sh.hidra.modules.alarm.application.command.RaiseAlarmCommand;
import dz.sh.hidra.modules.alarm.application.dto.AlarmSummaryDto;

/**
 * Maps alarm REST models to application models.
 */
public final class AlarmRestMapper {

    private AlarmRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static AcknowledgeAlarmCommand toCommand(AcknowledgeAlarmRequest request) {
        return new AcknowledgeAlarmCommand(
                request.alarmId(),
                request.acknowledgedByActorId(),
                request.acknowledgedByDisplayName(),
                request.organizationUnitId(),
                request.organizationUnitCode(),
                request.comment(),
                request.correlationId()
        );
    }

    public static CloseAlarmCommand toCommand(CloseAlarmRequest request) {
        return new CloseAlarmCommand(
                request.alarmId(),
                request.closureType(),
                request.closureReasonId(),
                request.closureComment(),
                request.closedByActorId(),
                request.requiresReview(),
                request.reviewWorkflowInstanceId(),
                request.correlationId()
        );
    }

    public static RaiseAlarmCommand toCommand(RaiseAlarmRequest request) {
        return new RaiseAlarmCommand(
                request.alarmNumber(),
                request.alarmTypeId(),
                request.severityId(),
                request.priorityId(),
                request.titleAr(),
                request.titleFr(),
                request.titleEn(),
                request.descriptionAr(),
                request.descriptionFr(),
                request.descriptionEn(),
                request.sourceType(),
                request.sourceReferenceId(),
                request.monitoringAlertCandidateId(),
                request.monitoringEvaluationId(),
                request.telemetryReadingId(),
                request.planningTargetId(),
                request.topologyAssetTypeCode(),
                request.topologyAssetId(),
                request.topologyAssetCode(),
                request.topologyAssetNameSnapshot(),
                request.firstDetectedAt(),
                request.owningOrganizationUnitId(),
                request.owningOrganizationUnitCode(),
                request.owningOrganizationUnitNameSnapshot(),
                request.workflowInstanceId(),
                request.correlationId()
        );
    }

    public static AlarmResponse toResponse(AlarmSummaryDto dto) {
        return new AlarmResponse(
                dto.id(),
                dto.alarmNumber(),
                dto.alarmTypeId(),
                dto.severityId(),
                dto.titleFr(),
                dto.sourceType(),
                dto.topologyAssetTypeCode(),
                dto.topologyAssetId(),
                dto.topologyAssetCode(),
                dto.currentState(),
                dto.raisedAt(),
                dto.acknowledgedAt(),
                dto.closedAt()
        );
    }
}
