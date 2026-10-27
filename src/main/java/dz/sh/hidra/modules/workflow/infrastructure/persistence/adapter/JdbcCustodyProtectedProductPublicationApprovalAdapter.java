/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcCustodyProtectedProductPublicationApprovalAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter
 *
 * @Description : Provides bounded product source publication governance.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.workflow.application.contract.custody.CustodyProtectedProductPublicationApprovalContract;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.support.*;

/** Actual configured terminal Workflow evidence; never creates an approval. */
@Repository
public class JdbcCustodyProtectedProductPublicationApprovalAdapter implements CustodyProtectedProductPublicationApprovalContract {
    private static final List<String> RELATIONS=List.of("public.hidra_workflow_action","public.hidra_workflow_custody_publication_policy","public.hidra_workflow_definition","public.hidra_workflow_definition_target_binding","public.hidra_workflow_instance","public.hidra_workflow_state_history","public.hidra_workflow_step","public.hidra_workflow_step_assignment_rule","public.hidra_workflow_task","public.hidra_workflow_transition","public.hidra_workflow_type_catalog");
    private static final Set<String> TRIGGERS=Set.of("public.hidra_workflow_action|tr_hmr081_action_immutable|27|hidra_hmr081_action_immutable|c16b56323aabbb5c0274d03a52d44915",
        "public.hidra_workflow_action|tr_hmr081_action_insert|7|hidra_hmr081_action_insert_guard|f16de495d500bb349302b6511a3cf2dc",
        "public.hidra_workflow_action|tr_hmr081_action_truncate|34|hidra_hmr081_action_immutable|c16b56323aabbb5c0274d03a52d44915",
        "public.hidra_workflow_definition_target_binding|tr_hmr055_binding|23|hidra_hmr055_binding_guard|bcc1fe0b0d5a67479b6e362e39f1e4f3",
        "public.hidra_workflow_definition|tr_hmr061_activation|23|hidra_hmr061_activation_guard|bc8230076c085e634b40c8469b0b71c5",
        "public.hidra_workflow_instance|tr_hmr055_start|23|hidra_hmr055_start_guard|9404f84f41dbb9c6dced331e04d7db68",
        "public.hidra_workflow_state_history|tr_hmr099_history_immutable|27|hidra_hmr099_history_immutable|54f3ad981366e4759387f0934029d9e2",
        "public.hidra_workflow_state_history|tr_hmr099_history_insert|7|hidra_hmr099_history_insert_guard|05be9bdffc95f897663abc73bc021814",
        "public.hidra_workflow_state_history|tr_hmr099_history_truncate|34|hidra_hmr099_history_immutable|54f3ad981366e4759387f0934029d9e2",
        "public.hidra_workflow_task|tr_hmr066_task_delete|11|hidra_hmr066_task_delete_guard|5628a9cfaf783cb5e77bb0d97b5b18cb",
        "public.hidra_workflow_task|tr_hmr066_task_truncate|34|hidra_hmr066_task_truncate_guard|4f07b412ed33d1bc1ace59a9b2e69217",
        "public.hidra_workflow_task|tr_hmr066_task|23|hidra_hmr066_task_guard|4ec9d3411f7204b2c58b5f58201c9aa0",
        "public.hidra_workflow_transition|tr_hmr061_configuration|23|hidra_hmr061_configuration_guard|434064b5e869cbb5429d9339b8a5926b");
    private final JdbcTemplate jdbc;private final DataSource source;
    public JdbcCustodyProtectedProductPublicationApprovalAdapter(JdbcTemplate jdbc){this.jdbc=Objects.requireNonNull(jdbc);source=Objects.requireNonNull(jdbc.getDataSource());}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(Request request) {
        try {
            Objects.requireNonNull(request);var holder=context();
            trustedPath();for(String name:RELATIONS)jdbc.execute("LOCK TABLE "+name+" IN SHARE MODE NOWAIT");
            safe();
            var t=jdbc.queryForObject("SELECT pg_catalog.pg_backend_pid(),pg_catalog.txid_current(),current_user,pg_catalog.clock_timestamp()",
                (row,n)->new Ticket(request,holder,row.getInt(1),row.getLong(2),row.getString(3),row.getTimestamp(4).toInstant()));
            if(t==null)throw invalid();TransactionSynchronizationManager.registerSynchronization(t);return t;
        }catch(RuntimeException failure){rollback(null);throw failure;}
    }
    @Override public Contribution read(Admission admission) {
        try {var t=current(admission);if(!t.used.compareAndSet(false,true))throw invalid();t.result=snapshot(t);return t.result;}
        catch(RuntimeException failure){rollback(admission);throw failure;}
    }
    @Override public void requireCurrent(Admission admission) {
        try {var t=current(admission);if(t.result==null || !t.result.equals(snapshot(t)))throw invalid();}
        catch(RuntimeException failure){rollback(admission);throw failure;}
    }
    @Override public List<ProtectedRelationFence> finalFences(Admission admission) {
        try {current(admission);return RELATIONS.stream().map(n->new ProtectedRelationFence(n,ProtectedRelationFence.Mode.SHARE)).toList();}
        catch(RuntimeException failure){rollback(admission);throw failure;}
    }
    private Contribution snapshot(Ticket t) {
        trustedPath();safe();
        check("SELECT pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=? AND current_user=? "
            +"AND pg_catalog.clock_timestamp()>=? AND pg_catalog.clock_timestamp()<?",t.pid,t.xid,t.role,Timestamp.from(t.at),Timestamp.from(t.at.plusMillis(500)));
        var policies=jdbc.query("""
            SELECT p.company_party_id,p.definition_id,p.definition_version,p.target_type_id,p.purpose_id,p.updated_at
            FROM public.hidra_workflow_custody_publication_policy p
            JOIN public.hidra_workflow_definition d ON d.id=p.definition_id AND d.version=p.definition_version AND d.status='ACTIVE'
            JOIN public.hidra_workflow_type_catalog ty ON ty.id=p.target_type_id AND ty.catalog_name='WORKFLOW_TARGET_TYPE' AND ty.code='PRODUCT_SOURCE_PUBLICATION' AND ty.active
            JOIN public.hidra_workflow_type_catalog pu ON pu.id=p.purpose_id AND pu.catalog_name='WORKFLOW_PURPOSE' AND pu.code='PRODUCT_SOURCE_PUBLICATION_APPROVAL' AND pu.active
            WHERE p.company_party_id=? AND p.active AND
              (SELECT count(*) FROM public.hidra_workflow_definition_target_binding b WHERE b.definition_id=p.definition_id
                AND b.target_module='custody' AND b.target_type_id=p.target_type_id AND b.workflow_purpose_id=p.purpose_id AND b.active)=1
            """,(row,n)->new Policy(row.getString(1),row.getString(2),row.getInt(3),row.getString(4),row.getString(5),row.getTimestamp(6).toInstant()),t.request.companyPartyId());
        if(policies.size()!=1)throw invalid();var p=policies.getFirst();Evidence approval=null;
        if(t.request.targetDigest()!=null) {
            var evidence=jdbc.query("""
                SELECT i.id,t.id,a.id,h.id,a.actor_id,a.actor_display_name_snapshot,a.acted_at
                FROM public.hidra_workflow_instance i
                JOIN public.hidra_workflow_task t ON t.instance_id=i.id
                JOIN public.hidra_workflow_action a ON a.instance_id=i.id AND a.task_id=t.id
                JOIN public.hidra_workflow_state_history h ON h.instance_id=i.id AND h.task_id=t.id AND h.action_id=a.id
                JOIN public.hidra_workflow_step s ON s.id=t.step_id AND s.definition_id=i.definition_id
                JOIN public.hidra_workflow_step e ON e.id=i.current_step_id AND e.definition_id=i.definition_id
                WHERE i.id=? AND t.id=? AND a.id=? AND i.definition_id=? AND i.definition_version=?
                  AND i.target_module='custody' AND i.target_type_id=? AND i.workflow_purpose_id=? AND i.target_id=?
                  AND i.status='COMPLETED' AND i.cancelled_at IS NULL AND t.status='APPROVED'
                  AND a.action_type='APPROVE' AND a.decision='APPROVE' AND a.source_system='HIDRA_API' AND a.actor_id=t.completed_by_actor_id
                  AND a.acted_at=t.completed_at AND a.acted_at=i.completed_at AND a.acted_at<=?
                  AND a.actor_id IS NOT NULL AND btrim(a.actor_id)<>'' AND btrim(a.actor_display_name_snapshot)<>''
                  AND a.action_sequence=(SELECT max(x.action_sequence) FROM public.hidra_workflow_action x WHERE x.instance_id=i.id)
                  AND h.actor_id=a.actor_id AND h.actor_display_name_snapshot=a.actor_display_name_snapshot
                  AND h.from_step_id=t.step_id AND h.to_step_id=i.current_step_id AND h.to_status='COMPLETED' AND h.changed_at=a.acted_at
                  AND NOT EXISTS(SELECT 1 FROM public.hidra_workflow_transition x WHERE x.definition_id=i.definition_id AND x.from_step_id=i.current_step_id)
                """,(row,n)->new Evidence(p,t.request.targetDigest(),row.getString(1),row.getString(2),row.getString(3),row.getString(4),row.getString(5),row.getString(6),row.getTimestamp(7).toInstant()),
                t.request.instanceId(),t.request.taskId(),t.request.actionId(),p.definitionId(),p.definitionVersion(),p.targetTypeId(),p.purposeId(),t.request.targetDigest(),Timestamp.from(t.at));
            if(evidence.size()!=1)throw invalid();approval=evidence.getFirst();
        }
        return new Contribution(p,approval,t.pid,t.xid,t.at);
    }
    private void safe() {
        check("""
            SELECT pg_catalog.current_setting('transaction_isolation')='read committed'
              AND pg_catalog.current_setting('transaction_read_only')='off'
              AND pg_catalog.current_setting('session_replication_role')='origin'
              AND NOT pg_catalog.has_schema_privilege(current_user,'public','CREATE')
              AND NOT pg_catalog.has_schema_privilege(current_user,'pg_catalog','CREATE')
              AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_roles r WHERE (r.rolname=current_user OR pg_catalog.pg_has_role(current_user,r.oid,'MEMBER')) AND (r.rolsuper OR r.rolbypassrls))
            """);
        for(String name:RELATIONS) {
            check("""
                SELECT c.relkind='r' AND c.relpersistence='p' AND NOT c.relrowsecurity AND NOT c.relforcerowsecurity AND NOT c.relispartition
                  AND NOT pg_catalog.pg_has_role(current_user,c.relowner,'MEMBER') AND NOT pg_catalog.has_table_privilege(current_user,c.oid,'TRIGGER')
                  AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_inherits WHERE inhrelid=c.oid OR inhparent=c.oid)
                  AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_rewrite WHERE ev_class=c.oid)
                  AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_constraint k WHERE k.conrelid=c.oid AND (NOT k.convalidated OR k.condeferrable))
                  AND EXISTS(SELECT 1 FROM pg_catalog.pg_constraint k JOIN pg_catalog.pg_index z ON z.indexrelid=k.conindid
                    WHERE k.conrelid=c.oid AND k.contype='p' AND z.indisvalid AND z.indisready AND z.indisunique)
                  AND EXISTS(SELECT 1 FROM pg_catalog.pg_locks l WHERE l.pid=pg_catalog.pg_backend_pid() AND l.relation=c.oid AND l.locktype='relation' AND l.mode='ShareLock' AND l.granted)
                FROM pg_catalog.pg_class c WHERE c.oid=?::regclass
                """,name);
        }
        var actual=new HashSet<String>();
        for(String name:RELATIONS) {
            var images=jdbc.query("""
                SELECT t.tgname,t.tgtype,p.proname,pg_catalog.md5(p.prosrc),t.tgenabled='O' AND t.tgqual IS NULL
                  AND t.tgnargs=0 AND pg_catalog.octet_length(t.tgargs)=0 AND NOT p.prosecdef AND p.proconfig IS NULL
                  AND p.provolatile='v' AND p.proparallel='u' AND p.prokind='f' AND p.pronargs=0 AND NOT p.proretset AND NOT p.proleakproof AND p.prosupport=0
                  AND n.nspname='public' AND l.lanname='plpgsql' AND NOT pg_catalog.pg_has_role(current_user,p.proowner,'MEMBER')
                FROM pg_catalog.pg_trigger t JOIN pg_catalog.pg_proc p ON p.oid=t.tgfoid
                JOIN pg_catalog.pg_namespace n ON n.oid=p.pronamespace JOIN pg_catalog.pg_language l ON l.oid=p.prolang
                WHERE t.tgrelid=?::regclass AND NOT t.tgisinternal
                """,(row,i)->{if(!row.getBoolean(5))throw invalid();return name+"|"+row.getString(1)+"|"+row.getInt(2)+"|"+row.getString(3)+"|"+row.getString(4);},name);
            actual.addAll(images);
        }
        if(!TRIGGERS.equals(actual))throw invalid();
        check("""
            SELECT (SELECT count(*)=8 AND bool_and(attnotnull AND NOT atthasdef AND attgenerated='' AND attidentity='')
              AND array_agg(attnum::int ORDER BY attnum)=ARRAY[1,2,3,4,5,6,7,8] AND array_agg(attname::text ORDER BY attnum)=
              ARRAY['company_party_id','definition_id','definition_version','target_type_id','purpose_id','active','created_at','updated_at']
              AND array_agg(atttypid::int ORDER BY attnum)=ARRAY[1043,1043,23,1043,1043,16,1184,1184]
              AND array_agg(atttypmod ORDER BY attnum)=ARRAY[84,84,-1,84,84,-1,-1,-1]
              FROM pg_catalog.pg_attribute WHERE attrelid='public.hidra_workflow_custody_publication_policy'::regclass AND attnum>0 AND NOT attisdropped)
              AND (SELECT count(*)=7 AND count(*) FILTER(WHERE contype='p')=1 AND count(*) FILTER(WHERE contype='f')=3
                AND count(*) FILTER(WHERE contype='c')=3 FROM pg_catalog.pg_constraint WHERE conrelid='public.hidra_workflow_custody_publication_policy'::regclass)
            """);
    }
    private Ticket current(Admission admission){
        if(!(admission instanceof Ticket t) || t.issuer!=this || !t.active || t.rejected || t.thread!=Thread.currentThread())throw invalid();
        var h=context();if(h!=t.holder || h.getConnection()!=t.connection)throw invalid();return t;
    }
    private ConnectionHolder context(){if(!TransactionSynchronizationManager.isActualTransactionActive() || !TransactionSynchronizationManager.isSynchronizationActive()
        || TransactionSynchronizationManager.isCurrentTransactionReadOnly() || !(TransactionSynchronizationManager.getResource(source) instanceof ConnectionHolder h) || h.isRollbackOnly())throw invalid();return h;}
    private void trustedPath(){String s=jdbc.queryForObject("SELECT pg_catalog.array_to_json(pg_catalog.current_schemas(true))::pg_catalog.text",String.class);
        if(s==null || !(s.equals("[\"pg_catalog\"]") || s.startsWith("[\"pg_catalog\",")))throw invalid();}
    private void check(String sql,Object... args){if(!Boolean.TRUE.equals(jdbc.queryForObject(sql,Boolean.class,args)))throw invalid();}
    private void rollback(Admission admission){if(admission instanceof Ticket t){t.rejected=true;t.holder.setRollbackOnly();}
        Object h=TransactionSynchronizationManager.getResource(source);if(h instanceof ConnectionHolder holder)holder.setRollbackOnly();}
    private static IllegalStateException invalid(){return new IllegalStateException("Protected publication approval unavailable");}
    private final class Ticket implements Admission,TransactionSynchronization {
        private final JdbcCustodyProtectedProductPublicationApprovalAdapter issuer=JdbcCustodyProtectedProductPublicationApprovalAdapter.this;
        private final Request request;private final ConnectionHolder holder;private final Connection connection;
        private final Thread thread=Thread.currentThread();private final int pid;private final long xid;private final String role;private final Instant at;
        private final AtomicBoolean used=new AtomicBoolean();private volatile boolean active=true,rejected;private Contribution result;
        private Ticket(Request request,ConnectionHolder holder,int pid,long xid,String role,Instant at){this.request=request;this.holder=holder;this.connection=holder.getConnection();this.pid=pid;this.xid=xid;this.role=role;this.at=at;}
        @Override public void suspend(){active=false;rejected=true;}
        @Override public void beforeCommit(boolean readOnly){if(!active || rejected || readOnly || result==null || thread!=Thread.currentThread() || holder.isRollbackOnly())throw invalid();}
        @Override public void afterCompletion(int status){active=false;}
        @Override public String toString(){return "Admission[opaque publication approval]";}
    }
}
