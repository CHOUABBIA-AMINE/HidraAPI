/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyInstalledUnitCompanyEvidencePostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence
 *
 * @Description : Verifies SELECT-only current company evidence and real manufacturer composition in PostgreSQL16.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence;

import dz.sh.hidra.modules.party.application.contract.topology.TopologyInstalledUnitManufacturerEvidenceContract;
import dz.sh.hidra.modules.party.application.contract.topology.TopologyInstalledUnitCompanyEvidenceContract;
import dz.sh.hidra.modules.party.infrastructure.persistence.adapter.JdbcTopologyInstalledUnitCompanyEvidenceAdapter;
import java.util.List;
import org.springframework.transaction.support.TransactionSynchronizationManager;
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
class TopologyInstalledUnitCompanyEvidencePostgresIntegrationTest {
    private static final String PARTY="public.hidra_party_party";
    private static final String PROFILE="public.hidra_party_manufacturer_profile";
    private static final String ROLE_TABLE="public.hidra_party_role";
    private static final String ASSIGNMENT="public.hidra_party_role_assignment";
    private static final String GENERATION="public.hidra_party_eligibility_generation";
    private static final String ROLE="j_installed_company_evidence",PASSWORD="synthetic-company-evidence";
    private static final String LOCK_OWNER="j_company_lock_owner";
    private static final String OTHER_ROLE="j_company_unreviewed";
    private static final String LOCK_FUNCTION="public.hidra_party_lock_installed_unit_manufacturer_evidence()";
    private boolean migrationRevokedPublicExecute;
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16")
            .withCommand("postgres","-c","max_locks_per_transaction=256");

    @Configuration @EnableTransactionManagement static class Config {}
    private AnnotationConfigApplicationContext context;
    private DriverManagerDataSource runtime;
    private JdbcTemplate admin,jdbc;
    private TransactionTemplate tx;
    private TopologyInstalledUnitCompanyEvidenceContract owner;
    private TopologyInstalledUnitManufacturerEvidenceContract manufacturer;
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
        admin.execute("GRANT SELECT ON "+PARTY+" TO "+ROLE);
        for(String table:new String[]{ROLE_TABLE,ASSIGNMENT,PROFILE})
            admin.execute("GRANT SELECT ON "+table+" TO "+ROLE);
        admin.execute("GRANT SELECT ON "+GENERATION+" TO "+ROLE);
        runtime=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),ROLE,PASSWORD);
        jdbc=new JdbcTemplate(runtime);
        migrationRevokedPublicExecute=Boolean.TRUE.equals(admin.queryForObject(
                "SELECT NOT EXISTS(SELECT 1 FROM pg_catalog.pg_proc p, LATERAL pg_catalog.aclexplode(COALESCE(p.proacl,pg_catalog.acldefault('f',p.proowner))) a WHERE p.oid=?::regprocedure AND a.grantee=0 AND a.privilege_type='EXECUTE')",
                Boolean.class,LOCK_FUNCTION));
        // Synthetic provisioning only. Migration creates no production reader/owner role or grant.
        admin.execute("CREATE ROLE "+LOCK_OWNER+" NOLOGIN NOINHERIT NOSUPERUSER NOCREATEDB NOCREATEROLE NOREPLICATION NOBYPASSRLS");
        admin.execute("CREATE ROLE "+OTHER_ROLE+" NOLOGIN");
        admin.execute("GRANT USAGE,CREATE ON SCHEMA public TO "+LOCK_OWNER);
        admin.execute("GRANT UPDATE ON "+PARTY+","+ROLE_TABLE+","+ASSIGNMENT+","+PROFILE+" TO "+LOCK_OWNER);
        admin.execute("ALTER FUNCTION "+LOCK_FUNCTION+" OWNER TO "+LOCK_OWNER);
        admin.execute("REVOKE CREATE ON SCHEMA public FROM "+LOCK_OWNER);
        admin.execute("GRANT EXECUTE ON FUNCTION "+LOCK_FUNCTION+" TO "+ROLE);
        var manager=new DataSourceTransactionManager(runtime);
        tx=new TransactionTemplate(manager);
        tx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        context=new AnnotationConfigApplicationContext();
        context.register(Config.class);
        context.registerBean(DataSource.class,()->runtime);
        context.registerBean(JdbcTemplate.class,()->jdbc);
        context.registerBean(PlatformTransactionManager.class,()->manager);
        context.registerBean(JdbcTopologyInstalledUnitManufacturerEvidenceAdapter.class);
        context.registerBean(JdbcTopologyInstalledUnitCompanyEvidenceAdapter.class);
        context.refresh();
        owner=context.getBean(TopologyInstalledUnitCompanyEvidenceContract.class);
        manufacturer=context.getBean(TopologyInstalledUnitManufacturerEvidenceContract.class);
        admin.execute("CREATE TABLE public.synthetic_company_rollback_witness (id text PRIMARY KEY)");
        admin.execute("GRANT INSERT ON public.synthetic_company_rollback_witness TO "+ROLE);
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

    private String company() {
        String p=id();
        admin.update("INSERT INTO "+PARTY+"(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at)"
            +" VALUES(?,?,?,'Synthetic non-manufacturer','DZ','ACTIVE',now(),now())",p,p,partyType);
        return p;
    }
    private TopologyInstalledUnitCompanyEvidenceContract.Contribution evidence(String id) {
        return tx.execute(s->{var a=owner.acquire(id);var c=owner.read(a);owner.requireCurrent(a);return c;});
    }
    private void unavailable(String id){assertThrows(RuntimeException.class,()->evidence(id));}
    private void alterAndReject(String sql,String restore,String party) {
        admin.execute(sql);try{unavailable(party);}finally{admin.execute(restore);}
    }
    private long witnesses(String id){return admin.queryForObject("SELECT count(*) FROM public.synthetic_company_rollback_witness WHERE id=?",Long.class,id);}
    private void writeWitness(String id){jdbc.update("INSERT INTO public.synthetic_company_rollback_witness(id) VALUES (?)",id);}
    @Test void activeNonManufacturerCompanyUsesOnlySelectAndReturnsActualCoordinates() {
        String p=company();
        tx.execute(s->{
            var a=owner.acquire(p);var c=owner.read(a);owner.requireCurrent(a);
            assertEquals(p,c.companyPartyId());assertEquals("ACTIVE",c.partyStatus());assertTrue(c.partyGeneration()>0);
            assertEquals(ROLE,c.databaseRole());assertFalse(c.toString().contains(p));
            assertEquals(jdbc.queryForObject("SELECT pg_catalog.pg_backend_pid()",Integer.class),c.backendPid());
            assertEquals(jdbc.queryForObject("SELECT pg_catalog.txid_current()",Long.class),c.transactionId());
            for(String table:new String[]{PARTY,GENERATION,ROLE_TABLE,ASSIGNMENT,PROFILE}) {
                assertTrue(jdbc.queryForObject("SELECT pg_catalog.has_table_privilege(current_user,?,'SELECT')",Boolean.class,table));
                assertFalse(jdbc.queryForObject("SELECT pg_catalog.has_table_privilege(current_user,?,'INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER')",Boolean.class,table));
            }
            return null;
        });
    }
    @Test void differentCompanyAndManufacturerComposeInTheSameSelectOnlyTransaction() {
        String p=company();var m=fixture();
        tx.execute(s->{
            var a=owner.acquire(p);var b=manufacturer.acquire(m.party());
            var c=owner.read(a);var d=manufacturer.read(b);owner.requireCurrent(a);
            assertEquals(c.backendPid(),d.backendPid());assertEquals(c.transactionId(),d.transactionId());
            assertNotEquals(c.companyPartyId(),d.manufacturerPartyId());
            var union=new java.util.HashSet<>(owner.finalFences(a));union.addAll(manufacturer.finalFences(b));
            assertEquals(5,union.size());assertEquals(ROLE,jdbc.queryForObject("SELECT current_user",String.class));
            return null;
        });
    }
    @Test void onePartyCanBeCompanyAndManufacturerWithoutBorrowedAuthority() {
        var m=fixture();
        tx.execute(s->{
            var a=owner.acquire(m.party());var b=manufacturer.acquire(m.party());
            var c=owner.read(a);var d=manufacturer.read(b);owner.requireCurrent(a);
            assertEquals(c.companyPartyId(),d.manufacturerPartyId());assertEquals(c.partyGeneration(),d.partyGeneration());
            assertEquals(c.transactionId(),d.transactionId());return null;
        });
    }
    @Test void manufacturerCanBeReadFreshAfterEarlierCompanyAndManufacturerObservations() {
        String p=company();var m=fixture();
        tx.execute(s->{
            var a=owner.acquire(p);var first=manufacturer.acquire(m.party());var spare=manufacturer.acquire(m.party());
            owner.read(a);var initial=manufacturer.read(first);
            owner.requireCurrent(a);var latest=manufacturer.read(spare);
            assertEquals(initial.manufacturerPartyId(),latest.manufacturerPartyId());
            assertEquals(initial.roleAssignmentId(),latest.roleAssignmentId());assertEquals(initial.transactionId(),latest.transactionId());
            return null;
        });
    }
    @Test void missingInactiveRetiredAndTombstonedCompanyFailClosed() {
        unavailable(id());
        for(String state:new String[]{"DRAFT","SUSPENDED","BLOCKED","RETIRED"}) {
            String p=company();admin.update("UPDATE "+PARTY+" SET status=? WHERE id=?",state,p);unavailable(p);
        }
        String p=company();admin.update("UPDATE "+GENERATION+" SET present=false WHERE party_id=?",p);unavailable(p);
        String missing=company();admin.update("DELETE FROM "+GENERATION+" WHERE party_id=?",missing);unavailable(missing);
    }
    @Test void directSourceDmlIsDeniedOnAllFiveRelations() {
        for(String table:new String[]{PARTY,GENERATION,ROLE_TABLE,ASSIGNMENT,PROFILE}) {
            for(String sql:List.of("UPDATE "+table+" SET "+(table.equals(GENERATION)?"generation=generation":"id=id")+" WHERE false",
                "DELETE FROM "+table+" WHERE false","INSERT INTO "+table+" DEFAULT VALUES")) {
                var failure=assertThrows(DataAccessException.class,()->tx.execute(s->{jdbc.execute(sql);return null;}));
                assertEquals("42501",((SQLException)failure.getMostSpecificCause()).getSQLState());
            }
        }
    }
    @Test void tableColumnAndInheritedPartyWritePrivilegesInvalidateEvidence() {
        String p=company();
        alterAndReject("GRANT UPDATE ON "+PARTY+" TO "+ROLE,"REVOKE UPDATE ON "+PARTY+" FROM "+ROLE,p);
        alterAndReject("GRANT UPDATE(legal_name) ON "+PARTY+" TO "+ROLE,"REVOKE UPDATE(legal_name) ON "+PARTY+" FROM "+ROLE,p);
        admin.execute("GRANT UPDATE ON "+PARTY+" TO "+OTHER_ROLE);
        try{alterAndReject("GRANT "+OTHER_ROLE+" TO "+ROLE,"REVOKE "+OTHER_ROLE+" FROM "+ROLE,p);}
        finally{admin.execute("REVOKE UPDATE ON "+PARTY+" FROM "+OTHER_ROLE);}
        assertEquals(p,evidence(p).companyPartyId());
    }
    @Test void extraDmlOnAnyOtherSourceAndMissingSelectReject() {
        String p=company();
        for(String table:new String[]{GENERATION,ROLE_TABLE,ASSIGNMENT,PROFILE}) {
            alterAndReject("GRANT INSERT ON "+table+" TO "+ROLE,"REVOKE INSERT ON "+table+" FROM "+ROLE,p);
            alterAndReject("REVOKE SELECT ON "+table+" FROM "+ROLE,"GRANT SELECT ON "+table+" TO "+ROLE,p);
        }
    }
    @Test void helperPrivilegesBodyAndSearchPathArePinned() {
        String p=company();assertTrue(migrationRevokedPublicExecute);
        alterAndReject("REVOKE EXECUTE ON FUNCTION "+LOCK_FUNCTION+" FROM "+ROLE,"GRANT EXECUTE ON FUNCTION "+LOCK_FUNCTION+" TO "+ROLE,p);
        alterAndReject("GRANT EXECUTE ON FUNCTION "+LOCK_FUNCTION+" TO PUBLIC","REVOKE EXECUTE ON FUNCTION "+LOCK_FUNCTION+" FROM PUBLIC",p);
        alterAndReject("GRANT EXECUTE ON FUNCTION "+LOCK_FUNCTION+" TO "+OTHER_ROLE,"REVOKE EXECUTE ON FUNCTION "+LOCK_FUNCTION+" FROM "+OTHER_ROLE,p);
        alterAndReject("ALTER FUNCTION "+LOCK_FUNCTION+" SECURITY INVOKER","ALTER FUNCTION "+LOCK_FUNCTION+" SECURITY DEFINER",p);
        alterAndReject("ALTER FUNCTION "+LOCK_FUNCTION+" SET search_path=public,pg_catalog","ALTER FUNCTION "+LOCK_FUNCTION+" SET search_path=pg_catalog,pg_temp",p);
        String original=admin.queryForObject("SELECT pg_catalog.pg_get_functiondef(?::regprocedure)",String.class,LOCK_FUNCTION);
        alterAndReject("CREATE OR REPLACE FUNCTION "+LOCK_FUNCTION+" RETURNS void LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $$ BEGIN PERFORM 1; END; $$",original,p);
    }
    @Test void helperOwnerMembershipCreateAndGrantOptionsReject() {
        String p=company();
        alterAndReject("GRANT "+LOCK_OWNER+" TO "+ROLE,"REVOKE "+LOCK_OWNER+" FROM "+ROLE,p);
        alterAndReject("GRANT "+LOCK_OWNER+" TO "+OTHER_ROLE,"REVOKE "+LOCK_OWNER+" FROM "+OTHER_ROLE,p);
        alterAndReject("GRANT CREATE ON SCHEMA public TO "+LOCK_OWNER,"REVOKE CREATE ON SCHEMA public FROM "+LOCK_OWNER,p);
        alterAndReject("GRANT UPDATE ON "+PARTY+" TO "+LOCK_OWNER+" WITH GRANT OPTION","REVOKE GRANT OPTION FOR UPDATE ON "+PARTY+" FROM "+LOCK_OWNER,p);
        alterAndReject("GRANT UPDATE(legal_name) ON "+PARTY+" TO "+LOCK_OWNER+" WITH GRANT OPTION","REVOKE GRANT OPTION FOR UPDATE(legal_name) ON "+PARTY+" FROM "+LOCK_OWNER,p);
        alterAndReject("GRANT SELECT ON "+PARTY+" TO "+LOCK_OWNER,"REVOKE SELECT ON "+PARTY+" FROM "+LOCK_OWNER,p);
        alterAndReject("GRANT UPDATE ON "+GENERATION+" TO "+LOCK_OWNER,"REVOKE UPDATE ON "+GENERATION+" FROM "+LOCK_OWNER,p);
        alterAndReject("ALTER ROLE "+LOCK_OWNER+" LOGIN","ALTER ROLE "+LOCK_OWNER+" NOLOGIN",p);
    }
    @Test void elevatedOwnerMissingUpdateAndRuntimeSchemaCreateReject() {
        String p=company();
        alterAndReject("GRANT CREATE ON SCHEMA public TO "+ROLE,"REVOKE CREATE ON SCHEMA public FROM "+ROLE,p);
        alterAndReject("ALTER ROLE "+ROLE+" BYPASSRLS","ALTER ROLE "+ROLE+" NOBYPASSRLS",p);
        alterAndReject("REVOKE UPDATE ON "+ROLE_TABLE+" FROM "+LOCK_OWNER,"GRANT UPDATE ON "+ROLE_TABLE+" TO "+LOCK_OWNER,p);
        admin.execute("ALTER FUNCTION "+LOCK_FUNCTION+" OWNER TO "+POSTGRES.getUsername());
        try{unavailable(p);}finally{
            admin.execute("GRANT CREATE ON SCHEMA public TO "+LOCK_OWNER);
            admin.execute("ALTER FUNCTION "+LOCK_FUNCTION+" OWNER TO "+LOCK_OWNER);
            admin.execute("REVOKE CREATE ON SCHEMA public FROM "+LOCK_OWNER);
            admin.execute("GRANT EXECUTE ON FUNCTION "+LOCK_FUNCTION+" TO "+ROLE);
        }
    }
    @Test void rlsAndRenamedViewSourcesCannotSubstituteForPinnedTables() {
        String p=company();
        alterAndReject("ALTER TABLE "+PROFILE+" ENABLE ROW LEVEL SECURITY","ALTER TABLE "+PROFILE+" DISABLE ROW LEVEL SECURITY",p);
        admin.execute("ALTER TABLE "+ROLE_TABLE+" RENAME TO synthetic_company_original_role");
        try{
            admin.execute("CREATE VIEW "+ROLE_TABLE+" AS SELECT * FROM public.synthetic_company_original_role");
            try{unavailable(p);}finally{admin.execute("DROP VIEW "+ROLE_TABLE);}
        }finally{admin.execute("ALTER TABLE public.synthetic_company_original_role RENAME TO hidra_party_role");}
        assertEquals(p,evidence(p).companyPartyId());
    }
    @Test void generationFunctionAndTriggerTamperingReject() {
        String p=company();
        alterAndReject("ALTER TABLE "+PARTY+" DISABLE TRIGGER trg_p25_party_eligibility_write",
            "ALTER TABLE "+PARTY+" ENABLE TRIGGER trg_p25_party_eligibility_write",p);
        String function="public.hidra_p25_party_eligibility_write()";
        String original=admin.queryForObject("SELECT pg_catalog.pg_get_functiondef(?::regprocedure)",String.class,function);
        alterAndReject("CREATE OR REPLACE FUNCTION "+function+" RETURNS trigger LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $$ BEGIN RETURN NEW; END; $$",original,p);
    }
    @Test void heldFencesMatchActualBackendAndCommitReleasesThem() throws Exception {
        String p=company();
        try(Connection writer=admin.getDataSource().getConnection()) {
            writer.setAutoCommit(false);try(var setup=writer.createStatement()){setup.execute("SET lock_timeout='100ms'");}
            tx.execute(s->{
                var a=owner.acquire(p);owner.read(a);var fences=owner.finalFences(a);
                assertEquals(5,fences.size());assertEquals(Set.of(PARTY,GENERATION,ROLE_TABLE,ASSIGNMENT,PROFILE),fences.stream().map(ProtectedRelationFence::relation).collect(java.util.stream.Collectors.toSet()));
                assertEquals(4,fences.stream().filter(f->f.mode()==ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE).count());
                for(var f:fences)assertTrue(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM pg_catalog.pg_locks WHERE pid=pg_catalog.pg_backend_pid() AND granted AND locktype='relation' AND relation=pg_catalog.to_regclass(?) AND mode=?)",Boolean.class,f.relation(),f.mode()==ProtectedRelationFence.Mode.ACCESS_SHARE?"AccessShareLock":"ShareRowExclusiveLock"));
                var failed=assertThrows(SQLException.class,()->{try(var q=writer.prepareStatement("UPDATE "+PARTY+" SET legal_name=legal_name WHERE id=?")){q.setString(1,p);q.executeUpdate();}});
                assertEquals("55P03",failed.getSQLState());return null;
            });
            writer.rollback();try(var q=writer.prepareStatement("UPDATE "+PARTY+" SET legal_name=legal_name WHERE id=?")){q.setString(1,p);assertEquals(1,q.executeUpdate());}writer.rollback();
        }
    }
    @Test void writerFirstPreventsUnlockedCompanyObservationNowait() throws Exception {
        String p=company();
        try(Connection writer=admin.getDataSource().getConnection()) {
            writer.setAutoCommit(false);try(var q=writer.prepareStatement("UPDATE "+PARTY+" SET legal_name=legal_name WHERE id=?")){q.setString(1,p);q.executeUpdate();}
            var failed=assertThrows(DataAccessException.class,()->tx.execute(s->owner.acquire(p)));
            assertEquals("55P03",((SQLException)failed.getMostSpecificCause()).getSQLState());writer.rollback();
        }
        assertEquals(p,evidence(p).companyPartyId());
    }
    @Test void evidenceRequiresWritableReadCommittedActualTransaction() {
        String p=company();assertThrows(RuntimeException.class,()->owner.acquire(p));
        var readOnly=new TransactionTemplate(tx.getTransactionManager());readOnly.setReadOnly(true);
        assertThrows(RuntimeException.class,()->readOnly.execute(s->owner.acquire(p)));
        var repeatable=new TransactionTemplate(tx.getTransactionManager());repeatable.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        assertThrows(RuntimeException.class,()->repeatable.execute(s->owner.acquire(p)));
        unavailable(" "+p);unavailable(p+" ");unavailable("x".repeat(81));unavailable(null);
    }
    @Test void forgedForeignAndReplayedTicketsPoisonPhysicalTransaction() {
        String p=company();
        assertThrows(RuntimeException.class,()->tx.execute(s->owner.read(new TopologyInstalledUnitCompanyEvidenceContract.Admission(){})));
        var foreign=new JdbcTopologyInstalledUnitCompanyEvidenceAdapter(jdbc);
        assertThrows(RuntimeException.class,()->tx.execute(s->foreign.read(owner.acquire(p))));
        assertThrows(RuntimeException.class,()->tx.execute(s->{var a=owner.acquire(p);owner.read(a);return owner.read(a);}));
        assertThrows(RuntimeException.class,()->tx.execute(s->{owner.requireCurrent(owner.acquire(p));return null;}));
    }
    @Test void completedTicketsCannotDiscloseInAnotherTransaction() {
        String p=company();var a=tx.execute(s->{var t=owner.acquire(p);owner.read(t);return t;});
        assertThrows(RuntimeException.class,()->tx.execute(s->owner.read(a)));
        assertThrows(RuntimeException.class,()->tx.execute(s->{owner.requireCurrent(a);return null;}));
    }
    @Test void foreignThreadMisuseCannotBeSwallowedIntoSuccessfulCommit() throws Exception {
        String p=company(),w=id();var worker=Executors.newSingleThreadExecutor();
        try{
            assertThrows(RuntimeException.class,()->tx.execute(s->{
                var a=owner.acquire(p);owner.read(a);writeWitness(w);
                try{assertTrue(worker.submit(()->{assertThrows(RuntimeException.class,()->owner.requireCurrent(a));return true;}).get(5,TimeUnit.SECONDS));}
                catch(Exception failure){throw new AssertionError(failure);}return null;
            }));assertEquals(0,witnesses(w));
        }finally{worker.shutdownNow();assertTrue(worker.awaitTermination(5,TimeUnit.SECONDS));}
    }
    @Test void suspensionInvalidatesOriginalTicketAndVetoesCommit() {
        String p=company(),w=id();
        assertThrows(RuntimeException.class,()->tx.execute(s->{
            var a=owner.acquire(p);owner.read(a);writeWitness(w);
            var nested=new TransactionTemplate(tx.getTransactionManager());nested.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            nested.execute(n->null);assertThrows(RuntimeException.class,()->owner.requireCurrent(a));return null;
        }));assertEquals(0,witnesses(w));
    }
    @Test void savepointRollbackLosesFencesAndCannotCommitSwallowedFailure() {
        String p=company(),w=id();
        assertThrows(RuntimeException.class,()->tx.execute(s->{
            writeWitness(w);Object sp=s.createSavepoint();var a=owner.acquire(p);owner.read(a);s.rollbackToSavepoint(sp);s.releaseSavepoint(sp);
            assertThrows(RuntimeException.class,()->owner.requireCurrent(a));return null;
        }));assertEquals(0,witnesses(w));
    }
    @Test void swallowedAcquisitionFailureRollsBackEarlierWitness() {
        String p=company(),w=id();admin.execute("REVOKE EXECUTE ON FUNCTION "+LOCK_FUNCTION+" FROM "+ROLE);
        try{assertThrows(RuntimeException.class,()->tx.execute(s->{writeWitness(w);assertThrows(RuntimeException.class,()->owner.acquire(p));return null;}));}
        finally{admin.execute("GRANT EXECUTE ON FUNCTION "+LOCK_FUNCTION+" TO "+ROLE);}
        assertEquals(0,witnesses(w));
    }
    // Deliberate same-transaction source mutation uses a separate synthetic schema-owner connection.
    // All owner observations happen as the restricted reader, after SET LOCAL ROLE.
    private void ownTransactionChange(String p,String mutation) {
        var data=admin.getDataSource();var localJdbc=new JdbcTemplate(data);
        var actual=new JdbcTopologyInstalledUnitCompanyEvidenceAdapter(localJdbc);
        var ownTx=new TransactionTemplate(new DataSourceTransactionManager(data));
        assertThrows(RuntimeException.class,()->ownTx.execute(s->{
            localJdbc.execute("SET LOCAL ROLE "+ROLE);var a=actual.acquire(p);actual.read(a);
            localJdbc.execute("SET LOCAL ROLE "+POSTGRES.getUsername());localJdbc.update(mutation,p);
            localJdbc.execute("SET LOCAL ROLE "+ROLE);actual.requireCurrent(a);return null;
        }));
        assertEquals(p,evidence(p).companyPartyId());
    }
    @Test void currentRecheckRejectsSameTransactionRowImageChanges() {
        ownTransactionChange(company(),"UPDATE "+PARTY+" SET legal_name='Changed synthetic name' WHERE id=?");
    }
    @Test void currentRecheckRejectsSameTransactionGenerationChanges() {
        ownTransactionChange(company(),"UPDATE "+GENERATION+" SET generation=generation+1 WHERE party_id=?");
    }
    @Test void currentRecheckRejectsSameTransactionStatusChanges() {
        ownTransactionChange(company(),"UPDATE "+PARTY+" SET status='SUSPENDED' WHERE id=?");
    }
    @Test void currentRecheckRejectsDatabaseRoleSubstitution() {
        String p=company();var localJdbc=new JdbcTemplate(admin.getDataSource());
        var actual=new JdbcTopologyInstalledUnitCompanyEvidenceAdapter(localJdbc);
        var ownTx=new TransactionTemplate(new DataSourceTransactionManager(admin.getDataSource()));
        assertThrows(RuntimeException.class,()->ownTx.execute(s->{
            localJdbc.execute("SET LOCAL ROLE "+ROLE);var a=actual.acquire(p);actual.read(a);
            localJdbc.execute("SET LOCAL ROLE "+POSTGRES.getUsername());actual.requireCurrent(a);return null;
        }));
    }
    @Test void currentRecheckDetectsPrivilegeChangeAfterRead() {
        String p=company();
        try{assertThrows(RuntimeException.class,()->tx.execute(s->{
            var a=owner.acquire(p);owner.read(a);admin.execute("GRANT UPDATE ON "+PARTY+" TO "+ROLE);owner.requireCurrent(a);return null;
        }));}finally{admin.execute("REVOKE UPDATE ON "+PARTY+" FROM "+ROLE);}
    }
    @Test void ticketAndScalarShapesCannotBeUsedAsTransferableAuthority() throws Exception {
        var contribution=new TopologyInstalledUnitCompanyEvidenceContract.Contribution("synthetic-company","ACTIVE",1,1,1,ROLE);
        assertFalse(contribution.toString().contains("synthetic-company"));
        assertThrows(IllegalArgumentException.class,()->new TopologyInstalledUnitCompanyEvidenceContract.Contribution("synthetic-company","RETIRED",1,1,1,ROLE));
        Class<?> ticket=Class.forName(JdbcTopologyInstalledUnitCompanyEvidenceAdapter.class.getName()+"$Ticket");
        assertEquals(0,ticket.getConstructors().length);
        for(var field:ticket.getDeclaredFields())if(!field.isSynthetic())assertTrue(java.lang.reflect.Modifier.isPrivate(field.getModifiers()));
    }
}
