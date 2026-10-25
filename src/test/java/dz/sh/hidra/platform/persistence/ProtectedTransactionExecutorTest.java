/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedTransactionExecutorTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.persistence
 *
 * @Description : Verifies protected transaction state, resource boundaries and completion outcomes with simulated JDBC behavior.
 *
 */
package dz.sh.hidra.platform.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.persistence.*;

import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.*;
import org.springframework.orm.jpa.DefaultJpaDialect;
import org.springframework.transaction.*;
import org.springframework.transaction.support.*;

import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import javax.sql.DataSource;

/** Exercises public lifecycle behavior with a simulated JDBC/JPA provider; PostgreSQL proof is separate. */
class ProtectedTransactionExecutorTest {
    Connection raw;
    ProtectedOperationDataSource ds;
    ProtectedJpaTransactionManager manager;
    ProtectedTransactionExecutor executor;
    JdbcTemplate jdbc;
    AtomicBoolean active;
    AtomicInteger commits, rollbacks;
    EntityTransaction providerTransaction;
    AtomicReference<Connection> providerConnection;
    final AtomicLong databaseXid = new AtomicLong(2);
    final AtomicInteger databasePid = new AtomicInteger(1);

    @Test
    void graphSqlProvisionalFamilyDoesNotGrantUnreviewedOwnerExecution() throws Exception {
        String body=java.nio.file.Files.readString(java.nio.file.Path.of(
            "src/main/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutor.java"));
        org.junit.jupiter.api.Assertions.assertTrue(body.contains("INSERT INTO public.hidra_topology_engineering_graph_snapshot "));
        org.junit.jupiter.api.Assertions.assertTrue(body.contains("caller.equals(DRAFT_COORDINATOR)"));
        org.junit.jupiter.api.Assertions.assertTrue(body.contains("caller.equals(permit.owner)"));
    }

    @Test void appointmentProvisionalTemplatesRequireExactHashesAndCompiledCaller() throws Exception {
        var field=ProtectedTransactionExecutor.class.getDeclaredField("APPOINTMENT_PROVISIONAL_HASHES");
        field.setAccessible(true);
        assertThat(field.get(null)).isEqualTo(Set.of(
                "516725eb3d2d0fa98f75e73c62186d730c22b44b15d648ed03b1f4e88cba1af4",
                "0991e6595ce13ba5b556da137e0211bc72ecc982a10a01feac5bcb2f9294c896",
                "9d331551beaa09d534325126e04d71625f123dd606e5ca99f79b5e37edbec4d5",
                "e1e6cbcbc2281f495441c7d63ead7d54dda6babfa4c8b1ad1fadbafb250454ee",
                "5a891a894c55c6ebd50cf78641cf19a3c6ad748b4e8b4365eeab6b446424f424",
                "db6d602e42ead6338d10af03c3086056a4c5d50e4b1b978b355c749d4dc57bda"));
        String body=java.nio.file.Files.readString(java.nio.file.Path.of(
                "src/main/java/dz/sh/hidra/platform/persistence/ProtectedTransactionExecutor.java"));
        assertThat(body).contains("caller.equals(APPOINTMENT_COORDINATOR)",
                "APPOINTMENT_PROVISIONAL_HASHES.contains(statementDigest(sql))");
        assertThat(body).doesNotContain("sql.startsWith(\"INSERT INTO public.hidra_org_",
                "sql.startsWith(\"UPDATE public.hidra_org_");
    }
    @BeforeEach
    void provider() throws Exception {
        raw = mock(Connection.class);
        DataSource target = mock(DataSource.class);
        when(target.getConnection()).thenReturn(raw);
        when(raw.getAutoCommit()).thenReturn(false);
        when(raw.getTransactionIsolation()).thenReturn(Connection.TRANSACTION_READ_COMMITTED);
        when(raw.isReadOnly()).thenReturn(false);
        ds = new ProtectedOperationDataSource(target);
        when(raw.createStatement()).thenAnswer(i -> statement(false, null));
        when(raw.prepareStatement(anyString())).thenAnswer(i -> statement(true, i.getArgument(0)));
        active = new AtomicBoolean();
        commits = new AtomicInteger();
        rollbacks = new AtomicInteger();
        var entity = mock(EntityManager.class);
        var transaction = mock(EntityTransaction.class);
        providerTransaction = transaction;
        var factory = mock(EntityManagerFactory.class);
        when(factory.createEntityManager(anyMap())).thenReturn(entity);
        when(factory.createEntityManager()).thenReturn(entity);
        when(entity.getTransaction()).thenReturn(transaction);
        when(entity.isOpen()).thenReturn(true);
        when(transaction.isActive()).thenAnswer(i -> active.get());
        AtomicReference<Connection> connection = new AtomicReference<>();
        providerConnection = connection;
        doAnswer(
                        i -> {
                            active.set(true);
                            return null;
                        })
                .when(transaction)
                .begin();
        doAnswer(
                        i -> {
                            connection.get().commit();
                            active.set(false);
                            return null;
                        })
                .when(transaction)
                .commit();
        doAnswer(
                        i -> {
                            connection.get().rollback();
                            active.set(false);
                            return null;
                        })
                .when(transaction)
                .rollback();
        doAnswer(
                        i -> {
                            commits.incrementAndGet();
                            return null;
                        })
                .when(raw)
                .commit();
        doAnswer(
                        i -> {
                            rollbacks.incrementAndGet();
                            return null;
                        })
                .when(raw)
                .rollback();
        manager = new ProtectedJpaTransactionManager(factory, ds);
        manager.setJpaDialect(
                new DefaultJpaDialect() {
                    @Override
                    public Object beginTransaction(EntityManager em, TransactionDefinition d) {
                        em.getTransaction().begin();
                        return null;
                    }

                    @Override
                    public ConnectionHandle getJdbcConnection(EntityManager em, boolean ro)
                            throws SQLException {
                        var c = ds.getConnection();
                        connection.set(c);
                        return new SimpleConnectionHandle(c);
                    }
                });
        executor = new ProtectedTransactionExecutor(manager, ds);
        jdbc = new JdbcTemplate(ds);
    }

    Statement statement(boolean prepared, String sql) throws Exception {
        Statement s = prepared ? mock(PreparedStatement.class) : mock(Statement.class);
        when(s.getConnection()).thenReturn(raw);
        if (prepared) when(((PreparedStatement) s).executeQuery()).thenAnswer(i -> rows(sql));
        else when(s.executeQuery(anyString())).thenAnswer(i -> rows(i.getArgument(0)));
        return s;
    }

    ResultSet rows(String sql) throws Exception {
        ResultSet rs = mock(ResultSet.class);
        ResultSetMetaData meta = mock(ResultSetMetaData.class);
        when(rs.getMetaData()).thenReturn(meta);
        when(rs.next()).thenReturn(true, false);
        if (sql.contains("empty")) when(rs.next()).thenReturn(false);
        if (sql.contains("pg_backend_pid")) {
            when(meta.getColumnCount()).thenReturn(4);
            when(rs.getInt(1)).thenAnswer(i -> databasePid.get());
            when(rs.getLong(2)).thenAnswer(i -> databaseXid.get());
            when(rs.getString(3)).thenReturn("read committed");
            when(rs.getString(4)).thenReturn("off");
            String[] names = {"pid", "xid", "isolation", "readonly"};
            Object[] values = {1, 2L, "read committed", "off"};
            for (int i = 0; i < 4; i++) {
                when(meta.getColumnLabel(i + 1)).thenReturn(names[i]);
                when(rs.getObject(i + 1)).thenReturn(values[i]);
            }
        } else {
            when(meta.getColumnCount()).thenReturn(1);
            when(meta.getColumnLabel(1)).thenReturn("value");
            when(rs.getString(1)).thenReturn("ok");
            when(rs.getObject(1)).thenReturn("ok");
        }
        return rs;
    }

    String run(java.util.function.Supplier<String> work, Runnable flush, Runnable guard) {
        return executor.execute(work, flush, guard);
    }

    void guard() {
        jdbc.queryForObject("SELECT 'ok'", String.class);
    }

    void rejected(Runnable work) {
        assertThatThrownBy(work::run)
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class);
        assertThat(commits).hasValue(0);
    }

    @Test
    void successfulScalarExecutionCommitsBeforeReturning() {
        assertThat(run(() -> "result", () -> {}, this::guard)).isEqualTo("result");
        assertThat(commits).hasValue(1);
        assertThat(rollbacks).hasValue(0);
    }

    @Test
    void zeroFinalStatementsRejectAndRollBack() {
        rejected(() -> run(() -> "result", () -> {}, () -> {}));
        assertThat(rollbacks).hasValue(1);
    }

    @Test
    void transactionLocalJitSetupPrecedesWorkAndCannotSilentlyFail() throws Exception {
        var setup = new ArrayList<String>();
        when(raw.createStatement()).thenAnswer(i -> {
            var statement = statement(false, null);
            when(statement.execute(anyString())).thenAnswer(call -> {
                String sql = call.getArgument(0);
                setup.add(sql);
                if (sql.equals("SET LOCAL jit='off'")) throw new SQLException("synthetic setup failure");
                return false;
            });
            return statement;
        });
        var reached = new AtomicBoolean();
        rejected(() -> run(() -> { reached.set(true); return "forbidden"; }, () -> {}, this::guard));
        assertThat(setup).containsExactly("SET LOCAL jit='off'");
        assertThat(reached).isFalse();
        assertThat(rollbacks).hasValue(1);
    }

    @Test
    void secondGuardStatementPoisonsEvenIfCaught() {
        rejected(
                () ->
                        run(
                                () -> "result",
                                () -> {},
                                () -> {
                                    guard();
                                    try {
                                        guard();
                                    } catch (RuntimeException ignored) {
                                    }
                                }));
        assertThat(rollbacks).hasValue(1);
    }

    @Test
    void workSqlErrorPoisonsCaughtContinuation() throws Exception {
        when(raw.createStatement()).thenThrow(new SQLException("injected", "08006"));
        rejected(
                () ->
                        run(
                                () -> {
                                    try {
                                        jdbc.execute("SELECT 1");
                                    } catch (RuntimeException ignored) {
                                    }
                                    return "result";
                                },
                                () -> {},
                                this::guard));
    }

    @Test
    void nestedExecutorPoisonsOuterAttempt() {
        rejected(
                () ->
                        run(
                                () -> {
                                    try {
                                        run(() -> "nested", () -> {}, this::guard);
                                    } catch (RuntimeException ignored) {
                                    }
                                    return "result";
                                },
                                () -> {},
                                this::guard));
    }

    @Test
    void ambientTransactionRejectsWithoutCreatingProtectedWork() {
        var t = new TransactionTemplate(manager);
        t.execute(
                s -> {
                    assertThatThrownBy(() -> run(() -> "bad", () -> {}, this::guard))
                            .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class);
                    return null;
                });
    }

    @Test
    void wrongPhysicalReadOnlyAndIsolationReject() {
        for (boolean ro : List.of(true, false)) {
            try {
                when(raw.isReadOnly()).thenReturn(ro);
                when(raw.getTransactionIsolation())
                        .thenReturn(
                                ro
                                        ? Connection.TRANSACTION_READ_COMMITTED
                                        : Connection.TRANSACTION_SERIALIZABLE);
            } catch (SQLException e) {
                throw new AssertionError(e);
            }
            rejected(
                    () ->
                            run(
                                    () -> {
                                        jdbc.queryForObject("SELECT 'ok'", String.class);
                                        return "bad";
                                    },
                                    () -> {},
                                    this::guard));
        }
    }

    @Test
    void prematureCommitRollbackSavepointAndSettingsPoisonAttempts() {
        for (String action :
                List.of(
                        "commit",
                        "rollback",
                        "savepoint",
                        "autocommit",
                        "isolation",
                        "readonly",
                        "close")) {
            rejected(
                    () ->
                            run(
                                    () -> {
                                        var c = DataSourceUtils.getConnection(ds);
                                        try {
                                            switch (action) {
                                                case "commit" -> c.commit();
                                                case "rollback" -> c.rollback();
                                                case "savepoint" -> c.setSavepoint();
                                                case "autocommit" -> c.setAutoCommit(true);
                                                case "isolation" ->
                                                        c.setTransactionIsolation(
                                                                Connection
                                                                        .TRANSACTION_SERIALIZABLE);
                                                case "readonly" -> c.setReadOnly(true);
                                                default -> c.close();
                                            }
                                        } catch (SQLException ignored) {
                                        }
                                        return "bad";
                                    },
                                    () -> {},
                                    this::guard));
        }
    }

    @Test
    void nativeControlAndMultipleSqlReject() {
        for (String sql :
                List.of(
                        "COMMIT",
                        "/* x */ SET TRANSACTION READ ONLY",
                        "/* outer /* inner */ rest */ COMMIT",
                        "SELECT 1; SELECT 2"))
            rejected(
                    () ->
                            run(
                                    () -> {
                                        jdbc.execute(sql);
                                        return "bad";
                                    },
                                    () -> {},
                                    this::guard));
    }

    @Test
    void rawConnectionAndDatasourceUnwrapReject() {
        for (boolean datasource : List.of(false, true))
            rejected(
                    () ->
                            run(
                                    () -> {
                                        try {
                                            if (datasource)
                                                ds.unwrap(
                                                        org.postgresql.ds.PGSimpleDataSource.class);
                                            else
                                                DataSourceUtils.getConnection(ds)
                                                        .unwrap(org.postgresql.PGConnection.class);
                                        } catch (SQLException ignored) {
                                        }
                                        return "bad";
                                    },
                                    () -> {},
                                    this::guard));
    }

    @Test
    void additionalDatasourceConnectionPoisonsOwner() {
        rejected(
                () ->
                        run(
                                () -> {
                                    try {
                                        ds.getConnection();
                                    } catch (SQLException ignored) {
                                    }
                                    return "bad";
                                },
                                () -> {},
                                this::guard));
    }

    @Test
    void batchStreamingAndStatementUnwrapReject() {
        for (String action : List.of("batch", "stream", "unwrap"))
            rejected(
                    () ->
                            run(
                                    () -> {
                                        try (var s =
                                                DataSourceUtils.getConnection(ds)
                                                        .createStatement()) {
                                            switch (action) {
                                                case "batch" -> s.addBatch("SELECT 1");
                                                case "stream" -> s.setFetchSize(1);
                                                default ->
                                                        s.unwrap(org.postgresql.PGStatement.class);
                                            }
                                        } catch (SQLException ignored) {
                                        }
                                        return "bad";
                                    },
                                    () -> {},
                                    this::guard));
    }

    @Test
    void wrapperBackReferencesStayControlled() {
        run(
                () -> {
                    try {
                        var c = DataSourceUtils.getConnection(ds);
                        try (var s = c.createStatement()) {
                            assertThat(s.getConnection()).isSameAs(c);
                            assertThat(c.unwrap(Connection.class)).isSameAs(c);
                        }
                    } catch (SQLException e) {
                        throw new AssertionError(e);
                    }
                    return "ok";
                },
                () -> {},
                this::guard);
    }

    @Test
    void crossThreadCapturedHandleRejectsAndPoisons() {
        rejected(
                () ->
                        run(
                                () -> {
                                    var c = DataSourceUtils.getConnection(ds);
                                    try (var pool = Executors.newSingleThreadExecutor()) {
                                        assertThatThrownBy(
                                                        () ->
                                                                pool.submit(
                                                                                () ->
                                                                                        c
                                                                                                .createStatement())
                                                                        .get(1, TimeUnit.SECONDS))
                                                .isInstanceOf(ExecutionException.class);
                                    }
                                    return "bad";
                                },
                                () -> {},
                                this::guard));
    }

    @Test
    void finishedHandleCannotBecomeUnscoped() {
        AtomicReference<Connection> saved = new AtomicReference<>();
        run(
                () -> {
                    saved.set(DataSourceUtils.getConnection(ds));
                    return "ok";
                },
                () -> {},
                this::guard);
        assertThatThrownBy(() -> saved.get().createStatement()).isInstanceOf(SQLException.class);
    }

    @Test
    void beforeCommitCallbackWriteRejectsFullAttempt() {
        rejected(
                () ->
                        run(
                                () -> {
                                    TransactionSynchronizationManager.registerSynchronization(
                                            new TransactionSynchronization() {
                                                @Override
                                                public void beforeCommit(boolean ro) {
                                                    jdbc.execute("SELECT 1");
                                                }
                                            });
                                    return "ok";
                                },
                                () -> {},
                                this::guard));
    }

    @Test
    void commitDriverErrorIsIndeterminateWithoutRetry() throws Exception {
        doThrow(new SQLException("lost acknowledgement", "08006")).when(raw).commit();
        assertThatThrownBy(() -> run(() -> "bad", () -> {}, this::guard))
                .isInstanceOfSatisfying(
                        ProtectedTransactionExecutor.ExecutionFailure.class,
                        e ->
                                assertThat(e.outcome())
                                        .isEqualTo(
                                                ProtectedTransactionExecutor.Outcome
                                                        .INDETERMINATE));
        verify(raw, times(1)).commit();
    }

    @Test
    void afterCommitCallbackErrorWithholdsCommittedResult() {
        assertThatThrownBy(
                        () ->
                                run(
                                        () -> {
                                            TransactionSynchronizationManager
                                                    .registerSynchronization(
                                                            new TransactionSynchronization() {
                                                                @Override
                                                                public void afterCommit() {
                                                                    throw new IllegalStateException(
                                                                            "callback");
                                                                }
                                                            });
                                            return "bad";
                                        },
                                        () -> {},
                                        this::guard))
                .isInstanceOfSatisfying(
                        ProtectedTransactionExecutor.ExecutionFailure.class,
                        e ->
                                assertThat(e.outcome())
                                        .isEqualTo(
                                                ProtectedTransactionExecutor.Outcome
                                                        .INDETERMINATE));
        assertThat(commits).hasValue(1);
    }

    @Test
    void failedAttemptCleansContextForNextSuccessfulAttempt() {
        rejected(
                () ->
                        run(
                                () -> {
                                    throw new IllegalArgumentException("bad");
                                },
                                () -> {},
                                this::guard));
        assertThat(run(() -> "ok", () -> {}, this::guard)).isEqualTo("ok");
        assertThat(commits).hasValue(1);
    }

    @Test
    void extendedAppointmentConfigurationCannotWidenOrdinaryExecution() {
        executor=new ProtectedTransactionExecutor(manager,ds,
                new dz.sh.hidra.platform.configuration.ProtectedExecutionBudgetProperties(10000));
        rejected(()->run(()->{
            var scope=ProtectedOperationDataSource.CURRENT.get();
            assertThat(scope.workBudgetNanos).isEqualTo(500_000_000L);
            scope.started=System.nanoTime()-500_000_001L;
            return "withheld";
        },()->{},this::guard));
        assertThat(rollbacks).hasValue(1);
    }

    @Test
    void selectedFiniteScopeBudgetExpiresWithoutAnAuthorizationGrace() {
        var scope=new ProtectedOperationDataSource.Scope(2_000_000_000L);
        scope.started=System.nanoTime()-600_000_000L;
        assertThatCode(scope::deadline).doesNotThrowAnyException();
        scope.started=System.nanoTime()-2_000_000_001L;
        assertThatThrownBy(scope::deadline).isInstanceOf(IllegalStateException.class)
                .hasMessage("Protected transaction poisoned: aggregate 2000ms work budget");
        assertThat(scope.poisoned).isTrue();
    }

    @Test
    void preparedQueryProbesLiveParticipationAtExecutionOnce() {
        assertThat(run(()->{
            long before=mockingDetails(raw).getInvocations().stream()
                    .filter(call->call.getMethod().getName().equals("createStatement")).count();
            try(var statement=DataSourceUtils.getConnection(ds).prepareStatement("SELECT 'ok'")) {
                assertThat(mockingDetails(raw).getInvocations().stream()
                        .filter(call->call.getMethod().getName().equals("createStatement")).count()).isEqualTo(before);
                try(var result=statement.executeQuery()) { assertThat(result.next()).isTrue(); }
                assertThat(mockingDetails(raw).getInvocations().stream()
                        .filter(call->call.getMethod().getName().equals("createStatement")).count()).isEqualTo(before+1);
            }catch(SQLException failure){throw new IllegalStateException(failure);}
            return "ok";
        },()->{},this::guard)).isEqualTo("ok");
        assertThat(commits).hasValue(1);assertThat(rollbacks).hasValue(0);
    }

    @Test
    void preparedQueryRejectsBackendChangeAfterPreparationBeforeUserSql() throws Exception {
        var userStatement=new AtomicReference<PreparedStatement>();
        when(raw.prepareStatement(anyString())).thenAnswer(call->{
            var prepared=(PreparedStatement)statement(true,call.getArgument(0));
            userStatement.set(prepared);return prepared;
        });
        rejected(()->run(()->{
            try(var statement=DataSourceUtils.getConnection(ds).prepareStatement("SELECT 'ok'")) {
                databasePid.set(9);
                assertThatThrownBy(statement::executeQuery)
                        .isInstanceOf(SQLException.class)
                        .hasMessageContaining("backend/xid/server participation changed");
            }catch(SQLException failure){throw new IllegalStateException(failure);}
            return "withheld";
        },()->{},this::guard));
        verify(userStatement.get(),never()).executeQuery();
        assertThat(rollbacks).hasValue(1);
    }

    @Test
    void expiredWorkReportsScalarSqlCountWithoutFlushingCommittingOrLeakingSql() {
        var flushed=new AtomicBoolean();
        var guarded=new AtomicBoolean();
        assertThatThrownBy(()->run(()->{
            jdbc.execute("SELECT 1 /* synthetic private marker */");
            // Deterministic expiry of the real unchanged deadline check; no sleep.
            ProtectedOperationDataSource.CURRENT.get().started=System.nanoTime()-500_000_001L;
            return "withheld";
        },()->flushed.set(true),()->guarded.set(true)))
                .isInstanceOfSatisfying(ProtectedTransactionExecutor.ExecutionFailure.class,failure->{
                    assertThat(failure.outcome()).isEqualTo(ProtectedTransactionExecutor.Outcome.REJECTED_BEFORE_COMMIT);
                    assertThat(failure.getCause()).isInstanceOf(IllegalStateException.class)
                            .hasMessage("Protected transaction poisoned: aggregate 500ms work budget");
                    assertThat(failure.getCause().getSuppressed()).hasSize(1);
                    var diagnostic=failure.getCause().getSuppressed()[0];
                    assertThat(diagnostic).isInstanceOf(IllegalStateException.class).hasNoCause();
                    assertThat(diagnostic.getMessage())
                            .startsWith("Protected transaction work diagnostics: phase=WORKING; userStatements=1; elapsedNs=")
                            .doesNotContain("SELECT","synthetic private marker","withheld");
                    long elapsed=Long.parseLong(diagnostic.getMessage().split("elapsedNs=",2)[1]);
                    assertThat(elapsed).isGreaterThanOrEqualTo(500_000_000L);
                });
        assertThat(flushed).isFalse();assertThat(guarded).isFalse();
        assertThat(commits).hasValue(0);assertThat(rollbacks).hasValue(1);
        assertThat(ProtectedOperationDataSource.CURRENT.get()).isNull();
    }

    @Test
    void mutableOrJdbcResultsCannotBeDelivered() {
        assertThatThrownBy(() -> executor.execute(() -> new ArrayList<>(), () -> {}, this::guard))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class);
        assertThatThrownBy(
                        () ->
                                executor.execute(
                                        () -> DataSourceUtils.getConnection(ds),
                                        () -> {},
                                        this::guard))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class);
        assertThatThrownBy(
                        () -> executor.execute(java.time.Clock::systemUTC, () -> {}, this::guard))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class);
        assertThat(commits).hasValue(0);
    }

    @Test
    void dormantDatasourceKeepsOrdinaryJdbcSemantics() throws Exception {
        try (var c = ds.getConnection()) {
            try (var s = c.createStatement()) {
                s.execute("SET LOCAL statement_timeout='0'");
            }
            c.commit();
        }
        assertThat(commits).hasValue(1);
    }

    @Test
    void unsupportedManagerRejectsBeforeWork() throws Exception {
        var unsupported =
                new ProtectedTransactionExecutor(mock(PlatformTransactionManager.class), ds);
        assertThatThrownBy(() -> unsupported.execute(() -> "bad", () -> {}, this::guard))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class);
        verify(raw, never()).commit();
    }

    @Test
    void changedServerBackendOrTransactionIdentityRejects() {
        rejected(
                () ->
                        run(
                                () -> {
                                    databaseXid.set(3);
                                    jdbc.execute("SELECT 1");
                                    return "bad";
                                },
                                () -> {},
                                this::guard));
        databaseXid.set(2);
        rejected(
                () ->
                        run(
                                () -> {
                                    databasePid.set(2);
                                    jdbc.execute("SELECT 1");
                                    return "bad";
                                },
                                () -> {},
                                this::guard));
    }

    @Test
    void swallowedExpiredHandlePoisonsNewAttempt() {
        AtomicReference<Connection> saved = new AtomicReference<>();
        run(
                () -> {
                    saved.set(DataSourceUtils.getConnection(ds));
                    return "ok";
                },
                () -> {},
                this::guard);
        assertThatThrownBy(
                        () ->
                                run(
                                        () -> {
                                            try {
                                                saved.get().createStatement();
                                            } catch (SQLException ignored) {
                                            }
                                            return "bad";
                                        },
                                        () -> {},
                                        this::guard))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class);
        assertThat(commits).hasValue(1);
    }

    @Test
    void finalGuardMustBeAClosedScalarQuery() {
        rejected(() -> run(() -> "bad", () -> {}, () -> jdbc.execute("SELECT 1")));
        rejected(
                () ->
                        run(
                                () -> "bad",
                                () -> {},
                                () -> {
                                    try {
                                        DataSourceUtils.getConnection(ds)
                                                .createStatement()
                                                .executeQuery("SELECT 1");
                                    } catch (SQLException e) {
                                        throw new IllegalStateException(e);
                                    }
                                }));
    }

    @Test
    void elapsedBudgetRejectsBeforeGuard() {
        rejected(
                () ->
                        run(
                                () -> {
                                    long start = System.nanoTime();
                                    while (System.nanoTime() - start < 510_000_000L)
                                        Thread.onSpinWait();
                                    return "bad";
                                },
                                () -> {},
                                this::guard));
    }

    @Test
    void emptyScalarRowPoisonsCaughtGuardFailure() {
        rejected(
                () ->
                        run(
                                () -> "bad",
                                () -> {},
                                () -> {
                                    try {
                                        jdbc.queryForObject("SELECT empty", String.class);
                                    } catch (RuntimeException ignored) {
                                    }
                                }));
    }

    @Test
    void datasourceCloseDuringWorkPoisonsAttempt() {
        rejected(
                () ->
                        run(
                                () -> {
                                    try {
                                        ds.close();
                                    } catch (Exception ignored) {
                                    }
                                    return "bad";
                                },
                                () -> {},
                                this::guard));
    }

    @Test
    void datasourceDelegateAndBuilderEscapesPoisonCaughtErrors() {
        for (boolean builder : List.of(true, false))
            rejected(
                    () ->
                            run(
                                    () -> {
                                        try {
                                            if (builder) ds.createConnectionBuilder();
                                            else ds.getTargetDataSource();
                                        } catch (Exception ignored) {
                                        }
                                        return "bad";
                                    },
                                    () -> {},
                                    this::guard));
    }

    @Test
    void providerMayResetAutocommitOnlyAfterObservedPhysicalCommit() throws Exception {
        doAnswer(
                        i -> {
                            providerConnection.get().commit();
                            providerConnection.get().setAutoCommit(true);
                            active.set(false);
                            return null;
                        })
                .when(providerTransaction)
                .commit();
        assertThat(run(() -> "ok", () -> {}, this::guard)).isEqualTo("ok");
        assertThat(commits).hasValue(1);
        verify(raw).setAutoCommit(true);
    }

    @Test
    void dormantCallableStatementRetainsJdbcInterfaceAndBackReference() throws Exception {
        when(raw.prepareCall(anyString())).thenReturn(mock(CallableStatement.class));
        try (var c = ds.getConnection();
                var statement = c.prepareCall("{call synthetic()}")) {
            assertThat(statement).isInstanceOf(CallableStatement.class);
            assertThat(statement.getConnection()).isSameAs(c);
            statement.execute();
        }
    }

    @Test void admissionGrammarKeepsQuotedDataButRejectsWritableQueryForms() {
        assertThat(ProtectedOperationDataSource.admissionSql("SELECT 'UPDATE WITH INTO' AS value")).isTrue();
        assertThat(ProtectedOperationDataSource.admissionSql("SELECT 1 INTO new_table")).isFalse();
        assertThat(ProtectedOperationDataSource.admissionSql("WITH x AS (DELETE FROM t RETURNING 1) SELECT 1")).isFalse();
        assertThat(ProtectedOperationDataSource.admissionSql("SELECT set_config('role','owner',true)")).isFalse();
    }

    @Test void publicationCredentialEntryRejectsUnreviewedCallerBeforeWork() {
        var called=new AtomicBoolean();
        assertThatThrownBy(()->executor.executePublication(()->{called.set(true);return "no";},()->{},()->{}))
            .isInstanceOf(IllegalStateException.class);
        assertThat(called).isFalse();assertThat(commits).hasValue(0);
    }

    @Test void diagnosticAdmissionCannotMintDraftMutationPermit(){
        assertThatThrownBy(()->run(()->{executor.beginAdmission();
            var permit=executor.sealAdmission();assertThat(permit).isNull();
            executor.beginProvisional(permit,java.util.Set.of("INSERT INTO public.hidra_topology_engineering_context (id) VALUES (?)"));
            return "forbidden";},()->{},this::guard)).isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class);
        assertThat(commits).hasValue(0);
    }
    @Test void ordinaryExecutorCannotSelectPrivateDraftCredentialByCallback(){
        var reached=new AtomicBoolean();
        assertThatThrownBy(()->executor.executePublication(()->{reached.set(true);return "forbidden";},()->{},this::guard))
            .isInstanceOf(IllegalStateException.class);
        assertThat(reached).isFalse();assertThat(commits).hasValue(0);
    }


    @Test void diagnosticAdmissionCannotMintSurveyMutationPermit(){
        assertThatThrownBy(()->run(()->{executor.beginAdmission();
            var permit=executor.sealAdmission();assertThat(permit).isNull();
            executor.beginProvisional(permit,java.util.Set.of("INSERT INTO public.hidra_topology_engineering_survey_snapshot (draft_id) VALUES (?)"));
            return "forbidden";},()->{},this::guard)).isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class);
        assertThat(commits).hasValue(0);assertThat(rollbacks).hasValue(1);
    }

    @Test void diagnosticAndOldOwnerPathsCannotMintPublicationMutationPermits() {
        assertThatThrownBy(()->run(()->{
            executor.beginAdmission();var permit=executor.sealAdmission();assertThat(permit).isNull();
            executor.beginProvisional(permit,Set.of("INSERT INTO public.hidra_topology_engineering_publication_snapshot (revision_id) VALUES (?)"));
            return "forbidden";
        },()->{},this::guard)).isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class);
        assertThat(commits).hasValue(0);assertThat(rollbacks).hasValue(1);
        var reached=new AtomicBoolean();
        assertThatThrownBy(()->executor.executePublication(()->{reached.set(true);return "forbidden";},()->{},this::guard))
            .isInstanceOf(IllegalStateException.class);
        assertThat(reached).isFalse();
    }

}
