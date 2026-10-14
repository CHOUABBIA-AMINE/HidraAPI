/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationAuthenticationBoundaryGuardrailTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity
 *
 * @Description : Protects strict binding values, opaque JDK-only exports and exclusive real-producer receipt transport boundaries.
 *
 */
package dz.sh.hidra.modules.identity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyOperationAuthenticationContract;
import dz.sh.hidra.modules.identity.application.model.AccessTokenIssuanceContext;
import dz.sh.hidra.modules.identity.application.model.AccessTokenIssuerSettings;
import dz.sh.hidra.modules.identity.application.model.AuthenticatedPrincipalInput;
import dz.sh.hidra.modules.identity.application.model.AuthenticationVerificationSnapshot;
import dz.sh.hidra.modules.identity.application.model.LocalCredentialVerificationState;
import dz.sh.hidra.modules.identity.application.model.VerifiedDirectAuthenticationResult;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.model.OperationAuthenticationBinding;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

class OperationAuthenticationBoundaryGuardrailTest {
    private static final Instant NOW = Instant.parse("2026-10-14T07:00:00Z");
    private static final Path IDENTITY = Path.of("src/main/java/dz/sh/hidra/modules/identity");

    @Test
    void nativeKeysCountUtf8BytesAndOwnerIdsCountPostgresqlCharacters() {
        assertThat(new AccessTokenIssuerSettings("é".repeat(256), "a".repeat(512), 1).ttlSeconds())
                .isEqualTo(1);
        assertThatThrownBy(() -> new AccessTokenIssuerSettings("é".repeat(257), "aud", 1))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new AccessTokenIssuerSettings("iss", "a".repeat(513), 1))
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(local("😀".repeat(80), NOW, NOW.plusSeconds(1))).isNotNull();
        assertThatThrownBy(() -> local("😀".repeat(81), NOW, NOW.plusSeconds(1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void nativeConfigurationAndIdsRejectBoundaryWhitespaceNulAndMalformedUnicode() {
        for (String text :
                new String[] {
                    "", " ", "\tissuer", "issuer\u2003", "issuer\0", "issuer\ud800", "issuer\udc00"
                }) {
            assertThatThrownBy(() -> new AccessTokenIssuerSettings(text, "aud", 1))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> local(text, NOW, NOW.plusSeconds(1)))
                    .isInstanceOf(IllegalArgumentException.class);
        }
        assertThatThrownBy(() -> new AccessTokenIssuerSettings("iss", "aud", 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void exactExternalSubjectAndIssuerKeepBoundaryWhitespaceAndUnicode() {
        var s =
                oidc(
                        "  exact😀subject  ",
                        " https://issuer.invalid/ ",
                        NOW.plusSeconds(10).plusNanos(1000));
        assertThat(s.externalSubject()).isEqualTo("  exact😀subject  ");
        assertThat(s.sourceIssuer()).isEqualTo(" https://issuer.invalid/ ");
        assertThat(s.toString()).doesNotContain("subject", "issuer.invalid");
    }

    @Test
    void verificationSnapshotsRejectMalformedSourceAndUnrepresentableDeadlines() {
        for (String text : new String[] {"", " ", "\ud800", "\udc00", "x\0y"})
            assertThatThrownBy(() -> oidc(text, "issuer", NOW.plusSeconds(1)))
                    .isInstanceOf(IllegalArgumentException.class);
        for (Instant time :
                new Instant[] {
                    Instant.MIN,
                    Instant.MAX,
                    NOW.plusNanos(1),
                    Instant.ofEpochSecond(9224318016000L)
                })
            assertThatThrownBy(() -> oidc("sub", "issuer", time))
                    .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void nativeIntervalsRequireFiniteWholeSecondIncreasingInstantsAndCanonicalUuids() {
        assertThat(
                        new AccessTokenIssuanceContext(
                                UUID.randomUUID().toString(), NOW, NOW.plusSeconds(1)))
                .isNotNull();
        for (Instant time : new Instant[] {Instant.MIN, Instant.MAX, NOW.plusNanos(1)}) {
            assertThatThrownBy(() -> local("user", time, NOW.plusSeconds(1)))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(
                            () ->
                                    new AccessTokenIssuanceContext(
                                            UUID.randomUUID().toString(), time, NOW.plusSeconds(1)))
                    .isInstanceOf(IllegalArgumentException.class);
        }
        assertThatThrownBy(() -> local("user", NOW, NOW))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                        () ->
                                new AccessTokenIssuanceContext(
                                        "AAAAAAAA-AAAA-4AAA-8AAA-AAAAAAAAAAAA",
                                        NOW,
                                        NOW.plusSeconds(1)))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                        () -> new AccessTokenIssuanceContext("1-1-1-1-1", NOW, NOW.plusSeconds(1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void sourceExpiryCapsNativeLifetimeWithoutRoundingUpNanoseconds() {
        var s = oidc("subject", "issuer", NOW.plusSeconds(10).plusNanos(1000));
        assertThat(binding(s, NOW, NOW.plusSeconds(10), new byte[32])).isNotNull();
        assertThatThrownBy(() -> binding(s, NOW, NOW.plusSeconds(11), new byte[32]))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void explicitNullBranchesAndPositiveGenerationsAreMandatory() {
        assertThatThrownBy(
                        () ->
                                new AuthenticationVerificationSnapshot(
                                        "user",
                                        "LOCAL",
                                        "provider",
                                        1,
                                        "link",
                                        "sub",
                                        1L,
                                        null,
                                        null,
                                        "credential",
                                        1L))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                        () ->
                                new AuthenticationVerificationSnapshot(
                                        "user",
                                        "LDAP",
                                        "provider",
                                        1,
                                        "link",
                                        "sub",
                                        1L,
                                        null,
                                        null,
                                        "credential",
                                        1L))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                        () ->
                                new AuthenticationVerificationSnapshot(
                                        "user",
                                        "OIDC",
                                        "provider",
                                        1,
                                        "link",
                                        "sub",
                                        1L,
                                        "issuer",
                                        null,
                                        null,
                                        null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                        () ->
                                new AuthenticationVerificationSnapshot(
                                        "user",
                                        "LOCAL",
                                        "provider",
                                        0,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        "credential",
                                        1L))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                        () ->
                                new AuthenticationVerificationSnapshot(
                                        "user",
                                        "LOCAL",
                                        "provider",
                                        1,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        "credential",
                                        0L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void sourceTupleAndTagShapeCannotBeMadeValidByCallerConstruction() {
        var snapshot = oidc("subject", "issuer", NOW.plusSeconds(20));
        for (byte[] tag : new byte[][] {null, new byte[0], new byte[31], new byte[33]})
            assertThatThrownBy(() -> binding(snapshot, NOW, NOW.plusSeconds(1), tag))
                    .isInstanceOf(IllegalArgumentException.class);
        assertThat(binding(snapshot, NOW, NOW.plusSeconds(1), new byte[32])).isNotNull();
        // Structural acceptance of 32 bytes is deliberately not persistent-origin authentication.
    }

    @Test
    void inputLegacyConstructorsKeepNoReceiptAndPrivateTransportIsRedacted() {
        var old =
                new AuthenticatedPrincipalInput(
                        "user",
                        "operator",
                        "Operator",
                        ProviderType.LOCAL,
                        "provider",
                        Set.of(),
                        Set.of());
        assertThat(old.verificationHandle()).isNull();
        var principal =
                new HidraPrincipal(
                        "user",
                        "operator",
                        "Operator",
                        ProviderType.LOCAL,
                        "provider",
                        Set.of(),
                        Set.of());
        assertThat(new VerifiedDirectAuthenticationResult(principal, "private-handle").toString())
                .doesNotContain("private-handle");
        assertThat(
                        new LocalCredentialVerificationState(
                                        "cred", "user", "private-hash", "ACTIVE", 1)
                                .toString())
                .doesNotContain("private-hash");
    }

    @Test
    void topologyExportHasNoArgumentsAndOpaqueDenialHasNoIdentity() {
        var method = TopologyOperationAuthenticationContract.class.getDeclaredMethods()[0];
        assertThat(method.getName()).isEqualTo("queryCurrentBoundAuthentication");
        assertThat(method.getParameterCount()).isZero();
        var denied = TopologyOperationAuthenticationContract.Result.denied();
        assertThat(denied.status()).isEqualTo("ACCESS_DENIED");
        assertThat(denied.evidence()).isNull();
        assertThatThrownBy(
                        () -> new TopologyOperationAuthenticationContract.Result("ELIGIBLE", null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new TopologyOperationAuthenticationContract.Result("PERMIT", null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(
                        Arrays.stream(
                                        TopologyOperationAuthenticationContract.Evidence.class
                                                .getRecordComponents())
                                .map(c -> c.getName()))
                .doesNotContain(
                        "producerAuthenticator",
                        "passwordHash",
                        "token",
                        "claims",
                        "callback",
                        "permit");
    }

    @Test
    void foundationsAreJdkOnlyAndProductionReceiptProducersAreStillDormant() throws Exception {
        for (Path path :
                new Path[] {
                    IDENTITY.resolve("domain/model/OperationAuthenticationBinding.java"),
                    IDENTITY.resolve(
                            "application/contract/topology/TopologyOperationAuthenticationContract.java")
                }) {
            for (String line : Files.readAllLines(path))
                if (line.startsWith("import ")) assertThat(line).startsWith("import java.");
        }
        Set<String> permitted =
                Set.of(
                        "LocalAuthenticationProvider.java",
                        "LdapAuthenticationProvider.java",
                        "IdentityOidcJwtAuthenticationConverter.java");
        int producers = 0;
        try (var paths = Files.walk(IDENTITY)) {
            for (Path path : paths.filter(p -> p.toString().endsWith(".java")).toList()) {
                if (path.getFileName()
                        .toString()
                        .equals("TrustedAuthenticationVerificationRegistry.java")) continue;
                String source = Files.readString(path);
                if (source.contains(".verifiedDirect(") || source.contains(".verifiedOidc(")) {
                    assertThat(permitted).contains(path.getFileName().toString());
                    producers++;
                }
            }
        }
        assertThat(producers)
                .as("050C permits exactly the three successful producer implementations")
                .isEqualTo(3);
    }

    @Test
    void restDtosAndOriginalAssertionNeverTransportBindingSecretsOrHandles() throws Exception {
        try (var paths = Files.walk(IDENTITY.resolve("api"))) {
            for (Path path : paths.filter(p -> p.toString().endsWith(".java")).toList())
                assertThat(Files.readString(path))
                        .doesNotContain(
                                "verificationHandle",
                                "producerAuthenticator",
                                "LocalCredentialVerificationState");
        }
        assertThat(
                        Files.readString(
                                IDENTITY.resolve(
                                        "application/model/VerifiedAuthorizationAssertion.java")))
                .doesNotContain("verificationHandle", "producerAuthenticator", "passwordHash");
    }

    @Test
    void readerKeepsPrivateNativeVerificationInsideIdentityAndUsesReadOnlyFreshOwnerQuery()
            throws Exception {
        var adapter =
                Files.readString(
                        IDENTITY.resolve(
                                "infrastructure/security/TopologyOperationAuthenticationQueryAdapter.java"));
        assertThat(adapter)
                .contains(
                        "queryCurrentBoundAuthentication()",
                        "hidraJwtDecoder",
                        "validatePersistentOrigin",
                        "STRICT_DUPLICATE_DETECTION");
        var owner =
                Files.readString(
                        IDENTITY.resolve(
                                "application/service/OperationAuthenticationQueryApplicationService.java"));
        var transaction =
                dz.sh.hidra.modules.identity.application.service
                        .OperationAuthenticationQueryApplicationService.class
                        .getMethod("query", OperationAuthenticationBinding.class, Instant.class)
                        .getAnnotation(
                                org.springframework.transaction.annotation.Transactional.class);
        assertThat(transaction.readOnly()).isTrue();
        assertThat(transaction.isolation())
                .isEqualTo(org.springframework.transaction.annotation.Isolation.READ_COMMITTED);
        assertThat(transaction.propagation())
                .isEqualTo(org.springframework.transaction.annotation.Propagation.REQUIRES_NEW);
        assertThat(owner)
                .doesNotContain(
                        "Jwt",
                        "SecurityContextHolder",
                        "producerAuthenticator",
                        "passwordHash",
                        "PERMIT");
        try (var paths = Files.walk(Path.of("src/main/java/dz/sh/hidra/modules/topology"))) {
            for (var path : paths.filter(p -> p.toString().endsWith(".java")).toList())
                assertThat(Files.readString(path))
                        .doesNotContain(
                                "OperationAuthenticationQueryApplicationService",
                                "TopologyOperationAuthenticationQueryAdapter",
                                "TrustedAuthenticationVerificationRegistry");
        }
    }

    private static AuthenticationVerificationSnapshot oidc(
            String subject, String issuer, Instant deadline) {
        return new AuthenticationVerificationSnapshot(
                "user", "OIDC", "provider", 1, "link", subject, 1L, issuer, deadline, null, null);
    }

    private static OperationAuthenticationBinding local(
            String user, Instant issued, Instant expires) {
        var s =
                new AuthenticationVerificationSnapshot(
                        user,
                        "LOCAL",
                        "provider",
                        1,
                        null,
                        null,
                        null,
                        null,
                        null,
                        "credential",
                        1L);
        return binding(s, issued, expires, new byte[32]);
    }

    private static OperationAuthenticationBinding binding(
            AuthenticationVerificationSnapshot s, Instant issued, Instant expires, byte[] tag) {
        return new OperationAuthenticationBinding(
                UUID.randomUUID().toString(),
                (short) 1,
                "issuer",
                "audience",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                s.userId(),
                s.authenticationType(),
                s.authenticationType(),
                s.providerId(),
                s.providerSecurityVersion(),
                s.externalIdentityId(),
                s.externalSubject(),
                s.externalSecurityVersion(),
                s.sourceIssuer(),
                s.sourceExpiresAt(),
                s.credentialId(),
                s.credentialSecurityVersion(),
                issued,
                expires,
                tag);
    }
}
