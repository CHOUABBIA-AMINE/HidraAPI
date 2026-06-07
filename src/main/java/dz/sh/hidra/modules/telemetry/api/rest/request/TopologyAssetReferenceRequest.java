/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyAssetReferenceRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestRequest
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.request
 *
 * @Description : REST request DTO for neutral topology asset references.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * REST request DTO for neutral topology asset references.
 *
 * <p>Architecture role:
 * REST inbound request DTO for the telemetry API. It must not contain business behavior,
 * persistence mapping, application orchestration, topology implementation imports, flow calculation,
 * risk scoring, analytics, workflow, reporting, or notification behavior.

 * <p>Boundary policy:
 * Telemetry carries neutral topology references only and must not expose topology domain or persistence objects.
 */
public record TopologyAssetReferenceRequest(
        @Schema(description = "Topology asset type code", example = "PIPELINE")
        @NotBlank
        @Size(max = 120)
        String assetTypeCode,
        @Schema(description = "Topology asset identifier", example = "pipeline-001")
        @NotBlank
        @Size(max = 120)
        String assetId,
        @Schema(description = "Topology asset business code", example = "GPL-001")
        @NotBlank
        @Size(max = 120)
        String assetCode,
        @Schema(description = "Topology asset name snapshot", example = "Gazoduc GPL 001")
        @Size(max = 160)
        String assetNameSnapshot) {
}
