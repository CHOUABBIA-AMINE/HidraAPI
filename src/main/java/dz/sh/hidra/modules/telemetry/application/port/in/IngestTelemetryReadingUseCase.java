/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IngestTelemetryReadingUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.in
 *
 * @Description : Accepts one authenticated synthetic observation with durable replay.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.in;


import dz.sh.hidra.modules.telemetry.application.command.IngestTelemetryReadingCommand;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;

public interface IngestTelemetryReadingUseCase {
    Outcome ingest(IngestTelemetryReadingCommand command);
    record Outcome(TelemetryReading reading, String sourceId, String effectiveUnitId, boolean replayed) { }
}
