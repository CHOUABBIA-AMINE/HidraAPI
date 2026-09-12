/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTargetTransitionApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Adapts the workflow transition command model to the public cross-module execution contract.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import dz.sh.hidra.modules.workflow.application.command.ExecuteWorkflowTransitionCommand;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTransitionExecutionDto;
import dz.sh.hidra.modules.workflow.application.port.in.ExecuteWorkflowTargetTransitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ExecuteWorkflowTransitionUseCase;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class WorkflowTargetTransitionApplicationService implements ExecuteWorkflowTargetTransitionUseCase {

    private final ExecuteWorkflowTransitionUseCase delegate;

    public WorkflowTargetTransitionApplicationService(ExecuteWorkflowTransitionUseCase delegate) {
        this.delegate = Objects.requireNonNull(delegate, "ExecuteWorkflowTransitionUseCase must not be null.");
    }

    @Override
    public Result execute(Command command) {
        Objects.requireNonNull(command, "Workflow target transition command must not be null.");
        WorkflowTransitionExecutionDto dto = delegate.execute(new ExecuteWorkflowTransitionCommand(
                command.taskId(), command.transitionId(), command.expectedTaskUpdatedAt(), command.reasonId(),
                command.decisionNote(), command.commentText(), command.correlationId(), command.actorId(),
                command.actorUsername(), command.actorDisplayName(), command.effectivePermissions()
        ));
        return new Result(
                dto.actionId(), dto.taskId(), dto.taskStatus(), dto.instanceId(), dto.instanceStatus(),
                dto.transitionId(), dto.decision(), dto.currentStepId(), dto.nextTaskId(), dto.executedAt()
        );
    }
}
