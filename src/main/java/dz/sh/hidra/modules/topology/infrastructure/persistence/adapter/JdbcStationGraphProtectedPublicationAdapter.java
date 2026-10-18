/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcStationGraphProtectedPublicationAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-18
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Protects immutable revisions and serialized Topology head transitions.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.StationGraphProtectedPublicationPort;
import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
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
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Repository
public final class JdbcStationGraphProtectedPublicationAdapter implements StationGraphProtectedPublicationPort {
    private static final String REV_INS="INSERT INTO public.hidra_topology_station_graph_revision (facility_id,revision_id,payload_format,canonical_payload,sha256) VALUES (?,?,?,?,?)";
    private static final String EVT_INS="INSERT INTO public.hidra_topology_station_graph_operation_event (event_id,facility_id,event_kind,resulting_epoch,prior_revision_id,resulting_revision_id,actor_binding_id,actor_session_id,actor_user_id,company_party_id,party_generation,facility_generation,sampled_at,valid_until,backend_pid,transaction_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String HEAD_INS="INSERT INTO public.hidra_topology_station_graph_head (facility_id,revision_id,head_epoch,state,last_event_id) VALUES (?,?,?,?,?)";
    private static final String HEAD_CAS="UPDATE public.hidra_topology_station_graph_head SET revision_id=?,head_epoch=?,state=?,last_event_id=? WHERE facility_id=? AND head_epoch=? AND revision_id=? AND state=?";
    private static final Set<String> MUTATIONS=Set.of(REV_INS,EVT_INS,HEAD_INS,HEAD_CAS);
    private static final String GUARD="""
        WITH sampled_clock AS MATERIALIZED (SELECT pg_catalog.clock_timestamp() AS t)
        SELECT ((SELECT t FROM sampled_clock)>=?::timestamptz)
          AND ((SELECT t FROM sampled_clock)<?::timestamptz)
          AND pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=?
          AND current_user=?
          AND EXISTS(SELECT 1 FROM public.hidra_party_eligibility_generation g WHERE g.party_id=? AND g.generation=? AND g.present)
          AND EXISTS(SELECT 1 FROM public.hidra_topology_facility f
            JOIN public.hidra_topology_facility_owner_generation g ON g.facility_id=f.id
            WHERE f.id=? AND f.owner_party_id=? AND g.generation=? AND g.present)
          AND ((?::bigint IS NULL AND NOT EXISTS(SELECT 1 FROM public.hidra_topology_station_graph_head h WHERE h.facility_id=?))
               OR EXISTS(SELECT 1 FROM public.hidra_topology_station_graph_head h WHERE h.facility_id=?
                 AND h.head_epoch=? AND h.revision_id=? AND h.state=? AND h.last_event_id=?::uuid))
          AND (?::uuid IS NULL OR EXISTS(SELECT 1 FROM public.hidra_topology_station_graph_operation_event e
             WHERE e.facility_id=? AND e.event_id=?::uuid))
        """;
    private final JdbcTemplate jdbc;
    private final DataSource source;
    private final ProtectedTransactionExecutor executor;
    private final StationInternalGraphRevisionCodec codec=new StationInternalGraphRevisionCodec();

    public JdbcStationGraphProtectedPublicationAdapter(JdbcTemplate jdbc,ProtectedTransactionExecutor executor){
        this.jdbc=Objects.requireNonNull(jdbc);source=Objects.requireNonNull(jdbc.getDataSource());
        this.executor=Objects.requireNonNull(executor);
    }
    private final class Ticket implements Admission,TransactionSynchronization {
        final String facility,role; final ConnectionHolder holder;final Connection physical;
        final int pid;final long xid;final Thread thread=Thread.currentThread();
        final AtomicBoolean active=new AtomicBoolean(true);
        Ticket(String facility,String role,ConnectionHolder holder,int pid,long xid) {
            this.facility=facility;this.role=role;this.holder=holder;physical=holder.getConnection();this.pid=pid;this.xid=xid;
        }
        @Override public void suspend(){active.set(false);}
        @Override public void afterCompletion(int status){active.set(false);}
    }
    private static IllegalStateException invalid(){return new IllegalStateException("Protected Topology publication unavailable");}
    private ConnectionHolder physical(){
        if(!TransactionSynchronizationManager.isActualTransactionActive() ||
           !TransactionSynchronizationManager.isSynchronizationActive() ||
           TransactionSynchronizationManager.isCurrentTransactionReadOnly() ||
           !(TransactionSynchronizationManager.getResource(source) instanceof ConnectionHolder holder) ||
           holder.isRollbackOnly())throw invalid();
        return holder;
    }
    private Ticket ticket(Admission admission){
        ConnectionHolder h=physical();
        if(!(admission instanceof Ticket t) || t.thread!=Thread.currentThread() ||
            !t.active.get() || t.holder!=h || t.physical!=h.getConnection()) {
            executor.rejectAdmission();throw invalid();
        }
        return t;
    }
    private Ticket observed(Admission a){
        Ticket t=ticket(a);
        if(!Boolean.TRUE.equals(jdbc.queryForObject(
            "SELECT pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=? AND current_user=?",
            Boolean.class,t.pid,t.xid,t.role)))throw invalid();
        return t;
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(String facilityId) {
        try {
            String id=StationGraphPublicationHead.identity(facilityId);
            ConnectionHolder h=physical();
            jdbc.execute("LOCK TABLE public.hidra_topology_station_graph_revision IN SHARE ROW EXCLUSIVE MODE NOWAIT");
            jdbc.execute("LOCK TABLE public.hidra_topology_station_graph_head IN SHARE ROW EXCLUSIVE MODE NOWAIT");
            jdbc.execute("LOCK TABLE public.hidra_topology_station_graph_operation_event IN SHARE ROW EXCLUSIVE MODE NOWAIT");
            Ticket t=jdbc.queryForObject("SELECT pg_catalog.pg_backend_pid(),pg_catalog.txid_current(),current_user",
                (r,n)->new Ticket(id,r.getString(3),h,r.getInt(1),r.getLong(2)));
            if(t==null)throw invalid();
            TransactionSynchronizationManager.registerSynchronization(t);
            return t;
        }catch(RuntimeException e){executor.rejectAdmission();throw e;}
    }
    @Override public Set<String> mutationSql(){return MUTATIONS;}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<StationGraphPublicationHead> head(Admission a){
        Ticket t=observed(a);
        var rows=jdbc.query("SELECT facility_id,revision_id,head_epoch,state,last_event_id FROM public.hidra_topology_station_graph_head WHERE facility_id=?",
           (r,n)->new StationGraphPublicationHead(r.getString(1),r.getString(2),r.getLong(3),
             StationGraphPublicationHead.State.valueOf(r.getString(4)),(UUID)r.getObject(5)),t.facility);
        if(rows.size()>1)throw invalid();
        return rows.stream().findFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<StationInternalGraphRevision> revision(Admission a,String revisionId){
        Ticket t=observed(a);String rev=StationGraphPublicationHead.identity(revisionId);
        var rows=jdbc.query("SELECT facility_id,revision_id,payload_format,canonical_payload,sha256 FROM public.hidra_topology_station_graph_revision WHERE facility_id=? AND revision_id=?",
            (r,n)->{
                byte[] bytes=r.getBytes(4);
                if(bytes==null || !StationInternalGraphRevisionCodec.FORMAT.equals(r.getString(3))
                   || !codec.sha256(bytes).equals(r.getString(5)))throw invalid();
                StationInternalGraphRevision value=codec.decode(bytes);
                if(!t.facility.equals(r.getString(1))||!rev.equals(r.getString(2))||
                   !t.facility.equals(value.facilityId())||!rev.equals(value.revisionId()))throw invalid();
                return value;
            },t.facility,rev);
        if(rows.size()>1)throw invalid();return rows.stream().findFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public List<StationGraphPublicationEvent> history(Admission a,int limit){
        Ticket t=observed(a);if(limit<1 || limit>100)throw invalid();
        return List.copyOf(jdbc.query("""
          SELECT event_id,facility_id,event_kind,resulting_epoch,prior_revision_id,resulting_revision_id,
            actor_binding_id,actor_session_id,actor_user_id,company_party_id,party_generation,
            facility_generation,sampled_at,valid_until,backend_pid,transaction_id
          FROM public.hidra_topology_station_graph_operation_event WHERE facility_id=?
          ORDER BY resulting_epoch DESC NULLS LAST,event_id DESC LIMIT ?
          """,(r,n)->new StationGraphPublicationEvent((UUID)r.getObject(1),r.getString(2),
            StationGraphPublicationEvent.Kind.valueOf(r.getString(3)),(Long)r.getObject(4),
            r.getString(5),r.getString(6),r.getString(7),r.getString(8),r.getString(9),
            r.getString(10),r.getLong(11),r.getLong(12),r.getTimestamp(13).toInstant(),
            r.getTimestamp(14).toInstant(),r.getInt(15),r.getLong(16)),t.facility,limit));
    }
    private void event(Ticket t,StationGraphPublicationEvent e){
        if(!t.facility.equals(e.facilityId()) || e.backendPid()!=t.pid || e.transactionId()!=t.xid)throw invalid();
        if(jdbc.update(EVT_INS,e.eventId(),e.facilityId(),e.kind().name(),e.resultingEpoch(),
          e.priorRevisionId(),e.resultingRevisionId(),e.actorBindingId(),e.actorSessionId(),
          e.actorUserId(),e.companyPartyId(),e.partyGeneration(),e.facilityGeneration(),
          Timestamp.from(e.sampledAt()),Timestamp.from(e.validUntil()),e.backendPid(),e.transactionId())!=1)throw invalid();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void append(Admission a,StationInternalGraphRevision revision,StationGraphPublicationEvent e){
        try {
            Ticket t=ticket(a);
            if(e.kind()!=StationGraphPublicationEvent.Kind.AUTHOR || !t.facility.equals(revision.facilityId()) ||
               !revision.revisionId().equals(e.resultingRevisionId()))throw invalid();
            byte[] bytes=codec.encode(revision);
            if(jdbc.update(REV_INS,revision.facilityId(),revision.revisionId(),
                StationInternalGraphRevisionCodec.FORMAT,bytes,codec.sha256(bytes))!=1)throw invalid();
            event(t,e);
        }catch(RuntimeException error){executor.rejectAdmission();throw error;}
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public StationGraphPublicationHead change(Admission a,StationGraphPublicationHead next,
         StationGraphPublicationEvent e,Long expectedEpoch,String expectedRevisionId){
        try {
            Ticket t=ticket(a);
            if(!t.facility.equals(next.facilityId()) || !e.eventId().equals(next.lastEventId()) ||
               !Objects.equals(e.resultingEpoch(),next.epoch()) ||
               !e.resultingRevisionId().equals(next.revisionId()) ||
               e.kind()==StationGraphPublicationEvent.Kind.AUTHOR)throw invalid();
            event(t,e);int n;
            if(expectedEpoch!=null && expectedEpoch==0 && expectedRevisionId==null && next.epoch()==1)
                n=jdbc.update(HEAD_INS,next.facilityId(),next.revisionId(),
                    next.epoch(),next.state().name(),next.lastEventId());
            else {
                if(expectedEpoch==null || expectedEpoch<=0 || expectedEpoch==Long.MAX_VALUE ||
                   expectedRevisionId==null || next.epoch()!=expectedEpoch+1)throw invalid();
                n=jdbc.update(HEAD_CAS,next.revisionId(),next.epoch(),next.state().name(),
                    next.lastEventId(),next.facilityId(),expectedEpoch,expectedRevisionId,
                    e.kind()==StationGraphPublicationEvent.Kind.REACTIVATE?"WITHDRAWN":"ACTIVE");
            }
            if(n!=1)throw invalid();
            return next;
        }catch(RuntimeException error){executor.rejectAdmission();throw error;}
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public boolean finalGuard(Admission a,Instant s,Instant u,String companyPartyId,
              long partyGeneration,long facilityGeneration,StationGraphPublicationHead head,UUID eventId){
        try {
            Ticket t=ticket(a); // Do not perform a second query before the one final scalar SQL.
            Boolean b=jdbc.queryForObject(GUARD,Boolean.class,Timestamp.from(s),Timestamp.from(u),
                t.pid,t.xid,t.role,companyPartyId,partyGeneration,t.facility,companyPartyId,facilityGeneration,
                head==null?null:head.epoch(),t.facility,t.facility,
                head==null?null:head.epoch(),head==null?null:head.revisionId(),
                head==null?null:head.state().name(),head==null?null:head.lastEventId(),
                eventId,t.facility,eventId);
            if(!Boolean.TRUE.equals(b))throw invalid();return true;
        }catch(RuntimeException error){executor.rejectAdmission();throw error;}
    }
}
