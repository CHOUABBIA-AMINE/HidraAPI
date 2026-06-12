/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyPeriodOpenedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.event
 *
 * @Description : Published when a custody measurement period is opened.
 *
 */
package dz.sh.hidra.modules.custody.domain.event;

import java.time.Instant;

/**
 * Published when a custody measurement period is opened.
 */
public record CustodyPeriodOpenedEvent(
        String eventId,
    String measurementPeriodId,
    Instant occurredAt
) implements CustodyDomainEvent {

    @Override
    public String eventType() {
        return "CustodyPeriodOpenedEvent";
    }
}
