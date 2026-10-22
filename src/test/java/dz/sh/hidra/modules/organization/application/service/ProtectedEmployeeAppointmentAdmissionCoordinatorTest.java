/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedEmployeeAppointmentAdmissionCoordinatorTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-22
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Verifies synthetic protected appointment descriptor, owner-order and transaction-ticket rejection mechanics.
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

    private static EmployeeAppointmentCommand.End endCommand() {
        return new EmployeeAppointmentCommand.End("req", "company", "assignment", 2, START, "reason");
    }

    private static EmployeeAppointmentCommand.Transfer transferCommand() {
        return new EmployeeAppointmentCommand.Transfer("req", "company", "assignment", 2,
                "zz-unit", "position", AssignmentType.PRIMARY, START, null, 1, 1, "reason");
    }

    private static EmployeeAppointmentCommand.Correct correctCommand(boolean moved) {
        return new EmployeeAppointmentCommand.Correct("req", "company", "assignment", 2,
                moved ? "zz-unit" : "unit", "position", AssignmentType.PRIMARY,
                dz.sh.hidra.modules.organization.domain.value.AssignmentStatus.ACTIVE,
                START, null, 1, 1, "reason");
    }

    /** All owner tickets are mocked; this fixture is not native/PostgreSQL acceptance. */
    private static final class WriteFixture implements AutoCloseable {
        final OrganizationProtectedAppointmentAuthorizationContract identity =
                mock(OrganizationProtectedAppointmentAuthorizationContract.class);
        final OrganizationProtectedPartyEligibilityContract party = mock(OrganizationProtectedPartyEligibilityContract.class);
        final ProtectedEmployeeAppointmentRepositoryPort storage = mock(ProtectedEmployeeAppointmentRepositoryPort.class);
        final ProtectedTransactionExecutor executor = mock(ProtectedTransactionExecutor.class);
        final OrganizationProtectedAppointmentAuthorizationContract.Admission nativeTicket =
                mock(OrganizationProtectedAppointmentAuthorizationContract.Admission.class);
        final OrganizationProtectedPartyEligibilityContract.Admission partyTicket =
                mock(OrganizationProtectedPartyEligibilityContract.Admission.class);
        final ProtectedEmployeeAppointmentRepositoryPort.Admission storageTicket =
                mock(ProtectedEmployeeAppointmentRepositoryPort.Admission.class);
        final ProtectedEmployeeAppointmentAdmissionCoordinator coordinator =
                new ProtectedEmployeeAppointmentAdmissionCoordinator(identity, party, storage, executor);
        final ProtectedEmployeeAppointmentAdmissionCoordinator.Prepared prepared;
        final EmployeeAppointmentCommand command;
        final ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview descriptors;
        final Request request;
        final Contribution sample;
        final Object resourceKey = new Object();
        final ConnectionHolder holder = new ConnectionHolder(mock(java.sql.Connection.class));

        WriteFixture() { this(create(), ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.CURRENT); }

        WriteFixture(EmployeeAppointmentCommand command, ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse use) {
            this.command = command;
            boolean moved = command instanceof EmployeeAppointmentCommand.Transfer
                    || command instanceof EmployeeAppointmentCommand.Correct c
                        && c.correctedOrganizationUnitId().equals("zz-unit");
            descriptors = new ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview(
                    moved ? List.of(new CompanyUnit("company", "unit"), new CompanyUnit("company", "zz-unit"))
                            : List.of(new CompanyUnit("company", "unit")),
                    "employee", command instanceof EmployeeAppointmentCommand.Create ? null : "assignment", false);
            request = ProtectedEmployeeAppointmentAdmissionCoordinator.requestFor(command, null, descriptors);
            sample = permitted(request);
            when(identity.prepare()).thenReturn(mock(OrganizationProtectedAppointmentAuthorizationContract.Prepared.class));
            prepared = coordinator.prepare();
            TransactionSynchronizationManager.setActualTransactionActive(true);
            TransactionSynchronizationManager.setCurrentTransactionReadOnly(false);
            TransactionSynchronizationManager.initSynchronization();
            TransactionSynchronizationManager.bindResource(resourceKey, holder);
            when(storage.preview(command)).thenReturn(descriptors);
            when(identity.acquire(any(), eq(request))).thenReturn(nativeTicket);
            when(party.acquire("company")).thenReturn(partyTicket);
            when(storage.acquire(nativeTicket, List.of(partyTicket), descriptors)).thenReturn(storageTicket);
            when(identity.contribute(nativeTicket)).thenReturn(sample);
            when(storage.referenceUse(storageTicket, command, sample)).thenReturn(use);
            when(party.read(partyTicket)).thenReturn(
                    new OrganizationProtectedPartyEligibilityContract.Contribution("company", 9, 17, 23));
        }

        ProtectedEmployeeAppointmentAdmissionCoordinator.WriteAdmission acquire() {
            return coordinator.acquireWrite(prepared, command);
        }

        @Override public void close() {
            TransactionSynchronizationManager.unbindResourceIfPossible(resourceKey);
            TransactionSynchronizationManager.clear();
        }
    }

    @Test void writeLocksIdentityPartyOrganizationBeforeOneSampleAndEligibilityRead() {
        try (var f = new WriteFixture()) {
            f.acquire();
            var order = inOrder(f.executor, f.storage, f.identity, f.party);
            order.verify(f.executor).beginAdmission();
            order.verify(f.storage).preview(f.command);
            order.verify(f.identity).acquire(any(), eq(f.request));
            order.verify(f.party).acquire("company");
            order.verify(f.storage).acquire(f.nativeTicket, List.of(f.partyTicket), f.descriptors);
            order.verify(f.storage).preview(f.command);
            order.verify(f.identity).contribute(f.nativeTicket);
            order.verify(f.storage).referenceUse(f.storageTicket, f.command, f.sample);
            order.verify(f.party).read(f.partyTicket);
            order.verify(f.identity).requireCurrent(f.nativeTicket);
            order.verify(f.party).requireCurrent(f.partyTicket);
            order.verify(f.storage).requireCurrent(f.storageTicket);
            verify(f.identity, times(1)).contribute(f.nativeTicket);
            verify(f.storage, never()).mutate(any(), any());
        }
    }

    @Test void newCreateTransferAndMovedCorrectionConsumeCurrentPartyEligibility() {
        for (var command : List.of(create(), transferCommand(), correctCommand(true))) {
            try (var f = new WriteFixture(command, ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.CURRENT)) {
                f.acquire();
                verify(f.party).read(f.partyTicket);
                verify(f.party).requireCurrent(f.partyTicket);
                verify(f.storage, never()).mutate(any(), any());
            }
        }
    }

    @Test void retainedEndAndSameUnitCorrectionDoNotDemandCurrentPartyStatus() {
        for (var command : List.of(endCommand(), correctCommand(false))) {
            try (var f = new WriteFixture(command, ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.RETAINED)) {
                doThrow(new IllegalStateException("retired company")).when(f.party).read(any());
                f.acquire();
                verify(f.party).acquire("company");
                verify(f.party, never()).read(any());
                verify(f.party, never()).requireCurrent(any());
                verify(f.identity).requireCurrent(f.nativeTicket);
            }
        }
    }

    @Test void exactReplayOfEveryCommandRequiresFreshNativeAuthorityWithoutActiveParty() {
        for (var command : List.of(create(), endCommand(), transferCommand(), correctCommand(true))) {
            try (var f = new WriteFixture(command, ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY)) {
                doThrow(new IllegalStateException("retired company")).when(f.party).read(any());
                f.acquire();
                verify(f.identity).acquire(any(), eq(f.request));
                verify(f.identity).contribute(f.nativeTicket);
                verify(f.identity).requireCurrent(f.nativeTicket);
                verify(f.party, never()).read(any());
                verify(f.party, never()).requireCurrent(any());
                verify(f.storage, never()).mutate(any(), any());
            }
        }
    }

    @Test void invalidNativeWriteTicketStopsBeforePartyOrStorageAcquisition() {
        try (var f = new WriteFixture()) {
            when(f.identity.acquire(any(), any())).thenReturn(null);
            assertThrows(AccessDeniedException.class, f::acquire);
            verifyNoInteractions(f.party);
            verify(f.storage, never()).acquire(any(), anyList(), any());
            verify(f.executor).rejectAdmission();
        }
    }

    @Test void missingPartyOrStorageTicketVetoesTheOriginalWriteTransaction() {
        for (boolean absentParty : List.of(true, false)) {
            try (var f = new WriteFixture()) {
                if (absentParty) when(f.party.acquire(any())).thenReturn(null);
                else when(f.storage.acquire(any(), anyList(), any())).thenReturn(null);
                assertThrows(AccessDeniedException.class, f::acquire);
                assertTrue(f.holder.isRollbackOnly());
                for (var callback : TransactionSynchronizationManager.getSynchronizations())
                    assertThrows(AccessDeniedException.class, () -> callback.beforeCommit(false));
                verify(f.identity, never()).contribute(any());
                verify(f.party, never()).read(any());
            }
        }
    }

    @Test void changedMissingOrOverflowedLockedWriteClosureCannotConsumeNativeSample() {
        for (int variant = 0; variant < 3; variant++) {
            try (var f = new WriteFixture()) {
                var changed = variant == 0 ? preview("company", "other", "employee", null)
                        : variant == 1 ? null : new ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview(
                                f.descriptors.companyUnits(), "employee", null, true);
                when(f.storage.preview(f.command)).thenReturn(f.descriptors, changed);
                assertThrows(AccessDeniedException.class, f::acquire);
                verify(f.identity, never()).contribute(any());
                verify(f.storage, never()).referenceUse(any(), any(), any());
                verify(f.party, never()).read(any());
                assertTrue(f.holder.isRollbackOnly());
            }
        }
    }

    @Test void deniedUncertainOrSubstitutedWriteSampleCannotClassifyAReceipt() {
        for (int variant = 0; variant < 3; variant++) {
            try (var f = new WriteFixture()) {
                var request = variant == 2 ? new Request(Action.END, f.request.companyUnits(), "employee") : f.request;
                var decision = variant == 0 ? Decision.DENY : variant == 1 ? Decision.INDETERMINATE : Decision.PERMIT;
                when(f.identity.contribute(f.nativeTicket)).thenReturn(new Contribution(request,
                        request.requirements().stream().map(r -> new ResourceDecision(r, decision)).toList(),
                        "binding", "session", "user", START, START.plusSeconds(1), 17, 23));
                assertThrows(AccessDeniedException.class, f::acquire);
                verify(f.storage, never()).referenceUse(any(), any(), any());
                verify(f.party, never()).read(any());
                assertTrue(f.holder.isRollbackOnly());
            }
        }
    }

    @Test void absentConflictingOrImpossibleReferenceUseCannotQualifyWrite() {
        for (int variant = 0; variant < 4; variant++) {
            var command = variant == 3 ? endCommand() : create();
            var use = variant == 3 ? ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.CURRENT
                    : ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.RETAINED;
            try (var f = new WriteFixture(command, use)) {
                if (variant == 0) when(f.storage.referenceUse(any(), any(), any())).thenReturn(null);
                if (variant == 1) when(f.storage.referenceUse(any(), any(), any()))
                        .thenThrow(new IllegalStateException("request key conflict"));
                assertThrows(AccessDeniedException.class, f::acquire);
                verify(f.party, never()).read(any());
                assertTrue(f.holder.isRollbackOnly());
            }
        }
    }

    @Test void wrongPartyIdentityOrPhysicalCoordinatesCannotQualifyCurrentReference() {
        for (int variant = 0; variant < 3; variant++) {
            try (var f = new WriteFixture()) {
                when(f.party.read(f.partyTicket)).thenReturn(new OrganizationProtectedPartyEligibilityContract.Contribution(
                        variant == 0 ? "other" : "company", 9, variant == 1 ? 18 : 17, variant == 2 ? 24 : 23));
                assertThrows(AccessDeniedException.class, f::acquire);
                assertTrue(f.holder.isRollbackOnly());
                verify(f.storage, never()).mutate(any(), any());
            }
        }
    }

    @Test void absentOrInactivePartyContributionPoisonsCurrentReferenceTransaction() {
        for (boolean missing : List.of(true, false)) {
            try (var f = new WriteFixture()) {
                if (missing) when(f.party.read(f.partyTicket)).thenReturn(null);
                else when(f.party.read(f.partyTicket)).thenThrow(new IllegalStateException("inactive"));
                assertThrows(AccessDeniedException.class, f::acquire);
                assertTrue(f.holder.isRollbackOnly());
            }
        }
    }

    @Test void changedWriteClassificationCannotTurnCurrentReferenceIntoReplay() {
        try (var f = new WriteFixture()) {
            var ticket = f.acquire();
            when(f.storage.referenceUse(any(), any(), any()))
                    .thenReturn(ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY);
            assertThrows(AccessDeniedException.class, () -> f.coordinator.requireCurrentWrite(ticket));
            assertTrue(f.holder.isRollbackOnly());
            assertThrows(AccessDeniedException.class, () -> ticket.beforeCommit(false));
        }
    }

    @Test void changedIdentityPartyOrRepositoryEvidencePermanentlyInvalidatesWrite() {
        for (int variant = 0; variant < 3; variant++) {
            try (var f = new WriteFixture()) {
                var ticket = f.acquire();
                if (variant == 0) doThrow(new IllegalStateException("revoked")).when(f.identity).requireCurrent(any());
                if (variant == 1) doThrow(new IllegalStateException("generation")).when(f.party).requireCurrent(any());
                if (variant == 2) doThrow(new IllegalStateException("lost fence")).when(f.storage).requireCurrent(any());
                assertThrows(AccessDeniedException.class, () -> f.coordinator.requireCurrentWrite(ticket));
                assertTrue(f.holder.isRollbackOnly());
                clearInvocations(f.identity, f.party, f.storage);
                assertThrows(AccessDeniedException.class, () -> f.coordinator.requireCurrentWrite(ticket));
                verifyNoInteractions(f.identity, f.party, f.storage);
            }
        }
    }

    @Test void writeOwnerCallbackCannotResurrectAnInvalidatedTicket() {
        try (var f = new WriteFixture()) {
            var ticket = f.acquire();
            doAnswer(call -> { ticket.suspend(); return null; }).when(f.storage).requireCurrent(any());
            assertThrows(AccessDeniedException.class, () -> f.coordinator.requireCurrentWrite(ticket));
            assertTrue(f.holder.isRollbackOnly());
            assertThrows(AccessDeniedException.class, () -> ticket.beforeCommit(false));
        }
    }

    @Test void foreignThreadWriteRecheckPoisonsOriginalHolderAndCommit() throws Exception {
        try (var f = new WriteFixture()) {
            var ticket = f.acquire();
            var failure = new java.util.concurrent.atomic.AtomicReference<Throwable>();
            var thread = new Thread(() -> {
                try { f.coordinator.requireCurrentWrite(ticket); }
                catch (Throwable rejected) { failure.set(rejected); }
            });
            thread.start();
            thread.join(5000);
            assertFalse(thread.isAlive());
            assertInstanceOf(AccessDeniedException.class, failure.get());
            assertTrue(f.holder.isRollbackOnly());
            assertThrows(AccessDeniedException.class, () -> ticket.beforeCommit(false));
        }
    }

    @Test void foreignCoordinatorWriteRejectionPoisonsTheIssuingTransaction() {
        try (var f = new WriteFixture()) {
            var ticket = f.acquire();
            var other = new ProtectedEmployeeAppointmentAdmissionCoordinator(f.identity, f.party, f.storage, f.executor);
            clearInvocations(f.identity, f.party, f.storage);
            assertThrows(AccessDeniedException.class, () -> other.requireCurrentWrite(ticket));
            verifyNoInteractions(f.identity, f.party, f.storage);
            assertTrue(f.holder.isRollbackOnly());
        }
    }

    @Test void suspendedCompletedOrReplacedTransactionCannotReuseWriteHandle() {
        for (int variant = 0; variant < 4; variant++) {
            try (var f = new WriteFixture()) {
                var ticket = f.acquire();
                if (variant == 0) ticket.suspend();
                if (variant == 1) ticket.afterCompletion(TransactionSynchronization.STATUS_COMMITTED);
                if (variant == 2) {
                    TransactionSynchronizationManager.clearSynchronization();
                    TransactionSynchronizationManager.initSynchronization();
                }
                if (variant == 3) {
                    TransactionSynchronizationManager.unbindResource(f.resourceKey);
                    TransactionSynchronizationManager.bindResource(f.resourceKey, new ConnectionHolder(mock(java.sql.Connection.class)));
                }
                clearInvocations(f.identity, f.party, f.storage);
                assertThrows(AccessDeniedException.class, () -> f.coordinator.requireCurrentWrite(ticket));
                verifyNoInteractions(f.identity, f.party, f.storage);
                assertTrue(f.holder.isRollbackOnly());
            }
        }
    }

    @Test void readonlyOrReusedPreparationCannotStartWriteOwnerQualification() {
        try (var f = new WriteFixture()) {
            TransactionSynchronizationManager.setCurrentTransactionReadOnly(true);
            assertThrows(AccessDeniedException.class, f::acquire);
            verify(f.storage, never()).preview(any(EmployeeAppointmentCommand.class));
            verifyNoInteractions(f.party);
        }
        try (var f = new WriteFixture()) {
            f.acquire();
            assertThrows(AccessDeniedException.class, f::acquire);
            verify(f.identity, times(1)).acquire(any(), any());
            verify(f.party, times(1)).acquire(any());
        }
    }

    @Test void writeHandleIsOpaqueAndItsCommitVetoPerformsNoOwnerSql() {
        var type = ProtectedEmployeeAppointmentAdmissionCoordinator.WriteAdmission.class;
        assertEquals(0, type.getConstructors().length);
        for (var constructor : type.getDeclaredConstructors())
            assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        for (var field : type.getDeclaredFields())
            assertTrue(java.lang.reflect.Modifier.isPrivate(field.getModifiers()));
        try (var f = new WriteFixture()) {
            var ticket = f.acquire();
            assertEquals("WriteAdmission[opaque appointment owners]", ticket.toString());
            clearInvocations(f.identity, f.party, f.storage, f.executor);
            ticket.beforeCommit(false);
            verifyNoInteractions(f.identity, f.party, f.storage, f.executor);
            f.holder.setRollbackOnly();
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
