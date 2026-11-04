/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FoundationIdentityAuthorizationAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Verifies live native bearer and current global administrator authority.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.port.out.FoundationIdentityAuthorizationPort;
import dz.sh.hidra.modules.identity.application.port.out.OperationAuthenticationBindingRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.AuthenticationSecurityStateQueryPort;
import java.time.Instant;
import java.util.Objects;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public final class FoundationIdentityAuthorizationAdapter implements FoundationIdentityAuthorizationPort {
    private final OperationAuthenticationBindingRepositoryPort bindings;
    private final AuthenticationSecurityStateQueryPort states;
    private final IdentityAdministratorGrantService administrators;

    public FoundationIdentityAuthorizationAdapter(OperationAuthenticationBindingRepositoryPort bindings,
            AuthenticationSecurityStateQueryPort states, IdentityAdministratorGrantService administrators) {
        this.bindings = Objects.requireNonNull(bindings);
        this.states = Objects.requireNonNull(states);
        this.administrators = Objects.requireNonNull(administrators);
    }

    @Override
    public String authenticatedActor() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof JwtAuthenticationToken token) || !auth.isAuthenticated())
            throw new AccessDeniedException("Current native authentication required.");
        var jwt = token.getToken();
        String issuer;
        try {
            issuer = jwt.getIssuer() == null ? null : jwt.getIssuer().toString();
        } catch (IllegalArgumentException malformedIssuer) {
            throw new AccessDeniedException("Current native authentication required.");
        }
        String jti = jwt.getId();
        if (issuer == null || jti == null || token.getName() == null)
            throw new AccessDeniedException("Current native authentication required.");
        try {
            var binding = bindings.findByNativeIssuerAndJti(issuer, jti)
                    .orElseThrow(() -> new AccessDeniedException("Current native authentication required."));
            if (!token.getName().equals(binding.userId()) || !issuer.equals(binding.nativeIssuer())
                    || !states.current(binding, Instant.now()))
                throw new AccessDeniedException("Current native authentication required.");
            return binding.userId();
        } catch (AccessDeniedException failure) {
            throw failure;
        } catch (RuntimeException unavailable) {
            throw new IllegalStateException("Current Identity authorization state unavailable.");
        }
    }

    @Override
    public String administratorActor() {
        String actor = authenticatedActor();
        if (!currentAdministrator(actor)) throw new AccessDeniedException("Current administrator grant required.");
        return actor;
    }

    @Override
    public boolean currentAdministrator(String actorId) {
        return actorId != null && administrators.hasActiveGlobalAdministratorGrant(actorId);
    }
}
