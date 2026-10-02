/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAlarmSuppressionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for governed alarm suppression evidence.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.alarm.application.dto.AlarmSuppressionDto;
import dz.sh.hidra.modules.alarm.application.dto.AlarmSuppressionPageDto;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmSuppressionRepositoryPort;
import dz.sh.hidra.modules.alarm.application.query.AlarmSuppressionQuery;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionScopeType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionStatus;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.entity.AlarmSuppressionJpaEntity;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmSuppressionJpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * JPA implementation of the suppression persistence boundary.
 */
@Component
public final class JpaAlarmSuppressionRepositoryAdapter implements AlarmSuppressionRepositoryPort {

    private final AlarmSuppressionJpaRepository repository;

    public JpaAlarmSuppressionRepositoryAdapter(AlarmSuppressionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "AlarmSuppressionJpaRepository must not be null.");
    }

    @Override
    public AlarmSuppressionDto save(AlarmSuppressionDto suppression) {
        Objects.requireNonNull(suppression, "Alarm suppression must not be null.");
        return toDto(repository.save(toEntity(suppression)));
    }

    @Override
    public Optional<AlarmSuppressionDto> findById(String id) {
        return repository.findById(id).map(JpaAlarmSuppressionRepositoryAdapter::toDto);
    }

    @Override
    public boolean existsByExactScopeAndStatus(
            AlarmSuppressionScopeType scopeType,
            String scopeReferenceId,
            AlarmSuppressionStatus status
    ) {
        return repository.existsByScopeTypeAndScopeReferenceIdAndStatus(scopeType, scopeReferenceId, status);
    }

    @Override
    public List<AlarmSuppressionDto> findDueForExpiry(Instant asOf) {
        Objects.requireNonNull(asOf, "Suppression expiry instant must not be null.");
        return repository.findByStatusAndSuppressedUntilLessThanEqualOrderBySuppressedUntilAsc(
                        AlarmSuppressionStatus.ACTIVE,
                        asOf
                ).stream()
                .map(JpaAlarmSuppressionRepositoryAdapter::toDto)
                .toList();
    }

    @Override
    public AlarmSuppressionPageDto find(AlarmSuppressionQuery query) {
        Objects.requireNonNull(query, "Alarm suppression query must not be null.");
        if (query.page() < 0 || query.size() <= 0) {
            throw new IllegalArgumentException("Suppression query page must be >= 0 and size must be > 0.");
        }

        Specification<AlarmSuppressionJpaEntity> specification = Specification.where((Specification<AlarmSuppressionJpaEntity>) null);
        if (hasText(query.suppressionId())) {
            specification = specification.and((root, ignoredQuery, cb) -> cb.equal(root.get("id"), query.suppressionId().trim()));
        }
        if (query.scopeType() != null) {
            specification = specification.and((root, ignoredQuery, cb) -> cb.equal(root.get("scopeType"), query.scopeType()));
        }
        if (hasText(query.scopeReferenceId())) {
            specification = specification.and((root, ignoredQuery, cb) -> cb.equal(root.get("scopeReferenceId"), query.scopeReferenceId().trim()));
        }
        if (hasText(query.alarmId())) {
            specification = specification.and((root, ignoredQuery, cb) -> cb.equal(root.get("alarmId"), query.alarmId().trim()));
        }
        if (query.status() != null) {
            specification = specification.and((root, ignoredQuery, cb) -> cb.equal(root.get("status"), query.status()));
        }

        var page = repository.findAll(specification, PageRequest.of(query.page(), query.size()));
        return new AlarmSuppressionPageDto(
                page.getContent().stream().map(JpaAlarmSuppressionRepositoryAdapter::toDto).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext()
        );
    }

    static AlarmSuppressionDto toDto(AlarmSuppressionJpaEntity entity) {
        return new AlarmSuppressionDto(
                entity.id(),
                entity.scopeType(),
                entity.scopeReferenceId(),
                entity.alarmId(),
                entity.alarmTypeId(),
                entity.topologyAssetTypeCode(),
                entity.topologyAssetId(),
                entity.suppressionReasonId(),
                entity.reasonText(),
                entity.suppressedByActorId(),
                entity.suppressedAt(),
                entity.suppressedUntil(),
                entity.releasedAt(),
                entity.releasedByActorId(),
                entity.status(),
                entity.workflowInstanceId(),
                entity.correlationId()
        );
    }

    static AlarmSuppressionJpaEntity toEntity(AlarmSuppressionDto dto) {
        return new AlarmSuppressionJpaEntity(
                dto.id(),
                dto.scopeType(),
                dto.scopeReferenceId(),
                dto.alarmId(),
                dto.alarmTypeId(),
                dto.topologyAssetTypeCode(),
                dto.topologyAssetId(),
                dto.suppressionReasonId(),
                dto.reasonText(),
                dto.suppressedByActorId(),
                dto.suppressedAt(),
                dto.suppressedUntil(),
                dto.releasedAt(),
                dto.releasedByActorId(),
                dto.status(),
                dto.workflowInstanceId(),
                dto.correlationId()
        );
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
