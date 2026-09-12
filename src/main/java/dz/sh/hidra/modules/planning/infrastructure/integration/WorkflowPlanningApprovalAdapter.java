/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowPlanningApprovalAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.integration
 *
 * @Description : Adapts the workflow target-approval public contract into planning-neutral approval records.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.integration;

import dz.sh.hidra.modules.planning.application.PlanningApprovalException;
import dz.sh.hidra.modules.planning.application.port.out.PlanningApprovalWorkflowPort;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowTargetApprovalUseCase;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public final class WorkflowPlanningApprovalAdapter implements PlanningApprovalWorkflowPort {

    private static final String TARGET_MODULE = "planning";
    private static final String TARGET_TYPE = "PLAN_REVISION";

    private final WorkflowTargetApprovalUseCase workflowUseCase;

    public WorkflowPlanningApprovalAdapter(WorkflowTargetApprovalUseCase workflowUseCase) {
        this.workflowUseCase = Objects.requireNonNull(workflowUseCase, "WorkflowTargetApprovalUseCase must not be null.");
    }

    @Override
    public ApprovalContext context(
            String workflowInstanceId,
            String revisionId,
            String actorId,
            java.util.Set<String> effectivePermissions
    ) {
        try {
            WorkflowTargetApprovalUseCase.ApprovalContext context = workflowUseCase.context(
                    workflowInstanceId, TARGET_MODULE, TARGET_TYPE, revisionId, actorId, effectivePermissions
            );
            return new ApprovalContext(
                    context.instanceId(), context.instanceStatus(), context.taskId(), context.taskStatus(),
                    context.taskUpdatedAt(), context.actions().stream().map(this::mapAction).toList()
            );
        } catch (WorkflowTargetApprovalUseCase.TargetApprovalException exception) {
            throw map(exception);
        }
    }

    @Override
    public Execution execute(ExecuteCommand command) {
        Objects.requireNonNull(command, "Planning approval workflow command must not be null.");
        try {
            WorkflowTargetApprovalUseCase.Execution result = workflowUseCase.execute(
                    new WorkflowTargetApprovalUseCase.ExecuteCommand(
                            command.workflowInstanceId(), TARGET_MODULE, TARGET_TYPE, command.revisionId(),
                            command.transitionId(), command.expectedTaskUpdatedAt(), command.reasonId(),
                            command.decisionNote(), command.commentText(), command.correlationId(), command.actorId(),
                            command.actorUsername(), command.actorDisplayName(), command.effectivePermissions()
                    )
            );
            return new Execution(
                    result.actionId(), result.taskId(), result.taskStatus(), result.instanceId(), result.instanceStatus(),
                    result.transitionId(), result.decision(), result.executedAt()
            );
        } catch (WorkflowTargetApprovalUseCase.TargetApprovalException exception) {
            throw map(exception);
        }
    }

    private Action mapAction(WorkflowTargetApprovalUseCase.Action action) {
        return new Action(
                action.transitionId(), action.decision(), action.reasonRequired(), action.commentRequired(),
                action.requiredPermissionCode(), action.permitted()
        );
    }

    private PlanningApprovalException map(WorkflowTargetApprovalUseCase.TargetApprovalException exception) {
        return new PlanningApprovalException(
                switch (exception.kind()) {
                    case NOT_FOUND -> PlanningApprovalException.Kind.NOT_FOUND;
                    case BOUNDARY -> PlanningApprovalException.Kind.BOUNDARY;
                    case DENIED -> PlanningApprovalException.Kind.DENIED;
                    case CONFLICT -> PlanningApprovalException.Kind.CONFLICT;
                },
                exception.getMessage()
        );
    }
}
