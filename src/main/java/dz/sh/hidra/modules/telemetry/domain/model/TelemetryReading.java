/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReading
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
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

import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.service.TelemetryReadingValueValidator;
import dz.sh.hidra.modules.telemetry.domain.value.ReadingState;
import java.math.BigDecimal;
import java.time.Instant;

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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryReading id must not be blank.");
        }
        // HRA-051 required: pointId
        if (pointId == null || pointId.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryReading point id must not be blank.");
        }
        // HRA-051 required: qualityCodeId
        if (qualityCodeId == null || qualityCodeId.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryReading quality code id must not be blank.");
        }
        // HRA-051 required: sourceTimestamp
        if (sourceTimestamp == null) {
            throw new InvalidTelemetryValueException("TelemetryReading source timestamp must not be null.");
        }
        // HRA-051 required: receivedAt
        if (receivedAt == null) {
            throw new InvalidTelemetryValueException("TelemetryReading received at must not be null.");
        }
        // HRA-051 required: state
        if (state == null) {
            throw new InvalidTelemetryValueException("TelemetryReading state must not be null.");
        }

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

        TelemetryReadingValueValidator.validateValueShape(
                state,
                numericValue,
                textValue,
                booleanValue
        );
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
