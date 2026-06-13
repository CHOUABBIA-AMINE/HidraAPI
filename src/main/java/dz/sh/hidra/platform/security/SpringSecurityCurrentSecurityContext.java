/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringSecurityCurrentSecurityContext
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security
 *
 * @Description : Adapts Spring SecurityContextHolder to the Hidra current security context.
 *
 */
package dz.sh.hidra.platform.security;

import dz.sh.hidra.kernel.domain.value.ActorId;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Spring Security adapter for the framework-neutral current security context port.
 */
@Component
public final class SpringSecurityCurrentSecurityContext implements CurrentSecurityContext {

    @Override
    public Optional<AuthenticatedPrincipal> currentPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        String principalName = authentication.getName();
        if (principalName == null || principalName.isBlank()) {
            return Optional.empty();
        }
        return Optional.of(new AuthenticatedPrincipal(
                ActorId.of(principalName.trim()),
                principalName.trim(),
                authentication.isAuthenticated()
        ));
    }

    @Override
    public void clear() {
        SecurityContextHolder.clearContext();
    }
}
