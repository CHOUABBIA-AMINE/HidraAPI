/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInstanceResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response for workflow instance.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import dz.sh.hidra.modules.workflow.domain.value.WorkflowInstanceStatus;

import java.time.Instant;

/**
 * REST response for workflow instance.
 */
public record WorkflowInstanceResponse(
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
