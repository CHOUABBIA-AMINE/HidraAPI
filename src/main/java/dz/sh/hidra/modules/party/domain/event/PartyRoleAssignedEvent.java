/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyRoleAssignedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.event
 *
 * @Description : Published when a party role is assigned.
 *
 */
package dz.sh.hidra.modules.party.domain.event;

import java.time.Instant;

/**
 * Published when a party role is assigned.
 *
     * @param eventId eventId
 * @param partyId partyId
 * @param roleAssignmentId roleAssignmentId
 * @param occurredAt occurredAt
 */
public record PartyRoleAssignedEvent(
        String eventId,
    String partyId,
    String roleAssignmentId,
    Instant occurredAt
) implements PartyDomainEvent {

    @Override
    public String eventType() {
        return "PartyRoleAssignedEvent";
    }
}
