/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyAssetReferenceResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.response
 *
 * @Description : REST response DTO for neutral topology asset references.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response DTO for neutral topology asset references.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the telemetry API. It must not contain business behavior,
 * persistence mapping, application orchestration, topology implementation imports, flow calculation,
 * risk scoring, analytics, workflow, reporting, or notification behavior.
 */
public record TopologyAssetReferenceResponse(
        @Schema(description = "Topology asset type code", example = "PIPELINE")
        String assetTypeCode,
        @Schema(description = "Topology asset identifier", example = "pipeline-001")
        String assetId,
        @Schema(description = "Topology asset business code", example = "GPL-001")
        String assetCode,
        @Schema(description = "Topology asset name snapshot", example = "Gazoduc GPL 001")
        String assetNameSnapshot) {
}
