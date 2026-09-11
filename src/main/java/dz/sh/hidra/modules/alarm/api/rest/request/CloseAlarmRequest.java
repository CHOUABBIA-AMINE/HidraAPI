/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CloseAlarmRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest.request
 *
 * @Description : REST request for closing an alarm; actor identity is server-derived.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest.request;

import dz.sh.hidra.modules.alarm.domain.value.AlarmClosureType;

/**
 * REST request for close alarm.
 *
 * The authenticated technical actor is resolved by the API adapter and is not client-selectable.
 */
public record CloseAlarmRequest(
        String alarmId,
        AlarmClosureType closureType,
        String closureReasonId,
        String closureComment,
        boolean requiresReview,
        String reviewWorkflowInstanceId,
        String correlationId
) {
}
