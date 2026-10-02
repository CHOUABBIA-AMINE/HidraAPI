/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AcknowledgeAlarmRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest.request
 *
 * @Description : REST request to acknowledge an alarm; actor identity is server-derived.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest.request;

/**
 * REST request to acknowledge an alarm.
 *
 * The authenticated technical actor and principal name are resolved by the API adapter and are not client-selectable.
 */
public record AcknowledgeAlarmRequest(
        String alarmId,
        String organizationUnitId,
        String organizationUnitCode,
        String comment,
        String correlationId
) {
}
