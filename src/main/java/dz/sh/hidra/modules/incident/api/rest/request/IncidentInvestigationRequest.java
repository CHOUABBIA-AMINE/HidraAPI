/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentInvestigationRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : API
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.api.rest.request
 * @Description : Explicit versioned, typed Incident investigation HTTP command payloads.
 */
package dz.sh.hidra.modules.incident.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public final class IncidentInvestigationRequest {
    private IncidentInvestigationRequest() {}
    public record Assign(@NotBlank String requestId,@PositiveOrZero long expectedVersion,@NotBlank String reason) {}
    public record Evidence(@NotBlank String requestId,@PositiveOrZero long expectedVersion,
            @NotBlank String sourceType,@NotBlank String sourceId,@NotBlank String direction,
            @NotBlank String explanation) {}
    public record InvestigationResponseAction(@NotBlank String requestId,@PositiveOrZero long expectedVersion,
            @NotBlank String actionTypeId,@NotBlank String description) {}
    public record Resolve(@NotBlank String requestId,@PositiveOrZero long expectedVersion,
            @NotBlank String resolutionTypeId,@NotBlank String summary,
            boolean correctiveActionRequired,boolean preventiveActionRequired) {}
    public record Review(@NotBlank String requestId,@PositiveOrZero long expectedVersion,
            @NotBlank String verdict,@NotBlank String rationale) {}
    public record Close(@NotBlank String requestId,@PositiveOrZero long expectedVersion,
            @NotBlank String closureSummary,String workflowInstanceId) {}
}
