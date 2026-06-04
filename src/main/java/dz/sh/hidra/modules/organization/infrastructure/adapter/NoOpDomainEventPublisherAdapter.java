/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoOpDomainEventPublisherAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.adapter
 *
 * @Description : Safe no-operation domain event publisher for organization module.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.adapter;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.modules.organization.application.port.out.DomainEventPublisherPort;

/**
 * Safe no-operation implementation of the organization domain event publisher port.
 *
 * <p>Business role:
 * Allows organization use cases to publish domain events before a real delivery mechanism exists.
 *
 * <p>Architecture role:
 * Infrastructure adapter for the application outbound port. It does not depend on identity,
 * topology, REST API, persistence, platform-specific delivery, or messaging infrastructure.
 *
 * <p>Validation:
 * Null domain events are rejected. Valid events are intentionally ignored.
 *
 * <p>Usage:
 * Register from <code>OrganizationConfiguration</code> until a real event publisher is introduced.
 */
public final class NoOpDomainEventPublisherAdapter implements DomainEventPublisherPort {

    @Override
    public void publish(DomainEvent domainEvent) {
        Objects.requireNonNull(domainEvent, "Domain event must not be null.");
        // Intentionally no operation.
    }
}
