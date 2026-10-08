/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmShelvingSemanticRemediationTest
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
import dz.sh.hidra.modules.alarm.domain.policy.AlarmShelvingPolicy;
import dz.sh.hidra.modules.alarm.application.port.out.*;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter.JpaAlarmShelvingRepositoryAdapter;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.mapper.AlarmPersistenceMapper;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.validation.AlarmCatalogValidation;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlarmShelvingSemanticRemediationTest {
    public static final Instant START=AlarmSemanticRemediationTest.NOW.plusSeconds(30), END=START.plusSeconds(60);
    public static AlarmShelving shelving(String id,String alarm) {return new AlarmShelving(id,alarm,"reason",null,"actor",START,END,null,null,AlarmShelvingStatus.ACTIVE,"corr");}
    public static AlarmShelving finished(AlarmShelving shelf,AlarmShelvingStatus status,Instant at) {
        return new AlarmShelving(shelf.id(),shelf.alarmId(),shelf.shelvingReasonId(),shelf.reasonText(),shelf.shelvedByActorId(),shelf.shelvedAt(),shelf.shelvedUntil(),at,"operator",status,shelf.correlationId());
    }
    @Test void intrinsicIntervalAndExactOpenStatePolicyAreEnforced() {
        assertEquals(11,AlarmShelving.class.getRecordComponents().length);
        for(var end:new Instant[]{START,START.minusSeconds(1)}) assertThrows(RuntimeException.class,()->new AlarmShelving("s","a","reason",null,"actor",START,end,null,null,AlarmShelvingStatus.ACTIVE,null));
        for(var state:AlarmState.values()) {
            var alarm=AlarmSemanticRemediationTest.alarm("a",state,"Titre");
            if(state==AlarmState.RAISED||state==AlarmState.ACTIVE||state==AlarmState.ACKNOWLEDGED||state==AlarmState.ESCALATED) assertDoesNotThrow(()->AlarmShelvingPolicy.ensureCanShelve(alarm));
            else assertThrows(RuntimeException.class,()->AlarmShelvingPolicy.ensureCanShelve(alarm));
        }
    }
    @Test void restorationUsesPreviousEvidenceAndNeverReopensTerminalOrClearedAlarm() {
        var alarm=AlarmSemanticRemediationTest.alarm("a",AlarmState.SHELVED,"Titre");
        assertThrows(IllegalStateException.class,()->AlarmShelvingPolicy.restorationState(alarm,null));
        var source=new AlarmLifecycleEvent("e","a",AlarmLifecycleEventType.SHELVED,AlarmState.ESCALATED,AlarmState.SHELVED,"reason",null,"actor",null,null,null,null,START,null,null);
        assertEquals(AlarmState.ESCALATED,AlarmShelvingPolicy.restorationState(alarm.withAcknowledgement(START.plusSeconds(1),"actor"),source));
        for(var state:new AlarmState[]{AlarmState.CLOSED,AlarmState.CANCELLED,AlarmState.CLEARED}) assertEquals(state,AlarmShelvingPolicy.restorationState(AlarmSemanticRemediationTest.alarm("a",state,"Titre"),null));
        assertEquals(alarm.lastUpdatedAt(),AlarmShelvingPolicy.changedAt(alarm,alarm.lastUpdatedAt().minusSeconds(1)));
    }
    @Test void activeOverlapIsRejectedBeforeAnyEvidenceWrite() {
        var repo=mock(AlarmShelvingJpaRepository.class);var alarms=mock(AlarmRepositoryPort.class);var events=mock(AlarmLifecycleEventRepositoryPort.class);
        var suppressions=mock(AlarmSuppressionJpaRepository.class);
        when(alarms.findByIdForUpdate("a")).thenReturn(Optional.of(AlarmSemanticRemediationTest.alarm("a",AlarmState.RAISED,"Titre")));
        when(repo.findByIdForUpdate("s")).thenReturn(Optional.empty());when(repo.existsByAlarmIdAndStatus("a",AlarmShelvingStatus.ACTIVE)).thenReturn(true);
        var adapter=new JpaAlarmShelvingRepositoryAdapter(repo,alarms,events,mock(AlarmCatalogValidation.class),suppressions,()->new AlarmLifecycleActorPort.Actor("system",null));
        assertThrows(IllegalStateException.class,()->adapter.save(shelving("s","a")));verify(repo,never()).saveAndFlush(any());
        when(repo.existsByAlarmIdAndStatus("a",AlarmShelvingStatus.ACTIVE)).thenReturn(false);
        when(suppressions.existsByScopeTypeAndScopeReferenceIdAndStatus(AlarmSuppressionScopeType.ALARM,"a",AlarmSuppressionStatus.ACTIVE)).thenReturn(true);
        assertThrows(IllegalStateException.class,()->adapter.save(shelving("s","a")));verify(events,never()).append(any());
    }
    @Test void expiryRechecksDueBoundaryAndRetainsTheContractualInstant() {
        var repo=mock(AlarmShelvingJpaRepository.class);var alarms=mock(AlarmRepositoryPort.class);var events=mock(AlarmLifecycleEventRepositoryPort.class);
        var shelf=shelving("s","a");var alarm=AlarmSemanticRemediationTest.alarm("a",AlarmState.SHELVED,"Titre");
        when(repo.findAlarmId("s")).thenReturn(Optional.of("a"));when(alarms.findByIdForUpdate("a")).thenReturn(Optional.of(alarm));
        when(repo.findByIdForUpdate("s")).thenReturn(Optional.of(AlarmPersistenceMapper.toEntity(shelf)));when(repo.saveAndFlush(any())).thenAnswer(i->i.getArgument(0));
        when(events.findById(AlarmLifecycleEvent.operationId("SHELVED","s"))).thenReturn(Optional.of(new AlarmLifecycleEvent("source","a",AlarmLifecycleEventType.SHELVED,AlarmState.RAISED,AlarmState.SHELVED,"reason",null,"actor",null,null,null,null,START,null,null)));
        var adapter=new JpaAlarmShelvingRepositoryAdapter(repo,alarms,events,mock(AlarmCatalogValidation.class),mock(AlarmSuppressionJpaRepository.class),()->new AlarmLifecycleActorPort.Actor("system",null));
        assertFalse(adapter.expireIfDue("s",END.minusNanos(1),"scheduler",null));verify(events,never()).append(any());
        assertTrue(adapter.expireIfDue("s",END.plusSeconds(3600),"scheduler",null));
        verify(events).append(argThat(e->e.eventType()==AlarmLifecycleEventType.UNSHELVED&&e.occurredAt().equals(END)&&e.actorId().equals("scheduler")));
        verify(repo).saveAndFlush(argThat(e->e.status()==AlarmShelvingStatus.EXPIRED&&e.unshelvedAt().equals(END)));
    }
}
