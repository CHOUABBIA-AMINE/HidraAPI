/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest.response
 *
 * @Description : REST response for alarm summary.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest.response;

import dz.sh.hidra.modules.alarm.domain.value.AlarmSourceType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmState;

import java.time.Instant;

/**
 * REST response for alarm summary.
 */
public record AlarmResponse(
        String id,
        String alarmNumber,
        String alarmTypeId,
        String severityId,
        String titleFr,
        AlarmSourceType sourceType,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCode,
        AlarmState currentState,
        Instant raisedAt,
        Instant acknowledgedAt,
        Instant closedAt
) {
}
