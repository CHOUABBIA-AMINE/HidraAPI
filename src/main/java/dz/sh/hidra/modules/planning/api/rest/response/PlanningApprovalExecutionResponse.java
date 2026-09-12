/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningApprovalExecutionResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.response
 *
 * @Description : Reports the workflow decision and resulting planning revision lifecycle state.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.response;

import java.time.Instant;

public record PlanningApprovalExecutionResponse(
        String revisionId,
        String revisionStatus,
        String workflowInstanceId,
        String workflowInstanceStatus,
        String transitionId,
        String decision,
        String nextTaskId,
        Instant executedAt
) { }
