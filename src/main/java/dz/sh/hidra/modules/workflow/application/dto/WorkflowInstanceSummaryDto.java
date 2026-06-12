/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInstanceSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.dto
 *
 * @Description : Workflow instance summary DTO.
 *
 */
package dz.sh.hidra.modules.workflow.application.dto;

import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;

import java.time.Instant;

/**
 * Workflow instance summary DTO.
 */
public record WorkflowInstanceSummaryDto(
        String id,
        String definitionId,
        int definitionVersion,
        String targetModule,
        String targetTypeId,
        String targetId,
        WorkflowInstanceStatus status,
        String currentStepId,
        Instant startedAt,
        Instant completedAt
) {
}
