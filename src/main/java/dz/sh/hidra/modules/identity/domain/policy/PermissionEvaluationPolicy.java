/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionEvaluationPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.policy
 *
 * @Description : Domain policy determining identity permission evaluation rules.
 *
 */
package dz.sh.hidra.modules.identity.domain.policy;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.exception.IdentityDomainException;
import dz.sh.hidra.modules.identity.domain.exception.UserLifecycleException;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;

import java.util.List;

/**
 * Domain policy determining identity permission evaluation rules.
 *
 * <p>Business role: answers whether a user has a permission through the active roles
 * assigned to that user.</p>
 *
 * <p>Architecture role: pure identity domain policy that evaluates domain aggregates and
 * value objects only. It does not own Spring Security authorization plumbing, filters,
 * current-principal extraction, or platform access-denied behavior.</p>
 *
 * <p>Validation responsibility: requires a non-null active user, a non-null permission
 * code, and a non-null list of assigned roles. Inactive roles are ignored during
 * evaluation.</p>
 *
 * <p>Usage: call {@link #hasPermission(User, PermissionCode, List)} from domain services
 * or application services when permission-based business access must be evaluated.</p>
 */
public final class PermissionEvaluationPolicy {

    public boolean hasPermission(User user, PermissionCode permissionCode, List<Role> assignedRoles) {
        User requiredUser = requireNonNull(user, "User");
        PermissionCode requiredPermissionCode = requireNonNull(permissionCode, "PermissionCode");
        List<Role> requiredAssignedRoles = requireNonNull(assignedRoles, "assignedRoles");

        requireActiveUser(requiredUser);

        return requiredAssignedRoles.stream()
                .filter(Role::isActive)
                .filter(role -> requiredUser.hasRole(role.id()))
                .anyMatch(role -> role.hasPermission(requiredPermissionCode));
    }

    public void requirePermission(User user, PermissionCode permissionCode, List<Role> assignedRoles) {
        if (!hasPermission(user, permissionCode, assignedRoles)) {
            throw new IdentityDomainException(
                    "Active user does not have required permission: " + permissionCode.value() + "."
            );
        }
    }

    private static void requireActiveUser(User user) {
        if (!user.isActive()) {
            throw new UserLifecycleException("Only active users can be evaluated for permissions.");
        }
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
