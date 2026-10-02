/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTransitionApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Test
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Verifies authoritative workflow transition execution semantics.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.workflow.application.command.ExecuteWorkflowTransitionCommand;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTransitionExecutionDto;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowActionRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowInstanceRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowStateHistoryRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowStepAssignmentRuleRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowStepRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTaskRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTransitionRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.exception.WorkflowTransitionConflictException;
import dz.sh.hidra.modules.workflow.domain.exception.WorkflowTransitionDeniedException;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowAction;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStateHistory;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStep;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStepAssignmentRule;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTransition;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowSlaStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;
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
class WorkflowTransitionApplicationServiceTest {

    private static final Instant TASK_UPDATED_AT = Instant.parse("2026-09-11T12:00:00Z");

    @Mock private WorkflowTaskRepositoryPort taskRepository;
    @Mock private WorkflowInstanceRepositoryPort instanceRepository;
    @Mock private WorkflowTransitionRepositoryPort transitionRepository;
    @Mock private WorkflowActionRepositoryPort actionRepository;
    @Mock private WorkflowStateHistoryRepositoryPort stateHistoryRepository;
    @Mock private WorkflowStepRepositoryPort stepRepository;
    @Mock private WorkflowStepAssignmentRuleRepositoryPort assignmentRuleRepository;

    private WorkflowTransitionApplicationService service;

    @BeforeEach
    void setUp() {
        service = new WorkflowTransitionApplicationService(
                taskRepository,
                instanceRepository,
                transitionRepository,
                actionRepository,
                stateHistoryRepository,
                stepRepository,
                assignmentRuleRepository
        );
    }

    @Test
    void executesPermittedTransitionAndCreatesNextTask() {
        stubCommon(true);
        WorkflowStepAssignmentRule rule = new WorkflowStepAssignmentRule(
                "rule-2", "def-1", "step-2", "DIRECT", "actor-2", "REVIEWER",
                "org-2", null, null, true, TASK_UPDATED_AT, TASK_UPDATED_AT
        );
        when(assignmentRuleRepository.findById("rule-2")).thenReturn(Optional.of(rule));

        WorkflowTransitionExecutionDto result = service.execute(command("actor-1", "alice", Set.of("workflow:approve:execute")));

        assertEquals("APPROVED", result.taskStatus());
        assertEquals("IN_PROGRESS", result.instanceStatus());
        assertEquals("step-2", result.currentStepId());
        assertNotNull(result.nextTaskId());

        ArgumentCaptor<WorkflowTask> tasks = ArgumentCaptor.forClass(WorkflowTask.class);
        verify(taskRepository, org.mockito.Mockito.times(2)).save(tasks.capture());
        List<WorkflowTask> saved = tasks.getAllValues();
        assertEquals(WorkflowTaskStatus.APPROVED, saved.get(0).status());
        assertEquals("actor-1", saved.get(0).completedByActorId());
        assertEquals(WorkflowTaskStatus.OPEN, saved.get(1).status());
        assertEquals("step-2", saved.get(1).stepId());
        assertEquals("actor-2", saved.get(1).assignedActorId());

        verify(actionRepository).save(any(WorkflowAction.class));
        verify(stateHistoryRepository).save(any(WorkflowStateHistory.class));
    }

    @Test
    void completesInstanceWhenTargetStepIsTerminal() {
        stubCommon(false);

        WorkflowTransitionExecutionDto result = service.execute(command("actor-1", "alice", Set.of("workflow:approve:execute")));

        assertEquals("COMPLETED", result.instanceStatus());
        assertNull(result.nextTaskId());
        verify(assignmentRuleRepository, never()).findById(any());
    }

    @Test
    void rejectsStaleTaskVersionBeforeMutation() {
        WorkflowTask task = task();
        when(taskRepository.findByIdForUpdate("task-1")).thenReturn(Optional.of(task));

        ExecuteWorkflowTransitionCommand stale = new ExecuteWorkflowTransitionCommand(
                "task-1", "transition-1", TASK_UPDATED_AT.minusSeconds(1), null, null, null,
                "corr-1", "actor-1", "alice", "Alice", Set.of("workflow:approve:execute")
        );

        assertThrows(WorkflowTransitionConflictException.class, () -> service.execute(stale));
        verify(instanceRepository, never()).findByIdForUpdate(any());
        verify(actionRepository, never()).save(any());
    }

    @Test
    void rejectsActorWhoDoesNotOwnTask() {
        when(taskRepository.findByIdForUpdate("task-1")).thenReturn(Optional.of(task()));

        assertThrows(
                WorkflowTransitionDeniedException.class,
                () -> service.execute(command("actor-other", "mallory", Set.of("workflow:approve:execute")))
        );
        verify(instanceRepository, never()).findByIdForUpdate(any());
    }

    @Test
    void rejectsMissingTransitionPermission() {
        WorkflowTask task = task();
        WorkflowInstance instance = instance();
        when(taskRepository.findByIdForUpdate("task-1")).thenReturn(Optional.of(task));
        when(instanceRepository.findByIdForUpdate("instance-1")).thenReturn(Optional.of(instance));
        when(transitionRepository.findById("transition-1")).thenReturn(Optional.of(transition()));

        assertThrows(WorkflowTransitionDeniedException.class, () -> service.execute(command("actor-1", "alice", Set.of())));
        verify(actionRepository, never()).save(any());
    }

    private void stubCommon(boolean graphContinues) {
        WorkflowTask task = task();
        WorkflowInstance instance = instance();
        WorkflowTransition transition = transition();
        WorkflowStep targetStep = new WorkflowStep(
                "step-2", "def-1", "FINAL_REVIEW", null, "Validation finale", "Final review", 2,
                true, "APPROVAL", "rule-2", null, false, false, false, TASK_UPDATED_AT, TASK_UPDATED_AT
        );
        when(taskRepository.findByIdForUpdate("task-1")).thenReturn(Optional.of(task));
        when(instanceRepository.findByIdForUpdate("instance-1")).thenReturn(Optional.of(instance));
        when(transitionRepository.findById("transition-1")).thenReturn(Optional.of(transition));
        when(actionRepository.nextSequence("instance-1")).thenReturn(4L);
        when(actionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(taskRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(instanceRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(stateHistoryRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(stepRepository.findById("step-2")).thenReturn(Optional.of(targetStep));
        when(transitionRepository.existsFromStep("def-1", "step-2")).thenReturn(graphContinues);
    }

    private ExecuteWorkflowTransitionCommand command(String actorId, String actorUsername, Set<String> permissions) {
        return new ExecuteWorkflowTransitionCommand(
                "task-1", "transition-1", TASK_UPDATED_AT, null, null, null, "corr-1",
                actorId, actorUsername, "Alice", permissions
        );
    }

    private WorkflowTask task() {
        return new WorkflowTask(
                "task-1", "instance-1", "step-1", WorkflowTaskStatus.OPEN,
                "actor-1", "alice", "Alice", "org-1", "Operations", "VALIDATOR",
                null, null, null, null, null, null, "DIRECT", "Review reading", WorkflowSlaStatus.NORMAL,
                null, null, null, TASK_UPDATED_AT.minusSeconds(60), TASK_UPDATED_AT
        );
    }

    private WorkflowInstance instance() {
        return new WorkflowInstance(
                "instance-1", "def-1", 1, "VALIDATION", "telemetry", "TELEMETRY_READING", "reading-1",
                "R-1", "Reading 1", WorkflowInstanceStatus.IN_PROGRESS, "step-1",
                "starter-1", "starter", "Starter", "OPERATOR", TASK_UPDATED_AT.minusSeconds(120),
                null, null, "corr-1", TASK_UPDATED_AT.minusSeconds(120), TASK_UPDATED_AT
        );
    }

    private WorkflowTransition transition() {
        return new WorkflowTransition(
                "transition-1", "def-1", "step-1", "step-2", WorkflowDecision.APPROVE,
                false, false, null, "workflow:approve:execute", null, TASK_UPDATED_AT, TASK_UPDATED_AT
        );
    }
}
