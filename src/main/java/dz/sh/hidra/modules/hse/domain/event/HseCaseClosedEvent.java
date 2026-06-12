/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCaseClosedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.event
 *
 * @Description : Published when an HSE case is closed.
 *
 */
package dz.sh.hidra.modules.hse.domain.event;

import java.time.Instant;

/**
 * Published when an HSE case is closed.
 */
public record HseCaseClosedEvent(
        String eventId,
    String hseCaseId,
    String closureId,
    Instant occurredAt
) implements HseDomainEvent {

    @Override
    public String eventType() {
        return "HseCaseClosedEvent";
    }
}
