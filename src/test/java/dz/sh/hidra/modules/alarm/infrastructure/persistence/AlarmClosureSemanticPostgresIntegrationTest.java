/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmClosureSemanticPostgresIntegrationTest
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
import dz.sh.hidra.modules.alarm.application.port.out.*;
import dz.sh.hidra.modules.alarm.application.service.AlarmApplicationService;
import dz.sh.hidra.modules.alarm.application.port.in.CloseAlarmUseCase;
import dz.sh.hidra.modules.alarm.application.command.CloseAlarmCommand;
import dz.sh.hidra.modules.alarm.domain.value.*;
import dz.sh.hidra.modules.alarm.semantic.*;
import java.sql.SQLException;
import java.util.concurrent.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;

@Testcontainers(disabledWithoutDocker=true)
class AlarmClosureSemanticPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    Database db;
    @BeforeEach void baseline() throws Exception {db=new Database(POSTGRES);db.baseline();db.migrate100();db.legacyAlarm("a","Titre");}
    void migrate() throws Exception {db.file("V20261008_023__hmr_105_alarm_closure_integrity.sql");}
    AlarmClosureRepositoryPort port(RuntimeFixture r) {return r.proxy(new JpaAlarmClosureRepositoryAdapter(r.repositories.getRepository(AlarmClosureJpaRepository.class),r.alarms,r.events),AlarmClosureRepositoryPort.class);}
    @Test void liveClosureRequiresClearAndCancellationIsRecordedWithoutMandatoryReviewWorkflow() throws Exception {
        migrate();try(var r=new RuntimeFixture(db,false)) {
            var port=port(r);var service=r.proxy(new AlarmApplicationService(r.alarms,mock(AlarmAcknowledgementRepositoryPort.class),port),CloseAlarmUseCase.class);
            assertThrows(RuntimeException.class,()->service.closeAlarm(new CloseAlarmCommand("a",AlarmClosureType.NORMALIZED,null,null,"actor",true,null,"corr")));
            service.closeAlarm(new CloseAlarmCommand("a",AlarmClosureType.CANCELLED,null,null,"actor",true,null,"corr"));
            assertEquals(AlarmState.CANCELLED,r.alarms.findById("a").orElseThrow().currentState());
            assertEquals(1,db.count("hidra_alarm_closure"));assertEquals(1,db.count("hidra_alarm_lifecycle_event"));
        }
    }
    @Test void flushedClosureAndAlarmUpdatesRollbackWhenEventAppendFails() throws Exception {
        migrate();try(var r=new RuntimeFixture(db,true)) {
            assertThrows(IllegalStateException.class,()->port(r).save(AlarmClosureSemanticRemediationTest.closure("rollback","a",AlarmClosureType.CANCELLED)));
            assertEquals(0,db.count("hidra_alarm_closure"));assertEquals(0,db.count("hidra_alarm_lifecycle_event"));assertEquals(AlarmState.RAISED,r.alarms.findById("a").orElseThrow().currentState());
        }
    }
    @Test void migrationRejectsDuplicateHistoricalClosuresWithoutMergingEvidence() throws Exception {
        db.sql("INSERT INTO hidra_alarm_closure(id,alarm_id,closure_type,closed_by_actor_id,closed_at,requires_review) VALUES('c1','a','NORMALIZED','actor',now(),false),('c2','a','NORMALIZED','actor',now(),false)");
        assertThrows(SQLException.class,this::migrate);assertEquals(2,db.count("hidra_alarm_closure"));
    }
    @Test void twoRealConcurrentClosuresProduceOneTerminalRecordAndOneEvent() throws Exception {
        migrate();var executor=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var r=new RuntimeFixture(db,false)) {
            var waiting=r.transaction.execute(status->{
                port(r).save(AlarmClosureSemanticRemediationTest.closure("first","a",AlarmClosureType.CANCELLED));
                var task=executor.submit(()->{attempting.countDown();try {port(r).save(AlarmClosureSemanticRemediationTest.closure("second","a",AlarmClosureType.CANCELLED));return false;}catch(RuntimeException denied){return true;}});
                try {assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,()->task.get(150,TimeUnit.MILLISECONDS));}catch(Exception e){throw new RuntimeException(e);}return task;
            });
            assertTrue(waiting.get(10,TimeUnit.SECONDS));assertEquals(1,db.count("hidra_alarm_closure"));assertEquals(1,db.count("hidra_alarm_lifecycle_event"));
        } finally {executor.shutdownNow();}
    }
    @Test void acknowledgementCommittedFirstDoesNotPreventSubsequentExplicitCancellation() throws Exception {
        migrate();var executor=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var r=new RuntimeFixture(db,false)) {
            var ack=r.proxy(new JpaAlarmAcknowledgementRepositoryAdapter(r.repositories.getRepository(AlarmAcknowledgementJpaRepository.class),r.alarms,r.events),AlarmAcknowledgementRepositoryPort.class);
            var waiting=r.transaction.execute(status->{
                ack.save(AlarmAcknowledgementSemanticRemediationTest.acknowledgement("ack","a"));
                var task=executor.submit(()->{attempting.countDown();return port(r).save(AlarmClosureSemanticRemediationTest.closure("closure","a",AlarmClosureType.CANCELLED));});
                try {assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,()->task.get(150,TimeUnit.MILLISECONDS));}catch(Exception e){throw new RuntimeException(e);}return task;
            });
            waiting.get(10,TimeUnit.SECONDS);assertEquals(1,db.count("hidra_alarm_acknowledgement"));assertEquals(1,db.count("hidra_alarm_closure"));
            assertEquals(2,db.count("hidra_alarm_lifecycle_event"));assertEquals(AlarmState.CANCELLED,r.alarms.findById("a").orElseThrow().currentState());
        } finally {executor.shutdownNow();}
    }
}
