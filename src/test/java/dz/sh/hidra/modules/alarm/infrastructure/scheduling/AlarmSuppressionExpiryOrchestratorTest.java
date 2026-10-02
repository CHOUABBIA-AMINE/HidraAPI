/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionExpiryOrchestratorTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.scheduling
 *
 * @Description : Verifies suppression expiry idempotency, Audit evidence, and ALARM restoration.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.scheduling;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.alarm.application.port.out.AlarmRepositoryPort;
import dz.sh.hidra.modules.alarm.domain.model.Alarm;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSourceType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmState;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionScopeType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionStatus;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.entity.AlarmSuppressionJpaEntity;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmLifecycleEventJpaRepository;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmSuppressionJpaRepository;
import dz.sh.hidra.modules.audit.application.contract.alarm.AlarmSuppressionAuditContract;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class AlarmSuppressionExpiryOrchestratorTest {

    private static final Instant NOW = Instant.parse("2026-10-02T10:00:00Z");

    @Test
    void expiresBroadScopeWithAuditButWithoutAlarmMutation() {
        AlarmSuppressionJpaRepository suppressions = mock(AlarmSuppressionJpaRepository.class);
        AlarmRepositoryPort alarms = mock(AlarmRepositoryPort.class);
        AlarmLifecycleEventJpaRepository events = mock(AlarmLifecycleEventJpaRepository.class);
        AlarmSuppressionAuditContract audit = mock(AlarmSuppressionAuditContract.class);
        AlarmSuppressionJpaEntity suppression = suppression(
                AlarmSuppressionScopeType.TOPOLOGY_ASSET, "asset-1", null
        );
        when(suppressions.findByStatusAndSuppressedUntilLessThanEqualOrderBySuppressedUntilAsc(
                AlarmSuppressionStatus.ACTIVE, NOW
        )).thenReturn(List.of(suppression));

        int count = new AlarmSuppressionExpiryOrchestrator(
                suppressions, alarms, events, audit
        ).expireDue(NOW, "system", "corr-1");

        assertThat(count).isEqualTo(1);
        assertThat(suppression.status()).isEqualTo(AlarmSuppressionStatus.EXPIRED);
        verify(audit).appendExpiry(any());
        verify(alarms, never()).findById(any());
        verify(events, never()).save(any());
    }

    @Test
    void alarmScopeRestoresAcknowledgedEvidenceAndRecordsLifecycleEvent() {
        AlarmSuppressionJpaRepository suppressions = mock(AlarmSuppressionJpaRepository.class);
        AlarmRepositoryPort alarms = mock(AlarmRepositoryPort.class);
        AlarmLifecycleEventJpaRepository events = mock(AlarmLifecycleEventJpaRepository.class);
        AlarmSuppressionAuditContract audit = mock(AlarmSuppressionAuditContract.class);
        AlarmSuppressionJpaEntity suppression = suppression(
                AlarmSuppressionScopeType.ALARM, "alarm-1", "alarm-1"
        );
        when(suppressions.findByStatusAndSuppressedUntilLessThanEqualOrderBySuppressedUntilAsc(
                AlarmSuppressionStatus.ACTIVE, NOW
        )).thenReturn(List.of(suppression));
        when(alarms.findById("alarm-1")).thenReturn(Optional.of(alarm(AlarmState.SUPPRESSED, NOW.minusSeconds(60))));

        int count = new AlarmSuppressionExpiryOrchestrator(
                suppressions, alarms, events, audit
        ).expireDue(NOW, "system", "corr-1");

        assertThat(count).isEqualTo(1);
        verify(alarms).save(org.mockito.ArgumentMatchers.argThat(
                value -> value.currentState() == AlarmState.ACKNOWLEDGED
        ));
        verify(events).save(org.mockito.ArgumentMatchers.argThat(
                event -> event.newState() == AlarmState.ACKNOWLEDGED
        ));
    }

    @Test
    void nonDueOrNonActiveRowsAreIgnoredIfReturnedDefensively() {
        AlarmSuppressionJpaRepository suppressions = mock(AlarmSuppressionJpaRepository.class);
        AlarmRepositoryPort alarms = mock(AlarmRepositoryPort.class);
        AlarmLifecycleEventJpaRepository events = mock(AlarmLifecycleEventJpaRepository.class);
        AlarmSuppressionAuditContract audit = mock(AlarmSuppressionAuditContract.class);
        AlarmSuppressionJpaEntity future = new AlarmSuppressionJpaEntity(
                "suppression-1", AlarmSuppressionScopeType.SOURCE, "source-1",
                null, null, null, null, "reason-1", null, "actor-1",
                NOW.minusSeconds(300), NOW.plusSeconds(300), null, null,
                AlarmSuppressionStatus.ACTIVE, null, "corr-1"
        );
        when(suppressions.findByStatusAndSuppressedUntilLessThanEqualOrderBySuppressedUntilAsc(
                AlarmSuppressionStatus.ACTIVE, NOW
        )).thenReturn(List.of(future));

        int count = new AlarmSuppressionExpiryOrchestrator(
                suppressions, alarms, events, audit
        ).expireDue(NOW, "system", "corr-1");

        assertThat(count).isZero();
        verify(audit, never()).appendExpiry(any());
        verify(suppressions, never()).save(any());
    }

    private static AlarmSuppressionJpaEntity suppression(
            AlarmSuppressionScopeType scope,
            String reference,
            String alarmId
    ) {
        return new AlarmSuppressionJpaEntity(
                "suppression-1", scope, reference, alarmId, null, null, null,
                "reason-1", null, "actor-1",
                NOW.minusSeconds(600), NOW.minusSeconds(1), null, null,
                AlarmSuppressionStatus.ACTIVE, null, "corr-1"
        );
    }

    private static Alarm alarm(AlarmState state, Instant acknowledgedAt) {
        return new Alarm(
                "alarm-1", "AL-1", "type-1", "severity-1", null,
                null, "Alarme", null, null, null, null,
                AlarmSourceType.MANUAL, "source-1", null, null, null, null,
                "PIPELINE", "asset-1", "PL-1", "Pipeline 1",
                state, NOW.minusSeconds(900), null, NOW.minusSeconds(60),
                null, null, acknowledgedAt, acknowledgedAt == null ? null : "actor-1",
                null, null, null, null, null, "corr-1",
                NOW.minusSeconds(900), NOW.minusSeconds(60)
        );
    }
}
