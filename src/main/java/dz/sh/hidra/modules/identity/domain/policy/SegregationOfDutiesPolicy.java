/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SegregationOfDutiesPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.policy
 *
 * @Description : Domain policy preventing dangerous identity access combinations.
 *
 */
package dz.sh.hidra.modules.identity.domain.policy;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.exception.RoleAssignmentNotAllowedException;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.model.User;

import java.util.List;

/**
 * Domain policy preventing dangerous identity access combinations.
 *
 * <p>Business role: prevents assignment of a candidate role when the user already has a
 * mutually exclusive role defined by the caller's business rule set.</p>
 *
 * <p>Architecture role: pure identity domain policy. It does not hardcode organization
 * structure, platform security configuration, Spring authorities, or external IAM
 * semantics.</p>
 *
 * <p>Validation responsibility: requires non-null user, candidate role, and mutually
 * exclusive role list. It rejects assignment when one of the mutually exclusive roles is
 * already assigned to the user.</p>
 *
 * <p>Usage: call {@link #requireNoConflict(User, Role, List)} before assigning a role
 * when a use case has computed the mutually exclusive roles for the candidate role.</p>
 */
public final class SegregationOfDutiesPolicy {

    public boolean hasConflict(User user, Role candidateRole, List<Role> mutuallyExclusiveRoles) {
        try {
            requireNoConflict(user, candidateRole, mutuallyExclusiveRoles);
            return false;
        } catch (RoleAssignmentNotAllowedException exception) {
            return true;
        }
    }

    public void requireNoConflict(User user, Role candidateRole, List<Role> mutuallyExclusiveRoles) {
        User requiredUser = requireNonNull(user, "User");
        Role requiredCandidateRole = requireNonNull(candidateRole, "candidateRole");
        List<Role> requiredMutuallyExclusiveRoles = requireNonNull(mutuallyExclusiveRoles, "mutuallyExclusiveRoles");

        boolean conflictsWithAssignedRole = requiredMutuallyExclusiveRoles.stream()
                .filter(Role::isActive)
                .anyMatch(exclusiveRole -> requiredUser.hasRole(exclusiveRole.id()));

        if (conflictsWithAssignedRole) {
            throw new RoleAssignmentNotAllowedException(
                    "Role assignment violates segregation of duties for role: "
                            + requiredCandidateRole.code().value()
                            + "."
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
