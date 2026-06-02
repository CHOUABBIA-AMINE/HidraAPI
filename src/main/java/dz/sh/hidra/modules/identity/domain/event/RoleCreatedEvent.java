/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleCreatedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.event
 *
 * @Description : Domain event published when an identity role is created.
 *
 */
package dz.sh.hidra.modules.identity.domain.event;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.kernel.domain.event.DomainEventId;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.RoleName;

import java.time.Instant;

/**
 * Domain event published when an identity role is created.
 *
 * <p>Business role: records that a role is available as a named permission grouping
 * inside the identity access model.</p>
 *
 * <p>Architecture role: immutable identity domain event implementing the kernel
 * {@link DomainEvent} contract without importing platform event infrastructure,
 * persistence, Spring, or security plumbing.</p>
 *
 * <p>Validation responsibility: requires event identity, occurrence time, role
 * identifier, role code, and role name. It intentionally contains no password, token,
 * session, credential, or secret value.</p>
 *
 * <p>Usage: create with {@link #newEvent(RoleId, RoleCode, RoleName, Instant)} when a
 * role creation operation succeeds.</p>
 */
public record RoleCreatedEvent(
        DomainEventId eventId,
        Instant occurredAt,
        RoleId roleId,
        RoleCode roleCode,
        RoleName roleName
) implements DomainEvent {

    private static final String EVENT_TYPE = "identity.role.created";

    public RoleCreatedEvent {
        eventId = requireNonNull(eventId, "DomainEventId");
        occurredAt = requireNonNull(occurredAt, "occurredAt");
        roleId = requireNonNull(roleId, "RoleId");
        roleCode = requireNonNull(roleCode, "RoleCode");
        roleName = requireNonNull(roleName, "RoleName");
    }

    public static RoleCreatedEvent newEvent(
            RoleId roleId,
            RoleCode roleCode,
            RoleName roleName,
            Instant occurredAt
    ) {
        return new RoleCreatedEvent(
                DomainEventId.newId(),
                occurredAt,
                roleId,
                roleCode,
                roleName
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
