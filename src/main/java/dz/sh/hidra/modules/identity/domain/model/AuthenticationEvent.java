/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthenticationEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Records authentication outcomes for security traceability.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;

/**
 * Records authentication outcomes for security traceability.
 *
     * @param id id
 * @param userId userId
 * @param identityProviderId identityProviderId
 * @param externalIdentityId externalIdentityId
 * @param externalSubject externalSubject
 * @param eventType eventType
 * @param protocol protocol
 * @param clientIp clientIp
 * @param userAgent userAgent
 * @param failureReason failureReason
 * @param riskScore riskScore
 * @param occurredAt occurredAt
 * @param correlationId correlationId
 */
public record AuthenticationEvent(
        String id,
    String userId,
    String identityProviderId,
    String externalIdentityId,
    String externalSubject,
    AuthenticationEventType eventType,
    AuthenticationProtocol protocol,
    String clientIp,
    String userAgent,
    String failureReason,
    java.math.BigDecimal riskScore,
    Instant occurredAt,
    String correlationId
) {

    public AuthenticationEvent {
    id = normalize(id);
    userId = normalize(userId);
    identityProviderId = normalize(identityProviderId);
    externalIdentityId = normalize(externalIdentityId);
    externalSubject = normalize(externalSubject);
    clientIp = normalize(clientIp);
    userAgent = normalize(userAgent);
    failureReason = normalize(failureReason);
    correlationId = normalize(correlationId);
    }



    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
