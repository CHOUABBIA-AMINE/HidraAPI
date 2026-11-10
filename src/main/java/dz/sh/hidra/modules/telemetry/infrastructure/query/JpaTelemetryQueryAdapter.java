/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaTelemetryQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.query
 *
 * @Description : JPA-backed telemetry time-series and reference metadata query adapter.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.query;

import dz.sh.hidra.modules.telemetry.application.port.in.TelemetryQueryUseCase;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingIngestionReferencePort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingScopeAuthorizationPort;
import dz.sh.hidra.modules.telemetry.domain.value.ReadingState;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryCatalogEntryJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryCatalogTranslationJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryPointJpaEntity;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity.TelemetryReadingJpaEntity;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class JpaTelemetryQueryAdapter implements TelemetryQueryUseCase {

    private static final String QUALITY_CODE_CATALOG = "QUALITY_CODE";

    private final EntityManager entityManager;
    private final TelemetryReadingIngestionReferencePort references;
    private final TelemetryReadingScopeAuthorizationPort authorization;

    public JpaTelemetryQueryAdapter(EntityManager entityManager,
            TelemetryReadingIngestionReferencePort references,
            TelemetryReadingScopeAuthorizationPort authorization) {
        this.entityManager=Objects.requireNonNull(entityManager);
        this.references=Objects.requireNonNull(references);
        this.authorization=Objects.requireNonNull(authorization);
    }

    @Override
    public Page<ReadingView> readings(String pointId, Instant from, Instant to, String state, int page, int size) {
        String unitId = unitId(pointId);
        List<ReadingView> all = entityManager
                .createQuery("select e from TelemetryReadingJpaEntity e where e.pointId = :point order by e.sourceTimestamp desc", TelemetryReadingJpaEntity.class)
                .setParameter("point", pointId)
                .getResultList().stream()
                .filter(entity -> from == null || !entity.sourceTimestamp().isBefore(from))
                .filter(entity -> to == null || !entity.sourceTimestamp().isAfter(to))
                .filter(entity -> state == null || state.isBlank() || state.equalsIgnoreCase(String.valueOf(entity.state())))
                .map(entity -> readingView(entity, unitId))
                .toList();
        return page(all, page, size);
    }

    @Override
    public ReadingView latestReading(String pointId) {
        String unitId = unitId(pointId);
        return entityManager
                .createQuery("select e from TelemetryReadingJpaEntity e where e.pointId = :point order by e.sourceTimestamp desc", TelemetryReadingJpaEntity.class)
                .setParameter("point", pointId)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .map(entity -> readingView(entity, unitId))
                .orElseThrow(() -> new IllegalArgumentException("No telemetry reading exists for point: " + pointId));
    }

    @Override
    public List<ReadingView> trend(String pointId, Instant from, Instant to, int limit) {
        int boundedLimit = Math.min(10_000, Math.max(1, limit));
        String unitId = unitId(pointId);
        return entityManager
                .createQuery("select e from TelemetryReadingJpaEntity e where e.pointId = :point order by e.sourceTimestamp", TelemetryReadingJpaEntity.class)
                .setParameter("point", pointId)
                .getResultList().stream()
                .filter(entity -> from == null || !entity.sourceTimestamp().isBefore(from))
                .filter(entity -> to == null || !entity.sourceTimestamp().isAfter(to))
                .limit(boundedLimit)
                .map(entity -> readingView(entity, unitId))
                .toList();
    }

    @Override
    public List<String> readingStates() {
        return List.of(ReadingState.values()).stream().map(Enum::name).toList();
    }

    @Override
    public List<QualityCodeView> qualityCodes() {
        List<TelemetryCatalogTranslationJpaEntity> translations = entityManager
                .createQuery("select e from TelemetryCatalogTranslationJpaEntity e", TelemetryCatalogTranslationJpaEntity.class)
                .getResultList();
        Map<String, Map<String, TranslationView>> translationsByEntry = new LinkedHashMap<>();
        for (TelemetryCatalogTranslationJpaEntity translation : translations) {
            translationsByEntry
                    .computeIfAbsent(translation.typeId(), ignored -> new LinkedHashMap<>())
                    .put(translation.locale(), new TranslationView(
                            translation.locale(), translation.name(), translation.description()));
        }
        return entityManager
                .createQuery("select e from TelemetryCatalogEntryJpaEntity e where e.catalogName = :catalog", TelemetryCatalogEntryJpaEntity.class)
                .setParameter("catalog", QUALITY_CODE_CATALOG)
                .getResultList().stream()
                .sorted(Comparator.comparingInt(TelemetryCatalogEntryJpaEntity::sortOrder)
                        .thenComparing(TelemetryCatalogEntryJpaEntity::code))
                .map(entry -> new QualityCodeView(
                        entry.id(), entry.code(), entry.active(), entry.sortOrder(), entry.systemDefined(),
                        Map.copyOf(translationsByEntry.getOrDefault(entry.id(), Map.of()))))
                .toList();
    }

    private String unitId(String pointId) {
        // No existence, count, history or latest-value disclosure before original-transaction native authority.
        var scope=references.current(pointId,null,Instant.now());
        authorization.require(scope,TelemetryReadingScopeAuthorizationPort.Operation.READ);
        return scope.unitId();
    }

    private ReadingView readingView(TelemetryReadingJpaEntity entity, String unitId) {
        // Immutable ingest receipt preserves the accepted unit even if point metadata later changes.
        var receipts=entityManager.createNativeQuery("""
                select unit_id from public.hidra_telemetry_reading_ingest_receipt where reading_id=:reading
                """).setParameter("reading",entity.id()).getResultList();
        String effectiveUnit=receipts.isEmpty()?unitId:(String)receipts.getFirst();
        return new ReadingView(
                entity.id(), entity.pointId(), entity.numericValue(), entity.textValue(), entity.booleanValue(),
                effectiveUnit, entity.qualityCodeId(), entity.sourceTimestamp(), entity.receivedAt(),
                String.valueOf(entity.state()), entity.correlationId(), entity.rejectionReason()
        );
    }

    private static <T> Page<T> page(List<T> all, int requestedPage, int requestedSize) {
        int page = Math.max(0, requestedPage);
        int size = Math.min(500, Math.max(1, requestedSize));
        int from = Math.min(all.size(), page * size);
        int to = Math.min(all.size(), from + size);
        int totalPages = all.isEmpty() ? 0 : (all.size() + size - 1) / size;
        return new Page<>(List.copyOf(all.subList(from, to)), page, size, all.size(), totalPages, to < all.size());
    }
}
