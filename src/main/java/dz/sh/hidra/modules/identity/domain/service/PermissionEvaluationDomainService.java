/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionEvaluationDomainService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.service
 *
 * @Description : Domain service coordinating identity permission evaluation rules.
 *
 */
package dz.sh.hidra.modules.identity.domain.service;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.policy.PermissionEvaluationPolicy;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;

import java.util.List;

/**
 * Domain service coordinating identity permission evaluation rules.
 *
 * <p>Business role: determines whether a user receives a required permission through the
 * roles currently assigned to that user.</p>
 *
 * <p>Architecture role: domain service used when permission evaluation requires
 * coordination between a user aggregate, assigned role aggregates, and the permission
 * evaluation policy. It does not own Spring Security authorization plumbing or platform
 * current-principal extraction.</p>
 *
 * <p>Validation responsibility: requires non-null user, permission code, role list, and
 * policy. Inactive users are rejected and inactive roles are ignored by the policy.</p>
 *
 * <p>Usage: use from identity application services when checking whether an actor can
 * perform a permission-protected business action.</p>
 */
public final class PermissionEvaluationDomainService {

    private final PermissionEvaluationPolicy permissionEvaluationPolicy;

    public PermissionEvaluationDomainService() {
        this(new PermissionEvaluationPolicy());
    }

    public PermissionEvaluationDomainService(PermissionEvaluationPolicy permissionEvaluationPolicy) {
        this.permissionEvaluationPolicy = requireNonNull(
                permissionEvaluationPolicy,
                "PermissionEvaluationPolicy"
        );
    }

    public boolean hasPermission(User user, PermissionCode permissionCode, List<Role> assignedRoles) {
        return permissionEvaluationPolicy.hasPermission(user, permissionCode, assignedRoles);
    }

    public void requirePermission(User user, PermissionCode permissionCode, List<Role> assignedRoles) {
        permissionEvaluationPolicy.requirePermission(user, permissionCode, assignedRoles);
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
