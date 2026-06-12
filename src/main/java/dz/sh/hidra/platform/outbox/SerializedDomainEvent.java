/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SerializedDomainEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.outbox
 *
 * @Description : Represents a serialized domain event before outbox storage.
 *
 */
package dz.sh.hidra.platform.outbox;

import java.time.Instant;
import java.util.Objects;

/**
 * Technical representation of a domain event after serialization and before outbox storage.
 *
 * @param eventId domain event identifier
 * @param eventType domain event type
 * @param occurredAt event occurrence timestamp
 * @param payload serialized payload
 */
public record SerializedDomainEvent(
        String eventId,
        String eventType,
        Instant occurredAt,
        String payload
) {

    public SerializedDomainEvent {
        eventId = requireText(eventId, "Serialized event ID must not be null or blank.");
        eventType = requireText(eventType, "Serialized event type must not be null or blank.");
        Objects.requireNonNull(occurredAt, "Serialized event occurrence timestamp must not be null.");
        payload = requireText(payload, "Serialized event payload must not be null or blank.");
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }
}
