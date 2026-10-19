/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcEngineeringNetworkDraftRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Implements the bounded company-scoped engineering draft contract.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.out.EngineeringNetworkDraftRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.*;
import dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringDraftAdmissionCoordinator.Contribution;
import dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure;
import dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason;
import dz.sh.hidra.modules.topology.domain.model.EngineeringNetworkDraft;
import dz.sh.hidra.platform.persistence.*;
import java.sql.*;
import java.util.*;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.support.*;

@Repository
public class JdbcEngineeringNetworkDraftRepositoryAdapter implements EngineeringNetworkDraftRepositoryPort {
    private static final String CONTEXT_INS="INSERT INTO public.hidra_topology_engineering_context (id,company_party_id,pipeline_system_id) VALUES (?,?,?)";
    private static final String DRAFT_INS="INSERT INTO public.hidra_topology_engineering_draft (id,context_id,creation_request_id,request_sha256) VALUES (?,?,?,?)";
    private static final String VERSION_INS="INSERT INTO public.hidra_topology_engineering_draft_version (draft_id,version,payload_format,canonical_payload,sha256,predecessor_sha256,actor_binding_id,actor_session_id,actor_user_id,party_generation,sampled_at,valid_until,backend_pid,transaction_id) SELECT ?,?,?,?,?,?,?::uuid,?::uuid,?,?,?,?,?,? WHERE COALESCE((SELECT max(version) FROM public.hidra_topology_engineering_draft_version WHERE draft_id=?),0)=?";
    private static final Set<String> SQL=Set.of(CONTEXT_INS,DRAFT_INS,VERSION_INS);
    private static final List<String> RELATIONS=List.of("public.hidra_topology_pipeline_system","public.hidra_topology_pipeline_system_type",
        "public.hidra_topology_engineering_context","public.hidra_topology_engineering_draft","public.hidra_topology_engineering_draft_version");
    private static final String CATALOG="""
        SELECT current_setting('session_replication_role')='origin'
          AND NOT has_schema_privilege(current_user,'public','CREATE')
          AND NOT has_schema_privilege(current_user,'pg_catalog','CREATE')
          AND NOT EXISTS (SELECT 1 FROM pg_roles r WHERE (r.rolname=current_user OR pg_has_role(current_user,r.oid,'MEMBER'))
            AND (r.rolsuper OR r.rolbypassrls OR r.rolcreaterole OR r.rolcreatedb OR r.rolreplication
              OR r.rolname IN ('pg_execute_server_program','pg_read_server_files','pg_write_server_files')))
          AND (SELECT count(*)=3 AND bool_and(c.relkind='r' AND c.relpersistence='p' AND NOT c.relrowsecurity
            AND NOT c.relforcerowsecurity AND NOT c.relispartition AND NOT pg_has_role(current_user,c.relowner,'MEMBER')
            AND has_table_privilege(current_user,c.oid,'SELECT') AND has_table_privilege(current_user,c.oid,'INSERT')
            AND has_table_privilege(current_user,c.oid,'UPDATE') AND NOT has_table_privilege(current_user,c.oid,'DELETE,TRUNCATE,TRIGGER')
            AND NOT EXISTS (SELECT 1 FROM pg_inherits h WHERE h.inhrelid=c.oid OR h.inhparent=c.oid))
            FROM pg_class c WHERE c.oid IN ('public.hidra_topology_engineering_context'::regclass,
              'public.hidra_topology_engineering_draft'::regclass,'public.hidra_topology_engineering_draft_version'::regclass))
          AND (SELECT count(*)=7 AND bool_and(t.tgenabled='O' AND t.tgqual IS NULL AND t.tgnargs=0 AND t.tgparentid=0
            AND cardinality(t.tgattr::smallint[])=0 AND t.tgconstraint=0 AND
            ((t.tgfoid='public.hidra_p25_draft_immutable()'::regprocedure AND t.tgtype IN (26,34))
             OR (t.tgfoid='public.hidra_p25_draft_next_version()'::regprocedure AND t.tgtype=7
                AND t.tgrelid='public.hidra_topology_engineering_draft_version'::regclass)))
            FROM pg_trigger t WHERE NOT t.tgisinternal AND t.tgrelid IN
              ('public.hidra_topology_engineering_context'::regclass,'public.hidra_topology_engineering_draft'::regclass,
               'public.hidra_topology_engineering_draft_version'::regclass))
          AND (SELECT count(*)=2 AND bool_and(p.prosecdef AND p.prokind='f' AND p.pronargs=0
             AND p.prorettype='trigger'::regtype AND p.provolatile='v' AND NOT p.proleakproof
             AND p.proconfig=ARRAY['search_path=pg_catalog, pg_temp']
             AND p.prolang=(SELECT oid FROM pg_language WHERE lanname='plpgsql')
             AND NOT pg_has_role(current_user,p.proowner,'MEMBER') AND NOT has_function_privilege(current_user,p.oid,'EXECUTE')
             AND ((p.oid='public.hidra_p25_draft_immutable()'::regprocedure AND md5(p.prosrc)='543600367aa907964dac9777269caa07')
               OR (p.oid='public.hidra_p25_draft_next_version()'::regprocedure AND md5(p.prosrc)='ce8ad03dffcf0ca3fcb063be59a26bf7')))
            FROM pg_proc p WHERE p.oid IN ('public.hidra_p25_draft_immutable()'::regprocedure,'public.hidra_p25_draft_next_version()'::regprocedure))
          AND NOT EXISTS (SELECT 1 FROM pg_constraint k WHERE k.conrelid IN
             ('public.hidra_topology_engineering_context'::regclass,'public.hidra_topology_engineering_draft'::regclass,
              'public.hidra_topology_engineering_draft_version'::regclass) AND (NOT k.convalidated OR k.condeferrable))
        """;
    private final JdbcTemplate jdbc;private final ProtectedTransactionExecutor executor;private final EntityManager em;
    private final EngineeringNetworkDraftCodec codec=new EngineeringNetworkDraftCodec();
    public JdbcEngineeringNetworkDraftRepositoryAdapter(JdbcTemplate j,ProtectedTransactionExecutor e,EntityManager em){jdbc=Objects.requireNonNull(j);executor=Objects.requireNonNull(e);this.em=Objects.requireNonNull(em);}
    private final class Ticket implements Admission,TransactionSynchronization {
        final JdbcEngineeringNetworkDraftRepositoryAdapter owner=JdbcEngineeringNetworkDraftRepositoryAdapter.this;
        final Command command;final ConnectionHolder holder;final Connection connection;final Thread thread=Thread.currentThread();
        final int pid;final long xid;final String role,ordinary;boolean active=true,eligible,applied,newContext,replayed;
        UUID contextId,draftId;long latest;String latestDigest,requestDigest;byte[] latestBytes;Result result;
        Ticket(Command c,ConnectionHolder h,int pid,long xid,String role,String ordinary){command=c;holder=h;connection=h.getConnection();this.pid=pid;this.xid=xid;this.role=role;this.ordinary=ordinary;}
        @Override public void suspend(){active=false;}@Override public void afterCompletion(int status){active=false;}
        @Override public String toString(){return "Admission[opaque draft storage]";}
    }
    private static EngineeringNetworkDraftFailure fail(Reason reason){return new EngineeringNetworkDraftFailure(reason);}
    private ConnectionHolder physical(){
        if(!StackWalker.getInstance().walk(f->f.anyMatch(x->x.getClassName().equals(
            "dz.sh.hidra.modules.topology.application.service.EngineeringNetworkDraftApplicationService"))))throw fail(Reason.UNAVAILABLE);
        if(!TransactionSynchronizationManager.isActualTransactionActive() || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
            || !(TransactionSynchronizationManager.getResource(jdbc.getDataSource()) instanceof ConnectionHolder h) || h.isRollbackOnly())throw fail(Reason.UNAVAILABLE);return h;
    }
    private Ticket ticket(Admission a){var h=physical();if(!(a instanceof Ticket t) || t.owner!=this || t.thread!=Thread.currentThread()
        || !t.active || t.holder!=h || t.connection!=h.getConnection())throw fail(Reason.UNAVAILABLE);return t;}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(Command cmd){try {
        var h=physical();for(String r:RELATIONS)jdbc.execute("LOCK TABLE "+r+" IN SHARE ROW EXCLUSIVE MODE NOWAIT");
        var principals=executor.publicationDatabaseIdentity();
        if(!Boolean.TRUE.equals(jdbc.queryForObject(CATALOG,Boolean.class)))throw fail(Reason.UNAVAILABLE);
        if(!Boolean.TRUE.equals(jdbc.queryForObject("""
            SELECT session_user=? AND current_user=? AND ?<>?
             AND NOT pg_has_role(?::name,?::name,'MEMBER')
             AND NOT has_schema_privilege(?::name,'public','CREATE')
             AND NOT has_schema_privilege(?::name,'pg_catalog','CREATE')
             AND NOT EXISTS(SELECT 1 FROM pg_roles r WHERE (r.rolname=?::name OR pg_has_role(?::name,r.oid,'MEMBER'))
               AND (r.rolsuper OR r.rolbypassrls OR r.rolcreaterole OR r.rolcreatedb OR r.rolreplication OR r.rolname IN ('pg_execute_server_program','pg_read_server_files','pg_write_server_files')))
             AND NOT EXISTS(SELECT 1 FROM pg_class c WHERE c.oid IN
               ('public.hidra_topology_engineering_context'::regclass,'public.hidra_topology_engineering_draft'::regclass,
                'public.hidra_topology_engineering_draft_version'::regclass)
               AND (pg_has_role(?::name,c.relowner,'MEMBER') OR NOT has_table_privilege(?::name,c.oid,'SELECT')
                 OR has_table_privilege(?::name,c.oid,'INSERT,UPDATE,DELETE,TRUNCATE,TRIGGER')
                 OR has_any_column_privilege(?::name,c.oid,'INSERT,UPDATE,REFERENCES')))
            """,Boolean.class,principals.publisherUser(),principals.publisherUser(),principals.ordinaryUser(),principals.publisherUser(),
            principals.ordinaryUser(),principals.publisherUser(),principals.ordinaryUser(),principals.ordinaryUser(),
            principals.ordinaryUser(),principals.ordinaryUser(),principals.ordinaryUser(),principals.ordinaryUser(),principals.ordinaryUser(),principals.ordinaryUser())))throw fail(Reason.UNAVAILABLE);
        Ticket t=jdbc.queryForObject("SELECT pg_backend_pid(),txid_current(),current_user",(r,n)->new Ticket(cmd,h,r.getInt(1),r.getLong(2),r.getString(3),principals.ordinaryUser()));
        if(t==null)throw fail(Reason.UNAVAILABLE);TransactionSynchronizationManager.registerSynchronization(t);return t;
    }catch(RuntimeException e){executor.rejectAdmission();throw e;}}
    @Override public List<ProtectedRelationFence> finalFences(Admission a){ticket(a);return RELATIONS.stream()
        .map(r->new ProtectedRelationFence(r,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE)).toList();}
    private record Stored(long version,byte[] bytes,String digest) {}
    private Stored stored(UUID id,boolean first){
        var rows=jdbc.query("SELECT version,payload_format,canonical_payload,sha256 FROM public.hidra_topology_engineering_draft_version WHERE draft_id=? ORDER BY version "+(first?"ASC":"DESC")+" LIMIT 1",
            (r,n)->{byte[] bytes=r.getBytes(3);if(!EngineeringNetworkDraftCodec.FORMAT.equals(r.getString(2)) || !EngineeringNetworkDraftCodec.digest(bytes).equals(r.getString(4)))throw fail(Reason.UNAVAILABLE);
                codec.decode(bytes);return new Stored(r.getLong(1),bytes,r.getString(4));},id);
        return rows.isEmpty()?null:rows.get(0);
    }
    private String requestDigest(Command cmd,byte[] bytes){
        try {var out=new java.io.ByteArrayOutputStream();var d=new java.io.DataOutputStream(out);
            d.writeUTF(cmd.companyPartyId());d.writeUTF(cmd.pipelineSystemId());d.writeUTF(cmd.creationRequestId().toString());d.writeInt(bytes.length);d.write(bytes);
            return EngineeringNetworkDraftCodec.digest(out.toByteArray());}catch(java.io.IOException e){throw fail(Reason.UNAVAILABLE);}
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void eligible(Admission a){try {var t=ticket(a);if(t.eligible)throw fail(Reason.UNAVAILABLE);var c=t.command;
        var systems=jdbc.queryForList("SELECT s.status AS system_status,y.active AS type_active FROM public.hidra_topology_pipeline_system s JOIN public.hidra_topology_pipeline_system_type y ON y.id=s.system_type_id WHERE s.id=?",c.pipelineSystemId());
        if(systems.isEmpty())throw fail(Reason.NOT_FOUND);
        var system=systems.get(0);String status=Objects.toString(system.get("system_status"),"");boolean type=Boolean.TRUE.equals(system.get("type_active"));
        if(!Set.of("DRAFT","ACTIVE").contains(status) || !type)throw fail(Reason.NOT_FOUND);
        var contexts=jdbc.queryForList("SELECT id FROM public.hidra_topology_engineering_context WHERE company_party_id=? AND pipeline_system_id=?",UUID.class,c.companyPartyId(),c.pipelineSystemId());
        t.newContext=contexts.isEmpty();t.contextId=t.newContext?UUID.randomUUID():contexts.get(0);
        if(c.kind()==Kind.CREATE){
            byte[] bytes=codec.encode(c.metadata());t.requestDigest=requestDigest(c,bytes);
            var found=jdbc.queryForList("SELECT id,request_sha256 FROM public.hidra_topology_engineering_draft WHERE context_id=? AND creation_request_id=?",t.contextId,c.creationRequestId());
            t.replayed=!found.isEmpty();t.draftId=t.replayed?(UUID)found.get(0).get("id"):UUID.randomUUID();
            if(t.replayed && !t.requestDigest.equals(found.get(0).get("request_sha256")))throw fail(Reason.CONFLICT);
        }else{
            if(t.newContext)throw fail(Reason.NOT_FOUND);t.draftId=c.draftId();
            if(jdbc.queryForList("SELECT id FROM public.hidra_topology_engineering_draft WHERE id=? AND context_id=?",UUID.class,t.draftId,t.contextId).isEmpty())throw fail(Reason.NOT_FOUND);
        }
        Stored latest=stored(t.draftId,false);
        if(latest==null && (c.kind()!=Kind.CREATE || t.replayed))throw fail(Reason.UNAVAILABLE);
        t.latest=latest==null?0:latest.version();t.latestDigest=latest==null?null:latest.digest();t.latestBytes=latest==null?null:latest.bytes();
        if(c.expectedVersion()!=null && c.expectedVersion()!=t.latest)throw fail(Reason.CONFLICT);
        if(c.kind()==Kind.UPDATE && t.latest==Long.MAX_VALUE)throw fail(Reason.CONFLICT);
        Stored output=t.replayed?stored(t.draftId,true):latest;
        if(t.replayed && (output==null || output.version()!=1 || !Arrays.equals(output.bytes(),bytesFor(c))))throw fail(Reason.UNAVAILABLE);
        var metadata=(c.kind()==Kind.UPDATE || (c.kind()==Kind.CREATE && !t.replayed))?c.metadata():codec.decode(output.bytes());
        long version=c.kind()==Kind.CREATE?1:(c.kind()==Kind.UPDATE?t.latest+1:t.latest);
        var draft=new EngineeringNetworkDraft(t.contextId,c.companyPartyId(),c.pipelineSystemId(),t.draftId,version,metadata);
        t.result=new Result(draft,t.replayed,c.kind()==Kind.VALIDATE?draft.diagnostics():List.of());t.eligible=true;
    }catch(RuntimeException e){executor.rejectAdmission();throw e;}}
    private byte[] bytesFor(Command c){return codec.encode(c.metadata());}
    @Override public boolean replay(Admission a){var t=ticket(a);if(!t.eligible)throw fail(Reason.UNAVAILABLE);return t.replayed;}
    @Override public Set<String> mutationSql(){return SQL;}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Result apply(Admission a,Contribution c){try {var t=ticket(a);if(!t.eligible || t.applied || c.backendPid()!=t.pid || c.transactionId()!=t.xid
            || !c.request().companyPartyId().equals(t.command.companyPartyId()) || !c.request().pipelineSystemId().equals(t.command.pipelineSystemId()))throw fail(Reason.UNAVAILABLE);
        if(t.command.kind()==Kind.CREATE && !t.replayed){
            if(t.newContext && jdbc.update(CONTEXT_INS,t.contextId,t.command.companyPartyId(),t.command.pipelineSystemId())!=1)throw fail(Reason.CONFLICT);
            if(jdbc.update(DRAFT_INS,t.draftId,t.contextId,t.command.creationRequestId(),t.requestDigest)!=1)throw fail(Reason.CONFLICT);
        }
        if(t.command.kind()==Kind.UPDATE || (t.command.kind()==Kind.CREATE && !t.replayed)){
            byte[] bytes=codec.encode(t.result.draft().metadata());String hash=EngineeringNetworkDraftCodec.digest(bytes);
            if(jdbc.update(VERSION_INS,t.draftId,t.result.draft().version(),EngineeringNetworkDraftCodec.FORMAT,bytes,hash,t.latestDigest,
                c.bindingId(),c.sessionId(),c.userId(),c.partyGeneration(),Timestamp.from(c.sampledAt()),Timestamp.from(c.validUntil()),t.pid,t.xid,t.draftId,t.latest)!=1)throw fail(Reason.CONFLICT);
            t.latest=t.result.draft().version();t.latestBytes=bytes;t.latestDigest=hash;
        }
        t.applied=true;return t.result;
    }catch(RuntimeException e){executor.rejectAdmission();throw e;}}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void flush(){physical();em.flush();}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public boolean finalGuard(Admission a,Contribution c,List<ProtectedRelationFence> fences){
        var t=ticket(a);if(!t.applied || fences.size()!=31 || fences.stream().map(ProtectedRelationFence::relation).distinct().count()!=31
            || fences.stream().filter(f->f.mode()==ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE).count()!=28
            || fences.stream().filter(f->f.mode()==ProtectedRelationFence.Mode.ACCESS_SHARE).count()!=2
            || fences.stream().filter(f->f.mode()==ProtectedRelationFence.Mode.SHARE).count()!=1)throw fail(Reason.UNAVAILABLE);
        String required=String.join(",",Collections.nCopies(fences.size(),"(?::regclass,?::text)"));
        String guard="""
            WITH sampled_clock AS MATERIALIZED (SELECT clock_timestamp() AS t)
            SELECT (SELECT t FROM sampled_clock)>=?::timestamptz AND (SELECT t FROM sampled_clock)<?::timestamptz
              AND pg_backend_pid()=? AND txid_current()=? AND current_user=? AND session_user=?
              AND EXISTS(SELECT 1 FROM public.hidra_party_party p JOIN public.hidra_party_eligibility_generation g ON g.party_id=p.id
                WHERE p.id=? AND p.status='ACTIVE' AND g.generation=? AND g.present)
              AND EXISTS(SELECT 1 FROM public.hidra_topology_pipeline_system s JOIN public.hidra_topology_pipeline_system_type y ON y.id=s.system_type_id
                WHERE s.id=? AND s.status IN ('DRAFT','ACTIVE') AND y.active)
              AND EXISTS(SELECT 1 FROM public.hidra_topology_engineering_context x JOIN public.hidra_topology_engineering_draft d ON d.context_id=x.id
                WHERE x.id=? AND x.company_party_id=? AND x.pipeline_system_id=? AND d.id=?)
              AND EXISTS(SELECT 1 FROM public.hidra_topology_engineering_draft_version v WHERE v.draft_id=? AND v.version=?
                AND v.payload_format='HIDRA_ENGINEERING_METADATA_V1' AND v.sha256=? AND v.canonical_payload=?
                AND v.sha256=encode(sha256(v.canonical_payload),'hex')
                AND NOT EXISTS(SELECT 1 FROM public.hidra_topology_engineering_draft_version n WHERE n.draft_id=v.draft_id AND n.version>v.version))
              AND EXISTS(SELECT 1 FROM public.hidra_topology_engineering_draft_version v WHERE v.draft_id=? AND v.version=?
                AND v.canonical_payload=? AND v.sha256=encode(sha256(v.canonical_payload),'hex'))
            """;
        var args=new ArrayList<Object>(Arrays.asList(Timestamp.from(c.sampledAt()),Timestamp.from(c.validUntil()),t.pid,t.xid,t.role,t.role,
            t.command.companyPartyId(),c.partyGeneration(),t.command.pipelineSystemId(),t.contextId,t.command.companyPartyId(),t.command.pipelineSystemId(),t.draftId,
            t.draftId,t.latest,t.latestDigest,t.latestBytes,t.draftId,t.result.draft().version(),codec.encode(t.result.draft().metadata())));
        if(t.command.kind()==Kind.UPDATE || (t.command.kind()==Kind.CREATE && !t.replayed)){
            guard+=" AND EXISTS(SELECT 1 FROM public.hidra_topology_engineering_draft_version v WHERE v.draft_id=? AND v.version=? AND v.actor_binding_id=?::uuid AND v.actor_session_id=?::uuid AND v.actor_user_id=? AND v.party_generation=? AND v.sampled_at=? AND v.valid_until=? AND v.backend_pid=? AND v.transaction_id=?)";
            args.addAll(Arrays.asList(t.draftId,t.latest,c.bindingId(),c.sessionId(),c.userId(),c.partyGeneration(),Timestamp.from(c.sampledAt()),Timestamp.from(c.validUntil()),t.pid,t.xid));
        }
        if(t.command.kind()==Kind.CREATE){
            guard+=" AND EXISTS(SELECT 1 FROM public.hidra_topology_engineering_draft d WHERE d.id=? AND d.context_id=? AND d.creation_request_id=? AND d.request_sha256=?)";
            args.addAll(Arrays.asList(t.draftId,t.contextId,t.command.creationRequestId(),t.requestDigest));
        }
        guard+=" AND ("+CATALOG+")";
        guard+=" AND NOT EXISTS(SELECT 1 FROM (VALUES "+required+") required(relation,mode) WHERE NOT EXISTS(SELECT 1 FROM pg_locks l WHERE l.pid=pg_backend_pid() AND l.granted AND l.locktype='relation' AND l.relation=required.relation AND l.mode=required.mode))";
        for(var f:fences){args.add(f.relation());args.add(f.mode().catalog());}
        return Boolean.TRUE.equals(jdbc.queryForObject(guard,Boolean.class,args.toArray()));
    }
}
