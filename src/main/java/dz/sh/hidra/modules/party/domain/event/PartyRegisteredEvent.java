/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyRegisteredEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.event
 *
 * @Description : Published when a party is registered.
 *
 */
package dz.sh.hidra.modules.party.domain.event;

import java.time.Instant;

/**
 * Published when a party is registered.
 *
     * @param eventId eventId
 * @param partyId partyId
 * @param occurredAt occurredAt
 */
public record PartyRegisteredEvent(
        String eventId,
    String partyId,
    Instant occurredAt
) implements PartyDomainEvent {

    @Override
    public String eventType() {
        return "PartyRegisteredEvent";
    }
}
