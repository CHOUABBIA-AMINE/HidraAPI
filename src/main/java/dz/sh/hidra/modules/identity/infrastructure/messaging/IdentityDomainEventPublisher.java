/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityDomainEventPublisher
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.messaging
 *
 * @Description : In-memory/no-op identity domain event publisher.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.messaging;

import dz.sh.hidra.modules.identity.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.identity.domain.event.IdentityDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory domain event publisher for source alignment.
 */
public class IdentityDomainEventPublisher implements DomainEventPublisherPort {

    private final List<IdentityDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(IdentityDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<IdentityDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
