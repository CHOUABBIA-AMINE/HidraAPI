/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.response
 *
 * @Description : REST response DTO for telemetry readings.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * REST response DTO for telemetry readings.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the telemetry API. It must not contain business behavior,
 * persistence mapping, application orchestration, topology implementation imports, flow calculation,
 * risk scoring, analytics, workflow, reporting, or notification behavior.
 */
public record TelemetryReadingResponse(
        @Schema(description = "Telemetry reading identifier", example = "reading-001")
        String id,
        @Schema(description = "Telemetry point identifier", example = "point-001")
        String pointId,
        @Schema(description = "Telemetry reading value")
        TelemetryReadingValueResponse value,
        @Schema(description = "Telemetry quality code catalog reference")
        TelemetryTypeReferenceResponse qualityCode,
        @Schema(description = "Source timestamp from the acquisition system")
        Instant sourceTimestamp,
        @Schema(description = "Server receive timestamp")
        Instant receivedAt,
        @Schema(description = "Technical reading processing state", example = "ACCEPTED")
        String state,
        @Schema(description = "Optional ingestion batch identifier", example = "batch-001")
        String ingestionBatchId,
        @Schema(description = "Optional correlation identifier", example = "corr-20260607-001")
        String correlationId,
        @Schema(description = "Optional rejection reason")
        String rejectionReason) {
}
