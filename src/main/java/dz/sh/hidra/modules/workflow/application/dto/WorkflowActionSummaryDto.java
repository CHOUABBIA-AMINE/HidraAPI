/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowActionSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.dto
 *
 * @Description : Workflow action summary DTO.
 *
 */
package dz.sh.hidra.modules.workflow.application.dto;

import dz.sh.hidra.modules.workflow.domain.value.WorkflowActionType;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;

import java.time.Instant;

/**
 * Workflow action summary DTO.
 */
public record WorkflowActionSummaryDto(
        String id,
        String instanceId,
        String taskId,
        WorkflowActionType actionType,
        WorkflowDecision decision,
        String reasonId,
        String actorId,
        String actorDisplayNameSnapshot,
        long actionSequence,
        Instant actedAt
) {
}
