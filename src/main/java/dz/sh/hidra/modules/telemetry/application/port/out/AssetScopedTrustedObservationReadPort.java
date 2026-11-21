/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetScopedTrustedObservationReadPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Reads commissioned membership and pinned Telemetry observation evidence.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase.Asset;
import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase.HistoryCursor;
import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase.Observation;
import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase.Point;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface AssetScopedTrustedObservationReadPort {
    List<String> pointIds(Asset asset, Instant at, String after, int batchSize);
    Optional<Point> membership(Asset asset, String pointId, Instant at);
    List<String> historicalSources(Asset asset, String pointId, Instant from, Instant until);

    Optional<String> latestCandidate(Asset asset, Point point);
    Optional<Observation> evidence(Asset asset, String pointId, List<String> sources, String trustedId);
    List<Observation> history(Asset asset, String pointId, List<String> sources, Instant from, Instant until,
                              HistoryCursor after, int limit);
}
