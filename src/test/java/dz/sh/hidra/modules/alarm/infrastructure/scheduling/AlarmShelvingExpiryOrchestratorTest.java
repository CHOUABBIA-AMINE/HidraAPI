/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmShelvingExpiryOrchestratorTest
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

import dz.sh.hidra.modules.alarm.application.port.out.AlarmShelvingRepositoryPort;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmShelvingJpaRepository;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AlarmShelvingExpiryOrchestratorTest {
    @Test void eachCandidateCrossesTheTransactionalPortAndOnlyActualFinishesAreCounted() {
        var repo=mock(AlarmShelvingJpaRepository.class);var port=mock(AlarmShelvingRepositoryPort.class);var asOf=Instant.parse("2026-10-08T12:10:00Z");
        when(repo.findDueIds(asOf)).thenReturn(List.of("first","already-finished"));
        when(port.expireIfDue("first",asOf,"scheduler","corr")).thenReturn(true);
        when(port.expireIfDue("already-finished",asOf,"scheduler","corr")).thenReturn(false);
        assertEquals(1,new AlarmShelvingExpiryOrchestrator(repo,port).expireDue(asOf,"scheduler","corr"));
        verify(port).expireIfDue("first",asOf,"scheduler","corr");verify(port).expireIfDue("already-finished",asOf,"scheduler","corr");
    }
    @Test void aFailedRecordDoesNotKeepOtherDueRecordsActive() {
        var repo=mock(AlarmShelvingJpaRepository.class);var port=mock(AlarmShelvingRepositoryPort.class);
        var asOf=Instant.parse("2026-10-08T12:10:00Z");when(repo.findDueIds(asOf)).thenReturn(List.of("invalid-history","healthy"));
        when(port.expireIfDue("invalid-history",asOf,"scheduler","corr")).thenThrow(new IllegalStateException("source evidence missing"));
        when(port.expireIfDue("healthy",asOf,"scheduler","corr")).thenReturn(true);
        assertEquals(1,new AlarmShelvingExpiryOrchestrator(repo,port).expireDue(asOf,"scheduler","corr"));
        verify(port).expireIfDue("healthy",asOf,"scheduler","corr");
    }
}
