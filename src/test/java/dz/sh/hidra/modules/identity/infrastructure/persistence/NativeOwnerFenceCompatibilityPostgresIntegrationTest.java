/**
 * @Project : HidraAPI @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and
 * Analytics @Author : Abir MEDJERAB @Owner : Sonatrach / TRC : Digitalization Initiative @Name :
 * NativeOwnerFenceCompatibilityPostgresIntegrationTest @CreatedOn : 2025-06-26 @UpdatedOn :
 * 2026-10-14 @Type : Class @Layer : Identity Test @Module : identity @Package :
 * dz.sh.hidra.modules.identity.infrastructure.persistence @Description : Proves bounded real
 * Identity native writer and protected relation fence compatibility without granting business
 * authority.
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.port.out.LoginSessionRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.OperationAuthenticationBinding;
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
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;

import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

/** Real SQL-trigger fixtures, NOT private producer-origin or permission proofs. */
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(
        classes = HidraApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class NativeOwnerFenceCompatibilityPostgresIntegrationTest {
    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16");

    private static final String B = "public.hidra_identity_operation_binding";
    private static final String S = "public.hidra_identity_login_session";
    private static final String U = "public.hidra_identity_user";
    private static final String P = "public.hidra_identity_provider";
    private static final String E = "public.hidra_identity_external_identity";
    private static final String C = "public.hidra_identity_local_credential";
    private static final String G = "public.hidra_identity_user_permission_grant";
    @Autowired JdbcTemplate jdbc;
    @Autowired Flyway flyway;
    @Autowired EntityManager em;
    @Autowired ProtectedTransactionExecutor executor;
    @Autowired LoginSessionRepositoryPort sessions;
    String schema;

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
    void warmLifecycleAndCreateReceiptFixture() {
        schema = "hpr051_native_" + id().replace("-", "");
        jdbc.execute("CREATE SCHEMA " + schema);
        jdbc.execute("CREATE TABLE " + receipt() + "(id integer PRIMARY KEY)");
        // Initialize provider/JDBC paths outside the unchanged 500ms protected-work budget.
        assertThat(
                        executor.execute(
                                () -> "warm",
                                em::flush,
                                () -> jdbc.queryForObject("SELECT 1", Integer.class)))
                .isEqualTo("warm");
    }

    @AfterEach
    void cleanup() {
        // Immutable security identities stay retained in this disposable container.
        // Never TRUNCATE/DELETE guarded bindings, providers, credentials or links.
        jdbc.execute("DROP SCHEMA " + schema + " CASCADE");
    }

    String receipt() {
        return schema + ".receipt";
    }

    void guard() {
        jdbc.queryForObject(
                "WITH made AS (INSERT INTO "
                        + receipt()
                        + " VALUES(1) RETURNING id) SELECT id FROM made",
                Integer.class);
    }

    void fence(String table) {
        jdbc.execute("LOCK TABLE " + table + " IN SHARE ROW EXCLUSIVE MODE");
    }

    @Test
    void freshFlywayAndNativeTriggerLockOrderAreReal() throws Exception {
        flyway.validate();
        assertThat(flyway.info().pending()).isEmpty();
        var inventory =
                Arrays.stream(
                                new PathMatchingResourcePatternResolver()
                                        .getResources("classpath*:db/migration/V*__*.sql"))
                        .map(Resource::getFilename)
                        .toList();
        assertThat(inventory).doesNotHaveDuplicates();
        assertThat(
                        jdbc.queryForList(
                                "SELECT script FROM flyway_schema_history WHERE success AND"
                                        + " type='SQL'",
                                String.class))
                .containsExactlyInAnyOrderElementsOf(inventory);
        assertThat(
                        jdbc.queryForObject(
                                "SELECT count(*) FROM flyway_schema_history WHERE NOT success",
                                Integer.class))
                .isZero();
        String definition =
                jdbc.queryForObject(
                        "SELECT"
                            + " pg_get_functiondef('public.hidra_identity_operation_binding_guard()'::regprocedure)",
                        String.class);
        int provider = definition.indexOf("SELECT * INTO p");
        int credential = definition.indexOf("SELECT * INTO c");
        int link = definition.indexOf("SELECT * INTO e");
        int user = definition.indexOf("SELECT * INTO u");
        int session = definition.indexOf("SELECT * INTO s");
        assertThat(provider).isGreaterThanOrEqualTo(0);
        assertThat(credential).isGreaterThan(provider);
        assertThat(link).isGreaterThan(provider);
        assertThat(user).isGreaterThan(Math.max(credential, link));
        assertThat(session).isGreaterThan(user);
        assertThat(definition).contains("FOR UPDATE");
        assertThat(
                        jdbc.queryForObject(
                                "SELECT count(*) FROM pg_trigger WHERE tgrelid=?::regclass AND"
                                    + " tgname='trg_p25_binding_insert' AND NOT tgisinternal AND"
                                    + " tgenabled='O'",
                                Integer.class,
                                B))
                .isEqualTo(1);
        var b = fixture("LOCAL");
        assertThat(
                        executor.execute(
                                () -> {
                                    assertThat(sessions.findById(b.sessionId())).isPresent();
                                    int pid =
                                            jdbc.queryForObject(
                                                    "SELECT pg_backend_pid()", Integer.class);
                                    long xid =
                                            jdbc.queryForObject(
                                                    "SELECT txid_current()", Long.class);
                                    assertThat(
                                                    jdbc.queryForObject(
                                                            "SELECT"
                                                                + " current_setting('transaction_isolation')",
                                                            String.class))
                                            .isEqualTo("read committed");
                                    em.unwrap(Session.class)
                                            .doWork(
                                                    c -> {
                                                        assertThat(
                                                                        scalar(
                                                                                c,
                                                                                "SELECT"
                                                                                    + " pg_backend_pid()"))
                                                                .isEqualTo(pid);
                                                        assertThat(
                                                                        scalar(
                                                                                c,
                                                                                "SELECT"
                                                                                    + " txid_current()"))
                                                                .isEqualTo(xid);
                                                    });
                                    return "same physical transaction";
                                },
                                em::flush,
                                this::guard))
                .isEqualTo("same physical transaction");
    }

    @Test
    void uncontendedNativeBindingWriterCommits() throws Exception {
        var b = fixture("LOCAL");
        try (var c = connection()) {
            c.setAutoCommit(false);
            nativeInsert(c, b, Map.of());
            c.commit();
        }
        assertThat(count(b)).isEqualTo(1);
        assertThat(
                        jdbc.queryForObject(
                                "SELECT producer_authenticator FROM " + B + " WHERE binding_id=?",
                                byte[].class,
                                b.bindingId()))
                .containsOnly((byte) 0);
        // The zero tag proves only a structurally valid native SQL-trigger tuple.
        try (var c = connection()) {
            c.setAutoCommit(false);
            nativeInsert(c, b, Map.of());
            c.commit();
        }
        assertThat(count(b)).isEqualTo(1);
    }

    @Test
    void relationFenceBlocksPhantomPermissionWriter() throws Exception {
        var b = fixture("LOCAL");
        String permission = id(), grant = id();
        jdbc.update(
                "INSERT INTO"
                    + " public.hidra_identity_permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)"
                    + " VALUES"
                    + " (?,?,'TOPOLOGY','FACILITY','READ',false,'ACTIVE',clock_timestamp(),clock_timestamp())",
                permission,
                permission);
        String insert =
                "INSERT INTO "
                        + G
                        + "(id,user_id,permission_id,effect,grant_reason,emergency_access,valid_from,valid_to,status,created_at)"
                        + " VALUES ('"
                        + grant
                        + "','"
                        + b.userId()
                        + "','"
                        + permission
                        + "','GRANT','synthetic fence"
                        + " proof',false,clock_timestamp(),clock_timestamp()+interval '1"
                        + " hour','ACTIVE',clock_timestamp())";
        try (var writer = new Writer(c -> sql(c, insert))) {
            executor.execute(
                    () -> {
                        fence(G);
                        int holder = jdbc.queryForObject("SELECT pg_backend_pid()", Integer.class);
                        writer.start();
                        observeWait(writer.pid, holder);
                        assertThat(writer.result()).isEqualTo("55P03");
                        assertThat(
                                        jdbc.queryForObject(
                                                "SELECT count(*) FROM " + G + " WHERE id=?",
                                                Integer.class,
                                                grant))
                                .isZero();
                        return "phantom blocked";
                    },
                    em::flush,
                    this::guard);
            writer.retryAndCommit();
        }
        assertThat(
                        jdbc.queryForObject(
                                "SELECT count(*) FROM " + G + " WHERE id=?", Integer.class, grant))
                .isEqualTo(1);
    }

    @Test
    void nativeBindingTriggerAndRelationFenceAbortSafely() throws Exception {
        var b = fixture("LOCAL");
        try (var writer = new Writer(c -> nativeInsert(c, b, Map.of()));
                var probe = connection()) {
            probe.setAutoCommit(false);
            executor.execute(
                    () -> {
                        // RowShare from trigger FOR UPDATE is compatible with these coarse fences.
                        // The real writable JPA session port supplies the actual row-lock
                        // collision.
                        fence(P + "," + C + "," + U + "," + S);
                        assertThat(sessions.findById(b.sessionId())).isPresent();
                        int holder = jdbc.queryForObject("SELECT pg_backend_pid()", Integer.class);
                        writer.start();
                        observeWait(writer.pid, holder);
                        // Observe that the native trigger reached/holds the preceding owner row.
                        assertThatThrownBy(
                                        () ->
                                                sql(
                                                        probe,
                                                        "SELECT id FROM "
                                                                + P
                                                                + " WHERE id='"
                                                                + b.providerId()
                                                                + "' FOR UPDATE NOWAIT"))
                                .isInstanceOfSatisfying(
                                        SQLException.class,
                                        e -> assertThat(e.getSQLState()).isEqualTo("55P03"));
                        rollback(probe);
                        assertThat(writer.result()).isEqualTo("55P03");
                        assertThat(count(b)).isZero();
                        return "bounded native abort";
                    },
                    em::flush,
                    this::guard);
            writer.retryAndCommit();
        }
        assertThat(count(b)).isEqualTo(1);
        assertThat(jdbc.queryForObject("SELECT count(*) FROM " + receipt(), Integer.class))
                .isEqualTo(1);
    }

    @Test
    void generationRevocationWaitsOrFailsClosed() throws Exception {
        for (String branch : List.of("provider", "credential", "link")) {
            var b = fixture(branch.equals("link") ? "OIDC" : "LOCAL");
            String table = branch.equals("provider") ? P : branch.equals("credential") ? C : E;
            String key =
                    branch.equals("provider")
                            ? b.providerId()
                            : branch.equals("credential")
                                    ? b.credentialId()
                                    : b.externalIdentityId();
            String change =
                    branch.equals("provider")
                            ? "status='INACTIVE'"
                            : branch.equals("credential")
                                    ? "password_hash='rotated-synthetic-hash'"
                                    : "status='UNLINKED'";
            long before =
                    jdbc.queryForObject(
                            "SELECT security_version FROM " + table + " WHERE id=?",
                            Long.class,
                            key);
            try (var writer =
                    new Writer(
                            c ->
                                    sql(
                                            c,
                                            "UPDATE "
                                                    + table
                                                    + " SET "
                                                    + change
                                                    + " WHERE id='"
                                                    + key
                                                    + "'"))) {
                // Each independent attempt retains the original executor time limits.
                executor.execute(
                        () -> {
                            fence(table);
                            int holder =
                                    jdbc.queryForObject("SELECT pg_backend_pid()", Integer.class);
                            writer.start();
                            observeWait(writer.pid, holder);
                            assertThat(writer.result()).isEqualTo("55P03");
                            assertThat(
                                            jdbc.queryForObject(
                                                    "SELECT security_version FROM "
                                                            + table
                                                            + " WHERE id=?",
                                                    Long.class,
                                                    key))
                                    .isEqualTo(before);
                            return "generation fenced";
                        },
                        em::flush,
                        () -> jdbc.queryForObject("SELECT 1", Integer.class));
                writer.retryAndCommit();
            }
            assertThat(
                            jdbc.queryForObject(
                                    "SELECT security_version FROM " + table + " WHERE id=?",
                                    Long.class,
                                    key))
                    .isEqualTo(before + 1);
            assertThatThrownBy(() -> nativeInsert(jdbc, b, Map.of()))
                    .isInstanceOfSatisfying(
                            DataAccessException.class,
                            e -> assertThat(sqlState(e)).isEqualTo("23514"));
            assertThat(count(b)).isZero();
            assertThat(
                            jdbc.queryForObject(
                                    "SELECT count(*) FROM "
                                            + (branch.equals("link") ? E : C)
                                            + " WHERE id=?",
                                    Integer.class,
                                    branch.equals("link")
                                            ? b.externalIdentityId()
                                            : b.credentialId()))
                    .isEqualTo(1);
        }
    }

    @Test
    void lockAbortLeavesNoPartialProtectedWrite() throws Exception {
        var b = fixture("LOCAL");
        AtomicBoolean delivered = new AtomicBoolean(), guarded = new AtomicBoolean();
        AtomicInteger attempts = new AtomicInteger();
        // Holder is opened and acquires the real credential row before protected admission.
        try (var holder = connection()) {
            holder.setAutoCommit(false);
            sql(holder, "SELECT id FROM " + C + " WHERE id='" + b.credentialId() + "' FOR UPDATE");
            int holding = pid(holder);
            CountDownLatch enteringNative = new CountDownLatch(1), observed = new CountDownLatch(1);
            AtomicInteger protectedPid = new AtomicInteger();
            try (var observerConnection = connection();
                    var pool = Executors.newSingleThreadExecutor()) {
                Future<?> observer =
                        pool.submit(
                                () -> {
                                    await(enteringNative);
                                    observeWait(observerConnection, protectedPid.get(), holding);
                                    observed.countDown();
                                });
                assertThatThrownBy(
                                () -> {
                                    executor.execute(
                                            () -> {
                                                attempts.incrementAndGet();
                                                fence(U);
                                                var entity =
                                                        em.find(UserJpaEntity.class, b.userId());
                                                ReflectionTestUtils.setField(
                                                        entity,
                                                        "displayName",
                                                        "provisional native collision");
                                                em.flush();
                                                jdbc.update(
                                                        "INSERT INTO " + receipt() + " VALUES(99)");
                                                protectedPid.set(
                                                        jdbc.queryForObject(
                                                                "SELECT pg_backend_pid()",
                                                                Integer.class));
                                                enteringNative.countDown();
                                                try {
                                                    nativeInsert(jdbc, b, Map.of());
                                                } catch (DataAccessException e) {
                                                    assertThat(sqlState(e)).isEqualTo("55P03");
                                                    // Attempted recovery inside the
                                                    // poisoned/aborted transaction must fail.
                                                    assertThatThrownBy(
                                                                    () ->
                                                                            jdbc.queryForObject(
                                                                                    "SELECT 1",
                                                                                    Integer.class))
                                                            .isInstanceOf(RuntimeException.class);
                                                }
                                                return "must not escape";
                                            },
                                            em::flush,
                                            () -> {
                                                guarded.set(true);
                                                guard();
                                            });
                                    delivered.set(true);
                                })
                        .isInstanceOfSatisfying(
                                ProtectedTransactionExecutor.ExecutionFailure.class,
                                e ->
                                        assertThat(e.outcome())
                                                .isEqualTo(
                                                        ProtectedTransactionExecutor.Outcome
                                                                .REJECTED_BEFORE_COMMIT));
                joined(observer);
                assertThat(observed.getCount()).isZero();
            } finally {
                holder.rollback();
            }
        }
        assertThat(attempts).hasValue(1);
        assertThat(delivered).isFalse();
        assertThat(guarded).isFalse();
        assertThat(count(b)).isZero();
        assertThat(
                        jdbc.queryForObject(
                                "SELECT display_name FROM " + U + " WHERE id=?",
                                String.class,
                                b.userId()))
                .isNull();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM " + receipt(), Integer.class))
                .isZero();
        // Positive control after release: same valid native tuple can now commit once.
        try (var c = connection()) {
            c.setAutoCommit(false);
            nativeInsert(c, b, Map.of());
            c.commit();
        }
        assertThat(count(b)).isEqualTo(1);
    }

    @FunctionalInterface
    interface Mutation {
        void run(Connection c) throws SQLException;
    }

    final class Writer implements AutoCloseable {
        final Connection c;
        final int pid;
        final Mutation mutation;
        final CountDownLatch gate = new CountDownLatch(1);
        final CountDownLatch ready = new CountDownLatch(1);
        final ExecutorService pool = Executors.newSingleThreadExecutor();
        final Future<String> future;

        Writer(Mutation mutation) throws SQLException {
            this.mutation = mutation;
            c = connection();
            c.setAutoCommit(false);
            pid = pid(c);
            sql(c, "SET LOCAL lock_timeout='250ms'");
            sql(c, "SET LOCAL statement_timeout='750ms'");
            future =
                    pool.submit(
                            () -> {
                                ready.countDown();
                                await(gate);
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
            gate.countDown();
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

        @Override
        public void close() throws SQLException {
            if (gate.getCount() != 0) future.cancel(true);
            gate.countDown();
            pool.shutdownNow();
            try {
                if (!pool.awaitTermination(2, TimeUnit.SECONDS)) {
                    c.close();
                    assertThat(pool.awaitTermination(2, TimeUnit.SECONDS))
                            .as("writer terminated after closing socket")
                            .isTrue();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new AssertionError(e);
            } finally {
                c.close();
            }
        }
    }

    static void await(CountDownLatch latch) {
        try {
            assertThat(latch.await(2, TimeUnit.SECONDS)).as("bounded writer gate").isTrue();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }
    }

    static <T> T joined(Future<T> f) {
        try {
            return f.get(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new AssertionError("bounded independent transaction completion", e);
        }
    }

    static void sql(Connection c, String query) throws SQLException {
        try (var s = c.createStatement()) {
            s.execute(query);
        }
    }

    static long scalar(Connection c, String query) throws SQLException {
        try (var s = c.createStatement();
                var r = s.executeQuery(query)) {
            assertThat(r.next()).isTrue();
            return r.getLong(1);
        }
    }

    static void rollback(Connection c) {
        try {
            c.rollback();
        } catch (SQLException e) {
            throw new AssertionError(e);
        }
    }

    static String sqlState(Throwable failure) {
        for (Throwable e = failure; e != null; e = e.getCause())
            if (e instanceof SQLException sql) return sql.getSQLState();
        throw new AssertionError("Expected real database SQLSTATE", failure);
    }

    static void observeWait(Connection observer, int waiting, int holding) {
        assertThat(waiting).isNotEqualTo(holding);
        long deadline = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(200);
        try (var s = observer.prepareStatement("SELECT ? = ANY(pg_blocking_pids(?))")) {
            s.setInt(1, holding);
            s.setInt(2, waiting);
            while (System.nanoTime() < deadline)
                try (var r = s.executeQuery()) {
                    assertThat(r.next()).isTrue();
                    if (r.getBoolean(1)) return;
                }
        } catch (SQLException e) {
            throw new AssertionError(e);
        }
        fail("Required real independent-backend wait not observed within bounded lock interval");
    }

    private OperationAuthenticationBinding fixture(String type) {
        // Fresh synthetic owners obey the existing singleton active-provider indexes.
        // Retire earlier fixtures through ordinary guarded SQL; never delete bindings or disable
        // constraints. Directory uniqueness covers LDAP and ACTIVE_DIRECTORY together.
        if (type.equals("LOCAL"))
            jdbc.update(
                    "UPDATE "
                            + P
                            + " SET status='INACTIVE' WHERE status='ACTIVE' AND"
                            + " provider_type='LOCAL'");
        else if (type.equals("LDAP") || type.equals("ACTIVE_DIRECTORY"))
            jdbc.update(
                    "UPDATE "
                            + P
                            + " SET status='INACTIVE' WHERE status='ACTIVE' AND provider_type IN"
                            + " ('LDAP','ACTIVE_DIRECTORY')");
        String user = id(),
                provider = id(),
                external = type.equals("LOCAL") ? null : id(),
                credential = type.equals("LOCAL") ? id() : null;
        Instant issued = Instant.now().truncatedTo(ChronoUnit.SECONDS).minusSeconds(1),
                expires = issued.plusSeconds(600);
        String subject = external == null ? null : "  exacté-" + external + "  ",
                issuer =
                        type.equals("OIDC")
                                ? " https://" + provider + ".synthetic.invalid/ "
                                : null;
        jdbc.update(
                "INSERT INTO "
                        + U
                        + "(id,username,user_type,status,failed_login_count,created_at,updated_at)"
                        + " VALUES (?,?,'HUMAN','ACTIVE',0,?,?)",
                user,
                user,
                Timestamp.from(issued),
                Timestamp.from(issued));
        jdbc.update(
                "INSERT INTO "
                        + P
                        + "(id,code,name,provider_type,issuer_uri,status,sync_enabled,just_in_time_provisioning_enabled,created_at,updated_at)"
                        + " VALUES (?,?,'Synthetic provider',?,?,'ACTIVE',false,false,?,?)",
                provider,
                provider,
                type,
                issuer,
                Timestamp.from(issued),
                Timestamp.from(issued));
        if (credential != null)
            jdbc.update(
                    "INSERT INTO "
                            + C
                            + "(id,user_id,password_hash,credential_status,created_at,updated_at)"
                            + " VALUES (?,?,'synthetic-hash','ACTIVE',?,?)",
                    credential,
                    user,
                    Timestamp.from(issued),
                    Timestamp.from(issued));
        else
            jdbc.update(
                    "INSERT INTO "
                            + E
                            + "(id,user_id,identity_provider_id,external_subject,status,created_at,updated_at)"
                            + " VALUES (?,?,?,?,'LINKED',?,?)",
                    external,
                    user,
                    provider,
                    subject,
                    Timestamp.from(issued),
                    Timestamp.from(issued));
        String session = id(), protocol = type.equals("ACTIVE_DIRECTORY") ? "LDAP" : type;
        jdbc.update(
                "INSERT INTO "
                        + S
                        + "(id,user_id,identity_provider_id,external_identity_id,started_at,expires_at,status,session_type,client_ip,user_agent,correlation_id)"
                        + " VALUES"
                        + " (?,?,?,?,?,?,'ACTIVE',?,'synthetic-ip','synthetic-agent','synthetic-correlation')",
                session,
                user,
                provider,
                external,
                Timestamp.from(issued),
                Timestamp.from(expires),
                protocol);
        return new OperationAuthenticationBinding(
                id(),
                (short) 1,
                "hidra-api",
                "hidra-api",
                id(),
                session,
                user,
                type,
                protocol,
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
    }

    private static LinkedHashMap<String, Object> values(OperationAuthenticationBinding b) {
        var values = new LinkedHashMap<String, Object>();
        for (var component : OperationAuthenticationBinding.class.getRecordComponents())
            try {
                String name =
                        component.getName().replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
                Object value = component.getAccessor().invoke(b);
                values.put(
                        name, value instanceof Instant instant ? Timestamp.from(instant) : value);
            } catch (ReflectiveOperationException failure) {
                throw new AssertionError(failure);
            }
        return values;
    }

    private static String parameters(Map<String, Object> values) {
        return values.keySet().stream()
                .map(
                        c ->
                                "?::"
                                        + (c.endsWith("_at")
                                                ? "timestamptz"
                                                : c.endsWith("_version")
                                                                && !c.equals("format_version")
                                                        ? "bigint"
                                                        : c.equals("format_version")
                                                                ? "smallint"
                                                                : c.equals("producer_authenticator")
                                                                        ? "bytea"
                                                                        : "text"))
                .collect(java.util.stream.Collectors.joining(","));
    }

    private static String insertion(Map<String, Object> values) {
        return "INSERT INTO "
                + B
                + " ("
                + String.join(",", values.keySet())
                + ") VALUES ("
                + parameters(values)
                + ")";
    }

    private void nativeInsert(
            JdbcTemplate target, OperationAuthenticationBinding b, Map<String, Object> changes) {
        var v = values(b);
        v.putAll(changes);
        target.update(insertion(v), v.values().toArray());
    }

    private static void nativeInsert(
            Connection target, OperationAuthenticationBinding b, Map<String, Object> changes)
            throws SQLException {
        var v = values(b);
        v.putAll(changes);
        try (var statement = target.prepareStatement(insertion(v))) {
            int i = 1;
            for (Object value : v.values()) statement.setObject(i++, value);
            statement.executeUpdate();
        }
    }

    private int count(OperationAuthenticationBinding b) {
        return jdbc.queryForObject(
                "SELECT count(*) FROM " + B + " WHERE binding_id=?", Integer.class, b.bindingId());
    }

    private Connection connection() throws SQLException {
        var c =
                DriverManager.getConnection(
                        POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
        c.setNetworkTimeout(Runnable::run, 1500);
        return c;
    }

    private static int pid(Connection c) throws SQLException {
        try (var q = c.createStatement();
                var r = q.executeQuery("SELECT pg_backend_pid()")) {
            assertTrue(r.next());
            return r.getInt(1);
        }
    }

    private void observeWait(int waiting, int holding) {
        assertNotEquals(waiting, holding);
        long deadline = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(200);
        while (System.nanoTime() < deadline)
            if (Boolean.TRUE.equals(
                    jdbc.queryForObject(
                            "SELECT ? = ANY(pg_blocking_pids(?))",
                            Boolean.class,
                            holding,
                            waiting))) return;
        fail("Required independent-backend lock wait was not observed");
    }

    private static String id() {
        return UUID.randomUUID().toString();
    }
}
