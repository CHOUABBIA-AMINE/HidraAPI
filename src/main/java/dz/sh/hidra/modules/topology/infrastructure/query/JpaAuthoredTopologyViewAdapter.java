/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAuthoredTopologyViewAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.query
 *
 * @Description : Owns the bounded canonical Pipeline and persisted graph association contract.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.query;

import dz.sh.hidra.modules.topology.application.port.in.*;
import dz.sh.hidra.modules.topology.application.port.in.PipelineAuthoringUseCase.*;
import dz.sh.hidra.modules.topology.application.port.in.AuthoredTopologyViewUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.PipelineAuthoringPersistencePort;
import dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringDraftAdmissionCoordinator.Contribution;
import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringGraphDraftCodec;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.sql.*;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.transaction.support.*;
import org.springframework.stereotype.Repository;
@Repository
public class JpaAuthoredTopologyViewAdapter implements PipelineAuthoringPersistencePort {
    public static final String PIPE_INSERT="INSERT INTO public.hidra_topology_pipeline(id,pipeline_system_id,code,name_ar,name_fr,name_en,pipeline_type_id,status,created_at,updated_at) VALUES (?,?,?,?,?,?,?,'DRAFT',?,?)";
    public static final String PIPE_UPDATE="UPDATE public.hidra_topology_pipeline SET name_ar=?,name_fr=?,name_en=?,updated_at=? WHERE id=? AND pipeline_system_id=? AND updated_at=?";
    public static final String REV_INSERT="INSERT INTO public.hidra_topology_pipeline_authoring_revision(pipeline_id,revision,company_party_id,pipeline_system_id,code,pipeline_type_id,name_ar,name_fr,name_en,actor_user_id,recorded_at) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    public static final String SNAP_INSERT="INSERT INTO public.hidra_topology_pipeline_graph_snapshot(draft_id,version,request_id,company_party_id,pipeline_system_id,graph_sha256,actor_user_id,transaction_id) VALUES (?,?,?,?,?,?,?,?)";
    public static final String LINK_INSERT="INSERT INTO public.hidra_topology_pipeline_graph_binding(draft_id,version,branch_id,pipeline_id,pipeline_revision,segment_id) SELECT ?,?,x.branch_id,x.pipeline_id,x.pipeline_revision,x.segment_id FROM jsonb_to_recordset(?::jsonb) AS x(branch_id text,pipeline_id text,pipeline_revision bigint,segment_id text)";
    public static final Set<String> SQL=Set.of(PIPE_INSERT,PIPE_UPDATE,REV_INSERT,SNAP_INSERT,LINK_INSERT);
    private static final List<String> RELATIONS=List.of("public.hidra_topology_pipeline","public.hidra_topology_pipeline_type","public.hidra_topology_pipeline_segment","public.hidra_topology_pipeline_authoring_revision","public.hidra_topology_pipeline_graph_snapshot","public.hidra_topology_pipeline_graph_binding");
    private static final String REV="public.hidra_topology_pipeline_authoring_revision";
    private final JdbcTemplate jdbc;private final ProtectedTransactionExecutor executor;
    public JpaAuthoredTopologyViewAdapter(JdbcTemplate jdbc,ProtectedTransactionExecutor executor){this.jdbc=Objects.requireNonNull(jdbc);this.executor=Objects.requireNonNull(executor);}
    private final class Ticket implements Admission,TransactionSynchronization {
        final JpaAuthoredTopologyViewAdapter issuer=JpaAuthoredTopologyViewAdapter.this;final Scope scope;final ConnectionHolder holder;final Thread thread=Thread.currentThread();
        boolean active=true,prepared,persisted,frozen;Write command;Contribution actor;PipelineView edited;Timestamp previousTime;
        List<Binding> bindings=List.of();long selected;FinalCheck check;
        final Set<String> ids=new TreeSet<>(),segments=new TreeSet<>(),types=new TreeSet<>(),facilities=new TreeSet<>();final Map<String,PipelineView> pins=new TreeMap<>();
        Ticket(Scope scope,ConnectionHolder holder){this.scope=scope;this.holder=holder;}
        void pin(PipelineView p){ids.add(p.id());types.add(p.pipelineTypeId());pins.put(p.id()+":"+p.revision(),p);}
        @Override public void suspend(){active=false;}@Override public void afterCompletion(int status){active=false;}
    }
    private ConnectionHolder physical(){
        if(!StackWalker.getInstance().walk(s->s.anyMatch(f->f.getClassName().equals("dz.sh.hidra.modules.topology.application.service.EngineeringNetworkDraftApplicationService")))
                ||!TransactionSynchronizationManager.isActualTransactionActive()||TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ||!(TransactionSynchronizationManager.getResource(jdbc.getDataSource()) instanceof ConnectionHolder h)||h.isRollbackOnly())throw new IllegalStateException("Protected Pipeline owner required");return h;
    }
    private Ticket ticket(Admission a){var h=physical();if(!(a instanceof JpaAuthoredTopologyViewAdapter.Ticket t)||t.issuer!=this||!t.active||t.thread!=Thread.currentThread()||t.holder!=h)throw new IllegalStateException("Invalid Pipeline admission");return t;}
    @Override public Admission acquire(Scope scope){
        var h=physical();for(String relation:RELATIONS)jdbc.execute("LOCK TABLE "+relation+" IN SHARE ROW EXCLUSIVE MODE NOWAIT");
        var principals=executor.publicationDatabaseIdentity();
        for(String relation:RELATIONS)if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT c.relkind='r' AND c.relpersistence='p' AND NOT c.relrowsecurity AND NOT c.relforcerowsecurity AND NOT c.relispartition AND NOT pg_has_role(current_user,c.relowner,'MEMBER') AND has_table_privilege(current_user,c.oid,'SELECT') AND has_table_privilege(current_user,c.oid,'UPDATE') FROM pg_class c WHERE c.oid=?::regclass",Boolean.class,relation)))throw new IllegalStateException("Pipeline source catalog unavailable");
        for(String relation:RELATIONS.subList(3,6))if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT NOT has_table_privilege(?::name,c.oid,'SELECT,INSERT,UPDATE,DELETE,TRUNCATE,TRIGGER') AND NOT has_any_column_privilege(?::name,c.oid,'INSERT,UPDATE,REFERENCES') AND has_table_privilege(current_user,c.oid,'INSERT') AND NOT has_table_privilege(current_user,c.oid,'DELETE,TRUNCATE,TRIGGER') AND (SELECT count(*)=2 AND bool_and(tgenabled='O' AND tgfoid='public.hidra_p25_draft_immutable()'::regprocedure) FROM pg_trigger WHERE tgrelid=c.oid AND NOT tgisinternal AND tgname LIKE 'trg_b4_immutable_%') FROM pg_class c WHERE c.oid=?::regclass",Boolean.class,principals.ordinaryUser(),principals.ordinaryUser(),relation)))throw new IllegalStateException("Pipeline publication separation unavailable");
        var t=new Ticket(Objects.requireNonNull(scope),h);TransactionSynchronizationManager.registerSynchronization(t);return t;
    }
    private PipelineView pipeline(ResultSet r)throws SQLException{return new PipelineView(r.getString("pipeline_id"),r.getLong("revision"),r.getString("company_party_id"),r.getString("pipeline_system_id"),r.getString("code"),r.getString("pipeline_type_id"),r.getString("name_ar"),r.getString("name_fr"),r.getString("name_en"));}
    private PipelineView latest(Ticket t,String id){
        var rows=jdbc.query("SELECT * FROM "+REV+" WHERE pipeline_id=? ORDER BY revision DESC LIMIT 1",(r,n)->pipeline(r),id);
        if(rows.size()!=1||!rows.get(0).companyPartyId().equals(t.scope.companyPartyId())||!rows.get(0).pipelineSystemId().equals(t.scope.pipelineSystemId()))throw new dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.CONFLICT);
        return rows.get(0);
    }
    @Override public void prepare(Admission a,Write command,Contribution actor){
        var t=ticket(a);executor.requireAdmission();if(t.prepared||!t.scope.equals(command.scope())||!actor.request().companyPartyId().equals(t.scope.companyPartyId())||!actor.request().pipelineSystemId().equals(t.scope.pipelineSystemId()))throw new IllegalStateException("Pipeline command mismatch");
        t.command=command;t.actor=actor;t.selected=command.expectedVersion()+1;
        if(!jdbc.queryForList("SELECT version FROM public.hidra_topology_pipeline_graph_snapshot WHERE draft_id=? AND request_id=?",Long.class,t.scope.draftId(),command.requestId()).isEmpty())throw new dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.CONFLICT);
        var edit=command.pipeline();
        if(edit!=null){
            if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_topology_pipeline_type WHERE id=? AND active)",Boolean.class,edit.pipelineTypeId())))throw new dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.CONFLICT);
            var rows=jdbc.queryForList("SELECT * FROM public.hidra_topology_pipeline WHERE id=?",edit.id());
            if(edit.expectedRevision()==0){if(!rows.isEmpty())throw new dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.CONFLICT);}
            else {
                var old=latest(t,edit.id());if(old.revision()!=edit.expectedRevision()||!old.code().equals(edit.code())||!old.pipelineTypeId().equals(edit.pipelineTypeId())||rows.size()!=1)throw new dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.CONFLICT);
                var row=rows.getFirst();if(!Objects.equals(row.get("pipeline_system_id"),t.scope.pipelineSystemId())||!Set.of("DRAFT","ACTIVE").contains(row.get("status"))||!Objects.equals(row.get("code"),old.code())||!Objects.equals(row.get("pipeline_type_id"),old.pipelineTypeId())||!Objects.equals(row.get("name_ar"),old.nameAr())||!Objects.equals(row.get("name_fr"),old.nameFr())||!Objects.equals(row.get("name_en"),old.nameEn()))throw new dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.CONFLICT);
                t.previousTime=(Timestamp)row.get("updated_at");
            }
            t.edited=new PipelineView(edit.id(),edit.expectedRevision()+1,t.scope.companyPartyId(),t.scope.pipelineSystemId(),edit.code(),edit.pipelineTypeId(),edit.nameAr(),edit.nameFr(),edit.nameEn());t.pin(t.edited);
        }
        var links=new ArrayList<Binding>();for(var reference:command.references()){
            var p=t.edited!=null&&t.edited.id().equals(reference.pipelineId())?t.edited:latest(t,reference.pipelineId());
            if(p!=t.edited&&!Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_topology_pipeline p JOIN public.hidra_topology_pipeline_type y ON y.id=p.pipeline_type_id WHERE p.id=? AND p.pipeline_system_id=? AND p.status IN ('DRAFT','ACTIVE') AND y.active)",Boolean.class,p.id(),t.scope.pipelineSystemId())))throw new dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.CONFLICT);
            if(reference.segmentId()!=null){if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_topology_pipeline_segment WHERE id=? AND pipeline_id=? AND status IN ('DRAFT','ACTIVE'))",Boolean.class,reference.segmentId(),p.id())))throw new dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.CONFLICT);t.segments.add(reference.segmentId());}
            t.pin(p);links.add(new Binding(reference.branchId(),p,reference.segmentId()));
        }
        t.bindings=List.copyOf(links);t.prepared=true;
    }
    @Override public Set<String> mutationSql(){return SQL;}
    private String json(Object value){try{return tools.jackson.databind.json.JsonMapper.builder().build().writeValueAsString(value);}catch(Exception e){throw new IllegalStateException("Pipeline encoding unavailable",e);}}
    @Override public void persist(Admission a){var t=ticket(a);if(!t.prepared||t.persisted)throw new IllegalStateException("Pipeline plan unavailable");
        var c=t.actor;var p=t.edited;
        if(p!=null){Timestamp at=Timestamp.from(c.sampledAt());int changed;
            if(t.command.pipeline().expectedRevision()==0)changed=jdbc.update(PIPE_INSERT,p.id(),p.pipelineSystemId(),p.code(),p.nameAr(),p.nameFr(),p.nameEn(),p.pipelineTypeId(),at,at);
            else changed=jdbc.update(PIPE_UPDATE,p.nameAr(),p.nameFr(),p.nameEn(),at,p.id(),p.pipelineSystemId(),t.previousTime);
            if(changed!=1)throw new dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.CONFLICT);
            jdbc.update(REV_INSERT,p.id(),p.revision(),p.companyPartyId(),p.pipelineSystemId(),p.code(),p.pipelineTypeId(),p.nameAr(),p.nameFr(),p.nameEn(),c.userId(),at);
        }
        jdbc.update(SNAP_INSERT,t.scope.draftId(),t.selected,t.command.requestId(),t.scope.companyPartyId(),t.scope.pipelineSystemId(),new EngineeringGraphDraftCodec().sha256(new EngineeringGraphDraftCodec().encode(t.command.graph())),c.userId(),c.transactionId());
        var rows=t.bindings.stream().map(v->{var m=new LinkedHashMap<String,Object>();m.put("branch_id",v.branchId());m.put("pipeline_id",v.pipeline().id());m.put("pipeline_revision",v.pipeline().revision());m.put("segment_id",v.segmentId());return m;}).toList();
        if(jdbc.update(LINK_INSERT,t.scope.draftId(),t.selected,json(rows))!=rows.size())throw new IllegalStateException("Incomplete Pipeline references");t.persisted=true;
    }
    @Override public View materialize(Admission a,EngineeringGraphDraftUseCase.Result graph){
        var t=ticket(a);executor.requireAdmission();t.selected=graph.graphVersion()==null?0:graph.graphVersion();
        var diagnostics=new ArrayList<String>(graph.diagnostics());List<Binding> bindings=List.of();
        var snapshot=jdbc.queryForList("SELECT * FROM public.hidra_topology_pipeline_graph_snapshot WHERE draft_id=? AND version=?",t.scope.draftId(),t.selected);
        if(snapshot.isEmpty())diagnostics.add("CANONICAL_PIPELINE_ASSOCIATIONS_NOT_AUTHORED");
        else {
            var row=snapshot.getFirst();if(!Objects.equals(row.get("company_party_id"),t.scope.companyPartyId())||!Objects.equals(row.get("pipeline_system_id"),t.scope.pipelineSystemId())||!Objects.equals(row.get("graph_sha256"),graph.graphSha256()))throw new IllegalStateException("Pipeline graph provenance conflict");
            bindings=jdbc.query("SELECT b.branch_id,b.segment_id,r.* FROM public.hidra_topology_pipeline_graph_binding b JOIN "+REV+" r ON r.pipeline_id=b.pipeline_id AND r.revision=b.pipeline_revision WHERE b.draft_id=? AND b.version=? ORDER BY b.branch_id",(r,n)->new Binding(r.getString("branch_id"),pipeline(r),r.getString("segment_id")),t.scope.draftId(),t.selected);
            if(graph.graph()==null)throw new IllegalStateException("Associated graph missing");
            graph.graph().pipelineReferences(bindings.stream().map(v->new EngineeringGraphDraft.PipelineReference(v.branchId(),v.pipeline().id(),v.segmentId())).toList());
            for(var v:bindings){if(!v.pipeline().companyPartyId().equals(t.scope.companyPartyId())||!v.pipeline().pipelineSystemId().equals(t.scope.pipelineSystemId()))throw new IllegalStateException("Foreign Pipeline binding");t.pin(v.pipeline());if(v.segmentId()!=null)t.segments.add(v.segmentId());}
        }
        diagnostics.add("SURVEY_GEOMETRY_NOT_SUPPLIED");return new View(t.scope.companyPartyId(),t.scope.pipelineSystemId(),graph,bindings,diagnostics,"NOT_SUPPLIED");
    }
    @Override public Discovery discover(Admission a,int offset,int limit){
        var t=ticket(a);executor.requireAdmission();if(offset<0||offset>100000||limit<1||limit>200)throw new IllegalArgumentException("Bounded discovery required");
        String select=" FROM "+REV+" r JOIN public.hidra_topology_pipeline p ON p.id=r.pipeline_id JOIN public.hidra_topology_pipeline_type y ON y.id=r.pipeline_type_id WHERE r.company_party_id=? AND r.pipeline_system_id=? AND p.pipeline_system_id=r.pipeline_system_id AND p.status IN ('DRAFT','ACTIVE') AND y.active AND NOT EXISTS(SELECT 1 FROM "+REV+" n WHERE n.pipeline_id=r.pipeline_id AND n.revision>r.revision)";
        long total=jdbc.queryForObject("SELECT count(*)"+select,Long.class,t.scope.companyPartyId(),t.scope.pipelineSystemId());
        var pipelines=jdbc.query("SELECT r.*"+select+" ORDER BY r.code,r.pipeline_id OFFSET ? LIMIT ?",(r,n)->pipeline(r),t.scope.companyPartyId(),t.scope.pipelineSystemId(),offset,limit);pipelines.forEach(t::pin);
        var facilities=jdbc.query("SELECT id,code,coalesce(name_en,name_fr,name_ar,code) AS label,facility_kind AS kind FROM public.hidra_topology_facility WHERE owner_party_id=? AND status='ACTIVE' ORDER BY code,id LIMIT 200",(r,n)->new Choice(r.getString(1),r.getString(2),r.getString(3),r.getString(4)),t.scope.companyPartyId());facilities.forEach(v->t.facilities.add(v.id()));
        var types=jdbc.query("SELECT id,code,coalesce(name_en,name_fr,name_ar,code) FROM public.hidra_topology_pipeline_type WHERE active ORDER BY code,id LIMIT 200",(r,n)->new Choice(r.getString(1),r.getString(2),r.getString(3),"PIPELINE_TYPE"));types.forEach(v->t.types.add(v.id()));
        var segments=jdbc.query("SELECT s.id,s.code,s.segment_type FROM public.hidra_topology_pipeline_segment s WHERE s.pipeline_id IN (SELECT value FROM jsonb_array_elements_text(?::jsonb)) AND s.status IN ('DRAFT','ACTIVE') ORDER BY s.code,s.id LIMIT 200",(r,n)->new Choice(r.getString(1),r.getString(2),r.getString(2),r.getString(3)),json(t.ids));segments.forEach(v->t.segments.add(v.id()));
        return new Discovery(t.scope.companyPartyId(),t.scope.pipelineSystemId(),pipelines,total,offset,limit,facilities,types,segments);
    }
    private static final String IMAGE="""
        SELECT encode(sha256(convert_to(jsonb_build_object(
        'canonical',(SELECT jsonb_agg(to_jsonb(p) ORDER BY p.id) FROM public.hidra_topology_pipeline p WHERE p.id IN (SELECT value FROM jsonb_array_elements_text(?::jsonb))),
        'revisions',(SELECT jsonb_agg(to_jsonb(r) ORDER BY r.pipeline_id,r.revision) FROM public.hidra_topology_pipeline_authoring_revision r JOIN jsonb_to_recordset(?::jsonb) x(pipeline_id text,revision bigint) ON x.pipeline_id=r.pipeline_id AND x.revision=r.revision),
        'snapshot',(SELECT to_jsonb(s) FROM public.hidra_topology_pipeline_graph_snapshot s WHERE s.draft_id=? AND s.version=?),
        'links',(SELECT jsonb_agg(to_jsonb(l) ORDER BY l.branch_id) FROM public.hidra_topology_pipeline_graph_binding l WHERE l.draft_id=? AND l.version=?),
        'segments',(SELECT jsonb_agg(to_jsonb(s) ORDER BY s.id) FROM public.hidra_topology_pipeline_segment s WHERE s.id IN (SELECT value FROM jsonb_array_elements_text(?::jsonb))),
        'types',(SELECT jsonb_agg(to_jsonb(y) ORDER BY y.id) FROM public.hidra_topology_pipeline_type y WHERE y.id IN (SELECT value FROM jsonb_array_elements_text(?::jsonb))),
        'facilities',(SELECT jsonb_agg(to_jsonb(f) ORDER BY f.id) FROM public.hidra_topology_facility f WHERE f.id IN (SELECT value FROM jsonb_array_elements_text(?::jsonb)))
        )::text,'UTF8')),'hex')
        """;
    @Override public void freeze(Admission a){var t=ticket(a);executor.requireAdmission();if(t.frozen)throw new IllegalStateException("Pipeline evidence already frozen");
        var pins=t.pins.values().stream().map(p->Map.of("pipeline_id",p.id(),"revision",p.revision())).toList();
        var args=new ArrayList<Object>(List.of(json(t.ids),json(pins),t.scope.draftId(),t.selected,t.scope.draftId(),t.selected,json(t.segments),json(t.types),json(t.facilities)));
        String digest=jdbc.queryForObject(IMAGE,String.class,args.toArray());
        String sql="("+IMAGE+")=?";args.add(digest);
        for(String relation:RELATIONS){sql+=" AND EXISTS(SELECT 1 FROM pg_locks WHERE pid=pg_backend_pid() AND granted AND locktype='relation' AND relation=?::regclass AND mode='ShareRowExclusiveLock')";args.add(relation);}
        t.check=new FinalCheck(sql,args);t.frozen=true;
    }
    @Override public FinalCheck finalCheck(Admission a){var t=ticket(a);if(!t.frozen||t.check==null)throw new IllegalStateException("Pipeline guard not frozen");return t.check;}
}
