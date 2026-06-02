/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleAssignedToUserEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.event
 *
 * @Description : Domain event published when an identity role is assigned to a user.
 *
 */
package dz.sh.hidra.modules.identity.domain.event;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.kernel.domain.event.DomainEventId;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.UserId;

import java.time.Instant;

/**
 * Domain event published when an identity role is assigned to a user.
 *
 * <p>Business role: records that a user received a role that may grant permissions
 * through the identity access model.</p>
 *
 * <p>Architecture role: immutable identity domain event implementing the kernel
 * {@link DomainEvent} contract without importing platform security authorities,
 * persistence, Spring, or organization employee structures.</p>
 *
 * <p>Validation responsibility: requires event identity, occurrence time, user
 * identifier, role identifier, and role code. It intentionally contains no password,
 * token, session, credential, or secret value.</p>
 *
 * <p>Usage: create with {@link #newEvent(UserId, RoleId, RoleCode, Instant)} when a role
 * assignment succeeds.</p>
 */
public record RoleAssignedToUserEvent(
        DomainEventId eventId,
        Instant occurredAt,
        UserId userId,
        RoleId roleId,
        RoleCode roleCode
) implements DomainEvent {

    private static final String EVENT_TYPE = "identity.role.assigned-to-user";

    public RoleAssignedToUserEvent {
        eventId = requireNonNull(eventId, "DomainEventId");
        occurredAt = requireNonNull(occurredAt, "occurredAt");
        userId = requireNonNull(userId, "UserId");
        roleId = requireNonNull(roleId, "RoleId");
        roleCode = requireNonNull(roleCode, "RoleCode");
    }

    public static RoleAssignedToUserEvent newEvent(
            UserId userId,
            RoleId roleId,
            RoleCode roleCode,
            Instant occurredAt
    ) {
        return new RoleAssignedToUserEvent(
                DomainEventId.newId(),
                occurredAt,
                userId,
                roleId,
                roleCode
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
