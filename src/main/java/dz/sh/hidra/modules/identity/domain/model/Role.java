/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Role
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Role aggregate that owns lifecycle and permission assignments.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.AggregateRoot;
import dz.sh.hidra.modules.identity.domain.exception.RoleAssignmentNotAllowedException;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.PermissionId;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.RoleName;
import dz.sh.hidra.modules.identity.domain.value.RoleStatus;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Aggregate root representing an identity role.
 *
 * <p>Business role: groups identity permissions that can be assigned to users through
 * role assignment policies.</p>
 *
 * <p>Architecture role: domain aggregate root that owns role lifecycle state and its
 * permission assignment collection without depending on Spring Security, persistence, or
 * platform infrastructure.</p>
 *
 * <p>Validation responsibility: requires valid role identity values, controls activation
 * and disable transitions, and rejects duplicate permission assignments.</p>
 *
 * <p>Usage: create roles with {@link #create(RoleId, RoleCode, RoleName)}, assign
 * permissions through {@link #grantPermission(Permission, Clock)}, and persist through a
 * domain repository contract.</p>
 */
public final class Role implements AggregateRoot<RoleId> {

    private final RoleId id;
    private final RoleCode code;
    private RoleName name;
    private RoleStatus status;
    private final List<RolePermissionAssignment> permissionAssignments;

    private Role(
            RoleId id,
            RoleCode code,
            RoleName name,
            RoleStatus status,
            List<RolePermissionAssignment> permissionAssignments
    ) {
        this.id = requireNonNull(id, "RoleId");
        this.code = requireNonNull(code, "RoleCode");
        this.name = requireNonNull(name, "RoleName");
        this.status = requireNonNull(status, "RoleStatus");
        this.permissionAssignments = new ArrayList<>(requireNonNull(permissionAssignments, "permissionAssignments"));
        rejectForeignAssignments(this.permissionAssignments);
        rejectDuplicateAssignments(this.permissionAssignments);
    }

    public static Role create(RoleId id, RoleCode code, RoleName name) {
        return new Role(id, code, name, RoleStatus.ACTIVE, List.of());
    }

    public static Role rehydrate(
            RoleId id,
            RoleCode code,
            RoleName name,
            RoleStatus status,
            List<RolePermissionAssignment> permissionAssignments
    ) {
        return new Role(id, code, name, status, permissionAssignments);
    }

    @Override
    public RoleId id() {
        return id;
    }

    public RoleCode code() {
        return code;
    }

    public RoleName name() {
        return name;
    }

    public RoleStatus status() {
        return status;
    }

    public List<RolePermissionAssignment> permissionAssignments() {
        return Collections.unmodifiableList(permissionAssignments);
    }

    public boolean isActive() {
        return RoleStatus.ACTIVE.equals(status);
    }

    public void rename(RoleName newName) {
        this.name = requireNonNull(newName, "RoleName");
    }

    public void activate() {
        this.status = RoleStatus.ACTIVE;
    }

    public void disable() {
        this.status = RoleStatus.DISABLED;
    }

    public void grantPermission(Permission permission, Clock clock) {
        requireActiveRole();
        Permission requiredPermission = requireNonNull(permission, "Permission");
        Clock requiredClock = requireNonNull(clock, "Clock");

        grantPermission(
                requiredPermission.id(),
                requiredPermission.code(),
                Instant.now(requiredClock)
        );
    }

    public void grantPermission(
            PermissionId permissionId,
            PermissionCode permissionCode,
            Instant assignedAt
    ) {
        requireActiveRole();

        RolePermissionAssignment assignment = RolePermissionAssignment.assign(
                id,
                permissionId,
                permissionCode,
                assignedAt
        );

        if (hasPermission(permissionCode)) {
            throw new RoleAssignmentNotAllowedException(
                    "Role already grants permission: " + permissionCode.value() + "."
            );
        }

        permissionAssignments.add(assignment);
    }

    public boolean revokePermission(PermissionCode permissionCode) {
        PermissionCode requiredPermissionCode = requireNonNull(permissionCode, "PermissionCode");
        return permissionAssignments.removeIf(assignment -> assignment.grants(requiredPermissionCode));
    }

    public boolean hasPermission(PermissionCode permissionCode) {
        PermissionCode requiredPermissionCode = requireNonNull(permissionCode, "PermissionCode");
        return permissionAssignments.stream()
                .anyMatch(assignment -> assignment.grants(requiredPermissionCode));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Role role)) {
            return false;
        }

        return id.equals(role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private void requireActiveRole() {
        if (!isActive()) {
            throw new RoleAssignmentNotAllowedException("Disabled roles cannot be changed.");
        }
    }

    private void rejectForeignAssignments(List<RolePermissionAssignment> assignments) {
        boolean containsForeignAssignment = assignments.stream()
                .anyMatch(assignment -> !id.equals(assignment.roleId()));

        if (containsForeignAssignment) {
            throw new RoleAssignmentNotAllowedException(
                    "Role cannot contain permission assignments for another role."
            );
        }
    }

    private static void rejectDuplicateAssignments(List<RolePermissionAssignment> assignments) {
        long distinctPermissionCodes = assignments.stream()
                .map(RolePermissionAssignment::permissionCode)
                .distinct()
                .count();

        if (distinctPermissionCodes != assignments.size()) {
            throw new RoleAssignmentNotAllowedException("Role cannot contain duplicate permission assignments.");
        }
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
