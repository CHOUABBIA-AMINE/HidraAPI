/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.dto
 *
 * @Description : Application read model for alarm suppression evidence.
 *
 */
package dz.sh.hidra.modules.alarm.application.dto;

import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionScopeType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionStatus;

import java.time.Instant;

/**
 * Suppression evidence returned by application use cases.
 */
public record AlarmSuppressionDto(
        String id,
        AlarmSuppressionScopeType scopeType,
        String scopeReferenceId,
        String alarmId,
        String alarmTypeId,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String suppressionReasonId,
        String reasonText,
        String suppressedByActorId,
        Instant suppressedAt,
        Instant suppressedUntil,
        Instant releasedAt,
        String releasedByActorId,
        AlarmSuppressionStatus status,
        String workflowInstanceId,
        String correlationId
) {
}
