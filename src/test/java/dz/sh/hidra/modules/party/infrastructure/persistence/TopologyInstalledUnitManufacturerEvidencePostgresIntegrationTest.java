/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyInstalledUnitManufacturerEvidencePostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence
 *
 * @Description : Qualifies manufacturer evidence from a fresh Party schema and protected transaction.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence;

import dz.sh.hidra.modules.party.application.contract.topology.TopologyInstalledUnitManufacturerEvidenceContract;
import dz.sh.hidra.modules.party.infrastructure.persistence.adapter.JdbcTopologyInstalledUnitManufacturerEvidenceAdapter;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.*;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
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
class TopologyInstalledUnitManufacturerEvidencePostgresIntegrationTest {
    private static final String PARTY="public.hidra_party_party";
    private static final String PROFILE="public.hidra_party_manufacturer_profile";
    private static final String ROLE_TABLE="public.hidra_party_role";
    private static final String ASSIGNMENT="public.hidra_party_role_assignment";
    private static final String GENERATION="public.hidra_party_eligibility_generation";
    private static final String ROLE="j_manufacturer_evidence",PASSWORD="synthetic-manufacturer-evidence";
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16")
            .withCommand("postgres","-c","max_locks_per_transaction=256");

    @Configuration @EnableTransactionManagement static class Config {}
    private AnnotationConfigApplicationContext context;
    private DriverManagerDataSource runtime;
    private JdbcTemplate admin,jdbc;
    private TransactionTemplate tx;
    private TopologyInstalledUnitManufacturerEvidenceContract owner;
    private String partyType;

    private record Fixture(String party,String profile,String role,String assignment) {}
    private static String id() { return "syn-"+UUID.randomUUID(); }

    @BeforeAll void migrateAndWireActualOwnerBean() {
        var migration=new DriverManagerDataSource(
                POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
        Flyway.configure().dataSource(migration).load().migrate();
        admin=new JdbcTemplate(migration);
        admin.execute("CREATE ROLE "+ROLE+" LOGIN PASSWORD '"+PASSWORD+"'");
        admin.execute("GRANT USAGE ON SCHEMA public TO "+ROLE);
        admin.execute("GRANT SELECT,UPDATE ON "+PARTY+" TO "+ROLE);
        for(String table:new String[]{ROLE_TABLE,ASSIGNMENT,PROFILE})
            admin.execute("GRANT SELECT ON "+table+" TO "+ROLE);
        admin.execute("GRANT SELECT ON "+GENERATION+" TO "+ROLE);
        runtime=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),ROLE,PASSWORD);
        jdbc=new JdbcTemplate(runtime);
        var manager=new DataSourceTransactionManager(runtime);
        tx=new TransactionTemplate(manager);
        tx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        context=new AnnotationConfigApplicationContext();
        context.register(Config.class);
        context.registerBean(DataSource.class,()->runtime);
        context.registerBean(JdbcTemplate.class,()->jdbc);
        context.registerBean(PlatformTransactionManager.class,()->manager);
        context.registerBean(JdbcTopologyInstalledUnitManufacturerEvidenceAdapter.class);
        context.refresh();
        owner=context.getBean(TopologyInstalledUnitManufacturerEvidenceContract.class);
        partyType=id();
        admin.update("INSERT INTO public.hidra_party_type(id,code,status,created_at,updated_at)"
                +" VALUES(?,?,'ACTIVE',now(),now())",partyType,partyType);
    }

    @AfterAll void shutdown() { if(context!=null)context.close(); }

    private Fixture fixture() {
        String p=id(),pr=id(),role=id(),as=id();
        admin.update("INSERT INTO "+PARTY
                +"(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at)"
                +" VALUES(?,?,?,'Synthetic','DZ','ACTIVE',now(),now())",p,p,partyType);
        admin.update("INSERT INTO "+ROLE_TABLE
                +"(id,code,qualification_required_by_default,status,created_at,updated_at)"
                +" VALUES(?,'MANUFACTURER',true,'ACTIVE',now(),now())",role);
        admin.update("INSERT INTO "+ASSIGNMENT
                +"(id,party_id,role_id,valid_from,status,qualification_required,created_at,updated_at)"
                +" VALUES(?,?,?,?,'ACTIVE',true,now(),now())",
                as,p,role,Timestamp.from(Instant.now().minusSeconds(120)));
        admin.update("INSERT INTO "+PROFILE
                +"(id,party_id,capability_type,qualification_status,created_at,updated_at)"
                +" VALUES(?,?,'EQUIPMENT','QUALIFIED',now(),now())",pr,p);
        return new Fixture(p,pr,role,as);
    }

    private TopologyInstalledUnitManufacturerEvidenceContract.Contribution evidence(String id) {
        return tx.execute(s->owner.read(owner.acquire(id)));
    }
    private void unavailable(String id) {
        assertThrows(RuntimeException.class,()->evidence(id));
    }
    private void change(String table,String column,String value,String id) {
        admin.update("UPDATE "+table+" SET "+column+"=? WHERE id=?",value,id);
    }

    @Test void qualifiedCurrentManufacturerReturnsOnlyPartyOwnedEvidence() {
        var f=fixture();
        var result=evidence(f.party());
        assertEquals(f.party(),result.manufacturerPartyId());
        assertEquals(f.assignment(),result.roleAssignmentId());
        assertEquals("MANUFACTURER",result.roleCode());
        assertEquals("QUALIFIED",result.qualificationStatus());
        assertEquals("EQUIPMENT",result.manufacturerCapability());
        assertTrue(result.qualificationRequired());
        assertTrue(result.partyGeneration()>0);
        assertFalse(result.toString().contains(f.party()));
        tx.execute(s->{
            var ticket=owner.acquire(f.party());
            var proof=owner.read(ticket);
            assertEquals(jdbc.queryForObject("SELECT pg_catalog.pg_backend_pid()",Integer.class),
                    proof.backendPid());
            assertEquals(jdbc.queryForObject("SELECT pg_catalog.txid_current()",Long.class),
                    proof.transactionId());
            return null;
        });
    }

    @Test void missingPartyInactivePartyOrMissingProfileFailClosed() {
        unavailable(id());
        var inactive=fixture();
        change(PARTY,"status","SUSPENDED",inactive.party());
        unavailable(inactive.party());
        var absent=fixture();
        admin.update("DELETE FROM "+PROFILE+" WHERE id=?",absent.profile());
        unavailable(absent.party());
        var corrupt=fixture();
        admin.update("UPDATE "+GENERATION+" SET present=false WHERE party_id=?",corrupt.party());
        unavailable(corrupt.party());
    }

    @Test void revokedSuspendedOrNonManufacturerRolesFailClosed() {
        var f=fixture();
        for(String state:new String[]{"REVOKED","EXPIRED","SUSPENDED"}) {
            change(ASSIGNMENT,"status",state,f.assignment());
            unavailable(f.party());
        }
        change(ASSIGNMENT,"status","ACTIVE",f.assignment());
        change(ROLE_TABLE,"status","INACTIVE",f.role());
        unavailable(f.party());
        change(ROLE_TABLE,"status","ACTIVE",f.role());
        change(ROLE_TABLE,"code","SUPPLIER",f.role());
        unavailable(f.party());
    }

    @Test void manufacturerQualificationAndCapabilityDoNotImplyEquipmentKind() {
        var f=fixture();
        for(String state:new String[]{"DRAFT","REJECTED","SUSPENDED",
                                      "EXPIRED","CONDITIONALLY_QUALIFIED"}) {
            change(PROFILE,"qualification_status",state,f.profile());
            unavailable(f.party());
        }
        change(PROFILE,"qualification_status","QUALIFIED",f.profile());
        change(PROFILE,"capability_type","SPARES",f.profile());
        unavailable(f.party());
        change(PROFILE,"capability_type","INSTRUMENTATION",f.profile());
        assertEquals("INSTRUMENTATION",evidence(f.party()).manufacturerCapability());
        // No equipmentKindId is accepted, derived or certified by this contract.
    }

    @Test void effectiveDateWindowsAreHalfOpenAndReadFromDatabaseClock() {
        var f=fixture();
        admin.update("UPDATE "+ASSIGNMENT+" SET valid_from=now()+interval '1 hour' WHERE id=?",
                f.assignment());
        unavailable(f.party());
        admin.update("UPDATE "+ASSIGNMENT
                +" SET valid_from=now()-interval '1 day',valid_to=now() WHERE id=?",f.assignment());
        unavailable(f.party());
        admin.update("UPDATE "+ASSIGNMENT+" SET valid_to=now()+interval '1 day' WHERE id=?",
                f.assignment());
        assertEquals(f.party(),evidence(f.party()).manufacturerPartyId());
    }

    @Test void duplicateManufacturerProfileOrRoleIsNotSilentlySelected() {
        var f=fixture();
        String second=id();
        admin.update("INSERT INTO "+PROFILE
                +"(id,party_id,capability_type,qualification_status,created_at,updated_at)"
                +" VALUES(?,?,'EQUIPMENT','QUALIFIED',now(),now())",second,f.party());
        unavailable(f.party());
        admin.update("DELETE FROM "+PROFILE+" WHERE id=?",second);
        String extraRole=id(),assignment=id();
        admin.update("INSERT INTO "+ROLE_TABLE
                +"(id,code,qualification_required_by_default,status,created_at,updated_at)"
                +" VALUES(?,'MANUFACTURER',true,'ACTIVE',now(),now())",extraRole);
        admin.update("INSERT INTO "+ASSIGNMENT
                +"(id,party_id,role_id,valid_from,status,qualification_required,created_at,updated_at)"
                +" VALUES(?,?,?,now()-interval '1 day','ACTIVE',true,now(),now())",
                assignment,f.party(),extraRole);
        unavailable(f.party());
    }

    @Test void staleReadOnlyForeignAndReplayedAdmissionAreRejected() {
        var f=fixture();
        assertThrows(RuntimeException.class,()->owner.acquire(f.party()));
        var readOnly=new TransactionTemplate(tx.getTransactionManager());
        readOnly.setReadOnly(true);
        assertThrows(RuntimeException.class,()->readOnly.execute(s->owner.acquire(f.party())));
        var repeatable=new TransactionTemplate(tx.getTransactionManager());
        repeatable.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        assertThrows(RuntimeException.class,()->repeatable.execute(s->owner.acquire(f.party())));
        var expired=tx.execute(s->owner.acquire(f.party()));
        assertThrows(RuntimeException.class,()->tx.execute(s->owner.read(expired)));
        assertThrows(RuntimeException.class,()->tx.execute(s->{
            var t=owner.acquire(f.party());owner.read(t);return owner.read(t);
        }));
        assertThrows(RuntimeException.class,()->tx.execute(s->owner.read(
                new TopologyInstalledUnitManufacturerEvidenceContract.Admission(){})));
        var foreign=new JdbcTopologyInstalledUnitManufacturerEvidenceAdapter(jdbc);
        assertThrows(RuntimeException.class,()->tx.execute(s->foreign.read(owner.acquire(f.party()))));
        unavailable(" "+f.party()+" ");
    }

    @Test void ticketCannotCrossThreadOrSuspendedTransaction() throws Exception {
        var f=fixture();
        var worker=Executors.newSingleThreadExecutor();
        try {
            tx.execute(s->{
                var t=owner.acquire(f.party());
                try {
                    assertTrue(worker.submit(()->{
                        assertThrows(RuntimeException.class,()->owner.read(t));
                        return true;
                    }).get(5,TimeUnit.SECONDS));
                } catch(Exception e) {throw new AssertionError(e);}
                return owner.read(t);
            });
        } finally {worker.shutdownNow();assertTrue(worker.awaitTermination(5,TimeUnit.SECONDS));}
        assertThrows(RuntimeException.class,()->tx.execute(s->{
            var t=owner.acquire(f.party());
            var nested=new TransactionTemplate(tx.getTransactionManager());
            nested.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            nested.execute(n->null);
            return owner.read(t);
        }));
    }

    @Test void writerFirstPreventsUnlockedOwnerAdmissionWithoutWaiting() throws Exception {
        var f=fixture();
        try(Connection writer=admin.getDataSource().getConnection()) {
            writer.setAutoCommit(false);
            try(var statement=writer.prepareStatement(
                    "UPDATE "+PROFILE+" SET qualification_status=qualification_status WHERE id=?")) {
                statement.setString(1,f.profile());statement.executeUpdate();
            }
            var denied=assertThrows(DataAccessException.class,()->tx.execute(s->owner.acquire(f.party())));
            assertEquals("55P03",((SQLException)denied.getMostSpecificCause()).getSQLState());
            writer.rollback();
        }
        assertEquals(f.party(),evidence(f.party()).manufacturerPartyId());
    }

    @Test void actualRelationFencesAndCatalogTamperingAreChecked() {
        var f=fixture();
        tx.execute(s->{
            var a=owner.acquire(f.party());
            var fences=owner.finalFences(a);
            assertEquals(5,fences.size());
            assertEquals(Set.of(PARTY,ROLE_TABLE,ASSIGNMENT,PROFILE,GENERATION),
                    fences.stream().map(ProtectedRelationFence::relation).collect(
                            java.util.stream.Collectors.toSet()));
            assertEquals(4,fences.stream().filter(
                    x->x.mode()==ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE).count());
            owner.read(a);
            return null;
        });
        admin.execute("ALTER TABLE "+PROFILE+" ENABLE ROW LEVEL SECURITY");
        try {unavailable(f.party());}
        finally {admin.execute("ALTER TABLE "+PROFILE+" DISABLE ROW LEVEL SECURITY");}
        admin.execute("GRANT CREATE ON SCHEMA public TO "+ROLE);
        try {unavailable(f.party());}
        finally {admin.execute("REVOKE CREATE ON SCHEMA public FROM "+ROLE);}
        assertEquals(f.party(),evidence(f.party()).manufacturerPartyId());
        for(String table:new String[]{ROLE_TABLE,ASSIGNMENT,PROFILE}) {
            var error=assertThrows(DataAccessException.class,()->tx.execute(s->
                    jdbc.update("UPDATE "+table+" SET updated_at=now() WHERE id='unreachable'")));
            assertEquals("42501",((SQLException)error.getMostSpecificCause()).getSQLState());
        }
        admin.execute("GRANT UPDATE ON "+PROFILE+" TO "+ROLE);
        try {unavailable(f.party());}
        finally {admin.execute("REVOKE UPDATE ON "+PROFILE+" FROM "+ROLE);}
        assertEquals(f.party(),evidence(f.party()).manufacturerPartyId());
    }

    @Test void swallowedOwnerRejectionMarksPhysicalTransactionForRollback() {
        var f=fixture();
        assertThrows(UnexpectedRollbackException.class,()->tx.execute(s->{
            jdbc.update("UPDATE "+PARTY+" SET status=status WHERE id=?",f.party());
            try {
                owner.read(new TopologyInstalledUnitManufacturerEvidenceContract.Admission(){});
            } catch (IllegalStateException expected) {
                // Intentionally swallowed: the protected owner must still poison the transaction.
            }
            return null;
        }));
        assertEquals("QUALIFIED",evidence(f.party()).qualificationStatus());
    }
}
