/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityOidcJwtAuthenticationConverterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Verifies validated OIDC identities normalize through Hidra linkage, account state, and Hidra-owned authorization.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.value.ExternalIdentityStatus;
import dz.sh.hidra.modules.identity.domain.value.IdentityProviderStatus;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.UserType;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.ExternalIdentityJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.IdentityProviderJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.ExternalIdentityJpaRepository;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.IdentityProviderJpaRepository;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserJpaRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.oauth2.jwt.Jwt;

class IdentityOidcJwtAuthenticationConverterTest {

    private static final String USER_ID = "user-oidc-1";
    private static final String PROVIDER_ID = "provider-oidc";
    private static final String USERNAME = "operator.oidc";
    private static final String ISSUER = "https://idp.example.invalid/realms/hidra";
    private static final String SUBJECT = "external-subject-123";

    private IdentityProviderJpaRepository identityProviderRepository;
    private ExternalIdentityJpaRepository externalIdentityRepository;
    private UserJpaRepository userRepository;
    private IdentityAdministrationQueryUseCase queryUseCase;
    private IdentityOidcJwtAuthenticationConverter converter;

    @BeforeEach
    void setUp() {
        identityProviderRepository = mock(IdentityProviderJpaRepository.class);
        externalIdentityRepository = mock(ExternalIdentityJpaRepository.class);
        userRepository = mock(UserJpaRepository.class);
        queryUseCase = mock(IdentityAdministrationQueryUseCase.class);
        converter = new IdentityOidcJwtAuthenticationConverter(
                identityProviderRepository,
                externalIdentityRepository,
                userRepository,
                queryUseCase
        );
    }

    @Test
    void normalizesLinkedOidcIdentityAndUsesOnlyHidraPermissions() {
        IdentityAdministrationQueryUseCase.PrincipalView authorization =
                mock(IdentityAdministrationQueryUseCase.PrincipalView.class);
        when(identityProviderRepository.findByProviderTypeAndIssuerUri(ProviderType.OIDC, ISSUER))
                .thenReturn(Optional.of(oidcProvider(IdentityProviderStatus.ACTIVE)));
        when(externalIdentityRepository.findByIdentityProviderIdAndExternalSubject(PROVIDER_ID, SUBJECT))
                .thenReturn(Optional.of(externalIdentity(ExternalIdentityStatus.LINKED)));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user(UserStatus.ACTIVE)));
        when(queryUseCase.principal(USER_ID, List.of())).thenReturn(authorization);
        when(authorization.effectivePermissions()).thenReturn(List.of("pipeline:read", "alarm:acknowledge"));

        HidraOidcAuthenticationToken result = (HidraOidcAuthenticationToken) converter.convert(jwt());

        assertThat(result.isAuthenticated()).isTrue();
        assertThat(result.getCredentials()).isNull();
        assertThat(result.getAuthorities()).isEmpty();
        assertThat(result.getPrincipal()).isInstanceOf(HidraPrincipal.class);

        HidraPrincipal principal = result.getPrincipal();
        assertThat(principal.userId()).isEqualTo(USER_ID);
        assertThat(principal.username()).isEqualTo(USERNAME);
        assertThat(principal.displayName()).isEqualTo("OIDC Operator");
        assertThat(principal.authenticationType()).isEqualTo(ProviderType.OIDC);
        assertThat(principal.identityProviderId()).isEqualTo(PROVIDER_ID);
        assertThat(principal.roles()).isEmpty();
        assertThat(principal.permissions()).containsExactlyInAnyOrder("pipeline:read", "alarm:acknowledge");

        verify(identityProviderRepository).findByProviderTypeAndIssuerUri(ProviderType.OIDC, ISSUER);
        verify(externalIdentityRepository).findByIdentityProviderIdAndExternalSubject(PROVIDER_ID, SUBJECT);
        verify(queryUseCase).principal(USER_ID, List.of());
    }

    @Test
    void ignoresExternalRoleAndGroupClaimsForHidraAuthorization() {
        IdentityAdministrationQueryUseCase.PrincipalView authorization =
                mock(IdentityAdministrationQueryUseCase.PrincipalView.class);
        when(identityProviderRepository.findByProviderTypeAndIssuerUri(ProviderType.OIDC, ISSUER))
                .thenReturn(Optional.of(oidcProvider(IdentityProviderStatus.ACTIVE)));
        when(externalIdentityRepository.findByIdentityProviderIdAndExternalSubject(PROVIDER_ID, SUBJECT))
                .thenReturn(Optional.of(externalIdentity(ExternalIdentityStatus.LINKED)));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user(UserStatus.ACTIVE)));
        when(queryUseCase.principal(USER_ID, List.of())).thenReturn(authorization);
        when(authorization.effectivePermissions()).thenReturn(List.of("pipeline:read"));

        Jwt externalJwt = Jwt.withTokenValue("external-token")
                .header("alg", "RS256")
                .issuer(ISSUER)
                .subject(SUBJECT)
                .issuedAt(Instant.parse("2026-09-15T10:00:00Z"))
                .expiresAt(Instant.parse("2026-09-15T11:00:00Z"))
                .claim("roles", List.of("HIDRA_ADMIN", "pipeline:delete"))
                .claim("groups", List.of("Domain Admins", "Operators"))
                .claim("scope", "openid pipeline:delete")
                .build();

        HidraPrincipal principal = ((HidraOidcAuthenticationToken) converter.convert(externalJwt)).getPrincipal();

        assertThat(principal.roles()).isEmpty();
        assertThat(principal.permissions()).containsExactly("pipeline:read");
        assertThat(principal.permissions()).doesNotContain("pipeline:delete");
    }

    @Test
    void rejectsMissingIssuerOrSubjectBeforeIdentityLookup() {
        Jwt missingIssuer = Jwt.withTokenValue("external-token")
                .header("alg", "RS256")
                .subject(SUBJECT)
                .issuedAt(Instant.parse("2026-09-15T10:00:00Z"))
                .expiresAt(Instant.parse("2026-09-15T11:00:00Z"))
                .build();
        Jwt missingSubject = Jwt.withTokenValue("external-token")
                .header("alg", "RS256")
                .issuer(ISSUER)
                .issuedAt(Instant.parse("2026-09-15T10:00:00Z"))
                .expiresAt(Instant.parse("2026-09-15T11:00:00Z"))
                .build();

        assertThatThrownBy(() -> converter.convert(missingIssuer))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("OIDC identity is missing issuer or subject.");
        assertThatThrownBy(() -> converter.convert(missingSubject))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("OIDC identity is missing issuer or subject.");

        verify(identityProviderRepository, never()).findByProviderTypeAndIssuerUri(
                org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.anyString());
    }

    @Test
    void rejectsUnknownOrInactiveOidcProvider() {
        when(identityProviderRepository.findByProviderTypeAndIssuerUri(ProviderType.OIDC, ISSUER))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> converter.convert(jwt()))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("OIDC identity provider is not available.");

        when(identityProviderRepository.findByProviderTypeAndIssuerUri(ProviderType.OIDC, ISSUER))
                .thenReturn(Optional.of(oidcProvider(IdentityProviderStatus.INACTIVE)));

        assertThatThrownBy(() -> converter.convert(jwt()))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("OIDC identity provider is not available.");

        verify(externalIdentityRepository, never()).findByIdentityProviderIdAndExternalSubject(
                org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.anyString());
    }

    @Test
    void rejectsUnmappedOrInactiveExternalIdentity() {
        when(identityProviderRepository.findByProviderTypeAndIssuerUri(ProviderType.OIDC, ISSUER))
                .thenReturn(Optional.of(oidcProvider(IdentityProviderStatus.ACTIVE)));
        when(externalIdentityRepository.findByIdentityProviderIdAndExternalSubject(PROVIDER_ID, SUBJECT))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> converter.convert(jwt()))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("OIDC identity is not linked to a Hidra user.");

        when(externalIdentityRepository.findByIdentityProviderIdAndExternalSubject(PROVIDER_ID, SUBJECT))
                .thenReturn(Optional.of(externalIdentity(ExternalIdentityStatus.UNLINKED)));

        assertThatThrownBy(() -> converter.convert(jwt()))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("OIDC identity is not linked to a Hidra user.");

        verify(userRepository, never()).findById(USER_ID);
        verify(queryUseCase, never()).principal(org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.anyList());
    }

    @Test
    void rejectsLockedAndDisabledMappedHidraUser() {
        arrangeLinkedIdentity(user(UserStatus.LOCKED));

        assertThatThrownBy(() -> converter.convert(jwt()))
                .isInstanceOf(LockedException.class)
                .hasMessage("Hidra user account is locked.");
        verify(queryUseCase, never()).principal(org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.anyList());

        setUp();
        arrangeLinkedIdentity(user(UserStatus.DISABLED));

        assertThatThrownBy(() -> converter.convert(jwt()))
                .isInstanceOf(DisabledException.class)
                .hasMessage("Hidra user account is not active.");
        verify(queryUseCase, never()).principal(org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.anyList());
    }

    @Test
    void rejectsLinkedIdentityWhoseHidraUserNoLongerExists() {
        when(identityProviderRepository.findByProviderTypeAndIssuerUri(ProviderType.OIDC, ISSUER))
                .thenReturn(Optional.of(oidcProvider(IdentityProviderStatus.ACTIVE)));
        when(externalIdentityRepository.findByIdentityProviderIdAndExternalSubject(PROVIDER_ID, SUBJECT))
                .thenReturn(Optional.of(externalIdentity(ExternalIdentityStatus.LINKED)));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> converter.convert(jwt()))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("OIDC identity is not linked to a Hidra user.");
        verify(queryUseCase, never()).principal(org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.anyList());
    }

    private void arrangeLinkedIdentity(UserJpaEntity mappedUser) {
        when(identityProviderRepository.findByProviderTypeAndIssuerUri(ProviderType.OIDC, ISSUER))
                .thenReturn(Optional.of(oidcProvider(IdentityProviderStatus.ACTIVE)));
        when(externalIdentityRepository.findByIdentityProviderIdAndExternalSubject(PROVIDER_ID, SUBJECT))
                .thenReturn(Optional.of(externalIdentity(ExternalIdentityStatus.LINKED)));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(mappedUser));
    }

    private static Jwt jwt() {
        return Jwt.withTokenValue("external-token")
                .header("alg", "RS256")
                .issuer(ISSUER)
                .subject(SUBJECT)
                .issuedAt(Instant.parse("2026-09-15T10:00:00Z"))
                .expiresAt(Instant.parse("2026-09-15T11:00:00Z"))
                .claim("email", "external.operator@example.invalid")
                .build();
    }

    private static IdentityProviderJpaEntity oidcProvider(IdentityProviderStatus status) {
        Instant now = Instant.parse("2026-09-15T10:00:00Z");
        return new IdentityProviderJpaEntity(
                PROVIDER_ID,
                "OIDC_MAIN",
                "OIDC Main",
                ProviderType.OIDC,
                ISSUER,
                ISSUER + "/protocol/openid-connect/auth",
                ISSUER + "/protocol/openid-connect/token",
                ISSUER + "/protocol/openid-connect/certs",
                null,
                null,
                null,
                null,
                "email",
                "name",
                "sub",
                "groups",
                false,
                false,
                status,
                null,
                null,
                now,
                now
        );
    }

    private static ExternalIdentityJpaEntity externalIdentity(ExternalIdentityStatus status) {
        Instant now = Instant.parse("2026-09-15T10:00:00Z");
        return new ExternalIdentityJpaEntity(
                "external-oidc-1",
                USER_ID,
                PROVIDER_ID,
                SUBJECT,
                SUBJECT,
                USERNAME,
                "external.operator@example.invalid",
                "External OIDC Name",
                null,
                null,
                now,
                now,
                status,
                now,
                now
        );
    }

    private static UserJpaEntity user(UserStatus status) {
        Instant now = Instant.parse("2026-09-15T10:00:00Z");
        return new UserJpaEntity(
                USER_ID,
                USERNAME,
                "operator.oidc@hidra.invalid",
                "OIDC Operator",
                UserType.HUMAN,
                status,
                null,
                null,
                0,
                status == UserStatus.LOCKED ? now.plusSeconds(300) : null,
                now,
                status == UserStatus.ACTIVE ? now : null,
                null,
                null,
                now
        );
    }
}
