/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionDomainEventPublisher
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.messaging
 *
 * @Description : In-memory leak detection domain event publisher.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.messaging;

import dz.sh.hidra.modules.leakdetection.application.port.out.LeakDetectionDomainEventPublisherPort;
import dz.sh.hidra.modules.leakdetection.domain.event.LeakDetectionDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory leak detection domain event publisher.
 */
public class LeakDetectionDomainEventPublisher implements LeakDetectionDomainEventPublisherPort {

    private final List<LeakDetectionDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(LeakDetectionDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<LeakDetectionDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
