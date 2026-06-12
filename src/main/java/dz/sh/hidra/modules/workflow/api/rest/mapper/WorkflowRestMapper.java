/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowRestMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.mapper
 *
 * @Description : Maps workflow REST models to application models.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.mapper;

import dz.sh.hidra.modules.workflow.api.rest.request.CreateWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.StartWorkflowInstanceRequest;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowInstanceResponse;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowTaskResponse;
import dz.sh.hidra.modules.workflow.application.command.CreateWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.application.command.StartWorkflowInstanceCommand;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowInstanceSummaryDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTaskSummaryDto;

/**
 * Maps workflow REST models to application models.
 */
public final class WorkflowRestMapper {

    private WorkflowRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static StartWorkflowInstanceCommand toCommand(StartWorkflowInstanceRequest request) {
        return new StartWorkflowInstanceCommand(request.definitionId(), request.definitionVersion(), request.workflowPurposeId(), request.targetModule(), request.targetTypeId(), request.targetId(), request.targetCodeSnapshot(), request.targetLabelSnapshot(), request.currentStepId(), request.startedByActorId(), request.startedByUsernameSnapshot(), request.startedByDisplayNameSnapshot(), request.startedByRoleCodeSnapshot(), request.correlationId());
    }

    public static CreateWorkflowTaskCommand toCommand(CreateWorkflowTaskRequest request) {
        return new CreateWorkflowTaskCommand(request.instanceId(), request.stepId(), request.assignedActorId(), request.assignedActorUsernameSnapshot(), request.assignedActorDisplayNameSnapshot(), request.assignedOrganizationUnitId(), request.assignedOrganizationUnitNameSnapshot(), request.assignedRoleCodeSnapshot(), request.priorityId(), request.dueAt(), request.assignmentModeId(), request.taskLabelSnapshot());
    }

    public static WorkflowInstanceResponse toResponse(WorkflowInstanceSummaryDto dto) {
        return new WorkflowInstanceResponse(dto.id(), dto.definitionId(), dto.definitionVersion(), dto.targetModule(), dto.targetTypeId(), dto.targetId(), dto.status(), dto.currentStepId(), dto.startedAt(), dto.completedAt());
    }

    public static WorkflowTaskResponse toResponse(WorkflowTaskSummaryDto dto) {
        return new WorkflowTaskResponse(dto.id(), dto.instanceId(), dto.stepId(), dto.status(), dto.assignedActorId(), dto.assignedOrganizationUnitId(), dto.priorityId(), dto.dueAt());
    }
}
