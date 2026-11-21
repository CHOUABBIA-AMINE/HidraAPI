/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetScopedTrustedObservationResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.response
 *
 * @Description : Maps asset-scoped Telemetry query results to explicit HTTP response types.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase.Freshness;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public final class AssetScopedTrustedObservationResponses {
    private AssetScopedTrustedObservationResponses() { }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record BindingView(String id, Instant validFrom, Instant validUntil) { }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record CommissionedPointView(String pointId, String sourceId, String unitId, BindingView binding) { }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record CommissionedPointsView(Instant at, List<CommissionedPointView> points, String nextPointId) { }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record TrustedObservationView(String trustedId, String rawReadingId, String sourceId, String unitId,
                                         BigDecimal numericValue, String textValue, Boolean booleanValue,
                                         Instant observedAt, Instant receivedAt, Instant assessedAt, Instant trustedAt,
                                         String assessmentId, String assessmentStatus, String qualityCodeId,
                                         String trustLevel, BindingView binding, Freshness freshness, BigDecimal ageSeconds) { }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record LatestObservationView(Instant evaluatedAt, int maxAgeSeconds, Freshness status,
                                        TrustedObservationView observation) { }

    public record HistoryCursorView(Instant observedAt, String trustedId) { }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ObservationHistoryView(Instant evaluatedAt, int maxAgeSeconds,
                                         List<TrustedObservationView> observations, HistoryCursorView next) { }

    public static CommissionedPointsView discovery(AssetScopedTrustedObservationReadUseCase.Discovery result) {
        return new CommissionedPointsView(result.at(), result.points().stream()
                .map(point -> new CommissionedPointView(point.id(), point.sourceId(), point.unitId(),
                        binding(point.binding()))).toList(), result.nextPointId());
    }

    public static LatestObservationView latest(AssetScopedTrustedObservationReadUseCase.Latest result) {
        return new LatestObservationView(result.evaluatedAt(), result.maxAgeSeconds(), result.status(),
                result.value() == null ? null : observation(result.value()));
    }

    public static ObservationHistoryView history(AssetScopedTrustedObservationReadUseCase.History result) {
        var cursor = result.next();
        return new ObservationHistoryView(result.evaluatedAt(), result.maxAgeSeconds(),
                result.observations().stream().map(AssetScopedTrustedObservationResponses::observation).toList(),
                cursor == null ? null : new HistoryCursorView(cursor.observedAt(), cursor.trustedId()));
    }

    private static TrustedObservationView observation(AssetScopedTrustedObservationReadUseCase.ObservationView view) {
        var value = view.observation();
        return new TrustedObservationView(value.trustedId(), value.rawReadingId(), value.sourceId(),
                value.unitId(), value.numericValue(), value.textValue(), value.booleanValue(), value.observedAt(),
                value.receivedAt(), value.assessedAt(), value.trustedAt(), value.assessmentId(),
                value.assessmentStatus(), value.qualityCodeId(), value.trustLevel(), binding(value.binding()),
                view.freshness(), view.ageSeconds());
    }

    private static BindingView binding(AssetScopedTrustedObservationReadUseCase.Binding value) {
        return new BindingView(value.id(), value.validFrom(), value.validUntil());
    }
}
