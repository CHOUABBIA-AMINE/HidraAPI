/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyDomainEventPublisher
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.messaging
 *
 * @Description : In-memory party domain event publisher.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.messaging;

import dz.sh.hidra.modules.party.application.port.out.PartyDomainEventPublisherPort;
import dz.sh.hidra.modules.party.domain.event.PartyDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory party domain event publisher.
 */
public class PartyDomainEventPublisher implements PartyDomainEventPublisherPort {

    private final List<PartyDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(PartyDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<PartyDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
