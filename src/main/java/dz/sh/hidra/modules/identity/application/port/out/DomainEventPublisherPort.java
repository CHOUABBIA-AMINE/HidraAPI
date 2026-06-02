/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DomainEventPublisherPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Outbound port for publishing identity domain events.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.kernel.domain.event.DomainEvent;

import java.util.List;

/**
 * Outbound port for publishing identity domain events.
 *
 * <p>Business role: lets identity application services publish user, role, and permission
 * domain events after successful use-case execution.</p>
 *
 * <p>Architecture role: application outbound port implemented later by infrastructure or
 * platform adapters. The identity module defines the need to publish events but does not
 * own event transport or outbox plumbing here.</p>
 *
 * <p>Validation responsibility: implementations should reject missing events and preserve
 * event order when publishing multiple events.</p>
 *
 * <p>Usage: depend on this port from application services that need to publish domain
 * events.</p>
 */
public interface DomainEventPublisherPort {

    void publish(DomainEvent event);

    void publishAll(List<? extends DomainEvent> events);
}
