/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentLifecycleApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-24
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Verifies application callback ordering, failure containment and scope-specific result dispatch.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.command.EmployeeAppointmentCommand;
import dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult;
import dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import dz.sh.hidra.modules.organization.application.port.out.ProtectedEmployeeAppointmentRepositoryPort;
import java.time.Instant;
import java.util.List;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/** Mocked executor/owners test application mechanics only, never physical commit or native authority. */
class EmployeeAppointmentLifecycleApplicationServiceTest {
    private static final Instant AT=Instant.parse("2026-10-24T10:00:00Z");
    private final ProtectedEmployeeAppointmentAdmissionCoordinator coordinator=mock(ProtectedEmployeeAppointmentAdmissionCoordinator.class);
    private final ProtectedTransactionExecutor executor=mock(ProtectedTransactionExecutor.class);
    private final ProtectedEmployeeAppointmentRepositoryPort repository=mock(ProtectedEmployeeAppointmentRepositoryPort.class);
    private final EmployeeAppointmentLifecycleApplicationService service=
            new EmployeeAppointmentLifecycleApplicationService(coordinator,executor,repository);

    private void callbacks(RuntimeException completionFailure) {
        when(executor.executePublication(any(),any(),any())).thenAnswer(call->{
            Object result=((Supplier<?>)call.getArgument(0)).get();
            ((Runnable)call.getArgument(1)).run();
            ((Runnable)call.getArgument(2)).run();
            if(completionFailure!=null)throw completionFailure;
            return result;
        });
    }

    private EmployeeAppointmentCommand.Create create() {
        return new EmployeeAppointmentCommand.Create("req","company","employee","unit","position",
                AssignmentType.PRIMARY,AT,null,1,1);
    }

    @Test void writeMaterializesThenFlushesThenFinalizesOnTheSamePrivateHandle() {
        callbacks(null);var command=create();
        var prepared=mock(ProtectedEmployeeAppointmentAdmissionCoordinator.Prepared.class);
        var admission=mock(ProtectedEmployeeAppointmentAdmissionCoordinator.WriteAdmission.class);
        var result=mock(EmployeeAppointmentResult.MutationReceipt.class);
        when(coordinator.prepare()).thenReturn(prepared);
        when(coordinator.acquireWrite(prepared,command)).thenReturn(admission);
        when(coordinator.apply(admission)).thenReturn(result);
        assertSame(result,service.create(command));
        var order=inOrder(coordinator,executor,repository);
        order.verify(coordinator).prepare();order.verify(executor).executePublication(any(),any(),any());
        order.verify(coordinator).acquireWrite(prepared,command);order.verify(coordinator).apply(admission);
        order.verify(repository).flushManagedWork();order.verify(coordinator).prepareFinal(admission);
        order.verify(coordinator).finalizeOperation(admission);verifyNoMoreInteractions(coordinator,executor,repository);
    }

    @Test void allFourTypedCommandsUseOneFreshPreparationAndOneProtectedExecution() {
        var commands=List.<EmployeeAppointmentCommand>of(create(),
                new EmployeeAppointmentCommand.End("end","company","assignment",1,AT.plusSeconds(1),"reason"),
                new EmployeeAppointmentCommand.Transfer("transfer","company","assignment",1,"destination","position",
                        AssignmentType.ACTING,AT.plusSeconds(1),null,1,1,"reason"),
                new EmployeeAppointmentCommand.Correct("correct","company","assignment",1,"unit","position",
                        AssignmentType.PRIMARY,dz.sh.hidra.modules.organization.domain.value.AssignmentStatus.ACTIVE,
                        AT,null,1,1,"reason"));
        for(var command:commands) {
            reset(coordinator,executor,repository);callbacks(null);
            var prepared=mock(ProtectedEmployeeAppointmentAdmissionCoordinator.Prepared.class);
            var admission=mock(ProtectedEmployeeAppointmentAdmissionCoordinator.WriteAdmission.class);
            var result=mock(EmployeeAppointmentResult.MutationReceipt.class);
            when(coordinator.prepare()).thenReturn(prepared);when(coordinator.acquireWrite(prepared,command)).thenReturn(admission);
            when(coordinator.apply(admission)).thenReturn(result);
            var actual=command instanceof EmployeeAppointmentCommand.Create c?service.create(c)
                    :command instanceof EmployeeAppointmentCommand.End c?service.end(c)
                    :command instanceof EmployeeAppointmentCommand.Transfer c?service.transfer(c)
                    :service.correct((EmployeeAppointmentCommand.Correct)command);
            assertSame(result,actual);verify(coordinator,times(1)).prepare();
            verify(executor,times(1)).executePublication(any(),any(),any());
        }
    }

    @Test void deniedPreparationNeverOpensThePrivateCredentialRoute() {
        when(coordinator.prepare()).thenThrow(new AccessDeniedException("denied"));
        assertThrows(AccessDeniedException.class,()->service.create(create()));
        verifyNoInteractions(executor,repository);
    }

    @Test void deniedAdmissionDoesNotMaterializeFlushOrFinalize() {
        callbacks(null);when(coordinator.acquireWrite(any(),any())).thenThrow(new AccessDeniedException("denied"));
        assertThrows(AccessDeniedException.class,()->service.create(create()));
        verify(coordinator,never()).apply(any());verify(coordinator,never()).prepareFinal(any());
        verify(coordinator,never()).finalizeOperation(any());verifyNoInteractions(repository);
    }

    @Test void managedFlushFailureCannotReachPreparationOrFinalGuard() {
        callbacks(null);doThrow(new IllegalStateException("flush failed")).when(repository).flushManagedWork();
        assertThrows(IllegalStateException.class,()->service.create(create()));
        verify(coordinator,never()).prepareFinal(any());verify(coordinator,never()).finalizeOperation(any());
        verify(executor,times(1)).executePublication(any(),any(),any());
    }

    @Test void finalPreparationFailureCannotReachTheScalarGuard() {
        callbacks(null);doThrow(new AccessDeniedException("revoked")).when(coordinator).prepareFinal(any());
        assertThrows(AccessDeniedException.class,()->service.create(create()));
        verify(coordinator,never()).finalizeOperation(any());verify(executor,times(1)).executePublication(any(),any(),any());
    }

    @Test void finalGuardOrCompletionFailureNeverReturnsTheDetachedResultOrRetries() {
        for(boolean completion:List.of(false,true)) {
            reset(coordinator,executor,repository);
            var failure=new IllegalStateException(completion?"commit outcome unknown":"guard rejected");
            callbacks(completion?failure:null);
            if(!completion)doThrow(failure).when(coordinator).finalizeOperation(any());
            assertSame(failure,assertThrows(IllegalStateException.class,()->service.create(create())));
            verify(executor,times(1)).executePublication(any(),any(),any());verify(coordinator,times(1)).prepare();
        }
    }

    @Test void privateAndRedactedHistoryDispatchCannotBeInterchanged() {
        var queries=List.of(EmployeeAppointmentQuery.History.assignment("company","assignment",2,null),
                EmployeeAppointmentQuery.History.employee("company","employee",2,null),
                EmployeeAppointmentQuery.History.position("company","unit","position",2,null));
        for(var query:queries) {
            reset(coordinator,executor,repository);callbacks(null);
            var prepared=mock(ProtectedEmployeeAppointmentAdmissionCoordinator.Prepared.class);
            var admission=mock(ProtectedEmployeeAppointmentAdmissionCoordinator.ReadAdmission.class);
            when(coordinator.prepare()).thenReturn(prepared);when(coordinator.acquireRead(prepared,query)).thenReturn(admission);
            if(query.scope()==EmployeeAppointmentQuery.Scope.POSITION) {
                var result=new EmployeeAppointmentResult.HistoryPage<EmployeeAppointmentResult.PositionHistoryEvent>(List.of(),null,false);
                when(coordinator.positionHistory(admission)).thenReturn(result);assertSame(result,service.positionHistory(query));
                verify(coordinator,never()).privateHistory(any());
            } else {
                var result=new EmployeeAppointmentResult.HistoryPage<EmployeeAppointmentResult.PrivateHistoryEvent>(List.of(),null,false);
                when(coordinator.privateHistory(admission)).thenReturn(result);
                assertSame(result,query.scope()==EmployeeAppointmentQuery.Scope.EMPLOYEE
                        ?service.employeeHistory(query):service.assignmentHistory(query));
                verify(coordinator,never()).positionHistory(any());
            }
            var order=inOrder(coordinator,repository);
            order.verify(coordinator).acquireRead(prepared,query);order.verify(coordinator).sealRead(admission);
            order.verify(repository).flushManagedWork();order.verify(coordinator).prepareFinalRead(admission);
            order.verify(coordinator).finalizeRead(admission);
        }
    }

    @Test void exactVersionAndPolicyUseTheirOwnAuthorizedReadAndFinalGuard() {
        callbacks(null);var admission=mock(ProtectedEmployeeAppointmentAdmissionCoordinator.ReadAdmission.class);
        when(coordinator.acquireRead(any(),any())).thenReturn(admission);
        var version=mock(EmployeeAppointmentResult.PinnedVersion.class);var policy=mock(EmployeeAppointmentResult.PolicyView.class);
        when(coordinator.exactVersion(admission)).thenReturn(version);when(coordinator.readPolicy(admission)).thenReturn(policy);
        assertSame(version,service.exactVersion(new EmployeeAppointmentQuery.ExactVersion("company","assignment",1)));
        assertSame(policy,service.readPolicy(new EmployeeAppointmentQuery.Policy("company","unit")));
        verify(coordinator,times(2)).sealRead(admission);verify(coordinator,times(2)).finalizeRead(admission);
    }

    @Test void mismatchedHistoryScopeIsRejectedBeforeAnyPreparationOrSql() {
        var employee=EmployeeAppointmentQuery.History.employee("company","employee",1,null);
        var position=EmployeeAppointmentQuery.History.position("company","unit","position",1,null);
        assertThrows(IllegalArgumentException.class,()->service.assignmentHistory(employee));
        assertThrows(IllegalArgumentException.class,()->service.employeeHistory(position));
        assertThrows(IllegalArgumentException.class,()->service.positionHistory(employee));
        verifyNoInteractions(coordinator,executor,repository);
    }
}
