/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TrustedAuthenticationVerificationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Verifies private receipt lifetimes, format-one cryptographic vectors and simulated transaction publication without claiming runtime database proof.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dz.sh.hidra.modules.identity.application.model.AccessTokenIssuanceContext;
import dz.sh.hidra.modules.identity.application.model.AccessTokenIssuerSettings;
import dz.sh.hidra.modules.identity.application.model.AuthenticatedPrincipalInput;
import dz.sh.hidra.modules.identity.application.model.AuthenticationVerificationSnapshot;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.model.OperationAuthenticationBinding;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/** Unit evidence only: synthetic producer objects and explicitly simulated Spring callbacks. */
class TrustedAuthenticationVerificationTest {
    private static final String SECRET = "This is a synthetic development signing key.";
    private static final String BINDING = "00000000-0000-4000-8000-000000000001";
    private static final String JTI = "00000000-0000-4000-8000-000000000002";
    private static final String SESSION = "00000000-0000-4000-8000-000000000003";
    private static final Instant NOW = Instant.parse("2026-10-14T07:00:00Z");
    private static final AccessTokenIssuerSettings SETTINGS =
            new AccessTokenIssuerSettings("hidra-api", "hidra-api", 600);
    private static final AccessTokenIssuanceContext CONTEXT =
            new AccessTokenIssuanceContext(JTI, NOW, NOW.plusSeconds(60));
    private final TrustedAuthenticationVerificationRegistry registry = registry(SECRET);

    @AfterEach
    void cleanup() {
        registry.close();
        SecurityContextHolder.clearContext();
        TransactionSynchronizationManager.clear();
    }

    @Test
    void boundIssuerCryptographicReadbackRejectsFakeBearerWithoutSecretDiagnostics() {
        var encoder =
                (org.springframework.security.oauth2.jwt.JwtEncoder)
                        parameters ->
                                org.springframework.security.oauth2.jwt.Jwt.withTokenValue(
                                                "private-synthetic-bearer")
                                        .header("alg", "HS256")
                                        .claims(c -> c.putAll(parameters.getClaims().getClaims()))
                                        .build();
        var decoder =
                (org.springframework.security.oauth2.jwt.JwtDecoder)
                        token -> {
                            throw new org.springframework.security.oauth2.jwt.JwtException(
                                    "private-synthetic-bearer");
                        };
        var provider =
                new org.springframework.beans.factory.support.StaticListableBeanFactory(
                                java.util.Map.of("decoder", decoder))
                        .getBeanProvider(org.springframework.security.oauth2.jwt.JwtDecoder.class);
        var issuer =
                new HidraAccessTokenIssuer(
                        encoder,
                        "hidra-api",
                        "hidra-api",
                        "sub",
                        "roles",
                        "scope",
                        900,
                        provider,
                        65536);
        var p =
                new HidraPrincipal(
                        "user",
                        "operator",
                        null,
                        ProviderType.LOCAL,
                        "provider",
                        Set.of(),
                        Set.of());
        assertThatThrownBy(
                        () ->
                                issuer.issue(
                                        p,
                                        new AccessTokenIssuanceContext(
                                                JTI,
                                                Instant.now()
                                                        .minusSeconds(1)
                                                        .truncatedTo(
                                                                java.time.temporal.ChronoUnit
                                                                        .SECONDS),
                                                Instant.now()
                                                        .plusSeconds(60)
                                                        .truncatedTo(
                                                                java.time.temporal.ChronoUnit
                                                                        .SECONDS))))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Bound native signing or readback failed.")
                .hasNoCause();
    }

    @Test
    void nativeReadbackRequiresExactSingletonRawAudienceAndTypedOwnerClaims() {
        var now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.SECONDS);
        var jwt =
                org.springframework.security.oauth2.jwt.Jwt.withTokenValue("synthetic")
                        .header("alg", "HS256")
                        .issuer("hidra-api")
                        .subject("user")
                        .audience(java.util.List.of("hidra-api", "other"))
                        .issuedAt(now)
                        .expiresAt(now.plusSeconds(60))
                        .claim("jti", JTI)
                        .claim("authentication_type", "LOCAL")
                        .claim("identity_provider_id", "provider")
                        .build();
        assertThat(
                        HidraAccessTokenIssuer.exactClaims(
                                jwt,
                                "hidra-api",
                                "hidra-api",
                                "user",
                                JTI,
                                "LOCAL",
                                "provider",
                                now,
                                now.plusSeconds(60)))
                .isFalse();
    }

    @Test
    void hkdfMatchesRfc5869ShortVector() {
        assertThat(
                        TrustedAuthenticationVerificationRegistry.hkdf(
                                bytes("0b".repeat(22)),
                                bytes("000102030405060708090a0b0c"),
                                bytes("f0f1f2f3f4f5f6f7f8f9"),
                                42))
                .isEqualTo(
                        bytes(
                                "3cb25f25faacd57a90434f64d0362f2a2d2d0a90cf1a5a4c5db02d56ecc4c5bf34007208d5b887185865"));
    }

    @Test
    void hkdfMatchesRfc5869LongVector() {
        assertThat(
                        TrustedAuthenticationVerificationRegistry.hkdf(
                                range(0, 80), range(96, 80), range(176, 80), 82))
                .isEqualTo(
                        bytes(
                                "b11e398dc80327a1c8e7f78c596a49344f012eda2d4efad8a050cc4c19afa97c59045a99cac7827271cb41c65e590e09da3275600c2f09b8367793a9aca3db71cc30c58179ec3e87c14c01d5c1f3434f1d87"));
    }

    @Test
    void hkdfMatchesRfc5869EmptySaltVectorAndRejectsInvalidLength() {
        assertThat(
                        TrustedAuthenticationVerificationRegistry.hkdf(
                                bytes("0b".repeat(22)), new byte[0], new byte[0], 42))
                .isEqualTo(
                        bytes(
                                "8da4e775a563c18f715f802a063c5a31b8a11f5c5ee1879ec3454e5f3c738d2d9d201395faa4b61a96c8"));
        assertThatThrownBy(
                        () ->
                                TrustedAuthenticationVerificationRegistry.hkdf(
                                        new byte[1], new byte[1], new byte[0], 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                        () ->
                                TrustedAuthenticationVerificationRegistry.hkdf(
                                        new byte[1], new byte[1], new byte[0], 8161))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void allFourSyntheticProvenanceBranchesMatchIndependentGoldenTags() throws Exception {
        String[] types = {"LOCAL", "LDAP", "ACTIVE_DIRECTORY", "OIDC"};
        String[] tags = {
            "82f175c34d9008e6cda58909d553cea3782d0726b6b27f687e1c061ca1ab9b7f",
            "900d8a5c80c55994f8e6b60fb2ae839ffe8a9e6b404a92b9bf105083c7c49b77",
            "9f00cb929d3aac502b2f13d72f543ba358b25d42bb844472cb158c3f53b631f9",
            "e6964abb75a221adbbffd0c861b2eb7c7aaf1b637781573c96c596ae2a6917cc"
        };
        for (int i = 0; i < types.length; i++) {
            simulateTransaction();
            AuthenticationVerificationSnapshot snapshot = snapshot(types[i], "  subjecté  ");
            var produced = produce(snapshot);
            var verified = registry.consume(produced.input());
            var binding = verified.attest(BINDING, SESSION, SETTINGS, CONTEXT);
            assertThat(binding.producerAuthenticator())
                    .isEqualTo(bytes(tags[i]))
                    .isEqualTo(referenceTag(binding));
            registry.validatePersistentOrigin(binding);
            assertThat(binding.sessionProtocol())
                    .isEqualTo(types[i].equals("ACTIVE_DIRECTORY") ? "LDAP" : types[i]);
            assertThat(binding.externalSubject()).isEqualTo(snapshot.externalSubject());
            assertThat(binding.sourceIssuer()).isEqualTo(snapshot.sourceIssuer());
            registry.close();
            SecurityContextHolder.clearContext();
            TransactionSynchronizationManager.clear();
        }
    }

    @Test
    void streamingFrameHandlesMultibyteBufferBoundariesExactly() throws Exception {
        simulateTransaction();
        String subject = "a".repeat(4095) + "😀".repeat(4097) + " tail ";
        var produced = produce(snapshot("LDAP", subject));
        var binding =
                registry.consume(produced.input()).attest(BINDING, SESSION, SETTINGS, CONTEXT);
        assertThat(binding.producerAuthenticator()).isEqualTo(referenceTag(binding));
    }

    @Test
    void persistentWitnessSurvivesRegistryRestartButRejectsAnotherKey() {
        var binding = attestLocal();
        registry("  " + SECRET + " \n").validatePersistentOrigin(binding);
        assertThatThrownBy(
                        () ->
                                registry("Another synthetic development signing key!")
                                        .validatePersistentOrigin(binding))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Corrupt persisted authentication provenance.");
    }

    @Test
    void everyScalarOrTagMutationIsRejectedByTupleValidationOrAuthentication() throws Exception {
        var binding = attestLocal();
        var components = OperationAuthenticationBinding.class.getRecordComponents();
        var constructor =
                OperationAuthenticationBinding.class.getDeclaredConstructor(
                        Arrays.stream(components).map(c -> c.getType()).toArray(Class<?>[]::new));
        for (int i = 0; i < components.length; i++) {
            Object[] values = new Object[components.length];
            for (int j = 0; j < components.length; j++)
                values[j] = components[j].getAccessor().invoke(binding);
            Object value = values[i];
            values[i] =
                    value == null
                            ? (components[i].getType() == Instant.class
                                    ? NOW
                                    : components[i].getType() == Long.class ? 1L : "changed")
                            : value instanceof String text
                                    ? (text.length() == 36
                                            ? "00000000-0000-4000-8000-000000000009"
                                            : text + "changed")
                                    : value instanceof Short n
                                            ? (short) (n + 1)
                                            : value instanceof Long n
                                                    ? n + 1
                                                    : value instanceof Instant time
                                                            ? time.plusSeconds(1)
                                                            : new byte[32];
            try {
                var modified = constructor.newInstance(values);
                assertThatThrownBy(() -> registry.validatePersistentOrigin(modified))
                        .isInstanceOf(IllegalStateException.class);
            } catch (InvocationTargetException invalid) {
                assertThat(invalid.getCause()).isInstanceOf(IllegalArgumentException.class);
            }
        }
    }

    @Test
    void arbitraryZeroTagAndCopiedTagCannotAuthenticateAChangedTuple() {
        var binding = attestLocal();
        assertThatThrownBy(
                        () ->
                                registry.validatePersistentOrigin(
                                        copy(binding, "user", new byte[32])))
                .isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(
                        () ->
                                registry.validatePersistentOrigin(
                                        copy(
                                                binding,
                                                "another-user",
                                                binding.producerAuthenticator())))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void constructorAndGetterCopiesProtectWitnessEqualityAndHashing() {
        byte[] tag = bytes("ab".repeat(32));
        var original = copy(attestLocal(), "user", tag);
        var equal = copy(original, "user", tag.clone());
        int hash = original.hashCode();
        tag[0] = 0;
        original.producerAuthenticator()[1] = 0;
        assertThat(original).isEqualTo(equal);
        assertThat(original.hashCode()).isEqualTo(hash).isEqualTo(equal.hashCode());
        assertThat(original.toString())
                .doesNotContain("abab", "credential", "user")
                .contains("redacted");
    }

    @Test
    void dtoAndGenericAuthenticatedTokenCannotSupplyPrivateVerification() {
        var principal = principal(snapshot("LOCAL", null));
        String handle = registry.begin();
        SecurityContextHolder.getContext()
                .setAuthentication(
                        UsernamePasswordAuthenticationToken.authenticated(
                                principal, null, Set.of()));
        assertDenied(input(principal, handle));
        assertDenied(input(principal, null));
    }

    @Test
    void oldPublicOidcConstructorsHaveNoReceiptEvenWithAuthenticatedPrincipal() {
        var principal = principal(snapshot("OIDC", "sub"));
        var token = new HidraOidcAuthenticationToken(principal);
        SecurityContextHolder.getContext().setAuthentication(token);
        assertThat(token.verificationReceipt()).isNull();
        assertThat(registry.completed(token)).isNull();
        assertDenied(input(principal, null));
    }

    @Test
    void directHandleIsOneUseAndNeverStoredInDetailsOrAuthorities() {
        var produced = produce(snapshot("LOCAL", null));
        assertThat(produced.actual().getDetails()).isEqualTo("synthetic-details");
        assertThat(produced.actual().getAuthorities()).isEmpty();
        assertThat(produced.input().toString())
                .doesNotContain(produced.input().verificationHandle());
        registry.consume(produced.input());
        assertDenied(produced.input());
    }

    @Test
    void changedPrincipalPermissionsRejectAndConsumeTheReceipt() {
        var produced = produce(snapshot("LOCAL", null));
        var changed =
                new AuthenticatedPrincipalInput(
                        "user",
                        "operator",
                        "Operator",
                        ProviderType.LOCAL,
                        "provider",
                        Set.of(),
                        Set.of("*"),
                        null,
                        produced.input().verificationHandle());
        assertDenied(changed);
        assertDenied(produced.input());
    }

    @Test
    void previousInvocationHandleCannotCrossIntoNewInvocation() {
        var produced = produce(snapshot("LOCAL", null));
        registry.close();
        registry.begin();
        assertDenied(produced.input());
    }

    @Test
    void directReceiptCannotBeConsumedByAnotherThread() throws Exception {
        var produced = produce(snapshot("LOCAL", null));
        try (var executor = Executors.newSingleThreadExecutor()) {
            assertThat(
                            executor.submit(
                                            () -> {
                                                try {
                                                    registry.consume(produced.input());
                                                    return false;
                                                } catch (BadCredentialsException expected) {
                                                    return true;
                                                }
                                            })
                                    .get(3, TimeUnit.SECONDS))
                    .isTrue();
        }
        registry.consume(produced.input());
    }

    @Test
    void oidcReceiptCannotBeConsumedByAnotherThreadOrForgedContext() throws Exception {
        var produced = produce(snapshot("OIDC", "sub"));
        try (var executor = Executors.newSingleThreadExecutor()) {
            assertThat(
                            executor.submit(
                                            () -> {
                                                SecurityContextHolder.getContext()
                                                        .setAuthentication(produced.actual());
                                                try {
                                                    registry.consume(produced.input());
                                                    return false;
                                                } catch (BadCredentialsException expected) {
                                                    return true;
                                                } finally {
                                                    SecurityContextHolder.clearContext();
                                                }
                                            })
                                    .get(3, TimeUnit.SECONDS))
                    .isTrue();
        }
        SecurityContextHolder.getContext()
                .setAuthentication(
                        new HidraOidcAuthenticationToken(
                                (HidraPrincipal) produced.actual().getPrincipal()));
        assertDenied(produced.input());
        SecurityContextHolder.getContext().setAuthentication(produced.actual());
        registry.consume(produced.input());
    }

    @Test
    void consumedReceiptCannotOutliveItsDirectInvocation() {
        var produced = produce(snapshot("LOCAL", null));
        var verified = registry.consume(produced.input());
        registry.close();
        simulateTransaction();
        assertThatThrownBy(() -> verified.attest(BINDING, SESSION, SETTINGS, CONTEXT))
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    void consumedOidcReceiptRequiresItsSameActualAmbientObject() {
        var produced = produce(snapshot("OIDC", "sub"));
        var verified = registry.consume(produced.input());
        SecurityContextHolder.getContext()
                .setAuthentication(
                        new HidraOidcAuthenticationToken(
                                (HidraPrincipal) produced.actual().getPrincipal()));
        simulateTransaction();
        assertThatThrownBy(() -> verified.attest(BINDING, SESSION, SETTINGS, CONTEXT))
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    void attestationRequiresActiveWritableSynchronizedTransactionAndCannotRetry() {
        var produced = produce(snapshot("LOCAL", null));
        var verified = registry.consume(produced.input());
        assertThatThrownBy(() -> verified.attest(BINDING, SESSION, SETTINGS, CONTEXT))
                .isInstanceOf(BadCredentialsException.class);
        simulateTransaction();
        assertThatThrownBy(() -> verified.attest(BINDING, SESSION, SETTINGS, CONTEXT))
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    void readOnlyTransactionCannotAttest() {
        simulateTransaction();
        TransactionSynchronizationManager.setCurrentTransactionReadOnly(true);
        var produced = produce(snapshot("LOCAL", null));
        var verified = registry.consume(produced.input());
        assertThatThrownBy(() -> verified.attest(BINDING, SESSION, SETTINGS, CONTEXT))
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    void attestationMustAgreeWithExactConfiguredIssuerAudienceAndTtl() {
        for (var settings :
                new AccessTokenIssuerSettings[] {
                    new AccessTokenIssuerSettings("other", "hidra-api", 600),
                    new AccessTokenIssuerSettings("hidra-api", "other", 600),
                    new AccessTokenIssuerSettings("hidra-api", "hidra-api", 1)
                }) {
            registry.close();
            simulateTransaction();
            var verified = registry.consume(produce(snapshot("LOCAL", null)).input());
            assertThatThrownBy(() -> verified.attest(BINDING, SESSION, settings, CONTEXT))
                    .isInstanceOf(BadCredentialsException.class);
            TransactionSynchronizationManager.clear();
        }
    }

    @Test
    void malformedAttestationAttemptCannotBeRetriedWithValidIds() {
        simulateTransaction();
        var verified = registry.consume(produce(snapshot("LOCAL", null)).input());
        assertThatThrownBy(() -> verified.attest("malformed", SESSION, SETTINGS, CONTEXT))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> verified.attest(BINDING, SESSION, SETTINGS, CONTEXT))
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    void simulatedCommitPublishesOnlyAfterCommitAndOnlyOnce() {
        simulateTransaction();
        var produced = produce(snapshot("LOCAL", null));
        var verified = registry.consume(produced.input());
        var binding = verified.attest(BINDING, SESSION, SETTINGS, CONTEXT);
        assertThat(registry.completed(produced.actual())).isNull();
        verified.publishAfterCommit(binding);
        assertThat(registry.completed(produced.actual())).isNull();
        assertThatThrownBy(() -> verified.publishAfterCommit(binding))
                .isInstanceOf(BadCredentialsException.class);
        TransactionSynchronizationManager.getSynchronizations()
                .forEach(TransactionSynchronization::afterCommit);
        assertThat(registry.completed(produced.actual())).isEqualTo(binding);
        produced.actual().setAuthenticated(false);
        assertThat(registry.completed(produced.actual())).isNull();
    }

    @Test
    void simulatedRollbackNeverPublishesCompletion() {
        simulateTransaction();
        var produced = produce(snapshot("LOCAL", null));
        var verified = registry.consume(produced.input());
        var binding = verified.attest(BINDING, SESSION, SETTINGS, CONTEXT);
        verified.publishAfterCommit(binding);
        TransactionSynchronizationManager.getSynchronizations()
                .forEach(s -> s.afterCompletion(TransactionSynchronization.STATUS_ROLLED_BACK));
        assertThat(registry.completed(produced.actual())).isNull();
    }

    @Test
    void afterCommitRegistrationCannotMoveToAnotherTransaction() {
        simulateTransaction();
        var produced = produce(snapshot("LOCAL", null));
        var verified = registry.consume(produced.input());
        var binding = verified.attest(BINDING, SESSION, SETTINGS, CONTEXT);
        TransactionSynchronizationManager.clearSynchronization();
        TransactionSynchronizationManager.initSynchronization();
        assertThatThrownBy(() -> verified.publishAfterCommit(binding))
                .isInstanceOf(BadCredentialsException.class);
        assertThat(registry.completed(produced.actual())).isNull();
    }

    @Test
    void afterCommitRegistrationRejectsChangedBindingAndOriginCleanup() {
        simulateTransaction();
        var produced = produce(snapshot("LOCAL", null));
        var verified = registry.consume(produced.input());
        var binding = verified.attest(BINDING, SESSION, SETTINGS, CONTEXT);
        assertThatThrownBy(
                        () ->
                                verified.publishAfterCommit(
                                        copy(binding, "other", binding.producerAuthenticator())))
                .isInstanceOf(BadCredentialsException.class);
        assertThat(registry.completed(produced.actual())).isNull();
        registry.close();
        assertThatThrownBy(() -> registry.verifiedHandle(produced.actual()))
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    void blankNativeKeyDoesNotBreakDormantBeanButCannotAttest() {
        var empty = registry("");
        empty.begin();
        var s = snapshot("LOCAL", null);
        var p = principal(s);
        var actual =
                empty.verifiedDirect(
                        UsernamePasswordAuthenticationToken.authenticated(p, null, Set.of()), s);
        var verified = empty.consume(input(p, empty.verifiedHandle(actual)));
        simulateTransaction();
        try {
            assertThatThrownBy(() -> verified.attest(BINDING, SESSION, SETTINGS, CONTEXT))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("Native authentication key is unavailable.");
        } finally {
            empty.close();
        }
    }

    @Test
    void actualNativeIssuerWireIsAcceptedWithoutChangingExistingClaimSchema() {
        var b = attestLocal();
        var key = new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        var encoder =
                org.springframework.security.oauth2.jwt.NimbusJwtEncoder.withSecretKey(key)
                        .algorithm(org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256)
                        .build();
        var decoder = fixedDecoder();
        var provider =
                new org.springframework.beans.factory.support.StaticListableBeanFactory(
                                java.util.Map.of("decoder", decoder))
                        .getBeanProvider(org.springframework.security.oauth2.jwt.JwtDecoder.class);
        var issuer =
                new HidraAccessTokenIssuer(
                        encoder,
                        "hidra-api",
                        "hidra-api",
                        "sub",
                        "roles",
                        "scope",
                        600,
                        provider,
                        65536);
        var output = issuer.issue(principal(snapshot("LOCAL", null)), CONTEXT);
        ambient(output.tokenValue());
        var h = readerHarness(b, decoder, java.time.Clock.fixed(NOW, java.time.ZoneOffset.UTC));
        assertThat(h.reader().queryCurrentBoundAuthentication().status()).isEqualTo("ELIGIBLE");
    }

    @Test
    void signedReaderUsesActualWireInsteadOfCallerConstructedAmbientClaims() {
        var b = attestLocal();
        var h =
                readerHarness(
                        b, fixedDecoder(), java.time.Clock.fixed(NOW, java.time.ZoneOffset.UTC));
        ambient(wire(b, null));
        assertThat(h.reader().queryCurrentBoundAuthentication().evidence().userId())
                .isEqualTo(b.userId());
        org.mockito.Mockito.verify(h.states()).current(b, NOW);
    }

    @Test
    void signedRawTypeCoercionAndDuplicateJsonCannotPassNativeReader() {
        var b = attestLocal();
        var h =
                readerHarness(
                        b, fixedDecoder(), java.time.Clock.fixed(NOW, java.time.ZoneOffset.UTC));
        for (String change :
                new String[] {
                    "numeric-audience",
                    "fractional-iat",
                    "string-exp",
                    "duplicate-sub",
                    "uppercase-jti"
                }) {
            ambient(wire(b, change));
            assertThat(h.reader().queryCurrentBoundAuthentication().status())
                    .isEqualTo("ACCESS_DENIED");
        }
        org.mockito.Mockito.verifyNoInteractions(h.states());
    }

    @Test
    void invalidNativeSignatureDeniesButDecoderInfrastructureFailureIsOperational() {
        var b = attestLocal();
        var h =
                readerHarness(
                        b,
                        raw -> {
                            throw new org.springframework.security.oauth2.jwt.BadJwtException(
                                    "private-synthetic-bearer");
                        },
                        java.time.Clock.fixed(NOW, java.time.ZoneOffset.UTC));
        ambient(wire(b, null));
        assertThat(h.reader().queryCurrentBoundAuthentication().evidence()).isNull();
        h =
                readerHarness(
                        b,
                        raw -> {
                            throw new org.springframework.security.oauth2.jwt.JwtException(
                                    "private-synthetic-bearer");
                        },
                        java.time.Clock.fixed(NOW, java.time.ZoneOffset.UTC));
        final var unavailable = h;
        assertThatThrownBy(() -> unavailable.reader().queryCurrentBoundAuthentication())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Native authentication verification is unavailable.")
                .hasNoCause();
    }

    @Test
    void elapsedNativeLookupDeniesWithoutAnotherOwnerQueryOrExpiryGrace() {
        var b = attestLocal();
        var clock = org.mockito.Mockito.mock(java.time.Clock.class);
        org.mockito.Mockito.when(clock.instant()).thenReturn(NOW, b.expiresAt());
        var h = readerHarness(b, fixedDecoder(), clock);
        ambient(wire(b, null));
        assertThat(h.reader().queryCurrentBoundAuthentication().status())
                .isEqualTo("ACCESS_DENIED");
        org.mockito.Mockito.verify(h.states(), org.mockito.Mockito.times(1)).current(b, NOW);
    }

    @Test
    void readerDoesNotTreatStructurallyValidCopiedProducerTagAsAuthority() {
        var b = attestLocal();
        var forged =
                new OperationAuthenticationBinding(
                        b.bindingId(),
                        b.formatVersion(),
                        b.nativeIssuer(),
                        b.nativeAudience(),
                        b.jti(),
                        b.sessionId(),
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
                        new byte[32]);
        var h =
                readerHarness(
                        forged,
                        fixedDecoder(),
                        java.time.Clock.fixed(NOW, java.time.ZoneOffset.UTC));
        ambient(wire(b, null));
        assertThatThrownBy(() -> h.reader().queryCurrentBoundAuthentication())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Corrupt persisted authentication provenance.");
        org.mockito.Mockito.verifyNoInteractions(h.states());
    }

    @Test
    void oversizeNativeBearerDeniesBeforeDecoderOrStorageLookup() {
        var b = attestLocal();
        var decoder =
                org.mockito.Mockito.mock(org.springframework.security.oauth2.jwt.JwtDecoder.class);
        var h = readerHarness(b, decoder, java.time.Clock.fixed(NOW, java.time.ZoneOffset.UTC));
        ambient("x".repeat(65537));
        assertThat(h.reader().queryCurrentBoundAuthentication().status())
                .isEqualTo("ACCESS_DENIED");
        org.mockito.Mockito.verifyNoInteractions(decoder, h.repo(), h.states());
    }

    @Test
    void copiedPublicOidcTokenCannotPromoteCompletedPrivateReceipt() {
        simulateTransaction();
        var produced = produce(snapshot("OIDC", " exact source "));
        var consumed = registry.consume(produced.input());
        var b = consumed.attest(BINDING, SESSION, SETTINGS, CONTEXT);
        consumed.publishAfterCommit(b);
        var h =
                readerHarness(
                        b, fixedDecoder(), java.time.Clock.fixed(NOW, java.time.ZoneOffset.UTC));
        assertThat(h.reader().queryCurrentBoundAuthentication().status())
                .isEqualTo("ACCESS_DENIED");
        TransactionSynchronizationManager.getSynchronizations()
                .forEach(TransactionSynchronization::afterCommit);
        assertThat(h.reader().queryCurrentBoundAuthentication().status()).isEqualTo("ELIGIBLE");
        SecurityContextHolder.getContext()
                .setAuthentication(
                        new HidraOidcAuthenticationToken(
                                (HidraPrincipal) produced.actual().getPrincipal()));
        assertThat(h.reader().queryCurrentBoundAuthentication().evidence()).isNull();
    }

    private record ReaderHarness(
            TopologyOperationAuthenticationQueryAdapter reader,
            dz.sh.hidra.modules.identity.application.port.out
                            .OperationAuthenticationBindingRepositoryPort
                    repo,
            dz.sh.hidra.modules.identity.application.port.out.AuthenticationSecurityStateQueryPort
                    states) {}

    private ReaderHarness readerHarness(
            OperationAuthenticationBinding b,
            org.springframework.security.oauth2.jwt.JwtDecoder decoder,
            java.time.Clock clock) {
        var repo =
                org.mockito.Mockito.mock(
                        dz.sh.hidra.modules.identity.application.port.out
                                .OperationAuthenticationBindingRepositoryPort.class);
        org.mockito.Mockito.when(repo.findByNativeIssuerAndJti(b.nativeIssuer(), b.jti()))
                .thenReturn(java.util.Optional.of(b));
        var states =
                org.mockito.Mockito.mock(
                        dz.sh.hidra.modules.identity.application.port.out
                                .AuthenticationSecurityStateQueryPort.class);
        org.mockito.Mockito.when(
                        states.current(
                                org.mockito.ArgumentMatchers.any(),
                                org.mockito.ArgumentMatchers.any()))
                .thenReturn(true);
        var issuer =
                org.mockito.Mockito.mock(
                        dz.sh.hidra.modules.identity.application.port.out.AccessTokenIssuerPort
                                .class);
        org.mockito.Mockito.when(issuer.settings()).thenReturn(SETTINGS);
        var provider =
                new org.springframework.beans.factory.support.StaticListableBeanFactory(
                                java.util.Map.of("decoder", decoder))
                        .getBeanProvider(org.springframework.security.oauth2.jwt.JwtDecoder.class);
        return new ReaderHarness(
                new TopologyOperationAuthenticationQueryAdapter(
                        repo,
                        new dz.sh.hidra.modules.identity.application.service
                                .OperationAuthenticationQueryApplicationService(states),
                        provider,
                        issuer,
                        registry,
                        clock,
                        65536),
                repo,
                states);
    }

    private static org.springframework.security.oauth2.jwt.JwtDecoder fixedDecoder() {
        var decoder =
                org.springframework.security.oauth2.jwt.NimbusJwtDecoder.withSecretKey(
                                new SecretKeySpec(
                                        SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"))
                        .macAlgorithm(
                                org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256)
                        .build();
        // Unit signature proof uses the fixed fixture clock; no hosted timing/DB acceptance claim.
        decoder.setJwtValidator(
                jwt ->
                        org.springframework.security.oauth2.core.OAuth2TokenValidatorResult
                                .success());
        return decoder;
    }

    private static void ambient(String raw) {
        var jwt =
                org.springframework.security.oauth2.jwt.Jwt.withTokenValue(raw)
                        .header("alg", "HS256")
                        .issuer("forged")
                        .subject("forged")
                        .issuedAt(NOW)
                        .expiresAt(NOW.plusSeconds(1))
                        .build();
        var token =
                new org.springframework.security.oauth2.server.resource.authentication
                        .JwtAuthenticationToken(jwt, java.util.List.of());
        token.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    private static String wire(OperationAuthenticationBinding b, String change) {
        var json = tools.jackson.databind.json.JsonMapper.builder().build();
        var fields = new java.util.LinkedHashMap<String, Object>();
        fields.put("iss", b.nativeIssuer());
        fields.put("aud", java.util.List.of(b.nativeAudience()));
        fields.put("sub", b.userId());
        fields.put("jti", b.jti());
        fields.put("authentication_type", b.authenticationType());
        fields.put("identity_provider_id", b.providerId());
        fields.put("iat", b.issuedAt().getEpochSecond());
        fields.put("exp", b.expiresAt().getEpochSecond());
        if ("numeric-audience".equals(change)) fields.put("aud", 123);
        if ("fractional-iat".equals(change)) fields.put("iat", b.issuedAt().getEpochSecond() + 0.5);
        if ("string-exp".equals(change))
            fields.put("exp", String.valueOf(b.expiresAt().getEpochSecond()));
        if ("uppercase-jti".equals(change))
            fields.put("jti", "AAAAAAAA-AAAA-4AAA-8AAA-AAAAAAAAAAAA");
        String payload = json.writeValueAsString(fields);
        if ("duplicate-sub".equals(change))
            payload =
                    payload.substring(0, payload.length() - 1) + ",\"sub\":\"" + b.userId() + "\"}";
        var b64 = java.util.Base64.getUrlEncoder().withoutPadding();
        String unsigned =
                b64.encodeToString("{\"alg\":\"HS256\"}".getBytes(StandardCharsets.UTF_8))
                        + "."
                        + b64.encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        try {
            Mac m = Mac.getInstance("HmacSHA256");
            m.init(new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return unsigned
                    + "."
                    + b64.encodeToString(m.doFinal(unsigned.getBytes(StandardCharsets.US_ASCII)));
        } catch (java.security.GeneralSecurityException failure) {
            throw new IllegalStateException("Synthetic signer unavailable.");
        }
    }

    private static TrustedAuthenticationVerificationRegistry registry(String secret) {
        return new TrustedAuthenticationVerificationRegistry(secret, "hidra-api", "hidra-api");
    }

    private record Produced(Authentication actual, AuthenticatedPrincipalInput input) {}

    private Produced produce(AuthenticationVerificationSnapshot s) {
        var p = principal(s);
        if (s.authenticationType().equals("OIDC")) {
            var actual = new HidraOidcAuthenticationToken(p, null, registry.verifiedOidc(s, p));
            SecurityContextHolder.getContext().setAuthentication(actual);
            return new Produced(actual, input(p, null));
        }
        registry.begin();
        var ordinary = UsernamePasswordAuthenticationToken.authenticated(p, null, Set.of());
        ordinary.setDetails("synthetic-details");
        var actual = registry.verifiedDirect(ordinary, s);
        return new Produced(actual, input(p, registry.verifiedHandle(actual)));
    }

    private OperationAuthenticationBinding attestLocal() {
        simulateTransaction();
        var produced = produce(snapshot("LOCAL", null));
        return registry.consume(produced.input()).attest(BINDING, SESSION, SETTINGS, CONTEXT);
    }

    private static AuthenticationVerificationSnapshot snapshot(String type, String subject) {
        boolean local = type.equals("LOCAL"), oidc = type.equals("OIDC");
        return new AuthenticationVerificationSnapshot(
                "user",
                type,
                "provider",
                3,
                local ? null : "link",
                local ? null : subject,
                local ? null : 5L,
                oidc ? " https://issuer.invalid/ " : null,
                oidc ? NOW.plusSeconds(120).plusNanos(123456000) : null,
                local ? "credential" : null,
                local ? 7L : null);
    }

    private static HidraPrincipal principal(AuthenticationVerificationSnapshot s) {
        return new HidraPrincipal(
                s.userId(),
                "operator",
                "Operator",
                ProviderType.valueOf(s.authenticationType()),
                s.providerId(),
                Set.of(),
                Set.of("read"),
                s.externalIdentityId());
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

    private void assertDenied(AuthenticatedPrincipalInput input) {
        assertThatThrownBy(() -> registry.consume(input))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Trusted authentication provenance is unavailable.");
    }

    private static void simulateTransaction() {
        if (TransactionSynchronizationManager.isSynchronizationActive())
            TransactionSynchronizationManager.clear();
        TransactionSynchronizationManager.setActualTransactionActive(true);
        TransactionSynchronizationManager.setCurrentTransactionReadOnly(false);
        TransactionSynchronizationManager.initSynchronization();
    }

    private static byte[] bytes(String hex) {
        return HexFormat.of().parseHex(hex);
    }

    private static byte[] range(int start, int size) {
        byte[] bytes = new byte[size];
        for (int i = 0; i < size; i++) bytes[i] = (byte) (start + i);
        return bytes;
    }

    private static OperationAuthenticationBinding copy(
            OperationAuthenticationBinding b, String user, byte[] tag) {
        return new OperationAuthenticationBinding(
                b.bindingId(),
                b.formatVersion(),
                b.nativeIssuer(),
                b.nativeAudience(),
                b.jti(),
                b.sessionId(),
                user,
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

    /** Independent bounded test-fixture encoder; production uses its streaming implementation. */
    private static byte[] referenceTag(OperationAuthenticationBinding b) throws Exception {
        var output = new ByteArrayOutputStream();
        var frame = new DataOutputStream(output);
        frame.write(
                "Hidra/identity/operation-binding/format-1/tuple".getBytes(StandardCharsets.UTF_8));
        frame.writeByte(0);
        for (var component : OperationAuthenticationBinding.class.getRecordComponents()) {
            if (component.getName().equals("producerAuthenticator")) continue;
            Object value = component.getAccessor().invoke(b);
            frame.writeByte(value == null ? 0 : 1);
            if (value instanceof String text) {
                byte[] utf8 = text.getBytes(StandardCharsets.UTF_8);
                frame.writeInt(utf8.length);
                frame.write(utf8);
            } else if (value instanceof Short n) frame.writeShort(n);
            else if (value instanceof Long n) frame.writeLong(n);
            else if (value instanceof Instant time) {
                frame.writeLong(time.getEpochSecond());
                frame.writeInt(time.getNano());
            }
        }
        var hmac = Mac.getInstance("HmacSHA256");
        hmac.init(
                new SecretKeySpec(
                        "Hidra/identity/operation-binding/format-1/key"
                                .getBytes(StandardCharsets.UTF_8),
                        "HmacSHA256"));
        byte[] prk = hmac.doFinal(SECRET.getBytes(StandardCharsets.UTF_8));
        hmac.init(new SecretKeySpec(prk, "HmacSHA256"));
        hmac.update(
                "Hidra/identity/operation-binding/format-1/authenticator"
                        .getBytes(StandardCharsets.UTF_8));
        byte[] key = hmac.doFinal(new byte[] {1});
        hmac.init(new SecretKeySpec(key, "HmacSHA256"));
        return hmac.doFinal(output.toByteArray());
    }
}
