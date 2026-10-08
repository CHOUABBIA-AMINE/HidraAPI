/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseLifecycleSemanticPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence
 *
 * @Description : Enforces HSE-owned lifecycle integrity through owner-controlled references.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence;

import java.nio.file.*;
import java.sql.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import dz.sh.hidra.modules.hse.domain.model.HseClosure;
import dz.sh.hidra.modules.hse.application.port.out.*;
import dz.sh.hidra.modules.hse.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.identity.application.contract.hse.HseActorContract;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.support.TransactionTemplate;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
/** Real PostgreSQL baseline and actual forward SQL; no reconstructed operational evidence. */
@Testcontainers(disabledWithoutDocker=true)
class HseLifecycleSemanticPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    Connection connection() throws SQLException {return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());}
    void sql(String text) throws SQLException {try(var c=connection();var s=c.createStatement()) {s.execute(text);}}
    void file(String name) throws Exception {try(var c=connection()) {c.setAutoCommit(false);try(var s=c.createStatement()) {s.execute(Files.readString(Path.of("src/main/resources/db/migration",name)));c.commit();}catch(Exception e) {c.rollback();throw e;}}}
    @BeforeEach void baseline() throws Exception {sql("DROP SCHEMA public CASCADE;CREATE SCHEMA public");file("V20260611_012__create_hse_tables.sql");}
    void migrate() throws Exception {file("V20261008_014__hmr_082_hse_case_lifecycle.sql");file("V20261008_015__hmr_096_hse_closure_atomic_evidence.sql");}
    void parent() throws SQLException {sql("INSERT INTO hidra_hse_case(id,case_number,title,case_type_id,severity_id,status,source_type,reported_at,created_at,updated_at) VALUES('case','CASE','Title','type','severity','OPEN','MANUAL',now(),now(),now())");}
    @Test void genericClosedInsertAndStandaloneClosureCannotCommit() throws Exception {
        migrate();parent();assertThrows(SQLException.class,() -> sql("UPDATE hidra_hse_case SET status='CLOSED',closed_at=now() WHERE id='case'"));
        assertThrows(SQLException.class,() -> sql("INSERT INTO hidra_hse_closure VALUES('closure','case','summary',true,true,true,false,'actor','Actor',now(),null)"));
    }
    @Test void incoherentLegacyLifecycleAbortsWithoutManufacturingEvidence() throws Exception {
        parent();sql("UPDATE hidra_hse_case SET status='CLOSED',closed_at=now()");assertThrows(SQLException.class,this::migrate);
        try(var c=connection();var s=c.createStatement();var rows=s.executeQuery("SELECT count(*) FROM hidra_hse_case_status_history")) {assertTrue(rows.next());assertEquals(0,rows.getInt(1));}
    }
    @Test void statusHistoryCannotBeOverwrittenDeletedOrTruncated() throws Exception {
        migrate();parent();sql("INSERT INTO hidra_hse_case_status_history(id,hse_case_id,new_status,changed_at) VALUES('history','case','OPEN',now())");
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_hse_case_status_history SET new_status='IN_PROGRESS'"));
        assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_hse_case_status_history"));assertThrows(SQLException.class,() -> sql("TRUNCATE hidra_hse_case_status_history"));
    }

    int count(String table) throws SQLException {try(var c=connection();var st=c.createStatement();var rows=st.executeQuery("SELECT count(*) FROM "+table)) {rows.next();return rows.getInt(1);}}
    HseClosure requested(String id) {return new HseClosure(id,"case","summary",true,true,true,false,"actor","untrusted",Instant.EPOCH,null);}
    class RuntimeFixture implements AutoCloseable {
        final LocalContainerEntityManagerFactoryBean factory=new LocalContainerEntityManagerFactoryBean();
        final HseClosureRepositoryPort port;
        final TransactionTemplate transaction;
        RuntimeFixture() {
            factory.setDataSource(new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword()));
            factory.setPackagesToScan("dz.sh.hidra.modules.hse.infrastructure.persistence.entity");factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
            factory.setJpaPropertyMap(Map.of("hibernate.hbm2ddl.auto","none"));factory.afterPropertiesSet();
            var emf=Objects.requireNonNull(factory.getObject());var em=SharedEntityManagerCreator.createSharedEntityManager(emf);var repos=new JpaRepositoryFactory(em);
            var actors=new HseActorContract() {
                public Actor currentActor(Instant at) {return new Actor("actor","Canonical Actor");}
                public Optional<Actor> eligibleActor(String id,Instant at) {return Optional.of(new Actor(id,"Canonical Actor"));}
            };
            var manager=new JpaTransactionManager(emf);transaction=new TransactionTemplate(manager);
            var coordinator=new JpaHseClosureLifecycleAdapter(repos.getRepository(HseCaseJpaRepository.class),repos.getRepository(HseClosureJpaRepository.class),
                    repos.getRepository(HseCaseStatusHistoryJpaRepository.class),actors,new dz.sh.hidra.modules.workflow.application.contract.hse.HseWorkflowReferenceContract() {public boolean caseMatches(String id,String target) {return false;}public boolean taskMatches(String id,String parent,String capa) {return false;}},em);
            var proxy=new ProxyFactory(coordinator);proxy.addAdvice(new TransactionInterceptor(manager,new AnnotationTransactionAttributeSource()));
            port=new JpaHseClosureRepositoryAdapter(repos.getRepository(HseClosureJpaRepository.class),(HseClosureLifecyclePort)proxy.getProxy());
        }
        public void close() {factory.destroy();}
    }
    @Test void actualJpaClosureParentAndHistoryRollbackAndCommitTogether() throws Exception {
        migrate();parent();try(var runtime=new RuntimeFixture()) {
            assertThrows(IllegalStateException.class,() -> runtime.transaction.execute(status -> {runtime.port.save(requested("closure"));throw new IllegalStateException("forced after all flushed writes");}));
            assertEquals(0,count("hidra_hse_closure"));assertEquals(0,count("hidra_hse_case_status_history"));
            try(var c=connection();var st=c.createStatement();var r=st.executeQuery("SELECT status,closed_at FROM hidra_hse_case")) {assertTrue(r.next());assertEquals("OPEN",r.getString(1));assertNull(r.getTimestamp(2));}
            var result=runtime.port.save(requested("closure"));assertEquals("Canonical Actor",result.closedByDisplayNameSnapshot());assertFalse(result.regulatoryReviewed());
            try(var c=connection();var st=c.createStatement();var r=st.executeQuery("SELECT p.status,p.closed_at,c.closed_at,h.changed_at,h.old_status,h.new_status,c.closed_by_actor_id,h.changed_by_actor_id,h.correlation_id FROM hidra_hse_case p JOIN hidra_hse_closure c ON c.hse_case_id=p.id JOIN hidra_hse_case_status_history h ON h.correlation_id=c.id")) {
                assertTrue(r.next());assertEquals("CLOSED",r.getString(1));assertEquals(result.closedAt(),r.getTimestamp(2).toInstant());assertEquals(r.getTimestamp(2),r.getTimestamp(3));assertEquals(r.getTimestamp(3),r.getTimestamp(4));
                assertEquals("OPEN",r.getString(5));assertEquals("CLOSED",r.getString(6));assertEquals(r.getString(7),r.getString(8));assertEquals(result.id(),r.getString(9));assertFalse(r.next());
            }
        }
    }
    @Test void concurrentAuthoritativeClosuresHaveExactlyOneWinner() throws Exception {
        migrate();parent();var executor=Executors.newFixedThreadPool(2);
        try(var runtime=new RuntimeFixture()) {
            var start=new CountDownLatch(1);
            var a=executor.submit(() -> {start.await();try {runtime.port.save(requested("a"));return true;}catch(RuntimeException denied) {return false;}});
            var b=executor.submit(() -> {start.await();try {runtime.port.save(requested("b"));return true;}catch(RuntimeException denied) {return false;}});
            start.countDown();assertEquals(1,(a.get(15,TimeUnit.SECONDS)?1:0)+(b.get(15,TimeUnit.SECONDS)?1:0));
            assertEquals(1,count("hidra_hse_closure"));assertEquals(1,count("hidra_hse_case_status_history"));
        } finally {executor.shutdownNow();}
    }
    @Test void recordedClosureCannotBeChangedOrRemovedAndParentCannotReopen() throws Exception {
        migrate();parent();try(var runtime=new RuntimeFixture()) {runtime.port.save(requested("closure"));assertThrows(RuntimeException.class,() -> runtime.port.save(requested("replay")));}
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_hse_closure SET impact_assessed=false"));assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_hse_closure"));
        assertThrows(SQLException.class,() -> sql("TRUNCATE hidra_hse_closure"));assertThrows(SQLException.class,() -> sql("UPDATE hidra_hse_case SET status='OPEN',closed_at=null"));
    }
    @Test void falseAttestationAndCancelledParentCannotClose() throws Exception {
        migrate();parent();assertThrows(SQLException.class,() -> sql("INSERT INTO hidra_hse_closure VALUES('closure','case','summary',false,true,true,false,'actor','Actor',now(),null)"));
        sql("UPDATE hidra_hse_case SET status='CANCELLED'");try(var runtime=new RuntimeFixture()) {assertThrows(RuntimeException.class,() -> runtime.port.save(requested("closure")));}
        assertEquals(0,count("hidra_hse_closure"));
    }
    void policy() throws SQLException {sql("INSERT INTO hidra_hse_catalog_field_policy VALUES('CAPA_ACTION_TYPE','OWNER_APPROVED_FIXTURE',true,now(),now())");}
    void type(String id,String family,boolean active) throws SQLException {sql("INSERT INTO hidra_hse_catalog_entry VALUES('"+id+"','"+family+"','CODE',"+active+",0,false,now(),now())");}
    void capa(Connection c,String id,String type) throws SQLException {try(var st=c.createStatement()) {st.execute("INSERT INTO hidra_hse_capa(id,hse_case_id,action_number,action_type_id,title,verification_required,status,created_at,updated_at) VALUES('"+id+"','case','CAPA','"+type+"','Title',false,'PROPOSED',now(),now())");}}
    void capaMigration() throws Exception {file("V20261008_016__hmr_097_hse_capa_reference_catalog_integrity.sql");}
    @Test void missingMappingAndWrongFamilyOrInactiveFreshTypeRejectCapa() throws Exception {
        migrate();parent();type("type","OWNER_APPROVED_FIXTURE",true);capaMigration();
        try(var c=connection()) {assertThrows(SQLException.class,() -> capa(c,"missingPolicy","type"));}
        policy();type("wrong","OTHER",true);type("inactive","OWNER_APPROVED_FIXTURE",false);
        try(var c=connection()) {assertThrows(SQLException.class,() -> capa(c,"wrongFamily","wrong"));assertThrows(SQLException.class,() -> capa(c,"inactiveType","inactive"));capa(c,"valid","type");}
    }
    @Test void unchangedInactiveHistorySurvivesButMappingIdentityCannotBeReassigned() throws Exception {
        migrate();parent();policy();type("type","OWNER_APPROVED_FIXTURE",true);capaMigration();try(var c=connection()) {capa(c,"capa","type");}
        sql("UPDATE hidra_hse_catalog_entry SET active=false WHERE id='type'");sql("UPDATE hidra_hse_catalog_field_policy SET active=false");sql("UPDATE hidra_hse_capa SET title='Updated' WHERE id='capa'");
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_hse_catalog_field_policy SET catalog_name='OTHER'"));assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_hse_catalog_field_policy"));
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_hse_catalog_entry SET catalog_name='OTHER' WHERE id='type'"));assertThrows(SQLException.class,() -> sql("TRUNCATE hidra_hse_catalog_field_policy"));
    }
    @Test void legacyWithoutMappingFailsThenApprovedMappingPermitsRetryWithoutDataRewrite() throws Exception {
        migrate();parent();type("type","OWNER_APPROVED_FIXTURE",true);try(var c=connection()) {capa(c,"legacy","type");}
        assertThrows(SQLException.class,this::capaMigration);assertEquals(1,count("hidra_hse_capa"));assertEquals(0,count("hidra_hse_catalog_field_policy"));
        policy();capaMigration();assertEquals(1,count("hidra_hse_capa"));
    }
    @Test void legacyWrongFamilyFailsAndDoesNotReclassifyIt() throws Exception {
        migrate();parent();policy();type("wrong","OTHER",true);try(var c=connection()) {capa(c,"legacy","wrong");}assertThrows(SQLException.class,this::capaMigration);
        try(var c=connection();var st=c.createStatement();var r=st.executeQuery("SELECT catalog_name FROM hidra_hse_catalog_entry WHERE id='wrong'")) {assertTrue(r.next());assertEquals("OTHER",r.getString(1));}
    }
    @Test void committedParentDeletionWinsAgainstConcurrentCapaInsert() throws Exception {
        migrate();parent();policy();type("type","OWNER_APPROVED_FIXTURE",true);capaMigration();var pool=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var deleting=connection()) {
            deleting.setAutoCommit(false);try(var st=deleting.createStatement()) {st.execute("DELETE FROM hidra_hse_case WHERE id='case'");}
            var write=pool.submit(() -> {try(var c=connection()) {attempting.countDown();capa(c,"late","type");return false;}catch(SQLException denied) {return true;}});
            assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,() -> write.get(150,TimeUnit.MILLISECONDS));deleting.commit();assertTrue(write.get(10,TimeUnit.SECONDS));
        } finally {pool.shutdownNow();}
    }
    @Test void concurrentFamilyReclassificationCannotInvalidateCommittedCapa() throws Exception {
        migrate();parent();policy();type("type","OWNER_APPROVED_FIXTURE",true);capaMigration();var pool=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var writing=connection()) {
            writing.setAutoCommit(false);capa(writing,"capa","type");
            var update=pool.submit(() -> {try(var c=connection();var st=c.createStatement()) {attempting.countDown();st.execute("UPDATE hidra_hse_catalog_entry SET catalog_name='OTHER' WHERE id='type'");return false;}catch(SQLException denied) {return true;}});
            assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,() -> update.get(150,TimeUnit.MILLISECONDS));writing.commit();assertTrue(update.get(10,TimeUnit.SECONDS));assertEquals(1,count("hidra_hse_capa"));
        } finally {pool.shutdownNow();}
    }
}
