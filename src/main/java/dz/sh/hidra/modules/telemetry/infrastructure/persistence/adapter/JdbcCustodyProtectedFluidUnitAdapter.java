/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcCustodyProtectedFluidUnitAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter
 *
 * @Description : Provides protected owner evidence for explicitly admitted fluid units.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.telemetry.application.contract.custody.CustodyProtectedFluidUnitContract;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.support.*;

/** Native physical owner evidence; no cached unit eligibility and no numerical conversion. */
@Repository
public class JdbcCustodyProtectedFluidUnitAdapter implements CustodyProtectedFluidUnitContract {
    private static final List<String> RELATIONS=List.of("public.hidra_telemetry_unit","public.hidra_telemetry_fluid_unit_policy");
    private static final String GUARD="""
        SELECT pg_catalog.current_setting('transaction_isolation')='read committed'
          AND pg_catalog.current_setting('transaction_read_only')='off'
          AND pg_catalog.current_setting('session_replication_role')='origin'
          AND NOT pg_catalog.has_schema_privilege(current_user,'public','CREATE')
          AND NOT pg_catalog.has_schema_privilege(current_user,'pg_catalog','CREATE')
          AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_roles r
            WHERE (r.rolname=current_user OR pg_catalog.pg_has_role(current_user,r.oid,'MEMBER')) AND (r.rolsuper OR r.rolbypassrls))
          AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_class c
            WHERE c.oid IN ('public.hidra_telemetry_unit'::regclass,'public.hidra_telemetry_fluid_unit_policy'::regclass)
              AND (c.relkind<>'r' OR c.relpersistence<>'p' OR c.relrowsecurity OR c.relforcerowsecurity
                OR c.relispartition OR pg_catalog.pg_has_role(current_user,c.relowner,'MEMBER')
                OR pg_catalog.has_table_privilege(current_user,c.oid,'TRIGGER')))
          AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_inherits
            WHERE inhrelid IN ('public.hidra_telemetry_unit'::regclass,'public.hidra_telemetry_fluid_unit_policy'::regclass)
               OR inhparent IN ('public.hidra_telemetry_unit'::regclass,'public.hidra_telemetry_fluid_unit_policy'::regclass))
          AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_trigger WHERE NOT tgisinternal AND tgrelid IN
            ('public.hidra_telemetry_unit'::regclass,'public.hidra_telemetry_fluid_unit_policy'::regclass))
          AND (SELECT count(*)=6 AND bool_and(attnotnull) AND
             array_agg(attname::text ORDER BY attnum)=ARRAY['unit_id','usage_role','expected_dimension','active','created_at','updated_at'] AND
             array_agg(atttypid::integer ORDER BY attnum)=ARRAY[1043,1043,1043,16,1184,1184] AND
             array_agg(atttypmod ORDER BY attnum)=ARRAY[84,84,84,-1,-1,-1]
             FROM pg_catalog.pg_attribute WHERE attrelid='public.hidra_telemetry_fluid_unit_policy'::regclass AND attnum>0 AND NOT attisdropped)
          AND (SELECT count(*)=5 AND bool_and(convalidated AND NOT condeferrable)
             AND count(*) FILTER(WHERE contype='p' AND conkey=ARRAY[1,2]::smallint[])=1
             AND count(*) FILTER(WHERE contype='f' AND conkey=ARRAY[1]::smallint[] AND confkey=ARRAY[1]::smallint[]
                AND confrelid='public.hidra_telemetry_unit'::regclass)=1
             AND count(*) FILTER(WHERE contype='c')=3
             FROM pg_catalog.pg_constraint WHERE conrelid='public.hidra_telemetry_fluid_unit_policy'::regclass)
          AND EXISTS(SELECT 1 FROM pg_catalog.pg_constraint k JOIN pg_catalog.pg_index i ON i.indexrelid=k.conindid
             WHERE k.conrelid='public.hidra_telemetry_unit'::regclass AND k.contype='p' AND k.convalidated AND NOT k.condeferrable
               AND k.conkey=ARRAY[1]::smallint[] AND i.indisvalid AND i.indisready AND i.indisunique)
        """;
    private static final String HELD="""
        SELECT count(DISTINCT relation)=2 FROM pg_catalog.pg_locks
        WHERE pid=pg_catalog.pg_backend_pid() AND granted AND locktype='relation' AND mode='ShareRowExclusiveLock'
          AND relation IN ('public.hidra_telemetry_unit'::regclass,'public.hidra_telemetry_fluid_unit_policy'::regclass)
        """;
    private final JdbcTemplate jdbc;private final DataSource source;
    public JdbcCustodyProtectedFluidUnitAdapter(JdbcTemplate jdbc){this.jdbc=Objects.requireNonNull(jdbc);this.source=Objects.requireNonNull(jdbc.getDataSource());}
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(List<Need> needs) {
        try {
            var exact=CustodyProtectedFluidUnitContract.exactNeeds(needs);var h=context();trustedPath();
            for(String relation:RELATIONS)jdbc.execute("LOCK TABLE "+relation+" IN SHARE ROW EXCLUSIVE MODE NOWAIT");
            check(GUARD);check(HELD);
            var t=jdbc.queryForObject("SELECT pg_catalog.pg_backend_pid(),pg_catalog.txid_current(),current_user",
                (row,n)->new Ticket(exact,h,row.getInt(1),row.getLong(2),row.getString(3)));
            if(t==null)throw invalid();TransactionSynchronizationManager.registerSynchronization(t);return t;
        }catch(RuntimeException failure){rollback(null);throw failure;}
    }
    // Explicit validation runs even on an unbound thread, poisoning its original ticket.
    @Override public Contribution read(Admission admission) {
        try {var t=current(admission);if(!t.used.compareAndSet(false,true))throw invalid();
            t.result=new Contribution(snapshot(t),t.pid,t.xid);return t.result;
        }catch(RuntimeException failure){rollback(admission);throw failure;}
    }
    @Override public void requireCurrent(Admission admission) {
        try {var t=current(admission);if(t.result==null || !t.result.units().equals(snapshot(t)))throw invalid();}
        catch(RuntimeException failure){rollback(admission);throw failure;}
    }
    @Override public List<ProtectedRelationFence> finalFences(Admission admission) {
        try {current(admission);return RELATIONS.stream().map(x->new ProtectedRelationFence(x,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE)).toList();}
        catch(RuntimeException failure){rollback(admission);throw failure;}
    }
    private List<Unit> snapshot(Ticket t) {
        trustedPath();check(GUARD);check(HELD);
        if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=? AND current_user=?",Boolean.class,t.pid,t.xid,t.role)))throw invalid();
        var result=new ArrayList<Unit>();
        for(var n:t.needs) {
            var rows=jdbc.query("""
                SELECT u.id,p.unit_id,p.usage_role,p.expected_dimension,u.code,u.symbol,u.dimension,u.updated_at FROM public.hidra_telemetry_unit u
                JOIN public.hidra_telemetry_fluid_unit_policy p ON p.unit_id=u.id
                WHERE u.id=? AND p.usage_role=? AND u.active AND p.active AND u.dimension=p.expected_dimension
                """,(row,i)->{
                    if(!n.unitId().equals(row.getString(1)) || !n.unitId().equals(row.getString(2))
                        || !n.role().name().equals(row.getString(3)) || !Objects.equals(row.getString(4),row.getString(7)))throw invalid();
                    return new Unit(n,row.getString(5),row.getString(6),row.getString(7),row.getTimestamp(8).toInstant());
                },n.unitId(),n.role().name());
            if(rows.size()!=1)throw invalid();result.add(rows.getFirst());
        }
        return List.copyOf(result);
    }
    private Ticket current(Admission admission) {
        if(!(admission instanceof Ticket t) || t.issuer!=this || !t.active || t.rejected || t.thread!=Thread.currentThread())throw invalid();
        var h=context();if(t.holder!=h || t.connection!=h.getConnection())throw invalid();return t;
    }
    private ConnectionHolder context() {
        if(!TransactionSynchronizationManager.isActualTransactionActive() || !TransactionSynchronizationManager.isSynchronizationActive()
            || TransactionSynchronizationManager.isCurrentTransactionReadOnly() || !(TransactionSynchronizationManager.getResource(source) instanceof ConnectionHolder h)
            || h.isRollbackOnly())throw invalid();return h;
    }
    private void trustedPath(){String s=jdbc.queryForObject("SELECT pg_catalog.array_to_json(pg_catalog.current_schemas(true))::pg_catalog.text",String.class);
        if(s==null || !(s.equals("[\"pg_catalog\"]") || s.startsWith("[\"pg_catalog\",")))throw invalid();}
    private void check(String sql){if(!Boolean.TRUE.equals(jdbc.queryForObject(sql,Boolean.class)))throw invalid();}
    private void rollback(Admission a){if(a instanceof Ticket t){t.rejected=true;t.holder.setRollbackOnly();}
        Object r=TransactionSynchronizationManager.getResource(source);if(r instanceof ConnectionHolder h)h.setRollbackOnly();}
    private static IllegalStateException invalid(){return new IllegalStateException("Protected fluid unit evidence unavailable");}
    private final class Ticket implements Admission,TransactionSynchronization {
        private final JdbcCustodyProtectedFluidUnitAdapter issuer=JdbcCustodyProtectedFluidUnitAdapter.this;
        private final List<Need> needs;private final ConnectionHolder holder;private final Connection connection;
        private final Thread thread=Thread.currentThread();private final int pid;private final long xid;private final String role;
        private final AtomicBoolean used=new AtomicBoolean();private volatile boolean active=true,rejected;private Contribution result;
        private Ticket(List<Need> needs,ConnectionHolder h,int pid,long xid,String role){this.needs=needs;holder=h;connection=h.getConnection();this.pid=pid;this.xid=xid;this.role=role;}
        @Override public void suspend(){active=false;rejected=true;}
        @Override public void beforeCommit(boolean readOnly){if(rejected || !active || readOnly || thread!=Thread.currentThread())throw invalid();}
        @Override public void afterCompletion(int status){active=false;}
        @Override public String toString(){return "Admission[opaque fluid units]";}
    }
}
