/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationDomainEventPublisher
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.messaging
 *
 * @Description : In-memory simulation domain event publisher.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.messaging;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationDomainEventPublisherPort;
import dz.sh.hidra.modules.simulation.domain.event.SimulationDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory simulation domain event publisher.
 */
public class SimulationDomainEventPublisher implements SimulationDomainEventPublisherPort {

    private final List<SimulationDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(SimulationDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<SimulationDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
