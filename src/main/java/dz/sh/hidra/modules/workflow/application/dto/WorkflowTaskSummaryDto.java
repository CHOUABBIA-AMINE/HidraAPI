/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTaskSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.dto
 *
 * @Description : Workflow task summary DTO.
 *
 */
package dz.sh.hidra.modules.workflow.application.dto;

import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;

import java.time.Instant;

/**
 * Workflow task summary DTO.
 */
public record WorkflowTaskSummaryDto(
        String id,
        String instanceId,
        String stepId,
        WorkflowTaskStatus status,
        String assignedActorId,
        String assignedOrganizationUnitId,
        String priorityId,
        Instant dueAt
) {
}
