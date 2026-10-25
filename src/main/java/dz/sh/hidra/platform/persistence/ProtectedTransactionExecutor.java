/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedTransactionExecutor
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.persistence
 *
 * @Description : Executes one physically committed protected transaction with an enforced final SQL statement.
 *
 */
package dz.sh.hidra.platform.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.*;
import org.springframework.transaction.support.*;

import java.math.*;
import java.util.*;
import java.util.function.Supplier;

import javax.sql.DataSource;

/** Trusted compiled-owner callbacks only; no permission, protected API or business fence. */
@Component
public final class ProtectedTransactionExecutor {
    private static final String DRAFT_SERVICE="dz.sh.hidra.modules.topology.application.service.EngineeringNetworkDraftApplicationService";
    private static final String DRAFT_COORDINATOR="dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringDraftAdmissionCoordinator";
    private static final String PUBLICATION_SERVICE="dz.sh.hidra.modules.topology.application.service.EngineeringRevisionPublicationApplicationService";
    private static final String PUBLICATION_COORDINATOR="dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringPublicationAdmissionCoordinator";
    // HAPI-778: only the compiled Organization coordinator may request these six frozen SQL texts.
    // A hash is statement shape, NEVER Identity admission or a business permission.
    private static final String APPOINTMENT_COORDINATOR="dz.sh.hidra.modules.organization.application.service.ProtectedEmployeeAppointmentAdmissionCoordinator";
    private static final String APPOINTMENT_SERVICE="dz.sh.hidra.modules.organization.application.service.EmployeeAppointmentLifecycleApplicationService";
    private static final Set<String> APPOINTMENT_PROVISIONAL_HASHES=Set.of(
        "516725eb3d2d0fa98f75e73c62186d730c22b44b15d648ed03b1f4e88cba1af4",
        "0991e6595ce13ba5b556da137e0211bc72ecc982a10a01feac5bcb2f9294c896",
        "9d331551beaa09d534325126e04d71625f123dd606e5ca99f79b5e37edbec4d5",
        "e1e6cbcbc2281f495441c7d63ead7d54dda6babfa4c8b1ad1fadbafb250454ee",
        "5a891a894c55c6ebd50cf78641cf19a3c6ad748b4e8b4365eeab6b446424f424",
        "db6d602e42ead6338d10af03c3086056a4c5d50e4b1b978b355c749d4dc57bda");
    private static final Set<String> PUBLICATION_DML_HASHES=Set.of(
        "321c4652f15f2da9f76f5ca12395cd899ac3d55c49e8039bdd46b96a1d8fb155",
        "28c7c4023a6d0a597703912a57e0813c04b18fb6caf4d11a4cc91c50be5385b1",
        "76b726f5c9ead80cfebb8cf1b9f109f7f36a5438465f760687f9c8225b62467a",
        "845f50127991e910b122499565576ea758963779dfbcda3df382a92e38dc3ca1");
    private static String statementDigest(String sql) {
        try { return HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256")
            .digest(sql.getBytes(java.nio.charset.StandardCharsets.UTF_8))); }
        catch(java.security.NoSuchAlgorithmException impossible) { throw new IllegalStateException(impossible); }
    }
    public enum Outcome {
        REJECTED_BEFORE_COMMIT,
        INDETERMINATE
    }

    public static final class ExecutionFailure extends RuntimeException {
        private final Outcome outcome;

        ExecutionFailure(Outcome outcome, Throwable cause) {
            super("Protected execution " + outcome, cause);
            this.outcome = outcome;
        }

        public Outcome outcome() {
            return outcome;
        }
    }

    private final PlatformTransactionManager manager;
    private final DataSource datasource;
    private final ProtectedCredentialRoutingDataSource publicationRouter;

    public ProtectedTransactionExecutor(PlatformTransactionManager manager, DataSource datasource) {
        this.manager = Objects.requireNonNull(manager);
        this.datasource = Objects.requireNonNull(datasource);
        ProtectedCredentialRoutingDataSource router=null;
        try {
            if(datasource.isWrapperFor(ProtectedCredentialRoutingDataSource.class))
                router=datasource.unwrap(ProtectedCredentialRoutingDataSource.class);
        } catch(java.sql.SQLException unavailable) { /* Ordinary executor remains usable. */ }
        publicationRouter=router;
    }

    /** Select the private credential before any physical transaction; no business permission implied. */
    public <T> T executePublication(Supplier<T> work,Runnable flush,Runnable guard) {
        String caller=StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getName();
        if(!caller.equals(DRAFT_SERVICE) && !caller.equals(PUBLICATION_SERVICE) && !caller.equals(APPOINTMENT_SERVICE) && !caller.equals(
            "dz.sh.hidra.modules.topology.application.service.ProtectedStationGraphOperationService"))
            throw new IllegalStateException("Unreviewed credential route caller");
        try(var route=credentialRouter().open()) {return execute(work,flush,guard);}
    }

    public ProtectedCredentialRoutingDataSource.Identity publicationDatabaseIdentity() {
        return credentialRouter().identity();
    }

    private ProtectedCredentialRoutingDataSource credentialRouter() {
        if(publicationRouter==null)throw new IllegalStateException("Protected credential router required");
        return publicationRouter;
    }

    public <T> T execute(Supplier<T> work, Runnable flush, Runnable guard) {
        var previous = ProtectedOperationDataSource.CURRENT.get();
        if (previous != null) {
            previous.reject("nested protected execution");
            throw new ExecutionFailure(
                    Outcome.REJECTED_BEFORE_COMMIT,
                    new IllegalStateException("nested protected execution"));
        }
        Objects.requireNonNull(work);
        Objects.requireNonNull(flush);
        Objects.requireNonNull(guard);
        if (TransactionSynchronizationManager.isActualTransactionActive())
            throw new ExecutionFailure(
                    Outcome.REJECTED_BEFORE_COMMIT,
                    new IllegalStateException("ambient transaction"));
        if (!(manager instanceof ProtectedJpaTransactionManager))
            throw new ExecutionFailure(
                    Outcome.REJECTED_BEFORE_COMMIT,
                    new IllegalStateException("unsupported manager"));
        var s = new ProtectedOperationDataSource.Scope();
        s.executor=this;
        TransactionStatus status = null;
        ProtectedOperationDataSource.CURRENT.set(s);
        try {
            if (!datasource.isWrapperFor(ProtectedOperationDataSource.class))
                throw new IllegalStateException("unsupported datasource");
            var definition = new DefaultTransactionDefinition();
            definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
            definition.setReadOnly(false);
            status = manager.getTransaction(definition);
            if (!status.isNewTransaction()
                    || !TransactionSynchronizationManager.isActualTransactionActive())
                throw new IllegalStateException("not new physical transaction");
            JdbcTemplate jdbc = new JdbcTemplate(datasource);
            // Short, repeatedly revalidated owner/catalog reads must not pay LLVM
            // compilation costs. LOCAL restores the connection's setting at completion.
            jdbc.execute("SET LOCAL jit='off'");
            jdbc.execute("SET LOCAL lock_timeout='250ms'");
            jdbc.execute("SET LOCAL statement_timeout='750ms'");
            jdbc.execute("SET LOCAL idle_in_transaction_session_timeout='1000ms'");
            var image =
                    jdbc.queryForMap(
                            "SELECT pg_backend_pid() AS pid, txid_current() AS xid,"
                                    + " current_setting('transaction_isolation') AS isolation,"
                                    + " current_setting('transaction_read_only') AS readonly");
            if (!"read committed".equals(image.get("isolation"))
                    || !"off".equals(image.get("readonly"))
                    || s.physical == null)
                throw new IllegalStateException("invalid actual participation");
            s.pid = ((Number) image.get("pid")).intValue();
            s.xid = ((Number) image.get("xid")).longValue();
            s.started = System.nanoTime();
            s.phase = ProtectedOperationDataSource.Phase.WORKING;
            T result = work.get();
            requireDeadline(s);
            if(s.admission && s.phase!=ProtectedOperationDataSource.Phase.OBSERVING) {
                s.reject("incomplete owner admission");s.healthy();
            }
            materialized(result, 0);
            s.phase = ProtectedOperationDataSource.Phase.FLUSHING;
            flush.run();
            requireDeadline(s);
            s.phase = ProtectedOperationDataSource.Phase.GUARD;
            guard.run();
            requireDeadline(s);
            if (s.phase != ProtectedOperationDataSource.Phase.SEALED
                    || s.guardExecutions != 1
                    || s.openGuardResults != 0)
                throw new IllegalStateException("exactly one closed scalar final guard required");
            manager.commit(status);
            s.healthy();
            if (!s.committed) throw new IllegalStateException("physical commit not observed");
            return result;
        } catch (Throwable error) {
            if (status != null && !status.isCompleted())
                try {
                    manager.rollback(status);
                } catch (Throwable rollback) {
                    error.addSuppressed(rollback);
                }
            Outcome outcome =
                    s.commitAttempted || s.committed || (status != null && !s.rolledBack)
                            ? Outcome.INDETERMINATE
                            : Outcome.REJECTED_BEFORE_COMMIT;
            if (error instanceof Error fatal) throw fatal;
            throw new ExecutionFailure(outcome, error);
        } finally {
            s.phase = ProtectedOperationDataSource.Phase.FINISHED;
            ProtectedOperationDataSource.CURRENT.remove();
        }
    }

    /** Technical state only; compiled owner coordinator supplies every business invariant. */
    public void beginAdmission() {
        var s=admissionScope();
        if(s.phase!=ProtectedOperationDataSource.Phase.WORKING || s.admission || s.userStatements!=0) {
            s.reject("admission must precede all user SQL");s.healthy();
        }
        s.admission=true;s.phase=ProtectedOperationDataSource.Phase.ADMITTING;
    }
    /** Opaque one-use technical permit; no business authority is inferred from this handle. */
    public static final class ProvisionalPermit {
        private final ProtectedTransactionExecutor issuer;
        private final ProtectedOperationDataSource.Scope scope;
        private boolean opened,consumed;
        private final String owner;
        private ProvisionalPermit(ProtectedTransactionExecutor issuer,ProtectedOperationDataSource.Scope scope,String owner){
            this.issuer=issuer;this.scope=scope;this.owner=owner;
        }
        @Override public String toString(){return "ProvisionalPermit[opaque]";}
    }
    public ProvisionalPermit sealAdmission() {
        var s=admissionScope();
        if(!s.admission || s.phase!=ProtectedOperationDataSource.Phase.ADMITTING) {
            s.reject("admission cannot be sealed here");s.healthy();
        }
        s.phase=ProtectedOperationDataSource.Phase.OBSERVING;
        // Only the genuine compiled owner K coordinator can receive an opaque mutation permit.
        // Other callers can still seal read-only diagnostic admission (legacy contract).
        String caller=StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getName();
        if(!caller.equals(DRAFT_COORDINATOR) && !caller.equals(PUBLICATION_COORDINATOR) && !caller.equals(
                    "dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator") && !caller.equals(APPOINTMENT_COORDINATOR))
            return null;
        var result=new ProvisionalPermit(this,s,caller);
        s.provisionalPermit=result;
        return result;
    }
    /** Accept only exact, reviewed DML templates for one use of an issued owner ticket. */
    public void beginProvisional(ProvisionalPermit permit,Set<String> exactStatements) {
        var s=admissionScope();
        String caller=StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getName();
        if((!caller.equals(DRAFT_COORDINATOR) && !caller.equals(PUBLICATION_COORDINATOR) && !caller.equals(
                    "dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator") && !caller.equals(APPOINTMENT_COORDINATOR))
          || permit==null || !caller.equals(permit.owner) || permit.issuer!=this || permit.scope!=s
          || s.provisionalPermit!=permit || permit.opened || permit.consumed
          || s.provisionalUsed || s.phase!=ProtectedOperationDataSource.Phase.OBSERVING
          || exactStatements==null || exactStatements.isEmpty()) {
            s.reject("invalid provisional request");s.healthy();
        }
        Set<String> allowed=Set.copyOf(exactStatements);
        for(String sql:allowed) {
            if(sql==null || sql.contains(";") || sql.contains("--") || sql.contains("/*")
               || !(caller.equals(PUBLICATION_COORDINATOR)
                    ? PUBLICATION_DML_HASHES.contains(statementDigest(sql))
                    : caller.equals(APPOINTMENT_COORDINATOR)
                    ? APPOINTMENT_PROVISIONAL_HASHES.contains(statementDigest(sql))
                    : caller.equals(DRAFT_COORDINATOR)
                    ? (sql.startsWith("INSERT INTO public.hidra_topology_engineering_context ")
                        || sql.startsWith("INSERT INTO public.hidra_topology_engineering_draft ")
                        || sql.startsWith("INSERT INTO public.hidra_topology_engineering_draft_version ")
                        || sql.startsWith("INSERT INTO public.hidra_topology_engineering_graph_snapshot ")
                        || sql.startsWith("INSERT INTO public.hidra_topology_engineering_survey_snapshot "))
                    : (sql.startsWith("INSERT INTO public.hidra_topology_station_graph_")
                        || sql.startsWith("UPDATE public.hidra_topology_station_graph_head ")))) {
                s.reject("provisional statement not reviewed");s.healthy();
            }
        }
        s.provisionalStatements=allowed;s.phase=ProtectedOperationDataSource.Phase.PROVISIONAL;
        permit.opened=true;
    }
    public void endProvisional(ProvisionalPermit permit) {
        var s=admissionScope();
        String caller=StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getName();
        if((!caller.equals(DRAFT_COORDINATOR) && !caller.equals(PUBLICATION_COORDINATOR) && !caller.equals(
                    "dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator") && !caller.equals(APPOINTMENT_COORDINATOR))
          || permit==null || !caller.equals(permit.owner) || permit.issuer!=this || permit.scope!=s || s.provisionalPermit!=permit
          || !permit.opened || permit.consumed || s.phase!=ProtectedOperationDataSource.Phase.PROVISIONAL) {
            s.reject("invalid provisional completion");s.healthy();
        }
        permit.consumed=true;s.provisionalUsed=true;s.provisionalStatements=Set.of();
        s.phase=ProtectedOperationDataSource.Phase.OBSERVING;
    }
    public void requireAdmission() {
        var s=admissionScope();
        if(!s.admission || s.phase!=ProtectedOperationDataSource.Phase.OBSERVING) {
            s.reject("qualified admission required");s.healthy();
        }
    }
    public void rejectAdmission() {
        var s=ProtectedOperationDataSource.CURRENT.get();
        if(s!=null) s.reject("owner admission failed");
        for(Object resource:TransactionSynchronizationManager.getResourceMap().values())
            if(resource instanceof org.springframework.jdbc.datasource.ConnectionHolder h) h.setRollbackOnly();
    }
    private ProtectedOperationDataSource.Scope admissionScope() {
        var s=ProtectedOperationDataSource.CURRENT.get();
        if(s==null) throw new IllegalStateException("Protected physical scope required");
        if(s.executor!=this || s.owner!=Thread.currentThread() || s.physical==null
            || !TransactionSynchronizationManager.isActualTransactionActive()
            || TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            s.reject("foreign admission scope");s.healthy();
        }
        requireDeadline(s);return s;
    }

    /** Internal scalar diagnostics only; never reset the deadline or perform SQL after rejection. */
    private static void requireDeadline(ProtectedOperationDataSource.Scope scope) {
        try {
            scope.deadline();
        } catch (RuntimeException failure) {
            failure.addSuppressed(new IllegalStateException(
                    "Protected transaction work diagnostics: phase=" + scope.phase
                    + "; userStatements=" + scope.userStatements
                    + "; elapsedNs=" + (System.nanoTime() - scope.started)));
            throw failure;
        }
    }

    private static void materialized(Object value, int depth) throws ReflectiveOperationException {
        if (value == null) return;
        if (depth > 32) throw new IllegalArgumentException("result nesting unsupported");
        Class<?> type = value.getClass();
        if (value instanceof String
                || value instanceof Boolean
                || value instanceof Character
                || value instanceof UUID
                || value instanceof Enum<?>
                || type == Byte.class
                || type == Short.class
                || type == Integer.class
                || type == Long.class
                || type == Float.class
                || type == Double.class
                || type == BigInteger.class
                || type == BigDecimal.class
                || (type.getPackageName().equals("java.time")
                        && !(value instanceof java.time.Clock))) return;
        if (type.isRecord()) {
            for (var component : type.getRecordComponents())
                materialized(component.getAccessor().invoke(value), depth + 1);
            return;
        }
        if (type.getName().startsWith("java.util.ImmutableCollections$")) {
            if (value instanceof Map<?, ?> map) {
                for (var entry : map.entrySet()) {
                    materialized(entry.getKey(), depth + 1);
                    materialized(entry.getValue(), depth + 1);
                }
                return;
            }
            if (value instanceof Collection<?> list) {
                for (var item : list) materialized(item, depth + 1);
                return;
            }
        }
        throw new IllegalArgumentException("result must be immutable materialized data");
    }
}
