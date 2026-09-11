/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTransitionApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Executes backend-defined task transitions atomically.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import dz.sh.hidra.modules.workflow.application.command.ExecuteWorkflowTransitionCommand;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTransitionExecutionDto;
import dz.sh.hidra.modules.workflow.application.port.in.ExecuteWorkflowTransitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowActionRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowInstanceRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowStateHistoryRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowStepAssignmentRuleRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowStepRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTaskRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTransitionRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.exception.WorkflowBoundaryViolationException;
import dz.sh.hidra.modules.workflow.domain.exception.WorkflowTransitionConflictException;
import dz.sh.hidra.modules.workflow.domain.exception.WorkflowTransitionDeniedException;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowAction;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStateHistory;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStep;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStepAssignmentRule;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTransition;
import dz.sh.hidra.modules.workflow.domain.service.WorkflowDecisionGuard;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActionType;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowSlaStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public final class WorkflowTransitionApplicationService implements ExecuteWorkflowTransitionUseCase {

    private static final String ALL_PERMISSIONS = "*";
    private static final String SOURCE_SYSTEM = "HIDRA_API";

    private final WorkflowTaskRepositoryPort taskRepository;
    private final WorkflowInstanceRepositoryPort instanceRepository;
    private final WorkflowTransitionRepositoryPort transitionRepository;
    private final WorkflowActionRepositoryPort actionRepository;
    private final WorkflowStateHistoryRepositoryPort stateHistoryRepository;
    private final WorkflowStepRepositoryPort stepRepository;
    private final WorkflowStepAssignmentRuleRepositoryPort assignmentRuleRepository;
    private final WorkflowDecisionGuard decisionGuard = new WorkflowDecisionGuard();

    public WorkflowTransitionApplicationService(
            WorkflowTaskRepositoryPort taskRepository,
            WorkflowInstanceRepositoryPort instanceRepository,
            WorkflowTransitionRepositoryPort transitionRepository,
            WorkflowActionRepositoryPort actionRepository,
            WorkflowStateHistoryRepositoryPort stateHistoryRepository,
            WorkflowStepRepositoryPort stepRepository,
            WorkflowStepAssignmentRuleRepositoryPort assignmentRuleRepository
    ) {
        this.taskRepository = Objects.requireNonNull(taskRepository, "WorkflowTaskRepositoryPort must not be null.");
        this.instanceRepository = Objects.requireNonNull(instanceRepository, "WorkflowInstanceRepositoryPort must not be null.");
        this.transitionRepository = Objects.requireNonNull(transitionRepository, "WorkflowTransitionRepositoryPort must not be null.");
        this.actionRepository = Objects.requireNonNull(actionRepository, "WorkflowActionRepositoryPort must not be null.");
        this.stateHistoryRepository = Objects.requireNonNull(stateHistoryRepository, "WorkflowStateHistoryRepositoryPort must not be null.");
        this.stepRepository = Objects.requireNonNull(stepRepository, "WorkflowStepRepositoryPort must not be null.");
        this.assignmentRuleRepository = Objects.requireNonNull(assignmentRuleRepository, "WorkflowStepAssignmentRuleRepositoryPort must not be null.");
    }

    @Override
    @Transactional
    public WorkflowTransitionExecutionDto execute(ExecuteWorkflowTransitionCommand command) {
        Objects.requireNonNull(command, "ExecuteWorkflowTransitionCommand must not be null.");

        WorkflowTask task = taskRepository.findByIdForUpdate(command.taskId())
                .orElseThrow(() -> new NoSuchElementException("Unknown workflow task: " + command.taskId()));
        if (!command.expectedTaskUpdatedAt().equals(task.updatedAt())) {
            throw new WorkflowTransitionConflictException("Workflow task changed after it was loaded. Refresh available actions before retrying.");
        }
        if (!task.openTask()) {
            throw new WorkflowTransitionConflictException("Workflow task is already completed or is not actionable: " + task.id());
        }
        if (!belongsToActor(task, command.actorId(), command.actorUsername())) {
            throw new WorkflowTransitionDeniedException("Authenticated actor is not assigned or entitled to execute this workflow task.");
        }

        WorkflowInstance instance = instanceRepository.findByIdForUpdate(task.instanceId())
                .orElseThrow(() -> new NoSuchElementException("Unknown workflow instance: " + task.instanceId()));
        if (!instance.nonTerminal()) {
            throw new WorkflowTransitionConflictException("Workflow instance is already terminal: " + instance.id());
        }
        if (!Objects.equals(instance.currentStepId(), task.stepId())) {
            throw new WorkflowTransitionConflictException("Workflow task no longer belongs to the instance current step.");
        }

        WorkflowTransition transition = transitionRepository.findById(command.transitionId())
                .orElseThrow(() -> new NoSuchElementException("Unknown workflow transition: " + command.transitionId()));
        validateTransition(instance, task, transition, command);

        Instant now = Instant.now();
        long sequence = actionRepository.nextSequence(instance.id());
        String actionId = WorkflowId.newId().value();
        WorkflowAction action = actionRepository.save(new WorkflowAction(
                actionId,
                instance.id(),
                task.id(),
                WorkflowActionType.valueOf(transition.decision().name()),
                transition.decision(),
                command.reasonId(),
                command.decisionNote(),
                command.commentText(),
                command.actorId(),
                command.actorUsername(),
                command.actorDisplayName(),
                task.assignedRoleCodeSnapshot(),
                task.assignedOrganizationUnitId(),
                task.assignedOrganizationUnitNameSnapshot(),
                task.assignedRoleCodeSnapshot(),
                command.correlationId(),
                sequence,
                SOURCE_SYSTEM,
                null,
                null,
                now
        ));

        WorkflowTask completedTask = taskRepository.save(completeTask(task, transition.decision(), command.actorId(), now));
        WorkflowStep targetStep = stepRepository.findById(transition.toStepId())
                .orElseThrow(() -> new NoSuchElementException("Unknown workflow target step: " + transition.toStepId()));
        if (!Objects.equals(targetStep.definitionId(), instance.definitionId())) {
            throw new WorkflowBoundaryViolationException("Workflow target step does not belong to the running definition.");
        }

        boolean terminal = transition.decision() == WorkflowDecision.CANCEL
                || !transitionRepository.existsFromStep(instance.definitionId(), targetStep.id());
        WorkflowInstanceStatus nextInstanceStatus = transition.decision() == WorkflowDecision.CANCEL
                ? WorkflowInstanceStatus.CANCELLED
                : terminal ? WorkflowInstanceStatus.COMPLETED : WorkflowInstanceStatus.IN_PROGRESS;

        WorkflowInstance advancedInstance = instanceRepository.save(advanceInstance(
                instance,
                targetStep.id(),
                nextInstanceStatus,
                now
        ));

        String nextTaskId = null;
        if (!terminal) {
            WorkflowTask nextTask = createNextTask(instance, targetStep, now);
            nextTaskId = taskRepository.save(nextTask).id();
        }

        stateHistoryRepository.save(new WorkflowStateHistory(
                WorkflowId.newId().value(),
                instance.id(),
                task.id(),
                task.stepId(),
                targetStep.id(),
                String.valueOf(instance.status()),
                String.valueOf(nextInstanceStatus),
                command.actorId(),
                command.actorUsername(),
                command.actorDisplayName(),
                task.assignedRoleCodeSnapshot(),
                action.id(),
                command.reasonId(),
                command.correlationId(),
                now
        ));

        return new WorkflowTransitionExecutionDto(
                action.id(),
                completedTask.id(),
                String.valueOf(completedTask.status()),
                advancedInstance.id(),
                String.valueOf(advancedInstance.status()),
                transition.id(),
                String.valueOf(transition.decision()),
                advancedInstance.currentStepId(),
                nextTaskId,
                now
        );
    }

    private void validateTransition(
            WorkflowInstance instance,
            WorkflowTask task,
            WorkflowTransition transition,
            ExecuteWorkflowTransitionCommand command
    ) {
        if (!Objects.equals(transition.definitionId(), instance.definitionId())
                || !Objects.equals(transition.fromStepId(), task.stepId())) {
            throw new WorkflowBoundaryViolationException("Workflow transition is not available from the current task step.");
        }
        if (Objects.equals(transition.fromStepId(), transition.toStepId())) {
            throw new WorkflowBoundaryViolationException("Workflow transition must advance to a different step.");
        }
        if (transition.decision() == WorkflowDecision.COMMENT) {
            throw new WorkflowBoundaryViolationException("COMMENT is not a state-advancing transition. Use workflow comment semantics instead.");
        }
        if (transition.conditionExpression() != null) {
            throw new WorkflowBoundaryViolationException("Conditional workflow transition execution is not available until a condition evaluator is configured.");
        }
        if (transition.targetModuleCallback() != null) {
            throw new WorkflowBoundaryViolationException("Workflow target-module callback execution is not available for this transition.");
        }
        if (!permissionSatisfied(transition.requiredPermissionCode(), command.effectivePermissions())) {
            throw new WorkflowTransitionDeniedException("Authenticated actor lacks the permission required by this workflow transition.");
        }
        decisionGuard.ensureReasonAndCommentRules(transition.decision(), command.reasonId(), command.commentText());
        if (transition.reasonRequired() && command.reasonId() == null) {
            throw new WorkflowBoundaryViolationException("Workflow transition requires a reason.");
        }
        if (transition.commentRequired() && command.commentText() == null) {
            throw new WorkflowBoundaryViolationException("Workflow transition requires a comment.");
        }
    }

    private WorkflowTask createNextTask(WorkflowInstance instance, WorkflowStep targetStep, Instant now) {
        WorkflowStepAssignmentRule rule = null;
        if (targetStep.defaultAssignmentRuleId() != null) {
            rule = assignmentRuleRepository.findById(targetStep.defaultAssignmentRuleId())
                    .filter(WorkflowStepAssignmentRule::active)
                    .orElse(null);
        }
        if (rule != null && (!Objects.equals(rule.definitionId(), instance.definitionId())
                || !Objects.equals(rule.stepId(), targetStep.id()))) {
            throw new WorkflowBoundaryViolationException("Workflow target-step assignment rule does not belong to the running definition.");
        }
        return new WorkflowTask(
                WorkflowId.newId().value(),
                instance.id(),
                targetStep.id(),
                WorkflowTaskStatus.OPEN,
                rule == null ? null : rule.actorId(),
                null,
                null,
                rule == null ? null : rule.organizationUnitId(),
                null,
                rule == null ? null : firstNonBlank(rule.roleCode(), rule.organizationRoleCode()),
                null,
                null,
                null,
                null,
                null,
                null,
                rule == null ? null : rule.assignmentModeId(),
                firstNonBlank(targetStep.nameFr(), targetStep.nameEn(), targetStep.code()),
                WorkflowSlaStatus.NORMAL,
                null,
                null,
                null,
                now,
                now
        );
    }

    private static WorkflowTask completeTask(WorkflowTask task, WorkflowDecision decision, String actorId, Instant now) {
        return new WorkflowTask(
                task.id(), task.instanceId(), task.stepId(), taskStatus(decision),
                task.assignedActorId(), task.assignedActorUsernameSnapshot(), task.assignedActorDisplayNameSnapshot(),
                task.assignedOrganizationUnitId(), task.assignedOrganizationUnitNameSnapshot(), task.assignedRoleCodeSnapshot(),
                task.priorityId(), task.dueAt(), task.claimedByActorId(), task.claimedAt(), actorId, now,
                task.assignmentModeId(), task.taskLabelSnapshot(), task.slaStatus(),
                decision == WorkflowDecision.ESCALATE ? now : task.escalatedAt(),
                decision == WorkflowDecision.DELEGATE ? now : task.delegatedAt(),
                task.expiresAt(), task.createdAt(), now
        );
    }

    private static WorkflowInstance advanceInstance(
            WorkflowInstance instance,
            String targetStepId,
            WorkflowInstanceStatus status,
            Instant now
    ) {
        return new WorkflowInstance(
                instance.id(), instance.definitionId(), instance.definitionVersion(), instance.workflowPurposeId(),
                instance.targetModule(), instance.targetTypeId(), instance.targetId(), instance.targetCodeSnapshot(),
                instance.targetLabelSnapshot(), status, targetStepId, instance.startedByActorId(),
                instance.startedByUsernameSnapshot(), instance.startedByDisplayNameSnapshot(), instance.startedByRoleCodeSnapshot(),
                instance.startedAt(), status == WorkflowInstanceStatus.COMPLETED ? now : instance.completedAt(),
                status == WorkflowInstanceStatus.CANCELLED ? now : instance.cancelledAt(),
                instance.correlationId(), instance.createdAt(), now
        );
    }

    private static WorkflowTaskStatus taskStatus(WorkflowDecision decision) {
        return switch (decision) {
            case APPROVE, CORRECT -> WorkflowTaskStatus.APPROVED;
            case REJECT -> WorkflowTaskStatus.REJECTED;
            case REQUEST_CORRECTION, RETURN -> WorkflowTaskStatus.RETURNED;
            case DELEGATE -> WorkflowTaskStatus.DELEGATED;
            case ESCALATE -> WorkflowTaskStatus.ESCALATED;
            case CANCEL -> WorkflowTaskStatus.CANCELLED;
            case COMMENT -> throw new WorkflowBoundaryViolationException("COMMENT cannot complete a workflow task.");
        };
    }

    private static boolean belongsToActor(WorkflowTask task, String actorId, String actorUsername) {
        return Objects.equals(actorId, task.assignedActorId())
                || Objects.equals(actorId, task.claimedByActorId())
                || (actorUsername != null
                    && task.assignedActorUsernameSnapshot() != null
                    && actorUsername.equalsIgnoreCase(task.assignedActorUsernameSnapshot()));
    }

    private static boolean permissionSatisfied(String requiredPermission, Set<String> permissions) {
        return requiredPermission == null
                || permissions.contains(ALL_PERMISSIONS)
                || permissions.contains(requiredPermission);
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value.trim();
            }
        }
        return null;
    }
}
