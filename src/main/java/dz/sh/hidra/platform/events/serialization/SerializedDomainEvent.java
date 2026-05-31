/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SerializedDomainEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.events.serialization
 *
 * @Description : Immutable technical representation of a serialized domain event.
 *
 */
package dz.sh.hidra.platform.events.serialization;

import java.time.Instant;

public record SerializedDomainEvent(String eventId, String eventType, Instant occurredAt, String payload) {

    public SerializedDomainEvent {
        eventId = requireText(eventId, "Event ID");
        eventType = requireText(eventType, "Event type");
        if (occurredAt == null) {
            throw new IllegalArgumentException("Event occurrence timestamp must not be null.");
        }
        payload = requireText(payload, "Event payload");
    }

    private static String requireText(String value, String label) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(label + " must not be blank.");
        }
        return value.trim();
    }
}
