/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcStationConfigurationSourceEvidenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Checks current station graph, ownership and installation witnesses under locked PostgreSQL sources.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.StationConfigurationSourceEvidencePort;
import dz.sh.hidra.modules.topology.domain.model.StationInstalledConfiguration;
import dz.sh.hidra.modules.topology.domain.model.StationInstalledConfiguration.Position;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * SELECT-only and fail-closed. Does not grant or acquire locks itself: the reviewed
 * B3-S2 coordinator must arrange restricted-role locks BEFORE this reader is used.
 * The explicit relation locks exclude phantoms while testing unoccupied positions.
 * It does not imply native Identity, Party, manufacturer or operational approval.
 */
@Repository
public final class JdbcStationConfigurationSourceEvidenceAdapter implements StationConfigurationSourceEvidencePort {
    private static final List<String> RELATIONS=List.of(
        "public.hidra_topology_equipment",
        "public.hidra_topology_facility",
        "public.hidra_topology_facility_owner_generation",
        "public.hidra_topology_installed_unit",
        "public.hidra_topology_installed_unit_installation",
        "public.hidra_topology_installed_unit_installation_event",
        "public.hidra_topology_station_graph_head",
        "public.hidra_topology_station_graph_revision");
    private static final String LOCKED="""
        SELECT count(*)=8 FROM (VALUES
        ('public.hidra_topology_equipment'),('public.hidra_topology_facility'),
        ('public.hidra_topology_facility_owner_generation'),('public.hidra_topology_installed_unit'),
        ('public.hidra_topology_installed_unit_installation'),
        ('public.hidra_topology_installed_unit_installation_event'),
        ('public.hidra_topology_station_graph_head'),
        ('public.hidra_topology_station_graph_revision')) AS needed(name)
        WHERE EXISTS (SELECT 1 FROM pg_catalog.pg_locks l
           WHERE l.pid=pg_catalog.pg_backend_pid() AND l.granted
             AND l.locktype='relation' AND l.mode='ShareRowExclusiveLock'
             AND l.relation=pg_catalog.to_regclass(needed.name))
        """;
    private final JdbcTemplate jdbc;
    private final DataSource dataSource;
    private final StationInternalGraphRevisionCodec graphCodec=new StationInternalGraphRevisionCodec();

    public JdbcStationConfigurationSourceEvidenceAdapter(JdbcTemplate jdbc){
        this.jdbc=Objects.requireNonNull(jdbc);this.dataSource=Objects.requireNonNull(jdbc.getDataSource());
    }

    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(StationInstalledConfiguration proposed){
        try{
            if(proposed==null)throw unavailable();
            ConnectionHolder h=context();guards();
            Ticket t=jdbc.queryForObject("SELECT pg_catalog.pg_backend_pid(),pg_catalog.txid_current(),current_user",
                (r,n)->new Ticket(proposed,h,r.getInt(1),r.getLong(2),r.getString(3)));
            if(t==null)throw unavailable();
            TransactionSynchronizationManager.registerSynchronization(t);
            return t;
        }catch(RuntimeException e){poison(null);throw e;}
    }

    @Override @Transactional(propagation=Propagation.MANDATORY)
    public SourceObservation read(Admission admission){
        try{
            Ticket t=ticket(admission);
            if(!t.used.compareAndSet(false,true))throw unavailable();
            t.observation=observe(t);
            ticket(t);return t.observation;
        }catch(RuntimeException e){poison(admission);throw e;}
    }

    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void requireCurrent(Admission admission){
        try{
            Ticket t=ticket(admission);
            if(t.observation==null || !t.observation.equals(observe(t)))throw unavailable();
            ticket(t);
        }catch(RuntimeException e){poison(admission);throw e;}
    }

    @Override
    public List<ProtectedRelationFence> finalFences(Admission admission){
        try{
            ticket(admission);
            return RELATIONS.stream().map(r->new ProtectedRelationFence(
                r,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE)).toList();
        }catch(RuntimeException e){poison(admission);throw e;}
    }

    private SourceObservation observe(Ticket ticket){
        StationInstalledConfiguration c=ticket.proposed;
        var owners=jdbc.query("""
            SELECT f.owner_party_id,f.status,f.facility_kind,g.generation,g.present,
                   pg_catalog.row_to_json(f)::text,pg_catalog.row_to_json(g)::text
            FROM public.hidra_topology_facility f JOIN
                 public.hidra_topology_facility_owner_generation g ON g.facility_id=f.id
            WHERE f.id=?
            """,(r,n)->new Owner(r.getString(1),r.getString(2),r.getString(3),
                r.getLong(4),r.getBoolean(5),r.getString(6),r.getString(7)),c.facilityId());
        if(owners.size()!=1)throw unavailable();
        Owner owner=owners.get(0);
        if(!c.companyPartyId().equals(owner.company) || !"ACTIVE".equals(owner.status)
            || !"STATION".equals(owner.kind) || !owner.present || owner.generation<1)throw unavailable();
        var heads=jdbc.query("""
            SELECT h.revision_id,h.head_epoch,h.state,r.payload_format,r.sha256,
                   r.canonical_payload,pg_catalog.row_to_json(h)::text
            FROM public.hidra_topology_station_graph_head h
            JOIN public.hidra_topology_station_graph_revision r
              ON r.facility_id=h.facility_id AND r.revision_id=h.revision_id
            WHERE h.facility_id=?
            """,(r,n)->new Graph(r.getString(1),r.getLong(2),r.getString(3),
                r.getString(4),r.getString(5),r.getBytes(6),r.getString(7)),c.facilityId());
        if(heads.size()!=1)throw unavailable();
        Graph graph=heads.get(0);
        if(!graph.revision.equals(c.pinnedGraph().revisionId()) || graph.epoch!=c.graphHeadEpoch()
           || !"ACTIVE".equals(graph.state) || !StationInternalGraphRevisionCodec.FORMAT.equals(graph.format)
           || !c.graphSha256().equals(graph.digest) || graph.bytes==null
           || !Arrays.equals(graph.bytes,graphCodec.encode(c.pinnedGraph()))
           || !graph.digest.equals(graphCodec.sha256(graph.bytes)))throw unavailable();
        StringBuilder image=new StringBuilder(owner.facilityImage).append('\n')
            .append(owner.generationImage).append('\n').append(graph.headImage)
            .append('\n').append(graph.digest).append('\n');
        for(Position p:c.positions()){
            var equipment=jdbc.query("""
                SELECT e.facility_id,e.equipment_type_id,e.node_id,e.pipeline_segment_id,
                       e.status,pg_catalog.row_to_json(e)::text
                FROM public.hidra_topology_equipment e WHERE e.id=?
                """,(r,n)->new Equipment(r.getString(1),r.getString(2),r.getString(3),
                    r.getString(4),r.getString(5),r.getString(6)),p.positionId());
            if(equipment.size()!=1)throw unavailable();
            Equipment e=equipment.get(0);
            if(!c.facilityId().equals(e.facility) || e.node!=null || e.segment!=null
                || !Set.of("PLANNED","ACTIVE").contains(e.status))throw unavailable();
            image.append(e.image).append('\n');
            if(p.disposition()==StationInstalledConfiguration.Disposition.UNOCCUPIED){
                Boolean empty=jdbc.queryForObject("""
                    SELECT NOT EXISTS(SELECT 1 FROM public.hidra_topology_installed_unit_installation i
                    WHERE i.position_id=?
                      AND pg_catalog.tstzrange(i.effective_from,i.effective_until,'[)')
                          && pg_catalog.tstzrange(?::timestamptz,?::timestamptz,'[)'))
                    """,Boolean.class,p.positionId(),Timestamp.from(c.effectiveFrom()),timestamp(c.effectiveUntil()));
                if(!Boolean.TRUE.equals(empty))throw unavailable();
                image.append("VACANT:").append(p.positionId()).append('\n');
                continue;
            }
            var units=jdbc.query("""
                SELECT i.unit_id,i.position_id,i.company_party_id,i.revision,
                       i.effective_from,i.effective_until,i.evidence_reference,u.equipment_kind_id,
                       v.unit_id,v.position_id,v.company_party_id,v.effective_from,v.effective_until,
                       v.evidence_reference,pg_catalog.row_to_json(i)::text,
                       pg_catalog.row_to_json(v)::text,pg_catalog.row_to_json(u)::text
                FROM public.hidra_topology_installed_unit_installation i
                JOIN public.hidra_topology_installed_unit u
                  ON u.id=i.unit_id AND u.company_party_id=i.company_party_id
                JOIN public.hidra_topology_installed_unit_installation_event v
                  ON v.installation_id=i.installation_id AND v.revision=i.revision
                WHERE i.installation_id=? AND i.unit_id=? AND i.position_id=?
                """,(r,n)->new Unit(r.getString(1),r.getString(2),r.getString(3),r.getLong(4),
                    instant(r.getTimestamp(5)),instant(r.getTimestamp(6)),r.getString(7),
                    r.getString(8),r.getString(9),r.getString(10),r.getString(11),
                    instant(r.getTimestamp(12)),instant(r.getTimestamp(13)),r.getString(14),
                    r.getString(15),r.getString(16),r.getString(17)),
                    p.installationId(),p.installedUnitId(),p.positionId());
            if(units.size()!=1)throw unavailable();
            Unit u=units.get(0);
            if(!p.installedUnitId().equals(u.unit) || !p.positionId().equals(u.position)
              || !c.companyPartyId().equals(u.company) || !e.type.equals(u.kind)
              || u.revision!=p.installationRevision() || !Objects.equals(u.from,p.installationFrom())
              || !Objects.equals(u.until,p.installationUntil())
              || !Objects.equals(u.evidence,p.installationEvidenceReference())
              || !u.unit.equals(u.eventUnit) || !u.position.equals(u.eventPosition)
              || !u.company.equals(u.eventCompany) || !Objects.equals(u.from,u.eventFrom)
              || !Objects.equals(u.until,u.eventUntil) || !Objects.equals(u.evidence,u.eventEvidence)
              || u.from.isAfter(c.effectiveFrom())
              || (u.until!=null && (c.effectiveUntil()==null || u.until.isBefore(c.effectiveUntil()))))
                throw unavailable();
            image.append(u.installationImage).append('\n').append(u.eventImage)
                .append('\n').append(u.unitImage).append('\n');
        }
        return new SourceObservation(c.facilityId(),c.companyPartyId(),graph.revision,graph.epoch,
                c.positions().size(),digest(image.toString()),ticket.pid,ticket.xid,ticket.role);
    }

    private void guards(){
        String schemas=jdbc.queryForObject(
            "SELECT pg_catalog.array_to_json(pg_catalog.current_schemas(true))::text",String.class);
        if(schemas==null || !schemas.startsWith("[\"pg_catalog\""))throw unavailable();
        Boolean okay=jdbc.queryForObject("""
            SELECT pg_catalog.current_setting('transaction_isolation')='read committed'
            AND pg_catalog.current_setting('transaction_read_only')='off'
            AND pg_catalog.current_setting('session_replication_role')='origin'
            AND (SELECT pg_catalog.count(*)=8 AND pg_catalog.bool_and(
                relkind='r' AND relpersistence='p' AND NOT relispartition
                AND NOT relrowsecurity AND NOT relforcerowsecurity)
                FROM pg_catalog.pg_class WHERE oid IN (
                pg_catalog.to_regclass('public.hidra_topology_equipment'),
                pg_catalog.to_regclass('public.hidra_topology_facility'),
                pg_catalog.to_regclass('public.hidra_topology_facility_owner_generation'),
                pg_catalog.to_regclass('public.hidra_topology_installed_unit'),
                pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation'),
                pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation_event'),
                pg_catalog.to_regclass('public.hidra_topology_station_graph_head'),
                pg_catalog.to_regclass('public.hidra_topology_station_graph_revision')))
            AND (""" + LOCKED + ")",Boolean.class);
        if(!Boolean.TRUE.equals(okay))throw unavailable();
    }
    private Ticket ticket(Admission admission){
        ConnectionHolder h=context();
        if(!(admission instanceof Ticket t) || t.issuer!=this || !t.active
            || t.thread!=Thread.currentThread() || t.holder!=h
            || t.connection!=h.getConnection())throw unavailable();
        guards();
        List<String> coords=jdbc.queryForObject(
            "SELECT pg_catalog.pg_backend_pid(),pg_catalog.txid_current(),current_user",
            (r,n)->List.of(r.getString(1),r.getString(2),r.getString(3)));
        if(!List.of(String.valueOf(t.pid),String.valueOf(t.xid),t.role).equals(coords))throw unavailable();
        return t;
    }
    private ConnectionHolder context(){
        if(!TransactionSynchronizationManager.isActualTransactionActive()
            || !TransactionSynchronizationManager.isSynchronizationActive()
            || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
            || !(TransactionSynchronizationManager.getResource(dataSource) instanceof ConnectionHolder h)
            || h.isRollbackOnly())throw unavailable();
        return h;
    }
    private void poison(Admission admission){
        if(admission instanceof Ticket t){t.active=false;t.holder.setRollbackOnly();}
        if(TransactionSynchronizationManager.getResource(dataSource) instanceof ConnectionHolder h)h.setRollbackOnly();
        try{TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();}
        catch(org.springframework.transaction.NoTransactionException ignored){/* TransactionTemplate case. */}
    }
    private static Instant instant(Timestamp value){return value==null?null:value.toInstant();}
    private static Timestamp timestamp(Instant value){return value==null?null:Timestamp.from(value);}
    private static String digest(String text){
        try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
            .digest(text.getBytes(StandardCharsets.UTF_8)));}
        catch(java.security.NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}
    }
    private static IllegalStateException unavailable(){return new IllegalStateException(
        "Protected station configuration source unavailable");}
    private record Owner(String company,String status,String kind,long generation,boolean present,
                         String facilityImage,String generationImage){}
    private record Graph(String revision,long epoch,String state,String format,String digest,byte[] bytes,String headImage){}
    private record Equipment(String facility,String type,String node,String segment,String status,String image){}
    private record Unit(String unit,String position,String company,long revision,Instant from,Instant until,
        String evidence,String kind,String eventUnit,String eventPosition,String eventCompany,
        Instant eventFrom,Instant eventUntil,String eventEvidence,
        String installationImage,String eventImage,String unitImage){}
    private final class Ticket implements Admission,TransactionSynchronization{
        private final JdbcStationConfigurationSourceEvidenceAdapter issuer=JdbcStationConfigurationSourceEvidenceAdapter.this;
        private final StationInstalledConfiguration proposed;
        private final ConnectionHolder holder;
        private final Connection connection;
        private final Thread thread=Thread.currentThread();
        private final int pid;
        private final long xid;
        private final String role;
        private final AtomicBoolean used=new AtomicBoolean();
        private volatile boolean active=true;
        private SourceObservation observation;
        private Ticket(StationInstalledConfiguration proposed,ConnectionHolder holder,int pid,long xid,String role){
            this.proposed=proposed;this.holder=holder;this.connection=holder.getConnection();
            this.pid=pid;this.xid=xid;this.role=role;
        }
        @Override public void suspend(){active=false;}
        @Override public void afterCompletion(int status){active=false;}
        @Override public void beforeCommit(boolean readOnly){
            if(!active || observation==null || holder.isRollbackOnly() || readOnly)throw unavailable();
        }
        @Override public String toString(){return "Admission[opaque station source]";}
    }
}
