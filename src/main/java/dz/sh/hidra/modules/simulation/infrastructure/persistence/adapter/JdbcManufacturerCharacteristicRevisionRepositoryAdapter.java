/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcManufacturerCharacteristicRevisionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter
 *
 * @Description : Original-transaction manufacturer canonical source and terminal approval persistence.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.simulation.application.port.in.ManufacturerCharacteristicRevisionUseCase;
import dz.sh.hidra.modules.simulation.application.port.out.ManufacturerCharacteristicRevisionRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevision;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/** Only trusted reviewed mutation SQL may pass ProtectedTransactionExecutor's provisional phase. */
@Repository
public class JdbcManufacturerCharacteristicRevisionRepositoryAdapter
        implements ManufacturerCharacteristicRevisionRepositoryPort {
    public static final String SOURCE="public.hidra_simulation_manufacturer_characteristic_revision";
    public static final String QUALIFICATION="public.hidra_simulation_manufacturer_characteristic_qualification";
    private static final String INSERT_SOURCE="""
        INSERT INTO public.hidra_simulation_manufacturer_characteristic_revision
        (company_party_id,source_id,revision_id,manufacturer_party_id,manufacturer_model_reference,
         payload_format,canonical_payload,payload_sha256,workflow_target_digest,document_sha256,
         source_recorded_at,effective_from,effective_until,source_origin,creator_user_id,
         operation_binding_id,session_id,backend_pid,transaction_id,database_role,inserted_at)
        VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ON CONFLICT (company_party_id,source_id,revision_id) DO NOTHING
        """;
    private static final String INSERT_QUALIFICATION="""
        INSERT INTO public.hidra_simulation_manufacturer_characteristic_qualification
        (qualification_id,company_party_id,source_id,revision_id,source_sha256,workflow_target_digest,
         workflow_instance_id,workflow_task_id,workflow_action_id,approver_id,approver_display_name,
         approved_at,qualified_at,qualifying_user_id,operation_binding_id,backend_pid,transaction_id,database_role)
        VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ON CONFLICT (qualification_id) DO NOTHING
        """;
    private static final String SELECT_SOURCE="SELECT * FROM "+SOURCE
        +" WHERE company_party_id=? AND source_id=? AND revision_id=?";
    private static final String SELECT_QUAL="SELECT * FROM "+QUALIFICATION
        +" WHERE company_party_id=? AND source_id=? AND revision_id=?";
    private final JdbcTemplate jdbc;
    private final DataSource dataSource;
    private final ManufacturerCharacteristicRevisionCodec codec=new ManufacturerCharacteristicRevisionCodec();
    public JdbcManufacturerCharacteristicRevisionRepositoryAdapter(JdbcTemplate jdbc){
        this.jdbc=Objects.requireNonNull(jdbc);this.dataSource=Objects.requireNonNull(jdbc.getDataSource());
    }
    @Override public byte[] encode(ManufacturerCharacteristicRevision revision){return codec.encode(revision);}
    @Override public String sha256(byte[] bytes){return codec.sha256(bytes);}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(ManufacturerCharacteristicRevisionUseCase.Command command) {
        try {
            ConnectionHolder holder=holder();
            if(!Boolean.TRUE.equals(jdbc.queryForObject("""
                    SELECT p.prosecdef AND p.provolatile='v'
                      AND pg_catalog.has_function_privilege(current_user,p.oid,'EXECUTE')
                      AND NOT EXISTS(SELECT 1 FROM pg_catalog.aclexplode(COALESCE(p.proacl,
                             pg_catalog.acldefault('f',p.proowner))) a
                             WHERE a.grantee=0 AND a.privilege_type='EXECUTE')
                    FROM pg_catalog.pg_proc p WHERE p.oid=
                       'public.hidra_simulation_manufacturer_lock_sources()'::regprocedure
                    """,Boolean.class)))throw denied();
            jdbc.queryForObject("SELECT public.hidra_simulation_manufacturer_lock_sources()", (r,n)->Boolean.TRUE);
            Ticket ticket=jdbc.queryForObject(
                "SELECT pg_catalog.pg_backend_pid(),pg_catalog.txid_current(),current_user",
                (r,n)->new Ticket(this,holder,holder.getConnection(),r.getInt(1),r.getLong(2),r.getString(3)));
            if(ticket==null)throw denied();
            TransactionSynchronizationManager.registerSynchronization(ticket);
            return ticket;
        }catch(RuntimeException fault){rollback();throw denied();}
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Stored> read(Admission admission,Key key){
        Ticket t=current(admission);
        return one(SELECT_SOURCE,key.companyPartyId(),key.sourceId(),key.revisionId());
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public List<Stored> history(Admission admission,String company,String source){
        current(admission);
        List<Stored> rows=jdbc.query("SELECT * FROM "+SOURCE
                +" WHERE company_party_id=? AND source_id=? ORDER BY source_recorded_at,revision_id LIMIT 257",
                (r,n)->stored(r),company,source);
        if(rows.size()>256)throw denied();
        return List.copyOf(rows);
    }

    /** Streams every immutable row for canonical-integrity proof, retaining only page summaries. */
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public HistoryScan scanHistory(Admission admission,String company,String source,int size,
            dz.sh.hidra.modules.simulation.application.port.out.ManufacturerCharacteristicHistoryCursorPort.Position after,
            int maximumPrincipals) {
        current(admission);
        if(size<1 || size>10_000 || maximumPrincipals<1 || maximumPrincipals>64)
            throw denied();
        String beyond = after == null ? "TRUE"
                : "(source_recorded_at,revision_id)>(?::timestamptz,?::varchar)";
        String query = "SELECT *, "+beyond+" AS beyond FROM "+SOURCE
                +" WHERE company_party_id=? AND source_id=? ORDER BY source_recorded_at,revision_id";
        try {
            return jdbc.query(connection -> {
                var statement=connection.prepareStatement(query,
                        java.sql.ResultSet.TYPE_FORWARD_ONLY,java.sql.ResultSet.CONCUR_READ_ONLY);
                statement.setFetchSize(32);
                int index=1;
                if(after!=null){
                    statement.setTimestamp(index++,java.sql.Timestamp.from(after.recordedAt()));
                    statement.setString(index++,after.revisionId());
                }
                statement.setString(index++,company);
                statement.setString(index,source);
                return statement;
            },rs->{
                var principals=new java.util.LinkedHashSet<Principal>();
                var candidates=new java.util.ArrayList<ManufacturerCharacteristicRevisionUseCase.RevisionSummary>();
                boolean any=false;
                while(rs.next()){
                    any=true;
                    // Each row is decoded, SHA checked and compared with its relational owner identity.
                    // No canonical bytes or decoded families are retained beyond this iteration.
                    Stored stored=stored(rs);
                    if(!company.equals(stored.key().companyPartyId())
                            ||!source.equals(stored.key().sourceId()))throw denied();
                    var manufacturer=stored.revision();
                    principals.add(new Principal(manufacturer.manufacturerPartyId(),
                            manufacturer.manufacturerModelReference()));
                    if(principals.size()>maximumPrincipals)throw denied();
                    java.time.Instant persisted=rs.getObject("source_recorded_at",
                            java.time.OffsetDateTime.class).toInstant();
                    if(persisted.getNano()%1_000!=0)throw denied();
                    if(rs.getBoolean("beyond") && candidates.size()<=size)
                        candidates.add(new ManufacturerCharacteristicRevisionUseCase.RevisionSummary(
                            source,stored.key().revisionId(),stored.payloadSha256(),persisted,
                            manufacturer.effectiveFrom(),manufacturer.effectiveUntil(),false));
                }
                if(!any)throw denied();
                boolean more=candidates.size()>size;
                if(more)candidates.remove(candidates.size()-1);
                return new HistoryScan(List.copyOf(principals),List.copyOf(candidates),more);
            });
        }catch(RuntimeException fault){throw denied();}
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Stored> byWorkflowTarget(Admission admission,String digest){
        current(admission);
        if(digest==null||!digest.matches("[0-9a-f]{64}"))throw denied();
        var rows=jdbc.query("SELECT * FROM "+SOURCE+" WHERE workflow_target_digest=?",(r,n)->stored(r),digest);
        if(rows.size()>1)throw denied();
        return rows.stream().findFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Qualification> qualification(Admission admission,Key key){
        current(admission);
        var rows=jdbc.query(SELECT_QUAL,(r,n)->qualification(r),
                key.companyPartyId(),key.sourceId(),key.revisionId());
        if(rows.size()>1)throw denied();
        return rows.stream().findFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Stored append(Admission admission,Stored desired){
        Ticket t=provisionalTicket(admission);
        if(desired==null||t.mutated)throw denied();
        byte[] canonical=codec.encode(desired.revision());
        if(!Arrays.equals(canonical,desired.canonicalPayload())
                || !codec.sha256(canonical).equals(desired.payloadSha256()))throw denied();
        jdbc.update(INSERT_SOURCE,desired.key().companyPartyId(),desired.key().sourceId(),desired.key().revisionId(),
                desired.revision().manufacturerPartyId(),desired.revision().manufacturerModelReference(),
                ManufacturerCharacteristicRevisionCodec.FORMAT,canonical,desired.payloadSha256(),
                desired.workflowTargetDigest(),desired.revision().documentSha256(),
                Timestamp.from(desired.revision().recordedAt()),Timestamp.from(desired.revision().effectiveFrom()),
                desired.revision().effectiveUntil()==null?null:Timestamp.from(desired.revision().effectiveUntil()),
                desired.revision().origin().name(),desired.creatorUserId(),desired.bindingId(),desired.sessionId(),
                t.pid,t.xid,t.role,Timestamp.from(desired.insertedAt()));
        // Provisional phase permits only this one pinned DML; verify the winning row afterwards.
        t.mutated=true;
        return desired;
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Qualification appendQualification(Admission admission,Qualification desired){
        Ticket t=provisionalTicket(admission);
        if(desired==null||t.mutated)throw denied();
        jdbc.update(INSERT_QUALIFICATION,desired.qualificationId(),desired.key().companyPartyId(),
                desired.key().sourceId(),desired.key().revisionId(),desired.sourceSha256(),
                desired.workflowTargetDigest(),desired.workflowInstanceId(),desired.workflowTaskId(),
                desired.workflowActionId(),desired.approverId(),desired.approverDisplayName(),
                Timestamp.from(desired.approvedAt()),Timestamp.from(desired.qualifiedAt()),
                desired.qualifyingUserId(),desired.bindingId(),t.pid,t.xid,t.role);
        t.mutated=true;return desired;
    }
    @Override public void requireCurrent(Admission admission){current(admission);}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void prepareFinal(Admission admission,List<ProtectedRelationFence> fences,Instant observedAt,Instant validUntil){
        Ticket t=current(admission);
        if(fences==null||fences.isEmpty()||t.frozen||observedAt==null||validUntil==null
                ||!validUntil.isAfter(observedAt))throw denied();
        t.observedAt=observedAt;
        t.validUntil=validUntil;
        t.fences=List.copyOf(fences);
        // Physical source images are not cached from a different transaction.
        t.frozen=true;
    }
    @Override public List<ProtectedRelationFence> finalFences(Admission admission) {
        current(admission);
        return List.of(new ProtectedRelationFence(SOURCE,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
                new ProtectedRelationFence(QUALIFICATION,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
    }
    @Override public Set<String> mutationSql(){return Set.of(INSERT_SOURCE,INSERT_QUALIFICATION);}
    public String sourceInsertSql(){return INSERT_SOURCE;}
    public String qualificationInsertSql(){return INSERT_QUALIFICATION;}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public boolean finalGuard(Admission admission){
        if(!(admission instanceof Ticket t) || t.owner!=this || !t.active
                || t.thread!=Thread.currentThread() || t.holder!=holder()
                || t.connection!=t.holder.getConnection() || !t.frozen || t.guarded)throw denied();
        // One final physical SQL scalar. Each owner relation must still be locked in the original PID.
        var b=new StringBuilder("SELECT pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=?"
                +" AND current_user=? AND pg_catalog.clock_timestamp()>=?::timestamptz"
                +" AND pg_catalog.clock_timestamp()<?::timestamptz");
        List<Object> args=new ArrayList<>();args.add(t.pid);args.add(t.xid);args.add(t.role);
        args.add(Timestamp.from(t.observedAt));args.add(Timestamp.from(t.validUntil));
        for(var fence:t.fences) {
            b.append(" AND EXISTS(SELECT 1 FROM pg_catalog.pg_locks WHERE pid=pg_catalog.pg_backend_pid()")
                .append(" AND granted AND locktype='relation' AND relation=?::regclass AND mode=?)");
            args.add(fence.relation());
            args.add(switch(fence.mode()){
                case ACCESS_SHARE->"AccessShareLock";
                case SHARE->"ShareLock";
                case SHARE_ROW_EXCLUSIVE->"ShareRowExclusiveLock";
            });
        }
        t.guarded=Boolean.TRUE.equals(jdbc.queryForObject(b.toString(),Boolean.class,args.toArray()));
        return t.guarded;
    }
    @Override public void flush(){/* JDBC writes are immediate; final images are observed before scalar guard. */}
    private Optional<Stored> one(String sql,Object... args){
        List<Stored> rows=jdbc.query(sql,(r,n)->stored(r),args);
        if(rows.size()>1)throw denied();return rows.stream().findFirst();
    }
    private Stored stored(java.sql.ResultSet r)throws java.sql.SQLException {
        byte[] payload=r.getBytes("canonical_payload");
        if(payload==null||!codec.sha256(payload).equals(r.getString("payload_sha256")))throw denied();
        ManufacturerCharacteristicRevision value=codec.decode(payload);
        Key key=new Key(r.getString("company_party_id"),r.getString("source_id"),r.getString("revision_id"));
        if(!key.sourceId().equals(value.sourceId())||!key.revisionId().equals(value.revisionId())
                ||!r.getString("manufacturer_party_id").equals(value.manufacturerPartyId())
                ||!r.getString("manufacturer_model_reference").equals(value.manufacturerModelReference())
                ||!r.getString("document_sha256").equals(value.documentSha256())
                ||!ManufacturerCharacteristicRevisionCodec.FORMAT.equals(r.getString("payload_format")))throw denied();
        return new Stored(key,value,r.getString("payload_sha256"),r.getString("workflow_target_digest"),
                r.getString("creator_user_id"),r.getString("operation_binding_id"),r.getString("session_id"),
                r.getInt("backend_pid"),r.getLong("transaction_id"),r.getString("database_role"),
                r.getObject("inserted_at",OffsetDateTime.class).toInstant(),payload);
    }
    private Qualification qualification(java.sql.ResultSet r)throws java.sql.SQLException {
        return new Qualification(r.getString("qualification_id"),new Key(r.getString("company_party_id"),
                r.getString("source_id"),r.getString("revision_id")),r.getString("source_sha256"),
                r.getString("workflow_target_digest"),r.getString("workflow_instance_id"),
                r.getString("workflow_task_id"),r.getString("workflow_action_id"),
                r.getString("approver_id"),r.getString("approver_display_name"),
                r.getObject("approved_at",OffsetDateTime.class).toInstant(),
                r.getObject("qualified_at",OffsetDateTime.class).toInstant(),r.getString("qualifying_user_id"),
                r.getString("operation_binding_id"),r.getInt("backend_pid"),r.getLong("transaction_id"),
                r.getString("database_role"));
    }
    /** No pre-DML SELECT is legal in the executor's one-use PROVISIONAL phase. */
    private Ticket provisionalTicket(Admission a) {
        if(!(a instanceof Ticket t)||t.owner!=this||!t.active||t.thread!=Thread.currentThread()
                ||t.holder!=holder()||t.connection!=t.holder.getConnection())throw denied();
        return t;
    }
    private Ticket current(Admission a){
        if(!(a instanceof Ticket t)||t.owner!=this||!t.active||t.thread!=Thread.currentThread()
                ||t.holder!=holder()||t.connection!=t.holder.getConnection())throw denied();
        boolean same=Boolean.TRUE.equals(jdbc.queryForObject(
                "SELECT pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=? AND current_user=?"
                +" AND pg_catalog.current_setting('transaction_isolation')='read committed'"
                +" AND pg_catalog.current_setting('transaction_read_only')='off'",
                Boolean.class,t.pid,t.xid,t.role));
        if(!same)throw denied();
        return t;
    }
    private ConnectionHolder holder(){
        if(!TransactionSynchronizationManager.isActualTransactionActive()
                ||!TransactionSynchronizationManager.isSynchronizationActive()
                ||TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ||!(TransactionSynchronizationManager.getResource(dataSource) instanceof ConnectionHolder h)
                ||h.isRollbackOnly())throw denied();
        return h;
    }
    private void rollback(){
        Object h=TransactionSynchronizationManager.getResource(dataSource);
        if(h instanceof ConnectionHolder holder)holder.setRollbackOnly();
    }
    private static IllegalStateException denied(){return new IllegalStateException("Manufacturer source unavailable");}
    private static final class Ticket implements Admission,TransactionSynchronization{
        private final JdbcManufacturerCharacteristicRevisionRepositoryAdapter owner;
        private final ConnectionHolder holder;private final Connection connection;
        private final Thread thread=Thread.currentThread();
        private final int pid;private final long xid;private final String role;
        private volatile boolean active=true,mutated,frozen,guarded;
        private Instant observedAt,validUntil;
        private List<ProtectedRelationFence> fences=List.of();
        Ticket(JdbcManufacturerCharacteristicRevisionRepositoryAdapter owner,ConnectionHolder holder,
                Connection connection,int pid,long xid,String role){
            this.owner=owner;this.holder=holder;this.connection=connection;this.pid=pid;this.xid=xid;this.role=role;
        }
        @Override public void beforeCommit(boolean readOnly){
            if(!active||readOnly||holder.isRollbackOnly()||!guarded)throw denied();
        }
        @Override public void afterCompletion(int status){active=false;}
        @Override public void suspend(){active=false;}
        @Override public String toString(){return "ManufacturerSourceAdmission[opaque]";}
    }
}
