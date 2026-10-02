/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.policy
 *
 * @Description : Defines deterministic alarm suppression lifecycle and matching rules.
 *
 */
package dz.sh.hidra.modules.alarm.domain.policy;

import dz.sh.hidra.modules.alarm.domain.exception.AlarmLifecycleViolationException;
import dz.sh.hidra.modules.alarm.domain.model.Alarm;
import dz.sh.hidra.modules.alarm.domain.value.AlarmState;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionScopeType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionStatus;

import java.time.Instant;
import java.util.Objects;

/**
 * Pure domain policy for suppression validation, matching, expiry, conflicts, and restoration.
 */
public final class AlarmSuppressionPolicy {

    private AlarmSuppressionPolicy() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static void ensureValidCreation(
            AlarmSuppressionScopeType scopeType,
            String scopeReferenceId,
            String suppressionReasonId,
            String actorId,
            Instant suppressedUntil,
            String workflowInstanceId,
            Instant now
    ) {
        if (scopeType == null) {
            throw new AlarmLifecycleViolationException("Suppression scope type must not be null.");
        }
        requireText(scopeReferenceId, "Suppression scope reference id must not be blank.");
        requireText(suppressionReasonId, "Suppression reason id must not be blank.");
        requireText(actorId, "Suppression actor id must not be blank.");
        Objects.requireNonNull(now, "Suppression validation instant must not be null.");

        if (suppressedUntil != null && !suppressedUntil.isAfter(now)) {
            throw new AlarmLifecycleViolationException("Suppression end time must be after the current instant.");
        }
        if (suppressedUntil == null && isBlank(workflowInstanceId)) {
            throw new AlarmLifecycleViolationException("Open-ended suppression requires workflow approval evidence.");
        }
    }

    public static void ensureNoActiveOverlap(boolean overlappingActiveSuppression) {
        if (overlappingActiveSuppression) {
            throw new AlarmLifecycleViolationException("An ACTIVE suppression already exists for the same scope and reference.");
        }
    }

    public static void ensureCanRelease(AlarmSuppressionStatus status) {
        if (status != AlarmSuppressionStatus.ACTIVE) {
            throw new AlarmLifecycleViolationException("Only ACTIVE suppression can be released.");
        }
    }

    public static boolean dueForExpiry(AlarmSuppressionStatus status, Instant suppressedUntil, Instant asOf) {
        Objects.requireNonNull(asOf, "Suppression expiry evaluation instant must not be null.");
        return status == AlarmSuppressionStatus.ACTIVE
                && suppressedUntil != null
                && !suppressedUntil.isAfter(asOf);
    }

    public static boolean matches(
            AlarmSuppressionScopeType scopeType,
            String scopeReferenceId,
            String alarmId,
            String alarmTypeId,
            String topologyAssetId,
            String monitoringRuleId,
            String sourceReferenceId
    ) {
        if (scopeType == null || isBlank(scopeReferenceId)) {
            return false;
        }
        return switch (scopeType) {
            case ALARM -> Objects.equals(scopeReferenceId, normalize(alarmId));
            case ALARM_TYPE -> Objects.equals(scopeReferenceId, normalize(alarmTypeId));
            case TOPOLOGY_ASSET -> Objects.equals(scopeReferenceId, normalize(topologyAssetId));
            case MONITORING_RULE -> Objects.equals(scopeReferenceId, normalize(monitoringRuleId));
            case SOURCE -> Objects.equals(scopeReferenceId, normalize(sourceReferenceId));
        };
    }

    public static AlarmState restorationState(Alarm alarm) {
        Objects.requireNonNull(alarm, "Alarm must not be null.");

        if (alarm.currentState() == AlarmState.CANCELLED) {
            return AlarmState.CANCELLED;
        }
        if (alarm.closedAt() != null || alarm.currentState() == AlarmState.CLOSED) {
            return AlarmState.CLOSED;
        }
        if (alarm.clearedAt() != null || alarm.currentState() == AlarmState.CLEARED) {
            return AlarmState.CLEARED;
        }
        if (alarm.acknowledgedAt() != null || alarm.currentState() == AlarmState.ACKNOWLEDGED) {
            return AlarmState.ACKNOWLEDGED;
        }
        if (alarm.currentState() == AlarmState.ESCALATED) {
            return AlarmState.ESCALATED;
        }
        return AlarmState.ACTIVE;
    }

    public static boolean canAcknowledge(Alarm alarm) {
        Objects.requireNonNull(alarm, "Alarm must not be null.");
        return !alarm.closed();
    }

    public static boolean canEscalate(Alarm alarm) {
        Objects.requireNonNull(alarm, "Alarm must not be null.");
        return !alarm.closed();
    }

    private static void requireText(String value, String message) {
        if (isBlank(value)) {
            throw new AlarmLifecycleViolationException(message);
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private static String normalize(String value) {
        return isBlank(value) ? null : value.trim();
    }
}
