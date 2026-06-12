/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LoginSession
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Records logical login session metadata without storing tokens.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Records logical login session metadata without storing tokens.
 *
     * @param id id
 * @param userId userId
 * @param identityProviderId identityProviderId
 * @param externalIdentityId externalIdentityId
 * @param startedAt startedAt
 * @param lastSeenAt lastSeenAt
 * @param expiresAt expiresAt
 * @param clientIp clientIp
 * @param userAgent userAgent
 * @param status status
 * @param correlationId correlationId
 */
public record LoginSession(
        String id,
    String userId,
    String identityProviderId,
    String externalIdentityId,
    Instant startedAt,
    Instant lastSeenAt,
    Instant expiresAt,
    String clientIp,
    String userAgent,
    LoginSessionStatus status,
    String correlationId
) {

    public LoginSession {
    id = normalize(id);
    userId = normalize(userId);
    identityProviderId = normalize(identityProviderId);
    externalIdentityId = normalize(externalIdentityId);
    clientIp = normalize(clientIp);
    userAgent = normalize(userAgent);
    correlationId = normalize(correlationId);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
