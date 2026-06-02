/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserSuspendedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.event
 *
 * @Description : Domain event published when an identity user is suspended.
 *
 */
package dz.sh.hidra.modules.identity.domain.event;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.kernel.domain.event.DomainEventId;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.value.UserId;

import java.time.Instant;

/**
 * Domain event published when an identity user is suspended.
 *
 * <p>Business role: records that an active identity user has been suspended and should
 * no longer be treated as active for business access evaluation.</p>
 *
 * <p>Architecture role: immutable identity domain event implementing the kernel
 * {@link DomainEvent} contract without importing platform event dispatchers,
 * persistence, Spring, or security plumbing.</p>
 *
 * <p>Validation responsibility: requires event identity, occurrence time, and user
 * identifier. It intentionally contains no password, token, session, credential, or
 * secret value.</p>
 *
 * <p>Usage: create with {@link #newEvent(UserId, Instant)} when a user suspension
 * transition succeeds.</p>
 */
public record UserSuspendedEvent(
        DomainEventId eventId,
        Instant occurredAt,
        UserId userId
) implements DomainEvent {

    private static final String EVENT_TYPE = "identity.user.suspended";

    public UserSuspendedEvent {
        eventId = requireNonNull(eventId, "DomainEventId");
        occurredAt = requireNonNull(occurredAt, "occurredAt");
        userId = requireNonNull(userId, "UserId");
    }

    public static UserSuspendedEvent newEvent(UserId userId, Instant occurredAt) {
        return new UserSuspendedEvent(DomainEventId.newId(), occurredAt, userId);
    }

    @Override
    public String eventType() {
        return EVENT_TYPE;
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
