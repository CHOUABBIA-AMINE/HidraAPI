/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanActualDeviationSemanticPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence
 *
 * @Description : Executes real Monitoring PostgreSQL migration, local races and Spring-JPA rollback checks.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence;

import java.nio.file.*;
import java.sql.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import dz.sh.hidra.modules.monitoring.application.command.RecordDeviationCommand;
import dz.sh.hidra.modules.monitoring.application.port.in.RecordDeviationUseCase;
import dz.sh.hidra.modules.monitoring.application.port.out.PlanActualDeviationRepositoryPort;
import dz.sh.hidra.modules.monitoring.application.service.DeviationApplicationService;
import dz.sh.hidra.modules.monitoring.domain.model.PlanActualDeviation;
import dz.sh.hidra.modules.monitoring.domain.service.DeviationSeverityClassifier;
import dz.sh.hidra.modules.monitoring.domain.value.*;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.planning.application.contract.monitoring.MonitoringPlanTargetReferenceContract;
import dz.sh.hidra.modules.telemetry.application.contract.monitoring.MonitoringTrustedReadingReferenceContract;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
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
class PlanActualDeviationSemanticPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    Connection connection() throws SQLException {return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());}
    void sql(String text) throws SQLException {try(var c=connection();var s=c.createStatement()){s.execute(text);}}
    void file(String name) throws Exception {
        try(var c=connection()){
            c.setAutoCommit(false);try(var s=c.createStatement()){s.execute(Files.readString(Path.of("src/main/resources/db/migration",name)));c.commit();}
            catch(Exception e){c.rollback();throw e;}
        }
    }
    int count(String table) throws SQLException {try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT count(*) FROM "+table)){r.next();return r.getInt(1);}}
    @BeforeEach void baseline() throws Exception {
        sql("DROP SCHEMA public CASCADE; CREATE SCHEMA public");file("V20260611_007__create_monitoring_tables.sql");
    }
    void migrate() throws Exception {file("V20261008_021__hmr_103_monitoring_plan_actual_deviation_integrity.sql");}
    void evaluation() throws SQLException {
        sql("INSERT INTO hidra_monitoring_evaluation(id,plan_revision_id,topology_asset_type,topology_asset_id,telemetry_point_id,status,evaluation_start,actual_reading_count,deviation_count,created_at) VALUES('evaluation','revision','PIPELINE','asset','point','FAILED',now(),0,0,now())");
    }
    void record(Connection c,String id,String evaluation,String point) throws SQLException {
        try(var s=c.prepareStatement("INSERT INTO hidra_monitoring_plan_actual_deviation(id,evaluation_id,plan_target_id,telemetry_point_id,topology_asset_type,topology_asset_id,severity,status,detected_at) VALUES(?,?,'target',?,'PIPELINE','asset','INFO','OPEN',now())")){
            s.setString(1,id);s.setString(2,evaluation);s.setString(3,point);s.executeUpdate();
        }
    }
    @Test void optionalEvaluationRemainsNullableAndUnknownOrInconsistentContextIsDenied() throws Exception {
        migrate();evaluation();
        try(var c=connection()){
            record(c,"optional",null,null);record(c,"related","evaluation","point");
            assertThrows(SQLException.class,() -> record(c,"unknown","missing",null));
            assertThrows(SQLException.class,() -> record(c,"wrong-point","evaluation","other"));
        }
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_monitoring_plan_actual_deviation SET topology_asset_type='FACILITY' WHERE id='related'"));
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_monitoring_plan_actual_deviation SET topology_asset_id='other' WHERE id='related'"));
        assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_monitoring_evaluation"));
        assertEquals(2,count("hidra_monitoring_plan_actual_deviation"));
    }
    @Test void legacyOrphanAbortsWithoutPartialConstraintOrFabricatedEvaluation() throws Exception {
        try(var c=connection()){record(c,"legacy","missing",null);}
        assertThrows(SQLException.class,this::migrate);assertEquals(0,count("hidra_monitoring_evaluation"));
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT count(*) FROM pg_constraint WHERE conname='fk_hmr103_evaluation'")){r.next();assertEquals(0,r.getInt(1));}
        assertEquals(1,count("hidra_monitoring_plan_actual_deviation"));
    }
    @Test void legacyContextMismatchAbortsAndDoesNotRewriteSource() throws Exception {
        evaluation();try(var c=connection()){record(c,"legacy","evaluation","other");}
        assertThrows(SQLException.class,this::migrate);
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT telemetry_point_id FROM hidra_monitoring_plan_actual_deviation")){r.next();assertEquals("other",r.getString(1));}
    }
    @Test void usedEvaluationContextCannotBeReassignedButLifecycleCountersRemainOwned() throws Exception {
        migrate();evaluation();try(var c=connection()){record(c,"related","evaluation","point");}
        for(String field:List.of("plan_revision_id","topology_asset_type","topology_asset_id","telemetry_point_id"))
            assertThrows(SQLException.class,() -> sql("UPDATE hidra_monitoring_evaluation SET "+field+"='other'"));
        sql("UPDATE hidra_monitoring_evaluation SET status='COMPLETED',deviation_count=1");
        assertThrows(SQLException.class,() -> sql("TRUNCATE hidra_monitoring_evaluation CASCADE"));
    }
    @Test void deletionCommittedFirstDeniesConcurrentEvaluationReference() throws Exception {
        migrate();evaluation();var executor=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var deleting=connection()){
            deleting.setAutoCommit(false);try(var s=deleting.createStatement()){s.execute("DELETE FROM hidra_monitoring_evaluation");}
            var write=executor.submit(() -> {try(var c=connection()){attempting.countDown();record(c,"late","evaluation","point");return false;}catch(SQLException denied){if(!"23503".equals(denied.getSQLState()))throw denied;return true;}});
            assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,() -> write.get(150,TimeUnit.MILLISECONDS));
            deleting.commit();assertTrue(write.get(10,TimeUnit.SECONDS));assertEquals(0,count("hidra_monitoring_plan_actual_deviation"));
        } finally {executor.shutdownNow();}
    }
    @Test void deviationCommittedFirstDeniesConcurrentEvaluationReassignment() throws Exception {
        migrate();evaluation();var executor=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var writing=connection()){
            writing.setAutoCommit(false);record(writing,"deviation","evaluation","point");
            var change=executor.submit(() -> {try(var c=connection();var s=c.createStatement()){attempting.countDown();s.execute("UPDATE hidra_monitoring_evaluation SET plan_revision_id='other'");return false;}catch(SQLException denied){if(!"P0001".equals(denied.getSQLState()))throw denied;return true;}});
            assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,() -> change.get(150,TimeUnit.MILLISECONDS));
            writing.commit();assertTrue(change.get(10,TimeUnit.SECONDS));
        } finally {executor.shutdownNow();}
    }
    PlanActualDeviation requested(String id,String target,String reading,String point,String evaluation) {
        return new PlanActualDeviation(id,evaluation,target,null,reading,point,"PIPELINE","asset","HISTORICAL",null,null,null,null,null,
                DeviationSeverity.INFO,DeviationStatus.OPEN,Instant.parse("2026-10-08T00:00:00Z"),null,null,null);
    }
    class RuntimeFixture implements AutoCloseable {
        final LocalContainerEntityManagerFactoryBean factory=new LocalContainerEntityManagerFactoryBean();
        final PlanActualDeviationRepositoryPort port;
        final RecordDeviationUseCase service;
        final TransactionTemplate transaction;
        RuntimeFixture(){
            factory.setDataSource(new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword()));
            factory.setPackagesToScan("dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity");
            factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());factory.setJpaPropertyMap(Map.of("hibernate.hbm2ddl.auto","none"));factory.afterPropertiesSet();
            var emf=Objects.requireNonNull(factory.getObject());var em=SharedEntityManagerCreator.createSharedEntityManager(emf);var repos=new JpaRepositoryFactory(em);
            var manager=new JpaTransactionManager(emf);transaction=new TransactionTemplate(manager);var at=Instant.parse("2026-10-08T00:00:00Z");
            var validation=new PlanActualDeviationReferenceValidation(
                    id -> "target".equals(id)?Optional.of(new MonitoringPlanTargetReferenceContract.Target(id,"revision","PIPELINE","asset","point","DRAFT",null,null,null,at,at)):Optional.empty(),
                    id -> "point".equals(id),
                    id -> "reading".equals(id)?Optional.of(new MonitoringTrustedReadingReferenceContract.Reading(id,"point","LOW")):Optional.empty(),
                    repos.getRepository(MonitoringEvaluationJpaRepository.class));
            var adapter=new JpaPlanActualDeviationRepositoryAdapter(repos.getRepository(PlanActualDeviationJpaRepository.class),validation);
            var proxy=new ProxyFactory(adapter);proxy.addAdvice(new TransactionInterceptor(manager,new AnnotationTransactionAttributeSource()));
            port=(PlanActualDeviationRepositoryPort)proxy.getProxy();
            var application=new DeviationApplicationService(port,new DeviationSeverityClassifier(),validation);
            var applicationProxy=new ProxyFactory(application);applicationProxy.addAdvice(new TransactionInterceptor(manager,new AnnotationTransactionAttributeSource()));
            service=(RecordDeviationUseCase)applicationProxy.getProxy();
        }
        public void close(){factory.destroy();}
    }
    @Test void directJpaSaveRejectsUnknownOwnerEvidenceAndRollsBackFlushedWrites() throws Exception {
        migrate();evaluation();try(var runtime=new RuntimeFixture()){
            assertThrows(RuntimeException.class,() -> runtime.port.save(requested("unknown","missing",null,null,null)));
            assertThrows(RuntimeException.class,() -> runtime.port.save(requested("point","target",null,"missing",null)));
            assertThrows(RuntimeException.class,() -> runtime.port.save(requested("reading","target","missing",null,null)));
            assertThrows(RuntimeException.class,() -> runtime.port.save(requested("evaluation","target",null,null,"missing")));
            assertEquals(0,count("hidra_monitoring_plan_actual_deviation"));
            assertThrows(IllegalStateException.class,() -> runtime.transaction.execute(status -> {runtime.port.save(requested("rollback","target","reading","point","evaluation"));throw new IllegalStateException("forced after flush");}));
            assertEquals(0,count("hidra_monitoring_plan_actual_deviation"));
            var saved=runtime.port.save(requested("saved","target","reading","point","evaluation"));assertEquals("HISTORICAL",saved.topologyAssetCode());
            assertEquals(1,count("hidra_monitoring_plan_actual_deviation"));
        }
    }
    @Test void liveRecordUsesSpringTransactionRetainsFallbackAndRollback() throws Exception {
        migrate();evaluation();try(var runtime=new RuntimeFixture()){
            var command=new RecordDeviationCommand("evaluation","target",null,"reading","point","PIPELINE","asset","CODE",null,null,null,null,null,null,null,null);
            assertThrows(IllegalStateException.class,() -> runtime.transaction.execute(status -> {runtime.service.recordDeviation(command);throw new IllegalStateException("forced after record flush");}));
            assertEquals(0,count("hidra_monitoring_plan_actual_deviation"));
            runtime.service.recordDeviation(command);assertEquals(1,count("hidra_monitoring_plan_actual_deviation"));
            try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT severity FROM hidra_monitoring_plan_actual_deviation")){r.next();assertEquals("INFO",r.getString(1));}
            sql("UPDATE hidra_monitoring_evaluation SET deviation_count=9");
            try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT deviation_count FROM hidra_monitoring_evaluation")){r.next();assertEquals(9,r.getInt(1));}
        }
    }
}
