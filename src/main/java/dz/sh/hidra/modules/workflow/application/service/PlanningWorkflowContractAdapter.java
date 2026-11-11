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
import dz.sh.hidra.modules.workflow.application.port.in.CreateWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.StartWorkflowInstanceUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowDefinitionDiscoveryUseCase;
import dz.sh.hidra.modules.workflow.application.command.CreateWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.StartWorkflowInstanceCommand;
import dz.sh.hidra.modules.workflow.application.command.ExecuteWorkflowTransitionCommand;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class PlanningWorkflowContractAdapter implements PlanningWorkflowContract {

    private final WorkflowQueryUseCase workflowQuery;
    private final WorkflowTransitionApplicationService workflowTransition;
    private final WorkflowDefinitionDiscoveryUseCase discovery;
    private final StartWorkflowInstanceUseCase starts;
    private final CreateWorkflowTaskUseCase tasks;

    public PlanningWorkflowContractAdapter(
            WorkflowQueryUseCase workflowQuery,
            WorkflowTransitionApplicationService workflowTransition,
            WorkflowDefinitionDiscoveryUseCase discovery,
            StartWorkflowInstanceUseCase starts,
            CreateWorkflowTaskUseCase tasks
    ) {
        this.workflowQuery = Objects.requireNonNull(workflowQuery, "WorkflowQueryUseCase must not be null.");
        this.workflowTransition = Objects.requireNonNull(workflowTransition);
        this.discovery = Objects.requireNonNull(discovery);
        this.starts = Objects.requireNonNull(starts);
        this.tasks = Objects.requireNonNull(tasks);
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
        var source = workflowTransition.executeForPlanning(
                new ExecuteWorkflowTransitionCommand(
                        command.taskId(), command.transitionId(), command.expectedTaskUpdatedAt(),
                        command.reasonId(), command.decisionNote(), command.commentText(), command.correlationId(),
                        command.actorId(), command.actorUsername(), command.actorDisplayName(),
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
    @Override
    public RevisionStartResult startRevisionApproval(RevisionStartCommand command) {
        Objects.requireNonNull(command, "Planning revision workflow start is required.");
        var matching = discovery.discover("planning", command.targetTypeId(), command.workflowPurposeId(),
                command.revisionId(), "").stream()
                .filter(d -> d.id().equals(command.definitionId())
                        && d.version() == command.definitionVersion()
                        && d.assignmentChoices().stream().anyMatch(a -> a.actorId().equals(command.reviewerActorId())))
                .toList();
        if (matching.size() != 1) {
            throw new IllegalArgumentException("No active, owner-authorized independent Planning workflow selection.");
        }
        var definition = matching.getFirst();
        var instance = starts.startWorkflowInstance(new StartWorkflowInstanceCommand(
                definition.id(), definition.version(), command.workflowPurposeId(), "planning",
                command.targetTypeId(), command.revisionId(), null, null,
                definition.firstStepId(), command.starterActorId(), null, null, null,
                command.correlationId()));
        var task = tasks.createWorkflowTask(new CreateWorkflowTaskCommand(
                instance.id(), definition.firstStepId(), command.reviewerActorId(),
                null, null, null, null, null, null, null, null, "Planning revision approval"));
        return new RevisionStartResult(instance.id(), task.id(), workflowQuery.currentTask(instance.id()).updatedAt().truncatedTo(java.time.temporal.ChronoUnit.MICROS));
    }
}

