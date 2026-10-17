/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedAuthorizationEvidencePostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-17
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Verifies fenced authorization evidence and conservative rejection within the registered G scope.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.model.*;
import dz.sh.hidra.modules.identity.application.port.in.*;
import dz.sh.hidra.modules.identity.application.port.out.*;
import dz.sh.hidra.modules.identity.domain.model.*;
import dz.sh.hidra.modules.identity.domain.policy.*;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.domain.value.*;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityAuthorityFence;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcProtectedAuthorizationEvidenceReader;
import dz.sh.hidra.modules.identity.infrastructure.security.*;
import dz.sh.hidra.modules.identity.infrastructure.security.ProtectedOperationIdentityParticipant.*;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;

import org.flywaydb.core.Flyway;
import org.hibernate.Session;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.test.context.*;
import org.springframework.test.context.bean.override.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.*;
import org.springframework.transaction.support.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import javax.crypto.spec.SecretKeySpec;

/**
 * Only directory/source transport is synthetic; native crypto, origin, fences and owner state are
 * real.
 */
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(
        classes = HidraApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        properties = {
            "hidra.platform.security.ldap.enabled=true",
            "hidra.platform.security.ldap.url=ldap://synthetic.invalid:389",
            "hidra.platform.security.ldap.base-dn=dc=synthetic,dc=invalid",
            "hidra.platform.security.ldap.user-search-base=ou=people",
            "hidra.platform.security.ldap.user-search-filter=(uid={0})",
            "hidra.platform.security.jwt.hmac-secret=synthetic-completion-test-key-32-bytes-only",
            "hidra.platform.security.jwt.token-issuer=hidra-api",
            "hidra.platform.security.jwt.audience=hidra-api"
        })
@ActiveProfiles("test")
public class ProtectedAuthorizationEvidencePostgresIntegrationTest {
    private static final String KEY = "synthetic-completion-test-key-32-bytes-only";
    private static final String SOURCE = "https://oidc.synthetic.invalid";
    private static final String P = "public.hidra_identity_provider",
            U = "public.hidra_identity_user",
            E = "public.hidra_identity_external_identity",
            C = "public.hidra_identity_local_credential",
            S = "public.hidra_identity_login_session",
            B = "public.hidra_identity_operation_binding";

    @Container
    static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("hidra_evidence_test")
                    .withCommand(
                            "postgres",
                            "-c",
                            "log_error_verbosity=terse",
                            "-c",
                            "track_commit_timestamp=on",
                            "-c",
                            "log_min_error_statement=panic",
                            "-c",
                            "log_parameter_max_length_on_error=0");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        r.add("spring.datasource.username", POSTGRES::getUsername);
        r.add("spring.datasource.password", POSTGRES::getPassword);
        r.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        r.add("spring.flyway.enabled", () -> "true");
    }

    @Autowired JdbcTemplate jdbc;
    @Autowired JdbcProtectedAuthorizationEvidenceReader reader;
    @Autowired jakarta.persistence.EntityManager em;
    @Autowired Flyway flyway;
    @Autowired ProtectedOperationIdentityParticipant participant;
    @Autowired JdbcIdentityAuthorityFence fence;
    // Failure injection only: real event persistence and real owner transaction still execute.

    @Autowired ProtectedTransactionExecutor executor;
    @Autowired PlatformTransactionManager manager;
    @Autowired AuthenticateDirectUserUseCase direct;
    @Autowired CompleteAuthenticatedPrincipalUseCase completion;
    @Autowired DirectAuthenticationPort directPort;
    @Autowired IdentityOidcJwtAuthenticationConverter oidc;
    @Autowired LdapAuthenticationProvider ldap;
    @Autowired TrustedAuthenticationVerificationRegistry verificationRegistry;
    @MockitoSpyBean OperationAuthenticationBindingRepositoryPort bindings;
    @Autowired PasswordEncoder passwords;
    @Autowired LoginSessionRepositoryPort sessions;
    @MockitoSpyBean ClaimNameCatalogPort claimCatalog;
    @MockitoSpyBean BoundSourceClaimIssuanceFencePort claimIssuanceFence;
    @MockitoSpyBean BoundSourceClaimEvidenceRepositoryPort claimCompanions;

    @Autowired
    @Qualifier("hidraJwtDecoder")
    JwtDecoder nativeDecoder;

    // Transport fixture only: no external directory service in this synthetic database.
    @MockitoBean LdapCredentialVerificationPort directory;

    // Transport fixture delegates genuine signed source verification below; native decoder is real.
    @MockitoBean(name = "externalOidcJwtDecoder")
    JwtDecoder externalDecoder;

    // Capture only: every call delegates the real LOCAL producer including its private receipt.

    private static final tools.jackson.databind.json.JsonMapper JSON =
            tools.jackson.databind.json.JsonMapper.builder().build();
    private final JwtEncoder sourceEncoder =
            NimbusJwtEncoder.withSecretKey(
                            new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256"))
                    .algorithm(MacAlgorithm.HS256)
                    .build();
    private final JwtDecoder realExternal =
            NimbusJwtDecoder.withSecretKey(
                            new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256"))
                    .macAlgorithm(MacAlgorithm.HS256)
                    .build();

    private record Fixture(
            String user,
            String provider,
            String link,
            String credential,
            String subject,
            String type) {}

    private String schema;
    private final AtomicInteger receipts = new AtomicInteger();

    @BeforeEach
    void initialize() {
        SecurityContextHolder.clearContext();
        ReflectionTestUtils.setField(oidc, "verifiedClaimReceiptEnabled", false);
        ReflectionTestUtils.setField(ldap, "verifiedClaimReceiptEnabled", false);
        cleanEvidence();
        flyway.validate();
        assertThat(flyway.info().pending()).isEmpty();
        when(externalDecoder.decode(anyString()))
                .thenAnswer(i -> realExternal.decode(i.getArgument(0)));
        schema = "hpr051g_" + UUID.randomUUID().toString().replace("-", "");
        jdbc.execute("CREATE SCHEMA " + schema);
        jdbc.execute("CREATE TABLE " + receipt() + "(id integer PRIMARY KEY)");
        // Warm proxy/JPA/executor paths outside every bounded operation.
        var result = authenticate(fixture("LOCAL"));
        ambient(result.accessToken());
        run(participant.prepare());
    }

    @AfterEach
    void clear() {
        ReflectionTestUtils.setField(oidc, "verifiedClaimReceiptEnabled", false);
        ReflectionTestUtils.setField(ldap, "verifiedClaimReceiptEnabled", false);
        directPort.finishVerification();
        SecurityContextHolder.clearContext();
        // Guarded provenance/security rows are retained; only this test's disposable receipt is
        // removed.
        cleanEvidence();
        if (schema != null) jdbc.execute("DROP SCHEMA " + schema + " CASCADE");
    }

    private String receipt() {
        return schema + ".receipt";
    }

    private TransactionTemplate tx() {
        var t = new TransactionTemplate(manager);
        t.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        return t;
    }

    private Contribution run(Prepared prepared) {
        AtomicReference<Contribution> saved = new AtomicReference<>();
        return executor.execute(
                () -> {
                    var c = participant.contribute(prepared);
                    saved.set(c);
                    return c;
                },
                em::flush,
                () -> guard(saved.get()));
    }

    private void guard(Contribution c) {
        Integer count =
                jdbc.queryForObject(
                        "WITH observed AS MATERIALIZED (SELECT clock_timestamp() AS t), made AS"
                                + " (INSERT INTO "
                                + receipt()
                                + " SELECT ? FROM observed WHERE t>=? AND t<? RETURNING id) SELECT"
                                + " count(*) FROM made",
                        Integer.class,
                        receipts.incrementAndGet(),
                        Timestamp.from(c.evidence().observedAt()),
                        Timestamp.from(c.validUntil()));
        if (!Integer.valueOf(1).equals(count))
            throw new IllegalStateException(
                    "Protected temporal guard rejected invalid or expired interval");
    }

    private static Connection connection() throws SQLException {
        var c =
                DriverManager.getConnection(
                        POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
        c.setAutoCommit(false);
        c.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        return c;
    }

    private static long scalar(Connection c, String sql) throws SQLException {
        try (var st = c.createStatement();
                var rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getLong(1);
        }
    }

    private static void update(Connection c, String sql, String id) throws SQLException {
        try (var st = c.prepareStatement(sql)) {
            st.setString(1, id);
            assertThat(st.executeUpdate()).isEqualTo(1);
        }
    }

    private static void await(CountDownLatch l) {
        try {
            assertThat(l.await(5, TimeUnit.SECONDS)).isTrue();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }
    }

    private static <T> T join(Future<T> f) {
        try {
            return f.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private static org.springframework.security.oauth2.server.resource.authentication
                    .JwtAuthenticationToken
            ambient(String raw) {
        var jwt =
                Jwt.withTokenValue(raw)
                        .header("alg", "HS256")
                        .issuer("forged-wrapper")
                        .subject("forged-wrapper")
                        .issuedAt(Instant.now().minusSeconds(1))
                        .expiresAt(Instant.now().plusSeconds(3600))
                        .build();
        var actual =
                new org.springframework.security.oauth2.server.resource.authentication
                        .JwtAuthenticationToken(jwt, List.of());
        actual.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(actual);
        return actual;
    }

    @SuppressWarnings("unchecked")
    private AuthenticationResult authenticate(Fixture f) {
        if (f.type().equals("OIDC"))
            return completeOidc(f, Instant.now().plusSeconds(300).truncatedTo(ChronoUnit.SECONDS));
        return direct.authenticate(
                new DirectAuthenticationCommand(
                        ProviderType.valueOf(f.type()),
                        f.user(),
                        "synthetic-password",
                        "synthetic-ip",
                        "synthetic-agent",
                        "synthetic-correlation"));
    }

    private AuthenticationResult completeOidc(Fixture f, Instant deadline) {
        return completeOidc(f, deadline, List.of("private-original-group"));
    }

    private AuthenticationResult completeOidc(Fixture f, Instant deadline, Object groups) {
        Jwt signed =
                sourceEncoder.encode(
                        JwtEncoderParameters.from(
                                JwtClaimsSet.builder()
                                        .issuer(SOURCE)
                                        .subject(f.subject())
                                        .audience(List.of("hidra-api"))
                                        .issuedAt(Instant.now().minusSeconds(1))
                                        .expiresAt(deadline)
                                        .claim("groups", groups)
                                        .build()));
        var actual = oidc.convert(realExternal.decode(signed.getTokenValue()));
        SecurityContextHolder.getContext().setAuthentication(actual);
        return completion.complete(
                input((HidraPrincipal) actual.getPrincipal(), null),
                "synthetic-ip",
                "synthetic-agent",
                "synthetic-correlation");
    }

    private static AuthenticatedPrincipalInput input(HidraPrincipal p, String handle) {
        return new AuthenticatedPrincipalInput(
                p.userId(),
                p.username(),
                p.displayName(),
                p.authenticationType(),
                p.identityProviderId(),
                p.roles(),
                p.permissions(),
                p.externalIdentityId(),
                handle);
    }

    private int backend() {
        return jdbc.queryForObject("SELECT pg_backend_pid()", Integer.class);
    }

    private Fixture fixture(String type) {
        jdbc.update("UPDATE " + P + " SET status='INACTIVE' WHERE status='ACTIVE'");
        String user = UUID.randomUUID().toString(),
                provider = UUID.randomUUID().toString(),
                link = type.equals("LOCAL") ? null : UUID.randomUUID().toString(),
                credential = type.equals("LOCAL") ? UUID.randomUUID().toString() : null,
                subject = link == null ? null : "  exact-synthetic-é-" + link + "  ";
        Timestamp now = Timestamp.from(Instant.now());
        jdbc.update(
                "INSERT INTO "
                        + U
                        + "(id,username,user_type,status,failed_login_count,created_at,updated_at)"
                        + " VALUES (?,?,'HUMAN','ACTIVE',0,?,?)",
                user,
                user,
                now,
                now);
        jdbc.update(
                "INSERT INTO "
                        + P
                        + "(id,code,name,provider_type,issuer_uri,directory_base_dn,user_search_base,status,sync_enabled,just_in_time_provisioning_enabled,created_at,updated_at)"
                        + " VALUES (?,?,'Synthetic completion"
                        + " provider',?,?,?,?,'ACTIVE',false,false,?,?)",
                provider,
                provider,
                type,
                type.equals("OIDC") ? SOURCE : null,
                type.equals("LDAP") || type.equals("ACTIVE_DIRECTORY")
                        ? "dc=synthetic,dc=invalid"
                        : null,
                type.equals("LDAP") || type.equals("ACTIVE_DIRECTORY") ? "ou=people" : null,
                now,
                now);
        if (credential != null)
            jdbc.update(
                    "INSERT INTO "
                            + C
                            + "(id,user_id,password_hash,credential_status,created_at,updated_at)"
                            + " VALUES (?, ?,?,'ACTIVE',?,?)",
                    credential,
                    user,
                    passwords.encode("synthetic-password"),
                    now,
                    now);
        else {
            jdbc.update(
                    "INSERT INTO "
                            + E
                            + "(id,user_id,identity_provider_id,external_subject,status,created_at,updated_at)"
                            + " VALUES (?,?,?,?,'LINKED',?,?)",
                    link,
                    user,
                    provider,
                    subject,
                    now,
                    now);
            if (!type.equals("OIDC"))
                when(directory.verify(user, "synthetic-password"))
                        .thenReturn(
                                Optional.of(
                                        new VerifiedDirectoryIdentity(
                                                subject, user, "Synthetic operator", null, null)));
        }
        return new Fixture(user, provider, link, credential, subject, type);
    }

    private static final String T = "public.hidra_identity_";
    private static final List<String> OPS =
            List.of(
                    "author",
                    "publish",
                    "read-current",
                    "read-history",
                    "list-history",
                    "withdraw",
                    "reactivate",
                    "transfer");
    private static final String COMPANY = "synthetic-company", FACILITY = "synthetic-facility";

    private static String id() {
        return UUID.randomUUID().toString();
    }

    private void cleanEvidence() {
        // Only ordinary fixtures; guarded security generations and provenance are retained.
        for (String name :
                List.of(
                        "authorization_policy_rule",
                        "authorization_policy_version",
                        "authorization_policy",
                        "subject_security_attribute",
                        "attribute_definition",
                        "authorization_delegation_grant",
                        "user_permission_grant",
                        "role_permission_grant",
                        "group_role_grant",
                        "user_role_grant",
                        "user_group_membership",
                        "external_permission_mapping",
                        "external_role_mapping",
                        "external_group_mapping",
                        "group",
                        "role",
                        "permission")) jdbc.update("DELETE FROM " + T + name);
    }

    private Instant dbNow() {
        return jdbc.queryForObject("SELECT clock_timestamp()", Timestamp.class).toInstant();
    }

    private String permission(String op) {
        String code = "topology:station-revision:" + op;
        List<String> found =
                jdbc.queryForList(
                        "SELECT id FROM " + T + "permission WHERE code=?", String.class, code);
        if (!found.isEmpty()) return found.getFirst();
        String id = id();
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "permission(id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)"
                        + " VALUES"
                        + " (?,?,'topology','STATION_GRAPH_REVISION',?,false,'ACTIVE',clock_timestamp(),clock_timestamp())",
                id,
                code,
                op);
        return id;
    }

    private String grant(Fixture f, String op, String effect, String scope, String ref) {
        String id = id();
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "user_permission_grant(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,emergency_access,valid_from,valid_to,status,created_at)"
                        + " VALUES (?,?,?,?,?,?,'synthetic"
                        + " evidence',false,clock_timestamp()-interval '1"
                        + " hour',clock_timestamp()+interval '1 hour','ACTIVE',clock_timestamp())",
                id,
                f.user(),
                permission(op),
                effect,
                scope,
                ref);
        return id;
    }

    private void both(Fixture f, String op) {
        grant(f, op, "GRANT", "CUSTOM", "topology-company:" + COMPANY);
        grant(f, op, "GRANT", "FACILITY", FACILITY);
    }

    private AuthorizationEvaluationRequest request(Fixture f, String op, boolean company) {
        return new AuthorizationEvaluationRequest(
                f.user(),
                "topology:station-revision:" + op,
                "STATION_GRAPH_REVISION",
                FACILITY,
                new AuthorizationScope(
                        company ? ScopeType.CUSTOM : ScopeType.FACILITY,
                        company ? "topology-company:" + COMPANY : FACILITY,
                        null));
    }

    private Prepared prepare(Fixture f) {
        var result = authenticate(f);
        ambient(result.accessToken());
        return participant.prepare();
    }

    public record Observation(
            AuthorizationDecisionValue company,
            AuthorizationDecisionValue facility,
            Instant sampledAt,
            Instant validUntil,
            int pid,
            long xid) {}

    private Observation assess(Fixture f, String op) {
        return assess(f, op, prepare(f), () -> {});
    }

    private Observation assess(Fixture f, String op, Prepared prepared, Runnable inside) {
        AtomicReference<Contribution> saved = new AtomicReference<>();
        AtomicBoolean committed = new AtomicBoolean();
        Observation observed =
                executor.execute(
                        () -> {
                            var c = participant.contribute(prepared);
                            saved.set(c);
                            inside.run();
                            assertThat(backend()).isEqualTo(c.backendPid());
                            em.unwrap(Session.class)
                                    .doWork(
                                            conn -> {
                                                assertThat(scalar(conn, "SELECT pg_backend_pid()"))
                                                        .isEqualTo(c.backendPid());
                                                assertThat(scalar(conn, "SELECT txid_current()"))
                                                        .isEqualTo(c.transactionId());
                                            });
                            var a = request(f, op, true);
                            var b = request(f, op, false);
                            var ea =
                                    reader.load(c.evidence(), a, c.backendPid(), c.transactionId());
                            var eb =
                                    reader.load(c.evidence(), b, c.backendPid(), c.transactionId());
                            var evaluator = new AuthorizationPolicyEvaluator();
                            var da = evaluator.evaluate(a, ea, c.evidence().observedAt());
                            var db = evaluator.evaluate(b, eb, c.evidence().observedAt());
                            assertThat(da.evaluatedAt())
                                    .isEqualTo(db.evaluatedAt())
                                    .isEqualTo(c.evidence().observedAt());
                            TransactionSynchronizationManager.registerSynchronization(
                                    new TransactionSynchronization() {
                                        @Override
                                        public void afterCommit() {
                                            committed.set(true);
                                        }
                                    });
                            assertThat(committed).isFalse();
                            // Only scalar diagnostic observations leave the protected executor,
                            // never the graph.
                            return new Observation(
                                    da.decision(),
                                    db.decision(),
                                    c.evidence().observedAt(),
                                    c.validUntil(),
                                    c.backendPid(),
                                    c.transactionId());
                        },
                        em::flush,
                        () -> guard(saved.get()));
        assertThat(committed).isTrue();
        assertThat(TransactionSynchronizationManager.isActualTransactionActive()).isFalse();
        try (var observer = connection();
                var statement =
                        observer.prepareStatement(
                                "SELECT pg_xact_commit_timestamp(?::xid) IS NOT NULL")) {
            statement.setString(1, Long.toString(observed.xid()));
            try (var rows = statement.executeQuery()) {
                assertThat(rows.next()).isTrue();
                assertThat(rows.getBoolean(1)).isTrue();
            }
        } catch (SQLException fault) {
            throw new AssertionError(fault);
        }
        return observed;
    }

    private void permits(Observation o) {
        assertThat(o.company()).isEqualTo(AuthorizationDecisionValue.PERMIT);
        assertThat(o.facility()).isEqualTo(AuthorizationDecisionValue.PERMIT);
    }

    private String role(Fixture f, String op, String effect, boolean grouped) {
        String role = id(), rp = id();
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "role(id,code,role_type,status,created_at,updated_at) VALUES"
                        + " (?,?,'BUSINESS','ACTIVE',clock_timestamp(),clock_timestamp())",
                role,
                role);
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "role_permission_grant(id,role_id,permission_id,effect,valid_from,status,created_at)"
                        + " VALUES (?,?,?,?,clock_timestamp()-interval '1"
                        + " hour','ACTIVE',clock_timestamp())",
                rp,
                role,
                permission(op),
                effect);
        if (!grouped)
            jdbc.update(
                    "INSERT INTO "
                            + T
                            + "user_role_grant(id,user_id,role_id,valid_from,status,created_at)"
                            + " VALUES (?,?,?,clock_timestamp()-interval '1"
                            + " hour','ACTIVE',clock_timestamp())",
                    id(),
                    f.user(),
                    role);
        else {
            String group = id();
            jdbc.update(
                    "INSERT INTO "
                            + T
                            + "group(id,code,group_type,status,created_at,updated_at) VALUES"
                            + " (?,?,'LOCAL','ACTIVE',clock_timestamp(),clock_timestamp())",
                    group,
                    group);
            jdbc.update(
                    "INSERT INTO "
                            + T
                            + "user_group_membership(id,user_id,group_id,membership_type,valid_from,status,created_at,updated_at)"
                            + " VALUES (?,?,?,'DIRECT',clock_timestamp()-interval '1"
                            + " hour','ACTIVE',clock_timestamp(),clock_timestamp())",
                    id(),
                    f.user(),
                    group);
            jdbc.update(
                    "INSERT INTO "
                            + T
                            + "group_role_grant(id,group_id,role_id,valid_from,status,created_at)"
                            + " VALUES (?,?,?,clock_timestamp()-interval '1"
                            + " hour','ACTIVE',clock_timestamp())",
                    id(),
                    group,
                    role);
        }
        return role;
    }

    private String attribute(
            String subjectType, String subject, String code, String type, String value) {
        String d = id(), a = id();
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "attribute_definition(id,code,name,attribute_target,data_type,multi_valued,sensitive,status,created_at,updated_at)"
                        + " VALUES (?,?,?,"
                        + " ?,?,false,false,'ACTIVE',clock_timestamp(),clock_timestamp())",
                d,
                code,
                code,
                subjectType,
                type);
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "subject_security_attribute(id,subject_type,subject_id,attribute_definition_id,attribute_value,status,created_at,updated_at)"
                        + " VALUES (?,?,?,?,?,'ACTIVE',clock_timestamp(),clock_timestamp())",
                a,
                subjectType,
                subject,
                d,
                value);
        return d;
    }

    private String policy(String effect, String context) {
        String p = id(), v = id();
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "authorization_policy(id,code,name,policy_domain,status,created_at,updated_at)"
                        + " VALUES (?,?,?,'topology','ACTIVE',clock_timestamp(),clock_timestamp())",
                p,
                p,
                p);
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "authorization_policy_version(id,policy_id,version_number,status,effective_from,created_at)"
                        + " VALUES (?,?,1,'ACTIVE',clock_timestamp()-interval '1"
                        + " hour',clock_timestamp())",
                v,
                p);
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "authorization_policy_rule(id,policy_version_id,rule_code,effect,priority,context_expression,status,created_at)"
                        + " VALUES (?,?,?, ?,0,?::jsonb,'ACTIVE',clock_timestamp())",
                id(),
                v,
                id(),
                effect,
                context);
        return p;
    }

    /**
     * The original source verification runs outside a transaction, but the actual
     * issuance/claim replay and subsequent selection must be exercised under a
     * non-owner whose generation relation is SELECT-only.
     */
    private void c3NonownerCapture() {
        String role = "c3_source_claim_app";
        if (!Boolean.TRUE.equals(
                jdbc.queryForObject(
                        "SELECT EXISTS(SELECT 1 FROM pg_roles WHERE rolname=?)",
                        Boolean.class,
                        role))) {
            jdbc.execute("CREATE ROLE " + role + " LOGIN PASSWORD 'synthetic-c3-test-only'");
            jdbc.execute("REVOKE CREATE ON SCHEMA public FROM PUBLIC");
            jdbc.execute("GRANT USAGE ON SCHEMA public TO " + role);
            jdbc.execute(
                    "GRANT SELECT,INSERT,UPDATE,DELETE ON ALL TABLES IN SCHEMA public TO " + role);
            jdbc.execute(
                    "REVOKE ALL ON public.hidra_identity_external_mapping_generation FROM " + role);
            jdbc.execute(
                    "GRANT SELECT ON public.hidra_identity_external_mapping_generation TO " + role);
        }
        var appJdbc =
                new JdbcTemplate(
                        new org.springframework.jdbc.datasource.DriverManagerDataSource(
                                POSTGRES.getJdbcUrl(), role, "synthetic-c3-test-only"));
        var appCatalog =
                new dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
                        .JdbcClaimNameCatalogAdapter(appJdbc);
        // Authentication's source-name hint is selected on a real non-owner connection.
        // During the issuance fence the identical selected generation must be rechecked
        // on the native physical transaction, after taking the non-owner identity.
        doAnswer(
                        i -> {
                            if (TransactionSynchronizationManager.isActualTransactionActive()
                                    && Boolean.TRUE.equals(
                                            jdbc.queryForObject(
                                                    "SELECT current_user=?", Boolean.class, role)))
                                return i.callRealMethod();
                            return appCatalog.selectVersioned(i.getArgument(0));
                        })
                .when(claimCatalog)
                .selectVersioned(anyString());
        new TransactionTemplate(manager)
                .executeWithoutResult(
                        status ->
                                doAnswer(
                                                i -> {
                                                    c3SelectRestrictedRole();
                                                    return i.callRealMethod();
                                                })
                                        .when(claimIssuanceFence)
                                        .prepare(anyString(), any()));
        // The final guarded fixture insert must also be performed without owner rights.
        jdbc.execute("GRANT USAGE ON SCHEMA " + schema + " TO " + role);
        jdbc.execute("GRANT INSERT ON " + receipt() + " TO " + role);
        ReflectionTestUtils.setField(oidc, "verifiedClaimReceiptEnabled", true);
    }

    /**
     * Only this synthetic fixture switches the PostgreSQL current role. The protected
     * transaction still rejects SET/RESET SQL statements; no production escape hatch
     * or alternate authorization implementation is installed. The setting is LOCAL
     * to the physical transaction and automatically resets on completion.
     */
    private void c3SelectRestrictedRole() {
        assertThat(
                        jdbc.queryForObject(
                                "SELECT set_config('role',?,true)",
                                String.class,
                                "c3_source_claim_app"))
                .isEqualTo("c3_source_claim_app");
        assertThat(
                        jdbc.queryForObject(
                                "SELECT current_user='c3_source_claim_app' "
                                        + "AND NOT has_schema_privilege(current_user,'public','CREATE') "
                                        + "AND NOT has_table_privilege(current_user,"
                                        + "'public.hidra_identity_external_mapping_generation','UPDATE')",
                                Boolean.class))
                .isTrue();
    }

    private void c3PermissionMapping(Fixture f, String op, String effect) {
        jdbc.update(
                "INSERT INTO public.hidra_identity_external_permission_mapping"
                        + "(id,identity_provider_id,permission_id,external_permission_code,claim_name,"
                        + "mapping_mode,effect,status,created_at,updated_at)"
                        + " VALUES (?,?,?,'private-original-group','groups','DIRECT_GRANT',?,'ACTIVE',now(),now())",
                id(),
                f.provider(),
                permission(op),
                effect);
    }

    private Prepared c3DirectoryPrepared(Fixture f, String attributeName) {
        var properties =
                new dz.sh.hidra.platform.configuration.HidraLdapSecurityProperties(
                        true,
                        "ldap://synthetic.invalid:389",
                        "dc=synthetic,dc=invalid",
                        "ou=people",
                        "(uid={0})",
                        null,
                        null,
                        null,
                        null);
        var adapter =
                new LdapCredentialVerificationAdapter(
                        mock(org.springframework.ldap.core.support.LdapContextSource.class),
                        properties,
                        verificationRegistry);
        var template = mock(org.springframework.ldap.core.LdapTemplate.class);
        ReflectionTestUtils.setField(adapter, "ldapTemplate", template);
        var context = mock(javax.naming.directory.DirContext.class);
        var attrs = new javax.naming.directory.BasicAttributes(true);
        attrs.put("entryUUID", f.subject());
        attrs.put("uid", f.user());
        if ("binary".equals(attributeName)) attrs.put("memberOf", new byte[] {1, 2});
        else if (attributeName != null)
            attrs.put(attributeName, " private-original-directory-group ");
        try {
            when(context.getAttributes(any(javax.naming.Name.class), any(String[].class)))
                    .thenReturn(attrs);
            when(template.authenticate(
                            anyString(),
                            anyString(),
                            anyString(),
                            any(
                                    org.springframework.ldap.core
                                            .AuthenticatedLdapEntryContextCallback.class)))
                    .thenAnswer(
                            i -> {
                                ((org.springframework.ldap.core
                                                        .AuthenticatedLdapEntryContextCallback)
                                                i.getArgument(3))
                                        .executeWithContext(
                                                context,
                                                new org.springframework.ldap.core
                                                        .LdapEntryIdentification(
                                                        new javax.naming.ldap.LdapName(
                                                                "uid=synthetic,ou=people,dc=synthetic,dc=invalid"),
                                                        new javax.naming.ldap.LdapName(
                                                                "uid=synthetic,ou=people")));
                                return true;
                            });
        } catch (javax.naming.NamingException impossible) {
            throw new IllegalStateException(impossible);
        }
        doAnswer(i -> adapter.verify(i.getArgument(0), i.getArgument(1)))
                .when(directory)
                .verify(f.user(), "synthetic-password");
        ReflectionTestUtils.setField(ldap, "verifiedClaimReceiptEnabled", true);
        return prepare(f);
    }

    private String c3RoleMapping(
            Fixture f, boolean grouped, String effect, String claim, String value) {
        String role = role(f, "author", effect, false);
        jdbc.update("DELETE FROM " + T + "user_role_grant WHERE user_id=?", f.user());
        if (!grouped) {
            jdbc.update(
                    "INSERT INTO "
                            + T
                            + "external_role_mapping"
                            + "(id,identity_provider_id,role_id,external_role_code,claim_name,mapping_mode,scope_type,scope_reference_id,status,created_at,updated_at)"
                            + " VALUES"
                            + " (?,?,?,?,?,'DIRECT_GRANT','FACILITY',?,'ACTIVE',now(),now())",
                    id(),
                    f.provider(),
                    role,
                    value,
                    claim,
                    FACILITY);
            return role;
        }
        String group = id();
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "group(id,code,group_type,status,created_at,updated_at)"
                        + " VALUES (?,?,'LOCAL','ACTIVE',now(),now())",
                group,
                group);
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "group_role_grant"
                        + "(id,group_id,role_id,scope_type,scope_reference_id,valid_from,status,created_at)"
                        + " VALUES (?,?,?,'FACILITY',?,now()-interval '1 hour','ACTIVE',now())",
                id(),
                group,
                role,
                FACILITY);
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "external_group_mapping"
                        + "(id,identity_provider_id,group_id,external_group_name,claim_name,mapping_mode,auto_create_membership,status,created_at,updated_at)"
                        + " VALUES (?,?,?,?,?,'ASSERTION_ONLY',false,'ACTIVE',now(),now())",
                id(),
                f.provider(),
                group,
                value,
                claim);
        attribute("GROUP", group, "qualified-flag", "BOOLEAN", "true");
        jdbc.update(
                "UPDATE "
                        + T
                        + "role_permission_grant SET condition_expression=?::jsonb WHERE role_id=?",
                "{\"op\":\"in\",\"attribute\":\"subject.groupIds\",\"type\":\"STRING\",\"value\":[\""
                        + group
                        + "\"]}",
                role);
        return group;
    }

    private AuthorizationDecisionValue c3Decision(
            Prepared prepared,
            AuthorizationEvaluationRequest request,
            Runnable afterQualification) {
        return executor.execute(
                () -> {
                    c3SelectRestrictedRole();
                    var sealed = participant.qualifyClaims(prepared, request);
                    afterQualification.run();
                    var evidence = reader.loadQualified(sealed, request);
                    if (evidence.grants().stream().anyMatch(g -> g.order() == 3))
                        assertThat(evidence.attributes().get("subject.attributes.qualified-flag"))
                                .isEqualTo(true);
                    assertThat(evidence.toString())
                            .doesNotContain(
                                    "private-original-group", "private-original-directory-group");
                    return new AuthorizationPolicyEvaluator()
                            .evaluate(
                                    request,
                                    evidence,
                                    Instant.parse(
                                            (String)
                                                    evidence.attributes()
                                                            .get("context.evaluatedAt")))
                            .decision();
                },
                em::flush,
                () -> assertThat(jdbc.queryForObject("SELECT 1", Integer.class)).isEqualTo(1));
    }

    private void c3Rejected(Runnable action) {
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

    private void c3AttemptBlockedIssuance(
            Fixture f,
            ClaimNameCatalogPort.VersionedSelection selected,
            AtomicInteger writerPid,
            CountDownLatch started) {
        var transaction = new TransactionTemplate(manager);
        assertThatThrownBy(
                        () ->
                                transaction.executeWithoutResult(
                                        status -> {
                                            c3SelectRestrictedRole();
                                            jdbc.execute("SET LOCAL lock_timeout='250ms'");
                                            writerPid.set(backend());
                                            started.countDown();
                                            claimIssuanceFence.prepare(f.provider(), selected);
                                        }))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void c3QualifiedDirectoryAllMappingFamiliesPreservePositiveAndDenySemantics() {
        for (String type : List.of("LDAP", "ACTIVE_DIRECTORY")) {
            for (String family : List.of("permission", "role", "group")) {
                for (String effect : List.of("GRANT", "DENY")) {
                    cleanEvidence();
                    var f = fixture(type);
                    c3NonownerCapture();
                    if (family.equals("permission")) {
                        c3PermissionMapping(f, "author", effect);
                        jdbc.update(
                                "UPDATE "
                                        + T
                                        + "external_permission_mapping SET"
                                        + " claim_name='memberOf',external_permission_code=? WHERE"
                                        + " identity_provider_id=?",
                                " private-original-directory-group ",
                                f.provider());
                    } else
                        c3RoleMapping(
                                f,
                                family.equals("group"),
                                effect,
                                "memberOf",
                                " private-original-directory-group ");
                    if (effect.equals("DENY")) grant(f, "author", "GRANT", "FACILITY", FACILITY);
                    var prepared = c3DirectoryPrepared(f, "memberOf");
                    assertThat(c3Decision(prepared, request(f, "author", false), () -> {}))
                            .as(type + "/" + family + "/" + effect)
                            .isEqualTo(
                                    effect.equals("DENY")
                                            ? AuthorizationDecisionValue.DENY
                                            : AuthorizationDecisionValue.PERMIT);
                }
            }
        }
    }

    @Test
    void c3QualifiedDirectoryNonmatchOmissionAndRangeCannotErasePotentialDeny() {
        for (String type : List.of("LDAP", "ACTIVE_DIRECTORY")) {
            for (String family : List.of("permission", "role", "group")) {
                for (String variant :
                        List.of("nonmatch", "omitted", "ranged", "option", "binary")) {
                    cleanEvidence();
                    var f = fixture(type);
                    c3NonownerCapture();
                    if (family.equals("permission")) {
                        c3PermissionMapping(f, "author", "DENY");
                        jdbc.update(
                                "UPDATE "
                                        + T
                                        + "external_permission_mapping"
                                        + " SET claim_name='memberOf',external_permission_code='unobserved-deny'"
                                        + " WHERE identity_provider_id=?",
                                f.provider());
                    } else {
                        c3RoleMapping(
                                f, family.equals("group"), "DENY", "memberOf", "unobserved-deny");
                    }
                    grant(f, "author", "GRANT", "FACILITY", FACILITY);
                    String attributeName =
                            switch (variant) {
                                case "omitted" -> null;
                                case "ranged" -> "memberOf;range=0-1499";
                                case "option" -> "memberOf;binary";
                                case "binary" -> "binary";
                                default -> "memberOf";
                            };
                    var prepared = c3DirectoryPrepared(f, attributeName);
                    assertThat(c3Decision(prepared, request(f, "author", false), () -> {}))
                            .as(type + "/" + family + "/" + variant)
                            .isEqualTo(AuthorizationDecisionValue.INDETERMINATE);
                }
            }
        }
    }

    @Test
    void c3QualifiedRoleScopeAndGroupWindowCannotGrantOutsideTheirBounds() {
        for (boolean grouped : List.of(false, true)) {
            cleanEvidence();
            var f = fixture("OIDC");
            c3NonownerCapture();
            c3RoleMapping(f, grouped, "GRANT", "groups", "private-original-group");
            if (grouped)
                jdbc.update(
                        "UPDATE " + T + "group_role_grant SET valid_to=now()-interval '1 minute'");
            else
                jdbc.update(
                        "UPDATE "
                                + T
                                + "external_role_mapping SET scope_reference_id='other-facility'"
                                + " WHERE identity_provider_id=?",
                        f.provider());
            assertThat(c3Decision(prepare(f), request(f, "author", false), () -> {}))
                    .isEqualTo(AuthorizationDecisionValue.DENY);
        }
    }

    @Test
    void c3QualifiedMalformedOidcClaimNeverOverridesPotentialDeny() {
        var f = fixture("OIDC");
        c3NonownerCapture();
        c3PermissionMapping(f, "author", "DENY");
        grant(f, "author", "GRANT", "FACILITY", FACILITY);
        var result =
                completeOidc(
                        f,
                        Instant.now().plusSeconds(300).truncatedTo(ChronoUnit.SECONDS),
                        Map.of("unsupported", "value"));
        ambient(result.accessToken());
        assertThat(c3Decision(participant.prepare(), request(f, "author", false), () -> {}))
                .isEqualTo(AuthorizationDecisionValue.INDETERMINATE);
    }

    @Test
    void c3QualifiedGraphKeepsCombinedRowBudgetAndUncertainConditionsFailClosed() {
        for (boolean overflow : List.of(false, true)) {
            cleanEvidence();
            var f = fixture("OIDC");
            c3NonownerCapture();
            String role = c3RoleMapping(f, false, "GRANT", "groups", "private-original-group");
            if (overflow) {
                jdbc.update(
                        "INSERT INTO "
                                + T
                                + "external_permission_mapping"
                                + "(id,identity_provider_id,permission_id,external_permission_code,claim_name,mapping_mode,effect,status,created_at,updated_at)"
                                + " SELECT ? || '-' ||"
                                + " n,?,?,'other','groups','DIRECT_GRANT','GRANT','ACTIVE',now(),now()"
                                + " FROM generate_series(1,1020) n",
                        id(),
                        f.provider(),
                        permission("author"));
            } else
                jdbc.update(
                        "UPDATE "
                                + T
                                + "role_permission_grant SET condition_expression=?::jsonb WHERE"
                                + " role_id=?",
                        "{\"op\":\"eq\",\"attribute\":\"subject.attributes.missing\",\"type\":\"BOOLEAN\",\"value\":true}",
                        role);
            // Unclassified attribute conditions are rejected by the real temporal census;
            // do not weaken its static grammar merely to obtain a diagnostic decision.
            c3Rejected(() -> c3Decision(prepare(f), request(f, "author", false), () -> {}));
        }
    }

    @Test
    void c3RealMappingWriterWaitsThenOldClaimsRefuseUntilFreshAuthentication() {
        var f = fixture("OIDC");
        c3NonownerCapture();
        c3PermissionMapping(f, "author", "GRANT");
        var prepared = prepare(f);
        String raw =
                ((org.springframework.security.oauth2.server.resource.authentication
                                        .JwtAuthenticationToken)
                                SecurityContextHolder.getContext().getAuthentication())
                        .getToken()
                        .getTokenValue();
        assertThat(
                        c3Decision(
                                prepared,
                                request(f, "author", false),
                                () -> {
                                    try (var other = connection()) {
                                        other.setAutoCommit(false);
                                        try (var statement =
                                                other.prepareStatement(
                                                        "UPDATE "
                                                                + T
                                                                + "external_permission_mapping SET"
                                                                + " external_permission_code=external_permission_code"
                                                                + " || '-changed' WHERE"
                                                                + " identity_provider_id=?")) {
                                            try (var settings = other.createStatement()) {
                                                settings.execute("SET LOCAL lock_timeout='100ms'");
                                            }
                                            statement.setString(1, f.provider());
                                            assertThatThrownBy(statement::executeUpdate)
                                                    .isInstanceOf(SQLException.class)
                                                    .satisfies(
                                                            e ->
                                                                    assertThat(
                                                                                    ((SQLException)
                                                                                                    e)
                                                                                            .getSQLState())
                                                                            .isEqualTo("55P03"));
                                        } finally {
                                            other.rollback();
                                        }
                                    } catch (SQLException failure) {
                                        throw new AssertionError(failure);
                                    }
                                }))
                .isEqualTo(AuthorizationDecisionValue.PERMIT);
        assertThat(
                        jdbc.update(
                                "UPDATE "
                                        + T
                                        + "external_permission_mapping SET"
                                        + " external_permission_code=external_permission_code ||"
                                        + " '-changed' WHERE identity_provider_id=?",
                                f.provider()))
                .isEqualTo(1);
        ambient(raw);
        var stale = participant.prepare();
        c3Rejected(() -> c3Decision(stale, request(f, "author", false), () -> {}));
        assertThat(c3Decision(prepare(f), request(f, "author", false), () -> {}))
                .isEqualTo(AuthorizationDecisionValue.DENY);
        jdbc.update(
                "UPDATE "
                        + T
                        + "external_permission_mapping SET"
                        + " external_permission_code='private-original-group' WHERE"
                        + " identity_provider_id=?",
                f.provider());
        assertThat(c3Decision(prepare(f), request(f, "author", false), () -> {}))
                .isEqualTo(AuthorizationDecisionValue.PERMIT);
    }

    @Test
    void c3AcceptedIssuanceFenceCannotInvertTheQualifiedReaderPrefix() {
        var f = fixture("OIDC");
        c3NonownerCapture();
        c3PermissionMapping(f, "author", "GRANT");
        var prepared = prepare(f);
        var selected = claimCatalog.selectVersioned(f.provider());
        var pool = Executors.newSingleThreadExecutor();
        try {
            var result =
                    c3Decision(
                            prepared,
                            request(f, "author", false),
                            () -> {
                                var started = new CountDownLatch(1);
                                var writerPid = new AtomicInteger();
                                var writer =
                                        pool.submit(
                                                () ->
                                                        c3AttemptBlockedIssuance(
                                                                f, selected, writerPid, started));
                                await(started);
                                int readerPid = backend();
                                boolean observed = false;
                                long until = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(200);
                                while (!observed && System.nanoTime() < until) {
                                    observed =
                                            Boolean.TRUE.equals(
                                                    jdbc.queryForObject(
                                                            "SELECT ?=ANY(pg_blocking_pids(?))",
                                                            Boolean.class,
                                                            readerPid,
                                                            writerPid.get()));
                                }
                                assertThat(observed)
                                        .as("Actual issuance fence waits on this reader")
                                        .isTrue();
                                assertThat(writerPid.get()).isNotEqualTo(readerPid);
                                join(writer);
                            });
            assertThat(result).isEqualTo(AuthorizationDecisionValue.PERMIT);
            new TransactionTemplate(manager)
                    .executeWithoutResult(
                            status -> {
                                c3SelectRestrictedRole();
                                var stamp = claimIssuanceFence.prepare(f.provider(), selected);
                                claimIssuanceFence.requireCurrent(stamp);
                            });
        } finally {
            pool.shutdownNow();
        }
    }

    @Test
    void c3CoverageGenerationAndIssuanceXidCannotBorrowAnOriginalCompanionMac() {
        for (String change : List.of("coverage", "generation", "xid")) {
            cleanEvidence();
            var f = fixture("OIDC");
            c3NonownerCapture();
            c3PermissionMapping(f, "author", "GRANT");
            var prepared = prepare(f);
            doAnswer(
                            i -> {
                                @SuppressWarnings("unchecked")
                                Optional<BoundSourceClaimEvidence> actual =
                                        (Optional<BoundSourceClaimEvidence>) i.callRealMethod();
                                var e = actual.orElseThrow();
                                byte[] mac = e.producerAuthenticator();
                                if (change.equals("mac")) mac[0] ^= 1;
                                return Optional.of(
                                        new BoundSourceClaimEvidence(
                                                change.equals("binding") ? id() : e.bindingId(),
                                                e.evidenceFormat(),
                                                e.mappingGeneration()
                                                        + (change.equals("generation") ? 1 : 0),
                                                e.issuanceTransactionId()
                                                        + (change.equals("xid") ? 1 : 0),
                                                change.equals("coverage")
                                                        ? ClaimCoverage.empty()
                                                        : e.coverage(),
                                                mac));
                            })
                    .when(claimCompanions)
                    .findByBindingId(anyString());
            c3Rejected(() -> c3Decision(prepared, request(f, "author", false), () -> {}));
            reset(claimCompanions);
        }
    }

    @Test
    void c3UnusedQualifiedHandleCannotMoveToANewTransactionOnItsOriginalThread() {
        var f = fixture("OIDC");
        c3NonownerCapture();
        c3PermissionMapping(f, "author", "GRANT");
        var prepared = prepare(f);
        var request = request(f, "author", false);
        var unused = new AtomicReference<QualifiedClaimRead>();
        var xid = new AtomicLong();
        executor.execute(
                () -> {
                    c3SelectRestrictedRole();
                    unused.set(participant.qualifyClaims(prepared, request));
                    xid.set(jdbc.queryForObject("SELECT txid_current()", Long.class));
                    return "qualified-only";
                },
                em::flush,
                () -> assertThat(jdbc.queryForObject("SELECT 1", Integer.class)).isEqualTo(1));
        c3Rejected(
                () ->
                        executor.execute(
                                () -> {
                                    c3SelectRestrictedRole();
                                    assertThat(
                                                    jdbc.queryForObject(
                                                            "SELECT txid_current()", Long.class))
                                            .isNotEqualTo(xid.get());
                                    reader.loadQualified(unused.get(), request);
                                    return "transplanted";
                                },
                                em::flush,
                                () ->
                                        fail(
                                                "Unused handle from another physical transaction cannot publish")));
    }

    @Test
    void c3GenuineQualifiedHandleRejectsAnotherParticipantBeforeAnyOwnerPortCall() {
        var f = fixture("OIDC");
        c3NonownerCapture();
        c3PermissionMapping(f, "author", "GRANT");
        var prepared = prepare(f);
        var request = request(f, "author", false);
        // Only the wrong owner instance is synthetic; the attempted handle was genuinely
        // produced in the native owner transaction. Its ports must not even be consulted.
        var foreignStates = mock(AuthenticationSecurityStateQueryPort.class);
        var foreignIssuer = mock(AccessTokenIssuerPort.class);
        @SuppressWarnings("unchecked")
        var foreignDecoder =
                (org.springframework.beans.factory.ObjectProvider<JwtDecoder>)
                        mock(org.springframework.beans.factory.ObjectProvider.class);
        var other =
                new ProtectedOperationIdentityParticipant(
                        bindings,
                        foreignStates,
                        foreignDecoder,
                        foreignIssuer,
                        verificationRegistry,
                        fence,
                        jdbc,
                        65536);
        c3Rejected(
                () ->
                        executor.execute(
                                () -> {
                                    c3SelectRestrictedRole();
                                    var q = participant.qualifyClaims(prepared, request);
                                    other.consumeClaims(q, request);
                                    return "foreign-owner";
                                },
                                em::flush,
                                () -> fail("Foreign owner must not reach commit guard")));
        verifyNoInteractions(foreignStates, foreignIssuer, foreignDecoder);
    }

    private OperationAuthenticationBinding c3AlterBinding(
            OperationAuthenticationBinding b, String field) {
        return new OperationAuthenticationBinding(
                field.equals("binding") ? id() : b.bindingId(),
                b.formatVersion(),
                field.equals("issuer") ? "other-native" : b.nativeIssuer(),
                field.equals("audience") ? "other-audience" : b.nativeAudience(),
                field.equals("jti") ? id() : b.jti(),
                field.equals("session") ? id() : b.sessionId(),
                field.equals("user") ? id() : b.userId(),
                b.authenticationType(),
                b.sessionProtocol(),
                field.equals("provider") ? id() : b.providerId(),
                b.providerSecurityVersion() + (field.equals("provider-generation") ? 1 : 0),
                field.equals("link") ? id() : b.externalIdentityId(),
                b.externalSubject(),
                b.externalSecurityVersion() + (field.equals("link-generation") ? 1 : 0),
                field.equals("source-issuer") ? "https://other-source.invalid" : b.sourceIssuer(),
                field.equals("source-expiry")
                        ? b.sourceExpiresAt().plusSeconds(1)
                        : b.sourceExpiresAt(),
                b.credentialId(),
                b.credentialSecurityVersion(),
                b.issuedAt(),
                field.equals("native-expiry") ? b.expiresAt().minusSeconds(1) : b.expiresAt(),
                b.producerAuthenticator());
    }

    @Test
    void c3QualifiedReadCannotBorrowTheOriginalNativeAuthenticatorForAnotherTuple() {
        for (String field :
                List.of(
                        "binding",
                        "issuer",
                        "audience",
                        "jti",
                        "session",
                        "user",
                        "provider",
                        "provider-generation",
                        "link",
                        "link-generation",
                        "source-issuer",
                        "source-expiry",
                        "native-expiry")) {
            cleanEvidence();
            var f = fixture("OIDC");
            c3NonownerCapture();
            c3PermissionMapping(f, "author", "GRANT");
            var prepared = prepare(f);
            c3Rejected(
                    () ->
                            c3Decision(
                                    prepared,
                                    request(f, "author", false),
                                    () -> {
                                        doAnswer(
                                                        i -> {
                                                            var actual =
                                                                    (Optional<?>)
                                                                            i.callRealMethod();
                                                            return Optional.of(
                                                                    c3AlterBinding(
                                                                            (OperationAuthenticationBinding)
                                                                                    actual
                                                                                            .orElseThrow(),
                                                                            field));
                                                        })
                                                .when(bindings)
                                                .findByNativeIssuerAndJti(anyString(), anyString());
                                    }));
            reset(bindings);
        }
    }

    @Test
    void c3ProviderReassignmentAdvancesOldEpochAndRequiresFreshAuthentication() {
        var target = fixture("OIDC");
        var f = fixture("OIDC");
        c3NonownerCapture();
        c3PermissionMapping(f, "author", "GRANT");
        var prepared = prepare(f);
        Long generation =
                jdbc.queryForObject(
                        "SELECT generation FROM "
                                + T
                                + "external_mapping_generation WHERE provider_id=?",
                        Long.class,
                        f.provider());
        assertThat(
                        jdbc.update(
                                "UPDATE "
                                        + T
                                        + "external_permission_mapping SET identity_provider_id=? WHERE identity_provider_id=?",
                                target.provider(),
                                f.provider()))
                .isEqualTo(1);
        assertThat(
                        jdbc.queryForObject(
                                "SELECT generation FROM "
                                        + T
                                        + "external_mapping_generation WHERE provider_id=?",
                                Long.class,
                                f.provider()))
                .isGreaterThan(generation);
        var reached = new AtomicBoolean();
        c3Rejected(
                () -> c3Decision(prepared, request(f, "author", false), () -> reached.set(true)));
        assertThat(reached).isTrue();
        jdbc.update(
                "UPDATE "
                        + T
                        + "external_permission_mapping SET identity_provider_id=? WHERE identity_provider_id=?",
                f.provider(),
                target.provider());
        assertThat(c3Decision(prepare(f), request(f, "author", false), () -> {}))
                .isEqualTo(AuthorizationDecisionValue.PERMIT);
    }

    @Test
    void c3PreparedOriginalSourceCannotExtendItsRealPersistedDeadline() throws Exception {
        var f = fixture("OIDC");
        c3NonownerCapture();
        c3PermissionMapping(f, "author", "GRANT");
        var result = completeOidc(f, dbNow().plusSeconds(6).truncatedTo(ChronoUnit.SECONDS));
        ambient(result.accessToken());
        var prepared = participant.prepare();
        Instant sourceExpiry =
                jdbc.queryForObject(
                        "SELECT source_expires_at FROM " + B + " WHERE user_id=?",
                        (row, n) -> row.getTimestamp(1).toInstant(),
                        f.user());
        Instant nativeExpiry =
                jdbc.queryForObject(
                        "SELECT expires_at FROM " + B + " WHERE user_id=?",
                        (row, n) -> row.getTimestamp(1).toInstant(),
                        f.user());
        assertThat(nativeExpiry).isBeforeOrEqualTo(sourceExpiry);
        long bound = System.nanoTime() + TimeUnit.SECONDS.toNanos(8);
        // Outside every protected transaction/budget. Observe real database time;
        // do not alter the server clock, MAC, receipt or native binding.
        while (dbNow().isBefore(sourceExpiry) && System.nanoTime() < bound) Thread.sleep(25);
        assertThat(dbNow()).isAfterOrEqualTo(sourceExpiry);
        c3Rejected(() -> c3Decision(prepared, request(f, "author", false), () -> {}));
    }

    @Test
    void c3QualifiedUserPermissionAndResourceMustRemainExactlyTheOriginalRequest() {
        for (String field : List.of("user", "permission", "resource")) {
            cleanEvidence();
            var f = fixture("OIDC");
            c3NonownerCapture();
            c3PermissionMapping(f, "author", "GRANT");
            var prepared = prepare(f);
            var original = request(f, "author", false);
            var changed =
                    new AuthorizationEvaluationRequest(
                            field.equals("user") ? id() : original.userId(),
                            field.equals("permission")
                                    ? "topology:station-revision:publish"
                                    : original.permissionCode(),
                            original.resourceType(),
                            field.equals("resource")
                                    ? "other-facility"
                                    : original.resourceReferenceId(),
                            original.scope());
            c3Rejected(
                    () ->
                            executor.execute(
                                    () -> {
                                        c3SelectRestrictedRole();
                                        var q = participant.qualifyClaims(prepared, original);
                                        reader.loadQualified(q, changed);
                                        return "substituted-request";
                                    },
                                    em::flush,
                                    () -> fail("Changed request cannot publish")));
        }
    }

    @Test
    void c3InactiveMappedTargetsDoNotConferDenyAuthorityOrGroupFacts() {
        for (boolean grouped : List.of(false, true)) {
            cleanEvidence();
            var f = fixture("OIDC");
            c3NonownerCapture();
            String target = c3RoleMapping(f, grouped, "DENY", "groups", "private-original-group");
            jdbc.update(
                    "UPDATE "
                            + T
                            + (grouped
                                    ? "group SET status='INACTIVE'"
                                    : "role SET status='DISABLED'")
                            + " WHERE id=?",
                    target);
            grant(f, "author", "GRANT", "FACILITY", FACILITY);
            assertThat(c3Decision(prepare(f), request(f, "author", false), () -> {}))
                    .isEqualTo(AuthorizationDecisionValue.PERMIT);
        }
    }

    @Test
    void c3MappedTargetDeleteIsExcludedDuringReadAndReferencedTargetCannotDisappear() {
        var f = fixture("OIDC");
        c3NonownerCapture();
        String role = c3RoleMapping(f, false, "GRANT", "groups", "private-original-group");
        assertThat(
                        c3Decision(
                                prepare(f),
                                request(f, "author", false),
                                () -> {
                                    try (var other = connection()) {
                                        other.setAutoCommit(false);
                                        try (var statement =
                                                other.prepareStatement(
                                                        "DELETE FROM " + T + "role WHERE id=?")) {
                                            try (var settings = other.createStatement()) {
                                                settings.execute("SET LOCAL lock_timeout='100ms'");
                                            }
                                            statement.setString(1, role);
                                            assertThatThrownBy(statement::executeUpdate)
                                                    .isInstanceOf(SQLException.class)
                                                    .satisfies(
                                                            e ->
                                                                    assertThat(
                                                                                    ((SQLException)
                                                                                                    e)
                                                                                            .getSQLState())
                                                                            .isEqualTo("55P03"));
                                        } finally {
                                            other.rollback();
                                        }
                                    } catch (SQLException failure) {
                                        throw new AssertionError(failure);
                                    }
                                }))
                .isEqualTo(AuthorizationDecisionValue.PERMIT);
        try (var other = connection();
                var statement = other.prepareStatement("DELETE FROM " + T + "role WHERE id=?")) {
            statement.setString(1, role);
            assertThatThrownBy(statement::executeUpdate)
                    .isInstanceOf(SQLException.class)
                    .satisfies(
                            e -> assertThat(((SQLException) e).getSQLState()).isEqualTo("23503"));
        } catch (SQLException failure) {
            throw new AssertionError(failure);
        }
    }

    @Test
    void c3UnsupportedLiveMappingCannotFallBackToAnIndependentDirectGrant() {
        var f = fixture("OIDC");
        c3NonownerCapture();
        c3PermissionMapping(f, "author", "GRANT");
        ReflectionTestUtils.setField(oidc, "verifiedClaimReceiptEnabled", false);
        grant(f, "author", "GRANT", "FACILITY", FACILITY);
        var prepared = prepare(f);
        jdbc.update(
                "UPDATE "
                        + T
                        + "external_permission_mapping SET mapping_mode='UNSUPPORTED' WHERE identity_provider_id=?",
                f.provider());
        var reached = new AtomicBoolean();
        c3Rejected(
                () -> c3Decision(prepared, request(f, "author", false), () -> reached.set(true)));
        assertThat(reached).isTrue();
    }

    @Test
    void c3ActualAuthenticationReplacedDuringQualifiedReadRejectsBeforeCommitGuard() {
        var f = fixture("OIDC");
        c3NonownerCapture();
        c3PermissionMapping(f, "author", "GRANT");
        var prepared = prepare(f);
        var request = request(f, "author", false);
        AtomicBoolean companionRead = new AtomicBoolean();
        AtomicBoolean guardCalled = new AtomicBoolean();
        doAnswer(
                        invocation -> {
                            var proof = invocation.callRealMethod();
                            companionRead.set(true);
                            SecurityContextHolder.clearContext();
                            return proof;
                        })
                .when(claimCompanions)
                .findByBindingId(anyString());
        assertThatThrownBy(
                        () ->
                                executor.execute(
                                        () -> {
                                            c3SelectRestrictedRole();
                                            var sealed =
                                                    participant.qualifyClaims(prepared, request);
                                            reader.loadQualified(sealed, request);
                                            return "unfenced-origin";
                                        },
                                        em::flush,
                                        () -> guardCalled.set(true)))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .hasRootCauseInstanceOf(IllegalStateException.class);
        assertThat(companionRead).isTrue();
        assertThat(guardCalled).isFalse();
    }

    @Test
    void c3AuthenticationReplacedDuringFinalCatalogReadRejectsBeforeCommitGuard() {
        var f = fixture("OIDC");
        c3NonownerCapture();
        c3PermissionMapping(f, "author", "GRANT");
        var prepared = prepare(f);
        var request = request(f, "author", false);
        AtomicInteger catalogReads = new AtomicInteger();
        AtomicBoolean finalCatalogRead = new AtomicBoolean();
        AtomicBoolean guardCalled = new AtomicBoolean();

        // Only the already verified source/issuance path may create the handle.
        // Corrupt the actual context after the reader's first post-graph check.
        doAnswer(
                        invocation -> {
                            var selection = invocation.callRealMethod();
                            if (catalogReads.incrementAndGet() == 2) {
                                finalCatalogRead.set(true);
                                SecurityContextHolder.clearContext();
                            }
                            return selection;
                        })
                .when(claimCatalog)
                .selectVersioned(anyString());

        assertThatThrownBy(
                        () ->
                                executor.execute(
                                        () -> {
                                            c3SelectRestrictedRole();
                                            var sealed =
                                                    participant.qualifyClaims(prepared, request);
                                            reader.loadQualified(sealed, request);
                                            return "replaced-after-graph";
                                        },
                                        em::flush,
                                        () -> guardCalled.set(true)))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .satisfies(
                        error ->
                                assertThat(
                                                ((ProtectedTransactionExecutor.ExecutionFailure)
                                                                error)
                                                        .outcome())
                                        .isEqualTo(
                                                ProtectedTransactionExecutor.Outcome
                                                        .REJECTED_BEFORE_COMMIT));
        assertThat(finalCatalogRead).isTrue();
        assertThat(catalogReads.get()).isEqualTo(2);
        assertThat(guardCalled).isFalse();
    }

    @Test
    void c3SameTransactionMappingEditAfterClaimSelectionRejectsBeforeCommitGuard() {
        var f = fixture("OIDC");
        c3NonownerCapture();
        c3PermissionMapping(f, "author", "GRANT");
        var prepared = prepare(f);
        var request = request(f, "author", false);
        AtomicBoolean altered = new AtomicBoolean();
        AtomicBoolean guardCalled = new AtomicBoolean();
        doAnswer(
                        invocation -> {
                            var proof = invocation.callRealMethod();
                            assertThat(
                                            jdbc.update(
                                                    "UPDATE public.hidra_identity_external_permission_mapping "
                                                            + "SET status='INACTIVE',updated_at=clock_timestamp() "
                                                            + "WHERE identity_provider_id=?",
                                                    f.provider()))
                                    .isEqualTo(1);
                            altered.set(true);
                            return proof;
                        })
                .when(claimCompanions)
                .findByBindingId(anyString());
        assertThatThrownBy(
                        () ->
                                executor.execute(
                                        () -> {
                                            c3SelectRestrictedRole();
                                            var sealed =
                                                    participant.qualifyClaims(prepared, request);
                                            reader.loadQualified(sealed, request);
                                            return "stale-epoch";
                                        },
                                        em::flush,
                                        () -> guardCalled.set(true)))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .hasRootCauseInstanceOf(IllegalStateException.class);
        assertThat(altered).isTrue();
        assertThat(guardCalled).isFalse();
    }

    @Test
    void c3VerifiedOriginalOidcSourceCanMatchCurrentPermissionWithoutPublicAssertion() {
        var f = fixture("OIDC");
        c3NonownerCapture();
        c3PermissionMapping(f, "author", "GRANT");
        var prepared = prepare(f);
        var request = request(f, "author", false);
        executor.execute(
                () -> {
                    c3SelectRestrictedRole();
                    var sealed = participant.qualifyClaims(prepared, request);
                    var evidence = reader.loadQualified(sealed, request);

                    assertThat(evidence.failure()).isNull();
                    assertThat(
                                    new AuthorizationPolicyEvaluator()
                                            .evaluate(
                                                    request,
                                                    evidence,
                                                    Instant.parse(
                                                            (String)
                                                                    evidence.attributes()
                                                                            .get(
                                                                                    "context.evaluatedAt")))
                                            .decision())
                            .isEqualTo(AuthorizationDecisionValue.PERMIT);
                    assertThat(evidence.grants())
                            .extracting(AuthorizationEvidence.Grant::order)
                            .contains(5);
                    return "qualified";
                },
                em::flush,
                () -> assertThat(jdbc.queryForObject("SELECT 1", Integer.class)).isEqualTo(1));
        // A replay is a distinct protected operation: Spring correctly marks its physical
        // transaction rollback-only when the once-consumed credential is rejected.
        // Keep the successful qualified read committed above, and prove that a replay
        // cannot reach the mandatory final commit guard.
        var replayPrepared = prepare(f);
        assertThatThrownBy(
                        () ->
                                executor.execute(
                                        () -> {
                                            c3SelectRestrictedRole();
                                            var replay =
                                                    participant.qualifyClaims(
                                                            replayPrepared, request);
                                            assertThat(
                                                            reader.loadQualified(replay, request)
                                                                    .failure())
                                                    .isNull();
                                            reader.loadQualified(replay, request);
                                            return "replayed";
                                        },
                                        em::flush,
                                        () -> fail("Claim replay must not reach the commit guard")))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .hasCauseInstanceOf(
                        org.springframework.dao.InvalidDataAccessApiUsageException.class)
                .satisfies(
                        error ->
                                assertThat(
                                                ((ProtectedTransactionExecutor.ExecutionFailure)
                                                                error)
                                                        .outcome())
                                        .isEqualTo(
                                                ProtectedTransactionExecutor.Outcome
                                                        .REJECTED_BEFORE_COMMIT));
    }

    @Test
    void c3VerifiedOriginalOidcDenyOverridesIndependentDirectPermit() {
        var f = fixture("OIDC");
        c3NonownerCapture();
        c3PermissionMapping(f, "author", "DENY");
        grant(f, "author", "GRANT", "FACILITY", FACILITY);
        var prepared = prepare(f);
        var request = request(f, "author", false);
        executor.execute(
                () -> {
                    c3SelectRestrictedRole();
                    var sealed = participant.qualifyClaims(prepared, request);
                    var evidence = reader.loadQualified(sealed, request);
                    assertThat(evidence.failure()).isNull();
                    assertThat(evidence.grants())
                            .extracting(AuthorizationEvidence.Grant::order)
                            .contains(0, 5);
                    assertThat(
                                    new AuthorizationPolicyEvaluator()
                                            .evaluate(
                                                    request,
                                                    evidence,
                                                    Instant.parse(
                                                            (String)
                                                                    evidence.attributes()
                                                                            .get(
                                                                                    "context.evaluatedAt")))
                                            .decision())
                            .isEqualTo(AuthorizationDecisionValue.DENY);
                    return "verified-deny";
                },
                em::flush,
                () -> assertThat(jdbc.queryForObject("SELECT 1", Integer.class)).isEqualTo(1));
    }

    @Test
    void c3MappingGenerationMutationInvalidatesAlreadyIssuedOidcClaimReceipt() {
        var f = fixture("OIDC");
        c3NonownerCapture();
        c3PermissionMapping(f, "author", "DENY");
        grant(f, "author", "GRANT", "FACILITY", FACILITY);
        var prepared = prepare(f);
        var request = request(f, "author", false);
        Long issuedGeneration =
                jdbc.queryForObject(
                        "SELECT generation FROM public.hidra_identity_external_mapping_generation"
                                + " WHERE provider_id=?",
                        Long.class,
                        f.provider());
        assertThat(issuedGeneration).isPositive();
        assertThat(
                        jdbc.update(
                                "UPDATE public.hidra_identity_external_permission_mapping"
                                        + " SET status='INACTIVE',updated_at=clock_timestamp()"
                                        + " WHERE identity_provider_id=?",
                                f.provider()))
                .isEqualTo(1);
        assertThat(
                        jdbc.queryForObject(
                                "SELECT generation"
                                        + " FROM public.hidra_identity_external_mapping_generation"
                                        + " WHERE provider_id=?",
                                Long.class,
                                f.provider()))
                .isGreaterThan(issuedGeneration);

        AtomicBoolean reachedQualifiedReader = new AtomicBoolean();
        assertThatThrownBy(
                        () ->
                                executor.execute(
                                        () -> {
                                            c3SelectRestrictedRole();
                                            var sealed =
                                                    participant.qualifyClaims(prepared, request);
                                            reachedQualifiedReader.set(true);
                                            reader.loadQualified(sealed, request);
                                            return "stale-claim-accepted";
                                        },
                                        em::flush,
                                        () ->
                                                fail(
                                                        "Stale source claims must never reach the commit guard")))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .satisfies(
                        error ->
                                assertThat(
                                                ((ProtectedTransactionExecutor.ExecutionFailure)
                                                                error)
                                                        .outcome())
                                        .isEqualTo(
                                                ProtectedTransactionExecutor.Outcome
                                                        .REJECTED_BEFORE_COMMIT));
        assertThat(reachedQualifiedReader).isTrue();
    }

    @Test
    void c3NewMappingClaimRequiresFreshOidcAuthenticationBeforeDirectPermit() {
        var f = fixture("OIDC");
        c3NonownerCapture();
        c3PermissionMapping(f, "author", "GRANT");
        grant(f, "author", "GRANT", "FACILITY", FACILITY);
        var stale = prepare(f);
        var request = request(f, "author", false);
        Long previousGeneration =
                jdbc.queryForObject(
                        "SELECT generation FROM public.hidra_identity_external_mapping_generation"
                                + " WHERE provider_id=?",
                        Long.class,
                        f.provider());
        assertThat(previousGeneration).isPositive();
        assertThat(
                        jdbc.update(
                                "INSERT INTO public.hidra_identity_external_permission_mapping"
                                        + "(id,identity_provider_id,permission_id,external_permission_code,"
                                        + "claim_name,mapping_mode,effect,status,created_at,updated_at)"
                                        + " VALUES (?,?,?,'unobserved-deny','new-claim-name',"
                                        + "'DIRECT_GRANT','DENY','ACTIVE',now(),now())",
                                id(),
                                f.provider(),
                                permission("author")))
                .isEqualTo(1);
        assertThat(
                        jdbc.queryForObject(
                                "SELECT generation FROM public.hidra_identity_external_mapping_generation"
                                        + " WHERE provider_id=?",
                                Long.class,
                                f.provider()))
                .isGreaterThan(previousGeneration);

        // Current direct grants cannot upgrade an older claim-coverage generation.
        assertThatThrownBy(
                        () ->
                                executor.execute(
                                        () -> {
                                            c3SelectRestrictedRole();
                                            var sealed = participant.qualifyClaims(stale, request);
                                            reader.loadQualified(sealed, request);
                                            return "stale-coverage";
                                        },
                                        em::flush,
                                        () ->
                                                fail(
                                                        "Changed claim catalog must reject before commit")))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .satisfies(
                        thrown ->
                                assertThat(
                                                ((ProtectedTransactionExecutor.ExecutionFailure)
                                                                thrown)
                                                        .outcome())
                                        .isEqualTo(
                                                ProtectedTransactionExecutor.Outcome
                                                        .REJECTED_BEFORE_COMMIT));

        // Fresh verified source captures the new name as ABSENT; it is not a DENY.
        var fresh = prepare(f);
        executor.execute(
                () -> {
                    c3SelectRestrictedRole();
                    var sealed = participant.qualifyClaims(fresh, request);
                    var evidence = reader.loadQualified(sealed, request);
                    assertThat(evidence.failure()).isNull();
                    assertThat(evidence.grants())
                            .extracting(AuthorizationEvidence.Grant::order)
                            .contains(0, 5);
                    assertThat(
                                    new AuthorizationPolicyEvaluator()
                                            .evaluate(
                                                    request,
                                                    evidence,
                                                    Instant.parse(
                                                            (String)
                                                                    evidence.attributes()
                                                                            .get(
                                                                                    "context.evaluatedAt")))
                                            .decision())
                            .isEqualTo(AuthorizationDecisionValue.PERMIT);
                    return "fresh-coverage";
                },
                em::flush,
                () -> assertThat(jdbc.queryForObject("SELECT 1", Integer.class)).isEqualTo(1));
    }

    @Test
    void c3LegacyOidcWithoutCompanionCannotPromotePotentialUnknownDeny() {
        var f = fixture("OIDC");
        c3NonownerCapture();
        ReflectionTestUtils.setField(oidc, "verifiedClaimReceiptEnabled", false);
        c3PermissionMapping(f, "author", "DENY");
        grant(f, "author", "GRANT", "FACILITY", FACILITY);
        var prepared = prepare(f);
        var request = request(f, "author", false);
        executor.execute(
                () -> {
                    c3SelectRestrictedRole();
                    var sealed = participant.qualifyClaims(prepared, request);
                    var result = reader.loadQualified(sealed, request);

                    assertThat(result.failure()).isEqualTo("EVIDENCE_INDETERMINATE");
                    assertThat(
                                    new AuthorizationPolicyEvaluator()
                                            .evaluate(
                                                    request,
                                                    result,
                                                    Instant.parse(
                                                            (String)
                                                                    result.attributes()
                                                                            .get(
                                                                                    "context.evaluatedAt")))
                                            .decision())
                            .isEqualTo(AuthorizationDecisionValue.INDETERMINATE);
                    return "fail-closed";
                },
                em::flush,
                () -> assertThat(jdbc.queryForObject("SELECT 1", Integer.class)).isEqualTo(1));
    }

    @Test
    void c3OriginalOidcGroupAndRoleClaimsRequireFencedCurrentMappings() {
        var f = fixture("OIDC");
        c3NonownerCapture();
        String roleId = id(), groupId = id();
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "role(id,code,role_type,status,created_at,updated_at)"
                        + " VALUES (?,?,'BUSINESS','ACTIVE',now(),now())",
                roleId,
                roleId);
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "role_permission_grant(id,role_id,permission_id,effect,valid_from,status,created_at)"
                        + " VALUES (?,?,?,'GRANT',now()-interval '1 hour','ACTIVE',now())",
                id(),
                roleId,
                permission("author"));
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "group(id,code,group_type,status,created_at,updated_at)"
                        + " VALUES (?,?,'LOCAL','ACTIVE',now(),now())",
                groupId,
                groupId);
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "group_role_grant(id,group_id,role_id,valid_from,status,created_at)"
                        + " VALUES (?,?,?,now()-interval '1 hour','ACTIVE',now())",
                id(),
                groupId,
                roleId);
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "external_group_mapping(id,identity_provider_id,group_id,external_group_name,"
                        + "claim_name,mapping_mode,auto_create_membership,status,created_at,updated_at)"
                        + " VALUES (?,?,?,'private-original-group','groups','ASSERTION_ONLY',false,"
                        + "'ACTIVE',now(),now())",
                id(),
                f.provider(),
                groupId);
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "external_role_mapping(id,identity_provider_id,role_id,external_role_code,"
                        + "claim_name,mapping_mode,status,created_at,updated_at)"
                        + " VALUES (?,?,?,'private-original-group','groups','DIRECT_GRANT',"
                        + "'ACTIVE',now(),now())",
                id(),
                f.provider(),
                roleId);
        var request = request(f, "author", false);
        var prepared = prepare(f);
        executor.execute(
                () -> {
                    c3SelectRestrictedRole();
                    var qualified = participant.qualifyClaims(prepared, request);
                    var evidence = reader.loadQualified(qualified, request);
                    assertThat(evidence.failure()).isNull();
                    assertThat(evidence.grants())
                            .extracting(AuthorizationEvidence.Grant::order)
                            .contains(3, 4)
                            .doesNotContain(0, 1, 2, 5);
                    assertThat(
                                    new AuthorizationPolicyEvaluator()
                                            .evaluate(
                                                    request,
                                                    evidence,
                                                    Instant.parse(
                                                            (String)
                                                                    evidence.attributes()
                                                                            .get(
                                                                                    "context.evaluatedAt")))
                                            .decision())
                            .isEqualTo(AuthorizationDecisionValue.PERMIT);
                    return "mapped-group-and-role";
                },
                em::flush,
                () -> assertThat(jdbc.queryForObject("SELECT 1", Integer.class)).isEqualTo(1));
    }

    @Test
    void c3QualifiedCredentialRefusesAlternateScopeBeforeCommitGuard() {
        var f = fixture("OIDC");
        c3NonownerCapture();
        c3PermissionMapping(f, "author", "GRANT");
        var prepared = prepare(f);
        var request = request(f, "author", false);
        AtomicBoolean qualified = new AtomicBoolean(), guardCalled = new AtomicBoolean();
        assertThatThrownBy(
                        () ->
                                executor.execute(
                                        () -> {
                                            c3SelectRestrictedRole();
                                            var sealed =
                                                    participant.qualifyClaims(prepared, request);
                                            qualified.set(true);
                                            reader.loadQualified(
                                                    sealed, request(f, "author", true));
                                            return "wrong-scope";
                                        },
                                        em::flush,
                                        () -> guardCalled.set(true)))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .satisfies(
                        error ->
                                assertThat(
                                                ((ProtectedTransactionExecutor.ExecutionFailure)
                                                                error)
                                                        .outcome())
                                        .isEqualTo(
                                                ProtectedTransactionExecutor.Outcome
                                                        .REJECTED_BEFORE_COMMIT));
        assertThat(qualified).isTrue();
        assertThat(guardCalled).isFalse();
    }

    @Test
    void c3CrossThreadClaimConsumeFailsWithDistinctTransactionAndCannotBeReused() {
        var f = fixture("OIDC");
        c3NonownerCapture();
        c3PermissionMapping(f, "author", "GRANT");
        var prepared = prepare(f);
        var request = request(f, "author", false);
        var actual = SecurityContextHolder.getContext().getAuthentication();
        AtomicBoolean wrongThreadAttempted = new AtomicBoolean();
        AtomicBoolean wrongThreadRefused = new AtomicBoolean();
        AtomicBoolean guardCalled = new AtomicBoolean();

        assertThatThrownBy(
                        () ->
                                executor.execute(
                                        () -> {
                                            c3SelectRestrictedRole();
                                            var sealed =
                                                    participant.qualifyClaims(prepared, request);
                                            int ownerPid =
                                                    jdbc.queryForObject(
                                                            "SELECT pg_backend_pid()",
                                                            Integer.class);
                                            var worker = Executors.newSingleThreadExecutor();
                                            try {
                                                Future<Boolean> attempt =
                                                        worker.submit(
                                                                () -> {
                                                                    SecurityContextHolder
                                                                            .getContext()
                                                                            .setAuthentication(
                                                                                    actual);
                                                                    try {
                                                                        assertThatThrownBy(
                                                                                        () ->
                                                                                                tx().execute(
                                                                                                                status -> {
                                                                                                                    assertThat(
                                                                                                                                    jdbc
                                                                                                                                            .queryForObject(
                                                                                                                                                    "SELECT pg_backend_pid()",
                                                                                                                                                    Integer
                                                                                                                                                            .class))
                                                                                                                            .isNotEqualTo(
                                                                                                                                    ownerPid);
                                                                                                                    wrongThreadAttempted
                                                                                                                            .set(
                                                                                                                                    true);
                                                                                                                    return participant
                                                                                                                            .consumeClaims(
                                                                                                                                    sealed,
                                                                                                                                    request);
                                                                                                                }))
                                                                                .isInstanceOf(
                                                                                        AccessDeniedException
                                                                                                .class);
                                                                        wrongThreadRefused.set(
                                                                                true);
                                                                        return true;
                                                                    } finally {
                                                                        SecurityContextHolder
                                                                                .clearContext();
                                                                    }
                                                                });
                                                assertThat(attempt.get(10, TimeUnit.SECONDS))
                                                        .isTrue();
                                            } catch (InterruptedException interrupted) {
                                                Thread.currentThread().interrupt();
                                                throw new AssertionError(interrupted);
                                            } catch (ExecutionException
                                                    | TimeoutException failure) {
                                                throw new AssertionError(failure);
                                            } finally {
                                                worker.shutdownNow();
                                            }
                                            // Failed cross-thread consumption burns the one-shot
                                            // handle:
                                            // it cannot be promoted in its original physical
                                            // transaction.
                                            reader.loadQualified(sealed, request);
                                            return "cross-thread-credential-reused";
                                        },
                                        em::flush,
                                        () -> guardCalled.set(true)))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .satisfies(
                        error ->
                                assertThat(
                                                ((ProtectedTransactionExecutor.ExecutionFailure)
                                                                error)
                                                        .outcome())
                                        .isEqualTo(
                                                ProtectedTransactionExecutor.Outcome
                                                        .REJECTED_BEFORE_COMMIT));
        assertThat(wrongThreadAttempted).isTrue();
        assertThat(wrongThreadRefused).isTrue();
        assertThat(guardCalled).isFalse();
    }

    @Test
    void c3ConsumedClaimCredentialCannotBeReplayedInAnotherPhysicalTransaction() {
        var f = fixture("OIDC");
        c3NonownerCapture();
        c3PermissionMapping(f, "author", "GRANT");
        var prepared = prepare(f);
        var request = request(f, "author", false);
        AtomicReference<QualifiedClaimRead> consumed = new AtomicReference<>();
        executor.execute(
                () -> {
                    c3SelectRestrictedRole();
                    var qualified = participant.qualifyClaims(prepared, request);
                    assertThat(reader.loadQualified(qualified, request).failure()).isNull();
                    consumed.set(qualified);
                    return "first-transaction";
                },
                em::flush,
                () -> assertThat(jdbc.queryForObject("SELECT 1", Integer.class)).isEqualTo(1));

        AtomicBoolean guardCalled = new AtomicBoolean();
        assertThatThrownBy(
                        () ->
                                executor.execute(
                                        () -> {
                                            c3SelectRestrictedRole();
                                            reader.loadQualified(consumed.get(), request);
                                            return "reused-in-new-transaction";
                                        },
                                        em::flush,
                                        () -> guardCalled.set(true)))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .satisfies(
                        error ->
                                assertThat(
                                                ((ProtectedTransactionExecutor.ExecutionFailure)
                                                                error)
                                                        .outcome())
                                        .isEqualTo(
                                                ProtectedTransactionExecutor.Outcome
                                                        .REJECTED_BEFORE_COMMIT));
        assertThat(guardCalled).isFalse();
    }

    /**
     * Negative repository-read fault injection only: the native binding, receipt MAC
     * and secured physical transaction are genuine. No new producer authentication
     * is minted and the protected commit guard must never be reached.
     */
    private void c3CorruptClaimCompanionRejectedBeforeCommitGuard(boolean corruptMac) {
        var f = fixture("OIDC");
        c3NonownerCapture();
        c3PermissionMapping(f, "author", "GRANT");
        var prepared = prepare(f);
        var request = request(f, "author", false);
        AtomicBoolean substituted = new AtomicBoolean();
        AtomicBoolean guardCalled = new AtomicBoolean();

        doAnswer(
                        invocation -> {
                            var stored = (Optional<?>) invocation.callRealMethod();
                            var authentic = (BoundSourceClaimEvidence) stored.orElseThrow();
                            byte[] authenticator = authentic.producerAuthenticator();
                            String bindingId = authentic.bindingId();
                            if (corruptMac) authenticator[0] ^= 1;
                            else bindingId = id();
                            substituted.set(true);
                            return Optional.of(
                                    new BoundSourceClaimEvidence(
                                            bindingId,
                                            authentic.evidenceFormat(),
                                            authentic.mappingGeneration(),
                                            authentic.issuanceTransactionId(),
                                            authentic.coverage(),
                                            authenticator));
                        })
                .when(claimCompanions)
                .findByBindingId(anyString());

        assertThatThrownBy(
                        () ->
                                executor.execute(
                                        () -> {
                                            c3SelectRestrictedRole();
                                            var sealed =
                                                    participant.qualifyClaims(prepared, request);
                                            reader.loadQualified(sealed, request);
                                            return "unverified-companion";
                                        },
                                        em::flush,
                                        () -> guardCalled.set(true)))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .satisfies(
                        error ->
                                assertThat(
                                                ((ProtectedTransactionExecutor.ExecutionFailure)
                                                                error)
                                                        .outcome())
                                        .isEqualTo(
                                                ProtectedTransactionExecutor.Outcome
                                                        .REJECTED_BEFORE_COMMIT));
        assertThat(substituted).isTrue();
        assertThat(guardCalled).isFalse();
    }

    @Test
    void c3TamperedCompanionMacRejectsBeforeCommitGuard() {
        c3CorruptClaimCompanionRejectedBeforeCommitGuard(true);
    }

    @Test
    void c3SwappedCompanionBindingRejectsBeforeCommitGuard() {
        c3CorruptClaimCompanionRejectedBeforeCommitGuard(false);
    }

    @Test
    void allFourRealBoundActorsLoadDatabaseAuthorityAtFencedSample() {
        for (String type : List.of("LOCAL", "LDAP", "ACTIVE_DIRECTORY", "OIDC")) {
            var f = fixture(type);
            for (String op : OPS) {
                both(f, op);
                permits(assess(f, op));
            }
        }
    }

    @Test
    void bothCompanyAndFacilityRequireIndependentPermits() {
        var f = fixture("LOCAL");
        grant(f, "author", "GRANT", "CUSTOM", "topology-company:" + COMPANY);
        var first = assess(f, "author");
        assertThat(first.company()).isEqualTo(AuthorizationDecisionValue.PERMIT);
        assertThat(first.facility()).isEqualTo(AuthorizationDecisionValue.DENY);
        grant(f, "author", "GRANT", "FACILITY", FACILITY);
        permits(assess(f, "author"));
        jdbc.update(
                "DELETE FROM "
                        + T
                        + "user_permission_grant WHERE user_id=? AND scope_type='CUSTOM'",
                f.user());
        var second = assess(f, "author");
        assertThat(second.company()).isEqualTo(AuthorizationDecisionValue.DENY);
        assertThat(second.facility()).isEqualTo(AuthorizationDecisionValue.PERMIT);
    }

    @Test
    void directRoleAndGroupDeniesOverridePermit() {
        for (int path = 0; path < 3; path++) {
            cleanEvidence();
            var f = fixture("LOCAL");
            both(f, "publish");
            if (path == 0) grant(f, "publish", "DENY", "GLOBAL", null);
            else role(f, "publish", "DENY", path == 2);
            var o = assess(f, "publish");
            assertThat(o.company()).isEqualTo(AuthorizationDecisionValue.DENY);
            assertThat(o.facility()).isEqualTo(AuthorizationDecisionValue.DENY);
        }
        cleanEvidence();
        var f = fixture("LOCAL");
        role(f, "publish", "GRANT", false);
        role(f, "publish", "GRANT", true);
        permits(assess(f, "publish"));
        Prepared p = prepare(f);
        AtomicReference<Contribution> c = new AtomicReference<>();
        executor.execute(
                () -> {
                    c.set(participant.contribute(p));
                    var e =
                            reader.load(
                                    c.get().evidence(),
                                    request(f, "publish", false),
                                    c.get().backendPid(),
                                    c.get().transactionId());
                    assertThat(e.grants())
                            .extracting(AuthorizationEvidence.Grant::order)
                            .containsExactly(1, 2);
                    return "orders";
                },
                em::flush,
                () -> guard(c.get()));
    }

    @Test
    void scopeMismatchAndIneligibleOwnersNeverGrant() {
        var f = fixture("LOCAL");
        grant(f, "author", "GRANT", "FACILITY", "other-facility");
        grant(f, "author", "GRANT", "CUSTOM", "topology-company:other");
        assertThat(assess(f, "author").facility()).isEqualTo(AuthorizationDecisionValue.DENY);
        String role = role(f, "author", "GRANT", true);
        jdbc.update("UPDATE " + T + "role SET status='DISABLED' WHERE id=?", role);
        assertThat(assess(f, "author").company()).isEqualTo(AuthorizationDecisionValue.DENY);
        both(f, "author");
        var prepared = prepare(f);
        jdbc.update("UPDATE " + P + " SET status='INACTIVE' WHERE id=?", f.provider());
        assertThatThrownBy(() -> assess(f, "author", prepared, () -> {}))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .hasRootCauseInstanceOf(AccessDeniedException.class);
    }

    @Test
    void synchronizedMembershipSourcesAreReadFresh() {
        var source = fixture("LDAP");
        var f = fixture("LOCAL");
        jdbc.update("UPDATE " + P + " SET status='ACTIVE' WHERE id=?", source.provider());
        jdbc.update(
                "INSERT INTO "
                        + E
                        + "(id,user_id,identity_provider_id,external_subject,status,created_at,updated_at)"
                        + " VALUES (?,?,?,?,'LINKED',clock_timestamp(),clock_timestamp())",
                id(),
                f.user(),
                source.provider(),
                id());
        role(f, "author", "GRANT", true);
        String group =
                jdbc.queryForObject(
                        "SELECT group_id FROM " + T + "user_group_membership WHERE user_id=?",
                        String.class,
                        f.user());
        String mapping = id();
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "external_group_mapping(id,identity_provider_id,group_id,external_group_name,mapping_mode,auto_create_membership,status,created_at,updated_at)"
                        + " VALUES"
                        + " (?,?,?,'synthetic','SYNC_MEMBERSHIP',false,'ACTIVE',clock_timestamp(),clock_timestamp())",
                mapping,
                source.provider(),
                group);
        jdbc.update(
                "UPDATE "
                        + T
                        + "user_group_membership SET"
                        + " membership_type='EXTERNAL_SYNC',source_provider_id=?,source_mapping_id=?"
                        + " WHERE user_id=?",
                source.provider(),
                mapping,
                f.user());
        permits(assess(f, "author"));
        jdbc.update("UPDATE " + P + " SET status='INACTIVE' WHERE id=?", source.provider());
        assertThat(assess(f, "author").facility()).isEqualTo(AuthorizationDecisionValue.DENY);
        jdbc.update("UPDATE " + P + " SET status='ACTIVE' WHERE id=?", source.provider());
        permits(assess(f, "author"));
        jdbc.update(
                "UPDATE " + T + "external_group_mapping SET status='INACTIVE' WHERE id=?", mapping);
        assertThat(assess(f, "author").company()).isEqualTo(AuthorizationDecisionValue.DENY);
    }

    @Test
    void typedAttributesAndPolicyConstraintsUseDatabaseSample() {
        var f = fixture("OIDC");
        both(f, "author");
        attribute("USER", f.user(), "number", "NUMBER", "42.00");
        attribute("EXTERNAL_IDENTITY", f.link(), "json", "JSON", "{\"nested\":[true,null,2]}");
        role(f, "author", "GRANT", true);
        String group =
                jdbc.queryForObject(
                        "SELECT group_id FROM " + T + "user_group_membership WHERE user_id=?",
                        String.class,
                        f.user());
        attribute("GROUP", group, "flag", "BOOLEAN", "true");
        policy(
                "CONSTRAIN",
                "{\"op\":\"eq\",\"attribute\":\"context.scopeType\",\"type\":\"STRING\",\"value\":\"CUSTOM\"}");
        var o = assess(f, "author");
        assertThat(o.company()).isEqualTo(AuthorizationDecisionValue.PERMIT);
        assertThat(o.facility()).isEqualTo(AuthorizationDecisionValue.DENY);
        var p = prepare(f);
        AtomicReference<Contribution> c = new AtomicReference<>();
        executor.execute(
                () -> {
                    c.set(participant.contribute(p));
                    var e =
                            reader.load(
                                    c.get().evidence(),
                                    request(f, "author", true),
                                    c.get().backendPid(),
                                    c.get().transactionId());
                    assertThat(e.attributes().get("subject.attributes.number"))
                            .isEqualTo(new java.math.BigDecimal("42"));
                    assertThat(e.attributes().get("subject.attributes.flag")).isEqualTo(true);
                    Map<?, ?> json = (Map<?, ?>) e.attributes().get("subject.attributes.json");
                    assertThatThrownBy(() -> ((List<?>) json.get("nested")).clear())
                            .isInstanceOf(UnsupportedOperationException.class);
                    assertThat(e.rules().getFirst().expressions().subList(0, 3))
                            .containsOnlyNulls();
                    return "typed";
                },
                em::flush,
                () -> guard(c.get()));
        attribute("USER", f.user(), "number", "NUMBER", "43");
        assertThat(assess(f, "author").company())
                .isEqualTo(AuthorizationDecisionValue.INDETERMINATE);
    }

    @Test
    void oidcAndNewClaimMappingsRemainIndeterminateWithoutCoverage() {
        var f = fixture("OIDC");
        both(f, "author");
        var p = prepare(f);
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "external_permission_mapping(id,identity_provider_id,permission_id,external_permission_code,claim_name,mapping_mode,effect,status,created_at,updated_at)"
                        + " VALUES"
                        + " (?,?,?,'unrelated','new-claim','DIRECT_GRANT','DENY','ACTIVE',clock_timestamp(),clock_timestamp())",
                id(),
                f.provider(),
                permission("transfer"));
        var o = assess(f, "author", p, () -> {});
        assertThat(o.company()).isEqualTo(AuthorizationDecisionValue.INDETERMINATE);
        assertThat(o.facility()).isEqualTo(AuthorizationDecisionValue.INDETERMINATE);
        grant(f, "author", "DENY", "GLOBAL", null);
        assertThat(assess(f, "author").company()).isEqualTo(AuthorizationDecisionValue.DENY);
        jdbc.update("UPDATE " + T + "external_permission_mapping SET claim_name=NULL");
        assertThat(assess(f, "author").company()).isEqualTo(AuthorizationDecisionValue.DENY);
    }

    @Test
    void staleJpaAndEarlierSnapshotsCannotSupplyAuthority() throws Exception {
        var f = fixture("LOCAL");
        String grant = grant(f, "author", "GRANT", "GLOBAL", null);
        permits(assess(f, "author"));
        var p = prepare(f);
        try (var writer = connection()) {
            scalar(writer, "SELECT 1");
            writer.commit();
            AtomicReference<Contribution> saved = new AtomicReference<>();
            String result =
                    executor.execute(
                            () -> {
                                var cached =
                                        em.find(
                                                dz.sh.hidra.modules.identity.infrastructure
                                                        .persistence.entity
                                                        .UserPermissionGrantJpaEntity.class,
                                                grant);
                                assertThat(cached.status()).isEqualTo(GrantStatus.ACTIVE);
                                try {
                                    update(
                                            writer,
                                            "UPDATE "
                                                    + T
                                                    + "user_permission_grant SET status='REVOKED'"
                                                    + " WHERE id=?",
                                            grant);
                                    writer.commit();
                                } catch (SQLException ex) {
                                    throw new AssertionError(ex);
                                }
                                assertThat(
                                                em.find(
                                                        dz.sh.hidra.modules.identity.infrastructure
                                                                .persistence.entity
                                                                .UserPermissionGrantJpaEntity.class,
                                                        grant))
                                        .isSameAs(cached);
                                assertThat(cached.status()).isEqualTo(GrantStatus.ACTIVE);
                                var c = participant.contribute(p);
                                saved.set(c);
                                for (boolean company : List.of(true, false)) {
                                    var request = request(f, "author", company);
                                    var fresh =
                                            reader.load(
                                                    c.evidence(),
                                                    request,
                                                    c.backendPid(),
                                                    c.transactionId());
                                    assertThat(
                                                    new AuthorizationPolicyEvaluator()
                                                            .evaluate(
                                                                    request,
                                                                    fresh,
                                                                    c.evidence().observedAt())
                                                            .decision())
                                            .isEqualTo(AuthorizationDecisionValue.DENY);
                                }
                                return "fresh-denial";
                            },
                            em::flush,
                            () -> guard(saved.get()));
            assertThat(result).isEqualTo("fresh-denial");
        }
    }

    @Test
    void committedRevocationBeforeFencesRemovesPermit() {
        var f = fixture("LOCAL");
        both(f, "author");
        permits(assess(f, "author"));
        var prepared = prepare(f);
        jdbc.update(
                "UPDATE " + T + "user_permission_grant SET status='REVOKED' WHERE user_id=?",
                f.user());
        var o = assess(f, "author", prepared, () -> {});
        assertThat(o.company()).isEqualTo(AuthorizationDecisionValue.DENY);
        assertThat(o.facility()).isEqualTo(AuthorizationDecisionValue.DENY);
    }

    @Test
    void writersAfterAdmissionBlockUntilRelease() throws Exception {
        var f = fixture("LOCAL");
        both(f, "author");
        var prepared = prepare(f);
        CountDownLatch start = new CountDownLatch(1), ready = new CountDownLatch(1);
        AtomicInteger writerPid = new AtomicInteger();
        try (var writer = connection();
                var observer = connection();
                var pool = Executors.newSingleThreadExecutor()) {
            writerPid.set((int) scalar(writer, "SELECT pg_backend_pid()"));
            writer.commit();
            scalar(observer, "SELECT 1");
            observer.commit();
            Future<Integer> future =
                    pool.submit(
                            () -> {
                                ready.countDown();
                                await(start);
                                try (var st =
                                        writer.prepareStatement(
                                                "UPDATE "
                                                        + T
                                                        + "user_permission_grant SET"
                                                        + " grant_reason='writer' WHERE"
                                                        + " user_id=?")) {
                                    st.setString(1, f.user());
                                    int n = st.executeUpdate();
                                    writer.commit();
                                    return n;
                                }
                            });
            await(ready);
            permits(
                    assess(
                            f,
                            "author",
                            prepared,
                            () -> {
                                start.countDown();
                                boolean blocked = false;
                                long deadline =
                                        System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(250);
                                try (var st =
                                        observer.prepareStatement(
                                                "SELECT cardinality(pg_blocking_pids(?))>0")) {
                                    st.setInt(1, writerPid.get());
                                    do {
                                        try (var rs = st.executeQuery()) {
                                            rs.next();
                                            blocked = rs.getBoolean(1);
                                        }
                                        if (!blocked) Thread.onSpinWait();
                                    } while (!blocked && System.nanoTime() < deadline);
                                } catch (SQLException ex) {
                                    throw new AssertionError(ex);
                                }
                                assertThat(blocked).isTrue();
                                assertThat(future.isDone()).isFalse();
                            }));
            assertThat(join(future)).isEqualTo(2);
        } finally {
            start.countDown();
        }
        permits(assess(f, "author"));
    }

    @Test
    void futureDenyBoundAndExpiryCannotExtendEvidence() {
        var f = fixture("LOCAL");
        both(f, "author");
        String deny = grant(f, "author", "DENY", "GLOBAL", null);
        Instant boundary = dbNow().plusSeconds(2);
        jdbc.update(
                "UPDATE "
                        + T
                        + "user_permission_grant SET status='REVOKED',valid_from=?,valid_to=? WHERE"
                        + " id=?",
                Timestamp.from(boundary),
                Timestamp.from(boundary.plusSeconds(60)),
                deny);
        var p = prepare(f);
        AtomicReference<Contribution> saved = new AtomicReference<>();
        executor.execute(
                () -> {
                    var c = participant.contribute(p);
                    saved.set(c);
                    for (boolean company : List.of(true, false)) {
                        var request = request(f, "author", company);
                        var e =
                                reader.load(
                                        c.evidence(), request, c.backendPid(), c.transactionId());
                        assertThat(
                                        new AuthorizationPolicyEvaluator()
                                                .evaluate(request, e, c.evidence().observedAt())
                                                .decision())
                                .isEqualTo(AuthorizationDecisionValue.PERMIT);
                    }
                    return "bounded";
                },
                em::flush,
                () -> guard(saved.get()));
        assertThat(saved.get().validUntil()).isEqualTo(boundary);
        // Expiry observation is outside the protected work budget; no sleeps or wider timeout.
        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(5);
        while (dbNow().isBefore(boundary) && System.nanoTime() < deadline) Thread.onSpinWait();
        assertThat(dbNow()).isAfterOrEqualTo(boundary);
        int committedReceipts =
                jdbc.queryForObject("SELECT count(*) FROM " + receipt(), Integer.class);
        assertThatThrownBy(
                        () ->
                                executor.execute(
                                        () -> "diagnostic scalar",
                                        em::flush,
                                        () -> guard(saved.get())))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .hasRootCauseInstanceOf(IllegalStateException.class)
                .hasRootCauseMessage(
                        "Protected temporal guard rejected invalid or expired interval")
                .satisfies(
                        thrown ->
                                assertThat(
                                                ((ProtectedTransactionExecutor.ExecutionFailure)
                                                                thrown)
                                                        .outcome())
                                        .isEqualTo(
                                                ProtectedTransactionExecutor.Outcome
                                                        .REJECTED_BEFORE_COMMIT));
        assertThat(jdbc.queryForObject("SELECT count(*) FROM " + receipt(), Integer.class))
                .isEqualTo(committedReceipts);
    }

    @Test
    void boundedOverflowMalformedStateAndRollbackStayClosed() {
        var f = fixture("LOCAL");
        both(f, "author");
        attribute("USER", f.user(), "bad", "NUMBER", "not-a-number");
        assertThat(assess(f, "author").company())
                .isEqualTo(AuthorizationDecisionValue.INDETERMINATE);
        cleanEvidence();
        both(f, "author");
        String permission = permission("author");
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "external_permission_mapping(id,identity_provider_id,permission_id,external_permission_code,mapping_mode,effect,status,created_at,updated_at)"
                        + " SELECT"
                        + " 'overflow-'||n,?,?,'synthetic','REQUIRES_LOCAL_APPROVAL','GRANT','ACTIVE',clock_timestamp(),clock_timestamp()"
                        + " FROM generate_series(1,1025) n",
                f.provider(),
                permission);
        int before = jdbc.queryForObject("SELECT count(*) FROM " + receipt(), Integer.class);
        var prepared = prepare(f);
        AtomicBoolean guardCalled = new AtomicBoolean();
        assertThatThrownBy(
                        () ->
                                executor.execute(
                                        () -> {
                                            var c = participant.contribute(prepared);
                                            jdbc.update(
                                                    "INSERT INTO " + receipt() + " VALUES (?)",
                                                    receipts.incrementAndGet());
                                            reader.load(
                                                    c.evidence(),
                                                    request(f, "author", true),
                                                    c.backendPid(),
                                                    c.transactionId());
                                            return "unreachable";
                                        },
                                        em::flush,
                                        () -> guardCalled.set(true)))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .hasRootCauseInstanceOf(IllegalStateException.class)
                .hasRootCauseMessage("Protected authorization evidence is unavailable or corrupt.");
        assertThat(guardCalled).isFalse();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM " + receipt(), Integer.class))
                .isEqualTo(before);
        jdbc.update(
                "DELETE FROM " + T + "external_permission_mapping WHERE identity_provider_id=?",
                f.provider());
        permits(assess(f, "author"));
        policy("PERMIT", null);
        jdbc.update(
                "INSERT INTO "
                        + T
                        + "authorization_policy_version(id,policy_id,version_number,status,effective_from,created_at)"
                        + " SELECT ?,id,2,'ACTIVE',clock_timestamp()-interval '1"
                        + " hour',clock_timestamp() FROM "
                        + T
                        + "authorization_policy",
                id());
        assertThat(assess(f, "author").company())
                .isEqualTo(AuthorizationDecisionValue.INDETERMINATE);
    }
}
