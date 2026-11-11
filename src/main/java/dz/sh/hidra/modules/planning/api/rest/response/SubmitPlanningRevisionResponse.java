/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SubmitPlanningRevisionResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.response
 *
 * @Description : Returns persisted Planning and Workflow submission identities and versions.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.response;

import java.time.Instant;

public record SubmitPlanningRevisionResponse(String revisionId, String revisionStatus,
        Instant revisionUpdatedAt, Instant planUpdatedAt, String workflowInstanceId,
        String workflowTaskId, Instant workflowTaskUpdatedAt) { }
