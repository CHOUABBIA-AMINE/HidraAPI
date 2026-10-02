/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CloseAlarmCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.command
 *
 * @Description : Command to close an alarm.
 *
 */
package dz.sh.hidra.modules.alarm.application.command;

import dz.sh.hidra.modules.alarm.domain.value.AlarmClosureType;

/**
 * Command to close an alarm.
 */
public record CloseAlarmCommand(
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
