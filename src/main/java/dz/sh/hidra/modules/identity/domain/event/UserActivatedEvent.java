/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserActivatedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.event
 *
 * @Description : Published when a user is activated.
 *
 */
package dz.sh.hidra.modules.identity.domain.event;

import java.time.Instant;

/**
 * Published when a user is activated.
 *
     * @param eventId eventId
 * @param userId userId
 * @param occurredAt occurredAt
 */
public record UserActivatedEvent(
        String eventId,
    String userId,
    Instant occurredAt
) implements IdentityDomainEvent {

    @Override
    public String eventType() {
        return "UserActivatedEvent";
    }
}
