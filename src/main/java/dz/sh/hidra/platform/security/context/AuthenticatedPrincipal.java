/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthenticatedPrincipal
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security.context
 *
 * @Description : Lightweight technical principal resolved from the Spring Security context.
 *
 */
package dz.sh.hidra.platform.security.context;

import dz.sh.hidra.kernel.domain.value.ActorId;

public record AuthenticatedPrincipal(ActorId actorId, String principalName, boolean authenticated) {

    public AuthenticatedPrincipal {
        if (actorId == null) {
            throw new IllegalArgumentException("ActorId must not be null.");
        }
        principalName = normalize(principalName, actorId.value());
    }

    public static AuthenticatedPrincipal authenticated(ActorId actorId, String principalName) {
        return new AuthenticatedPrincipal(actorId, principalName, true);
    }

    public static AuthenticatedPrincipal anonymous() {
        return new AuthenticatedPrincipal(CurrentActorResolver.ANONYMOUS_ACTOR_ID, "anonymous", false);
    }

    private static String normalize(String value, String defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value.trim();
    }
}
