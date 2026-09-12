/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTargetApprovalApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Executes workflow transitions for one verified target without exposing workflow internals to callers.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import dz.sh.hidra.modules.workflow.application.command.ExecuteWorkflowTransitionCommand;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTransitionExecutionDto;
import dz.sh.hidra.modules.workflow.application.port.in.ExecuteWorkflowTransitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowTargetApprovalUseCase;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTargetApprovalQueryPort;
import dz.sh.hidra.modules.workflow.domain.exception.WorkflowBoundaryViolationException;
import dz.sh.hidra.modules.workflow.domain.exception.WorkflowTransitionConflictException;
import dz.sh.hidra.modules.workflow.domain.exception.WorkflowTransitionDeniedException;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public final class WorkflowTargetApprovalApplicationService implements WorkflowTargetApprovalUseCase {

    private final WorkflowTargetApprovalQueryPort queryPort;
    private final ExecuteWorkflowTransitionUseCase transitionUseCase;

    public WorkflowTargetApprovalApplicationService(
            WorkflowTargetApprovalQueryPort queryPort,
            ExecuteWorkflowTransitionUseCase transitionUseCase
    ) {
        this.queryPort = Objects.requireNonNull(queryPort, "WorkflowTargetApprovalQueryPort must not be null.");
        this.transitionUseCase = Objects.requireNonNull(transitionUseCase, "ExecuteWorkflowTransitionUseCase must not be null.");
    }

    @Override
    public ApprovalContext context(
            String instanceId,
            String targetModule,
            String targetTypeId,
            String targetId,
            String actorReference,
            java.util.Set<String> effectivePermissions
    ) {
        try {
            WorkflowTargetApprovalQueryPort.Context context = queryPort.load(
                    instanceId, targetModule, targetTypeId, targetId, actorReference, effectivePermissions
            );
            return new ApprovalContext(
                    context.instanceId(), context.instanceStatus(), context.taskId(), context.taskStatus(),
                    context.taskUpdatedAt(), context.actions().stream().map(this::mapAction).toList()
            );
        } catch (WorkflowTargetApprovalQueryPort.QueryException exception) {
            throw map(exception);
        }
    }

    @Override
    public Execution execute(ExecuteCommand command) {
        Objects.requireNonNull(command, "Workflow target approval command must not be null.");
        ApprovalContext context = context(
                command.instanceId(), command.targetModule(), command.targetTypeId(), command.targetId(),
                command.actorId(), command.effectivePermissions()
        );
        Action action = context.actions().stream()
                .filter(candidate -> Objects.equals(candidate.transitionId(), command.transitionId()))
                .findFirst()
                .orElseThrow(() -> new TargetApprovalException(
                        TargetApprovalException.Kind.BOUNDARY,
                        "Workflow transition is not available for the current target task."
                ));
        if (!action.permitted()) {
            throw new TargetApprovalException(
                    TargetApprovalException.Kind.DENIED,
                    "Authenticated actor is not permitted to execute this target workflow transition."
            );
        }
        if (!Objects.equals(context.taskUpdatedAt(), command.expectedTaskUpdatedAt())) {
            throw new TargetApprovalException(
                    TargetApprovalException.Kind.CONFLICT,
                    "Workflow target task changed after it was loaded. Refresh approval context before retrying."
            );
        }
        try {
            WorkflowTransitionExecutionDto result = transitionUseCase.execute(new ExecuteWorkflowTransitionCommand(
                    context.taskId(),
                    command.transitionId(),
                    command.expectedTaskUpdatedAt(),
                    command.reasonId(),
                    command.decisionNote(),
                    command.commentText(),
                    command.correlationId(),
                    command.actorId(),
                    command.actorUsername(),
                    command.actorDisplayName(),
                    command.effectivePermissions()
            ));
            return new Execution(
                    result.actionId(), result.taskId(), result.taskStatus(), result.instanceId(), result.instanceStatus(),
                    result.transitionId(), result.decision(), result.executedAt()
            );
        } catch (WorkflowTransitionConflictException exception) {
            throw new TargetApprovalException(TargetApprovalException.Kind.CONFLICT, exception.getMessage());
        } catch (WorkflowTransitionDeniedException exception) {
            throw new TargetApprovalException(TargetApprovalException.Kind.DENIED, exception.getMessage());
        } catch (WorkflowBoundaryViolationException | IllegalArgumentException exception) {
            throw new TargetApprovalException(TargetApprovalException.Kind.BOUNDARY, exception.getMessage());
        } catch (NoSuchElementException exception) {
            throw new TargetApprovalException(TargetApprovalException.Kind.NOT_FOUND, exception.getMessage());
        }
    }

    private Action mapAction(WorkflowTargetApprovalQueryPort.Action action) {
        return new Action(
                action.transitionId(), action.decision(), action.reasonRequired(), action.commentRequired(),
                action.requiredPermissionCode(), action.permitted()
        );
    }

    private TargetApprovalException map(WorkflowTargetApprovalQueryPort.QueryException exception) {
        return new TargetApprovalException(
                switch (exception.kind()) {
                    case NOT_FOUND -> TargetApprovalException.Kind.NOT_FOUND;
                    case BOUNDARY -> TargetApprovalException.Kind.BOUNDARY;
                    case CONFLICT -> TargetApprovalException.Kind.CONFLICT;
                },
                exception.getMessage()
        );
    }
}
