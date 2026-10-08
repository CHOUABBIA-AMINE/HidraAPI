/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSemanticRemediationTest
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
import dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter.JpaAlarmRepositoryAdapter;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.validation.AlarmCatalogValidation;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.entity.AlarmCatalogEntryJpaEntity;
import dz.sh.hidra.modules.alarm.application.port.out.*;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlarmSemanticRemediationTest {
    public static final Instant NOW=Instant.parse("2026-10-08T12:00:00Z");
    public static Alarm alarm(String id, AlarmState state, String title) {
        return new Alarm(id,"AL-"+id,"type","severity",null,null,title,null,null,null,null,
                AlarmSourceType.MANUAL,null,null,null,null,null,"PIPELINE","asset","HISTORICAL",null,
                state,NOW,null,NOW,state==AlarmState.CLEARED?NOW:null,
                state==AlarmState.CLOSED||state==AlarmState.CANCELLED?NOW:null,
                null,null,null,null,null,null,null,"corr",NOW,NOW);
    }
    @Test void requiredFrenchTitleFailsBeforePersistenceAndShapeRemainsStable() {
        for(String title:new String[]{null,""," "}) assertThrows(dz.sh.hidra.modules.alarm.domain.exception.InvalidAlarmValueException.class,()->alarm("a",AlarmState.RAISED,title));
        assertEquals(37,Alarm.class.getRecordComponents().length);
        assertEquals(15,AlarmLifecycleEvent.class.getRecordComponents().length);
        assertEquals("HISTORICAL",alarm("a",AlarmState.RAISED,"Titre").topologyAssetCode());
    }
    @Test void catalogMembershipIsExactButInactiveHistoryIsNotReclassified() {
        var entries=mock(AlarmCatalogEntryJpaRepository.class);
        when(entries.findByIdForShare("type")).thenReturn(Optional.of(entry("type","ALARM_TYPE",false)));
        when(entries.findByIdForShare("severity")).thenReturn(Optional.of(entry("severity","ALARM_SEVERITY",false)));
        var validation=new AlarmCatalogValidation(entries);
        assertDoesNotThrow(()->validation.validate(alarm("a",AlarmState.RAISED,"Titre")));
        when(entries.findByIdForShare("type")).thenReturn(Optional.of(entry("type","ALARM_PRIORITY",true)));
        assertThrows(IllegalArgumentException.class,()->validation.validate(alarm("a",AlarmState.RAISED,"Titre")));
        when(entries.findByIdForShare("type")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,()->validation.validate(alarm("a",AlarmState.RAISED,"Titre")));
    }
    @Test void directNewSaveAppendsOneInitialEventAndUpdateDoesNotAppendAgain() {
        var repo=mock(AlarmJpaRepository.class);var catalogs=mock(AlarmCatalogValidation.class);
        var events=mock(AlarmLifecycleEventRepositoryPort.class);var model=alarm("a",AlarmState.RAISED,"Titre");
        when(repo.findByIdForUpdate("a")).thenReturn(Optional.empty());
        when(repo.saveAndFlush(any())).thenAnswer(i->i.getArgument(0));
        var adapter=new JpaAlarmRepositoryAdapter(repo,catalogs,events,()->new AlarmLifecycleActorPort.Actor("actor","Name"));
        assertEquals(model,adapter.save(model));
        verify(events).append(argThat(e->e.eventType()==AlarmLifecycleEventType.RAISED&&e.previousState()==null&&e.actorId().equals("actor")));
        when(repo.findByIdForUpdate("a")).thenReturn(Optional.of(dz.sh.hidra.modules.alarm.infrastructure.persistence.mapper.AlarmPersistenceMapper.toEntity(model)));
        adapter.save(model);verify(events,times(1)).append(any());
    }
    @Test void aNewAlarmCannotPretendToBeExistingLifecycleHistory() {
        var repo=mock(AlarmJpaRepository.class);
        when(repo.findByIdForUpdate("a")).thenReturn(Optional.empty());
        var adapter=new JpaAlarmRepositoryAdapter(repo,mock(AlarmCatalogValidation.class),mock(AlarmLifecycleEventRepositoryPort.class),()->new AlarmLifecycleActorPort.Actor("system",null));
        assertThrows(IllegalArgumentException.class,()->adapter.save(alarm("a",AlarmState.CLOSED,"Titre")));
        verify(repo,never()).saveAndFlush(any());
    }
    static AlarmCatalogEntryJpaEntity entry(String id,String family,boolean active) {
        return new AlarmCatalogEntryJpaEntity(id,family,id,active,0,false,null,null,NOW,NOW);
    }
}
