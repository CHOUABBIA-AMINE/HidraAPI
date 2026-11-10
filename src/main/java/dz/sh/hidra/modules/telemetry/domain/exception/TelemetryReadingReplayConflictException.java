/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingReplayConflictException
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.exception
 *
 * @Description : Signals conflicting canonical content for an already-used source sequence.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.exception;


public final class TelemetryReadingReplayConflictException extends RuntimeException {
    public TelemetryReadingReplayConflictException() {
        super("Conflicting telemetry observation sequence.");
    }
}
