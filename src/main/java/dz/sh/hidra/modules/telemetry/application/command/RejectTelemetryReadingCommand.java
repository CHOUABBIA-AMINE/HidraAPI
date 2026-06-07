/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RejectTelemetryReadingCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.command
 *
 * @Description : Command to reject a telemetry reading.
 *
 */
package dz.sh.hidra.modules.telemetry.application.command;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingId;
import java.util.Objects;

/**
 * Command to reject a telemetry reading.
 *
 * <p>Architecture role:
 * Application-layer command contract for the telemetry module. It carries validated
 * domain value objects and catalog references only. It must not depend on persistence, REST, Spring,
 * JPA, topology implementation classes, flow, risk, analytics, workflow, reporting, or notification.
 */
public record RejectTelemetryReadingCommand(
        TelemetryReadingId readingId,
        String reason) implements Command {

    public RejectTelemetryReadingCommand {
        readingId = Objects.requireNonNull(readingId, "RejectTelemetryReadingCommand readingId must not be null.");
        reason = Objects.requireNonNull(reason, "RejectTelemetryReadingCommand reason must not be null.");
    }
}
