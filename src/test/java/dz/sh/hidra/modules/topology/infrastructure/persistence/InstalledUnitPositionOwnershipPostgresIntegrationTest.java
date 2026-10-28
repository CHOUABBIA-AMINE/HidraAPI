/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitPositionOwnershipPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence
 *
 * @Description : Verifies current position ownership and fixed lock privilege isolation in PostgreSQL16.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence;

import dz.sh.hidra.modules.topology.application.port.out.InstalledUnitPositionOwnershipPort;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcInstalledUnitPositionOwnershipAdapter;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.sql.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.*;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker=true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InstalledUnitPositionOwnershipPostgresIntegrationTest {
    private static final String EQUIPMENT="public.hidra_topology_equipment",FACILITY="public.hidra_topology_facility",
        GENERATION="public.hidra_topology_facility_owner_generation";
    private static final String ROLE="installed_position_reader",PASSWORD="synthetic-position-reader",
        LOCK_OWNER="installed_position_lock_owner",OTHER="installed_position_unreviewed",
        FUNCTION="public.hidra_topology_lock_installed_unit_position_ownership()";
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16")
        .withCommand("postgres","-c","max_locks_per_transaction=256");
    @Configuration @EnableTransactionManagement static class Config {}
    private AnnotationConfigApplicationContext context;
    private JdbcTemplate admin,jdbc;
    private DriverManagerDataSource runtime;
    private TransactionTemplate tx;
    private InstalledUnitPositionOwnershipPort owner;
    private String facilityType,equipmentType;
    private boolean revokedPublicAtMigration;
    private static String id(){return "syn-"+UUID.randomUUID();}
    private record Fixture(String position,String facility,String company) {}
    @BeforeAll void migrateAndConfigureActualRestrictedOwner() {
        var migration=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
        var flyway=Flyway.configure().dataSource(migration).load();flyway.migrate();flyway.validate();assertEquals(0,flyway.info().pending().length);
        admin=new JdbcTemplate(migration);
        revokedPublicAtMigration=Boolean.TRUE.equals(admin.queryForObject("SELECT NOT EXISTS(SELECT 1 FROM pg_catalog.pg_proc p,LATERAL pg_catalog.aclexplode(COALESCE(p.proacl,pg_catalog.acldefault('f',p.proowner))) a WHERE p.oid=?::regprocedure AND a.grantee=0 AND a.privilege_type='EXECUTE')",Boolean.class,FUNCTION));
        admin.execute("CREATE ROLE "+ROLE+" LOGIN PASSWORD '"+PASSWORD+"'");
        admin.execute("GRANT USAGE ON SCHEMA public TO "+ROLE);
        admin.execute("GRANT SELECT ON "+EQUIPMENT+","+FACILITY+","+GENERATION+" TO "+ROLE);
        admin.execute("CREATE ROLE "+LOCK_OWNER+" NOLOGIN NOINHERIT NOSUPERUSER NOCREATEDB NOCREATEROLE NOREPLICATION NOBYPASSRLS");
        admin.execute("CREATE ROLE "+OTHER+" NOLOGIN");
        admin.execute("GRANT USAGE,CREATE ON SCHEMA public TO "+LOCK_OWNER);
        admin.execute("GRANT UPDATE ON "+EQUIPMENT+","+FACILITY+" TO "+LOCK_OWNER);
        admin.execute("ALTER FUNCTION "+FUNCTION+" OWNER TO "+LOCK_OWNER);
        admin.execute("REVOKE CREATE ON SCHEMA public FROM "+LOCK_OWNER);
        admin.execute("GRANT EXECUTE ON FUNCTION "+FUNCTION+" TO "+ROLE);
        runtime=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),ROLE,PASSWORD);jdbc=new JdbcTemplate(runtime);
        var manager=new DataSourceTransactionManager(runtime);tx=new TransactionTemplate(manager);tx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        context=new AnnotationConfigApplicationContext();context.register(Config.class);
        context.registerBean(DataSource.class,()->runtime);context.registerBean(JdbcTemplate.class,()->jdbc);
        context.registerBean(PlatformTransactionManager.class,()->manager);context.registerBean(JdbcInstalledUnitPositionOwnershipAdapter.class);
        context.refresh();owner=context.getBean(InstalledUnitPositionOwnershipPort.class);
        facilityType=id();equipmentType=id();
        admin.update("INSERT INTO public.hidra_topology_facility_type(id,code,name,facility_kind,status,created_at,updated_at) VALUES(?,?,'Synthetic','STATION','ACTIVE',now(),now())",facilityType,facilityType);
        admin.update("INSERT INTO public.hidra_topology_equipment_type(id,code,name,status,created_at,updated_at) VALUES(?,?,'Synthetic','ACTIVE',now(),now())",equipmentType,equipmentType);
    }
    @AfterAll void close(){if(context!=null)context.close();}
    private Fixture fixture(){
        String p=id(),f=id(),company=id();
        admin.update("INSERT INTO "+FACILITY+"(id,code,facility_type_id,facility_kind,owner_party_id,status,created_at,updated_at) VALUES(?,?,?,'STATION',?,'ACTIVE',now(),now())",f,f,facilityType,company);
        admin.update("INSERT INTO "+EQUIPMENT+"(id,code,facility_id,equipment_type_id,status,created_at,updated_at) VALUES(?,?,?,?,'ACTIVE',now(),now())",p,p,f,equipmentType);
        return new Fixture(p,f,company);
    }
    private InstalledUnitPositionOwnershipPort.Contribution evidence(String id){return tx.execute(s->{var a=owner.acquire(id);var c=owner.read(a);owner.requireCurrent(a);return c;});}
    private void unavailable(String id){assertThrows(RuntimeException.class,()->evidence(id));}
    // A separate synthetic schema-owner connection permits deliberate same-transaction tampering.
    // Every observation is made after switching that connection to the real SELECT-only reader.
    private TransactionTemplate elevatedFixtureTransaction(){
        var t=new TransactionTemplate(new DataSourceTransactionManager(admin.getDataSource()));t.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);return t;
    }
    @Test void directPositionOwnerEvidenceHasActualCoordinatesAndThreeFences(){
        var f=fixture();var c=evidence(f.position());assertEquals(f.facility(),c.facilityId());assertEquals(f.company(),c.companyPartyId());assertEquals(equipmentType,c.equipmentTypeId());assertEquals("ACTIVE",c.equipmentStatus());assertEquals("STATION",c.facilityKind());assertTrue(c.facilityOwnerGeneration()>0);assertEquals(ROLE,c.databaseRole());
        tx.execute(s->{var a=owner.acquire(f.position());var result=owner.read(a);assertEquals(jdbc.queryForObject("SELECT pg_backend_pid()",Integer.class),result.backendPid());assertEquals(jdbc.queryForObject("SELECT txid_current()",Long.class),result.transactionId());
            var fences=owner.finalFences(a);assertEquals(3,fences.size());assertEquals(Set.of(EQUIPMENT,FACILITY,GENERATION),fences.stream().map(ProtectedRelationFence::relation).collect(java.util.stream.Collectors.toSet()));assertEquals(2,fences.stream().filter(x->x.mode()==ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE).count());owner.requireCurrent(a);return null;});
    }
    @Test void nullMissingAndTombstonedPlacementNeverUsesAnInventedGeneration(){
        unavailable(id());var f=fixture();admin.update("UPDATE "+EQUIPMENT+" SET facility_id=NULL WHERE id=?",f.position());unavailable(f.position());
        admin.update("UPDATE "+EQUIPMENT+" SET facility_id=? WHERE id=?",f.facility(),f.position());
        admin.update("UPDATE "+GENERATION+" SET present=false WHERE facility_id=?",f.facility());unavailable(f.position());
        admin.update("UPDATE "+GENERATION+" SET present=true WHERE facility_id=?",f.facility());assertEquals(f.company(),evidence(f.position()).companyPartyId());
    }
    @Test void statusClassificationsAreFaithfulWithoutEligibilityPolicy(){
        var f=fixture();admin.update("UPDATE "+EQUIPMENT+" SET status='OUT_OF_SERVICE' WHERE id=?",f.position());admin.update("UPDATE "+FACILITY+" SET status='SUSPENDED' WHERE id=?",f.facility());
        var c=evidence(f.position());assertEquals("OUT_OF_SERVICE",c.equipmentStatus());assertEquals("SUSPENDED",c.facilityStatus());
        String nextCompany=id();admin.update("UPDATE "+FACILITY+" SET owner_party_id=? WHERE id=?",nextCompany,f.facility());var next=evidence(f.position());assertEquals(nextCompany,next.companyPartyId());assertTrue(next.facilityOwnerGeneration()>c.facilityOwnerGeneration());
    }
    @Test void readerCannotWriteAnySourceTableOrMintGeneration(){
        var f=fixture();for(String table:List.of(EQUIPMENT,FACILITY,GENERATION)){
            var failure=assertThrows(DataAccessException.class,()->tx.execute(s->{jdbc.execute("UPDATE "+table+" SET "+(table.equals(GENERATION)?"generation=generation":"status=status"));return null;}));assertEquals("42501",((SQLException)failure.getMostSpecificCause()).getSQLState());
        }assertEquals(f.position(),evidence(f.position()).positionId());
    }
    @Test void noPublicExecutionAndRevokedReaderExecuteFailBeforeObservation(){
        assertTrue(revokedPublicAtMigration);var f=fixture();admin.execute("REVOKE EXECUTE ON FUNCTION "+FUNCTION+" FROM "+ROLE);
        try{unavailable(f.position());var error=assertThrows(DataAccessException.class,()->tx.execute(s->{jdbc.execute("SELECT "+FUNCTION);return null;}));assertEquals("42501",((SQLException)error.getMostSpecificCause()).getSQLState());}
        finally{admin.execute("GRANT EXECUTE ON FUNCTION "+FUNCTION+" TO "+ROLE);}assertEquals(f.position(),evidence(f.position()).positionId());
    }
    @Test void helperBodyInvokerAndUnsafeSearchPathAreRejected(){
        var f=fixture();String definition=admin.queryForObject("SELECT pg_get_functiondef(?::regprocedure)",String.class,FUNCTION);
        admin.execute("CREATE OR REPLACE FUNCTION "+FUNCTION+" RETURNS void LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $$ BEGIN PERFORM 1; END; $$");
        try{unavailable(f.position());}finally{admin.execute(definition);}
        admin.execute("ALTER FUNCTION "+FUNCTION+" SECURITY INVOKER");try{unavailable(f.position());}finally{admin.execute("ALTER FUNCTION "+FUNCTION+" SECURITY DEFINER");}
        admin.execute("ALTER FUNCTION "+FUNCTION+" SET search_path=public,pg_catalog");try{unavailable(f.position());}finally{admin.execute("ALTER FUNCTION "+FUNCTION+" SET search_path=pg_catalog,pg_temp");}
        assertEquals(f.position(),evidence(f.position()).positionId());
    }
    @Test void publicAndExtraExecuteAclOrOwnerMembershipCannotAdmit(){
        var f=fixture();for(String grantee:List.of("PUBLIC",OTHER)){
            admin.execute("GRANT EXECUTE ON FUNCTION "+FUNCTION+" TO "+grantee);try{unavailable(f.position());}finally{admin.execute("REVOKE EXECUTE ON FUNCTION "+FUNCTION+" FROM "+grantee);}
        }
        admin.execute("GRANT "+LOCK_OWNER+" TO "+ROLE);try{unavailable(f.position());}finally{admin.execute("REVOKE "+LOCK_OWNER+" FROM "+ROLE);}
        admin.execute("GRANT "+LOCK_OWNER+" TO "+OTHER);try{unavailable(f.position());}finally{admin.execute("REVOKE "+LOCK_OWNER+" FROM "+OTHER);}
        assertEquals(f.position(),evidence(f.position()).positionId());
    }
    @Test void helperOwnerRequiresOnlyNonGrantableFixedTableLockPrivileges(){
        var f=fixture();admin.execute("REVOKE UPDATE ON "+EQUIPMENT+" FROM "+LOCK_OWNER);try{unavailable(f.position());}finally{admin.execute("GRANT UPDATE ON "+EQUIPMENT+" TO "+LOCK_OWNER);}
        admin.execute("GRANT UPDATE ON "+EQUIPMENT+" TO "+LOCK_OWNER+" WITH GRANT OPTION");try{unavailable(f.position());}finally{admin.execute("REVOKE GRANT OPTION FOR UPDATE ON "+EQUIPMENT+" FROM "+LOCK_OWNER);}
        admin.execute("GRANT UPDATE(status) ON "+EQUIPMENT+" TO "+LOCK_OWNER+" WITH GRANT OPTION");try{unavailable(f.position());}finally{admin.execute("REVOKE GRANT OPTION FOR UPDATE(status) ON "+EQUIPMENT+" FROM "+LOCK_OWNER);}
        admin.execute("GRANT SELECT ON "+FACILITY+" TO "+LOCK_OWNER);try{unavailable(f.position());}finally{admin.execute("REVOKE SELECT ON "+FACILITY+" FROM "+LOCK_OWNER);}
        assertEquals(f.position(),evidence(f.position()).positionId());
    }
    @Test void tableAndColumnReaderWritesAndRlsFailClosed(){
        var f=fixture();admin.execute("GRANT UPDATE(status) ON "+EQUIPMENT+" TO "+ROLE);try{unavailable(f.position());}finally{admin.execute("REVOKE UPDATE(status) ON "+EQUIPMENT+" FROM "+ROLE);}
        admin.execute("GRANT UPDATE ON "+FACILITY+" TO "+ROLE);try{unavailable(f.position());}finally{admin.execute("REVOKE UPDATE ON "+FACILITY+" FROM "+ROLE);}
        admin.execute("ALTER TABLE "+EQUIPMENT+" ENABLE ROW LEVEL SECURITY");try{unavailable(f.position());}finally{admin.execute("ALTER TABLE "+EQUIPMENT+" DISABLE ROW LEVEL SECURITY");}
        admin.execute("GRANT CREATE ON SCHEMA public TO "+ROLE);try{unavailable(f.position());}finally{admin.execute("REVOKE CREATE ON SCHEMA public FROM "+ROLE);}
        assertEquals(f.position(),evidence(f.position()).positionId());
    }
    @Test void writerFirstOnEitherSourceRejectsWithoutWaiting() throws Exception {
        var f=fixture();for(String table:List.of(EQUIPMENT,FACILITY))try(Connection writer=admin.getDataSource().getConnection()){
            writer.setAutoCommit(false);try(var statement=writer.createStatement()){statement.execute("UPDATE "+table+" SET status=status");}
            var error=assertThrows(DataAccessException.class,()->tx.execute(s->owner.acquire(f.position())));assertEquals("55P03",((SQLException)error.getMostSpecificCause()).getSQLState());writer.rollback();
        }assertEquals(f.position(),evidence(f.position()).positionId());
    }
    @Test void bothOwnerLocksStayInReaderTransactionAndReleaseAtCommit() throws Exception {
        var f=fixture();for(String table:List.of(EQUIPMENT,FACILITY))try(Connection writer=admin.getDataSource().getConnection()){
            writer.setAutoCommit(false);try(var statement=writer.createStatement()){statement.execute("SET lock_timeout='100ms'");}
            tx.execute(s->{var a=owner.acquire(f.position());var c=owner.read(a);assertEquals(ROLE,jdbc.queryForObject("SELECT current_user",String.class));assertEquals(c.transactionId(),jdbc.queryForObject("SELECT txid_current()",Long.class).longValue());
                var error=assertThrows(SQLException.class,()->{try(var statement=writer.createStatement()){statement.execute("UPDATE "+table+" SET status=status");}});assertEquals("55P03",error.getSQLState());owner.requireCurrent(a);return null;});
            writer.rollback();try(var statement=writer.createStatement()){assertTrue(statement.executeUpdate("UPDATE "+table+" SET status=status")>0);}writer.rollback();
        }
    }
    @Test void forgedReplayForeignAndCompletedTicketsAreRejected(){
        var f=fixture();assertThrows(RuntimeException.class,()->owner.acquire(f.position()));var expired=tx.execute(s->owner.acquire(f.position()));assertThrows(RuntimeException.class,()->tx.execute(s->owner.read(expired)));
        assertThrows(RuntimeException.class,()->tx.execute(s->{var a=owner.acquire(f.position());owner.read(a);return owner.read(a);}));
        assertThrows(RuntimeException.class,()->tx.execute(s->owner.read(new InstalledUnitPositionOwnershipPort.Admission(){})));
        var foreign=new JdbcInstalledUnitPositionOwnershipAdapter(jdbc);assertThrows(RuntimeException.class,()->tx.execute(s->foreign.read(owner.acquire(f.position()))));
        unavailable(" "+f.position());
    }
    @Test void readOnlyAndRepeatableReadTransactionsCannotAcquire(){
        var f=fixture();var ro=new TransactionTemplate(tx.getTransactionManager());ro.setReadOnly(true);assertThrows(RuntimeException.class,()->ro.execute(s->owner.acquire(f.position())));
        var rr=new TransactionTemplate(tx.getTransactionManager());rr.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);assertThrows(RuntimeException.class,()->rr.execute(s->owner.acquire(f.position())));
    }
    @Test void crossThreadFailurePoisonsOriginalPhysicalTransaction() throws Exception {
        var f=fixture();var worker=Executors.newSingleThreadExecutor();try{
            assertThrows(RuntimeException.class,()->tx.execute(s->{var a=owner.acquire(f.position());owner.read(a);
                try{worker.submit(()->assertThrows(RuntimeException.class,()->owner.requireCurrent(a))).get(5,TimeUnit.SECONDS);}catch(Exception e){throw new AssertionError(e);}return null;}));
        }finally{worker.shutdownNow();assertTrue(worker.awaitTermination(5,TimeUnit.SECONDS));}
    }
    @Test void suspendedOrSavepointRolledBackAdmissionCannotResume(){
        var f=fixture();assertThrows(RuntimeException.class,()->tx.execute(s->{var a=owner.acquire(f.position());owner.read(a);var other=new TransactionTemplate(tx.getTransactionManager());other.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);other.execute(t->null);owner.requireCurrent(a);return null;}));
        assertThrows(RuntimeException.class,()->tx.execute(s->{Object savepoint=s.createSavepoint();var a=owner.acquire(f.position());owner.read(a);s.rollbackToSavepoint(savepoint);owner.requireCurrent(a);return null;}));
    }
    @Test void swallowedMissingOwnerFailureStillVetoesCommit(){
        assertThrows(RuntimeException.class,()->tx.execute(s->{try{owner.read(owner.acquire(id()));}catch(RuntimeException expected){}return null;}));
    }
    @Test void generationTriggerCatalogTamperingIsNeverTrusted(){
        var f=fixture();admin.execute("ALTER TABLE "+FACILITY+" DISABLE TRIGGER trg_p25_facility_owner_write");try{unavailable(f.position());}finally{admin.execute("ALTER TABLE "+FACILITY+" ENABLE TRIGGER trg_p25_facility_owner_write");}
        assertEquals(f.position(),evidence(f.position()).positionId());
    }
    @Test void helperSuperuserOwnerIsRejectedUntilExplicitProvisioningRestored(){
        var f=fixture();admin.execute("ALTER FUNCTION "+FUNCTION+" OWNER TO "+POSTGRES.getUsername());try{unavailable(f.position());}finally{
            admin.execute("GRANT CREATE ON SCHEMA public TO "+LOCK_OWNER);admin.execute("ALTER FUNCTION "+FUNCTION+" OWNER TO "+LOCK_OWNER);admin.execute("REVOKE CREATE ON SCHEMA public FROM "+LOCK_OWNER);admin.execute("GRANT EXECUTE ON FUNCTION "+FUNCTION+" TO "+ROLE);
        }assertEquals(f.position(),evidence(f.position()).positionId());
    }
    @Test void sameTransactionPositionAndFacilityImageChangesCannotSurviveRecheck(){
        var f=fixture();var raw=new JdbcInstalledUnitPositionOwnershipAdapter(admin);
        for(String table:List.of(EQUIPMENT,FACILITY))assertThrows(RuntimeException.class,()->elevatedFixtureTransaction().execute(status->{
            admin.queryForObject("SELECT set_config('role',?,true)",String.class,ROLE);
            var a=raw.acquire(f.position());raw.read(a);
            admin.queryForObject("SELECT set_config('role',?,true)",String.class,POSTGRES.getUsername());
            admin.update("UPDATE "+table+" SET name='tampered' WHERE id=?",table.equals(EQUIPMENT)?f.position():f.facility());
            admin.queryForObject("SELECT set_config('role',?,true)",String.class,ROLE);
            raw.requireCurrent(a);return null;
        }));
        assertEquals(f.company(),evidence(f.position()).companyPartyId());
    }
    @Test void roleSwapAndOwnTransactionCompanyTransferInvalidateBoundEvidence(){
        var f=fixture();var raw=new JdbcInstalledUnitPositionOwnershipAdapter(admin);
        assertThrows(RuntimeException.class,()->elevatedFixtureTransaction().execute(status->{
            admin.queryForObject("SELECT set_config('role',?,true)",String.class,ROLE);var a=raw.acquire(f.position());raw.read(a);
            admin.queryForObject("SELECT set_config('role',?,true)",String.class,OTHER);raw.requireCurrent(a);return null;
        }));
        assertThrows(RuntimeException.class,()->elevatedFixtureTransaction().execute(status->{
            admin.queryForObject("SELECT set_config('role',?,true)",String.class,ROLE);var a=raw.acquire(f.position());raw.read(a);
            admin.queryForObject("SELECT set_config('role',?,true)",String.class,POSTGRES.getUsername());admin.update("UPDATE "+FACILITY+" SET owner_party_id=? WHERE id=?",id(),f.facility());
            admin.queryForObject("SELECT set_config('role',?,true)",String.class,ROLE);raw.requireCurrent(a);return null;
        }));
        assertEquals(f.company(),evidence(f.position()).companyPartyId());
    }
    @Test void missingOwnerMalformedStatusAndRemovedPositionAreUnavailable(){
        var f=fixture();admin.update("UPDATE "+FACILITY+" SET owner_party_id=NULL WHERE id=?",f.facility());unavailable(f.position());
        admin.update("UPDATE "+FACILITY+" SET owner_party_id=? WHERE id=?",f.company(),f.facility());
        admin.update("UPDATE "+EQUIPMENT+" SET status='UNRECOGNIZED' WHERE id=?",f.position());unavailable(f.position());
        admin.update("UPDATE "+EQUIPMENT+" SET status='ACTIVE' WHERE id=?",f.position());admin.update("DELETE FROM "+EQUIPMENT+" WHERE id=?",f.position());unavailable(f.position());
    }
    @Test void viewReplacementAndHelperSchemaCreatePrivilegeAreRejected(){
        var f=fixture();admin.execute("GRANT CREATE ON SCHEMA public TO "+LOCK_OWNER);try{unavailable(f.position());}finally{admin.execute("REVOKE CREATE ON SCHEMA public FROM "+LOCK_OWNER);}
        admin.execute("ALTER TABLE "+EQUIPMENT+" RENAME TO installed_position_shadow");
        try{admin.execute("CREATE VIEW "+EQUIPMENT+" AS SELECT * FROM public.installed_position_shadow");admin.execute("GRANT SELECT ON "+EQUIPMENT+" TO "+ROLE);unavailable(f.position());}
        finally{admin.execute("DROP VIEW IF EXISTS "+EQUIPMENT);admin.execute("ALTER TABLE public.installed_position_shadow RENAME TO hidra_topology_equipment");}
        assertEquals(f.position(),evidence(f.position()).positionId());
    }
    @Test void missingLiveGenerationAndNonDirectNodePlacementAreNotInferred(){
        var f=fixture();admin.update("DELETE FROM "+GENERATION+" WHERE facility_id=?",f.facility());unavailable(f.position());
        var other=fixture();String node=id();
        admin.update("INSERT INTO public.hidra_topology_node(id,code,node_type,status,created_at,updated_at) VALUES(?,?,'JUNCTION','ACTIVE',now(),now())",node,node);
        admin.update("UPDATE "+EQUIPMENT+" SET node_id=? WHERE id=?",node,other.position());unavailable(other.position());
    }

}
