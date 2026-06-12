/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationChangeRequestSubmittedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.event
 *
 * @Description : Published when a configuration change request is submitted.
 *
 */
package dz.sh.hidra.modules.configuration.domain.event;

import java.time.Instant;

/**
 * Published when a configuration change request is submitted.
 */
public record ConfigurationChangeRequestSubmittedEvent(
        String eventId,
    String changeRequestId,
    Instant occurredAt
) implements ConfigurationDomainEvent {

    @Override
    public String eventType() {
        return "ConfigurationChangeRequestSubmittedEvent";
    }
}
