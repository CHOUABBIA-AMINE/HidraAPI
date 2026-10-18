/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcIdentityAuthorityFence
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-15
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Fences every reviewed Identity authority dependency before diagnostic temporal collection.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityTemporalEventCollector.Candidate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import java.util.concurrent.atomic.AtomicBoolean;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;

/** Identity-private diagnostic fencing only; this method establishes no B1 or PERMIT authority. */
@Repository
public class JdbcIdentityAuthorityFence {
    private static final List<String> RELATIONS =
            List.of(
                    "public.hidra_identity_attribute_definition",
                    "public.hidra_identity_authorization_policy",
                    "public.hidra_identity_authorization_policy_rule",
                    "public.hidra_identity_authorization_policy_version",
                    "public.hidra_identity_external_group_mapping",
                    "public.hidra_identity_external_identity",
                    "public.hidra_identity_external_permission_mapping",
                    "public.hidra_identity_external_role_mapping",
                    "public.hidra_identity_group",
                    "public.hidra_identity_group_role_grant",
                    "public.hidra_identity_local_credential",
                    "public.hidra_identity_login_session",
                    "public.hidra_identity_operation_binding",
                    "public.hidra_identity_permission",
                    "public.hidra_identity_provider",
                    "public.hidra_identity_role",
                    "public.hidra_identity_role_permission_grant",
                    "public.hidra_identity_subject_security_attribute",
                    "public.hidra_identity_user",
                    "public.hidra_identity_user_group_membership",
                    "public.hidra_identity_user_permission_grant",
                    "public.hidra_identity_user_role_grant");
    private static final String LOCKS =
            "LOCK TABLE " + String.join(",", RELATIONS) + " IN SHARE ROW EXCLUSIVE MODE";
    private static final String HELD =
            """
            SELECT n.nspname || '.' || c.relname FROM pg_locks l
            JOIN pg_class c ON c.oid=l.relation JOIN pg_namespace n ON n.oid=c.relnamespace
            WHERE l.pid=pg_backend_pid() AND l.locktype='relation' AND l.granted
              AND l.mode='ShareRowExclusiveLock' AND n.nspname='public'
              AND n.nspname || '.' || c.relname IN (
            """
                    + RELATIONS.stream()
                            .map(r -> "'" + r + "'")
                            .collect(java.util.stream.Collectors.joining(","))
                    + ") ORDER BY 1";
    private final JdbcTemplate jdbc;
    private final JdbcIdentityTemporalEventCollector collector;

    public JdbcIdentityAuthorityFence(
            JdbcTemplate jdbc, JdbcIdentityTemporalEventCollector collector) {
        this.jdbc = Objects.requireNonNull(jdbc);
        this.collector = Objects.requireNonNull(collector);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Candidate collect(String bindingId) {
        if (!TransactionSynchronizationManager.isActualTransactionActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly())
            throw invalid("Writable participating transaction required");
        if (bindingId == null
                || !bindingId.matches(
                        "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"))
            throw invalid("Exact binding identity required");
        if (!Boolean.TRUE.equals(
                jdbc.queryForObject(
                        "SELECT current_setting('transaction_isolation')='read committed'"
                                + " AND current_setting('transaction_read_only')='off'",
                        Boolean.class))) throw invalid("Incompatible physical transaction");
        // The complete fixed set precedes every owner read and the delegate's clock sample.
        jdbc.execute(LOCKS);
        if (!RELATIONS.equals(jdbc.queryForList(HELD, String.class).stream().sorted().toList()))
            throw invalid("Complete Identity relation fences required");
        // Prefix first, then the committed epoch and companion row fence, then DB time.
        // AccessShare protects schema epoch while Share conflicts with companion DML.
        jdbc.execute(
                "LOCK TABLE public.hidra_identity_external_mapping_generation"
                        + " IN ACCESS SHARE MODE");
        jdbc.execute(
                "LOCK TABLE public.hidra_identity_bound_source_claim_evidence IN SHARE MODE");
        if (!Boolean.TRUE.equals(
                jdbc.queryForObject(
                        """
                        SELECT EXISTS (
                            SELECT 1 FROM pg_catalog.pg_locks
                            WHERE pid=pg_backend_pid() AND granted AND locktype='relation'
                              AND relation='public.hidra_identity_external_mapping_generation'::regclass
                              AND mode='AccessShareLock'
                        ) AND EXISTS (
                            SELECT 1 FROM pg_catalog.pg_locks
                            WHERE pid=pg_backend_pid() AND granted AND locktype='relation'
                              AND relation='public.hidra_identity_bound_source_claim_evidence'::regclass
                              AND mode='ShareLock'
                        )
                        """,
                        Boolean.class))) throw invalid("Identity claim tail fences unavailable");
        Candidate candidate = collector.collect(bindingId);
        if (candidate == null
                || !Boolean.TRUE.equals(
                        jdbc.queryForObject(
                                "SELECT pg_backend_pid()=? AND txid_current()=?",
                                Boolean.class,
                                candidate.backendPid(),
                                candidate.transactionId())))
            throw invalid("Temporal collector changed physical transaction");
        // Preserve the delegate's exact sample/bound and immutable diagnostic result.
        return candidate;
    }

    /** Integrated admission acquires no owner eligibility sample. */
    @Transactional(propagation = Propagation.MANDATORY)
    public Admission acquireNowait(String bindingId) { return acquire(bindingId, true); }

    @Transactional(propagation = Propagation.MANDATORY)
    public Admission acquire(String bindingId) { return acquire(bindingId, false); }

    private Admission acquire(String bindingId, boolean nowait) {
        try {
            if (bindingId == null || !bindingId.matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"))
                throw invalid("Exact binding identity required");
            var holder = holder();
            String suffix = nowait ? " NOWAIT" : "";
            jdbc.execute(LOCKS + suffix);
            jdbc.execute("LOCK TABLE public.hidra_identity_external_mapping_generation IN ACCESS SHARE MODE" + suffix);
            jdbc.execute("LOCK TABLE public.hidra_identity_bound_source_claim_evidence IN SHARE MODE" + suffix);
            var ticket = jdbc.queryForObject("SELECT pg_catalog.pg_backend_pid(),pg_catalog.txid_current(),current_user",
                (r,n) -> new Admission(this,bindingId,holder,r.getInt(1),r.getLong(2),r.getString(3)));
            if (ticket == null) throw invalid("Missing physical admission");
            TransactionSynchronizationManager.registerSynchronization(ticket);
            requireCurrent(ticket);
            return ticket;
        } catch (RuntimeException failure) { rollback(); throw failure; }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Candidate collect(Admission admission) {
        try {
            requireCurrent(admission);
            if (!admission.used.compareAndSet(false,true)) throw invalid("Admission already collected");
            Candidate result = collector.collectFenced(admission.bindingId);
            requireCurrent(admission);
            if (result == null || result.backendPid()!=admission.pid || result.transactionId()!=admission.xid)
                throw invalid("Changed temporal physical context");
            return result;
        } catch (RuntimeException failure) { rollback(); throw failure; }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void requireCurrent(Admission a) {
        try {
            var holder=holder();
            if (a==null || a.owner!=this || !a.active || a.thread!=Thread.currentThread()
                || a.holder!=holder || a.connection!=holder.getConnection()) throw invalid("Foreign admission");
            if (!Boolean.TRUE.equals(jdbc.queryForObject(
                "SELECT pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=? AND current_user=?",
                Boolean.class,a.pid,a.xid,a.role))) throw invalid("Changed physical admission");
            requireFences(jdbc);
        } catch (RuntimeException failure) { rollback(); throw failure; }
    }

    static void requireFences(JdbcTemplate jdbc) {
        if (!RELATIONS.equals(jdbc.queryForList(HELD,String.class).stream().sorted().toList())
            || !Boolean.TRUE.equals(jdbc.queryForObject("""
                SELECT pg_catalog.current_setting('transaction_isolation')='read committed'
                  AND pg_catalog.current_setting('transaction_read_only')='off'
                  AND EXISTS (SELECT 1 FROM pg_catalog.pg_locks WHERE pid=pg_catalog.pg_backend_pid() AND granted
                    AND locktype='relation' AND relation='public.hidra_identity_external_mapping_generation'::regclass AND mode='AccessShareLock')
                  AND EXISTS (SELECT 1 FROM pg_catalog.pg_locks WHERE pid=pg_catalog.pg_backend_pid() AND granted
                    AND locktype='relation' AND relation='public.hidra_identity_bound_source_claim_evidence'::regclass AND mode='ShareLock')
                """,Boolean.class))) throw invalid("Complete retained Identity fences required");
    }

    private ConnectionHolder holder() {
        if (!TransactionSynchronizationManager.isActualTransactionActive()
            || !TransactionSynchronizationManager.isSynchronizationActive()
            || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
            || jdbc.getDataSource()==null
            || !(TransactionSynchronizationManager.getResource(jdbc.getDataSource()) instanceof ConnectionHolder h)
            || h.isRollbackOnly()) throw invalid("Writable bound resource required");
        return h;
    }

    private void rollback() {
        if (jdbc.getDataSource()!=null && TransactionSynchronizationManager.getResource(jdbc.getDataSource()) instanceof ConnectionHolder h) h.setRollbackOnly();
        try { TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); }
        catch (org.springframework.transaction.NoTransactionException absent) { /* Direct template caller. */ }
    }

    public static final class Admission implements TransactionSynchronization {
        private final JdbcIdentityAuthorityFence owner;
        private final String bindingId,role;
        private final Thread thread=Thread.currentThread();
        private final ConnectionHolder holder;
        private final Connection connection;
        private final int pid;
        private final long xid;
        private final AtomicBoolean used=new AtomicBoolean();
        private boolean active=true;
        private Admission(JdbcIdentityAuthorityFence owner,String bindingId,ConnectionHolder holder,int pid,long xid,String role) {
            this.owner=owner;this.bindingId=bindingId;this.holder=holder;this.connection=holder.getConnection();this.pid=pid;this.xid=xid;this.role=role;
        }
        @Override public void suspend() { active=false; }
        @Override public void afterCompletion(int status) { active=false; }
        @Override public String toString() { return "Admission[opaque Identity fences]"; }
    }

    private static IllegalStateException invalid(String reason) {
        return new IllegalStateException(reason);
    }
}
