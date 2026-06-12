/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DomainEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.event
 *
 * @Description : Defines the minimal generic domain-event contract.
 *
 */
package dz.sh.hidra.kernel.domain.event;

import java.time.Instant;

/**
 * Generic contract for domain events emitted by business modules.
 */
public interface DomainEvent {

    /**
     * Returns the unique event identifier.
     *
     * @return domain event identifier
     */
    DomainEventId eventId();

    /**
     * Returns when the event occurred.
     *
     * @return occurrence timestamp
     */
    Instant occurredAt();

    /**
     * Returns the stable event type name.
     *
     * @return event type code
     */
    String eventType();
}
