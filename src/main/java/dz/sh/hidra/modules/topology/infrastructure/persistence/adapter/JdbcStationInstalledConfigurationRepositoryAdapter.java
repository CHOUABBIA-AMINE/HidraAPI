/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcStationInstalledConfigurationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Privileged, source-fenced immutable station configuration storage.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.StationInstalledConfigurationRepositoryPort;
import dz.sh.hidra.modules.topology.application.model.StationInstalledConfigurationFailure;
import dz.sh.hidra.modules.topology.application.model.StationInstalledConfigurationFailure.Kind;
import dz.sh.hidra.modules.topology.domain.model.StationInstalledConfiguration;
import dz.sh.hidra.modules.topology.domain.model.StationInstalledConfiguration.Position;
import dz.sh.hidra.modules.topology.application.port.out.StationInstalledConfigurationRepositoryPort.Origin;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import dz.sh.hidra.platform.persistence.ProtectedCredentialRoutingDataSource;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import jakarta.persistence.EntityManager;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.sql.Timestamp;
import java.sql.Connection;
import java.util.*;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/** Exact caller/physical scope/least-privilege verification; never returns an authorization token. */
@Repository
public class JdbcStationInstalledConfigurationRepositoryAdapter implements StationInstalledConfigurationRepositoryPort {
    private static final String REV_INS="INSERT INTO public.hidra_topology_station_configuration_revision (company_party_id,facility_id,revision_id,request_id,expected_epoch,selected_epoch,graph_head_epoch,graph_revision_id,graph_payload_format,graph_sha256,payload_format,canonical_payload,canonical_sha256,effective_from,effective_until,recorded_at,source_reference,actor_binding_id,actor_session_id,actor_user_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String MEMBER_INS="INSERT INTO public.hidra_topology_station_configuration_member (company_party_id,facility_id,revision_id,position_id,disposition,installed_unit_id,installation_id,installation_revision,installation_from,installation_until,installation_evidence_reference) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String HEAD_INS="INSERT INTO public.hidra_topology_station_configuration_head (company_party_id,facility_id,revision_id,head_epoch) VALUES (?,?,?,?)";
    private static final String HEAD_CAS="UPDATE public.hidra_topology_station_configuration_head SET revision_id=?,head_epoch=? WHERE company_party_id=? AND facility_id=? AND revision_id=? AND head_epoch=?";
    private static final String REQUEST_INS="INSERT INTO public.hidra_topology_station_configuration_request (company_party_id,facility_id,request_id,command_sha256,revision_id,selected_epoch,actor_binding_id,recorded_at,actor_session_id,actor_user_id,sampled_at,valid_until,backend_pid,transaction_id,database_role) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final Set<String> MUTATIONS=Set.of(REV_INS,MEMBER_INS,HEAD_INS,HEAD_CAS,REQUEST_INS);
    private static final List<String> LOCKED=List.of(
        "public.hidra_topology_equipment","public.hidra_topology_facility",
        "public.hidra_topology_facility_owner_generation","public.hidra_topology_installed_unit",
        "public.hidra_topology_installed_unit_installation","public.hidra_topology_installed_unit_installation_event",
        "public.hidra_topology_station_graph_head","public.hidra_topology_station_graph_revision",
        "public.hidra_topology_station_configuration_revision","public.hidra_topology_station_configuration_member",
        "public.hidra_topology_station_configuration_head","public.hidra_topology_station_configuration_request");
    private static final String LOCK_CHECK="""
        SELECT count(*)=12 FROM (VALUES
        ('public.hidra_topology_equipment'),('public.hidra_topology_facility'),
        ('public.hidra_topology_facility_owner_generation'),('public.hidra_topology_installed_unit'),
        ('public.hidra_topology_installed_unit_installation'),('public.hidra_topology_installed_unit_installation_event'),
        ('public.hidra_topology_station_graph_head'),('public.hidra_topology_station_graph_revision'),
        ('public.hidra_topology_station_configuration_revision'),('public.hidra_topology_station_configuration_member'),
        ('public.hidra_topology_station_configuration_head'),('public.hidra_topology_station_configuration_request'))
        AS required(name) WHERE EXISTS (
          SELECT 1 FROM pg_catalog.pg_locks l
          WHERE l.pid=pg_catalog.pg_backend_pid() AND l.locktype='relation'
          AND l.granted AND l.mode='ShareRowExclusiveLock'
          AND l.relation=pg_catalog.to_regclass(required.name))
        """;
    private static final String CATALOG="""
        SELECT pg_catalog.current_setting('transaction_isolation')='read committed'
          AND pg_catalog.current_setting('transaction_read_only')='off'
          AND pg_catalog.current_setting('session_replication_role')='origin'
          AND session_user=current_user
          AND NOT pg_catalog.has_schema_privilege(current_user,'public','CREATE')
          AND NOT EXISTS (SELECT 1 FROM pg_catalog.pg_roles r
              WHERE (r.rolname=current_user OR pg_catalog.pg_has_role(current_user,r.oid,'MEMBER'))
              AND (r.rolsuper OR r.rolbypassrls OR r.rolcreatedb OR r.rolcreaterole OR r.rolreplication))
          AND (SELECT count(*)=12 AND bool_and(c.relkind='r' AND c.relpersistence='p'
              AND NOT c.relispartition AND NOT c.relrowsecurity AND NOT c.relforcerowsecurity
              AND NOT pg_catalog.pg_has_role(current_user,c.relowner,'MEMBER'))
              FROM pg_catalog.pg_class c WHERE c.oid IN (
              pg_catalog.to_regclass('public.hidra_topology_equipment'),
              pg_catalog.to_regclass('public.hidra_topology_facility'),
              pg_catalog.to_regclass('public.hidra_topology_facility_owner_generation'),
              pg_catalog.to_regclass('public.hidra_topology_installed_unit'),
              pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation'),
              pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation_event'),
              pg_catalog.to_regclass('public.hidra_topology_station_graph_head'),
              pg_catalog.to_regclass('public.hidra_topology_station_graph_revision'),
              pg_catalog.to_regclass('public.hidra_topology_station_configuration_revision'),
              pg_catalog.to_regclass('public.hidra_topology_station_configuration_member'),
              pg_catalog.to_regclass('public.hidra_topology_station_configuration_head'),
              pg_catalog.to_regclass('public.hidra_topology_station_configuration_request')))
          AND pg_catalog.has_function_privilege(current_user,
              'public.hidra_station_configuration_lock_sources()','EXECUTE')
          AND pg_catalog.has_table_privilege(current_user,
              'public.hidra_topology_station_configuration_revision','SELECT')
          AND pg_catalog.has_table_privilege(current_user,
              'public.hidra_topology_station_configuration_revision','INSERT')
          AND NOT pg_catalog.has_table_privilege(current_user,
              'public.hidra_topology_station_configuration_revision','UPDATE')
          AND pg_catalog.has_table_privilege(current_user,
              'public.hidra_topology_station_configuration_member','INSERT')
          AND NOT pg_catalog.has_table_privilege(current_user,
              'public.hidra_topology_station_configuration_member','UPDATE')
          AND pg_catalog.has_table_privilege(current_user,
              'public.hidra_topology_station_configuration_head','SELECT')
          AND pg_catalog.has_table_privilege(current_user,
              'public.hidra_topology_station_configuration_head','INSERT')
          AND pg_catalog.has_table_privilege(current_user,
              'public.hidra_topology_station_configuration_head','UPDATE')
          AND pg_catalog.has_table_privilege(current_user,
              'public.hidra_topology_station_configuration_request','INSERT')
          AND pg_catalog.has_table_privilege(current_user,
              'public.hidra_topology_station_configuration_request','SELECT')
          AND NOT pg_catalog.has_table_privilege(current_user,
              'public.hidra_topology_station_configuration_request','UPDATE')
          AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_class c WHERE c.oid IN (
              pg_catalog.to_regclass('public.hidra_topology_equipment'),
              pg_catalog.to_regclass('public.hidra_topology_installed_unit'),
              pg_catalog.to_regclass('public.hidra_topology_installed_unit_installation'),
              pg_catalog.to_regclass('public.hidra_topology_station_graph_revision'))
              AND (pg_catalog.has_table_privilege(current_user,c.oid,'INSERT')
                OR pg_catalog.has_table_privilege(current_user,c.oid,'UPDATE')
                OR pg_catalog.has_table_privilege(current_user,c.oid,'DELETE')))
          AND (""" + LOCK_CHECK + ")";
    private final JdbcTemplate jdbc;
    private final EntityManager em;
    private final ProtectedTransactionExecutor executor;
    private final DataSource ds;
    private final StationInstalledConfigurationCodec codec=new StationInstalledConfigurationCodec();

    public JdbcStationInstalledConfigurationRepositoryAdapter(JdbcTemplate jdbc,EntityManager em,
            ProtectedTransactionExecutor executor) {
        this.jdbc=Objects.requireNonNull(jdbc);this.em=Objects.requireNonNull(em);
        this.executor=Objects.requireNonNull(executor);this.ds=Objects.requireNonNull(jdbc.getDataSource());
    }
    private static IllegalStateException unavailable(){
        return new IllegalStateException("Protected station configuration storage unavailable");
    }
    private static boolean authorizedCaller(){
        return StackWalker.getInstance().walk(frames->frames.anyMatch(frame->
            frame.getClassName().equals("dz.sh.hidra.modules.topology.application.service.ProtectedStationConfigurationAdmissionCoordinator")));
    }
    private void caller(){if(!authorizedCaller())throw unavailable();}
    private ConnectionHolder physical(){
        if(!TransactionSynchronizationManager.isActualTransactionActive()
            || !TransactionSynchronizationManager.isSynchronizationActive()
            || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
            || !(TransactionSynchronizationManager.getResource(ds) instanceof ConnectionHolder holder)
            || holder.isRollbackOnly())throw unavailable();
        return holder;
    }
    private Ticket ticket(Admission a){
        caller();ConnectionHolder h=physical();
        if(!(a instanceof Ticket t) || t.owner!=this || !t.active ||
            t.thread!=Thread.currentThread() || t.holder!=h || t.connection!=h.getConnection())
            throw unavailable();
        return t;
    }
    private void catalog(){
        if(!Boolean.TRUE.equals(jdbc.queryForObject(CATALOG,Boolean.class)))throw unavailable();
    }
    private static Timestamp timestamp(Instant t){return t==null?null:Timestamp.from(t);}
    private static String sha(byte[] bytes){
        try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));}
        catch(java.security.NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}
    }
    private static String commandDigest(StationInstalledConfiguration c){
        byte[] bytes=new StationInstalledConfigurationCodec().encode(c);
        return sha(bytes);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(Command command){
        caller();Ticket ticket=null;
        try{
            Objects.requireNonNull(command);ConnectionHolder holder=physical();
            executor.requireAdmissionForSourceLock();
            jdbc.queryForObject("SELECT public.hidra_station_configuration_lock_sources()",
                (r,n)->r.getObject(1));
            catalog();
            var ids=executor.publicationDatabaseIdentity();
            if(!Boolean.TRUE.equals(jdbc.queryForObject(
                "SELECT current_user=? AND session_user=? AND current_user<>? AND "
              + "NOT pg_catalog.has_table_privilege(?::name,'public.hidra_topology_station_configuration_revision','SELECT')",
                Boolean.class,ids.publisherUser(),ids.publisherUser(),ids.ordinaryUser(),ids.ordinaryUser())))
                throw unavailable();
            ticket=jdbc.queryForObject("SELECT pg_catalog.pg_backend_pid(),pg_catalog.txid_current(),current_user",
                (r,n)->new Ticket(this,command,holder,r.getInt(1),r.getLong(2),r.getString(3)));
            if(ticket==null)throw unavailable();
            TransactionSynchronizationManager.registerSynchronization(ticket);
            return ticket;
        }catch(RuntimeException e){if(ticket!=null)reject(ticket);executor.rejectAdmission();throw e;}
    }
    private StationInstalledConfiguration row(java.sql.ResultSet r)throws java.sql.SQLException{
        byte[] bytes=r.getBytes("canonical_payload");
        String hash=r.getString("canonical_sha256");
        if(bytes==null || !sha(bytes).equals(hash)
                || !StationInstalledConfigurationCodec.FORMAT.equals(r.getString("payload_format")))
            throw unavailable();
        var c=codec.decode(bytes);
        if(!c.companyPartyId().equals(r.getString("company_party_id"))
            || !c.facilityId().equals(r.getString("facility_id"))
            || !c.revisionId().equals(r.getString("revision_id"))
            || c.selectedEpoch()!=r.getLong("selected_epoch")
            || !c.actorBindingId().equals(r.getString("actor_binding_id")))
            throw unavailable();
        return c;
    }
    private StationInstalledConfiguration revision(Ticket t,String revision){
        var rows=jdbc.query("SELECT * FROM public.hidra_topology_station_configuration_revision "
            +"WHERE company_party_id=? AND facility_id=? AND revision_id=?",
            (r,n)->row(r),t.command.companyPartyId(),t.command.facilityId(),revision);
        if(rows.size()>1)throw unavailable();return rows.isEmpty()?null:rows.get(0);
    }
    private List<StationInstalledConfiguration> history(Ticket t,int limit,Long beforeEpoch,boolean lookahead){
        // Fetch one extra authenticated row for a truthful exclusive-epoch next cursor.
        // All these rows join the native referenced-unit authorization before disclosure.
        if(limit<1 || limit>100 || beforeEpoch!=null && beforeEpoch<1)throw unavailable();
        var sql="SELECT * FROM public.hidra_topology_station_configuration_revision "
            +"WHERE company_party_id=? AND facility_id=? ";
        var rows=beforeEpoch==null
            ?jdbc.query(sql+"ORDER BY selected_epoch DESC LIMIT ?",
                (r,n)->row(r),t.command.companyPartyId(),t.command.facilityId(),limit+(lookahead?1:0))
            :jdbc.query(sql+"AND selected_epoch<? ORDER BY selected_epoch DESC LIMIT ?",
                (r,n)->row(r),t.command.companyPartyId(),t.command.facilityId(),beforeEpoch,limit+(lookahead?1:0));
        return List.copyOf(rows);
    }
    private StationInstalledConfiguration current(Ticket t){
        var rows=jdbc.query("SELECT r.* FROM public.hidra_topology_station_configuration_head h "
            +"JOIN public.hidra_topology_station_configuration_revision r ON "
            +"r.company_party_id=h.company_party_id AND r.facility_id=h.facility_id "
            +"AND r.revision_id=h.revision_id AND r.selected_epoch=h.head_epoch "
            +"WHERE h.company_party_id=? AND h.facility_id=?",(r,n)->row(r),
            t.command.companyPartyId(),t.command.facilityId());
        if(rows.size()>1)throw unavailable();return rows.isEmpty()?null:rows.get(0);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Snapshot observe(Admission a,Command command){
        try {
            Ticket t=ticket(a);if(t.snapshot!=null || !t.command.equals(command))throw unavailable();
            catalog();
            var owner=jdbc.query("SELECT owner_party_id,status,facility_kind FROM public.hidra_topology_facility "
                +"WHERE id=?",(r,n)->List.of(r.getString(1),r.getString(2),r.getString(3)),command.facilityId());
            // Do not distinguish a missing, foreign, withdrawn or non-station facility
            // before native Identity has authorized its exact resource scope.
            if(owner.size()!=1 || !owner.get(0).equals(List.of(command.companyPartyId(),"ACTIVE","STATION")))
                throw new org.springframework.security.access.AccessDeniedException(
                    "Protected station configuration unavailable");
            var current=current(t);
            StationInstalledConfiguration requested=null;String digest=null,revision=null;
            if(command.operation()==Operation.READ_REVISION)requested=revision(t,command.revisionId());
            if(command.operation()==Operation.LIST_HISTORY){
                t.snapshot=new Snapshot(current,null,history(t,command.limit(),command.beforeEpoch(),command.lookahead()),null,null);
            }else if(command.operation()==Operation.SAVE) {
                var receipts=jdbc.query("SELECT command_sha256,revision_id FROM "
                    +"public.hidra_topology_station_configuration_request WHERE company_party_id=? "
                    +"AND facility_id=? AND request_id=?",
                    (r,n)->List.of(r.getString(1),r.getString(2)),
                    command.companyPartyId(),command.facilityId(),command.declaration().requestId());
                if(receipts.size()>1)throw unavailable();
                if(!receipts.isEmpty()){digest=receipts.get(0).get(0);revision=receipts.get(0).get(1);
                    requested=revision(t,revision);if(requested==null)throw unavailable();}
                t.snapshot=new Snapshot(current,requested,List.of(),digest,revision);
            } else t.snapshot=new Snapshot(current,requested,List.of(),null,null);
            return t.snapshot;
        }catch(RuntimeException e){reject(a);throw e;}
    }
    @Override public Set<String> mutationSql(){caller();return MUTATIONS;}
    /** Caller engineering values are not native actor identity or receipt timestamps. */
    private StationInstalledConfiguration attributed(StationInstalledConfiguration c,Origin origin){
        // Materialize the SAME canonical graph/list order as the persisted immutable payload.
        // Returning a noncanonical Java record makes a truthful post-write read appear different.
        return codec.decode(codec.encode(new StationInstalledConfiguration(c.companyPartyId(),c.facilityId(),c.revisionId(),
            c.requestId(),c.expectedEpoch(),c.selectedEpoch(),c.graphHeadEpoch(),c.pinnedGraph(),
            c.graphPayloadFormat(),c.graphSha256(),c.effectiveFrom(),c.effectiveUntil(),
            origin.sampledAt(),c.sourceReference(),origin.bindingId(),origin.sessionId(),
            origin.userId(),c.positions(),c.activeEdgeIds())));
    }
    private static boolean samePinnedGraph(StationInstalledConfiguration c,StationInstalledConfiguration stored){
        var graphCodec=new StationInternalGraphRevisionCodec();
        return Arrays.equals(graphCodec.encode(c.pinnedGraph()),graphCodec.encode(stored.pinnedGraph()));
    }
    private static boolean sameEngineering(StationInstalledConfiguration c,StationInstalledConfiguration stored){
        return stored!=null && c.companyPartyId().equals(stored.companyPartyId())
          && c.facilityId().equals(stored.facilityId()) && c.revisionId().equals(stored.revisionId())
          && c.requestId().equals(stored.requestId()) && c.expectedEpoch()==stored.expectedEpoch()
          && c.selectedEpoch()==stored.selectedEpoch() && c.graphHeadEpoch()==stored.graphHeadEpoch()
          && samePinnedGraph(c,stored)
          && c.graphPayloadFormat().equals(stored.graphPayloadFormat())
          && c.graphSha256().equals(stored.graphSha256())
          && c.effectiveFrom().equals(stored.effectiveFrom())
          && Objects.equals(c.effectiveUntil(),stored.effectiveUntil())
          && c.sourceReference().equals(stored.sourceReference())
          && c.positions().equals(stored.positions())
          && c.activeEdgeIds().equals(stored.activeEdgeIds());
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Result execute(Admission a,Command c,Origin origin,Snapshot snapshot){
        try{
            Ticket t=ticket(a);
            if(t.result!=null || t.snapshot!=snapshot || !t.command.equals(c) || origin==null
                || origin.backendPid()!=t.pid || origin.transactionId()!=t.xid)throw unavailable();
            if(c.operation()!=Operation.SAVE){
                t.result=switch(c.operation()){
                    case READ_CURRENT -> new Result(snapshot.current(),List.of(),false);
                    case READ_REVISION -> new Result(snapshot.revision(),List.of(),false);
                    case LIST_HISTORY -> new Result(null,snapshot.history(),false);
                    default -> throw unavailable();
                };
                return t.result;
            }
            var declaration=attributed(c.declaration(),origin);
            String digest=commandDigest(c.declaration());
            if(snapshot.requestDigest()!=null){
                if(!snapshot.requestDigest().equals(digest)
                  || !Objects.equals(snapshot.requestRevision(),declaration.revisionId())
                  || !sameEngineering(c.declaration(),snapshot.revision()))
                    throw new StationInstalledConfigurationFailure(Kind.CONFLICT,null);
                t.result=new Result(snapshot.revision(),List.of(),true);return t.result;
            }
            var current=snapshot.current();
            if(current==null && declaration.expectedEpoch()!=0)throw new StationInstalledConfigurationFailure(Kind.CONFLICT,null);
            if(current!=null && (current.selectedEpoch()!=declaration.expectedEpoch()
              || current.revisionId().equals(declaration.revisionId())))
                throw new StationInstalledConfigurationFailure(Kind.CONFLICT,null);
            byte[] bytes=codec.encode(declaration);
            int revisionRows=jdbc.update(REV_INS,declaration.companyPartyId(),declaration.facilityId(),
                declaration.revisionId(),declaration.requestId(),declaration.expectedEpoch(),
                declaration.selectedEpoch(),declaration.graphHeadEpoch(),declaration.pinnedGraph().revisionId(),
                declaration.graphPayloadFormat(),declaration.graphSha256(),
                StationInstalledConfigurationCodec.FORMAT,bytes,sha(bytes),
                timestamp(declaration.effectiveFrom()),timestamp(declaration.effectiveUntil()),
                timestamp(declaration.recordedAt()),declaration.sourceReference(),
                declaration.actorBindingId(),declaration.actorSessionId(),declaration.actorUserId());
            if(revisionRows!=1)throw unavailable();
            for(Position p:declaration.positions()){
                int n=jdbc.update(MEMBER_INS,declaration.companyPartyId(),declaration.facilityId(),
                    declaration.revisionId(),p.positionId(),p.disposition().name(),
                    p.installedUnitId(),p.installationId(),
                    p.disposition()==StationInstalledConfiguration.Disposition.UNOCCUPIED?null:p.installationRevision(),
                    timestamp(p.installationFrom()),timestamp(p.installationUntil()),p.installationEvidenceReference());
                if(n!=1)throw unavailable();
            }
            int heads=current==null
                ?jdbc.update(HEAD_INS,declaration.companyPartyId(),declaration.facilityId(),
                    declaration.revisionId(),declaration.selectedEpoch())
                :jdbc.update(HEAD_CAS,declaration.revisionId(),declaration.selectedEpoch(),
                    declaration.companyPartyId(),declaration.facilityId(),current.revisionId(),current.selectedEpoch());
            if(heads!=1)throw new StationInstalledConfigurationFailure(Kind.CONFLICT,null);
            int receipts=jdbc.update(REQUEST_INS,declaration.companyPartyId(),declaration.facilityId(),
                declaration.requestId(),digest,declaration.revisionId(),declaration.selectedEpoch(),
                origin.bindingId(),timestamp(origin.sampledAt()),origin.sessionId(),origin.userId(),
                timestamp(origin.sampledAt()),timestamp(origin.validUntil()),
                origin.backendPid(),origin.transactionId(),t.role);
            if(receipts!=1)throw unavailable();
            t.result=new Result(declaration,List.of(),false);
            return t.result;
        }catch(RuntimeException e){reject(a);throw e;}
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void prepareFinal(Admission a,Origin origin,List<ProtectedRelationFence> fences){
        try{
            Ticket t=ticket(a);
            if(t.result==null || t.frozen || origin==null || origin.backendPid()!=t.pid
              || origin.transactionId()!=t.xid || !t.role.equals(executor.publicationDatabaseIdentity().publisherUser()))
                throw unavailable();
            if(fences==null || !fences.containsAll(finalFences(a))
              || fences.stream().map(ProtectedRelationFence::relation).distinct().count()!=fences.size())
                throw unavailable();
            catalog();
            StationInstalledConfiguration current=current(t);
            if(t.command.operation()==Operation.SAVE && !t.result.replayed()
              && !t.result.declaration().equals(current))throw unavailable();
            if(t.command.operation()==Operation.READ_CURRENT && !Objects.equals(t.result.declaration(),current))
                throw unavailable();
            if(t.command.operation()==Operation.READ_REVISION
              && !Objects.equals(t.result.declaration(),revision(t,t.command.revisionId())))throw unavailable();
            if(t.command.operation()==Operation.LIST_HISTORY
              && !t.result.history().equals(history(t,t.command.limit(),t.command.beforeEpoch(),t.command.lookahead())))throw unavailable();
            t.origin=origin;t.fences=List.copyOf(fences);t.expectedEpoch=current==null?0:current.selectedEpoch();
            t.expectedRevision=current==null?null:current.revisionId();t.frozen=true;
        }catch(RuntimeException e){reject(a);throw e;}
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public boolean finalGuard(Admission a){
        Ticket t=ticket(a);
        if(!t.frozen || t.guarded)throw unavailable();
        t.guarded=true;
        Boolean value=jdbc.queryForObject("""
            WITH clock_sample AS MATERIALIZED (SELECT pg_catalog.clock_timestamp() t),
                 head AS MATERIALIZED (
                     SELECT revision_id,head_epoch FROM public.hidra_topology_station_configuration_head
                     WHERE company_party_id=? AND facility_id=?)
            SELECT (SELECT t FROM clock_sample)>=?::timestamptz
              AND (SELECT t FROM clock_sample)<?::timestamptz
              AND pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=?
              AND current_user=? AND session_user=current_user
              AND pg_catalog.current_setting('transaction_isolation')='read committed'
              AND pg_catalog.current_setting('transaction_read_only')='off'
              AND pg_catalog.current_setting('session_replication_role')='origin'
              AND (SELECT count(*) FROM head)=?
              AND COALESCE((SELECT head_epoch FROM head),0)=?
              AND (SELECT revision_id FROM head) IS NOT DISTINCT FROM ?::varchar
              AND (""" + LOCK_CHECK + ")",
            Boolean.class,t.command.companyPartyId(),t.command.facilityId(),
            timestamp(t.origin.sampledAt()),timestamp(t.origin.validUntil()),
            t.pid,t.xid,t.role,t.expectedRevision==null?0:1,t.expectedEpoch,t.expectedRevision);
        return Boolean.TRUE.equals(value);
    }
    @Override public void flush(){caller();physical();em.flush();}
    @Override public void reject(Admission a){
        if(a instanceof Ticket t){t.active=false;t.holder.setRollbackOnly();}
        if(TransactionSynchronizationManager.getResource(ds) instanceof ConnectionHolder h)h.setRollbackOnly();
        executor.rejectAdmission();
    }
    @Override public List<ProtectedRelationFence> finalFences(Admission a){
        Ticket t=ticket(a);return LOCKED.stream().map(s->new ProtectedRelationFence(
            s,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE)).toList();
    }
    private static final class Ticket implements Admission,TransactionSynchronization {
        final JdbcStationInstalledConfigurationRepositoryAdapter owner;
        final Command command;final ConnectionHolder holder;final Connection connection;
        final Thread thread=Thread.currentThread();final int pid;final long xid;final String role;
        boolean active=true,frozen,guarded;
        Snapshot snapshot;Result result;Origin origin;List<ProtectedRelationFence> fences=List.of();
        long expectedEpoch;String expectedRevision;
        Ticket(JdbcStationInstalledConfigurationRepositoryAdapter owner,Command command,
                ConnectionHolder holder,int pid,long xid,String role){
            this.owner=owner;this.command=command;this.holder=holder;this.connection=holder.getConnection();
            this.pid=pid;this.xid=xid;this.role=role;
        }
        @Override public void afterCompletion(int status){active=false;}
        @Override public void beforeCommit(boolean readOnly){
            if(!active || holder.isRollbackOnly() || !guarded)throw unavailable();
        }
        @Override public String toString(){return "Ticket[private station configuration storage]";}
    }
}
