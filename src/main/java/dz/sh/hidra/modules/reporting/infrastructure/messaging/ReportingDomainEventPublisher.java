/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingDomainEventPublisher
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.messaging
 *
 * @Description : In-memory reporting domain event publisher.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.messaging;

import dz.sh.hidra.modules.reporting.application.port.out.ReportingDomainEventPublisherPort;
import dz.sh.hidra.modules.reporting.domain.event.ReportingDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory reporting domain event publisher.
 */
public class ReportingDomainEventPublisher implements ReportingDomainEventPublisherPort {

    private final List<ReportingDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(ReportingDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<ReportingDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
