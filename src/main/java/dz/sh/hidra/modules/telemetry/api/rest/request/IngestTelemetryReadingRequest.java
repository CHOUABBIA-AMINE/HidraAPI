/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IngestTelemetryReadingRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.request
 *
 * @Description : Explicit untrusted REST input without any accepted authority or trust metadata.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.request;


import com.fasterxml.jackson.annotation.JsonAnySetter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Schema(description="Synthetic untrusted raw observation. Actor, company, receive time, trust and approval are server controlled.")
public record IngestTelemetryReadingRequest(
        @NotBlank String sourceId,
        @NotBlank String sourceSequenceNumber,
        BigDecimal numericValue,
        String textValue,
        Boolean booleanValue,
        String unitId,
        @NotBlank String qualityCodeId,
        @NotNull Instant sourceTimestamp) {
    /** Reject caller-forged state, trustedAt, receivedAt, company or approval fields rather than ignoring them. */
    @JsonAnySetter
    public void rejectUnknown(String name, Object value) {
        throw new IllegalArgumentException("Unknown telemetry ingestion field: " + name);
    }
}
