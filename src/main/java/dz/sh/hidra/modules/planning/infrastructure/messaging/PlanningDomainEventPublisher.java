/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningDomainEventPublisher
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.messaging
 *
 * @Description : In-memory planning domain event publisher.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.messaging;

import dz.sh.hidra.modules.planning.application.port.out.PlanningDomainEventPublisherPort;
import dz.sh.hidra.modules.planning.domain.event.PlanningDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory planning domain event publisher.
 */
public class PlanningDomainEventPublisher implements PlanningDomainEventPublisherPort {

    private final List<PlanningDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(PlanningDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<PlanningDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
