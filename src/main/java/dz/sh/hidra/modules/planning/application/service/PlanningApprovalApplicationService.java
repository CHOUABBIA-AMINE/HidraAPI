/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningApprovalApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Orchestrates revision-scoped workflow approval while keeping planning lifecycle ownership server-side.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import dz.sh.hidra.modules.planning.application.port.in.PlanningApprovalUseCase;
import dz.sh.hidra.modules.planning.application.port.out.PlanRevisionRepositoryPort;
import dz.sh.hidra.modules.planning.domain.model.PlanRevision;
import dz.sh.hidra.modules.planning.domain.value.PlanRevisionStatus;
import dz.sh.hidra.modules.workflow.application.port.in.ExecuteWorkflowTargetTransitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlanningApprovalApplicationService implements PlanningApprovalUseCase {

    private static final String PLANNING_MODULE = "planning";
    private static final Set<String> TERMINAL_WORKFLOW_STATES = Set.of("COMPLETED", "CANCELLED");

    private final PlanRevisionRepositoryPort revisionRepository;
    private final WorkflowQueryUseCase workflowQuery;
    private final ExecuteWorkflowTargetTransitionUseCase workflowTransition;

    public PlanningApprovalApplicationService(
            PlanRevisionRepositoryPort revisionRepository,
            WorkflowQueryUseCase workflowQuery,
            ExecuteWorkflowTargetTransitionUseCase workflowTransition
    ) {
        this.revisionRepository = Objects.requireNonNull(revisionRepository, "PlanRevisionRepositoryPort must not be null.");
        this.workflowQuery = Objects.requireNonNull(workflowQuery, "WorkflowQueryUseCase must not be null.");
        this.workflowTransition = Objects.requireNonNull(workflowTransition, "ExecuteWorkflowTargetTransitionUseCase must not be null.");
    }

    @Override
    @Transactional(readOnly = true)
    public ApprovalView approval(String revisionId, String actorReference, Set<String> effectivePermissions) {
        PlanRevision revision = revision(revisionId);
        WorkflowQueryUseCase.InstanceView instance = validatedInstance(revision);
        if (TERMINAL_WORKFLOW_STATES.contains(normalizeState(instance.status()))) {
            return new ApprovalView(
                    revision.id(), String.valueOf(revision.status()), instance.id(), instance.status(), null, null, List.of()
            );
        }
        WorkflowQueryUseCase.TaskView task = workflowQuery.currentTask(instance.id());
        List<ActionView> actions = workflowQuery.availableActions(task.id(), actorReference, effectivePermissions).stream()
                .map(action -> new ActionView(
                        action.transitionId(), action.decision(), action.reasonRequired(), action.commentRequired(),
                        action.requiredPermissionCode(), action.permitted()
                ))
                .toList();
        return new ApprovalView(
                revision.id(), String.valueOf(revision.status()), instance.id(), instance.status(),
                task.id(), task.updatedAt(), actions
        );
    }

    @Override
    @Transactional
    public ExecutionView execute(String revisionId, String transitionId, ExecutionCommand command) {
        Objects.requireNonNull(command, "Planning approval execution command must not be null.");
        PlanRevision revision = revision(revisionId);
        WorkflowQueryUseCase.InstanceView instance = validatedInstance(revision);
        if (TERMINAL_WORKFLOW_STATES.contains(normalizeState(instance.status()))) {
            throw new IllegalStateException("Planning revision workflow is already terminal: " + instance.id());
        }
        WorkflowQueryUseCase.TaskView task = workflowQuery.currentTask(instance.id());
        ExecuteWorkflowTargetTransitionUseCase.Result result = workflowTransition.execute(
                new ExecuteWorkflowTargetTransitionUseCase.Command(
                        task.id(), transitionId, command.expectedTaskUpdatedAt(), command.reasonId(),
                        command.decisionNote(), command.commentText(), command.correlationId(), command.actorId(),
                        command.actorUsername(), command.actorDisplayName(), command.effectivePermissions()
                )
        );
        if (!Objects.equals(result.instanceId(), instance.id())) {
            throw new IllegalStateException("Workflow transition result does not belong to the planning revision workflow instance.");
        }
        PlanRevisionStatus nextStatus = planningStatus(result.decision());
        Instant executedAt = Objects.requireNonNull(result.executedAt(), "Workflow transition execution timestamp must not be null.");
        PlanRevision updated = revisionRepository.save(new PlanRevision(
                revision.id(), revision.planId(), revision.revisionNumber(), revision.revisionCode(), nextStatus,
                revision.changeReasonCodeId(), revision.changeReasonText(), revision.baseRevisionId(),
                revision.submittedByActorId(), revision.submittedAt(),
                nextStatus == PlanRevisionStatus.APPROVED ? command.actorId() : revision.approvedByActorId(),
                nextStatus == PlanRevisionStatus.APPROVED ? executedAt : revision.approvedAt(),
                revision.workflowInstanceId(), revision.createdAt(), executedAt
        ));
        return new ExecutionView(
                updated.id(), String.valueOf(updated.status()), result.instanceId(), result.instanceStatus(),
                result.transitionId(), result.decision(), result.nextTaskId(), executedAt
        );
    }

    private PlanRevision revision(String revisionId) {
        if (revisionId == null || revisionId.isBlank()) {
            throw new IllegalArgumentException("Plan revision ID must not be blank.");
        }
        return revisionRepository.findById(revisionId.trim())
                .orElseThrow(() -> new NoSuchElementException("Unknown plan revision: " + revisionId.trim()));
    }

    private WorkflowQueryUseCase.InstanceView validatedInstance(PlanRevision revision) {
        if (revision.workflowInstanceId() == null) {
            throw new IllegalStateException("Plan revision is not linked to a workflow approval instance: " + revision.id());
        }
        WorkflowQueryUseCase.InstanceView instance = workflowQuery.instance(revision.workflowInstanceId());
        if (!PLANNING_MODULE.equalsIgnoreCase(String.valueOf(instance.targetModule()))
                || !Objects.equals(revision.id(), instance.targetId())) {
            throw new IllegalStateException("Workflow instance does not target this planning revision: " + instance.id());
        }
        return instance;
    }

    private static PlanRevisionStatus planningStatus(String decision) {
        String normalized = normalizeState(decision);
        return switch (normalized) {
            case "APPROVE" -> PlanRevisionStatus.APPROVED;
            case "REJECT", "REQUEST_CORRECTION", "RETURN" -> PlanRevisionStatus.REJECTED;
            case "CANCEL" -> PlanRevisionStatus.WITHDRAWN;
            case "CORRECT", "DELEGATE", "ESCALATE" -> PlanRevisionStatus.SUBMITTED;
            default -> throw new IllegalStateException("Workflow decision has no planning revision lifecycle effect: " + decision);
        };
    }

    private static String normalizeState(String value) {
        return value == null ? "" : value.trim().toUpperCase(Locale.ROOT);
    }
}
