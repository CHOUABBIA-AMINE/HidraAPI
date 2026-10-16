/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OidcVerifiedClaimReceiptTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-16
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Verifies opt-in original-source OIDC evidence, failed provenance and private receipt use.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.identity.application.model.AuthenticatedPrincipalInput;
import dz.sh.hidra.modules.identity.application.model.AuthenticationVerificationSnapshot;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
import dz.sh.hidra.modules.identity.application.port.out.AuthenticationSecurityStateQueryPort;
import dz.sh.hidra.modules.identity.application.port.out.ClaimNameCatalogPort;
import dz.sh.hidra.modules.identity.domain.value.ClaimCoverage;
import dz.sh.hidra.modules.identity.domain.value.ExternalIdentityStatus;
import dz.sh.hidra.modules.identity.domain.value.IdentityProviderStatus;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.ExternalIdentityJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.IdentityProviderJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.ExternalIdentityJpaRepository;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.IdentityProviderJpaRepository;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserJpaRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

/** Pure synthetic JVM fixtures; no IdP, privileged PostgreSQL runtime or grant authority. */
class OidcVerifiedClaimReceiptTest {
    private static final String ISSUER = "https://oidc.example.invalid";
    private static final String PROVIDER = "provider-oidc";
    private static final String SUBJECT = "external-subject";
    private static final String USER = "user-oidc";
    private static final Instant ISSUED = Instant.now().truncatedTo(
            java.time.temporal.ChronoUnit.SECONDS);
    private static final Instant EXPIRY = ISSUED.plusSeconds(3600);

    private record Fixture(IdentityOidcJwtAuthenticationConverter converter,
            ClaimNameCatalogPort catalog, TrustedAuthenticationVerificationRegistry registry,
            SpringAuthorizationContextAdapter assertions) {}

    private static ClaimNameCatalogPort.VersionedSelection selected(long generation) {
        return new ClaimNameCatalogPort.VersionedSelection(
                new ClaimNameCatalogPort.Selection(List.of("groups", "missing")), generation);
    }

    private static Jwt source(String group) {
        return Jwt.withTokenValue("original-external-token").header("alg", "RS256")
                .issuer(ISSUER).subject(SUBJECT).issuedAt(ISSUED).expiresAt(EXPIRY)
                .claim("groups", List.of(group)).build();
    }

    private static Fixture fixture(boolean enabled, Jwt verified,
            ClaimNameCatalogPort.VersionedSelection first,
            ClaimNameCatalogPort.VersionedSelection second, boolean decoderFailure) {
        var providers = mock(IdentityProviderJpaRepository.class);
        var links = mock(ExternalIdentityJpaRepository.class);
        var users = mock(UserJpaRepository.class);
        var query = mock(IdentityAdministrationQueryUseCase.class);
        var states = mock(AuthenticationSecurityStateQueryPort.class);
        var assertions = mock(SpringAuthorizationContextAdapter.class);
        var catalog = mock(ClaimNameCatalogPort.class);
        var registry = new TrustedAuthenticationVerificationRegistry(
                "synthetic-unit-key-material-32-bytes-only", "hidra-api", "hidra-api");
        var provider = mock(IdentityProviderJpaEntity.class);
        when(provider.id()).thenReturn(PROVIDER);
        when(provider.status()).thenReturn(IdentityProviderStatus.ACTIVE);
        when(providers.findByProviderTypeAndIssuerUri(ProviderType.OIDC, ISSUER))
                .thenReturn(Optional.of(provider));
        var link = mock(ExternalIdentityJpaEntity.class);
        when(link.id()).thenReturn("linked-external");
        when(link.userId()).thenReturn(USER);
        when(link.status()).thenReturn(ExternalIdentityStatus.LINKED);
        when(links.findByIdentityProviderIdAndExternalSubject(PROVIDER, SUBJECT))
                .thenReturn(Optional.of(link));
        var user = mock(UserJpaEntity.class);
        when(user.id()).thenReturn(USER);
        when(user.username()).thenReturn("operator");
        when(user.displayName()).thenReturn("Operator");
        when(user.status()).thenReturn(UserStatus.ACTIVE);
        when(users.findById(USER)).thenReturn(Optional.of(user));
        var principal = mock(IdentityAdministrationQueryUseCase.PrincipalView.class);
        when(principal.effectivePermissions()).thenReturn(List.of());
        when(query.principal(USER, List.of())).thenReturn(principal);
        var providerState = new AuthenticationSecurityStateQueryPort.ProviderState(
                PROVIDER, "OIDC", 1, ISSUER, null, null);
        when(states.provider(PROVIDER, "OIDC")).thenReturn(providerState);
        when(states.external(any(), anyString(), anyString(), any()))
                .thenReturn(new AuthenticationVerificationSnapshot(
                        USER, "OIDC", PROVIDER, 1, "linked-external", SUBJECT, 1L,
                        ISSUER, EXPIRY, null, null));
        when(catalog.selectVersioned(PROVIDER)).thenReturn(first, second);
        @SuppressWarnings("unchecked")
        ObjectProvider<JwtDecoder> decoder = mock(ObjectProvider.class);
        when(decoder.getObject()).thenReturn(raw -> {
            if (decoderFailure) throw new BadCredentialsException("sensitive-decoder-detail");
            if (!"original-external-token".equals(raw))
                throw new BadCredentialsException("wrong source token");
            return verified;
        });
        var converter = new IdentityOidcJwtAuthenticationConverter(
                providers, links, users, query, assertions, states, registry, decoder,
                65536, catalog, enabled);
        return new Fixture(converter, catalog, registry, assertions);
    }

    @AfterEach
    void cleanup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void optInUsesOnlyTheOriginalDecodedSourceAndRetainsBoundedPrivateEvidence() {
        var f = fixture(true, source("verified-operator"), selected(7), selected(7), false);
        var token = (HidraOidcAuthenticationToken) f.converter().convert(source("caller-claims"));
        var evidence = token.verificationReceipt().oidcEvidence();
        assertThat(evidence).isNotNull();
        assertThat(evidence.projection().coverage().values("groups"))
                .containsExactly("verified-operator");
        assertThat(evidence.projection().coverage().state("missing"))
                .isEqualTo(ClaimCoverage.State.ABSENT);
        assertThat(evidence.projection().mappingGeneration()).isEqualTo(7);
        assertThat(evidence.providerId()).isEqualTo(PROVIDER);
        assertThat(evidence.issuer()).isEqualTo(ISSUER);
        assertThat(evidence.subject()).isEqualTo(SUBJECT);
        assertThat(evidence.toString()).contains("redacted")
                .doesNotContain("verified-operator", "external-subject", "7");
        assertThat(token.authorizationAssertion()).isNull();
        verify(f.assertions(), never()).capture(any(), any());
    }

    @Test
    void defaultDisabledNeverSelectsNamesOrRetainsClaimEvidence() {
        var f = fixture(false, source("verified"), selected(7), selected(7), false);
        var token = (HidraOidcAuthenticationToken) f.converter().convert(source("caller"));
        assertThat(token.verificationReceipt().oidcEvidence()).isNull();
        verify(f.catalog(), never()).selectVersioned(anyString());
    }

    @Test
    void decoderFailureRejectsWithRedactedErrorAndWithoutLegacyFallback() {
        var f = fixture(true, source("verified"), selected(7), selected(7), true);
        assertThatThrownBy(() -> f.converter().convert(source("caller")))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("OIDC source validation failed.").hasNoCause();
        verify(f.assertions(), never()).capture(any(), any());
    }

    @Test
    void mappingGenerationChangeDuringDecodingRejectsReceipt() {
        var f = fixture(true, source("verified"), selected(7), selected(8), false);
        assertThatThrownBy(() -> f.converter().convert(source("caller")))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("OIDC verified claim evidence unavailable.");
        verify(f.assertions(), never()).capture(any(), any());
    }

    @Test
    void privilegedOrUnavailableSelectorFailsClosedWithoutSensitiveDetails() {
        var f = fixture(true, source("verified"), selected(7), selected(7), false);
        when(f.catalog().selectVersioned(PROVIDER))
                .thenThrow(new IllegalStateException("sensitive-database-owner-detail"));
        assertThatThrownBy(() -> f.converter().convert(source("caller")))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("OIDC verified claim evidence unavailable.").hasNoCause();
        verify(f.assertions(), never()).capture(any(), any());
    }

    @Test
    void changedSourceSubjectDoesNotBecomeTrustedEvidence() {
        var changed = Jwt.withTokenValue("verified-token").header("alg", "RS256")
                .issuer(ISSUER).subject("changed-subject").issuedAt(ISSUED)
                .expiresAt(EXPIRY).claim("groups", List.of("verified")).build();
        var f = fixture(true, changed, selected(7), selected(7), false);
        assertThatThrownBy(() -> f.converter().convert(source("caller")))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("OIDC source identity or deadline changed.");
    }

    @Test
    void plainOidcTokenCannotForgeReceiptAndRealReceiptIsSingleUse() {
        var f = fixture(true, source("verified"), selected(7), selected(7), false);
        var token = (HidraOidcAuthenticationToken) f.converter().convert(source("caller"));
        var p = token.getPrincipal();
        var input = new AuthenticatedPrincipalInput(
                p.userId(), p.username(), p.displayName(), p.authenticationType(),
                p.identityProviderId(), p.roles(), p.permissions(), p.externalIdentityId(), null);
        SecurityContextHolder.getContext().setAuthentication(new HidraOidcAuthenticationToken(p));
        assertThatThrownBy(() -> f.registry().consume(input))
                .isInstanceOf(BadCredentialsException.class);
        SecurityContextHolder.getContext().setAuthentication(token);
        assertThat(f.registry().consume(input).snapshot().userId()).isEqualTo(USER);
        assertThatThrownBy(() -> f.registry().consume(input))
                .isInstanceOf(BadCredentialsException.class);
    }
}
