/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RaiseAlarmCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.command
 *
 * @Description : Command to raise a formal alarm.
 *
 */
package dz.sh.hidra.modules.alarm.application.command;

import dz.sh.hidra.modules.alarm.domain.value.AlarmSourceType;

import java.time.Instant;

/**
 * Command to raise a formal alarm.
 */
public record RaiseAlarmCommand(
        String alarmNumber,
        String alarmTypeId,
        String severityId,
        String priorityId,
        String titleAr,
        String titleFr,
        String titleEn,
        String descriptionAr,
        String descriptionFr,
        String descriptionEn,
        AlarmSourceType sourceType,
        String sourceReferenceId,
        String monitoringAlertCandidateId,
        String monitoringEvaluationId,
        String telemetryReadingId,
        String planningTargetId,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCode,
        String topologyAssetNameSnapshot,
        Instant firstDetectedAt,
        String owningOrganizationUnitId,
        String owningOrganizationUnitCode,
        String owningOrganizationUnitNameSnapshot,
        String workflowInstanceId,
        String correlationId
) {
}
