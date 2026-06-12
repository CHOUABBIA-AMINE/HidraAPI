/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityDomainEventPublisher
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.messaging
 *
 * @Description : In-memory integrity domain event publisher.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.messaging;

import dz.sh.hidra.modules.integrity.application.port.out.IntegrityDomainEventPublisherPort;
import dz.sh.hidra.modules.integrity.domain.event.IntegrityDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory integrity domain event publisher.
 */
public class IntegrityDomainEventPublisher implements IntegrityDomainEventPublisherPort {

    private final List<IntegrityDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(IntegrityDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<IntegrityDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
