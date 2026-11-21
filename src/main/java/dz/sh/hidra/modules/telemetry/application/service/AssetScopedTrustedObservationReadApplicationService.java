/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetScopedTrustedObservationReadApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.service
 *
 * @Description : Orchestrates protected asset-scoped trusted observation reads.
 *
 */
package dz.sh.hidra.modules.telemetry.application.service;

import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase;
import dz.sh.hidra.modules.telemetry.application.port.out.AssetScopedTrustedObservationAuthorizationPort;
import dz.sh.hidra.modules.telemetry.application.port.out.AssetScopedTrustedObservationReadPort;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AssetScopedTrustedObservationReadApplicationService implements AssetScopedTrustedObservationReadUseCase {
    // The native require operation can roll back its transaction on an ordinary denial.
    // Authorize in short owner transactions and revalidate the Telemetry read before disclosure.
    private static final int DISCOVERY_BATCH_SIZE = 50;
    private final AssetScopedTrustedObservationReadPort read;
    private final AssetScopedTrustedObservationAuthorizationPort authority;

    public AssetScopedTrustedObservationReadApplicationService(AssetScopedTrustedObservationReadPort read,
            AssetScopedTrustedObservationAuthorizationPort authority) {
        this.read = Objects.requireNonNull(read);
        this.authority = Objects.requireNonNull(authority);
    }

    @Override
    public Discovery discover(DiscoveryQuery query) {
        Instant now = now();
        Asset asset = query.asset();
        Instant at = query.at() == null ? now : query.at();
        int limit = limit(query.limit());
        if (at.isAfter(now)) throw invalid();
        requireAsset(asset);

        String after = query.afterPointId();
        if (after != null) {
            requireId(after);
            if (visiblePoint(asset, after, at).isEmpty()) throw invalid();
        }
        var eligible = new ArrayList<Point>(limit + 1);
        while (eligible.size() <= limit) {
            List<String> candidates = read.pointIds(asset, at, after, DISCOVERY_BATCH_SIZE);
            for (String candidate : candidates) {
                visiblePoint(asset, candidate, at).ifPresent(eligible::add);
                if (eligible.size() > limit) break;
            }
            if (eligible.size() > limit || candidates.size() < DISCOVERY_BATCH_SIZE) break;
            after = candidates.getLast();
        }

        requireAsset(asset);
        for (Point point : eligible) requireUnchanged(asset, point, at);
        boolean more = eligible.size() > limit;
        List<Point> page = List.copyOf(eligible.subList(0, Math.min(limit, eligible.size())));
        return new Discovery(at, page, more ? page.getLast().id() : null);
    }

    @Override
    public Latest latest(LatestQuery query) {
        Instant now = now();
        Asset asset = query.asset();
        requireId(query.pointId());
        requireMaxAge(query.maxAgeSeconds());
        requireAsset(asset);

        Point point = visiblePoint(asset, query.pointId(), now).orElseThrow(
                AssetScopedTrustedObservationReadApplicationService::unavailable);
        Optional<String> candidate = read.latestCandidate(asset, point);
        Optional<Observation> observation = candidate
                .flatMap(id -> read.evidence(asset, point.id(), List.of(point.sourceId()), id))
                .filter(value -> value.binding().equals(point.binding()) && Objects.equals(value.unitId(), point.unitId())
                        && !value.observedAt().isAfter(now));

        if (!candidate.equals(read.latestCandidate(asset, point))) throw unavailable();
        requireAsset(asset);
        requireUnchanged(asset, point, now);
        return observation.map(value -> {
            ObservationView view = view(value, now, query.maxAgeSeconds());
            return new Latest(now, query.maxAgeSeconds(), view.freshness(), view);
        }).orElseGet(() -> new Latest(now, query.maxAgeSeconds(), Freshness.UNAVAILABLE, null));
    }

    @Override
    public History history(HistoryQuery query) {
        Instant now = now();
        Asset asset = query.asset();
        requireId(query.pointId());
        requireMaxAge(query.maxAgeSeconds());
        int limit = limit(query.limit());
        if (query.from() == null || query.until() == null || !query.from().isBefore(query.until())
                || query.until().isAfter(now)
                || Duration.between(query.from(), query.until()).compareTo(Duration.ofDays(31)) > 0) {
            throw invalid();
        }
        if (query.after() != null) {
            requireId(query.after().trustedId());
            if (!inside(query.after().observedAt(), query.from(), query.until())) throw invalid();
        }
        requireAsset(asset);
        List<String> sources = visibleSources(query);
        if (sources.isEmpty()) throw unavailable();

        if (query.after() != null && read.evidence(asset, query.pointId(), sources, query.after().trustedId())
                .filter(value -> value.observedAt().equals(query.after().observedAt())).isEmpty()) {
            throw invalid();
        }
        List<Observation> rows = read.history(asset, query.pointId(), sources, query.from(), query.until(),
                query.after(), limit + 1);
        if (!rows.equals(read.history(asset, query.pointId(), sources, query.from(), query.until(),
                query.after(), limit + 1))) throw unavailable();
        requireAsset(asset);
        if (!visibleSources(query).equals(sources)) throw unavailable();

        List<Observation> page = rows.subList(0, Math.min(limit, rows.size()));
        HistoryCursor next = null;
        if (rows.size() > limit) {
            Observation last = page.getLast();
            next = new HistoryCursor(last.observedAt(), last.trustedId());
        }
        return new History(now, query.maxAgeSeconds(),
                page.stream().map(value -> view(value, now, query.maxAgeSeconds())).toList(), next);
    }

    private Optional<Point> visiblePoint(Asset asset, String pointId, Instant at) {
        return read.membership(asset, pointId, at)
                .filter(point -> authority.permitsPoint(asset, point.sourceId(), pointId));
    }

    private void requireUnchanged(Asset asset, Point point, Instant at) {
        if (read.membership(asset, point.id(), at).filter(point::equals).isEmpty()
                || !authority.permitsPoint(asset, point.sourceId(), point.id())) throw unavailable();
    }

    private List<String> visibleSources(HistoryQuery query) {
        return read.historicalSources(query.asset(), query.pointId(), query.from(), query.until()).stream()
                .filter(source -> authority.permitsPoint(query.asset(), source, query.pointId())).toList();
    }

    private void requireAsset(Asset asset) {
        requireId(asset.companyPartyId());
        requireId(asset.id());
        if (asset.type() == null) throw invalid();
        if (!authority.permitsAsset(asset)) throw unavailable();
    }

    private static ObservationView view(Observation value, Instant evaluatedAt, int maxAgeSeconds) {
        if (value.observedAt() == null || value.observedAt().isAfter(evaluatedAt)) {
            return new ObservationView(value, Freshness.FRESHNESS_UNAVAILABLE, null);
        }
        Duration age = Duration.between(value.observedAt(), evaluatedAt);
        Freshness freshness = age.compareTo(Duration.ofSeconds(maxAgeSeconds)) <= 0
                ? Freshness.FRESH : Freshness.STALE;
        BigDecimal seconds = BigDecimal.valueOf(age.getSeconds()).add(BigDecimal.valueOf(age.getNano(), 9));
        return new ObservationView(value, freshness, seconds);
    }

    private static boolean inside(Instant time, Instant from, Instant until) {
        return time != null && !time.isBefore(from) && time.isBefore(until);
    }

    private static Instant now() {
        return Instant.now().truncatedTo(ChronoUnit.MICROS);
    }

    private static void requireId(String id) {
        if (id == null || id.isBlank() || id.length() > 80 || !id.equals(id.strip())) throw invalid();
    }

    private static void requireMaxAge(int seconds) {
        if (seconds < 1 || seconds > 604800) throw invalid();
    }

    private static int limit(int limit) {
        if (limit < 1 || limit > 50) throw invalid();
        return limit;
    }

    private static IllegalArgumentException invalid() {
        return new IllegalArgumentException("Invalid commissioned observation query");
    }

    private static NoSuchElementException unavailable() {
        return new NoSuchElementException("Resource unavailable");
    }
}
