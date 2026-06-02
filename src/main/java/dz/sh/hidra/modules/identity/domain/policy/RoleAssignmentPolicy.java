/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleAssignmentPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.policy
 *
 * @Description : Domain policy validating whether a role can be assigned to a user.
 *
 */
package dz.sh.hidra.modules.identity.domain.policy;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.exception.RoleAssignmentNotAllowedException;
import dz.sh.hidra.modules.identity.domain.exception.UserLifecycleException;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.model.User;

/**
 * Domain policy validating whether a role can be assigned to a user.
 *
 * <p>Business role: protects role assignment rules so only active users can receive
 * active roles and duplicate role assignments are rejected.</p>
 *
 * <p>Architecture role: pure identity domain policy with no Spring Security, platform,
 * persistence, or organization dependency. It coordinates already-created identity
 * aggregates and value objects.</p>
 *
 * <p>Validation responsibility: requires non-null user and role aggregates, checks user
 * lifecycle state, checks role lifecycle state, and checks duplicate role assignment.</p>
 *
 * <p>Usage: call {@link #requireAssignable(User, Role)} before invoking aggregate
 * assignment methods from domain services or application services.</p>
 */
public final class RoleAssignmentPolicy {

    public boolean canAssign(User user, Role role) {
        try {
            requireAssignable(user, role);
            return true;
        } catch (RoleAssignmentNotAllowedException | UserLifecycleException exception) {
            return false;
        }
    }

    public void requireAssignable(User user, Role role) {
        User requiredUser = requireNonNull(user, "User");
        Role requiredRole = requireNonNull(role, "Role");

        if (!requiredUser.isActive()) {
            throw new UserLifecycleException("Only active users can receive role assignments.");
        }

        if (!requiredRole.isActive()) {
            throw new RoleAssignmentNotAllowedException("Only active roles can be assigned to users.");
        }

        if (requiredUser.hasRole(requiredRole.id())) {
            throw new RoleAssignmentNotAllowedException(
                    "User already has role: " + requiredRole.code().value() + "."
            );
        }
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
