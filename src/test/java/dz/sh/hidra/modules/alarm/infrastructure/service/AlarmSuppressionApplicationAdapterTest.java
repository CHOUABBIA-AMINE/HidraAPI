/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionApplicationAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.service
 *
 * @Description : Verifies exact-scope conflict and suppression read mapping behavior.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.alarm.application.command.CreateAlarmSuppressionCommand;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmRepositoryPort;
import dz.sh.hidra.modules.alarm.application.service.AlarmSuppressionApprovalService;
import dz.sh.hidra.modules.alarm.domain.exception.AlarmSuppressionConflictException;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionScopeType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmSuppressionStatus;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmLifecycleEventJpaRepository;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmSuppressionJpaRepository;
import dz.sh.hidra.modules.alarm.infrastructure.scheduling.AlarmSuppressionExpiryOrchestrator;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class AlarmSuppressionApplicationAdapterTest {

    @Test
    void createRejectsExistingExactActiveScope() {
        AlarmSuppressionJpaRepository suppressions = mock(AlarmSuppressionJpaRepository.class);
        when(suppressions.existsByScopeTypeAndScopeReferenceIdAndStatus(
                AlarmSuppressionScopeType.TOPOLOGY_ASSET,
                "asset-1",
                AlarmSuppressionStatus.ACTIVE
        )).thenReturn(true);

        var service = new AlarmSuppressionApplicationAdapter(
                suppressions,
                mock(AlarmRepositoryPort.class),
                mock(AlarmLifecycleEventJpaRepository.class),
                mock(AlarmSuppressionApprovalService.class),
                mock(AlarmSuppressionExpiryOrchestrator.class)
        );

        assertThatThrownBy(() -> service.createSuppression(new CreateAlarmSuppressionCommand(
                AlarmSuppressionScopeType.TOPOLOGY_ASSET,
                "asset-1",
                null,
                null,
                "PIPELINE",
                "asset-1",
                "reason-1",
                "maintenance",
                "actor-1",
                Instant.now().plusSeconds(3600),
                null,
                "corr-1"
        ))).isInstanceOf(AlarmSuppressionConflictException.class);
    }
}
