/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitLifecycleApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Implements the protected installed-unit lifecycle within its owning boundary.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.ProtectedInstalledUnitRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.InstalledUnit;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.*;
import java.util.function.Supplier;
class InstalledUnitLifecycleApplicationServiceTest {
    private final ProtectedInstalledUnitAdmissionCoordinator coordinator=mock(ProtectedInstalledUnitAdmissionCoordinator.class);
    private final ProtectedInstalledUnitRepositoryPort storage=mock(ProtectedInstalledUnitRepositoryPort.class);
    private final ProtectedTransactionExecutor executor=mock(ProtectedTransactionExecutor.class);
    private final InstalledUnitLifecycleApplicationService service=new InstalledUnitLifecycleApplicationService(coordinator,storage,executor);
    private Command command(){return new Command(Operation.READ_UNIT,null,"company","unit",null,null,null,null,null,null,null,null,0);}
    private Result result(){return new Result(new InstalledUnit("unit","company","kind",InstalledUnit.Evidence.unknown(),InstalledUnit.Evidence.unknown(),InstalledUnit.Evidence.unknown(),null),null,null,List.of(),false);}
    @Test void orderKeepsFinalOwnerChecksAfterFlushAndBeforeCommittedReturn() {
        var cmd=command();var entry=mock(ProtectedInstalledUnitAdmissionCoordinator.Prepared.class);var ticket=mock(ProtectedInstalledUnitAdmissionCoordinator.Admission.class);var expected=result();
        when(coordinator.prepare(cmd)).thenReturn(entry);when(coordinator.acquire(entry,cmd)).thenReturn(ticket);when(coordinator.perform(ticket)).thenReturn(expected);when(coordinator.finalGuard(ticket)).thenReturn(true);
        when(executor.executePublication(any(),any(),any())).thenAnswer(i->{Object value=((Supplier<?>)i.getArgument(0)).get();((Runnable)i.getArgument(1)).run();((Runnable)i.getArgument(2)).run();return value;});
        assertSame(expected,service.execute(cmd));var order=inOrder(executor,coordinator,storage);
        order.verify(executor).requireNoProtectedContext();order.verify(coordinator).prepare(cmd);
        order.verify(executor).executePublication(any(),any(),any());order.verify(coordinator).acquire(entry,cmd);order.verify(coordinator).perform(ticket);
        order.verify(storage).flush();order.verify(coordinator).prepareFinal(ticket);order.verify(coordinator).finalGuard(ticket);
    }
    @Test void failedPhysicalCommitNeverReturnsProvisionalResult() {
        var cmd=command();var ticket=mock(ProtectedInstalledUnitAdmissionCoordinator.Admission.class);
        when(coordinator.acquire(any(),eq(cmd))).thenReturn(ticket);when(coordinator.perform(ticket)).thenReturn(result());when(coordinator.finalGuard(ticket)).thenReturn(true);
        when(executor.executePublication(any(),any(),any())).thenAnswer(i->{((Supplier<?>)i.getArgument(0)).get();((Runnable)i.getArgument(1)).run();((Runnable)i.getArgument(2)).run();throw new IllegalStateException("synthetic indeterminate physical commit");});
        assertThrows(IllegalStateException.class,()->service.execute(cmd));verify(coordinator).perform(ticket);verify(coordinator).finalGuard(ticket);
    }
    @Test void nestedProtectedContextIsRejectedBeforeNativePreparation() {
        doThrow(new IllegalStateException("nested")).when(executor).requireNoProtectedContext();
        assertThrows(IllegalStateException.class,()->service.execute(command()));verifyNoInteractions(coordinator,storage);
    }
    @Test void falseFinalGuardCannotProduceSuccess() {
        var cmd=command();var ticket=mock(ProtectedInstalledUnitAdmissionCoordinator.Admission.class);
        when(coordinator.acquire(any(),eq(cmd))).thenReturn(ticket);when(coordinator.perform(ticket)).thenReturn(result());
        when(executor.executePublication(any(),any(),any())).thenAnswer(i->{((Supplier<?>)i.getArgument(0)).get();((Runnable)i.getArgument(1)).run();((Runnable)i.getArgument(2)).run();return result();});
        assertThrows(IllegalStateException.class,()->service.execute(cmd));
    }
}
