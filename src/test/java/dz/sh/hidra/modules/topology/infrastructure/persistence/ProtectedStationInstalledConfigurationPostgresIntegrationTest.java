/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedStationInstalledConfigurationPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence
 *
 * @Description : Proves locked source qualification and fail-closed source drift on fresh synthetic PostgreSQL.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence;

import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.modules.topology.domain.model.StationInstalledConfiguration.*;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph.*;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision.*;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker=true)
class ProtectedStationInstalledConfigurationPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES =
        new PostgreSQLContainer<>("postgres:16").withCommand("postgres","-c","max_locks_per_transaction=256");
    private static volatile boolean migrated;
    private static final Instant FROM=Instant.parse("2026-02-01T00:00:00Z");
    private static final String LOCK_ALL="""
        LOCK TABLE public.hidra_topology_equipment,
          public.hidra_topology_facility,
          public.hidra_topology_facility_owner_generation,
          public.hidra_topology_installed_unit,
          public.hidra_topology_installed_unit_installation,
          public.hidra_topology_installed_unit_installation_event,
          public.hidra_topology_station_graph_head,
          public.hidra_topology_station_graph_revision
        IN SHARE ROW EXCLUSIVE MODE NOWAIT
        """;
    private static synchronized void migrate() {
        if(!migrated){
            Flyway.configure().dataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),
                POSTGRES.getPassword()).load().migrate();
            migrated=true;
        }
    }
    private JdbcTemplate jdbc() {
        migrate();
        var ds=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),
            POSTGRES.getPassword());
        ds.setDriverClassName("org.postgresql.Driver");
        return new JdbcTemplate(ds);
    }
    private static String id(){return UUID.randomUUID().toString();}
    private static TransactionTemplate tx(JdbcTemplate db){
        return new TransactionTemplate(new DataSourceTransactionManager(
            Objects.requireNonNull(db.getDataSource())));
    }
    private record Fixture(StationInstalledConfiguration declaration,String firstPosition){}
    private Fixture fixture(JdbcTemplate db,boolean published) { return fixture(db,published,false); }
    private Fixture fixture(JdbcTemplate db,boolean published,boolean vacantFirst) {
        String company=id(),facility=id(),facilityType=id(),kind=id();
        db.update("INSERT INTO public.hidra_topology_facility_type(id,code,name,facility_kind,status,created_at,updated_at)"
                +" VALUES(?,?,'Synthetic station','STATION','ACTIVE',now(),now())",
            facilityType,facilityType);
        db.update("INSERT INTO public.hidra_topology_equipment_type(id,code,name,status,created_at,updated_at)"
                +" VALUES(?,?,'Synthetic unit','ACTIVE',now(),now())",kind,kind);
        db.update("INSERT INTO public.hidra_topology_facility(id,code,facility_type_id,facility_kind,"
                +"owner_party_id,status,created_at,updated_at) VALUES(?,?,?,'STATION',?,'ACTIVE',now(),now())",
            facility,facility,facilityType,company);
        List<Unit> graphUnits=new ArrayList<>();
        List<Edge> graphEdges=new ArrayList<>();
        List<Position> positions=new ArrayList<>();
        String firstPosition=null;
        for(int i=0;i<3;i++){
            String position=id(),unit=id(),installation=id();
            if(i==0)firstPosition=position;
            db.update("INSERT INTO public.hidra_topology_equipment(id,code,facility_id,equipment_type_id,"
                    +"status,created_at,updated_at) VALUES(?,?,?,?,'PLANNED',now(),now())",
                position,position,facility,kind);
            if(!(vacantFirst && i==0)) db.update("INSERT INTO public.hidra_topology_installed_unit(id,company_party_id,equipment_kind_id,"
                    +"manufacturer_party_verification,manufacturer_model_verification,serial_verification)"
                    +" VALUES(?,?,?,'UNKNOWN','UNKNOWN','UNKNOWN')",unit,company,kind);
            if(!(vacantFirst && i==0)) db.update("INSERT INTO public.hidra_topology_installed_unit_installation(installation_id,"
                    +"unit_id,position_id,company_party_id,effective_from,recorded_at,evidence_reference,revision)"
                    +" VALUES(?,?,?,?,?,?,'synthetic:source',1)",
                installation,unit,position,company,Timestamp.from(FROM),Timestamp.from(FROM));
            graphUnits.add(new Unit(position,facility,"i"+i,"o"+i));
            graphEdges.add(new Edge("in"+i,"s","i"+i));
            graphEdges.add(new Edge("out"+i,"o"+i,"d"));
            positions.add(vacantFirst && i==0
                ? new Position(position,Disposition.UNOCCUPIED,null,null,0,null,null,null)
                : new Position(position,Disposition.CONNECTED,unit,installation,1,
                    FROM,null,"synthetic:source"));
        }
        var graph=new StationInternalGraph(facility,"graph-r1",Status.COMPLETE,
            List.of(new Header("suction",HeaderKind.SUCTION,"s"),
                    new Header("discharge",HeaderKind.DISCHARGE,"d")),graphUnits,graphEdges);
        var revision=new StationInternalGraphRevision(facility,"graph-r1",graph,
            SourceClassification.SYNTHETIC,FROM,FROM,null,"synthetic:graph",null,
            Qualification.STRUCTURALLY_QUALIFIED);
        var codec=new StationInternalGraphRevisionCodec();
        byte[] bytes=codec.encode(revision);
        db.update("INSERT INTO public.hidra_topology_station_graph_revision"
                +"(facility_id,revision_id,payload_format,canonical_payload,sha256) VALUES(?,?,?,?,?)",
            facility,"graph-r1",StationInternalGraphRevisionCodec.FORMAT,bytes,codec.sha256(bytes));
        if(published){
            UUID event=UUID.randomUUID();
            db.update("INSERT INTO public.hidra_topology_station_graph_operation_event("
                    +"event_id,facility_id,event_kind,resulting_epoch,resulting_revision_id,"
                    +"actor_binding_id,actor_session_id,actor_user_id,company_party_id,party_generation,"
                    +"facility_generation,sampled_at,valid_until,backend_pid,transaction_id)"
                    +" VALUES(?,?,'PUBLISH',1,?,?,?,?,?,?,?,?,?,?,?)",
                event,facility,"graph-r1","synthetic-binding","synthetic-session","synthetic-user",
                company,1L,1L,Timestamp.from(FROM),Timestamp.from(FROM.plusSeconds(3600)),
                1,1L);
            db.update("INSERT INTO public.hidra_topology_station_graph_head("
                    +"facility_id,revision_id,head_epoch,state,last_event_id)"
                    +" VALUES(?, ?,1,'ACTIVE',?)",facility,"graph-r1",event);
        }
        var configured=new StationInstalledConfiguration(company,facility,"config-r1",id(),0,1,1,
            revision,StationInternalGraphRevisionCodec.FORMAT,codec.sha256(bytes),
            FROM,null,FROM,"synthetic:configuration","synthetic-binding","synthetic-session",
            "synthetic-user",positions,graphEdges.stream().map(Edge::id)
                .filter(edge -> !vacantFirst || !edge.equals("in0") && !edge.equals("out0")).toList());
        return new Fixture(configured,firstPosition);
    }

    @Test void sourceReaderRejectsTransactionlessCall(){
        JdbcTemplate db=jdbc();
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        Fixture f=fixture(db,true);
        assertThrows(RuntimeException.class,()->adapter.acquire(f.declaration()));
    }

    @Test void absentSourceLocksRejectBeforeAnyProtectedEvidence(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,true);
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        tx(db).executeWithoutResult(s->{
            assertThrows(RuntimeException.class,()->adapter.acquire(f.declaration()));
            s.setRollbackOnly();
        });
    }

    @Test void selectedPublishedGraphAndInstallationEventsReloadUnderActualLocks(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,true);
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        tx(db).executeWithoutResult(s->{
            db.execute(LOCK_ALL);
            var admission=adapter.acquire(f.declaration());
            var first=adapter.read(admission);
            assertEquals(3,first.positionCount());
            assertEquals(f.declaration().companyPartyId(),first.companyPartyId());
            assertEquals(f.declaration().graphSha256().length(),first.sourceSha256().length());
            assertEquals(8,adapter.finalFences(admission).size());
            adapter.requireCurrent(admission);
        });
    }

    @Test void graphWithoutActivePublicationFailsClosedEvenWithLocks(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,false);
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        tx(db).executeWithoutResult(s->{
            db.execute(LOCK_ALL);
            var ticket=adapter.acquire(f.declaration());
            assertThrows(RuntimeException.class,()->adapter.read(ticket));
            s.setRollbackOnly();
        });
    }

    @Test void sourceChangesAfterObservationPoisonOriginalTransaction(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,true);
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        tx(db).executeWithoutResult(s->{
            db.execute(LOCK_ALL);
            var ticket=adapter.acquire(f.declaration());
            adapter.read(ticket);
            db.update("UPDATE public.hidra_topology_equipment SET status='ACTIVE' WHERE id=?",f.firstPosition());
            assertThrows(RuntimeException.class,()->adapter.requireCurrent(ticket));
            s.setRollbackOnly();
        });
        assertEquals("PLANNED",db.queryForObject(
            "SELECT status FROM public.hidra_topology_equipment WHERE id=?",String.class,f.firstPosition()));
    }

    private static StationInstalledConfiguration cloneScope(StationInstalledConfiguration c,
            String company,long graphEpoch) {
        return new StationInstalledConfiguration(company,c.facilityId(),c.revisionId(),c.requestId(),
            c.expectedEpoch(),c.selectedEpoch(),graphEpoch,c.pinnedGraph(),
            c.graphPayloadFormat(),c.graphSha256(),c.effectiveFrom(),c.effectiveUntil(),
            c.recordedAt(),c.sourceReference(),c.actorBindingId(),c.actorSessionId(),
            c.actorUserId(),c.positions(),c.activeEdgeIds());
    }

    @Test void wrongCompanyCannotReadStationSourceEvenWithLocks(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,true);
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        var wrong=cloneScope(f.declaration(),id(),1);
        tx(db).executeWithoutResult(s->{
            db.execute(LOCK_ALL);
            var ticket=adapter.acquire(wrong);
            assertThrows(RuntimeException.class,()->adapter.read(ticket));
            s.setRollbackOnly();
        });
    }

    @Test void staleGraphHeadEpochCannotBeTreatedAsCurrent(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,true);
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        var stale=cloneScope(f.declaration(),f.declaration().companyPartyId(),2);
        tx(db).executeWithoutResult(s->{
            db.execute(LOCK_ALL);
            var ticket=adapter.acquire(stale);
            assertThrows(RuntimeException.class,()->adapter.read(ticket));
            s.setRollbackOnly();
        });
    }

    @Test void positionMovedToAnotherStationCannotBeRetainedAsSource(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,true);Fixture other=fixture(db,true);
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        tx(db).executeWithoutResult(s->{
            db.execute(LOCK_ALL);
            db.update("UPDATE public.hidra_topology_equipment SET facility_id=? WHERE id=?",
                other.declaration().facilityId(),f.firstPosition());
            var ticket=adapter.acquire(f.declaration());
            assertThrows(RuntimeException.class,()->adapter.read(ticket));
            s.setRollbackOnly();
        });
        assertEquals(f.declaration().facilityId(),db.queryForObject(
            "SELECT facility_id FROM public.hidra_topology_equipment WHERE id=?",
            String.class,f.firstPosition()));
    }

    @Test void closedAndRevisedInstallationCannotMatchStaleWitness(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,true);
        String installation=f.declaration().positions().stream()
            .filter(p->p.positionId().equals(f.firstPosition()))
            .findFirst().orElseThrow().installationId();
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        tx(db).executeWithoutResult(s->{
            db.execute(LOCK_ALL);
            db.update("UPDATE public.hidra_topology_installed_unit_installation "
                +"SET effective_until=?,revision=revision+1 WHERE installation_id=?",
                Timestamp.from(FROM.plusSeconds(3600)),installation);
            var ticket=adapter.acquire(f.declaration());
            assertThrows(RuntimeException.class,()->adapter.read(ticket));
            s.setRollbackOnly();
        });
        assertEquals(1L,db.queryForObject(
            "SELECT revision FROM public.hidra_topology_installed_unit_installation "
                +"WHERE installation_id=?",Long.class,installation));
    }

    @Test void newlyOccupiedVacantPositionInvalidatesOriginalSourceTicket(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,true,true);
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        tx(db).executeWithoutResult(s->{
            db.execute(LOCK_ALL);
            var ticket=adapter.acquire(f.declaration());
            var original=adapter.read(ticket);
            assertEquals(3,original.positionCount());
            String unit=id();
            String kind=db.queryForObject("SELECT equipment_type_id FROM public.hidra_topology_equipment "
                +"WHERE id=?",String.class,f.firstPosition());
            db.update("INSERT INTO public.hidra_topology_installed_unit(id,company_party_id,equipment_kind_id,"
                +"manufacturer_party_verification,manufacturer_model_verification,serial_verification)"
                +" VALUES(?,?,?,'UNKNOWN','UNKNOWN','UNKNOWN')",
                unit,f.declaration().companyPartyId(),kind);
            db.update("INSERT INTO public.hidra_topology_installed_unit_installation(installation_id,"
                +"unit_id,position_id,company_party_id,effective_from,recorded_at,evidence_reference,revision)"
                +" VALUES(?,?,?,?,?,?,'synthetic:new-occupancy',1)",
                id(),unit,f.firstPosition(),f.declaration().companyPartyId(),
                Timestamp.from(FROM),Timestamp.from(FROM));
            assertThrows(RuntimeException.class,()->adapter.requireCurrent(ticket));
            s.setRollbackOnly();
        });
        assertEquals(0,db.queryForObject(
            "SELECT count(*) FROM public.hidra_topology_installed_unit_installation "
                +"WHERE position_id=?",Integer.class,f.firstPosition()));
    }

    @Test void catalogColumnMutationFailsClosedAndRestoresOnRollback(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,true);
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        tx(db).executeWithoutResult(s->{
            db.execute(LOCK_ALL);
            var ticket=adapter.acquire(f.declaration());
            db.execute("ALTER TABLE public.hidra_topology_station_graph_revision "
                +"RENAME COLUMN sha256 TO synthetic_missing_digest");
            assertThrows(RuntimeException.class,()->adapter.read(ticket));
            s.setRollbackOnly();
        });
        assertEquals("sha256",db.queryForObject("""
            SELECT attname FROM pg_catalog.pg_attribute
            WHERE attrelid='public.hidra_topology_station_graph_revision'::regclass
            AND attname='sha256' AND attnum>0 AND NOT attisdropped
            """,String.class));
    }

    @Test void roleChangeAfterSourceReadInvalidatesIssuerCoordinates(){
        JdbcTemplate db=jdbc();Fixture f=fixture(db,true);
        var adapter=new JdbcStationConfigurationSourceEvidenceAdapter(db);
        tx(db).executeWithoutResult(s->{
            db.execute(LOCK_ALL);
            var ticket=adapter.acquire(f.declaration());
            adapter.read(ticket);
            db.execute("SET LOCAL ROLE pg_monitor");
            assertThrows(RuntimeException.class,()->adapter.requireCurrent(ticket));
            s.setRollbackOnly();
        });
    }
}
