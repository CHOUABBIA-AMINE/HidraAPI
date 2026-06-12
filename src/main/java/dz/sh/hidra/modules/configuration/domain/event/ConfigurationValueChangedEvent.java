/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationValueChangedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.event
 *
 * @Description : Published when a configuration value changes.
 *
 */
package dz.sh.hidra.modules.configuration.domain.event;

import java.time.Instant;

/**
 * Published when a configuration value changes.
 */
public record ConfigurationValueChangedEvent(
        String eventId,
    String configurationValueId,
    String definitionId,
    Instant occurredAt
) implements ConfigurationDomainEvent {

    @Override
    public String eventType() {
        return "ConfigurationValueChangedEvent";
    }
}
