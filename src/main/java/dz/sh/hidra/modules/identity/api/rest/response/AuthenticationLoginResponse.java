/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthenticationLoginResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.response
 *
 * @Description : Returns the Hidra direct-login session, bearer token, and normalized principal metadata.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.response;

import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import java.time.Instant;
import java.util.Set;

/**
 * Safe direct-login response for authenticated Hidra users.
 */
public record AuthenticationLoginResponse(
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

    public AuthenticationLoginResponse {
        roles = roles == null ? Set.of() : Set.copyOf(roles);
        permissions = permissions == null ? Set.of() : Set.copyOf(permissions);
    }
}
