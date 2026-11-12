/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryTrustedReadPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Retrieves qualified observation lineage under caller-established scope.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

import dz.sh.hidra.modules.telemetry.domain.model.TrustedTelemetryReading;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface TelemetryTrustedReadPort {
    Optional<Published> byRaw(String readingId);
    Optional<Published> byId(String pointId, String trustedId);
    List<Published> history(String pointId, int limit);

    record Published(TrustedTelemetryReading reading, String reasonCode,
            Instant receivedAt, Instant assessedAt, boolean replayed) { }
}
