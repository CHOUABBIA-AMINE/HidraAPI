/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RaiseAlarmRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest.request
 *
 * @Description : REST request to raise an alarm.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest.request;

import dz.sh.hidra.modules.alarm.domain.value.AlarmSourceType;

import java.time.Instant;

/**
 * REST request to raise an alarm.
 */
public record RaiseAlarmRequest(
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
