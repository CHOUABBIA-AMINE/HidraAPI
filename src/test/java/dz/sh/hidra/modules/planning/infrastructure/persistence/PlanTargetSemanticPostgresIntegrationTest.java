/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanTargetSemanticPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence
 *
 * @Description : Executes real PostgreSQL migrations, local races and Spring-JPA rollback checks.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence;

import java.nio.file.*;
import java.sql.*;
import java.time.Instant;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import dz.sh.hidra.modules.planning.domain.model.PlanTarget;
import dz.sh.hidra.modules.planning.domain.value.PlanTargetStatus;
import dz.sh.hidra.modules.planning.application.port.out.PlanTargetRepositoryPort;
import dz.sh.hidra.modules.planning.infrastructure.configuration.PlanningTargetValuePolicy;
import dz.sh.hidra.modules.planning.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.*;
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
class PlanTargetSemanticPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    Connection connection() throws SQLException {return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());}
    void sql(String text) throws SQLException {try(var c=connection();var s=c.createStatement()){s.execute(text);}}
    void file(String name) throws Exception {
        try(var c=connection()) {
            c.setAutoCommit(false);
            try(var s=c.createStatement()){s.execute(Files.readString(Path.of("src/main/resources/db/migration",name)));c.commit();}
            catch(Exception e){c.rollback();throw e;}
        }
    }
    int count(String table) throws SQLException {try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT count(*) FROM "+table)){r.next();return r.getInt(1);}}
    @BeforeEach void baseline() throws Exception {
        sql("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        file("V20260611_006__create_planning_tables.sql");
        file("V20261008_019__hmr_094_planning_target_value_policy.sql");
        sql("INSERT INTO hidra_planning_plan_revision(id,plan_id,revision_number,revision_code,status,created_at,updated_at) VALUES('revision','plan',1,'R','DRAFT',now(),now()),('other','plan',2,'R2','DRAFT',now(),now())");
        sql("INSERT INTO hidra_planning_catalog_entry VALUES('type','TARGET_TYPE','OWNER_FIXTURE',true,0,false,now(),now())");
    }
    void policy() throws SQLException {sql("INSERT INTO hidra_planning_target_value_policy(target_type_id,representation_kind,active) VALUES('type','NUMERIC',true)");}
    void migrate() throws Exception {file("V20261008_020__hmr_094_planning_plan_target_integrity.sql");}
    void configured() throws Exception {policy();migrate();}
    void record(Connection c,String id,String nomination,String scenario) throws SQLException {
        try(var s=c.prepareStatement("INSERT INTO hidra_planning_plan_target(id,revision_id,nomination_id,scenario_id,target_type_id,topology_asset_type,topology_asset_id,topology_asset_code,target_value,unit_id,valid_from,valid_to,status,created_at,updated_at) VALUES(?,'revision',?,?,'type','PIPELINE','asset','HISTORICAL',1,'unit',now(),now(),'DRAFT',now(),now())")){
            s.setString(1,id);s.setString(2,nomination);s.setString(3,scenario);s.executeUpdate();
        }
    }
    void parents() throws SQLException {
        sql("INSERT INTO hidra_planning_nomination(id,revision_id,code,nomination_type_id,product_type_id,quantity,quantity_unit_id,status,period_start,period_end,created_at,updated_at) VALUES('nomination','revision','N','n-type','product',1,'unit','DRAFT',now(),now(),now(),now())");
        sql("INSERT INTO hidra_planning_plan_scenario(id,revision_id,code,name_fr,scenario_type_id,primary_scenario,status,created_at,updated_at) VALUES('scenario','revision','S','Scenario','s-type',false,'DRAFT',now(),now())");
    }
    @Test void emptyPolicyMetadataAndMissingLegacyMappingAbortThenAllowApprovedRetry() throws Exception {
        assertEquals(0,count("hidra_planning_target_value_policy"));
        try(var c=connection()){record(c,"legacy",null,null);}
        assertThrows(SQLException.class,this::migrate);
        assertEquals(1,count("hidra_planning_plan_target"));assertEquals(0,count("hidra_planning_target_value_policy"));
        policy();migrate();assertEquals(1,count("hidra_planning_plan_target"));
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT topology_asset_code FROM hidra_planning_plan_target")){r.next();assertEquals("HISTORICAL",r.getString(1));}
    }
    @Test void legacyOrphanCrossRevisionAndIncompleteValueAbortWithoutPartialInstallation() throws Exception {
        policy();parents();sql("UPDATE hidra_planning_nomination SET revision_id='other'");
        try(var c=connection()){record(c,"legacy","nomination","scenario");}
        assertThrows(SQLException.class,this::migrate);
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT count(*) FROM pg_constraint WHERE conname='fk_hmr094_value_policy'")){r.next();assertEquals(0,r.getInt(1));}
        sql("UPDATE hidra_planning_nomination SET revision_id='revision'");sql("UPDATE hidra_planning_plan_target SET scenario_id='missing'");
        assertThrows(SQLException.class,this::migrate);
        sql("UPDATE hidra_planning_plan_target SET scenario_id=null,target_value=null");
        assertThrows(SQLException.class,this::migrate);assertEquals(1,count("hidra_planning_plan_target"));
    }
    @Test void numericTextWrongFamilyAndMissingFreshPoliciesFailClosed() throws Exception {
        migrate();try(var c=connection()){assertThrows(SQLException.class,() -> record(c,"no-policy",null,null));}
        policy();try(var c=connection()){record(c,"numeric",null,null);}
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_planning_plan_target SET unit_id=' '"));
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_planning_plan_target SET target_value=null"));
        sql("DELETE FROM hidra_planning_plan_target");sql("UPDATE hidra_planning_target_value_policy SET representation_kind='TEXT'");
        try(var c=connection()){assertThrows(SQLException.class,() -> record(c,"empty-text",null,null));}
        sql("INSERT INTO hidra_planning_plan_target(id,revision_id,target_type_id,topology_asset_type,topology_asset_id,topology_asset_code,target_value,target_text_value,valid_from,valid_to,status,created_at,updated_at) VALUES('text','revision','type','PIPELINE','asset','CODE',1,'STATE',now(),now(),'DRAFT',now(),now())");
        assertEquals(1,count("hidra_planning_plan_target"));
        sql("DELETE FROM hidra_planning_plan_target");sql("UPDATE hidra_planning_catalog_entry SET catalog_name='OTHER'");
        try(var c=connection()){assertThrows(SQLException.class,() -> record(c,"wrong-family",null,null));}
    }
    @Test void inactiveHistorySurvivesButSemanticsAndMetadataCannotBeChanged() throws Exception {
        configured();try(var c=connection()){record(c,"target",null,null);}
        sql("UPDATE hidra_planning_target_value_policy SET active=false");sql("UPDATE hidra_planning_catalog_entry SET active=false");
        sql("UPDATE hidra_planning_plan_target SET status='CANCELLED'");
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_planning_plan_target SET target_value=2"));
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_planning_target_value_policy SET representation_kind='TEXT'"));
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_planning_catalog_entry SET catalog_name='OTHER'"));
        assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_planning_target_value_policy"));
        assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_planning_catalog_entry"));
        assertThrows(SQLException.class,() -> sql("TRUNCATE hidra_planning_target_value_policy CASCADE"));
        try(var c=connection()){assertThrows(SQLException.class,() -> record(c,"fresh",null,null));}
    }
    @Test void optionalParentsRemainNullableAndLocalReparentOrDeleteIsDenied() throws Exception {
        configured();parents();try(var c=connection()){record(c,"optional",null,null);record(c,"related","nomination","scenario");}
        assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_planning_nomination"));
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_planning_plan_scenario SET revision_id='other'"));
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_planning_plan_target SET revision_id='other' WHERE id='related'"));
        assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_planning_plan_revision WHERE id='revision'"));
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_planning_plan_target SET valid_to=valid_from-interval '1 second'"));
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_planning_plan_target SET topology_asset_type=' '"));
    }
    @Test void committedNominationReparentWinsAgainstConcurrentTargetWrite() throws Exception {
        configured();parents();var executor=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var parent=connection()) {
            parent.setAutoCommit(false);try(var s=parent.createStatement()){s.execute("UPDATE hidra_planning_nomination SET revision_id='other' WHERE id='nomination'");}
            var write=executor.submit(() -> {try(var c=connection()){attempting.countDown();record(c,"late","nomination",null);return false;}catch(SQLException denied){if(!"23503".equals(denied.getSQLState()))throw denied;return true;}});
            assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,() -> write.get(150,TimeUnit.MILLISECONDS));
            parent.commit();assertTrue(write.get(10,TimeUnit.SECONDS));assertEquals(0,count("hidra_planning_plan_target"));
        } finally {executor.shutdownNow();}
    }
    @Test void approvedRevisionImmutabilityIsRetainedWhenTargetIntegrityIsInstalled() throws Exception {
        file("V20261007_001__hmr_064_planning_plan_revision.sql");
        configured();sql("UPDATE hidra_planning_plan_revision SET status='APPROVED' WHERE id='revision'");
        try(var c=connection()){record(c,"target",null,null);}
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_planning_plan_revision SET status='DRAFT' WHERE id='revision'"));
        assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_planning_plan_revision WHERE id='revision'"));
        assertThrows(SQLException.class,() -> sql("TRUNCATE hidra_planning_plan_revision CASCADE"));
    }
    @Test void committedScenarioDeletionWinsAgainstConcurrentTargetWrite() throws Exception {
        configured();parents();var executor=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var deleting=connection()) {
            deleting.setAutoCommit(false);try(var s=deleting.createStatement()){s.execute("DELETE FROM hidra_planning_plan_scenario WHERE id='scenario'");}
            var write=executor.submit(() -> {try(var c=connection()){attempting.countDown();record(c,"late",null,"scenario");return false;}catch(SQLException denied){if(!"23503".equals(denied.getSQLState()))throw denied;return true;}});
            assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,() -> write.get(150,TimeUnit.MILLISECONDS));
            deleting.commit();assertTrue(write.get(10,TimeUnit.SECONDS));
        } finally {executor.shutdownNow();}
    }
    @Test void sharedPolicyAndCatalogLocksRejectConcurrentReclassification() throws Exception {
        configured();var executor=Executors.newSingleThreadExecutor();
        for(String update:List.of("UPDATE hidra_planning_target_value_policy SET representation_kind='TEXT'","UPDATE hidra_planning_catalog_entry SET catalog_name='OTHER'")){
            sql("DELETE FROM hidra_planning_plan_target");var attempting=new CountDownLatch(1);
            try(var writing=connection()){
                writing.setAutoCommit(false);record(writing,"target",null,null);
                var change=executor.submit(() -> {try(var c=connection();var s=c.createStatement()){attempting.countDown();s.execute(update);return false;}catch(SQLException denied){return true;}});
                assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,() -> change.get(150,TimeUnit.MILLISECONDS));
                writing.commit();assertTrue(change.get(10,TimeUnit.SECONDS));
            }
        }
        executor.shutdownNow();
    }
    PlanTarget requested(String id) {var at=Instant.parse("2026-10-08T00:00:00Z");return new PlanTarget(id,"revision",null,null,"type","PIPELINE","asset","CALLER",null,null,null,BigDecimal.ONE,null,"unit",null,null,at,at,null,PlanTargetStatus.DRAFT,at,at);}
    class RuntimeFixture implements AutoCloseable {
        final LocalContainerEntityManagerFactoryBean factory=new LocalContainerEntityManagerFactoryBean();
        final PlanTargetRepositoryPort port;
        final TransactionTemplate transaction;
        RuntimeFixture(){
            factory.setDataSource(new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword()));
            factory.setPackagesToScan("dz.sh.hidra.modules.planning.infrastructure.persistence.entity");
            factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());factory.setJpaPropertyMap(Map.of("hibernate.hbm2ddl.auto","none"));factory.afterPropertiesSet();
            var emf=Objects.requireNonNull(factory.getObject());var em=SharedEntityManagerCreator.createSharedEntityManager(emf);var repos=new JpaRepositoryFactory(em);
            var manager=new JpaTransactionManager(emf);transaction=new TransactionTemplate(manager);
            var validation=new PlanTargetReferenceValidation(repos.getRepository(PlanRevisionJpaRepository.class),repos.getRepository(NominationJpaRepository.class),
                    repos.getRepository(PlanScenarioJpaRepository.class),repos.getRepository(PlanningCatalogEntryJpaRepository.class),new PlanningTargetValuePolicy(em),
                    (type,id) -> "PIPELINE".equals(type)&&"asset".equals(id)?Optional.of(new dz.sh.hidra.modules.topology.application.contract.planning.PlanningTargetTopologyReferenceContract.Asset(id,"OWNER",null)):Optional.empty(),
                    id -> Optional.empty());
            var adapter=new JpaPlanTargetRepositoryAdapter(repos.getRepository(PlanTargetJpaRepository.class),validation);var proxy=new ProxyFactory(adapter);
            proxy.addAdvice(new TransactionInterceptor(manager,new AnnotationTransactionAttributeSource()));port=(PlanTargetRepositoryPort)proxy.getProxy();
        }
        public void close(){factory.destroy();}
    }
    @Test void springJpaRollbackAfterFlushAndCanonicalSuccessfulWrite() throws Exception {
        configured();try(var runtime=new RuntimeFixture()){
            assertThrows(IllegalStateException.class,() -> runtime.transaction.execute(status -> {runtime.port.save(requested("rolled-back"));throw new IllegalStateException("forced rollback after flush");}));
            assertEquals(0,count("hidra_planning_plan_target"));
            assertEquals("OWNER",runtime.port.save(requested("saved")).topologyAssetCode());assertEquals(1,count("hidra_planning_plan_target"));
            sql("UPDATE hidra_planning_target_value_policy SET active=false");
            assertThrows(RuntimeException.class,() -> runtime.port.save(requested("denied")));assertEquals(1,count("hidra_planning_plan_target"));
        }
    }
}
