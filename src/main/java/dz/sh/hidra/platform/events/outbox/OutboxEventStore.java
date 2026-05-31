/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OutboxEventStore
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.events.outbox
 *
 * @Description : Stores serialized domain events in the technical platform outbox.
 *
 */
package dz.sh.hidra.platform.events.outbox;

import dz.sh.hidra.platform.events.serialization.SerializedDomainEvent;
import org.springframework.stereotype.Component;

@Component
public class OutboxEventStore {

    private final OutboxEventRepository repository;

    public OutboxEventStore(OutboxEventRepository repository) {
        this.repository = repository;
    }

    public OutboxEventEntity store(SerializedDomainEvent event) {
        return repository.save(OutboxEventEntity.pending(event));
    }

    public OutboxEventEntity store(SerializedDomainEvent event, String aggregateId, String aggregateType) {
        return repository.save(OutboxEventEntity.pending(event, aggregateId, aggregateType));
    }
}
