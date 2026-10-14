/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationAuthenticationBindingPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Exercises fresh Flyway binding storage, restrictive tuple guards and observed native/JPA lock races in real PostgreSQL16.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.port.out.AuthenticationSecurityStateQueryPort;
import dz.sh.hidra.modules.identity.application.port.out.LoginSessionRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.OperationAuthenticationBindingRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.LoginSession;
import dz.sh.hidra.modules.identity.domain.model.OperationAuthenticationBinding;
import dz.sh.hidra.modules.identity.domain.value.LoginSessionStatus;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.LoginSessionJpaEntity;

import jakarta.persistence.EntityManager;

import org.flywaydb.core.Flyway;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.postgresql.PGConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(
        classes = HidraApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class OperationAuthenticationBindingPostgresIntegrationTest {
    @Container
    static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("hidra_test")
                    .withCommand(
                            "postgres",
                            "-c",
                            "log_error_verbosity=terse",
                            "-c",
                            "log_min_error_statement=panic",
                            "-c",
                            "log_parameter_max_length_on_error=0");

    private static final String B = "public.hidra_identity_operation_binding";
    private static final String S = "public.hidra_identity_login_session";
    private static final String U = "public.hidra_identity_user";
    private static final String P = "public.hidra_identity_provider";
    private static final String E = "public.hidra_identity_external_identity";
    private static final String C = "public.hidra_identity_local_credential";
    @Autowired JdbcTemplate jdbc;
    @Autowired OperationAuthenticationBindingRepositoryPort bindings;
    @Autowired AuthenticationSecurityStateQueryPort states;
    @Autowired LoginSessionRepositoryPort sessions;
    @Autowired EntityManager em;
    @Autowired PlatformTransactionManager manager;
    @Autowired Flyway flyway;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        r.add("spring.datasource.username", POSTGRES::getUsername);
        r.add("spring.datasource.password", POSTGRES::getPassword);
        r.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        r.add("spring.flyway.enabled", () -> "true");
        r.add("spring.flyway.validate-on-migrate", () -> "true");
    }

    @Test
    void freshChainUsesActualCurrentInventoryWithNoPendingOrFailedMigration() throws Exception {
        flyway.validate();
        assertEquals(0, flyway.info().pending().length);
        var files =
                java.util.Arrays.stream(
                                new PathMatchingResourcePatternResolver()
                                        .getResources("classpath*:db/migration/V*__*.sql"))
                        .map(Resource::getFilename)
                        .toList();
        assertThat(files)
                .doesNotHaveDuplicates()
                .contains("V20261013_001__p25_identity_operation_binding.sql");
        assertThat(
                        jdbc.queryForList(
                                "SELECT script FROM flyway_schema_history WHERE success AND"
                                    + " type='SQL'",
                                String.class))
                .containsExactlyInAnyOrderElementsOf(files);
        assertEquals(
                0,
                jdbc.queryForObject(
                        "SELECT count(*) FROM flyway_schema_history WHERE NOT success",
                        Integer.class));
    }

    @Test
    void physicalCatalogHasCompleteColumnsRestrictiveFksAndInvokerGuards() {
        assertEquals(
                21,
                jdbc.queryForObject(
                        "SELECT count(*) FROM information_schema.columns WHERE"
                            + " table_schema='public' AND"
                            + " table_name='hidra_identity_operation_binding'",
                        Integer.class));
        assertEquals(
                5,
                jdbc.queryForObject(
                        "SELECT count(*) FROM pg_constraint WHERE conrelid=?::regclass AND"
                            + " contype='f' AND NOT condeferrable AND confdeltype='r' AND"
                            + " confupdtype='r' AND convalidated",
                        Integer.class,
                        B));
        assertEquals(
                3,
                jdbc.queryForObject(
                        "SELECT count(*) FROM pg_constraint WHERE conrelid=?::regclass AND contype"
                            + " IN ('p','u')",
                        Integer.class,
                        B));
        for (String table : List.of(B, S))
            assertEquals(
                    4,
                    jdbc.queryForObject(
                            "SELECT count(*) FROM pg_trigger WHERE tgrelid=?::regclass AND tgname"
                                + " LIKE 'trg_p25_%' AND NOT tgisinternal AND tgenabled='O'",
                            Integer.class, table));
        for (String name :
                List.of(
                        "hidra_identity_operation_binding_guard",
                        "hidra_identity_bound_session_guard",
                        "hidra_identity_session_isolation_guard")) {
            var row =
                    jdbc.queryForMap(
                            "SELECT prosecdef,provolatile,proconfig::text AS config FROM pg_proc"
                                + " WHERE oid=?::regprocedure",
                            "public." + name + "()");
            assertEquals(false, row.get("prosecdef"));
            assertEquals("v", row.get("provolatile"));
            assertThat(row.get("config").toString()).contains("search_path=pg_catalog, public");
        }
        assertEquals(
                "bytea",
                jdbc.queryForObject(
                        "SELECT data_type FROM information_schema.columns WHERE"
                            + " table_schema='public' AND"
                            + " table_name='hidra_identity_operation_binding' AND"
                            + " column_name='producer_authenticator'",
                        String.class));
    }

    @Test
    void allFourProvenanceBranchesRoundTripExactSyntheticStructuralTuples() {
        for (String type : List.of("LOCAL", "LDAP", "ACTIVE_DIRECTORY", "OIDC")) {
            var b = fixture(type);
            bindings.append(b);
            assertEquals(
                    b, bindings.findByNativeIssuerAndJti(b.nativeIssuer(), b.jti()).orElseThrow());
            assertTrue(states.current(b, Instant.now()));
        }
        // These zero tags prove storage structure only, never producer authentication or eligible
        // B1.
    }

    @Test
    void exactReplayWorksAfterTerminationAndSecurityGenerationChange() {
        var b = fixture("LOCAL");
        bindings.append(b);
        jdbc.update(
                "UPDATE " + S + " SET status='REVOKED',ended_at=? WHERE id=?",
                Timestamp.from(Instant.now()),
                b.sessionId());
        jdbc.update("UPDATE " + P + " SET status='INACTIVE' WHERE id=?", b.providerId());
        bindings.append(b);
        assertEquals(1, count(b));
        assertFalse(states.current(b, Instant.now()));
    }

    @Test
    void differentTagOrScalarDuplicateCannotRewriteOrReturnFreshEvidence() {
        var b = fixture("LOCAL");
        bindings.append(b);
        for (Map<String, Object> change :
                List.<Map<String, Object>>of(
                        Map.of("producer_authenticator", differentTag()),
                        Map.of("native_audience", "different"))) {
            reject(() -> nativeInsert(jdbc, b, change));
            assertEquals(
                    b, bindings.findByNativeIssuerAndJti(b.nativeIssuer(), b.jti()).orElseThrow());
        }
        assertEquals(1, count(b));
    }

    @Test
    void everyBindingUpdateAndDeleteStatementIsRejectedEvenWithoutMatchingRows() {
        var b = fixture("LOCAL");
        bindings.append(b);
        for (String column : values(b).keySet())
            reject(
                    () ->
                            jdbc.update(
                                    "UPDATE "
                                            + B
                                            + " SET "
                                            + column
                                            + "="
                                            + column
                                            + " WHERE false"));
        reject(() -> jdbc.update("DELETE FROM " + B + " WHERE false"));
        reject(() -> jdbc.execute("TRUNCATE " + B));
        assertEquals(b, bindings.findByNativeIssuerAndJti(b.nativeIssuer(), b.jti()).orElseThrow());
    }

    @Test
    void localNullabilityAndAllRequiredColumnsCannotBypassTupleChecks() {
        for (String column :
                List.of(
                        "binding_id",
                        "format_version",
                        "native_issuer",
                        "native_audience",
                        "jti",
                        "session_id",
                        "user_id",
                        "authentication_type",
                        "session_protocol",
                        "provider_id",
                        "provider_security_version",
                        "credential_id",
                        "credential_security_version",
                        "issued_at",
                        "expires_at",
                        "producer_authenticator")) {
            var b = fixture("LOCAL");
            var change = new LinkedHashMap<String, Object>();
            change.put(column, null);
            reject(() -> nativeInsert(jdbc, b, change));
            assertEquals(0, count(b));
        }
        for (String column :
                List.of(
                        "external_identity_id",
                        "external_subject",
                        "external_security_version",
                        "source_issuer",
                        "source_expires_at")) {
            var b = fixture("LOCAL");
            Object value =
                    column.endsWith("version")
                            ? 1L
                            : column.endsWith("_at") ? Timestamp.from(b.expiresAt()) : id();
            reject(() -> nativeInsert(jdbc, b, Map.of(column, value)));
            assertEquals(0, count(b));
        }
    }

    @Test
    void externalNullabilityAndProtocolBranchesCannotBecomeUnknownCheckResults() {
        for (String type : List.of("LDAP", "ACTIVE_DIRECTORY", "OIDC"))
            for (String column :
                    List.of(
                            "external_identity_id",
                            "external_subject",
                            "external_security_version")) {
                var b = fixture(type);
                var change = new LinkedHashMap<String, Object>();
                change.put(column, null);
                reject(() -> nativeInsert(jdbc, b, change));
                assertEquals(0, count(b));
            }
        for (String column : List.of("source_issuer", "source_expires_at")) {
            var b = fixture("OIDC");
            var change = new LinkedHashMap<String, Object>();
            change.put(column, null);
            reject(() -> nativeInsert(jdbc, b, change));
        }
        for (String type : List.of("LDAP", "OIDC")) {
            var b = fixture(type);
            reject(() -> nativeInsert(jdbc, b, Map.of("credential_id", id())));
            reject(() -> nativeInsert(jdbc, b, Map.of("session_protocol", "LOCAL")));
        }
    }

    @Test
    void nativeByteLimitsCanonicalIdsAndTagLengthsAreEnforced() {
        var b = fixture("LOCAL");
        nativeInsert(
                jdbc,
                b,
                Map.of("native_issuer", "é".repeat(256), "native_audience", "a".repeat(512)));
        assertTrue(bindings.findByNativeIssuerAndJti("é".repeat(256), b.jti()).isPresent());
        for (var change :
                List.<Map<String, Object>>of(
                        Map.of("native_issuer", "é".repeat(257)),
                        Map.of("native_audience", "a".repeat(513)),
                        Map.of("binding_id", id().toUpperCase()),
                        Map.of("jti", "1-1-1-1-1"),
                        Map.of("producer_authenticator", new byte[31]),
                        Map.of("producer_authenticator", new byte[33]),
                        Map.of("native_issuer", " issuer"),
                        Map.of("native_audience", "aud\u2003"),
                        Map.of("format_version", 2))) {
            var next = fixture("LOCAL");
            reject(() -> nativeInsert(jdbc, next, change));
            assertEquals(0, count(next));
        }
    }

    @Test
    void oidcSourceIdentityWhitespaceIsPreservedExactly() {
        var b = fixture("OIDC");
        bindings.append(b);
        var after = bindings.findByNativeIssuerAndJti(b.nativeIssuer(), b.jti()).orElseThrow();
        assertEquals(b.externalSubject(), after.externalSubject());
        assertTrue(after.externalSubject().startsWith("  "));
        assertEquals(b.sourceIssuer(), after.sourceIssuer());
        assertTrue(after.sourceIssuer().startsWith(" "));
    }

    @Test
    void finiteWholeSecondAndSourceCappedIntervalsRejectNativeInfinityAndFractions() {
        for (var change :
                List.<Map<String, Object>>of(
                        Map.of("issued_at", "infinity"),
                        Map.of("expires_at", "infinity"),
                        Map.of("issued_at", Timestamp.from(Instant.now())),
                        Map.of("expires_at", Timestamp.from(Instant.now())))) {
            var b = fixture("LOCAL");
            reject(() -> nativeInsert(jdbc, b, change));
        }
        var oidc = fixture("OIDC");
        reject(() -> nativeInsert(jdbc, oidc, Map.of("source_expires_at", "infinity")));
        reject(
                () ->
                        nativeInsert(
                                jdbc,
                                oidc,
                                Map.of(
                                        "source_expires_at",
                                        Timestamp.from(oidc.expiresAt().minusSeconds(1)))));
    }

    @Test
    void inactiveLockedAndNonhumanOwnersCannotReceiveNewBindings() {
        for (String clause :
                List.of(
                        "status='DISABLED'",
                        "locked_until=clock_timestamp()+interval '1 hour'",
                        "user_type='SYSTEM'")) {
            var b = fixture("LOCAL");
            jdbc.update("UPDATE " + U + " SET " + clause + " WHERE id=?", b.userId());
            reject(() -> nativeInsert(jdbc, b, Map.of()));
        }
        var b = fixture("LOCAL");
        jdbc.update("UPDATE " + P + " SET status='INACTIVE' WHERE id=?", b.providerId());
        reject(() -> nativeInsert(jdbc, b, Map.of()));
    }

    @Test
    void providerLinkAndCredentialAbaCannotRelabelOriginalGenerations() {
        var provider = fixture("LOCAL");
        jdbc.update("UPDATE " + P + " SET status='INACTIVE' WHERE id=?", provider.providerId());
        jdbc.update("UPDATE " + P + " SET status='ACTIVE' WHERE id=?", provider.providerId());
        reject(() -> nativeInsert(jdbc, provider, Map.of()));
        assertFalse(states.current(provider, Instant.now()));
        var credential = fixture("LOCAL");
        jdbc.update(
                "UPDATE " + C + " SET password_hash='changed' WHERE id=?",
                credential.credentialId());
        jdbc.update(
                "UPDATE " + C + " SET password_hash='synthetic-hash' WHERE id=?",
                credential.credentialId());
        reject(() -> nativeInsert(jdbc, credential, Map.of()));
        assertFalse(states.current(credential, Instant.now()));
        var external = fixture("OIDC");
        jdbc.update(
                "UPDATE " + E + " SET status='UNLINKED' WHERE id=?", external.externalIdentityId());
        jdbc.update(
                "UPDATE " + E + " SET status='LINKED' WHERE id=?", external.externalIdentityId());
        reject(() -> nativeInsert(jdbc, external, Map.of()));
        assertFalse(states.current(external, Instant.now()));
    }

    @Test
    void boundSessionAllowsActivityAndAllAcceptedTerminalTransitions() {
        for (String terminal : List.of("EXPIRED", "REVOKED", "LOGGED_OUT")) {
            var b = fixture("LOCAL");
            bindings.append(b);
            jdbc.update(
                    "UPDATE " + S + " SET last_seen_at=? WHERE id=?",
                    Timestamp.from(Instant.now()),
                    b.sessionId());
            jdbc.update(
                    "UPDATE " + S + " SET status=?,ended_at=? WHERE id=?",
                    terminal,
                    Timestamp.from(Instant.now()),
                    b.sessionId());
            jdbc.update(
                    "UPDATE " + S + " SET status=status,ended_at=ended_at WHERE id=?",
                    b.sessionId());
            assertFalse(states.current(b, Instant.now()));
            assertEquals(
                    b, bindings.findByNativeIssuerAndJti(b.nativeIssuer(), b.jti()).orElseThrow());
        }
    }

    @Test
    void everyBoundSessionImmutableFieldCannotChange() {
        var b = fixture("LOCAL");
        bindings.append(b);
        var changes =
                Map.of(
                        "id",
                        id(),
                        "user_id",
                        id(),
                        "identity_provider_id",
                        id(),
                        "external_identity_id",
                        id(),
                        "session_type",
                        "OIDC",
                        "client_ip",
                        "different",
                        "user_agent",
                        "different",
                        "correlation_id",
                        "different",
                        "started_at",
                        Timestamp.from(b.issuedAt().minusSeconds(1)),
                        "expires_at",
                        Timestamp.from(b.expiresAt().plusSeconds(1)));
        for (var change : changes.entrySet())
            reject(
                    () ->
                            jdbc.update(
                                    "UPDATE " + S + " SET " + change.getKey() + "=? WHERE id=?",
                                    change.getValue(),
                                    b.sessionId()));
        assertTrue(states.current(b, Instant.now()));
    }

    @Test
    void terminalResurrectionReplacementEndRewriteAndBoundDeleteReject() {
        var b = fixture("LOCAL");
        bindings.append(b);
        jdbc.update(
                "UPDATE " + S + " SET status='REVOKED',ended_at=? WHERE id=?",
                Timestamp.from(Instant.now()),
                b.sessionId());
        for (String clause :
                List.of(
                        "status='ACTIVE',ended_at=NULL",
                        "status='LOGGED_OUT'",
                        "ended_at=ended_at+interval '1 second'"))
            reject(
                    () ->
                            jdbc.update(
                                    "UPDATE " + S + " SET " + clause + " WHERE id=?",
                                    b.sessionId()));
        reject(() -> jdbc.update("DELETE FROM " + S + " WHERE id=?", b.sessionId()));
        assertEquals(
                "REVOKED",
                jdbc.queryForObject(
                        "SELECT status FROM " + S + " WHERE id=?", String.class, b.sessionId()));
    }

    @Test
    void boundTerminationRequiresFiniteEndAtOrAfterStart() {
        for (String end :
                List.of("NULL", "'infinity'::timestamptz", "started_at-interval '1 second'")) {
            var b = fixture("LOCAL");
            bindings.append(b);
            reject(
                    () ->
                            jdbc.update(
                                    "UPDATE "
                                            + S
                                            + " SET status='REVOKED',ended_at="
                                            + end
                                            + " WHERE id=?",
                                    b.sessionId()));
        }
    }

    @Test
    void freshUnboundRowsRetainOrdinaryMutationAndDeletionBehavior() {
        var b = fixture("LOCAL");
        jdbc.update(
                "UPDATE "
                        + S
                        + " SET user_agent='changed',correlation_id=NULL,last_seen_at=NULL WHERE"
                        + " id=?",
                b.sessionId());
        assertEquals(1, jdbc.update("DELETE FROM " + S + " WHERE id=?", b.sessionId()));
        assertEquals(0, count(b));
    }

    @Test
    void truncateRejectsWithZeroBindingsForRestrictCascadeAndMultitableStatements() {
        var fresh = freshDatabase();
        assertEquals(0, fresh.queryForObject("SELECT count(*) FROM " + B, Integer.class));
        for (String sql :
                List.of(
                        "TRUNCATE " + S,
                        "TRUNCATE " + S + " CASCADE",
                        "TRUNCATE " + S + ", " + B,
                        "TRUNCATE " + B))
            assertThrows(DataAccessException.class, () -> fresh.execute(sql));
        assertEquals(0, fresh.queryForObject("SELECT count(*) FROM " + B, Integer.class));
    }

    @Test
    void truncateRejectsAndPreservesRowsWithBoundSessions() {
        var b = fixture("LOCAL");
        bindings.append(b);
        for (String sql :
                List.of("TRUNCATE " + S, "TRUNCATE " + S + " CASCADE", "TRUNCATE " + S + ", " + B))
            reject(() -> jdbc.execute(sql));
        assertEquals(1, count(b));
        assertTrue(states.current(b, Instant.now()));
    }

    @Test
    void unsupportedIsolationRejectsNativeAppendAndPotentialSessionMutation() throws Exception {
        for (int level :
                List.of(
                        Connection.TRANSACTION_REPEATABLE_READ,
                        Connection.TRANSACTION_SERIALIZABLE)) {
            var b = fixture("LOCAL");
            try (var connection = connection()) {
                connection.setAutoCommit(false);
                connection.setTransactionIsolation(level);
                try (var query = connection.createStatement()) {
                    query.executeQuery("SELECT count(*) FROM " + B).close();
                }
                assertThrows(SQLException.class, () -> nativeInsert(connection, b, Map.of()));
                connection.rollback();
                try (var query = connection.createStatement()) {
                    assertThrows(
                            SQLException.class,
                            () ->
                                    query.executeUpdate(
                                            "UPDATE "
                                                    + S
                                                    + " SET last_seen_at=clock_timestamp() WHERE"
                                                    + " id='"
                                                    + b.sessionId()
                                                    + "'"));
                }
                connection.rollback();
            }
            assertEquals(0, count(b));
        }
    }

    @Test
    void sameTransactionMutationBypassRollsBackItsBindingAppend() {
        var b = fixture("LOCAL");
        assertThrows(
                DataAccessException.class,
                () ->
                        tx().executeWithoutResult(
                                        status -> {
                                            nativeInsert(jdbc, b, Map.of());
                                            jdbc.update(
                                                    "UPDATE "
                                                            + S
                                                            + " SET user_agent='changed' WHERE"
                                                            + " id=?",
                                                    b.sessionId());
                                        }));
        assertEquals(0, count(b));
        assertEquals(
                "synthetic-agent",
                jdbc.queryForObject(
                        "SELECT user_agent FROM " + S + " WHERE id=?",
                        String.class,
                        b.sessionId()));
    }

    @Test
    void failedOwnerTransactionCannotLeaveItsAppendedBinding() {
        var b = fixture("LOCAL");
        assertThrows(
                IllegalStateException.class,
                () ->
                        tx().executeWithoutResult(
                                        status -> {
                                            bindings.append(b);
                                            throw new IllegalStateException("synthetic rollback");
                                        }));
        assertEquals(0, count(b));
    }

    @Test
    void jdbcStorageFailureRedactsPrivateTupleAndLeavesNoCause() {
        var b = fixture("LOCAL");
        jdbc.update("UPDATE " + P + " SET status='INACTIVE' WHERE id=?", b.providerId());
        var failure = assertThrows(IllegalStateException.class, () -> bindings.append(b));
        assertEquals("Authentication storage append failed.", failure.getMessage());
        assertNull(failure.getCause());
        assertFalse(failure.toString().contains(b.userId()));
        assertEquals(0, count(b));
    }

    @Test
    void atomicCredentialAndProviderSqlImagesIgnoreStaleEntityGenerations() {
        var b = fixture("LOCAL");
        var first = states.credential(b.userId());
        assertEquals("synthetic-hash", first.passwordHash());
        assertEquals(1, first.securityVersion());
        jdbc.update("UPDATE " + C + " SET password_hash='changed' WHERE id=?", b.credentialId());
        var next = states.credential(b.userId());
        assertEquals("changed", next.passwordHash());
        assertEquals(2, next.securityVersion());
        assertEquals("synthetic-hash", first.passwordHash());
        jdbc.update(
                "UPDATE " + P + " SET metadata='{\"changed\":true}'::jsonb WHERE id=?",
                b.providerId());
        assertEquals(2, states.provider(b.providerId(), "LOCAL").version());
    }

    @Test
    void jpaAdapterFlushesOnSameBackendAndRefreshesAnAlreadyManagedSession() {
        var b = fixture("LOCAL");
        bindings.append(b);
        tx().executeWithoutResult(
                        status -> {
                            var cached = em.find(LoginSessionJpaEntity.class, b.sessionId());
                            assertEquals(LoginSessionStatus.ACTIVE, cached.status());
                            int backend =
                                    jdbc.queryForObject("SELECT pg_backend_pid()", Integer.class);
                            em.unwrap(Session.class)
                                    .doWork(
                                            connection -> {
                                                try (var statement = connection.createStatement();
                                                        var result =
                                                                statement.executeQuery(
                                                                        "SELECT"
                                                                            + " pg_backend_pid()")) {
                                                    assertTrue(result.next());
                                                    assertEquals(backend, result.getInt(1));
                                                }
                                            });
                            jdbc.update(
                                    "UPDATE " + S + " SET status='REVOKED',ended_at=? WHERE id=?",
                                    Timestamp.from(Instant.now()),
                                    b.sessionId());
                            assertEquals(
                                    LoginSessionStatus.REVOKED,
                                    sessions.findById(b.sessionId()).orElseThrow().status());
                        });
        assertFalse(states.current(b, Instant.now()));
    }

    @Test
    void staleJpaMergeCannotResurrectCommittedBoundTermination() {
        var b = fixture("LOCAL");
        var stale = sessions.findById(b.sessionId()).orElseThrow();
        bindings.append(b);
        jdbc.update(
                "UPDATE " + S + " SET status='REVOKED',ended_at=? WHERE id=?",
                Timestamp.from(Instant.now()),
                b.sessionId());
        assertThrows(
                RuntimeException.class,
                () -> tx().executeWithoutResult(status -> sessions.save(stale)));
        assertEquals(
                "REVOKED",
                jdbc.queryForObject(
                        "SELECT status FROM " + S + " WHERE id=?", String.class, b.sessionId()));
    }

    @Test
    void nativeMutationAndDeletionWaitingOnAppendRecheckBindingAfterCommit() throws Exception {
        for (String operation :
                List.of(
                        "UPDATE " + S + " SET user_agent='changed' WHERE id=",
                        "DELETE FROM " + S + " WHERE id=")) {
            var b = fixture("LOCAL");
            try (var owner = connection();
                    var executor = Executors.newSingleThreadExecutor()) {
                owner.setAutoCommit(false);
                nativeInsert(owner, b, Map.of());
                int holding = pid(owner);
                var started = new CountDownLatch(1);
                var waiting = new AtomicInteger();
                var future =
                        executor.submit(
                                () -> {
                                    try (var writer = connection()) {
                                        waiting.set(pid(writer));
                                        started.countDown();
                                        try (var statement = writer.createStatement()) {
                                            statement.executeUpdate(
                                                    operation + "'" + b.sessionId() + "'");
                                            return "accepted";
                                        }
                                    } catch (SQLException failure) {
                                        return failure.getSQLState();
                                    }
                                });
                assertTrue(started.await(5, TimeUnit.SECONDS));
                observeWait(waiting.get(), holding);
                owner.commit();
                assertEquals("23514", future.get(5, TimeUnit.SECONDS));
            }
            assertEquals(1, count(b));
            assertTrue(states.current(b, Instant.now()));
        }
    }

    @Test
    void appendWaitingOnNativeTerminationReadsItsCommittedTerminalImage() throws Exception {
        var b = fixture("LOCAL");
        try (var owner = connection();
                var executor = Executors.newSingleThreadExecutor()) {
            owner.setAutoCommit(false);
            try (var statement = owner.createStatement()) {
                statement.executeUpdate(
                        "UPDATE "
                                + S
                                + " SET status='REVOKED',ended_at=clock_timestamp() WHERE id='"
                                + b.sessionId()
                                + "'");
            }
            int holding = pid(owner);
            var started = new CountDownLatch(1);
            var waiting = new AtomicInteger();
            var future =
                    executor.submit(
                            () -> {
                                try (var writer = connection()) {
                                    waiting.set(pid(writer));
                                    started.countDown();
                                    nativeInsert(writer, b, Map.of());
                                    return "accepted";
                                } catch (SQLException failure) {
                                    return failure.getSQLState();
                                }
                            });
            assertTrue(started.await(5, TimeUnit.SECONDS));
            observeWait(waiting.get(), holding);
            owner.commit();
            assertEquals("23514", future.get(5, TimeUnit.SECONDS));
        }
        assertEquals(0, count(b));
    }

    @Test
    void competingExactAndDifferentReplaysSerializeWithoutRewritingTuple() throws Exception {
        for (boolean exact : List.of(true, false)) {
            var b = fixture("LOCAL");
            byte[] different = new byte[32];
            different[0] = 1;
            try (var owner = connection();
                    var executor = Executors.newSingleThreadExecutor()) {
                owner.setAutoCommit(false);
                nativeInsert(owner, b, Map.of());
                int holding = pid(owner);
                var waiting = new AtomicInteger();
                var started = new CountDownLatch(1);
                var future =
                        executor.submit(
                                () -> {
                                    try (var writer = connection()) {
                                        waiting.set(pid(writer));
                                        started.countDown();
                                        nativeInsert(
                                                writer,
                                                b,
                                                exact
                                                        ? Map.of()
                                                        : Map.of(
                                                                "producer_authenticator",
                                                                different));
                                        return "accepted";
                                    } catch (SQLException failure) {
                                        return failure.getSQLState();
                                    }
                                });
                assertTrue(started.await(5, TimeUnit.SECONDS));
                observeWait(waiting.get(), holding);
                owner.commit();
                assertEquals(exact ? "accepted" : "23514", future.get(5, TimeUnit.SECONDS));
            }
            assertEquals(1, count(b));
            assertEquals(
                    b, bindings.findByNativeIssuerAndJti(b.nativeIssuer(), b.jti()).orElseThrow());
        }
    }

    @Test
    void observedOwnerLockOrderCoversCredentialAndExternalBranches() throws Exception {
        for (String type : List.of("LOCAL", "OIDC")) {
            var b = fixture(type);
            try (var owner = connection();
                    var executor = Executors.newSingleThreadExecutor()) {
                owner.setAutoCommit(false);
                try (var query = owner.createStatement()) {
                    query.executeQuery(
                                    "SELECT id FROM "
                                            + U
                                            + " WHERE id='"
                                            + b.userId()
                                            + "' FOR UPDATE")
                            .close();
                }
                int holding = pid(owner);
                var waiting = new AtomicInteger();
                var started = new CountDownLatch(1);
                var future =
                        executor.submit(
                                () -> {
                                    try (var writer = connection()) {
                                        waiting.set(pid(writer));
                                        started.countDown();
                                        nativeInsert(writer, b, Map.of());
                                        return true;
                                    }
                                });
                assertTrue(started.await(5, TimeUnit.SECONDS));
                observeWait(waiting.get(), holding);
                for (var entry :
                        Map.of(
                                        P,
                                        b.providerId(),
                                        type.equals("LOCAL") ? C : E,
                                        type.equals("LOCAL")
                                                ? b.credentialId()
                                                : b.externalIdentityId())
                                .entrySet())
                    assertEquals("55P03", probeLock(entry.getKey(), entry.getValue()));
                assertEquals("accepted", probeLock(S, b.sessionId()));
                owner.rollback();
                assertTrue(future.get(5, TimeUnit.SECONDS));
            }
            assertEquals(1, count(b));
        }
    }

    @Test
    void jpaMutationWaitingOnAppendCannotBypassFreshBoundGuard() throws Exception {
        var b = fixture("LOCAL");
        var original = sessions.findById(b.sessionId()).orElseThrow();
        var changed =
                new LoginSession(
                        original.id(),
                        original.userId(),
                        original.identityProviderId(),
                        original.externalIdentityId(),
                        original.startedAt(),
                        original.lastSeenAt(),
                        original.expiresAt(),
                        original.clientIp(),
                        "changed",
                        original.status(),
                        original.correlationId(),
                        original.sessionType(),
                        original.endedAt());
        try (var owner = connection();
                var executor = Executors.newSingleThreadExecutor()) {
            owner.setAutoCommit(false);
            nativeInsert(owner, b, Map.of());
            int holding = pid(owner);
            var waiting = new AtomicInteger();
            var started = new CountDownLatch(1);
            var future =
                    executor.submit(
                            () -> {
                                try {
                                    tx().executeWithoutResult(
                                                    status -> {
                                                        jdbc.execute(
                                                                "SET LOCAL"
                                                                    + " statement_timeout=10000");
                                                        waiting.set(
                                                                jdbc.queryForObject(
                                                                        "SELECT pg_backend_pid()",
                                                                        Integer.class));
                                                        started.countDown();
                                                        sessions.save(changed);
                                                    });
                                    return "accepted";
                                } catch (RuntimeException failure) {
                                    for (Throwable c = failure; c != null; c = c.getCause())
                                        if (c instanceof SQLException sql) return sql.getSQLState();
                                    return "missing SQLSTATE";
                                }
                            });
            assertTrue(started.await(5, TimeUnit.SECONDS));
            observeWait(waiting.get(), holding);
            owner.commit();
            assertEquals("23514", future.get(5, TimeUnit.SECONDS));
        }
        assertTrue(states.current(b, Instant.now()));
    }

    @Test
    void copyUsesRealGuardAndRollsBackTheWholeFailedBatch() throws Exception {
        var first = fixture("LDAP");
        var second = fixture("LOCAL");
        var bad = values(second);
        bad.put("format_version", 2);
        try (var connection = connection()) {
            var copy = connection.unwrap(PGConnection.class).getCopyAPI();
            assertThrows(
                    SQLException.class,
                    () ->
                            copy.copyIn(
                                    "COPY "
                                            + B
                                            + " ("
                                            + String.join(",", values(first).keySet())
                                            + ") FROM STDIN WITH (FORMAT csv, NULL '\\N')",
                                    new StringReader(csv(values(first)) + csv(bad))));
        }
        assertEquals(0, count(first));
        assertEquals(0, count(second));
        try (var connection = connection()) {
            assertEquals(
                    1,
                    connection
                            .unwrap(PGConnection.class)
                            .getCopyAPI()
                            .copyIn(
                                    "COPY "
                                            + B
                                            + " ("
                                            + String.join(",", values(first).keySet())
                                            + ") FROM STDIN WITH (FORMAT csv, NULL '\\N')",
                                    new StringReader(csv(values(first)))));
        }
        assertEquals(
                first,
                bindings.findByNativeIssuerAndJti(first.nativeIssuer(), first.jti()).orElseThrow());
    }

    @Test
    void mergeCanInsertButCannotUseAnUpdateActionToRewriteBinding() {
        var b = fixture("LOCAL");
        var values = values(b);
        String columns = String.join(",", values.keySet());
        String incoming =
                "USING (VALUES ("
                        + parameters(values)
                        + ")) incoming("
                        + columns
                        + ") ON target.binding_id=incoming.binding_id ";
        String insert =
                "WHEN NOT MATCHED THEN INSERT ("
                        + columns
                        + ") VALUES ("
                        + values.keySet().stream()
                                .map(c -> "incoming." + c)
                                .collect(java.util.stream.Collectors.joining(","))
                        + ")";
        jdbc.update("MERGE INTO " + B + " target " + incoming + insert, values.values().toArray());
        reject(
                () ->
                        jdbc.update(
                                "MERGE INTO "
                                        + B
                                        + " target "
                                        + incoming
                                        + "WHEN MATCHED THEN UPDATE SET"
                                        + " native_audience=incoming.native_audience "
                                        + insert,
                                values.values().toArray()));
        assertEquals(b, bindings.findByNativeIssuerAndJti(b.nativeIssuer(), b.jti()).orElseThrow());
    }

    @Test
    void failedFreshDdlCollisionDoesNotInstallPartialBindingGuards() {
        String name = "binding_collision_" + id().replace("-", "");
        jdbc.execute("CREATE DATABASE " + name);
        var ds =
                new DriverManagerDataSource(
                        POSTGRES.getJdbcUrl().replace("/hidra_test", "/" + name),
                        POSTGRES.getUsername(),
                        POSTGRES.getPassword());
        var baseline =
                Flyway.configure()
                        .dataSource(ds)
                        .locations("classpath:db/migration")
                        .target("20261012.002")
                        .load();
        baseline.migrate();
        var isolated = new JdbcTemplate(ds);
        isolated.execute("CREATE TABLE " + B + "(collision integer)");
        assertThrows(
                org.flywaydb.core.api.FlywayException.class,
                () ->
                        Flyway.configure()
                                .dataSource(ds)
                                .locations("classpath:db/migration")
                                .load()
                                .migrate());
        assertEquals(
                0,
                isolated.queryForObject(
                        "SELECT count(*) FROM flyway_schema_history WHERE version='20261013.001'",
                        Integer.class));
        assertNull(
                isolated.queryForObject(
                        "SELECT"
                            + " to_regprocedure('public.hidra_identity_operation_binding_guard()')::text",
                        String.class));
        assertEquals(
                0,
                isolated.queryForObject(
                        "SELECT count(*) FROM pg_trigger WHERE tgname LIKE 'trg_p25_binding_%' OR"
                            + " tgname LIKE 'trg_p25_bound_session_%' OR"
                            + " tgname='trg_p25_session_isolation'",
                        Integer.class));
    }

    @Test
    void ownerTupleSwapsMissingReferencesAndExpiredOrFutureIntervalsReject() {
        for (var change :
                List.<Map<String, Object>>of(
                        Map.of("user_id", id()),
                        Map.of("provider_id", id()),
                        Map.of("session_id", id()),
                        Map.of("credential_id", id()),
                        Map.of("provider_security_version", 0L),
                        Map.of("credential_security_version", 0L))) {
            var b = fixture("LOCAL");
            reject(() -> nativeInsert(jdbc, b, change));
        }
        var expired = fixture("LOCAL");
        var end = expired.issuedAt().plusSeconds(1);
        jdbc.update(
                "UPDATE " + S + " SET expires_at=? WHERE id=?",
                Timestamp.from(end),
                expired.sessionId());
        reject(() -> nativeInsert(jdbc, expired, Map.of("expires_at", Timestamp.from(end))));
        var future = fixture("LOCAL");
        var start = future.expiresAt();
        var finish = start.plusSeconds(600);
        jdbc.update(
                "UPDATE " + S + " SET started_at=?,expires_at=? WHERE id=?",
                Timestamp.from(start),
                Timestamp.from(finish),
                future.sessionId());
        reject(
                () ->
                        nativeInsert(
                                jdbc,
                                future,
                                Map.of(
                                        "issued_at",
                                        Timestamp.from(start),
                                        "expires_at",
                                        Timestamp.from(finish))));
    }

    @Test
    void restrictiveOwnerForeignKeysAndBoundSessionDeleteCannotOrphanEvidence() {
        var b = fixture("LOCAL");
        bindings.append(b);
        for (var entry :
                Map.of(U, b.userId(), P, b.providerId(), C, b.credentialId(), S, b.sessionId())
                        .entrySet()) {
            reject(
                    () ->
                            jdbc.update(
                                    "DELETE FROM " + entry.getKey() + " WHERE id=?",
                                    entry.getValue()));
            assertEquals(1, count(b));
        }
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

    private static String csv(Map<String, Object> values) {
        return values.values().stream()
                        .map(
                                v ->
                                        v == null
                                                ? "\\N"
                                                : "\""
                                                        + (v instanceof byte[] bytes
                                                                        ? "\\x"
                                                                                + HexFormat.of()
                                                                                        .formatHex(
                                                                                                bytes)
                                                                        : v
                                                                                        instanceof
                                                                                        Timestamp
                                                                                                timestamp
                                                                                ? timestamp
                                                                                        .toInstant()
                                                                                        .toString()
                                                                                : v.toString())
                                                                .replace("\"", "\"\"")
                                                        + "\"")
                        .collect(java.util.stream.Collectors.joining(","))
                + "\n";
    }

    private int count(OperationAuthenticationBinding b) {
        return jdbc.queryForObject(
                "SELECT count(*) FROM " + B + " WHERE binding_id=?", Integer.class, b.bindingId());
    }

    private TransactionTemplate tx() {
        var tx = new TransactionTemplate(manager);
        tx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        return tx;
    }

    private void reject(Runnable mutation) {
        assertThrows(
                DataAccessException.class,
                () -> tx().executeWithoutResult(status -> mutation.run()));
    }

    private Connection connection() throws SQLException {
        var c =
                DriverManager.getConnection(
                        POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
        c.setNetworkTimeout(Runnable::run, 10000);
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
        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(5);
        while (System.nanoTime() < deadline)
            if (Boolean.TRUE.equals(
                    jdbc.queryForObject(
                            "SELECT ? = ANY(pg_blocking_pids(?))",
                            Boolean.class,
                            holding,
                            waiting))) return;
        fail("Required independent-backend lock wait was not observed");
    }

    private String probeLock(String table, String key) throws SQLException {
        try (var c = connection()) {
            c.setAutoCommit(false);
            try (var q = c.createStatement()) {
                q.executeQuery(
                                "SELECT id FROM "
                                        + table
                                        + " WHERE id='"
                                        + key
                                        + "' FOR UPDATE NOWAIT")
                        .close();
                c.rollback();
                return "accepted";
            } catch (SQLException locked) {
                c.rollback();
                return locked.getSQLState();
            }
        }
    }

    private JdbcTemplate freshDatabase() {
        String name = "binding_fresh_" + id().replace("-", "");
        jdbc.execute("CREATE DATABASE " + name);
        var ds =
                new DriverManagerDataSource(
                        POSTGRES.getJdbcUrl().replace("/hidra_test", "/" + name),
                        POSTGRES.getUsername(),
                        POSTGRES.getPassword());
        var fresh = Flyway.configure().dataSource(ds).locations("classpath:db/migration").load();
        fresh.migrate();
        fresh.validate();
        assertEquals(0, fresh.info().pending().length);
        return new JdbcTemplate(ds);
    }

    private static byte[] differentTag() {
        byte[] tag = new byte[32];
        tag[0] = 1;
        return tag;
    }

    private static String id() {
        return UUID.randomUUID().toString();
    }
}
