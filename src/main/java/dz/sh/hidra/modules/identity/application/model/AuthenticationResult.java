/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthenticationResult
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.model
 *
 * @Description : Application-owned successful-authentication result for REST and other inbound boundaries.
 *
 */
package dz.sh.hidra.modules.identity.application.model;

import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import java.time.Instant;
import java.util.Set;

/**
 * Flattened provider-neutral authentication result that does not expose domain aggregates.
 */
public record AuthenticationResult(
        String sessionId,
        String accessToken,
        String tokenType,
        String jti,
        Instant issuedAt,
        Instant expiresAt,
        String userId,
        String username,
        String displayName,
        ProviderType authenticationType,
        String identityProviderId,
        Set<String> roles,
        Set<String> permissions
) {

    public AuthenticationResult {
        roles = roles == null ? Set.of() : Set.copyOf(roles);
        permissions = permissions == null ? Set.of() : Set.copyOf(permissions);
    }
}
