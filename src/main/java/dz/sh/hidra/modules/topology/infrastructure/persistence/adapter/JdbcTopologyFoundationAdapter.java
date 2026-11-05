package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.in.TopologyFoundationUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.TopologyFoundationPersistencePort;
import dz.sh.hidra.platform.persistence.*;
import java.sql.*;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.*;

/** Setup data is acquired before the common sample and frozen before the final SQL guard. */
@Repository
public class JdbcTopologyFoundationAdapter implements TopologyFoundationPersistencePort {
    public static final String SYSTEM="INSERT INTO public.hidra_topology_pipeline_system(id,code,name_en,system_type_id,status,created_at,updated_at) VALUES (?,?,?,?,'DRAFT',?,?)";
    public static final String OWNER="INSERT INTO public.hidra_topology_foundation_system_owner(pipeline_system_id,company_party_id) VALUES (?,?)";
    public static final String FACILITY="INSERT INTO public.hidra_topology_facility(id,code,name_en,facility_type_id,facility_kind,owner_party_id,status,created_at,updated_at) VALUES (?,?,?,?,?,?,'PLANNED',?,?)";
    public static final String ACTIVATE="UPDATE public.hidra_topology_facility SET status='ACTIVE',updated_at=? WHERE id=? AND owner_party_id=? AND status='PLANNED' AND updated_at=?";
    public static final String POSITION="INSERT INTO public.hidra_topology_equipment(id,code,name,facility_id,equipment_type_id,status,created_at,updated_at) VALUES (?,?,?,?,?,'PLANNED',?,?)";
    public static final String RECEIPT="INSERT INTO public.hidra_topology_foundation_request(company_party_id,request_id,operation,resource_id,command_sha256,result_json,actor_user_id,actor_binding_id,actor_session_id,sampled_at,valid_until,backend_pid,transaction_id,reason) VALUES (?,?,?,?,?,?::jsonb,?,?,?,?,?,?,?,?)";
    private static final Set<String> DML=Set.of(SYSTEM,OWNER,FACILITY,ACTIVATE,POSITION,RECEIPT);
    private static final List<String> RELATIONS=List.of("hidra_topology_pipeline_system","hidra_topology_pipeline_system_type","hidra_topology_pipeline_type","hidra_topology_facility","hidra_topology_facility_type","hidra_topology_equipment","hidra_topology_equipment_type","hidra_topology_foundation_system_owner","hidra_topology_foundation_request","hidra_topology_engineering_context","hidra_topology_installed_unit_installation");
    private final JdbcTemplate jdbc;private final ProtectedTransactionExecutor executor;
    private final tools.jackson.databind.json.JsonMapper json=tools.jackson.databind.json.JsonMapper.builder().build();
    public JdbcTopologyFoundationAdapter(JdbcTemplate jdbc,ProtectedTransactionExecutor executor){this.jdbc=Objects.requireNonNull(jdbc);this.executor=Objects.requireNonNull(executor);}
    private final class Ticket implements Admission,TransactionSynchronization {
        final JdbcTopologyFoundationAdapter issuer=JdbcTopologyFoundationAdapter.this;final Thread thread=Thread.currentThread();final ConnectionHolder holder;final Command command;final String role;final int pid;final long xid;
        boolean active=true,observed;Result result;String image;String digest;Item pendingItem;String resultTable;
        Ticket(ConnectionHolder h,Command c,int p,long x,String r){holder=h;command=c;pid=p;xid=x;role=r;digest=sha(encode(c));}
        @Override public void suspend(){active=false;}@Override public void afterCompletion(int s){active=false;}
    }
    private ConnectionHolder physical(){
        if(!StackWalker.getInstance().walk(f->f.anyMatch(x->x.getClassName().equals("dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator")))
            || !TransactionSynchronizationManager.isActualTransactionActive() || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
            || !(TransactionSynchronizationManager.getResource(jdbc.getDataSource()) instanceof ConnectionHolder h) || h.isRollbackOnly())throw unavailable();return h;
    }
    private Ticket ticket(Admission a){var h=physical();if(!(a instanceof JdbcTopologyFoundationAdapter.Ticket t)||t.issuer!=this||t.thread!=Thread.currentThread()||t.holder!=h||!t.active)throw unavailable();return t;}
    @Override public Admission acquire(Command c){
        var h=physical();
        for(String r:RELATIONS)jdbc.execute("LOCK TABLE public."+r+" IN SHARE ROW EXCLUSIVE MODE NOWAIT");
        var ids=executor.publicationDatabaseIdentity();
        for(String r:RELATIONS) {
            if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT c.relkind='r' AND c.relpersistence='p' AND NOT c.relrowsecurity AND NOT c.relforcerowsecurity AND NOT c.relispartition AND NOT EXISTS(SELECT 1 FROM pg_inherits WHERE inhrelid=c.oid OR inhparent=c.oid) AND NOT has_table_privilege(current_user,c.oid,'DELETE,TRUNCATE,TRIGGER') AND NOT pg_has_role(current_user,c.relowner,'MEMBER') AND has_table_privilege(current_user,c.oid,'SELECT') AND has_table_privilege(current_user,c.oid,'UPDATE') AND session_user=? AND NOT EXISTS(SELECT 1 FROM pg_roles WHERE (rolname=current_user OR pg_has_role(current_user,oid,'MEMBER')) AND (rolsuper OR rolbypassrls)) FROM pg_class c WHERE c.oid=?::regclass",Boolean.class,ids.publisherUser(),"public."+r)))throw unavailable();
        }
        for(String r:List.of("hidra_topology_foundation_system_owner","hidra_topology_foundation_request")) {
            if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT has_table_privilege(current_user,c.oid,'INSERT') AND NOT has_table_privilege(current_user,c.oid,'DELETE,TRUNCATE,TRIGGER') AND NOT has_table_privilege(?::name,c.oid,'SELECT,INSERT,UPDATE,DELETE,TRUNCATE,TRIGGER') AND NOT has_any_column_privilege(?::name,c.oid,'SELECT,INSERT,UPDATE,REFERENCES') AND (SELECT count(*)=2 AND bool_and(tgenabled='O' AND tgfoid='public.hidra_p25_draft_immutable()'::regprocedure) FROM pg_trigger WHERE tgrelid=c.oid AND NOT tgisinternal) FROM pg_class c WHERE c.oid=?::regclass",Boolean.class,ids.ordinaryUser(),ids.ordinaryUser(),"public."+r)))throw unavailable();
        }
        var t=jdbc.queryForObject("SELECT pg_backend_pid(),txid_current(),current_user",(rs,n)->new Ticket(h,c,rs.getInt(1),rs.getLong(2),rs.getString(3)));
        TransactionSynchronizationManager.registerSynchronization(t);return t;
    }
    @Override public Set<String> mutationSql(){return DML;}
    private String encode(Object o){try{return json.writeValueAsString(o);}catch(Exception e){throw unavailable();}}
    private String sha(String s){try{return HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(s.getBytes(java.nio.charset.StandardCharsets.UTF_8)));}catch(Exception e){throw unavailable();}}
    private Item item(ResultSet r)throws SQLException{return new Item(r.getString("id"),r.getString("code"),r.getString("name"),r.getString("kind"),r.getString("status"),r.getTimestamp("updated_at")==null?null:r.getTimestamp("updated_at").toInstant());}
    private Item existing(String table,String id){var rows=jdbc.query("SELECT id,code,"+(table.endsWith("equipment")?"name":"coalesce(name_en,name_fr,name_ar,code)")+" AS name,"+(table.endsWith("facility")?"facility_kind":"'"+(table.endsWith("equipment")?"EQUIPMENT_POSITION":"PIPELINE_SYSTEM")+"'")+" AS kind,status,updated_at FROM public."+table+" WHERE id=?",(r,n)->item(r),id);if(rows.size()!=1)throw conflict();return rows.getFirst();}
    private void facility(Ticket t,boolean active){
        if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_topology_facility WHERE id=? AND owner_party_id=? AND status "+(active?"='ACTIVE'":"IN ('PLANNED','ACTIVE')")+(t.command.kind()==Kind.ACTIVATE_FACILITY?"":" AND facility_kind IN ('STATION','COMPRESSOR_STATION','PUMP_STATION','METERING_STATION')")+")",Boolean.class,t.command.facilityId(),t.command.companyPartyId())))throw denied();
    }
    @Override public Result observe(Admission a){
        var t=ticket(a);executor.requireAdmission();if(t.observed)throw unavailable();t.observed=true;var c=t.command;
        if(c.mutation()) {
            var prior=jdbc.queryForList("SELECT operation,resource_id,command_sha256,result_json::text FROM public.hidra_topology_foundation_request WHERE company_party_id=? AND request_id=?",c.companyPartyId(),c.requestId());
            if(!prior.isEmpty()){
                var row=prior.getFirst();if(!Objects.equals(row.get("command_sha256"),t.digest))throw conflict();
                try {var result=json.readValue((String)row.get("result_json"),Result.class);return t.result=new Result(result.items(),List.of(),List.of(),null,true);}catch(Exception e){throw unavailable();}
            }
            prepareMutation(t);
            return null;
        }
        if(c.kind()==Kind.POSITIONS || c.kind()==Kind.INSTALLATIONS)facility(t,true);
        if(c.kind()==Kind.INSTALLATIONS){
            var rows=jdbc.query("SELECT i.unit_id,i.installation_id,i.position_id,e.facility_id FROM public.hidra_topology_installed_unit_installation i JOIN public.hidra_topology_equipment e ON e.id=i.position_id WHERE i.company_party_id=? AND e.facility_id=? AND i.effective_from<=statement_timestamp() AND (i.effective_until IS NULL OR i.effective_until>statement_timestamp()) ORDER BY i.installation_id OFFSET ? LIMIT ?",(r,n)->new InstallationCandidate(r.getString(1),r.getString(2),r.getString(3),r.getString(4)),c.companyPartyId(),c.facilityId(),c.offset(),c.limit()+1);
            boolean more=rows.size()>c.limit();return t.result=new Result(List.of(),rows.subList(0,Math.min(rows.size(),c.limit())),List.of(),more?c.offset()+c.limit():null,false);
        }
        String select;var args=new ArrayList<Object>();
        if(c.kind()==Kind.POSITIONS){select="SELECT e.id,e.code,e.name,e.equipment_type_id AS kind,e.status,e.updated_at FROM public.hidra_topology_equipment e JOIN public.hidra_topology_equipment_type k ON k.id=e.equipment_type_id WHERE e.facility_id=? AND e.node_id IS NULL AND e.pipeline_segment_id IS NULL AND e.status IN ('PLANNED','ACTIVE') AND k.status='ACTIVE'";args.add(c.facilityId());}
        else select=switch(c.catalog()) {
            case SYSTEM_TYPES -> "SELECT id,code,coalesce(name_en,name_fr,name_ar,code) AS name,'SYSTEM_TYPE' AS kind,'ACTIVE' AS status,updated_at FROM public.hidra_topology_pipeline_system_type WHERE active";
            case FACILITY_TYPES -> "SELECT id,code,name,facility_kind AS kind,status,updated_at FROM public.hidra_topology_facility_type WHERE status='ACTIVE'";
            case PIPELINE_TYPES -> "SELECT id,code,coalesce(name_en,name_fr,name_ar,code) AS name,'PIPELINE_TYPE' AS kind,'ACTIVE' AS status,updated_at FROM public.hidra_topology_pipeline_type WHERE active";
            case EQUIPMENT_KINDS -> "SELECT id,code,name,'EQUIPMENT_KIND' AS kind,status,updated_at FROM public.hidra_topology_equipment_type WHERE status='ACTIVE'";
            case SYSTEMS -> {args.add(c.companyPartyId());args.add(c.companyPartyId());yield "SELECT s.id,s.code,coalesce(s.name_en,s.name_fr,s.name_ar,s.code) AS name,'PIPELINE_SYSTEM' AS kind,s.status,s.updated_at FROM public.hidra_topology_pipeline_system s JOIN public.hidra_topology_pipeline_system_type k ON k.id=s.system_type_id WHERE k.active AND s.status IN ('DRAFT','ACTIVE') AND (EXISTS(SELECT 1 FROM public.hidra_topology_foundation_system_owner o WHERE o.pipeline_system_id=s.id AND o.company_party_id=?) OR EXISTS(SELECT 1 FROM public.hidra_topology_engineering_context x WHERE x.pipeline_system_id=s.id AND x.company_party_id=?))";}
            case FACILITIES -> {args.add(c.companyPartyId());yield "SELECT id,code,coalesce(name_en,name_fr,name_ar,code) AS name,facility_kind AS kind,status,updated_at FROM public.hidra_topology_facility WHERE owner_party_id=? AND status IN ('PLANNED','ACTIVE')";}
        };
        args.add(c.offset());args.add(c.limit()+1);var rows=jdbc.query(select+" ORDER BY code,id OFFSET ? LIMIT ?",(r,n)->item(r),args.toArray());
        return t.result=new Result(rows.subList(0,Math.min(rows.size(),c.limit())),List.of(),List.of(),rows.size()>c.limit()?c.offset()+c.limit():null,false);
    }
    // Eligibility reads run while OBSERVING under the already acquired relation fences.
    private void prepareMutation(Ticket t){
        var c=t.command;
        switch(c.kind()) {
            case CREATE_SYSTEM -> {
                if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_topology_pipeline_system_type WHERE id=? AND active)",Boolean.class,c.typeId())))throw conflict();
                t.resultTable="hidra_topology_pipeline_system";
                t.pendingItem=new Item(c.resourceId(),c.code(),c.name(),"PIPELINE_SYSTEM","DRAFT",null);
            }
            case REGISTER_FACILITY -> {
                if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_topology_facility_type WHERE id=? AND status='ACTIVE' AND facility_kind=?)",Boolean.class,c.typeId(),c.facilityKind())))throw conflict();
                t.resultTable="hidra_topology_facility";
                t.pendingItem=new Item(c.resourceId(),c.code(),c.name(),c.facilityKind(),"PLANNED",null);
            }
            case ACTIVATE_FACILITY -> {
                facility(t,false);
                if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_topology_facility f JOIN public.hidra_topology_facility_type k ON k.id=f.facility_type_id WHERE f.id=? AND k.status='ACTIVE' AND k.facility_kind=f.facility_kind)",Boolean.class,c.facilityId())))throw conflict();
                t.resultTable="hidra_topology_facility";
                var before=existing(t.resultTable,c.resourceId());
                t.pendingItem=new Item(before.id(),before.code(),before.name(),before.kind(),"ACTIVE",null);
            }
            case REGISTER_POSITION -> {
                facility(t,true);
                if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_topology_equipment_type WHERE id=? AND status='ACTIVE')",Boolean.class,c.typeId())))throw conflict();
                t.resultTable="hidra_topology_equipment";
                t.pendingItem=new Item(c.resourceId(),c.code(),c.name(),"EQUIPMENT_POSITION","PLANNED",null);
            }
            default -> throw unavailable();
        }
    }
    @Override public Result persist(Admission a,Origin o){
        var t=ticket(a);if(!t.observed||t.result!=null||t.pendingItem==null||!t.command.mutation())throw unavailable();var c=t.command;var at=Timestamp.from(o.sampledAt());
        // PROVISIONAL admits only the six exact reviewed DML templates, never SELECT.
        switch(c.kind()) {
            case CREATE_SYSTEM -> {
                jdbc.update(SYSTEM,c.resourceId(),c.code(),c.name(),c.typeId(),at,at);
                jdbc.update(OWNER,c.resourceId(),c.companyPartyId());
            }
            case REGISTER_FACILITY -> jdbc.update(FACILITY,c.resourceId(),c.code(),c.name(),c.typeId(),c.facilityKind(),c.companyPartyId(),at,at);
            case ACTIVATE_FACILITY -> {
                if(!o.sampledAt().isAfter(c.expectedUpdatedAt()))throw conflict();
                if(jdbc.update(ACTIVATE,at,c.resourceId(),c.companyPartyId(),Timestamp.from(c.expectedUpdatedAt()))!=1)throw conflict();
            }
            case REGISTER_POSITION -> jdbc.update(POSITION,c.resourceId(),c.code(),c.name(),c.facilityId(),c.typeId(),at,at);
            default -> throw unavailable();
        }
        var item=t.pendingItem;
        t.result=new Result(List.of(new Item(item.id(),item.code(),item.name(),item.kind(),item.status(),o.sampledAt())),List.of(),List.of(),null,false);
        jdbc.update(RECEIPT,c.companyPartyId(),c.requestId(),c.kind().name(),c.resourceId(),t.digest,encode(t.result),o.actorId(),o.bindingId(),o.sessionId(),at,Timestamp.from(o.validUntil()),o.backendPid(),o.transactionId(),c.reason());return t.result;
    }
    private static final String IMAGE="SELECT encode(sha256(convert_to(jsonb_build_object('systems',(SELECT jsonb_agg(to_jsonb(x) ORDER BY x.id) FROM public.hidra_topology_pipeline_system x),'facilities',(SELECT jsonb_agg(to_jsonb(x) ORDER BY x.id) FROM public.hidra_topology_facility x),'equipment',(SELECT jsonb_agg(to_jsonb(x) ORDER BY x.id) FROM public.hidra_topology_equipment x),'receipts',(SELECT jsonb_agg(to_jsonb(x) ORDER BY x.company_party_id,x.request_id) FROM public.hidra_topology_foundation_request x))::text,'UTF8')),'hex')";
    @Override public void freeze(Admission a){var t=ticket(a);executor.requireAdmission();if(t.result==null||t.image!=null)throw unavailable();
        // Back in OBSERVING, bind the response/receipt to the actual persisted row.
        if(t.command.mutation() && !t.result.replayed()
            && !t.result.items().equals(List.of(existing(t.resultTable,t.command.resourceId()))))throw unavailable();
        t.image=jdbc.queryForObject(IMAGE,String.class);}
    @Override public boolean finalGuard(Admission a,Origin o,List<ProtectedRelationFence> owners){
        var t=ticket(a);if(t.image==null || owners.size()!=26)throw unavailable();
        var fences=new TreeMap<String,String>();for(var f:owners)fences.put(f.relation(),f.mode().catalog());for(String r:RELATIONS)fences.put("public."+r,"ShareRowExclusiveLock");
        String sql="SELECT ("+IMAGE+")=? AND statement_timestamp()>=? AND clock_timestamp()<? AND pg_backend_pid()=? AND txid_current()=? AND current_user=? AND EXISTS(SELECT 1 FROM public.hidra_party_eligibility_generation WHERE party_id=? AND generation=? AND present)";
        var args=new ArrayList<Object>(Arrays.asList(t.image,Timestamp.from(o.sampledAt()),Timestamp.from(o.validUntil()),t.pid,t.xid,t.role,t.command.companyPartyId(),o.partyGeneration()));
        for(var f:fences.entrySet()){sql+=" AND EXISTS(SELECT 1 FROM pg_locks WHERE pid=pg_backend_pid() AND granted AND locktype='relation' AND relation=?::regclass AND mode=?)";args.add(f.getKey());args.add(f.getValue());}
        return Boolean.TRUE.equals(jdbc.queryForObject(sql,Boolean.class,args.toArray()));
    }
    private static IllegalStateException unavailable(){return new IllegalStateException("Topology foundation source unavailable");}
    private static dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure conflict(){return new dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure(dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason.CONFLICT);}
    private static org.springframework.security.access.AccessDeniedException denied(){return new org.springframework.security.access.AccessDeniedException("Topology foundation scope denied");}
}
