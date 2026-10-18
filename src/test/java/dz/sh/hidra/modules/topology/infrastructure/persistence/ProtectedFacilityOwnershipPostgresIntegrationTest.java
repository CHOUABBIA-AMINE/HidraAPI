/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedFacilityOwnershipPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-18
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence
 *
 * @Description : Qualifies actual owner generations, admission, isolation and ordinary writers.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence;

import dz.sh.hidra.modules.topology.application.contract.station.StationProtectedFacilityOwnershipContract;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcStationProtectedFacilityOwnershipAdapter;
import dz.sh.hidra.modules.topology.domain.value.FacilityStatus;
import dz.sh.hidra.modules.topology.domain.value.FacilityKind;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.*;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker=true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProtectedFacilityOwnershipPostgresIntegrationTest {
    private static final String BASE="public.hidra_topology_facility",GEN="public.hidra_topology_facility_owner_generation";
    private static final String ROLE="j_topology_runtime",PASSWORD="synthetic-owner-fixture";
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16");
    private AnnotationConfigApplicationContext context;
    private JdbcTemplate admin,jdbc;
    private DriverManagerDataSource runtime;
    private TransactionTemplate tx;
    private StationProtectedFacilityOwnershipContract owner;
    private String type;
    @Configuration @EnableTransactionManagement static class Config {}
    @BeforeAll void migrateAndConfigureRealOwnerBean() {
        var migration=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
        Flyway.configure().dataSource(migration).load().migrate();
        admin=new JdbcTemplate(migration);
        admin.execute("CREATE ROLE "+ROLE+" LOGIN PASSWORD '"+PASSWORD+"'");
        admin.execute("GRANT USAGE ON SCHEMA public TO "+ROLE);
        admin.execute("GRANT SELECT,INSERT,UPDATE,DELETE,TRUNCATE ON "+BASE+" TO "+ROLE);
        admin.execute("GRANT SELECT ON "+GEN+" TO "+ROLE);
        runtime=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),ROLE,PASSWORD);
        jdbc=new JdbcTemplate(runtime);
        var manager=new DataSourceTransactionManager(runtime);
        tx=new TransactionTemplate(manager);tx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        context=new AnnotationConfigApplicationContext();context.register(Config.class);
        context.registerBean(DataSource.class,()->runtime);
        context.registerBean(JdbcTemplate.class,()->jdbc);
        context.registerBean(PlatformTransactionManager.class,()->manager);
        context.registerBean(JdbcStationProtectedFacilityOwnershipAdapter.class);
        context.refresh();owner=context.getBean(StationProtectedFacilityOwnershipContract.class);
        type="jtype-"+UUID.randomUUID();admin.update("INSERT INTO public.hidra_topology_facility_type(id,code,name,facility_kind,status,created_at,updated_at) VALUES(?,?,'Synthetic','STATION','ACTIVE',now(),now())",type,type);
    }
    @AfterAll void closeContext() { if(context!=null) context.close(); }
    private String fixture(String value) {
        String id="j-"+UUID.randomUUID();insert(id,value);return id;
    }
    private void insert(String id,String value) { admin.update("INSERT INTO public.hidra_topology_facility(id,code,facility_type_id,facility_kind,owner_party_id,status,created_at,updated_at) VALUES(?,?,?,'STATION',?,'ACTIVE',now(),now())",id,id,type,value); }
    private long generation(String id) { return admin.queryForObject("SELECT generation FROM "+GEN+" WHERE facility_id=?",Long.class,id); }
    private StationProtectedFacilityOwnershipContract.Contribution evidence(String id) {
        return tx.execute(s->owner.read(owner.acquire(id)));
    }
    private void write(String id,String value) {jdbc.update("UPDATE "+BASE+" SET owner_party_id=? WHERE id=?",value,id);}
    private void deniedSql(String sql,String state) {
        var failure=assertThrows(DataAccessException.class,()->tx.execute(s->{jdbc.execute(sql);return null;}));
        assertEquals(state,((SQLException)failure.getMostSpecificCause()).getSQLState());
    }

    @Test void onlyRealOwnerSemanticsAreExported() { for(var status:FacilityStatus.values()) for(var kind:FacilityKind.values()) {
            String id=fixture("owner-A");admin.update("UPDATE public.hidra_topology_facility SET status=?,facility_kind=? WHERE id=?",status.name(),kind.name(),id);
            assertEquals("owner-A",evidence(id).ownerPartyId());
        } }
    @Test void independentIdentitiesAndPhysicalCoordinatesDoNotSubstitute() {
        String a=fixture("owner-A"),b=fixture("owner-A");
        tx.execute(s->{
            var x=owner.read(owner.acquire(" "+a+" "));var y=owner.read(owner.acquire(b));
            assertEquals(a,x.facilityId());assertEquals(b,y.facilityId());
            assertEquals(x.backendPid(),y.backendPid());assertEquals(x.transactionId(),y.transactionId());
            assertEquals(jdbc.queryForObject("SELECT pg_backend_pid()",Integer.class),x.backendPid());
            assertEquals(jdbc.queryForObject("SELECT txid_current()",Long.class),x.transactionId());return null;
        });
        assertThrows(IllegalStateException.class,()->evidence(a.toUpperCase()));
    }
    @Test void relevantNoopUnrelatedAndBulkUpdatesRespectGenerationRules() {
        String a=fixture("owner-A"),b=fixture("owner-A");assertEquals(1,generation(a));
        tx.execute(s->{write(a,"owner-A");return null;});assertEquals(2,generation(a));
        tx.execute(s->{jdbc.update("UPDATE "+BASE+" SET name_fr='Other' WHERE id=?",a);return null;});assertEquals(2,generation(a));
        tx.execute(s->{jdbc.update("UPDATE "+BASE+" SET owner_party_id=? WHERE id IN (?,?)","owner-A",a,b);return null;});
        assertEquals(3,generation(a));assertEquals(2,generation(b));
    }
    @Test void deleteReinsertRenameAndRollbackPreserveTombstones() {
        String id=fixture("owner-A"),renamed="j-"+UUID.randomUUID();
        tx.execute(s->{jdbc.update("DELETE FROM "+BASE+" WHERE id=?",id);return null;});
        assertEquals(2,generation(id));assertFalse(admin.queryForObject("SELECT present FROM "+GEN+" WHERE facility_id=?",Boolean.class,id));
        insert(id,"owner-A");assertEquals(3,generation(id));
        tx.execute(s->{jdbc.update("UPDATE "+BASE+" SET id=? WHERE id=?",renamed,id);return null;});
        assertEquals(4,generation(id));assertEquals(1,generation(renamed));
        tx.execute(s->{write(renamed,"owner-B");s.setRollbackOnly();return null;});
        assertEquals(1,generation(renamed));assertEquals(renamed,evidence(renamed).facilityId());
    }
    @Test void relevantAbaCannotRestoreRetainedWitness() {
        String id=fixture("owner-A");long before=evidence(id).generation();
        tx.execute(s->{write(id,"owner-B");write(id,"owner-A");return null;});
        assertEquals(before+2,evidence(id).generation());
    }
    @Test void missingNonliveCorruptAndExhaustedWitnessesFailClosed() {
        String missing=fixture("owner-A");admin.update("DELETE FROM "+GEN+" WHERE facility_id=?",missing);
        assertThrows(IllegalStateException.class,()->evidence(missing));
        deniedSql("UPDATE "+BASE+" SET owner_party_id='owner-A' WHERE id='"+missing+"'","23514");
        String nonlive=fixture("owner-A");admin.update("UPDATE "+GEN+" SET present=false WHERE facility_id=?",nonlive);
        assertThrows(IllegalStateException.class,()->evidence(nonlive));
        String max=fixture("owner-A");admin.update("UPDATE "+GEN+" SET generation=9223372036854775807 WHERE facility_id=?",max);
        deniedSql("UPDATE "+BASE+" SET owner_party_id='owner-B' WHERE id='"+max+"'","23514");
        assertEquals(Long.MAX_VALUE,generation(max));
        assertThrows(IllegalStateException.class,()->evidence("j-missing"));
    }
    @Test void truncateCascadeAndWitnessForgeryAreRejected() {
        fixture("owner-A");deniedSql("TRUNCATE "+BASE+" CASCADE","42501");
        var rootFailure=assertThrows(DataAccessException.class,()->admin.execute("TRUNCATE "+BASE+" CASCADE"));
        assertEquals("23514",((SQLException)rootFailure.getMostSpecificCause()).getSQLState());
        deniedSql("UPDATE "+GEN+" SET generation=1","42501");
        deniedSql("DELETE FROM "+GEN,"42501");deniedSql("TRUNCATE "+GEN,"42501");
        deniedSql("SELECT public.hidra_p25_facility_owner_write()","42501");
        var failure=assertThrows(DataAccessException.class,()->admin.execute("TRUNCATE "+GEN+" CASCADE"));
        assertEquals("23514",((SQLException)failure.getMostSpecificCause()).getSQLState());
    }
    @Test void transactionContextAndConsumedOrForeignTicketsFailClosed() {
        String id=fixture("owner-A");
        assertThrows(RuntimeException.class,()->owner.acquire(id));
        var readOnly=new TransactionTemplate(tx.getTransactionManager());readOnly.setReadOnly(true);
        assertThrows(RuntimeException.class,()->readOnly.execute(s->owner.acquire(id)));
        var repeatable=new TransactionTemplate(tx.getTransactionManager());repeatable.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        assertThrows(RuntimeException.class,()->repeatable.execute(s->owner.acquire(id)));
        var expired=tx.execute(s->owner.acquire(id));
        assertThrows(RuntimeException.class,()->tx.execute(s->owner.read(expired)));
        assertThrows(RuntimeException.class,()->tx.execute(s->{var a=owner.acquire(id);owner.read(a);return owner.read(a);}));
        assertThrows(RuntimeException.class,()->tx.execute(s->owner.read(new StationProtectedFacilityOwnershipContract.Admission(){})));
        var foreign=new JdbcStationProtectedFacilityOwnershipAdapter(jdbc);
        assertThrows(RuntimeException.class,()->tx.execute(s->foreign.read(owner.acquire(id))));
    }
    @Test void suspensionAndCrossThreadCannotReuseAdmission() throws Exception {
        String id=fixture("owner-A");
        assertThrows(RuntimeException.class,()->tx.execute(s->{
            var admission=owner.acquire(id);
            var nested=new TransactionTemplate(tx.getTransactionManager());nested.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            nested.execute(n->null);return owner.read(admission);
        }));
        var executor=Executors.newSingleThreadExecutor();
        try {tx.execute(s->{
            var admission=owner.acquire(id);
            try {assertTrue(executor.submit(()->{assertThrows(RuntimeException.class,()->owner.read(admission));return true;}).get(5,TimeUnit.SECONDS));}
            catch(Exception fault) {throw new AssertionError(fault);}
            return owner.read(admission);
        });} finally {executor.shutdownNow();assertTrue(executor.awaitTermination(5,TimeUnit.SECONDS));}
    }
    @Test void swallowedFailureCannotCommitPartialWork() {
        String id=fixture("owner-A");long before=generation(id);
        assertThrows(UnexpectedRollbackException.class,()->tx.execute(s->{
            write(id,"owner-A");try {owner.read(new StationProtectedFacilityOwnershipContract.Admission(){});} catch(IllegalStateException expected){}
            return null;
        }));assertEquals(before,generation(id));
    }
    @Test void writerFirstMakesAdmissionFailNowaitForExistingAndAbsentRows() throws Exception {
        String id=fixture("owner-A");
        try(Connection writer=runtime.getConnection()) {
            writer.setAutoCommit(false);
            try(var st=writer.prepareStatement("UPDATE "+BASE+" SET owner_party_id=? WHERE id=?")) {st.setString(1,"owner-A");st.setString(2,id);st.executeUpdate();}
            for(String target:new String[]{id,"j-phantom"}) {
                var error=assertThrows(DataAccessException.class,()->tx.execute(s->owner.acquire(target)));
                assertEquals("55P03",((SQLException)error.getMostSpecificCause()).getSQLState());
            }
            writer.rollback();
        }assertEquals(1,evidence(id).generation());
    }
    @Test void admissionRetainsRelationFencesAndExcludesWritersUntilCompletion() throws Exception {
        String id=fixture("owner-A");var worker=Executors.newSingleThreadExecutor();var started=new CountDownLatch(1);
        try {tx.execute(s->{
            var admission=owner.acquire(id);
            var blocked=worker.submit(()->{try(Connection c=runtime.getConnection();var st=c.createStatement()) {
                st.execute("SET lock_timeout='600ms'");started.countDown();
                try {st.executeUpdate("UPDATE "+BASE+" SET owner_party_id='owner-A' WHERE id='"+id+"'");return "unexpected-write";}
                catch(SQLException conflict) {return conflict.getSQLState();}
            }});
            try {assertTrue(started.await(3,TimeUnit.SECONDS));assertEquals("55P03",blocked.get(5,TimeUnit.SECONDS));}
            catch(Exception fault) {throw new AssertionError(fault);}
            assertEquals(0,jdbc.queryForObject("SELECT count(*) FROM pg_locks WHERE pid=pg_backend_pid() AND locktype='tuple'",Integer.class));
            return owner.read(admission);
        });} finally {worker.shutdownNow();assertTrue(worker.awaitTermination(5,TimeUnit.SECONDS));}
        tx.execute(s->{write(id,"owner-A");return null;});assertEquals(2,generation(id));
    }
    @Test void inheritedMutationAndSchemaCreatePrivilegeInvalidateAdmission() {
        String id=fixture("owner-A");
        admin.execute("GRANT CREATE ON SCHEMA public TO "+ROLE);
        try {assertThrows(IllegalStateException.class,()->evidence(id));} finally {admin.execute("REVOKE CREATE ON SCHEMA public FROM "+ROLE);}
        String member="j_topology_writer";admin.execute("CREATE ROLE "+member);
        admin.execute("GRANT UPDATE(generation) ON "+GEN+" TO "+member);admin.execute("GRANT "+member+" TO "+ROLE);
        try {assertThrows(IllegalStateException.class,()->evidence(id));} finally {admin.execute("REVOKE "+member+" FROM "+ROLE);}
        assertEquals(id,evidence(id).facilityId());
    }
    @Test void disabledAlteredTriggersFunctionsAndRlsInvalidateAdmission() {
        String id=fixture("owner-A");
        admin.execute("ALTER TABLE "+BASE+" DISABLE TRIGGER trg_p25_facility_owner_write");
        try {assertThrows(IllegalStateException.class,()->evidence(id));} finally {admin.execute("ALTER TABLE "+BASE+" ENABLE TRIGGER trg_p25_facility_owner_write");}
        String definition=admin.queryForObject("SELECT pg_get_functiondef('public.hidra_p25_facility_owner_write()'::regprocedure)",String.class);
        admin.execute("CREATE OR REPLACE FUNCTION public.hidra_p25_facility_owner_write() RETURNS trigger LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $$ BEGIN RETURN NULL; END; $$");
        try {assertThrows(IllegalStateException.class,()->evidence(id));} finally {admin.execute(definition);}
        admin.execute("ALTER TABLE "+BASE+" ENABLE ROW LEVEL SECURITY");
        try {assertThrows(IllegalStateException.class,()->evidence(id));} finally {admin.execute("ALTER TABLE "+BASE+" DISABLE ROW LEVEL SECURITY");}
        assertEquals(id,evidence(id).facilityId());
    }
    @Test void elevatedOwnerAndReplicaModeAreRejected() {
        String id=fixture("owner-A");
        var elevated=new JdbcStationProtectedFacilityOwnershipAdapter(admin);var adminManager=new DataSourceTransactionManager(admin.getDataSource());
        assertThrows(IllegalStateException.class,()->new TransactionTemplate(adminManager).execute(s->elevated.acquire(id)));
        admin.execute("GRANT SET ON PARAMETER session_replication_role TO "+ROLE);
        try {assertThrows(RuntimeException.class,()->tx.execute(s->{jdbc.execute("SET LOCAL session_replication_role=replica");return owner.acquire(id);}));}
        finally {admin.execute("REVOKE SET ON PARAMETER session_replication_role FROM "+ROLE);}
    }
    @Test void migrationRefusesNonemptyMasterAndRegistryHasExactPositiveConstraint() throws Exception {
        String id=fixture("owner-A");
        String sql=Files.readString(Path.of("src/main/resources/db/migration/V20261018_002__p25_topology_facility_owner_generations.sql"));
        String check=sql.substring(sql.indexOf("DO $$"),sql.indexOf("END; $$;")+8);
        var failure=assertThrows(DataAccessException.class,()->admin.execute(check));
        assertEquals("23514",((SQLException)failure.getMostSpecificCause()).getSQLState());
        assertThrows(DataAccessException.class,()->admin.update("UPDATE "+GEN+" SET generation=0 WHERE facility_id=?",id));
        assertEquals(1,evidence(id).generation());
    }

    @Test void actualJpaSaveAndFlushBumpsWitnessAndRollbackRestoresIt() {
        String id=fixture("owner-A");
        var configuration=new org.hibernate.cfg.Configuration().addAnnotatedClass(dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity.class);
        configuration.getProperties().put("hibernate.connection.datasource",runtime);
        configuration.setProperty("hibernate.hbm2ddl.auto","validate");
        try(var factory=configuration.buildSessionFactory()) {
            for(boolean commit:new boolean[]{false,true}) {
                var em=factory.createEntityManager();
                try {
                    em.getTransaction().begin();
                    var repository=new org.springframework.data.jpa.repository.support.SimpleJpaRepository<dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity,String>(dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity.class,em);
                    var now=java.time.Instant.now();
                    repository.saveAndFlush(new FacilityJpaEntity(id,id,null,null,null,type,FacilityKind.STATION,"owner-B",null,null,null,null,null,FacilityStatus.ACTIVE,null,null,now,now));
                    if(commit) em.getTransaction().commit();else em.getTransaction().rollback();
                } finally {if(em.getTransaction().isActive())em.getTransaction().rollback();em.close();}
                assertEquals(commit?2:1,generation(id));
            }
        }
    }
    @Test void fkChildInsertAndPlanningStyleRowShareRemainCompatible() throws Exception {
        String id=fixture("owner-A");String equipmentType="jeq-"+UUID.randomUUID();
        admin.update("INSERT INTO public.hidra_topology_equipment_type(id,code,name,status,created_at,updated_at) VALUES(?,?, 'Synthetic','ACTIVE',now(),now())",equipmentType,equipmentType);
        admin.execute("GRANT INSERT ON public.hidra_topology_equipment TO "+ROLE);
        var worker=Executors.newSingleThreadExecutor();
        try {tx.execute(s->{
            var admission=owner.acquire(id);var result=worker.submit(()->{
                try(var connection=runtime.getConnection();var statement=connection.createStatement()) {
                    connection.setAutoCommit(false);statement.execute("SET LOCAL lock_timeout='1s'");
                    try(var rows=statement.executeQuery("SELECT id FROM "+BASE+" WHERE id='"+id+"' FOR SHARE")) {assertTrue(rows.next());}
                    String child="jchild-"+UUID.randomUUID();statement.executeUpdate("INSERT INTO public.hidra_topology_equipment(id,code,facility_id,equipment_type_id,status,created_at,updated_at) VALUES('"+child+"','"+child+"','"+id+"','"+equipmentType+"','ACTIVE',now(),now())");
                    connection.commit();return true;
                }
            });
            try {assertTrue(result.get(5,TimeUnit.SECONDS));} catch(Exception fault) {throw new AssertionError(fault);}
            assertEquals(1,owner.read(admission).generation());return null;
        });} finally {worker.shutdownNow();assertTrue(worker.awaitTermination(5,TimeUnit.SECONDS));}
    }
    @Test void lostFenceAfterSavepointRollbackInvalidatesTicket() {
        String id=fixture("owner-A");
        assertThrows(RuntimeException.class,()->tx.execute(s->{
            Object savepoint=s.createSavepoint();var admission=owner.acquire(id);
            s.rollbackToSavepoint(savepoint);return owner.read(admission);
        }));assertEquals(1,generation(id));
    }
    @Test void inheritanceAndUnknownOwnerStateCannotProduceEvidence() {
        String id=fixture("owner-A");
        admin.execute("CREATE TABLE public.j_inherited_topology () INHERITS ("+BASE+")");
        try {assertThrows(IllegalStateException.class,()->evidence(id));}
        finally {admin.execute("DROP TABLE public.j_inherited_topology");}
        admin.update("UPDATE "+BASE+" SET status='UNKNOWN' WHERE id=?",id);
        assertThrows(IllegalStateException.class,()->evidence(id));
    }
    @Test void multirowFailureRollsBackEveryRootAndGeneration() {
        String a=fixture("owner-A"),b=fixture("owner-A");
        admin.update("UPDATE "+GEN+" SET generation=9223372036854775807 WHERE facility_id=?",b);
        var error=assertThrows(DataAccessException.class,()->tx.execute(s->{
            jdbc.update("UPDATE "+BASE+" SET owner_party_id=? WHERE id IN (?,?)","owner-B",a,b);return null;
        }));assertEquals("23514",((SQLException)error.getMostSpecificCause()).getSQLState());
        assertEquals(1,generation(a));assertEquals(Long.MAX_VALUE,generation(b));
        assertEquals("owner-A",admin.queryForObject("SELECT owner_party_id FROM "+BASE+" WHERE id=?",String.class,a));
    }

    @Test void ownerSnapshotCannotReplaceMissingLiveOwner() {
        String id=fixture("owner-A");admin.update("UPDATE "+BASE+" SET owner_party_id=NULL,owner_party_code_snapshot='owner-A',owner_party_name_snapshot='owner-A' WHERE id=?",id);
        assertThrows(IllegalStateException.class,()->evidence(id));
    }
    @Test void earlierSearchPathCannotShadowCatalogAuthorityChecks() {
        String id=fixture("owner-A");
        admin.execute("CREATE SCHEMA j_shadow_topology AUTHORIZATION "+ROLE);
        assertThrows(IllegalStateException.class,()->tx.execute(s->{
            jdbc.execute("SET LOCAL search_path=j_shadow_topology,pg_catalog");return owner.acquire(id);
        }));
        assertEquals(1,evidence(id).generation());
    }
}
