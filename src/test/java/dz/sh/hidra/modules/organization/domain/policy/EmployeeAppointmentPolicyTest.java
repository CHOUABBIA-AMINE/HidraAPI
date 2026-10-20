/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentPolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.policy
 *
 * @Description : Tests explicit overlap matrices, effective-time occupancy and detached final-set validation.
 *
 */
package dz.sh.hidra.modules.organization.domain.policy;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAppointmentChange;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAppointmentChange.VersionedAssignment;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAssignment;
import dz.sh.hidra.modules.organization.domain.policy.EmployeeAppointmentPolicy.PositionCapacity;
import dz.sh.hidra.modules.organization.domain.policy.EmployeeAppointmentPolicy.TypePair;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeAppointmentPolicyTest {
    private static final Instant START = Instant.parse("2026-10-01T00:00:00Z");

    private static Map<TypePair, Boolean> matrix(boolean allowed) {
        Map<TypePair, Boolean> result = new HashMap<>();
        for (AssignmentType first : AssignmentType.values())
            for (AssignmentType second : AssignmentType.values())
                result.put(new TypePair(first, second), allowed);
        return result;
    }

    private static EmployeeAppointmentPolicy policy(boolean allowed, int primary, PositionCapacity capacity) {
        return new EmployeeAppointmentPolicy(matrix(allowed), primary, capacity);
    }

    private static VersionedAssignment assignment(String id, String employee, String unit, String position,
            AssignmentType type, long start, Long end, AssignmentStatus status) {
        return new VersionedAssignment(1, new EmployeeAssignment(id, employee, unit, position, type,
                START.plusSeconds(start), end == null ? null : START.plusSeconds(end), status, START, START));
    }

    private static VersionedAssignment active(String id, AssignmentType type, long start, Long end) {
        return assignment(id, "employee", "unit", "position", type, start, end, AssignmentStatus.ACTIVE);
    }

    @Test void requiresCompleteSymmetricMatrixAndExplicitLimits() {
        assertThrows(InvalidOrganizationValueException.class,
                () -> new EmployeeAppointmentPolicy(null, 1, PositionCapacity.UNRESTRICTED));
        assertThrows(InvalidOrganizationValueException.class,
                () -> new EmployeeAppointmentPolicy(matrix(true), 1, null));
        assertThrows(InvalidOrganizationValueException.class,
                () -> policy(true, 0, PositionCapacity.UNRESTRICTED));
        assertThrows(InvalidOrganizationValueException.class,
                () -> policy(true, -1, PositionCapacity.UNRESTRICTED));
        var missing = matrix(true);
        missing.remove(new TypePair(AssignmentType.PRIMARY, AssignmentType.ACTING));
        assertThrows(InvalidOrganizationValueException.class,
                () -> new EmployeeAppointmentPolicy(missing, 1, PositionCapacity.UNRESTRICTED));
        var asymmetric = matrix(true);
        asymmetric.put(new TypePair(AssignmentType.PRIMARY, AssignmentType.ACTING), false);
        assertThrows(InvalidOrganizationValueException.class,
                () -> new EmployeeAppointmentPolicy(asymmetric, 1, PositionCapacity.UNRESTRICTED));
        var unknown = matrix(true);
        unknown.put(new TypePair(AssignmentType.PRIMARY, AssignmentType.PRIMARY), null);
        assertThrows(InvalidOrganizationValueException.class,
                () -> new EmployeeAppointmentPolicy(unknown, 1, PositionCapacity.UNRESTRICTED));
        assertThrows(InvalidOrganizationValueException.class, () -> new TypePair(null, AssignmentType.ACTING));
        assertThrows(InvalidOrganizationValueException.class, () -> new TypePair(AssignmentType.PRIMARY, null));
    }

    @Test void everyOrderedTypePairObeysItsExplicitDecision() {
        for (AssignmentType first : AssignmentType.values()) {
            for (AssignmentType second : AssignmentType.values()) {
                var pair = List.of(active("one", first, 0, null), active("two", second, 1, null));
                assertDoesNotThrow(() -> policy(true, 2, PositionCapacity.UNRESTRICTED).validate(pair));
                var rules = matrix(true);
                rules.put(new TypePair(first, second), false);
                rules.put(new TypePair(second, first), false);
                var denied = new EmployeeAppointmentPolicy(rules, 2, PositionCapacity.UNRESTRICTED);
                assertThrows(InvalidOrganizationValueException.class, () -> denied.validate(pair));
            }
        }
    }

    @Test void adjacentIntervalsAreAllowedEvenWhenTheirTypesCannotOverlap() {
        var noOverlap = policy(false, 1, PositionCapacity.UNRESTRICTED);
        noOverlap.validate(List.of(active("one", AssignmentType.PRIMARY, 0, 10L),
                active("two", AssignmentType.ACTING, 10, 20L)));
        assertThrows(InvalidOrganizationValueException.class,
                () -> noOverlap.validate(List.of(active("one", AssignmentType.PRIMARY, 0, 11L),
                        active("two", AssignmentType.ACTING, 10, 20L))));
    }

    @Test void primaryLimitUsesEffectiveConcurrencyRatherThanHistorySize() {
        var two = policy(true, 2, PositionCapacity.UNRESTRICTED);
        var staggered = List.of(active("one", AssignmentType.PRIMARY, 0, 20L),
                active("two", AssignmentType.PRIMARY, 10, 30L),
                active("three", AssignmentType.PRIMARY, 20, 40L));
        assertDoesNotThrow(() -> two.validate(staggered));
        var overlap = new ArrayList<>(staggered);
        overlap.add(active("four", AssignmentType.PRIMARY, 15, 25L));
        assertThrows(InvalidOrganizationValueException.class, () -> two.validate(overlap));
    }

    @Test void equalTimeBoundariesAndOpenEndsUseHalfOpenCounting() {
        var one = policy(true, 1, PositionCapacity.UNRESTRICTED);
        one.validate(List.of(active("one", AssignmentType.PRIMARY, 0, 10L),
                active("two", AssignmentType.PRIMARY, 10, 20L),
                active("three", AssignmentType.PRIMARY, 20, null)));
        assertThrows(InvalidOrganizationValueException.class,
                () -> one.validate(List.of(active("one", AssignmentType.PRIMARY, 0, null),
                        active("two", AssignmentType.PRIMARY, 10, 20L))));
    }

    @Test void matrixAndPrimaryLimitMustBothAllowAProposal() {
        var pair = List.of(active("one", AssignmentType.PRIMARY, 0, null),
                active("two", AssignmentType.PRIMARY, 10, null));
        assertThrows(InvalidOrganizationValueException.class,
                () -> policy(true, 1, PositionCapacity.UNRESTRICTED).validate(pair));
        assertThrows(InvalidOrganizationValueException.class,
                () -> policy(false, 2, PositionCapacity.UNRESTRICTED).validate(pair));
        var distinctEmployees = List.of(pair.get(0),
                assignment("two", "other", "unit", "position", AssignmentType.PRIMARY, 10, null, AssignmentStatus.ACTIVE));
        assertDoesNotThrow(() -> policy(true, 1, PositionCapacity.UNRESTRICTED).validate(distinctEmployees));
    }

    @Test void singleHolderIsScopedToUnitAndPositionAndIncludesActing() {
        var original = active("one", AssignmentType.PRIMARY, 0, null);
        var acting = assignment("two", "other", "unit", "position", AssignmentType.ACTING, 10, null, AssignmentStatus.ACTIVE);
        var single = policy(true, 2, PositionCapacity.SINGLE_HOLDER);
        assertThrows(InvalidOrganizationValueException.class, () -> single.validate(List.of(original, acting)));
        assertDoesNotThrow(() -> policy(true, 2, PositionCapacity.UNRESTRICTED).validate(List.of(original, acting)));
        for (VersionedAssignment independent : List.of(
                assignment("two", "other", "other-unit", "position", AssignmentType.PRIMARY, 10, null, AssignmentStatus.ACTIVE),
                assignment("two", "other", "unit", "other-position", AssignmentType.PRIMARY, 10, null, AssignmentStatus.ACTIVE))) {
            assertDoesNotThrow(() -> single.validate(List.of(original, independent)));
        }
        single.validate(List.of(active("one", AssignmentType.PRIMARY, 0, 10L), acting));
    }

    @Test void historicalNonActiveRecordsAreRetainedButDoNotOccupy() {
        var values = new ArrayList<VersionedAssignment>();
        values.add(active("one", AssignmentType.PRIMARY, 0, null));
        for (AssignmentStatus status : new AssignmentStatus[] {
                AssignmentStatus.SUSPENDED, AssignmentStatus.ENDED, AssignmentStatus.CANCELLED }) {
            values.add(assignment(status.name(), "other", "unit", "position",
                    AssignmentType.PRIMARY, 0, 100L, status));
        }
        assertDoesNotThrow(() -> policy(false, 1, PositionCapacity.SINGLE_HOLDER).validate(values));
        assertEquals(4, values.size());
    }

    @Test void policyAndReturnedSnapshotsAreDefensivelyCopied() {
        var rules = matrix(true);
        var copied = new EmployeeAppointmentPolicy(rules, 1, PositionCapacity.UNRESTRICTED);
        rules.clear();
        assertEquals(16, copied.overlapsAllowed().size());
        assertThrows(UnsupportedOperationException.class, () -> copied.overlapsAllowed().clear());
        var source = active("source", AssignmentType.PRIMARY, 0, null);
        var input = new ArrayList<>(List.of(source));
        var change = EmployeeAppointmentChange.end(source, START.plusSeconds(10), START.plusSeconds(10), "end");
        var result = copied.apply(input, change);
        input.clear();
        assertEquals(1, result.size());
        assertEquals(change.after(), result.get(0));
        assertThrows(UnsupportedOperationException.class, () -> result.clear());
        assertNull(source.assignment().validTo());
    }

    @Test void rejectsDuplicateNullAndMissingSnapshotInputs() {
        var policy = policy(true, 1, PositionCapacity.UNRESTRICTED);
        var original = active("one", AssignmentType.PRIMARY, 0, null);
        assertThrows(InvalidOrganizationValueException.class, () -> policy.validate(null));
        var nullMember = new ArrayList<VersionedAssignment>();
        nullMember.add(null);
        assertThrows(InvalidOrganizationValueException.class, () -> policy.validate(nullMember));
        assertThrows(InvalidOrganizationValueException.class, () -> policy.validate(List.of(original, original)));
        assertThrows(InvalidOrganizationValueException.class, () -> policy.apply(List.of(original), null));
    }

    @Test void transferValidatesTheFinalSetAndLeavesObservedStateIntactOnFailure() {
        var source = active("source", AssignmentType.PRIMARY, 0, null);
        var change = EmployeeAppointmentChange.transfer(source, "next", "new-unit", "new-position",
                AssignmentType.PRIMARY, START.plusSeconds(10), null, START.plusSeconds(5), "future transfer");
        var one = policy(false, 1, PositionCapacity.SINGLE_HOLDER);
        var result = one.apply(List.of(source), change);
        assertEquals(List.of(change.after(), change.successor()), result);
        assertEquals(AssignmentStatus.ACTIVE, result.get(0).assignment().status());
        var blocker = assignment("blocker", "other", "new-unit", "new-position", AssignmentType.ACTING,
                10, null, AssignmentStatus.ACTIVE);
        var observed = new ArrayList<>(List.of(source, blocker));
        assertThrows(InvalidOrganizationValueException.class, () -> one.apply(observed, change));
        assertEquals(List.of(source, blocker), observed);
        assertNull(source.assignment().validTo());
    }

    @Test void applyRequiresTheExactBeforeVersionAndSnapshot() {
        var source = active("source", AssignmentType.PRIMARY, 0, null);
        var change = EmployeeAppointmentChange.end(source, START.plusSeconds(10), START.plusSeconds(10), "end");
        var policy = policy(true, 1, PositionCapacity.UNRESTRICTED);
        assertThrows(InvalidOrganizationValueException.class, () -> policy.apply(List.of(), change));
        assertThrows(InvalidOrganizationValueException.class,
                () -> policy.apply(List.of(new VersionedAssignment(2, source.assignment())), change));
        var different = assignment("source", "employee", "other-unit", "position", AssignmentType.PRIMARY,
                0, null, AssignmentStatus.ACTIVE);
        assertThrows(InvalidOrganizationValueException.class, () -> policy.apply(List.of(different), change));
        var transfer = EmployeeAppointmentChange.transfer(source, "duplicate", "unit", "position", AssignmentType.PRIMARY,
                START.plusSeconds(10), null, START.plusSeconds(10), "transfer");
        var duplicate = active("duplicate", AssignmentType.SECONDARY, 0, null);
        assertThrows(InvalidOrganizationValueException.class, () -> policy.apply(List.of(source, duplicate), transfer));
    }
}
