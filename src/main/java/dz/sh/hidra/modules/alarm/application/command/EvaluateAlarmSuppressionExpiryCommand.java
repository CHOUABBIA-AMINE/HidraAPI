/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EvaluateAlarmSuppressionExpiryCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.command
 *
 * @Description : Command contract for backend-owned suppression expiry evaluation.
 *
 */
package dz.sh.hidra.modules.alarm.application.command;

import java.time.Instant;

/**
 * Requests idempotent expiry evaluation at an authoritative backend instant.
 */
public record EvaluateAlarmSuppressionExpiryCommand(
        Instant asOf,
        String systemActorId,
        String correlationId
) {
}
