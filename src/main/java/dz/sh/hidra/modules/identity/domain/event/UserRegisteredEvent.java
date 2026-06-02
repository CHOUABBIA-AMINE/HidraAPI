/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserRegisteredEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.event
 *
 * @Description : Domain event published when an identity user is registered.
 *
 */
package dz.sh.hidra.modules.identity.domain.event;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.kernel.domain.event.DomainEventId;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.value.EmailAddress;
import dz.sh.hidra.modules.identity.domain.value.UserId;
import dz.sh.hidra.modules.identity.domain.value.Username;

import java.time.Instant;

/**
 * Domain event published when an identity user is registered.
 *
 * <p>Business role: records that a HidraAPI security identity has been created and can
 * proceed through identity lifecycle activation later.</p>
 *
 * <p>Architecture role: immutable identity domain event implementing the kernel
 * {@link DomainEvent} contract without importing platform event dispatchers,
 * persistence, Spring, or security plumbing.</p>
 *
 * <p>Validation responsibility: requires event identity, occurrence time, user
 * identifier, username, and email address. It intentionally contains no password, token,
 * session, credential, or secret value.</p>
 *
 * <p>Usage: create with {@link #newEvent(UserId, Username, EmailAddress, Instant)} from
 * the identity domain/application layer when a user registration is accepted.</p>
 */
public record UserRegisteredEvent(
        DomainEventId eventId,
        Instant occurredAt,
        UserId userId,
        Username username,
        EmailAddress emailAddress
) implements DomainEvent {

    private static final String EVENT_TYPE = "identity.user.registered";

    public UserRegisteredEvent {
        eventId = requireNonNull(eventId, "DomainEventId");
        occurredAt = requireNonNull(occurredAt, "occurredAt");
        userId = requireNonNull(userId, "UserId");
        username = requireNonNull(username, "Username");
        emailAddress = requireNonNull(emailAddress, "EmailAddress");
    }

    public static UserRegisteredEvent newEvent(
            UserId userId,
            Username username,
            EmailAddress emailAddress,
            Instant occurredAt
    ) {
        return new UserRegisteredEvent(
                DomainEventId.newId(),
                occurredAt,
                userId,
                username,
                emailAddress
        );
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
