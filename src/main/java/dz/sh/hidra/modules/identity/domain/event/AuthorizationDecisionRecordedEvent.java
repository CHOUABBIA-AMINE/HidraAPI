/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationDecisionRecordedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.event
 *
 * @Description : Published when an authorization decision is recorded.
 *
 */
package dz.sh.hidra.modules.identity.domain.event;

import java.time.Instant;

/**
 * Published when an authorization decision is recorded.
 *
     * @param eventId eventId
 * @param decisionId decisionId
 * @param userId userId
 * @param permissionCode permissionCode
 * @param occurredAt occurredAt
 */
public record AuthorizationDecisionRecordedEvent(
        String eventId,
    String decisionId,
    String userId,
    String permissionCode,
    Instant occurredAt
) implements IdentityDomainEvent {

    @Override
    public String eventType() {
        return "AuthorizationDecisionRecordedEvent";
    }
}
