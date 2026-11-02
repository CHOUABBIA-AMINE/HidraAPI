/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 *
 * @Name        : JdbcInstalledCharacteristicLinkRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Restricted immutable draft-link writer with physical source guards and exact replay.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.InstalledCharacteristicLinkStoragePort;
import dz.sh.hidra.modules.topology.application.port.out.InstalledCharacteristicLinkStoragePort.*;
import dz.sh.hidra.modules.topology.domain.model.InstalledCharacteristicLink;
import dz.sh.hidra.modules.topology.domain.model.InstalledCharacteristicLinkApproval;
import dz.sh.hidra.modules.topology.domain.service.InstalledCharacteristicLinkApprovalTargetDigester;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledCharacteristicLinkAuthorizationContract.Origin;
import dz.sh.hidra.platform.persistence.ProtectedCredentialRoutingDataSource;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/** This adapter owns storage only; it cannot manufacture an Identity or Simulation authorization. */
@Repository
public class JdbcInstalledCharacteristicLinkRepositoryAdapter implements InstalledCharacteristicLinkStoragePort {
    private static final String REV="public.hidra_topology_installed_characteristic_revision";
    private static final String HEAD="public.hidra_topology_installed_characteristic_head";
    private static final String RECEIPT="public.hidra_topology_installed_characteristic_request";
    public static final String INSERT_REVISION="INSERT INTO public.hidra_topology_installed_characteristic_revision (company_party_id,installed_unit_id,link_id,link_revision,source_id,source_revision_id,source_sha256,source_kind,effective_from,effective_until,recorded_at,evidence_reference,installation_id,operation_binding_id,actor_session_id,actor_user_id,backend_pid,transaction_id,database_role) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String INSERT_HEAD="INSERT INTO public.hidra_topology_installed_characteristic_head (company_party_id,installed_unit_id,link_id,link_revision,head_epoch) VALUES (?,?,?,?,?)";
    public static final String CAS_HEAD="UPDATE public.hidra_topology_installed_characteristic_head SET link_revision=?,head_epoch=? WHERE company_party_id=? AND installed_unit_id=? AND link_id=? AND head_epoch=?";
    public static final String INSERT_REQUEST="INSERT INTO public.hidra_topology_installed_characteristic_request (company_party_id,installed_unit_id,link_id,request_id,command_sha256,link_revision,actor_binding_id,actor_session_id,actor_user_id,backend_pid,transaction_id,database_role) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final Set<String> MUTATIONS=Set.of(INSERT_REVISION,INSERT_HEAD,CAS_HEAD,INSERT_REQUEST);
    private static final String APPROVAL="public.hidra_topology_installed_characteristic_approval";
    private static final String APPROVAL_RECEIPT="public.hidra_topology_installed_characteristic_approval_request";
    private static final String INSERT_APPROVAL="INSERT INTO "+APPROVAL+" (company_party_id,installed_unit_id,link_id,link_revision,target_sha256,draft_actor_user_id,approving_actor_user_id,workflow_definition_id,workflow_definition_version,workflow_target_type_id,workflow_purpose_id,workflow_instance_id,workflow_task_id,workflow_action_id,workflow_acted_at,approved_at,operation_binding_id,actor_session_id,backend_pid,transaction_id,database_role) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_APPROVAL_RECEIPT="INSERT INTO "+APPROVAL_RECEIPT+" (company_party_id,installed_unit_id,link_id,request_id,link_revision,command_sha256,approving_actor_user_id,operation_binding_id,actor_session_id,backend_pid,transaction_id,database_role) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final Set<String> APPROVAL_MUTATIONS=Set.of(INSERT_APPROVAL,INSERT_APPROVAL_RECEIPT);
    private static final String FIELDS="company_party_id,installed_unit_id,link_id,link_revision,source_id,source_revision_id,source_sha256,source_kind,effective_from,effective_until,recorded_at,evidence_reference,installation_id";
    private static final String FILTER=" company_party_id=? AND installed_unit_id=? AND link_id=?";
    private static final String CATALOG="""
        SELECT current_user=session_user
          AND current_setting('transaction_isolation')='read committed'
          AND current_setting('transaction_read_only')='off'
          AND current_setting('session_replication_role')='origin'
          AND NOT pg_catalog.has_schema_privilege(current_user,'public','CREATE')
          AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_roles r
            WHERE (r.rolname=current_user OR pg_catalog.pg_has_role(current_user,r.oid,'MEMBER'))
              AND (r.rolsuper OR r.rolbypassrls OR r.rolcreatedb OR r.rolcreaterole OR r.rolreplication))
          AND pg_catalog.has_function_privilege(current_user,'public.hidra_topology_characteristic_lock_sources()','EXECUTE')
          AND pg_catalog.has_table_privilege(current_user,'public.hidra_topology_installed_characteristic_revision','SELECT')
          AND pg_catalog.has_table_privilege(current_user,'public.hidra_topology_installed_characteristic_revision','INSERT')
          AND NOT pg_catalog.has_table_privilege(current_user,'public.hidra_topology_installed_characteristic_revision','UPDATE,DELETE')
          AND pg_catalog.has_table_privilege(current_user,'public.hidra_topology_installed_characteristic_head','SELECT')
          AND pg_catalog.has_table_privilege(current_user,'public.hidra_topology_installed_characteristic_head','INSERT')
          AND pg_catalog.has_table_privilege(current_user,'public.hidra_topology_installed_characteristic_head','UPDATE')
          AND NOT pg_catalog.has_table_privilege(current_user,'public.hidra_topology_installed_characteristic_head','DELETE')
          AND pg_catalog.has_table_privilege(current_user,'public.hidra_topology_installed_characteristic_request','SELECT')
          AND pg_catalog.has_table_privilege(current_user,'public.hidra_topology_installed_characteristic_request','INSERT')
          AND NOT pg_catalog.has_table_privilege(current_user,'public.hidra_topology_installed_characteristic_request','UPDATE,DELETE')
          AND NOT pg_catalog.has_table_privilege(current_user,'public.hidra_topology_installed_unit','INSERT,UPDATE,DELETE')
          AND NOT pg_catalog.has_table_privilege(current_user,'public.hidra_simulation_manufacturer_characteristic_revision','INSERT,UPDATE,DELETE')
        """;
    private final JdbcTemplate jdbc;
    private final DataSource ds;
    private final ProtectedTransactionExecutor executor;
    public JdbcInstalledCharacteristicLinkRepositoryAdapter(JdbcTemplate jdbc,ProtectedTransactionExecutor executor){
        this.jdbc=Objects.requireNonNull(jdbc);this.executor=Objects.requireNonNull(executor);
        this.ds=Objects.requireNonNull(jdbc.getDataSource());
    }
    private static IllegalStateException denied(){return new IllegalStateException("Protected draft characteristic storage unavailable");}
    private static boolean authorizedCaller() {
        return StackWalker.getInstance().walk(stream->stream.anyMatch(f->
            f.getClassName().equals("dz.sh.hidra.modules.topology.application.service.ProtectedInstalledCharacteristicLinkAdmissionCoordinator")));
    }
    private void caller(){if(!authorizedCaller())throw denied();}
    private ConnectionHolder holder(){
        if(!TransactionSynchronizationManager.isActualTransactionActive()
                ||!TransactionSynchronizationManager.isSynchronizationActive()
                ||TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ||!(TransactionSynchronizationManager.getResource(ds) instanceof ConnectionHolder h)
                ||h.isRollbackOnly())throw denied();
        return h;
    }
    private Ticket ticket(Admission admission){
        caller();
        if(!(admission instanceof Ticket t)||t.owner!=this||!t.active||t.thread!=Thread.currentThread()
                ||t.holder!=holder()||t.connection!=t.holder.getConnection())throw denied();
        return t;
    }
    private static Timestamp ts(Instant i){return i==null?null:Timestamp.from(i);}
    private static String sha(String s){
        try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(s.getBytes(StandardCharsets.UTF_8)));}
        catch(java.security.NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}
    }
    @Override public String commandDigest(Command c){
        if(c.operation()!=Operation.SAVE_DRAFT)throw denied();
        var r=c.draft();
        StringBuilder value=new StringBuilder("HIDRA_CHARACTERISTIC_LINK_DRAFT_REQUEST_V1");
        for(String v:List.of(c.companyPartyId(),c.installedUnitId(),c.linkId(),c.requestId(),
                c.expectedEpoch().toString(),r.linkRevision()+"",r.sourceId(),r.sourceRevisionId(),
                r.sourceSha256(),r.kind().name(),r.effectiveFrom().toString(),
                r.effectiveUntil()==null?"NULL":r.effectiveUntil().toString(),
                r.recordedAt().toString(),r.evidenceReference(),
                r.installationId()==null?"NULL":r.installationId())){
            value.append('|').append(v.getBytes(StandardCharsets.UTF_8).length).append(':').append(v);
        }
        return sha(value.toString());
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void requireInstallation(Admission admission,InstalledCharacteristicLink row) {
        Ticket t=ticket(admission);
        if(row==null||!isApproval(t.command.operation()))throw denied();
        if(row.installationId()==null)return; // explicit absence is source evidence, not a guessed installation
        Boolean qualified=jdbc.queryForObject("""
            SELECT EXISTS(SELECT 1 FROM public.hidra_topology_installed_unit_installation i
              WHERE i.installation_id=? AND i.company_party_id=? AND i.unit_id=?
                AND i.effective_from<=?
                AND (i.effective_until IS NULL OR i.effective_until>=?)
                AND (?::timestamptz IS NULL OR i.effective_until IS NULL OR i.effective_until>=?))
            """,Boolean.class,row.installationId(),row.companyPartyId(),row.installedUnitId(),
                ts(row.effectiveFrom()),ts(row.effectiveFrom()),ts(row.effectiveUntil()),ts(row.effectiveUntil()));
        if(!Boolean.TRUE.equals(qualified))throw denied();
    }
    @Override public Set<String> mutationSql(){return MUTATIONS;}
    @Override public Set<String> approvalMutationSql(){return APPROVAL_MUTATIONS;}
    private static boolean isApproval(Operation op) {
        return op==Operation.APPROVE||op==Operation.READ_APPROVAL||op==Operation.LIST_APPROVAL_HISTORY;
    }
    @Override public String approvalCommandDigest(Command c,String actor,String targetDigest){
        if(c.operation()!=Operation.APPROVE||actor==null||actor.isBlank()
                ||targetDigest==null||!targetDigest.matches("[0-9a-f]{64}"))throw denied();
        var e=c.approval();
        var parts=List.of(c.companyPartyId(),c.installedUnitId(),c.linkId(),
                c.revision().toString(),c.expectedEpoch().toString(),c.requestId(),
                targetDigest,actor,e.definitionId(),Integer.toString(e.definitionVersion()),
                e.targetTypeId(),e.purposeId(),e.instanceId(),e.taskId(),e.actionId());
        var b=new StringBuilder("HIDRA_TOPOLOGY_CHARACTERISTIC_APPROVAL_REQUEST_V1");
        for(String x:parts)b.append('|').append(x.getBytes(StandardCharsets.UTF_8).length).append(':').append(x);
        return sha(b.toString());
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(Command command){
        caller();Ticket t=null;
        try {
            var h=holder();
            if(isApproval(command.operation())) {
                executor.requireCharacteristicLinkApprovalSourceLock();
                jdbc.queryForObject("SELECT public.hidra_topology_characteristic_lock_approval_sources()",(r,n)->Boolean.TRUE);
            } else {
                executor.requireCharacteristicLinkSourceLock();
                jdbc.queryForObject("SELECT public.hidra_topology_characteristic_lock_sources()",(r,n)->Boolean.TRUE);
            }
            if(!Boolean.TRUE.equals(jdbc.queryForObject(CATALOG,Boolean.class)))throw denied();
            if(isApproval(command.operation())) {
                String extra = "SELECT "
                    +"pg_catalog.has_function_privilege(current_user,'public.hidra_topology_characteristic_lock_approval_sources()','EXECUTE') "
                    +"AND pg_catalog.has_table_privilege(current_user,'public.hidra_topology_installed_characteristic_approval','SELECT') "
                    +"AND pg_catalog.has_table_privilege(current_user,'public.hidra_topology_installed_characteristic_approval_request','SELECT') "
                    +"AND NOT pg_catalog.has_table_privilege(current_user,'public.hidra_topology_installed_characteristic_approval','UPDATE,DELETE') "
                    +"AND NOT pg_catalog.has_table_privilege(current_user,'public.hidra_topology_installed_characteristic_approval_request','UPDATE,DELETE') "
                    +(command.operation()==Operation.APPROVE
                      ?"AND pg_catalog.has_table_privilege(current_user,'public.hidra_topology_installed_characteristic_approval','INSERT') "
                      +"AND pg_catalog.has_table_privilege(current_user,'public.hidra_topology_installed_characteristic_approval_request','INSERT')"
                      :"");
                if(!Boolean.TRUE.equals(jdbc.queryForObject(extra,Boolean.class)))throw denied();
            }
            var ids=executor.publicationDatabaseIdentity();
            if(!Boolean.TRUE.equals(jdbc.queryForObject(
                "SELECT current_user=? AND session_user=? AND current_user<>? AND NOT "
                    +"pg_catalog.has_table_privilege(?::name,'public.hidra_topology_installed_characteristic_revision','SELECT')",
                Boolean.class,ids.publisherUser(),ids.publisherUser(),ids.ordinaryUser(),ids.ordinaryUser())))throw denied();
            t=jdbc.queryForObject("SELECT pg_catalog.pg_backend_pid(),pg_catalog.txid_current(),current_user",
                    (rs,n)->new Ticket(this,command,h,rs.getInt(1),rs.getLong(2),rs.getString(3)));
            if(t==null)throw denied();
            TransactionSynchronizationManager.registerSynchronization(t);
            return t;
        }catch(RuntimeException ex){reject(t);executor.rejectAdmission();throw ex;}
    }
    private static InstalledCharacteristicLink map(ResultSet r,int row) throws SQLException {
        Timestamp start=r.getTimestamp("effective_from");
        Timestamp end=r.getTimestamp("effective_until");
        Timestamp at=r.getTimestamp("recorded_at");
        return new InstalledCharacteristicLink(r.getString("link_id"),r.getLong("link_revision"),
                r.getString("company_party_id"),r.getString("installed_unit_id"),
                r.getString("source_id"),r.getString("source_revision_id"),r.getString("source_sha256"),
                InstalledCharacteristicLink.Kind.valueOf(r.getString("source_kind")),
                start.toInstant(),end==null?null:end.toInstant(),at.toInstant(),
                r.getString("evidence_reference"),r.getString("installation_id"));
    }
    private List<InstalledCharacteristicLink> rows(String sql,Object...params) {
        return jdbc.query(sql,JdbcInstalledCharacteristicLinkRepositoryAdapter::map,params);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Snapshot observe(Admission admission,Command c){
        Ticket t=ticket(admission);
        if(!t.command.equals(c)||!t.observed.compareAndSet(false,true))throw denied();
        String from=" FROM "+REV+" WHERE "+FILTER;
        Object[] keys={c.companyPartyId(),c.installedUnitId(),c.linkId()};
        var epochs=jdbc.query("SELECT head_epoch FROM "+HEAD+" WHERE "+FILTER,(r,n)->r.getLong(1),keys);
        if(epochs.size()>1)throw denied();
        Long epoch=epochs.isEmpty()?null:epochs.getFirst();
        InstalledCharacteristicLink current=null,revision=null,replay=null;
        List<InstalledCharacteristicLink> history=List.of();
        Receipt receipt=null;
        Long next=null;
        switch(c.operation()){
            case READ_CURRENT -> {
                var list=rows("SELECT "+FIELDS+from+" AND link_revision=(SELECT link_revision FROM "+HEAD+" WHERE "+FILTER+")",
                    c.companyPartyId(),c.installedUnitId(),c.linkId(),c.companyPartyId(),c.installedUnitId(),c.linkId());
                if(list.size()>1)throw denied();
                current=list.isEmpty()?null:list.getFirst();
            }
            case READ_REVISION -> {
                var list=rows("SELECT "+FIELDS+from+" AND link_revision=?",
                        c.companyPartyId(),c.installedUnitId(),c.linkId(),c.revision());
                if(list.size()>1)throw denied();
                revision=list.isEmpty()?null:list.getFirst();
            }
            case LIST_HISTORY -> {
                var list=c.beforeRevision()==null
                    ?rows("SELECT "+FIELDS+from+" ORDER BY link_revision DESC LIMIT ?",
                        c.companyPartyId(),c.installedUnitId(),c.linkId(),c.limit()+1)
                    :rows("SELECT "+FIELDS+from+" AND link_revision<? ORDER BY link_revision DESC LIMIT ?",
                        c.companyPartyId(),c.installedUnitId(),c.linkId(),c.beforeRevision(),c.limit()+1);
                if(list.size()>c.limit()) {
                    history=List.copyOf(list.subList(0,c.limit()));
                    next=history.getLast().linkRevision();
                } else history=List.copyOf(list);
            }
            case APPROVE -> {
                var list=rows("SELECT "+FIELDS+from+" AND link_revision=?",
                    c.companyPartyId(),c.installedUnitId(),c.linkId(),c.revision());
                if(list.size()!=1)throw denied();
                revision=list.getFirst();
                var heads=rows("SELECT "+FIELDS+from+" AND link_revision=(SELECT link_revision FROM "+HEAD+
                        " WHERE "+FILTER+")",c.companyPartyId(),c.installedUnitId(),c.linkId(),
                        c.companyPartyId(),c.installedUnitId(),c.linkId());
                if(heads.size()!=1)throw denied();
                current=heads.getFirst();
            }
            case READ_APPROVAL -> {
                var rows=rows("SELECT "+FIELDS+from+" AND link_revision=?",
                    c.companyPartyId(),c.installedUnitId(),c.linkId(),c.revision());
                if(rows.size()>1)throw denied();
                revision=rows.isEmpty()?null:rows.getFirst();
            }
            case LIST_APPROVAL_HISTORY -> {
                var qualified=java.util.Arrays.stream(FIELDS.split(","))
                        .map(col->"r."+col).collect(java.util.stream.Collectors.joining(","));
                var query="SELECT "+qualified+" FROM "+REV+" r JOIN "+APPROVAL+
                    " a ON a.company_party_id=r.company_party_id AND a.installed_unit_id=r.installed_unit_id"+
                    " AND a.link_id=r.link_id AND a.link_revision=r.link_revision WHERE"+
                    " a.company_party_id=? AND a.installed_unit_id=? AND a.link_id=?";
                var list=c.beforeRevision()==null?
                    rows(query+" ORDER BY a.link_revision DESC LIMIT ?",
                        c.companyPartyId(),c.installedUnitId(),c.linkId(),c.limit()+1):
                    rows(query+" AND a.link_revision<? ORDER BY a.link_revision DESC LIMIT ?",
                        c.companyPartyId(),c.installedUnitId(),c.linkId(),c.beforeRevision(),c.limit()+1);
                if(list.size()>c.limit()) {
                    history=List.copyOf(list.subList(0,c.limit()));
                    next=history.getLast().linkRevision();
                }else history=List.copyOf(list);
            }
            case SAVE_DRAFT -> {
                var receipts=jdbc.query("SELECT command_sha256,link_revision FROM "+RECEIPT+
                    " WHERE "+FILTER+" AND request_id=?",(r,n)->new Receipt(r.getString(1),r.getLong(2)),
                    c.companyPartyId(),c.installedUnitId(),c.linkId(),c.requestId());
                if(receipts.size()>1)throw denied();
                if(!receipts.isEmpty()) {
                    receipt=receipts.getFirst();
                    var old=rows("SELECT "+FIELDS+from+" AND link_revision=?",
                            c.companyPartyId(),c.installedUnitId(),c.linkId(),receipt.linkRevision());
                    if(old.size()!=1)throw denied();
                    replay=old.getFirst();
                }
            }
        }
        t.observation=new Snapshot(current,revision,replay,history,receipt,epoch,next);
        return t.observation;
    }
    private static final String APPROVAL_FIELDS=
        "a.target_sha256,a.draft_actor_user_id,a.approving_actor_user_id,"+
        "a.workflow_definition_id,a.workflow_definition_version,a.workflow_target_type_id,"+
        "a.workflow_purpose_id,a.workflow_instance_id,a.workflow_task_id,a.workflow_action_id,"+
        "a.workflow_acted_at,a.approved_at";
    private static InstalledCharacteristicLinkApproval approval(ResultSet rs,int n)throws SQLException {
        var link=map(rs,n);
        var workflow=new InstalledCharacteristicLinkApproval.WorkflowEvidence(
                rs.getString("workflow_definition_id"),rs.getInt("workflow_definition_version"),
                rs.getString("workflow_target_type_id"),
                InstalledCharacteristicLinkApproval.TARGET_TYPE_CODE,
                rs.getString("workflow_purpose_id"),InstalledCharacteristicLinkApproval.PURPOSE_CODE,
                rs.getString("workflow_instance_id"),rs.getString("workflow_task_id"),
                rs.getString("workflow_action_id"),rs.getString("approving_actor_user_id"),
                rs.getTimestamp("workflow_acted_at").toInstant());
        return new InstalledCharacteristicLinkApproval(link,rs.getString("draft_actor_user_id"),
                rs.getString("approving_actor_user_id"),rs.getString("target_sha256"),
                workflow,rs.getTimestamp("approved_at").toInstant());
    }
    private List<InstalledCharacteristicLinkApproval> approvalRows(String suffix,Object...params) {
        String fields=java.util.Arrays.stream(FIELDS.split(","))
            .map(column->"r."+column).collect(java.util.stream.Collectors.joining(","));
        return jdbc.query("SELECT "+fields+","+APPROVAL_FIELDS+" FROM "+REV+" r JOIN "+APPROVAL+
            " a ON a.company_party_id=r.company_party_id AND a.installed_unit_id=r.installed_unit_id"+
            " AND a.link_id=r.link_id AND a.link_revision=r.link_revision WHERE"+
            " a.company_party_id=? AND a.installed_unit_id=? AND a.link_id=?"+suffix,
            JdbcInstalledCharacteristicLinkRepositoryAdapter::approval,params);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public ApprovalObservation observeApproval(Admission admission,Command c) {
        Ticket t=ticket(admission);
        if(!t.command.equals(c)||!isApproval(c.operation())||!t.observed.get())throw denied();
        var keys=new Object[]{c.companyPartyId(),c.installedUnitId(),c.linkId()};
        var existing=List.<InstalledCharacteristicLinkApproval>of();
        List<InstalledCharacteristicLinkApproval> history=List.of();
        ApprovalReceipt receipt=null;
        String draftActor=null;
        Long next=null;
        if(c.operation()==Operation.APPROVE||c.operation()==Operation.READ_APPROVAL) {
            existing=approvalRows(" AND a.link_revision=?",
                c.companyPartyId(),c.installedUnitId(),c.linkId(),c.revision());
            if(existing.size()>1)throw denied();
        }
        if(c.operation()==Operation.APPROVE) {
            var actors=jdbc.queryForList("SELECT actor_user_id FROM "+REV+" WHERE "+FILTER+
                        " AND link_revision=?",String.class,
                    c.companyPartyId(),c.installedUnitId(),c.linkId(),c.revision());
            if(actors.size()!=1)throw denied();
            draftActor=actors.getFirst();
            var receipts=jdbc.query("SELECT command_sha256,link_revision,approving_actor_user_id FROM "+
                    APPROVAL_RECEIPT+" WHERE "+FILTER+" AND request_id=?",
                (rs,n)->new ApprovalReceipt(rs.getString(1),rs.getLong(2),rs.getString(3)),
                c.companyPartyId(),c.installedUnitId(),c.linkId(),c.requestId());
            if(receipts.size()>1)throw denied();
            if(!receipts.isEmpty())receipt=receipts.getFirst();
        }
        if(c.operation()==Operation.LIST_APPROVAL_HISTORY) {
            var all=c.beforeRevision()==null?
                approvalRows(" ORDER BY a.link_revision DESC LIMIT ?",
                    c.companyPartyId(),c.installedUnitId(),c.linkId(),c.limit()+1):
                approvalRows(" AND a.link_revision<? ORDER BY a.link_revision DESC LIMIT ?",
                    c.companyPartyId(),c.installedUnitId(),c.linkId(),c.beforeRevision(),c.limit()+1);
            if(all.size()>c.limit()){
                history=List.copyOf(all.subList(0,c.limit()));
                next=history.getLast().target().linkRevision();
            }else history=List.copyOf(all);
        }
        return new ApprovalObservation(existing.isEmpty()?null:existing.getFirst(),history,draftActor,receipt,next);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Result performApproval(Admission admission,Command c,Origin origin,
            Snapshot draft,ApprovalObservation observation,InstalledCharacteristicLinkApproval approved) {
        Ticket t=ticket(admission);requireOrigin(t,origin);
        if(c.operation()!=Operation.APPROVE||!t.observed.get()||!t.performed.compareAndSet(false,true)
                ||t.observation!=draft||observation==null||approved==null
                ||draft.revision()==null||draft.headEpoch()==null
                ||!draft.headEpoch().equals(c.expectedEpoch())
                ||!approved.target().equals(draft.revision())
                ||!approved.approvingActorUserId().equals(origin.userId())
                ||!approved.draftActorUserId().equals(observation.draftActorUserId()))throw denied();
        String digest=approvalCommandDigest(c,origin.userId(),approved.targetSha256());
        if(observation.receipt()!=null){
            if(!digest.equals(observation.receipt().commandSha256())
                    ||observation.receipt().linkRevision()!=c.revision()
                    ||!origin.userId().equals(observation.receipt().approvingActorUserId())
                    ||observation.existing()==null
                    ||!approved.workflow().equals(observation.existing().workflow())
                    ||!approved.targetSha256().equals(observation.existing().targetSha256()))
                throw conflict();
            return new Result(approved.target(),List.of(),true,null,observation.existing(),List.of());
        }
        if(observation.existing()!=null)throw conflict();
        var wf=approved.workflow();
        int n=jdbc.update(INSERT_APPROVAL,c.companyPartyId(),c.installedUnitId(),c.linkId(),c.revision(),
                approved.targetSha256(),approved.draftActorUserId(),origin.userId(),
                wf.definitionId(),wf.definitionVersion(),wf.targetTypeId(),wf.purposeId(),
                wf.instanceId(),wf.taskId(),wf.actionId(),ts(wf.actedAt()),ts(approved.approvedAt()),
                origin.bindingId(),origin.sessionId(),origin.backendPid(),origin.transactionId(),origin.databaseRole());
        if(n!=1)throw denied();
        n=jdbc.update(INSERT_APPROVAL_RECEIPT,c.companyPartyId(),c.installedUnitId(),c.linkId(),
                c.requestId(),c.revision(),digest,origin.userId(),origin.bindingId(),origin.sessionId(),
                origin.backendPid(),origin.transactionId(),origin.databaseRole());
        if(n!=1)throw denied();
        return new Result(approved.target(),List.of(),false,null,approved,List.of());
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Result perform(Admission admission,Command c,Origin origin,Snapshot s){
        Ticket t=ticket(admission);
        if(!t.observed.get()||t.observation!=s||!t.command.equals(c)
                ||!t.performed.compareAndSet(false,true))throw denied();
        requireOrigin(t,origin);
        if(c.operation()!=Operation.SAVE_DRAFT){
            // Read-only approval operations complete the same original protected storage ticket.
            return new Result(c.operation()==Operation.READ_CURRENT?s.current():s.revision(),
                    c.operation()==Operation.LIST_HISTORY?s.history():List.of(),false,s.nextBeforeRevision());
        }
        String digest=commandDigest(c);
        if(s.receipt()!=null){
            if(!digest.equals(s.receipt().commandSha256()))throw conflict();
            if(s.replayRevision()==null)throw denied();
            return new Result(s.replayRevision(),List.of(),true,null);
        }
        long expected=c.expectedEpoch();
        if(expected!=(s.headEpoch()==null?0L:s.headEpoch()))throw conflict();
        var v=c.draft();
        if(v.linkRevision()!=expected+1)throw conflict();
        int n=jdbc.update(INSERT_REVISION,c.companyPartyId(),c.installedUnitId(),c.linkId(),v.linkRevision(),
                v.sourceId(),v.sourceRevisionId(),v.sourceSha256(),v.kind().name(),ts(v.effectiveFrom()),
                ts(v.effectiveUntil()),ts(v.recordedAt()),v.evidenceReference(),v.installationId(),
                origin.bindingId(),origin.sessionId(),origin.userId(),origin.backendPid(),origin.transactionId(),
                origin.databaseRole());
        if(n!=1)throw denied();
        if(expected==0){
            n=jdbc.update(INSERT_HEAD,c.companyPartyId(),c.installedUnitId(),c.linkId(),v.linkRevision(),1);
        }else {
            n=jdbc.update(CAS_HEAD,v.linkRevision(),expected+1,c.companyPartyId(),c.installedUnitId(),c.linkId(),expected);
        }
        if(n!=1)throw conflict();
        n=jdbc.update(INSERT_REQUEST,c.companyPartyId(),c.installedUnitId(),c.linkId(),c.requestId(),digest,
                v.linkRevision(),origin.bindingId(),origin.sessionId(),origin.userId(),
                origin.backendPid(),origin.transactionId(),origin.databaseRole());
        if(n!=1)throw denied();
        return new Result(v,List.of(),false,null);
    }
    private static RuntimeException conflict(){return new dz.sh.hidra.modules.topology.application.model.InstalledCharacteristicLinkFailure(
            dz.sh.hidra.modules.topology.application.model.InstalledCharacteristicLinkFailure.Kind.CONFLICT);}
    private void requireOrigin(Ticket ticket,Origin origin){
        if(origin==null||ticket.backendPid!=origin.backendPid()||ticket.transactionId!=origin.transactionId()
                ||!ticket.role.equals(origin.databaseRole()))throw denied();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void prepareFinal(Admission admission,Origin origin,List<ProtectedRelationFence> fences){
        Ticket t=ticket(admission);
        requireOrigin(t,origin);
        if(!t.performed.get()||t.frozen||fences==null||fences.isEmpty())throw denied();
        for(var fence:fences){
            if(!Boolean.TRUE.equals(jdbc.queryForObject("""
                  SELECT EXISTS(SELECT 1 FROM pg_catalog.pg_locks
                   WHERE pid=pg_catalog.pg_backend_pid() AND locktype='relation' AND granted
                     AND relation=pg_catalog.to_regclass(?) AND mode=?)
                  """,Boolean.class,fence.relation(),fence.mode().catalog())))throw denied();
        }
        t.frozen=true;
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public boolean finalGuard(Admission admission) {
        Ticket t=ticket(admission);
        if(!t.frozen||t.guarded)throw denied();
        t.guarded=Boolean.TRUE.equals(jdbc.queryForObject("""
            SELECT pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=? AND current_user=?
              AND current_user=session_user
              AND current_setting('transaction_isolation')='read committed'
              AND current_setting('transaction_read_only')='off'
              AND current_setting('session_replication_role')='origin'
              AND (SELECT count(*)=5 FROM (VALUES
                ('public.hidra_simulation_manufacturer_characteristic_revision'),
                ('public.hidra_topology_installed_unit'),
                ('public.hidra_topology_installed_characteristic_revision'),
                ('public.hidra_topology_installed_characteristic_head'),
                ('public.hidra_topology_installed_characteristic_request')) locked(relation)
                WHERE EXISTS(SELECT 1 FROM pg_catalog.pg_locks
                  WHERE pid=pg_catalog.pg_backend_pid() AND locktype='relation' AND granted
                    AND mode='ShareRowExclusiveLock'
                    AND relation=pg_catalog.to_regclass(locked.relation)))
            """,Boolean.class,t.backendPid,t.transactionId,t.role));
        return t.guarded;
    }
    @Override public List<ProtectedRelationFence> finalFences(Admission admission){
        Ticket t=ticket(admission);
        var fences=new java.util.ArrayList<ProtectedRelationFence>();
        fences.add(new ProtectedRelationFence("public.hidra_simulation_manufacturer_characteristic_revision",ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
        fences.add(new ProtectedRelationFence("public.hidra_topology_installed_unit",ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
        fences.add(new ProtectedRelationFence(REV,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
        fences.add(new ProtectedRelationFence(HEAD,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
        fences.add(new ProtectedRelationFence(RECEIPT,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
        if(isApproval(t.command.operation())) {
            for(String relation:List.of("public.hidra_topology_installed_unit_installation",APPROVAL,APPROVAL_RECEIPT))
                fences.add(new ProtectedRelationFence(relation,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
            if(t.command.operation()==Operation.APPROVE) {
                for(String relation:List.of(
                    "public.hidra_workflow_action","public.hidra_workflow_definition",
                    "public.hidra_workflow_definition_target_binding","public.hidra_workflow_instance",
                    "public.hidra_workflow_state_history","public.hidra_workflow_step",
                    "public.hidra_workflow_task","public.hidra_workflow_transition",
                    "public.hidra_workflow_type_catalog"))
                    fences.add(new ProtectedRelationFence(relation,ProtectedRelationFence.Mode.SHARE));
            }
        }
        return List.copyOf(fences);
    }
    @Override public void flush(){/* JDBC writes complete synchronously; outer protected flush remains mandatory. */}
    @Override public void reject(Admission admission){
        if(admission instanceof Ticket t){t.active=false;t.holder.setRollbackOnly();}
        Object bound=TransactionSynchronizationManager.getResource(ds);
        if(bound instanceof ConnectionHolder h)h.setRollbackOnly();
    }
    private static final class Ticket implements Admission,TransactionSynchronization {
        final JdbcInstalledCharacteristicLinkRepositoryAdapter owner;
        final Command command;final ConnectionHolder holder;final Connection connection;
        final int backendPid;final long transactionId;final String role;
        final Thread thread=Thread.currentThread();
        final AtomicBoolean observed=new AtomicBoolean(),performed=new AtomicBoolean();
        boolean active=true,frozen,guarded;
        Snapshot observation;
        Ticket(JdbcInstalledCharacteristicLinkRepositoryAdapter owner,Command command,ConnectionHolder holder,
               int backendPid,long transactionId,String role){
            this.owner=owner;this.command=command;this.holder=holder;this.connection=holder.getConnection();
            this.backendPid=backendPid;this.transactionId=transactionId;this.role=role;
        }
        @Override public void suspend(){active=false;}
        @Override public void beforeCommit(boolean readOnly){if(!active||!guarded||holder.isRollbackOnly())throw denied();}
        @Override public void afterCompletion(int code){active=false;}
    }
}
