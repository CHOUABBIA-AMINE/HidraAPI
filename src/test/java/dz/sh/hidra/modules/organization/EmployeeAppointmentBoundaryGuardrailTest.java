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

    @Test void currentSelectorsRejectGlobalUnsafeAndUnboundedLists() {
        assertThrows(InvalidOrganizationValueException.class,()->new EmployeeAppointmentQuery.Current(null,null,null,null,0,10,false));
        assertThrows(InvalidOrganizationValueException.class,()->new EmployeeAppointmentQuery.Current(null," employee",null,null,0,10,false));
        for(int page:new int[]{-1,1001})assertThrows(InvalidOrganizationValueException.class,()->new EmployeeAppointmentQuery.Current(null,"employee",null,null,page,10,false));
        for(int size:new int[]{0,101})assertThrows(InvalidOrganizationValueException.class,()->new EmployeeAppointmentQuery.Current(null,null,"unit",null,0,size,false));
        assertThrows(InvalidOrganizationValueException.class,()->new EmployeeAppointmentQuery.Current(null,null,"unit",null,0,100,true));
        assertThrows(InvalidOrganizationValueException.class,()->new EmployeeAppointmentQuery.Current(null,"employee",null,null,1,100,true));
        assertNull(new EmployeeAppointmentQuery.Current(null,"employee",null,null,0,100,true).companyPartyId());
    }

    @Test void currentPageRejectsInconsistentCountsAndRetainsAnImmutableEmptyResult() {
        var empty=new dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.CurrentPage(java.util.List.of(),0,10,0,0,false);
        assertThrows(UnsupportedOperationException.class,()->empty.entries().clear());
        assertThrows(InvalidOrganizationValueException.class,()->new dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.CurrentPage(java.util.List.of(),0,10,1,1,false));
        assertThrows(InvalidOrganizationValueException.class,()->new dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.CurrentPage(java.util.List.of(),0,10,0,1,false));
    }

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

    private static dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.PinnedVersion pinned() {
        var assignment = new dz.sh.hidra.modules.organization.domain.model.EmployeeAssignment(
                "assignment", "employee-private", "unit", "position", AssignmentType.PRIMARY,
                FROM, TO, AssignmentStatus.ACTIVE, FROM, FROM);
        var revision = new dz.sh.hidra.modules.organization.domain.model.EmployeeAppointmentChange
                .VersionedAssignment(1, assignment);
        var binding = new dz.sh.hidra.modules.organization.domain.model.OrganizationUnitCompanyBinding(
                "binding", 2, "unit", "company", FROM);
        return new dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.PinnedVersion(
                new dz.sh.hidra.modules.organization.domain.model.CompanyBoundEmployeeAppointment(revision, binding),
                "policy", 3);
    }

    @Test void authorizedOutputPinsOriginalCompanyAndPolicyWithoutInferringCurrentState() {
        var pinned = pinned();
        assertEquals("company", pinned.companyPartyId());
        assertEquals(1, pinned.appointmentVersion());
        assertEquals("binding", pinned.snapshot().binding().id());
        assertEquals(2, pinned.snapshot().binding().revision());
        assertThrows(InvalidOrganizationValueException.class, () ->
                new dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.PinnedVersion(
                        pinned.snapshot(), "policy", 0));
    }

    @Test void positionResultRedactsEmployeeAndPrivateActorFieldsStructurally() {
        var redacted = dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult
                .PositionVersion.redact(pinned());
        assertEquals("assignment", redacted.assignmentId());
        assertEquals("company", redacted.companyPartyId());
        assertEquals("unit", redacted.organizationUnitId());
        for (var component : redacted.getClass().getRecordComponents()) {
            var name = component.getName().toLowerCase(java.util.Locale.ROOT);
            assertFalse(name.contains("employee") || name.contains("name") || name.contains("actor")
                    || name.contains("permission") || name.contains("token") || name.contains("session"));
        }
    }

    @Test void mutationReceiptDistinguishesNewEventFromHistoricalReplayAndRequiresShape() {
        var p = pinned();
        var result = new dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult
                .MutationReceipt(EmployeeAppointmentCommand.Kind.CREATE, "req", "evt", FROM,
                        false, null, p, null);
        assertFalse(result.historicalReplay());
        assertThrows(InvalidOrganizationValueException.class, () ->
                new dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult
                        .MutationReceipt(EmployeeAppointmentCommand.Kind.TRANSFER, "req", "evt", FROM,
                                true, p, p, null));
        assertThrows(InvalidOrganizationValueException.class, () ->
                new dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult
                        .MutationReceipt(EmployeeAppointmentCommand.Kind.CREATE, "req", "evt", FROM,
                                false, p, p, null));
    }

    @Test void historyPageDefensivelyCopiesEntriesAndNeverDisclosesTotals() {
        var original = new java.util.ArrayList<dz.sh.hidra.modules.organization.application.dto
                .EmployeeAppointmentResult.PrivateHistoryEvent>();
        original.add(new dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult
                .PrivateHistoryEvent("evt", EmployeeAppointmentCommand.Kind.CREATE, FROM, null, pinned(), null));
        var page = new dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult
                .HistoryPage<>(original, "opaque", true);
        original.clear();
        assertEquals(1, page.entries().size());
        assertThrows(UnsupportedOperationException.class, () -> page.entries().clear());
        assertThrows(InvalidOrganizationValueException.class, () ->
                new dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult
                        .HistoryPage<>(java.util.List.of(), null, true));
        assertThrows(InvalidOrganizationValueException.class, () ->
                new dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult
                        .HistoryPage<>(java.util.List.of(), "cursor", false));
        assertEquals(3, page.getClass().getRecordComponents().length);
    }

    @Test void inboundUseCaseReturnsDistinctPrivateAndRedactedHistoryTypes() throws Exception {
        var useCase = dz.sh.hidra.modules.organization.application.port.in
                .EmployeeAppointmentLifecycleUseCase.class;
        assertTrue(useCase.isInterface());
        var privateHistory = useCase.getMethod("employeeHistory", EmployeeAppointmentQuery.History.class)
                .getGenericReturnType().getTypeName();
        var positionHistory = useCase.getMethod("positionHistory", EmployeeAppointmentQuery.History.class)
                .getGenericReturnType().getTypeName();
        assertTrue(privateHistory.contains("PrivateHistoryEvent"));
        assertTrue(positionHistory.contains("PositionHistoryEvent"));
        assertFalse(positionHistory.contains("PrivateHistoryEvent"));
        assertEquals(10, useCase.getDeclaredMethods().length);
        assertEquals(dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.CurrentPage.class,
                useCase.getMethod("currentAssignments",EmployeeAppointmentQuery.Current.class).getReturnType());
    }

    @Test void protectedRepositoryPreviewRejectsUnboundedAndUnorderedOwnerPairs() {
        var pair = new dz.sh.hidra.modules.identity.application.contract.organization
                .OrganizationProtectedAppointmentAuthorizationContract.CompanyUnit("company", "unit");
        var snapshot = new dz.sh.hidra.modules.organization.application.port.out
                .ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview(
                        java.util.List.of(pair), "employee", "assignment", false);
        assertEquals("company", snapshot.companyUnits().get(0).companyPartyId());
        assertDoesNotThrow(snapshot::requireBounded);
        assertThrows(IllegalArgumentException.class, () ->
                new dz.sh.hidra.modules.organization.application.port.out
                        .ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview(
                                java.util.List.of(pair), "employee", "assignment", true).requireBounded());
        assertThrows(IllegalArgumentException.class, () ->
                new dz.sh.hidra.modules.organization.application.port.out
                        .ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview(
                                java.util.List.of(pair, pair), "employee", "assignment", false));
        assertThrows(IllegalArgumentException.class, () ->
                new dz.sh.hidra.modules.organization.application.port.out
                        .ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview(
                                java.util.List.of(pair), "employee", " assignment", false));
    }

    @Test void protectedRepositoryPreviewIsDefensiveAndNeverCredentialsOrAuthority() {
        var pair = new dz.sh.hidra.modules.identity.application.contract.organization
                .OrganizationProtectedAppointmentAuthorizationContract.CompanyUnit("company", "unit");
        var input = new java.util.ArrayList<dz.sh.hidra.modules.identity.application.contract.organization
                .OrganizationProtectedAppointmentAuthorizationContract.CompanyUnit>();
        input.add(pair);
        var preview = new dz.sh.hidra.modules.organization.application.port.out
                .ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview(input, null, null, false);
        input.clear();
        assertEquals(1, preview.companyUnits().size());
        assertThrows(UnsupportedOperationException.class, () -> preview.companyUnits().clear());
        for (var component : preview.getClass().getRecordComponents()) {
            String name = component.getName().toLowerCase(java.util.Locale.ROOT);
            assertFalse(name.contains("permission") || name.contains("token")
                    || name.contains("actor") || name.contains("credential")
                    || name.contains("session") || name.contains("ticket"));
        }
    }

    @Test void protectedRepositoryHasNoUnguardedCrudOrAdapterImports() throws Exception {
        var port = dz.sh.hidra.modules.organization.application.port.out
                .ProtectedEmployeeAppointmentRepositoryPort.class;
        assertTrue(port.isInterface());
        var names = java.util.Arrays.stream(port.getDeclaredMethods())
                .map(java.lang.reflect.Method::getName).toList();
        for (String forbidden : java.util.List.of("save", "delete", "deleteAll", "findAll", "flush",
                "getOne", "getReferenceById")) assertFalse(names.contains(forbidden));
        assertTrue(names.contains("acquire") && names.contains("requireCurrent")
                && names.contains("mutate") && names.contains("positionHistory"));
        var admission = port.getDeclaredClasses();
        assertTrue(java.util.Arrays.stream(admission).anyMatch(c ->
                c.getSimpleName().equals("Admission") && c.isInterface()));
        for (var method : port.getDeclaredMethods()) {
            String types = method.toGenericString();
            assertFalse(types.contains("jakarta.persistence")
                    || types.contains("org.springframework.data")
                    || types.contains("infrastructure.persistence")
                    || types.contains("java.sql.Connection"));
            if (java.util.Set.of("mutate", "positionHistory", "privateHistory",
                    "exactVersion", "readPolicy", "currentAssignments").contains(method.getName()))
                assertEquals(port.getDeclaredClasses()[java.util.Arrays.stream(port.getDeclaredClasses())
                        .map(Class::getSimpleName).toList().indexOf("Admission")],
                        method.getParameterTypes()[0]);
        }
    }

    @Test void protectedRepositorySeparatesPrivateFromRedactedHistoryAndCurrentPolicy() throws Exception {
        var port = dz.sh.hidra.modules.organization.application.port.out
                .ProtectedEmployeeAppointmentRepositoryPort.class;
        assertTrue(port.getMethod("privateHistory",
                dz.sh.hidra.modules.organization.application.port.out
                        .ProtectedEmployeeAppointmentRepositoryPort.Admission.class,
                EmployeeAppointmentQuery.History.class).getGenericReturnType()
                .getTypeName().contains("PrivateHistoryEvent"));
        String position = port.getMethod("positionHistory",
                dz.sh.hidra.modules.organization.application.port.out
                        .ProtectedEmployeeAppointmentRepositoryPort.Admission.class,
                EmployeeAppointmentQuery.History.class).getGenericReturnType().getTypeName();
        assertTrue(position.contains("PositionHistoryEvent"));
        assertFalse(position.contains("PrivateHistoryEvent"));
        assertEquals(dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.PolicyView.class,
                port.getMethod("readPolicy",
                        dz.sh.hidra.modules.organization.application.port.out
                                .ProtectedEmployeeAppointmentRepositoryPort.Admission.class,
                        EmployeeAppointmentQuery.Policy.class).getReturnType());
    }
}
