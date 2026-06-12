/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateWorkflowTaskRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.request
 *
 * @Description : REST request to create workflow task.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.request;

import java.time.Instant;

/**
 * REST request to create workflow task.
 */
public record CreateWorkflowTaskRequest(
        String instanceId,
        String stepId,
        String assignedActorId,
        String assignedActorUsernameSnapshot,
        String assignedActorDisplayNameSnapshot,
        String assignedOrganizationUnitId,
        String assignedOrganizationUnitNameSnapshot,
        String assignedRoleCodeSnapshot,
        String priorityId,
        Instant dueAt,
        String assignmentModeId,
        String taskLabelSnapshot
) {
}
