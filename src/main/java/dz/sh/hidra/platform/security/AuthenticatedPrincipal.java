/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthenticatedPrincipal
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security
 *
 * @Description : Represents the current technical authenticated principal.
 *
 */
package dz.sh.hidra.platform.security;

import dz.sh.hidra.kernel.domain.value.ActorId;

import java.util.Objects;

/**
 * Lightweight technical principal resolved from security context.
 *
 * @param actorId technical actor identifier
 * @param principalName security principal name
 * @param authenticated whether the principal is authenticated
 */
public record AuthenticatedPrincipal(
        ActorId actorId,
        String principalName,
        boolean authenticated
) {

    public AuthenticatedPrincipal {
        Objects.requireNonNull(actorId, "Actor ID must not be null.");
        principalName = normalizeOrDefault(principalName, actorId.value());
    }

    public static AuthenticatedPrincipal anonymous(ActorId anonymousActorId) {
        return new AuthenticatedPrincipal(anonymousActorId, "anonymous", false);
    }

    private static String normalizeOrDefault(String value, String defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value.trim();
    }
}
