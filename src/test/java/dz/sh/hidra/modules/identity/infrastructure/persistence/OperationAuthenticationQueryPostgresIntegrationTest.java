/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationAuthenticationQueryPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Proves signed B1 lookup, private origin, fresh joined eligibility, opaque denials and observed native/JPA writer races on synthetic PostgreSQL.
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
import dz.sh.hidra.modules.identity.infrastructure.security.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
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
import java.util.concurrent.atomic.*;

import javax.crypto.spec.SecretKeySpec;

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
class OperationAuthenticationQueryPostgresIntegrationTest {
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
                    .withDatabaseName("hidra_completion_test")
                    .withCommand(
                            "postgres",
                            "-c",
                            "log_error_verbosity=terse",
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

    @Autowired
    dz.sh.hidra.modules.identity.application.contract.topology
                    .TopologyOperationAuthenticationContract
            reader;

    @Autowired
    dz.sh.hidra.modules.identity.application.service
                    .AuthenticationSessionLifecycleApplicationService
            lifecycle;

    @Autowired
    dz.sh.hidra.modules.identity.application.service.OperationAuthenticationQueryApplicationService
            owner;

    @MockitoSpyBean AuthenticationSecurityStateQueryPort states;
    @MockitoSpyBean LocalAuthenticationProvider localProvider;

    @MockitoBean(name = "identityAuthenticationClock")
    java.time.Clock clock;

    private static final tools.jackson.databind.json.JsonMapper JSON =
            tools.jackson.databind.json.JsonMapper.builder().build();
    @Autowired AuthenticateDirectUserUseCase direct;
    @Autowired CompleteAuthenticatedPrincipalUseCase completion;
    @Autowired DirectAuthenticationPort directPort;
    @Autowired IdentityOidcJwtAuthenticationConverter oidc;
    @Autowired TrustedAuthenticationVerificationRegistry registry;
    @MockitoSpyBean PasswordEncoder passwords;
    @Autowired PlatformTransactionManager manager;

    @Autowired
    @Qualifier("hidraJwtDecoder")
    JwtDecoder nativeDecoder;

    @MockitoBean LdapCredentialVerificationPort directory;

    @MockitoBean(name = "externalOidcJwtDecoder")
    JwtDecoder externalDecoder;

    @MockitoSpyBean LoginSessionRepositoryPort sessions;
    @MockitoSpyBean AccessTokenIssuerPort issuer;
    @MockitoSpyBean OperationAuthenticationBindingRepositoryPort bindings;
    @MockitoSpyBean AuthenticationEventRepositoryPort events;
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

    @BeforeEach
    void prepare() {
        SecurityContextHolder.clearContext();
        when(clock.instant()).thenAnswer(i -> Instant.now());
        jdbc.update("UPDATE " + P + " SET status='INACTIVE' WHERE status='ACTIVE'");
        when(externalDecoder.decode(anyString()))
                .thenAnswer(i -> realExternal.decode(i.getArgument(0)));
    }

    @AfterEach
    void cleanInvocation() {
        directPort.finishVerification();
        SecurityContextHolder.clearContext();
    }

    @Test
    void allFourRealProducerBearersReturnOpaqueBoundEvidenceWithoutPermissions() {
        for (String type : List.of("LOCAL", "LDAP", "ACTIVE_DIRECTORY", "OIDC")) {
            var f = fixture(type);
            var result = authenticate(f);
            ambient(result.accessToken());
            var read = reader.queryCurrentBoundAuthentication();
            assertThat(read.status()).isEqualTo("ELIGIBLE");
            assertThat(read.evidence().userId()).isEqualTo(f.user());
            assertThat(read.evidence().sessionId()).isEqualTo(result.sessionId());
            assertThat(read.evidence().authenticationType()).isEqualTo(type);
            assertThat(read.evidence().jti()).isEqualTo(result.jti());
            assertThat(read.evidence().expiresAt()).isEqualTo(result.expiresAt());
            assertThat(read.evidence().toString())
                    .doesNotContain(
                            result.accessToken(),
                            "synthetic-password",
                            "producerAuthenticator",
                            "PERMIT");
        }
    }

    @Test
    void ambientWrapperClaimsRolesDetailsAndPrincipalCannotOverrideValidatedWire() {
        var f = fixture("LOCAL");
        var result = authenticate(f);
        var token = ambient(result.accessToken());
        token.setDetails(Map.of("user", "forged", "roles", List.of("HIDRA_ADMIN")));
        assertThat(token.getToken().getSubject()).isEqualTo("forged-wrapper");
        assertThat(reader.queryCurrentBoundAuthentication().evidence().userId())
                .isEqualTo(f.user());
    }

    @Test
    void missingUnauthenticatedPublicAndLegacyNativeContextsDenyWithoutIdentity() {
        denied();
        var f = fixture("LOCAL");
        SecurityContextHolder.getContext()
                .setAuthentication(
                        org.springframework.security.authentication
                                .UsernamePasswordAuthenticationToken.authenticated(
                                "forged", null, List.of()));
        denied();
        var p =
                new HidraPrincipal(
                        f.user(),
                        f.user(),
                        null,
                        ProviderType.LOCAL,
                        f.provider(),
                        Set.of(),
                        Set.of());
        ambient(issuer.issue(p).tokenValue());
        denied();
        var token = ambient(issuer.issue(p).tokenValue());
        token.setAuthenticated(false);
        denied();
    }

    @Test
    void badSignatureAndWrongAlgorithmCannotUseTheStoredJti() {
        var result = authenticate(fixture("LOCAL"));
        var raw = claims(result.accessToken());
        ambient(wire(raw, "HS256", "wrong-synthetic-test-key-material-32-bytes"));
        denied();
        ambient(wire(raw, "RS256", KEY));
        denied();
    }

    @Test
    void signedIssuerAudienceAndOwnerIdentityMismatchesDeny() {
        var result = authenticate(fixture("LOCAL"));
        var raw = claims(result.accessToken());
        for (var changed :
                List.<Map<String, Object>>of(
                        Map.of("iss", "other"),
                        Map.of("aud", List.of("other")),
                        Map.of("sub", "another-user"),
                        Map.of("identity_provider_id", "another-provider"),
                        Map.of("authentication_type", "OIDC"))) {
            var fields = new LinkedHashMap<>(raw);
            fields.putAll(changed);
            ambient(wire(fields, "HS256", KEY));
            denied();
        }
    }

    @Test
    void signedWrongTimesAndMissingBindingCannotPassReadback() {
        var result = authenticate(fixture("LOCAL"));
        var raw = claims(result.accessToken());
        for (var changed :
                List.<Map<String, Object>>of(
                        Map.of("iat", result.issuedAt().getEpochSecond() - 1),
                        Map.of("exp", result.expiresAt().getEpochSecond() + 1),
                        Map.of("jti", UUID.randomUUID().toString()))) {
            var fields = new LinkedHashMap<>(raw);
            fields.putAll(changed);
            ambient(wire(fields, "HS256", KEY));
            denied();
        }
    }

    @Test
    void rawSignedClaimTypesCanonicalUuidsWhitespaceAndNativeLimitsAreStrict() {
        var result = authenticate(fixture("LOCAL"));
        var raw = claims(result.accessToken());
        for (var changed :
                List.<Map<String, Object>>of(
                        Map.of("aud", 123),
                        Map.of("aud", List.of("hidra-api", "other")),
                        Map.of("sub", 123),
                        Map.of("sub", " " + raw.get("sub")),
                        Map.of("jti", "1-1-1-1-1"),
                        Map.of("jti", result.jti().toUpperCase()),
                        Map.of("authentication_type", List.of("LOCAL")),
                        Map.of("identity_provider_id", List.of(raw.get("identity_provider_id"))),
                        Map.of("iat", result.issuedAt().getEpochSecond() + 0.5),
                        Map.of("exp", String.valueOf(result.expiresAt().getEpochSecond())),
                        Map.of("iss", "é".repeat(257)))) {
            var fields = new LinkedHashMap<>(raw);
            fields.putAll(changed);
            ambient(wire(fields, "HS256", KEY));
            denied();
        }
    }

    @Test
    void duplicateSignedPayloadAndOversizeBearerCannotBecomeTrustedEvidence() {
        var result = authenticate(fixture("LOCAL"));
        String payload =
                new String(
                        Base64.getUrlDecoder().decode(result.accessToken().split("\\.")[1]),
                        StandardCharsets.UTF_8);
        payload =
                payload.substring(0, payload.length() - 1)
                        + ",\"sub\":\""
                        + result.userId()
                        + "\"}";
        ambient(wirePayload(payload, "HS256", KEY));
        denied();
        ambient("a".repeat(65537));
        denied();
    }

    @Test
    void missingSignedIdentityClaimsDenyBeforeAnyOwnerEvidence() {
        var result = authenticate(fixture("LOCAL"));
        var original = claims(result.accessToken());
        for (String name :
                List.of(
                        "iss",
                        "aud",
                        "sub",
                        "jti",
                        "authentication_type",
                        "identity_provider_id",
                        "iat",
                        "exp")) {
            var fields = new LinkedHashMap<>(original);
            fields.remove(name);
            ambient(wire(fields, "HS256", KEY));
            denied();
        }
    }

    @Test
    void committedOwnerChangesIgnoreCachedJpaAndRepeatableReadCallerImages() throws Exception {
        var f = fixture("LOCAL");
        ambient(authenticate(f).accessToken());
        var tx = new TransactionTemplate(manager);
        tx.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        tx.setReadOnly(true);
        tx.execute(
                status -> {
                    var stale =
                            em.find(
                                    dz.sh.hidra.modules.identity.infrastructure.persistence.entity
                                            .UserJpaEntity.class,
                                    f.user());
                    assertThat(stale.status()).isEqualTo(UserStatus.ACTIVE);
                    try (Connection writer =
                                    DriverManager.getConnection(
                                            POSTGRES.getJdbcUrl(),
                                            POSTGRES.getUsername(),
                                            POSTGRES.getPassword());
                            var statement =
                                    writer.prepareStatement(
                                            "UPDATE " + U + " SET status='DISABLED' WHERE id=?")) {
                        statement.setString(1, f.user());
                        assertThat(statement.executeUpdate()).isEqualTo(1);
                    } catch (SQLException fault) {
                        throw new IllegalStateException("Synthetic owner writer failed.", fault);
                    }
                    denied();
                    assertThat(
                                    em.find(
                                            dz.sh.hidra.modules.identity.infrastructure.persistence
                                                    .entity.UserJpaEntity.class,
                                            f.user()))
                            .isSameAs(stale);
                    assertThat(stale.status()).isEqualTo(UserStatus.ACTIVE);
                    return null;
                });
    }

    @Test
    void providerAndCredentialAbaInvalidateAlreadySignedBindings() {
        var f = fixture("LOCAL");
        var result = authenticate(f);
        ambient(result.accessToken());
        jdbc.update("UPDATE " + P + " SET status='INACTIVE' WHERE id=?", f.provider());
        jdbc.update("UPDATE " + P + " SET status='ACTIVE' WHERE id=?", f.provider());
        denied();
        f = fixture("LOCAL");
        result = authenticate(f);
        ambient(result.accessToken());
        String hash =
                jdbc.queryForObject(
                        "SELECT password_hash FROM " + C + " WHERE id=?",
                        String.class,
                        f.credential());
        jdbc.update(
                "UPDATE " + C + " SET password_hash='synthetic-changed' WHERE id=?",
                f.credential());
        jdbc.update("UPDATE " + C + " SET password_hash=? WHERE id=?", hash, f.credential());
        denied();
    }

    @Test
    void externalLinkAbaInvalidatesExactSourceIdentity() {
        var f = fixture("OIDC");
        ambient(authenticate(f).accessToken());
        jdbc.update("UPDATE " + E + " SET status='UNLINKED' WHERE id=?", f.link());
        jdbc.update("UPDATE " + E + " SET status='LINKED' WHERE id=?", f.link());
        denied();
    }

    @Test
    void lockedDisabledAndNonhumanCurrentUsersDeny() {
        for (String change :
                List.of(
                        "status='DISABLED'",
                        "locked_until=clock_timestamp()+interval '1 hour'",
                        "user_type='SYSTEM'")) {
            var f = fixture("LOCAL");
            ambient(authenticate(f).accessToken());
            jdbc.update("UPDATE " + U + " SET " + change + " WHERE id=?", f.user());
            denied();
        }
    }

    @Test
    void allCommittedTerminalStatesDenyAndCannotResurrectTheirBinding() {
        for (String status : List.of("EXPIRED", "REVOKED", "LOGGED_OUT")) {
            var f = fixture("LOCAL");
            var result = authenticate(f);
            ambient(result.accessToken());
            jdbc.update(
                    "UPDATE " + S + " SET status=?,ended_at=clock_timestamp() WHERE id=?",
                    status,
                    result.sessionId());
            denied();
        }
    }

    @Test
    void actualNativeDecoderSkewCannotExtendTheInjectedRealDeadline() {
        var result = authenticate(fixture("LOCAL"));
        ambient(result.accessToken());
        when(clock.instant()).thenReturn(result.expiresAt());
        denied();
    }

    @Test
    void sourceDeadlineAndElapsedLookupNeverExtendEvidenceOrReadTwice() {
        var f = fixture("OIDC");
        var result =
                completeOidc(f, Instant.now().truncatedTo(ChronoUnit.SECONDS).plusSeconds(120));
        ambient(result.accessToken());
        doAnswer(
                        i -> {
                            Object current = i.callRealMethod();
                            assertThat(current).isEqualTo(true);
                            when(clock.instant()).thenReturn(result.expiresAt());
                            return current;
                        })
                .when(states)
                .current(any(), any());
        denied();
        verify(states, times(2))
                .current(any(), any()); // completion once, this reader exactly once.
    }

    @Test
    void freshOwnerLookupHasReadCommittedReadOnlyBackendEvenInsideStaleCallerTransaction() {
        var result = authenticate(fixture("LOCAL"));
        ambient(result.accessToken());
        assertThat(org.springframework.aop.support.AopUtils.isAopProxy(owner)).isTrue();
        doAnswer(
                        i -> {
                            assertThat(
                                            jdbc.queryForObject(
                                                    "SHOW transaction_isolation", String.class))
                                    .isEqualTo("read committed");
                            assertThat(
                                            jdbc.queryForObject(
                                                    "SHOW transaction_read_only", String.class))
                                    .isEqualTo("on");
                            return i.callRealMethod();
                        })
                .when(states)
                .current(any(), any());
        var tx = new TransactionTemplate(manager);
        tx.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        tx.execute(
                status -> {
                    assertThat(reader.queryCurrentBoundAuthentication().status())
                            .isEqualTo("ELIGIBLE");
                    return null;
                });
    }

    @Test
    void zeroCopiedAndArbitraryTagsAreRealStructuralRowsButNotPrivateProducerOrigin() {
        var f = fixture("LOCAL");
        var real = authenticate(f);
        var original = bindings.findByNativeIssuerAndJti("hidra-api", real.jti()).orElseThrow();
        for (byte[] tag : List.of(new byte[32], original.producerAuthenticator(), filledTag())) {
            var forgery =
                    copy(
                            original,
                            UUID.randomUUID().toString(),
                            UUID.randomUUID().toString(),
                            UUID.randomUUID().toString(),
                            tag);
            var tx = new TransactionTemplate(manager);
            tx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
            tx.execute(
                    status -> {
                        sessions.save(
                                new LoginSession(
                                        forgery.sessionId(),
                                        f.user(),
                                        f.provider(),
                                        null,
                                        forgery.issuedAt(),
                                        forgery.issuedAt(),
                                        forgery.expiresAt(),
                                        null,
                                        null,
                                        LoginSessionStatus.ACTIVE,
                                        null,
                                        AuthenticationProtocol.LOCAL,
                                        null));
                        bindings.append(forgery);
                        return null;
                    });
            var fields = claims(real.accessToken());
            fields.put("jti", forgery.jti());
            ambient(wire(fields, "HS256", KEY));
            assertThatThrownBy(() -> reader.queryCurrentBoundAuthentication())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("Corrupt persisted authentication provenance.");
        }
    }

    @Test
    void changedOrSwappedPersistedTupleIsAnOperationalErrorBeforeEligibility() {
        var f = fixture("LOCAL");
        var result = authenticate(f);
        var old = bindings.findByNativeIssuerAndJti("hidra-api", result.jti()).orElseThrow();
        ambient(result.accessToken());
        doReturn(
                        Optional.of(
                                copy(
                                        old,
                                        UUID.randomUUID().toString(),
                                        old.sessionId(),
                                        old.jti(),
                                        old.producerAuthenticator())))
                .when(bindings)
                .findByNativeIssuerAndJti("hidra-api", old.jti());
        assertThatThrownBy(() -> reader.queryCurrentBoundAuthentication())
                .isInstanceOf(IllegalStateException.class);
        verify(states, times(1)).current(any(), any()); // completion only.
    }

    @Test
    void corruptSessionTupleAndMissingOwnerAreOperationalErrorsNotAbsence() {
        var result = authenticate(fixture("LOCAL"));
        ambient(result.accessToken());
        doThrow(new IllegalStateException("Corrupt bound session tuple."))
                .when(states)
                .current(any(), any());
        assertThatThrownBy(() -> reader.queryCurrentBoundAuthentication())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Corrupt bound session tuple.");
    }

    @Test
    void failedStorageLookupCannotBeRelabelledAsOpaqueDenial() {
        var result = authenticate(fixture("LOCAL"));
        ambient(result.accessToken());
        doThrow(new IllegalStateException("Authentication storage lookup failed."))
                .when(bindings)
                .findByNativeIssuerAndJti(anyString(), anyString());
        assertThatThrownBy(() -> reader.queryCurrentBoundAuthentication())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void immutableDuplicateCannotCreateAmbiguousReaderEvidence() {
        var result = authenticate(fixture("LOCAL"));
        var old = bindings.findByNativeIssuerAndJti("hidra-api", result.jti()).orElseThrow();
        var tx = new TransactionTemplate(manager);
        tx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        assertThatThrownBy(
                        () ->
                                tx.execute(
                                        status -> {
                                            bindings.append(
                                                    copy(
                                                            old,
                                                            UUID.randomUUID().toString(),
                                                            old.sessionId(),
                                                            old.jti(),
                                                            old.producerAuthenticator()));
                                            return null;
                                        }))
                .isInstanceOf(RuntimeException.class);
        ambient(result.accessToken());
        assertThat(reader.queryCurrentBoundAuthentication().status()).isEqualTo("ELIGIBLE");
    }

    @Test
    void actualLocalProducerReceiptOnlyBecomesEligibleAfterCompletionCommit() {
        var f = fixture("LOCAL");
        var captured = new AtomicReference<org.springframework.security.core.Authentication>();
        doAnswer(
                        i -> {
                            var actual =
                                    (org.springframework.security.core.Authentication)
                                            i.callRealMethod();
                            captured.set(actual);
                            return actual;
                        })
                .when(localProvider)
                .authenticate(any());
        var v = directPort.verify(ProviderType.LOCAL, f.user(), "synthetic-password");
        SecurityContextHolder.getContext().setAuthentication(captured.get());
        denied();
        complete(v);
        assertThat(reader.queryCurrentBoundAuthentication().status()).isEqualTo("ELIGIBLE");
        directPort.finishVerification();
        SecurityContextHolder.getContext()
                .setAuthentication(
                        org.springframework.security.authentication
                                .UsernamePasswordAuthenticationToken.authenticated(
                                v.principal(), null, List.of()));
        denied();
    }

    @Test
    void actualOidcReceiptBeforeCompletionAndCopiedPublicTokenDeny() {
        var f = fixture("OIDC");
        var signed =
                sourceEncoder.encode(
                        JwtEncoderParameters.from(
                                JwtClaimsSet.builder()
                                        .issuer(SOURCE)
                                        .subject(f.subject())
                                        .issuedAt(Instant.now().minusSeconds(1))
                                        .expiresAt(Instant.now().plusSeconds(300))
                                        .build()));
        var actual = oidc.convert(realExternal.decode(signed.getTokenValue()));
        SecurityContextHolder.getContext().setAuthentication(actual);
        denied();
        completion.complete(input((HidraPrincipal) actual.getPrincipal(), null), null, null, null);
        assertThat(reader.queryCurrentBoundAuthentication().status()).isEqualTo("ELIGIBLE");
        SecurityContextHolder.getContext()
                .setAuthentication(
                        new HidraOidcAuthenticationToken((HidraPrincipal) actual.getPrincipal()));
        denied();
    }

    @Test
    void readerVersusNativeWriterObservesCommittedMvccImagesAndNoOperationFence() throws Exception {
        race(false);
    }

    @Test
    void readerVersusJpaWriterObservesCommittedMvccImagesAndNoOperationFence() throws Exception {
        race(true);
    }

    private void race(boolean jpa) throws Exception {
        var result = authenticate(fixture("LOCAL"));
        ambient(result.accessToken());
        var wrote = new java.util.concurrent.CountDownLatch(1);
        var release = new java.util.concurrent.CountDownLatch(1);
        var pid = new AtomicInteger();
        var pool = java.util.concurrent.Executors.newSingleThreadExecutor();
        var writer =
                pool.submit(
                        () -> {
                            if (jpa) {
                                var tx = new TransactionTemplate(manager);
                                tx.setIsolationLevel(
                                        TransactionDefinition.ISOLATION_READ_COMMITTED);
                                tx.execute(
                                        status -> {
                                            pid.set(backend());
                                            lifecycle.logoutSession(result.sessionId());
                                            wrote.countDown();
                                            await(release);
                                            return null;
                                        });
                            } else
                                try (Connection conn =
                                        DriverManager.getConnection(
                                                POSTGRES.getJdbcUrl(),
                                                POSTGRES.getUsername(),
                                                POSTGRES.getPassword())) {
                                    conn.setAutoCommit(false);
                                    conn.setTransactionIsolation(
                                            Connection.TRANSACTION_READ_COMMITTED);
                                    try (var st = conn.createStatement();
                                            var rows = st.executeQuery("SELECT pg_backend_pid()")) {
                                        rows.next();
                                        pid.set(rows.getInt(1));
                                    }
                                    try (var st =
                                            conn.prepareStatement(
                                                    "UPDATE "
                                                            + S
                                                            + " SET status='LOGGED_OUT',ended_at=clock_timestamp()"
                                                            + " WHERE id=?")) {
                                        st.setString(1, result.sessionId());
                                        st.executeUpdate();
                                    }
                                    wrote.countDown();
                                    await(release);
                                    conn.commit();
                                } catch (SQLException fault) {
                                    throw new IllegalStateException(
                                            "Synthetic writer failed.", fault);
                                }
                        });
        try {
            assertThat(wrote.await(15, java.util.concurrent.TimeUnit.SECONDS)).isTrue();
            assertThat(pid.get()).isNotEqualTo(backend());
            try (Connection observer =
                    DriverManager.getConnection(
                            POSTGRES.getJdbcUrl(),
                            POSTGRES.getUsername(),
                            POSTGRES.getPassword())) {
                observer.setAutoCommit(false);
                try (var st =
                        observer.prepareStatement(
                                "SELECT id FROM " + S + " WHERE id=? FOR UPDATE NOWAIT")) {
                    st.setString(1, result.sessionId());
                    try {
                        st.executeQuery();
                        throw new AssertionError("Writer must hold the actual session row lock.");
                    } catch (SQLException locked) {
                        assertThat(locked.getSQLState()).isEqualTo("55P03");
                        observer.rollback();
                    }
                }
            }
            assertThat(reader.queryCurrentBoundAuthentication().status()).isEqualTo("ELIGIBLE");
            release.countDown();
            writer.get(15, java.util.concurrent.TimeUnit.SECONDS);
            denied();
        } finally {
            release.countDown();
            pool.shutdownNow();
            assertThat(pool.awaitTermination(15, java.util.concurrent.TimeUnit.SECONDS)).isTrue();
        }
    }

    private static void await(java.util.concurrent.CountDownLatch latch) {
        try {
            if (!latch.await(15, java.util.concurrent.TimeUnit.SECONDS))
                throw new IllegalStateException("Synthetic barrier deadline.");
        } catch (InterruptedException stop) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Synthetic barrier interrupted.");
        }
    }

    private void denied() {
        var r = reader.queryCurrentBoundAuthentication();
        assertThat(r.status()).isEqualTo("ACCESS_DENIED");
        assertThat(r.evidence()).isNull();
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
