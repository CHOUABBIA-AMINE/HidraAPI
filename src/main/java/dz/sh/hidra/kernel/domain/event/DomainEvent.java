/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DomainEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.event
 *
 * @Description : Minimal generic contract for domain events.
 *
 */
package dz.sh.hidra.kernel.domain.event;

import java.time.Instant;

public interface DomainEvent {

    DomainEventId eventId();

    Instant occurredAt();

    String eventType();
}
