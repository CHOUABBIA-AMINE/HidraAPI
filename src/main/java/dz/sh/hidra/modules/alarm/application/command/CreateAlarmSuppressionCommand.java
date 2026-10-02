/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateAlarmSuppressionCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.command
 *
 * @Description : Command contract for creating an alarm suppression.
 *
 */
package dz.sh.hidra.modules.alarm.application.command;

import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionScopeType;

import java.time.Instant;

/**
 * Requests creation of one governed alarm suppression.
 *
 * <p>The API boundary is responsible for deriving the authenticated actor identity.
 * Open-ended requests carry workflow evidence for later policy verification.</p>
 */
public record CreateAlarmSuppressionCommand(
        AlarmSuppressionScopeType scopeType,
        String scopeReferenceId,
        String alarmId,
        String alarmTypeId,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String suppressionReasonId,
        String reasonText,
        String actorId,
        Instant suppressedUntil,
        String workflowInstanceId,
        String correlationId
) {
}
