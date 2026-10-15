/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedOperationIdentityParticipantPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-15
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Proves real producer origin and fenced database-time B1 participation, rejection, rollback and commit delivery.
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
import dz.sh.hidra.modules.identity.domain.value.*;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserJpaEntity;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.test.context.*;
import org.springframework.test.context.bean.override.mockito.*;
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

/** Only directory/source transport is synthetic; native crypto, origin, fences and owner state are real. */
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
class ProtectedOperationIdentityParticipantPostgresIntegrationTest {
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
                    .withDatabaseName("hidra_participant_test")
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
    @Autowired jakarta.persistence.EntityManager em;
    @Autowired Flyway flyway;
    @Autowired ProtectedOperationIdentityParticipant participant;
    @Autowired ProtectedTransactionExecutor executor;
    @Autowired PlatformTransactionManager manager;
    @Autowired AuthenticateDirectUserUseCase direct;
    @Autowired CompleteAuthenticatedPrincipalUseCase completion;
    @Autowired DirectAuthenticationPort directPort;
    @Autowired IdentityOidcJwtAuthenticationConverter oidc;
    @Autowired OperationAuthenticationBindingRepositoryPort bindings;
    @Autowired PasswordEncoder passwords;
    @Autowired LoginSessionRepositoryPort sessions;

    @Autowired
    @Qualifier("hidraJwtDecoder")
    JwtDecoder nativeDecoder;

    // Transport fixture only: no external directory service in this synthetic database.
    @MockitoBean LdapCredentialVerificationPort directory;

    // Transport fixture delegates genuine signed source verification below; native decoder is real.
    @MockitoBean(name = "externalOidcJwtDecoder")
    JwtDecoder externalDecoder;

    // Capture only: every call delegates the real LOCAL producer including its private receipt.
    @MockitoSpyBean LocalAuthenticationProvider localProvider;
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
        flyway.validate();
        assertThat(flyway.info().pending()).isEmpty();
        when(externalDecoder.decode(anyString()))
                .thenAnswer(i -> realExternal.decode(i.getArgument(0)));
        schema = "hpr051f_" + UUID.randomUUID().toString().replace("-", "");
        jdbc.execute("CREATE SCHEMA " + schema);
        jdbc.execute("CREATE TABLE " + receipt() + "(id integer PRIMARY KEY)");
        // Warm proxy/JPA/executor paths outside every bounded operation.
        var result = authenticate(fixture("LOCAL"));
        ambient(result.accessToken());
        run(participant.prepare());
    }

    @AfterEach
    void clear() {
        directPort.finishVerification();
        SecurityContextHolder.clearContext();
        // Guarded provenance/security rows are retained; only this test's disposable receipt is
        // removed.
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
        assertThat(count).isEqualTo(1);
    }

    private OperationAuthenticationBinding binding(AuthenticationResult r) {
        return bindings.findByNativeIssuerAndJti("hidra-api", r.jti()).orElseThrow();
    }

    private void denied(Prepared p) {
        assertThatThrownBy(() -> run(p))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .hasRootCauseInstanceOf(AccessDeniedException.class);
    }

    private void errorPrepare() {
        assertThatThrownBy(participant::prepare)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Protected authentication infrastructure is unavailable or corrupt.")
                .hasNoCause();
    }

    @Test
    void allFourRealProducerBearersParticipateAtDatabaseSample() {
        for (String type : List.of("LOCAL", "LDAP", "ACTIVE_DIRECTORY", "OIDC")) {
            var f = fixture(type);
            var result = authenticate(f);
            var b = binding(result);
            assertThat(nativeDecoder.decode(result.accessToken()).getSubject()).isEqualTo(f.user());
            ambient(result.accessToken());
            var prepared = participant.prepare();
            AtomicReference<Contribution> saved = new AtomicReference<>();
            Contribution c =
                    executor.execute(
                            () -> {
                                var actual = participant.contribute(prepared);
                                saved.set(actual);
                                assertThat(
                                                jdbc.queryForObject(
                                                        "SELECT pg_backend_pid()", Integer.class))
                                        .isEqualTo(actual.backendPid());
                                assertThat(jdbc.queryForObject("SELECT txid_current()", Long.class))
                                        .isEqualTo(actual.transactionId());
                                em.unwrap(Session.class)
                                        .doWork(
                                                conn -> {
                                                    assertThat(
                                                                    scalar(
                                                                            conn,
                                                                            "SELECT"
                                                                                + " pg_backend_pid()"))
                                                            .isEqualTo(actual.backendPid());
                                                    assertThat(
                                                                    scalar(
                                                                            conn,
                                                                            "SELECT"
                                                                                + " txid_current()"))
                                                            .isEqualTo(actual.transactionId());
                                                });
                                return actual;
                            },
                            em::flush,
                            () -> guard(saved.get()));
            assertThat(c.evidence().userId()).isEqualTo(f.user());
            assertThat(c.evidence().sessionId()).isEqualTo(result.sessionId());
            assertThat(c.evidence().authenticationType()).isEqualTo(type);
            assertThat(c.evidence().providerGeneration()).isEqualTo(b.providerSecurityVersion());
            assertThat(c.evidence().externalGeneration()).isEqualTo(b.externalSecurityVersion());
            assertThat(c.evidence().credentialGeneration())
                    .isEqualTo(b.credentialSecurityVersion());
            assertThat(c.validUntil())
                    .isAfter(c.evidence().observedAt())
                    .isBeforeOrEqualTo(b.expiresAt());
            if (b.sourceExpiresAt() != null)
                assertThat(c.validUntil()).isBeforeOrEqualTo(b.sourceExpiresAt());
        }
    }

    @Test
    void committedDirectAndOidcReceiptsParticipateWithoutCallerClaims() {
        var f = fixture("LOCAL");
        var captured = new AtomicReference<Authentication>();
        doAnswer(
                        i -> {
                            Authentication a = (Authentication) i.callRealMethod();
                            captured.set(a);
                            return a;
                        })
                .when(localProvider)
                .authenticate(any());
        var v = directPort.verify(ProviderType.LOCAL, f.user(), "synthetic-password");
        SecurityContextHolder.getContext().setAuthentication(captured.get());
        assertThatThrownBy(participant::prepare).isInstanceOf(AccessDeniedException.class);
        complete(v);
        assertThat(run(participant.prepare()).evidence().userId()).isEqualTo(f.user());
        directPort.finishVerification();
        SecurityContextHolder.getContext()
                .setAuthentication(
                        org.springframework.security.authentication
                                .UsernamePasswordAuthenticationToken.authenticated(
                                v.principal(), null, List.of()));
        assertThatThrownBy(participant::prepare).isInstanceOf(AccessDeniedException.class);
        var of = fixture("OIDC");
        completeOidc(of, Instant.now().plusSeconds(300).truncatedTo(ChronoUnit.SECONDS));
        Authentication actual = SecurityContextHolder.getContext().getAuthentication();
        assertThat(run(participant.prepare()).evidence().userId()).isEqualTo(of.user());
        SecurityContextHolder.getContext()
                .setAuthentication(
                        new HidraOidcAuthenticationToken((HidraPrincipal) actual.getPrincipal()));
        assertThatThrownBy(participant::prepare).isInstanceOf(AccessDeniedException.class);
        // A real completion rolled back before afterCommit never publishes a receipt.
        var rolled = fixture("LOCAL");
        var verified = directPort.verify(ProviderType.LOCAL, rolled.user(), "synthetic-password");
        SecurityContextHolder.getContext().setAuthentication(captured.get());
        tx().executeWithoutResult(
                        s -> {
                            complete(verified);
                            s.setRollbackOnly();
                        });
        assertThatThrownBy(participant::prepare).isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void rawWireForgeryAndZeroOrCopiedTagsReject() {
        var result = authenticate(fixture("LOCAL"));
        var b = binding(result);
        var original = claims(result.accessToken());
        List<Map<String, Object>> invalid = new ArrayList<>();
        for (String field :
                List.of("iss", "sub", "jti", "authentication_type", "identity_provider_id")) {
            var c = new LinkedHashMap<>(original);
            c.put(field, "wrong");
            invalid.add(c);
        }
        for (Object aud : List.of(List.of("hidra-api", "extra"), 1, Map.of("x", "y"))) {
            var c = new LinkedHashMap<>(original);
            c.put("aud", aud);
            invalid.add(c);
        }
        for (Object time : List.of("1", 1.5, Long.MAX_VALUE)) {
            var c = new LinkedHashMap<>(original);
            c.put("exp", time);
            invalid.add(c);
        }
        for (var c : invalid) {
            ambient(wire(c, "HS256", KEY));
            assertThatThrownBy(participant::prepare).isInstanceOf(AccessDeniedException.class);
        }
        ambient(wire(original, "HS256", "synthetic-wrong-key-with-32-bytes-only"));
        assertThatThrownBy(participant::prepare).isInstanceOf(AccessDeniedException.class);
        ambient(wire(original, "HS512", KEY));
        assertThatThrownBy(participant::prepare).isInstanceOf(AccessDeniedException.class);
        ambient(
                wirePayload(
                        JSON.writeValueAsString(original)
                                .replaceFirst("\\{", "{\"sub\":\"duplicate\","),
                        "HS256",
                        KEY));
        assertThatThrownBy(participant::prepare).isInstanceOf(AccessDeniedException.class);
        ambient(result.accessToken() + "a".repeat(65536));
        assertThatThrownBy(participant::prepare).isInstanceOf(AccessDeniedException.class);
        for (byte[] tag : List.of(new byte[32], b.producerAuthenticator(), filledTag())) {
            // Real structural row, deliberately wrong origin; never compute the private HMAC in
            // tests.
            var r = authenticate(fixture("LOCAL"));
            var owner = binding(r);
            String jti = UUID.randomUUID().toString();
            var forgery =
                    copy(
                            owner,
                            UUID.randomUUID().toString(),
                            UUID.randomUUID().toString(),
                            jti,
                            tag);
            tx().executeWithoutResult(
                            s -> {
                                sessions.save(
                                        new LoginSession(
                                                forgery.sessionId(),
                                                owner.userId(),
                                                owner.providerId(),
                                                null,
                                                owner.issuedAt(),
                                                owner.issuedAt(),
                                                owner.expiresAt(),
                                                null,
                                                null,
                                                LoginSessionStatus.ACTIVE,
                                                null,
                                                AuthenticationProtocol.LOCAL,
                                                null));
                                bindings.append(forgery);
                            });
            var c = claims(r.accessToken());
            c.put("jti", jti);
            ambient(wire(c, "HS256", KEY));
            errorPrepare();
        }
    }

    @Test
    void bindingOwnerGenerationsAndAbaAreReadAfterFences() {
        for (String branch :
                List.of("provider", "local_credential", "external_identity", "session", "user")) {
            var f = fixture(branch.equals("external_identity") ? "LDAP" : "LOCAL");
            var r = authenticate(f);
            ambient(r.accessToken());
            var p = participant.prepare();
            String table;
            String id;
            String column;
            String away;
            String back;
            switch (branch) {
                case "provider" -> {
                    table = P;
                    id = f.provider();
                    column = "name";
                    away = "aba-away";
                    back = "Synthetic completion provider";
                }
                case "local_credential" -> {
                    table = C;
                    id = f.credential();
                    column = "credential_status";
                    away = "DISABLED";
                    back = "ACTIVE";
                }
                case "external_identity" -> {
                    table = E;
                    id = f.link();
                    column = "status";
                    away = "UNLINKED";
                    back = "LINKED";
                }
                case "session" -> {
                    table = S;
                    id = r.sessionId();
                    column = "status";
                    away = "LOGGED_OUT";
                    back = null;
                }
                default -> {
                    table = U;
                    id = f.user();
                    column = "status";
                    away = "DISABLED";
                    back = null;
                }
            }
            if (branch.equals("provider")) {
                // Status is generation-relevant; restore the same visible value (ABA).
                jdbc.update("UPDATE " + P + " SET status='INACTIVE' WHERE id=?", id);
                jdbc.update("UPDATE " + P + " SET status='ACTIVE' WHERE id=?", id);
            } else {
                jdbc.update("UPDATE " + table + " SET " + column + "=? WHERE id=?", away, id);
                if (back != null)
                    jdbc.update("UPDATE " + table + " SET " + column + "=? WHERE id=?", back, id);
            }
            denied(p);
        }
    }

    @Test
    void revocationBeforeFenceAdmissionDeniesWithoutResult() throws Exception {
        var r = authenticate(fixture("LOCAL"));
        ambient(r.accessToken());
        var p = participant.prepare();
        try (var writer = connection()) {
            assertThat(scalar(writer, "SELECT pg_backend_pid()")).isNotEqualTo(backend());
            update(
                    writer,
                    "UPDATE "
                            + S
                            + " SET status='LOGGED_OUT',ended_at=clock_timestamp() WHERE id=?",
                    r.sessionId());
            writer.commit();
        }
        int before = countReceipts();
        denied(p);
        assertThat(countReceipts()).isEqualTo(before);
    }

    @Test
    void writerAfterAdmissionCannotPassTheContributionFence() throws Exception {
        var r = authenticate(fixture("LOCAL"));
        ambient(r.accessToken());
        var p = participant.prepare();
        try (var writer = connection();
                var observer = connection();
                var pool = Executors.newSingleThreadExecutor()) {
            int pid = (int) scalar(writer, "SELECT pg_backend_pid()");
            try (var st = writer.createStatement()) {
                st.execute("SET LOCAL lock_timeout='250ms'");
                st.execute("SET LOCAL statement_timeout='750ms'");
            }
            scalar(observer, "SELECT 1");
            observer.commit();
            CountDownLatch start = new CountDownLatch(1), ready = new CountDownLatch(1);
            Future<String> future =
                    pool.submit(
                            () -> {
                                ready.countDown();
                                await(start);
                                try {
                                    update(
                                            writer,
                                            "UPDATE " + S + " SET status='LOGGED_OUT' WHERE id=?",
                                            r.sessionId());
                                    writer.commit();
                                    return "committed";
                                } catch (SQLException e) {
                                    writer.rollback();
                                    return e.getSQLState();
                                }
                            });
            await(ready);
            AtomicReference<Contribution> saved = new AtomicReference<>();
            executor.execute(
                    () -> {
                        var c = participant.contribute(p);
                        saved.set(c);
                        start.countDown();
                        long end = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(220);
                        boolean waiting = false;
                        while (System.nanoTime() < end) {
                            try (var st =
                                    observer.prepareStatement(
                                            "SELECT ?=ANY(pg_blocking_pids(?))")) {
                                st.setInt(1, c.backendPid());
                                st.setInt(2, pid);
                                try (var rs = st.executeQuery()) {
                                    rs.next();
                                    if (rs.getBoolean(1)) {
                                        waiting = true;
                                        break;
                                    }
                                }
                            } catch (SQLException e) {
                                throw new AssertionError(e);
                            }
                        }
                        assertThat(waiting).isTrue();
                        assertThat(join(future)).isEqualTo("55P03");
                        return c;
                    },
                    em::flush,
                    () -> guard(saved.get()));
            update(
                    writer,
                    "UPDATE "
                            + S
                            + " SET status='LOGGED_OUT',ended_at=clock_timestamp() WHERE id=?",
                    r.sessionId());
            writer.commit();
            ambient(r.accessToken());
            denied(participant.prepare());
        }
    }

    @Test
    void expiryBetweenPrepareAndContributionAborts() {
        var f = fixture("OIDC");
        var r = completeOidc(f, Instant.now().plusSeconds(5).truncatedTo(ChronoUnit.SECONDS));
        ambient(r.accessToken());
        var p = participant.prepare();
        Instant expires = binding(r).expiresAt();
        long stop = System.nanoTime() + TimeUnit.SECONDS.toNanos(8);
        Instant sample;
        do {
            sample = jdbc.queryForObject("SELECT clock_timestamp()", Timestamp.class).toInstant();
        } while (sample.isBefore(expires) && System.nanoTime() < stop);
        assertThat(sample).isAfterOrEqualTo(expires);
        assertThatThrownBy(() -> run(p))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .hasRootCauseInstanceOf(IllegalStateException.class)
                .hasStackTraceContaining("Unsupported temporal census");
    }

    @Test
    void opaqueHandleReplayContextSwapAndCrossThreadReject() throws Exception {
        var r = authenticate(fixture("LOCAL"));
        Authentication actual = ambient(r.accessToken());
        var p = participant.prepare();
        run(p);
        denied(p);
        var swap = participant.prepare();
        ambient(r.accessToken());
        denied(swap);
        var foreign = participant.prepare();
        actual = SecurityContextHolder.getContext().getAuthentication();
        final Authentication captured = actual;
        try (var pool = Executors.newSingleThreadExecutor()) {
            Throwable e =
                    pool.submit(
                                    () -> {
                                        SecurityContextHolder.getContext()
                                                .setAuthentication(captured);
                                        try {
                                            return catchThrowable(() -> run(foreign));
                                        } finally {
                                            SecurityContextHolder.clearContext();
                                        }
                                    })
                            .get(5, TimeUnit.SECONDS);
            assertThat(e)
                    .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                    .hasRootCauseInstanceOf(AccessDeniedException.class);
        }
        var replay = participant.prepare();
        int before = countReceipts();
        assertThatThrownBy(
                        () ->
                                executor.execute(
                                        () -> {
                                            participant.contribute(replay);
                                            jdbc.update(
                                                    "INSERT INTO " + receipt() + " VALUES (?)",
                                                    receipts.incrementAndGet());
                                            assertThatThrownBy(() -> participant.contribute(replay))
                                                    .isInstanceOf(AccessDeniedException.class);
                                            return "caught";
                                        },
                                        em::flush,
                                        () -> jdbc.queryForObject("SELECT 1", Integer.class)))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .hasRootCauseInstanceOf(UnexpectedRollbackException.class);
        assertThat(countReceipts()).isEqualTo(before);
    }

    @Test
    void incompatibleTransactionsAndAmbientPreparationReject() {
        var r = authenticate(fixture("LOCAL"));
        ambient(r.accessToken());
        assertThatThrownBy(() -> tx().execute(s -> participant.prepare()))
                .isInstanceOf(IllegalTransactionStateException.class);
        var none = participant.prepare();
        assertThatThrownBy(() -> participant.contribute(none))
                .isInstanceOf(IllegalTransactionStateException.class);
        for (int isolation :
                List.of(
                        TransactionDefinition.ISOLATION_READ_COMMITTED,
                        TransactionDefinition.ISOLATION_REPEATABLE_READ,
                        TransactionDefinition.ISOLATION_SERIALIZABLE)) {
            var p = participant.prepare();
            var t = tx();
            t.setIsolationLevel(isolation);
            t.setReadOnly(isolation == TransactionDefinition.ISOLATION_READ_COMMITTED);
            assertThatThrownBy(() -> t.execute(s -> participant.contribute(p)))
                    .isInstanceOf(IllegalStateException.class);
        }
        // A valid ordinary RC transaction establishes B1 data, but does not certify executor/global
        // admission.
        var p = participant.prepare();
        Contribution ordinary = tx().execute(s -> participant.contribute(p));
        assertThat(ordinary).isNotNull();
    }

    @Test
    void staleJpaImagesCannotRelabelFreshOwnerState() throws Exception {
        var f = fixture("LOCAL");
        var r = authenticate(f);
        ambient(r.accessToken());
        var p = participant.prepare();
        try (var writer = connection()) {
            scalar(writer, "SELECT 1");
            writer.commit();
            assertThatThrownBy(
                            () ->
                                    executor.execute(
                                            () -> {
                                                var cached = em.find(UserJpaEntity.class, f.user());
                                                assertThat(cached.status())
                                                        .isEqualTo(UserStatus.ACTIVE);
                                                try {
                                                    update(
                                                            writer,
                                                            "UPDATE "
                                                                    + U
                                                                    + " SET status='DISABLED' WHERE"
                                                                    + " id=?",
                                                            f.user());
                                                    writer.commit();
                                                } catch (SQLException e) {
                                                    throw new AssertionError(e);
                                                }
                                                assertThat(em.find(UserJpaEntity.class, f.user()))
                                                        .isSameAs(cached);
                                                assertThat(cached.status())
                                                        .isEqualTo(UserStatus.ACTIVE);
                                                return participant.contribute(p);
                                            },
                                            em::flush,
                                            () -> {
                                                throw new AssertionError(
                                                        "No guard after denied state");
                                            }))
                    .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                    .hasRootCauseInstanceOf(AccessDeniedException.class);
        }
    }

    @Test
    void caughtFailureRollsBackProvisionalJpaAndReceipt() {
        var f = fixture("LOCAL");
        var r = authenticate(f);
        ambient(r.accessToken());
        var p = participant.prepare();
        jdbc.update(
                "UPDATE " + S + " SET status='LOGGED_OUT',ended_at=clock_timestamp() WHERE id=?",
                r.sessionId());
        String provisional = UUID.randomUUID().toString();
        int before = countReceipts();
        AtomicBoolean guarded = new AtomicBoolean();
        assertThatThrownBy(
                        () ->
                                executor.execute(
                                        () -> {
                                            Instant now = Instant.now();
                                            em.persist(
                                                    new UserJpaEntity(
                                                            provisional,
                                                            provisional,
                                                            null,
                                                            "Synthetic provisional",
                                                            UserType.HUMAN,
                                                            UserStatus.ACTIVE,
                                                            null,
                                                            null,
                                                            0,
                                                            null,
                                                            now,
                                                            null,
                                                            null,
                                                            null,
                                                            now));
                                            em.flush();
                                            jdbc.update(
                                                    "INSERT INTO " + receipt() + " VALUES (?)",
                                                    receipts.incrementAndGet());
                                            Throwable failure =
                                                    catchThrowable(() -> participant.contribute(p));
                                            assertThat(failure)
                                                    .isInstanceOf(AccessDeniedException.class);
                                            throw (AccessDeniedException) failure;
                                        },
                                        em::flush,
                                        () -> {
                                            guarded.set(true);
                                            jdbc.queryForObject("SELECT 1", Integer.class);
                                        }))
                .isInstanceOf(ProtectedTransactionExecutor.ExecutionFailure.class)
                .hasRootCauseInstanceOf(AccessDeniedException.class);
        assertThat(countReceipts()).isEqualTo(before);
        assertThat(
                        jdbc.queryForObject(
                                "SELECT count(*) FROM " + U + " WHERE id=?",
                                Integer.class,
                                provisional))
                .isZero();
        // The actual caught denial is propagated: no final guard, commit or delivery.
        assertThat(guarded).isFalse();
        var clean = authenticate(fixture("LOCAL"));
        ambient(clean.accessToken());
        assertThat(run(participant.prepare())).isNotNull();
    }

    @Test
    void immutableContributionCommitsBeforeDeliveryAndDoesNotGrantPermission() throws Exception {
        var r = authenticate(fixture("LOCAL"));
        ambient(r.accessToken());
        var p = participant.prepare();
        AtomicBoolean committed = new AtomicBoolean();
        AtomicReference<Contribution> saved = new AtomicReference<>();
        Contribution c =
                executor.execute(
                        () -> {
                            var contribution = participant.contribute(p);
                            saved.set(contribution);
                            TransactionSynchronizationManager.registerSynchronization(
                                    new TransactionSynchronization() {
                                        @Override
                                        public void afterCommit() {
                                            committed.set(true);
                                        }
                                    });
                            assertThat(committed).isFalse();
                            return contribution;
                        },
                        em::flush,
                        () -> guard(saved.get()));
        assertThat(committed).isTrue();
        assertThat(TransactionSynchronizationManager.isActualTransactionActive()).isFalse();
        try (var observed = connection()) {
            assertThat(scalar(observed, "SELECT count(*) FROM " + receipt()))
                    .isGreaterThanOrEqualTo(2);
            try (var st =
                    observed.prepareStatement(
                            "SELECT pg_xact_commit_timestamp(?::xid) IS NOT NULL")) {
                st.setString(1, Long.toString(c.transactionId()));
                try (var rs = st.executeQuery()) {
                    rs.next();
                    assertThat(rs.getBoolean(1)).isTrue();
                }
            }
        }
        assertThat(c.getClass().isRecord()).isTrue();
        assertThat(Arrays.stream(c.getClass().getRecordComponents()).map(v -> v.getName()))
                .containsExactly("evidence", "validUntil", "backendPid", "transactionId");
        assertThat(c.toString())
                .doesNotContain(
                        r.accessToken(), "PERMIT", "producerAuthenticator", "synthetic-password");
        assertThatThrownBy(
                        () ->
                                new Contribution(
                                        c.evidence(),
                                        c.evidence().observedAt(),
                                        c.backendPid(),
                                        c.transactionId()))
                .isInstanceOf(IllegalArgumentException.class);
        // Public construction copies scalar data only; a later use requires the real participant
        // again.
        assertThat(
                        new Contribution(
                                c.evidence(), c.validUntil(), c.backendPid(), c.transactionId()))
                .isEqualTo(c);
        denied(p);
    }

    private int countReceipts() {
        return jdbc.queryForObject("SELECT count(*) FROM " + receipt(), Integer.class);
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
    private static LinkedHashMap<String, Object> claims(String wire) {
        return new LinkedHashMap<>(
                JSON.readValue(Base64.getUrlDecoder().decode(wire.split("\\.")[1]), Map.class));
    }

    private static String wire(Map<String, Object> claims, String alg, String key) {
        return wirePayload(JSON.writeValueAsString(claims), alg, key);
    }

    private static String wirePayload(String payload, String alg, String key) {
        try {
            var b64 = Base64.getUrlEncoder().withoutPadding();
            String unsigned =
                    b64.encodeToString(JSON.writeValueAsBytes(Map.of("alg", alg, "typ", "JWT")))
                            + "."
                            + b64.encodeToString(payload.getBytes(StandardCharsets.UTF_8));
            var mac = javax.crypto.Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return unsigned
                    + "."
                    + b64.encodeToString(mac.doFinal(unsigned.getBytes(StandardCharsets.US_ASCII)));
        } catch (java.security.GeneralSecurityException fault) {
            throw new IllegalStateException("Synthetic signer unavailable.");
        }
    }

    private static byte[] filledTag() {
        byte[] bytes = new byte[32];
        Arrays.fill(bytes, (byte) 93);
        return bytes;
    }

    private static OperationAuthenticationBinding copy(
            OperationAuthenticationBinding b, String id, String session, String jti, byte[] tag) {
        return new OperationAuthenticationBinding(
                id,
                b.formatVersion(),
                b.nativeIssuer(),
                b.nativeAudience(),
                jti,
                session,
                b.userId(),
                b.authenticationType(),
                b.sessionProtocol(),
                b.providerId(),
                b.providerSecurityVersion(),
                b.externalIdentityId(),
                b.externalSubject(),
                b.externalSecurityVersion(),
                b.sourceIssuer(),
                b.sourceExpiresAt(),
                b.credentialId(),
                b.credentialSecurityVersion(),
                b.issuedAt(),
                b.expiresAt(),
                tag);
    }

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
        Jwt signed =
                sourceEncoder.encode(
                        JwtEncoderParameters.from(
                                JwtClaimsSet.builder()
                                        .issuer(SOURCE)
                                        .subject(f.subject())
                                        .audience(List.of("hidra-api"))
                                        .issuedAt(Instant.now().minusSeconds(1))
                                        .expiresAt(deadline)
                                        .build()));
        var actual = oidc.convert(realExternal.decode(signed.getTokenValue()));
        SecurityContextHolder.getContext().setAuthentication(actual);
        return completion.complete(
                input((HidraPrincipal) actual.getPrincipal(), null),
                "synthetic-ip",
                "synthetic-agent",
                "synthetic-correlation");
    }

    private AuthenticationResult complete(VerifiedDirectAuthenticationResult v) {
        return completion.complete(input(v.principal(), v.verificationHandle()), null, null, null);
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
}
