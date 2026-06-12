/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskDomainEventPublisher
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.messaging
 *
 * @Description : In-memory risk domain event publisher.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.messaging;

import dz.sh.hidra.modules.risk.application.port.out.RiskDomainEventPublisherPort;
import dz.sh.hidra.modules.risk.domain.event.RiskDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory risk domain event publisher.
 */
public class RiskDomainEventPublisher implements RiskDomainEventPublisherPort {

    private final List<RiskDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(RiskDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<RiskDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
