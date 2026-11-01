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
public final class JdbcInstalledCharacteristicLinkRepositoryAdapter implements InstalledCharacteristicLinkStoragePort {
    private static final String REV="public.hidra_topology_installed_characteristic_revision";
    private static final String HEAD="public.hidra_topology_installed_characteristic_head";
    private static final String RECEIPT="public.hidra_topology_installed_characteristic_request";
    public static final String INSERT_REVISION="INSERT INTO public.hidra_topology_installed_characteristic_revision (company_party_id,installed_unit_id,link_id,link_revision,source_id,source_revision_id,source_sha256,source_kind,effective_from,effective_until,recorded_at,evidence_reference,installation_id,operation_binding_id,actor_session_id,actor_user_id,backend_pid,transaction_id,database_role) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String INSERT_HEAD="INSERT INTO public.hidra_topology_installed_characteristic_head (company_party_id,installed_unit_id,link_id,link_revision,head_epoch) VALUES (?,?,?,?,?)";
    public static final String CAS_HEAD="UPDATE public.hidra_topology_installed_characteristic_head SET link_revision=?,head_epoch=? WHERE company_party_id=? AND installed_unit_id=? AND link_id=? AND head_epoch=?";
    public static final String INSERT_REQUEST="INSERT INTO public.hidra_topology_installed_characteristic_request (company_party_id,installed_unit_id,link_id,request_id,command_sha256,link_revision,actor_binding_id,actor_session_id,actor_user_id,backend_pid,transaction_id,database_role) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final Set<String> MUTATIONS=Set.of(INSERT_REVISION,INSERT_HEAD,CAS_HEAD,INSERT_REQUEST);
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
    @Override public Set<String> mutationSql(){return MUTATIONS;}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(Command command){
        caller();Ticket t=null;
        try {
            var h=holder();
            executor.requireCharacteristicLinkSourceLock();
            jdbc.queryForObject("SELECT public.hidra_topology_characteristic_lock_sources()",(r,n)->Boolean.TRUE);
            if(!Boolean.TRUE.equals(jdbc.queryForObject(CATALOG,Boolean.class)))throw denied();
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
        var start=r.getTimestamp("effective_from"),end=r.getTimestamp("effective_until"),at=r.getTimestamp("recorded_at");
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
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Result perform(Admission admission,Command c,Origin origin,Snapshot s){
        Ticket t=ticket(admission);
        if(!t.observed.get()||t.observation!=s||!t.command.equals(c)
                ||!t.performed.compareAndSet(false,true))throw denied();
        requireOrigin(t,origin);
        if(c.operation()!=Operation.SAVE_DRAFT){
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
        ticket(admission);
        return List.of(new ProtectedRelationFence("public.hidra_simulation_manufacturer_characteristic_revision",ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
                new ProtectedRelationFence("public.hidra_topology_installed_unit",ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
                new ProtectedRelationFence(REV,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
                new ProtectedRelationFence(HEAD,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
                new ProtectedRelationFence(RECEIPT,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
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
