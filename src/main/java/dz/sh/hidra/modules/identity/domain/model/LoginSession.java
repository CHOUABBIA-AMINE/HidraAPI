/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LoginSession
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
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
    String correlationId,
    AuthenticationProtocol sessionType,
    Instant endedAt
) {

    public LoginSession {
        if (sessionType == null) {
            throw new InvalidIdentityValueException("LoginSession sessionType is required.");
        }
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIdentityValueException("LoginSession id must not be blank.");
        }
        // HRA-051 required: userId
        if (userId == null || userId.isBlank()) {
            throw new InvalidIdentityValueException("LoginSession user id must not be blank.");
        }
        // HRA-051 required: startedAt
        if (startedAt == null) {
            throw new InvalidIdentityValueException("LoginSession started at must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIdentityValueException("LoginSession status must not be null.");
        }

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
