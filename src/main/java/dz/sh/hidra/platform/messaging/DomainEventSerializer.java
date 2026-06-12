/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DomainEventSerializer
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.messaging
 *
 * @Description : Defines technical serialization of domain events.
 *
 */
package dz.sh.hidra.platform.messaging;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.platform.outbox.SerializedDomainEvent;

/**
 * Serializes a kernel domain event into a platform outbox payload.
 */
public interface DomainEventSerializer {

    SerializedDomainEvent serialize(DomainEvent event);
}
