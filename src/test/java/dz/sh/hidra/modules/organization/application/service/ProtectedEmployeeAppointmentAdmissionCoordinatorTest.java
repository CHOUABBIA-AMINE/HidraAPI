package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.*;
import dz.sh.hidra.modules.organization.application.command.EmployeeAppointmentCommand;
import dz.sh.hidra.modules.organization.application.port.out.ProtectedEmployeeAppointmentRepositoryPort;
import dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import dz.sh.hidra.modules.party.application.contract.organization.OrganizationProtectedPartyEligibilityContract;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/** Pure synthetic descriptor mechanics, not issued native tickets or PostgreSQL proof. */
class ProtectedEmployeeAppointmentAdmissionCoordinatorTest {
    private static final Instant START = Instant.parse("2026-10-21T10:00:00Z");

    private static ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview preview(
            String company, String unit, String employee, String assignment) {
        return new ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview(
                List.of(new CompanyUnit(company, unit)), employee, assignment, false);
    }

    private static EmployeeAppointmentCommand.Create create() {
        return new EmployeeAppointmentCommand.Create("req", "company", "employee", "unit",
                "position", AssignmentType.PRIMARY, START, null, 1, 1);
    }

    @Test void mapsTypedCommandsAndQueriesToFixedNativeActions() {
        var end = new EmployeeAppointmentCommand.End("req", "company", "assignment", 1,
                START.plusSeconds(60), "reason");
        assertEquals(Action.CREATE, ProtectedEmployeeAppointmentAdmissionCoordinator.requestFor(
                create(), null, preview("company", "unit", "employee", null)).action());
        assertEquals(Action.END, ProtectedEmployeeAppointmentAdmissionCoordinator.requestFor(
                end, null, preview("company", "unit", "employee", "assignment")).action());
        assertEquals(Action.READ_HISTORY, ProtectedEmployeeAppointmentAdmissionCoordinator.requestFor(
                null, EmployeeAppointmentQuery.History.position("company", "unit", "position", 20, null),
                preview("company", "unit", null, null)).action());
        assertEquals(Action.READ_POLICY, ProtectedEmployeeAppointmentAdmissionCoordinator.requestFor(
                null, new EmployeeAppointmentQuery.Policy("company", "unit"),
                preview("company", "unit", null, null)).action());
    }

    @Test void rejectsSwappedCompanyUnitEmployeeOrPositionIdentity() {
        assertThrows(AccessDeniedException.class, () ->
                ProtectedEmployeeAppointmentAdmissionCoordinator.requestFor(create(), null,
                        preview("other", "unit", "employee", null)));
        assertThrows(AccessDeniedException.class, () ->
                ProtectedEmployeeAppointmentAdmissionCoordinator.requestFor(create(), null,
                        preview("company", "other", "employee", null)));
        assertThrows(AccessDeniedException.class, () ->
                ProtectedEmployeeAppointmentAdmissionCoordinator.requestFor(create(), null,
                        preview("company", "unit", "other", null)));
        assertThrows(AccessDeniedException.class, () ->
                ProtectedEmployeeAppointmentAdmissionCoordinator.requestFor(null,
                        EmployeeAppointmentQuery.History.position("company", "unit", "position", 2, null),
                        preview("company", "unit", "employee", null)));
    }

    @Test void historicalReadRequiresAllHistoricCompanyAndUnitPermissions() {
        var history = List.of(new CompanyUnit("company", "u1"), new CompanyUnit("other", "u2"));
        var preview = new ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview(
                history, "employee", null, false);
        var request = ProtectedEmployeeAppointmentAdmissionCoordinator.requestFor(null,
                EmployeeAppointmentQuery.History.employee("company", "employee", 20, null), preview);
        assertEquals(history, request.companyUnits());
        assertEquals(List.of(new Requirement(ResourceKind.COMPANY, "company"),
                new Requirement(ResourceKind.COMPANY, "other"),
                new Requirement(ResourceKind.UNIT, "u1"),
                new Requirement(ResourceKind.UNIT, "u2"),
                new Requirement(ResourceKind.EMPLOYEE, "employee")), request.requirements());
    }

    @Test void preparationCallsNativeOwnerOnlyOutsideTransaction() {
        var identity = mock(OrganizationProtectedAppointmentAuthorizationContract.class);
        var party = mock(OrganizationProtectedPartyEligibilityContract.class);
        var storage = mock(ProtectedEmployeeAppointmentRepositoryPort.class);
        var executor = mock(ProtectedTransactionExecutor.class);
        var coordinator = new ProtectedEmployeeAppointmentAdmissionCoordinator(identity, party, storage, executor);
        assertThrows(NullPointerException.class, coordinator::prepare);
        verify(identity).prepare();
        try {
            TransactionSynchronizationManager.setActualTransactionActive(true);
            assertThrows(AccessDeniedException.class, coordinator::prepare);
            verifyNoInteractions(party, storage, executor);
        } finally {
            TransactionSynchronizationManager.clear();
        }
    }

    @Test void absentPhysicalTransactionCannotDiscoverOwnerDescriptors() {
        var identity = mock(OrganizationProtectedAppointmentAuthorizationContract.class);
        var party = mock(OrganizationProtectedPartyEligibilityContract.class);
        var storage = mock(ProtectedEmployeeAppointmentRepositoryPort.class);
        var executor = mock(ProtectedTransactionExecutor.class);
        when(identity.prepare()).thenReturn(mock(OrganizationProtectedAppointmentAuthorizationContract.Prepared.class));
        var coordinator = new ProtectedEmployeeAppointmentAdmissionCoordinator(identity, party, storage, executor);
        var prepared = coordinator.prepare();
        assertThrows(AccessDeniedException.class, () -> coordinator.begin(prepared, create()));
        verify(executor, never()).beginAdmission();
        verify(executor).rejectAdmission();
        verifyNoInteractions(storage, party);
        verify(identity, never()).acquire(any(), any());
    }

    @Test void preparationIsOneUseAndPreviewFailureCannotBecomeAuthority() {
        var identity = mock(OrganizationProtectedAppointmentAuthorizationContract.class);
        var party = mock(OrganizationProtectedPartyEligibilityContract.class);
        var storage = mock(ProtectedEmployeeAppointmentRepositoryPort.class);
        var executor = mock(ProtectedTransactionExecutor.class);
        when(identity.prepare()).thenReturn(mock(OrganizationProtectedAppointmentAuthorizationContract.Prepared.class));
        var coordinator = new ProtectedEmployeeAppointmentAdmissionCoordinator(identity, party, storage, executor);
        var prepared = coordinator.prepare();
        var policy = new EmployeeAppointmentQuery.Policy("company", "unit");
        try {
            TransactionSynchronizationManager.setActualTransactionActive(true);
            TransactionSynchronizationManager.setCurrentTransactionReadOnly(false);
            TransactionSynchronizationManager.initSynchronization();
            when(storage.preview(policy)).thenReturn(preview("other", "unit", null, null));
            assertThrows(AccessDeniedException.class, () -> coordinator.begin(prepared, policy));
            assertThrows(AccessDeniedException.class, () -> coordinator.begin(prepared, policy));
            verify(executor, times(2)).rejectAdmission();
            verify(storage, times(1)).preview(policy);
            verify(identity, never()).acquire(any(), any());
            verifyNoInteractions(party);
        } finally {
            TransactionSynchronizationManager.clear();
        }
    }
}
