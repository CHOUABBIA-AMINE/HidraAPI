/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTransferTicketCreatedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.event
 *
 * @Description : Published when a custody transfer ticket is created.
 *
 */
package dz.sh.hidra.modules.custody.domain.event;

import java.time.Instant;

/**
 * Published when a custody transfer ticket is created.
 */
public record CustodyTransferTicketCreatedEvent(
        String eventId,
    String ticketId,
    Instant occurredAt
) implements CustodyDomainEvent {

    @Override
    public String eventType() {
        return "CustodyTransferTicketCreatedEvent";
    }
}
