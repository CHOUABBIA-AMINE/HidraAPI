/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DomainEventSerializer
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.events.serialization
 *
 * @Description : Serializes kernel domain events into technical platform payloads.
 *
 */
package dz.sh.hidra.platform.events.serialization;

import dz.sh.hidra.kernel.domain.event.DomainEvent;

public interface DomainEventSerializer {

    SerializedDomainEvent serialize(DomainEvent domainEvent);
}
