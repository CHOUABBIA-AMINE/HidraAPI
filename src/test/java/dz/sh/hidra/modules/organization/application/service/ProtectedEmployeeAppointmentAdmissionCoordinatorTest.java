/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedEmployeeAppointmentAdmissionCoordinatorTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-21
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Verifies synthetic protected appointment descriptor, owner-order and read-ticket rejection mechanics.
 *
 */
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
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.transaction.support.TransactionSynchronization;
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

    private static Contribution permitted(Request request) {
        return new Contribution(request, request.requirements().stream()
                .map(r -> new ResourceDecision(r, Decision.PERMIT)).toList(),
                "binding", "session", "user", START, START.plusSeconds(1), 17, 23);
    }

    /** Transaction callbacks/owner ports are mocked; no actual database proof. */
    private static final class ReadFixture implements AutoCloseable {
        final OrganizationProtectedAppointmentAuthorizationContract identity =
                mock(OrganizationProtectedAppointmentAuthorizationContract.class);
        final OrganizationProtectedPartyEligibilityContract party =
                mock(OrganizationProtectedPartyEligibilityContract.class);
        final ProtectedEmployeeAppointmentRepositoryPort storage = mock(ProtectedEmployeeAppointmentRepositoryPort.class);
        final ProtectedTransactionExecutor executor = mock(ProtectedTransactionExecutor.class);
        final OrganizationProtectedAppointmentAuthorizationContract.Admission nativeTicket =
                mock(OrganizationProtectedAppointmentAuthorizationContract.Admission.class);
        final ProtectedEmployeeAppointmentRepositoryPort.Admission storageTicket =
                mock(ProtectedEmployeeAppointmentRepositoryPort.Admission.class);
        final ProtectedEmployeeAppointmentAdmissionCoordinator coordinator =
                new ProtectedEmployeeAppointmentAdmissionCoordinator(identity, party, storage, executor);
        final ProtectedEmployeeAppointmentAdmissionCoordinator.Prepared prepared;
        final EmployeeAppointmentQuery query;
        final ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview descriptors;
        final Request request;
        final Object resourceKey = new Object();
        final ConnectionHolder holder = new ConnectionHolder(mock(java.sql.Connection.class));

        ReadFixture() {
            this(EmployeeAppointmentQuery.History.assignment("company", "assignment", 20, null),
                    preview("company", "unit", "employee", "assignment"));
        }

        ReadFixture(EmployeeAppointmentQuery query,
                    ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview descriptors) {
            this.query = query;
            this.descriptors = descriptors;
            this.request = ProtectedEmployeeAppointmentAdmissionCoordinator.requestFor(null, query, descriptors);
            when(identity.prepare()).thenReturn(mock(OrganizationProtectedAppointmentAuthorizationContract.Prepared.class));
            prepared = coordinator.prepare();
            TransactionSynchronizationManager.setActualTransactionActive(true);
            TransactionSynchronizationManager.setCurrentTransactionReadOnly(false);
            TransactionSynchronizationManager.initSynchronization();
            TransactionSynchronizationManager.bindResource(resourceKey, holder);
            when(storage.preview(query)).thenReturn(descriptors);
            when(identity.acquire(any(), eq(request))).thenReturn(nativeTicket);
            when(storage.acquire(eq(nativeTicket), eq(List.of()), eq(descriptors))).thenReturn(storageTicket);
            when(identity.contribute(nativeTicket)).thenReturn(permitted(request));
        }

        ProtectedEmployeeAppointmentAdmissionCoordinator.ReadAdmission acquire() {
            return coordinator.acquireRead(prepared, query);
        }

        @Override public void close() {
            TransactionSynchronizationManager.unbindResourceIfPossible(resourceKey);
            TransactionSynchronizationManager.clear();
        }
    }

    @Test void readAdmissionLocksIdentityBeforeOrganizationAndSamplesAfterLockedClosure() {
        try (var f = new ReadFixture()) {
            var ticket = f.acquire();
            var order = inOrder(f.executor, f.storage, f.identity);
            order.verify(f.executor).beginAdmission();
            order.verify(f.storage).preview(f.query);
            order.verify(f.identity).acquire(any(), eq(f.request));
            order.verify(f.storage).acquire(f.nativeTicket, List.of(), f.descriptors);
            order.verify(f.storage).preview(f.query);
            order.verify(f.identity).contribute(f.nativeTicket);
            order.verify(f.identity).requireCurrent(f.nativeTicket);
            order.verify(f.storage).requireCurrent(f.storageTicket);
            f.coordinator.requireCurrentRead(ticket);
            verify(f.identity, times(1)).contribute(f.nativeTicket);
            verifyNoInteractions(f.party);
            // Owner qualification alone neither seals the executor nor authorizes a write.
            verify(f.executor, never()).sealAdmission();
            verify(f.storage, never()).mutate(any(), any());
        }
    }

    @Test void changedLockedHistoryClosureIsDeniedBeforeSampling() {
        try (var f = new ReadFixture()) {
            when(f.storage.preview(f.query)).thenReturn(f.descriptors,
                    preview("company", "other-unit", "employee", "assignment"));
            assertThrows(AccessDeniedException.class, f::acquire);
            verify(f.identity, never()).contribute(any());
            verify(f.executor).rejectAdmission();
            verify(f.storage, never()).privateHistory(any(), any());
        }
    }

    @Test void absentOrOverflowedLockedDescriptorsCannotQualify() {
        for (boolean absent : List.of(true, false)) {
            try (var f = new ReadFixture()) {
                var invalid = absent ? null : new ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview(
                        f.descriptors.companyUnits(), "employee", "assignment", true);
                when(f.storage.preview(f.query)).thenReturn(f.descriptors, invalid);
                assertThrows(AccessDeniedException.class, f::acquire);
                verify(f.identity, never()).contribute(any());
                verify(f.executor).rejectAdmission();
            }
        }
    }

    @Test void denyUncertaintyOrSubstitutedNativeRequestCannotQualifyRead() {
        for (int variant = 0; variant < 3; variant++) {
            try (var f = new ReadFixture()) {
                Request request = variant == 2
                        ? new Request(Action.READ_CURRENT, f.request.companyUnits(), "employee") : f.request;
                Decision decision = variant == 0 ? Decision.DENY
                        : variant == 1 ? Decision.INDETERMINATE : Decision.PERMIT;
                when(f.identity.contribute(f.nativeTicket)).thenReturn(new Contribution(request,
                        request.requirements().stream().map(r -> new ResourceDecision(r, decision)).toList(),
                        "binding", "session", "user", START, START.plusSeconds(1), 17, 23));
                assertThrows(AccessDeniedException.class, f::acquire);
                assertTrue(f.holder.isRollbackOnly());
                var callback = TransactionSynchronizationManager.getSynchronizations().get(0);
                assertThrows(AccessDeniedException.class, () -> callback.beforeCommit(false));
                verify(f.storage, never()).privateHistory(any(), any());
            }
        }
    }

    @Test void failedNativeAcquisitionCannotReachOrganizationOrParty() {
        try (var f = new ReadFixture()) {
            when(f.identity.acquire(any(), any())).thenThrow(new IllegalStateException("owner unavailable"));
            assertThrows(AccessDeniedException.class, f::acquire);
            verify(f.storage, never()).acquire(any(), anyList(), any());
            verify(f.identity, never()).contribute(any());
            verifyNoInteractions(f.party);
            verify(f.executor).rejectAdmission();
        }
    }

    @Test void missingRepositoryTicketCannotConsumeNativeSample() {
        try (var f = new ReadFixture()) {
            when(f.storage.acquire(any(), anyList(), any())).thenReturn(null);
            assertThrows(AccessDeniedException.class, f::acquire);
            verify(f.identity, never()).contribute(any());
            verify(f.executor).rejectAdmission();
        }
    }

    @Test void changedNativeOrStorageEvidenceInvalidatesHandleAndVetoesCommit() {
        for (boolean nativeFailure : List.of(true, false)) {
            try (var f = new ReadFixture()) {
                var ticket = f.acquire();
                if (nativeFailure) doThrow(new IllegalStateException("native expired"))
                        .when(f.identity).requireCurrent(f.nativeTicket);
                else doThrow(new IllegalStateException("ownership changed"))
                        .when(f.storage).requireCurrent(f.storageTicket);
                assertThrows(AccessDeniedException.class, () -> f.coordinator.requireCurrentRead(ticket));
                assertTrue(f.holder.isRollbackOnly());
                assertThrows(AccessDeniedException.class, () -> ticket.beforeCommit(false));
                clearInvocations(f.identity, f.storage);
                assertThrows(AccessDeniedException.class, () -> f.coordinator.requireCurrentRead(ticket));
                verifyNoInteractions(f.identity, f.storage);
            }
        }
    }

    @Test void foreignThreadReadHandlePoisonsOriginalHolderAndCommitVeto() throws Exception {
        try (var f = new ReadFixture()) {
            var ticket = f.acquire();
            var failure = new java.util.concurrent.atomic.AtomicReference<Throwable>();
            var thread = new Thread(() -> {
                try { f.coordinator.requireCurrentRead(ticket); }
                catch (Throwable expected) { failure.set(expected); }
            });
            thread.start();
            thread.join(5000);
            assertFalse(thread.isAlive());
            assertInstanceOf(AccessDeniedException.class, failure.get());
            assertTrue(f.holder.isRollbackOnly());
            assertThrows(AccessDeniedException.class, () -> ticket.beforeCommit(false));
        }
    }

    @Test void suspendedCompletedOrDifferentTransactionCannotReuseReadHandle() {
        for (int variant = 0; variant < 3; variant++) {
            try (var f = new ReadFixture()) {
                var ticket = f.acquire();
                if (variant == 0) ticket.suspend();
                else if (variant == 1) ticket.afterCompletion(TransactionSynchronization.STATUS_COMMITTED);
                else {
                    TransactionSynchronizationManager.clearSynchronization();
                    TransactionSynchronizationManager.initSynchronization();
                }
                clearInvocations(f.identity, f.storage);
                assertThrows(AccessDeniedException.class, () -> f.coordinator.requireCurrentRead(ticket));
                verifyNoInteractions(f.identity, f.storage);
            }
        }
    }

    @Test void foreignCoordinatorCannotReuseAnIssuedReadHandle() {
        try (var f = new ReadFixture()) {
            var ticket = f.acquire();
            var other = new ProtectedEmployeeAppointmentAdmissionCoordinator(f.identity, f.party, f.storage, f.executor);
            clearInvocations(f.identity, f.storage);
            assertThrows(AccessDeniedException.class, () -> other.requireCurrentRead(ticket));
            verifyNoInteractions(f.identity, f.storage);
        }
    }

    @Test void readOnlyTransactionAndReusedPreparationCannotAcquireReadAuthority() {
        try (var f = new ReadFixture()) {
            TransactionSynchronizationManager.setCurrentTransactionReadOnly(true);
            assertThrows(AccessDeniedException.class, f::acquire);
            verify(f.storage, never()).preview(any(EmployeeAppointmentQuery.class));
            verify(f.identity, never()).acquire(any(), any());
        }
        try (var f = new ReadFixture()) {
            f.acquire();
            assertThrows(AccessDeniedException.class, f::acquire);
            verify(f.identity, times(1)).acquire(any(), any());
            verify(f.identity, times(1)).contribute(any());
        }
    }

    @Test void historicalCompaniesRemainCompleteWithoutCurrentPartyEligibility() {
        var descriptors = new ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview(
                List.of(new CompanyUnit("company", "u1"), new CompanyUnit("retired-company", "u2")),
                "employee", null, false);
        try (var f = new ReadFixture(EmployeeAppointmentQuery.History.employee(
                "company", "employee", 20, null), descriptors)) {
            f.acquire();
            verify(f.identity).acquire(any(), eq(f.request));
            assertEquals(5, f.request.requirements().size());
            verifyNoInteractions(f.party);
        }
    }

    @Test void policyPositionAndExactVersionReadsKeepTheirDistinctSelectors() {
        List<EmployeeAppointmentQuery> queries = List.of(new EmployeeAppointmentQuery.Policy("company", "unit"),
                EmployeeAppointmentQuery.History.position("company", "unit", "position", 10, null),
                new EmployeeAppointmentQuery.ExactVersion("company", "assignment", 3));
        for (var query : queries) {
            boolean exact = query instanceof EmployeeAppointmentQuery.ExactVersion;
            var descriptors = preview("company", "unit", exact ? "employee" : null, exact ? "assignment" : null);
            try (var f = new ReadFixture(query, descriptors)) {
                f.acquire();
                verify(f.identity).acquire(any(), eq(f.request));
                assertEquals(exact ? "employee" : null, f.request.employeeId());
                verifyNoInteractions(f.party);
            }
        }
    }

    @Test void invalidationDuringOwnerRecheckCannotRestoreReadHandle() {
        try (var f = new ReadFixture()) {
            var ticket = f.acquire();
            doAnswer(call -> { ticket.suspend(); return null; }).when(f.storage).requireCurrent(f.storageTicket);
            assertThrows(AccessDeniedException.class, () -> f.coordinator.requireCurrentRead(ticket));
            assertThrows(AccessDeniedException.class, () -> ticket.beforeCommit(false));
        }
    }

    @Test void readTicketCannotBeConstructedOrExposeOwnerCredentials() {
        var type = ProtectedEmployeeAppointmentAdmissionCoordinator.ReadAdmission.class;
        assertEquals(0, type.getConstructors().length);
        for (var constructor : type.getDeclaredConstructors())
            assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        for (var field : type.getDeclaredFields())
            assertTrue(java.lang.reflect.Modifier.isPrivate(field.getModifiers()));
        try (var f = new ReadFixture()) {
            var ticket = f.acquire();
            assertEquals("ReadAdmission[opaque appointment owners]", ticket.toString());
            clearInvocations(f.identity, f.storage, f.executor);
            ticket.beforeCommit(false);
            verifyNoInteractions(f.identity, f.storage, f.executor);
        }
    }
}
