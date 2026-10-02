/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionPolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.policy
 *
 * @Description : Verifies approved alarm suppression lifecycle semantics.
 *
 */
package dz.sh.hidra.modules.alarm.domain.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dz.sh.hidra.modules.alarm.domain.exception.AlarmLifecycleViolationException;
import dz.sh.hidra.modules.alarm.domain.model.Alarm;
import dz.sh.hidra.modules.alarm.domain.service.AlarmLifecycleGuard;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSourceType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmState;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionScopeType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionStatus;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class AlarmSuppressionPolicyTest {

    private static final Instant NOW = Instant.parse("2026-10-02T08:00:00Z");

    @Test
    void openEndedSuppressionRequiresWorkflowEvidence() {
        assertThrows(AlarmLifecycleViolationException.class, () ->
                AlarmSuppressionPolicy.ensureValidCreation(
                        AlarmSuppressionScopeType.ALARM, "alarm-1", "reason-1", "actor-1",
                        null, null, NOW));
    }

    @Test
    void timeBoundedSuppressionDoesNotRequireWorkflowEvidence() {
        AlarmSuppressionPolicy.ensureValidCreation(
                AlarmSuppressionScopeType.ALARM, "alarm-1", "reason-1", "actor-1",
                NOW.plusSeconds(300), null, NOW);
    }

    @Test
    void exactScopeMatchingIsDeterministic() {
        assertTrue(AlarmSuppressionPolicy.matches(
                AlarmSuppressionScopeType.TOPOLOGY_ASSET, "asset-1",
                "alarm-1", "type-1", "asset-1", "rule-1", "source-1"));
        assertFalse(AlarmSuppressionPolicy.matches(
                AlarmSuppressionScopeType.MONITORING_RULE, "rule-2",
                "alarm-1", "type-1", "asset-1", "rule-1", "source-1"));
    }

    @Test
    void onlyActiveTimedSuppressionExpires() {
        assertTrue(AlarmSuppressionPolicy.dueForExpiry(
                AlarmSuppressionStatus.ACTIVE, NOW.minusSeconds(1), NOW));
        assertFalse(AlarmSuppressionPolicy.dueForExpiry(
                AlarmSuppressionStatus.RELEASED, NOW.minusSeconds(1), NOW));
        assertFalse(AlarmSuppressionPolicy.dueForExpiry(
                AlarmSuppressionStatus.ACTIVE, null, NOW));
    }

    @Test
    void overlappingActiveSuppressionIsRejected() {
        assertThrows(AlarmLifecycleViolationException.class,
                () -> AlarmSuppressionPolicy.ensureNoActiveOverlap(true));
    }

    @Test
    void releasedSuppressionCannotBeReleasedAgain() {
        assertThrows(AlarmLifecycleViolationException.class,
                () -> AlarmSuppressionPolicy.ensureCanRelease(AlarmSuppressionStatus.RELEASED));
    }

    @Test
    void restorationPrefersClosedThenClearedThenAcknowledgedThenActive() {
        assertEquals(AlarmState.CLOSED,
                AlarmSuppressionPolicy.restorationState(alarm(AlarmState.SUPPRESSED, NOW, NOW, NOW)));
        assertEquals(AlarmState.CLEARED,
                AlarmSuppressionPolicy.restorationState(alarm(AlarmState.SUPPRESSED, NOW, NOW, null)));
        assertEquals(AlarmState.ACKNOWLEDGED,
                AlarmSuppressionPolicy.restorationState(alarm(AlarmState.SUPPRESSED, NOW, null, null)));
        assertEquals(AlarmState.ACTIVE,
                AlarmSuppressionPolicy.restorationState(alarm(AlarmState.SUPPRESSED, null, null, null)));
    }

    @Test
    void suppressedAlarmMayBeAcknowledgedAndEscalatedWhileActive() {
        Alarm alarm = alarm(AlarmState.SUPPRESSED, null, null, null);
        assertTrue(AlarmSuppressionPolicy.canAcknowledge(alarm));
        assertTrue(AlarmSuppressionPolicy.canEscalate(alarm));
    }

    @Test
    void suppressedAlarmMayCloseOnlyAfterUnderlyingClearUnderNormalClosureRule() {
        AlarmLifecycleGuard guard = new AlarmLifecycleGuard();
        assertThrows(AlarmLifecycleViolationException.class,
                () -> guard.ensureCanClose(alarm(AlarmState.SUPPRESSED, null, null, null), false));

        guard.ensureCanClose(alarm(AlarmState.SUPPRESSED, null, NOW, null), false);
    }

    private static Alarm alarm(
            AlarmState state,
            Instant acknowledgedAt,
            Instant clearedAt,
            Instant closedAt
    ) {
        return new Alarm(
                "alarm-1", "AL-1", "type-1", "severity-1", null,
                null, "Alarme", null, null, null, null,
                AlarmSourceType.MANUAL, "source-1", null, null, null, null,
                "PIPELINE", "asset-1", "PL-1", "Pipeline 1",
                state, NOW.minusSeconds(600), null, NOW,
                clearedAt, closedAt, acknowledgedAt,
                acknowledgedAt == null ? null : "actor-1",
                null, null, null, null, null, "corr-1",
                NOW.minusSeconds(600), NOW
        );
    }
}
