/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityOidcJwtAuthenticationConverter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Normalizes a validated external OIDC JWT into the Hidra identity and authorization model.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
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
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * Converts only already-validated JWTs, resolving issuer and subject through Hidra Identity.
 */
@Component("identityOidcJwtAuthenticationConverter")
public final class IdentityOidcJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final IdentityProviderJpaRepository identityProviderRepository;
    private final ExternalIdentityJpaRepository externalIdentityRepository;
    private final UserJpaRepository userRepository;
    private final IdentityAdministrationQueryUseCase queryUseCase;

    public IdentityOidcJwtAuthenticationConverter(
            IdentityProviderJpaRepository identityProviderRepository,
            ExternalIdentityJpaRepository externalIdentityRepository,
            UserJpaRepository userRepository,
            IdentityAdministrationQueryUseCase queryUseCase
    ) {
        this.identityProviderRepository = Objects.requireNonNull(identityProviderRepository);
        this.externalIdentityRepository = Objects.requireNonNull(externalIdentityRepository);
        this.userRepository = Objects.requireNonNull(userRepository);
        this.queryUseCase = Objects.requireNonNull(queryUseCase);
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Objects.requireNonNull(jwt, "Validated JWT must not be null.");
        String issuer = jwt.getIssuer() == null ? null : jwt.getIssuer().toString();
        String subject = normalize(jwt.getSubject());
        if (issuer == null || subject == null) {
            throw new BadCredentialsException("OIDC identity is missing issuer or subject.");
        }

        IdentityProviderJpaEntity provider = identityProviderRepository
                .findByProviderTypeAndIssuerUri(ProviderType.OIDC, issuer)
                .filter(candidate -> candidate.status() == IdentityProviderStatus.ACTIVE)
                .orElseThrow(() -> new BadCredentialsException("OIDC identity provider is not available."));

        ExternalIdentityJpaEntity externalIdentity = externalIdentityRepository
                .findByIdentityProviderIdAndExternalSubject(provider.id(), subject)
                .filter(candidate -> candidate.status() == ExternalIdentityStatus.LINKED)
                .orElseThrow(() -> new BadCredentialsException("OIDC identity is not linked to a Hidra user."));

        UserJpaEntity user = userRepository.findById(externalIdentity.userId())
                .orElseThrow(() -> new BadCredentialsException("OIDC identity is not linked to a Hidra user."));

        if (user.status() == UserStatus.LOCKED
                || (user.lockedUntil() != null && Instant.now().isBefore(user.lockedUntil()))) {
            throw new LockedException("Hidra user account is locked.");
        }
        if (user.status() != UserStatus.ACTIVE) {
            throw new DisabledException("Hidra user account is not active.");
        }

        IdentityAdministrationQueryUseCase.PrincipalView authorization =
                queryUseCase.principal(user.id(), List.of());

        HidraPrincipal principal = new HidraPrincipal(
                user.id(),
                user.username(),
                user.displayName(),
                ProviderType.OIDC,
                provider.id(),
                Set.of(),
                Set.copyOf(authorization.effectivePermissions())
        );
        return new HidraOidcAuthenticationToken(principal);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
