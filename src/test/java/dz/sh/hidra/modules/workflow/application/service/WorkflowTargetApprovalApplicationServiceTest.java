/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTargetApprovalApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Test
 * @Layer       : Application Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Verifies exact target approval delegation, permission enforcement, and stale-task conflicts.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.workflow.application.dto.WorkflowTransitionExecutionDto;
import dz.sh.hidra.modules.workflow.application.port.in.ExecuteWorkflowTransitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowTargetApprovalUseCase;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTargetApprovalQueryPort;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WorkflowTargetApprovalApplicationServiceTest {

    private static final Instant TASK_UPDATED_AT = Instant.parse("2026-09-12T06:00:00Z");

    @Mock private WorkflowTargetApprovalQueryPort queryPort;
    @Mock private ExecuteWorkflowTransitionUseCase transitionUseCase;

    private WorkflowTargetApprovalApplicationService service;

    @BeforeEach
    void setUp() {
        service = new WorkflowTargetApprovalApplicationService(queryPort, transitionUseCase);
    }

    @Test
    void executesOnlyTargetScopedPermittedTransition() {
        when(queryPort.load(any(), any(), any(), any(), any(), any())).thenReturn(context(true));
        when(transitionUseCase.execute(any())).thenReturn(new WorkflowTransitionExecutionDto(
                "action-1", "task-1", "APPROVED", "wf-1", "COMPLETED",
                "transition-approve", "APPROVE", "step-final", null, TASK_UPDATED_AT.plusSeconds(5)
        ));

        WorkflowTargetApprovalUseCase.Execution execution = service.execute(command(TASK_UPDATED_AT));

        assertEquals("APPROVE", execution.decision());
        assertEquals("wf-1", execution.instanceId());
        verify(transitionUseCase).execute(any());
    }

    @Test
    void deniesTransitionWhenBackendContextMarksItNotPermitted() {
        when(queryPort.load(any(), any(), any(), any(), any(), any())).thenReturn(context(false));

        WorkflowTargetApprovalUseCase.TargetApprovalException exception = assertThrows(
                WorkflowTargetApprovalUseCase.TargetApprovalException.class,
                () -> service.execute(command(TASK_UPDATED_AT))
        );

        assertEquals(WorkflowTargetApprovalUseCase.TargetApprovalException.Kind.DENIED, exception.kind());
        verify(transitionUseCase, never()).execute(any());
    }

    @Test
    void detectsStaleTaskBeforeTransitionExecution() {
        when(queryPort.load(any(), any(), any(), any(), any(), any())).thenReturn(context(true));

        WorkflowTargetApprovalUseCase.TargetApprovalException exception = assertThrows(
                WorkflowTargetApprovalUseCase.TargetApprovalException.class,
                () -> service.execute(command(TASK_UPDATED_AT.minusSeconds(1)))
        );

        assertEquals(WorkflowTargetApprovalUseCase.TargetApprovalException.Kind.CONFLICT, exception.kind());
        verify(transitionUseCase, never()).execute(any());
    }

    private WorkflowTargetApprovalQueryPort.Context context(boolean permitted) {
        return new WorkflowTargetApprovalQueryPort.Context(
                "wf-1", "IN_PROGRESS", "task-1", "OPEN", TASK_UPDATED_AT,
                List.of(new WorkflowTargetApprovalQueryPort.Action(
                        "transition-approve", "APPROVE", false, false,
                        "workflow:approve:execute", permitted
                ))
        );
    }

    private WorkflowTargetApprovalUseCase.ExecuteCommand command(Instant expectedTaskUpdatedAt) {
        return new WorkflowTargetApprovalUseCase.ExecuteCommand(
                "wf-1", "planning", "PLAN_REVISION", "rev-1", "transition-approve",
                expectedTaskUpdatedAt, null, null, null, "corr-1",
                "actor-1", "alice", "Alice", Set.of("workflow:approve:execute")
        );
    }
}
