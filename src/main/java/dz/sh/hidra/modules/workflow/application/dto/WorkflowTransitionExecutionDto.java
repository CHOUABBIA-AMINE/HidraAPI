/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTransitionExecutionDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.dto
 *
 * @Description : Result of authoritative workflow transition execution.
 *
 */
package dz.sh.hidra.modules.workflow.application.dto;

import java.time.Instant;

public record WorkflowTransitionExecutionDto(
        String actionId,
        String taskId,
        String taskStatus,
        String instanceId,
        String instanceStatus,
        String transitionId,
        String decision,
        String currentStepId,
        String nextTaskId,
        Instant executedAt
) { }
