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
    public WorkflowTaskSummaryDto createWorkflowTask(CreateWorkflowTaskCommand command) {
        Objects.requireNonNull(command, "Create workflow task command must not be null.");
        Instant now = Instant.now();
        WorkflowTask task = new WorkflowTask(
                WorkflowId.newId().value(),
                command.instanceId(),
                command.stepId(),
                WorkflowTaskStatus.OPEN,
                command.assignedActorId(),
                command.assignedActorUsernameSnapshot(),
                command.assignedActorDisplayNameSnapshot(),
                command.assignedOrganizationUnitId(),
                command.assignedOrganizationUnitNameSnapshot(),
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
        return WorkflowApplicationMapper.toSummary(taskRepositoryPort.save(task));
    }

    @Override
    public WorkflowActionSummaryDto recordWorkflowAction(RecordWorkflowActionCommand command) {
        Objects.requireNonNull(command, "Record workflow action command must not be null.");
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
                command.actorUsernameSnapshot(),
                command.actorDisplayNameSnapshot(),
                command.actorRoleCodeSnapshot(),
                command.organizationUnitId(),
                command.organizationUnitNameSnapshot(),
                command.organizationRoleCodeSnapshot(),
                command.correlationId(),
                command.actionSequence(),
                command.sourceSystem(),
                null,
                null,
                command.actedAt() == null ? Instant.now() : command.actedAt()
        );
        return WorkflowApplicationMapper.toSummary(actionRepositoryPort.save(action));
    }
}
