/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionApplicationAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.service
 *
 * @Description : Implements Alarm suppression application ports over the retained infrastructure persistence model.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.service;

import dz.sh.hidra.modules.alarm.application.command.CreateAlarmSuppressionCommand;
import dz.sh.hidra.modules.alarm.application.command.EvaluateAlarmSuppressionExpiryCommand;
import dz.sh.hidra.modules.alarm.application.command.ReleaseAlarmSuppressionCommand;
import dz.sh.hidra.modules.alarm.application.dto.AlarmSuppressionDto;
import dz.sh.hidra.modules.alarm.application.dto.AlarmSuppressionPageDto;
import dz.sh.hidra.modules.alarm.application.port.in.AlarmSuppressionQueryUseCase;
import dz.sh.hidra.modules.alarm.application.port.in.ManageAlarmSuppressionUseCase;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmRepositoryPort;
import dz.sh.hidra.modules.alarm.application.query.AlarmSuppressionQuery;
import dz.sh.hidra.modules.alarm.application.service.AlarmSuppressionApprovalService;
import dz.sh.hidra.modules.alarm.domain.exception.AlarmSuppressionConflictException;
import dz.sh.hidra.modules.alarm.domain.model.Alarm;
import dz.sh.hidra.modules.alarm.domain.policy.AlarmSuppressionPolicy;
import dz.sh.hidra.modules.alarm.domain.value.AlarmId;
import dz.sh.hidra.modules.alarm.domain.value.AlarmLifecycleEventType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmState;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionScopeType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionStatus;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.entity.AlarmLifecycleEventJpaEntity;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.entity.AlarmSuppressionJpaEntity;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmLifecycleEventJpaRepository;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmSuppressionJpaRepository;
import dz.sh.hidra.modules.alarm.infrastructure.scheduling.AlarmSuppressionExpiryOrchestrator;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Infrastructure implementation of the suppression input ports.
 *
 * <p>This placement intentionally preserves HRA-061: the API depends only on application
 * input ports, while the retained suppression JPA model stays infrastructure-owned.</p>
 */
@Service
public final class AlarmSuppressionApplicationAdapter
        implements ManageAlarmSuppressionUseCase, AlarmSuppressionQueryUseCase {

    private final AlarmSuppressionJpaRepository suppressionRepository;
    private final AlarmRepositoryPort alarmRepository;
    private final AlarmLifecycleEventJpaRepository lifecycleEventRepository;
    private final AlarmSuppressionApprovalService approvalService;
    private final AlarmSuppressionExpiryOrchestrator expiryOrchestrator;

    public AlarmSuppressionApplicationAdapter(
            AlarmSuppressionJpaRepository suppressionRepository,
            AlarmRepositoryPort alarmRepository,
            AlarmLifecycleEventJpaRepository lifecycleEventRepository,
            AlarmSuppressionApprovalService approvalService,
            AlarmSuppressionExpiryOrchestrator expiryOrchestrator
    ) {
        this.suppressionRepository = Objects.requireNonNull(suppressionRepository);
        this.alarmRepository = Objects.requireNonNull(alarmRepository);
        this.lifecycleEventRepository = Objects.requireNonNull(lifecycleEventRepository);
        this.approvalService = Objects.requireNonNull(approvalService);
        this.expiryOrchestrator = Objects.requireNonNull(expiryOrchestrator);
    }

    @Override
    @Transactional
    public AlarmSuppressionDto createSuppression(CreateAlarmSuppressionCommand command) {
        Objects.requireNonNull(command, "Create alarm suppression command must not be null.");
        Instant now = Instant.now();
        AlarmSuppressionPolicy.ensureValidCreation(
                command.scopeType(),
                command.scopeReferenceId(),
                command.suppressionReasonId(),
                command.actorId(),
                command.suppressedUntil(),
                command.workflowInstanceId(),
                now
        );
        approvalService.requireApprovalIfOpenEnded(command);

        boolean overlap = suppressionRepository.existsByScopeTypeAndScopeReferenceIdAndStatus(
                command.scopeType(),
                command.scopeReferenceId().trim(),
                AlarmSuppressionStatus.ACTIVE
        );
        if (overlap) {
            throw new AlarmSuppressionConflictException(
                    "An ACTIVE suppression already exists for the same scope and reference."
            );
        }

        String alarmId = normalizedAlarmId(command);
        if (command.scopeType() == AlarmSuppressionScopeType.ALARM) {
            suppressAlarm(alarmId, command.suppressionReasonId(), command.reasonText(),
                    command.actorId(), command.correlationId(), now);
        }

        AlarmSuppressionJpaEntity entity = new AlarmSuppressionJpaEntity(
                UUID.randomUUID().toString(),
                command.scopeType(),
                command.scopeReferenceId().trim(),
                alarmId,
                command.alarmTypeId(),
                command.topologyAssetTypeCode(),
                command.topologyAssetId(),
                command.suppressionReasonId().trim(),
                command.reasonText(),
                command.actorId().trim(),
                now,
                command.suppressedUntil(),
                null,
                null,
                AlarmSuppressionStatus.ACTIVE,
                command.workflowInstanceId(),
                command.correlationId()
        );
        try {
            return toDto(suppressionRepository.save(entity));
        } catch (DataIntegrityViolationException exception) {
            throw new AlarmSuppressionConflictException(
                    "An ACTIVE suppression already exists for the same scope and reference."
            );
        }
    }

    @Override
    @Transactional
    public AlarmSuppressionDto releaseSuppression(ReleaseAlarmSuppressionCommand command) {
        Objects.requireNonNull(command, "Release alarm suppression command must not be null.");
        AlarmSuppressionJpaEntity suppression = suppressionRepository.findByIdForUpdate(command.suppressionId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Alarm suppression not found: " + command.suppressionId()
                ));
        if (suppression.status() != AlarmSuppressionStatus.ACTIVE) {
            throw new AlarmSuppressionConflictException(
                    "Only ACTIVE suppression can be released."
            );
        }

        Instant now = Instant.now();
        if (suppression.scopeType() == AlarmSuppressionScopeType.ALARM) {
            restoreAlarmIfStillSuppressed(
                    suppression, now, command.actorId(), command.correlationId()
            );
        }

        if (!suppression.markReleased(now, command.actorId())) {
            throw new AlarmSuppressionConflictException(
                    "Only ACTIVE suppression can be released."
            );
        }
        return toDto(suppressionRepository.save(suppression));
    }

    @Override
    public int expireDueSuppressions(EvaluateAlarmSuppressionExpiryCommand command) {
        Objects.requireNonNull(command, "Evaluate suppression expiry command must not be null.");
        return expiryOrchestrator.expireDue(
                command.asOf(),
                command.systemActorId(),
                command.correlationId()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public AlarmSuppressionDto suppression(String suppressionId) {
        return suppressionRepository.findById(suppressionId)
                .map(AlarmSuppressionApplicationAdapter::toDto)
                .orElseThrow(() -> new NoSuchElementException(
                        "Alarm suppression not found: " + suppressionId
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public AlarmSuppressionPageDto suppressions(AlarmSuppressionQuery query) {
        Objects.requireNonNull(query, "Alarm suppression query must not be null.");
        if (query.page() < 0 || query.size() <= 0 || query.size() > 200) {
            throw new IllegalArgumentException(
                    "Suppression query page must be >= 0 and size must be between 1 and 200."
            );
        }

        Specification<AlarmSuppressionJpaEntity> specification =
                (root, ignoredQuery, cb) -> cb.conjunction();
        if (hasText(query.suppressionId())) {
            specification = specification.and((root, ignoredQuery, cb) ->
                    cb.equal(root.get("id"), query.suppressionId().trim()));
        }
        if (query.scopeType() != null) {
            specification = specification.and((root, ignoredQuery, cb) ->
                    cb.equal(root.get("scopeType"), query.scopeType()));
        }
        if (hasText(query.scopeReferenceId())) {
            specification = specification.and((root, ignoredQuery, cb) ->
                    cb.equal(root.get("scopeReferenceId"), query.scopeReferenceId().trim()));
        }
        if (hasText(query.alarmId())) {
            specification = specification.and((root, ignoredQuery, cb) ->
                    cb.equal(root.get("alarmId"), query.alarmId().trim()));
        }
        if (query.status() != null) {
            specification = specification.and((root, ignoredQuery, cb) ->
                    cb.equal(root.get("status"), query.status()));
        }

        var page = suppressionRepository.findAll(
                specification,
                PageRequest.of(query.page(), query.size(), Sort.by(Sort.Direction.DESC, "suppressedAt"))
        );
        return new AlarmSuppressionPageDto(
                page.getContent().stream().map(AlarmSuppressionApplicationAdapter::toDto).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext()
        );
    }

    private void suppressAlarm(
            String alarmId,
            String reasonId,
            String reasonText,
            String actorId,
            String correlationId,
            Instant now
    ) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new NoSuchElementException("Alarm not found: " + alarmId));
        if (alarm.closed()) {
            throw new AlarmSuppressionConflictException("Closed alarm cannot be suppressed.");
        }
        if (alarm.currentState() == AlarmState.SUPPRESSED) {
            throw new AlarmSuppressionConflictException("Alarm is already suppressed.");
        }

        Alarm suppressed = alarm.withState(AlarmState.SUPPRESSED, now);
        alarmRepository.save(suppressed);
        lifecycleEventRepository.save(new AlarmLifecycleEventJpaEntity(
                AlarmId.newId().value(),
                alarm.id(),
                AlarmLifecycleEventType.SUPPRESSED,
                alarm.currentState(),
                AlarmState.SUPPRESSED,
                reasonId,
                reasonText,
                actorId,
                null,
                alarm.owningOrganizationUnitId(),
                alarm.owningOrganizationUnitCode(),
                alarm.owningOrganizationUnitNameSnapshot(),
                now,
                correlationId,
                null
        ));
    }

    private void restoreAlarmIfStillSuppressed(
            AlarmSuppressionJpaEntity suppression,
            Instant now,
            String actorId,
            String correlationId
    ) {
        String alarmId = suppression.alarmId() == null
                ? suppression.scopeReferenceId()
                : suppression.alarmId();
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new NoSuchElementException("Alarm not found: " + alarmId));
        if (alarm.currentState() != AlarmState.SUPPRESSED) {
            return;
        }

        AlarmState restored = AlarmSuppressionPolicy.restorationState(alarm);
        alarmRepository.save(alarm.withState(restored, now));
        lifecycleEventRepository.save(new AlarmLifecycleEventJpaEntity(
                AlarmId.newId().value(),
                alarm.id(),
                AlarmLifecycleEventType.UNSUPPRESSED,
                AlarmState.SUPPRESSED,
                restored,
                suppression.suppressionReasonId(),
                "Manual suppression release",
                actorId,
                null,
                alarm.owningOrganizationUnitId(),
                alarm.owningOrganizationUnitCode(),
                alarm.owningOrganizationUnitNameSnapshot(),
                now,
                correlationId,
                null
        ));
    }

    private static String normalizedAlarmId(CreateAlarmSuppressionCommand command) {
        if (command.scopeType() != AlarmSuppressionScopeType.ALARM) {
            return command.alarmId();
        }
        String scopeAlarmId = command.scopeReferenceId().trim();
        if (hasText(command.alarmId()) && !scopeAlarmId.equals(command.alarmId().trim())) {
            throw new IllegalArgumentException(
                    "ALARM suppression alarmId must match scopeReferenceId."
            );
        }
        return scopeAlarmId;
    }

    private static AlarmSuppressionDto toDto(AlarmSuppressionJpaEntity entity) {
        return new AlarmSuppressionDto(
                entity.id(), entity.scopeType(), entity.scopeReferenceId(), entity.alarmId(),
                entity.alarmTypeId(), entity.topologyAssetTypeCode(), entity.topologyAssetId(),
                entity.suppressionReasonId(), entity.reasonText(), entity.suppressedByActorId(),
                entity.suppressedAt(), entity.suppressedUntil(), entity.releasedAt(),
                entity.releasedByActorId(), entity.status(), entity.workflowInstanceId(),
                entity.correlationId()
        );
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
