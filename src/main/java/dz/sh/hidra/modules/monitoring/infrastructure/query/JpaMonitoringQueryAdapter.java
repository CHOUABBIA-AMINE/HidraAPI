/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMonitoringQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.query
 *
 * @Description : JPA-backed monitoring-rule and deviation query adapter.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.query;

import dz.sh.hidra.modules.monitoring.application.port.in.MonitoringQueryUseCase;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity.MonitoringRuleJpaEntity;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity.PlanActualDeviationJpaEntity;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class JpaMonitoringQueryAdapter implements MonitoringQueryUseCase {

    private final EntityManager entityManager;

    public JpaMonitoringQueryAdapter(EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager, "EntityManager must not be null.");
    }

    @Override
    public Page<MonitoringRuleView> rules(String status, String topologyAssetId, String telemetryPointId, int page, int size) {
        List<MonitoringRuleView> all = entityManager
                .createQuery("select e from MonitoringRuleJpaEntity e order by e.code", MonitoringRuleJpaEntity.class)
                .getResultList().stream()
                .filter(entity -> blank(status) || status.equalsIgnoreCase(String.valueOf(entity.status())))
                .filter(entity -> blank(topologyAssetId) || topologyAssetId.equals(entity.topologyAssetId()))
                .filter(entity -> blank(telemetryPointId) || telemetryPointId.equals(entity.telemetryPointId()))
                .map(this::ruleView)
                .toList();
        return page(all, page, size);
    }

    @Override
    public MonitoringRuleView rule(String id) {
        MonitoringRuleJpaEntity entity = entityManager.find(MonitoringRuleJpaEntity.class, id);
        if (entity == null) {
            throw new IllegalArgumentException("Unknown monitoring rule: " + id);
        }
        return ruleView(entity);
    }

    @Override
    public Page<DeviationView> deviations(
            String planTargetId,
            String status,
            String severity,
            String topologyAssetId,
            String telemetryPointId,
            Instant from,
            Instant to,
            int page,
            int size
    ) {
        List<DeviationView> all = entityManager
                .createQuery("select e from PlanActualDeviationJpaEntity e order by e.detectedAt desc", PlanActualDeviationJpaEntity.class)
                .getResultList().stream()
                .filter(entity -> blank(planTargetId) || planTargetId.equals(entity.planTargetId()))
                .filter(entity -> blank(status) || status.equalsIgnoreCase(String.valueOf(entity.status())))
                .filter(entity -> blank(severity) || severity.equalsIgnoreCase(String.valueOf(entity.severity())))
                .filter(entity -> blank(topologyAssetId) || topologyAssetId.equals(entity.topologyAssetId()))
                .filter(entity -> blank(telemetryPointId) || telemetryPointId.equals(entity.telemetryPointId()))
                .filter(entity -> from == null || !entity.detectedAt().isBefore(from))
                .filter(entity -> to == null || !entity.detectedAt().isAfter(to))
                .map(this::deviationView)
                .toList();
        return page(all, page, size);
    }

    @Override
    public DeviationView deviation(String id) {
        PlanActualDeviationJpaEntity entity = entityManager.find(PlanActualDeviationJpaEntity.class, id);
        if (entity == null) {
            throw new IllegalArgumentException("Unknown monitoring deviation: " + id);
        }
        return deviationView(entity);
    }

    private MonitoringRuleView ruleView(MonitoringRuleJpaEntity entity) {
        return new MonitoringRuleView(
                entity.id(), entity.code(), entity.nameAr(), entity.nameFr(), entity.nameEn(),
                String.valueOf(entity.ruleType()), entity.evaluationFrequencyId(), entity.topologyAssetType(),
                entity.topologyAssetId(), entity.topologyAssetCode(), entity.telemetryPointId(),
                entity.planningTargetTypeId(), String.valueOf(entity.status()), entity.createdByActorId(),
                entity.createdAt(), entity.updatedAt()
        );
    }

    private DeviationView deviationView(PlanActualDeviationJpaEntity entity) {
        return new DeviationView(
                entity.id(), entity.evaluationId(), entity.planTargetId(), entity.expectedFlowStateId(),
                entity.trustedTelemetryReadingId(), entity.telemetryPointId(), entity.topologyAssetType(),
                entity.topologyAssetId(), entity.topologyAssetCode(), entity.actualValue(), entity.expectedValue(),
                entity.differenceValue(), entity.differencePercent(), entity.unitId(), String.valueOf(entity.severity()),
                String.valueOf(entity.status()), entity.detectedAt(), entity.resolvedAt(), entity.reasonCode(),
                entity.reasonMessage()
        );
    }

    private static boolean blank(String value) {
        return value == null || value.isBlank();
    }

    private static <T> Page<T> page(List<T> all, int requestedPage, int requestedSize) {
        int page = Math.max(0, requestedPage);
        int size = Math.min(200, Math.max(1, requestedSize));
        int from = Math.min(all.size(), page * size);
        int to = Math.min(all.size(), from + size);
        int totalPages = all.isEmpty() ? 0 : (all.size() + size - 1) / size;
        return new Page<>(List.copyOf(all.subList(from, to)), page, size, all.size(), totalPages, to < all.size());
    }
}
