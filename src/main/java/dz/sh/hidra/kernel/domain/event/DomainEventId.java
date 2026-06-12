/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DomainEventId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Kernel
 * @Module      : kernel
 * @Package     : dz.sh.hidra.kernel.domain.event
 *
 * @Description : Represents an immutable domain-event identifier.
 *
 */
package dz.sh.hidra.kernel.domain.event;

import dz.sh.hidra.kernel.domain.model.ValueObject;
import dz.sh.hidra.kernel.exception.InvalidValueObjectException;

import java.util.UUID;

/**
 * Immutable identifier for domain events.
 *
 * @param value non-blank event identifier value
 */
public record DomainEventId(String value) implements ValueObject {

    public DomainEventId {
        value = requireText(value, "Domain event ID must not be null or blank.");
    }

    /**
     * Restores an event ID from text.
     *
     * @param value event identifier text
     * @return domain event identifier
     */
    public static DomainEventId of(String value) {
        return new DomainEventId(value);
    }

    /**
     * Generates a UUID-based event ID.
     *
     * @return new domain event identifier
     */
    public static DomainEventId newId() {
        return new DomainEventId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(message);
        }
        return value.trim();
    }
}
