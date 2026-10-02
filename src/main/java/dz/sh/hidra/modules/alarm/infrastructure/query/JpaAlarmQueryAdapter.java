/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAlarmQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.query
 *
 * @Description : JPA-backed active/history/detail alarm and shelving query adapter.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.query;

import dz.sh.hidra.modules.alarm.application.port.in.AlarmQueryUseCase;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.entity.AlarmJpaEntity;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.entity.AlarmShelvingJpaEntity;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class JpaAlarmQueryAdapter implements AlarmQueryUseCase {

    private static final Set<String> TERMINAL_STATES = Set.of("CLEARED", "CLOSED", "CANCELLED");

    private final EntityManager entityManager;

    public JpaAlarmQueryAdapter(EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager, "EntityManager must not be null.");
    }

    @Override
    public Page<AlarmView> alarms(
            String view,
            String state,
            String severityId,
            String topologyAssetId,
            Instant from,
            Instant to,
            int page,
            int size
    ) {
        String normalizedView = view == null ? "active" : view.trim().toLowerCase(Locale.ROOT);
        List<AlarmView> all = entityManager
                .createQuery("select e from AlarmJpaEntity e order by e.raisedAt desc", AlarmJpaEntity.class)
                .getResultList().stream()
                .filter(entity -> matchesView(entity, normalizedView))
                .filter(entity -> blank(state) || state.equalsIgnoreCase(String.valueOf(entity.currentState())))
                .filter(entity -> blank(severityId) || severityId.equals(entity.severityId()))
                .filter(entity -> blank(topologyAssetId) || topologyAssetId.equals(entity.topologyAssetId()))
                .filter(entity -> from == null || !entity.raisedAt().isBefore(from))
                .filter(entity -> to == null || !entity.raisedAt().isAfter(to))
                .map(this::alarmView)
                .toList();
        return page(all, page, size);
    }

    @Override
    public AlarmView alarm(String id) {
        AlarmJpaEntity entity = entityManager.find(AlarmJpaEntity.class, id);
        if (entity == null) {
            throw new IllegalArgumentException("Unknown alarm: " + id);
        }
        return alarmView(entity);
    }

    @Override
    public List<ShelvingView> shelvings(String alarmId) {
        return entityManager
                .createQuery("select e from AlarmShelvingJpaEntity e where e.alarmId = :alarm order by e.shelvedAt desc", AlarmShelvingJpaEntity.class)
                .setParameter("alarm", alarmId)
                .getResultList().stream()
                .map(this::shelvingView)
                .toList();
    }

    private boolean matchesView(AlarmJpaEntity entity, String view) {
        String state = String.valueOf(entity.currentState());
        return switch (view) {
            case "all" -> true;
            case "history" -> TERMINAL_STATES.contains(state);
            case "active" -> !TERMINAL_STATES.contains(state);
            default -> throw new IllegalArgumentException("Unsupported alarm view: " + view);
        };
    }

    private AlarmView alarmView(AlarmJpaEntity entity) {
        return new AlarmView(
                entity.id(), entity.alarmNumber(), entity.alarmTypeId(), entity.severityId(), entity.priorityId(),
                entity.titleAr(), entity.titleFr(), entity.titleEn(), String.valueOf(entity.sourceType()),
                entity.sourceReferenceId(), entity.topologyAssetTypeCode(), entity.topologyAssetId(),
                entity.topologyAssetCode(), entity.topologyAssetNameSnapshot(), String.valueOf(entity.currentState()),
                entity.raisedAt(), entity.firstDetectedAt(), entity.lastUpdatedAt(), entity.clearedAt(),
                entity.closedAt(), entity.acknowledgedAt(), entity.acknowledgedByActorId(),
                entity.owningOrganizationUnitId(), entity.workflowInstanceId(), entity.incidentId(),
                entity.correlationId()
        );
    }

    private ShelvingView shelvingView(AlarmShelvingJpaEntity entity) {
        return new ShelvingView(
                entity.id(), entity.alarmId(), entity.shelvingReasonId(), entity.reasonText(),
                entity.shelvedByActorId(), entity.shelvedAt(), entity.shelvedUntil(), entity.unshelvedAt(),
                entity.unshelvedByActorId(), String.valueOf(entity.status()), entity.correlationId()
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
