/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryIngestionBatchResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestResponse
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.response
 *
 * @Description : REST response DTO for telemetry ingestion batches.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

/**
 * REST response DTO for telemetry ingestion batches.
 *
 * <p>Architecture role:
 * REST outbound response DTO for the telemetry API. It must not contain business behavior,
 * persistence mapping, application orchestration, topology implementation imports, flow calculation,
 * risk scoring, analytics, workflow, reporting, or notification behavior.
 */
public record TelemetryIngestionBatchResponse(
        @Schema(description = "Telemetry ingestion batch identifier", example = "batch-001")
        String id,
        @Schema(description = "Telemetry source identifier", example = "source-001")
        String sourceId,
        @Schema(description = "Optional correlation identifier", example = "corr-20260607-001")
        String correlationId,
        @Schema(description = "Technical ingestion batch status", example = "COMPLETED")
        String status,
        @Schema(description = "Received reading count", example = "100")
        Integer receivedCount,
        @Schema(description = "Accepted reading count", example = "95")
        Integer acceptedCount,
        @Schema(description = "Rejected reading count", example = "2")
        Integer rejectedCount,
        @Schema(description = "Duplicate reading count", example = "1")
        Integer duplicateCount,
        @Schema(description = "Quarantined reading count", example = "2")
        Integer quarantinedCount,
        @Schema(description = "Batch start timestamp")
        Instant startedAt,
        @Schema(description = "Batch completion timestamp")
        Instant completedAt,
        @Schema(description = "Optional failure reason")
        String failureReason) {
}
