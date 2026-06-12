/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowApplicationService
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
import java.util.Objects;

/**
 * Application service for workflow instances, tasks, and actions.
 */
public class WorkflowApplicationService implements StartWorkflowInstanceUseCase, CreateWorkflowTaskUseCase, RecordWorkflowActionUseCase {

    private final WorkflowInstanceRepositoryPort instanceRepositoryPort;
    private final WorkflowTaskRepositoryPort taskRepositoryPort;
    private final WorkflowActionRepositoryPort actionRepositoryPort;

    public WorkflowApplicationService(
            WorkflowInstanceRepositoryPort instanceRepositoryPort,
            WorkflowTaskRepositoryPort taskRepositoryPort,
            WorkflowActionRepositoryPort actionRepositoryPort
    ) {
        this.instanceRepositoryPort = Objects.requireNonNull(instanceRepositoryPort, "Workflow instance repository port must not be null.");
        this.taskRepositoryPort = Objects.requireNonNull(taskRepositoryPort, "Workflow task repository port must not be null.");
        this.actionRepositoryPort = Objects.requireNonNull(actionRepositoryPort, "Workflow action repository port must not be null.");
    }

    @Override
    public WorkflowInstanceSummaryDto startWorkflowInstance(StartWorkflowInstanceCommand command) {
        Objects.requireNonNull(command, "Start workflow instance command must not be null.");
        Instant now = Instant.now();
        WorkflowInstance instance = new WorkflowInstance(
                WorkflowId.newId().value(),
                command.definitionId(),
                command.definitionVersion(),
                command.workflowPurposeId(),
                command.targetModule(),
                command.targetTypeId(),
                command.targetId(),
                command.targetCodeSnapshot(),
                command.targetLabelSnapshot(),
                WorkflowInstanceStatus.STARTED,
                command.currentStepId(),
                command.startedByActorId(),
                command.startedByUsernameSnapshot(),
                command.startedByDisplayNameSnapshot(),
                command.startedByRoleCodeSnapshot(),
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
