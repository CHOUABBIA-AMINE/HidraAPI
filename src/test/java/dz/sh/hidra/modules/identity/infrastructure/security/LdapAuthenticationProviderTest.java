/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LdapAuthenticationProviderTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Verifies LDAP/Active Directory authentication, Hidra identity linkage, account state, and authorization ownership.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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
import dz.sh.hidra.platform.security.LdapAuthenticationToken;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;

class LdapAuthenticationProviderTest {

    private static final String USER_ID = "user-ldap-1";
    private static final String PROVIDER_ID = "provider-ldap";
    private static final String USERNAME = "operator.directory";
    private static final String PASSWORD = "directory-secret";
    private static final String SUBJECT = "objectGUID:AQIDBA==";

    private LdapCredentialVerificationPort credentialVerificationPort;
    private IdentityProviderJpaRepository identityProviderRepository;
    private ExternalIdentityJpaRepository externalIdentityRepository;
    private UserJpaRepository userRepository;
    private IdentityAdministrationQueryUseCase queryUseCase;
    private LdapAuthenticationProvider provider;

    @BeforeEach
    void setUp() {
        credentialVerificationPort = mock(LdapCredentialVerificationPort.class);
        identityProviderRepository = mock(IdentityProviderJpaRepository.class);
        externalIdentityRepository = mock(ExternalIdentityJpaRepository.class);
        userRepository = mock(UserJpaRepository.class);
        queryUseCase = mock(IdentityAdministrationQueryUseCase.class);
        provider = new LdapAuthenticationProvider(
                credentialVerificationPort,
                identityProviderRepository,
                externalIdentityRepository,
                userRepository,
                queryUseCase
        );
    }

    @Test
    void authenticatesLinkedLdapIdentityAndUsesOnlyHidraPermissions() {
        arrangeSuccessfulAuthentication(ProviderType.LDAP, ExternalIdentityStatus.LINKED, UserStatus.ACTIVE);
        LdapAuthenticationToken request = LdapAuthenticationToken.unauthenticated(USERNAME, PASSWORD);
        request.setDetails("request-details");

        Authentication result = provider.authenticate(request);

        assertThat(result.isAuthenticated()).isTrue();
        assertThat(result.getCredentials()).isNull();
        assertThat(result.getDetails()).isEqualTo("request-details");
        HidraPrincipal principal = (HidraPrincipal) result.getPrincipal();
        assertThat(principal.userId()).isEqualTo(USER_ID);
        assertThat(principal.username()).isEqualTo(USERNAME);
        assertThat(principal.displayName()).isEqualTo("Directory Operator");
        assertThat(principal.authenticationType()).isEqualTo(ProviderType.LDAP);
        assertThat(principal.identityProviderId()).isEqualTo(PROVIDER_ID);
        assertThat(principal.roles()).isEmpty();
        assertThat(principal.permissions()).containsExactlyInAnyOrder("pipeline:read", "alarm:acknowledge");
        verify(credentialVerificationPort).verify(USERNAME, PASSWORD);
        verify(externalIdentityRepository).findByIdentityProviderIdAndExternalSubject(PROVIDER_ID, SUBJECT);
        verify(queryUseCase).principal(USER_ID, List.of());
    }

    @Test
    void preservesActiveDirectoryProviderTypeAfterSuccessfulDirectoryAuthentication() {
        arrangeSuccessfulAuthentication(ProviderType.ACTIVE_DIRECTORY, ExternalIdentityStatus.LINKED, UserStatus.ACTIVE);
        HidraPrincipal principal = (HidraPrincipal) provider
                .authenticate(LdapAuthenticationToken.unauthenticated(USERNAME, PASSWORD))
                .getPrincipal();
        assertThat(principal.authenticationType()).isEqualTo(ProviderType.ACTIVE_DIRECTORY);
        assertThat(principal.identityProviderId()).isEqualTo(PROVIDER_ID);
    }

    @Test
    void rejectsInvalidDirectoryCredentialsBeforeIdentityLinkage() {
        when(identityProviderRepository.findAll()).thenReturn(List.of(directoryProvider(ProviderType.LDAP)));
        when(credentialVerificationPort.verify(USERNAME, PASSWORD)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> provider.authenticate(LdapAuthenticationToken.unauthenticated(USERNAME, PASSWORD)))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Invalid LDAP/Active Directory credentials.");
        verify(externalIdentityRepository, never())
                .findByIdentityProviderIdAndExternalSubject(org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.anyString());
        verify(queryUseCase, never()).principal(org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.anyList());
    }

    @Test
    void rejectsVerifiedDirectoryIdentityThatIsNotLinkedToHidra() {
        when(identityProviderRepository.findAll()).thenReturn(List.of(directoryProvider(ProviderType.LDAP)));
        when(credentialVerificationPort.verify(USERNAME, PASSWORD)).thenReturn(Optional.of(verifiedIdentity()));
        when(externalIdentityRepository.findByIdentityProviderIdAndExternalSubject(PROVIDER_ID, SUBJECT))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> provider.authenticate(LdapAuthenticationToken.unauthenticated(USERNAME, PASSWORD)))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Verified directory identity is not linked to Hidra.");
        verify(userRepository, never()).findById(org.mockito.ArgumentMatchers.anyString());
        verify(queryUseCase, never()).principal(org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.anyList());
    }

    @Test
    void rejectsInactiveExternalIdentity() {
        arrangeSuccessfulAuthentication(ProviderType.LDAP, ExternalIdentityStatus.DISABLED_EXTERNAL, UserStatus.ACTIVE);

        assertThatThrownBy(() -> provider.authenticate(LdapAuthenticationToken.unauthenticated(USERNAME, PASSWORD)))
                .isInstanceOf(DisabledException.class)
                .hasMessage("External directory identity is not linked and active.");
        verify(userRepository, never()).findById(USER_ID);
    }

    @Test
    void rejectsLockedHidraUserAfterDirectoryVerification() {
        arrangeSuccessfulAuthentication(ProviderType.LDAP, ExternalIdentityStatus.LINKED, UserStatus.LOCKED);

        assertThatThrownBy(() -> provider.authenticate(LdapAuthenticationToken.unauthenticated(USERNAME, PASSWORD)))
                .isInstanceOf(LockedException.class)
                .hasMessage("Hidra user account is locked.");
        verify(queryUseCase, never()).principal(org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.anyList());
    }

    @Test
    void rejectsUnavailableAndAmbiguousDirectoryProviderConfiguration() {
        when(identityProviderRepository.findAll()).thenReturn(List.of());
        assertThatThrownBy(() -> provider.authenticate(LdapAuthenticationToken.unauthenticated(USERNAME, PASSWORD)))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("LDAP/Active Directory authentication provider is not available.");

        when(identityProviderRepository.findAll()).thenReturn(List.of(
                directoryProvider(ProviderType.LDAP),
                directoryProvider("provider-ad", ProviderType.ACTIVE_DIRECTORY)
        ));
        assertThatThrownBy(() -> provider.authenticate(LdapAuthenticationToken.unauthenticated(USERNAME, PASSWORD)))
                .isInstanceOf(AuthenticationServiceException.class)
                .hasMessage("Multiple active LDAP/Active Directory identity providers are configured.");
        verify(credentialVerificationPort, never()).verify(USERNAME, PASSWORD);
    }

    @Test
    void directoryOutageFailsClosedWithoutResolvingHidraIdentity() {
        when(identityProviderRepository.findAll()).thenReturn(List.of(directoryProvider(ProviderType.LDAP)));
        when(credentialVerificationPort.verify(USERNAME, PASSWORD))
                .thenThrow(new IllegalStateException("directory unavailable"));

        assertThatThrownBy(() -> provider.authenticate(LdapAuthenticationToken.unauthenticated(USERNAME, PASSWORD)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("directory unavailable");
        verify(externalIdentityRepository, never())
                .findByIdentityProviderIdAndExternalSubject(org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.anyString());
        verify(queryUseCase, never()).principal(org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.anyList());
    }

    @Test
    void supportsOnlyLdapAuthenticationTokenContract() {
        assertThat(provider.supports(LdapAuthenticationToken.class)).isTrue();
        assertThat(provider.supports(org.springframework.security.authentication.UsernamePasswordAuthenticationToken.class)).isFalse();
    }

    private void arrangeSuccessfulAuthentication(
            ProviderType providerType,
            ExternalIdentityStatus externalIdentityStatus,
            UserStatus userStatus
    ) {
        IdentityAdministrationQueryUseCase.PrincipalView authorization =
                mock(IdentityAdministrationQueryUseCase.PrincipalView.class);
        when(identityProviderRepository.findAll()).thenReturn(List.of(directoryProvider(providerType)));
        when(credentialVerificationPort.verify(USERNAME, PASSWORD)).thenReturn(Optional.of(verifiedIdentity()));
        when(externalIdentityRepository.findByIdentityProviderIdAndExternalSubject(PROVIDER_ID, SUBJECT))
                .thenReturn(Optional.of(externalIdentity(externalIdentityStatus)));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user(userStatus)));
        when(queryUseCase.principal(USER_ID, List.of())).thenReturn(authorization);
        when(authorization.effectivePermissions()).thenReturn(List.of("pipeline:read", "alarm:acknowledge"));
    }

    private static VerifiedDirectoryIdentity verifiedIdentity() {
        return new VerifiedDirectoryIdentity(
                SUBJECT,
                USERNAME,
                "External Directory Name",
                "operator.directory@example.invalid",
                "CN=Operator,OU=Users,DC=example,DC=invalid"
        );
    }

    private static IdentityProviderJpaEntity directoryProvider(ProviderType providerType) {
        return directoryProvider(PROVIDER_ID, providerType);
    }

    private static IdentityProviderJpaEntity directoryProvider(String id, ProviderType providerType) {
        Instant now = Instant.parse("2026-09-15T10:00:00Z");
        return new IdentityProviderJpaEntity(
                id, providerType.name(), providerType.name(), providerType,
                null, null, null, null,
                "DC=example,DC=invalid", "OU=Users", null,
                "userPrincipalName", "mail", "displayName", "objectGUID", null,
                false, false, IdentityProviderStatus.ACTIVE, null, null, now, now
        );
    }

    private static ExternalIdentityJpaEntity externalIdentity(ExternalIdentityStatus status) {
        Instant now = Instant.parse("2026-09-15T10:00:00Z");
        return new ExternalIdentityJpaEntity(
                "external-identity-1", USER_ID, PROVIDER_ID, SUBJECT, SUBJECT, USERNAME,
                "operator.directory@example.invalid", "External Directory Name",
                "CN=Operator,OU=Users,DC=example,DC=invalid", null,
                now, now, status, now, now
        );
    }

    private static UserJpaEntity user(UserStatus status) {
        Instant now = Instant.parse("2026-09-15T10:00:00Z");
        return new UserJpaEntity(
                USER_ID, USERNAME, "operator.directory@hidra.invalid", "Directory Operator",
                UserType.HUMAN, status, null, null, 0,
                status == UserStatus.LOCKED ? now.plusSeconds(300) : null,
                now, status == UserStatus.ACTIVE ? now : null, null, null, now
        );
    }
}
