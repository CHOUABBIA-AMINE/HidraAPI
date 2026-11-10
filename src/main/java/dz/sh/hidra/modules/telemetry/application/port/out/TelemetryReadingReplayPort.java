/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingReplayPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Atomic PostgreSQL insert-or-replay boundary with immutable canonical digest.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;


import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;

public interface TelemetryReadingReplayPort {
    Result appendOrReplay(String sourceId, String effectiveUnitId, TelemetryReading reading);
    record Result(TelemetryReading reading, String effectiveUnitId, boolean replayed) { }
}
