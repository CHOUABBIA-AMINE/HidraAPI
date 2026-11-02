/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ManufacturerCharacteristicRevisionApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.service
 *
 * @Description : Checks the synthetic manufacturer characteristic contract and denial cases.
 *
 */
package dz.sh.hidra.modules.simulation.application.service;

import dz.sh.hidra.modules.simulation.application.port.in.ManufacturerCharacteristicRevisionUseCase.*;
import dz.sh.hidra.modules.simulation.application.port.out.ManufacturerCharacteristicRevisionRepositoryPort;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.PlatformTransactionManager;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ManufacturerCharacteristicRevisionApplicationServiceTest {
    @Test void invalidWorkflowDigestDoesNotInvokeProtectedExecutor(){
        var coordinator=mock(ProtectedManufacturerCharacteristicAdmissionCoordinator.class);
        var repo=mock(ManufacturerCharacteristicRevisionRepositoryPort.class);
        var executor=mock(ProtectedTransactionExecutor.class);
        var manager=mock(PlatformTransactionManager.class);
        var service=new ManufacturerCharacteristicRevisionApplicationService(coordinator,repo,executor,manager);
        assertEquals(Optional.empty(),service.resolveWorkflowTarget("invalid",false));
        verifyNoInteractions(coordinator,repo,executor,manager);
    }
    @Test void missingCommandIsRejectedBeforeNativePreparation(){
        var coordinator=mock(ProtectedManufacturerCharacteristicAdmissionCoordinator.class);
        var repo=mock(ManufacturerCharacteristicRevisionRepositoryPort.class);
        var executor=mock(ProtectedTransactionExecutor.class);
        var manager=mock(PlatformTransactionManager.class);
        var service=new ManufacturerCharacteristicRevisionApplicationService(coordinator,repo,executor,manager);
        assertThrows(NullPointerException.class,()->service.execute(null));
        verifyNoInteractions(coordinator,repo,executor,manager);
    }
    @Test void malformedExactCompanySourceAndRevisionAreRejected(){
        assertThrows(IllegalArgumentException.class,()->new Command(Operation.READ_REVISION," company","s","v1",null,null));
        assertThrows(IllegalArgumentException.class,()->new Command(Operation.LIST_HISTORY,"company","s","v1",null,null));
        assertThrows(NullPointerException.class,()->new Command(Operation.SAVE,"company","s","v1",null,null));
        assertThrows(NullPointerException.class,()->new Command(Operation.QUALIFY,"company","s","v1",null,null));
    }
    @Test void historyPageQueryValidatesScopeAndNullCannotAcquirePrivileges(){
        assertThrows(IllegalArgumentException.class,()->new HistoryPageQuery(" company","source",10,null));
        assertThrows(IllegalArgumentException.class,()->new HistoryPageQuery("company"," source",10,null));
        var coordinator=mock(ProtectedManufacturerCharacteristicAdmissionCoordinator.class);
        var repo=mock(ManufacturerCharacteristicRevisionRepositoryPort.class);
        var executor=mock(ProtectedTransactionExecutor.class);
        var manager=mock(PlatformTransactionManager.class);
        var service=new ManufacturerCharacteristicRevisionApplicationService(coordinator,repo,executor,manager);
        assertThrows(NullPointerException.class,()->service.pageHistory(null));
        verifyNoInteractions(coordinator,repo,executor,manager);
    }

    @Test void workflowLookupMaterializesRecordInsideExecutorBeforeWrappingOptional() throws Exception {
        var coordinator=mock(ProtectedManufacturerCharacteristicAdmissionCoordinator.class);
        var repo=mock(ManufacturerCharacteristicRevisionRepositoryPort.class);
        var executor=mock(ProtectedTransactionExecutor.class);
        var manager=mock(PlatformTransactionManager.class);
        when(manager.getTransaction(any())).thenReturn(
            new org.springframework.transaction.support.SimpleTransactionStatus());
        var prepared=mock(ProtectedManufacturerCharacteristicAdmissionCoordinator.Prepared.class);
        var admission=mock(ProtectedManufacturerCharacteristicAdmissionCoordinator.Admission.class);
        String digest="b".repeat(64);
        when(coordinator.prepareWorkflowTarget(digest,false)).thenReturn(prepared);
        when(coordinator.acquireWorkflowTarget(prepared,digest,false)).thenReturn(admission);
        when(coordinator.perform(admission)).thenReturn(new Result(Operation.READ_REVISION,
            "company","source","v1","a".repeat(64),null,java.util.List.of(),
            java.time.Instant.parse("2026-11-02T00:00:00Z"),false));
        when(coordinator.targetDigest(any(),eq("a".repeat(64)))).thenReturn(digest);
        var materialized=ProtectedTransactionExecutor.class.getDeclaredMethod(
            "materialized",Object.class,int.class);
        materialized.setAccessible(true);
        when(executor.executePublication(any(),any(),any())).thenAnswer(invocation->{
            Object value=((java.util.function.Supplier<?>)invocation.getArgument(0)).get();
            // Exercise the real unchanged executor result-shape check; Optional fails here.
            materialized.invoke(null,value,0);
            assertInstanceOf(ProtectedManufacturerCharacteristicAdmissionCoordinator.WorkflowTargetView.class,value);
            return value;
        });
        var service=new ManufacturerCharacteristicRevisionApplicationService(coordinator,repo,executor,manager);
        assertEquals(Optional.of(new ProtectedManufacturerCharacteristicAdmissionCoordinator.WorkflowTargetView(
            digest,"v1")),service.resolveWorkflowTarget(digest,false));
        verify(manager).commit(any());
    }
}
