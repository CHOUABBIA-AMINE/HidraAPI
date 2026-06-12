/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationDomainEventPublisher
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.messaging
 *
 * @Description : In-memory integration domain event publisher.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.messaging;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationDomainEventPublisherPort;
import dz.sh.hidra.modules.integration.domain.event.IntegrationDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory integration domain event publisher.
 */
public class IntegrationDomainEventPublisher implements IntegrationDomainEventPublisherPort {

    private final List<IntegrationDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(IntegrationDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<IntegrationDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
