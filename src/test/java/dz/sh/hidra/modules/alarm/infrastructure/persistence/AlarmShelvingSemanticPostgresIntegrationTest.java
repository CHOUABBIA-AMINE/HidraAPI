/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmShelvingSemanticPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence
 *
 * @Description : Enforces Alarm-owned transactional lifecycle evidence and integrity.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence;

import dz.sh.hidra.modules.alarm.infrastructure.persistence.AlarmSemanticPostgresIntegrationTest.*;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.alarm.infrastructure.scheduling.*;
import dz.sh.hidra.modules.alarm.infrastructure.service.AlarmSuppressionApplicationAdapter;
import dz.sh.hidra.modules.alarm.application.service.*;
import dz.sh.hidra.modules.alarm.application.port.out.*;
import dz.sh.hidra.modules.alarm.application.port.in.*;
import dz.sh.hidra.modules.alarm.application.command.*;
import dz.sh.hidra.modules.alarm.domain.model.*;
import dz.sh.hidra.modules.alarm.domain.value.*;
import dz.sh.hidra.modules.alarm.semantic.*;
import dz.sh.hidra.modules.audit.application.contract.alarm.AlarmSuppressionAuditContract;
import java.time.Instant;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;

@Testcontainers(disabledWithoutDocker=true)
class AlarmShelvingSemanticPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    Database db;
    @BeforeEach void baseline() throws Exception {db=new Database(POSTGRES);db.baseline();db.migrate100();db.legacyAlarm("a","Titre");}
    void migrate() throws Exception {db.file("V20261008_023__hmr_105_alarm_closure_integrity.sql");db.file("V20261008_024__hmr_106_alarm_shelving_integrity.sql");}
    AlarmShelvingRepositoryPort port(RuntimeFixture r) {
        return r.proxy(new JpaAlarmShelvingRepositoryAdapter(r.repositories.getRepository(AlarmShelvingJpaRepository.class),r.alarms,r.events,r.catalogs,
                r.repositories.getRepository(AlarmSuppressionJpaRepository.class),()->new AlarmLifecycleActorPort.Actor("system",null)),AlarmShelvingRepositoryPort.class);
    }
    ManageAlarmSuppressionUseCase suppression(RuntimeFixture r) {
        var repo=r.repositories.getRepository(AlarmSuppressionJpaRepository.class);
        var events=r.repositories.getRepository(AlarmLifecycleEventJpaRepository.class);
        var expiry=r.proxy(new AlarmSuppressionExpiryOrchestrator(repo,r.alarms,events,evidence->"audit-test-evidence"),AlarmSuppressionExpiryOrchestrator.class);
        var approval=new AlarmSuppressionApprovalService((workflow,operation)->{throw new AssertionError("Bounded suppression must not invoke approval");});
        return r.proxy(new AlarmSuppressionApplicationAdapter(repo,r.alarms,events,approval,expiry),ManageAlarmSuppressionUseCase.class);
    }
    CreateAlarmSuppressionCommand suppressCommand() {
        return new CreateAlarmSuppressionCommand(AlarmSuppressionScopeType.ALARM,"a","a",null,null,null,"reason",null,"actor",Instant.now().plusSeconds(3600),null,"corr");
    }
    @Test void liveShelveUnshelveSynchronizeAlarmAndRecordExactlyTwoEvents() throws Exception {
        migrate();try(var r=new RuntimeFixture(db,false)) {
            var p=port(r);var service=r.proxy(new AlarmShelvingApplicationService(r.alarms,p),ManageAlarmShelvingUseCase.class);
            String id=service.shelve(new ManageAlarmShelvingUseCase.ShelveAlarmCommand("a","reason",null,"actor",Instant.now().plusSeconds(60),"corr"));
            assertEquals(AlarmState.SHELVED,r.alarms.findById("a").orElseThrow().currentState());
            service.unshelve(new ManageAlarmShelvingUseCase.UnshelveAlarmCommand("a",id,"operator",null));
            assertEquals(AlarmState.RAISED,r.alarms.findById("a").orElseThrow().currentState());assertEquals(2,db.count("hidra_alarm_lifecycle_event"));
            assertEquals(AlarmShelvingStatus.COMPLETED,p.findById(id).orElseThrow().status());
        }
    }
    @Test void delayedExpiryKeepsDueTimeAndDoesNotReopenCancelledAlarmOrMoveSnapshotBackwards() throws Exception {
        migrate();try(var r=new RuntimeFixture(db,false)) {
            var p=port(r);var shelf=AlarmShelvingSemanticRemediationTest.shelving("s","a");p.save(shelf);
            var closure=r.proxy(new JpaAlarmClosureRepositoryAdapter(r.repositories.getRepository(AlarmClosureJpaRepository.class),r.alarms,r.events),AlarmClosureRepositoryPort.class);
            closure.save(AlarmClosureSemanticRemediationTest.closure("close","a",AlarmClosureType.CANCELLED));
            var before=r.alarms.findById("a").orElseThrow().lastUpdatedAt();
            assertFalse(p.expireIfDue("s",shelf.shelvedUntil().minusNanos(1),"scheduler",null));
            assertTrue(p.expireIfDue("s",shelf.shelvedUntil().plusSeconds(3600),"scheduler",null));assertFalse(p.expireIfDue("s",shelf.shelvedUntil().plusSeconds(3600),"scheduler",null));
            assertEquals(shelf.shelvedUntil(),p.findById("s").orElseThrow().unshelvedAt());
            assertEquals(AlarmState.CANCELLED,r.alarms.findById("a").orElseThrow().currentState());assertEquals(before,r.alarms.findById("a").orElseThrow().lastUpdatedAt());
            assertEquals(3,db.count("hidra_alarm_lifecycle_event"));
        }
    }
    @Test void newAndFinishedShelvingRollBackFlushedEvidenceWhenEventFails() throws Exception {
        migrate();try(var failing=new RuntimeFixture(db,true)) {
            assertThrows(IllegalStateException.class,()->port(failing).save(AlarmShelvingSemanticRemediationTest.shelving("s","a")));
            assertEquals(0,db.count("hidra_alarm_shelving"));assertEquals(AlarmState.RAISED,failing.alarms.findById("a").orElseThrow().currentState());
        }
        try(var r=new RuntimeFixture(db,false)) {port(r).save(AlarmShelvingSemanticRemediationTest.shelving("s","a"));}
        try(var failing=new RuntimeFixture(db,true)) {
            assertThrows(IllegalStateException.class,()->port(failing).expireIfDue("s",AlarmShelvingSemanticRemediationTest.END,"scheduler",null));
            assertEquals(AlarmShelvingStatus.ACTIVE,port(failing).findById("s").orElseThrow().status());assertEquals(AlarmState.SHELVED,failing.alarms.findById("a").orElseThrow().currentState());
            assertEquals(1,db.count("hidra_alarm_lifecycle_event"));
        }
    }
    void rawShelf(String id,String reason,String start,String end) throws SQLException {
        db.sql("INSERT INTO hidra_alarm_shelving(id,alarm_id,shelving_reason_id,shelved_by_actor_id,shelved_at,shelved_until,status) VALUES('"+id+"','a','"+reason+"','actor',"+start+","+end+",'ACTIVE')");
    }
    @Test void migrationsAbortOnInvalidIntervalsFamiliesAndActiveDuplicatesWithoutChangingHistory() throws Exception {
        rawShelf("bad","reason","now()","now()");assertThrows(SQLException.class,this::migrate);assertEquals(1,db.count("hidra_alarm_shelving"));
        db.baseline();db.migrate100();db.legacyAlarm("a","Titre");rawShelf("bad-family","type","now()","now()+interval '1 hour'");assertThrows(SQLException.class,this::migrate);
        db.baseline();db.migrate100();db.legacyAlarm("a","Titre");rawShelf("first","reason","now()","now()+interval '1 hour'");rawShelf("second","reason","now()","now()+interval '1 hour'");assertThrows(SQLException.class,this::migrate);assertEquals(2,db.count("hidra_alarm_shelving"));
    }
    @Test void directWritesCheckReasonAndUsedMetadataCannotBeReassignedOrDeleted() throws Exception {
        migrate();try(var r=new RuntimeFixture(db,false)) {
            var p=port(r);var s=AlarmShelvingSemanticRemediationTest.shelving("s","a");
            var bad=new AlarmShelving("bad","a","type",null,"actor",s.shelvedAt(),s.shelvedUntil(),null,null,AlarmShelvingStatus.ACTIVE,null);
            assertThrows(IllegalArgumentException.class,()->p.save(bad));p.save(s);
        }
        assertThrows(SQLException.class,()->db.sql("UPDATE hidra_alarm_catalog_entry SET catalog_name='WRONG' WHERE id='reason'"));
        assertThrows(SQLException.class,()->db.sql("DELETE FROM hidra_alarm_catalog_entry WHERE id='reason'"));
        assertThrows(SQLException.class,()->rawShelf("duplicate","reason","now()","now()+interval '1 hour'"));
    }
    @Test void legacyMissingRestorationEvidenceFailsClosedWithoutManufacturingTimeline() throws Exception {
        rawShelf("legacy","reason","now()","now()+interval '1 hour'");migrate();db.sql("UPDATE hidra_alarm SET current_state='SHELVED'");
        try(var r=new RuntimeFixture(db,false)) {
            var p=port(r);var shelf=p.findById("legacy").orElseThrow();
            assertThrows(IllegalStateException.class,()->p.save(AlarmShelvingSemanticRemediationTest.finished(shelf,AlarmShelvingStatus.COMPLETED,Instant.now())));
            assertEquals(AlarmShelvingStatus.ACTIVE,p.findById("legacy").orElseThrow().status());assertEquals(0,db.count("hidra_alarm_lifecycle_event"));
        }
    }
    @Test void simultaneousShelvingsSerializeAndProduceOneActiveRecord() throws Exception {
        migrate();var executor=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var r=new RuntimeFixture(db,false)) {
            var p=port(r);var waiting=r.transaction.execute(status->{
                p.save(AlarmShelvingSemanticRemediationTest.shelving("first","a"));
                var task=executor.submit(()->{attempting.countDown();try {p.save(AlarmShelvingSemanticRemediationTest.shelving("second","a"));return false;}catch(RuntimeException denied){return true;}});
                try {assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,()->task.get(150,TimeUnit.MILLISECONDS));}catch(Exception e){throw new RuntimeException(e);}return task;
            });assertTrue(waiting.get(10,TimeUnit.SECONDS));assertEquals(1,db.count("hidra_alarm_shelving"));assertEquals(1,db.count("hidra_alarm_lifecycle_event"));
        } finally {executor.shutdownNow();}
    }
    @Test void twoExpiryWorkersAndManualFinishRacesProduceOneFinishEvent() throws Exception {
        migrate();var executor=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var r=new RuntimeFixture(db,false)) {
            var p=port(r);var s=AlarmShelvingSemanticRemediationTest.shelving("s","a");p.save(s);
            var waiting=r.transaction.execute(status->{
                assertTrue(p.expireIfDue("s",s.shelvedUntil(),"scheduler",null));
                var task=executor.submit(()->{attempting.countDown();return p.expireIfDue("s",s.shelvedUntil(),"scheduler-2",null);});
                try {assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,()->task.get(150,TimeUnit.MILLISECONDS));}catch(Exception e){throw new RuntimeException(e);}return task;
            });assertFalse(waiting.get(10,TimeUnit.SECONDS));
            assertThrows(RuntimeException.class,()->p.save(AlarmShelvingSemanticRemediationTest.finished(s,AlarmShelvingStatus.COMPLETED,Instant.now())));
            assertEquals(2,db.count("hidra_alarm_lifecycle_event"));
        } finally {executor.shutdownNow();}
    }
    @Test void manualFinishCommittedFirstMakesWaitingExpiryANoOp() throws Exception {
        migrate();var executor=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var r=new RuntimeFixture(db,false)) {
            var p=port(r);var s=AlarmShelvingSemanticRemediationTest.shelving("s","a");p.save(s);
            var waiting=r.transaction.execute(status->{
                p.save(AlarmShelvingSemanticRemediationTest.finished(s,AlarmShelvingStatus.COMPLETED,Instant.now()));
                var task=executor.submit(()->{attempting.countDown();return p.expireIfDue("s",s.shelvedUntil(),"scheduler",null);});
                try {assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,()->task.get(150,TimeUnit.MILLISECONDS));}catch(Exception e){throw new RuntimeException(e);}return task;
            });assertFalse(waiting.get(10,TimeUnit.SECONDS));assertEquals(2,db.count("hidra_alarm_lifecycle_event"));
        } finally {executor.shutdownNow();}
    }
    @Test void shelvingVersusAlarmSuppressionUsesTheSameParentLockAndRejectsOverlap() throws Exception {
        migrate();var executor=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var r=new RuntimeFixture(db,false)) {
            var p=port(r);var suppression=suppression(r);var waiting=r.transaction.execute(status->{
                p.save(AlarmShelvingSemanticRemediationTest.shelving("s","a"));
                var task=executor.submit(()->{attempting.countDown();try {suppression.createSuppression(suppressCommand());return false;}catch(RuntimeException denied){return true;}});
                try {assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,()->task.get(150,TimeUnit.MILLISECONDS));}catch(Exception e){throw new RuntimeException(e);}return task;
            });assertTrue(waiting.get(10,TimeUnit.SECONDS));assertEquals(0,db.count("hidra_alarm_suppression"));assertEquals(1,db.count("hidra_alarm_shelving"));
        } finally {executor.shutdownNow();}
    }
    @Test void suppressionReleaseWaitingOnCancellationDoesNotReopenTerminalAlarm() throws Exception {
        migrate();var executor=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var r=new RuntimeFixture(db,false)) {
            var suppression=suppression(r);String id=suppression.createSuppression(suppressCommand()).id();
            var closure=r.proxy(new JpaAlarmClosureRepositoryAdapter(r.repositories.getRepository(AlarmClosureJpaRepository.class),r.alarms,r.events),AlarmClosureRepositoryPort.class);
            var waiting=r.transaction.execute(status->{
                closure.save(AlarmClosureSemanticRemediationTest.closure("close","a",AlarmClosureType.CANCELLED));
                var task=executor.submit(()->{attempting.countDown();return suppression.releaseSuppression(new ReleaseAlarmSuppressionCommand(id,"actor","corr"));});
                try {assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,()->task.get(150,TimeUnit.MILLISECONDS));}catch(Exception e){throw new RuntimeException(e);}return task;
            });waiting.get(10,TimeUnit.SECONDS);assertEquals(AlarmState.CANCELLED,r.alarms.findById("a").orElseThrow().currentState());assertEquals(2,db.count("hidra_alarm_lifecycle_event"));
        } finally {executor.shutdownNow();}
    }
    @Test void reasonDeletionOrFamilyMutationCommittedFirstDeniesWaitingNewShelving() throws Exception {
        migrate();var executor=Executors.newSingleThreadExecutor();
        try(var r=new RuntimeFixture(db,false)) {
            for(String operation:List.of("DELETE FROM hidra_alarm_catalog_entry WHERE id='unused'",
                    "UPDATE hidra_alarm_catalog_entry SET catalog_name='WRONG' WHERE id='unused'")) {
                db.sql("INSERT INTO hidra_alarm_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) VALUES('unused','SHELVING_REASON','unused',true,0,false,now(),now())");
                var attempting=new CountDownLatch(1);
                try(var changing=db.connection()) {
                    changing.setAutoCommit(false);try(var statement=changing.createStatement()){statement.execute(operation);}
                    var model=new AlarmShelving("race","a","unused",null,"actor",AlarmShelvingSemanticRemediationTest.START,
                            AlarmShelvingSemanticRemediationTest.END,null,null,AlarmShelvingStatus.ACTIVE,null);
                    var waiting=executor.submit(()->{attempting.countDown();try {port(r).save(model);return false;}catch(RuntimeException denied){return true;}});
                    assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,()->waiting.get(150,TimeUnit.MILLISECONDS));
                    changing.commit();assertTrue(waiting.get(10,TimeUnit.SECONDS));assertEquals(0,db.count("hidra_alarm_shelving"));assertEquals(0,db.count("hidra_alarm_lifecycle_event"));
                }
                db.sql("DELETE FROM hidra_alarm_catalog_entry WHERE id='unused'");
            }
        } finally {executor.shutdownNow();}
    }
    @Test void suppressionExpiryWaitingOnCancellationPreservesTerminalStateAndRecordsExpiryOnce() throws Exception {
        migrate();var executor=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var r=new RuntimeFixture(db,false)) {
            var suppression=suppression(r);suppression.createSuppression(suppressCommand());
            var closure=r.proxy(new JpaAlarmClosureRepositoryAdapter(r.repositories.getRepository(AlarmClosureJpaRepository.class),r.alarms,r.events),AlarmClosureRepositoryPort.class);
            var waiting=r.transaction.execute(status->{
                closure.save(AlarmClosureSemanticRemediationTest.closure("close","a",AlarmClosureType.CANCELLED));
                var task=executor.submit(()->{attempting.countDown();return suppression.expireDueSuppressions(
                        new EvaluateAlarmSuppressionExpiryCommand(Instant.now().plusSeconds(7200),"scheduler","corr"));});
                try {assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,()->task.get(150,TimeUnit.MILLISECONDS));}catch(Exception e){throw new RuntimeException(e);}return task;
            });assertEquals(1,waiting.get(10,TimeUnit.SECONDS));
            assertEquals(AlarmState.CANCELLED,r.alarms.findById("a").orElseThrow().currentState());assertEquals(2,db.count("hidra_alarm_lifecycle_event"));
            assertEquals(0,suppression.expireDueSuppressions(new EvaluateAlarmSuppressionExpiryCommand(Instant.now().plusSeconds(7200),"scheduler","corr")));
        } finally {executor.shutdownNow();}
    }
}
