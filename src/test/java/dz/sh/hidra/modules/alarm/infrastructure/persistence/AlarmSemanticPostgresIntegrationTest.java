/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSemanticPostgresIntegrationTest
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

import dz.sh.hidra.modules.alarm.application.port.out.*;
import dz.sh.hidra.modules.alarm.application.service.AlarmApplicationService;
import dz.sh.hidra.modules.alarm.application.port.in.RaiseAlarmUseCase;
import dz.sh.hidra.modules.alarm.application.command.RaiseAlarmCommand;
import dz.sh.hidra.modules.alarm.domain.model.*;
import dz.sh.hidra.modules.alarm.domain.value.*;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.validation.AlarmCatalogValidation;
import dz.sh.hidra.modules.alarm.semantic.AlarmSemanticRemediationTest;
import java.nio.file.*;
import java.sql.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.support.TransactionTemplate;

@Testcontainers(disabledWithoutDocker=true)
public class AlarmSemanticPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    Database db;
    @BeforeEach void baseline() throws Exception {db=new Database(POSTGRES);db.baseline();}
    public static class Database {
        final PostgreSQLContainer<?> postgres;
        public Database(PostgreSQLContainer<?> postgres) {this.postgres=postgres;}
        public Connection connection() throws SQLException {return DriverManager.getConnection(postgres.getJdbcUrl(),postgres.getUsername(),postgres.getPassword());}
        public void sql(String text) throws SQLException {try(var c=connection();var s=c.createStatement()){s.execute(text);}}
        public void file(String name) throws Exception {
            try(var c=connection()) {c.setAutoCommit(false);try(var s=c.createStatement()) {
                s.execute(Files.readString(Path.of("src/main/resources/db/migration",name)));c.commit();
            }catch(Exception e){c.rollback();throw e;}}
        }
        public int count(String table) throws SQLException {try(var c=connection();var s=c.createStatement();var r=s.executeQuery("select count(*) from "+table)){r.next();return r.getInt(1);}}
        public void baseline() throws Exception {
            sql("DROP SCHEMA public CASCADE; CREATE SCHEMA public");file("V20260611_008__create_alarm_tables.sql");
            String integrity=Files.readString(Path.of("src/main/resources/db/migration/V20260929_001__enforce_same_module_reference_integrity_a.sql"));
            sql(integrity.substring(integrity.indexOf("-- alarm: 14"),integrity.indexOf("-- leakdetection: 15")));
            sql("INSERT INTO hidra_alarm_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) VALUES ('type','ALARM_TYPE','type',false,0,false,now(),now()),('severity','ALARM_SEVERITY','severity',true,0,false,now(),now()),('reason','SHELVING_REASON','reason',true,0,false,now(),now())");
        }
        public void migrate100() throws Exception {file("V20261008_022__hmr_100_alarm_lifecycle_integrity.sql");}
        public void legacyAlarm(String id,String title) throws SQLException {
            try(var c=connection();var s=c.prepareStatement("INSERT INTO hidra_alarm(id,alarm_number,alarm_type_id,severity_id,title_fr,source_type,topology_asset_type_code,topology_asset_id,topology_asset_code,current_state,raised_at,last_updated_at,created_at,updated_at) VALUES (?,?,'type','severity',?,'MANUAL','PIPELINE','asset','HISTORICAL','RAISED',now(),now(),now(),now())")) {
                s.setString(1,id);s.setString(2,"AL-"+id);s.setString(3,title);s.executeUpdate();
            }
        }
    }
    public static class RuntimeFixture implements AutoCloseable {
        final LocalContainerEntityManagerFactoryBean factory=new LocalContainerEntityManagerFactoryBean();
        public final JpaRepositoryFactory repositories;
        public final JpaTransactionManager manager;
        public final TransactionTemplate transaction;
        public final AlarmRepositoryPort alarms;
        public final AlarmLifecycleEventRepositoryPort events;
        public final RaiseAlarmUseCase raise;
        public final AlarmCatalogValidation catalogs;
        public RuntimeFixture(Database db, boolean failEvents) {
            factory.setDataSource(new DriverManagerDataSource(db.postgres.getJdbcUrl(),db.postgres.getUsername(),db.postgres.getPassword()));
            factory.setPackagesToScan("dz.sh.hidra.modules.alarm.infrastructure.persistence.entity");
            factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());factory.setJpaPropertyMap(Map.of("hibernate.hbm2ddl.auto","none"));factory.afterPropertiesSet();
            var emf=Objects.requireNonNull(factory.getObject());manager=new JpaTransactionManager(emf);transaction=new TransactionTemplate(manager);
            var em=SharedEntityManagerCreator.createSharedEntityManager(emf);repositories=new JpaRepositoryFactory(em);
            catalogs=new AlarmCatalogValidation(repositories.getRepository(AlarmCatalogEntryJpaRepository.class));
            var eventAdapter=new JpaAlarmLifecycleEventRepositoryAdapter(repositories.getRepository(AlarmLifecycleEventJpaRepository.class));
            events=failEvents?new AlarmLifecycleEventRepositoryPort() {
                public AlarmLifecycleEvent append(AlarmLifecycleEvent event) {throw new IllegalStateException("forced lifecycle append failure after evidence flush");}
                public Optional<AlarmLifecycleEvent> findById(String id) {return eventAdapter.findById(id);}
            }:proxy(eventAdapter,AlarmLifecycleEventRepositoryPort.class);
            alarms=proxy(new JpaAlarmRepositoryAdapter(repositories.getRepository(AlarmJpaRepository.class),catalogs,events,()->new AlarmLifecycleActorPort.Actor("actor","Operator")),AlarmRepositoryPort.class);
            raise=proxy(new AlarmApplicationService(alarms,mock(AlarmAcknowledgementRepositoryPort.class),mock(AlarmClosureRepositoryPort.class)),RaiseAlarmUseCase.class);
        }
        public <T> T proxy(Object target,Class<T> type) {
            var proxy=new ProxyFactory(target);proxy.addAdvice(new TransactionInterceptor(manager,new AnnotationTransactionAttributeSource()));
            return type.cast(proxy.getProxy());
        }
        public void close() {factory.destroy();}
    }
    @Test void creationLiveAndDirectPathsProduceExactlyOneEventAndRetainSnapshots() throws Exception {
        db.migrate100();try(var runtime=new RuntimeFixture(db,false)) {
            var model=AlarmSemanticRemediationTest.alarm("direct",AlarmState.RAISED,"Titre");runtime.alarms.save(model);runtime.alarms.save(model);
            var now=AlarmSemanticRemediationTest.NOW;
            runtime.raise.raiseAlarm(new RaiseAlarmCommand("AL-live","type","severity",null,null,"Titre",null,null,null,null,
                    AlarmSourceType.MANUAL,null,null,null,null,null,"PIPELINE","asset","HISTORICAL",null,null,null,null,null,null,"corr"));
            assertEquals(2,db.count("hidra_alarm"));assertEquals(2,db.count("hidra_alarm_lifecycle_event"));
            assertEquals("HISTORICAL",runtime.alarms.findById("direct").orElseThrow().topologyAssetCode());
        }
    }
    @Test void eventFailureRollsBackTheFlushedAlarm() throws Exception {
        db.migrate100();try(var runtime=new RuntimeFixture(db,true)) {
            assertThrows(IllegalStateException.class,()->runtime.alarms.save(AlarmSemanticRemediationTest.alarm("rollback",AlarmState.RAISED,"Titre")));
            assertEquals(0,db.count("hidra_alarm"));assertEquals(0,db.count("hidra_alarm_lifecycle_event"));
        }
    }
    @Test void invalidLegacyTitleAbortsAndOwnerCorrectedDataCanRetryWithoutFakeEvents() throws Exception {
        db.legacyAlarm("legacy"," ");assertThrows(SQLException.class,db::migrate100);
        assertEquals(1,db.count("hidra_alarm"));assertEquals(0,db.count("hidra_alarm_lifecycle_event"));
        db.sql("UPDATE hidra_alarm SET title_fr='Owner supplied title'");db.migrate100();
        assertEquals(0,db.count("hidra_alarm_lifecycle_event"));
    }
    @Test void wrongLegacyFamilyAndDuplicateInitialEventsAbortWithoutRepair() throws Exception {
        db.legacyAlarm("legacy","Titre");db.sql("UPDATE hidra_alarm_catalog_entry SET catalog_name='WRONG' WHERE id='type'");
        assertThrows(SQLException.class,db::migrate100);db.sql("UPDATE hidra_alarm_catalog_entry SET catalog_name='ALARM_TYPE' WHERE id='type'");
        db.sql("INSERT INTO hidra_alarm_lifecycle_event(id,alarm_id,event_type,new_state,actor_id,occurred_at) VALUES('e1','legacy','RAISED','RAISED','actor',now()),('e2','legacy','RAISED','RAISED','actor',now())");
        assertThrows(SQLException.class,db::migrate100);assertEquals(2,db.count("hidra_alarm_lifecycle_event"));
    }
    @Test void historyAndUsedCatalogCannotBeMutatedDeletedOrTruncated() throws Exception {
        db.migrate100();try(var runtime=new RuntimeFixture(db,false)) {runtime.alarms.save(AlarmSemanticRemediationTest.alarm("a",AlarmState.RAISED,"Titre"));}
        for(String sql:List.of("UPDATE hidra_alarm_lifecycle_event SET reason_text='rewrite'","DELETE FROM hidra_alarm_lifecycle_event","TRUNCATE hidra_alarm_lifecycle_event","UPDATE hidra_alarm_catalog_entry SET catalog_name='WRONG' WHERE id='type'","DELETE FROM hidra_alarm_catalog_entry WHERE id='type'","TRUNCATE hidra_alarm_catalog_entry CASCADE"))
            assertThrows(SQLException.class,()->db.sql(sql));
        db.sql("UPDATE hidra_alarm_catalog_entry SET active=true WHERE id='type'");
        assertThrows(SQLException.class,()->db.sql("INSERT INTO hidra_alarm_acknowledgement(id,alarm_id,acknowledged_by_actor_id,acknowledged_at) VALUES('orphan','missing','actor',now())"));
    }
    @Test void concurrentUsedCatalogFamilyChangeWaitsAndFailsAfterAlarmCommit() throws Exception {
        db.migrate100();var executor=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var runtime=new RuntimeFixture(db,false)) {
            assertTrue(runtime.transaction.execute(status->{
                runtime.alarms.save(AlarmSemanticRemediationTest.alarm("race",AlarmState.RAISED,"Titre"));
                var change=executor.submit(()->{attempting.countDown();try {db.sql("UPDATE hidra_alarm_catalog_entry SET catalog_name='WRONG' WHERE id='type'");return false;}catch(SQLException denied){return true;}});
                try {assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,()->change.get(150,TimeUnit.MILLISECONDS));}catch(Exception e){throw new RuntimeException(e);}
                status.flush();return change;
            }).get(10,TimeUnit.SECONDS));
            assertEquals(1,db.count("hidra_alarm"));
        } finally {executor.shutdownNow();}
    }
}
