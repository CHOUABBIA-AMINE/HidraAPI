/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyProtectedFluidUnitPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence
 *
 * @Description : Qualifies protected unit owner admission using actual PostgreSQL and synthetic fixtures.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence;

import dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyProtectedFluidUnitContract;
import dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyProtectedFluidUnitContract.*;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter.JdbcCustodyProtectedFluidUnitAdapter;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker=true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustodyProtectedFluidUnitPostgresIntegrationTest {
    private static final String U="public.hidra_telemetry_unit",P="public.hidra_telemetry_fluid_unit_policy";
    private static final String ROLE="custody_fluid_unit_publisher",PASSWORD="synthetic-unit-owner-only";
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16");
    private JdbcTemplate admin,jdbc;private DriverManagerDataSource runtime;private TransactionTemplate tx;
    private AnnotationConfigApplicationContext context;private CustodyProtectedFluidUnitContract owner;
    @Configuration @EnableTransactionManagement static class Config {}
    @BeforeAll void migrateAndWireActualOwner() {
        var source=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
        Flyway.configure().dataSource(source).load().migrate();admin=new JdbcTemplate(source);
        assertEquals(0L,admin.queryForObject("SELECT count(*) FROM "+P,Long.class).longValue());
        admin.execute("REVOKE CREATE ON SCHEMA public FROM PUBLIC");
        admin.execute("CREATE ROLE "+ROLE+" LOGIN PASSWORD '"+PASSWORD+"'");admin.execute("GRANT USAGE ON SCHEMA public TO "+ROLE);
        // Private publication fixture: UPDATE privilege permits the reviewed strong relation lock.
        // A separate ordinary login remains SELECT-only on the owner policy.
        admin.execute("GRANT SELECT,UPDATE ON "+U+","+P+" TO "+ROLE);
        admin.execute("CREATE ROLE custody_fluid_unit_ordinary LOGIN PASSWORD 'synthetic-ordinary-only'");
        admin.execute("GRANT USAGE ON SCHEMA public TO custody_fluid_unit_ordinary");
        admin.execute("GRANT SELECT ON "+U+","+P+" TO custody_fluid_unit_ordinary");
        runtime=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),ROLE,PASSWORD);jdbc=new JdbcTemplate(runtime);
        var manager=new DataSourceTransactionManager(runtime);tx=new TransactionTemplate(manager);tx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        context=new AnnotationConfigApplicationContext();context.register(Config.class);context.registerBean(DataSource.class,()->runtime);
        context.registerBean(JdbcTemplate.class,()->jdbc);context.registerBean(PlatformTransactionManager.class,()->manager);
        context.registerBean(JdbcCustodyProtectedFluidUnitAdapter.class);context.refresh();owner=context.getBean(CustodyProtectedFluidUnitContract.class);
    }
    @AfterAll void close(){if(context!=null)context.close();}
    private String unit(Role role) {
        String id="fluid-unit-"+UUID.randomUUID();
        admin.update("INSERT INTO "+U+"(id,code,symbol,dimension,active,system_defined,created_at,updated_at) VALUES(?,?,?,'declared-dimension',true,false,now(),now())",id,id,"synthetic-unit");
        admin.update("INSERT INTO "+P+" VALUES(?,?,'declared-dimension',true,now(),now())",id,role.name());return id;
    }
    private Contribution read(Role role,String id){return tx.execute(s->owner.read(owner.acquire(List.of(new Need(role,id)))));}
    private static String state(Throwable failure){for(Throwable t=failure;t!=null;t=t.getCause())if(t instanceof SQLException sql)return sql.getSQLState();return null;}
    @Test void allRolesReturnExactOwnerSnapshotsWithoutConversion() {
        for(var role:Role.values()) {String id=unit(role);var c=read(role,id);assertEquals(1,c.units().size());var u=c.units().getFirst();
            assertEquals(id,u.need().unitId());assertEquals(role,u.need().role());assertEquals("declared-dimension",u.dimension());assertEquals("synthetic-unit",u.symbol());assertTrue(c.backendPid()>0);assertTrue(c.transactionId()>0);}
    }
    @Test void missingInactiveWrongRoleAndDimensionFailClosed() {
        assertThrows(RuntimeException.class,()->read(Role.DENSITY,"missing-unit"));String id=unit(Role.DENSITY);
        assertThrows(RuntimeException.class,()->read(Role.ABSOLUTE_PRESSURE,id));
        admin.update("UPDATE "+P+" SET expected_dimension='wrong' WHERE unit_id=?",id);assertThrows(RuntimeException.class,()->read(Role.DENSITY,id));
        admin.update("UPDATE "+P+" SET expected_dimension='declared-dimension',active=false WHERE unit_id=?",id);assertThrows(RuntimeException.class,()->read(Role.DENSITY,id));
        admin.update("UPDATE "+P+" SET active=true WHERE unit_id=?",id);admin.update("UPDATE "+U+" SET active=false WHERE id=?",id);assertThrows(RuntimeException.class,()->read(Role.DENSITY,id));
    }
    @Test void malformedDuplicateAndOversizeRequestsRejectBeforeAdmission() {
        assertThrows(RuntimeException.class,()->new Need(Role.DENSITY," spaced "));assertThrows(RuntimeException.class,()->new Need(Role.DENSITY,"\uD800"));
        assertThrows(RuntimeException.class,()->new Need(null,"unit"));assertThrows(RuntimeException.class,()->new Need(Role.DENSITY,"\u0000"));
        var n=new Need(Role.DENSITY,"unit");assertThrows(RuntimeException.class,()->tx.execute(s->owner.acquire(List.of(n,n))));
        assertThrows(RuntimeException.class,()->tx.execute(s->owner.acquire(Collections.nCopies(129,n))));
        assertThrows(RuntimeException.class,()->tx.execute(s->owner.acquire(null)));
    }
    @Test void transactionContextForeignIssuerReplayAndCompletedTicketsReject() {
        String id=unit(Role.DENSITY);var needs=List.of(new Need(Role.DENSITY,id));assertThrows(RuntimeException.class,()->owner.acquire(needs));
        var ro=new TransactionTemplate(tx.getTransactionManager());ro.setReadOnly(true);assertThrows(RuntimeException.class,()->ro.execute(s->owner.acquire(needs)));
        var rr=new TransactionTemplate(tx.getTransactionManager());rr.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);assertThrows(RuntimeException.class,()->rr.execute(s->owner.acquire(needs)));
        var expired=tx.execute(s->owner.acquire(needs));assertThrows(RuntimeException.class,()->tx.execute(s->owner.read(expired)));
        assertThrows(RuntimeException.class,()->tx.execute(s->{var a=owner.acquire(needs);owner.read(a);return owner.read(a);}));
        assertThrows(RuntimeException.class,()->tx.execute(s->owner.read(new Admission(){})));
        assertThrows(RuntimeException.class,()->tx.execute(s->{var a=owner.acquire(needs);return new JdbcCustodyProtectedFluidUnitAdapter(jdbc).read(a);}));
    }
    @Test void currentnessDetectsOwnUnitAndPolicyRevocationAndRollsBack() {
        for(boolean policy:List.of(true,false)){String id=unit(Role.DENSITY);
            assertThrows(RuntimeException.class,()->tx.execute(s->{var a=owner.acquire(List.of(new Need(Role.DENSITY,id)));owner.read(a);
                jdbc.update("UPDATE "+(policy?P:U)+" SET active=false WHERE "+(policy?"unit_id":"id")+"=?",id);owner.requireCurrent(a);return null;}));
            assertTrue(admin.queryForObject("SELECT active FROM "+(policy?P:U)+" WHERE "+(policy?"unit_id":"id")+"=?",Boolean.class,id));}
    }
    @Test void crossThreadSwallowedReusePoisonsOriginalTransaction() {
        String id=unit(Role.DENSITY);var pool=Executors.newSingleThreadExecutor();try{
            assertThrows(RuntimeException.class,()->tx.execute(s->{var a=owner.acquire(List.of(new Need(Role.DENSITY,id)));owner.read(a);
                try{pool.submit(()->{assertThrows(RuntimeException.class,()->owner.requireCurrent(a));}).get(5,TimeUnit.SECONDS);}catch(Exception e){throw new AssertionError("Worker rejection must be observed before the commit vote",e);}
                return null;}));
        }finally{pool.shutdownNow();}
    }
    @Test void writerFirstUnitOrPolicyChangeMakesAdmissionNowait() throws Exception {
        String id=unit(Role.DENSITY);for(String table:List.of(U,P))for(boolean present:List.of(true,false))try(var c=DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword())){
            String selected=present?id:"missing-"+UUID.randomUUID();
            c.setAutoCommit(false);try(var st=c.createStatement()){st.execute("UPDATE "+table+" SET active=false WHERE "+(table.equals(U)?"id":"unit_id")+"='"+selected+"'");}
            var failure=assertThrows(RuntimeException.class,()->read(Role.DENSITY,selected));assertEquals("55P03",state(failure));c.rollback();
        }
    }
    @Test void ownerFirstRetainsBothFencesAndBlocksOrdinaryWriters() {
        String id=unit(Role.DENSITY);var pool=Executors.newSingleThreadExecutor();try{tx.executeWithoutResult(s->{var a=owner.acquire(List.of(new Need(Role.DENSITY,id)));owner.read(a);
            assertEquals(2,owner.finalFences(a).size());for(String table:List.of(U,P))try{
                String state=pool.submit(()->{try(var c=DriverManager.getConnection(POSTGRES.getJdbcUrl(),ROLE,PASSWORD);var st=c.createStatement()){
                    st.execute("SET lock_timeout='50ms'");st.execute("UPDATE "+table+" SET active=false WHERE "+(table.equals(U)?"id":"unit_id")+"='"+id+"'");return "unexpected";
                }catch(SQLException failure){return failure.getSQLState();}}).get(5,TimeUnit.SECONDS);assertEquals("55P03",state);
            }catch(Exception failure){throw new IllegalStateException(failure);}owner.requireCurrent(a);});}finally{pool.shutdownNow();}
    }
    @Test void lostLocksAfterSavepointRollbackInvalidateAdmission() {
        String id=unit(Role.DENSITY);assertThrows(RuntimeException.class,()->tx.execute(s->{Object save=s.createSavepoint();var a=owner.acquire(List.of(new Need(Role.DENSITY,id)));owner.read(a);s.rollbackToSavepoint(save);owner.requireCurrent(a);return null;}));
    }
    @Test void unsafeCatalogAndElevatedOwnerCannotProduceEvidence() {
        String id=unit(Role.DENSITY);assertEquals(1,read(Role.DENSITY,id).units().size());
        admin.execute("ALTER TABLE "+P+" ENABLE ROW LEVEL SECURITY");try{assertThrows(RuntimeException.class,()->read(Role.DENSITY,id));}finally{admin.execute("ALTER TABLE "+P+" DISABLE ROW LEVEL SECURITY");}
        admin.execute("CREATE TABLE public.fluid_unit_fixture_child () INHERITS ("+P+")");
        try{assertThrows(RuntimeException.class,()->read(Role.DENSITY,id));}finally{admin.execute("DROP TABLE public.fluid_unit_fixture_child");}
        admin.execute("CREATE FUNCTION public.fluid_unit_fixture_trigger() RETURNS trigger LANGUAGE plpgsql AS $$ BEGIN RETURN NEW; END $$");
        admin.execute("CREATE TRIGGER fluid_unit_fixture_trigger BEFORE UPDATE ON "+P+" FOR EACH ROW EXECUTE FUNCTION public.fluid_unit_fixture_trigger()");
        try{assertThrows(RuntimeException.class,()->read(Role.DENSITY,id));}finally{admin.execute("DROP TRIGGER fluid_unit_fixture_trigger ON "+P);admin.execute("DROP FUNCTION public.fluid_unit_fixture_trigger()");}
        assertEquals(1,read(Role.DENSITY,id).units().size());
        var source=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());var template=new TransactionTemplate(new DataSourceTransactionManager(source));
        assertThrows(RuntimeException.class,()->template.execute(s->new JdbcCustodyProtectedFluidUnitAdapter(new JdbcTemplate(source)).read(new Admission(){})));
        assertThrows(RuntimeException.class,()->template.execute(s->new JdbcCustodyProtectedFluidUnitAdapter(new JdbcTemplate(source)).acquire(List.of(new Need(Role.DENSITY,id)))));
    }
    @Test void ordinaryCredentialsCannotChangeOwnerPolicy() {
        String id=unit(Role.DENSITY);var ordinary=new JdbcTemplate(new DriverManagerDataSource(POSTGRES.getJdbcUrl(),"custody_fluid_unit_ordinary","synthetic-ordinary-only"));
        var failure=assertThrows(RuntimeException.class,()->ordinary.update("UPDATE "+P+" SET active=false WHERE unit_id=?",id));assertEquals("42501",state(failure));
        assertTrue(admin.queryForObject("SELECT active FROM "+P+" WHERE unit_id=?",Boolean.class,id));
    }
}
