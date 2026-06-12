/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyStatusChangedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.event
 *
 * @Description : Published when party status changes.
 *
 */
package dz.sh.hidra.modules.party.domain.event;

import java.time.Instant;

/**
 * Published when party status changes.
 *
     * @param eventId eventId
 * @param partyId partyId
 * @param oldStatus oldStatus
 * @param newStatus newStatus
 * @param occurredAt occurredAt
 */
public record PartyStatusChangedEvent(
        String eventId,
    String partyId,
    String oldStatus,
    String newStatus,
    Instant occurredAt
) implements PartyDomainEvent {

    @Override
    public String eventType() {
        return "PartyStatusChangedEvent";
    }
}
