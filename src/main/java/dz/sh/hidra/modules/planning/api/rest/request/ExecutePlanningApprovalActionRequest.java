/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExecutePlanningApprovalActionRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.request
 *
 * @Description : Carries the authoritative workflow task precondition and optional decision context for a planning approval action.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.request;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record ExecutePlanningApprovalActionRequest(
        @NotNull Instant expectedTaskUpdatedAt,
        String reasonId,
        String decisionNote,
        String commentText,
        String correlationId
) { }
