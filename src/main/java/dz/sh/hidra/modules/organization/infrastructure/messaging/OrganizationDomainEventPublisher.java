/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationDomainEventPublisher
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.messaging
 *
 * @Description : In-memory organization domain event publisher.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.messaging;

import dz.sh.hidra.modules.organization.application.port.out.OrganizationDomainEventPublisherPort;
import dz.sh.hidra.modules.organization.domain.event.OrganizationDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory organization domain event publisher.
 */
public class OrganizationDomainEventPublisher implements OrganizationDomainEventPublisherPort {

    private final List<OrganizationDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(OrganizationDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<OrganizationDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
