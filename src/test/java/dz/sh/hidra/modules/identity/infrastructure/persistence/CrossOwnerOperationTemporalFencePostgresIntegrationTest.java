/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CrossOwnerOperationTemporalFencePostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Exercises disposable temporal-fence prototypes and rejects unsafe Spring transaction lifecycles without claiming production owner coverage.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

import static org.assertj.core.api.Assertions.*;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserJpaEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.hibernate.Session;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.test.context.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.*;
import org.springframework.transaction.support.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;

import java.lang.reflect.*;
import java.sql.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import javax.sql.DataSource;

/**
 * Disposable mechanism proof only. Synthetic Identity/Party/Topology relations are not
 * production contributor exports or the 038 head. The datasource seal below is TEST ONLY:
 * ordinary Spring beforeCommit plus a database guard cannot enforce the final statement.
 * No callback, connection wrapper or temporal predicate is installed in production.
 */
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(
        classes = HidraApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@Import(CrossOwnerOperationTemporalFencePostgresIntegrationTest.ProofConfiguration.class)
class CrossOwnerOperationTemporalFencePostgresIntegrationTest {
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

    @Autowired JdbcTemplate jdbc;
    @Autowired PlatformTransactionManager manager;
    @PersistenceContext EntityManager em;
    String schema, user;
    final List<Attempt> observations = new CopyOnWriteArrayList<>();
    static final ThreadLocal<Attempt> ACTIVE = new ThreadLocal<>();
    static final List<String> FAMILIES =
            List.of(
                    "grant",
                    "rule",
                    "membership",
                    "attribute",
                    "source",
                    "session",
                    "credential",
                    "provider",
                    "user",
                    "ownership");

    static class Attempt {
        final Thread thread = Thread.currentThread();
        Connection physical;
        int pid;
        long xid;
        Instant start, sample, end, guard;
        boolean sealed, enforce = true, uncertainCommit;
        int statements, guardStatement, commits;
        final List<String> order = new ArrayList<>();
        final List<Double> waits = new ArrayList<>();
        long firstAttempt, firstHeld, acquired, guarded, committed, finished;

        void participation() {
            if (Thread.currentThread() != thread
                    || ACTIVE.get() != this
                    || !TransactionSynchronizationManager.isActualTransactionActive()
                    || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                    || !Objects.equals(
                            TransactionSynchronizationManager.getCurrentTransactionIsolationLevel(),
                            TransactionDefinition.ISOLATION_READ_COMMITTED)) {
                throw new IllegalStateException("INDETERMINATE: wrong owner transaction");
            }
        }
    }

    /** Intercepts actual Spring JDBC AND Hibernate statements, including commit-time flush. */
    @TestConfiguration(proxyBeanMethods = false)
    static class ProofConfiguration {
        @Bean
        static BeanPostProcessor proofDatasource() {
            return new BeanPostProcessor() {
                @Override
                public Object postProcessAfterInitialization(Object bean, String name) {
                    if (!(bean instanceof DataSource ds)) return bean;
                    return new DelegatingDataSource(ds) {
                        @Override
                        public Connection getConnection() throws SQLException {
                            return connection(super.getConnection());
                        }

                        @Override
                        public Connection getConnection(String u, String p) throws SQLException {
                            return connection(super.getConnection(u, p));
                        }
                    };
                }
            };
        }
    }

    static Object invoke(Object target, Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(target, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    static Connection connection(Connection physical) {
        return (Connection)
                Proxy.newProxyInstance(
                        Connection.class.getClassLoader(),
                        new Class<?>[] {Connection.class},
                        (proxy, method, args) -> {
                            Attempt a = ACTIVE.get();
                            if (a != null
                                    && a.enforce
                                    && a.sealed
                                    && (method.getName().equals("prepareStatement")
                                            || method.getName().equals("prepareCall")
                                            || method.getName().equals("createStatement"))) {
                                throw new SQLException(
                                        "INDETERMINATE: statement preparation after final"
                                                + " guard",
                                        "25000");
                            }
                            if (a != null && method.getName().equals("setSavepoint")) {
                                throw new SQLException(
                                        "INDETERMINATE: partial savepoint continuation"
                                                + " prohibited",
                                        "25000");
                            }
                            if (a != null && method.getName().equals("commit")) {
                                a.commits++;
                                Object result = invoke(physical, method, args);
                                a.committed = System.nanoTime();
                                if (a.uncertainCommit)
                                    throw new SQLException(
                                            "Injected lost COMMIT acknowledgement AFTER actual"
                                                    + " commit",
                                            "08006");
                                return result;
                            }
                            Object result = invoke(physical, method, args);
                            if (result instanceof Statement statement) {
                                Class<?> type =
                                        result instanceof CallableStatement
                                                ? CallableStatement.class
                                                : result instanceof PreparedStatement
                                                        ? PreparedStatement.class
                                                        : Statement.class;
                                return Proxy.newProxyInstance(
                                        type.getClassLoader(),
                                        new Class<?>[] {type},
                                        (p, m, v) -> {
                                            Attempt current = ACTIVE.get();
                                            if (current != null
                                                    && (m.getName().startsWith("execute")
                                                            || m.getName().equals("addBatch"))) {
                                                current.participation();
                                                if (current.physical == null)
                                                    current.physical = physical;
                                                if (current.physical != physical)
                                                    throw new SQLException(
                                                            "INDETERMINATE: different physical"
                                                                    + " connection",
                                                            "25000");
                                                if (current.enforce && current.sealed)
                                                    throw new SQLException(
                                                            "INDETERMINATE: SQL after final guard",
                                                            "25000");
                                                current.statements++;
                                            }
                                            return invoke(statement, m, v);
                                        });
                            }
                            return result;
                        });
    }

    @BeforeEach
    void fixture() {
        ACTIVE.remove();
        schema = "hpr051_" + UUID.randomUUID().toString().replace("-", "");
        user = "hpr051-" + UUID.randomUUID();
        jdbc.execute("CREATE SCHEMA " + schema);
        for (String table : List.of("identity", "party", "topology")) {
            jdbc.execute(
                    "CREATE TABLE "
                            + table(table)
                            + " (id integer PRIMARY KEY, epoch bigint NOT NULL, value integer NOT"
                            + " NULL)");
            jdbc.update("INSERT INTO " + table(table) + " VALUES (1,1,0)");
        }
        jdbc.execute(
                "CREATE TABLE "
                        + table("windows")
                        + " (family text PRIMARY KEY, starts timestamptz NOT NULL, ends timestamptz"
                        + " NOT NULL, deny boolean NOT NULL, matching boolean NOT NULL, supported"
                        + " boolean NOT NULL)");
        for (String family : FAMILIES)
            jdbc.update(
                    "INSERT INTO "
                            + table("windows")
                            + " VALUES (?,clock_timestamp()-interval '1"
                            + " minute',clock_timestamp()+interval '1 minute',false,true,true)",
                    family);
        jdbc.execute(
                "CREATE TABLE "
                        + table("receipt")
                        + " (id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY, s timestamptz NOT"
                        + " NULL, t timestamptz NOT NULL, pid integer NOT NULL, xid bigint NOT"
                        + " NULL)");
        // Invoker function; fixture names are generated identifiers, never external input.
        jdbc.execute(
                "CREATE FUNCTION "
                        + schema
                        + ".guard(s timestamptz,u timestamptz,b timestamptz,expected bigint)"
                        + " RETURNS timestamptz LANGUAGE plpgsql AS $$ DECLARE t timestamptz :="
                        + " clock_timestamp(); BEGIN IF t<s OR t>=u OR t-b>=interval '500"
                        + " milliseconds' OR EXISTS (SELECT 1 FROM "
                        + table("topology")
                        + " WHERE epoch<>expected) THEN RAISE EXCEPTION 'INDETERMINATE:"
                        + " temporal/head guard' USING ERRCODE='P0001'; END IF; INSERT INTO "
                        + table("receipt")
                        + " (s,t,pid,xid) VALUES(s,t,pg_backend_pid(),txid_current()); RETURN t;"
                        + " END $$");
        jdbc.update(
                "INSERT INTO"
                    + " public.hidra_identity_user(id,username,user_type,status,failed_login_count,created_at,updated_at)"
                    + " VALUES (?,?,'HUMAN','ACTIVE',0,clock_timestamp(),clock_timestamp())",
                user,
                user);
    }

    @AfterEach
    void cleanup() {
        ACTIVE.remove();
        jdbc.execute("DROP SCHEMA " + schema + " CASCADE");
        jdbc.update("DELETE FROM public.hidra_identity_user WHERE id=?", user);
    }

    String table(String name) {
        return schema + "." + name;
    }

    TransactionTemplate transaction(int isolation, boolean readOnly) {
        var t = new TransactionTemplate(manager);
        t.setIsolationLevel(isolation);
        t.setReadOnly(readOnly);
        return t;
    }

    <T> T owner(java.util.function.Function<Attempt, T> body) {
        Attempt a = new Attempt();
        observations.add(a);
        try {
            return transaction(TransactionDefinition.ISOLATION_READ_COMMITTED, false)
                    .execute(
                            status -> {
                                ACTIVE.set(a);
                                a.participation();
                                return body.apply(a);
                            });
        } finally {
            a.finished = System.nanoTime();
            ACTIVE.remove();
        }
    }

    void fence(Attempt a) {
        a.participation();
        jdbc.execute("SET LOCAL lock_timeout='250ms'");
        jdbc.execute("SET LOCAL statement_timeout='750ms'");
        jdbc.execute("SET LOCAL idle_in_transaction_session_timeout='1000ms'");
        a.firstAttempt = System.nanoTime();
        a.start = now();
        for (String relation :
                List.of(
                        "public.hidra_identity_user",
                        table("identity"),
                        table("windows"),
                        table("party"),
                        table("topology"),
                        table("receipt"))) {
            if (Duration.between(a.start, now()).toMillis() >= 500)
                throw new IllegalStateException("INDETERMINATE: aggregate fence deadline");
            long before = System.nanoTime();
            try {
                jdbc.execute("LOCK TABLE " + relation + " IN SHARE ROW EXCLUSIVE MODE");
                if (a.firstHeld == 0) a.firstHeld = System.nanoTime();
            } finally {
                a.waits.add((System.nanoTime() - before) / 1_000_000.0);
            }
            a.order.add(relation);
        }
        a.acquired = System.nanoTime();
        a.pid = jdbc.queryForObject("SELECT pg_backend_pid()", Integer.class);
        a.xid = jdbc.queryForObject("SELECT txid_current()", Long.class);
        a.sample = now();
        assertThat(
                        jdbc.queryForObject(
                                "SELECT count(*) FROM pg_locks WHERE pid=pg_backend_pid() AND"
                                        + " mode='ShareRowExclusiveLock' AND granted",
                                Integer.class))
                .isGreaterThanOrEqualTo(6);
    }

    Instant now() {
        return jdbc.queryForObject(
                "SELECT clock_timestamp()", (rs, n) -> rs.getTimestamp(1).toInstant());
    }

    void decisions(Attempt a) {
        a.participation();
        if (!new HashSet<>(
                        jdbc.queryForList("SELECT family FROM " + table("windows"), String.class))
                .equals(new HashSet<>(FAMILIES))) {
            throw new IllegalStateException("INDETERMINATE: incomplete contributor families");
        }
        if (jdbc.queryForObject(
                        "SELECT count(*) FROM "
                                + table("windows")
                                + " WHERE NOT supported OR starts>=ends",
                        Integer.class)
                != 0) throw new IllegalStateException("INDETERMINATE: unsupported window");
        if (jdbc.queryForObject(
                        "SELECT count(*) FROM "
                                + table("windows")
                                + " WHERE deny AND matching AND starts<=? AND ends>?",
                        Integer.class,
                        Timestamp.from(a.sample),
                        Timestamp.from(a.sample))
                != 0) throw new IllegalStateException("DENY");
        // Do not filter inactive or currently nonmatching contributors out of future coverage.
        a.end =
                jdbc.queryForObject(
                        "SELECT min(boundary) FROM (SELECT starts boundary FROM "
                                + table("windows")
                                + " UNION ALL SELECT ends FROM "
                                + table("windows")
                                + ") w WHERE boundary>?",
                        (rs, n) -> rs.getTimestamp(1).toInstant(),
                        Timestamp.from(a.sample));
        if (a.end == null || !a.sample.isBefore(a.end))
            throw new IllegalStateException("INDETERMINATE: no bounded interval");
        // Both synthetic owner decisions are evaluated in one statement at that SAME DB sample.
        var pair =
                jdbc.queryForMap(
                        "WITH sample AS (SELECT ?::timestamptz s) SELECT p.epoch=1 AND"
                                + " s<?::timestamptz AS company, h.epoch=1 AND s<?::timestamptz AS"
                                + " facility, s company_time, s facility_time FROM "
                                + table("party")
                                + " p CROSS JOIN "
                                + table("topology")
                                + " h CROSS JOIN sample",
                        Timestamp.from(a.sample),
                        Timestamp.from(a.end),
                        Timestamp.from(a.end));
        if (!Boolean.TRUE.equals(pair.get("company")) || !Boolean.TRUE.equals(pair.get("facility")))
            throw new IllegalStateException("DENY: synthetic paired decisions");
        assertThat(pair.get("company_time")).isEqualTo(pair.get("facility_time"));
    }

    void work() {
        var entity = em.find(UserJpaEntity.class, user);
        ReflectionTestUtils.setField(entity, "displayName", "provisional");
        em.flush();
        jdbc.update("UPDATE " + table("topology") + " SET value=value+1 WHERE id=1");
    }

    void guard(Attempt a) {
        a.participation();
        a.guard =
                jdbc.queryForObject(
                        "SELECT " + schema + ".guard(?,?,?,1)",
                        (rs, n) -> rs.getTimestamp(1).toInstant(),
                        Timestamp.from(a.sample),
                        Timestamp.from(a.end),
                        Timestamp.from(a.start));
        a.guarded = System.nanoTime();
        if (a.guarded - a.firstAttempt >= TimeUnit.MILLISECONDS.toNanos(500))
            throw new IllegalStateException("INDETERMINATE: aggregate elapsed budget");
        a.guardStatement = a.statements;
        a.sealed = true;
    }

    void rolledBack() {
        assertThat(jdbc.queryForObject("SELECT value FROM " + table("topology"), Integer.class))
                .isZero();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM " + table("receipt"), Integer.class))
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

    static void sql(Connection c, String sql) throws SQLException {
        try (var s = c.createStatement()) {
            s.execute(sql);
        }
    }

    static int scalar(Connection c, String sql) throws SQLException {
        try (var s = c.createStatement();
                var r = s.executeQuery(sql)) {
            r.next();
            return r.getInt(1);
        }
    }

    @Test
    void freshFlywayAndJpaJdbcShareOneWritablePhysicalTransaction() {
        assertThat(
                        jdbc.queryForObject(
                                "SELECT count(*) FROM flyway_schema_history WHERE success",
                                Integer.class))
                .isPositive();
        owner(
                a -> {
                    fence(a);
                    decisions(a);
                    int[] jpaPid = new int[1];
                    em.unwrap(Session.class)
                            .doWork(c -> jpaPid[0] = scalar(c, "SELECT pg_backend_pid()"));
                    assertThat(jpaPid[0]).isEqualTo(a.pid);
                    assertThat(jdbc.queryForObject("SELECT txid_current()", Long.class))
                            .isEqualTo(a.xid);
                    work();
                    guard(a);
                    return null;
                });
        assertThat(jdbc.queryForObject("SELECT value FROM " + table("topology"), Integer.class))
                .isEqualTo(1);
    }

    @Test
    void guardedHeadAndProtectedReadDeliverOnlyAfterPhysicalCommit() {
        AtomicBoolean delivered = new AtomicBoolean();
        AtomicReference<Attempt> saved = new AtomicReference<>();
        String result =
                owner(
                        a -> {
                            saved.set(a);
                            fence(a);
                            decisions(a);
                            work();
                            String value =
                                    jdbc.queryForObject(
                                            "SELECT display_name FROM public.hidra_identity_user"
                                                    + " WHERE id=?",
                                            String.class,
                                            user);
                            TransactionSynchronizationManager.registerSynchronization(
                                    new TransactionSynchronization() {
                                        @Override
                                        public void afterCommit() {
                                            assertThat(delivered).isFalse();
                                        }
                                    });
                            guard(a);
                            return value;
                        });
        delivered.set(true);
        assertThat(result).isEqualTo("provisional");
        var a = saved.get();
        assertThat(a.commits).isEqualTo(1);
        assertThat(a.statements).isEqualTo(a.guardStatement);
        assertThat(a.committed).isGreaterThanOrEqualTo(a.guarded);
        assertThat(jdbc.queryForObject("SELECT t>=s FROM " + table("receipt"), Boolean.class))
                .isTrue();
    }

    @Test
    void nativeInsertDeleteUpdateAndPhantomDenyCannotPassHeldRelationFences() throws Exception {
        for (String mutation :
                List.of(
                        "INSERT INTO "
                                + table("windows")
                                + " VALUES('phantom',clock_timestamp(),clock_timestamp()+interval"
                                + " '1 minute',true,true,true)",
                        "DELETE FROM " + table("windows") + " WHERE family='rule'",
                        "UPDATE " + table("party") + " SET epoch=2")) {
            // Establish the independent backend before retaining owner fences. Each negative
            // writer probe has its own protected transaction; deliberate waits never stack.
            try (Connection c = independent()) {
                c.setAutoCommit(false);
                sql(c, "SET LOCAL lock_timeout='100ms'");
                int writerPid = scalar(c, "SELECT pg_backend_pid()");
                owner(
                        a -> {
                            fence(a);
                            assertThat(a.order)
                                    .containsExactly(
                                            "public.hidra_identity_user",
                                            table("identity"),
                                            table("windows"),
                                            table("party"),
                                            table("topology"),
                                            table("receipt"));
                            assertThat(writerPid).isNotEqualTo(a.pid);
                            assertThatThrownBy(() -> sql(c, mutation))
                                    .isInstanceOf(SQLException.class)
                                    .extracting(e -> ((SQLException) e).getSQLState())
                                    .isEqualTo("55P03");
                            assertThatThrownBy(() -> sql(c, "SELECT 1"))
                                    .isInstanceOf(SQLException.class);
                            try {
                                c.rollback();
                            } catch (SQLException e) {
                                throw new IllegalStateException(e);
                            }
                            decisions(a);
                            guard(a);
                            return null;
                        });
            }
        }
        try (Connection c = independent()) {
            sql(c, "UPDATE " + table("party") + " SET epoch=2");
            sql(c, "DELETE FROM " + table("windows") + " WHERE family='rule'");
            sql(
                    c,
                    "INSERT INTO "
                            + table("windows")
                            + " VALUES('phantom',clock_timestamp(),clock_timestamp()+interval '1"
                            + " minute',true,true,true)");
        }
    }

    @Test
    void nativeDenyCommittedBeforeFenceIsObservedAndNoProvisionalEffectEscapes() throws Exception {
        try (Connection c = independent()) {
            sql(c, "UPDATE " + table("windows") + " SET deny=true WHERE family='rule'");
        }
        assertThatThrownBy(
                        () ->
                                owner(
                                        a -> {
                                            fence(a);
                                            decisions(a);
                                            work();
                                            guard(a);
                                            return null;
                                        }))
                .hasMessageContaining("DENY");
        rolledBack();
    }

    @Test
    void heldNativeWriterCausesFenceTimeoutAndFullRollback() throws Exception {
        try (Connection c = independent()) {
            c.setAutoCommit(false);
            sql(c, "UPDATE " + table("party") + " SET epoch=2");
            assertThatThrownBy(
                            () ->
                                    owner(
                                            a -> {
                                                jdbc.update(
                                                        "UPDATE "
                                                                + table("topology")
                                                                + " SET value=1");
                                                fence(a);
                                                return null;
                                            }))
                    .isInstanceOf(RuntimeException.class);
            c.rollback();
        }
        rolledBack();
    }

    @Test
    void earliestBoundaryIncludesInactiveNonmatchingDenyAndSourceCap() {
        jdbc.update(
                "UPDATE "
                        + table("windows")
                        + " SET starts=clock_timestamp()+interval '10"
                        + " seconds',ends=clock_timestamp()+interval '20"
                        + " seconds',deny=true,matching=false WHERE family='rule'");
        jdbc.update(
                "UPDATE "
                        + table("windows")
                        + " SET ends=clock_timestamp()+interval '5 seconds' WHERE family='source'");
        owner(
                a -> {
                    fence(a);
                    decisions(a);
                    Instant expected =
                            jdbc.queryForObject(
                                    "SELECT ends FROM "
                                            + table("windows")
                                            + " WHERE family='source'",
                                    (rs, n) -> rs.getTimestamp(1).toInstant());
                    assertThat(a.end).isEqualTo(expected);
                    guard(a);
                    return null;
                });
    }

    @Test
    void halfOpenEqualityBackwardAndForwardSamplesRejectEveryProvisionalEffect() {
        for (int variant = 0; variant < 3; variant++) {
            int v = variant;
            assertThatThrownBy(
                            () ->
                                    owner(
                                            a -> {
                                                fence(a);
                                                decisions(a);
                                                work();
                                                // Deliberate invalid candidate inputs, NOT
                                                // fabricated server clock metadata.
                                                if (v == 0) a.end = a.sample;
                                                if (v == 1) a.sample = a.end.plusSeconds(1);
                                                if (v == 2) a.end = a.sample.minusSeconds(1);
                                                guard(a);
                                                return null;
                                            }))
                    .isInstanceOf(RuntimeException.class);
            rolledBack();
        }
    }

    @Test
    void unsupportedAndIncompleteDependenciesFailClosed() {
        jdbc.update("UPDATE " + table("windows") + " SET supported=false WHERE family='attribute'");
        assertThatThrownBy(
                        () ->
                                owner(
                                        a -> {
                                            fence(a);
                                            decisions(a);
                                            return null;
                                        }))
                .hasMessageContaining("unsupported");
        jdbc.update("DELETE FROM " + table("windows") + " WHERE family='membership'");
        assertThatThrownBy(
                        () ->
                                owner(
                                        a -> {
                                            fence(a);
                                            decisions(a);
                                            return null;
                                        }))
                .isInstanceOf(IllegalStateException.class);
        rolledBack();
    }

    @Test
    void expiryAndFutureDenyActivationDuringWorkRejectFinalGuard() {
        for (String family : List.of("source", "rule")) {
            jdbc.update(
                    "UPDATE "
                            + table("windows")
                            + " SET starts=clock_timestamp()-interval '1 minute',"
                            + " ends=clock_timestamp()+interval '1"
                            + " minute',deny=false,matching=true");
            jdbc.update(
                    "UPDATE "
                            + table("windows")
                            + " SET starts=clock_timestamp()+interval '100"
                            + " milliseconds',ends=clock_timestamp()+interval '1"
                            + " minute',deny=true,matching=false WHERE family=?",
                    family);
            assertThatThrownBy(
                            () ->
                                    owner(
                                            a -> {
                                                fence(a);
                                                decisions(a);
                                                work();
                                                // Actual DB-clock barrier, no sleep or forged
                                                // sample; does not extend lifetime.
                                                long deadline =
                                                        System.nanoTime()
                                                                + TimeUnit.MILLISECONDS.toNanos(
                                                                        700);
                                                while (now().isBefore(a.end)) {
                                                    if (System.nanoTime() > deadline)
                                                        throw new AssertionError(
                                                                "clock barrier deadline");
                                                }
                                                guard(a);
                                                return null;
                                            }))
                    .isInstanceOf(RuntimeException.class);
            rolledBack();
        }
    }

    @Test
    void transactionStartTimestampCannotReplaceCurrentDatabaseClockOrWorkBudget() {
        assertThatThrownBy(
                        () ->
                                owner(
                                        a -> {
                                            fence(a);
                                            decisions(a);
                                            work();
                                            Instant began =
                                                    jdbc.queryForObject(
                                                            "SELECT transaction_timestamp()",
                                                            (rs, n) ->
                                                                    rs.getTimestamp(1).toInstant());
                                            long limit =
                                                    System.nanoTime()
                                                            + TimeUnit.MILLISECONDS.toNanos(900);
                                            while (Duration.between(a.start, now()).toMillis()
                                                    < 510) {
                                                if (System.nanoTime() > limit)
                                                    throw new AssertionError(
                                                            "clock barrier deadline");
                                            }
                                            assertThat(now()).isAfter(began);
                                            guard(a);
                                            return null;
                                        }))
                .isInstanceOf(RuntimeException.class);
        rolledBack();
    }

    @Test
    void wrongIsolationReadOnlyMissingAsyncAndRequiresNewParticipationReject() {
        Attempt orphan = new Attempt();
        assertThatThrownBy(orphan::participation).isInstanceOf(IllegalStateException.class);
        for (var t :
                List.of(
                        transaction(TransactionDefinition.ISOLATION_SERIALIZABLE, false),
                        transaction(TransactionDefinition.ISOLATION_READ_COMMITTED, true))) {
            assertThatThrownBy(
                            () ->
                                    t.execute(
                                            s -> {
                                                ACTIVE.set(new Attempt());
                                                try {
                                                    ACTIVE.get().participation();
                                                    return null;
                                                } finally {
                                                    ACTIVE.remove();
                                                }
                                            }))
                    .isInstanceOf(IllegalStateException.class);
        }
        owner(
                a -> {
                    fence(a);
                    try (var pool = Executors.newSingleThreadExecutor()) {
                        assertThatThrownBy(
                                        () ->
                                                pool.submit(a::participation)
                                                        .get(2, TimeUnit.SECONDS))
                                .isInstanceOf(ExecutionException.class);
                    }
                    var nested = transaction(TransactionDefinition.ISOLATION_READ_COMMITTED, false);
                    nested.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                    assertThatThrownBy(
                                    () ->
                                            nested.execute(
                                                    s -> {
                                                        jdbc.queryForObject(
                                                                "SELECT pg_backend_pid()",
                                                                Integer.class);
                                                        return null;
                                                    }))
                            .isInstanceOf(RuntimeException.class);
                    return null;
                });
        rolledBack();
    }

    @Test
    void lateDirtyJpaFlushAfterGuardIsRejectedAndEntireTransactionRollsBack() {
        assertThatThrownBy(
                        () ->
                                owner(
                                        a -> {
                                            fence(a);
                                            decisions(a);
                                            work();
                                            guard(a);
                                            // No explicit second flush: this dirty change reaches
                                            // Hibernate's real commit flush.
                                            ReflectionTestUtils.setField(
                                                    em.find(UserJpaEntity.class, user),
                                                    "displayName",
                                                    "late");
                                            return "must not deliver";
                                        }))
                .isInstanceOf(RuntimeException.class);
        rolledBack();
    }

    @Test
    void beforeCommitWriteAndCallbackEvaluationCannotFollowGuard() {
        for (boolean read : List.of(false, true)) {
            assertThatThrownBy(
                            () ->
                                    owner(
                                            a -> {
                                                fence(a);
                                                decisions(a);
                                                work();
                                                TransactionSynchronizationManager
                                                        .registerSynchronization(
                                                                new TransactionSynchronization() {
                                                                    @Override
                                                                    public void beforeCommit(
                                                                            boolean ro) {
                                                                        if (read)
                                                                            jdbc.queryForObject(
                                                                                    "SELECT epoch"
                                                                                        + " FROM "
                                                                                            + table(
                                                                                                    "party"),
                                                                                    Long.class);
                                                                        else
                                                                            jdbc.update(
                                                                                    "UPDATE "
                                                                                            + table(
                                                                                                    "topology")
                                                                                            + " SET value=99");
                                                                    }
                                                                });
                                                guard(a);
                                                return null;
                                            }))
                    .isInstanceOf(RuntimeException.class);
            rolledBack();
        }
    }

    @Test
    void ordinarySpringFinalGuardWithoutDatasourceSealIsNotAnAtomicFence() {
        owner(
                a -> {
                    a.enforce = false;
                    fence(a);
                    decisions(a);
                    work();
                    guard(a);
                    TransactionSynchronizationManager.registerSynchronization(
                            new TransactionSynchronization() {
                                @Override
                                public void beforeCommit(boolean ro) {
                                    jdbc.update("UPDATE " + table("topology") + " SET value=99");
                                }
                            });
                    return null;
                });
        assertThat(jdbc.queryForObject("SELECT value FROM " + table("topology"), Integer.class))
                .isEqualTo(99);
        // This positive counterexample rejects adopting ordinary Spring beforeCommit alone.
    }

    @Test
    void partialSavepointContinuationIsRejectedInsteadOfReturningPermit() {
        assertThatThrownBy(
                        () ->
                                owner(
                                        a -> {
                                            fence(a);
                                            decisions(a);
                                            work();
                                            // Exercise an actual Hibernate/JDBC savepoint attempt;
                                            // owner refuses it.
                                            em.unwrap(Session.class)
                                                    .doWork(c -> c.setSavepoint("partial"));
                                            throw new AssertionError(
                                                    "savepoint must have been rejected");
                                        }))
                .isInstanceOf(RuntimeException.class);
        rolledBack();
    }

    @Test
    void deferredCommitFailureHasObservedRollbackAndNoDeliveryOrRetry() {
        jdbc.execute(
                "CREATE TABLE "
                        + table("commit_check")
                        + " (id integer REFERENCES "
                        + table("party")
                        + "(id) DEFERRABLE INITIALLY DEFERRED)");
        AtomicInteger attempts = new AtomicInteger();
        AtomicBoolean delivered = new AtomicBoolean();
        assertThatThrownBy(
                        () -> {
                            owner(
                                    a -> {
                                        attempts.incrementAndGet();
                                        fence(a);
                                        decisions(a);
                                        work();
                                        jdbc.update(
                                                "INSERT INTO "
                                                        + table("commit_check")
                                                        + " VALUES(999)");
                                        guard(a);
                                        return null;
                                    });
                            delivered.set(true);
                        })
                .isInstanceOf(RuntimeException.class);
        assertThat(attempts).hasValue(1);
        assertThat(delivered).isFalse();
        rolledBack();
        assertThat(
                        jdbc.queryForObject(
                                "SELECT count(*) FROM " + table("commit_check"), Integer.class))
                .isZero();
    }

    @Test
    void lostCommitAcknowledgementIsIndeterminateEvenWhenObserverSeesCommittedEffects() {
        AtomicInteger attempts = new AtomicInteger();
        AtomicBoolean delivered = new AtomicBoolean();
        assertThatThrownBy(
                        () -> {
                            owner(
                                    a -> {
                                        attempts.incrementAndGet();
                                        fence(a);
                                        decisions(a);
                                        work();
                                        guard(a);
                                        a.uncertainCommit = true;
                                        return null;
                                    });
                            delivered.set(true);
                        })
                .isInstanceOf(RuntimeException.class);
        assertThat(attempts).hasValue(1);
        assertThat(delivered).isFalse();
        assertThat(jdbc.queryForObject("SELECT value FROM " + table("topology"), Integer.class))
                .isEqualTo(1);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM " + table("receipt"), Integer.class))
                .isEqualTo(1);
        // This is an injected transport outcome after a real commit, NOT an observed rollback.
    }

    @Test
    void eightContendersReportEveryWaitHoldCommitTailAbortAndAdminCompletion() throws Exception {
        var barrier = new CyclicBarrier(8);
        List<Attempt> successes = new CopyOnWriteArrayList<>();
        List<String> aborts = new CopyOnWriteArrayList<>();
        List<Future<?>> futures = new ArrayList<>();
        List<Double> adminTails = new ArrayList<>();
        try (var pool = Executors.newFixedThreadPool(8)) {
            for (int i = 0; i < 8; i++)
                futures.add(
                        pool.submit(
                                () -> {
                                    try {
                                        barrier.await(3, TimeUnit.SECONDS);
                                        owner(
                                                a -> {
                                                    fence(a);
                                                    decisions(a);
                                                    jdbc.update(
                                                            "UPDATE "
                                                                    + table("topology")
                                                                    + " SET value=value+1");
                                                    guard(a);
                                                    successes.add(a);
                                                    return null;
                                                });
                                    } catch (Exception e) {
                                        aborts.add(
                                                e.getClass().getSimpleName()
                                                        + ":"
                                                        + e.getMessage());
                                    }
                                }));
            for (var f : futures) f.get(10, TimeUnit.SECONDS);
        }
        // Admin samples begin after all retained fences are released; no outage injected here.
        try (Connection c = independent()) {
            for (int i = 0; i < 8; i++) {
                long start = System.nanoTime();
                sql(c, "UPDATE " + table("party") + " SET value=value+1");
                adminTails.add((System.nanoTime() - start) / 1_000_000.0);
            }
        }
        assertThat(successes.size() + aborts.size()).isEqualTo(8);
        assertThat(successes).isNotEmpty();
        assertThat(jdbc.queryForObject("SELECT value FROM " + table("topology"), Integer.class))
                .isEqualTo(successes.size());
        assertThat(jdbc.queryForObject("SELECT count(*) FROM " + table("receipt"), Integer.class))
                .isEqualTo(successes.size());
        for (var a : successes) {
            assertThat(Duration.between(a.start, a.guard).toNanos())
                    .isLessThan(TimeUnit.MILLISECONDS.toNanos(500));
            assertThat(a.guarded - a.firstAttempt).isLessThan(TimeUnit.MILLISECONDS.toNanos(500));
            assertThat(a.statements).isEqualTo(a.guardStatement);
            System.out.println(
                    "HPR051 contender waits_ms="
                            + a.waits
                            + " hold_ms="
                            + (a.committed - a.acquired) / 1_000_000.0
                            + " commit_tail_ms="
                            + (a.committed - a.guarded) / 1_000_000.0);
        }
        assertThat(observations).hasSize(8);
        for (var a : observations) {
            System.out.println(
                    "HPR051 all_attempt waits_ms="
                            + a.waits
                            + " acquired="
                            + (a.acquired > 0)
                            + " guarded="
                            + (a.guarded > 0)
                            + " commits="
                            + a.commits
                            + " total_ms="
                            + (a.finished - a.firstAttempt) / 1_000_000.0
                            + " hold_until_release_ms="
                            + (a.firstHeld > 0 ? (a.finished - a.firstHeld) / 1_000_000.0 : 0));
        }
        adminTails.sort(Double::compareTo);
        assertThat(adminTails.get(7)).isLessThanOrEqualTo(1000.0);
        System.out.println(
                "HPR051 cohort=8 success="
                        + successes.size()
                        + " aborts="
                        + aborts
                        + " admin_samples_ms="
                        + adminTails
                        + " cpus="
                        + Runtime.getRuntime().availableProcessors()
                        + " heap_bytes="
                        + Runtime.getRuntime().maxMemory()
                        + " condition=fresh-fixture/warm-Spring injected_outages=0; no production"
                        + " COMMIT-tail guarantee");
    }

    @Test
    void wrongEpochRejectsTheFinalGuardAndRollsBackJpaAndHead() {
        assertThatThrownBy(
                        () ->
                                owner(
                                        a -> {
                                            fence(a);
                                            decisions(a);
                                            work();
                                            jdbc.update(
                                                    "UPDATE " + table("topology") + " SET epoch=2");
                                            guard(a);
                                            return null;
                                        }))
                .isInstanceOf(RuntimeException.class);
        rolledBack();
        assertThat(jdbc.queryForObject("SELECT epoch FROM " + table("topology"), Long.class))
                .isEqualTo(1L);
    }

    @Test
    void nativeDeadlockVictimAbortsAndBothTransactionsLeaveNoPartialEffect() throws Exception {
        try (Connection first = independent();
                Connection second = independent();
                Connection observer = independent();
                var pool = Executors.newSingleThreadExecutor()) {
            first.setAutoCommit(false);
            second.setAutoCommit(false);
            sql(first, "SET LOCAL statement_timeout='4s'");
            sql(second, "SET LOCAL statement_timeout='4s'");
            sql(first, "UPDATE " + table("identity") + " SET value=11");
            sql(second, "UPDATE " + table("party") + " SET value=22");
            int secondPid = scalar(second, "SELECT pg_backend_pid()");
            Future<String> wait =
                    pool.submit(
                            () -> {
                                try {
                                    sql(second, "UPDATE " + table("identity") + " SET value=22");
                                    return "OK";
                                } catch (SQLException e) {
                                    second.rollback();
                                    return e.getSQLState();
                                }
                            });
            long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(2);
            while (scalar(observer, "SELECT cardinality(pg_blocking_pids(" + secondPid + "))")
                    == 0) {
                if (System.nanoTime() > deadline) throw new AssertionError("writer never blocked");
            }
            String firstState = "OK";
            try {
                sql(first, "UPDATE " + table("party") + " SET value=11");
            } catch (SQLException e) {
                firstState = e.getSQLState();
            }
            first.rollback();
            String secondState = wait.get(5, TimeUnit.SECONDS);
            second.rollback();
            assertThat(List.of(firstState, secondState)).contains("40P01");
        }
        assertThat(jdbc.queryForObject("SELECT value FROM " + table("identity"), Integer.class))
                .isZero();
        assertThat(jdbc.queryForObject("SELECT value FROM " + table("party"), Integer.class))
                .isZero();
        rolledBack();
    }

    @Test
    void inclusiveLowerAndExclusiveUpperWindowsHaveExactDatabaseEquality() {
        owner(
                a -> {
                    fence(a);
                    jdbc.update(
                            "UPDATE "
                                    + table("windows")
                                    + " SET starts=?,ends=? WHERE family='rule'",
                            Timestamp.from(a.sample),
                            Timestamp.from(a.sample.plusSeconds(1)));
                    assertThat(
                                    jdbc.queryForObject(
                                            "SELECT starts<=? AND ends>? FROM "
                                                    + table("windows")
                                                    + " WHERE family='rule'",
                                            Boolean.class,
                                            Timestamp.from(a.sample),
                                            Timestamp.from(a.sample)))
                            .isTrue();
                    assertThat(
                                    jdbc.queryForObject(
                                            "SELECT starts<=? AND ends>? FROM "
                                                    + table("windows")
                                                    + " WHERE family='rule'",
                                            Boolean.class,
                                            Timestamp.from(a.sample.plusSeconds(1)),
                                            Timestamp.from(a.sample.plusSeconds(1))))
                            .isFalse();
                    decisions(a);
                    guard(a);
                    return null;
                });
    }

    @Test
    void declaredStatementTimeoutOutageAbortsEveryProvisionalEffect() {
        assertThatThrownBy(
                        () ->
                                owner(
                                        a -> {
                                            fence(a);
                                            decisions(a);
                                            work();
                                            assertThat(
                                                            jdbc.queryForObject(
                                                                    "SELECT"
                                                                        + " current_setting('lock_timeout')",
                                                                    String.class))
                                                    .isEqualTo("250ms");
                                            assertThat(
                                                            jdbc.queryForObject(
                                                                    "SELECT"
                                                                        + " current_setting('statement_timeout')",
                                                                    String.class))
                                                    .isEqualTo("750ms");
                                            assertThat(
                                                            jdbc.queryForObject(
                                                                    "SELECT"
                                                                        + " current_setting('idle_in_transaction_session_timeout')",
                                                                    String.class))
                                                    .isEqualTo("1s");
                                            // Explicit timeout fault injection, never a sleep used
                                            // to establish race ordering.
                                            jdbc.execute("SELECT pg_sleep(2)");
                                            guard(a);
                                            return null;
                                        }))
                .isInstanceOf(RuntimeException.class);
        rolledBack();
    }
}
