/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IngestTelemetryReadingResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.response
 *
 * @Description : REST projection of the canonical immutable raw reading receipt.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.response;


import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.Instant;

@Schema(description="Canonical raw reading receipt; replayed is true only after fresh native authorization.")
public record IngestTelemetryReadingResponse(
        String id, String pointId, String sourceId,
        BigDecimal numericValue, String textValue, Boolean booleanValue,
        String unitId, String qualityCodeId, Instant sourceTimestamp,
        Instant receivedAt, String state, boolean replayed) { }
