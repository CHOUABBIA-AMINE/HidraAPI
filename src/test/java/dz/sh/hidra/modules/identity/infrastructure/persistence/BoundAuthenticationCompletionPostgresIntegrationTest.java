/**
 * @Project : HidraAPI @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and
 * Analytics @Author : Abir MEDJERAB @Owner : Sonatrach / TRC : Digitalization Initiative @Name :
 * BoundAuthenticationCompletionPostgresIntegrationTest @CreatedOn : 2025-06-26 @UpdatedOn :
 * 2026-10-17 @Type : Class @Layer : Identity Test @Module : identity @Package :
 * dz.sh.hidra.modules.identity.infrastructure.persistence @Description : Proves real four-provider
 * verification, signed bound completion and physical owner-transaction commit/rollback on synthetic
 * PostgreSQL fixtures.
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
class BoundAuthenticationCompletionPostgresIntegrationTest {
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
    @MockitoSpyBean BoundSourceClaimEvidenceRepositoryPort sourceClaims;
    @MockitoSpyBean BoundSourceClaimIssuanceFencePort claimFence;
    @MockitoSpyBean ClaimNameCatalogPort claimCatalog;

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
        ReflectionTestUtils.setField(oidc, "verifiedClaimReceiptEnabled", false);
        SecurityContextHolder.clearContext();
        jdbc.update("UPDATE " + P + " SET status='INACTIVE' WHERE status='ACTIVE'");
        when(externalDecoder.decode(anyString()))
                .thenAnswer(i -> realExternal.decode(i.getArgument(0)));
    }

    @AfterEach
    void cleanInvocation() {
        ReflectionTestUtils.setField(oidc, "verifiedClaimReceiptEnabled", false);
        directPort.finishVerification();
        SecurityContextHolder.clearContext();
    }

    private void enableSyntheticNonownerCapture() {
        String role = "completion_claim_app";
        if (!Boolean.TRUE.equals(
                jdbc.queryForObject(
                        "SELECT EXISTS(SELECT 1 FROM pg_roles WHERE rolname=?)",
                        Boolean.class,
                        role))) {
            jdbc.execute(
                    "CREATE ROLE " + role + " LOGIN PASSWORD 'synthetic-completion-claims-only'");
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
                                POSTGRES.getJdbcUrl(), role, "synthetic-completion-claims-only"));
        var appCatalog =
                new dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
                        .JdbcClaimNameCatalogAdapter(appJdbc);
        doAnswer(
                        i -> {
                            if (TransactionSynchronizationManager.isActualTransactionActive())
                                return i.callRealMethod();
                            return appCatalog.selectVersioned(i.getArgument(0));
                        })
                .when(claimCatalog)
                .selectVersioned(anyString());
        doAnswer(
                        i -> {
                            jdbc.execute("SET LOCAL ROLE " + role);
                            return i.callRealMethod();
                        })
                .when(claimFence)
                .prepare(anyString(), any());
        ReflectionTestUtils.setField(oidc, "verifiedClaimReceiptEnabled", true);
    }

    private void addCaptureMapping(Fixture f, String claim) {
        String role = UUID.randomUUID().toString();
        jdbc.update(
                "INSERT INTO public.hidra_identity_role"
                    + " (id,code,role_type,status,created_at,updated_at) VALUES"
                    + " (?,?,'EXTERNAL_MAPPED','ACTIVE',now(),now())",
                role,
                role);
        jdbc.update(
                "INSERT INTO public.hidra_identity_external_role_mapping"
                    + " (id,identity_provider_id,role_id,external_role_code,claim_name,mapping_mode,status,created_at,updated_at)"
                    + " VALUES (?,?,?,?,?,'DIRECT_GRANT','ACTIVE',now(),now())",
                UUID.randomUUID().toString(),
                f.provider(),
                role,
                claim,
                claim);
    }

    private AuthenticationResult capturedOidc(Fixture f) {
        var signed =
                sourceEncoder.encode(
                        JwtEncoderParameters.from(
                                JwtClaimsSet.builder()
                                        .issuer(SOURCE)
                                        .subject(f.subject())
                                        .audience(List.of("hidra-api"))
                                        .issuedAt(Instant.now().minusSeconds(1))
                                        .expiresAt(
                                                Instant.now()
                                                        .plusSeconds(300)
                                                        .truncatedTo(ChronoUnit.SECONDS))
                                        .claim("groups", List.of("private-original-group"))
                                        .build()));
        var actual = oidc.convert(realExternal.decode(signed.getTokenValue()));
        SecurityContextHolder.getContext().setAuthentication(actual);
        return completion.complete(
                input((HidraPrincipal) actual.getPrincipal(), null), null, null, null);
    }

    @Test
    void optInOriginalOidcClaimsPersistWithNativeBindingUnderNonownerRole() {
        var f = fixture("OIDC");
        enableSyntheticNonownerCapture();
        addCaptureMapping(f, "groups");
        addCaptureMapping(f, "missing");
        var result = capturedOidc(f);
        var binding = bindings.findByNativeIssuerAndJti("hidra-api", result.jti()).orElseThrow();
        var claims = sourceClaims.findByBindingId(binding.bindingId()).orElseThrow();
        registry.validatePersistentSourceClaimOrigin(binding, claims);
        assertThat(claims.coverage().values("groups")).containsExactly("private-original-group");
        assertThat(claims.coverage().state("missing")).isEqualTo(ClaimCoverage.State.ABSENT);
        assertThat(claims.mappingGeneration()).isEqualTo(3);
        assertThat(nativeDecoder.decode(result.accessToken()).getClaims())
                .doesNotContainKeys("groups", "missing");
        verifyStored(f, result);
    }

    @Test
    void companionAppendFailureRollsBackNativeSessionBindingAndSuccessEvent() {
        var f = fixture("OIDC");
        enableSyntheticNonownerCapture();
        doThrow(new IllegalStateException("Synthetic companion refusal"))
                .when(sourceClaims)
                .append(any());
        assertThatThrownBy(() -> capturedOidc(f)).isInstanceOf(IllegalStateException.class);
        assertNoOwnerSuccess(f);
        Object completed =
                ReflectionTestUtils.invokeMethod(
                        registry,
                        "completed",
                        SecurityContextHolder.getContext().getAuthentication());
        assertThat(completed).isNull();
    }

    @Test
    void defaultOffOidcNeverInvokesClaimFenceOrCompanionStorage() {
        var f = fixture("OIDC");
        authenticate(f);
        verify(claimFence, never()).prepare(anyString(), any());
        verify(sourceClaims, never()).append(any());
    }

    @Test
    void localCompletionHasRealCapturedCredentialAndSignedPersistentOrigin() {
        verifySuccess(fixture("LOCAL"));
    }

    @Test
    void ldapCompletionHasExactVerifiedSubjectAndOneBoundSuccessEvent() {
        verifySuccess(fixture("LDAP"));
    }

    @Test
    void activeDirectoryCompletionRetainsActualTypeAndLdapSessionProtocol() {
        verifySuccess(fixture("ACTIVE_DIRECTORY"));
    }

    @Test
    void oidcCompletionUsesOriginalSignedBearerAndFloorsItsSourceCap() {
        var f = fixture("OIDC");
        Instant sourceEnd = Instant.now().truncatedTo(ChronoUnit.SECONDS).plusSeconds(90);
        var result = completeOidc(f, sourceEnd);
        assertThat(result.expiresAt()).isEqualTo(sourceEnd);
        verifyStored(f, result);
        verify(externalDecoder).decode(anyString());
    }

    @Test
    void allOwnerWritesAndSigningObserveOneBackendAndCommitBeforeDelivery() throws Exception {
        var f = fixture("LDAP");
        Set<Integer> backends = new HashSet<>();
        AtomicBoolean committed = new AtomicBoolean();
        try (Connection outsider =
                DriverManager.getConnection(
                        POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword())) {
            int outsiderId;
            try (var st = outsider.createStatement();
                    var rows = st.executeQuery("SELECT pg_backend_pid()")) {
                rows.next();
                outsiderId = rows.getInt(1);
            }
            doAnswer(
                            i -> {
                                backends.add(backend());
                                return i.callRealMethod();
                            })
                    .when(sessions)
                    .save(any());
            doAnswer(
                            i -> {
                                backends.add(backend());
                                assertThat(
                                                TransactionSynchronizationManager
                                                        .isActualTransactionActive())
                                        .isTrue();
                                try (var st =
                                        outsider.prepareStatement(
                                                "SELECT count(*) FROM " + S + " WHERE user_id=?")) {
                                    st.setString(1, f.user());
                                    try (var rows = st.executeQuery()) {
                                        rows.next();
                                        assertThat(rows.getInt(1)).isZero();
                                    }
                                }
                                TransactionSynchronizationManager.registerSynchronization(
                                        new TransactionSynchronization() {
                                            @Override
                                            public void afterCommit() {
                                                committed.set(true);
                                            }
                                        });
                                return i.callRealMethod();
                            })
                    .when(issuer)
                    .issue(any(), any());
            doAnswer(
                            i -> {
                                backends.add(backend());
                                return i.callRealMethod();
                            })
                    .when(bindings)
                    .append(any());
            doAnswer(
                            i -> {
                                backends.add(backend());
                                return i.callRealMethod();
                            })
                    .when(events)
                    .save(any());
            var result = authenticate(f);
            assertThat(committed).isTrue();
            assertThat(backends).hasSize(1).doesNotContain(outsiderId);
            verifyStored(f, result);
        }
    }

    @Test
    void sessionFlushFailureReturnsNothingAndRollsBackOwnerState() {
        var f = fixture("LDAP");
        doThrow(new IllegalStateException("synthetic save fault")).when(sessions).save(any());
        assertFailure(f);
    }

    @Test
    void signingFailureRollsBackFlushedSessionWithoutRetry() {
        var f = fixture("LDAP");
        doThrow(new IllegalStateException("synthetic sign fault")).when(issuer).issue(any(), any());
        assertFailure(f);
        verify(issuer, times(1)).issue(any(), any());
    }

    @Test
    void bindingAppendFailureRollsBackSignedSessionAndExposesNoBearer() {
        var f = fixture("LDAP");
        doThrow(new IllegalStateException("synthetic append fault")).when(bindings).append(any());
        assertFailure(f);
    }

    @Test
    void externalEventFailureRollsBackSessionBindingAndPublication() {
        var f = fixture("OIDC");
        doThrow(new IllegalStateException("synthetic event fault")).when(events).save(any());
        assertThatThrownBy(
                        () ->
                                completeOidc(
                                        f,
                                        Instant.now()
                                                .plusSeconds(300)
                                                .truncatedTo(ChronoUnit.SECONDS)))
                .isInstanceOf(RuntimeException.class);
        assertNoOwnerSuccess(f);
        assertThat(
                        (OperationAuthenticationBinding)
                                ReflectionTestUtils.invokeMethod(
                                        registry,
                                        "completed",
                                        SecurityContextHolder.getContext().getAuthentication()))
                .isNull();
    }

    @Test
    void actualPostgresqlDeferredCommitFailureReturnsNoResultAndRollsBackEverything() {
        var f = fixture("LDAP");
        doAnswer(
                        i -> {
                            Object result = i.callRealMethod();
                            jdbc.execute(
                                    "CREATE TEMP TABLE synthetic_commit_fault(owner_id varchar(80)"
                                            + " REFERENCES "
                                            + U
                                            + "(id) DEFERRABLE INITIALLY DEFERRED)");
                            jdbc.update(
                                    "INSERT INTO synthetic_commit_fault VALUES (?)",
                                    UUID.randomUUID().toString());
                            return result;
                        })
                .when(bindings)
                .append(any());
        assertFailure(f);
    }

    @Test
    void beforeCommitFaultCannotPublishOidcReceiptOrDeliverSignedResult() {
        var f = fixture("OIDC");
        doAnswer(
                        i -> {
                            Object result = i.callRealMethod();
                            TransactionSynchronizationManager.registerSynchronization(
                                    new TransactionSynchronization() {
                                        @Override
                                        public void beforeCommit(boolean readOnly) {
                                            throw new IllegalStateException(
                                                    "synthetic precommit fault");
                                        }
                                    });
                            return result;
                        })
                .when(bindings)
                .append(any());
        assertThatThrownBy(
                        () ->
                                completeOidc(
                                        f,
                                        Instant.now()
                                                .plusSeconds(300)
                                                .truncatedTo(ChronoUnit.SECONDS)))
                .isInstanceOf(RuntimeException.class);
        assertNoOwnerSuccess(f);
        assertThat(
                        (OperationAuthenticationBinding)
                                ReflectionTestUtils.invokeMethod(
                                        registry,
                                        "completed",
                                        SecurityContextHolder.getContext().getAuthentication()))
                .isNull();
    }

    @Test
    void providerAbaAfterVerificationCannotCreateAnUnboundSuccess() {
        var f = fixture("LOCAL");
        var verified = directPort.verify(ProviderType.LOCAL, f.user(), "synthetic-password");
        jdbc.update("UPDATE " + P + " SET status='INACTIVE' WHERE id=?", f.provider());
        jdbc.update("UPDATE " + P + " SET status='ACTIVE' WHERE id=?", f.provider());
        assertThatThrownBy(() -> complete(verified)).isInstanceOf(RuntimeException.class);
        assertNoOwnerSuccess(f);
    }

    @Test
    void credentialAbaAfterVerificationRejectsTheOriginalHashGeneration() {
        var f = fixture("LOCAL");
        var verified = directPort.verify(ProviderType.LOCAL, f.user(), "synthetic-password");
        String old =
                jdbc.queryForObject(
                        "SELECT password_hash FROM " + C + " WHERE id=?",
                        String.class,
                        f.credential());
        jdbc.update(
                "UPDATE " + C + " SET password_hash='synthetic-changed' WHERE id=?",
                f.credential());
        jdbc.update("UPDATE " + C + " SET password_hash=? WHERE id=?", old, f.credential());
        assertThatThrownBy(() -> complete(verified)).isInstanceOf(RuntimeException.class);
        assertNoOwnerSuccess(f);
    }

    @Test
    void directoryLinkAbaAfterRealVerificationRejectsOriginalReceipt() {
        var f = fixture("LDAP");
        var verified = directPort.verify(ProviderType.LDAP, f.user(), "synthetic-password");
        jdbc.update("UPDATE " + E + " SET status='UNLINKED' WHERE id=?", f.link());
        jdbc.update("UPDATE " + E + " SET status='LINKED' WHERE id=?", f.link());
        assertThatThrownBy(() -> complete(verified)).isInstanceOf(RuntimeException.class);
        assertNoOwnerSuccess(f);
    }

    @Test
    void changedAtomicCredentialDuringPasswordVerificationCannotRelabelSuccess() {
        var f = fixture("LOCAL");
        doAnswer(
                        i -> {
                            Object result = i.callRealMethod();
                            jdbc.update(
                                    "UPDATE "
                                            + C
                                            + " SET password_hash='synthetic-changed' WHERE id=?",
                                    f.credential());
                            return result;
                        })
                .when(passwords)
                .matches(any(), anyString());
        assertFailure(f);
        // LOCAL verification outcome is independent; failed bound completion leaves no
        // session/binding.
    }

    @Test
    void forgedPublicCompletionInputCannotMintPrivateOrigin() {
        var f = fixture("LOCAL");
        assertThatThrownBy(
                        () ->
                                completion.complete(
                                        new AuthenticatedPrincipalInput(
                                                f.user(),
                                                f.user(),
                                                null,
                                                ProviderType.LOCAL,
                                                f.provider(),
                                                Set.of(),
                                                Set.of()),
                                        null,
                                        null,
                                        null))
                .isInstanceOf(RuntimeException.class);
        assertNoOwnerSuccess(f);
    }

    @Test
    void completionInsideCallerTransactionRejectsDeliveryBeforeOuterCommit() {
        var f = fixture("LDAP");
        TransactionTemplate tx = new TransactionTemplate(manager);
        tx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        assertThatThrownBy(() -> tx.execute(status -> authenticate(f)))
                .isInstanceOf(RuntimeException.class);
        assertNoOwnerSuccess(f);
    }

    @Test
    void repeatedOidcReceiptCannotCreateASecondSession() {
        var f = fixture("OIDC");
        var result =
                completeOidc(f, Instant.now().plusSeconds(300).truncatedTo(ChronoUnit.SECONDS));
        var p =
                (HidraPrincipal)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assertThatThrownBy(() -> completion.complete(input(p, null), null, null, null))
                .isInstanceOf(RuntimeException.class);
        assertThat(count(S, f.user())).isEqualTo(1);
        verifyStored(f, result);
    }

    @Test
    void forgedOidcJwtCannotBypassQualifiedCryptographicReadback() {
        var f = fixture("OIDC");
        var fake =
                Jwt.withTokenValue("synthetic-forged-not-a-jwt")
                        .header("alg", "HS256")
                        .issuer(SOURCE)
                        .subject(f.subject())
                        .issuedAt(Instant.now().minusSeconds(1))
                        .expiresAt(Instant.now().plusSeconds(300))
                        .build();
        assertThatThrownBy(() -> oidc.convert(fake)).isInstanceOf(RuntimeException.class);
        assertNoOwnerSuccess(f);
    }

    @Test
    void wrongPasswordHasNoProducerReceiptSessionOrBinding() {
        var f = fixture("LOCAL");
        assertThatThrownBy(
                        () ->
                                direct.authenticate(
                                        new DirectAuthenticationCommand(
                                                ProviderType.LOCAL,
                                                f.user(),
                                                "wrong",
                                                null,
                                                null,
                                                null)))
                .isInstanceOf(RuntimeException.class);
        assertThat(count(S, f.user())).isZero();
        assertThat(count(B, f.user())).isZero();
    }

    @Test
    void realDirectoryVerificationDenialCannotFallBackToUnboundCompletion() {
        var f = fixture("LDAP");
        when(directory.verify(f.user(), "synthetic-password")).thenReturn(Optional.empty());
        assertFailure(f);
    }

    @Test
    void nativeCryptographicReadbackFailureIsSanitizedAndRollsBack() {
        var f = fixture("LDAP");
        doAnswer(
                        i -> {
                            var principal = (HidraPrincipal) i.getArgument(0);
                            var context = (AccessTokenIssuanceContext) i.getArgument(1);
                            var malformed =
                                    new HidraAccessTokenIssuer(
                                            parameters ->
                                                    Jwt.withTokenValue("private-synthetic-bearer")
                                                            .header("alg", "HS256")
                                                            .claims(
                                                                    c ->
                                                                            c.putAll(
                                                                                    parameters
                                                                                            .getClaims()
                                                                                            .getClaims()))
                                                            .build(),
                                            "hidra-api",
                                            "hidra-api",
                                            "sub",
                                            "roles",
                                            "scope",
                                            900,
                                            new org.springframework.beans.factory.support
                                                            .StaticListableBeanFactory(
                                                            Map.of("decoder", nativeDecoder))
                                                    .getBeanProvider(JwtDecoder.class),
                                            65536);
                            return malformed.issue(principal, context);
                        })
                .when(issuer)
                .issue(any(), any());
        assertThatThrownBy(() -> authenticate(f))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Bound native signing or readback failed.");
        assertNoOwnerSuccess(f);
    }

    private void verifySuccess(Fixture f) {
        var result = authenticate(f);
        verifyStored(f, result);
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

    private void assertFailure(Fixture f) {
        assertThatThrownBy(() -> authenticate(f)).isInstanceOf(RuntimeException.class);
        assertNoOwnerSuccess(f);
    }

    private void assertNoOwnerSuccess(Fixture f) {
        assertThat(count(S, f.user())).isZero();
        assertThat(count(B, f.user())).isZero();
        if (!f.type().equals("LOCAL"))
            assertThat(
                            jdbc.queryForObject(
                                    "SELECT count(*) FROM"
                                            + " public.hidra_identity_authentication_event WHERE"
                                            + " user_id=? AND event_type='LOGIN_SUCCESS'",
                                    Integer.class,
                                    f.user()))
                    .isZero();
    }

    private int count(String table, String user) {
        return jdbc.queryForObject(
                "SELECT count(*) FROM " + table + " WHERE user_id=?", Integer.class, user);
    }

    private int backend() {
        return jdbc.queryForObject("SELECT pg_backend_pid()", Integer.class);
    }

    private void verifyStored(Fixture f, AuthenticationResult result) {
        assertThat(TransactionSynchronizationManager.isActualTransactionActive()).isFalse();
        var token = nativeDecoder.decode(result.accessToken());
        var binding = bindings.findByNativeIssuerAndJti("hidra-api", result.jti()).orElseThrow();
        registry.validatePersistentOrigin(binding);
        assertThat(binding.sessionId()).isEqualTo(result.sessionId());
        assertThat(binding.userId()).isEqualTo(f.user());
        assertThat(binding.authenticationType()).isEqualTo(f.type());
        assertThat(binding.providerSecurityVersion()).isEqualTo(1);
        assertThat(binding.issuedAt().getNano()).isZero();
        assertThat(binding.expiresAt().getNano()).isZero();
        assertThat(token.getSubject()).isEqualTo(f.user());
        assertThat(token.getClaims().get("jti")).isEqualTo(binding.jti());
        assertThat(token.getIssuedAt()).isEqualTo(binding.issuedAt());
        assertThat(token.getExpiresAt()).isEqualTo(binding.expiresAt());
        assertThat(token.getClaims().get("identity_provider_id")).isEqualTo(f.provider());
        assertThat(token.getClaims().get("authentication_type")).isEqualTo(f.type());
        var session = jdbc.queryForMap("SELECT * FROM " + S + " WHERE id=?", result.sessionId());
        assertThat(session.get("started_at")).isEqualTo(Timestamp.from(binding.issuedAt()));
        assertThat(session.get("expires_at")).isEqualTo(Timestamp.from(binding.expiresAt()));
        assertThat(session.get("session_type"))
                .isEqualTo(f.type().equals("ACTIVE_DIRECTORY") ? "LDAP" : f.type());
        if (f.link() != null) {
            assertThat(binding.externalSubject()).isEqualTo(f.subject());
            assertThat(binding.externalIdentityId()).isEqualTo(f.link());
        } else {
            assertThat(binding.credentialId()).isEqualTo(f.credential());
            assertThat(binding.credentialSecurityVersion()).isEqualTo(1);
        }
        assertThat(count(S, f.user())).isEqualTo(1);
        assertThat(count(B, f.user())).isEqualTo(1);
        assertThat(
                        jdbc.queryForObject(
                                "SELECT count(*) FROM public.hidra_identity_authentication_event"
                                        + " WHERE user_id=? AND event_type='LOGIN_SUCCESS'",
                                Integer.class,
                                f.user()))
                .isEqualTo(1);
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
