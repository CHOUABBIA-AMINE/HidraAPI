/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CompleteTelemetryIngestionBatchRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestRequest
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.request
 *
 * @Description : REST request DTO for completing telemetry ingestion batches.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

/**
 * REST request DTO for completing telemetry ingestion batches.
 *
 * <p>Architecture role:
 * REST inbound request DTO for the telemetry API. It must not contain business behavior,
 * persistence mapping, application orchestration, topology implementation imports, flow calculation,
 * risk scoring, analytics, workflow, reporting, or notification behavior.

 */
public record CompleteTelemetryIngestionBatchRequest(
        @Schema(description = "Received reading count", example = "100")
        @Min(0)
        int receivedCount,
        @Schema(description = "Accepted reading count", example = "95")
        @Min(0)
        int acceptedCount,
        @Schema(description = "Rejected reading count", example = "2")
        @Min(0)
        int rejectedCount,
        @Schema(description = "Duplicate reading count", example = "1")
        @Min(0)
        int duplicateCount,
        @Schema(description = "Quarantined reading count", example = "2")
        @Min(0)
        int quarantinedCount) {
}
