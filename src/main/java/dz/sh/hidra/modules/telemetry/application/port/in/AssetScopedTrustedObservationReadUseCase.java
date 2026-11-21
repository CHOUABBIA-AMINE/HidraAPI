/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetScopedTrustedObservationReadUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.in
 *
 * @Description : Defines the asset-scoped trusted observation query contract.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.in;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface AssetScopedTrustedObservationReadUseCase {
    enum AssetType { FACILITY, EQUIPMENT }
    enum Freshness { FRESH, STALE, UNAVAILABLE, FRESHNESS_UNAVAILABLE }

    record Asset(String companyPartyId, AssetType type, String id) { }

    record DiscoveryQuery(Asset asset, Instant at, int limit, String afterPointId) { }
    record LatestQuery(Asset asset, String pointId, int maxAgeSeconds) { }
    record HistoryQuery(Asset asset, String pointId, Instant from, Instant until,
                        int limit, HistoryCursor after, int maxAgeSeconds) { }

    record HistoryCursor(Instant observedAt, String trustedId) { }
    record Binding(String id, Instant validFrom, Instant validUntil) { }
    record Point(String id, String sourceId, String unitId, Binding binding) { }
    record Discovery(Instant at, List<Point> points, String nextPointId) { }

    record Observation(String trustedId, String rawReadingId, String sourceId, String unitId,
                       BigDecimal numericValue, String textValue, Boolean booleanValue,
                       Instant observedAt, Instant receivedAt, Instant assessedAt, Instant trustedAt,
                       String assessmentId, String assessmentStatus, String qualityCodeId,
                       String trustLevel, Binding binding) { }

    record ObservationView(Observation observation, Freshness freshness, BigDecimal ageSeconds) { }
    record Latest(Instant evaluatedAt, int maxAgeSeconds, Freshness status, ObservationView value) { }
    record History(Instant evaluatedAt, int maxAgeSeconds, List<ObservationView> observations,
                   HistoryCursor next) { }

    Discovery discover(DiscoveryQuery query);
    Latest latest(LatestQuery query);
    History history(HistoryQuery query);
}
