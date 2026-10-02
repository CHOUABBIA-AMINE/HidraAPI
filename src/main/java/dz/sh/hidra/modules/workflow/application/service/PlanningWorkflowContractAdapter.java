/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningWorkflowContractAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Adapts Workflow internal use cases to the narrow contract exported to Planning.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import dz.sh.hidra.modules.workflow.application.contract.planning.PlanningWorkflowContract;
import dz.sh.hidra.modules.workflow.application.port.in.ExecuteWorkflowTargetTransitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class PlanningWorkflowContractAdapter implements PlanningWorkflowContract {

    private final WorkflowQueryUseCase workflowQuery;
    private final ExecuteWorkflowTargetTransitionUseCase workflowTransition;

    public PlanningWorkflowContractAdapter(
            WorkflowQueryUseCase workflowQuery,
            ExecuteWorkflowTargetTransitionUseCase workflowTransition
    ) {
        this.workflowQuery = Objects.requireNonNull(workflowQuery, "WorkflowQueryUseCase must not be null.");
        this.workflowTransition = Objects.requireNonNull(
                workflowTransition,
                "ExecuteWorkflowTargetTransitionUseCase must not be null."
        );
    }

    @Override
    public InstanceView instance(String id) {
        WorkflowQueryUseCase.InstanceView source = workflowQuery.instance(id);
        return new InstanceView(
                source.id(),
                source.targetModule(),
                source.targetId(),
                source.status()
        );
    }

    @Override
    public TaskView currentTask(String instanceId) {
        WorkflowQueryUseCase.TaskView source = workflowQuery.currentTask(instanceId);
        return new TaskView(source.id(), source.updatedAt());
    }

    @Override
    public List<ActionView> availableActions(
            String taskId,
            String actorReference,
            Set<String> effectivePermissions
    ) {
        return workflowQuery.availableActions(taskId, actorReference, effectivePermissions).stream()
                .map(source -> new ActionView(
                        source.transitionId(),
                        source.decision(),
                        source.reasonRequired(),
                        source.commentRequired(),
                        source.requiredPermissionCode(),
                        source.permitted()
                ))
                .toList();
    }

    @Override
    public TransitionResult execute(TransitionCommand command) {
        Objects.requireNonNull(command, "Planning workflow transition command must not be null.");
        ExecuteWorkflowTargetTransitionUseCase.Result source = workflowTransition.execute(
                new ExecuteWorkflowTargetTransitionUseCase.Command(
                        command.taskId(),
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
                )
        );
        return new TransitionResult(
                source.instanceId(),
                source.instanceStatus(),
                source.transitionId(),
                source.decision(),
                source.nextTaskId(),
                source.executedAt()
        );
    }
}
