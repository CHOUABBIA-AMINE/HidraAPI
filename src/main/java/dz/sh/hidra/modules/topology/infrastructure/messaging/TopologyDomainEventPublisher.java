/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyDomainEventPublisher
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.messaging
 *
 * @Description : In-memory topology domain event publisher.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.messaging;

import dz.sh.hidra.modules.topology.application.port.out.TopologyDomainEventPublisherPort;
import dz.sh.hidra.modules.topology.domain.event.TopologyDomainEvent;
import java.util.ArrayList;
import java.util.List;
public class TopologyDomainEventPublisher implements TopologyDomainEventPublisherPort {
    private final List<TopologyDomainEvent> publishedEvents = new ArrayList<>();
    public void publish(TopologyDomainEvent event) { if (event != null) publishedEvents.add(event); }
    public List<TopologyDomainEvent> publishedEvents() { return List.copyOf(publishedEvents); }
}
