/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCaseOpenedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.event
 *
 * @Description : Published when an HSE case is opened.
 *
 */
package dz.sh.hidra.modules.hse.domain.event;

import java.time.Instant;

/**
 * Published when an HSE case is opened.
 */
public record HseCaseOpenedEvent(
        String eventId,
    String hseCaseId,
    String caseNumber,
    Instant occurredAt
) implements HseDomainEvent {

    @Override
    public String eventType() {
        return "HseCaseOpenedEvent";
    }
}
