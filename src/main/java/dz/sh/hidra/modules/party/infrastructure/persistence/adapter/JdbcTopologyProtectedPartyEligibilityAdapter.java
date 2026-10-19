/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcTopologyProtectedPartyEligibilityAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-18
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Provides protected owner evidence in the actual physical transaction.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.contract.topology.TopologyProtectedPartyEligibilityContract;
import dz.sh.hidra.modules.party.domain.value.PartyStatus;
import java.sql.Connection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/** Owner-only, non-waiting relation admission; no independent transaction, clock or permission. */
@Repository
public class JdbcTopologyProtectedPartyEligibilityAdapter implements TopologyProtectedPartyEligibilityContract {
    private static final String GUARD = """
        SELECT pg_catalog.current_setting('transaction_isolation')='read committed'
          AND pg_catalog.current_setting('transaction_read_only')='off'
          AND pg_catalog.current_setting('session_replication_role')='origin'
          AND NOT pg_catalog.has_schema_privilege(current_user,'public','CREATE')
          AND NOT pg_catalog.has_schema_privilege(current_user,'pg_catalog','CREATE')
          AND NOT EXISTS (SELECT 1 FROM pg_catalog.pg_roles r
              WHERE (r.rolname=current_user OR pg_catalog.pg_has_role(current_user,r.oid,'MEMBER'))
                AND (r.rolsuper OR r.rolbypassrls))
          AND NOT EXISTS (SELECT 1 FROM pg_catalog.pg_class c
              WHERE c.oid IN ('public.hidra_party_party'::regclass,'public.hidra_party_eligibility_generation'::regclass)
                AND (c.relkind<>'r' OR c.relpersistence<>'p' OR c.relrowsecurity OR c.relforcerowsecurity
                  OR c.relispartition OR pg_catalog.pg_has_role(current_user,c.relowner,'MEMBER')))
          AND NOT EXISTS (SELECT 1 FROM pg_catalog.pg_inherits
              WHERE inhrelid IN ('public.hidra_party_party'::regclass,'public.hidra_party_eligibility_generation'::regclass)
                 OR inhparent IN ('public.hidra_party_party'::regclass,'public.hidra_party_eligibility_generation'::regclass))
          AND NOT pg_catalog.has_table_privilege(current_user,'public.hidra_party_party','TRIGGER')
          AND NOT pg_catalog.has_table_privilege(current_user,'public.hidra_party_eligibility_generation','INSERT,UPDATE,DELETE,TRUNCATE,REFERENCES,TRIGGER')
          AND NOT pg_catalog.has_any_column_privilege(current_user,'public.hidra_party_eligibility_generation','INSERT,UPDATE,REFERENCES')
          AND (SELECT pg_catalog.count(*)=3 AND pg_catalog.bool_and(attnotnull) AND
              pg_catalog.array_agg(attname::text ORDER BY attnum)=ARRAY['party_id','generation','present'] AND
              pg_catalog.array_agg(atttypid::integer ORDER BY attnum)=ARRAY[1043,20,16] AND
              pg_catalog.array_agg(atttypmod ORDER BY attnum)=ARRAY[84,-1,-1]
              FROM pg_catalog.pg_attribute WHERE attrelid='public.hidra_party_eligibility_generation'::regclass AND attnum>0 AND NOT attisdropped)
          AND (SELECT pg_catalog.count(*)=2 AND pg_catalog.count(*) FILTER (WHERE contype='p' AND convalidated AND NOT condeferrable AND conkey=ARRAY[1]::smallint[])=1
              AND pg_catalog.count(*) FILTER (WHERE contype='c' AND convalidated AND NOT condeferrable
                AND pg_catalog.pg_get_constraintdef(oid)='CHECK ((generation > 0))')=1
              FROM pg_catalog.pg_constraint WHERE conrelid='public.hidra_party_eligibility_generation'::regclass)
          AND (SELECT pg_catalog.count(*)=2 AND pg_catalog.bool_and(prosecdef AND prokind='f' AND pronargs=0
              AND prorettype='trigger'::regtype AND prolang=(SELECT oid FROM pg_catalog.pg_language WHERE lanname='plpgsql')
              AND NOT proleakproof AND NOT proisstrict AND provolatile='v' AND proparallel='u'
              AND proconfig=ARRAY['search_path=pg_catalog, pg_temp']
              AND NOT pg_catalog.pg_has_role(current_user,proowner,'MEMBER') AND NOT pg_catalog.has_function_privilege(current_user,oid,'EXECUTE')
              AND ((oid='public.hidra_p25_party_eligibility_write()'::regprocedure AND pg_catalog.md5(prosrc)='6aa06c78c97082be80c99071b7504f4f')
                OR (oid='public.hidra_p25_party_eligibility_truncate()'::regprocedure AND pg_catalog.md5(prosrc)='c2a48183395f90cd23e2dbe84d424d01')))
              FROM pg_catalog.pg_proc WHERE oid IN ('public.hidra_p25_party_eligibility_write()'::regprocedure,'public.hidra_p25_party_eligibility_truncate()'::regprocedure))
          AND (SELECT pg_catalog.count(*)=3 AND pg_catalog.bool_and(tgenabled='O' AND tgqual IS NULL AND tgnargs=0 AND tgconstraint=0 AND tgparentid=0
              AND ((tgrelid='public.hidra_party_party'::regclass AND tgname='trg_p25_party_eligibility_write' AND tgtype=29
                    AND tgfoid='public.hidra_p25_party_eligibility_write()'::regprocedure
                    AND ARRAY(SELECT a.attname::text FROM pg_catalog.pg_attribute a WHERE a.attrelid=tgrelid
                              AND a.attnum=ANY(tgattr::smallint[]) ORDER BY a.attnum)=ARRAY['id','status'])
                OR (tgrelid='public.hidra_party_party'::regclass AND tgname='trg_p25_party_eligibility_root_truncate' AND tgtype=34
                    AND tgfoid='public.hidra_p25_party_eligibility_truncate()'::regprocedure AND pg_catalog.cardinality(tgattr::smallint[])=0)
                OR (tgrelid='public.hidra_party_eligibility_generation'::regclass AND tgname='trg_p25_party_eligibility_registry_truncate' AND tgtype=34
                    AND tgfoid='public.hidra_p25_party_eligibility_truncate()'::regprocedure AND pg_catalog.cardinality(tgattr::smallint[])=0)))
              FROM pg_catalog.pg_trigger WHERE tgrelid IN ('public.hidra_party_party'::regclass,'public.hidra_party_eligibility_generation'::regclass) AND NOT tgisinternal)
        """;
    private static final String HELD = """
        SELECT EXISTS (SELECT 1 FROM pg_catalog.pg_locks WHERE pid=pg_catalog.pg_backend_pid() AND granted
          AND locktype='relation' AND relation='public.hidra_party_party'::regclass AND mode='ShareRowExclusiveLock')
          AND EXISTS (SELECT 1 FROM pg_catalog.pg_locks WHERE pid=pg_catalog.pg_backend_pid() AND granted
          AND locktype='relation' AND relation='public.hidra_party_eligibility_generation'::regclass AND mode='AccessShareLock')
        """;
    private final JdbcTemplate jdbc;
    private final DataSource source;

    public JdbcTopologyProtectedPartyEligibilityAdapter(JdbcTemplate jdbc) {
        this.jdbc = Objects.requireNonNull(jdbc);
        this.source = Objects.requireNonNull(jdbc.getDataSource());
    }

    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(String exactId) {
        try {
            String id = identity(exactId);
            ConnectionHolder holder = context();
            trustedSearchPath();
            jdbc.execute("LOCK TABLE public.hidra_party_party IN SHARE ROW EXCLUSIVE MODE NOWAIT");
            jdbc.execute("LOCK TABLE public.hidra_party_eligibility_generation IN ACCESS SHARE MODE NOWAIT");
            check(GUARD); check(HELD);
            Ticket ticket = jdbc.queryForObject("SELECT pg_catalog.pg_backend_pid(),pg_catalog.txid_current(),current_user", (row,n) ->
                    new Ticket(id,holder,holder.getConnection(),row.getInt(1),row.getLong(2),row.getString(3)));
            if (ticket == null) throw invalid();
            TransactionSynchronizationManager.registerSynchronization(ticket);
            return ticket;
        } catch (RuntimeException failure) { rollback(); throw failure; }
    }

    @Override
    @Transactional(propagation=Propagation.MANDATORY)
    public Contribution read(Admission admission) {
        try {
            ConnectionHolder holder = context();
            if (!(admission instanceof Ticket ticket) || ticket.issuer != this
                    || !ticket.used.compareAndSet(false,true) || !ticket.active
                    || ticket.thread != Thread.currentThread() || ticket.holder != holder
                    || ticket.connection != holder.getConnection()) throw invalid();
            trustedSearchPath();
            check(GUARD); check(HELD);
            if (!Boolean.TRUE.equals(jdbc.queryForObject(
                    "SELECT pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=? AND current_user=?",
                    Boolean.class,ticket.pid,ticket.xid,ticket.role))) throw invalid();
            var rows = jdbc.query("""
                SELECT m.id,m.status,g.generation,g.present
                FROM public.hidra_party_party m JOIN public.hidra_party_eligibility_generation g ON g.party_id=m.id WHERE m.id=?
                """, (row,n) -> {
                long generation = row.getLong("generation");
                if (!ticket.id.equals(row.getString("id")) || !row.getBoolean("present") || generation<=0) throw invalid();
                if (PartyStatus.valueOf(row.getString("status")) != PartyStatus.ACTIVE) throw invalid();
                return new Contribution(ticket.id, generation, ticket.pid, ticket.xid);
            },ticket.id);
            if (rows.size()!=1) throw invalid();
            return rows.get(0);
        } catch (RuntimeException failure) { rollback(); throw failure instanceof IllegalArgumentException ? invalid() : failure; }
    }

    @Override @Transactional(propagation=Propagation.MANDATORY)
    public java.util.List<dz.sh.hidra.platform.persistence.ProtectedRelationFence> finalFences(Admission admission) {
        try {
            var holder=context();
            if(!(admission instanceof Ticket t) || t.issuer!=this || !t.active || t.thread!=Thread.currentThread()
                || t.holder!=holder || t.connection!=holder.getConnection())throw invalid();
            return java.util.List.of(
                new dz.sh.hidra.platform.persistence.ProtectedRelationFence("public.hidra_party_party",dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
                new dz.sh.hidra.platform.persistence.ProtectedRelationFence("public.hidra_party_eligibility_generation",dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.ACCESS_SHARE));
        } catch(RuntimeException failure){rollback();throw failure;}
    }

    private ConnectionHolder context() {
        if (!TransactionSynchronizationManager.isActualTransactionActive()
                || !TransactionSynchronizationManager.isSynchronizationActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                || !(TransactionSynchronizationManager.getResource(source) instanceof ConnectionHolder holder)
                || holder.isRollbackOnly()) throw invalid();
        return holder;
    }
    private void trustedSearchPath() {
        // Qualified scalar JSON avoids SQL comparison shadowing and raw JDBC array resources.
        String schemas = jdbc.queryForObject("SELECT pg_catalog.array_to_json(pg_catalog.current_schemas(true))::pg_catalog.text", String.class);
        if (schemas == null || !(schemas.equals("[\"pg_catalog\"]") || schemas.startsWith("[\"pg_catalog\",")))
            throw invalid();
    }
    private void check(String sql) {
        if (!Boolean.TRUE.equals(jdbc.queryForObject(sql,Boolean.class))) throw invalid();
    }
    private void rollback() {
        Object resource = TransactionSynchronizationManager.getResource(source);
        if (resource instanceof ConnectionHolder holder) holder.setRollbackOnly();
        try { TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); }
        catch (org.springframework.transaction.NoTransactionException absent) { /* Direct transaction-template caller. */ }
    }
    private static String identity(String id) {
        if (id==null || id.trim().isEmpty() || id.trim().length()>80) throw new IllegalArgumentException("Invalid owner identity");
        return id.trim();
    }
    private static IllegalStateException invalid() { return new IllegalStateException("Protected owner evidence unavailable"); }

    private final class Ticket implements Admission, TransactionSynchronization {
        private final JdbcTopologyProtectedPartyEligibilityAdapter issuer = JdbcTopologyProtectedPartyEligibilityAdapter.this;
        private final String id,role;
        private final ConnectionHolder holder;
        private final Connection connection;
        private final Thread thread = Thread.currentThread();
        private final int pid;
        private final long xid;
        private final AtomicBoolean used = new AtomicBoolean();
        private volatile boolean active = true;
        private Ticket(String id,ConnectionHolder holder,Connection connection,int pid,long xid,String role) {
            this.id=id;this.holder=holder;this.connection=connection;this.pid=pid;this.xid=xid;this.role=role;
        }
        @Override public void suspend() { active=false; }
        @Override public void afterCompletion(int status) { active=false; }
    }
}
