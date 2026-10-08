/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationSemanticPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence
 *
 * @Description : Verifies forward integrity, catalog races and real owner Audit publication rollback on PostgreSQL.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence;

import dz.sh.hidra.modules.simulation.domain.model.SimulationRecommendation;
import dz.sh.hidra.modules.simulation.domain.value.SimulationRecommendationStatus;
import dz.sh.hidra.modules.simulation.application.port.out.SimulationRecommendationRepositoryPort;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter.JpaSimulationRecommendationRepositoryAdapter;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.audit.application.contract.simulation.SimulationRecommendationAuditContract;
import dz.sh.hidra.modules.audit.infrastructure.integration.SimulationRecommendationAuditContractAdapter;
import dz.sh.hidra.modules.audit.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.audit.application.service.*;
import dz.sh.hidra.modules.audit.application.port.out.*;
import jakarta.persistence.EntityManager;
import java.nio.file.*;
import java.sql.*;
import java.time.Instant;
import java.util.Map;
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

@Testcontainers(disabledWithoutDocker=true)
class SimulationSemanticPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    static final Path MIGRATIONS=Path.of("src/main/resources/db/migration");
    static final String CHANGE="V20261008_001__hmr_078_simulation_candidate_change_integrity.sql";
    static final String RECOMMENDATION="V20261008_002__hmr_079_simulation_recommendation_integrity.sql";
    static final String TAXONOMY="V20261008_003__provision_simulation_recommendation_audit_taxonomy.sql";
    Connection connection() throws SQLException {
        return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
    }
    void sql(String text) throws SQLException {try(var c=connection();var s=c.createStatement()){s.execute(text);}}
    void file(String name) throws Exception {
        String migration=Files.readString(MIGRATIONS.resolve(name));
        try(var c=connection()) {
            // Match Flyway: LOCK TABLE and all migration statements share one transaction.
            c.setAutoCommit(false);
            try(var statement=c.createStatement()) {
                statement.execute(migration);
                c.commit();
            } catch(SQLException | RuntimeException failure) {
                try {c.rollback();} catch(SQLException rollbackFailure) {failure.addSuppressed(rollbackFailure);}
                throw failure;
            }
        }
    }
    void oldSchema() throws Exception {
        sql("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        file("V20260611_022__create_simulation_tables.sql");file("V20260611_017__create_audit_tables.sql");
        // The four existing HRA-111 references relevant to these isolated migration tests.
        sql("ALTER TABLE hidra_simulation_candidate_change ADD FOREIGN KEY(candidate_id) REFERENCES hidra_simulation_optimization_candidate(id); "
                +"ALTER TABLE hidra_simulation_candidate_change ADD FOREIGN KEY(change_type_id) REFERENCES hidra_simulation_catalog_entry(id); "
                +"ALTER TABLE hidra_simulation_recommendation ADD FOREIGN KEY(run_id) REFERENCES hidra_simulation_run(id); "
                +"ALTER TABLE hidra_simulation_recommendation ADD FOREIGN KEY(recommendation_type_id) REFERENCES hidra_simulation_catalog_entry(id)");
    }
    void seed() throws SQLException {
        sql("INSERT INTO hidra_simulation_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) VALUES "
                +"('change-type','SIMULATION_CHANGE_TYPE','CHANGE',true,0,false,now(),now()),"
                +"('recommendation-type','SIMULATION_RECOMMENDATION_TYPE','ADOPT',true,0,false,now(),now()),"
                +"('confidence','SIMULATION_CONFIDENCE_LEVEL','HIGH',true,0,false,now(),now()),"
                +"('wrong','OTHER','WRONG',true,0,false,now(),now())");
        sql("INSERT INTO hidra_simulation_run(id,scenario_id,model_version_id,input_snapshot_id,run_type_id,status,requested_by_actor_id,requested_by_display_name_snapshot,queued_at,solver_profile_id,created_at) "
                +"VALUES ('run','scenario','version','snapshot','run-type','QUEUED','actor','Actor',now(),'solver',now())");
        sql("INSERT INTO hidra_simulation_optimization_candidate(id,run_id,candidate_number,candidate_status,feasible,created_at) "
                +"VALUES ('candidate','another-run',1,'GENERATED',true,now())");
    }
    @BeforeEach void setup() throws Exception {oldSchema();file(CHANGE);file(RECOMMENDATION);file(TAXONOMY);seed();}
    String change(String id,String type,String after) {
        return "INSERT INTO hidra_simulation_candidate_change(id,candidate_id,change_type_id,target_type,target_id,after_value,"
                +"requires_topology_change,requires_operational_procedure,safety_critical,created_at) VALUES ('"+id
                +"','candidate','"+type+"','PIPELINE','target','"+after+"',false,false,false,now())";
    }
    String recommendation(String id,String candidate,String type,String confidence,String title) {
        return "INSERT INTO hidra_simulation_recommendation(id,run_id,candidate_id,recommendation_type_id,recommendation_status,"
                +"title,description,confidence_level_id,created_at) VALUES ('"+id+"','run',"+literal(candidate)+",'"+type
                +"','DRAFT','"+title+"','Description',"+literal(confidence)+",now())";
    }
    String literal(String v) {return v==null?"null":"'"+v+"'";}
    int count(String table) throws SQLException {
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT count(*) FROM "+table)){r.next();return r.getInt(1);}
    }
    @Test void changeRequiredFieldsFamilyAndRetiredHistoryAreEnforced() throws Exception {
        assertThrows(SQLException.class,()->sql(change("blank","change-type"," ")));
        assertThrows(SQLException.class,()->sql(change("wrong","wrong","value")));
        sql(change("valid","change-type","value"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_simulation_candidate_change SET target_type='UNKNOWN' WHERE id='valid'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_simulation_catalog_entry SET catalog_name='OTHER' WHERE id='change-type'"));
        sql("UPDATE hidra_simulation_catalog_entry SET active=false WHERE id='change-type'");
        sql("UPDATE hidra_simulation_candidate_change SET explanation='Historical annotation' WHERE id='valid'");
        assertThrows(SQLException.class,()->sql(change("inactive","change-type","value")));
        assertEquals(1,count("hidra_simulation_candidate_change"));
    }
    @Test void optionalLocalReferencesAndRequiredRecommendationContentFailClosed() throws Exception {
        sql(recommendation("optional",null,"recommendation-type",null,"Title"));
        sql(recommendation("candidate","candidate","recommendation-type","confidence","Title"));
        // Candidate/run equality was not an admitted obligation; candidate has another run.
        assertThrows(SQLException.class,()->sql(recommendation("orphan","missing","recommendation-type",null,"Title")));
        assertThrows(SQLException.class,()->sql(recommendation("confidence-orphan",null,"recommendation-type","missing","Title")));
        assertThrows(SQLException.class,()->sql(recommendation("wrong-confidence",null,"recommendation-type","wrong","Title")));
        assertThrows(SQLException.class,()->sql(recommendation("wrong-type",null,"wrong",null,"Title")));
        assertThrows(SQLException.class,()->sql(recommendation("blank",null,"recommendation-type",null," ")));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_simulation_recommendation SET description=' ' WHERE id='optional'"));
        assertThrows(SQLException.class,()->sql("DELETE FROM hidra_simulation_optimization_candidate WHERE id='candidate'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_simulation_catalog_entry SET catalog_name='OTHER' WHERE id='confidence'"));
        sql("UPDATE hidra_simulation_catalog_entry SET active=false WHERE id IN ('recommendation-type','confidence')");
        sql("UPDATE hidra_simulation_recommendation SET title='Historical title' WHERE id='candidate'");
        assertThrows(SQLException.class,()->sql(recommendation("inactive",null,"recommendation-type",null,"Title")));
        assertEquals(2,count("hidra_simulation_recommendation"));
    }
    @Test void legacyBlankChangeAbortsWithoutInventedRepair() throws Exception {
        oldSchema();seed();sql(change("legacy","change-type"," "));
        assertThrows(SQLException.class,()->file(CHANGE));
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT after_value FROM hidra_simulation_candidate_change WHERE id='legacy'")) {
            assertTrue(r.next());assertEquals(" ",r.getString(1));
        }
    }
    @Test void legacyRecommendationOrphanAndWrongFamilyAbortWithoutRepair() throws Exception {
        oldSchema();seed();sql(recommendation("legacy","missing","recommendation-type",null,"Title"));
        assertThrows(SQLException.class,()->file(RECOMMENDATION));
        assertEquals(1,count("hidra_simulation_recommendation"));
        oldSchema();seed();sql(recommendation("legacy",null,"wrong",null,"Title"));
        assertThrows(SQLException.class,()->file(RECOMMENDATION));
        assertEquals(1,count("hidra_simulation_recommendation"));
    }
    @Test void taxonomyReusesActiveRowsAndRejectsInactiveWithoutReactivation() throws Exception {
        file(TAXONOMY);assertEquals(2,count("hidra_audit_catalog_entry"));
        sql("UPDATE hidra_audit_catalog_entry SET active=false WHERE catalog_name='EVENT_TYPE'");
        assertThrows(SQLException.class,()->file(TAXONOMY));
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT active FROM hidra_audit_catalog_entry WHERE catalog_name='EVENT_TYPE'")) {
            assertTrue(r.next());assertFalse(r.getBoolean(1));
        }
    }
    @Test void waitingNewReferenceObservesCommittedCatalogRetirement() throws Exception {
        var pool=Executors.newSingleThreadExecutor();
        try(var first=connection()) {
            first.setAutoCommit(false);
            try(var s=first.createStatement()){s.execute("UPDATE hidra_simulation_catalog_entry SET active=false WHERE id='recommendation-type'");}
            var entered=new CountDownLatch(1);
            var insert=pool.submit(()->{
                try(var second=connection();var s=second.createStatement()) {
                    s.execute("SET lock_timeout='5s'");entered.countDown();
                    try{s.execute(recommendation("waiting",null,"recommendation-type",null,"Title"));return true;}
                    catch(SQLException e){assertEquals("23514",e.getSQLState());return false;}
                }
            });
            assertTrue(entered.await(5,TimeUnit.SECONDS));first.commit();
            assertFalse(insert.get(10,TimeUnit.SECONDS));assertEquals(0,count("hidra_simulation_recommendation"));
        } finally {pool.shutdownNow();}
    }
    @Test void concurrentCandidateDeleteAndRecommendationInsertCannotBothCommit() throws Exception {
        var pool=Executors.newFixedThreadPool(2);var start=new CountDownLatch(1);
        try {
            var insert=pool.submit(()->attempt(start,recommendation("concurrent","candidate","recommendation-type",null,"Title")));
            var delete=pool.submit(()->attempt(start,"DELETE FROM hidra_simulation_optimization_candidate WHERE id='candidate'"));
            start.countDown();assertEquals(1,(insert.get(10,TimeUnit.SECONDS)?1:0)+(delete.get(10,TimeUnit.SECONDS)?1:0));
        } finally {pool.shutdownNow();}
    }
    boolean attempt(CountDownLatch start,String command) throws Exception {
        start.await();try(var c=connection();var s=c.createStatement()) {
            c.setAutoCommit(false);s.execute("SET LOCAL lock_timeout='5s'");
            try{s.execute(command);c.commit();return true;}
            catch(SQLException e){c.rollback();if(!"23503".equals(e.getSQLState()))throw e;return false;}
        }
    }
    SimulationRecommendation published(String id) {
        return new SimulationRecommendation(id,"run","candidate","recommendation-type",SimulationRecommendationStatus.PUBLISHED,
                "Title","Sensitive description omitted from Audit",null,null,null,null,null,Instant.now());
    }
    @Test void realJpaPublicationAndAuditCommitTogetherAndFailuresRollbackBoth() throws Exception {
        var ds=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
        var factory=new LocalContainerEntityManagerFactoryBean();factory.setDataSource(ds);
        factory.setPackagesToScan("dz.sh.hidra.modules.simulation.infrastructure.persistence.entity",
                "dz.sh.hidra.modules.audit.infrastructure.persistence.entity");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());factory.setJpaPropertyMap(Map.of("hibernate.hbm2ddl.auto","none"));
        factory.afterPropertiesSet();
        try {
            var emf=java.util.Objects.requireNonNull(factory.getObject());
            EntityManager em=SharedEntityManagerCreator.createSharedEntityManager(emf);
            var repos=new JpaRepositoryFactory(em);
            var auditCatalogs=repos.getRepository(AuditCatalogEntryJpaRepository.class);
            var policy=new AuditInputPolicy();
            var auditEvents=new JpaAuditEventRepositoryAdapter(repos.getRepository(AuditEventJpaRepository.class),em,
                    new JpaAuditCatalogEligibilityAdapter(auditCatalogs),policy);
            var auditService=new AuditApplicationService(auditEvents,mock(AuditExportRequestRepositoryPort.class),
                    mock(AuditAccessRecordRepositoryPort.class),policy);
            var auditOwner=new SimulationRecommendationAuditContractAdapter(auditCatalogs,auditService,policy);
            var transactionManager=new JpaTransactionManager(emf);
            java.util.function.Function<SimulationRecommendationAuditContract,SimulationRecommendationRepositoryPort> port=owner->{
                var target=new JpaSimulationRecommendationRepositoryAdapter(repos.getRepository(SimulationRecommendationJpaRepository.class),
                        repos.getRepository(SimulationCatalogEntryJpaRepository.class),repos.getRepository(SimulationOptimizationCandidateJpaRepository.class),
                        repos.getRepository(SimulationRunJpaRepository.class),owner,em);
                var interceptor=new TransactionInterceptor();interceptor.setTransactionManager(transactionManager);
                interceptor.setTransactionAttributeSource(new AnnotationTransactionAttributeSource());interceptor.afterPropertiesSet();
                var proxy=new ProxyFactory(target);proxy.addAdvice(interceptor);
                return (SimulationRecommendationRepositoryPort)proxy.getProxy();
            };
            var audited=port.apply(auditOwner);var saved=audited.publish(published("published"));
            assertNotNull(saved.publishedAt());assertNull(saved.publishedByActorId());
            assertEquals(1,count("hidra_simulation_recommendation"));assertEquals(1,count("hidra_audit_event"));
            try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT actor_id,occurred_at,payload_json FROM hidra_audit_event")) {
                assertTrue(r.next());assertNull(r.getString(1));assertEquals(saved.publishedAt().getEpochSecond(),r.getTimestamp(2).toInstant().getEpochSecond());
                assertTrue(r.getString(3).contains("published"));assertFalse(r.getString(3).contains("Sensitive description"));
            }
            assertThrows(RuntimeException.class,()->audited.save(published("bypass")));
            // Execute real Audit persistence/flush, then fail: BOTH flushed rows must roll back.
            var failing=port.apply(evidence->{auditOwner.appendPublished(evidence);throw new IllegalStateException("Injected Audit failure after flush");});
            assertThrows(RuntimeException.class,()->failing.publish(published("rollback")));
            assertEquals(1,count("hidra_simulation_recommendation"));assertEquals(1,count("hidra_audit_event"));
            sql("DELETE FROM hidra_audit_catalog_entry WHERE catalog_name='EVENT_TYPE'");
            assertThrows(RuntimeException.class,()->audited.publish(published("missing-taxonomy")));
            assertEquals(1,count("hidra_simulation_recommendation"));assertEquals(1,count("hidra_audit_event"));
            // A new publication ID is required; an existing publication cannot be silently merged.
            assertThrows(RuntimeException.class,()->audited.publish(published("published")));
            file(TAXONOMY);
            var pool=Executors.newFixedThreadPool(2);var start=new CountDownLatch(1);
            try {
                Callable<Boolean> publish=()->{start.await();try{audited.publish(published("concurrent-publication"));return true;}
                    catch(RuntimeException denied){return false;}};
                var first=pool.submit(publish);var second=pool.submit(publish);start.countDown();
                assertEquals(1,(first.get(15,TimeUnit.SECONDS)?1:0)+(second.get(15,TimeUnit.SECONDS)?1:0));
                assertEquals(2,count("hidra_simulation_recommendation"));assertEquals(2,count("hidra_audit_event"));
            } finally {pool.shutdownNow();}
        } finally {factory.destroy();}
    }
}
