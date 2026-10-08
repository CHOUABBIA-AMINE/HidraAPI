/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmAcknowledgementSemanticPostgresIntegrationTest
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
import dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter.JpaAlarmAcknowledgementRepositoryAdapter;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmAcknowledgementJpaRepository;
import dz.sh.hidra.modules.alarm.application.port.out.*;
import dz.sh.hidra.modules.alarm.application.service.AlarmApplicationService;
import dz.sh.hidra.modules.alarm.application.port.in.AcknowledgeAlarmUseCase;
import dz.sh.hidra.modules.alarm.application.command.AcknowledgeAlarmCommand;
import dz.sh.hidra.modules.alarm.domain.value.*;
import dz.sh.hidra.modules.alarm.semantic.*;
import java.util.concurrent.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;

@Testcontainers(disabledWithoutDocker=true)
class AlarmAcknowledgementSemanticPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    Database db;
    @BeforeEach void baseline() throws Exception {db=new Database(POSTGRES);db.baseline();db.migrate100();db.legacyAlarm("a","Titre");}
    AlarmAcknowledgementRepositoryPort port(RuntimeFixture r) {
        return r.proxy(new JpaAlarmAcknowledgementRepositoryAdapter(r.repositories.getRepository(AlarmAcknowledgementJpaRepository.class),r.alarms,r.events),AlarmAcknowledgementRepositoryPort.class);
    }
    @Test void liveAndDirectAcknowledgementsAreAtomicAndMultipleHistoryRowsRemainLegal() throws Exception {
        try(var r=new RuntimeFixture(db,false)) {
            var port=port(r);var service=r.proxy(new AlarmApplicationService(r.alarms,port,mock(AlarmClosureRepositoryPort.class)),AcknowledgeAlarmUseCase.class);
            service.acknowledgeAlarm(new AcknowledgeAlarmCommand("a","actor","Operator",null,null,null,"corr"));
            var ack=AlarmAcknowledgementSemanticRemediationTest.acknowledgement("direct","a");port.save(ack);port.save(ack);
            assertEquals(2,db.count("hidra_alarm_acknowledgement"));assertEquals(2,db.count("hidra_alarm_lifecycle_event"));
            assertEquals(AlarmState.ACKNOWLEDGED,r.alarms.findById("a").orElseThrow().currentState());
        }
    }
    @Test void unknownTerminalAndForcedEventFailureLeaveNoSplitEvidence() throws Exception {
        try(var r=new RuntimeFixture(db,true)) {
            var port=port(r);
            assertThrows(IllegalArgumentException.class,()->port.save(AlarmAcknowledgementSemanticRemediationTest.acknowledgement("unknown","missing")));
            assertThrows(IllegalStateException.class,()->port.save(AlarmAcknowledgementSemanticRemediationTest.acknowledgement("rollback","a")));
            assertEquals(0,db.count("hidra_alarm_acknowledgement"));assertEquals(AlarmState.RAISED,r.alarms.findById("a").orElseThrow().currentState());
        }
        db.sql("UPDATE hidra_alarm SET current_state='CLOSED',closed_at=now()");
        try(var r=new RuntimeFixture(db,false)) {assertThrows(RuntimeException.class,()->port(r).save(AlarmAcknowledgementSemanticRemediationTest.acknowledgement("late","a")));}
        assertEquals(0,db.count("hidra_alarm_acknowledgement"));
    }
    @Test void closeCommittedFirstSerializesAndDeniesWaitingAcknowledgement() throws Exception {
        var executor=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var c=db.connection();var r=new RuntimeFixture(db,false)) {
            c.setAutoCommit(false);try(var s=c.createStatement()){s.execute("UPDATE hidra_alarm SET current_state='CLOSED',closed_at=now() WHERE id='a'");}
            var waiting=executor.submit(()->{attempting.countDown();try {port(r).save(AlarmAcknowledgementSemanticRemediationTest.acknowledgement("race","a"));return false;}catch(RuntimeException denied){return true;}});
            assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,()->waiting.get(150,TimeUnit.MILLISECONDS));
            c.commit();assertTrue(waiting.get(10,TimeUnit.SECONDS));assertEquals(0,db.count("hidra_alarm_acknowledgement"));
        } finally {executor.shutdownNow();}
    }
}
