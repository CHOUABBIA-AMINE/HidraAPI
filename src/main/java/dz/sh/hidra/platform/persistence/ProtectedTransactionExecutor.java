/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedTransactionExecutor
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
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
    // HAPI-601: compiled Simulation manufacturer owner, pinned to the two exact immutable insert statements.
    private static final String MANUFACTURER_SERVICE="dz.sh.hidra.modules.simulation.application.service.ManufacturerCharacteristicRevisionApplicationService";
    private static final String MANUFACTURER_COORDINATOR="dz.sh.hidra.modules.simulation.application.service.ProtectedManufacturerCharacteristicAdmissionCoordinator";
    private static final Set<String> MANUFACTURER_DML_HASHES=Set.of(
        "2af4bb321a4c0aa685d79e9703541cba7e4ad1d6500c312beaef72c582a3e7c0",
        "6d7338e5d51dcd102be55156fdd703e99bfa2623325178839a67d475cb42af6a");
    private static final String STATION_CONFIG_SERVICE="dz.sh.hidra.modules.topology.application.service.StationInstalledConfigurationApplicationService";
    private static final String STATION_CONFIG_COORDINATOR="dz.sh.hidra.modules.topology.application.service.ProtectedStationConfigurationAdmissionCoordinator";
    private static final String STATION_CONFIG_REPOSITORY="dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcStationInstalledConfigurationRepositoryAdapter";
    private static final Set<String> STATION_CONFIG_DML_HASHES=Set.of("a526f8833040c3c7b6c4510538438cbeb6d2b0b0a603f647bf30c0a2cca095f9","7ce1197ed30cc3278fc03074d45b0c483472529bfd09b2784db655a0ee924c5b","5591e291eb0dbdef51ad2150363e65506143b4551f5d70ba4f6cfacd776dc01c","ef47e69fb0ce03dd54cf45eb5ab98fb0a1a94705892bec66c517d19a31fe438c","390240eb897a42dd62ed11d94d9682d6cb103224e9ba121845da9b352e6b7f40");
    private static final String INSTALLED_SERVICE="dz.sh.hidra.modules.topology.application.service.InstalledUnitLifecycleApplicationService";
    private static final String INSTALLED_COORDINATOR="dz.sh.hidra.modules.topology.application.service.ProtectedInstalledUnitAdmissionCoordinator";
    private static final Set<String> INSTALLED_DML_HASHES=Set.of("98d9e2d0cd9de0a5c85161d0bb465f4ab310a0ebd6208036eeb26572b3c35f46","13de71cd409afd07b736979dad5ad802324a4a40718dd7f411229492c81cb716","d55fab38a80ea7a81636dceb7d3af50c6a8e1c14410b58e1e8ff25a0557ae278","a10ecf5d21bc1a8efcdf7a7cbbf5e23104309f243b223a93c997a61d880d5bd7","1503d363ee40899cad0081f42e84f728d974409236ebc5fe96e631e571330495");
    private static final String PRODUCT_PUBLICATION_SERVICE="dz.sh.hidra.modules.custody.application.service.ProductRevisionPublicationApplicationService";
    private static final String PRODUCT_PUBLICATION_COORDINATOR="dz.sh.hidra.modules.custody.application.service.ProtectedProductPublicationAdmissionCoordinator";
    private static final Set<String> PRODUCT_PUBLICATION_DML_HASHES=Set.of("3c216e23ede48cc7eaf8a7d0fbd69ebb36ec492becfd258d7f2e9716f2abfc7b","e6366971cd3f33ccea87d34e808f76677fe1cd38326f0a4534777a3760211fb7","ec5361350fb1f8ab6b995e51425028d14e87305cefb469185b81d1a8616f0168","2a323fe9ab26838c24ae1f2bfd5cd2279e8b56da0d25c6e507f0300cb2cb4515","37a62c17c65bf88dca6462ff22c73c9c8c21f15bea4fc83435351d2fb071dd50","2a5cdfa1f5a75b72a4172371dce65a1d52189db7007f651feb3c93796535c68c");
    private static final String FLUID_SERVICE="dz.sh.hidra.modules.custody.application.service.SuppliedFluidPropertyRevisionApplicationService";
    private static final String FLUID_COORDINATOR="dz.sh.hidra.modules.custody.application.service.ProtectedSuppliedFluidPropertyAdmissionCoordinator";
    private static final Set<String> FLUID_PROVISIONAL_HASHES=Set.of("1bb18e11e43f0b770a4661f1ea36117f68ff7c7bfcc6dbb2e37b6a3975644eeb","316a9738f278d5c72e0f457040fe69c68ca96049b2917a57ab24e4e603ad48f2");
    private static final String PRODUCT_SERVICE="dz.sh.hidra.modules.custody.application.service.ProductEngineeringDraftApplicationService";
    private static final String PRODUCT_COORDINATOR="dz.sh.hidra.modules.custody.application.service.ProtectedProductDraftAdmissionCoordinator";
    private static final Set<String> PRODUCT_PROVISIONAL_HASHES=Set.of("17eb3368805ddaedc2d8cfa69fd6e845d9b0d855cea3945bcf69c5c7dee8801f",
        "2b27eab51711aed1b86389fef86cc9ac72c52e45feb4802a5c74da4df7811241");
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
    private final dz.sh.hidra.platform.configuration.ProtectedExecutionBudgetProperties budgets;

    public ProtectedTransactionExecutor(PlatformTransactionManager manager, DataSource datasource) {
        this(manager,datasource,dz.sh.hidra.platform.configuration.ProtectedExecutionBudgetProperties.defaults());
    }

    @org.springframework.beans.factory.annotation.Autowired
    public ProtectedTransactionExecutor(PlatformTransactionManager manager, DataSource datasource,
            dz.sh.hidra.platform.configuration.ProtectedExecutionBudgetProperties budgets) {
        this.manager = Objects.requireNonNull(manager);
        this.datasource = Objects.requireNonNull(datasource);
        this.budgets = Objects.requireNonNull(budgets);
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
        if(!caller.equals(MANUFACTURER_SERVICE) && !caller.equals(STATION_CONFIG_SERVICE) && !caller.equals(INSTALLED_SERVICE) && !caller.equals(PRODUCT_PUBLICATION_SERVICE) && !caller.equals(FLUID_SERVICE) && !caller.equals(PRODUCT_SERVICE) && !caller.equals(DRAFT_SERVICE) && !caller.equals(PUBLICATION_SERVICE) && !caller.equals(APPOINTMENT_SERVICE) && !caller.equals(
            "dz.sh.hidra.modules.topology.application.service.ProtectedStationGraphOperationService"))
            throw new IllegalStateException("Unreviewed credential route caller");
        long budgetNanos=publicationWorkBudgetNanos(caller);
        try(var route=credentialRouter().open()) {return executeWithBudget(work,flush,guard,budgetNanos);}
    }

    // Only executePublication, after its unchanged exact compiled-caller check, selects this policy.
    private long publicationWorkBudgetNanos(String caller) {
        if(caller.equals(APPOINTMENT_SERVICE))return budgets.appointmentWorkBudgetNanos();
        if(caller.equals(INSTALLED_SERVICE) || caller.equals(STATION_CONFIG_SERVICE))return budgets.installedUnitWorkBudgetNanos();
        if(caller.equals(PRODUCT_SERVICE) || caller.equals(FLUID_SERVICE) || caller.equals(PRODUCT_PUBLICATION_SERVICE))
            return budgets.custodyWorkBudgetNanos();
        return dz.sh.hidra.platform.configuration.ProtectedExecutionBudgetProperties.STANDARD_WORK_BUDGET_MILLIS*1_000_000L;
    }

    /** Reject before an ordinary caller can suspend an already protected physical execution. */
    public void requireNoProtectedContext() {
        var current=ProtectedOperationDataSource.CURRENT.get();
        if(current!=null){current.reject("nested protected lookup");throw new ExecutionFailure(Outcome.REJECTED_BEFORE_COMMIT,new IllegalStateException("nested protected lookup"));}
    }

    public ProtectedCredentialRoutingDataSource.Identity publicationDatabaseIdentity() {
        return credentialRouter().identity();
    }

    private ProtectedCredentialRoutingDataSource credentialRouter() {
        if(publicationRouter==null)throw new IllegalStateException("Protected credential router required");
        return publicationRouter;
    }

    public <T> T execute(Supplier<T> work, Runnable flush, Runnable guard) {
        return executeWithBudget(work,flush,guard,
                dz.sh.hidra.platform.configuration.ProtectedExecutionBudgetProperties.STANDARD_WORK_BUDGET_MILLIS*1_000_000L);
    }

    private <T> T executeWithBudget(Supplier<T> work, Runnable flush, Runnable guard,long budgetNanos) {
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
        var s = new ProtectedOperationDataSource.Scope(budgetNanos);
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
        if(!caller.equals(STATION_CONFIG_COORDINATOR) && !caller.equals(INSTALLED_COORDINATOR) && !caller.equals(PRODUCT_PUBLICATION_COORDINATOR) && !caller.equals(FLUID_COORDINATOR) && !caller.equals(PRODUCT_COORDINATOR) && !caller.equals(DRAFT_COORDINATOR) && !caller.equals(PUBLICATION_COORDINATOR) && !caller.equals(
                    "dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator") && !caller.equals(APPOINTMENT_COORDINATOR) && !caller.equals(MANUFACTURER_COORDINATOR))
            return null;
        var result=new ProvisionalPermit(this,s,caller);
        s.provisionalPermit=result;
        return result;
    }
    /** Accept only exact, reviewed DML templates for one use of an issued owner ticket. */
    public void beginProvisional(ProvisionalPermit permit,Set<String> exactStatements) {
        var s=admissionScope();
        String caller=StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getName();
        if((!caller.equals(STATION_CONFIG_COORDINATOR) && !caller.equals(INSTALLED_COORDINATOR) && !caller.equals(PRODUCT_PUBLICATION_COORDINATOR) && !caller.equals(FLUID_COORDINATOR) && !caller.equals(PRODUCT_COORDINATOR) && !caller.equals(DRAFT_COORDINATOR) && !caller.equals(PUBLICATION_COORDINATOR) && !caller.equals(
                    "dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator") && !caller.equals(APPOINTMENT_COORDINATOR) && !caller.equals(MANUFACTURER_COORDINATOR))
          || permit==null || !caller.equals(permit.owner) || permit.issuer!=this || permit.scope!=s
          || s.provisionalPermit!=permit || permit.opened || permit.consumed
          || s.provisionalUsed || s.phase!=ProtectedOperationDataSource.Phase.OBSERVING
          || exactStatements==null || exactStatements.isEmpty()) {
            s.reject("invalid provisional request");s.healthy();
        }
        Set<String> allowed=Set.copyOf(exactStatements);
        for(String sql:allowed) {
            if(sql==null || sql.contains(";") || sql.contains("--") || sql.contains("/*")
               || !(caller.equals(INSTALLED_COORDINATOR)
                    ? INSTALLED_DML_HASHES.contains(statementDigest(sql))
                    : caller.equals(STATION_CONFIG_COORDINATOR)
                    ? STATION_CONFIG_DML_HASHES.contains(statementDigest(sql))
                    : caller.equals(PRODUCT_PUBLICATION_COORDINATOR)
                    ? PRODUCT_PUBLICATION_DML_HASHES.contains(statementDigest(sql))
                    : caller.equals(FLUID_COORDINATOR)
                    ? FLUID_PROVISIONAL_HASHES.contains(statementDigest(sql))
                    : caller.equals(PRODUCT_COORDINATOR)
                    ? PRODUCT_PROVISIONAL_HASHES.contains(statementDigest(sql))
                    : caller.equals(PUBLICATION_COORDINATOR)
                    ? PUBLICATION_DML_HASHES.contains(statementDigest(sql))
                     : caller.equals(APPOINTMENT_COORDINATOR)
                    ? APPOINTMENT_PROVISIONAL_HASHES.contains(statementDigest(sql))
                    : caller.equals(MANUFACTURER_COORDINATOR)
                    ? MANUFACTURER_DML_HASHES.contains(statementDigest(sql))
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
        if((!caller.equals(STATION_CONFIG_COORDINATOR) && !caller.equals(INSTALLED_COORDINATOR) && !caller.equals(PRODUCT_PUBLICATION_COORDINATOR) && !caller.equals(FLUID_COORDINATOR) && !caller.equals(PRODUCT_COORDINATOR) && !caller.equals(DRAFT_COORDINATOR) && !caller.equals(PUBLICATION_COORDINATOR) && !caller.equals(
                    "dz.sh.hidra.modules.topology.application.service.ProtectedStationAdmissionCoordinator") && !caller.equals(APPOINTMENT_COORDINATOR) && !caller.equals(MANUFACTURER_COORDINATOR))
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
    /** Only this reviewed compiled owner may revalidate during managed flush; no new SQL/deadline policy. */
    /** Only the compiled storage adapter may request its fixed security-definer source lock. */
    public void requireAdmissionForSourceLock() {
        var s=admissionScope();
        String caller=StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getName();
        if(!caller.equals(STATION_CONFIG_REPOSITORY) || !s.admission
            || s.phase!=ProtectedOperationDataSource.Phase.ADMITTING) {
            s.reject("unreviewed station configuration lock caller");s.healthy();
        }
    }
    public void requireStationConfigurationFinalPreparation(){
        var s=admissionScope();
        String caller=StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getName();
        if(!caller.equals(STATION_CONFIG_COORDINATOR) || !s.admission
            || s.phase!=ProtectedOperationDataSource.Phase.FLUSHING){
            s.reject("unreviewed station configuration final preparation");s.healthy();
        }
    }
    public void requireInstalledUnitFinalPreparation() {
        var s=admissionScope();
        String caller=StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getName();
        if(!caller.equals(INSTALLED_COORDINATOR) || !s.admission || s.phase!=ProtectedOperationDataSource.Phase.FLUSHING) {
            s.reject("unreviewed final preparation");s.healthy();
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

