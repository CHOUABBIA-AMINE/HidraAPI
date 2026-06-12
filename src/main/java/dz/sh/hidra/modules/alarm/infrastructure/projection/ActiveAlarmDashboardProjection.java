/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ActiveAlarmDashboardProjection
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.projection
 *
 * @Description : Active alarm dashboard projection.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.projection;

import dz.sh.hidra.modules.alarm.domain.value.AlarmState;

import java.time.Instant;

/**
 * Active alarm dashboard projection.
 */
public record ActiveAlarmDashboardProjection(
        String alarmId,
        String alarmNumber,
        String titleFr,
        String severityId,
        String priorityId,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCode,
        AlarmState currentState,
        Instant raisedAt,
        Instant acknowledgedAt
) {
}
