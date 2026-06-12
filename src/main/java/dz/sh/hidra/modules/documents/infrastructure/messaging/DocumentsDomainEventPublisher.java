/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsDomainEventPublisher
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.messaging
 *
 * @Description : In-memory documents domain event publisher.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.messaging;

import dz.sh.hidra.modules.documents.application.port.out.DocumentsDomainEventPublisherPort;
import dz.sh.hidra.modules.documents.domain.event.DocumentsDomainEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory documents domain event publisher.
 */
public class DocumentsDomainEventPublisher implements DocumentsDomainEventPublisherPort {

    private final List<DocumentsDomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(DocumentsDomainEvent event) {
        if (event != null) {
            publishedEvents.add(event);
        }
    }

    public List<DocumentsDomainEvent> publishedEvents() {
        return List.copyOf(publishedEvents);
    }
}
