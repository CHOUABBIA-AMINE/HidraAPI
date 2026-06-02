/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionGrantedToRoleEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.event
 *
 * @Description : Domain event published when an identity permission is granted to a role.
 *
 */
package dz.sh.hidra.modules.identity.domain.event;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.kernel.domain.event.DomainEventId;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.PermissionId;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;

import java.time.Instant;

/**
 * Domain event published when an identity permission is granted to a role.
 *
 * <p>Business role: records that a role now grants a permission in the identity access
 * model.</p>
 *
 * <p>Architecture role: immutable identity domain event implementing the kernel
 * {@link DomainEvent} contract without importing platform authorization plumbing,
 * persistence, Spring, or security configuration.</p>
 *
 * <p>Validation responsibility: requires event identity, occurrence time, role
 * identifier, role code, permission identifier, and permission code. It intentionally
 * contains no password, token, session, credential, or secret value.</p>
 *
 * <p>Usage: create with {@link #newEvent(RoleId, RoleCode, PermissionId,
 * PermissionCode, Instant)} when a permission grant succeeds.</p>
 */
public record PermissionGrantedToRoleEvent(
        DomainEventId eventId,
        Instant occurredAt,
        RoleId roleId,
        RoleCode roleCode,
        PermissionId permissionId,
        PermissionCode permissionCode
) implements DomainEvent {

    private static final String EVENT_TYPE = "identity.permission.granted-to-role";

    public PermissionGrantedToRoleEvent {
        eventId = requireNonNull(eventId, "DomainEventId");
        occurredAt = requireNonNull(occurredAt, "occurredAt");
        roleId = requireNonNull(roleId, "RoleId");
        roleCode = requireNonNull(roleCode, "RoleCode");
        permissionId = requireNonNull(permissionId, "PermissionId");
        permissionCode = requireNonNull(permissionCode, "PermissionCode");
    }

    public static PermissionGrantedToRoleEvent newEvent(
            RoleId roleId,
            RoleCode roleCode,
            PermissionId permissionId,
            PermissionCode permissionCode,
            Instant occurredAt
    ) {
        return new PermissionGrantedToRoleEvent(
                DomainEventId.newId(),
                occurredAt,
                roleId,
                roleCode,
                permissionId,
                permissionCode
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
