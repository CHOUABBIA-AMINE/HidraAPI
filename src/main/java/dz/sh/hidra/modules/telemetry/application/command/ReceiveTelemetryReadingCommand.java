/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReceiveTelemetryReadingCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.command
 *
 * @Description : Command to receive a raw telemetry reading.
 *
 */
package dz.sh.hidra.modules.telemetry.application.command;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCorrelationId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryQualityCodeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingValue;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTimestamp;
import java.util.Objects;

/**
 * Command to receive a raw telemetry reading.
 *
 * <p>Architecture role:
 * Application-layer command contract for the telemetry module. It carries validated
 * domain value objects and catalog references only. It must not depend on persistence, REST, Spring,
 * JPA, topology implementation classes, flow, risk, analytics, workflow, reporting, or notification.
 */
public record ReceiveTelemetryReadingCommand(
        TelemetryPointId pointId,
        TelemetryReadingValue value,
        TelemetryQualityCodeReference qualityCode,
        TelemetryTimestamp sourceTimestamp,
        TelemetryIngestionBatchId ingestionBatchId,
        TelemetryCorrelationId correlationId) implements Command {

    public ReceiveTelemetryReadingCommand {
        pointId = Objects.requireNonNull(pointId, "ReceiveTelemetryReadingCommand pointId must not be null.");
        value = Objects.requireNonNull(value, "ReceiveTelemetryReadingCommand value must not be null.");
        qualityCode = Objects.requireNonNull(qualityCode, "ReceiveTelemetryReadingCommand qualityCode must not be null.");
        sourceTimestamp = Objects.requireNonNull(sourceTimestamp, "ReceiveTelemetryReadingCommand sourceTimestamp must not be null.");
    }
}
