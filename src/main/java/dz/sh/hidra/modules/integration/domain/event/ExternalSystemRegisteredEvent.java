/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalSystemRegisteredEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.event
 *
 * @Description : Published when an external system is registered.
 *
 */
package dz.sh.hidra.modules.integration.domain.event;

import java.time.Instant;

/**
 * Published when an external system is registered.
 */
public record ExternalSystemRegisteredEvent(
        String eventId,
    String externalSystemId,
    Instant occurredAt
) implements IntegrationDomainEvent {

    @Override
    public String eventType() {
        return "ExternalSystemRegisteredEvent";
    }
}
