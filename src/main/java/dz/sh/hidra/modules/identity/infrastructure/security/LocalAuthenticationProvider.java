/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LocalAuthenticationProvider
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-19
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Verifies persisted LOCAL credentials, records outcomes, and normalizes successful authentication to a Hidra principal.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
import dz.sh.hidra.modules.identity.application.port.out.LocalCredentialRepositoryPort;
import dz.sh.hidra.modules.identity.application.service.LocalAuthenticationOutcomeApplicationService;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.model.LocalCredential;
import dz.sh.hidra.modules.identity.domain.value.IdentityProviderStatus;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.IdentityProviderJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.IdentityProviderJpaRepository;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserJpaRepository;
import dz.sh.hidra.platform.security.LocalAuthenticationToken;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Database-backed LOCAL authentication strategy.
 */
@Component
public final class LocalAuthenticationProvider implements AuthenticationProvider {

    private static final String ACTIVE_CREDENTIAL_STATUS = "ACTIVE";

    private final IdentityProviderJpaRepository identityProviderRepository;
    private final UserJpaRepository userRepository;
    private final LocalCredentialRepositoryPort localCredentialRepository;
    private final IdentityAdministrationQueryUseCase queryUseCase;
    private final LocalAuthenticationOutcomeApplicationService outcomeService;
    private final PasswordEncoder passwordEncoder;
    private final IdentityAdministratorGrantService administratorGrantService;

    public LocalAuthenticationProvider(
            IdentityProviderJpaRepository identityProviderRepository,
            UserJpaRepository userRepository,
            LocalCredentialRepositoryPort localCredentialRepository,
            IdentityAdministrationQueryUseCase queryUseCase,
            LocalAuthenticationOutcomeApplicationService outcomeService,
            PasswordEncoder passwordEncoder,
            IdentityAdministratorGrantService administratorGrantService
    ) {
        this.identityProviderRepository = Objects.requireNonNull(identityProviderRepository);
        this.userRepository = Objects.requireNonNull(userRepository);
        this.localCredentialRepository = Objects.requireNonNull(localCredentialRepository);
        this.queryUseCase = Objects.requireNonNull(queryUseCase);
        this.outcomeService = Objects.requireNonNull(outcomeService);
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
        this.administratorGrantService = Objects.requireNonNull(administratorGrantService);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof LocalAuthenticationToken localRequest)) {
            return null;
        }

        String username = normalize(localRequest.getPrincipal() == null ? null : localRequest.getPrincipal().toString());
        String submittedPassword = localRequest.getCredentials() instanceof String value ? value : null;
        String userId = null;
        String providerId = null;

        try {
            if (username == null || submittedPassword == null || submittedPassword.isEmpty()) {
                throw invalidCredentials();
            }

            IdentityProviderJpaEntity localProvider = resolveActiveLocalProvider();
            providerId = localProvider.id();

            UserJpaEntity user = userRepository.findByUsername(username)
                    .orElseThrow(LocalAuthenticationProvider::invalidCredentials);
            userId = user.id();
            enforceAccountState(user);

            LocalCredential credential = localCredentialRepository.findByUserId(user.id())
                    .orElseThrow(LocalAuthenticationProvider::invalidCredentials);
            if (!ACTIVE_CREDENTIAL_STATUS.equalsIgnoreCase(credential.credentialStatus())) {
                throw new DisabledException("LOCAL credential is not active.");
            }
            if (!passwordEncoder.matches(submittedPassword, credential.passwordHash())) {
                throw invalidCredentials();
            }

            IdentityAdministrationQueryUseCase.PrincipalView authorization =
                    queryUseCase.principal(user.id(), List.of());

            boolean globalAdministrator = administratorGrantService.hasActiveGlobalAdministratorGrant(user.id());
            Set<String> roles = globalAdministrator ? Set.of("HIDRA_ADMIN") : Set.of();

            HidraPrincipal principal = new HidraPrincipal(
                    user.id(),
                    user.username(),
                    user.displayName(),
                    ProviderType.LOCAL,
                    localProvider.id(),
                    roles,
                    Set.copyOf(authorization.effectivePermissions())
            );

            outcomeService.recordSuccess(user.id(), localProvider.id());

            UsernamePasswordAuthenticationToken result = UsernamePasswordAuthenticationToken.authenticated(
                    principal,
                    null,
                    globalAdministrator
                            ? List.of(new SimpleGrantedAuthority("ROLE_HIDRA_ADMIN"))
                            : List.of()
            );
            result.setDetails(localRequest.getDetails());
            return result;
        } catch (AuthenticationException failure) {
            outcomeService.recordFailure(userId, providerId, failureReason(failure));
            throw failure;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return LocalAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private IdentityProviderJpaEntity resolveActiveLocalProvider() {
        List<IdentityProviderJpaEntity> providers = identityProviderRepository.findAll().stream()
                .filter(provider -> provider.providerType() == ProviderType.LOCAL)
                .filter(provider -> provider.status() == IdentityProviderStatus.ACTIVE)
                .toList();

        if (providers.isEmpty()) {
            throw new BadCredentialsException("LOCAL authentication provider is not available.");
        }
        if (providers.size() > 1) {
            throw new AuthenticationServiceException("Multiple active LOCAL identity providers are configured.");
        }
        return providers.getFirst();
    }

    private static void enforceAccountState(UserJpaEntity user) {
        if (user.status() == UserStatus.LOCKED
                || (user.lockedUntil() != null && Instant.now().isBefore(user.lockedUntil()))) {
            throw new LockedException("Hidra user account is locked.");
        }
        if (user.status() != UserStatus.ACTIVE) {
            throw new DisabledException("Hidra user account is not active.");
        }
    }

    private static String failureReason(AuthenticationException failure) {
        if (failure instanceof LockedException) {
            return "ACCOUNT_LOCKED";
        }
        if (failure instanceof DisabledException) {
            return "ACCOUNT_OR_CREDENTIAL_DISABLED";
        }
        if (failure instanceof AuthenticationServiceException) {
            return "AUTHENTICATION_SERVICE_FAILURE";
        }
        return "BAD_CREDENTIALS";
    }

    private static BadCredentialsException invalidCredentials() {
        return new BadCredentialsException("Invalid LOCAL credentials.");
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
