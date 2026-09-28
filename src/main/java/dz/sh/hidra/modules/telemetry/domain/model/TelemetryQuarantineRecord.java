/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryQuarantineRecord
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Malformed/unmapped/invalid telemetry payload record.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.value.*;
import java.time.Instant;

    /**
     * Malformed/unmapped/invalid telemetry payload record.
     *
         * @param id id
     * @param sourceId sourceId
     * @param endpointId endpointId
     * @param ingestionBatchId ingestionBatchId
     * @param externalTagName externalTagName
     * @param sourceTimestamp sourceTimestamp
     * @param receivedAt receivedAt
     * @param reasonCode reasonCode
     * @param reasonMessage reasonMessage
     * @param rawPayload rawPayload
     * @param rawPayloadHash rawPayloadHash
     * @param status status
     * @param resolvedReadingId resolvedReadingId
     * @param resolvedAt resolvedAt
     * @param resolvedByActorId resolvedByActorId
     */
    public record TelemetryQuarantineRecord(
            String id,
        String sourceId,
        String endpointId,
        String ingestionBatchId,
        String externalTagName,
        Instant sourceTimestamp,
        Instant receivedAt,
        String reasonCode,
        String reasonMessage,
        String rawPayload,
        String rawPayloadHash,
        QuarantineStatus status,
        String resolvedReadingId,
        Instant resolvedAt,
        String resolvedByActorId
    ) {

        public TelemetryQuarantineRecord {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryQuarantineRecord id must not be blank.");
        }
        // HRA-051 required: sourceId
        if (sourceId == null || sourceId.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryQuarantineRecord source id must not be blank.");
        }
        // HRA-051 required: receivedAt
        if (receivedAt == null) {
            throw new InvalidTelemetryValueException("TelemetryQuarantineRecord received at must not be null.");
        }
        // HRA-051 required: reasonCode
        if (reasonCode == null || reasonCode.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryQuarantineRecord reason code must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidTelemetryValueException("TelemetryQuarantineRecord status must not be null.");
        }

        id = normalize(id);
        sourceId = normalize(sourceId);
        endpointId = normalize(endpointId);
        ingestionBatchId = normalize(ingestionBatchId);
        externalTagName = normalize(externalTagName);
        reasonCode = normalize(reasonCode);
        reasonMessage = normalize(reasonMessage);
        rawPayload = normalize(rawPayload);
        rawPayloadHash = normalize(rawPayloadHash);
        resolvedReadingId = normalize(resolvedReadingId);
        resolvedByActorId = normalize(resolvedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
