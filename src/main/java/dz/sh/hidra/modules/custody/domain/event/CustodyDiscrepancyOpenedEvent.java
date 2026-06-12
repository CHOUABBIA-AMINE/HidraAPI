/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyDiscrepancyOpenedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.event
 *
 * @Description : Published when a custody discrepancy is opened.
 *
 */
package dz.sh.hidra.modules.custody.domain.event;

import java.time.Instant;

/**
 * Published when a custody discrepancy is opened.
 */
public record CustodyDiscrepancyOpenedEvent(
        String eventId,
    String discrepancyId,
    Instant occurredAt
) implements CustodyDomainEvent {

    @Override
    public String eventType() {
        return "CustodyDiscrepancyOpenedEvent";
    }
}
