/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserRoleAssignment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Domain model representing assignment of a role to a user.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.UserId;

import java.time.Instant;
import java.util.Objects;

/**
 * Immutable domain model representing a role assignment inside a user aggregate.
 *
 * <p>Business role: records that a user has been granted a specific identity role.</p>
 *
 * <p>Architecture role: value object owned by the {@link User} aggregate. It does not
 * depend on persistence annotations, Spring Security authorities, platform security
 * plumbing, or organization structures.</p>
 *
 * <p>Validation responsibility: requires a user identifier, role identifier, role code,
 * and assignment timestamp.</p>
 *
 * <p>Usage: create through {@link #assign(UserId, RoleId, RoleCode, Instant)} and attach
 * only through controlled methods on {@link User}.</p>
 */
public final class UserRoleAssignment implements ValueObject {

    private final UserId userId;
    private final RoleId roleId;
    private final RoleCode roleCode;
    private final Instant assignedAt;

    private UserRoleAssignment(
            UserId userId,
            RoleId roleId,
            RoleCode roleCode,
            Instant assignedAt
    ) {
        this.userId = requireNonNull(userId, "UserId");
        this.roleId = requireNonNull(roleId, "RoleId");
        this.roleCode = requireNonNull(roleCode, "RoleCode");
        this.assignedAt = requireNonNull(assignedAt, "assignedAt");
    }

    public static UserRoleAssignment assign(
            UserId userId,
            RoleId roleId,
            RoleCode roleCode,
            Instant assignedAt
    ) {
        return new UserRoleAssignment(userId, roleId, roleCode, assignedAt);
    }

    public UserId userId() {
        return userId;
    }

    public RoleId roleId() {
        return roleId;
    }

    public RoleCode roleCode() {
        return roleCode;
    }

    public Instant assignedAt() {
        return assignedAt;
    }

    public boolean references(RoleId candidateRoleId) {
        return roleId.equals(candidateRoleId);
    }

    public boolean references(RoleCode candidateRoleCode) {
        return roleCode.equals(candidateRoleCode);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof UserRoleAssignment assignment)) {
            return false;
        }

        return userId.equals(assignment.userId)
                && roleId.equals(assignment.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
