/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedOperationIdentityParticipantTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-21
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Checks mocked participant mechanics while keeping genuine publication provenance in PostgreSQL tests.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import dz.sh.hidra.modules.identity.application.model.AccessTokenIssuerSettings;
import dz.sh.hidra.modules.identity.application.port.out.*;
import dz.sh.hidra.modules.identity.domain.model.OperationAuthenticationBinding;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvaluationRequest;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityAuthorityFence;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcIdentityTemporalEventCollector.Candidate;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

/**
 * All infrastructure here is mocked; real provenance and transaction proof lives in the PG suite.
 */
class ProtectedOperationIdentityParticipantTest {
    private final OperationAuthenticationBindingRepositoryPort bindings =
            mock(OperationAuthenticationBindingRepositoryPort.class);
    private final AuthenticationSecurityStateQueryPort states =
            mock(AuthenticationSecurityStateQueryPort.class);
    private final AccessTokenIssuerPort issuer = mock(AccessTokenIssuerPort.class);
    private final TrustedAuthenticationVerificationRegistry registry =
            mock(TrustedAuthenticationVerificationRegistry.class);
    private final JdbcIdentityAuthorityFence fence = mock(JdbcIdentityAuthorityFence.class);
    private final JdbcTemplate jdbc = mock(JdbcTemplate.class);
    private final javax.sql.DataSource claimDataSource = mock(javax.sql.DataSource.class);
    private final JwtDecoder decoder = mock(JwtDecoder.class);

    @SuppressWarnings("unchecked")
    private final ObjectProvider<JwtDecoder> provider = mock(ObjectProvider.class);

    private ProtectedOperationIdentityParticipant participant;
    private OperationAuthenticationBinding b;
    private Jwt verified;
    private String raw;
    private final Instant issued = Instant.parse("2026-10-15T00:00:00Z");

    @BeforeEach
    void setup() {
        b =
                new OperationAuthenticationBinding(
                        id(),
                        (short) 1,
                        "hidra-api",
                        "hidra-api",
                        id(),
                        id(),
                        "synthetic-user",
                        "LOCAL",
                        "LOCAL",
                        "synthetic-provider",
                        1L,
                        null,
                        null,
                        null,
                        null,
                        null,
                        "synthetic-credential",
                        1L,
                        issued,
                        issued.plusSeconds(600),
                        new byte[32]);
        raw =
                wire(
                        "{\"iss\":\"hidra-api\",\"aud\":\"hidra-api\",\"sub\":\"synthetic-user\",\"jti\":\""
                                + b.jti()
                                + "\",\"authentication_type\":\"LOCAL\",\"identity_provider_id\":\"synthetic-provider\",\"iat\":"
                                + issued.getEpochSecond()
                                + ",\"exp\":"
                                + b.expiresAt().getEpochSecond()
                                + "}");
        verified =
                Jwt.withTokenValue(raw)
                        .header("alg", "HS256")
                        .issuer("hidra-api")
                        .audience(List.of("hidra-api"))
                        .subject(b.userId())
                        .claim("jti", b.jti())
                        .claim("authentication_type", "LOCAL")
                        .claim("identity_provider_id", b.providerId())
                        .issuedAt(issued)
                        .expiresAt(b.expiresAt())
                        .build();
        when(provider.getObject()).thenReturn(decoder);
        when(jdbc.getDataSource()).thenReturn(claimDataSource);
        doReturn(verified).when(decoder).decode(raw);
        when(issuer.settings())
                .thenReturn(new AccessTokenIssuerSettings("hidra-api", "hidra-api", 600));
        when(bindings.findByNativeIssuerAndJti("hidra-api", b.jti())).thenReturn(Optional.of(b));
        when(states.current(any(), any())).thenReturn(true);
        when(jdbc.queryForObject(anyString(), eq(Boolean.class))).thenReturn(true);
        when(jdbc.queryForObject(anyString(), eq(Boolean.class), anyInt(), anyLong()))
                .thenReturn(true);
        when(fence.collect(b.bindingId()))
                .thenReturn(
                        new Candidate(
                                issued.plusSeconds(1),
                                b.expiresAt(),
                                13,
                                42L,
                                List.of(),
                                List.of(),
                                1,
                                0));
        participant =
                new ProtectedOperationIdentityParticipant(
                        bindings, states, provider, issuer, registry, fence, jdbc, 65536);
        ambient(verified);
    }

    @AfterEach
    void clean() {
        if (TransactionSynchronizationManager.hasResource(claimDataSource))
            TransactionSynchronizationManager.unbindResource(claimDataSource);
        SecurityContextHolder.clearContext();
        TransactionSynchronizationManager.clear();
    }

    private dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Request appointmentRequest() {
        var action = dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Action.TRANSFER;
        return new dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Request(action,
                List.of(new dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.CompanyUnit("company", "unit-a"),
                        new dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.CompanyUnit("company", "unit-b")), "employee");
    }

    private ProtectedOperationIdentityParticipant.OrganizationClaimGroup appointmentGroup() {
        TransactionSynchronizationManager.setActualTransactionActive(false);
        var prepared = participant.prepare();
        TransactionSynchronizationManager.setActualTransactionActive(true);
        TransactionSynchronizationManager.initSynchronization();
        TransactionSynchronizationManager.bindResource(claimDataSource,
                new org.springframework.jdbc.datasource.ConnectionHolder(mock(java.sql.Connection.class)));
        when(fence.collect((JdbcIdentityAuthorityFence.Admission) isNull()))
                .thenReturn(new Candidate(issued.plusSeconds(1), b.expiresAt(), 13, 42L, List.of(), List.of(), 1, 0));
        when(jdbc.queryForObject("SELECT pg_catalog.clock_timestamp()", java.sql.Timestamp.class))
                .thenReturn(java.sql.Timestamp.from(issued.plusSeconds(2)));
        when(jdbc.queryForObject(startsWith("WITH organization_clock"), eq(Boolean.class),
                any(), any(), anyInt(), anyLong())).thenReturn(true);
        when(jdbc.queryForObject(startsWith("SELECT NOT EXISTS (SELECT 1 FROM ("), eq(Boolean.class),
                any(), any())).thenReturn(true);
        return participant.qualifyOrganizationGroup(participant.admit(prepared), appointmentRequest());
    }

    @Test void appointmentChildrenShareOneNativeSampleAndExactFiniteScopes() {
        var group = appointmentGroup();
        var reads = participant.consumeOrganizationGroup(group, appointmentRequest());
        assertThat(reads.credentials()).hasSize(4);
        assertThat(reads.requests()).extracting(AuthorizationEvaluationRequest::resourceType)
                .containsExactly("ORGANIZATION_COMPANY", "ORGANIZATION_UNIT", "ORGANIZATION_UNIT", "ORGANIZATION_EMPLOYEE");
        assertThat(reads.requests()).extracting(r -> r.scope().scopeType())
                .containsExactly(ScopeType.CUSTOM, ScopeType.ORGANIZATION_UNIT, ScopeType.ORGANIZATION_UNIT, ScopeType.CUSTOM);
        for (int i = 0; i < reads.credentials().size(); i++) {
            var request = reads.requests().get(i);
            assertThat(request.resourceReferenceId()).isEqualTo(request.scope().scopeReferenceId());
            assertThat(participant.consumeClaims(reads.credentials().get(i), request)).isSameAs(reads.contribution());
            assertThat(participant.organizationCredential(reads.credentials().get(i))).isTrue();
            assertThat(participant.engineeringDraftCredential(reads.credentials().get(i))).isFalse();
        }
        participant.requireCurrentOrganizationGroup(group, appointmentRequest());
        verify(fence, times(1)).collect((JdbcIdentityAuthorityFence.Admission) isNull());
        assertThatThrownBy(() -> participant.consumeOrganizationGroup(group, appointmentRequest())).isInstanceOf(AccessDeniedException.class);
        assertThat(((org.springframework.jdbc.datasource.ConnectionHolder)TransactionSynchronizationManager.getResource(claimDataSource)).isRollbackOnly()).isTrue();
    }

    @Test void appointmentGroupWrongThreadPoisonsOriginalHolderWithoutTransactionAdvice() throws Exception {
        var group = appointmentGroup();
        var worker = Executors.newSingleThreadExecutor();
        try {
            worker.submit(() -> assertThatThrownBy(() -> participant.consumeOrganizationGroup(group, appointmentRequest()))
                    .isInstanceOf(AccessDeniedException.class)).get(5, TimeUnit.SECONDS);
        } finally { worker.shutdownNow(); }
        assertThat(((org.springframework.jdbc.datasource.ConnectionHolder)TransactionSynchronizationManager.getResource(claimDataSource)).isRollbackOnly()).isTrue();
    }

    @Test void appointmentGroupRejectsAnotherIssuerAndPoisonsOriginalHolder() {
        var group = appointmentGroup();
        var other = new ProtectedOperationIdentityParticipant(bindings, states, provider, issuer, registry, fence, jdbc, 65536);
        assertThatThrownBy(() -> other.consumeOrganizationGroup(group, appointmentRequest())).isInstanceOf(AccessDeniedException.class);
        assertThat(((org.springframework.jdbc.datasource.ConnectionHolder)TransactionSynchronizationManager.getResource(claimDataSource)).isRollbackOnly()).isTrue();
    }

    @Test void appointmentGroupFinalExpiryEqualityFailsBeforeEvidenceDisclosure() {
        var group = appointmentGroup();
        when(jdbc.queryForObject("SELECT pg_catalog.clock_timestamp()", java.sql.Timestamp.class))
                .thenReturn(java.sql.Timestamp.from(b.expiresAt()));
        assertThatThrownBy(() -> participant.consumeOrganizationGroup(group, appointmentRequest())).isInstanceOf(AccessDeniedException.class);
        assertThat(((org.springframework.jdbc.datasource.ConnectionHolder)TransactionSynchronizationManager.getResource(claimDataSource)).isRollbackOnly()).isTrue();
    }

    private AuthorizationEvaluationRequest claimRequest() {
        return new AuthorizationEvaluationRequest(
                b.userId(),
                "topology:station-revision:author",
                "STATION_GRAPH_REVISION",
                "facility",
                new AuthorizationScope(ScopeType.FACILITY, "facility", null));
    }

    private ProtectedOperationIdentityParticipant.QualifiedClaimRead qualifiedClaim() {
        TransactionSynchronizationManager.setActualTransactionActive(false);
        var prepared = participant.prepare();
        TransactionSynchronizationManager.setActualTransactionActive(true);
        TransactionSynchronizationManager.bindResource(claimDataSource, new Object());
        return participant.qualifyClaims(prepared, claimRequest());
    }

    @Test
    void claimOwnerConnectionResourceCannotBeReplacedBeforeFirstConsumption() {
        var qualified = qualifiedClaim();
        TransactionSynchronizationManager.unbindResource(claimDataSource);
        TransactionSynchronizationManager.bindResource(claimDataSource, new Object());
        assertThatThrownBy(() -> participant.consumeClaims(qualified, claimRequest()))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void claimQualificationRejectsNativeDeadlineEqualityOrFutureIssuedSample() {
        for (Instant sample : List.of(b.expiresAt(), b.issuedAt().minusSeconds(1))) {
            TransactionSynchronizationManager.setActualTransactionActive(false);
            var prepared = participant.prepare();
            TransactionSynchronizationManager.setActualTransactionActive(true);
            when(fence.collect(b.bindingId()))
                    .thenReturn(
                            new Candidate(
                                    sample,
                                    b.expiresAt().plusSeconds(1),
                                    13,
                                    42L,
                                    List.of(),
                                    List.of(),
                                    1,
                                    0));
            assertThatThrownBy(() -> participant.qualifyClaims(prepared, claimRequest()))
                    .isInstanceOf(AccessDeniedException.class);
        }
    }

    @Test
    void postReadValidationRefusesUnconsumedHandleAndReplacedActualContext() {
        var qualified = qualifiedClaim();
        assertThatThrownBy(() -> participant.requireCurrentClaims(qualified, claimRequest()))
                .isInstanceOf(AccessDeniedException.class);
        participant.consumeClaims(qualified, claimRequest());
        participant.requireCurrentClaims(qualified, claimRequest());
        SecurityContextHolder.clearContext();
        assertThatThrownBy(() -> participant.requireCurrentClaims(qualified, claimRequest()))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void directoryCaptureEntryPointsRemainPrivateAndHaveOnlyReviewedCallers() throws Exception {
        Path root = Path.of("src/main/java/dz/sh/hidra/modules/identity/infrastructure/security");
        var expected =
                java.util.Map.of(
                        ".beginDirectoryCapture(",
                        "LdapAuthenticationProvider.java",
                        ".directoryCaptureSelection(",
                        "LdapCredentialVerificationAdapter.java",
                        ".captureDirectory(",
                        "LdapCredentialVerificationAdapter.java");
        for (var seam : expected.entrySet()) {
            var callers = new java.util.ArrayList<String>();
            try (var paths = java.nio.file.Files.list(root)) {
                for (var path : paths.filter(p -> p.toString().endsWith(".java")).toList())
                    if (!path.getFileName()
                                    .toString()
                                    .equals("TrustedAuthenticationVerificationRegistry.java")
                            && java.nio.file.Files.readString(path).contains(seam.getKey()))
                        callers.add(path.getFileName().toString());
            }
            assertThat(callers).containsExactly(seam.getValue());
        }
        var registry =
                Class.forName(
                        "dz.sh.hidra.modules.identity.infrastructure.security.TrustedAuthenticationVerificationRegistry");
        for (var method : registry.getDeclaredMethods())
            if (java.util.Set.of(
                            "beginDirectoryCapture",
                            "directoryCaptureSelection",
                            "captureDirectory")
                    .contains(method.getName()))
                assertThat(java.lang.reflect.Modifier.isPublic(method.getModifiers())).isFalse();
    }

    @Test
    void c3ClaimCredentialCannotBeConstructedOrReusedWithoutAPhysicalTransaction() {
        var type = ProtectedOperationIdentityParticipant.QualifiedClaimRead.class;
        assertThat(type.getConstructors()).isEmpty();
        assertThat(Modifier.isFinal(type.getModifiers())).isTrue();
        assertThat(
                        Arrays.stream(type.getDeclaredConstructors())
                                .allMatch(c -> Modifier.isPrivate(c.getModifiers())))
                .isTrue();
        assertThat(
                        Arrays.stream(type.getDeclaredFields())
                                .allMatch(
                                        f ->
                                                Modifier.isPrivate(f.getModifiers())
                                                        && Modifier.isFinal(f.getModifiers())))
                .isTrue();
        assertThat(type.getDeclaredMethods())
                .extracting(java.lang.reflect.Method::getName)
                .contains("toString");
        assertThatThrownBy(() -> participant.consumeClaims(null, null))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void preparedHandleHasNoPublicConstructorOrSensitiveText() {
        var p = participant.prepare();
        assertThat(p.getClass().getConstructors()).isEmpty();
        assertThat(Modifier.isFinal(p.getClass().getModifiers())).isTrue();
        assertThat(p.toString())
                .isEqualTo("Prepared[opaque]")
                .doesNotContain(raw, b.jti(), b.userId());
        assertThat(
                        Arrays.stream(p.getClass().getDeclaredFields())
                                .allMatch(
                                        f ->
                                                Modifier.isPrivate(f.getModifiers())
                                                        && Modifier.isFinal(f.getModifiers())))
                .isTrue();
    }

    @Test
    void preparationRefusesAmbientTransaction() throws Exception {
        TransactionSynchronizationManager.setActualTransactionActive(true);
        assertThatThrownBy(participant::prepare).isInstanceOf(IllegalStateException.class);
        assertThat(
                        ProtectedOperationIdentityParticipant.class
                                .getMethod("prepare")
                                .getAnnotation(Transactional.class)
                                .propagation())
                .isEqualTo(Propagation.NEVER);
        assertThat(
                        ProtectedOperationIdentityParticipant.class
                                .getMethod(
                                        "contribute",
                                        ProtectedOperationIdentityParticipant.Prepared.class)
                                .getAnnotation(Transactional.class)
                                .propagation())
                .isEqualTo(Propagation.MANDATORY);
        verifyNoInteractions(fence, states);
    }

    @Test
    void wireAndDecodedOwnerClaimsMustAgree() {
        Jwt wrong =
                Jwt.withTokenValue(raw)
                        .header("alg", "HS256")
                        .issuer("hidra-api")
                        .audience(List.of("hidra-api"))
                        .subject("different")
                        .issuedAt(issued)
                        .expiresAt(b.expiresAt())
                        .build();
        when(decoder.decode(raw)).thenReturn(wrong);
        assertThatThrownBy(participant::prepare).isInstanceOf(AccessDeniedException.class);
        doReturn(verified).when(decoder).decode(raw);
        ambient(
                Jwt.withTokenValue(raw)
                        .header("alg", "HS256")
                        .subject("untrusted-wrapper")
                        .build());
        assertThat(participant.prepare()).isNotNull();
        when(decoder.decode(raw))
                .thenReturn(
                        Jwt.withTokenValue("different-wire")
                                .header("alg", "HS256")
                                .subject("x")
                                .build());
        assertThatThrownBy(participant::prepare).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void publicAndLegacyContextsCannotCreatePreparedHandle() {
        SecurityContextHolder.clearContext();
        assertThatThrownBy(participant::prepare).isInstanceOf(AccessDeniedException.class);
        SecurityContextHolder.getContext()
                .setAuthentication(
                        UsernamePasswordAuthenticationToken.authenticated(
                                "synthetic-user", null, List.of()));
        assertThatThrownBy(participant::prepare).isInstanceOf(AccessDeniedException.class);
        ambient(
                Jwt.withTokenValue(wire("{\"sub\":\"legacy\"}"))
                        .header("alg", "HS256")
                        .subject("legacy")
                        .build());
        when(decoder.decode(anyString())).thenThrow(new BadJwtException("invalid"));
        assertThatThrownBy(participant::prepare).isInstanceOf(AccessDeniedException.class);
        verifyNoInteractions(states, fence);
    }

    @Test
    void unavailableStorageDecoderAndCorruptOriginRemainErrors() {
        when(decoder.decode(raw)).thenThrow(new IllegalStateException("secret SQL token"));
        sanitized();
        doReturn(verified).when(decoder).decode(raw);
        when(bindings.findByNativeIssuerAndJti(anyString(), anyString()))
                .thenThrow(new IllegalStateException("secret SQL token"));
        sanitized();
        doReturn(Optional.of(b)).when(bindings).findByNativeIssuerAndJti(anyString(), anyString());
        doThrow(new IllegalStateException("private tag"))
                .when(registry)
                .validatePersistentOrigin(b);
        sanitized();
        doNothing().when(registry).validatePersistentOrigin(b);
        var absent = participant.prepare();
        TransactionSynchronizationManager.setActualTransactionActive(true);
        when(states.current(any(), any())).thenReturn(false);
        assertThatThrownBy(() -> participant.contribute(absent))
                .isInstanceOf(AccessDeniedException.class);
        TransactionSynchronizationManager.clear();
        var unavailable = participant.prepare();
        TransactionSynchronizationManager.setActualTransactionActive(true);
        when(states.current(any(), any())).thenThrow(new IllegalStateException("private SQL"));
        assertThatThrownBy(() -> participant.contribute(unavailable))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Protected authentication infrastructure is unavailable or corrupt.")
                .hasNoCause();
        TransactionSynchronizationManager.clear();
        doReturn(true).when(states).current(any(), any());
        var changedBackend = participant.prepare();
        TransactionSynchronizationManager.setActualTransactionActive(true);
        when(jdbc.queryForObject(anyString(), eq(Boolean.class), anyInt(), anyLong()))
                .thenReturn(false);
        assertThatThrownBy(() -> participant.contribute(changedBackend))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Protected authentication infrastructure is unavailable or corrupt.")
                .hasNoCause();
    }

    @Test
    void handlesAreSingleUseAndBoundToThreadAndAmbientIdentity() throws Exception {
        var p = participant.prepare();
        TransactionSynchronizationManager.setActualTransactionActive(true);
        assertThat(participant.contribute(p).evidence().observedAt())
                .isEqualTo(issued.plusSeconds(1));
        assertThatThrownBy(() -> participant.contribute(p))
                .isInstanceOf(AccessDeniedException.class);
        TransactionSynchronizationManager.clear();
        var swapped = participant.prepare();
        ambient(verified);
        TransactionSynchronizationManager.setActualTransactionActive(true);
        assertThatThrownBy(() -> participant.contribute(swapped))
                .isInstanceOf(AccessDeniedException.class);
        TransactionSynchronizationManager.clear();
        var foreign = participant.prepare();
        var actual = SecurityContextHolder.getContext().getAuthentication();
        try (var pool = Executors.newSingleThreadExecutor()) {
            assertThat(
                            pool.submit(
                                            () -> {
                                                SecurityContextHolder.getContext()
                                                        .setAuthentication(actual);
                                                try {
                                                    return catchThrowable(
                                                            () -> participant.contribute(foreign));
                                                } finally {
                                                    SecurityContextHolder.clearContext();
                                                }
                                            })
                                    .get(5, TimeUnit.SECONDS))
                    .isInstanceOf(AccessDeniedException.class);
        }
    }

    @Test
    void contributionHasOnlyImmutableScalarEvidenceAndNoPermission() {
        var p = participant.prepare();
        TransactionSynchronizationManager.setActualTransactionActive(true);
        var c = participant.contribute(p);
        assertThat(Arrays.stream(c.getClass().getRecordComponents()).map(f -> f.getName()))
                .containsExactly("evidence", "validUntil", "backendPid", "transactionId");
        assertThat(c.toString()).doesNotContain(raw, "PERMIT", "producerAuthenticator");
        assertThatThrownBy(
                        () ->
                                new ProtectedOperationIdentityParticipant.Contribution(
                                        c.evidence(), c.evidence().observedAt(), 13, 42))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                        () ->
                                new ProtectedOperationIdentityParticipant.Contribution(
                                        c.evidence(), c.validUntil(), 0, 42))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                        () ->
                                new ProtectedOperationIdentityParticipant.Contribution(
                                        c.evidence(), c.validUntil().plusSeconds(1), 13, 42))
                .isInstanceOf(IllegalArgumentException.class);
        verify(states).current(b, issued.plusSeconds(1));
    }

    @Test
    void nativeParserReuseAndReceiptProducerAllowlistRemainClosed() throws Exception {
        assertThat(
                        TopologyOperationAuthenticationQueryAdapter.class
                                .getDeclaredMethod("rawClaims", String.class)
                                .getModifiers())
                .satisfies(
                        m -> {
                            assertThat(Modifier.isPublic(m)).isFalse();
                            assertThat(Modifier.isPrivate(m)).isFalse();
                        });
        assertThat(TopologyOperationAuthenticationQueryAdapter.rawClaims(wire("{\"iss\":1}")))
                .isNull();
        Path root = Path.of("src/main/java/dz/sh/hidra/modules/identity/infrastructure/security");
        var users = new ArrayList<String>();
        try (var files = Files.list(root)) {
            for (Path p : files.filter(p -> p.toString().endsWith(".java")).toList()) {
                String s = Files.readString(p);
                if (s.contains(".verifiedDirect(") || s.contains(".verifiedOidc("))
                    users.add(p.getFileName().toString());
            }
        }
        assertThat(users)
                .containsExactlyInAnyOrder(
                        "LocalAuthenticationProvider.java",
                        "LdapAuthenticationProvider.java",
                        "IdentityOidcJwtAuthenticationConverter.java");
        String s = Files.readString(root.resolve("ProtectedOperationIdentityParticipant.java"));
        assertThat(s)
                .contains("TopologyOperationAuthenticationQueryAdapter.rawClaims(raw)")
                .doesNotContain(
                        "REQUIRES_NEW", "Instant.now(", "Clock", "VerifiedAuthorizationAssertion");
    }

    private void sanitized() {
        assertThatThrownBy(participant::prepare)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Protected authentication infrastructure is unavailable or corrupt.")
                .hasNoCause();
    }

    private static void ambient(Jwt jwt) {
        var a = new JwtAuthenticationToken(jwt, List.of());
        a.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(a);
    }

    private static String id() {
        return UUID.randomUUID().toString();
    }

    private static String wire(String payload) {
        var b = Base64.getUrlEncoder().withoutPadding();
        return b.encodeToString("{\"alg\":\"HS256\"}".getBytes(StandardCharsets.UTF_8))
                + "."
                + b.encodeToString(payload.getBytes(StandardCharsets.UTF_8))
                + ".c3ludGhldGlj";
    }

    @Test void integratedNativeAdmissionRejectsNullBeforeAnyFenceOrClock() {
        assertThatThrownBy(()->participant.admit(null)).isInstanceOf(RuntimeException.class);verifyNoInteractions(fence);
    }


    @Test void publicationResourceMappingNeverChangesDraftOrMintsAClaimPair() {
        var action=dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.Action.class;
        for(String verb:List.of("PUBLISH","WITHDRAW","REACTIVATE","READ")) {
            var selected=Enum.valueOf(action,"PUBLICATION_"+verb);
            assertThat(dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.resourceType(selected))
                .isEqualTo("ENGINEERING_NETWORK_PUBLICATION");
            var request=new dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract.Request(selected,"company","network");
            assertThatThrownBy(()->participant.qualifyDraftPair(null,request)).isInstanceOf(RuntimeException.class);
        }
        assertThat(ProtectedOperationIdentityParticipant.DraftClaimPair.class.getConstructors()).isEmpty();
    }

}
