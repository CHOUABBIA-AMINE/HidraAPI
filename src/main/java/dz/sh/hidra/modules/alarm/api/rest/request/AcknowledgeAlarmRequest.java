/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AcknowledgeAlarmRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest.request
 *
 * @Description : REST request to acknowledge an alarm.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest.request;

/**
 * REST request to acknowledge an alarm.
 */
public record AcknowledgeAlarmRequest(
        String alarmId,
        String acknowledgedByActorId,
        String acknowledgedByDisplayName,
        String organizationUnitId,
        String organizationUnitCode,
        String comment,
        String correlationId
) {
}
