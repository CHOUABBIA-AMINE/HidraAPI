/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringDomainEventPublisher
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.messaging
 *
 * @Description : In-memory monitoring domain event publisher.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.messaging;

import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringDomainEventPublisherPort;
import dz.sh.hidra.modules.monitoring.domain.event.MonitoringDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory monitoring domain event publisher.
 */
public class MonitoringDomainEventPublisher implements MonitoringDomainEventPublisherPort {

    private final List<MonitoringDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(MonitoringDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<MonitoringDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
