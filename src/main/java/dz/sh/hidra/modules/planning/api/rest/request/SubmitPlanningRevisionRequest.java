/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SubmitPlanningRevisionRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.request
 *
 * @Description : Requires explicit Planning and Workflow preconditions for one submission.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import java.time.Instant;

public record SubmitPlanningRevisionRequest(
        @NotNull Instant expectedPlanUpdatedAt,
        @NotNull Instant expectedRevisionUpdatedAt,
        @NotBlank String definitionId,
        @Min(1) int definitionVersion,
        @NotBlank String targetTypeId,
        @NotBlank String workflowPurposeId,
        @NotBlank String reviewerActorId,
        String correlationId) { }
