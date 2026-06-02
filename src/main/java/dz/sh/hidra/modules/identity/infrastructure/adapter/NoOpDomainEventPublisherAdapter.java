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
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.adapter
 *
 * @Description : Safe no-op identity domain event publisher adapter.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.adapter;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.application.port.out.DomainEventPublisherPort;

import java.util.List;

/**
 * Safe no-op identity domain event publisher adapter.
 *
 * <p>Business role: allows identity use cases to publish domain events without failing
 * while platform outbox/event integration is not available yet.</p>
 *
 * <p>Architecture role: infrastructure fallback implementation of
 * {@link DomainEventPublisherPort}. It intentionally does not deliver events to a
 * broker, outbox table, notification system, or platform event bus.</p>
 *
 * <p>Validation responsibility: rejects null event collections and null events so
 * identity use cases cannot silently pass invalid event objects.</p>
 *
 * <p>Usage: registered by {@code IdentityConfiguration} only when no other
 * {@link DomainEventPublisherPort} bean exists.</p>
 */
public final class NoOpDomainEventPublisherAdapter implements DomainEventPublisherPort {

    @Override
    public void publish(DomainEvent event) {
        requireNonNull(event, "DomainEvent");
    }

    @Override
    public void publishAll(List<? extends DomainEvent> events) {
        List<? extends DomainEvent> requiredEvents = requireNonNull(events, "events");
        requiredEvents.forEach(event -> requireNonNull(event, "DomainEvent"));
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
