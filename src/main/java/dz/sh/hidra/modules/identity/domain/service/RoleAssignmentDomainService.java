/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleAssignmentDomainService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.service
 *
 * @Description : Domain service coordinating identity role assignment rules.
 *
 */
package dz.sh.hidra.modules.identity.domain.service;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.policy.RoleAssignmentPolicy;
import dz.sh.hidra.modules.identity.domain.policy.SegregationOfDutiesPolicy;

import java.time.Clock;
import java.util.List;

/**
 * Domain service coordinating identity role assignment rules.
 *
 * <p>Business role: assigns roles to users only after role assignment and segregation of
 * duties policies approve the assignment.</p>
 *
 * <p>Architecture role: domain service used when role assignment requires coordination
 * between a user aggregate, a role aggregate, and policy objects. It remains independent
 * from transactions, repositories, Spring Security plumbing, and platform code.</p>
 *
 * <p>Validation responsibility: requires non-null aggregates, policies, clock, and
 * mutually exclusive role list. Duplicate assignments and lifecycle violations are
 * rejected by policies or aggregates.</p>
 *
 * <p>Usage: use from identity application services after loading the user, candidate
 * role, and any mutually exclusive roles needed by the use case.</p>
 */
public final class RoleAssignmentDomainService {

    private final RoleAssignmentPolicy roleAssignmentPolicy;
    private final SegregationOfDutiesPolicy segregationOfDutiesPolicy;

    public RoleAssignmentDomainService() {
        this(new RoleAssignmentPolicy(), new SegregationOfDutiesPolicy());
    }

    public RoleAssignmentDomainService(
            RoleAssignmentPolicy roleAssignmentPolicy,
            SegregationOfDutiesPolicy segregationOfDutiesPolicy
    ) {
        this.roleAssignmentPolicy = requireNonNull(roleAssignmentPolicy, "RoleAssignmentPolicy");
        this.segregationOfDutiesPolicy = requireNonNull(segregationOfDutiesPolicy, "SegregationOfDutiesPolicy");
    }

    public void assignRole(User user, Role role, Clock clock) {
        assignRole(user, role, List.of(), clock);
    }

    public void assignRole(User user, Role role, List<Role> mutuallyExclusiveRoles, Clock clock) {
        User requiredUser = requireNonNull(user, "User");
        Role requiredRole = requireNonNull(role, "Role");
        List<Role> requiredMutuallyExclusiveRoles = requireNonNull(mutuallyExclusiveRoles, "mutuallyExclusiveRoles");
        Clock requiredClock = requireNonNull(clock, "Clock");

        roleAssignmentPolicy.requireAssignable(requiredUser, requiredRole);
        segregationOfDutiesPolicy.requireNoConflict(requiredUser, requiredRole, requiredMutuallyExclusiveRoles);

        requiredUser.assignRole(requiredRole, requiredClock);
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new InvalidValueObjectException(fieldName + " must not be null.");
        }
        return value;
    }
}
