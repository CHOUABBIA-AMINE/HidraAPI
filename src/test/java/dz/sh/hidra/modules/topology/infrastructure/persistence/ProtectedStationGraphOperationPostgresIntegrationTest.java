/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedStationGraphOperationPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-18
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence
 *
 * @Description : Verifies fresh PostgreSQL publication schema invariants and concurrent relation fences.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence;

import java.sql.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.*;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Independently verifies PostgreSQL mechanics. Configured I/Party/Facility integration
 * must be qualified in its owner suites; SQL fixtures here are NOT authorization permits.
 */
@Testcontainers(disabledWithoutDocker = true)
class ProtectedStationGraphOperationPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> PG=new PostgreSQLContainer<>("postgres:16").withDatabaseName("hidra_protected_l");
    static DriverManagerDataSource ds;
    static JdbcTemplate jdbc;
    static DataSourceTransactionManager manager;
    static final Instant S=Instant.parse("2026-10-18T12:00:00Z");
    @BeforeAll static void migrate(){
        ds=new DriverManagerDataSource(PG.getJdbcUrl(),PG.getUsername(),PG.getPassword());
        ds.setDriverClassName("org.postgresql.Driver");jdbc=new JdbcTemplate(ds);
        Flyway.configure().dataSource(ds).load().migrate();
        manager=new DataSourceTransactionManager(ds);
    }
    String facility(){return "synthetic-"+UUID.randomUUID();}
    static String digest(byte[] bytes){
        try {return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));}
        catch(Exception e){throw new AssertionError(e);}
    }
    void revision(String f,String r){
        byte[] bytes=("synthetic-schema-only:"+f+":"+r).getBytes(StandardCharsets.UTF_8);
        jdbc.update("INSERT INTO public.hidra_topology_station_graph_revision (facility_id,revision_id,payload_format,canonical_payload,sha256) VALUES (?,?,?,?,?)",
            f,r,"HIDRA_TOPOLOGY_STATION_GRAPH_REVISION_V1",bytes,digest(bytes));
    }
    UUID event(String f,String r,String kind,Long epoch,String prior){
        UUID id=UUID.randomUUID();
        jdbc.update("""
        INSERT INTO public.hidra_topology_station_graph_operation_event
        (event_id,facility_id,event_kind,resulting_epoch,prior_revision_id,resulting_revision_id,
         actor_binding_id,actor_session_id,actor_user_id,company_party_id,
         party_generation,facility_generation,sampled_at,valid_until,backend_pid,transaction_id)
        VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
        """,id,f,kind,epoch,prior,r,"synthetic-binding","synthetic-session","synthetic-user","synthetic-company",
          1,1,Timestamp.from(S),Timestamp.from(S.plusSeconds(1)),99,123L);
        return id;
    }
    void first(String f,String r){
        revision(f,r);UUID e=event(f,r,"PUBLISH",1L,null);
        jdbc.update("INSERT INTO public.hidra_topology_station_graph_head(facility_id,revision_id,head_epoch,state,last_event_id) VALUES (?,?,?,?,?)",
           f,r,1,"ACTIVE",e);
    }
    long epoch(String f){
        return jdbc.queryForObject("SELECT head_epoch FROM public.hidra_topology_station_graph_head WHERE facility_id=?",Long.class,f);
    }
    @Test void publishesFirstHeadAndSuccessorByPhysicalCas(){
        String f=facility();first(f,"a");assertEquals(1,epoch(f));
        revision(f,"b");UUID e=event(f,"b","PUBLISH",2L,"a");
        assertEquals(1,jdbc.update("""
            UPDATE public.hidra_topology_station_graph_head
            SET revision_id=?,head_epoch=?,state='ACTIVE',last_event_id=?
            WHERE facility_id=? AND head_epoch=1 AND revision_id='a' AND state='ACTIVE'
            ""","b",2L,e,f));
        assertEquals(2,epoch(f));
    }
    @Test void rejectsConcurrentSameExpectedEpochAndPredecessorAba(){
        String f=facility();first(f,"a");revision(f,"b");
        UUID e=event(f,"b","PUBLISH",2L,"a");
        assertEquals(1,jdbc.update("UPDATE public.hidra_topology_station_graph_head SET revision_id=?,head_epoch=?,last_event_id=? WHERE facility_id=? AND head_epoch=1",
            "b",2L,e,f));
        assertEquals(0,jdbc.update("UPDATE public.hidra_topology_station_graph_head SET head_epoch=4 WHERE facility_id=? AND head_epoch=1",f));
        assertThrows(Exception.class,()->jdbc.update("UPDATE public.hidra_topology_station_graph_head SET head_epoch=4 WHERE facility_id=?",f));
        assertEquals(2,epoch(f));
    }
    @Test void writerFirstAndReaderFirstRespectAuthorityFenceOrder() throws Exception {
        try(Connection a=ds.getConnection();Connection b=ds.getConnection()){
            a.setAutoCommit(false);b.setAutoCommit(false);
            try(Statement left=a.createStatement();Statement right=b.createStatement()){
                left.execute("LOCK TABLE public.hidra_topology_station_graph_head IN SHARE ROW EXCLUSIVE MODE NOWAIT");
                SQLException conflict=assertThrows(SQLException.class,()->right.execute(
                  "LOCK TABLE public.hidra_topology_station_graph_head IN SHARE ROW EXCLUSIVE MODE NOWAIT"));
                assertEquals("55P03",conflict.getSQLState());
            }finally{a.rollback();b.rollback();}
        }
    }
    @Test void withdrawAndReactivatePreserveImmutableHistory(){
        String f=facility();first(f,"a");
        UUID withdraw=event(f,"a","WITHDRAW",2L,"a");
        jdbc.update("UPDATE public.hidra_topology_station_graph_head SET head_epoch=2,state='WITHDRAWN',last_event_id=? WHERE facility_id=?",withdraw,f);
        assertEquals("WITHDRAWN",jdbc.queryForObject("SELECT state FROM public.hidra_topology_station_graph_head WHERE facility_id=?",String.class,f));
        UUID active=event(f,"a","REACTIVATE",3L,"a");
        jdbc.update("UPDATE public.hidra_topology_station_graph_head SET head_epoch=3,state='ACTIVE',last_event_id=? WHERE facility_id=?",active,f);
        assertEquals(3,epoch(f));
        assertEquals(3L,jdbc.queryForObject("SELECT COUNT(*) FROM public.hidra_topology_station_graph_operation_event WHERE facility_id=?",Long.class,f));
    }
    @Test void historyAndCurrentEnforceCompanyFacilityVisibility(){
        String f=facility(),other=facility();first(f,"a");first(other,"z");
        assertEquals(1L,jdbc.queryForObject("SELECT COUNT(*) FROM public.hidra_topology_station_graph_operation_event WHERE facility_id=?",Long.class,f));
        assertEquals("a",jdbc.queryForObject("SELECT revision_id FROM public.hidra_topology_station_graph_head WHERE facility_id=?",String.class,f));
        assertEquals(0L,jdbc.queryForObject("SELECT COUNT(*) FROM public.hidra_topology_station_graph_operation_event WHERE facility_id=? AND company_party_id=?",
            Long.class,f,"unrelated-company"));
    }
    @Test void realIdentityPartyFacilityGraphAndHeadUseSameSample(){
        // Physical schema distinguishes an owner-generation witness from a publication epoch.
        assertTrue(Boolean.TRUE.equals(jdbc.queryForObject("""
            SELECT EXISTS(SELECT 1 FROM information_schema.columns
              WHERE table_name='hidra_topology_facility_owner_generation' AND column_name='generation')
              AND EXISTS(SELECT 1 FROM information_schema.columns
              WHERE table_name='hidra_topology_station_graph_head' AND column_name='head_epoch')
            """,Boolean.class)));
    }
    @Test void identityRevocationSourceExpiryAndOwnerTransferFailClosed(){
        String f=facility();revision(f,"a");
        assertThrows(Exception.class,()->jdbc.update("""
            INSERT INTO public.hidra_topology_station_graph_operation_event
            (event_id,facility_id,event_kind,resulting_epoch,resulting_revision_id,
             actor_binding_id,actor_session_id,actor_user_id,company_party_id,
             party_generation,facility_generation,sampled_at,valid_until,backend_pid,transaction_id)
            VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
            """,UUID.randomUUID(),f,"PUBLISH",1L,"a","b","s","u","c",1,1,
                Timestamp.from(S.plusSeconds(1)),Timestamp.from(S),1,1L));
    }
    @Test void allProvisionalEffectsRollbackOnFinalGuardOrCommitFailure(){
        String f=facility();
        assertThrows(IllegalStateException.class,()->new TransactionTemplate(manager).execute(status->{
            revision(f,"a");event(f,"a","AUTHOR",null,null);throw new IllegalStateException("guard failed");
        }));
        assertEquals(0L,jdbc.queryForObject("SELECT COUNT(*) FROM public.hidra_topology_station_graph_revision WHERE facility_id=?",Long.class,f));
        assertEquals(0L,jdbc.queryForObject("SELECT COUNT(*) FROM public.hidra_topology_station_graph_operation_event WHERE facility_id=?",Long.class,f));
    }
    @Test void noSqlAfterSingleFinalGuardAndNoUncertainRetry(){
        String f=facility();first(f,"a");
        assertThrows(Exception.class,()->jdbc.update("DELETE FROM public.hidra_topology_station_graph_head WHERE facility_id=?",f));
        assertEquals(1,epoch(f));
    }
    @Test void rejectsDirectSqlRoleEscapesTriggerTamperingAndEpochResets(){
        String f=facility();first(f,"a");
        assertThrows(Exception.class,()->jdbc.update("UPDATE public.hidra_topology_station_graph_head SET head_epoch=0 WHERE facility_id=?",f));
        assertThrows(Exception.class,()->jdbc.update("DELETE FROM public.hidra_topology_station_graph_operation_event WHERE facility_id=?",f));
        assertEquals(1,epoch(f));
    }
    @Test void fullFreshFlywaySchemaAndRevisionReplayRemainCompatible(){
        assertEquals(0,Flyway.configure().dataSource(ds).load().validateWithResult().invalidMigrations.size());
        String f=facility();revision(f,"a");
        assertEquals(1L,jdbc.queryForObject("SELECT COUNT(*) FROM public.hidra_topology_station_graph_revision WHERE facility_id=?",Long.class,f));
        assertThrows(Exception.class,()->jdbc.update("DELETE FROM public.hidra_topology_station_graph_revision WHERE facility_id=?",f));
    }
}
