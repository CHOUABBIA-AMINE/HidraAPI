/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CurrentSecurityContext
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security.context
 *
 * @Description : Safely reads the current Spring Security authentication context.
 *
 */
package dz.sh.hidra.platform.security.context;

import dz.sh.hidra.kernel.domain.value.ActorId;
import java.util.Optional;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentSecurityContext {

    public Optional<Authentication> currentAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(securityContext -> securityContext.getAuthentication());
    }

    public Optional<AuthenticatedPrincipal> currentPrincipal() {
        return currentAuthentication()
                .filter(CurrentSecurityContext::isUsableAuthentication)
                .map(CurrentSecurityContext::authenticatedPrincipal);
    }

    public boolean isAuthenticated() {
        return currentAuthentication().filter(CurrentSecurityContext::isUsableAuthentication).isPresent();
    }

    public void clear() {
        SecurityContextHolder.clearContext();
    }

    private static boolean isUsableAuthentication(Authentication authentication) {
        return authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }

    private static AuthenticatedPrincipal authenticatedPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof AuthenticatedPrincipal authenticatedPrincipal) {
            return authenticatedPrincipal;
        }
        String principalName = principalName(authentication);
        return AuthenticatedPrincipal.authenticated(ActorId.of(principalName), principalName);
    }

    private static String principalName(Authentication authentication) {
        String name = authentication.getName();
        if (name == null || name.isBlank()) {
            return CurrentActorResolver.SYSTEM_ACTOR_ID.value();
        }
        return name.trim();
    }
}
