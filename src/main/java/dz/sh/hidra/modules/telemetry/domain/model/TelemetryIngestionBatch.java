/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryIngestionBatch
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Grouped ingestion run/import/API batch.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.value.*;
import java.time.Instant;

    /**
     * Grouped ingestion run/import/API batch.
     *
         * @param id id
     * @param sourceId sourceId
     * @param endpointId endpointId
     * @param correlationId correlationId
     * @param status status
     * @param receivedCount receivedCount
     * @param acceptedCount acceptedCount
     * @param rejectedCount rejectedCount
     * @param duplicateCount duplicateCount
     * @param quarantinedCount quarantinedCount
     * @param startedAt startedAt
     * @param completedAt completedAt
     * @param failureReason failureReason
     * @param createdByActorId createdByActorId
     */
    public record TelemetryIngestionBatch(
            String id,
        String sourceId,
        String endpointId,
        String correlationId,
        IngestionBatchStatus status,
        int receivedCount,
        int acceptedCount,
        int rejectedCount,
        int duplicateCount,
        int quarantinedCount,
        Instant startedAt,
        Instant completedAt,
        String failureReason,
        String createdByActorId
    ) {

        public TelemetryIngestionBatch {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryIngestionBatch id must not be blank.");
        }
        // HRA-051 required: sourceId
        if (sourceId == null || sourceId.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryIngestionBatch source id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidTelemetryValueException("TelemetryIngestionBatch status must not be null.");
        }
        // HRA-051 required: startedAt
        if (startedAt == null) {
            throw new InvalidTelemetryValueException("TelemetryIngestionBatch started at must not be null.");
        }

        id = normalize(id);
        sourceId = normalize(sourceId);
        endpointId = normalize(endpointId);
        correlationId = normalize(correlationId);
        failureReason = normalize(failureReason);
        createdByActorId = normalize(createdByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
