/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Application service for workflow instances, tasks, and actions.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import org.springframework.stereotype.Service;

import dz.sh.hidra.modules.workflow.application.command.CreateWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.RecordWorkflowActionCommand;
import dz.sh.hidra.modules.workflow.application.command.StartWorkflowInstanceCommand;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowActionSummaryDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowInstanceSummaryDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTaskSummaryDto;
import dz.sh.hidra.modules.workflow.application.mapper.WorkflowApplicationMapper;
import dz.sh.hidra.modules.workflow.application.port.in.CreateWorkflowTaskUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.RecordWorkflowActionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.StartWorkflowInstanceUseCase;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowActionRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowInstanceRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTaskRepositoryPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowAction;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowSlaStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;

import java.time.Instant;
import org.springframework.transaction.annotation.Transactional;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowDefinitionRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowStepRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowConfigurationPort;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;
import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;
import java.util.Objects;

/**
 * Application service for workflow instances, tasks, and actions.
 */
@Service
public class WorkflowApplicationService implements StartWorkflowInstanceUseCase, CreateWorkflowTaskUseCase, RecordWorkflowActionUseCase {

    private final WorkflowDefinitionRepositoryPort definitions;
    private final WorkflowStepRepositoryPort steps;
    private final WorkflowConfigurationPort configuration;
    private final WorkflowExecutionOwnership ownership;
    private final WorkflowInstanceRepositoryPort instanceRepositoryPort;
    private final WorkflowTaskRepositoryPort taskRepositoryPort;
    private final WorkflowActionRepositoryPort actionRepositoryPort;

    public WorkflowApplicationService(
            WorkflowInstanceRepositoryPort instanceRepositoryPort,
            WorkflowTaskRepositoryPort taskRepositoryPort,
            WorkflowActionRepositoryPort actionRepositoryPort,
            WorkflowDefinitionRepositoryPort definitions, WorkflowStepRepositoryPort steps,
            WorkflowConfigurationPort configuration, WorkflowExecutionOwnership ownership
    ) {
        this.definitions=Objects.requireNonNull(definitions);this.steps=Objects.requireNonNull(steps);
        this.configuration=Objects.requireNonNull(configuration);this.ownership=Objects.requireNonNull(ownership);
        this.instanceRepositoryPort = Objects.requireNonNull(instanceRepositoryPort, "Workflow instance repository port must not be null.");
        this.taskRepositoryPort = Objects.requireNonNull(taskRepositoryPort, "Workflow task repository port must not be null.");
        this.actionRepositoryPort = Objects.requireNonNull(actionRepositoryPort, "Workflow action repository port must not be null.");
    }

    @Override
    @Transactional
    public WorkflowInstanceSummaryDto startWorkflowInstance(StartWorkflowInstanceCommand command) {
        Objects.requireNonNull(command, "Start workflow instance command must not be null.");
        Instant now = Instant.now();
        var actor=ownership.requireCurrentActor(command.startedByActorId());
        var definition=definitions.findById(command.definitionId()).orElseThrow(()->new InvalidWorkflowValueException("Unknown Workflow definition."));
        if(definition.status()!=WorkflowDefinitionStatus.ACTIVE || definition.version()!=command.definitionVersion())
            throw new InvalidWorkflowValueException("Workflow start requires ACTIVE matching definition version.");
        configuration.requireActiveCatalog(command.workflowPurposeId(),"WORKFLOW_PURPOSE");
        var type=configuration.requireActiveCatalog(command.targetTypeId(),"WORKFLOW_TARGET_TYPE");
        if(!configuration.activeBinding(command.definitionId(),command.targetModule(),command.targetTypeId(),command.workflowPurposeId()))
            throw new InvalidWorkflowValueException("Exact active Workflow target/purpose binding required.");
        if(command.currentStepId()!=null && !command.currentStepId().isBlank()) {
            var step=steps.findById(command.currentStepId()).orElseThrow(()->new InvalidWorkflowValueException("Unknown Workflow current step."));
            if(!definition.id().equals(step.definitionId())) throw new InvalidWorkflowValueException("Workflow current step belongs to another definition.");
        }
        var target=ownership.requireTarget(command.targetModule(),type.code(),command.targetId());
        WorkflowInstance instance = new WorkflowInstance(
                WorkflowId.newId().value(),
                command.definitionId(),
                command.definitionVersion(),
                command.workflowPurposeId(),
                command.targetModule(),
                command.targetTypeId(),
                command.targetId(),
                target.code(),
                target.label(),
                WorkflowInstanceStatus.STARTED,
                command.currentStepId(),
                command.startedByActorId(),
                actor.username(),
                actor.displayName(),
                null,
                now,
                null,
                null,
                command.correlationId(),
                now,
                now
        );
        return WorkflowApplicationMapper.toSummary(instanceRepositoryPort.save(instance));
    }

    @Override
    @Transactional
    public WorkflowTaskSummaryDto createWorkflowTask(CreateWorkflowTaskCommand command) {
        Objects.requireNonNull(command, "Create workflow task command must not be null.");
        Instant now = Instant.now();
        var creator=ownership.requireCurrentActor();
        var instance=instanceRepositoryPort.findByIdForUpdate(command.instanceId()).orElseThrow(()->new InvalidWorkflowValueException("Unknown Workflow instance."));
        var step=steps.findById(command.stepId()).orElseThrow(()->new InvalidWorkflowValueException("Unknown Workflow step."));
        if(!instance.nonTerminal() || !Objects.equals(instance.currentStepId(),step.id()) || !instance.definitionId().equals(step.definitionId())
                || !instance.startedByActorId().equals(creator.id()))
            throw new InvalidWorkflowValueException("Generic task creation is limited to the starter and coherent current instance step.");
        var assigned=command.assignedActorId()==null?null:ownership.requireActor(command.assignedActorId());
        var unit=command.assignedOrganizationUnitId()==null?null:ownership.requireUnit(command.assignedOrganizationUnitId());
        WorkflowTask task = new WorkflowTask(
                WorkflowId.newId().value(),
                command.instanceId(),
                command.stepId(),
                WorkflowTaskStatus.OPEN,
                command.assignedActorId(),
                assigned==null?null:assigned.username(),
                assigned==null?null:assigned.displayName(),
                command.assignedOrganizationUnitId(),
                unit==null?null:unit.name(),
                command.assignedRoleCodeSnapshot(),
                command.priorityId(),
                command.dueAt(),
                null,
                null,
                null,
                null,
                command.assignmentModeId(),
                command.taskLabelSnapshot(),
                WorkflowSlaStatus.NORMAL,
                null,
                null,
                null,
                now,
                now
        );
        ownership.validateAssignment(task,configuration);
        if(assigned==null && !step.allowClaim()) throw new InvalidWorkflowValueException("Organization pool tasks require an explicitly claimable step.");
        return WorkflowApplicationMapper.toSummary(taskRepositoryPort.save(task));
    }

    @Override
    @Transactional
    public WorkflowActionSummaryDto recordWorkflowAction(RecordWorkflowActionCommand command) {
        Objects.requireNonNull(command, "Record workflow action command must not be null.");
        Instant now = Instant.now();
        if(command.actionType()!=dz.sh.hidra.modules.workflow.domain.value.WorkflowActionType.COMMENT || command.decision()!=null)
            throw new InvalidWorkflowValueException("Generic Workflow recording permits COMMENT only; decisions require configured transitions.");
        if(command.commentText()==null || command.commentText().isBlank()) throw new InvalidWorkflowValueException("Workflow comment text required.");
        var actor=ownership.requireCurrentActor(command.actorId());
        // Use the same task-before-instance lock order as configured transition execution.
        WorkflowTask task=command.taskId()==null?null:taskRepositoryPort.findByIdForUpdate(command.taskId()).orElseThrow(()->new InvalidWorkflowValueException("Unknown Workflow action task."));
        var instance=instanceRepositoryPort.findByIdForUpdate(command.instanceId()).orElseThrow(()->new InvalidWorkflowValueException("Unknown Workflow action instance."));
        if(task!=null){
            if(!task.instanceId().equals(instance.id())) throw new InvalidWorkflowValueException("Workflow action task belongs to another instance.");
            var step=steps.findById(task.stepId()).orElseThrow(()->new InvalidWorkflowValueException("Unknown Workflow action step."));
            if(!instance.definitionId().equals(step.definitionId()) || !ownership.canExecute(task,actor.id(),step.allowClaim()))
                throw new InvalidWorkflowValueException("Workflow actor is not eligible for this task-scoped comment.");
        } else if(!instance.startedByActorId().equals(actor.id())) throw new InvalidWorkflowValueException("Instance-scoped comments require the instance starter.");
        if(command.reasonId()!=null) configuration.requireActiveCatalog(command.reasonId(),"WORKFLOW_REASON");
        var unit=command.organizationUnitId()==null?null:ownership.requireUnit(command.organizationUnitId());
        if(unit!=null && !ownership.member(actor,unit.id())) throw new InvalidWorkflowValueException("Workflow comment organization context requires eligible membership.");
        long sequence=actionRepositoryPort.nextSequence(instance.id());
        WorkflowAction action = new WorkflowAction(
                WorkflowId.newId().value(),
                command.instanceId(),
                command.taskId(),
                command.actionType(),
                command.decision(),
                command.reasonId(),
                command.decisionNote(),
                command.commentText(),
                command.actorId(),
                actor.username(),
                actor.displayName(),
                null,
                command.organizationUnitId(),
                unit==null?null:unit.name(),
                null,
                command.correlationId(),
                sequence,
                "HIDRA_API",
                null,
                null,
                now
        );
        return WorkflowApplicationMapper.toSummary(actionRepositoryPort.save(action));
    }
}
