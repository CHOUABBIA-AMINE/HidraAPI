/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyCurrentActorQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Resolves a trusted authenticated actor against current Identity user eligibility.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyCurrentActorContract;
import dz.sh.hidra.modules.identity.application.port.out.UserRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import dz.sh.hidra.modules.identity.domain.value.UserType;
import java.time.Clock;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

/** Does not grant company/facility access or publication authority. */
@Component
public final class TopologyCurrentActorQueryAdapter implements TopologyCurrentActorContract {

    private final UserRepositoryPort users;
    private final JwtDecoder decoder;
    private final Clock clock;

    @Autowired
    public TopologyCurrentActorQueryAdapter(UserRepositoryPort users,
            @Qualifier("hidraJwtDecoder") JwtDecoder decoder) {
        this(users, decoder, Clock.systemUTC());
    }

    TopologyCurrentActorQueryAdapter(UserRepositoryPort users, JwtDecoder decoder, Clock clock) {
        this.users = Objects.requireNonNull(users, "users must not be null");
        this.decoder = Objects.requireNonNull(decoder, "decoder must not be null");
        this.clock = Objects.requireNonNull(clock, "clock must not be null");
    }

    @Override
    public Optional<Actor> currentActor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        String userId = authenticatedUserId(authentication);
        if (userId == null) {
            return Optional.empty();
        }
        Instant now = clock.instant();
        return users.findById(userId)
                .filter(user -> userId.equals(user.id()))
                .filter(user -> user.active() && !user.locked(now) && user.userType() == UserType.HUMAN)
                .map(user -> new Actor(user.id()));
    }

    private String authenticatedUserId(Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken bearer) {
            String token = bearer.getToken().getTokenValue();
            if (token == null || token.isBlank()) {
                return null;
            }
            try {
                String subject = decoder.decode(token).getSubject();
                return subject != null && !subject.isBlank() && subject.equals(subject.trim())
                        ? subject : null;
            } catch (JwtException invalidToken) {
                return null;
            }
        }
        if (authentication instanceof HidraOidcAuthenticationToken oidc) {
            HidraPrincipal principal = oidc.getPrincipal();
            return principal.authenticationType() == ProviderType.OIDC ? principal.userId() : null;
        }
        if (authentication instanceof UsernamePasswordAuthenticationToken password
                && password.getPrincipal() instanceof HidraPrincipal principal) {
            return switch (principal.authenticationType()) {
                case LOCAL, LDAP, ACTIVE_DIRECTORY -> principal.userId();
                default -> null;
            };
        }
        return null;
    }
}
