/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityOidcJwtAuthenticationConverter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
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

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Converts only already-validated JWTs, resolving issuer and subject through Hidra Identity.
 */
@Component("identityOidcJwtAuthenticationConverter")
public final class IdentityOidcJwtAuthenticationConverter
        implements Converter<Jwt, AbstractAuthenticationToken> {

    private final dz.sh.hidra.modules.identity.application.port.out
                    .AuthenticationSecurityStateQueryPort
            states;
    private final TrustedAuthenticationVerificationRegistry verification;
    private final org.springframework.beans.factory.ObjectProvider<
                    org.springframework.security.oauth2.jwt.JwtDecoder>
            decoder;
    private final int maxTokenBytes;
    private final IdentityProviderJpaRepository identityProviderRepository;
    private final ExternalIdentityJpaRepository externalIdentityRepository;
    private final UserJpaRepository userRepository;
    private final IdentityAdministrationQueryUseCase queryUseCase;
    private final SpringAuthorizationContextAdapter authorizationContext;

    /** Legacy constructor carries only original assertion evidence; it cannot mint a bound receipt. */
    public IdentityOidcJwtAuthenticationConverter(
            IdentityProviderJpaRepository providers,
            ExternalIdentityJpaRepository links,
            UserJpaRepository users,
            IdentityAdministrationQueryUseCase query,
            SpringAuthorizationContextAdapter assertions) {
        this(providers, links, users, query, assertions, null, null, null, 65536);
    }

    @org.springframework.beans.factory.annotation.Autowired
    public IdentityOidcJwtAuthenticationConverter(
            IdentityProviderJpaRepository identityProviderRepository,
            ExternalIdentityJpaRepository externalIdentityRepository,
            UserJpaRepository userRepository,
            IdentityAdministrationQueryUseCase queryUseCase,
            SpringAuthorizationContextAdapter authorizationContext,
            dz.sh.hidra.modules.identity.application.port.out.AuthenticationSecurityStateQueryPort
                    states,
            TrustedAuthenticationVerificationRegistry verification,
            @org.springframework.beans.factory.annotation.Qualifier("externalOidcJwtDecoder")
                    org.springframework.beans.factory.ObjectProvider<
                                    org.springframework.security.oauth2.jwt.JwtDecoder>
                            decoder,
            @org.springframework.beans.factory.annotation.Value(
                            "${hidra.identity.bound-authentication.max-token-bytes:65536}")
                    int maxTokenBytes) {
        this.states = states;
        this.verification = verification;
        this.decoder = decoder;
        if (maxTokenBytes <= 0)
            throw new IllegalArgumentException("Invalid source token byte envelope.");
        this.maxTokenBytes = maxTokenBytes;
        this.identityProviderRepository = Objects.requireNonNull(identityProviderRepository);
        this.externalIdentityRepository = Objects.requireNonNull(externalIdentityRepository);
        this.userRepository = Objects.requireNonNull(userRepository);
        this.queryUseCase = Objects.requireNonNull(queryUseCase);
        this.authorizationContext = Objects.requireNonNull(authorizationContext);
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Objects.requireNonNull(jwt, "Validated JWT must not be null.");
        Object rawIssuer = jwt.getClaims().get("iss"), rawSubject = jwt.getClaims().get("sub");
        String issuer = rawIssuer instanceof String text ? text : null;
        String subject = rawSubject instanceof String text ? text : null;
        if (issuer == null || issuer.isBlank() || subject == null || subject.isBlank()) {
            throw new BadCredentialsException("OIDC identity is missing issuer or subject.");
        }

        IdentityProviderJpaEntity provider =
                identityProviderRepository
                        .findByProviderTypeAndIssuerUri(ProviderType.OIDC, issuer)
                        .filter(candidate -> candidate.status() == IdentityProviderStatus.ACTIVE)
                        .orElseThrow(
                                () ->
                                        new BadCredentialsException(
                                                "OIDC identity provider is not available."));

        dz.sh.hidra.modules.identity.application.model.AuthenticationVerificationSnapshot snapshot =
                null;
        Jwt verified = jwt;
        if (states != null) {
            var capturedProvider = states.provider(provider.id(), "OIDC");
            if (!issuer.equals(capturedProvider.issuer())
                    || !(jwt.getClaims().get("exp") instanceof Instant deadline))
                throw new BadCredentialsException("OIDC source identity is invalid.");
            snapshot = states.external(capturedProvider, subject, issuer, deadline);
            String original = jwt.getTokenValue();
            if (original == null
                    || original.isBlank()
                    || original.getBytes(java.nio.charset.StandardCharsets.UTF_8).length
                            > maxTokenBytes)
                throw new BadCredentialsException("OIDC source token exceeds its byte envelope.");
            try {
                verified = decoder.getObject().decode(original);
            } catch (RuntimeException failure) {
                throw new BadCredentialsException("OIDC source validation failed.");
            }
            if (!Objects.equals(verified.getClaims().get("iss"), issuer)
                    || !Objects.equals(verified.getClaims().get("sub"), subject)
                    || !Objects.equals(verified.getClaims().get("exp"), deadline)
                    || !Instant.now().isBefore(deadline))
                throw new BadCredentialsException("OIDC source identity or deadline changed.");
        }
        ExternalIdentityJpaEntity externalIdentity =
                externalIdentityRepository
                        .findByIdentityProviderIdAndExternalSubject(provider.id(), subject)
                        .filter(candidate -> candidate.status() == ExternalIdentityStatus.LINKED)
                        .orElseThrow(
                                () ->
                                        new BadCredentialsException(
                                                "OIDC identity is not linked to a Hidra user."));

        UserJpaEntity user =
                userRepository
                        .findById(externalIdentity.userId())
                        .orElseThrow(
                                () ->
                                        new BadCredentialsException(
                                                "OIDC identity is not linked to a Hidra user."));

        if (user.status() == UserStatus.LOCKED
                || (user.lockedUntil() != null && Instant.now().isBefore(user.lockedUntil()))) {
            throw new LockedException("Hidra user account is locked.");
        }
        if (user.status() != UserStatus.ACTIVE) {
            throw new DisabledException("Hidra user account is not active.");
        }

        if (snapshot != null
                && (!snapshot.userId().equals(user.id())
                        || !snapshot.externalIdentityId().equals(externalIdentity.id())))
            throw new BadCredentialsException("OIDC linked owner changed.");
        IdentityAdministrationQueryUseCase.PrincipalView authorization =
                queryUseCase.principal(user.id(), List.of());

        HidraPrincipal principal =
                new HidraPrincipal(
                        user.id(),
                        user.username(),
                        user.displayName(),
                        ProviderType.OIDC,
                        provider.id(),
                        Set.of(),
                        Set.copyOf(authorization.effectivePermissions()),
                        externalIdentity.id());
        if (verification == null)
            return new HidraOidcAuthenticationToken(
                    principal, authorizationContext.capture(principal, verified));
        return new HidraOidcAuthenticationToken(
                principal,
                authorizationContext.capture(principal, verified),
                verification.verifiedOidc(snapshot, principal));
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
