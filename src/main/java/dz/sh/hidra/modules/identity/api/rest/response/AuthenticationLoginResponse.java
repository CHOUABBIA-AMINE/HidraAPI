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
        Set<String> permissions,
        String refreshToken,
        Instant refreshExpiresAt
) {

    /** Existing 13-field API construction remains source-compatible. */
    public AuthenticationLoginResponse(String sessionId, String accessToken, String tokenType,
            String jti, Instant issuedAt, Instant expiresAt, String userId,
            String username, String displayName, ProviderType authenticationType,
            String identityProviderId, Set<String> roles, Set<String> permissions) {
        this(sessionId,accessToken,tokenType,jti,issuedAt,expiresAt,userId,username,
                displayName,authenticationType,identityProviderId,roles,permissions,null,null);
    }

    @Override public String toString() {
        return "AuthenticationLoginResponse[tokens=<redacted>, principal=<redacted>]";
    }

    public AuthenticationLoginResponse {
        roles = roles == null ? Set.of() : Set.copyOf(roles);
        permissions = permissions == null ? Set.of() : Set.copyOf(permissions);
        if ((refreshToken == null) != (refreshExpiresAt == null))
            throw new IllegalArgumentException("Refresh credentials must have a deadline.");
        if (refreshToken != null && authenticationType != ProviderType.LOCAL)
            throw new IllegalArgumentException("External refresh credentials are unsupported.");
    }
}
