/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationDomainEventPublisher
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.messaging
 *
 * @Description : In-memory configuration domain event publisher.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.messaging;

import dz.sh.hidra.modules.configuration.application.port.out.ConfigurationDomainEventPublisherPort;
import dz.sh.hidra.modules.configuration.domain.event.ConfigurationDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory configuration domain event publisher.
 */
public class ConfigurationDomainEventPublisher implements ConfigurationDomainEventPublisherPort {

    private final List<ConfigurationDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(ConfigurationDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<ConfigurationDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
