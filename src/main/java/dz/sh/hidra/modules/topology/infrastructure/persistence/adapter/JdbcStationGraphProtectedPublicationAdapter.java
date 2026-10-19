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
import jakarta.persistence.EntityManager;
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
public class JdbcStationGraphProtectedPublicationAdapter implements StationGraphProtectedPublicationPort {
    private static final String REV_INS="INSERT INTO public.hidra_topology_station_graph_revision (facility_id,revision_id,payload_format,canonical_payload,sha256) VALUES (?,?,?,?,?)";
    private static final String EVT_INS="INSERT INTO public.hidra_topology_station_graph_operation_event (event_id,facility_id,event_kind,resulting_epoch,prior_revision_id,resulting_revision_id,actor_binding_id,actor_session_id,actor_user_id,company_party_id,party_generation,facility_generation,sampled_at,valid_until,backend_pid,transaction_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String HEAD_INS="INSERT INTO public.hidra_topology_station_graph_head (facility_id,revision_id,head_epoch,state,last_event_id) VALUES (?,?,?,?,?)";
    private static final String HEAD_CAS="UPDATE public.hidra_topology_station_graph_head SET revision_id=?,head_epoch=?,state=?,last_event_id=? WHERE facility_id=? AND head_epoch=? AND revision_id=? AND state=?";
    private static final Set<String> MUTATIONS=Set.of(REV_INS,EVT_INS,HEAD_INS,HEAD_CAS);
    private static final String CATALOG="""
        SELECT pg_catalog.current_setting('session_replication_role')='origin'
          AND NOT pg_catalog.has_schema_privilege(current_user,'public','CREATE')
          AND NOT pg_catalog.has_schema_privilege(current_user,'pg_catalog','CREATE')
          AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_roles r WHERE
            (r.rolname=current_user OR pg_catalog.pg_has_role(current_user,r.oid,'MEMBER'))
            AND (r.rolsuper OR r.rolbypassrls))
          AND (SELECT count(*)=3 AND bool_and(c.relkind='r' AND c.relpersistence='p'
            AND NOT c.relrowsecurity AND NOT c.relforcerowsecurity AND NOT c.relispartition
            AND NOT pg_catalog.pg_has_role(current_user,c.relowner,'MEMBER')
            AND NOT pg_catalog.has_table_privilege(current_user,c.oid,'TRIGGER,TRUNCATE'))
            FROM pg_catalog.pg_class c WHERE c.oid IN
              ('public.hidra_topology_station_graph_revision'::regclass,
               'public.hidra_topology_station_graph_head'::regclass,
               'public.hidra_topology_station_graph_operation_event'::regclass))
          AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_inherits WHERE inhrelid IN
              ('public.hidra_topology_station_graph_revision'::regclass,
               'public.hidra_topology_station_graph_head'::regclass,
               'public.hidra_topology_station_graph_operation_event'::regclass)
             OR inhparent IN ('public.hidra_topology_station_graph_revision'::regclass,
               'public.hidra_topology_station_graph_head'::regclass,
               'public.hidra_topology_station_graph_operation_event'::regclass))
          AND (SELECT count(*)=2 AND bool_and(prosecdef AND prokind='f' AND pronargs=0
            AND prorettype='trigger'::regtype AND provolatile='v' AND NOT proleakproof
            AND prolang=(SELECT oid FROM pg_catalog.pg_language WHERE lanname='plpgsql')
            AND proconfig=ARRAY['search_path=pg_catalog, pg_temp']
            AND NOT pg_catalog.pg_has_role(current_user,proowner,'MEMBER')
            AND NOT pg_catalog.has_function_privilege(current_user,oid,'EXECUTE')
            AND ((oid='public.hidra_p25_graph_immutable_event()'::regprocedure AND md5(prosrc)='1f6d2a85a7700611e4cdbe4d2a0b3a38')
              OR (oid='public.hidra_p25_graph_head_transition()'::regprocedure AND md5(prosrc)='b73d0be2d588f6f0e097314d3baf13e9')))
            FROM pg_catalog.pg_proc WHERE oid IN ('public.hidra_p25_graph_immutable_event()'::regprocedure,
              'public.hidra_p25_graph_head_transition()'::regprocedure))
          AND (SELECT count(*)=21 AND bool_and(k.convalidated AND NOT k.condeferrable)
            AND md5(string_agg(c.relname||'|'||k.conname||'|'||pg_catalog.pg_get_constraintdef(k.oid,true),
                E'\\n' ORDER BY c.relname COLLATE "C",k.conname COLLATE "C"))='0d3e9e1234818df1668b281f15df936f'
            FROM pg_catalog.pg_constraint k JOIN pg_catalog.pg_class c ON c.oid=k.conrelid
            WHERE k.conrelid IN ('public.hidra_topology_station_graph_revision'::regclass,
                'public.hidra_topology_station_graph_head'::regclass,
                'public.hidra_topology_station_graph_operation_event'::regclass))
          AND (SELECT count(*)=7 AND bool_and(tgenabled='O' AND tgqual IS NULL AND tgnargs=0
            AND tgconstraint=0 AND tgparentid=0 AND cardinality(tgattr::smallint[])=0 AND
            ((tgrelid='public.hidra_topology_station_graph_revision'::regclass AND
                tgfoid='public.hidra_topology_reject_station_graph_revision_mutation()'::regprocedure AND
                ((tgname='topology_station_graph_revision_no_update_delete' AND tgtype=27)
                 OR (tgname='topology_station_graph_revision_no_truncate' AND tgtype=34)))
             OR (tgrelid='public.hidra_topology_station_graph_operation_event'::regclass AND
                tgfoid='public.hidra_p25_graph_immutable_event()'::regprocedure AND
                ((tgname='trg_p25_graph_event_immutable' AND tgtype=27)
                 OR (tgname='trg_p25_graph_event_no_truncate' AND tgtype=34)))
             OR (tgrelid='public.hidra_topology_station_graph_head'::regclass AND
                ((tgname='trg_p25_graph_head_transition' AND tgtype=23 AND
                    tgfoid='public.hidra_p25_graph_head_transition()'::regprocedure)
                 OR (tgname='trg_p25_graph_head_no_delete' AND tgtype=11 AND
                    tgfoid='public.hidra_p25_graph_immutable_event()'::regprocedure)
                 OR (tgname='trg_p25_graph_head_no_truncate' AND tgtype=34 AND
                    tgfoid='public.hidra_p25_graph_immutable_event()'::regprocedure)))))
            FROM pg_catalog.pg_trigger WHERE NOT tgisinternal AND tgrelid IN
              ('public.hidra_topology_station_graph_revision'::regclass,
               'public.hidra_topology_station_graph_head'::regclass,
               'public.hidra_topology_station_graph_operation_event'::regclass))
        """;
    private static final String ORDINARY_DENIED="""
        SELECT session_user=? AND current_user=? AND ?<>?
          AND NOT pg_catalog.pg_has_role(?::name,?::name,'MEMBER')
          AND NOT pg_catalog.has_schema_privilege(?::name,'public','CREATE')
          AND NOT pg_catalog.has_schema_privilege(?::name,'pg_catalog','CREATE')
          AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_roles r
            WHERE (r.rolname=?::name OR pg_catalog.pg_has_role(?::name,r.oid,'MEMBER'))
              AND (r.rolsuper OR r.rolbypassrls OR r.rolcreaterole OR r.rolcreatedb OR r.rolreplication
                OR r.rolname IN ('pg_execute_server_program','pg_read_server_files','pg_write_server_files')))
          AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_class c
            WHERE c.oid IN ('public.hidra_topology_station_graph_head'::regclass,
                'public.hidra_topology_station_graph_operation_event'::regclass)
              AND (pg_catalog.pg_has_role(?::name,c.relowner,'MEMBER')
                OR pg_catalog.has_table_privilege(?::name,c.oid,'INSERT,UPDATE,DELETE,TRUNCATE,TRIGGER')
                OR pg_catalog.has_any_column_privilege(?::name,c.oid,'INSERT,UPDATE,REFERENCES')))
        """;
    private static final String RETAINED="""
        (SELECT count(DISTINCT relation)=3 FROM pg_catalog.pg_locks
          WHERE pid=pg_catalog.pg_backend_pid() AND granted AND locktype='relation'
            AND mode='ShareRowExclusiveLock' AND relation IN
              ('public.hidra_topology_station_graph_revision'::regclass,
               'public.hidra_topology_station_graph_head'::regclass,
               'public.hidra_topology_station_graph_operation_event'::regclass))
        """;
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
        """+" AND "+RETAINED+" AND NOT EXISTS (SELECT 1 FROM public.hidra_topology_station_graph_revision r "
            +"WHERE r.facility_id=? AND r.sha256<>pg_catalog.encode(pg_catalog.sha256(r.canonical_payload),'hex'))";
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final DataSource source;
    private final ProtectedTransactionExecutor executor;
    private final StationInternalGraphRevisionCodec codec=new StationInternalGraphRevisionCodec();

    public JdbcStationGraphProtectedPublicationAdapter(JdbcTemplate jdbc,ProtectedTransactionExecutor executor,EntityManager em){
        this.jdbc=Objects.requireNonNull(jdbc);source=Objects.requireNonNull(jdbc.getDataSource());
        this.executor=Objects.requireNonNull(executor);this.em=Objects.requireNonNull(em);
    }
    private final class Ticket implements Admission,TransactionSynchronization {
        final JdbcStationGraphProtectedPublicationAdapter issuer=JdbcStationGraphProtectedPublicationAdapter.this;
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
    private boolean compiledCaller(){
        return StackWalker.getInstance().walk(frames->frames.anyMatch(f->f.getClassName().equals(
            "dz.sh.hidra.modules.topology.application.service.ProtectedStationGraphOperationService")));
    }
    private ConnectionHolder physical(){
        if(!compiledCaller()) {executor.rejectAdmission();throw invalid();}
        if(!TransactionSynchronizationManager.isActualTransactionActive() ||
           !TransactionSynchronizationManager.isSynchronizationActive() ||
           TransactionSynchronizationManager.isCurrentTransactionReadOnly() ||
           !(TransactionSynchronizationManager.getResource(source) instanceof ConnectionHolder holder) ||
           holder.isRollbackOnly())throw invalid();
        return holder;
    }
    private Ticket ticket(Admission admission){
        ConnectionHolder h=physical();
        if(!(admission instanceof Ticket t) || t.issuer!=this || t.thread!=Thread.currentThread() ||
            !t.active.get() || t.holder!=h || t.physical!=h.getConnection()) {
            executor.rejectAdmission();throw invalid();
        }
        return t;
    }
    private Ticket observed(Admission a){
        Ticket t=ticket(a);
        if(!Boolean.TRUE.equals(jdbc.queryForObject(
            "SELECT pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=? AND current_user=? AND "+RETAINED,
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
            if(!Boolean.TRUE.equals(jdbc.queryForObject(CATALOG,Boolean.class)))throw invalid();
            var principals=executor.publicationDatabaseIdentity();
            String ordinary=principals.ordinaryUser(),publisher=principals.publisherUser();
            if(!Boolean.TRUE.equals(jdbc.queryForObject(ORDINARY_DENIED,Boolean.class,
                publisher,publisher,ordinary,publisher,ordinary,publisher,ordinary,ordinary,
                ordinary,ordinary,ordinary,ordinary,ordinary)))throw invalid();
            Ticket t=jdbc.queryForObject("SELECT pg_catalog.pg_backend_pid(),pg_catalog.txid_current(),current_user",
                (r,n)->new Ticket(id,r.getString(3),h,r.getInt(1),r.getLong(2)));
            if(t==null)throw invalid();
            TransactionSynchronizationManager.registerSynchronization(t);
            return t;
        }catch(RuntimeException e){executor.rejectAdmission();throw e;}
    }
    @Override public Set<String> mutationSql(){return MUTATIONS;}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void flush(){physical();em.flush();}
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
              long partyGeneration,long facilityGeneration,StationGraphPublicationHead head,UUID eventId,
              List<dz.sh.hidra.platform.persistence.ProtectedRelationFence> ownerFences){
        try {
            Ticket t=ticket(a); // No SQL before the one final scalar statement.
            if(ownerFences==null || ownerFences.size()!=28)throw invalid();
            var fences=new ArrayList<>(ownerFences);
            for(String relation:List.of("public.hidra_topology_station_graph_revision",
                "public.hidra_topology_station_graph_head","public.hidra_topology_station_graph_operation_event"))
                fences.add(new dz.sh.hidra.platform.persistence.ProtectedRelationFence(relation,
                    dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
            if(fences.stream().map(dz.sh.hidra.platform.persistence.ProtectedRelationFence::relation).distinct().count()!=31
                || fences.stream().filter(f->f.mode()==dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE).count()!=27
                || fences.stream().filter(f->f.mode()==dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.ACCESS_SHARE).count()!=3
                || fences.stream().filter(f->f.mode()==dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.SHARE).count()!=1)throw invalid();
            String required=String.join(",",Collections.nCopies(fences.size(),"(?::regclass,?::text)"));
            String complete=" AND session_user=? AND NOT EXISTS (SELECT 1 FROM (VALUES "+required+") required(relation,mode) "
                +"WHERE NOT EXISTS (SELECT 1 FROM pg_catalog.pg_locks l WHERE l.pid=pg_catalog.pg_backend_pid() "
                +"AND l.granted AND l.locktype='relation' AND l.relation=required.relation AND l.mode=required.mode))";
            var args=new ArrayList<Object>(Arrays.asList(Timestamp.from(s),Timestamp.from(u),
                t.pid,t.xid,t.role,companyPartyId,partyGeneration,t.facility,companyPartyId,facilityGeneration,
                head==null?null:head.epoch(),t.facility,t.facility,
                head==null?null:head.epoch(),head==null?null:head.revisionId(),
                head==null?null:head.state().name(),head==null?null:head.lastEventId(),eventId,t.facility,eventId,t.facility));
            args.add(executor.publicationDatabaseIdentity().publisherUser());
            for(var fence:fences){args.add(fence.relation());args.add(fence.mode().catalog());}
            Boolean b=jdbc.queryForObject(GUARD+complete,Boolean.class,args.toArray());
            if(!Boolean.TRUE.equals(b))throw invalid();return true;
        }catch(RuntimeException error){executor.rejectAdmission();throw error;}
    }
}
