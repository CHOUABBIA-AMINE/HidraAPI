/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentDomainEventPublisher
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.messaging
 *
 * @Description : In-memory incident domain event publisher.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.messaging;

import dz.sh.hidra.modules.incident.application.port.out.IncidentDomainEventPublisherPort;
import dz.sh.hidra.modules.incident.domain.event.IncidentDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory incident domain event publisher.
 */
public class IncidentDomainEventPublisher implements IncidentDomainEventPublisherPort {

    private final List<IncidentDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(IncidentDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<IncidentDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
