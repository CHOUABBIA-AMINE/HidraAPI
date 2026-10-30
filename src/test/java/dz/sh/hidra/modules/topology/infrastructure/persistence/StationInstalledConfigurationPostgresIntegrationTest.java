/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInstalledConfigurationPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence
 *
 * @Description : Verifies real fresh Flyway catalog constraints and immutable station declarations.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence;

import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.modules.topology.domain.model.StationInstalledConfiguration.*;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.*;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision.*;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.StationInstalledConfigurationCodec;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.StationInternalGraphRevisionCodec;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker=true)
class StationInstalledConfigurationPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16").withCommand("postgres","-c","max_locks_per_transaction=256");
    private static volatile boolean migrated;
    private static synchronized void migrate() {
        if (!migrated) {
            Flyway.configure().dataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword())
                .load().migrate();
            migrated = true;
        }
    }
    private JdbcTemplate jdbc() {
        migrate();
        var source=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
        source.setDriverClassName("org.postgresql.Driver");
        return new JdbcTemplate(source);
    }
    private boolean exists(String sql) {return Boolean.TRUE.equals(jdbc().queryForObject(sql,Boolean.class));}

    @Test void freshFlywayCreatesFourConfigurationRelations() {
        for (String suffix : new String[]{"revision","member","head","request"}) {
            assertTrue(exists("SELECT to_regclass('public.hidra_topology_station_configuration_"+suffix+"') IS NOT NULL"));
        }
    }
    @Test void physicalMemberRequiresOwnedUnitAndInstallationEventWitness() {
        assertTrue(exists("""
            SELECT EXISTS(SELECT 1 FROM pg_constraint
            WHERE conrelid='public.hidra_topology_station_configuration_member'::regclass
              AND conname='fk_station_configuration_member_event'
              AND contype='f' AND convalidated AND NOT condeferrable)
            """));
        assertTrue(exists("""
            SELECT EXISTS(SELECT 1 FROM pg_constraint
            WHERE conrelid='public.hidra_topology_station_configuration_member'::regclass
              AND conname='fk_station_configuration_member_unit_company' AND contype='f')
            """));
        assertTrue(exists("""
            SELECT EXISTS(SELECT 1 FROM pg_constraint
            WHERE conrelid='public.hidra_topology_station_configuration_member'::regclass
              AND conname='ck_station_configuration_member_binding' AND contype='c')
            """));
    }
    @Test void headIsConstrainedToExactRevisionAndSelectedEpoch() {
        assertTrue(exists("""
            SELECT EXISTS(SELECT 1 FROM pg_constraint
            WHERE conrelid='public.hidra_topology_station_configuration_head'::regclass
              AND conname='fk_station_configuration_head_selection' AND contype='f')
            """));
        assertTrue(exists("""
            SELECT EXISTS(SELECT 1 FROM pg_trigger
            WHERE tgrelid='public.hidra_topology_station_configuration_head'::regclass
              AND tgname='trg_station_configuration_head_cas' AND tgenabled<>'D')
            """));
    }
    @Test void immutableRevisionMemberRequestRetainUpdateDeleteAndTruncateFences() {
        for(String suffix:new String[]{"revision","member","request"}) {
            assertTrue(exists("SELECT EXISTS(SELECT 1 FROM pg_trigger WHERE tgrelid="
                +"'public.hidra_topology_station_configuration_"+suffix+"'::regclass"
                +" AND tgname='trg_station_configuration_"+suffix+"_immutable' AND tgenabled<>'D')"));
            assertTrue(exists("SELECT EXISTS(SELECT 1 FROM pg_trigger WHERE tgrelid="
                +"'public.hidra_topology_station_configuration_"+suffix+"'::regclass"
                +" AND tgname='trg_station_configuration_"+suffix+"_no_truncate' AND tgenabled<>'D')"));
        }
    }
    @Test void invalidHeadReferenceFailsUnderRealForeignKey() {
        assertThrows(DataAccessException.class,()->jdbc().update("""
            INSERT INTO public.hidra_topology_station_configuration_head(
             company_party_id,facility_id,revision_id,head_epoch)
            VALUES('synthetic-company','synthetic-facility','missing-revision',1)
            """));
    }

    private static final String PREFIX="public.hidra_topology_station_configuration_";
    private static final Instant FROM=Instant.parse("2026-02-01T00:00:00Z");
    private final StationInstalledConfigurationCodec codec=new StationInstalledConfigurationCodec();
    private final StationInternalGraphRevisionCodec graphCodec=new StationInternalGraphRevisionCodec();
    private record Fixture(String company, String facility, StationInternalGraphRevision graph,
            List<Position> positions) {}
    private static String id() { return UUID.randomUUID().toString(); }
    private TransactionTemplate transaction(JdbcTemplate db) {
        return new TransactionTemplate(new DataSourceTransactionManager(Objects.requireNonNull(db.getDataSource())));
    }
    /** Schema-owner synthetic data only; all original full-chain constraints/triggers stay enabled. */
    private Fixture populatedSources() {
        var db=jdbc();
        String company=id(),facility=id(),type=id(),kind=id();
        db.update("INSERT INTO public.hidra_topology_facility_type(id,code,name,facility_kind,status,created_at,updated_at)"
            +" VALUES(?,?,'Synthetic station','STATION','ACTIVE',now(),now())",type,type);
        db.update("INSERT INTO public.hidra_topology_equipment_type(id,code,name,status,created_at,updated_at)"
            +" VALUES(?,?,'Synthetic unit','ACTIVE',now(),now())",kind,kind);
        db.update("INSERT INTO public.hidra_topology_facility(id,code,facility_type_id,facility_kind,owner_party_id,status,created_at,updated_at)"
            +" VALUES(?,?,?,'STATION',?,'ACTIVE',now(),now())",facility,facility,type,company);
        var units=new ArrayList<Unit>();
        var edges=new ArrayList<Edge>();
        var positions=new ArrayList<Position>();
        for(int i=0;i<3;i++) {
            String position=id(),unit=id(),installation=id();
            db.update("INSERT INTO public.hidra_topology_equipment(id,code,facility_id,equipment_type_id,status,created_at,updated_at)"
                +" VALUES(?,?,?,?,'PLANNED',now(),now())",position,position,facility,kind);
            db.update("INSERT INTO public.hidra_topology_installed_unit(id,company_party_id,equipment_kind_id,"
                +"manufacturer_party_verification,manufacturer_model_verification,serial_verification)"
                +" VALUES(?,?,?,'UNKNOWN','UNKNOWN','UNKNOWN')",unit,company,kind);
            db.update("INSERT INTO public.hidra_topology_installed_unit_installation(installation_id,unit_id,position_id,"
                +"company_party_id,effective_from,recorded_at,evidence_reference,revision)"
                +" VALUES(?,?,?,?,?,?,'synthetic:installation',1)",installation,unit,position,company,
                Timestamp.from(FROM),Timestamp.from(FROM));
            // The original installation trigger appends the event; never insert a forged event row.
            assertEquals(1,db.queryForObject("SELECT count(*) FROM public.hidra_topology_installed_unit_installation_event"
                +" WHERE installation_id=? AND revision=1",Integer.class,installation));
            units.add(new Unit(position,facility,"i"+i,"o"+i));
            edges.add(new Edge("in"+i,"s","i"+i));
            edges.add(new Edge("out"+i,"o"+i,"d"));
            positions.add(new Position(position,Disposition.CONNECTED,unit,installation,1,FROM,null,"synthetic:installation"));
        }
        var graph=new StationInternalGraph(facility,"graph-r1",Status.COMPLETE,
            List.of(new Header("suction",HeaderKind.SUCTION,"s"),
                    new Header("discharge",HeaderKind.DISCHARGE,"d")),units,edges);
        var revision=new StationInternalGraphRevision(facility,"graph-r1",graph,SourceClassification.SYNTHETIC,
            FROM,FROM,null,"synthetic:graph",null,Qualification.STRUCTURALLY_QUALIFIED);
        byte[] raw=graphCodec.encode(revision);
        db.update("INSERT INTO public.hidra_topology_station_graph_revision"
            +"(facility_id,revision_id,payload_format,canonical_payload,sha256) VALUES(?,?,?,?,?)",
            facility,revision.revisionId(),StationInternalGraphRevisionCodec.FORMAT,raw,graphCodec.sha256(raw));
        return new Fixture(company,facility,graphCodec.decode(raw),List.copyOf(positions));
    }
    private StationInstalledConfiguration declaration(Fixture f,String revision,long epoch,String request) {
        return new StationInstalledConfiguration(f.company(),f.facility(),revision,request,epoch-1,epoch,1,
            f.graph(),StationInternalGraphRevisionCodec.FORMAT,graphCodec.sha256(graphCodec.encode(f.graph())),
            FROM,null,FROM,"synthetic:configuration","synthetic-binding","synthetic-session","synthetic-user",
            f.positions(),f.graph().graph().edges().stream().map(Edge::id).toList());
    }
    private void insert(JdbcTemplate db,String suffix,LinkedHashMap<String,Object> values,Map<String,Object> changes) {
        changes.forEach((key,value)->{
            if(!values.containsKey(key)) throw new AssertionError("Unknown fixture column: "+key);
            values.put(key,value);
        });
        String placeholders=String.join(",",Collections.nCopies(values.size(),"?"));
        assertEquals(1,db.update("INSERT INTO "+PREFIX+suffix+"("+String.join(",",values.keySet())+") VALUES("
            +placeholders+")",values.values().toArray()));
    }
    private void revision(JdbcTemplate db,StationInstalledConfiguration x,Map<String,Object> changes) {
        byte[] raw=codec.encode(x);
        var row=new LinkedHashMap<String,Object>();
        row.put("company_party_id",x.companyPartyId());row.put("facility_id",x.facilityId());
        row.put("revision_id",x.revisionId());row.put("request_id",x.requestId());
        row.put("expected_epoch",x.expectedEpoch());row.put("selected_epoch",x.selectedEpoch());
        row.put("graph_head_epoch",x.graphHeadEpoch());row.put("graph_revision_id",x.pinnedGraph().revisionId());
        row.put("graph_payload_format",x.graphPayloadFormat());row.put("graph_sha256",x.graphSha256());
        row.put("payload_format",StationInstalledConfigurationCodec.FORMAT);
        row.put("canonical_payload",raw);row.put("canonical_sha256",codec.sha256(raw));
        row.put("effective_from",Timestamp.from(x.effectiveFrom()));
        row.put("effective_until",x.effectiveUntil()==null?null:Timestamp.from(x.effectiveUntil()));
        row.put("recorded_at",Timestamp.from(x.recordedAt()));row.put("source_reference",x.sourceReference());
        row.put("actor_binding_id",x.actorBindingId());row.put("actor_session_id",x.actorSessionId());
        row.put("actor_user_id",x.actorUserId());
        insert(db,"revision",row,changes);
    }
    private void member(JdbcTemplate db,StationInstalledConfiguration x,Position p,Map<String,Object> changes) {
        var row=new LinkedHashMap<String,Object>();
        row.put("company_party_id",x.companyPartyId());row.put("facility_id",x.facilityId());
        row.put("revision_id",x.revisionId());row.put("position_id",p.positionId());
        row.put("disposition",p.disposition().name());row.put("installed_unit_id",p.installedUnitId());
        row.put("installation_id",p.installationId());row.put("installation_revision",p.installationRevision());
        row.put("installation_from",p.installationFrom()==null?null:Timestamp.from(p.installationFrom()));
        row.put("installation_until",p.installationUntil()==null?null:Timestamp.from(p.installationUntil()));
        row.put("installation_evidence_reference",p.installationEvidenceReference());
        insert(db,"member",row,changes);
    }
    private void receipt(JdbcTemplate db,StationInstalledConfiguration x,Map<String,Object> changes) {
        // The B3-S2 trigger checks the ORIGINAL SQL statement's real backend/XID/clock.
        // The fixture is a schema-owner constraint test, not a native Identity admission.
        // Capture these values inside the insertion itself, also for autocommit callers.
        var row=new LinkedHashMap<String,Object>();
        row.put("company_party_id",x.companyPartyId());row.put("facility_id",x.facilityId());
        row.put("request_id",x.requestId());row.put("command_sha256",codec.sha256(codec.encode(x)));
        row.put("revision_id",x.revisionId());row.put("selected_epoch",x.selectedEpoch());
        row.put("actor_binding_id",x.actorBindingId());
        changes.forEach((column,value)->{
            if(!row.containsKey(column))throw new AssertionError("Unknown receipt fixture column: "+column);
            row.put(column,value);
        });
        assertEquals(1,db.update("""
            WITH origin AS MATERIALIZED (SELECT pg_catalog.clock_timestamp() AS sampled)
            INSERT INTO public.hidra_topology_station_configuration_request
                (company_party_id,facility_id,request_id,command_sha256,revision_id,selected_epoch,
                 actor_binding_id,recorded_at,actor_session_id,actor_user_id,sampled_at,valid_until,
                 backend_pid,transaction_id,database_role)
            SELECT ?,?,?,?,?,?,?,origin.sampled,?,?,origin.sampled-interval '1 second',
                origin.sampled+interval '1 minute',
                pg_catalog.pg_backend_pid(),pg_catalog.txid_current(),session_user
            FROM origin
            """,row.get("company_party_id"),row.get("facility_id"),row.get("request_id"),
            row.get("command_sha256"),row.get("revision_id"),row.get("selected_epoch"),
            row.get("actor_binding_id"),x.actorSessionId(),x.actorUserId()));
    }
    private int select(JdbcTemplate db,StationInstalledConfiguration x,long expected) {
        if(expected==0) return db.update("INSERT INTO "+PREFIX+"head(company_party_id,facility_id,revision_id,head_epoch)"
            +" VALUES(?,?,?,?)",x.companyPartyId(),x.facilityId(),x.revisionId(),x.selectedEpoch());
        return db.update("UPDATE "+PREFIX+"head SET revision_id=?,head_epoch=?"
            +" WHERE company_party_id=? AND facility_id=? AND head_epoch=?",
            x.revisionId(),x.selectedEpoch(),x.companyPartyId(),x.facilityId(),expected);
    }
    private long count(JdbcTemplate db,String suffix,Fixture f) {
        return db.queryForObject("SELECT count(*) FROM "+PREFIX+suffix
            +" WHERE company_party_id=? AND facility_id=?",Long.class,f.company(),f.facility());
    }
    private void stored(JdbcTemplate db,StationInstalledConfiguration x) {
        byte[] actual=db.queryForObject("SELECT canonical_payload FROM "+PREFIX+"revision"
            +" WHERE company_party_id=? AND facility_id=? AND revision_id=?",byte[].class,
            x.companyPartyId(),x.facilityId(),x.revisionId());
        assertArrayEquals(codec.encode(x),actual);
        assertEquals(x,codec.decode(actual));
    }
    private void rejected(String state,String constraint,org.junit.jupiter.api.function.Executable work) {
        var failure=assertThrows(DataAccessException.class,work);
        Throwable cause=failure;
        while(!(cause instanceof SQLException) && cause.getCause()!=null) cause=cause.getCause();
        assertInstanceOf(SQLException.class,cause);
        assertEquals(state,((SQLException)cause).getSQLState());
        if(constraint!=null) assertTrue(cause.getMessage().contains(constraint),cause.getMessage());
    }
    @Test void populatedNineUnitThreeStationSnapshotsReloadExactCanonicalEvidence() {
        var db=jdbc();var seen=new HashSet<String>();
        for(int i=0;i<3;i++) {
            var f=populatedSources();var x=declaration(f,"r1",1,"request-1");
            transaction(db).execute(status->{
                revision(db,x,Map.of());
                x.positions().forEach(p->member(db,x,p,Map.of()));
                receipt(db,x,Map.of());assertEquals(1,select(db,x,0));return null;
            });
            stored(db,x);
            assertEquals(3,count(db,"member",f));assertEquals(1,count(db,"request",f));
            x.positions().forEach(p->assertTrue(seen.add(p.installedUnitId())));
        }
        assertEquals(9,seen.size());
    }
    @Test void populatedRevisionRejectsBadPeriodEpochFormatDigestAndGraphReference() {
        var db=jdbc();var f=populatedSources();var x=declaration(f,"r1",1,"request-1");
        rejected("23514","ck_station_configuration_period",()->revision(db,x,Map.of("effective_until",Timestamp.from(FROM))));
        rejected("23514","ck_station_configuration_epoch",()->revision(db,x,Map.of("selected_epoch",2L)));
        rejected("23514","ck_station_configuration_format",()->revision(db,x,Map.of("payload_format","UNKNOWN")));
        rejected("23514","ck_station_configuration_integrity",()->revision(db,x,Map.of("canonical_sha256","0".repeat(64))));
        rejected("23503","fk_station_configuration_graph",()->revision(db,x,Map.of("graph_revision_id","missing")));
        assertEquals(0,count(db,"revision",f));
        revision(db,x,Map.of());stored(db,x);
        rejected("23505",null,()->revision(db,x,Map.of()));
    }
    @Test void populatedMemberRejectsIncompleteBindingForeignWitnessAndDuplicateUnit() {
        var db=jdbc();var f=populatedSources();var x=declaration(f,"r1",1,"request-1");
        revision(db,x,Map.of());var p=x.positions().get(0);
        rejected("23514","ck_station_configuration_member_binding",()->member(db,x,p,Map.of("disposition","UNOCCUPIED")));
        rejected("23514","ck_station_configuration_member_binding",()->member(db,x,p,Map.of("installation_revision",0L)));
        rejected("23503","fk_station_configuration_member_event",()->member(db,x,p,Map.of("installation_id","missing")));
        rejected("23503","fk_station_configuration_member_unit_company",()->member(db,x,p,Map.of("installed_unit_id","missing")));
        assertEquals(0,count(db,"member",f));
        member(db,x,p,Map.of());
        rejected("23505",null,()->member(db,x,p,Map.of("position_id",x.positions().get(1).positionId())));
        assertEquals(1,count(db,"member",f));stored(db,x);
    }
    @Test void occupiedBindingCannotPassSqlCheckWithNullInstallationRevision() {
        var db=jdbc();var f=populatedSources();var x=declaration(f,"r1",1,"request-1");
        revision(db,x,Map.of());var changes=new HashMap<String,Object>();
        changes.put("installation_revision",null);
        rejected("23514","ck_station_configuration_member_binding",()->member(db,x,x.positions().get(0),changes));
        assertEquals(0,count(db,"member",f));
    }
    @Test void populatedReceiptsRejectDuplicateRequestBadDigestAndForeignResult() {
        var db=jdbc();var f=populatedSources();var x=declaration(f,"r1",1,"request-1");
        revision(db,x,Map.of());
        rejected("23514","ck_station_configuration_request_digest",()->receipt(db,x,Map.of("command_sha256","bad")));
        rejected("23503","fk_station_configuration_request_result",()->receipt(db,x,Map.of("revision_id","missing")));
        receipt(db,x,Map.of());
        rejected("23505","pk_station_configuration_request",()->receipt(db,x,Map.of()));
        assertEquals(1,count(db,"request",f));stored(db,x);
    }
    @Test void populatedImmutableRowsRejectUpdateDeleteAndTruncateWithoutChangingEvidence() {
        var db=jdbc();var f=populatedSources();var x=declaration(f,"r1",1,"request-1");
        revision(db,x,Map.of());member(db,x,x.positions().get(0),Map.of());receipt(db,x,Map.of());
        assertEquals(1,select(db,x,0));
        for(String suffix:List.of("revision","member","request")) {
            String filter=" WHERE company_party_id='"+f.company()+"' AND facility_id='"+f.facility()+"'";
            rejected("23514",null,()->db.update("UPDATE "+PREFIX+suffix+" SET revision_id=revision_id"+filter));
            rejected("23514",null,()->db.update("DELETE FROM "+PREFIX+suffix+filter));
            rejected("23514",null,()->db.execute("TRUNCATE "+PREFIX+suffix+" CASCADE"));
            assertEquals(1,count(db,suffix,f));
        }
        rejected("23514",null,()->db.update("DELETE FROM "+PREFIX+"head WHERE company_party_id=?",f.company()));
        rejected("23514",null,()->db.execute("TRUNCATE "+PREFIX+"head"));
        assertEquals(1,count(db,"head",f));stored(db,x);
    }
    @Test void headAdvancesOneEpochAndRejectsStaleSameRevisionAndRollback() {
        var db=jdbc();var f=populatedSources();var first=declaration(f,"r1",1,"request-1");
        var second=declaration(f,"r2",2,"request-2");
        revision(db,first,Map.of());revision(db,second,Map.of());
        rejected("23514",null,()->select(db,second,0));
        assertEquals(1,select(db,first,0));assertEquals(1,select(db,second,1));
        assertEquals(0,select(db,second,1));
        rejected("23514",null,()->db.update("UPDATE "+PREFIX+"head SET head_epoch=3 WHERE company_party_id=?",f.company()));
        rejected("23514",null,()->db.update("UPDATE "+PREFIX+"head SET revision_id='r1',head_epoch=1 WHERE company_party_id=?",f.company()));
        var third=declaration(f,"r3",3,"request-3");
        assertThrows(IllegalStateException.class,()->transaction(db).execute(status->{
            revision(db,third,Map.of());member(db,third,third.positions().get(0),Map.of());
            receipt(db,third,Map.of());assertEquals(1,select(db,third,2));
            throw new IllegalStateException("Synthetic downstream rollback");
        }));
        assertEquals(2,count(db,"revision",f));assertEquals(0,count(db,"member",f));assertEquals(0,count(db,"request",f));
        assertEquals("r2",db.queryForObject("SELECT revision_id FROM "+PREFIX+"head WHERE company_party_id=?",String.class,f.company()));
        stored(db,first);stored(db,second);
    }
    @Test void competingRevisionTransactionsCommitOneEpochAndRollBackLoser() throws Exception {
        var db=jdbc();var f=populatedSources();var first=declaration(f,"r1",1,"request-1");
        revision(db,first,Map.of());assertEquals(1,select(db,first,0));
        var ready=new CountDownLatch(2);var go=new CountDownLatch(1);
        var pool=Executors.newFixedThreadPool(2);
        try {
            var futures=new ArrayList<Future<Boolean>>();
            for(String revision:List.of("r2-a","r2-b")) futures.add(pool.submit(()->{
                var candidate=declaration(f,revision,2,revision+"-request");
                ready.countDown();assertTrue(go.await(15,TimeUnit.SECONDS));
                try {
                    transaction(db).execute(status->{
                        revision(db,candidate,Map.of());
                        member(db,candidate,candidate.positions().get(0),Map.of());
                        receipt(db,candidate,Map.of());assertEquals(1,select(db,candidate,1));return null;
                    });
                    return true;
                } catch(DataAccessException rejected) {
                    Throwable cause=rejected.getMostSpecificCause();
                    assertInstanceOf(SQLException.class,cause);
                    assertEquals("23505",((SQLException)cause).getSQLState());return false;
                }
            }));
            assertTrue(ready.await(15,TimeUnit.SECONDS));go.countDown();
            int wins=0;for(var future:futures) if(future.get(30,TimeUnit.SECONDS)) wins++;
            assertEquals(1,wins);
        } finally {go.countDown();pool.shutdownNow();}
        assertEquals(2,count(db,"revision",f));assertEquals(1,count(db,"member",f));assertEquals(1,count(db,"request",f));
        assertEquals(2L,db.queryForObject("SELECT head_epoch FROM "+PREFIX+"head WHERE company_party_id=?",Long.class,f.company()));
        String winner=db.queryForObject("SELECT revision_id FROM "+PREFIX+"head WHERE company_party_id=?",String.class,f.company());
        stored(db,declaration(f,winner,2,winner+"-request"));stored(db,first);
    }
}
