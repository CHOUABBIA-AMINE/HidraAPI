/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DomainEventPublisher
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.events.dispatcher
 *
 * @Description : Publishes kernel domain events through platform event infrastructure.
 *
 */
package dz.sh.hidra.platform.events.dispatcher;

import dz.sh.hidra.kernel.domain.event.DomainEvent;

public interface DomainEventPublisher {

    void publish(DomainEvent domainEvent);
}
