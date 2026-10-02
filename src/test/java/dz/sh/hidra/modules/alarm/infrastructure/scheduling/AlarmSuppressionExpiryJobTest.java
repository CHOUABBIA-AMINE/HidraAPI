/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionExpiryJobTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.scheduling
 *
 * @Description : Verifies the scheduled expiry trigger delegates with server-owned actor identity.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.scheduling;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

class AlarmSuppressionExpiryJobTest {

    @Test
    void delegatesToOrchestratorWithSystemActor() {
        AlarmSuppressionExpiryOrchestrator orchestrator = mock(AlarmSuppressionExpiryOrchestrator.class);
        new AlarmSuppressionExpiryJob(orchestrator).expireDueSuppressions();

        verify(orchestrator).expireDue(
                any(),
                eq(AlarmSuppressionExpiryJob.SYSTEM_ACTOR_ID),
                any()
        );
    }
}
