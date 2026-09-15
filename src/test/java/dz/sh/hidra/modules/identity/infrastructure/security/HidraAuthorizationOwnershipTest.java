/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraAuthorizationOwnershipTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Proves external authentication claims and directory identity data cannot bypass Hidra-owned authorization.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.identity.application.model.VerifiedDirectoryIdentity;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
import dz.sh.hidra.modules.identity.application.port.out.LdapCredentialVerificationPort;
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
import dz.sh.hidra.platform.security.HidraEffectivePermissionResolver;
import dz.sh.hidra.platform.security.LdapAuthenticationToken;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

class HidraAuthorizationOwnershipTest {

    private static final String USER_ID = "user-authorization-owner";
    private static final String USERNAME = "authorization.operator";
    private static final String OIDC_PROVIDER_ID = "provider-oidc-authorization";
    private static final String LDAP_PROVIDER_ID = "provider-ldap-authorization";
    private static final String OIDC_ISSUER = "https://idp.example.invalid/realms/hidra";
    private static final String OIDC_SUBJECT = "external-oidc-authorization";
    private static final String LDAP_SUBJECT = "objectGUID:AQIDBA==";

    @Test
    void externalOidcRolesGroupsAndScopesCannotGrantHidraBusinessPermission() {
        IdentityProviderJpaRepository providerRepository = mock(IdentityProviderJpaRepository.class);
        ExternalIdentityJpaRepository externalIdentityRepository = mock(ExternalIdentityJpaRepository.class);
        UserJpaRepository userRepository = mock(UserJpaRepository.class);
        IdentityAdministrationQueryUseCase queryUseCase = mock(IdentityAdministrationQueryUseCase.class);
        IdentityAdministrationQueryUseCase.PrincipalView authorization =
                mock(IdentityAdministrationQueryUseCase.PrincipalView.class);

        when(providerRepository.findByProviderTypeAndIssuerUri(ProviderType.OIDC, OIDC_ISSUER))
                .thenReturn(Optional.of(oidcProvider()));
        when(externalIdentityRepository.findByIdentityProviderIdAndExternalSubject(OIDC_PROVIDER_ID, OIDC_SUBJECT))
                .thenReturn(Optional.of(externalIdentity("external-oidc-link", OIDC_PROVIDER_ID, OIDC_SUBJECT)));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(activeUser()));
        when(queryUseCase.principal(USER_ID, List.of())).thenReturn(authorization);
        when(authorization.effectivePermissions()).thenReturn(List.of("pipeline:read"));

        IdentityOidcJwtAuthenticationConverter converter = new IdentityOidcJwtAuthenticationConverter(
                providerRepository,
                externalIdentityRepository,
                userRepository,
                queryUseCase
        );
        Authentication authentication = converter.convert(externalJwtWithPrivilegedClaims());
        HidraPrincipal principal = (HidraPrincipal) authentication.getPrincipal();
        HidraEffectivePermissionResolver resolver = new HidraEffectivePermissionResolver(List.of(
                requestedPrincipal -> USER_ID.equals(requestedPrincipal) ? principal.permissions() : Set.of()
        ));

        assertThat(authentication.getAuthorities()).isEmpty();
        assertThat(principal.roles()).isEmpty();
        assertThat(principal.permissions()).containsExactly("pipeline:read");
        assertThat(resolver.hasPermission(authentication, "pipeline:read")).isTrue();
        assertThat(resolver.hasPermission(authentication, "pipeline:delete")).isFalse();
        assertThat(resolver.hasPermission(authentication, HidraEffectivePermissionResolver.ALL_PERMISSIONS)).isFalse();
    }

    @Test
    void directoryAuthenticationCannotGrantPermissionsBeyondHidraAuthorization() {
        LdapCredentialVerificationPort credentialVerificationPort = mock(LdapCredentialVerificationPort.class);
        IdentityProviderJpaRepository providerRepository = mock(IdentityProviderJpaRepository.class);
        ExternalIdentityJpaRepository externalIdentityRepository = mock(ExternalIdentityJpaRepository.class);
        UserJpaRepository userRepository = mock(UserJpaRepository.class);
        IdentityAdministrationQueryUseCase queryUseCase = mock(IdentityAdministrationQueryUseCase.class);
        IdentityAdministrationQueryUseCase.PrincipalView authorization =
                mock(IdentityAdministrationQueryUseCase.PrincipalView.class);

        when(providerRepository.findAll()).thenReturn(List.of(ldapProvider()));
        when(credentialVerificationPort.verify(USERNAME, "directory-secret"))
                .thenReturn(Optional.of(new VerifiedDirectoryIdentity(
                        LDAP_SUBJECT,
                        USERNAME,
                        "Directory Administrator",
                        "authorization.operator@example.invalid",
                        "CN=Authorization Operator,OU=Domain Admins,DC=example,DC=invalid"
                )));
        when(externalIdentityRepository.findByIdentityProviderIdAndExternalSubject(LDAP_PROVIDER_ID, LDAP_SUBJECT))
                .thenReturn(Optional.of(externalIdentity("external-ldap-link", LDAP_PROVIDER_ID, LDAP_SUBJECT)));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(activeUser()));
        when(queryUseCase.principal(USER_ID, List.of())).thenReturn(authorization);
        when(authorization.effectivePermissions()).thenReturn(List.of("alarm:acknowledge"));

        LdapAuthenticationProvider provider = new LdapAuthenticationProvider(
                credentialVerificationPort,
                providerRepository,
                externalIdentityRepository,
                userRepository,
                queryUseCase
        );
        Authentication authentication = provider.authenticate(
                LdapAuthenticationToken.unauthenticated(USERNAME, "directory-secret")
        );
        HidraPrincipal principal = (HidraPrincipal) authentication.getPrincipal();
        HidraEffectivePermissionResolver resolver = new HidraEffectivePermissionResolver(List.of(
                requestedPrincipal -> USER_ID.equals(requestedPrincipal) ? principal.permissions() : Set.of()
        ));

        assertThat(authentication.getAuthorities()).isEmpty();
        assertThat(principal.roles()).isEmpty();
        assertThat(principal.permissions()).containsExactly("alarm:acknowledge");
        assertThat(resolver.hasPermission(authentication, "alarm:acknowledge")).isTrue();
        assertThat(resolver.hasPermission(authentication, "identity:users:write")).isFalse();
        assertThat(resolver.hasPermission(authentication, HidraEffectivePermissionResolver.ALL_PERMISSIONS)).isFalse();
    }

    private static Jwt externalJwtWithPrivilegedClaims() {
        return Jwt.withTokenValue("external-oidc-token")
                .header("alg", "RS256")
                .issuer(OIDC_ISSUER)
                .subject(OIDC_SUBJECT)
                .issuedAt(Instant.parse("2026-09-15T10:00:00Z"))
                .expiresAt(Instant.parse("2026-09-15T11:00:00Z"))
                .claim("roles", List.of("HIDRA_ADMIN", "pipeline:delete"))
                .claim("groups", List.of("Domain Admins", "Hidra Administrators"))
                .claim("scope", "openid pipeline:delete identity:users:write")
                .claim("permissions", List.of("*", "identity:users:write"))
                .build();
    }

    private static IdentityProviderJpaEntity oidcProvider() {
        Instant now = Instant.parse("2026-09-15T10:00:00Z");
        return new IdentityProviderJpaEntity(
                OIDC_PROVIDER_ID,
                "OIDC_AUTHORIZATION",
                "OIDC Authorization",
                ProviderType.OIDC,
                OIDC_ISSUER,
                OIDC_ISSUER + "/authorize",
                OIDC_ISSUER + "/token",
                OIDC_ISSUER + "/jwks",
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
                IdentityProviderStatus.ACTIVE,
                null,
                null,
                now,
                now
        );
    }

    private static IdentityProviderJpaEntity ldapProvider() {
        Instant now = Instant.parse("2026-09-15T10:00:00Z");
        return new IdentityProviderJpaEntity(
                LDAP_PROVIDER_ID,
                "LDAP_AUTHORIZATION",
                "LDAP Authorization",
                ProviderType.LDAP,
                null,
                null,
                null,
                null,
                "DC=example,DC=invalid",
                "OU=Users",
                null,
                "userPrincipalName",
                "mail",
                "displayName",
                "objectGUID",
                null,
                false,
                false,
                IdentityProviderStatus.ACTIVE,
                null,
                null,
                now,
                now
        );
    }

    private static ExternalIdentityJpaEntity externalIdentity(String id, String providerId, String subject) {
        Instant now = Instant.parse("2026-09-15T10:00:00Z");
        return new ExternalIdentityJpaEntity(
                id,
                USER_ID,
                providerId,
                subject,
                subject,
                USERNAME,
                "authorization.operator@example.invalid",
                "Authorization Operator",
                null,
                null,
                now,
                now,
                ExternalIdentityStatus.LINKED,
                now,
                now
        );
    }

    private static UserJpaEntity activeUser() {
        Instant now = Instant.parse("2026-09-15T10:00:00Z");
        return new UserJpaEntity(
                USER_ID,
                USERNAME,
                "authorization.operator@hidra.invalid",
                "Authorization Operator",
                UserType.HUMAN,
                UserStatus.ACTIVE,
                null,
                null,
                0,
                null,
                now,
                now,
                null,
                null,
                now
        );
    }
}
