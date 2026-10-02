/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowApplicationMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.mapper
 *
 * @Description : Maps workflow domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.workflow.application.mapper;

import dz.sh.hidra.modules.workflow.application.dto.WorkflowActionSummaryDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowInstanceSummaryDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTaskSummaryDto;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowAction;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;

/**
 * Maps workflow domain models to DTOs.
 */
public final class WorkflowApplicationMapper {

    private WorkflowApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static WorkflowInstanceSummaryDto toSummary(WorkflowInstance instance) {
        return new WorkflowInstanceSummaryDto(instance.id(), instance.definitionId(), instance.definitionVersion(), instance.targetModule(), instance.targetTypeId(), instance.targetId(), instance.status(), instance.currentStepId(), instance.startedAt(), instance.completedAt());
    }

    public static WorkflowTaskSummaryDto toSummary(WorkflowTask task) {
        return new WorkflowTaskSummaryDto(task.id(), task.instanceId(), task.stepId(), task.status(), task.assignedActorId(), task.assignedOrganizationUnitId(), task.priorityId(), task.dueAt());
    }

    public static WorkflowActionSummaryDto toSummary(WorkflowAction action) {
        return new WorkflowActionSummaryDto(action.id(), action.instanceId(), action.taskId(), action.actionType(), action.decision(), action.reasonId(), action.actorId(), action.actorDisplayNameSnapshot(), action.actionSequence(), action.actedAt());
    }
}
