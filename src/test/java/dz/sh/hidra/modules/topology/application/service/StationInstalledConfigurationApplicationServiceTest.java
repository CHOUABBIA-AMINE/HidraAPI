/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInstalledConfigurationApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Bounded station configuration transaction and rejection tests.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.model.StationInstalledConfigurationFailure;
import dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase.*;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.*;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class StationInstalledConfigurationApplicationServiceTest {
    private final ProtectedStationConfigurationAdmissionCoordinator coordinator=mock(ProtectedStationConfigurationAdmissionCoordinator.class);
    private final ProtectedTransactionExecutor executor=mock(ProtectedTransactionExecutor.class);
    private final StationInstalledConfigurationApplicationService service=
        new StationInstalledConfigurationApplicationService(coordinator,executor);

    private Command read(){return new Command(Operation.READ_CURRENT,"company","station",null,null,0);}

    @Test void physicalCommitRequiredBeforeReturningProtectedResult(){
        var cmd=read();
        var prepared=mock(ProtectedStationConfigurationAdmissionCoordinator.Prepared.class);
        var ticket=mock(ProtectedStationConfigurationAdmissionCoordinator.Admission.class);
        var result=new Result(null,List.of(),false);
        when(coordinator.prepare(cmd)).thenReturn(prepared);
        when(coordinator.acquire(prepared,cmd)).thenReturn(ticket);
        when(coordinator.perform(ticket)).thenReturn(result);
        when(coordinator.finalGuard(ticket)).thenReturn(true);
        when(executor.executePublication(any(),any(),any())).thenAnswer(i->{
            Object value=((Supplier<?>)i.getArgument(0)).get();
            ((Runnable)i.getArgument(1)).run();
            ((Runnable)i.getArgument(2)).run();
            return value;
        });
        assertSame(result,service.execute(cmd));
        var order=inOrder(executor,coordinator);
        order.verify(executor).requireNoProtectedContext();
        order.verify(coordinator).prepare(cmd);
        order.verify(executor).executePublication(any(),any(),any());
        order.verify(coordinator).acquire(prepared,cmd);
        order.verify(coordinator).perform(ticket);
        order.verify(coordinator).flush();
        order.verify(coordinator).prepareFinal(ticket);
        order.verify(coordinator).finalGuard(ticket);
    }

    @Test void failedFinalGuardDoesNotReturnReadOrSaveData(){
        var cmd=read();
        var prepared=mock(ProtectedStationConfigurationAdmissionCoordinator.Prepared.class);
        var ticket=mock(ProtectedStationConfigurationAdmissionCoordinator.Admission.class);
        when(coordinator.prepare(cmd)).thenReturn(prepared);
        when(coordinator.acquire(prepared,cmd)).thenReturn(ticket);
        when(coordinator.perform(ticket)).thenReturn(new Result(null,List.of(),false));
        when(coordinator.finalGuard(ticket)).thenReturn(false);
        when(executor.executePublication(any(),any(),any())).thenAnswer(i->{
            ((Supplier<?>)i.getArgument(0)).get();
            ((Runnable)i.getArgument(1)).run();
            ((Runnable)i.getArgument(2)).run();
            return null;
        });
        assertEquals(StationInstalledConfigurationFailure.Kind.UNAVAILABLE,
            assertThrows(StationInstalledConfigurationFailure.class,()->service.execute(cmd)).kind());
    }

    @Test void failedCommitNeverAutomaticallyRetries(){
        var cmd=read();
        var prepared=mock(ProtectedStationConfigurationAdmissionCoordinator.Prepared.class);
        when(coordinator.prepare(cmd)).thenReturn(prepared);
        when(executor.executePublication(any(),any(),any())).thenThrow(new IllegalStateException("commit unavailable"));
        assertEquals(StationInstalledConfigurationFailure.Kind.UNAVAILABLE,
            assertThrows(StationInstalledConfigurationFailure.class,()->service.execute(cmd)).kind());
        verify(executor,times(1)).executePublication(any(),any(),any());
    }

    @Test void malformedHistoryReadRejectsBeforeNativeService(){
        assertThrows(IllegalArgumentException.class,()->new Command(Operation.LIST_HISTORY,
            "company","station",null,null,0));
        verifyNoInteractions(coordinator,executor);
    }

    @Test void readRevisionRequiresAnExactRevision(){
        assertThrows(IllegalArgumentException.class,()->new Command(Operation.READ_REVISION,
            "company","station",null,null,0));
        verifyNoInteractions(coordinator,executor);
    }

    @Test void explicitScopeAndLimitAreValidatedBeforeExecution(){
        assertThrows(IllegalArgumentException.class,()->new Command(Operation.READ_CURRENT,
            " company","station",null,null,0));
        assertThrows(IllegalArgumentException.class,()->new Command(Operation.LIST_HISTORY,
            "company","station",null,null,101));
        verifyNoInteractions(coordinator,executor);
    }
}
