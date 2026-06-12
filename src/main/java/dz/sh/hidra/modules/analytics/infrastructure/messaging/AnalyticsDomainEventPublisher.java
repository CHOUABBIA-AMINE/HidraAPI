/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDomainEventPublisher
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.messaging
 *
 * @Description : In-memory analytics domain event publisher.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.messaging;

import dz.sh.hidra.modules.analytics.application.port.out.AnalyticsDomainEventPublisherPort;
import dz.sh.hidra.modules.analytics.domain.event.AnalyticsDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory analytics domain event publisher.
 */
public class AnalyticsDomainEventPublisher implements AnalyticsDomainEventPublisherPort {

    private final List<AnalyticsDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(AnalyticsDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<AnalyticsDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
