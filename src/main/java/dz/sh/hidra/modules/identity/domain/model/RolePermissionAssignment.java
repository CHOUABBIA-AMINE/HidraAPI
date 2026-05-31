/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RolePermissionAssignment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Domain model representing assignment of a permission to a role.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.PermissionId;
import dz.sh.hidra.modules.identity.domain.value.RoleId;

import java.time.Instant;
import java.util.Objects;

/**
 * Immutable domain model representing a permission assignment inside a role aggregate.
 *
 * <p>Business role: records that a role grants a specific identity permission from the
 * permission catalog.</p>
 *
 * <p>Architecture role: value object owned by the {@link Role} aggregate. It does not
 * expose persistence annotations, platform security concepts, or organization structure.</p>
 *
 * <p>Validation responsibility: requires a role identifier, permission identifier,
 * permission code, and assignment timestamp.</p>
 *
 * <p>Usage: create through {@link #assign(RoleId, PermissionId, PermissionCode, Instant)}
 * and attach only through controlled methods on {@link Role}.</p>
 */
public final class RolePermissionAssignment implements ValueObject {

    private final RoleId roleId;
    private final PermissionId permissionId;
    private final PermissionCode permissionCode;
    private final Instant assignedAt;

    private RolePermissionAssignment(
            RoleId roleId,
            PermissionId permissionId,
            PermissionCode permissionCode,
            Instant assignedAt
    ) {
        this.roleId = requireNonNull(roleId, "RoleId");
        this.permissionId = requireNonNull(permissionId, "PermissionId");
        this.permissionCode = requireNonNull(permissionCode, "PermissionCode");
        this.assignedAt = requireNonNull(assignedAt, "assignedAt");
    }

    public static RolePermissionAssignment assign(
            RoleId roleId,
            PermissionId permissionId,
            PermissionCode permissionCode,
            Instant assignedAt
    ) {
        return new RolePermissionAssignment(roleId, permissionId, permissionCode, assignedAt);
    }

    public RoleId roleId() {
        return roleId;
    }

    public PermissionId permissionId() {
        return permissionId;
    }

    public PermissionCode permissionCode() {
        return permissionCode;
    }

    public Instant assignedAt() {
        return assignedAt;
    }

    public boolean grants(PermissionCode candidatePermissionCode) {
        return permissionCode.equals(candidatePermissionCode);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof RolePermissionAssignment assignment)) {
            return false;
        }

        return roleId.equals(assignment.roleId)
                && permissionCode.equals(assignment.permissionCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, permissionCode);
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
