/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LdapAuthenticationProvider
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Authenticates LDAP/Active Directory requests and normalizes linked identities to a Hidra principal.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.model.VerifiedDirectoryIdentity;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
import dz.sh.hidra.modules.identity.application.port.out.LdapCredentialVerificationPort;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
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
import dz.sh.hidra.platform.security.LdapAuthenticationToken;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

/**
 * Database-linked LDAP/Active Directory authentication strategy.
 */
@Component
@ConditionalOnProperty(prefix = "hidra.platform.security.ldap", name = "enabled", havingValue = "true")
public final class LdapAuthenticationProvider implements AuthenticationProvider {

    private final LdapCredentialVerificationPort credentialVerificationPort;
    private final IdentityProviderJpaRepository identityProviderRepository;
    private final ExternalIdentityJpaRepository externalIdentityRepository;
    private final UserJpaRepository userRepository;
    private final IdentityAdministrationQueryUseCase queryUseCase;

    public LdapAuthenticationProvider(
            LdapCredentialVerificationPort credentialVerificationPort,
            IdentityProviderJpaRepository identityProviderRepository,
            ExternalIdentityJpaRepository externalIdentityRepository,
            UserJpaRepository userRepository,
            IdentityAdministrationQueryUseCase queryUseCase
    ) {
        this.credentialVerificationPort = Objects.requireNonNull(credentialVerificationPort);
        this.identityProviderRepository = Objects.requireNonNull(identityProviderRepository);
        this.externalIdentityRepository = Objects.requireNonNull(externalIdentityRepository);
        this.userRepository = Objects.requireNonNull(userRepository);
        this.queryUseCase = Objects.requireNonNull(queryUseCase);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof LdapAuthenticationToken ldapRequest)) {
            return null;
        }

        String principal = normalize(ldapRequest.getPrincipal() == null ? null : ldapRequest.getPrincipal().toString());
        String credentials = ldapRequest.getCredentials() instanceof String value ? value : null;
        if (principal == null || credentials == null || credentials.isEmpty()) {
            throw invalidCredentials();
        }

        IdentityProviderJpaEntity directoryProvider = resolveActiveDirectoryProvider();
        VerifiedDirectoryIdentity verifiedIdentity = credentialVerificationPort.verify(principal, credentials)
                .orElseThrow(LdapAuthenticationProvider::invalidCredentials);

        ExternalIdentityJpaEntity externalIdentity = externalIdentityRepository
                .findByIdentityProviderIdAndExternalSubject(directoryProvider.id(), verifiedIdentity.subject())
                .orElseThrow(() -> new BadCredentialsException("Verified directory identity is not linked to Hidra."));
        if (externalIdentity.status() != ExternalIdentityStatus.LINKED) {
            throw new DisabledException("External directory identity is not linked and active.");
        }

        UserJpaEntity user = userRepository.findById(externalIdentity.userId())
                .orElseThrow(() -> new AuthenticationServiceException("Linked Hidra user no longer exists."));
        enforceAccountState(user);

        IdentityAdministrationQueryUseCase.PrincipalView authorization =
                queryUseCase.principal(user.id(), List.of());

        HidraPrincipal hidraPrincipal = new HidraPrincipal(
                user.id(),
                user.username(),
                user.displayName(),
                directoryProvider.providerType(),
                directoryProvider.id(),
                Set.of(),
                Set.copyOf(authorization.effectivePermissions())
        );

        UsernamePasswordAuthenticationToken result = UsernamePasswordAuthenticationToken.authenticated(
                hidraPrincipal,
                null,
                AuthorityUtils.NO_AUTHORITIES
        );
        result.setDetails(ldapRequest.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return LdapAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private IdentityProviderJpaEntity resolveActiveDirectoryProvider() {
        List<IdentityProviderJpaEntity> providers = identityProviderRepository.findAll().stream()
                .filter(provider -> provider.status() == IdentityProviderStatus.ACTIVE)
                .filter(provider -> provider.providerType() == ProviderType.LDAP
                        || provider.providerType() == ProviderType.ACTIVE_DIRECTORY)
                .toList();

        if (providers.isEmpty()) {
            throw new BadCredentialsException("LDAP/Active Directory authentication provider is not available.");
        }
        if (providers.size() > 1) {
            throw new AuthenticationServiceException(
                    "Multiple active LDAP/Active Directory identity providers are configured."
            );
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

    private static BadCredentialsException invalidCredentials() {
        return new BadCredentialsException("Invalid LDAP/Active Directory credentials.");
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
