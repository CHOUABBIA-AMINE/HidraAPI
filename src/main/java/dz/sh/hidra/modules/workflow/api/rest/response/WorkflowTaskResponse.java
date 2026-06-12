/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTaskResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.response
 *
 * @Description : REST response for workflow task.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.response;

import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;

import java.time.Instant;

/**
 * REST response for workflow task.
 */
public record WorkflowTaskResponse(
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
