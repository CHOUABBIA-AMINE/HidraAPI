/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CloseAlarmRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest.request
 *
 * @Description : REST request for close alarm.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest.request;

import dz.sh.hidra.modules.alarm.domain.value.AlarmClosureType;

/**
 * REST request for close alarm.
 */
public record CloseAlarmRequest(
        String alarmId,
        AlarmClosureType closureType,
        String closureReasonId,
        String closureComment,
        String closedByActorId,
        boolean requiresReview,
        String reviewWorkflowInstanceId,
        String correlationId
) {
}
