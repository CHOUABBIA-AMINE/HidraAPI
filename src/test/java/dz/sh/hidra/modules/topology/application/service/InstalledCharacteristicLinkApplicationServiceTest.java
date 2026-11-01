/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledCharacteristicLinkApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Tests draft-only descriptor validation and protected transaction coordination.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.InstalledCharacteristicLinkRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.InstalledCharacteristicLink;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.time.Instant;
import java.util.List;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class InstalledCharacteristicLinkApplicationServiceTest {
    private final InstalledCharacteristicLinkRepositoryPort repository =
        mock(InstalledCharacteristicLinkRepositoryPort.class);
    private final ProtectedTransactionExecutor executor = mock(ProtectedTransactionExecutor.class);
    private final InstalledCharacteristicLinkApplicationService service =
        new InstalledCharacteristicLinkApplicationService(repository, executor);

    private InstalledCharacteristicLink draft(long revision) {
        return new InstalledCharacteristicLink("link", revision, "company", "unit", "source", "source-rev",
            "a".repeat(64), InstalledCharacteristicLink.Kind.COMPRESSOR_MAP,
            Instant.parse("2026-11-01T00:00:00Z"), null,
            Instant.parse("2026-11-01T00:00:00Z"), "synthetic evidence", null);
    }
    private Command save(long epoch) {
        return new Command(Operation.SAVE_DRAFT, "company", "unit", "link",
                draft(epoch + 1), "request", epoch, null, 0, null);
    }
    private Command current() {
        return new Command(Operation.READ_CURRENT, "company", "unit", "link",
                null, null, null, null, 0, null);
    }

    @Test void saveRequiresExactLinkAndPhysicalUnit() {
        var declaration = draft(1);
        assertThrows(IllegalArgumentException.class, () -> new Command(Operation.SAVE_DRAFT,
            "company", "different-unit", "link", declaration, "request", 0L, null, 0, null));
        assertThrows(IllegalArgumentException.class, () -> new Command(Operation.SAVE_DRAFT,
            "company", "unit", "different-link", declaration, "request", 0L, null, 0, null));
        assertEquals(Operation.SAVE_DRAFT, save(0).operation());
    }

    @Test void saveRequiresExpectedEpochAndNextImmutableRevision() {
        assertThrows(IllegalArgumentException.class, () -> new Command(Operation.SAVE_DRAFT,
            "company", "unit", "link", draft(2), "request", 0L, null, 0, null));
        assertThrows(IllegalArgumentException.class, () -> new Command(Operation.SAVE_DRAFT,
            "company", "unit", "link", draft(1), "request", null, null, 0, null));
        assertThrows(IllegalArgumentException.class, () -> new Command(Operation.SAVE_DRAFT,
            "company", "unit", "link", draft(1), "request", Long.MAX_VALUE, null, 0, null));
    }

    @Test void saveRequiresExplicitRequestIdentityAndRejectsReadFields() {
        assertThrows(IllegalArgumentException.class, () -> new Command(Operation.SAVE_DRAFT,
            "company", "unit", "link", draft(1), null, 0L, null, 0, null));
        assertThrows(IllegalArgumentException.class, () -> new Command(Operation.SAVE_DRAFT,
            "company", "unit", "link", draft(1), "request", 0L, 1L, 0, null));
    }

    @Test void currentReadCannotCarryDraftOrHistoryCursor() {
        assertEquals(Operation.READ_CURRENT, current().operation());
        assertThrows(IllegalArgumentException.class, () -> new Command(Operation.READ_CURRENT,
            "company", "unit", "link", null, null, null, null, 0, 1L));
    }

    @Test void exactRevisionReadRequiresPositiveRevision() {
        assertThrows(IllegalArgumentException.class, () -> new Command(Operation.READ_REVISION,
            "company", "unit", "link", null, null, null, 0L, 0, null));
        assertEquals(2L, new Command(Operation.READ_REVISION,
            "company", "unit", "link", null, null, null, 2L, 0, null).revision());
    }

    @Test void historyEnforcesLimitAndExclusivePositiveCursor() {
        assertThrows(IllegalArgumentException.class, () -> new Command(Operation.LIST_HISTORY,
            "company", "unit", "link", null, null, null, null, 101, null));
        assertThrows(IllegalArgumentException.class, () -> new Command(Operation.LIST_HISTORY,
            "company", "unit", "link", null, null, null, null, 1, 0L));
        assertEquals(3L, new Command(Operation.LIST_HISTORY,
            "company", "unit", "link", null, null, null, null, 10, 3L).beforeRevision());
    }

    @Test void identitiesAreExactAndResultsAreDefensivelyCopied() {
        assertThrows(IllegalArgumentException.class, () -> new Command(Operation.READ_CURRENT,
            "company ", "unit", "link", null, null, null, null, 0, null));
        var rows = new java.util.ArrayList<>(List.of(draft(1)));
        var result = new Result(null, rows, false, null);
        rows.clear();
        assertEquals(1, result.history().size());
        assertThrows(UnsupportedOperationException.class, () -> result.history().clear());
        assertFalse(result.toString().contains("source-rev"));
        assertFalse(save(0).toString().contains("request"));
    }

    @Test void onlyDraftOperationsExistWithoutApprovalOrQualification() {
        assertEquals(List.of("SAVE_DRAFT", "READ_CURRENT", "READ_REVISION", "LIST_HISTORY"),
            java.util.Arrays.stream(Operation.values()).map(Enum::name).toList());
    }

    @Test void successfulResultRequiresFinalGuardAndExecutorCompletion() {
        var command = current();
        var prepared = mock(InstalledCharacteristicLinkRepositoryPort.Prepared.class);
        var admission = mock(InstalledCharacteristicLinkRepositoryPort.Admission.class);
        var result = new Result(draft(1), List.of(), false, null);
        when(repository.prepare(command)).thenReturn(prepared);
        when(repository.acquire(prepared, command)).thenReturn(admission);
        when(repository.perform(admission, command)).thenReturn(result);
        when(repository.finalGuard(admission)).thenReturn(true);
        when(executor.executePublication(any(), any(), any())).thenAnswer(i -> {
            Object value = ((Supplier<?>) i.getArgument(0)).get();
            ((Runnable) i.getArgument(1)).run();
            ((Runnable) i.getArgument(2)).run();
            return value;
        });
        assertSame(result, service.execute(command));
        var order = inOrder(executor, repository);
        order.verify(executor).requireNoProtectedContext();
        order.verify(repository).prepare(command);
        order.verify(executor).executePublication(any(), any(), any());
        order.verify(repository).acquire(prepared, command);
        order.verify(repository).perform(admission, command);
        order.verify(repository).flush();
        order.verify(repository).prepareFinal(admission);
        order.verify(repository).finalGuard(admission);
    }

    @Test void deniedFinalGuardNeverReleasesProtectedRead() {
        var command = current();
        var prepared = mock(InstalledCharacteristicLinkRepositoryPort.Prepared.class);
        var admission = mock(InstalledCharacteristicLinkRepositoryPort.Admission.class);
        when(repository.prepare(command)).thenReturn(prepared);
        when(repository.acquire(prepared, command)).thenReturn(admission);
        when(repository.perform(admission, command)).thenReturn(new Result(draft(1), List.of(), false, null));
        when(executor.executePublication(any(), any(), any())).thenAnswer(i -> {
            ((Supplier<?>) i.getArgument(0)).get();
            ((Runnable) i.getArgument(1)).run();
            ((Runnable) i.getArgument(2)).run();
            return null;
        });
        assertThrows(IllegalStateException.class, () -> service.execute(command));
    }

    @Test void indeterminateCommitIsNeverAutomaticallyRetried() {
        var command = current();
        when(executor.executePublication(any(), any(), any()))
            .thenThrow(new IllegalStateException("synthetic commit acknowledgement failure"));
        assertThrows(IllegalStateException.class, () -> service.execute(command));
        verify(executor, times(1)).executePublication(any(), any(), any());
    }
}
