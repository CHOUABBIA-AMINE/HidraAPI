/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyProtectedPartyEligibilityPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-26
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence
 *
 * @Description : Qualifies actual owner generations, admission, isolation and ordinary writers.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence;

import dz.sh.hidra.modules.party.application.contract.custody.CustodyProtectedPartyEligibilityContract;
import dz.sh.hidra.modules.party.infrastructure.persistence.adapter.JdbcCustodyProtectedPartyEligibilityAdapter;
import dz.sh.hidra.modules.party.domain.value.PartyStatus;
import dz.sh.hidra.modules.party.infrastructure.persistence.entity.PartyJpaEntity;
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
class CustodyProtectedPartyEligibilityPostgresIntegrationTest {
    private static final String BASE="public.hidra_party_party",GEN="public.hidra_party_eligibility_generation";
    private static final String ROLE="custody_party_runtime",PASSWORD="synthetic-owner-fixture";
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16");
    private AnnotationConfigApplicationContext context;
    private JdbcTemplate admin,jdbc;
    private DriverManagerDataSource runtime;
    private TransactionTemplate tx;
    private CustodyProtectedPartyEligibilityContract owner;
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
        context.registerBean(JdbcCustodyProtectedPartyEligibilityAdapter.class);
        context.refresh();owner=context.getBean(CustodyProtectedPartyEligibilityContract.class);
        type="jtype-"+UUID.randomUUID();admin.update("INSERT INTO public.hidra_party_type(id,code,status,created_at,updated_at) VALUES(?,?,'ACTIVE',now(),now())",type,type);
    }
    @AfterAll void closeContext() { if(context!=null) context.close(); }
    private String fixture(String value) {
        String id="j-"+UUID.randomUUID();insert(id,value);return id;
    }
    private void insert(String id,String value) { admin.update("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES(?,?,?,'Synthetic','DZ',?,now(),now())",id,id,type,value); }
    private long generation(String id) { return admin.queryForObject("SELECT generation FROM "+GEN+" WHERE party_id=?",Long.class,id); }
    private CustodyProtectedPartyEligibilityContract.Contribution evidence(String id) {
        return tx.execute(s->owner.read(owner.acquire(id)));
    }
    private void write(String id,String value) {jdbc.update("UPDATE "+BASE+" SET status=? WHERE id=?",value,id);}
    private void deniedSql(String sql,String state) {
        var failure=assertThrows(DataAccessException.class,()->tx.execute(s->{jdbc.execute(sql);return null;}));
        assertEquals(state,((SQLException)failure.getMostSpecificCause()).getSQLState());
    }

    @Test void onlyRealOwnerSemanticsAreExported() { for(var status:PartyStatus.values()) {
            String id=fixture(status.name());
            if(status==PartyStatus.ACTIVE) assertEquals(id,evidence(id).partyId());
            else assertThrows(IllegalStateException.class,()->evidence(id));
        } }




    @Test void missingNonliveCorruptAndExhaustedWitnessesFailClosed() {
        String missing=fixture("ACTIVE");admin.update("DELETE FROM "+GEN+" WHERE party_id=?",missing);
        assertThrows(IllegalStateException.class,()->evidence(missing));
        deniedSql("UPDATE "+BASE+" SET status='ACTIVE' WHERE id='"+missing+"'","23514");
        String nonlive=fixture("ACTIVE");admin.update("UPDATE "+GEN+" SET present=false WHERE party_id=?",nonlive);
        assertThrows(IllegalStateException.class,()->evidence(nonlive));
        String max=fixture("ACTIVE");admin.update("UPDATE "+GEN+" SET generation=9223372036854775807 WHERE party_id=?",max);
        deniedSql("UPDATE "+BASE+" SET status='SUSPENDED' WHERE id='"+max+"'","23514");
        assertEquals(Long.MAX_VALUE,generation(max));
        assertThrows(IllegalStateException.class,()->evidence("j-missing"));
    }

    @Test void transactionContextAndConsumedOrForeignTicketsFailClosed() {
        String id=fixture("ACTIVE");
        assertThrows(RuntimeException.class,()->owner.acquire(id));
        var readOnly=new TransactionTemplate(tx.getTransactionManager());readOnly.setReadOnly(true);
        assertThrows(RuntimeException.class,()->readOnly.execute(s->owner.acquire(id)));
        var repeatable=new TransactionTemplate(tx.getTransactionManager());repeatable.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        assertThrows(RuntimeException.class,()->repeatable.execute(s->owner.acquire(id)));
        var expired=tx.execute(s->owner.acquire(id));
        assertThrows(RuntimeException.class,()->tx.execute(s->owner.read(expired)));
        assertThrows(RuntimeException.class,()->tx.execute(s->{var a=owner.acquire(id);owner.read(a);return owner.read(a);}));
        assertThrows(RuntimeException.class,()->tx.execute(s->owner.read(new CustodyProtectedPartyEligibilityContract.Admission(){})));
        var foreign=new JdbcCustodyProtectedPartyEligibilityAdapter(jdbc);
        assertThrows(RuntimeException.class,()->tx.execute(s->foreign.read(owner.acquire(id))));
    }

    @Test void swallowedFailureCannotCommitPartialWork() {
        String id=fixture("ACTIVE");long before=generation(id);
        assertThrows(UnexpectedRollbackException.class,()->tx.execute(s->{
            write(id,"ACTIVE");try {owner.read(new CustodyProtectedPartyEligibilityContract.Admission(){});} catch(IllegalStateException expected){}
            return null;
        }));assertEquals(before,generation(id));
    }
    @Test void writerFirstMakesAdmissionFailNowaitForExistingAndAbsentRows() throws Exception {
        String id=fixture("ACTIVE");
        try(Connection writer=runtime.getConnection()) {
            writer.setAutoCommit(false);
            try(var st=writer.prepareStatement("UPDATE "+BASE+" SET status=? WHERE id=?")) {st.setString(1,"ACTIVE");st.setString(2,id);st.executeUpdate();}
            for(String target:new String[]{id,"j-phantom"}) {
                var error=assertThrows(DataAccessException.class,()->tx.execute(s->owner.acquire(target)));
                assertEquals("55P03",((SQLException)error.getMostSpecificCause()).getSQLState());
            }
            writer.rollback();
        }assertEquals(1,evidence(id).generation());
    }
    @Test void admissionRetainsRelationFencesAndExcludesWritersUntilCompletion() throws Exception {
        String id=fixture("ACTIVE");var worker=Executors.newSingleThreadExecutor();var started=new CountDownLatch(1);
        try {tx.execute(s->{
            var admission=owner.acquire(id);
            var blocked=worker.submit(()->{try(Connection c=runtime.getConnection();var st=c.createStatement()) {
                st.execute("SET lock_timeout='600ms'");started.countDown();
                try {st.executeUpdate("UPDATE "+BASE+" SET status='ACTIVE' WHERE id='"+id+"'");return "unexpected-write";}
                catch(SQLException conflict) {return conflict.getSQLState();}
            }});
            try {assertTrue(started.await(3,TimeUnit.SECONDS));assertEquals("55P03",blocked.get(5,TimeUnit.SECONDS));}
            catch(Exception fault) {throw new AssertionError(fault);}
            assertEquals(0,jdbc.queryForObject("SELECT count(*) FROM pg_locks WHERE pid=pg_backend_pid() AND locktype='tuple'",Integer.class));
            return owner.read(admission);
        });} finally {worker.shutdownNow();assertTrue(worker.awaitTermination(5,TimeUnit.SECONDS));}
        tx.execute(s->{write(id,"ACTIVE");return null;});assertEquals(2,generation(id));
    }

    @Test void disabledAlteredTriggersFunctionsAndRlsInvalidateAdmission() {
        String id=fixture("ACTIVE");
        admin.execute("ALTER TABLE "+BASE+" DISABLE TRIGGER trg_p25_party_eligibility_write");
        try {assertThrows(IllegalStateException.class,()->evidence(id));} finally {admin.execute("ALTER TABLE "+BASE+" ENABLE TRIGGER trg_p25_party_eligibility_write");}
        String definition=admin.queryForObject("SELECT pg_get_functiondef('public.hidra_p25_party_eligibility_write()'::regprocedure)",String.class);
        admin.execute("CREATE OR REPLACE FUNCTION public.hidra_p25_party_eligibility_write() RETURNS trigger LANGUAGE plpgsql SECURITY DEFINER SET search_path=pg_catalog,pg_temp AS $$ BEGIN RETURN NULL; END; $$");
        try {assertThrows(IllegalStateException.class,()->evidence(id));} finally {admin.execute(definition);}
        admin.execute("ALTER TABLE "+BASE+" ENABLE ROW LEVEL SECURITY");
        try {assertThrows(IllegalStateException.class,()->evidence(id));} finally {admin.execute("ALTER TABLE "+BASE+" DISABLE ROW LEVEL SECURITY");}
        assertEquals(id,evidence(id).partyId());
    }
    @Test void elevatedOwnerAndReplicaModeAreRejected() {
        String id=fixture("ACTIVE");
        var elevated=new JdbcCustodyProtectedPartyEligibilityAdapter(admin);var adminManager=new DataSourceTransactionManager(admin.getDataSource());
        assertThrows(IllegalStateException.class,()->new TransactionTemplate(adminManager).execute(s->elevated.acquire(id)));
        admin.execute("GRANT SET ON PARAMETER session_replication_role TO "+ROLE);
        try {assertThrows(RuntimeException.class,()->tx.execute(s->{jdbc.execute("SET LOCAL session_replication_role=replica");return owner.acquire(id);}));}
        finally {admin.execute("REVOKE SET ON PARAMETER session_replication_role FROM "+ROLE);}
    }




    @Test void lostFenceAfterSavepointRollbackInvalidatesTicket() {
        String id=fixture("ACTIVE");
        assertThrows(RuntimeException.class,()->tx.execute(s->{
            Object savepoint=s.createSavepoint();var admission=owner.acquire(id);
            s.rollbackToSavepoint(savepoint);return owner.read(admission);
        }));assertEquals(1,generation(id));
    }
    @Test void inheritanceAndUnknownOwnerStateCannotProduceEvidence() {
        String id=fixture("ACTIVE");
        admin.execute("CREATE TABLE public.j_inherited_party () INHERITS ("+BASE+")");
        try {assertThrows(IllegalStateException.class,()->evidence(id));}
        finally {admin.execute("DROP TABLE public.j_inherited_party");}
        admin.update("UPDATE "+BASE+" SET status='UNKNOWN' WHERE id=?",id);
        assertThrows(IllegalStateException.class,()->evidence(id));
    }



    @Test void custodyAdmissionRejectsWhitespaceAndTopologyTickets() {
        String id=fixture("ACTIVE");
        assertThrows(RuntimeException.class,()->evidence(" "+id+" "));
        var topology=new dz.sh.hidra.modules.party.infrastructure.persistence.adapter.JdbcTopologyProtectedPartyEligibilityAdapter(jdbc);
        tx.execute(s->{
            var foreign=topology.acquire(id);
            // Distinct public marker types prevent accidental cross-family calls at compile time.
            assertFalse(CustodyProtectedPartyEligibilityContract.Admission.class.isInstance(foreign));
            assertEquals(id,owner.read(owner.acquire(id)).partyId());return null;
        });
    }
}
