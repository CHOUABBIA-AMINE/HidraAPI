/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationDomainEventPublisher
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.messaging
 *
 * @Description : In-memory notification domain event publisher.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.messaging;

import dz.sh.hidra.modules.notification.application.port.out.NotificationDomainEventPublisherPort;
import dz.sh.hidra.modules.notification.domain.event.NotificationDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory notification domain event publisher.
 */
public class NotificationDomainEventPublisher implements NotificationDomainEventPublisherPort {

    private final List<NotificationDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(NotificationDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<NotificationDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
