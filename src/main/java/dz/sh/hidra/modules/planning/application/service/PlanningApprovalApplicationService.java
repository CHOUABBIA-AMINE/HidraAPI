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
 * @Description : Coordinates workflow approval actions and atomically applies their planning revision lifecycle effect.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import dz.sh.hidra.modules.planning.application.PlanningApprovalException;
import dz.sh.hidra.modules.planning.application.port.in.PlanningApprovalUseCase;
import dz.sh.hidra.modules.planning.application.port.out.PlanRevisionRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanningApprovalWorkflowPort;
import dz.sh.hidra.modules.planning.domain.model.PlanRevision;
import dz.sh.hidra.modules.planning.domain.value.PlanRevisionStatus;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public final class PlanningApprovalApplicationService implements PlanningApprovalUseCase {

    private static final String DECISION_APPROVE = "APPROVE";
    private static final String DECISION_REJECT = "REJECT";

    private final PlanRevisionRepositoryPort revisionRepository;
    private final PlanningApprovalWorkflowPort workflowPort;

    public PlanningApprovalApplicationService(
            PlanRevisionRepositoryPort revisionRepository,
            PlanningApprovalWorkflowPort workflowPort
    ) {
        this.revisionRepository = Objects.requireNonNull(revisionRepository, "PlanRevisionRepositoryPort must not be null.");
        this.workflowPort = Objects.requireNonNull(workflowPort, "PlanningApprovalWorkflowPort must not be null.");
    }

    @Override
    @Transactional(readOnly = true)
    public ApprovalView approval(String revisionId, String actorId, Set<String> effectivePermissions) {
        PlanRevision revision = revision(revisionId);
        if (revision.status() != PlanRevisionStatus.SUBMITTED || revision.workflowInstanceId() == null) {
            return new ApprovalView(
                    revision.id(), String.valueOf(revision.status()), false, revision.workflowInstanceId(),
                    null, null, null, null, List.of()
            );
        }
        PlanningApprovalWorkflowPort.ApprovalContext context = workflowPort.context(
                revision.workflowInstanceId(), revision.id(), actorId, effectivePermissions
        );
        return new ApprovalView(
                revision.id(), String.valueOf(revision.status()), true,
                context.workflowInstanceId(), context.workflowInstanceStatus(), context.taskId(), context.taskStatus(),
                context.taskUpdatedAt(), context.actions().stream().map(this::mapAction).toList()
        );
    }

    @Override
    @Transactional
    public ExecutionView execute(String revisionId, String transitionId, ExecuteCommand command) {
        Objects.requireNonNull(command, "Planning approval execution command must not be null.");
        PlanRevision revision = revision(revisionId);
        if (revision.status() != PlanRevisionStatus.SUBMITTED || revision.workflowInstanceId() == null) {
            throw new PlanningApprovalException(
                    PlanningApprovalException.Kind.CONFLICT,
                    "Plan revision is not currently submitted for workflow approval."
            );
        }
        PlanningApprovalWorkflowPort.ApprovalContext context = workflowPort.context(
                revision.workflowInstanceId(), revision.id(), command.actorId(), command.effectivePermissions()
        );
        PlanningApprovalWorkflowPort.Action action = context.actions().stream()
                .filter(candidate -> Objects.equals(candidate.transitionId(), transitionId))
                .findFirst()
                .orElseThrow(() -> new PlanningApprovalException(
                        PlanningApprovalException.Kind.BOUNDARY,
                        "Workflow transition is not available for this plan revision."
                ));
        if (!action.permitted()) {
            throw new PlanningApprovalException(
                    PlanningApprovalException.Kind.DENIED,
                    "Authenticated actor is not permitted to execute this plan revision approval action."
            );
        }
        if (!DECISION_APPROVE.equals(action.decision()) && !DECISION_REJECT.equals(action.decision())) {
            throw new PlanningApprovalException(
                    PlanningApprovalException.Kind.BOUNDARY,
                    "Planning approval endpoint supports only backend-defined APPROVE or REJECT decisions."
            );
        }
        if (!Objects.equals(context.taskUpdatedAt(), command.expectedTaskUpdatedAt())) {
            throw new PlanningApprovalException(
                    PlanningApprovalException.Kind.CONFLICT,
                    "Planning approval task changed after it was loaded. Refresh approval context before retrying."
            );
        }

        PlanningApprovalWorkflowPort.Execution execution = workflowPort.execute(new PlanningApprovalWorkflowPort.ExecuteCommand(
                revision.workflowInstanceId(), revision.id(), transitionId, command.expectedTaskUpdatedAt(),
                command.reasonId(), command.decisionNote(), command.commentText(), command.correlationId(),
                command.actorId(), command.actorUsername(), command.actorDisplayName(), command.effectivePermissions()
        ));
        if (!Objects.equals(action.decision(), execution.decision())) {
            throw new PlanningApprovalException(
                    PlanningApprovalException.Kind.CONFLICT,
                    "Workflow execution decision changed after approval context was loaded."
            );
        }

        PlanRevisionStatus nextStatus = DECISION_APPROVE.equals(execution.decision())
                ? PlanRevisionStatus.APPROVED
                : PlanRevisionStatus.REJECTED;
        Instant executedAt = execution.executedAt();
        PlanRevision updated = revisionRepository.save(new PlanRevision(
                revision.id(), revision.planId(), revision.revisionNumber(), revision.revisionCode(), nextStatus,
                revision.changeReasonCodeId(), revision.changeReasonText(), revision.baseRevisionId(),
                revision.submittedByActorId(), revision.submittedAt(),
                nextStatus == PlanRevisionStatus.APPROVED ? command.actorId() : null,
                nextStatus == PlanRevisionStatus.APPROVED ? executedAt : null,
                revision.workflowInstanceId(), revision.createdAt(), executedAt
        ));
        return new ExecutionView(
                updated.id(), String.valueOf(updated.status()), execution.workflowInstanceId(),
                execution.workflowInstanceStatus(), execution.taskId(), execution.taskStatus(), execution.transitionId(),
                execution.decision(), executedAt
        );
    }

    private PlanRevision revision(String revisionId) {
        if (revisionId == null || revisionId.isBlank()) {
            throw new PlanningApprovalException(PlanningApprovalException.Kind.BOUNDARY, "Plan revision ID must not be blank.");
        }
        return revisionRepository.findById(revisionId.trim())
                .orElseThrow(() -> new PlanningApprovalException(
                        PlanningApprovalException.Kind.NOT_FOUND,
                        "Unknown plan revision: " + revisionId.trim()
                ));
    }

    private ActionView mapAction(PlanningApprovalWorkflowPort.Action action) {
        return new ActionView(
                action.transitionId(), action.decision(), action.reasonRequired(), action.commentRequired(),
                action.requiredPermissionCode(), action.permitted()
        );
    }
}
