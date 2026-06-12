/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AcknowledgeAlarmCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.command
 *
 * @Description : Command to acknowledge an alarm.
 *
 */
package dz.sh.hidra.modules.alarm.application.command;

/**
 * Command to acknowledge an alarm.
 */
public record AcknowledgeAlarmCommand(
        String alarmId,
        String acknowledgedByActorId,
        String acknowledgedByDisplayName,
        String organizationUnitId,
        String organizationUnitCode,
        String comment,
        String correlationId
) {
}
