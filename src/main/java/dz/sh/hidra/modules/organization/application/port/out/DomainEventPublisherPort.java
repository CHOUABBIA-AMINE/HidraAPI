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
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Outbound domain event publisher port.
 *
 */
package dz.sh.hidra.modules.organization.application.port.out;

import java.util.Collection;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.event.DomainEvent;

/**
 * Defines the outbound port for publishing domain events.
 *
 * <p>Business role:
 * This port lets organization application services publish business facts raised by employees,
 * organization units, positions, assignments, and reporting lines.
 *
 * <p>Architecture role:
 * This is an application outbound port. Infrastructure or platform adapters may implement it later,
 * but this port does not depend on any concrete event transport, Spring, JPA, REST, identity, or
 * topology code.
 *
 * <p>Validation:
 * Null domain events must not be published. Collection publication delegates to single-event
 * publication by default.
 *
 * <p>Usage:
 * Application services depend on this interface when they need to publish organization domain
 * events without knowing the delivery mechanism.
 */
public interface DomainEventPublisherPort {

    /**
     * Publishes one domain event.
     *
     * @param domainEvent domain event to publish
     */
    void publish(DomainEvent domainEvent);

    /**
     * Publishes multiple domain events.
     *
     * @param domainEvents domain events to publish
     */
    default void publishAll(Collection<? extends DomainEvent> domainEvents) {
        if (domainEvents == null || domainEvents.isEmpty()) {
            return;
        }

        domainEvents.stream()
                .filter(Objects::nonNull)
                .forEach(this::publish);
    }
}
