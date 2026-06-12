/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseDomainEventPublisher
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.messaging
 *
 * @Description : In-memory HSE domain event publisher.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.messaging;

import dz.sh.hidra.modules.hse.application.port.out.HseDomainEventPublisherPort;
import dz.sh.hidra.modules.hse.domain.event.HseDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory HSE domain event publisher.
 */
public class HseDomainEventPublisher implements HseDomainEventPublisherPort {

    private final List<HseDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(HseDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<HseDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
