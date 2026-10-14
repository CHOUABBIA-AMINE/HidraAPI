/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedTransactionLifecyclePostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Proves production protected transaction beans through fresh PostgreSQL Flyway and actual Identity JPA JDBC lifecycle.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

import static org.assertj.core.api.Assertions.*;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserJpaEntity;
import dz.sh.hidra.platform.persistence.*;

import jakarta.persistence.*;
import jakarta.transaction.Synchronization;

import org.hibernate.Session;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.*;
import org.springframework.test.context.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.*;
import org.springframework.transaction.support.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;

import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import javax.sql.DataSource;

/** Production lifecycle beans; fixtures supply no real permission/owner/head authority. */
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(
        classes = HidraApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@Import(ProtectedTransactionLifecyclePostgresIntegrationTest.TransportFault.class)
class ProtectedTransactionLifecyclePostgresIntegrationTest {
    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16");

    @DynamicPropertySource
    static void database(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        r.add("spring.datasource.username", POSTGRES::getUsername);
        r.add("spring.datasource.password", POSTGRES::getPassword);
        r.add("spring.flyway.enabled", () -> "true");
        r.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    }

    @Autowired ProtectedTransactionExecutor executor;
    @Autowired PlatformTransactionManager manager;
    @Autowired JdbcTemplate jdbc;
    @Autowired DataSource datasource;
    @PersistenceContext EntityManager em;
    String schema, user;
    static final ThreadLocal<Boolean> LOSE_ACK = new ThreadLocal<>();

    @TestConfiguration(proxyBeanMethods = false)
    static class TransportFault {
        @Bean
        static CommitFaultPostProcessor commitAcknowledgementFault() {
            return new CommitFaultPostProcessor();
        }
    }

    static class CommitFaultPostProcessor
            implements BeanPostProcessor, org.springframework.core.PriorityOrdered {
        @Override
        public int getOrder() {
            return org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String name) {
            if (!(bean instanceof DataSource ds)) return bean;
            return new DelegatingDataSource(ds) {
                @Override
                public Connection getConnection() throws SQLException {
                    Connection c = super.getConnection();
                    return (Connection)
                            Proxy.newProxyInstance(
                                    Connection.class.getClassLoader(),
                                    new Class<?>[] {Connection.class},
                                    (p, m, a) -> {
                                        if (m.getDeclaringClass() == Object.class)
                                            return switch (m.getName()) {
                                                case "equals" -> p == a[0];
                                                case "hashCode" -> System.identityHashCode(p);
                                                default -> "Injected transport observation";
                                            };
                                        Object v;
                                        try {
                                            v = m.invoke(c, a);
                                        } catch (InvocationTargetException e) {
                                            throw e.getCause();
                                        }
                                        if (m.getName().equals("commit")
                                                && Boolean.TRUE.equals(LOSE_ACK.get()))
                                            throw new SQLException(
                                                    "Injected lost acknowledgement after actual"
                                                        + " commit",
                                                    "08006");
                                        return v;
                                    });
                }
            };
        }
    }

    @BeforeEach
    void fixture() {
        LOSE_ACK.remove();
        schema = "hpr051a_" + UUID.randomUUID().toString().replace("-", "");
        user = "hpr051a-" + UUID.randomUUID();
        jdbc.execute("CREATE SCHEMA " + schema);
        jdbc.execute(
                "CREATE TABLE " + head() + " (id integer PRIMARY KEY, value integer NOT NULL)");
        jdbc.update("INSERT INTO " + head() + " VALUES(1,0)");
        jdbc.execute(
                "CREATE TABLE "
                        + receipt()
                        + " (id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY, pid integer NOT"
                        + " NULL, xid bigint NOT NULL, t timestamptz NOT NULL)");
        jdbc.update(
                "INSERT INTO"
                    + " public.hidra_identity_user(id,username,user_type,status,failed_login_count,created_at,updated_at)"
                    + " VALUES(?,?,'HUMAN','ACTIVE',0,clock_timestamp(),clock_timestamp())",
                user,
                user);
        assertThat(manager).isInstanceOf(ProtectedJpaTransactionManager.class);
    }

    @AfterEach
    void cleanup() {
        LOSE_ACK.remove();
        jdbc.execute("DROP SCHEMA " + schema + " CASCADE");
        jdbc.update("DELETE FROM public.hidra_identity_user WHERE id=?", user);
    }

    String head() {
        return schema + ".head";
    }

    String receipt() {
        return schema + ".receipt";
    }

    void fence() {
        jdbc.execute(
                "LOCK TABLE public.hidra_identity_user,"
                        + head()
                        + ","
                        + receipt()
                        + " IN SHARE ROW EXCLUSIVE MODE");
    }

    void work() {
        fence();
        ReflectionTestUtils.setField(
                em.find(UserJpaEntity.class, user), "displayName", "provisional");
        jdbc.update("UPDATE " + head() + " SET value=value+1");
    }

    void guard() {
        jdbc.queryForObject(
                "WITH sample AS MATERIALIZED (SELECT clock_timestamp() t,pg_backend_pid()"
                        + " pid,txid_current() xid),made AS (INSERT INTO "
                        + receipt()
                        + "(pid,xid,t) SELECT pid,xid,t FROM sample RETURNING t) SELECT t FROM"
                        + " made",
                Timestamp.class);
    }

    String run(Runnable action, Runnable guard) {
        return executor.execute(
                () -> {
                    work();
                    action.run();
                    return "materialized";
                },
                em::flush,
                guard);
    }

    void reject(Runnable attempt) {
        assertThatThrownBy(attempt::run)
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class);
        rolledBack();
    }

    void rolledBack() {
        assertThat(jdbc.queryForObject("SELECT value FROM " + head(), Integer.class)).isZero();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM " + receipt(), Integer.class))
                .isZero();
        assertThat(
                        jdbc.queryForObject(
                                "SELECT display_name FROM public.hidra_identity_user WHERE id=?",
                                String.class,
                                user))
                .isNull();
    }

    Connection independent() throws SQLException {
        return DriverManager.getConnection(
                POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
    }

    static void sql(Connection c, String query) throws SQLException {
        try (var s = c.createStatement()) {
            s.execute(query);
        }
    }

    static long scalar(Connection c, String query) throws SQLException {
        try (var s = c.createStatement();
                var r = s.executeQuery(query)) {
            r.next();
            return r.getLong(1);
        }
    }

    @Test
    void productionJpaJdbcUseOneBackendAndPhysicalCommitPrecedesDelivery() {
        assertThat(
                        jdbc.queryForObject(
                                "SELECT count(*) FROM flyway_schema_history WHERE success",
                                Integer.class))
                .isPositive();
        AtomicInteger pid = new AtomicInteger();
        AtomicLong xid = new AtomicLong();
        AtomicBoolean delivered = new AtomicBoolean();
        assertThat(
                        run(
                                () -> {
                                    pid.set(
                                            jdbc.queryForObject(
                                                    "SELECT pg_backend_pid()", Integer.class));
                                    xid.set(
                                            jdbc.queryForObject(
                                                    "SELECT txid_current()", Long.class));
                                    em.unwrap(Session.class)
                                            .doWork(
                                                    c -> {
                                                        assertThat(
                                                                        scalar(
                                                                                c,
                                                                                "SELECT"
                                                                                    + " pg_backend_pid()"))
                                                                .isEqualTo(pid.get());
                                                        assertThat(
                                                                        scalar(
                                                                                c,
                                                                                "SELECT"
                                                                                    + " txid_current()"))
                                                                .isEqualTo(xid.get());
                                                    });
                                    TransactionSynchronizationManager.registerSynchronization(
                                            new TransactionSynchronization() {
                                                @Override
                                                public void afterCommit() {
                                                    assertThat(delivered).isFalse();
                                                }
                                            });
                                },
                                this::guard))
                .isEqualTo("materialized");
        delivered.set(true);
        assertThat(jdbc.queryForObject("SELECT pid FROM " + receipt(), Integer.class))
                .isEqualTo(pid.get());
        assertThat(jdbc.queryForObject("SELECT xid FROM " + receipt(), Long.class))
                .isEqualTo(xid.get());
        assertThat(jdbc.queryForObject("SELECT value FROM " + head(), Integer.class)).isEqualTo(1);
    }

    @Test
    void independentNativeWriterCannotPassFenceButCompletesAfterCommit() throws Exception {
        try (Connection c = independent()) {
            c.setAutoCommit(false);
            sql(c, "SET LOCAL lock_timeout='100ms'");
            run(
                    () -> {
                        assertThatThrownBy(() -> sql(c, "UPDATE " + head() + " SET value=99"))
                                .isInstanceOf(SQLException.class)
                                .extracting(e -> ((SQLException) e).getSQLState())
                                .isEqualTo("55P03");
                        try {
                            c.rollback();
                        } catch (SQLException e) {
                            throw new IllegalStateException(e);
                        }
                    },
                    this::guard);
            sql(c, "UPDATE " + head() + " SET value=99");
            c.commit();
        }
        assertThat(jdbc.queryForObject("SELECT value FROM " + head(), Integer.class)).isEqualTo(99);
    }

    @Test
    void dirtyJpaCommitFlushAfterGuardAbortsAllEffects() {
        reject(
                () ->
                        run(
                                () -> {},
                                () -> {
                                    guard();
                                    ReflectionTestUtils.setField(
                                            em.find(UserJpaEntity.class, user),
                                            "displayName",
                                            "late");
                                }));
    }

    @Test
    void springBeforeCommitWriteAndReadAdversariesAbort() {
        for (boolean read : List.of(true, false))
            reject(
                    () ->
                            run(
                                    () ->
                                            TransactionSynchronizationManager
                                                    .registerSynchronization(
                                                            new TransactionSynchronization() {
                                                                @Override
                                                                public void beforeCommit(
                                                                        boolean ro) {
                                                                    if (read)
                                                                        jdbc.queryForObject(
                                                                                "SELECT value FROM "
                                                                                        + head(),
                                                                                Integer.class);
                                                                    else
                                                                        jdbc.update(
                                                                                "UPDATE "
                                                                                        + head()
                                                                                        + " SET value=99");
                                                                }
                                                            }),
                                    this::guard));
    }

    @Test
    void providerBeforeCompletionSqlCannotBypassTheSealedPhase() {
        reject(
                () ->
                        run(
                                () ->
                                        em.unwrap(Session.class)
                                                .getTransaction()
                                                .registerSynchronization(
                                                        new Synchronization() {
                                                            @Override
                                                            public void beforeCompletion() {
                                                                try {
                                                                    jdbc.queryForObject(
                                                                            "SELECT value FROM "
                                                                                    + head(),
                                                                            Integer.class);
                                                                } catch (RuntimeException ignored) {
                                                                }
                                                            }

                                                            @Override
                                                            public void afterCompletion(
                                                                    int status) {}
                                                        }),
                                this::guard));
    }

    @Test
    void missingFinalStatementRollsBack() {
        reject(() -> run(() -> {}, () -> {}));
    }

    @Test
    void swallowedSecondFinalStatementStillPoisonsWholeAttempt() {
        reject(
                () ->
                        run(
                                () -> {},
                                () -> {
                                    guard();
                                    try {
                                        guard();
                                    } catch (RuntimeException ignored) {
                                    }
                                }));
    }

    @Test
    void swallowedDatabaseErrorCannotReturnSuccess() {
        reject(
                () ->
                        run(
                                () -> {
                                    try {
                                        jdbc.execute("SELECT hpr051a_nonexistent_function()");
                                    } catch (RuntimeException ignored) {
                                    }
                                },
                                this::guard));
    }

    @Test
    void nestedProtectedCallPoisonsCaughtContinuation() {
        reject(
                () ->
                        run(
                                () -> {
                                    try {
                                        executor.execute(() -> "nested", () -> {}, this::guard);
                                    } catch (RuntimeException ignored) {
                                    }
                                },
                                this::guard));
    }

    @Test
    void requiresNewSuspensionIsRejected() {
        reject(
                () ->
                        run(
                                () -> {
                                    var t = new TransactionTemplate(manager);
                                    t.setPropagationBehavior(
                                            TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                                    try {
                                        t.execute(s -> "other");
                                    } catch (RuntimeException ignored) {
                                    }
                                },
                                this::guard));
    }

    @Test
    void ambientReadOnlyAndSerializableTransactionsRejectBeforeWork() {
        for (int isolation :
                List.of(
                        TransactionDefinition.ISOLATION_READ_COMMITTED,
                        TransactionDefinition.ISOLATION_SERIALIZABLE)) {
            var t = new TransactionTemplate(manager);
            t.setReadOnly(true);
            t.setIsolationLevel(isolation);
            t.execute(
                    s -> {
                        assertThatThrownBy(() -> run(() -> {}, this::guard))
                                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class);
                        return null;
                    });
        }
        rolledBack();
    }

    @Test
    void savepointCommitRollbackAndConnectionSettingsCannotContinue() {
        for (String action :
                List.of("savepoint", "commit", "rollback", "readonly", "isolation", "autocommit"))
            reject(
                    () ->
                            run(
                                    () -> {
                                        var c = DataSourceUtils.getConnection(datasource);
                                        try {
                                            switch (action) {
                                                case "savepoint" -> c.setSavepoint();
                                                case "commit" -> c.commit();
                                                case "rollback" -> c.rollback();
                                                case "readonly" -> c.setReadOnly(true);
                                                case "isolation" ->
                                                        c.setTransactionIsolation(
                                                                Connection
                                                                        .TRANSACTION_SERIALIZABLE);
                                                default -> c.setAutoCommit(true);
                                            }
                                        } catch (SQLException ignored) {
                                        }
                                    },
                                    this::guard));
    }

    @Test
    void rawUnwrapAndAdditionalDatasourceConnectionFailClosed() {
        for (boolean unwrap : List.of(true, false))
            reject(
                    () ->
                            run(
                                    () -> {
                                        try {
                                            if (unwrap)
                                                DataSourceUtils.getConnection(datasource)
                                                        .unwrap(org.postgresql.PGConnection.class);
                                            else datasource.getConnection();
                                        } catch (SQLException ignored) {
                                        }
                                    },
                                    this::guard));
    }

    @Test
    void crossThreadCapturedHandlePoisonsOwner() {
        reject(
                () ->
                        run(
                                () -> {
                                    var c = DataSourceUtils.getConnection(datasource);
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
                                },
                                this::guard));
    }

    @Test
    void finishedHandleCannotBeReusedInOrdinaryOrNewProtectedTransactions() {
        AtomicReference<Connection> saved = new AtomicReference<>();
        run(() -> saved.set(DataSourceUtils.getConnection(datasource)), this::guard);
        assertThatThrownBy(() -> saved.get().createStatement()).isInstanceOf(SQLException.class);
        assertThatThrownBy(
                        () ->
                                executor.execute(
                                        () -> {
                                            try {
                                                saved.get().createStatement();
                                            } catch (SQLException e) {
                                                throw new IllegalStateException(e);
                                            }
                                            return "bad";
                                        },
                                        () -> {},
                                        this::guard))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class);
    }

    @Test
    void statementResultAndMetadataBackReferencesAreControlled() {
        run(
                () -> {
                    var c = DataSourceUtils.getConnection(datasource);
                    try (var statement = c.createStatement();
                            var rows = statement.executeQuery("SELECT 1")) {
                        assertThat(statement.getConnection()).isSameAs(c);
                        assertThat(rows.getStatement()).isSameAs(statement);
                        assertThat(c.getMetaData().getConnection()).isSameAs(c);
                    } catch (SQLException e) {
                        throw new IllegalStateException(e);
                    }
                },
                this::guard);
    }

    @Test
    void managedEntityAndJdbcResourceResultsAreNotDelivered() {
        reject(
                () ->
                        executor.execute(
                                () -> {
                                    work();
                                    return em.find(UserJpaEntity.class, user);
                                },
                                em::flush,
                                this::guard));
        reject(
                () ->
                        executor.execute(
                                () -> {
                                    work();
                                    return DataSourceUtils.getConnection(datasource);
                                },
                                em::flush,
                                this::guard));
    }

    @Test
    void batchAndStreamingAttemptsPoisonCaughtErrors() {
        for (boolean batch : List.of(true, false))
            reject(
                    () ->
                            run(
                                    () -> {
                                        try (var s =
                                                DataSourceUtils.getConnection(datasource)
                                                        .createStatement()) {
                                            if (batch) s.addBatch("SELECT 1");
                                            else s.setFetchSize(1);
                                        } catch (SQLException ignored) {
                                        }
                                    },
                                    this::guard));
    }

    @Test
    void openOrNonscalarGuardResultsReject() {
        reject(
                () ->
                        run(
                                () -> {},
                                () -> {
                                    try {
                                        DataSourceUtils.getConnection(datasource)
                                                .createStatement()
                                                .executeQuery("SELECT 1");
                                    } catch (SQLException e) {
                                        throw new IllegalStateException(e);
                                    }
                                }));
        reject(() -> run(() -> {}, () -> jdbc.queryForList("SELECT 1 UNION ALL SELECT 2")));
    }

    @Test
    void deferredConstraintFailureIsObservedRollbackWithoutDeliveryOrRetry() {
        jdbc.execute(
                "CREATE TABLE "
                        + schema
                        + ".commit_check(id integer REFERENCES "
                        + head()
                        + "(id) DEFERRABLE INITIALLY DEFERRED)");
        AtomicInteger attempts = new AtomicInteger();
        reject(
                () ->
                        run(
                                () -> {
                                    attempts.incrementAndGet();
                                    jdbc.update(
                                            "INSERT INTO " + schema + ".commit_check VALUES(999)");
                                },
                                this::guard));
        assertThat(attempts).hasValue(1);
    }

    @Test
    void lostCommitAcknowledgementIsIndeterminateAndObserverSeesCommittedEffects() {
        AtomicInteger attempts = new AtomicInteger();
        try {
            assertThatThrownBy(
                            () ->
                                    run(
                                            () -> {
                                                attempts.incrementAndGet();
                                                LOSE_ACK.set(true);
                                            },
                                            this::guard))
                    .isInstanceOfSatisfying(
                            ProtectedTransactionExecutor.ExecutionFailure.class,
                            e ->
                                    assertThat(e.outcome())
                                            .isEqualTo(
                                                    ProtectedTransactionExecutor.Outcome
                                                            .INDETERMINATE));
        } finally {
            LOSE_ACK.remove();
        }
        assertThat(attempts).hasValue(1);
        assertThat(jdbc.queryForObject("SELECT value FROM " + head(), Integer.class)).isEqualTo(1);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM " + receipt(), Integer.class))
                .isEqualTo(1);
    }

    @Test
    void ordinaryReadOnlyAndRequiresNewTransactionsKeepTheirSemantics() {
        var outer = new TransactionTemplate(manager);
        outer.execute(
                s -> {
                    var inner = new TransactionTemplate(manager);
                    inner.setReadOnly(true);
                    inner.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                    inner.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
                    inner.execute(
                            i -> {
                                assertThat(
                                                jdbc.queryForObject(
                                                        "SELECT"
                                                            + " current_setting('transaction_read_only')",
                                                        String.class))
                                        .isEqualTo("on");
                                assertThat(em.find(UserJpaEntity.class, user)).isNotNull();
                                return null;
                            });
                    return null;
                });
        rolledBack();
    }

    @Test
    void aggregateElapsedBudgetAbortsWholeWorkWithoutRetry() {
        reject(
                () ->
                        run(
                                () -> {
                                    long start = System.nanoTime();
                                    while (System.nanoTime() - start < 510_000_000L)
                                        jdbc.queryForObject(
                                                "SELECT clock_timestamp()", Timestamp.class);
                                },
                                this::guard));
    }

    @Test
    void eightContendersMeasureEveryWaitHoldAbortCommitTailAndAdminP95() throws Exception {
        var gate = new CyclicBarrier(8);
        List<Future<?>> futures = new ArrayList<>();
        List<String> aborts = new CopyOnWriteArrayList<>();
        List<long[]> samples = new CopyOnWriteArrayList<>();
        AtomicInteger successes = new AtomicInteger();
        try (var pool = Executors.newFixedThreadPool(8)) {
            for (int i = 0; i < 8; i++)
                futures.add(
                        pool.submit(
                                () -> {
                                    long[] sample = new long[5];
                                    sample[0] = System.nanoTime();
                                    try {
                                        gate.await(3, TimeUnit.SECONDS);
                                        executor.execute(
                                                () -> {
                                                    sample[1] = System.nanoTime();
                                                    fence();
                                                    sample[2] = System.nanoTime();
                                                    jdbc.update(
                                                            "UPDATE "
                                                                    + head()
                                                                    + " SET value=value+1");
                                                    return "ok";
                                                },
                                                em::flush,
                                                () -> {
                                                    guard();
                                                    sample[3] = System.nanoTime();
                                                });
                                        successes.incrementAndGet();
                                    } catch (Exception e) {
                                        aborts.add(
                                                e.getClass().getSimpleName()
                                                        + ":"
                                                        + e.getMessage());
                                    } finally {
                                        sample[4] = System.nanoTime();
                                        samples.add(sample);
                                    }
                                }));
            for (var f : futures) f.get(10, TimeUnit.SECONDS);
        }
        assertThat(samples).hasSize(8);
        assertThat(successes.get()).isPositive();
        assertThat(successes.get() + aborts.size()).isEqualTo(8);
        assertThat(jdbc.queryForObject("SELECT value FROM " + head(), Integer.class))
                .isEqualTo(successes.get());
        assertThat(jdbc.queryForObject("SELECT count(*) FROM " + receipt(), Integer.class))
                .isEqualTo(successes.get());
        for (long[] s : samples) {
            if (s[3] > 0) assertThat(s[3] - s[1]).isLessThan(500_000_000L);
            System.out.println(
                    "HPR051A attempt work_started="
                            + (s[1] > 0)
                            + " guarded="
                            + (s[3] > 0)
                            + " acquisition_ms="
                            + (s[2] > 0 ? (s[2] - s[1]) / 1_000_000.0 : 0)
                            + " hold_release_ms="
                            + (s[2] > 0 ? (s[4] - s[2]) / 1_000_000.0 : 0)
                            + " commit_tail_ms="
                            + (s[3] > 0 ? (s[4] - s[3]) / 1_000_000.0 : 0)
                            + " total_ms="
                            + (s[4] - s[0]) / 1_000_000.0);
        }
        List<Double> tails = new ArrayList<>();
        try (Connection c = independent()) {
            for (int i = 0; i < 8; i++) {
                long start = System.nanoTime();
                sql(c, "UPDATE " + head() + " SET value=value");
                tails.add((System.nanoTime() - start) / 1_000_000.0);
            }
        }
        tails.sort(Double::compareTo);
        assertThat(tails.get(7)).isLessThanOrEqualTo(1000.0);
        System.out.println(
                "HPR051A cohort=8 success="
                        + successes
                        + " aborts="
                        + aborts
                        + " admin_samples_ms="
                        + tails
                        + " cpu="
                        + Runtime.getRuntime().availableProcessors()
                        + " heap_bytes="
                        + Runtime.getRuntime().maxMemory()
                        + " condition=fresh-fixture/warm-Spring injected_outages=0"
                        + " no-production-COMMIT-tail-guarantee");
    }

    @Test
    void emptyScalarGuardCannotBeCaughtAndCommitted() {
        reject(
                () ->
                        run(
                                () -> {},
                                () -> {
                                    try {
                                        jdbc.queryForObject("SELECT 1 WHERE false", Integer.class);
                                    } catch (RuntimeException ignored) {
                                    }
                                }));
    }
}
