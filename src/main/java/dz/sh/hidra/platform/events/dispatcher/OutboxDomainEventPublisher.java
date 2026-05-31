/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OutboxDomainEventPublisher
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.events.dispatcher
 *
 * @Description : Persists kernel domain events into the technical outbox for later dispatch.
 *
 */
package dz.sh.hidra.platform.events.dispatcher;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.platform.events.outbox.OutboxEventStore;
import dz.sh.hidra.platform.events.serialization.DomainEventSerializer;
import dz.sh.hidra.platform.events.serialization.SerializedDomainEvent;
import org.springframework.stereotype.Component;

@Component
public class OutboxDomainEventPublisher implements DomainEventPublisher {

    private final DomainEventSerializer domainEventSerializer;
    private final OutboxEventStore outboxEventStore;

    public OutboxDomainEventPublisher(
            DomainEventSerializer domainEventSerializer,
            OutboxEventStore outboxEventStore) {
        this.domainEventSerializer = domainEventSerializer;
        this.outboxEventStore = outboxEventStore;
    }

    @Override
    public void publish(DomainEvent domainEvent) {
        if (domainEvent == null) {
            throw new IllegalArgumentException("Domain event must not be null.");
        }
        SerializedDomainEvent serializedEvent = domainEventSerializer.serialize(domainEvent);
        outboxEventStore.store(serializedEvent);
    }
}
