/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReleaseAlarmSuppressionCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.command
 *
 * @Description : Command contract for releasing an active alarm suppression.
 *
 */
package dz.sh.hidra.modules.alarm.application.command;

/**
 * Requests release of one active suppression.
 */
public record ReleaseAlarmSuppressionCommand(
        String suppressionId,
        String actorId,
        String correlationId
) {
}
