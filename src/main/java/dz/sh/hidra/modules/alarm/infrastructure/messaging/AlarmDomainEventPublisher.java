/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmDomainEventPublisher
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.messaging
 *
 * @Description : In-memory alarm domain event publisher.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.messaging;

import dz.sh.hidra.modules.alarm.application.port.out.AlarmDomainEventPublisherPort;
import dz.sh.hidra.modules.alarm.domain.event.AlarmDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory alarm domain event publisher.
 */
public class AlarmDomainEventPublisher implements AlarmDomainEventPublisherPort {

    private final List<AlarmDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(AlarmDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<AlarmDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
