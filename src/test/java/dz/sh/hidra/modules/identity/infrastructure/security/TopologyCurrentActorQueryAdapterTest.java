/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyCurrentActorQueryAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Verifies trusted actor extraction and fresh human user eligibility for Topology.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyCurrentActorContract;
import dz.sh.hidra.modules.identity.application.model.VerifiedAuthorizationAssertion;
import dz.sh.hidra.modules.identity.application.port.out.UserRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.UserType;
import java.lang.reflect.Modifier;
import java.security.SecureRandom;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

class TopologyCurrentActorQueryAdapterTest {

    private static final Instant NOW = Instant.parse("2026-10-11T12:00:00Z");
    private static final Clock CLOCK = Clock.fixed(NOW, ZoneOffset.UTC);
    private static final String USER_ID = "synthetic-user-id";
    private final Users users = new Users();
    private final List<String> decodedTokens = new ArrayList<>();
    private Jwt decoded = jwt("validated-token", USER_ID);
    private final JwtDecoder decoder = value -> { decodedTokens.add(value); return decoded; };
    private final TopologyCurrentActorQueryAdapter adapter =
            new TopologyCurrentActorQueryAdapter(users, decoder, CLOCK);

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void nativeBearerUsesRevalidatedSubjectInsteadOfCachedClaimsNameOrDetails() {
        users.current = user(USER_ID, UserStatus.ACTIVE, UserType.HUMAN, null);
        Jwt cached = Jwt.withTokenValue("original-bearer").header("alg", "HS256")
                .subject("forged-cached-id").claim("username", "operator-name")
                .claim("roles", List.of("ADMIN")).claim("userId", "details-id").build();
        var token = new JwtAuthenticationToken(cached, List.of(), "untrusted-name");
        token.setDetails(Map.of("actor", "untrusted-detail"));
        authenticate(token);
        var context = SecurityContextHolder.getContext();

        assertEquals(Optional.of(new TopologyCurrentActorContract.Actor(USER_ID)), adapter.currentActor());
        assertEquals(List.of("original-bearer"), decodedTokens);
        assertEquals(List.of(USER_ID), users.lookups);
        assertSame(context, SecurityContextHolder.getContext());
        assertSame(token, context.getAuthentication());
        assertEquals(Map.of("actor", "untrusted-detail"), token.getDetails());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", " synthetic-user-id", "synthetic-user-id "})
    void noncanonicalOrMissingDecodedSubjectDeniesBeforeLookup(String subject) {
        decoded = jwt("validated-token", subject);
        authenticate(bearer(jwt("original-bearer", USER_ID)));
        assertTrue(adapter.currentActor().isEmpty());
        assertTrue(users.lookups.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"expired", "wrong issuer", "invalid signature"})
    void jwtValidationFailureDeniesWithoutUserLookup(String reason) {
        authenticate(bearer(jwt("invalid-token", USER_ID)));
        var rejecting = new TopologyCurrentActorQueryAdapter(users, value -> { throw new JwtException(reason); }, CLOCK);
        assertTrue(rejecting.currentActor().isEmpty());
        assertTrue(users.lookups.isEmpty());
    }

    @ParameterizedTest
    @EnumSource(value = ProviderType.class, names = {"LOCAL", "LDAP", "ACTIVE_DIRECTORY"})
    void normalizedCredentialContextsUseInternalUserId(ProviderType type) {
        users.current = user(USER_ID, UserStatus.ACTIVE, UserType.HUMAN, null);
        authenticate(UsernamePasswordAuthenticationToken.authenticated(principal(type), null, List.of()));
        assertEquals(USER_ID, adapter.currentActor().orElseThrow().userId());
        assertEquals(List.of(USER_ID), users.lookups);
        assertTrue(decodedTokens.isEmpty());
    }

    @Test
    void normalizedOidcUsesLocalIdInsteadOfExternalSubject() {
        users.current = user(USER_ID, UserStatus.ACTIVE, UserType.HUMAN, null);
        var evidence = new VerifiedAuthorizationAssertion(USER_ID, "synthetic-provider", "synthetic-link",
                "external-subject", NOW.plusSeconds(120), Map.of());
        authenticate(new HidraOidcAuthenticationToken(principal(ProviderType.OIDC), evidence));
        assertEquals(USER_ID, adapter.currentActor().orElseThrow().userId());
        assertEquals(List.of(USER_ID), users.lookups);
        assertTrue(decodedTokens.isEmpty());
    }

    @ParameterizedTest
    @EnumSource(value = ProviderType.class, mode = EnumSource.Mode.EXCLUDE,
            names = {"LOCAL", "LDAP", "ACTIVE_DIRECTORY"})
    void unsupportedPasswordProviderTypeDenies(ProviderType type) {
        authenticate(UsernamePasswordAuthenticationToken.authenticated(principal(type), null, List.of()));
        assertDeniedWithoutOwnerAccess();
    }

    @ParameterizedTest
    @EnumSource(value = ProviderType.class, mode = EnumSource.Mode.EXCLUDE, names = {"OIDC"})
    void oidcTokenRequiresOidcPrincipalType(ProviderType type) {
        authenticate(new HidraOidcAuthenticationToken(principal(type)));
        assertDeniedWithoutOwnerAccess();
    }

    @Test
    void absentAnonymousUnauthenticatedStringAndUnsupportedTokensDeny() {
        assertDeniedWithoutOwnerAccess();
        List<Authentication> invalid = List.of(
                new AnonymousAuthenticationToken("synthetic-key", principal(ProviderType.LOCAL),
                        org.springframework.security.core.authority.AuthorityUtils.createAuthorityList("ADMIN")),
                UsernamePasswordAuthenticationToken.unauthenticated(principal(ProviderType.LOCAL), null),
                UsernamePasswordAuthenticationToken.authenticated(USER_ID, null, List.of()),
                new TestingAuthenticationToken(principal(ProviderType.LOCAL), null, "ADMIN"));
        for (Authentication token : invalid) {
            if (token instanceof org.springframework.security.authentication.AbstractAuthenticationToken springToken) {
                springToken.setDetails(Map.of("userId", USER_ID));
            }
            authenticate(token);
            assertDeniedWithoutOwnerAccess();
        }
    }

    @Test
    void unauthenticatedNativeAndOidcContextsDeny() {
        var bearer = bearer(jwt("original-bearer", USER_ID));
        bearer.setAuthenticated(false);
        authenticate(bearer);
        assertDeniedWithoutOwnerAccess();
        var oidc = new HidraOidcAuthenticationToken(principal(ProviderType.OIDC));
        oidc.setAuthenticated(false);
        authenticate(oidc);
        assertDeniedWithoutOwnerAccess();
    }

    @ParameterizedTest
    @EnumSource(value = UserStatus.class, mode = EnumSource.Mode.EXCLUDE, names = {"ACTIVE"})
    void nonActiveUserDenies(UserStatus status) {
        users.current = user(USER_ID, status, UserType.HUMAN, null);
        localContext();
        assertTrue(adapter.currentActor().isEmpty());
    }

    @ParameterizedTest
    @EnumSource(value = UserType.class, mode = EnumSource.Mode.EXCLUDE, names = {"HUMAN"})
    void nonHumanUserDenies(UserType type) {
        users.current = user(USER_ID, UserStatus.ACTIVE, type, null);
        localContext();
        assertTrue(adapter.currentActor().isEmpty());
    }

    @Test
    void missingAndMismatchedUsersDenyWithoutUsernameFallback() {
        localContext();
        assertTrue(adapter.currentActor().isEmpty());
        users.current = user("other-user-id", UserStatus.ACTIVE, UserType.HUMAN, null);
        assertTrue(adapter.currentActor().isEmpty());
        assertEquals(List.of(USER_ID, USER_ID), users.lookups);
    }

    @Test
    void currentStatusAndLockChangesAreObservedWithExactExpiryBoundary() {
        localContext();
        users.current = user(USER_ID, UserStatus.ACTIVE, UserType.HUMAN, NOW.plusNanos(1));
        assertTrue(adapter.currentActor().isEmpty());
        users.current = user(USER_ID, UserStatus.ACTIVE, UserType.HUMAN, NOW);
        assertEquals(USER_ID, adapter.currentActor().orElseThrow().userId());
        users.current = user(USER_ID, UserStatus.SUSPENDED, UserType.HUMAN, null);
        assertTrue(adapter.currentActor().isEmpty());
        users.current = user(USER_ID, UserStatus.LOCKED, UserType.HUMAN, NOW.minusSeconds(1));
        assertTrue(adapter.currentActor().isEmpty());
        users.current = user(USER_ID, UserStatus.ACTIVE, UserType.HUMAN, NOW.minusSeconds(1));
        assertEquals(USER_ID, adapter.currentActor().orElseThrow().userId());
        assertEquals(5, users.lookups.size());
    }

    @Test
    void eachInvocationReadsItsOwnIdentityAndCapturesClockOnce() {
        var instants = new ArrayList<Instant>();
        Clock counting = new Clock() {
            public ZoneOffset getZone() { return ZoneOffset.UTC; }
            public Clock withZone(java.time.ZoneId zone) { return this; }
            public Instant instant() { instants.add(NOW); return NOW; }
        };
        var query = new TopologyCurrentActorQueryAdapter(users, decoder, counting);
        localContext();
        users.current = user(USER_ID, UserStatus.ACTIVE, UserType.HUMAN, null);
        assertEquals(USER_ID, query.currentActor().orElseThrow().userId());
        decoded = jwt("second-token", "second-user-id");
        authenticate(bearer(jwt("second-token", "cached-other-id")));
        users.current = user("second-user-id", UserStatus.ACTIVE, UserType.HUMAN, null);
        assertEquals("second-user-id", query.currentActor().orElseThrow().userId());
        assertEquals(List.of(USER_ID, "second-user-id"), users.lookups);
        assertEquals(2, instants.size());
    }

    @Test
    void infrastructureFailuresPropagateAndContextIsPreserved() {
        localContext();
        var context = SecurityContextHolder.getContext();
        var failure = new IllegalStateException("synthetic repository failure");
        users.failure = failure;
        assertSame(failure, assertThrows(IllegalStateException.class, adapter::currentActor));
        users.failure = null;
        authenticate(bearer(jwt("original-bearer", USER_ID)));
        var broken = new TopologyCurrentActorQueryAdapter(users, value -> { throw failure; }, CLOCK);
        assertSame(failure, assertThrows(IllegalStateException.class, broken::currentActor));
        assertSame(context, SecurityContextHolder.getContext());
    }

    @Test
    void contractAndConstructorsEnforceNarrowReferenceBoundary() {
        assertThrows(NullPointerException.class, () -> new TopologyCurrentActorQueryAdapter(null, decoder, CLOCK));
        assertThrows(NullPointerException.class, () -> new TopologyCurrentActorQueryAdapter(users, null, CLOCK));
        assertThrows(NullPointerException.class, () -> new TopologyCurrentActorQueryAdapter(users, decoder, null));
        for (String value : new String[] {null, "", " ", "\t"}) {
            assertThrows(IllegalArgumentException.class, () -> new TopologyCurrentActorContract.Actor(value));
        }
        assertEquals(USER_ID, new TopologyCurrentActorContract.Actor(" " + USER_ID + " ").userId());
        assertTrue(TopologyCurrentActorContract.Actor.class.isRecord());
        assertTrue(Modifier.isFinal(TopologyCurrentActorContract.Actor.class.getModifiers()));
        assertEquals(1, TopologyCurrentActorContract.Actor.class.getRecordComponents().length);
        assertEquals(String.class, TopologyCurrentActorContract.Actor.class.getRecordComponents()[0].getType());
        assertEquals(Optional.class, TopologyCurrentActorContract.class.getDeclaredMethods()[0].getReturnType());
        assertEquals(0, TopologyCurrentActorContract.class.getDeclaredMethods()[0].getParameterCount());
    }

    @Test
    void springWiresQualifiedNativeDecoderWithoutGlobalClockBean() {
        try (var context = new AnnotationConfigApplicationContext()) {
            context.registerBean("users", UserRepositoryPort.class, () -> users);
            context.registerBean("externalOidcJwtDecoder", JwtDecoder.class,
                    () -> value -> { throw new AssertionError("External decoder selected"); });
            context.registerBean("hidraJwtDecoder", JwtDecoder.class, () -> decoder);
            context.register(TopologyCurrentActorQueryAdapter.class);
            context.refresh();
            authenticate(bearer(jwt("original-bearer", USER_ID)));
            users.current = user(USER_ID, UserStatus.ACTIVE, UserType.HUMAN, null);
            assertEquals(USER_ID, context.getBean(TopologyCurrentActorContract.class)
                    .currentActor().orElseThrow().userId());
            assertTrue(context.getBeansOfType(Clock.class).isEmpty());
        }
    }

    @Test
    void realHs256RejectsWrongIssuerExpiryAndSignatureDespiteForgedCachedSubject() throws Exception {
        byte[] key = new byte[32];
        byte[] otherKey = new byte[32];
        new SecureRandom().nextBytes(key);
        new SecureRandom().nextBytes(otherKey);
        NimbusJwtDecoder nativeDecoder = NimbusJwtDecoder.withSecretKey(new SecretKeySpec(key, "HmacSHA256"))
                .macAlgorithm(MacAlgorithm.HS256).build();
        var timestamp = new JwtTimestampValidator(Duration.ZERO);
        timestamp.setClock(CLOCK);
        nativeDecoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(timestamp,
                new JwtIssuerValidator("hidra-api")));
        var query = new TopologyCurrentActorQueryAdapter(users, nativeDecoder, CLOCK);
        users.current = user(USER_ID, UserStatus.ACTIVE, UserType.HUMAN, null);
        authenticate(bearer(jwt(signed(key, "hidra-api", NOW.plusSeconds(60)), "forged-id")));
        assertEquals(USER_ID, query.currentActor().orElseThrow().userId());
        users.lookups.clear();
        List<String> invalid = List.of(signed(key, "external-issuer", NOW.plusSeconds(60)),
                signed(key, "hidra-api", NOW.minusSeconds(1)),
                signed(otherKey, "hidra-api", NOW.plusSeconds(60)));
        for (String value : invalid) {
            authenticate(bearer(jwt(value, USER_ID)));
            assertTrue(query.currentActor().isEmpty());
            assertTrue(users.lookups.isEmpty());
        }
    }

    private static String signed(byte[] key, String issuer, Instant expiry) throws Exception {
        var claims = new JWTClaimsSet.Builder().subject(USER_ID).issuer(issuer)
                .issueTime(Date.from(NOW.minusSeconds(120))).expirationTime(Date.from(expiry)).build();
        var token = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
        token.sign(new MACSigner(key));
        return token.serialize();
    }

    private void assertDeniedWithoutOwnerAccess() {
        assertTrue(adapter.currentActor().isEmpty());
        assertTrue(users.lookups.isEmpty());
        assertTrue(decodedTokens.isEmpty());
    }

    private void localContext() {
        authenticate(UsernamePasswordAuthenticationToken.authenticated(principal(ProviderType.LOCAL), null, List.of()));
    }

    private static void authenticate(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private static JwtAuthenticationToken bearer(Jwt jwt) {
        return new JwtAuthenticationToken(jwt, List.of());
    }

    private static Jwt jwt(String token, String subject) {
        var builder = Jwt.withTokenValue(token).header("alg", "HS256");
        if (subject != null) { builder.subject(subject); }
        else { builder.claim("iss", "synthetic-issuer"); }
        return builder.build();
    }

    private static HidraPrincipal principal(ProviderType type) {
        return new HidraPrincipal(USER_ID, "distinct-username", "Synthetic Operator", type,
                "synthetic-provider", Set.of("ADMIN"), Set.of("untrusted-publish"), "synthetic-link");
    }

    private static User user(String id, UserStatus status, UserType type, Instant lock) {
        return new User(id, "distinct-username", null, "Synthetic Operator", type, status,
                null, null, 0, lock, NOW, null, null, null, NOW);
    }

    private static final class Users implements UserRepositoryPort {
        private final List<String> lookups = new ArrayList<>();
        private User current;
        private RuntimeException failure;
        public Optional<User> findById(String id) {
            lookups.add(id);
            if (failure != null) { throw failure; }
            return Optional.ofNullable(current);
        }
        public Optional<User> findByUsername(String username) { throw new AssertionError("Username lookup forbidden"); }
        public User save(User user) { throw new AssertionError("Mutation forbidden"); }
    }
}
