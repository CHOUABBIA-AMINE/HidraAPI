/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningApprovalApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Test
 * @Layer       : Application Test
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Verifies revision-scoped workflow approval lifecycle effects and stale conflicts.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.planning.application.PlanningApprovalException;
import dz.sh.hidra.modules.planning.application.port.in.PlanningApprovalUseCase;
import dz.sh.hidra.modules.planning.application.port.out.PlanRevisionRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanningApprovalWorkflowPort;
import dz.sh.hidra.modules.planning.domain.model.PlanRevision;
import dz.sh.hidra.modules.planning.domain.value.PlanRevisionStatus;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlanningApprovalApplicationServiceTest {

    private static final Instant TASK_UPDATED_AT = Instant.parse("2026-09-12T06:00:00Z");
    private static final Instant EXECUTED_AT = Instant.parse("2026-09-12T06:05:00Z");

    @Mock private PlanRevisionRepositoryPort revisionRepository;
    @Mock private PlanningApprovalWorkflowPort workflowPort;

    private PlanningApprovalApplicationService service;

    @BeforeEach
    void setUp() {
        service = new PlanningApprovalApplicationService(revisionRepository, workflowPort);
    }

    @Test
    void exposesExactRevisionApprovalContext() {
        when(revisionRepository.findById("rev-1")).thenReturn(Optional.of(submittedRevision()));
        when(workflowPort.context("wf-1", "rev-1", "actor-1", Set.of("workflow:approve:execute")))
                .thenReturn(context(true));

        PlanningApprovalUseCase.ApprovalView view = service.approval(
                "rev-1", "actor-1", Set.of("workflow:approve:execute")
        );

        assertEquals(true, view.underApproval());
        assertEquals("wf-1", view.workflowInstanceId());
        assertEquals("task-1", view.taskId());
        assertEquals("transition-approve", view.actions().getFirst().transitionId());
    }

    @Test
    void approveTransitionPersistsApprovedRevision() {
        when(revisionRepository.findById("rev-1")).thenReturn(Optional.of(submittedRevision()));
        when(workflowPort.context(any(), any(), any(), any())).thenReturn(context(true));
        when(workflowPort.execute(any())).thenReturn(new PlanningApprovalWorkflowPort.Execution(
                "action-1", "task-1", "APPROVED", "wf-1", "COMPLETED",
                "transition-approve", "APPROVE", EXECUTED_AT
        ));
        when(revisionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        PlanningApprovalUseCase.ExecutionView result = service.execute(
                "rev-1",
                "transition-approve",
                command(TASK_UPDATED_AT)
        );

        assertEquals("APPROVED", result.revisionStatus());
        ArgumentCaptor<PlanRevision> saved = ArgumentCaptor.forClass(PlanRevision.class);
        verify(revisionRepository).save(saved.capture());
        assertEquals(PlanRevisionStatus.APPROVED, saved.getValue().status());
        assertEquals("actor-1", saved.getValue().approvedByActorId());
        assertEquals(EXECUTED_AT, saved.getValue().approvedAt());
    }

    @Test
    void staleApprovalContextFailsBeforeWorkflowMutation() {
        when(revisionRepository.findById("rev-1")).thenReturn(Optional.of(submittedRevision()));
        when(workflowPort.context(any(), any(), any(), any())).thenReturn(context(true));

        PlanningApprovalException exception = assertThrows(
                PlanningApprovalException.class,
                () -> service.execute("rev-1", "transition-approve", command(TASK_UPDATED_AT.minusSeconds(1)))
        );

        assertEquals(PlanningApprovalException.Kind.CONFLICT, exception.kind());
    }

    private PlanningApprovalWorkflowPort.ApprovalContext context(boolean permitted) {
        return new PlanningApprovalWorkflowPort.ApprovalContext(
                "wf-1", "IN_PROGRESS", "task-1", "OPEN", TASK_UPDATED_AT,
                List.of(new PlanningApprovalWorkflowPort.Action(
                        "transition-approve", "APPROVE", false, false,
                        "workflow:approve:execute", permitted
                ))
        );
    }

    private PlanningApprovalUseCase.ExecuteCommand command(Instant expectedTaskUpdatedAt) {
        return new PlanningApprovalUseCase.ExecuteCommand(
                expectedTaskUpdatedAt, null, null, null, "corr-1",
                "actor-1", "alice", "Alice", Set.of("workflow:approve:execute")
        );
    }

    private PlanRevision submittedRevision() {
        return new PlanRevision(
                "rev-1", "plan-1", 2, "R02", PlanRevisionStatus.SUBMITTED,
                null, null, "rev-0", "submitter-1", TASK_UPDATED_AT.minusSeconds(600),
                null, null, "wf-1", TASK_UPDATED_AT.minusSeconds(1200), TASK_UPDATED_AT.minusSeconds(600)
        );
    }
}
