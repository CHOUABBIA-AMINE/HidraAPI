/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthenticationEventRecordedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.event
 *
 * @Description : Published when an authentication event is recorded.
 *
 */
package dz.sh.hidra.modules.identity.domain.event;

import java.time.Instant;

/**
 * Published when an authentication event is recorded.
 *
     * @param eventId eventId
 * @param authenticationEventId authenticationEventId
 * @param userId userId
 * @param occurredAt occurredAt
 */
public record AuthenticationEventRecordedEvent(
        String eventId,
    String authenticationEventId,
    String userId,
    Instant occurredAt
) implements IdentityDomainEvent {

    @Override
    public String eventType() {
        return "AuthenticationEventRecordedEvent";
    }
}
