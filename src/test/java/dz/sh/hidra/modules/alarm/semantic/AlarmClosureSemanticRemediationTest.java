/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmClosureSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.semantic
 *
 * @Description : Enforces Alarm-owned transactional lifecycle evidence and integrity.
 *
 */
package dz.sh.hidra.modules.alarm.semantic;

import dz.sh.hidra.modules.alarm.domain.model.*;
import dz.sh.hidra.modules.alarm.domain.value.*;
import dz.sh.hidra.modules.alarm.domain.service.AlarmLifecycleGuard;
import dz.sh.hidra.modules.alarm.application.port.out.*;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter.JpaAlarmClosureRepositoryAdapter;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmClosureJpaRepository;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.mapper.AlarmPersistenceMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlarmClosureSemanticRemediationTest {
    public static AlarmClosure closure(String id,String alarm,AlarmClosureType type) {
        return new AlarmClosure(id,alarm,type,null,null,"actor",java.time.Instant.now(),true,null,"corr");
    }
    @Test void clearBeforeCloseAndCancellationAreTheOnlyEligibilityRule() {
        var guard=new AlarmLifecycleGuard();
        for(var state:new AlarmState[]{AlarmState.RAISED,AlarmState.ACTIVE,AlarmState.ESCALATED}) {
            var alarm=AlarmSemanticRemediationTest.alarm("a",state,"Titre");
            assertThrows(RuntimeException.class,()->guard.ensureCanClose(alarm,false));assertDoesNotThrow(()->guard.ensureCanClose(alarm,true));
        }
        assertDoesNotThrow(()->guard.ensureCanClose(AlarmSemanticRemediationTest.alarm("a",AlarmState.CLEARED,"Titre"),false));
        for(var state:new AlarmState[]{AlarmState.CLOSED,AlarmState.CANCELLED}) assertThrows(RuntimeException.class,()->guard.ensureCanClose(AlarmSemanticRemediationTest.alarm("a",state,"Titre"),true));
        assertEquals(10,AlarmClosure.class.getRecordComponents().length);
    }
    @Test void directClosureSynchronizesTerminalFieldsAndOneEventWithOptionalReviewPreserved() {
        var repo=mock(AlarmClosureJpaRepository.class);var alarms=mock(AlarmRepositoryPort.class);var events=mock(AlarmLifecycleEventRepositoryPort.class);
        when(alarms.findByIdForUpdate("a")).thenReturn(Optional.of(AlarmSemanticRemediationTest.alarm("a",AlarmState.CLEARED,"Titre")));
        when(repo.findById("close")).thenReturn(Optional.empty());when(repo.saveAndFlush(any())).thenAnswer(i->i.getArgument(0));
        var adapter=new JpaAlarmClosureRepositoryAdapter(repo,alarms,events);var closure=closure("close","a",AlarmClosureType.NORMALIZED);
        assertTrue(adapter.save(closure).requiresReview());
        verify(alarms).save(argThat(a->a.currentState()==AlarmState.CLOSED&&a.closedAt().equals(closure.closedAt())));
        verify(events).append(argThat(e->e.eventType()==AlarmLifecycleEventType.CLOSED));
        when(repo.findById("close")).thenReturn(Optional.of(AlarmPersistenceMapper.toEntity(closure)));adapter.save(closure);
        verify(events,times(1)).append(any());
    }
    @Test void unknownAndSecondClosureAreDeniedBeforeWritesAndCancellationSetsTerminalState() {
        var repo=mock(AlarmClosureJpaRepository.class);var alarms=mock(AlarmRepositoryPort.class);var events=mock(AlarmLifecycleEventRepositoryPort.class);
        var adapter=new JpaAlarmClosureRepositoryAdapter(repo,alarms,events);
        when(alarms.findByIdForUpdate("missing")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,()->adapter.save(closure("close","missing",AlarmClosureType.CANCELLED)));
        var alarm=AlarmSemanticRemediationTest.alarm("a",AlarmState.RAISED,"Titre");
        when(alarms.findByIdForUpdate("a")).thenReturn(Optional.of(alarm));when(repo.existsByAlarmId("a")).thenReturn(true);
        assertThrows(IllegalStateException.class,()->adapter.save(closure("close","a",AlarmClosureType.CANCELLED)));verify(repo,never()).saveAndFlush(any());
        assertEquals(AlarmState.CANCELLED,alarm.withClosure(AlarmSemanticRemediationTest.NOW.plusSeconds(20),true).currentState());
    }
}
