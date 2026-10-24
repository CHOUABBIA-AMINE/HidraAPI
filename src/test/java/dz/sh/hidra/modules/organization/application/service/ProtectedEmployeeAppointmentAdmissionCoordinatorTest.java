/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedEmployeeAppointmentAdmissionCoordinatorTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-23
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
import java.util.Set;
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

    private static dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.MutationReceipt replayResult() {
        var result=mock(dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.MutationReceipt.class);
        when(result.historicalReplay()).thenReturn(true);when(result.kind()).thenReturn(EmployeeAppointmentCommand.Kind.CREATE);
        when(result.requestId()).thenReturn("req");return result;
    }

    @Test void appliedReplaySealsAdmissionWithoutMutationAndRevalidatesItsDetachedResultBeforeGuard() {
        try(var f=new WriteFixture(create(),ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY)) {
            var result=replayResult();when(f.storage.mutate(f.storageTicket,f.command)).thenReturn(result);
            when(f.storage.prepareFinalReplay(f.storageTicket)).thenReturn(result);
            var ticket=f.acquire();assertSame(result,f.coordinator.apply(ticket));
            verify(f.executor).sealAdmission();verify(f.executor,never()).beginProvisional(any(),any());
            verify(f.storage,never()).prepareCommitProof(any());verify(f.storage,never()).contributeParty(any());
            f.coordinator.prepareFinal(ticket);clearInvocations(f.storage,f.identity,f.party);
            f.coordinator.finalizeOperation(ticket);ticket.beforeCommit(false);
            verify(f.storage).finalizeReplay(f.storageTicket);verifyNoMoreInteractions(f.storage);
            verifyNoInteractions(f.identity,f.party);
        }
    }

    @Test void changedReplayResultAfterManagedFlushPoisonsTheOriginalTransaction() {
        try(var f=new WriteFixture(create(),ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY)) {
            var before=replayResult();var changed=replayResult();
            when(f.storage.mutate(f.storageTicket,f.command)).thenReturn(before);
            when(f.storage.prepareFinalReplay(f.storageTicket)).thenReturn(changed);
            var ticket=f.acquire();assertSame(before,f.coordinator.apply(ticket));
            assertThrows(AccessDeniedException.class,()->f.coordinator.prepareFinal(ticket));
            assertTrue(f.holder.isRollbackOnly());assertThrows(AccessDeniedException.class,()->ticket.beforeCommit(false));
            verify(f.storage,never()).finalizeReplay(any());
        }
    }

    @Test void repeatedApplicationAndWrongResultClassificationCannotReachFinalGuard() {
        for(boolean repeated:List.of(false,true))try(var f=new WriteFixture(create(),ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY)) {
            var result=replayResult();if(!repeated)when(result.historicalReplay()).thenReturn(false);
            when(f.storage.mutate(f.storageTicket,f.command)).thenReturn(result);var ticket=f.acquire();
            if(repeated)f.coordinator.apply(ticket);
            assertThrows(AccessDeniedException.class,()->f.coordinator.apply(ticket));
            assertTrue(f.holder.isRollbackOnly());verify(f.storage,never()).finalizeReplay(any());
        }
    }

    @Test void replayFinalCallbacksUseOneReadGuardWithoutProvisionalProofOrPartyEligibility() {
        try(var f=new WriteFixture(create(),ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY)) {
            var ticket=f.acquire();var result=replayResult();when(f.storage.prepareFinalReplay(f.storageTicket)).thenReturn(result);
            assertSame(result,f.coordinator.prepareFinalReplay(ticket));clearInvocations(f.identity,f.party,f.storage);
            f.coordinator.finalizeReplay(ticket);ticket.beforeCommit(false);
            verify(f.storage).finalizeReplay(f.storageTicket);verifyNoMoreInteractions(f.storage);
            verifyNoInteractions(f.identity,f.party);verify(f.executor,never()).sealAdmission();
            assertThrows(AccessDeniedException.class,()->f.coordinator.requireCurrentWrite(ticket));
            assertTrue(f.holder.isRollbackOnly());
        }
    }

    @Test void replayFailedOrRepeatedPreparationCannotCommitOrRetry() {
        for(boolean fail:List.of(false,true))try(var f=new WriteFixture(create(),ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY)) {
            var ticket=f.acquire();
            if(fail)when(f.storage.prepareFinalReplay(f.storageTicket)).thenThrow(new IllegalStateException("storage"));
            else {var result=replayResult();when(f.storage.prepareFinalReplay(f.storageTicket)).thenReturn(result);}
            if(fail)assertThrows(AccessDeniedException.class,()->f.coordinator.prepareFinalReplay(ticket));
            else {f.coordinator.prepareFinalReplay(ticket);assertThrows(AccessDeniedException.class,()->f.coordinator.prepareFinalReplay(ticket));}
            assertThrows(AccessDeniedException.class,()->ticket.beforeCommit(false));assertTrue(f.holder.isRollbackOnly());
            verify(f.storage,never()).finalizeReplay(any());
        }
    }

    @Test void replayRejectsNewMutationClassificationAndMismatchedDetachedResult() {
        for(boolean current:List.of(false,true))try(var f=new WriteFixture(create(),current
                ?ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.CURRENT:ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY)) {
            var ticket=f.acquire();var result=replayResult();when(result.requestId()).thenReturn("wrong");
            when(f.storage.prepareFinalReplay(f.storageTicket)).thenReturn(result);
            assertThrows(AccessDeniedException.class,()->f.coordinator.prepareFinalReplay(ticket));
            assertTrue(f.holder.isRollbackOnly());verify(f.storage,never()).finalizeReplay(any());
        }
    }

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
            when(storage.contribute(storageTicket)).thenReturn(permitted(request));
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
            order.verify(f.storage).contribute(f.storageTicket);
            order.verify(f.identity).requireCurrent(f.nativeTicket);
            order.verify(f.storage).requireCurrent(f.storageTicket);
            f.coordinator.requireCurrentRead(ticket);
            verify(f.storage, times(1)).contribute(f.storageTicket);
            verifyNoInteractions(f.party);
            verify(f.identity, never()).contribute(any());
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
            verify(f.storage, never()).contribute(any());
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
                verify(f.storage, never()).contribute(any());
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
                when(f.storage.contribute(f.storageTicket)).thenReturn(new Contribution(request,
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
            verify(f.storage, never()).contribute(any());
            verifyNoInteractions(f.party);
            verify(f.executor).rejectAdmission();
        }
    }

    @Test void missingRepositoryTicketCannotConsumeNativeSample() {
        try (var f = new ReadFixture()) {
            when(f.storage.acquire(any(), anyList(), any())).thenReturn(null);
            assertThrows(AccessDeniedException.class, f::acquire);
            verify(f.storage, never()).contribute(any());
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
            verify(f.storage, times(1)).contribute(any());
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

    private static final Set<String> MOCK_STATEMENTS=Set.of("key-insert","key-lock","source-lock","insert","cas","event");
    private static void provisionalFixture(WriteFixture f) {
        when(f.storage.prepareProvisional(f.storageTicket,f.command)).thenReturn(MOCK_STATEMENTS);
        when(f.executor.sealAdmission()).thenReturn(mock(ProtectedTransactionExecutor.ProvisionalPermit.class));
    }

    @Test void provisionalBlockClosesBeforeAnyPostWriteOwnerCheckAndNeverReturnsAReceipt() {
        try(var f=new WriteFixture()) {
            var ticket=f.acquire();provisionalFixture(f);clearInvocations(f.executor,f.identity,f.party,f.storage);
            f.coordinator.persistProvisional(ticket);
            var order=inOrder(f.executor,f.identity,f.party,f.storage);
            order.verify(f.storage).prepareProvisional(f.storageTicket,f.command);
            order.verify(f.executor).sealAdmission();order.verify(f.executor).beginProvisional(any(),eq(MOCK_STATEMENTS));
            order.verify(f.storage).persistProvisional(f.storageTicket);order.verify(f.executor).endProvisional(any());
            order.verify(f.identity).requireCurrent(f.nativeTicket);order.verify(f.party).requireCurrent(f.partyTicket);
            order.verify(f.storage).requireCurrent(f.storageTicket);order.verify(f.storage).verifyProvisional(f.storageTicket);
            verify(f.storage,times(1)).referenceUse(any(),any(),any());
            verify(f.storage,never()).mutate(any(),any());
            assertThrows(AccessDeniedException.class,() -> ticket.beforeCommit(false));
            assertTrue(f.holder.isRollbackOnly());
        }
    }

    @Test void provisionalPermitCannotBeReopenedAndReplayNeverObtainsOne() {
        try(var f=new WriteFixture()) {
            var ticket=f.acquire();provisionalFixture(f);f.coordinator.persistProvisional(ticket);
            assertThrows(AccessDeniedException.class,() -> f.coordinator.persistProvisional(ticket));
            verify(f.executor,times(1)).sealAdmission();verify(f.storage,times(1)).persistProvisional(any());
            assertTrue(f.holder.isRollbackOnly());
        }
        try(var f=new WriteFixture(create(),ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY)) {
            var ticket=f.acquire();
            assertThrows(AccessDeniedException.class,() -> f.coordinator.persistProvisional(ticket));
            verify(f.executor,never()).sealAdmission();verify(f.storage,never()).persistProvisional(any());
        }
    }


    @Test void failedProvisionalAndCommitProofRetainInternalCauseButDenyAndPoison() {
        for(boolean proofFailure:List.of(false,true))try(var f=new WriteFixture()) {
            var ticket=f.acquire();
            var internal=new IllegalStateException("synthetic private write-boundary failure");
            if(proofFailure) {
                provisionalFixture(f);
                doThrow(internal).when(f.storage).prepareCommitProof(f.storageTicket);
            } else {
                when(f.storage.prepareProvisional(f.storageTicket,f.command)).thenThrow(internal);
            }
            var rejected=assertThrows(AccessDeniedException.class,()->f.coordinator.apply(ticket));
            assertEquals("Protected appointment admission unavailable",rejected.getMessage());
            assertNotNull(rejected.getCause());
            assertSame(internal,rejected.getCause().getCause());
            assertTrue(f.holder.isRollbackOnly());
            verify(f.executor,atLeastOnce()).rejectAdmission();
            verify(f.storage,never()).mutate(any(),any());
            assertThrows(AccessDeniedException.class,()->ticket.beforeCommit(false));
        }
    }

    @Test void preparationAndMissingPermitFailuresCannotOpenProvisional() {
        for(int variant=0;variant<3;variant++)try(var f=new WriteFixture()) {
            var ticket=f.acquire();provisionalFixture(f);
            if(variant==0)when(f.storage.prepareProvisional(any(),any())).thenThrow(new IllegalStateException("prepare"));
            if(variant==1)when(f.storage.prepareProvisional(any(),any())).thenReturn(Set.of("partial"));
            if(variant==2)when(f.executor.sealAdmission()).thenReturn(null);
            assertThrows(AccessDeniedException.class,() -> f.coordinator.persistProvisional(ticket));
            verify(f.executor,never()).beginProvisional(any(),any());verify(f.storage,never()).persistProvisional(any());
            assertTrue(f.holder.isRollbackOnly());
        }
    }

    @Test void anyProvisionalBoundaryFailurePoisonsTheOriginalHolderWithoutRetryOrOwnerCallbackInsideBlock() {
        for(int variant=0;variant<4;variant++)try(var f=new WriteFixture()) {
            var ticket=f.acquire();provisionalFixture(f);
            if(variant==0)doThrow(new IllegalStateException("begin")).when(f.executor).beginProvisional(any(),any());
            if(variant==1)doThrow(new IllegalStateException("write")).when(f.storage).persistProvisional(any());
            if(variant==2)doThrow(new IllegalStateException("end")).when(f.executor).endProvisional(any());
            if(variant==3)doThrow(new IllegalStateException("post-write")).when(f.storage).verifyProvisional(any());
            clearInvocations(f.identity,f.party);
            assertThrows(AccessDeniedException.class,() -> f.coordinator.persistProvisional(ticket));
            verify(f.identity,times(variant==3?2:1)).requireCurrent(any());
            verify(f.party,times(variant==3?2:1)).requireCurrent(any());
            assertThrows(AccessDeniedException.class,() -> ticket.beforeCommit(false));
            assertThrows(AccessDeniedException.class,() -> f.coordinator.persistProvisional(ticket));
            verify(f.executor,times(1)).beginProvisional(any(),any());assertTrue(f.holder.isRollbackOnly());
        }
    }

    @Test void foreignCoordinatorCannotWriteOrConsumeTheOriginalPermit() {
        try(var f=new WriteFixture()) {
            var ticket=f.acquire();provisionalFixture(f);
            var foreign=new ProtectedEmployeeAppointmentAdmissionCoordinator(f.identity,f.party,f.storage,f.executor);
            assertThrows(AccessDeniedException.class,() -> foreign.persistProvisional(ticket));
            verify(f.executor,never()).sealAdmission();verify(f.storage,never()).persistProvisional(any());
            assertTrue(f.holder.isRollbackOnly());
        }
    }

    @Test void proofHandoffOccursAfterClosedProvisionalAndRechecksOwnersWithoutReturningBytes() throws Exception {
        try(var f=new WriteFixture()) {
            var ticket=f.acquire();provisionalFixture(f);f.coordinator.persistProvisional(ticket);
            clearInvocations(f.executor,f.identity,f.party,f.storage);
            f.coordinator.prepareCommitProof(ticket);
            var order=inOrder(f.identity,f.party,f.storage);
            order.verify(f.identity).requireCurrent(f.nativeTicket);
            order.verify(f.party).requireCurrent(f.partyTicket);
            order.verify(f.storage).requireCurrent(f.storageTicket);
            order.verify(f.storage).verifyProvisional(f.storageTicket);
            order.verify(f.storage).prepareCommitProof(f.storageTicket);
            order.verify(f.identity).requireCurrent(f.nativeTicket);
            order.verify(f.party).requireCurrent(f.partyTicket);
            order.verify(f.storage).requireCurrent(f.storageTicket);
            order.verify(f.storage).verifyProvisional(f.storageTicket);
            verify(f.identity,never()).issueCommitProof(any(),any()); // Intent belongs to storage.
            verifyNoInteractions(f.executor);
            assertEquals(void.class,ProtectedEmployeeAppointmentAdmissionCoordinator.class.getDeclaredMethod(
                    "prepareCommitProof",ProtectedEmployeeAppointmentAdmissionCoordinator.WriteAdmission.class).getReturnType());
            assertThrows(AccessDeniedException.class,() -> ticket.beforeCommit(false));
            assertTrue(f.holder.isRollbackOnly());
        }
    }

    @Test void proofPreparationRequiresCompletedProvisionalAndCanNeverBeRepeated() {
        try(var f=new WriteFixture()) {
            var ticket=f.acquire();
            assertThrows(AccessDeniedException.class,() -> f.coordinator.prepareCommitProof(ticket));
            verify(f.storage,never()).prepareCommitProof(any());assertTrue(f.holder.isRollbackOnly());
        }
        try(var f=new WriteFixture()) {
            var ticket=f.acquire();provisionalFixture(f);f.coordinator.persistProvisional(ticket);
            f.coordinator.prepareCommitProof(ticket);
            assertThrows(AccessDeniedException.class,() -> f.coordinator.prepareCommitProof(ticket));
            verify(f.storage,times(1)).prepareCommitProof(f.storageTicket);assertTrue(f.holder.isRollbackOnly());
        }
        try(var f=new WriteFixture(create(),ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY)) {
            var ticket=f.acquire();
            assertThrows(AccessDeniedException.class,() -> f.coordinator.prepareCommitProof(ticket));
            verify(f.storage,never()).prepareCommitProof(any());
        }
    }

    @Test void proofCallbackFailureOrSuspensionPoisonsOriginalHolderAndCannotRetry() {
        for(boolean suspend:List.of(false,true))try(var f=new WriteFixture()) {
            var ticket=f.acquire();provisionalFixture(f);f.coordinator.persistProvisional(ticket);
            doAnswer(call -> {
                if(suspend){ticket.suspend();return null;}
                throw new IllegalStateException("proof unavailable");
            }).when(f.storage).prepareCommitProof(f.storageTicket);
            assertThrows(AccessDeniedException.class,() -> f.coordinator.prepareCommitProof(ticket));
            assertTrue(f.holder.isRollbackOnly());
            assertThrows(AccessDeniedException.class,() -> f.coordinator.prepareCommitProof(ticket));
            verify(f.storage,times(1)).prepareCommitProof(f.storageTicket);
            assertThrows(AccessDeniedException.class,() -> ticket.beforeCommit(false));
        }
    }

    @Test void foreignCoordinatorOrThreadCannotPrepareProofForTheOriginalWrite() throws Exception {
        for(boolean otherThread:List.of(false,true))try(var f=new WriteFixture()) {
            var ticket=f.acquire();provisionalFixture(f);f.coordinator.persistProvisional(ticket);
            clearInvocations(f.identity,f.party,f.storage);
            if(otherThread){
                var failure=new java.util.concurrent.atomic.AtomicReference<Throwable>();
                var thread=new Thread(() -> {
                    try{f.coordinator.prepareCommitProof(ticket);}catch(Throwable rejected){failure.set(rejected);}
                });
                thread.start();thread.join(5000);assertFalse(thread.isAlive());
                assertInstanceOf(AccessDeniedException.class,failure.get());
            }else{
                var foreign=new ProtectedEmployeeAppointmentAdmissionCoordinator(f.identity,f.party,f.storage,f.executor);
                assertThrows(AccessDeniedException.class,() -> foreign.prepareCommitProof(ticket));
            }
            verifyNoInteractions(f.identity,f.party,f.storage);assertTrue(f.holder.isRollbackOnly());
        }
    }

    @Test void finalPreparationRechecksOwnersAndGuardAndBeforeCommitPerformNoOwnerCallbacks() {
        try(var f=new WriteFixture()) {
            var ticket=f.acquire();provisionalFixture(f);f.coordinator.persistProvisional(ticket);
            f.coordinator.prepareCommitProof(ticket);clearInvocations(f.identity,f.party,f.storage,f.executor);
            f.coordinator.prepareFinalWrite(ticket);
            var order=inOrder(f.identity,f.party,f.storage);
            order.verify(f.identity).requireCurrent(f.nativeTicket);
            order.verify(f.party).requireCurrent(f.partyTicket);
            order.verify(f.storage).requireCurrent(f.storageTicket);
            order.verify(f.storage).verifyProvisional(f.storageTicket);
            order.verify(f.storage).prepareFinalWrite(f.storageTicket);
            clearInvocations(f.identity,f.party,f.storage,f.executor);
            f.coordinator.finalizeWrite(ticket);ticket.beforeCommit(false);
            verify(f.storage,times(1)).finalizeWrite(f.storageTicket);
            verifyNoMoreInteractions(f.storage);verifyNoInteractions(f.identity,f.party,f.executor);
            assertFalse(f.holder.isRollbackOnly());
        }
    }

    @Test void finalGuardRequiresProofAndFinalPreparationAndCannotBeRetried() {
        for(int phase=0;phase<3;phase++)try(var f=new WriteFixture()) {
            var ticket=f.acquire();provisionalFixture(f);f.coordinator.persistProvisional(ticket);
            if(phase>0)f.coordinator.prepareCommitProof(ticket);
            if(phase>1){f.coordinator.prepareFinalWrite(ticket);f.coordinator.finalizeWrite(ticket);}
            assertThrows(AccessDeniedException.class,() -> f.coordinator.finalizeWrite(ticket));
            verify(f.storage,times(phase>1?1:0)).finalizeWrite(any());
            assertTrue(f.holder.isRollbackOnly());
        }
    }

    @Test void swallowedFinalGuardFailureSuspensionOrInvalidationAlwaysVetoesCommit() {
        for(int variant=0;variant<3;variant++)try(var f=new WriteFixture()) {
            var ticket=f.acquire();provisionalFixture(f);f.coordinator.persistProvisional(ticket);
            f.coordinator.prepareCommitProof(ticket);f.coordinator.prepareFinalWrite(ticket);
            final int attack=variant;
            doAnswer(call -> {
                if(attack==0)throw new IllegalStateException("guard unavailable");
                if(attack==1)ticket.suspend();else f.holder.setRollbackOnly();
                return null;
            }).when(f.storage).finalizeWrite(f.storageTicket);
            assertThrows(AccessDeniedException.class,() -> f.coordinator.finalizeWrite(ticket));
            assertThrows(AccessDeniedException.class,() -> ticket.beforeCommit(false));
            assertThrows(AccessDeniedException.class,() -> f.coordinator.finalizeWrite(ticket));
            verify(f.storage,times(1)).finalizeWrite(f.storageTicket);assertTrue(f.holder.isRollbackOnly());
        }
    }

    @Test void preparedGuardCannotRunOnAnotherCoordinatorOrThread() throws Exception {
        for(boolean otherThread:List.of(false,true))try(var f=new WriteFixture()) {
            var ticket=f.acquire();provisionalFixture(f);f.coordinator.persistProvisional(ticket);
            f.coordinator.prepareCommitProof(ticket);f.coordinator.prepareFinalWrite(ticket);
            clearInvocations(f.identity,f.party,f.storage);
            if(otherThread){
                var failure=new java.util.concurrent.atomic.AtomicReference<Throwable>();
                var thread=new Thread(() -> {try{f.coordinator.finalizeWrite(ticket);}catch(Throwable e){failure.set(e);}});
                thread.start();thread.join(5000);assertFalse(thread.isAlive());
                assertInstanceOf(AccessDeniedException.class,failure.get());
            }else{
                var foreign=new ProtectedEmployeeAppointmentAdmissionCoordinator(f.identity,f.party,f.storage,f.executor);
                assertThrows(AccessDeniedException.class,() -> foreign.finalizeWrite(ticket));
            }
            verifyNoInteractions(f.identity,f.party,f.storage);assertTrue(f.holder.isRollbackOnly());
        }
    }

    @Test void missingOrRepeatedFinalPreparationAndPostGuardOwnerReadPoisonTheTicket() {
        for(int variant=0;variant<3;variant++)try(var f=new WriteFixture()) {
            var ticket=f.acquire();provisionalFixture(f);f.coordinator.persistProvisional(ticket);
            if(variant>0){f.coordinator.prepareCommitProof(ticket);f.coordinator.prepareFinalWrite(ticket);}
            if(variant==2){
                f.coordinator.finalizeWrite(ticket);clearInvocations(f.identity,f.party,f.storage);
                assertThrows(AccessDeniedException.class,() -> f.coordinator.requireCurrentWrite(ticket));
                verifyNoInteractions(f.identity,f.party,f.storage);
            }else assertThrows(AccessDeniedException.class,() -> f.coordinator.prepareFinalWrite(ticket));
            assertTrue(f.holder.isRollbackOnly());assertThrows(AccessDeniedException.class,() -> ticket.beforeCommit(false));
        }
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
            when(storage.contribute(storageTicket)).thenReturn(sample);
            when(storage.referenceUse(storageTicket, command, sample)).thenReturn(use);
            when(storage.contributeParty(storageTicket)).thenReturn(
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
            order.verify(f.storage).contribute(f.storageTicket);
            order.verify(f.storage).referenceUse(f.storageTicket, f.command, f.sample);
            order.verify(f.storage).contributeParty(f.storageTicket);
            order.verify(f.identity).requireCurrent(f.nativeTicket);
            order.verify(f.party).requireCurrent(f.partyTicket);
            order.verify(f.storage).requireCurrent(f.storageTicket);
            order.verify(f.storage).prepareMutation(f.storageTicket, f.command);
            verify(f.storage, times(1)).contribute(f.storageTicket);
            verify(f.storage, times(1)).contributeParty(f.storageTicket);
            verify(f.party, never()).read(any());
            verify(f.storage, never()).mutate(any(), any());
            verify(f.identity, never()).contribute(any());
        }
    }

    @Test void mutationPlanFailureVetoesCurrentRetainedAndReplayQualification() {
        for(var use:ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.values()) {
            var command=use==ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.RETAINED?endCommand():create();
            try(var f=new WriteFixture(command,use)) {
                doThrow(new IllegalStateException("plan unavailable")).when(f.storage).prepareMutation(f.storageTicket,command);
                assertThrows(AccessDeniedException.class,f::acquire);
                assertTrue(f.holder.isRollbackOnly());verify(f.executor,atLeastOnce()).rejectAdmission();
                verify(f.storage,never()).mutate(any(),any());
            }
        }
    }

    @Test void everyWriteBoundaryRechecksTheFrozenPlanAndSwallowedFailureCannotCommit() {
        try(var f=new WriteFixture()) {
            var ticket=f.acquire();
            f.coordinator.requireCurrentWrite(ticket);
            verify(f.storage,times(2)).prepareMutation(f.storageTicket,f.command);
            doThrow(new IllegalStateException("plan changed")).when(f.storage).prepareMutation(f.storageTicket,f.command);
            assertThrows(AccessDeniedException.class,() -> f.coordinator.requireCurrentWrite(ticket));
            assertTrue(f.holder.isRollbackOnly());
            for(var callback:TransactionSynchronizationManager.getSynchronizations())
                assertThrows(AccessDeniedException.class,() -> callback.beforeCommit(false));
            verify(f.party,never()).read(any());verify(f.storage,never()).mutate(any(),any());
        }
    }

    @Test void newCreateTransferAndMovedCorrectionConsumeCurrentPartyEligibility() {
        for (var command : List.of(create(), transferCommand(), correctCommand(true))) {
            try (var f = new WriteFixture(command, ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.CURRENT)) {
                f.acquire();
                verify(f.storage).contributeParty(f.storageTicket);
                verify(f.party).requireCurrent(f.partyTicket);
                verify(f.storage, never()).mutate(any(), any());
            }
        }
    }

    @Test void retainedEndAndSameUnitCorrectionDoNotDemandCurrentPartyStatus() {
        for (var command : List.of(endCommand(), correctCommand(false))) {
            try (var f = new WriteFixture(command, ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.RETAINED)) {
                doThrow(new IllegalStateException("retired company")).when(f.storage).contributeParty(any());
                f.acquire();
                verify(f.party).acquire("company");
                verify(f.storage, never()).contributeParty(any());
                verify(f.party, never()).requireCurrent(any());
                verify(f.identity).requireCurrent(f.nativeTicket);
            }
        }
    }

    @Test void exactReplayOfEveryCommandRequiresFreshNativeAuthorityWithoutActiveParty() {
        for (var command : List.of(create(), endCommand(), transferCommand(), correctCommand(true))) {
            try (var f = new WriteFixture(command, ProtectedEmployeeAppointmentRepositoryPort.ReferenceUse.REPLAY)) {
                doThrow(new IllegalStateException("retired company")).when(f.storage).contributeParty(any());
                f.acquire();
                verify(f.identity).acquire(any(), eq(f.request));
                verify(f.storage).contribute(f.storageTicket);
                verify(f.identity).requireCurrent(f.nativeTicket);
                verify(f.storage, never()).contributeParty(any());
                verify(f.party, never()).requireCurrent(any());
                verify(f.storage, never()).mutate(any(), any());
            }
        }
    }

    @Test void nativeWriteFailureRetainsInternalCauseWithoutChangingTheOutwardDenial() {
        try(var f=new WriteFixture()) {
            var original=new IllegalStateException("synthetic private issuer failure");
            when(f.identity.acquire(any(),any())).thenThrow(original);
            var failure=assertThrows(AccessDeniedException.class,f::acquire);
            assertEquals("Protected appointment admission unavailable",failure.getMessage());
            assertSame(original,failure.getCause());
            verifyNoInteractions(f.party);verify(f.storage,never()).acquire(any(),anyList(),any());
            verify(f.executor,atLeastOnce()).rejectAdmission();
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
                verify(f.storage, never()).contribute(any());
                verify(f.storage, never()).contributeParty(any());
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
                verify(f.storage, never()).contribute(any());
                verify(f.storage, never()).referenceUse(any(), any(), any());
                verify(f.storage, never()).contributeParty(any());
                assertTrue(f.holder.isRollbackOnly());
            }
        }
    }

    @Test void deniedUncertainOrSubstitutedWriteSampleCannotClassifyAReceipt() {
        for (int variant = 0; variant < 3; variant++) {
            try (var f = new WriteFixture()) {
                var request = variant == 2 ? new Request(Action.END, f.request.companyUnits(), "employee") : f.request;
                var decision = variant == 0 ? Decision.DENY : variant == 1 ? Decision.INDETERMINATE : Decision.PERMIT;
                when(f.storage.contribute(f.storageTicket)).thenReturn(new Contribution(request,
                        request.requirements().stream().map(r -> new ResourceDecision(r, decision)).toList(),
                        "binding", "session", "user", START, START.plusSeconds(1), 17, 23));
                assertThrows(AccessDeniedException.class, f::acquire);
                verify(f.storage, never()).referenceUse(any(), any(), any());
                verify(f.storage, never()).contributeParty(any());
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
                verify(f.storage, never()).contributeParty(any());
                assertTrue(f.holder.isRollbackOnly());
            }
        }
    }

    @Test void wrongPartyIdentityOrPhysicalCoordinatesCannotQualifyCurrentReference() {
        for (int variant = 0; variant < 3; variant++) {
            try (var f = new WriteFixture()) {
                when(f.storage.contributeParty(f.storageTicket)).thenReturn(new OrganizationProtectedPartyEligibilityContract.Contribution(
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
                if (missing) when(f.storage.contributeParty(f.storageTicket)).thenReturn(null);
                else when(f.storage.contributeParty(f.storageTicket)).thenThrow(new IllegalStateException("inactive"));
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

    @Test void finalJournalDispatchMaterializesEveryTypedShapeBeforeStoragePreparation() {
        var queries=List.of(
                EmployeeAppointmentQuery.History.assignment("company","assignment",1,null),
                EmployeeAppointmentQuery.History.employee("company","employee",1,null),
                EmployeeAppointmentQuery.History.position("company","unit","position",1,null),
                new EmployeeAppointmentQuery.ExactVersion("company","assignment",1));
        for(var query:queries) {
            boolean position=query instanceof EmployeeAppointmentQuery.History h && h.scope()==EmployeeAppointmentQuery.Scope.POSITION;
            boolean employee=query instanceof EmployeeAppointmentQuery.History h && h.scope()==EmployeeAppointmentQuery.Scope.EMPLOYEE;
            try(var f=new ReadFixture(query,preview("company","unit",position?null:"employee",position||employee?null:"assignment"))) {
                if(query instanceof EmployeeAppointmentQuery.ExactVersion exact)
                    when(f.storage.exactVersion(f.storageTicket,exact)).thenReturn(mock(dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.PinnedVersion.class));
                else if(position)when(f.storage.positionHistory(f.storageTicket,(EmployeeAppointmentQuery.History)query))
                    .thenReturn(new dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.HistoryPage<>(List.of(),null,false));
                else when(f.storage.privateHistory(f.storageTicket,(EmployeeAppointmentQuery.History)query))
                    .thenReturn(new dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.HistoryPage<>(List.of(),null,false));
                var ticket=f.acquire();clearInvocations(f.storage,f.identity,f.executor);
                f.coordinator.prepareFinalRead(ticket);
                var order=inOrder(f.storage);
                order.verify(f.storage).requireCurrent(f.storageTicket);
                if(query instanceof EmployeeAppointmentQuery.ExactVersion exact)order.verify(f.storage).exactVersion(f.storageTicket,exact);
                else if(position)order.verify(f.storage).positionHistory(f.storageTicket,(EmployeeAppointmentQuery.History)query);
                else order.verify(f.storage).privateHistory(f.storageTicket,(EmployeeAppointmentQuery.History)query);
                order.verify(f.storage).prepareFinalRead(f.storageTicket);
                clearInvocations(f.storage,f.identity,f.party,f.executor);
                f.coordinator.finalizeRead(ticket);ticket.beforeCommit(false);
                verify(f.storage).finalizeRead(f.storageTicket);verifyNoMoreInteractions(f.storage);
                verifyNoInteractions(f.identity,f.party,f.executor);
            }
        }
    }

    @Test void journalMaterializationFailureCannotReachFinalPreparationOrGuard() {
        try(var f=new ReadFixture()) {
            var ticket=f.acquire();
            doThrow(new IllegalStateException("synthetic invalid graph")).when(f.storage)
                    .privateHistory(f.storageTicket,(EmployeeAppointmentQuery.History)f.query);
            assertThrows(AccessDeniedException.class,()->f.coordinator.prepareFinalRead(ticket));
            verify(f.storage,never()).prepareFinalRead(any());verify(f.storage,never()).finalizeRead(any());
            assertTrue(f.holder.isRollbackOnly());assertThrows(AccessDeniedException.class,()->ticket.beforeCommit(false));
        }
    }

    private static ReadFixture policyFixture() {
        var f=new ReadFixture(new EmployeeAppointmentQuery.Policy("company","unit"),
                preview("company","unit",null,null));
        when(f.storage.readPolicy(f.storageTicket,(EmployeeAppointmentQuery.Policy)f.query))
                .thenReturn(mock(dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult.PolicyView.class));
        return f;
    }

    @Test void finalPolicyReadPreparesWithOwnersThenGuardsWithoutOwnerCallbacks() {
        try(var f=policyFixture()) {
            var ticket=f.acquire();clearInvocations(f.identity,f.party,f.storage,f.executor);
            f.coordinator.prepareFinalRead(ticket);
            var order=inOrder(f.identity,f.storage);
            order.verify(f.identity).requireCurrent(f.nativeTicket);
            order.verify(f.storage).requireCurrent(f.storageTicket);
            order.verify(f.storage).readPolicy(f.storageTicket,(EmployeeAppointmentQuery.Policy)f.query);
            order.verify(f.storage).prepareFinalRead(f.storageTicket);
            clearInvocations(f.identity,f.party,f.storage,f.executor);
            f.coordinator.finalizeRead(ticket);ticket.beforeCommit(false);
            verify(f.storage).finalizeRead(f.storageTicket);
            verifyNoMoreInteractions(f.storage);verifyNoInteractions(f.identity,f.party,f.executor);
            verify(f.storage,never()).prepareCommitProof(any());
        }
    }

    @Test void finalPolicyReadRejectsMissingPreparationUnsupportedHistoryAndRepeat() {
        try(var f=policyFixture()) {
            var ticket=f.acquire();assertThrows(AccessDeniedException.class,() -> f.coordinator.finalizeRead(ticket));
            assertTrue(f.holder.isRollbackOnly());verify(f.storage,never()).finalizeRead(any());
        }
        try(var f=new ReadFixture()) {
            var ticket=f.acquire();assertThrows(AccessDeniedException.class,() -> f.coordinator.prepareFinalRead(ticket));
            verify(f.storage,never()).prepareFinalRead(any());
        }
        for(boolean afterGuard:List.of(false,true))try(var f=policyFixture()) {
            var ticket=f.acquire();f.coordinator.prepareFinalRead(ticket);
            if(afterGuard)f.coordinator.finalizeRead(ticket);
            assertThrows(AccessDeniedException.class,() -> {
                if(afterGuard)f.coordinator.finalizeRead(ticket);else f.coordinator.prepareFinalRead(ticket);
            });
            assertTrue(f.holder.isRollbackOnly());
            verify(f.storage,times(afterGuard?1:0)).finalizeRead(f.storageTicket);
        }
    }

    @Test void preparedPolicyReadCannotCommitWithoutGuardOrRecoverSwallowedFailure() {
        for(boolean guardFailure:List.of(false,true))try(var f=policyFixture()) {
            var ticket=f.acquire();f.coordinator.prepareFinalRead(ticket);
            if(guardFailure) {
                doThrow(new IllegalStateException("unavailable")).when(f.storage).finalizeRead(f.storageTicket);
                assertThrows(AccessDeniedException.class,() -> f.coordinator.finalizeRead(ticket));
                assertThrows(AccessDeniedException.class,() -> f.coordinator.finalizeRead(ticket));
                verify(f.storage,times(1)).finalizeRead(f.storageTicket);
            }
            assertThrows(AccessDeniedException.class,() -> ticket.beforeCommit(false));
            assertTrue(f.holder.isRollbackOnly());
        }
    }

    @Test void finalPolicyReadRejectsForeignCoordinatorThreadAndReplacedHolderBeforeGuard() throws Exception {
        for(int variant=0;variant<3;variant++)try(var f=policyFixture()) {
            var ticket=f.acquire();f.coordinator.prepareFinalRead(ticket);clearInvocations(f.storage,f.identity,f.party);
            if(variant==0) {
                var other=new ProtectedEmployeeAppointmentAdmissionCoordinator(f.identity,f.party,f.storage,f.executor);
                assertThrows(AccessDeniedException.class,() -> other.finalizeRead(ticket));
            } else if(variant==1) {
                var error=new java.util.concurrent.atomic.AtomicReference<Throwable>();
                var thread=new Thread(() -> {try{f.coordinator.finalizeRead(ticket);}catch(Throwable failure){error.set(failure);}});
                thread.start();thread.join(5000);assertFalse(thread.isAlive());assertInstanceOf(AccessDeniedException.class,error.get());
            } else {
                TransactionSynchronizationManager.unbindResource(f.resourceKey);
                TransactionSynchronizationManager.bindResource(f.resourceKey,new ConnectionHolder(mock(java.sql.Connection.class)));
                assertThrows(AccessDeniedException.class,() -> f.coordinator.finalizeRead(ticket));
            }
            verifyNoInteractions(f.storage,f.identity,f.party);
            assertThrows(AccessDeniedException.class,() -> ticket.beforeCommit(false));
        }
    }

    @Test void finalPolicyReadRejectsSuspensionRollbackAndPostGuardOwnerQueries() {
        for(int variant=0;variant<3;variant++)try(var f=policyFixture()) {
            var ticket=f.acquire();f.coordinator.prepareFinalRead(ticket);
            if(variant==0)ticket.suspend();
            else if(variant==1)f.holder.setRollbackOnly();
            else f.coordinator.finalizeRead(ticket);
            clearInvocations(f.identity,f.party,f.storage);
            if(variant==2)assertThrows(AccessDeniedException.class,() -> f.coordinator.requireCurrentRead(ticket));
            else assertThrows(AccessDeniedException.class,() -> f.coordinator.finalizeRead(ticket));
            verifyNoInteractions(f.identity,f.party,f.storage);
            assertTrue(f.holder.isRollbackOnly());
        }
    }

    @Test void finalPolicyPreparationFailureAndCallbackInvalidationPoisonTheOriginalHolder() {
        for(int variant=0;variant<3;variant++)try(var f=policyFixture()) {
            var ticket=f.acquire();
            if(variant==0)when(f.storage.readPolicy(f.storageTicket,(EmployeeAppointmentQuery.Policy)f.query)).thenReturn(null);
            else if(variant==1)doThrow(new IllegalStateException("unavailable")).when(f.storage).prepareFinalRead(f.storageTicket);
            else doAnswer(call -> {ticket.suspend();return null;}).when(f.storage).prepareFinalRead(f.storageTicket);
            assertThrows(AccessDeniedException.class,() -> f.coordinator.prepareFinalRead(ticket));
            assertTrue(f.holder.isRollbackOnly());assertThrows(AccessDeniedException.class,() -> ticket.beforeCommit(false));
            verify(f.storage,never()).finalizeRead(any());
        }
    }
}
