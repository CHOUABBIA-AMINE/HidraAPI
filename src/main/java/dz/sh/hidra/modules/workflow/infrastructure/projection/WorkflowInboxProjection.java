/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInboxProjection
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.projection
 *
 * @Description : Workflow inbox projection.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.projection;

import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;

import java.time.Instant;

/**
 * Workflow inbox projection.
 */
public record WorkflowInboxProjection(
        String taskId,
        String instanceId,
        String targetModule,
        String targetTypeId,
        String targetId,
        String targetLabelSnapshot,
        WorkflowTaskStatus taskStatus,
        String assignedActorId,
        String assignedOrganizationUnitId,
        Instant dueAt
) {
}
