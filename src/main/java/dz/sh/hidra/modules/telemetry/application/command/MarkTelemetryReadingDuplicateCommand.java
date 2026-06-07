/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MarkTelemetryReadingDuplicateCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.command
 *
 * @Description : Command to mark a telemetry reading as duplicate.
 *
 */
package dz.sh.hidra.modules.telemetry.application.command;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingId;
import java.util.Objects;

/**
 * Command to mark a telemetry reading as duplicate.
 *
 * <p>Architecture role:
 * Application-layer command contract for the telemetry module. It carries validated
 * domain value objects and catalog references only. It must not depend on persistence, REST, Spring,
 * JPA, topology implementation classes, flow, risk, analytics, workflow, reporting, or notification.
 */
public record MarkTelemetryReadingDuplicateCommand(
        TelemetryReadingId readingId) implements Command {

    public MarkTelemetryReadingDuplicateCommand {
        readingId = Objects.requireNonNull(readingId, "MarkTelemetryReadingDuplicateCommand readingId must not be null.");
    }
}
