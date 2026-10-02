/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthenticationEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIdentityValueException("AuthenticationEvent id must not be blank.");
        }
        // HRA-051 required: eventType
        if (eventType == null) {
            throw new InvalidIdentityValueException("AuthenticationEvent event type must not be null.");
        }
        // HRA-051 required: protocol
        if (protocol == null) {
            throw new InvalidIdentityValueException("AuthenticationEvent protocol must not be null.");
        }
        // HRA-051 required: occurredAt
        if (occurredAt == null) {
            throw new InvalidIdentityValueException("AuthenticationEvent occurred at must not be null.");
        }

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
