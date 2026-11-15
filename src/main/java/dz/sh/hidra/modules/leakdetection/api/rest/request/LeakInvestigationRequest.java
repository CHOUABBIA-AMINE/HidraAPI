/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakInvestigationRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : API
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.api.rest.request
 * @Description : Explicit versioned Leak case investigation HTTP payloads.
 */
package dz.sh.hidra.modules.leakdetection.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public final class LeakInvestigationRequest {
    private LeakInvestigationRequest() {}
    public record Open(@NotBlank String candidateId,@NotBlank String requestId) {}
    public record Assign(@NotBlank String requestId,@PositiveOrZero long expectedVersion,@NotBlank String reason) {}
    public record Evidence(@NotBlank String requestId,@PositiveOrZero long expectedVersion,
            @NotBlank String evaluationId,@NotBlank String direction,@NotBlank String explanation) {}
    public record Action(@NotBlank String requestId,@PositiveOrZero long expectedVersion,
            @NotBlank String actionType,@NotBlank String result) {}
    public record Disposition(@NotBlank String requestId,@PositiveOrZero long expectedVersion,
            @NotBlank String disposition,@NotBlank String explanation) {}
    public record Review(@NotBlank String requestId,@PositiveOrZero long expectedVersion,
            @NotBlank String verdict,@NotBlank String rationale) {}
}
