/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyDomainEventPublisher
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.messaging
 *
 * @Description : In-memory custody domain event publisher.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.messaging;

import dz.sh.hidra.modules.custody.application.port.out.CustodyDomainEventPublisherPort;
import dz.sh.hidra.modules.custody.domain.event.CustodyDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory custody domain event publisher.
 */
public class CustodyDomainEventPublisher implements CustodyDomainEventPublisherPort {

    private final List<CustodyDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(CustodyDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<CustodyDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
