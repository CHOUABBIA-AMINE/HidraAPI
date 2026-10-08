/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmAcknowledgementSemanticRemediationTest
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
import dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter.JpaAlarmAcknowledgementRepositoryAdapter;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmAcknowledgementJpaRepository;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.mapper.AlarmPersistenceMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlarmAcknowledgementSemanticRemediationTest {
    public static AlarmAcknowledgement acknowledgement(String id,String alarm) {
        return new AlarmAcknowledgement(id,alarm,"actor","Operator",null,null,
                AlarmSemanticRemediationTest.NOW.plusSeconds(10),null,"corr");
    }
    @Test void terminalAcknowledgementIsDeniedAndVisibilityClearEscalationRemainMeaningful() {
        var guard=new AlarmLifecycleGuard();
        for(var state:new AlarmState[]{AlarmState.CLOSED,AlarmState.CANCELLED})
            assertThrows(RuntimeException.class,()->guard.ensureCanAcknowledge(AlarmSemanticRemediationTest.alarm("a",state,"Titre")));
        for(var state:new AlarmState[]{AlarmState.CLEARED,AlarmState.SUPPRESSED,AlarmState.SHELVED,AlarmState.ESCALATED}) {
            var alarm=AlarmSemanticRemediationTest.alarm("a",state,"Titre");
            var updated=alarm.withAcknowledgement(AlarmSemanticRemediationTest.NOW.plusSeconds(10),"actor");
            assertEquals(state,updated.currentState());assertEquals("actor",updated.acknowledgedByActorId());
        }
        assertEquals(9,AlarmAcknowledgement.class.getRecordComponents().length);
    }
    @Test void directSaveUpdatesStateAndWritesExactlyOneActionWhileReplayIsImmutable() {
        var repo=mock(AlarmAcknowledgementJpaRepository.class);var alarms=mock(AlarmRepositoryPort.class);var events=mock(AlarmLifecycleEventRepositoryPort.class);
        var alarm=AlarmSemanticRemediationTest.alarm("a",AlarmState.RAISED,"Titre");var ack=acknowledgement("ack","a");
        when(alarms.findByIdForUpdate("a")).thenReturn(Optional.of(alarm));
        when(repo.findById("ack")).thenReturn(Optional.empty());when(repo.saveAndFlush(any())).thenAnswer(i->i.getArgument(0));
        var adapter=new JpaAlarmAcknowledgementRepositoryAdapter(repo,alarms,events);adapter.save(ack);
        verify(alarms).save(argThat(a->a.currentState()==AlarmState.ACKNOWLEDGED&&a.acknowledgedAt().equals(ack.acknowledgedAt())));
        verify(events).append(argThat(e->e.eventType()==AlarmLifecycleEventType.ACKNOWLEDGED));
        when(repo.findById("ack")).thenReturn(Optional.of(AlarmPersistenceMapper.toEntity(ack)));adapter.save(ack);
        verify(events,times(1)).append(any());
        var changed=new AlarmAcknowledgement("ack","a","other",null,null,null,ack.acknowledgedAt(),null,"corr");
        assertThrows(IllegalStateException.class,()->adapter.save(changed));
    }
    @Test void unknownAlarmFailsBeforeAnyEvidenceWriteAndOlderEvidenceDoesNotMoveSnapshotBackwards() {
        var repo=mock(AlarmAcknowledgementJpaRepository.class);var alarms=mock(AlarmRepositoryPort.class);
        when(alarms.findByIdForUpdate("missing")).thenReturn(Optional.empty());
        var adapter=new JpaAlarmAcknowledgementRepositoryAdapter(repo,alarms,mock(AlarmLifecycleEventRepositoryPort.class));
        assertThrows(IllegalArgumentException.class,()->adapter.save(acknowledgement("ack","missing")));verify(repo,never()).saveAndFlush(any());
        var now=AlarmSemanticRemediationTest.NOW;
        var latest=AlarmSemanticRemediationTest.alarm("a",AlarmState.RAISED,"Titre").withAcknowledgement(now.plusSeconds(20),"latest");
        var old=latest.withAcknowledgement(now.plusSeconds(10),"old");assertEquals("latest",old.acknowledgedByActorId());assertEquals(latest.lastUpdatedAt(),old.lastUpdatedAt());
    }
}
