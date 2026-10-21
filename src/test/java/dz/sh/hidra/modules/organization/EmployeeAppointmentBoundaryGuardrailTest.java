package dz.sh.hidra.modules.organization;

import dz.sh.hidra.modules.organization.application.command.EmployeeAppointmentCommand;
import dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery;
import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Strict immutable selectors only; no permission, current owner proof or storage. */
class EmployeeAppointmentBoundaryGuardrailTest {
    private static final Instant FROM = Instant.parse("2026-10-21T10:00:00Z");
    private static final Instant TO = Instant.parse("2026-10-21T11:00:00Z");

    private static EmployeeAppointmentCommand.Create create() {
        return new EmployeeAppointmentCommand.Create("req", "company", "employee", "unit",
                "position", AssignmentType.PRIMARY, FROM, TO, 3, 4);
    }

    @Test void createRetainsExplicitTypeCompanyAndRevisions() {
        var c = create();
        assertEquals(EmployeeAppointmentCommand.Kind.CREATE, c.kind());
        assertEquals("company", c.companyPartyId());
        assertEquals("employee", c.employeeId());
        assertEquals(3, c.expectedBindingRevision());
        assertEquals(4, c.expectedPolicyRevision());
        assertEquals(TO, c.validTo());
    }

    @Test void createRejectsMissingOrUnsafeSelectorsAndType() {
        assertThrows(InvalidOrganizationValueException.class, () ->
                new EmployeeAppointmentCommand.Create("", "company", "employee", "unit", "position",
                        AssignmentType.PRIMARY, FROM, TO, 1, 1));
        assertThrows(InvalidOrganizationValueException.class, () ->
                new EmployeeAppointmentCommand.Create("req", " company", "employee", "unit", "position",
                        AssignmentType.PRIMARY, FROM, TO, 1, 1));
        assertThrows(InvalidOrganizationValueException.class, () ->
                new EmployeeAppointmentCommand.Create("req", "company", "employee\nother", "unit", "position",
                        AssignmentType.PRIMARY, FROM, TO, 1, 1));
        assertThrows(InvalidOrganizationValueException.class, () ->
                new EmployeeAppointmentCommand.Create("req", "company", "employee", "unit", "position",
                        null, FROM, TO, 1, 1));
    }

    @Test void createRequiresExplicitPositivePinsAndHalfOpenInterval() {
        assertThrows(InvalidOrganizationValueException.class, () ->
                new EmployeeAppointmentCommand.Create("req", "company", "employee", "unit", "position",
                        AssignmentType.ACTING, FROM, TO, 0, 1));
        assertThrows(InvalidOrganizationValueException.class, () ->
                new EmployeeAppointmentCommand.Create("req", "company", "employee", "unit", "position",
                        AssignmentType.ACTING, FROM, FROM, 1, 1));
        assertThrows(InvalidOrganizationValueException.class, () ->
                new EmployeeAppointmentCommand.Create("req", "company", "employee", "unit", "position",
                        AssignmentType.ACTING, TO, FROM, 1, 1));
    }

    @Test void timestampsRejectNanosecondsWithoutSilentRounding() {
        var nanos = FROM.plusNanos(1);
        assertThrows(InvalidOrganizationValueException.class, () ->
                new EmployeeAppointmentCommand.Create("req", "company", "employee", "unit", "position",
                        AssignmentType.ACTING, nanos, TO, 1, 1));
        assertThrows(InvalidOrganizationValueException.class, () ->
                new EmployeeAppointmentCommand.End("req", "company", "assignment", 2, nanos, "reason"));
    }

    @Test void endCarriesCasAndReasonWithoutTreatingItAsPermission() {
        var end = new EmployeeAppointmentCommand.End("req", "company", "assignment", 2, TO, "reason");
        assertEquals(EmployeeAppointmentCommand.Kind.END, end.kind());
        assertEquals(2, end.expectedVersion());
        assertThrows(InvalidOrganizationValueException.class, () ->
                new EmployeeAppointmentCommand.End("req", "company", "assignment", 0, TO, "reason"));
        assertThrows(InvalidOrganizationValueException.class, () ->
                new EmployeeAppointmentCommand.End("req", "company", "assignment", Long.MAX_VALUE, TO, "reason"));
        assertThrows(InvalidOrganizationValueException.class, () ->
                new EmployeeAppointmentCommand.End("req", "company", "assignment", 2, TO, " "));
    }

    @Test void transferRequiresTypedDestinationPinsAndAdjacentIntervalProposalInputs() {
        var transfer = new EmployeeAppointmentCommand.Transfer("req", "company", "source",
                5, "destination-unit", "position", AssignmentType.ACTING,
                FROM, TO, 4, 7, "operational reason");
        assertEquals(EmployeeAppointmentCommand.Kind.TRANSFER, transfer.kind());
        assertEquals(7, transfer.expectedDestinationPolicyRevision());
        assertThrows(InvalidOrganizationValueException.class, () ->
                new EmployeeAppointmentCommand.Transfer("req", "company", "source",
                        5, "destination-unit", "position", AssignmentType.ACTING,
                        FROM, FROM, 4, 7, "reason"));
        assertThrows(InvalidOrganizationValueException.class, () ->
                new EmployeeAppointmentCommand.Transfer("req", "company", "source",
                        5, "destination-unit", "position", null,
                        FROM, TO, 4, 7, "reason"));
    }

    @Test void correctRejectsMissingStatusAndInvalidEndedInterval() {
        var correction = new EmployeeAppointmentCommand.Correct("req", "company", "source",
                1, "unit", "position", AssignmentType.SECONDARY, AssignmentStatus.ACTIVE,
                FROM, null, 2, 3, "correction");
        assertEquals(EmployeeAppointmentCommand.Kind.CORRECT, correction.kind());
        assertThrows(InvalidOrganizationValueException.class, () ->
                new EmployeeAppointmentCommand.Correct("req", "company", "source",
                        1, "unit", "position", AssignmentType.SECONDARY, null,
                        FROM, null, 2, 3, "reason"));
        assertThrows(InvalidOrganizationValueException.class, () ->
                new EmployeeAppointmentCommand.Correct("req", "company", "source",
                        1, "unit", "position", AssignmentType.SECONDARY, AssignmentStatus.ENDED,
                        FROM, null, 2, 3, "reason"));
    }

    @Test void distinctHistoryScopesNeverPermitMixedSelectors() {
        var a = EmployeeAppointmentQuery.History.assignment("company", "assignment", 100, null);
        var e = EmployeeAppointmentQuery.History.employee("company", "employee", 1, "opaque");
        var p = EmployeeAppointmentQuery.History.position("company", "unit", "position", 25, null);
        assertEquals(EmployeeAppointmentQuery.Scope.ASSIGNMENT, a.scope());
        assertEquals(EmployeeAppointmentQuery.Scope.EMPLOYEE, e.scope());
        assertEquals(EmployeeAppointmentQuery.Scope.POSITION, p.scope());
        assertThrows(InvalidOrganizationValueException.class, () ->
                new EmployeeAppointmentQuery.History(EmployeeAppointmentQuery.Scope.POSITION, "company",
                        "assignment", null, "unit", "position", 20, null));
        assertThrows(InvalidOrganizationValueException.class, () ->
                new EmployeeAppointmentQuery.History(null, "company", "assignment",
                        null, null, null, 20, null));
    }

    @Test void pagesRejectUnboundedLimitsAndMalformedCursors() {
        assertThrows(InvalidOrganizationValueException.class, () ->
                EmployeeAppointmentQuery.History.assignment("company", "assignment", 0, null));
        assertThrows(InvalidOrganizationValueException.class, () ->
                EmployeeAppointmentQuery.History.employee("company", "employee", 101, null));
        assertThrows(InvalidOrganizationValueException.class, () ->
                EmployeeAppointmentQuery.History.employee("company", "employee", 1, "\n"));
        assertThrows(InvalidOrganizationValueException.class, () ->
                EmployeeAppointmentQuery.History.employee("company", "employee", 1, "a".repeat(2049)));
    }

    @Test void exactVersionAndPolicyQueriesRequireExplicitSelectors() {
        var exact = new EmployeeAppointmentQuery.ExactVersion("company", "assignment", 4);
        var policy = new EmployeeAppointmentQuery.Policy("company", "unit");
        assertEquals(4, exact.version());
        assertEquals("unit", policy.unitId());
        assertThrows(InvalidOrganizationValueException.class, () ->
                new EmployeeAppointmentQuery.ExactVersion("company", "assignment", 0));
        assertThrows(InvalidOrganizationValueException.class, () ->
                new EmployeeAppointmentQuery.Policy("company", " unit"));
    }

    @Test void contractsContainNoActorPermissionOrCredentials() {
        for (Class<?> kind : new Class<?>[]{
                EmployeeAppointmentCommand.Create.class, EmployeeAppointmentCommand.End.class,
                EmployeeAppointmentCommand.Transfer.class, EmployeeAppointmentCommand.Correct.class,
                EmployeeAppointmentQuery.History.class, EmployeeAppointmentQuery.ExactVersion.class,
                EmployeeAppointmentQuery.Policy.class}) {
            assertTrue(kind.isRecord());
            for (var component : kind.getRecordComponents()) {
                String name = component.getName().toLowerCase(java.util.Locale.ROOT);
                assertFalse(name.contains("permission") || name.contains("token")
                        || name.contains("credential") || name.contains("actor")
                        || name.contains("session"), kind.getSimpleName() + ":" + name);
            }
        }
    }
}
