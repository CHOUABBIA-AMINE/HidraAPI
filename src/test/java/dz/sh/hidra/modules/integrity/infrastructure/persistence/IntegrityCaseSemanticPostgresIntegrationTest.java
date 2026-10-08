/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityCaseSemanticPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence
 *
 * @Description : Validates IntegrityCase provenance through explicit owner-controlled references.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence;

import java.nio.file.*;
import java.sql.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import dz.sh.hidra.modules.integrity.domain.model.IntegrityCase;
import dz.sh.hidra.modules.integrity.domain.value.IntegrityCaseStatus;
import dz.sh.hidra.modules.integrity.application.port.out.IntegrityCaseRepositoryPort;
import dz.sh.hidra.modules.integrity.infrastructure.configuration.IntegrityCatalogFieldPolicy;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.*;
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
/** Isolated actual base tables/forward SQL and real JPA transactions. Full Flyway baseline belongs to repository CI. */
@Testcontainers(disabledWithoutDocker=true)
class IntegrityCaseSemanticPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    Connection connection() throws SQLException {return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());}
    void sql(String text) throws SQLException {try(var c=connection();var s=c.createStatement()) {s.execute(text);}}
    void file(String name) throws Exception {try(var c=connection()) {c.setAutoCommit(false);try(var s=c.createStatement()) {s.execute(Files.readString(Path.of("src/main/resources/db/migration",name)));c.commit();}catch(Exception e) {c.rollback();throw e;}}}
    @BeforeEach void baseline() throws Exception {
        sql("DROP SCHEMA public CASCADE;CREATE SCHEMA public");file("V20260611_013__create_integrity_tables.sql");
        // Install the existing case-type FK using its unchanged published HRA-111 statements.
        String published=Files.readString(Path.of("src/main/resources/db/migration/V20260929_002__enforce_same_module_reference_integrity_b.sql"));
        int start=published.indexOf("ALTER TABLE hidra_integrity_case\n");int end=published.indexOf("ALTER TABLE hidra_integrity_case VALIDATE CONSTRAINT fk_hra111_integrity_004;",start);
        assertTrue(start>=0 && end>start);sql(published.substring(start,end+"ALTER TABLE hidra_integrity_case VALIDATE CONSTRAINT fk_hra111_integrity_004;".length()));
        file("V20261008_017__hmr_098_integrity_case_catalog_field_policy.sql");
    }
    void migrate() throws Exception {file("V20261008_018__hmr_098_integrity_case_reference_integrity.sql");}
    void policy() throws SQLException {sql("INSERT INTO hidra_integrity_catalog_field_policy VALUES('CASE_TYPE','OWNER_APPROVED_CASE_FIXTURE',true,now(),now())");}
    void type(String id,String family,boolean active) throws SQLException {
        try(var c=connection();var s=c.prepareStatement("INSERT INTO hidra_integrity_catalog_entry VALUES(?,?,?, ?,0,false,now(),now())")) {s.setString(1,id);s.setString(2,family);s.setString(3,"TYPE");s.setBoolean(4,active);s.executeUpdate();}
    }
    void defect() throws SQLException {sql("INSERT INTO hidra_integrity_pipeline_defect(id,defect_number,defect_type_id,status,topology_asset_type_code,topology_asset_id,detected_at,created_at,updated_at) VALUES('defect','D','type','CLOSED','SEGMENT','different-asset',now(),now(),now())");}
    void record(Connection c,String id,String type,String defect) throws SQLException {
        try(var s=c.prepareStatement("INSERT INTO hidra_integrity_case(id,case_number,title,case_type_id,status,topology_asset_type_code,topology_asset_id,primary_defect_id,opened_at,created_at,updated_at) VALUES(?,'CASE','Title',?,'OPEN','PIPELINE','asset',?,now(),now(),now())")) {s.setString(1,id);s.setString(2,type);s.setString(3,defect);s.executeUpdate();}
    }
    int count(String table) throws SQLException {try(var c=connection();var s=c.createStatement();var rows=s.executeQuery("SELECT count(*) FROM "+table)) {rows.next();return rows.getInt(1);}}
    void configured() throws Exception {policy();type("type","OWNER_APPROVED_CASE_FIXTURE",true);migrate();}
    @Test void nullableAndExistingDefectReferencesAreAcceptedButDanglingReferenceCannotPersist() throws Exception {
        configured();defect();try(var c=connection()) {record(c,"optional","type",null);record(c,"with-defect","type","defect");assertThrows(SQLException.class,() -> record(c,"orphan","type","missing"));}
        assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_integrity_pipeline_defect WHERE id='defect'"));assertEquals(2,count("hidra_integrity_case"));
    }
    @Test void existingCasesWithoutMappingAbortThenApprovedMetadataPermitsRetryWithoutRewrite() throws Exception {
        type("type","OWNER_APPROVED_CASE_FIXTURE",true);try(var c=connection()) {record(c,"legacy","type",null);}
        assertThrows(SQLException.class,this::migrate);assertEquals(0,count("hidra_integrity_catalog_field_policy"));assertEquals(1,count("hidra_integrity_case"));
        policy();migrate();assertEquals(1,count("hidra_integrity_case"));
    }
    @Test void legacyOrphanAbortsWithoutInstallingPartialConstraintsOrFabricatingParent() throws Exception {
        policy();type("type","OWNER_APPROVED_CASE_FIXTURE",true);try(var c=connection()) {record(c,"legacy","type","missing");}
        assertThrows(SQLException.class,this::migrate);assertEquals(0,count("hidra_integrity_pipeline_defect"));
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT count(*) FROM pg_constraint WHERE conname='fk_hmr098_primary_defect'")) {r.next();assertEquals(0,r.getInt(1));}
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT primary_defect_id FROM hidra_integrity_case")) {r.next();assertEquals("missing",r.getString(1));}
    }
    @Test void wrongFamilyLegacyAndTemporalInconsistencyFailWithoutChangingEvidence() throws Exception {
        policy();type("wrong","OTHER",true);try(var c=connection()) {record(c,"legacy","wrong",null);}assertThrows(SQLException.class,this::migrate);
        sql("UPDATE hidra_integrity_catalog_entry SET catalog_name='OWNER_APPROVED_CASE_FIXTURE' WHERE id='wrong'");
        sql("UPDATE hidra_integrity_case SET closed_at=opened_at-interval '1 second'");assertThrows(SQLException.class,this::migrate);
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT closed_at<opened_at FROM hidra_integrity_case")) {r.next();assertTrue(r.getBoolean(1));}
    }
    @Test void freshMissingMappingWrongFamilyAndInactiveTypesRejectWrites() throws Exception {
        type("type","OWNER_APPROVED_CASE_FIXTURE",true);migrate();try(var c=connection()) {assertThrows(SQLException.class,() -> record(c,"missing-policy","type",null));}
        policy();type("wrong","OTHER",true);type("inactive","OWNER_APPROVED_CASE_FIXTURE",false);
        try(var c=connection()) {assertThrows(SQLException.class,() -> record(c,"wrong","wrong",null));assertThrows(SQLException.class,() -> record(c,"inactive","inactive",null));}
    }
    @Test void unchangedInactiveHistorySurvivesButUsedTaxonomyCannotBeReassigned() throws Exception {
        configured();try(var c=connection()) {record(c,"case","type",null);}sql("UPDATE hidra_integrity_catalog_entry SET active=false");sql("UPDATE hidra_integrity_catalog_field_policy SET active=false");
        sql("UPDATE hidra_integrity_case SET title='Updated' WHERE id='case'");
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_integrity_catalog_field_policy SET catalog_name='OTHER'"));assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_integrity_catalog_field_policy"));
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_integrity_catalog_entry SET catalog_name='OTHER'"));assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_integrity_catalog_entry"));
        assertThrows(SQLException.class,() -> sql("TRUNCATE hidra_integrity_catalog_field_policy"));assertThrows(SQLException.class,() -> sql("TRUNCATE hidra_integrity_catalog_entry CASCADE"));
        try(var c=connection()) {assertThrows(SQLException.class,() -> record(c,"new","type",null));}
    }
    @Test void databaseRetainsOrderingWithoutInventingClosedStatusTimestampCoupling() throws Exception {
        configured();try(var c=connection()) {record(c,"case","type",null);}sql("UPDATE hidra_integrity_case SET status='CLOSED',closed_at=null");
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_integrity_case SET closed_at=opened_at-interval '1 second'"));sql("UPDATE hidra_integrity_case SET status='OPEN',closed_at=opened_at");
    }
    @Test void committedDefectDeletionWinsAgainstConcurrentCaseReferenceWrite() throws Exception {
        configured();defect();var executor=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var deleting=connection()) {
            deleting.setAutoCommit(false);try(var s=deleting.createStatement()) {s.execute("DELETE FROM hidra_integrity_pipeline_defect WHERE id='defect'");}
            var write=executor.submit(() -> {try(var c=connection()) {attempting.countDown();record(c,"late","type","defect");return false;}catch(SQLException denied) {if(!"23503".equals(denied.getSQLState())) throw denied;return true;}});
            assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,() -> write.get(150,TimeUnit.MILLISECONDS));deleting.commit();assertTrue(write.get(10,TimeUnit.SECONDS));
        } finally {executor.shutdownNow();}
    }
    @Test void concurrentCatalogReclassificationCannotInvalidateCommittedCase() throws Exception {
        configured();var executor=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var writing=connection()) {
            writing.setAutoCommit(false);record(writing,"case","type",null);
            var update=executor.submit(() -> {try(var c=connection();var s=c.createStatement()) {attempting.countDown();s.execute("UPDATE hidra_integrity_catalog_entry SET catalog_name='OTHER' WHERE id='type'");return false;}catch(SQLException denied) {if(!"P0001".equals(denied.getSQLState())) throw denied;return true;}});
            assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,() -> update.get(150,TimeUnit.MILLISECONDS));writing.commit();assertTrue(update.get(10,TimeUnit.SECONDS));assertEquals(1,count("hidra_integrity_case"));
        } finally {executor.shutdownNow();}
    }
    @Test void concurrentMappingReassignmentCannotInvalidateCommittedCase() throws Exception {
        configured();var executor=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
        try(var writing=connection()) {
            writing.setAutoCommit(false);record(writing,"case","type",null);
            var update=executor.submit(() -> {try(var c=connection();var s=c.createStatement()) {attempting.countDown();s.execute("UPDATE hidra_integrity_catalog_field_policy SET catalog_name='OTHER'");return false;}catch(SQLException denied) {if(!"P0001".equals(denied.getSQLState())) throw denied;return true;}});
            assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,() -> update.get(150,TimeUnit.MILLISECONDS));writing.commit();assertTrue(update.get(10,TimeUnit.SECONDS));assertEquals(1,count("hidra_integrity_case"));
        } finally {executor.shutdownNow();}
    }
    IntegrityCase requested(String id,String defect) {var at=Instant.now();return new IntegrityCase(id,"CASE","Title",null,"type",IntegrityCaseStatus.OPEN,null,"PIPELINE","asset","untrusted",defect,"neutral-incident","neutral-hse",null,null,at,null,null,at,at);}
    class RuntimeFixture implements AutoCloseable {
        final LocalContainerEntityManagerFactoryBean factory=new LocalContainerEntityManagerFactoryBean();
        final IntegrityCaseRepositoryPort port;
        final TransactionTemplate transaction;
        RuntimeFixture() {
            factory.setDataSource(new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword()));factory.setPackagesToScan("dz.sh.hidra.modules.integrity.infrastructure.persistence.entity");
            factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());factory.setJpaPropertyMap(Map.of("hibernate.hbm2ddl.auto","none"));factory.afterPropertiesSet();
            var emf=Objects.requireNonNull(factory.getObject());var em=SharedEntityManagerCreator.createSharedEntityManager(emf);var repos=new JpaRepositoryFactory(em);
            var manager=new JpaTransactionManager(emf);transaction=new TransactionTemplate(manager);
            var validation=new IntegrityCaseReferenceValidation(new IntegrityCatalogFieldPolicy(em),repos.getRepository(IntegrityCatalogEntryJpaRepository.class),repos.getRepository(PipelineDefectJpaRepository.class),
                    (type,id) -> "PIPELINE".equals(type) && "asset".equals(id)?Optional.of(new dz.sh.hidra.modules.topology.application.contract.integrity.IntegrityCaseTopologyReferenceContract.Asset(id,"CanonicalCode",null)):Optional.empty(),
                    (id,at) -> false,id -> false,(id,target) -> false);
            var adapter=new JpaIntegrityCaseRepositoryAdapter(repos.getRepository(IntegrityCaseJpaRepository.class),validation);var proxy=new ProxyFactory(adapter);
            proxy.addAdvice(new TransactionInterceptor(manager,new AnnotationTransactionAttributeSource()));port=(IntegrityCaseRepositoryPort)proxy.getProxy();
        }
        public void close() {factory.destroy();}
    }
    @Test void actualJpaReferenceWriteRollsBackAfterFlushAndCommitsCanonicalEvidenceOnSuccess() throws Exception {
        configured();defect();try(var runtime=new RuntimeFixture()) {
            assertThrows(IllegalStateException.class,() -> runtime.transaction.execute(status -> {runtime.port.save(requested("case","defect"));throw new IllegalStateException("forced rollback after flush");}));
            assertEquals(0,count("hidra_integrity_case"));var saved=runtime.port.save(requested("case","defect"));assertEquals("CanonicalCode",saved.topologyAssetCodeSnapshot());
            try(var c=connection();var s=c.createStatement();var rows=s.executeQuery("SELECT primary_defect_id,topology_asset_code_snapshot,source_incident_id,source_hse_case_id FROM hidra_integrity_case")) {
                assertTrue(rows.next());assertEquals("defect",rows.getString(1));assertEquals("CanonicalCode",rows.getString(2));assertEquals("neutral-incident",rows.getString(3));assertEquals("neutral-hse",rows.getString(4));assertFalse(rows.next());
            }
        }
    }
    @Test void actualJpaMissingDefectAndWrongTypeRollbackBeforeAnyCaseIsCommitted() throws Exception {
        configured();try(var runtime=new RuntimeFixture()) {
            assertThrows(RuntimeException.class,() -> runtime.port.save(requested("missing-defect","missing")));assertEquals(0,count("hidra_integrity_case"));
            sql("UPDATE hidra_integrity_catalog_entry SET catalog_name='OTHER' WHERE id='type'");assertThrows(RuntimeException.class,() -> runtime.port.save(requested("wrong-type",null)));assertEquals(0,count("hidra_integrity_case"));
        }
    }
}
