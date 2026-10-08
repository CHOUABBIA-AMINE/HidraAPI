/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionExpiryOrchestrator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.scheduling
 *
 * @Description : Executes transactional, idempotent alarm suppression expiry.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.scheduling;

import dz.sh.hidra.modules.alarm.application.port.out.AlarmRepositoryPort;
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
import dz.sh.hidra.modules.audit.application.contract.alarm.AlarmSuppressionAuditContract;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Expires due suppressions under a pessimistic row lock.
 */
@Service
public class AlarmSuppressionExpiryOrchestrator {

    private final AlarmSuppressionJpaRepository suppressionRepository;
    private final AlarmRepositoryPort alarmRepository;
    private final AlarmLifecycleEventJpaRepository lifecycleEventRepository;
    private final AlarmSuppressionAuditContract auditContract;

    public AlarmSuppressionExpiryOrchestrator(
            AlarmSuppressionJpaRepository suppressionRepository,
            AlarmRepositoryPort alarmRepository,
            AlarmLifecycleEventJpaRepository lifecycleEventRepository,
            AlarmSuppressionAuditContract auditContract
    ) {
        this.suppressionRepository = Objects.requireNonNull(
                suppressionRepository, "Alarm suppression repository must not be null."
        );
        this.alarmRepository = Objects.requireNonNull(
                alarmRepository, "Alarm repository port must not be null."
        );
        this.lifecycleEventRepository = Objects.requireNonNull(
                lifecycleEventRepository, "Alarm lifecycle event repository must not be null."
        );
        this.auditContract = Objects.requireNonNull(
                auditContract, "Alarm suppression audit contract must not be null."
        );
    }

    @Transactional
    public int expireDue(Instant asOf, String systemActorId, String correlationId) {
        Objects.requireNonNull(asOf, "Suppression expiry instant must not be null.");
        String actorId = requireText(systemActorId, "Suppression expiry system actor must not be blank.");

        // Scalar discovery avoids stale managed entities after waiting for a lock.
        List<String> due = suppressionRepository.findDueIds(asOf);
        int expired = 0;
        for (String candidateId : due) {
            suppressionRepository.alarmIdForSuppression(candidateId).ifPresent(id ->
                    alarmRepository.findByIdForUpdate(id).orElseThrow(() -> new IllegalStateException("Unknown alarm: " + id)));
            AlarmSuppressionJpaEntity suppression = suppressionRepository.findByIdForUpdate(candidateId).orElse(null);
            if (suppression == null) continue;
            if (!AlarmSuppressionPolicy.dueForExpiry(
                    suppression.status(),
                    suppression.suppressedUntil(),
                    asOf
            )) {
                continue;
            }

            auditContract.appendExpiry(new AlarmSuppressionAuditContract.ExpiryEvidence(
                    suppression.id(),
                    suppression.scopeType().name(),
                    suppression.scopeReferenceId(),
                    actorId,
                    correlationId,
                    asOf
            ));

            if (suppression.scopeType() == AlarmSuppressionScopeType.ALARM) {
                restoreAlarmIfStillSuppressed(suppression, asOf, actorId, correlationId);
            }

            if (suppression.markExpired(asOf)) {
                suppressionRepository.save(suppression);
                expired++;
            }
        }
        return expired;
    }

    private void restoreAlarmIfStillSuppressed(
            AlarmSuppressionJpaEntity suppression,
            Instant asOf,
            String actorId,
            String correlationId
    ) {
        String alarmId = suppression.alarmId() == null
                ? suppression.scopeReferenceId()
                : suppression.alarmId();

        Alarm alarm = alarmRepository.findByIdForUpdate(alarmId)
                .orElseThrow(() -> new IllegalStateException(
                        "ALARM-scoped suppression references missing alarm: " + alarmId
                ));

        if (alarm.currentState() != AlarmState.SUPPRESSED) {
            return;
        }

        AlarmState restoredState = AlarmSuppressionPolicy.restorationState(alarm);
        Alarm restored = alarm.withState(restoredState, asOf);
        alarmRepository.save(restored);

        lifecycleEventRepository.save(new AlarmLifecycleEventJpaEntity(
                AlarmId.newId().value(),
                alarm.id(),
                AlarmLifecycleEventType.UNSUPPRESSED,
                AlarmState.SUPPRESSED,
                restoredState,
                suppression.suppressionReasonId(),
                "Automatic suppression expiry",
                actorId,
                "Hidra scheduled job",
                null,
                null,
                null,
                asOf,
                correlationId,
                null
        ));
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }
}
