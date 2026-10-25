/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAuthorityFencePostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-17
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Proves complete Identity relation fencing, normal-writer exclusion and rollback through PostgreSQL Flyway and JPA.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

import static org.assertj.core.api.Assertions.*;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.port.out.LoginSessionRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.LoginSession;
import dz.sh.hidra.modules.identity.domain.model.OperationAuthenticationBinding;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityAuthorityFence;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityTemporalEventCollector.Candidate;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserJpaEntity;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;

import jakarta.persistence.EntityManager;

import org.flywaydb.core.Flyway;
import org.hibernate.Session;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;

import java.nio.file.*;
import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.regex.*;

/** Diagnostic candidates and zero-tag SQL fixtures establish no producer origin or PERMIT. */
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(
        classes = HidraApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class IdentityAuthorityFencePostgresIntegrationTest {
    @Container
    static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16")
                    .withCommand("postgres", "-c", "track_commit_timestamp=on");

    @Autowired JdbcTemplate jdbc;
    @Autowired EntityManager em;
    @Autowired Flyway flyway;
    @Autowired JdbcIdentityAuthorityFence fence;
    @Autowired ProtectedTransactionExecutor executor;
    @Autowired PlatformTransactionManager manager;
    @Autowired LoginSessionRepositoryPort sessions;
    private final Deque<Cleanup> cleanup = new ArrayDeque<>();
    private final AtomicInteger receiptIds = new AtomicInteger();
    private OperationAuthenticationBinding binding;
    private String schema;

    private record Cleanup(String table, String id) {}

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        r.add("spring.datasource.username", POSTGRES::getUsername);
        r.add("spring.datasource.password", POSTGRES::getPassword);
        r.add("spring.flyway.enabled", () -> "true");
        r.add("spring.flyway.validate-on-migrate", () -> "true");
        r.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    }

    @BeforeEach
    void warmAndSeed() {
        flyway.validate();
        assertThat(flyway.info().pending()).isEmpty();
        schema = "hpr051e_" + id().replace("-", "");
        jdbc.execute("CREATE SCHEMA " + schema);
        jdbc.execute("CREATE TABLE " + receipt() + "(id integer PRIMARY KEY)");
        binding = fixture("LOCAL", 600);
        TransactionTemplate warm = new TransactionTemplate(manager);
        warm.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        Candidate c = warm.execute(status -> fence.collect(binding.bindingId()));
        assertThat(c).isNotNull();
        assertThat(c.temporalInputs()).isPositive().isLessThanOrEqualTo(1024);
        assertThat(
                        executor.execute(
                                () -> "warm",
                                em::flush,
                                () -> jdbc.queryForObject("SELECT 1", Integer.class)))
                .isEqualTo("warm");
    }

    @AfterEach
    void cleanupFixtureRows() {
        // Delete only this test's ordinary grant/policy/attribute fixtures in FK order.
        // Retain all guarded User/Provider/Credential/Link/Session/Binding identities.
        for (Cleanup c : cleanup)
            jdbc.update("DELETE FROM " + table(c.table()) + " WHERE id=?", c.id());
        if (schema != null) jdbc.execute("DROP SCHEMA " + schema + " CASCADE");
    }

    private String receipt() {
        return schema + ".receipt";
    }

    private Candidate run(String id) {
        AtomicReference<Candidate> captured = new AtomicReference<>();
        return executor.execute(
                () -> {
                    var c = fence.collect(id);
                    captured.set(c);
                    return c;
                },
                em::flush,
                () -> guard(captured.get()));
    }

    private void guard(Candidate c) {
        assertThat(c).isNotNull();
        Integer inserted =
                jdbc.queryForObject(
                        "WITH observed AS MATERIALIZED (SELECT clock_timestamp() AS t),"
                                + " made AS (INSERT INTO "
                                + receipt()
                                + " SELECT ? FROM observed WHERE t>=? AND t<? RETURNING id)"
                                + " SELECT count(*) FROM made",
                        Integer.class,
                        receiptIds.incrementAndGet(),
                        Timestamp.from(c.sampledAt()),
                        Timestamp.from(c.earliestFuture()));
        assertThat(inserted).isEqualTo(1);
    }

    private void rejected(Runnable action) {
        assertThatThrownBy(action::run)
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .satisfies(
                        e ->
                                assertThat(
                                                ((ProtectedTransactionExecutor.ExecutionFailure) e)
                                                        .outcome())
                                        .isEqualTo(
                                                ProtectedTransactionExecutor.Outcome
                                                        .REJECTED_BEFORE_COMMIT));
    }

    @SuppressWarnings("unchecked")
    private List<String> relations() {
        return (List<String>)
                ReflectionTestUtils.getField(JdbcIdentityAuthorityFence.class, "RELATIONS");
    }

    private List<String> held(int pid) {
        return jdbc
                .queryForList(
                        "SELECT n.nspname||'.'||c.relname FROM pg_locks l JOIN pg_class c ON"
                                + " c.oid=l.relation JOIN pg_namespace n ON n.oid=c.relnamespace WHERE"
                                + " l.pid=? AND l.locktype='relation' AND l.granted AND"
                                + " l.mode='ShareRowExclusiveLock' AND n.nspname='public' AND c.relname"
                                + " LIKE 'hidra_identity_%'",
                        String.class, pid)
                .stream()
                .sorted()
                .toList();
    }

    private void assertReleased(int pid) {
        assertThat(held(pid)).isEmpty();
    }

    private void verifySameBackend(Candidate c) {
        assertThat(jdbc.queryForObject("SELECT pg_backend_pid()", Integer.class))
                .isEqualTo(c.backendPid());
        assertThat(jdbc.queryForObject("SELECT txid_current()", Long.class))
                .isEqualTo(c.transactionId());
        em.unwrap(Session.class)
                .doWork(
                        connection -> {
                            assertThat(scalar(connection, "SELECT pg_backend_pid()"))
                                    .isEqualTo(c.backendPid());
                            assertThat(scalar(connection, "SELECT txid_current()"))
                                    .isEqualTo(c.transactionId());
                        });
    }

    @Test
    void claimTailLocksPrecedeSampleAndBlockIndependentCompanionWriters() throws Exception {
        var binding = fixture("LOCAL", 300);
        AtomicReference<Candidate> sampled = new AtomicReference<>();
        executor.execute(
                () -> {
                    var c = fence.collect(binding.bindingId());
                    sampled.set(c);
                    assertThat(
                                    jdbc.queryForObject(
                                            """
                            SELECT count(*) FROM pg_locks
                            WHERE pid=pg_backend_pid() AND granted AND locktype='relation'
                              AND ((relation='public.hidra_identity_external_mapping_generation'::regclass
                                    AND mode='AccessShareLock')
                                OR (relation='public.hidra_identity_bound_source_claim_evidence'::regclass
                                    AND mode='ShareLock'))
                            """,
                                            Integer.class))
                            .isEqualTo(2);
                    verifySameBackend(c);
                    try (var opposing = connection()) {
                        opposing.setAutoCommit(false);
                        try (var statement = opposing.createStatement()) {
                            statement.execute("SET lock_timeout='150ms'");
                            assertThatThrownBy(
                                            () ->
                                                    statement.execute(
                                                            "LOCK TABLE public.hidra_identity_bound_source_claim_evidence"
                                                                    + " IN ROW EXCLUSIVE MODE"))
                                    .isInstanceOf(SQLException.class)
                                    .satisfies(
                                            failure ->
                                                    assertThat(
                                                                    ((SQLException) failure)
                                                                            .getSQLState())
                                                            .isEqualTo("55P03"));
                        } finally {
                            opposing.rollback();
                        }
                    } catch (SQLException failure) {
                        throw new AssertionError(
                                "Independent physical fence proof failed", failure);
                    }
                    return c;
                },
                em::flush,
                () -> guard(sampled.get()));
        try (var after = connection()) {
            after.setAutoCommit(false);
            try (var statement = after.createStatement()) {
                statement.execute("SET lock_timeout='1000ms'");
                statement.execute(
                        "LOCK TABLE public.hidra_identity_bound_source_claim_evidence"
                                + " IN ROW EXCLUSIVE MODE");
            } finally {
                after.rollback();
            }
        }
    }

    @Test
    void claimCompanionInsertAndTruncateWaitForReaderRollbackThenKeepImmutableGuards()
            throws Exception {
        var transaction = new TransactionTemplate(manager);
        transaction.executeWithoutResult(
                status -> {
                    var c = fence.collect(binding.bindingId());
                    for (String sql :
                            List.of(
                                    "INSERT INTO public.hidra_identity_bound_source_claim_evidence"
                                            + "(binding_id,evidence_format,mapping_generation,issuance_transaction_id,coverage_payload,producer_authenticator)"
                                            + " VALUES ('"
                                            + binding.bindingId()
                                            + "',1,1,1,decode('00','hex'),decode(repeat('00',32),'hex'))",
                                    "TRUNCATE public.hidra_identity_bound_source_claim_evidence")) {
                        try (var opposing = connection()) {
                            opposing.setAutoCommit(false);
                            try (var statement = opposing.createStatement()) {
                                statement.execute("SET LOCAL lock_timeout='100ms'");
                                assertThat(scalar(opposing, "SELECT pg_backend_pid()"))
                                        .isNotEqualTo(c.backendPid());
                                assertThatThrownBy(() -> statement.execute(sql))
                                        .isInstanceOf(SQLException.class)
                                        .satisfies(
                                                e ->
                                                        assertThat(((SQLException) e).getSQLState())
                                                                .isEqualTo("55P03"));
                            } finally {
                                opposing.rollback();
                            }
                        } catch (SQLException failure) {
                            throw new AssertionError(failure);
                        }
                    }
                    status.setRollbackOnly();
                });
        try (var after = connection()) {
            after.setAutoCommit(false);
            try (var statement = after.createStatement()) {
                statement.execute("SET LOCAL lock_timeout='1000ms'");
                assertThatThrownBy(
                                () ->
                                        statement.execute(
                                                "TRUNCATE"
                                                        + " public.hidra_identity_bound_source_claim_evidence"))
                        .isInstanceOf(SQLException.class)
                        .satisfies(
                                e ->
                                        assertThat(((SQLException) e).getSQLState())
                                                .isEqualTo("23514"));
            } finally {
                after.rollback();
            }
        }
    }

    @Test
    void freshFlywayJpaAndCompleteDependencyInventoryMatch() throws Exception {
        flyway.validate();
        assertThat(flyway.info().pending()).isEmpty();
        var files =
                Arrays.stream(
                                new PathMatchingResourcePatternResolver()
                                        .getResources("classpath*:db/migration/V*__*.sql"))
                        .map(Resource::getFilename)
                        .toList();
        assertThat(files).doesNotHaveDuplicates();
        assertThat(
                        jdbc.queryForList(
                                "SELECT script FROM flyway_schema_history WHERE success AND"
                                        + " type='SQL'",
                                String.class))
                .containsExactlyInAnyOrderElementsOf(files);
        assertThat(
                        jdbc.queryForObject(
                                "SELECT count(*) FROM flyway_schema_history WHERE NOT success",
                                Integer.class))
                .isZero();
        Path base =
                Path.of("src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence");
        String evidence =
                Files.readString(base.resolve("adapter/JpaAuthorizationEvidenceAdapter.java"));
        Set<String> entities = matches(evidence, "\\b(\\w+JpaEntity)\\b");
        assertThat(entities).hasSize(19);
        Set<String> union = new TreeSet<>();
        for (String entity : entities)
            union.addAll(
                    matches(
                            Files.readString(base.resolve("entity/" + entity + ".java")),
                            "@Table\\(name\\s*=\\s*\"(hidra_identity_\\w+)\""));
        for (String file :
                List.of(
                        "JdbcIdentityTemporalEventCollector.java",
                        "JdbcAuthenticationSecurityStateQueryAdapter.java",
                        "JdbcOperationAuthenticationBindingRepositoryAdapter.java"))
            union.addAll(
                    matches(
                            Files.readString(base.resolve("adapter/" + file)),
                            "public\\.(hidra_identity_\\w+)"));
        assertThat(union).hasSize(22);
        assertThat(relations())
                .containsExactlyElementsOf(
                        union.stream().map(t -> "public." + t).sorted().toList());
        for (String relation : relations())
            assertThat(
                            jdbc.queryForObject(
                                    "SELECT relkind::text FROM pg_class WHERE oid=?::regclass",
                                    String.class,
                                    relation))
                    .isEqualTo("r");
        Candidate c = run(binding.bindingId());
        assertThat(c.earliestFuture()).isAfter(c.sampledAt());
    }

    @Test
    void allIdentityFencesPrecedeTemporalSampleOnOneBackend() throws Exception {
        String role = role();
        AtomicInteger waiting = new AtomicInteger();
        AtomicReference<Instant> committed = new AtomicReference<>();
        CountDownLatch enter = new CountDownLatch(1), ready = new CountDownLatch(1);
        try (var holder = connection();
                var observer = connection();
                var pool = Executors.newSingleThreadExecutor()) {
            holder.setAutoCommit(false);
            sql(
                    holder,
                    "UPDATE "
                            + table("role")
                            + " SET name_en='after-fenced-writer' WHERE id='"
                            + role
                            + "'");
            int holding = pid(holder);
            long xid = scalar(holder, "SELECT txid_current()");
            Future<?> release =
                    pool.submit(
                            () -> {
                                ready.countDown();
                                await(enter);
                                observeWait(observer, waiting.get(), holding);
                                try {
                                    holder.commit();
                                    try (var p =
                                            holder.prepareStatement(
                                                    "SELECT"
                                                            + " pg_xact_commit_timestamp(?::text::xid)")) {
                                        p.setString(1, Long.toString(xid));
                                        try (var r = p.executeQuery()) {
                                            assertThat(r.next()).isTrue();
                                            committed.set(r.getTimestamp(1).toInstant());
                                        }
                                    }
                                } catch (SQLException e) {
                                    throw new AssertionError(e);
                                }
                            });
            await(ready);
            AtomicReference<Candidate> saved = new AtomicReference<>();
            Candidate c =
                    executor.execute(
                            () -> {
                                waiting.set(
                                        jdbc.queryForObject(
                                                "SELECT pg_backend_pid()", Integer.class));
                                enter.countDown();
                                Candidate actual = fence.collect(binding.bindingId());
                                saved.set(actual);
                                verifySameBackend(actual);
                                assertThat(
                                                jdbc.queryForObject(
                                                        "SELECT name_en FROM "
                                                                + table("role")
                                                                + " WHERE id=?",
                                                        String.class,
                                                        role))
                                        .isEqualTo("after-fenced-writer");
                                return actual;
                            },
                            em::flush,
                            () -> guard(saved.get()));
            joined(release);
            assertThat(committed.get()).isNotNull();
            assertThat(c.sampledAt()).isAfterOrEqualTo(committed.get());
            assertReleased(c.backendPid());
        }
    }

    @Test
    void allTwentyTwoRelationsHoldReviewedModes() {
        AtomicReference<Candidate> saved = new AtomicReference<>();
        Candidate c =
                executor.execute(
                        () -> {
                            Candidate actual = fence.collect(binding.bindingId());
                            saved.set(actual);
                            verifySameBackend(actual);
                            assertThat(held(actual.backendPid()))
                                    .containsExactlyElementsOf(relations());
                            return actual;
                        },
                        em::flush,
                        () -> guard(saved.get()));
        assertReleased(c.backendPid());
    }

    @Test
    void addedStaticRelationsBlockNativeDmlUntilRelease() throws Exception {
        Map<String, Target> targets = staticTargets();
        assertThat(targets).hasSize(11);
        String old =
                Files.readString(
                        Path.of(
                                "src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JdbcIdentityTemporalEventCollector.java"));
        Set<String> additions = new TreeSet<>(relations());
        additions.removeAll(matches(old, "(public\\.hidra_identity_\\w+)"));
        assertThat(
                        targets.keySet().stream()
                                .map(IdentityAuthorityFencePostgresIntegrationTest::table)
                                .sorted()
                                .toList())
                .containsExactlyElementsOf(additions);
        for (var entry : targets.entrySet()) {
            String name = entry.getKey();
            Target target = entry.getValue();
            String update =
                    "UPDATE "
                            + table(name)
                            + " SET "
                            + target.column()
                            + "='after-static-fence' WHERE id='"
                            + target.id()
                            + "'";
            try (Writer writer = new Writer(c -> sql(c, update))) {
                AtomicReference<Candidate> saved = new AtomicReference<>();
                Candidate c =
                        executor.execute(
                                () -> {
                                    Candidate actual = fence.collect(binding.bindingId());
                                    saved.set(actual);
                                    writer.start();
                                    observeWait(writer.pid, actual.backendPid());
                                    assertThat(writer.result()).as(name).isEqualTo("55P03");
                                    assertThat(
                                                    jdbc.queryForObject(
                                                            "SELECT "
                                                                    + target.column()
                                                                    + " FROM "
                                                                    + table(name)
                                                                    + " WHERE id=?",
                                                            String.class,
                                                            target.id()))
                                            .isNotEqualTo("after-static-fence");
                                    return actual;
                                },
                                em::flush,
                                () -> guard(saved.get()));
                assertReleased(c.backendPid());
                writer.retryAndCommit();
            }
            assertThat(
                            jdbc.queryForObject(
                                    "SELECT "
                                            + target.column()
                                            + " FROM "
                                            + table(name)
                                            + " WHERE id=?",
                                    String.class,
                                    target.id()))
                    .isEqualTo("after-static-fence");
        }
    }

    @Test
    void nativeBindingAndSessionWritersAbortSafely() throws Exception {
        var unbound = fixture("LOCAL", 600, false);
        try (Writer nativeWriter = new Writer(c -> insertBinding(c, unbound))) {
            blockAndRelease(nativeWriter);
            assertThat(bindingCount(unbound)).isZero();
            nativeWriter.retryAndCommit();
        }
        assertThat(bindingCount(unbound)).isEqualTo(1);
        Instant seen = dbNow();
        CountDownLatch start = new CountDownLatch(1), ready = new CountDownLatch(1);
        AtomicInteger writerPid = new AtomicInteger();
        try (var pool = Executors.newSingleThreadExecutor()) {
            Future<String> jpa =
                    pool.submit(
                            () -> {
                                try {
                                    tx().executeWithoutResult(
                                                    status -> {
                                                        jdbc.execute(
                                                                "SET LOCAL lock_timeout='250ms'");
                                                        jdbc.execute(
                                                                "SET LOCAL"
                                                                        + " statement_timeout='750ms'");
                                                        writerPid.set(
                                                                jdbc.queryForObject(
                                                                        "SELECT pg_backend_pid()",
                                                                        Integer.class));
                                                        ready.countDown();
                                                        await(start);
                                                        updateSession(unbound.sessionId(), seen);
                                                    });
                                    return "committed";
                                } catch (RuntimeException e) {
                                    return sqlState(e);
                                }
                            });
            await(ready);
            AtomicReference<Candidate> saved = new AtomicReference<>();
            executor.execute(
                    () -> {
                        Candidate c = fence.collect(binding.bindingId());
                        saved.set(c);
                        start.countDown();
                        observeWait(writerPid.get(), c.backendPid());
                        assertThat(joined(jpa)).isEqualTo("55P03");
                        return c;
                    },
                    em::flush,
                    () -> guard(saved.get()));
        }
        assertThat(
                        jdbc.queryForObject(
                                "SELECT last_seen_at FROM "
                                        + table("login_session")
                                        + " WHERE id=?",
                                Timestamp.class,
                                unbound.sessionId()))
                .isNull();
        tx().executeWithoutResult(s -> updateSession(unbound.sessionId(), seen));
        assertThat(
                        jdbc.queryForObject(
                                        "SELECT last_seen_at FROM "
                                                + table("login_session")
                                                + " WHERE id=?",
                                        Timestamp.class,
                                        unbound.sessionId())
                                .toInstant())
                .isEqualTo(seen);
        assertThat(bindingCount(unbound)).isEqualTo(1);
    }

    @Test
    void nonOwnerDmlPrivilegesCannotBypassFences() throws Exception {
        String role = "hpr051e_" + id().replace("-", "");
        String permission = permission();
        jdbc.execute(
                "CREATE ROLE "
                        + role
                        + " NOLOGIN NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT NOREPLICATION"
                        + " NOBYPASSRLS");
        jdbc.execute("GRANT USAGE ON SCHEMA public TO " + role);
        jdbc.execute("GRANT SELECT,UPDATE ON " + table("permission") + " TO " + role);
        try {
            try (var probe = connection()) {
                sql(probe, "SET ROLE " + role);
                assertThat(text(probe, "SELECT current_user")).isEqualTo(role);
                assertThat(
                                scalar(
                                        probe,
                                        "SELECT count(*) FROM pg_roles WHERE rolname=current_user"
                                                + " AND NOT rolsuper AND NOT rolcreaterole AND NOT"
                                                + " rolcreatedb AND NOT rolreplication AND NOT"
                                                + " rolbypassrls AND NOT rolinherit AND NOT"
                                                + " rolcanlogin"))
                        .isEqualTo(1);
                assertThat(
                                scalar(
                                        probe,
                                        "SELECT CASE WHEN pg_has_role(current_user,(SELECT relowner"
                                                + " FROM pg_class WHERE oid='"
                                                + table("permission")
                                                + "'::regclass),'MEMBER') THEN 1 ELSE 0 END"))
                        .isZero();
                assertThat(
                                scalar(
                                        probe,
                                        "SELECT CASE WHEN has_table_privilege(current_user,'"
                                                + table("permission")
                                                + "','SELECT') AND"
                                                + " has_table_privilege(current_user,'"
                                                + table("permission")
                                                + "','UPDATE') AND NOT"
                                                + " has_table_privilege(current_user,'"
                                                + table("permission")
                                                + "','TRUNCATE') AND NOT"
                                                + " has_schema_privilege(current_user,'public','CREATE')"
                                                + " THEN 1 ELSE 0 END"))
                        .isEqualTo(1);
                assertThatThrownBy(
                                () ->
                                        sql(
                                                probe,
                                                "ALTER TABLE "
                                                        + table("permission")
                                                        + " DISABLE TRIGGER ALL"))
                        .isInstanceOfSatisfying(
                                SQLException.class,
                                e -> assertThat(e.getSQLState()).isEqualTo("42501"));
                assertThatThrownBy(() -> sql(probe, "SET session_replication_role='replica'"))
                        .isInstanceOfSatisfying(
                                SQLException.class,
                                e -> assertThat(e.getSQLState()).isEqualTo("42501"));
            }
            try (Writer writer =
                    new Writer(
                            c -> {
                                assertThat(text(c, "SELECT current_user")).isEqualTo(role);
                                sql(
                                        c,
                                        "UPDATE "
                                                + table("permission")
                                                + " SET action='WRITE' WHERE id='"
                                                + permission
                                                + "'");
                            },
                            role)) {
                blockAndRelease(writer);
                writer.retryAndCommit();
            }
            assertThat(
                            jdbc.queryForObject(
                                    "SELECT action FROM " + table("permission") + " WHERE id=?",
                                    String.class,
                                    permission))
                    .isEqualTo("WRITE");
        } finally {
            jdbc.execute("REVOKE SELECT,UPDATE ON " + table("permission") + " FROM " + role);
            jdbc.execute("REVOKE USAGE ON SCHEMA public FROM " + role);
            jdbc.execute("DROP ROLE " + role);
        }
    }

    @Test
    void lateFenceFailureRollsBackPrefixAndProvisionalJpaWrite() throws Exception {
        AtomicBoolean delivered = new AtomicBoolean(), guarded = new AtomicBoolean();
        AtomicInteger waiting = new AtomicInteger(), attempts = new AtomicInteger();
        CountDownLatch enter = new CountDownLatch(1), ready = new CountDownLatch(1);
        try (var holder = connection();
                var observer = connection();
                var pool = Executors.newSingleThreadExecutor()) {
            holder.setAutoCommit(false);
            sql(holder, "LOCK TABLE " + table("user_role_grant") + " IN ROW EXCLUSIVE MODE");
            int holding = pid(holder);
            Future<?> proof =
                    pool.submit(
                            () -> {
                                ready.countDown();
                                await(enter);
                                observeWait(observer, waiting.get(), holding);
                                assertThat(
                                                scalarUnchecked(
                                                        observer,
                                                        "SELECT count(*) FROM pg_locks WHERE pid="
                                                                + waiting.get()
                                                                + " AND mode='ShareRowExclusiveLock'"
                                                                + " AND granted AND relation='"
                                                                + table("attribute_definition")
                                                                + "'::regclass"))
                                        .isEqualTo(1);
                            });
            await(ready);
            try {
                rejected(
                        () -> {
                            executor.execute(
                                    () -> {
                                        attempts.incrementAndGet();
                                        var user = em.find(UserJpaEntity.class, binding.userId());
                                        ReflectionTestUtils.setField(
                                                user,
                                                "displayName",
                                                "provisional complete-fence failure");
                                        em.flush();
                                        jdbc.update("INSERT INTO " + receipt() + " VALUES(99)");
                                        waiting.set(
                                                jdbc.queryForObject(
                                                        "SELECT pg_backend_pid()", Integer.class));
                                        enter.countDown();
                                        try {
                                            fence.collect(binding.bindingId());
                                            fail("Blocked final relation unexpectedly acquired");
                                        } catch (RuntimeException e) {
                                            assertThat(sqlState(e)).isEqualTo("55P03");
                                            assertThatThrownBy(
                                                            () ->
                                                                    jdbc.queryForObject(
                                                                            "SELECT 1",
                                                                            Integer.class))
                                                    .isInstanceOf(RuntimeException.class);
                                        }
                                        return "must not be delivered";
                                    },
                                    em::flush,
                                    () -> {
                                        guarded.set(true);
                                        jdbc.queryForObject("SELECT 1", Integer.class);
                                    });
                            delivered.set(true);
                        });
                joined(proof);
            } finally {
                holder.rollback();
            }
        }
        assertThat(attempts).hasValue(1);
        assertThat(delivered).isFalse();
        assertThat(guarded).isFalse();
        assertReleased(waiting.get());
        assertThat(
                        jdbc.queryForObject(
                                "SELECT display_name FROM " + table("user") + " WHERE id=?",
                                String.class,
                                binding.userId()))
                .isNull();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM " + receipt(), Integer.class))
                .isZero();
        assertThat(run(binding.bindingId())).isNotNull();
    }

    @Test
    void missingReadOnlyAndWrongIsolationReject() {
        assertThatThrownBy(() -> fence.collect(binding.bindingId()))
                .isInstanceOf(
                        org.springframework.transaction.IllegalTransactionStateException.class);
        TransactionTemplate readOnly = tx();
        readOnly.setReadOnly(true);
        assertThatThrownBy(() -> readOnly.execute(s -> fence.collect(binding.bindingId())))
                .isInstanceOf(RuntimeException.class)
                .satisfies(
                        e ->
                                assertThat(rootCause(e))
                                        .isInstanceOf(IllegalStateException.class)
                                        .hasMessage("Writable participating transaction required"));
        for (int isolation :
                List.of(
                        TransactionDefinition.ISOLATION_REPEATABLE_READ,
                        TransactionDefinition.ISOLATION_SERIALIZABLE)) {
            TransactionTemplate wrong = tx();
            wrong.setIsolationLevel(isolation);
            assertThatThrownBy(() -> wrong.execute(s -> fence.collect(binding.bindingId())))
                    .isInstanceOf(RuntimeException.class)
                    .satisfies(
                            e ->
                                    assertThat(rootCause(e))
                                            .isInstanceOf(IllegalStateException.class)
                                            .hasMessage("Incompatible physical transaction"));
        }
        for (String bad :
                Arrays.asList(null, "", "AAAAAAAA-AAAA-4AAA-8AAA-AAAAAAAAAAAA", "not-an-id"))
            assertThatThrownBy(() -> run(bad))
                    .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                    .hasRootCauseMessage("Exact binding identity required");
        assertThatThrownBy(() -> run(id()))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .hasRootCauseMessage("Missing temporal binding");
        assertThat(run(binding.bindingId())).isNotNull();
    }

    @Test
    void temporalSyntaxAndOverflowFailuresRemainFailClosed() {
        String role = role(), permission = permission(), grant = id();
        seed(
                "role_permission_grant",
                grant,
                "id,role_id,permission_id,effect,condition_expression,valid_from,valid_to,status,created_at",
                "?,?,?,'DENY','{\"op\":\"exists\",\"attribute\":\"context.evaluatedAt\"}'::jsonb,clock_timestamp()-interval"
                        + " '1 hour',clock_timestamp()+interval '1 hour','SUSPENDED',clock_timestamp()",
                grant,
                role,
                permission);
        assertThatThrownBy(() -> run(binding.bindingId()))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .hasRootCauseMessage("Unsupported protected expression");
        assertThat(jdbc.queryForObject("SELECT count(*) FROM " + receipt(), Integer.class))
                .isZero();
        jdbc.update(
                "UPDATE "
                        + table("role_permission_grant")
                        + " SET condition_expression=NULL WHERE id=?",
                grant);
        String prefix = id();
        AtomicBoolean guarded = new AtomicBoolean();
        assertThatThrownBy(
                        () ->
                                executor.execute(
                                        () -> {
                                            jdbc.update(
                                                    "INSERT INTO "
                                                            + table("user_permission_grant")
                                                            + "(id,user_id,permission_id,effect,grant_reason,emergency_access,valid_from,valid_to,status,created_at)"
                                                            + " SELECT ?||'-'||g,?,?,'DENY','valid"
                                                            + " overflow"
                                                            + " fixture',false,clock_timestamp(),clock_timestamp()+interval"
                                                            + " '1 hour','REVOKED',clock_timestamp()"
                                                            + " FROM generate_series(1,1025) AS g",
                                                    prefix,
                                                    binding.userId(),
                                                    permission);
                                            assertThat(
                                                            jdbc.queryForObject(
                                                                    "SELECT count(*) FROM "
                                                                            + table(
                                                                                    "user_permission_grant")
                                                                            + " WHERE id LIKE ?",
                                                                    Integer.class,
                                                                    prefix + "%"))
                                                    .isEqualTo(1025);
                                            jdbc.update("INSERT INTO " + receipt() + " VALUES(99)");
                                            return fence.collect(binding.bindingId());
                                        },
                                        em::flush,
                                        () -> {
                                            guarded.set(true);
                                            jdbc.queryForObject("SELECT 1", Integer.class);
                                        }))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .hasRootCauseInstanceOf(IllegalStateException.class)
                .hasRootCauseMessage("Temporal census exceeds reviewed bound")
                .satisfies(
                        e ->
                                assertThat(
                                                ((ProtectedTransactionExecutor.ExecutionFailure) e)
                                                        .outcome())
                                        .isEqualTo(
                                                ProtectedTransactionExecutor.Outcome
                                                        .REJECTED_BEFORE_COMMIT));
        assertThat(guarded).isFalse();
        assertThat(
                        jdbc.queryForObject(
                                "SELECT count(*) FROM "
                                        + table("user_permission_grant")
                                        + " WHERE id LIKE ?",
                                Integer.class,
                                prefix + "%"))
                .isZero();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM " + receipt(), Integer.class))
                .isZero();
        assertThat(run(binding.bindingId())).isNotNull();
    }

    @Test
    void materializedCandidateContainsNoAuthorityOrSecrets() {
        AtomicBoolean delivered = new AtomicBoolean(), committed = new AtomicBoolean();
        AtomicReference<Candidate> saved = new AtomicReference<>();
        Candidate c =
                executor.execute(
                        () -> {
                            Candidate actual = fence.collect(binding.bindingId());
                            saved.set(actual);
                            TransactionSynchronizationManager.registerSynchronization(
                                    new TransactionSynchronization() {
                                        @Override
                                        public void afterCommit() {
                                            assertThat(delivered).isFalse();
                                            committed.set(true);
                                        }
                                    });
                            return actual;
                        },
                        em::flush,
                        () -> guard(saved.get()));
        delivered.set(true);
        assertThat(committed).isTrue();
        assertThat(c).isSameAs(saved.get());
        assertThat(c.getClass().getRecordComponents())
                .extracting(java.lang.reflect.RecordComponent::getName)
                .containsExactly(
                        "sampledAt",
                        "earliestFuture",
                        "backendPid",
                        "transactionId",
                        "contributors",
                        "referencedAttributes",
                        "temporalInputs",
                        "expressionRows");
        assertThatThrownBy(() -> c.contributors().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> c.referencedAttributes().add("secret"))
                .isInstanceOf(UnsupportedOperationException.class);
        assertThat(c.toString())
                .doesNotContain(
                        "password",
                        "producerAuthenticator",
                        "secret_reference",
                        "PERMIT",
                        "ELIGIBLE");
        assertThat(binding.producerAuthenticator()).containsOnly((byte) 0);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM " + receipt(), Integer.class))
                .isEqualTo(1);
        assertReleased(c.backendPid());
    }

    private record Target(String id, String column) {}

    private Map<String, Target> staticTargets() {
        Map<String, Target> rows = new TreeMap<>();
        String role = role(),
                group = group(),
                permission = permission(),
                definition = attributeDefinition();
        String policy = id();
        seed(
                "authorization_policy",
                policy,
                "id,code,name,policy_domain,status,created_at,updated_at",
                "?,?,'before','TOPOLOGY','DISABLED',clock_timestamp(),clock_timestamp()",
                policy,
                policy);
        OperationAuthenticationBinding oidc = fixture("OIDC", 600);
        String groupMap = id();
        seed(
                "external_group_mapping",
                groupMap,
                "id,identity_provider_id,group_id,external_group_name,claim_name,mapping_mode,auto_create_membership,status,created_at,updated_at",
                "?,?,?,'before','groups','ASSERTION_ONLY',false,'ACTIVE',clock_timestamp(),clock_timestamp()",
                groupMap,
                oidc.providerId(),
                group);
        String permissionMap = id();
        seed(
                "external_permission_mapping",
                permissionMap,
                "id,identity_provider_id,permission_id,external_permission_code,claim_name,mapping_mode,effect,status,created_at,updated_at",
                "?,?,?,'before','permissions','DIRECT_GRANT','DENY','ACTIVE',clock_timestamp(),clock_timestamp()",
                permissionMap,
                oidc.providerId(),
                permission);
        String roleMap = id();
        seed(
                "external_role_mapping",
                roleMap,
                "id,identity_provider_id,role_id,external_role_code,claim_name,mapping_mode,status,created_at,updated_at",
                "?,?,?,'before','roles','DIRECT_GRANT','ACTIVE',clock_timestamp(),clock_timestamp()",
                roleMap,
                oidc.providerId(),
                role);
        rows.put("attribute_definition", new Target(definition, "name"));
        rows.put("authorization_policy", new Target(policy, "name"));
        rows.put("external_group_mapping", new Target(groupMap, "external_group_name"));
        rows.put(
                "external_identity",
                new Target(oidc.externalIdentityId(), "external_display_name"));
        rows.put(
                "external_permission_mapping",
                new Target(permissionMap, "external_permission_code"));
        rows.put("external_role_mapping", new Target(roleMap, "external_role_code"));
        rows.put("group", new Target(group, "name_en"));
        rows.put("local_credential", new Target(binding.credentialId(), "password_hash"));
        rows.put("permission", new Target(permission, "action"));
        rows.put("provider", new Target(binding.providerId(), "name"));
        rows.put("role", new Target(role, "name_en"));
        return rows;
    }

    private void blockAndRelease(Writer writer) {
        AtomicReference<Candidate> saved = new AtomicReference<>();
        Candidate c =
                executor.execute(
                        () -> {
                            var actual = fence.collect(binding.bindingId());
                            saved.set(actual);
                            writer.start();
                            observeWait(writer.pid, actual.backendPid());
                            assertThat(writer.result()).isEqualTo("55P03");
                            return actual;
                        },
                        em::flush,
                        () -> guard(saved.get()));
        assertReleased(c.backendPid());
    }

    private TransactionTemplate tx() {
        var tx = new TransactionTemplate(manager);
        tx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        return tx;
    }

    private void updateSession(String sessionId, Instant seen) {
        LoginSession s = sessions.findById(sessionId).orElseThrow();
        sessions.save(
                new LoginSession(
                        s.id(),
                        s.userId(),
                        s.identityProviderId(),
                        s.externalIdentityId(),
                        s.startedAt(),
                        seen,
                        s.expiresAt(),
                        s.clientIp(),
                        s.userAgent(),
                        s.status(),
                        s.correlationId(),
                        s.sessionType(),
                        s.endedAt()));
    }

    private int bindingCount(OperationAuthenticationBinding b) {
        return jdbc.queryForObject(
                "SELECT count(*) FROM " + table("operation_binding") + " WHERE binding_id=?",
                Integer.class,
                b.bindingId());
    }

    private static Set<String> matches(String source, String pattern) {
        Set<String> result = new TreeSet<>();
        Matcher m = Pattern.compile(pattern).matcher(source);
        while (m.find()) result.add(m.group(1));
        return result;
    }

    private static long scalar(Connection c, String query) throws SQLException {
        try (var s = c.createStatement();
                var r = s.executeQuery(query)) {
            assertThat(r.next()).isTrue();
            return r.getLong(1);
        }
    }

    private static long scalarUnchecked(Connection c, String query) {
        try {
            return scalar(c, query);
        } catch (SQLException e) {
            throw new AssertionError(e);
        }
    }

    private static String text(Connection c, String query) throws SQLException {
        try (var s = c.createStatement();
                var r = s.executeQuery(query)) {
            assertThat(r.next()).isTrue();
            return r.getString(1);
        }
    }

    private static void insertBinding(Connection c, OperationAuthenticationBinding b)
            throws SQLException {
        var values = new LinkedHashMap<String, Object>();
        for (var component : OperationAuthenticationBinding.class.getRecordComponents())
            try {
                Object value = component.getAccessor().invoke(b);
                values.put(
                        component.getName().replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase(),
                        value instanceof Instant instant ? Timestamp.from(instant) : value);
            } catch (ReflectiveOperationException e) {
                throw new AssertionError(e);
            }
        String parameters =
                values.keySet().stream()
                        .map(
                                name ->
                                        "?::"
                                                + (name.endsWith("_at")
                                                        ? "timestamptz"
                                                        : name.endsWith("_version")
                                                                        && !name.equals(
                                                                                "format_version")
                                                                ? "bigint"
                                                                : name.equals("format_version")
                                                                        ? "smallint"
                                                                        : name.equals(
                                                                                        "producer_authenticator")
                                                                                ? "bytea"
                                                                                : "text"))
                        .collect(java.util.stream.Collectors.joining(","));
        try (var s =
                c.prepareStatement(
                        "INSERT INTO "
                                + table("operation_binding")
                                + "("
                                + String.join(",", values.keySet())
                                + ") VALUES ("
                                + parameters
                                + ")")) {
            int index = 1;
            for (Object v : values.values()) s.setObject(index++, v);
            s.executeUpdate();
        }
    }

    private static String table(String name) {
        return "public.hidra_identity_" + name;
    }

    private static String id() {
        return UUID.randomUUID().toString();
    }

    private Instant dbNow() {
        return jdbc.queryForObject("SELECT clock_timestamp()", Timestamp.class).toInstant();
    }

    private void seed(String table, String id, String columns, String values, Object... args) {
        jdbc.update(
                "INSERT INTO " + table(table) + "(" + columns + ") VALUES (" + values + ")", args);
        cleanup.addFirst(new Cleanup(table, id));
    }

    private String permission() {
        String id = id();
        seed(
                "permission",
                id,
                "id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at",
                "?,?,'TOPOLOGY','FACILITY','READ',false,'ACTIVE',clock_timestamp(),clock_timestamp()",
                id,
                "topology:facility:read-" + id);
        return id;
    }

    private String role() {
        String id = id();
        seed(
                "role",
                id,
                "id,code,role_type,status,created_at,updated_at",
                "?,?,'BUSINESS','ACTIVE',clock_timestamp(),clock_timestamp()",
                id,
                id);
        return id;
    }

    private String group() {
        String id = id();
        seed(
                "group",
                id,
                "id,code,group_type,status,created_at,updated_at",
                "?,?,'LOCAL','ACTIVE',clock_timestamp(),clock_timestamp()",
                id,
                id);
        return id;
    }

    private String attributeDefinition() {
        String id = id();
        seed(
                "attribute_definition",
                id,
                "id,code,name,attribute_target,data_type,multi_valued,sensitive,status,created_at,updated_at",
                "?,?,'Synthetic','USER','STRING',false,false,'ACTIVE',clock_timestamp(),clock_timestamp()",
                id,
                id);
        return id;
    }

    private String policyVersion(Instant from, Instant to) {
        String policy = id(), version = id();
        seed(
                "authorization_policy",
                policy,
                "id,code,name,policy_domain,status,created_at,updated_at",
                "?,?,'Synthetic','TOPOLOGY','DISABLED',clock_timestamp(),clock_timestamp()",
                policy,
                policy);
        seed(
                "authorization_policy_version",
                version,
                "id,policy_id,version_number,status,effective_from,effective_to,created_at",
                "?,?,1,'DRAFT',?,?,clock_timestamp()",
                version,
                policy,
                Timestamp.from(from),
                Timestamp.from(to));
        return version;
    }

    private OperationAuthenticationBinding fixture(String type, long duration) {
        return fixture(type, duration, true);
    }

    private OperationAuthenticationBinding fixture(String type, long duration, boolean append) {
        if (type.equals("LOCAL"))
            jdbc.update(
                    "UPDATE "
                            + table("provider")
                            + " SET status='INACTIVE' WHERE provider_type='LOCAL' AND"
                            + " status='ACTIVE'");
        String user = id(),
                provider = id(),
                credential = type.equals("LOCAL") ? id() : null,
                external = type.equals("LOCAL") ? null : id();
        Instant issued = dbNow().truncatedTo(ChronoUnit.SECONDS).minusSeconds(1),
                expires = issued.plusSeconds(duration);
        String issuer = type.equals("OIDC") ? "https://" + provider + ".synthetic.invalid" : null,
                subject = external == null ? null : "subject-" + external;
        jdbc.update(
                "INSERT INTO "
                        + table("user")
                        + "(id,username,user_type,status,failed_login_count,created_at,updated_at)"
                        + " VALUES (?,?,'HUMAN','ACTIVE',0,?,?)",
                user,
                user,
                Timestamp.from(issued),
                Timestamp.from(issued));
        jdbc.update(
                "INSERT INTO "
                        + table("provider")
                        + "(id,code,name,provider_type,issuer_uri,status,sync_enabled,just_in_time_provisioning_enabled,created_at,updated_at)"
                        + " VALUES (?,?,'Synthetic',?,?,'ACTIVE',false,false,?,?)",
                provider,
                provider,
                type,
                issuer,
                Timestamp.from(issued),
                Timestamp.from(issued));
        if (credential != null)
            jdbc.update(
                    "INSERT INTO "
                            + table("local_credential")
                            + "(id,user_id,password_hash,credential_status,created_at,updated_at)"
                            + " VALUES (?,?,'synthetic','ACTIVE',?,?)",
                    credential,
                    user,
                    Timestamp.from(issued),
                    Timestamp.from(issued));
        else
            jdbc.update(
                    "INSERT INTO "
                            + table("external_identity")
                            + "(id,user_id,identity_provider_id,external_subject,status,created_at,updated_at)"
                            + " VALUES (?,?,?,?,'LINKED',?,?)",
                    external,
                    user,
                    provider,
                    subject,
                    Timestamp.from(issued),
                    Timestamp.from(issued));
        String session = id();
        jdbc.update(
                "INSERT INTO "
                        + table("login_session")
                        + "(id,user_id,identity_provider_id,external_identity_id,started_at,expires_at,status,session_type)"
                        + " VALUES (?,?,?,?,?,?,'ACTIVE',?)",
                session,
                user,
                provider,
                external,
                Timestamp.from(issued),
                Timestamp.from(expires),
                type);
        OperationAuthenticationBinding b =
                new OperationAuthenticationBinding(
                        id(),
                        (short) 1,
                        "hidra-api",
                        "hidra-api",
                        id(),
                        session,
                        user,
                        type,
                        type,
                        provider,
                        1,
                        external,
                        subject,
                        external == null ? null : 1L,
                        issuer,
                        issuer == null ? null : expires.plusSeconds(1),
                        credential,
                        credential == null ? null : 1L,
                        issued,
                        expires,
                        new byte[32]);
        var values = new LinkedHashMap<String, Object>();
        for (var component : OperationAuthenticationBinding.class.getRecordComponents())
            try {
                String column =
                        component.getName().replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
                Object value = component.getAccessor().invoke(b);
                values.put(
                        column, value instanceof Instant instant ? Timestamp.from(instant) : value);
            } catch (ReflectiveOperationException e) {
                throw new AssertionError(e);
            }
        String parameters =
                values.keySet().stream()
                        .map(
                                c ->
                                        "?::"
                                                + (c.endsWith("_at")
                                                        ? "timestamptz"
                                                        : c.endsWith("_version")
                                                                        && !c.equals(
                                                                                "format_version")
                                                                ? "bigint"
                                                                : c.equals("format_version")
                                                                        ? "smallint"
                                                                        : c.equals(
                                                                                        "producer_authenticator")
                                                                                ? "bytea"
                                                                                : "text"))
                        .collect(java.util.stream.Collectors.joining(","));
        if (append)
            jdbc.update(
                    "INSERT INTO "
                            + table("operation_binding")
                            + "("
                            + String.join(",", values.keySet())
                            + ") VALUES ("
                            + parameters
                            + ")",
                    values.values().toArray());
        return b;
    }

    private Connection connection() throws SQLException {
        Connection c =
                DriverManager.getConnection(
                        POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
        c.setNetworkTimeout(Runnable::run, 1500);
        return c;
    }

    private static void sql(Connection c, String sql) throws SQLException {
        try (var p = c.createStatement()) {
            p.execute(sql);
        }
    }

    private static int pid(Connection c) throws SQLException {
        try (var p = c.createStatement();
                var r = p.executeQuery("SELECT pg_backend_pid()")) {
            r.next();
            return r.getInt(1);
        }
    }

    private void observeWait(int waiting, int holding) {
        long deadline = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(200);
        while (System.nanoTime() < deadline)
            if (Boolean.TRUE.equals(
                    jdbc.queryForObject(
                            "SELECT ? = ANY(pg_blocking_pids(?))",
                            Boolean.class,
                            holding,
                            waiting))) return;
        fail("Actual writer wait was not observed");
    }

    private static void observeWait(Connection c, int waiting, int holding) {
        long deadline = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(200);
        try (var p = c.prepareStatement("SELECT ? = ANY(pg_blocking_pids(?))")) {
            p.setInt(1, holding);
            p.setInt(2, waiting);
            while (System.nanoTime() < deadline)
                try (var r = p.executeQuery()) {
                    r.next();
                    if (r.getBoolean(1)) return;
                }
            fail("Actual collector wait was not observed");
        } catch (SQLException e) {
            throw new AssertionError(e);
        }
    }

    private static Throwable rootCause(Throwable e) {
        while (e.getCause() != null) e = e.getCause();
        return e;
    }

    private static String sqlState(Throwable e) {
        for (Throwable c = e; c != null; c = c.getCause())
            if (c instanceof SQLException sql) return sql.getSQLState();
        return null;
    }

    private static void await(CountDownLatch gate) {
        try {
            assertThat(gate.await(2, TimeUnit.SECONDS)).isTrue();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }
    }

    private static <T> T joined(Future<T> future) {
        try {
            return future.get(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    @FunctionalInterface
    interface Mutation {
        void run(Connection c) throws SQLException;
    }

    private final class Writer implements AutoCloseable {
        final Connection c;
        final int pid;
        final Mutation mutation;
        final ExecutorService pool = Executors.newSingleThreadExecutor();
        final CountDownLatch start = new CountDownLatch(1), ready = new CountDownLatch(1);
        final Future<String> future;

        Writer(Mutation mutation) throws SQLException {
            this(mutation, null);
        }

        Writer(Mutation mutation, String role) throws SQLException {
            this.mutation = mutation;
            c = connection();
            if (role != null) sql(c, "SET ROLE " + role);
            c.setAutoCommit(false);
            pid = pid(c);
            sql(c, "SET LOCAL lock_timeout='250ms'");
            sql(c, "SET LOCAL statement_timeout='750ms'");
            future =
                    pool.submit(
                            () -> {
                                ready.countDown();
                                await(start);
                                try {
                                    mutation.run(c);
                                    c.commit();
                                    return "committed";
                                } catch (SQLException e) {
                                    c.rollback();
                                    return e.getSQLState();
                                }
                            });
            await(ready);
        }

        void start() {
            start.countDown();
        }

        String result() {
            return joined(future);
        }

        void retryAndCommit() throws SQLException {
            assertThat(future.isDone()).isTrue();
            sql(c, "SET LOCAL lock_timeout='250ms'");
            sql(c, "SET LOCAL statement_timeout='750ms'");
            mutation.run(c);
            c.commit();
        }

        public void close() throws SQLException {
            if (start.getCount() != 0) future.cancel(true);
            start.countDown();
            pool.shutdownNow();
            try {
                assertThat(pool.awaitTermination(2, TimeUnit.SECONDS)).isTrue();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new AssertionError(e);
            } finally {
                c.close();
            }
        }
    }

    @Test void integratedFenceRefusesForeignOrCompletedAdmission() {
        var t=new TransactionTemplate(manager);t.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        var a=t.execute(s->fence.acquireNowait(java.util.UUID.randomUUID().toString()));
        assertThatThrownBy(()->t.execute(s->{fence.requireCurrent(a);return null;})).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(()->t.execute(s->{fence.requireCurrent(null);return null;})).isInstanceOf(RuntimeException.class);
    }

    @Test void integratedFenceRejectsLossOfEachRequiredLock() {
        var required=JdbcIdentityAuthorityFence.requiredRelationFences();
        // Direct owner fixture: each savepoint releases exactly one of24 locks,
        // while all other locks, holder, backend, XID and role remain current.
        // No savepoint is introduced into the protected executor's protocol.
        for (var missing:required) {
            var t=new TransactionTemplate(manager);
            t.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
            t.executeWithoutResult(status->{
                for(var relation:required)if(!relation.equals(missing)) {
                    String mode=switch(relation.mode()) {
                        case SHARE_ROW_EXCLUSIVE -> "SHARE ROW EXCLUSIVE";
                        case ACCESS_SHARE -> "ACCESS SHARE";
                        case SHARE -> "SHARE";
                    };
                    jdbc.execute("LOCK TABLE "+relation.relation()+" IN "+mode+" MODE NOWAIT");
                }
                jdbc.execute("SAVEPOINT before_required_lock");
                var admission=fence.acquireNowait(UUID.randomUUID().toString());
                fence.requireCurrent(admission);
                jdbc.execute("ROLLBACK TO SAVEPOINT before_required_lock");
                assertThatThrownBy(()->fence.requireCurrent(admission))
                        .as("Missing retained lock: %s",missing.relation())
                        .isInstanceOf(org.springframework.dao.InvalidDataAccessApiUsageException.class)
                        .hasRootCauseInstanceOf(IllegalStateException.class)
                        .hasRootCauseMessage("Changed physical admission");
                status.setRollbackOnly();
            });
        }
    }

}
