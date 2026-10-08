/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmShelvingExpiryJobTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.scheduling
 *
 * @Description : Enforces Alarm-owned transactional lifecycle evidence and integrity.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.scheduling;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class AlarmShelvingExpiryJobTest {
    @Test void scheduledTriggerUsesServerOwnedActorAndCorrelation() {
        var orchestrator=mock(AlarmShelvingExpiryOrchestrator.class);new AlarmShelvingExpiryJob(orchestrator).expireDueShelvings();
        verify(orchestrator).expireDue(any(),eq(AlarmShelvingExpiryJob.SYSTEM_ACTOR_ID),any());
    }
}
