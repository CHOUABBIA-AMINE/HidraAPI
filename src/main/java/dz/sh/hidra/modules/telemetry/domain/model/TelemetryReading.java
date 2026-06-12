/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReading
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Raw received telemetry reading.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import dz.sh.hidra.modules.telemetry.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Raw received telemetry reading.
     *
         * @param id id
     * @param pointId pointId
     * @param numericValue numericValue
     * @param textValue textValue
     * @param booleanValue booleanValue
     * @param qualityCodeId qualityCodeId
     * @param sourceTimestamp sourceTimestamp
     * @param receivedAt receivedAt
     * @param state state
     * @param ingestionBatchId ingestionBatchId
     * @param correlationId correlationId
     * @param rejectionReason rejectionReason
     * @param sourceSequenceNumber sourceSequenceNumber
     * @param externalTagMappingId externalTagMappingId
     * @param rawPayloadHash rawPayloadHash
     * @param createdAt createdAt
     */
    public record TelemetryReading(
            String id,
        String pointId,
        BigDecimal numericValue,
        String textValue,
        Boolean booleanValue,
        String qualityCodeId,
        Instant sourceTimestamp,
        Instant receivedAt,
        ReadingState state,
        String ingestionBatchId,
        String correlationId,
        String rejectionReason,
        String sourceSequenceNumber,
        String externalTagMappingId,
        String rawPayloadHash,
        Instant createdAt
    ) {

        public TelemetryReading {
        id = normalize(id);
        pointId = normalize(pointId);
        textValue = normalize(textValue);
        qualityCodeId = normalize(qualityCodeId);
        ingestionBatchId = normalize(ingestionBatchId);
        correlationId = normalize(correlationId);
        rejectionReason = normalize(rejectionReason);
        sourceSequenceNumber = normalize(sourceSequenceNumber);
        externalTagMappingId = normalize(externalTagMappingId);
        rawPayloadHash = normalize(rawPayloadHash);
        }
        public boolean hasExactlyOneValue() {
            int count = 0;
            if (numericValue != null) {
                count++;
            }
            if (textValue != null && !textValue.isBlank()) {
                count++;
            }
            if (booleanValue != null) {
                count++;
            }
            return count == 1;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
