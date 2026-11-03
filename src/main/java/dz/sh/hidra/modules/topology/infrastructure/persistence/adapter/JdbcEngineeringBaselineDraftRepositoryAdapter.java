/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcEngineeringBaselineDraftRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Persists immutable baseline drafts with guarded version and request receipts.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;


import dz.sh.hidra.modules.topology.application.port.out.EngineeringBaselineDraftRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringBaselineSourcePort.Original;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselineDraftUseCase.*;
import dz.sh.hidra.modules.topology.application.model.EngineeringBaselineDraftFailure;
import dz.sh.hidra.modules.topology.application.model.EngineeringBaselineDraftFailure.Kind;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest.*;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import tools.jackson.databind.json.JsonMapper;
import java.sql.Timestamp;
import java.util.*;
import java.nio.charset.StandardCharsets;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.*;

@Repository
public final class JdbcEngineeringBaselineDraftRepositoryAdapter implements EngineeringBaselineDraftRepositoryPort {
    public static final String HEAD_INSERT="INSERT INTO public.hidra_topology_engineering_baseline_draft (company_party_id,pipeline_system_id,manifest_id,head_version,created_at,created_by_user_id) VALUES (?,?,?,1,?,?)";
    public static final String REV_INSERT="INSERT INTO public.hidra_topology_engineering_baseline_revision (company_party_id,pipeline_system_id,manifest_id,version,payload_format,canonical_payload,canonical_sha256,findings,complete_at_capture,actor_binding_id,actor_session_id,actor_user_id,sampled_at,valid_until,backend_pid,transaction_id,database_role) VALUES (?,?,?,?,?,?,?,?::jsonb,?,?::uuid,?::uuid,?,?,?,?,?,?::name)";
    public static final String HEAD_UPDATE="UPDATE public.hidra_topology_engineering_baseline_draft SET head_version=? WHERE company_party_id=? AND pipeline_system_id=? AND manifest_id=? AND head_version=?";
    public static final String REQUEST_INSERT="INSERT INTO public.hidra_topology_engineering_baseline_request (company_party_id,pipeline_system_id,manifest_id,actor_user_id,request_id,operation,expected_version,version,command_sha256,actor_binding_id,actor_session_id,sampled_at,valid_until,backend_pid,transaction_id,database_role) VALUES (?,?,?,?,?,?,?,?,?,?::uuid,?::uuid,?,?,?,?,?::name)";
    private static final String PREFIX="public.hidra_topology_engineering_baseline";
    private static final List<String> TABLES=List.of(PREFIX+"_draft",PREFIX+"_revision",PREFIX+"_request",
        "public.hidra_topology_pipeline_system","public.hidra_topology_pipeline_system_type",
        "public.hidra_party_party","public.hidra_party_eligibility_generation");
    private static final Set<String> DML=Set.of(HEAD_INSERT,REV_INSERT,HEAD_UPDATE,REQUEST_INSERT);
    private final JdbcTemplate jdbc;
    private final ProtectedTransactionExecutor executor;
    private final JsonMapper json=JsonMapper.builder().build();
    private final EngineeringBaselineManifestCodec codec;
    private final int maxPins;
    public JdbcEngineeringBaselineDraftRepositoryAdapter(JdbcTemplate jdbc,ProtectedTransactionExecutor executor,
            @org.springframework.beans.factory.annotation.Value("${hidra.topology.engineering-baseline.max-pins:128}") int maxPins,
            @org.springframework.beans.factory.annotation.Value("${hidra.topology.engineering-baseline.max-bytes:1048576}") int maxBytes){
        if(maxPins<1||maxPins>1024||maxBytes<128||maxBytes>4194304)throw new IllegalArgumentException("Invalid baseline bounds");
        this.jdbc=Objects.requireNonNull(jdbc);this.executor=Objects.requireNonNull(executor);
        this.codec=new EngineeringBaselineManifestCodec(maxPins,maxBytes);this.maxPins=maxPins;
    }
    @Override public void lockSourcesAndStorage(){
        holder();executor.requireEngineeringBaselineStorageLock();
        jdbc.queryForObject("SELECT public.hidra_topology_baseline_lock_storage()",Boolean.class);
    }
    @Override public Admission select(Command c,Original origin){
        var h=holder();if(c.mutating()&&c.pins().size()>maxPins)throw new IllegalArgumentException("Baseline pin work bound exceeded");
        var t=new Ticket(this,c,origin,h);TransactionSynchronizationManager.registerSynchronization(t);
        physical(t);
        if(!Boolean.TRUE.equals(jdbc.queryForObject(
            "SELECT EXISTS(SELECT 1 FROM public.hidra_party_party p JOIN public.hidra_party_eligibility_generation g ON g.party_id=p.id"
            +" WHERE p.id=? AND p.status='ACTIVE' AND g.present)"
            +" AND EXISTS(SELECT 1 FROM public.hidra_topology_pipeline_system s JOIN public.hidra_topology_pipeline_system_type y ON y.id=s.system_type_id"
            +" WHERE s.id=? AND s.status IN ('DRAFT','ACTIVE') AND y.active)",
            Boolean.class,c.companyPartyId(),c.pipelineSystemId())))throw fail(Kind.NOT_FOUND);
        var heads=jdbc.queryForList("SELECT head_version FROM "+PREFIX+"_draft WHERE company_party_id=? AND pipeline_system_id=? AND manifest_id=?",
            Long.class,c.companyPartyId(),c.pipelineSystemId(),c.manifestId());
        t.head=heads.isEmpty()?null:heads.getFirst();
        if(c.mutating()){
            t.digest=codec.sha256(codec.encode(c.candidate()));
            var requests=jdbc.queryForList("SELECT version,command_sha256,operation,expected_version FROM "+PREFIX
                +"_request WHERE company_party_id=? AND pipeline_system_id=? AND manifest_id=? AND actor_user_id=? AND request_id=?",
                c.companyPartyId(),c.pipelineSystemId(),c.manifestId(),origin.userId(),c.requestId());
            if(!requests.isEmpty()){
                var r=requests.getFirst();
                if(!t.digest.equals(r.get("command_sha256"))||!c.action().name().equals(r.get("operation"))
                    ||((Number)r.get("expected_version")).longValue()!=c.expectedVersion())throw fail(Kind.CONFLICT);
                t.replay=true;t.manifest=read(t,((Number)r.get("version")).longValue());
                if(!c.candidate().equals(t.manifest))throw fail(Kind.UNAVAILABLE);
            }else{
                if(c.action()==Action.CREATE?t.head!=null:t.head==null||t.head!=c.expectedVersion())throw fail(Kind.CONFLICT);
                t.manifest=c.candidate();
            }
        }else{
            if(t.head==null)throw fail(Kind.NOT_FOUND);
            t.manifest=read(t,c.revision()==null?t.head:c.revision());
        }
        return t;
    }
    private EngineeringBaselineManifest read(Ticket t,long revision){
        var c=t.command;
        var rows=jdbc.query("SELECT * FROM "+PREFIX+"_revision WHERE company_party_id=? AND pipeline_system_id=? AND manifest_id=? AND version=?",
            (r,n)->{
                byte[] b=r.getBytes("canonical_payload");
                if(!EngineeringBaselineManifestCodec.FORMAT.equals(r.getString("payload_format"))
                    ||!codec.sha256(b).equals(r.getString("canonical_sha256")))throw fail(Kind.UNAVAILABLE);
                var value=codec.decode(b);
                if(!value.companyPartyId().equals(c.companyPartyId())||!value.pipelineSystemId().equals(c.pipelineSystemId())
                    ||!value.manifestId().equals(c.manifestId())||value.version()!=revision)throw fail(Kind.UNAVAILABLE);
                try{t.captured=new Completeness(List.of(json.readValue(r.getString("findings"),Diagnostic[].class)));}
                catch(RuntimeException invalid){throw fail(Kind.UNAVAILABLE);}
                if(t.captured.complete()!=r.getBoolean("complete_at_capture")||r.getBoolean("engineering_qualified")
                    ||r.getBoolean("authoritative")||r.getBoolean("frozen"))throw fail(Kind.UNAVAILABLE);
                return value;
            },c.companyPartyId(),c.pipelineSystemId(),c.manifestId(),revision);
        if(rows.size()!=1)throw fail(Kind.NOT_FOUND);
        return rows.getFirst();
    }
    @Override public EngineeringBaselineManifest manifest(Admission a){return ticket(a).manifest;}
    @Override public Set<String> mutationSql(){return DML;}
    @Override public Result apply(Admission a,Completeness current){
        var t=ticketWithoutSql(a);if(t.applied)throw fail(Kind.UNAVAILABLE);
        var c=t.command;var v=t.manifest;var o=t.origin;var bytes=codec.encode(v);String sha=codec.sha256(bytes);
        if(c.mutating()&&!t.replay){
            if(c.action()==Action.CREATE&&jdbc.update(HEAD_INSERT,c.companyPartyId(),c.pipelineSystemId(),c.manifestId(),ts(o.sampledAt()),o.userId())!=1)
                throw fail(Kind.CONFLICT);
            if(jdbc.update(REV_INSERT,c.companyPartyId(),c.pipelineSystemId(),c.manifestId(),v.version(),
                EngineeringBaselineManifestCodec.FORMAT,bytes,sha,findings(current),current.complete(),
                o.bindingId(),o.sessionId(),o.userId(),ts(o.sampledAt()),ts(o.validUntil()),o.backendPid(),o.transactionId(),o.databaseRole())!=1)
                throw fail(Kind.CONFLICT);
            if(c.action()==Action.UPDATE&&jdbc.update(HEAD_UPDATE,v.version(),c.companyPartyId(),c.pipelineSystemId(),c.manifestId(),c.expectedVersion())!=1)
                throw fail(Kind.CONFLICT);
            if(jdbc.update(REQUEST_INSERT,c.companyPartyId(),c.pipelineSystemId(),c.manifestId(),o.userId(),c.requestId(),
                c.action().name(),c.expectedVersion(),v.version(),t.digest,o.bindingId(),o.sessionId(),
                ts(o.sampledAt()),ts(o.validUntil()),o.backendPid(),o.transactionId(),o.databaseRole())!=1)throw fail(Kind.CONFLICT);
            t.captured=current;t.head=v.version();
        }
        t.result=new Result(v,sha,t.captured,current,t.replay);t.applied=true;return t.result;
    }
    private String findings(Completeness value){
        try{return json.writeValueAsString(value.diagnostics());}
        catch(RuntimeException invalid){throw fail(Kind.UNAVAILABLE);}
    }
    @Override public void flush(){holder();}
    @Override public List<ProtectedRelationFence> finalFences(){
        return TABLES.stream().map(x->new ProtectedRelationFence(x,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE)).toList();
    }
    @Override public void prepareFinal(Admission admission,List<ProtectedRelationFence> fences){
        var t=ticket(admission);if(!t.applied||t.frozen)throw fail(Kind.UNAVAILABLE);
        physical(t);
        var c=t.command;var o=t.origin;var ids=executor.publicationDatabaseIdentity();
        var args=new ArrayList<Object>(Arrays.asList(o.backendPid(),o.transactionId(),o.databaseRole(),o.databaseRole(),
            ts(o.sampledAt()),ts(o.validUntil()),ids.publisherUser(),ids.ordinaryUser(),ids.ordinaryUser()));
        var sql=new StringBuilder("""
            WITH sampled_clock AS MATERIALIZED (SELECT pg_catalog.clock_timestamp() AS t)
            SELECT pg_backend_pid()=? AND txid_current()=? AND current_user=? AND session_user=?
             AND (SELECT t FROM sampled_clock)>=?::timestamptz AND (SELECT t FROM sampled_clock)<?::timestamptz
             AND current_user=? AND current_user<>?
             AND NOT pg_has_role(?::name,current_user,'MEMBER')
             AND current_setting('session_replication_role')='origin'
             AND NOT has_schema_privilege(current_user,'public','CREATE')
             AND NOT has_schema_privilege(current_user,'pg_catalog','CREATE')
             AND NOT EXISTS(SELECT 1 FROM pg_roles r WHERE (r.rolname=current_user OR pg_has_role(current_user,r.oid,'MEMBER'))
                 AND (r.rolsuper OR r.rolbypassrls OR r.rolcreaterole OR r.rolcreatedb OR r.rolreplication))
            """);
        sql.append(" AND EXISTS(SELECT 1 FROM ").append(PREFIX).append("_draft WHERE company_party_id=? AND pipeline_system_id=? AND manifest_id=? AND head_version=?)");
        args.addAll(Arrays.asList(c.companyPartyId(),c.pipelineSystemId(),c.manifestId(),t.head));
        sql.append(" AND EXISTS(SELECT 1 FROM ").append(PREFIX).append("_revision WHERE company_party_id=? AND pipeline_system_id=? AND manifest_id=? AND version=? AND canonical_payload=? AND canonical_sha256=? AND findings=?::jsonb AND complete_at_capture=? AND NOT engineering_qualified AND NOT authoritative AND NOT frozen)");
        args.addAll(Arrays.asList(c.companyPartyId(),c.pipelineSystemId(),c.manifestId(),t.manifest.version(),
            codec.encode(t.manifest),t.result.canonicalSha256(),findings(t.captured),t.captured.complete()));
        if(c.mutating()){
            sql.append(" AND EXISTS(SELECT 1 FROM ").append(PREFIX).append("_request WHERE company_party_id=? AND pipeline_system_id=? AND manifest_id=? AND actor_user_id=? AND request_id=? AND version=? AND command_sha256=?)");
            args.addAll(Arrays.asList(c.companyPartyId(),c.pipelineSystemId(),c.manifestId(),o.userId(),c.requestId(),t.manifest.version(),t.digest));
        }
        for(String table:TABLES.subList(0,3)){
            sql.append(" AND EXISTS(SELECT 1 FROM pg_class c WHERE c.oid=?::regclass AND c.relkind='r' AND c.relpersistence='p' AND NOT c.relrowsecurity AND NOT c.relforcerowsecurity AND NOT c.relhasrules AND NOT pg_has_role(current_user,c.relowner,'MEMBER'))");
            args.add(table);
            sql.append(" AND NOT has_table_privilege(?::name,?::regclass,'SELECT,INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER') AND NOT has_any_column_privilege(?::name,?::regclass,'SELECT,INSERT,UPDATE,REFERENCES')");
            args.addAll(Arrays.asList(ids.ordinaryUser(),table,ids.ordinaryUser(),table));
        }
        for(var fence:fences){
            sql.append(" AND EXISTS(SELECT 1 FROM pg_locks WHERE pid=pg_backend_pid() AND granted AND locktype='relation' AND relation=?::regclass AND mode=?)");
            args.addAll(Arrays.asList(fence.relation(),fence.mode().catalog()));
        }
        // An owner-scoped catalog predicate is computed from the additive migration, never inferred metadata.
        sql.append(" AND (").append(EngineeringBaselineDraftCatalog.PREDICATE).append(")");
        t.guard=sql.toString();t.arguments=args.toArray();t.frozen=true;
    }
    @Override public boolean finalGuard(Admission a){
        var t=ticketWithoutSql(a);
        if(!t.frozen||t.guarded)throw fail(Kind.UNAVAILABLE);
        boolean ok=Boolean.TRUE.equals(jdbc.queryForObject(t.guard,Boolean.class,t.arguments));
        if(!ok)throw fail(Kind.UNAVAILABLE);
        t.guarded=true;return true;
    }
    private ConnectionHolder holder(){
        if(jdbc.getDataSource()==null||!TransactionSynchronizationManager.isActualTransactionActive()
            ||!TransactionSynchronizationManager.isSynchronizationActive()||TransactionSynchronizationManager.isCurrentTransactionReadOnly()
            ||!(TransactionSynchronizationManager.getResource(jdbc.getDataSource()) instanceof ConnectionHolder h)||h.isRollbackOnly())throw fail(Kind.UNAVAILABLE);
        return h;
    }
    private Ticket ticketWithoutSql(Admission a){
        if(!(a instanceof Ticket t)||t.owner!=this||!t.active||t.thread!=Thread.currentThread()
            ||holder()!=t.holder||t.connection!=t.holder.getConnection())throw fail(Kind.UNAVAILABLE);
        return t;
    }
    private Ticket ticket(Admission a){var t=ticketWithoutSql(a);physical(t);return t;}
    private void physical(Ticket t){
        if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT pg_backend_pid()=? AND txid_current()=? AND current_user=? AND session_user=? AND clock_timestamp()>=? AND clock_timestamp()<?",
            Boolean.class,t.origin.backendPid(),t.origin.transactionId(),t.origin.databaseRole(),t.origin.databaseRole(),
            ts(t.origin.sampledAt()),ts(t.origin.validUntil()))))throw fail(Kind.UNAVAILABLE);
    }
    private static Timestamp ts(java.time.Instant value){return Timestamp.from(value);}
    private static EngineeringBaselineDraftFailure fail(Kind kind){return new EngineeringBaselineDraftFailure(kind);}
    private static final class Ticket implements Admission,TransactionSynchronization{
        final JdbcEngineeringBaselineDraftRepositoryAdapter owner;final Command command;final Original origin;
        final ConnectionHolder holder;final java.sql.Connection connection;final Thread thread=Thread.currentThread();
        Long head;String digest;EngineeringBaselineManifest manifest;Completeness captured;
        boolean replay,applied,frozen,guarded,active=true;Result result;String guard;Object[] arguments;
        Ticket(JdbcEngineeringBaselineDraftRepositoryAdapter owner,Command command,Original origin,ConnectionHolder holder){
            this.owner=owner;this.command=command;this.origin=origin;this.holder=holder;this.connection=holder.getConnection();
        }
        @Override public void suspend(){active=false;holder.setRollbackOnly();}
        @Override public void afterCompletion(int status){active=false;}
        @Override public void beforeCommit(boolean readOnly){if(readOnly||!active||!guarded||holder.isRollbackOnly())throw fail(Kind.UNAVAILABLE);}
    }
}
