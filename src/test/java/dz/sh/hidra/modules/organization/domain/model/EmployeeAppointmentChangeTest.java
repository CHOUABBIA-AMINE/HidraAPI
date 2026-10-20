/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentChangeTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Exercises versioned appointment transition invariants and preservation of original snapshots.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAppointmentChange.Kind;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAppointmentChange.VersionedAssignment;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeAppointmentChangeTest {
    private static final Instant START = Instant.parse("2026-10-01T00:00:00Z");
    private static final Instant CHANGE = START.plusSeconds(60);
    private static final Instant END = START.plusSeconds(120);

    private static EmployeeAssignment assignment(String id, String employee, String unit, String position,
            AssignmentType type, Instant from, Instant to, AssignmentStatus status, Instant created, Instant updated) {
        return new EmployeeAssignment(id, employee, unit, position, type, from, to, status, created, updated);
    }

    private static VersionedAssignment source(Instant end) {
        return new VersionedAssignment(7, assignment("source", "employee", "unit", "position",
                AssignmentType.PRIMARY, START, end, AssignmentStatus.ACTIVE, START, START));
    }

    private static EmployeeAssignment replacement(VersionedAssignment source, String id, String employee,
            Instant created) {
        return assignment(id, employee, "corrected-unit", "corrected-position", AssignmentType.ACTING,
                START, END, AssignmentStatus.ACTIVE, created, CHANGE);
    }

    @Test void endsWithoutMutatingTheOriginalSnapshot() {
        var source = source(null);
        var result = EmployeeAppointmentChange.end(source, CHANGE, CHANGE, "  approved end  ");
        assertSame(source, result.before());
        assertNull(source.assignment().validTo());
        assertEquals(Kind.END, result.kind());
        assertEquals(8, result.after().version());
        assertEquals(CHANGE, result.after().assignment().validTo());
        assertEquals(AssignmentStatus.ENDED, result.after().assignment().status());
        assertEquals(START, result.after().assignment().createdAt());
        assertEquals("approved end", result.reason());
        assertNull(result.successor());
    }

    @Test void distinguishesFutureEndFromEffectiveEnding() {
        var future = EmployeeAppointmentChange.end(source(null), END, CHANGE, "planned end");
        assertEquals(AssignmentStatus.ACTIVE, future.after().assignment().status());
        var boundary = EmployeeAppointmentChange.end(source(null), CHANGE, CHANGE, "effective now");
        assertEquals(AssignmentStatus.ENDED, boundary.after().assignment().status());
    }

    @Test void rejectsNonpositiveIntervalsAndFiniteEndExtension() {
        assertThrows(InvalidOrganizationValueException.class,
                () -> EmployeeAppointmentChange.end(source(null), START, CHANGE, "invalid"));
        assertThrows(InvalidOrganizationValueException.class,
                () -> EmployeeAppointmentChange.end(source(null), START.minusSeconds(1), CHANGE, "invalid"));
        assertThrows(InvalidOrganizationValueException.class,
                () -> EmployeeAppointmentChange.end(source(CHANGE), END, CHANGE, "extension"));
        assertEquals(CHANGE, EmployeeAppointmentChange.end(source(CHANGE), CHANGE, CHANGE, "same end")
                .after().assignment().validTo());
    }

    @Test void transfersWithOneEmployeeNewIdentityAndExactAdjacency() {
        var source = source(END);
        var result = EmployeeAppointmentChange.transfer(source, "successor", "new-unit", "new-position",
                AssignmentType.TEMPORARY, CHANGE, END.plusSeconds(60), CHANGE, "transfer");
        assertEquals(Kind.TRANSFER, result.kind());
        assertEquals(result.after().assignment().validTo(), result.successor().assignment().validFrom());
        assertEquals(source.assignment().employeeId(), result.successor().assignment().employeeId());
        assertNotEquals(source.assignment().id(), result.successor().assignment().id());
        assertEquals(1, result.successor().version());
        assertEquals(END, source.assignment().validTo());
        assertEquals(CHANGE, result.successor().assignment().createdAt());
        assertEquals(END.plusSeconds(60), result.successor().assignment().validTo());
    }

    @Test void rejectsInvalidSuccessorWithoutChangingTheSource() {
        var source = source(END);
        assertThrows(InvalidOrganizationValueException.class,
                () -> EmployeeAppointmentChange.transfer(source, "next", "unit", "position",
                        AssignmentType.PRIMARY, CHANGE, CHANGE, CHANGE, "invalid successor"));
        assertEquals(7, source.version());
        assertEquals(END, source.assignment().validTo());
        assertEquals(START, source.assignment().updatedAt());
    }

    @Test void rejectsTransferAtFiniteEndOrWithReusedIdentity() {
        assertThrows(InvalidOrganizationValueException.class,
                () -> EmployeeAppointmentChange.transfer(source(END), "next", "unit", "position",
                        AssignmentType.PRIMARY, END, null, END, "not a shortening"));
        assertThrows(InvalidOrganizationValueException.class,
                () -> EmployeeAppointmentChange.transfer(source(null), "source", "unit", "position",
                        AssignmentType.PRIMARY, CHANGE, null, CHANGE, "same identity"));
    }

    @Test void correctionRetainsCompleteBeforeAfterAndCreationIdentity() {
        var source = source(null);
        var replacement = replacement(source, "source", "employee", START);
        var result = EmployeeAppointmentChange.correct(source, replacement, CHANGE, "source correction");
        assertEquals(Kind.CORRECT, result.kind());
        assertSame(source.assignment(), result.before().assignment());
        assertSame(replacement, result.after().assignment());
        assertEquals("unit", result.before().assignment().organizationUnitId());
        assertEquals("corrected-unit", result.after().assignment().organizationUnitId());
        assertNull(result.successor());
        assertEquals(8, result.after().version());
    }

    @Test void correctionRejectsIdentityCreationOrRecordedTimeSubstitution() {
        var source = source(null);
        for (EmployeeAssignment invalid : new EmployeeAssignment[] {
                replacement(source, "other", "employee", START),
                replacement(source, "source", "foreign-employee", START),
                replacement(source, "source", "employee", CHANGE) }) {
            assertThrows(InvalidOrganizationValueException.class,
                    () -> EmployeeAppointmentChange.correct(source, invalid, CHANGE, "invalid"));
        }
        assertThrows(InvalidOrganizationValueException.class,
                () -> EmployeeAppointmentChange.correct(source, source.assignment(), CHANGE, "stale update time"));
    }

    @Test void requiresPositiveVersionsStrictSuccessorAndNoOverflow() {
        var source = source(null);
        assertThrows(InvalidOrganizationValueException.class, () -> new VersionedAssignment(0, source.assignment()));
        assertThrows(InvalidOrganizationValueException.class, () -> new VersionedAssignment(-1, source.assignment()));
        assertThrows(InvalidOrganizationValueException.class, () -> new VersionedAssignment(1, null));
        var replacement = replacement(source, "source", "employee", START);
        assertThrows(InvalidOrganizationValueException.class, () -> new EmployeeAppointmentChange(
                Kind.CORRECT, source, new VersionedAssignment(9, replacement), null, "gap", CHANGE));
        assertThrows(InvalidOrganizationValueException.class, () -> EmployeeAppointmentChange.end(
                new VersionedAssignment(Long.MAX_VALUE, source.assignment()), CHANGE, CHANGE, "overflow"));
    }

    @Test void rejectsMissingReasonTimeAndBackwardRecordedClock() {
        for (String reason : new String[] { null, "", "   " }) {
            assertThrows(InvalidOrganizationValueException.class,
                    () -> EmployeeAppointmentChange.end(source(null), CHANGE, CHANGE, reason));
        }
        assertThrows(InvalidOrganizationValueException.class,
                () -> EmployeeAppointmentChange.end(source(null), CHANGE, null, "missing time"));
        assertThrows(InvalidOrganizationValueException.class,
                () -> EmployeeAppointmentChange.end(source(null), CHANGE, START.minusSeconds(1), "backward"));
        assertThrows(InvalidOrganizationValueException.class,
                () -> EmployeeAppointmentChange.end(null, CHANGE, CHANGE, "missing source"));
    }

    @Test void rejectsForgedTransitionFieldsStatusAndSuccessor() {
        var source = source(null);
        var valid = EmployeeAppointmentChange.end(source, CHANGE, CHANGE, "valid");
        var changed = valid.after().assignment();
        var wrongMembership = assignment("source", "employee", "other-unit", "position", AssignmentType.PRIMARY,
                START, CHANGE, AssignmentStatus.ENDED, START, CHANGE);
        assertThrows(InvalidOrganizationValueException.class, () -> new EmployeeAppointmentChange(
                Kind.END, source, new VersionedAssignment(8, wrongMembership), null, "forged", CHANGE));
        var futureEnded = assignment("source", "employee", "unit", "position", AssignmentType.PRIMARY,
                START, END, AssignmentStatus.ENDED, START, CHANGE);
        assertThrows(InvalidOrganizationValueException.class, () -> new EmployeeAppointmentChange(
                Kind.END, source, new VersionedAssignment(8, futureEnded), null, "future ended", CHANGE));
        var foreign = assignment("next", "other-employee", "unit", "position", AssignmentType.PRIMARY,
                CHANGE, null, AssignmentStatus.ACTIVE, CHANGE, CHANGE);
        assertThrows(InvalidOrganizationValueException.class, () -> new EmployeeAppointmentChange(
                Kind.TRANSFER, source, valid.after(), new VersionedAssignment(1, foreign), "foreign", CHANGE));
        assertThrows(InvalidOrganizationValueException.class, () -> new EmployeeAppointmentChange(
                Kind.CORRECT, source, new VersionedAssignment(8, changed),
                new VersionedAssignment(1, foreign), "unexpected successor", CHANGE));
        assertThrows(InvalidOrganizationValueException.class, () -> new EmployeeAppointmentChange(
                Kind.END, source, valid.after(), new VersionedAssignment(1, foreign), "unexpected successor", CHANGE));
    }

    @Test void inactiveSourceCannotEndOrTransfer() {
        for (AssignmentStatus status : new AssignmentStatus[] {
                AssignmentStatus.SUSPENDED, AssignmentStatus.ENDED, AssignmentStatus.CANCELLED }) {
            var inactive = new VersionedAssignment(1, assignment("source", "employee", "unit", "position",
                    AssignmentType.PRIMARY, START, CHANGE, status, START, START));
            assertThrows(InvalidOrganizationValueException.class,
                    () -> EmployeeAppointmentChange.end(inactive, CHANGE, CHANGE, "inactive"));
            assertThrows(InvalidOrganizationValueException.class,
                    () -> EmployeeAppointmentChange.transfer(inactive, "next", "unit", "position",
                            AssignmentType.ACTING, START.plusSeconds(30), null, CHANGE, "inactive"));
        }
    }
}
