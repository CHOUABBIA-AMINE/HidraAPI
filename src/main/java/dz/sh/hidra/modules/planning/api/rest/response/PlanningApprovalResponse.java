/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningApprovalResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.response
 *
 * @Description : Presents the authoritative workflow approval relation and available actions for one plan revision.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.response;

import java.time.Instant;
import java.util.List;

public record PlanningApprovalResponse(
        String revisionId,
        String revisionStatus,
        String workflowInstanceId,
        String workflowInstanceStatus,
        String currentTaskId,
        Instant currentTaskUpdatedAt,
        List<Action> actions
) {
    public record Action(
            String transitionId,
            String decision,
            boolean reasonRequired,
            boolean commentRequired,
            String requiredPermissionCode,
            boolean permitted
    ) { }
}
