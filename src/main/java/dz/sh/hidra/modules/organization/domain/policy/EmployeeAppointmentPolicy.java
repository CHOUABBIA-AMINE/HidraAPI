/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.policy
 *
 * @Description : Evaluates explicit appointment overlap and occupancy policy on immutable proposed snapshots.
 *
 */
package dz.sh.hidra.modules.organization.domain.policy;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAppointmentChange;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAppointmentChange.VersionedAssignment;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAssignment;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Explicit software-policy input with no deployed business default.
 * Complete, current, locked scope discovery and policy authority belong to the runtime owner.
 */
public record EmployeeAppointmentPolicy(
        Map<TypePair, Boolean> overlapsAllowed,
        int maxConcurrentPrimary,
        PositionCapacity positionCapacity
) {
    public enum PositionCapacity { UNRESTRICTED, SINGLE_HOLDER }

    public record TypePair(AssignmentType first, AssignmentType second) {
        public TypePair {
            require(first != null && second != null, "Both assignment types are required.");
        }
    }

    public EmployeeAppointmentPolicy {
        require(overlapsAllowed != null && positionCapacity != null, "An explicit complete policy is required.");
        require(maxConcurrentPrimary > 0, "Primary concurrency limit must be positive.");
        AssignmentType[] types = AssignmentType.values();
        require(overlapsAllowed.size() == types.length * types.length, "Every ordered type pair is required.");
        for (AssignmentType first : types) {
            for (AssignmentType second : types) {
                Boolean allowed = overlapsAllowed.get(new TypePair(first, second));
                Boolean reverse = overlapsAllowed.get(new TypePair(second, first));
                require(allowed != null && allowed.equals(reverse), "Overlap matrix must be complete and symmetric.");
            }
        }
        overlapsAllowed = Map.copyOf(overlapsAllowed);
    }

    /** Checks the supplied final set only; this is not an authoritative database query. */
    public void validate(List<VersionedAssignment> proposed) {
        List<VersionedAssignment> snapshot = snapshot(proposed);
        List<EmployeeAssignment> active = snapshot.stream().map(VersionedAssignment::assignment)
                .filter(a -> a.status() == AssignmentStatus.ACTIVE).toList();
        for (int i = 0; i < active.size(); i++) {
            EmployeeAssignment first = active.get(i);
            for (int j = i + 1; j < active.size(); j++) {
                EmployeeAssignment second = active.get(j);
                if (!overlaps(first, second)) continue;
                if (first.employeeId().equals(second.employeeId())) {
                    require(overlapsAllowed.get(new TypePair(first.assignmentType(), second.assignmentType())),
                            "Employee appointment types cannot overlap under this policy.");
                } else if (positionCapacity == PositionCapacity.SINGLE_HOLDER
                        && first.organizationUnitId().equals(second.organizationUnitId())
                        && first.positionId().equals(second.positionId())) {
                    throw new InvalidOrganizationValueException("The unit/position pair already has an overlapping holder.");
                }
            }
        }
        Map<String, TreeMap<Instant, Integer>> boundaries = new java.util.HashMap<>();
        for (EmployeeAssignment assignment : active) {
            if (assignment.assignmentType() != AssignmentType.PRIMARY) continue;
            TreeMap<Instant, Integer> employee = boundaries.computeIfAbsent(
                    assignment.employeeId(), ignored -> new TreeMap<>());
            employee.merge(assignment.validFrom(), 1, Integer::sum);
            if (assignment.validTo() != null) employee.merge(assignment.validTo(), -1, Integer::sum);
        }
        for (TreeMap<Instant, Integer> employee : boundaries.values()) {
            int count = 0;
            for (int delta : employee.values()) {
                // Coalescing equal instants implements half-open boundaries without a false peak.
                count += delta;
                require(count <= maxConcurrentPrimary, "Maximum concurrent PRIMARY assignments exceeded.");
            }
        }
    }

    /** Replaces the exact before image and checks the complete proposed post-change set. */
    public List<VersionedAssignment> apply(
            List<VersionedAssignment> observed, EmployeeAppointmentChange change) {
        require(change != null, "Change proposal is required.");
        List<VersionedAssignment> current = snapshot(observed);
        List<VersionedAssignment> proposed = new ArrayList<>(current);
        int index = -1;
        for (int i = 0; i < current.size(); i++) {
            if (current.get(i).assignment().id().equals(change.before().assignment().id())) {
                require(current.get(i).equals(change.before()), "Observed source version or snapshot is stale.");
                index = i;
            }
        }
        require(index >= 0, "Change source must be present.");
        proposed.set(index, change.after());
        if (change.successor() != null) proposed.add(change.successor());
        validate(proposed);
        return List.copyOf(proposed);
    }

    private static List<VersionedAssignment> snapshot(List<VersionedAssignment> supplied) {
        require(supplied != null, "Appointment snapshot is required.");
        HashSet<String> identities = new HashSet<>();
        for (VersionedAssignment assignment : supplied) {
            require(assignment != null, "Appointment snapshot cannot contain null.");
            require(identities.add(assignment.assignment().id()), "Appointment IDs must be unique.");
        }
        return List.copyOf(supplied);
    }

    private static boolean overlaps(EmployeeAssignment first, EmployeeAssignment second) {
        return (second.validTo() == null || first.validFrom().isBefore(second.validTo()))
                && (first.validTo() == null || second.validFrom().isBefore(first.validTo()));
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new InvalidOrganizationValueException(message);
    }
}
