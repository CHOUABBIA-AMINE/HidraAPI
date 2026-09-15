/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LocalAuthenticationProviderTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Verifies persisted LOCAL credential authentication, account-state enforcement, and Hidra principal normalization.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
import dz.sh.hidra.modules.identity.application.port.out.LocalCredentialRepositoryPort;
import dz.sh.hidra.modules.identity.application.service.LocalAuthenticationOutcomeApplicationService;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.model.LocalCredential;
import dz.sh.hidra.modules.identity.domain.value.IdentityProviderStatus;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.UserType;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.IdentityProviderJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.IdentityProviderJpaRepository;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserJpaRepository;
import dz.sh.hidra.platform.security.LocalAuthenticationToken;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class LocalAuthenticationProviderTest {

    private static final String USER_ID = "user-1";
    private static final String PROVIDER_ID = "provider-local";
    private static final String USERNAME = "operator";
    private static final String PASSWORD = "correct-secret";

    private IdentityProviderJpaRepository identityProviderRepository;
    private UserJpaRepository userRepository;
    private LocalCredentialRepositoryPort localCredentialRepository;
    private IdentityAdministrationQueryUseCase queryUseCase;
    private LocalAuthenticationOutcomeApplicationService outcomeService;
    private PasswordEncoder passwordEncoder;
    private LocalAuthenticationProvider provider;

    @BeforeEach
    void setUp() {
        identityProviderRepository = mock(IdentityProviderJpaRepository.class);
        userRepository = mock(UserJpaRepository.class);
        localCredentialRepository = mock(LocalCredentialRepositoryPort.class);
        queryUseCase = mock(IdentityAdministrationQueryUseCase.class);
        outcomeService = mock(LocalAuthenticationOutcomeApplicationService.class);
        passwordEncoder = new BCryptPasswordEncoder();
        provider = new LocalAuthenticationProvider(
                identityProviderRepository,
                userRepository,
                localCredentialRepository,
                queryUseCase,
                outcomeService,
                passwordEncoder
        );
    }

    @Test
    void authenticatesPersistedActiveLocalCredentialAndReturnsNormalizedHidraPrincipal() {
        IdentityProviderJpaEntity localProvider = localProvider(IdentityProviderStatus.ACTIVE);
        UserJpaEntity user = user(UserStatus.ACTIVE, null);
        LocalCredential credential = credential("ACTIVE", passwordEncoder.encode(PASSWORD));
        IdentityAdministrationQueryUseCase.PrincipalView authorization =
                mock(IdentityAdministrationQueryUseCase.PrincipalView.class);

        when(identityProviderRepository.findAll()).thenReturn(List.of(localProvider));
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        when(localCredentialRepository.findByUserId(USER_ID)).thenReturn(Optional.of(credential));
        when(queryUseCase.principal(USER_ID, List.of())).thenReturn(authorization);
        when(authorization.effectivePermissions()).thenReturn(List.of("pipeline:read", "alarm:acknowledge"));

        LocalAuthenticationToken request = LocalAuthenticationToken.unauthenticated(USERNAME, PASSWORD);
        request.setDetails("request-details");

        Authentication result = provider.authenticate(request);

        assertThat(result.isAuthenticated()).isTrue();
        assertThat(result.getCredentials()).isNull();
        assertThat(result.getDetails()).isEqualTo("request-details");
        assertThat(result.getPrincipal()).isInstanceOf(HidraPrincipal.class);

        HidraPrincipal principal = (HidraPrincipal) result.getPrincipal();
        assertThat(principal.userId()).isEqualTo(USER_ID);
        assertThat(principal.username()).isEqualTo(USERNAME);
        assertThat(principal.displayName()).isEqualTo("Pipeline Operator");
        assertThat(principal.authenticationType()).isEqualTo(ProviderType.LOCAL);
        assertThat(principal.identityProviderId()).isEqualTo(PROVIDER_ID);
        assertThat(principal.roles()).isEmpty();
        assertThat(principal.permissions()).containsExactlyInAnyOrder("pipeline:read", "alarm:acknowledge");

        verify(queryUseCase).principal(USER_ID, List.of());
        verify(outcomeService).recordSuccess(USER_ID, PROVIDER_ID);
        verify(outcomeService, never()).recordFailure(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());
    }

    @Test
    void rejectsWrongPasswordAndRecordsSanitizedFailureWithoutResolvingPermissions() {
        when(identityProviderRepository.findAll()).thenReturn(List.of(localProvider(IdentityProviderStatus.ACTIVE)));
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user(UserStatus.ACTIVE, null)));
        when(localCredentialRepository.findByUserId(USER_ID))
                .thenReturn(Optional.of(credential("ACTIVE", passwordEncoder.encode(PASSWORD))));

        assertThatThrownBy(() -> provider.authenticate(
                LocalAuthenticationToken.unauthenticated(USERNAME, "wrong-secret")
        )).isInstanceOf(BadCredentialsException.class)
                .hasMessage("Invalid LOCAL credentials.");

        verify(queryUseCase, never()).principal(org.mockito.ArgumentMatchers.anyString(), anyList());
        verify(outcomeService).recordFailure(USER_ID, PROVIDER_ID, "BAD_CREDENTIALS");
        verify(outcomeService, never()).recordSuccess(org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.anyString());
    }

    @Test
    void rejectsLockedUserBeforeCredentialVerificationAndRecordsLockedOutcome() {
        when(identityProviderRepository.findAll()).thenReturn(List.of(localProvider(IdentityProviderStatus.ACTIVE)));
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user(UserStatus.LOCKED, null)));

        assertThatThrownBy(() -> provider.authenticate(LocalAuthenticationToken.unauthenticated(USERNAME, PASSWORD)))
                .isInstanceOf(LockedException.class)
                .hasMessage("Hidra user account is locked.");

        verify(localCredentialRepository, never()).findByUserId(USER_ID);
        verify(outcomeService).recordFailure(USER_ID, PROVIDER_ID, "ACCOUNT_LOCKED");
    }

    @Test
    void rejectsInactiveCredentialAndRecordsDisabledOutcome() {
        when(identityProviderRepository.findAll()).thenReturn(List.of(localProvider(IdentityProviderStatus.ACTIVE)));
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user(UserStatus.ACTIVE, null)));
        when(localCredentialRepository.findByUserId(USER_ID))
                .thenReturn(Optional.of(credential("DISABLED", passwordEncoder.encode(PASSWORD))));

        assertThatThrownBy(() -> provider.authenticate(LocalAuthenticationToken.unauthenticated(USERNAME, PASSWORD)))
                .isInstanceOf(DisabledException.class)
                .hasMessage("LOCAL credential is not active.");

        verify(queryUseCase, never()).principal(org.mockito.ArgumentMatchers.anyString(), anyList());
        verify(outcomeService).recordFailure(USER_ID, PROVIDER_ID, "ACCOUNT_OR_CREDENTIAL_DISABLED");
    }

    @Test
    void rejectsUnavailableLocalProviderWithoutLookingUpUserOrCredential() {
        when(identityProviderRepository.findAll()).thenReturn(List.of());

        assertThatThrownBy(() -> provider.authenticate(LocalAuthenticationToken.unauthenticated(USERNAME, PASSWORD)))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("LOCAL authentication provider is not available.");

        verify(userRepository, never()).findByUsername(USERNAME);
        verify(localCredentialRepository, never()).findByUserId(USER_ID);
        verify(outcomeService).recordFailure(null, null, "BAD_CREDENTIALS");
    }

    @Test
    void supportsOnlyLocalAuthenticationTokenContract() {
        assertThat(provider.supports(LocalAuthenticationToken.class)).isTrue();
        assertThat(provider.supports(org.springframework.security.authentication.UsernamePasswordAuthenticationToken.class)).isFalse();
    }

    private static IdentityProviderJpaEntity localProvider(IdentityProviderStatus status) {
        Instant now = Instant.parse("2026-09-15T08:00:00Z");
        return new IdentityProviderJpaEntity(
                PROVIDER_ID,
                "LOCAL",
                "Local",
                ProviderType.LOCAL,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                false,
                false,
                status,
                null,
                null,
                now,
                now
        );
    }

    private static UserJpaEntity user(UserStatus status, Instant lockedUntil) {
        Instant now = Instant.parse("2026-09-15T08:00:00Z");
        return new UserJpaEntity(
                USER_ID,
                USERNAME,
                "operator@hidra.invalid",
                "Pipeline Operator",
                UserType.HUMAN,
                status,
                null,
                null,
                0,
                lockedUntil,
                now,
                status == UserStatus.ACTIVE ? now : null,
                null,
                null,
                now
        );
    }

    private static LocalCredential credential(String status, String hash) {
        Instant now = Instant.parse("2026-09-15T08:00:00Z");
        return new LocalCredential("credential-1", USER_ID, hash, status, now, now, now);
    }
}
