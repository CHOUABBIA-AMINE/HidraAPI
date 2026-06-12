/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditDomainEventPublisher
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.messaging
 *
 * @Description : In-memory audit domain event publisher.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.messaging;

import dz.sh.hidra.modules.audit.application.port.out.AuditDomainEventPublisherPort;
import dz.sh.hidra.modules.audit.domain.event.AuditDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory audit domain event publisher.
 */
public class AuditDomainEventPublisher implements AuditDomainEventPublisherPort {

    private final List<AuditDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(AuditDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<AuditDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
