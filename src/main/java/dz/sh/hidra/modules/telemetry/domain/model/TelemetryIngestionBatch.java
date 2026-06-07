/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryIngestionBatch
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Core telemetry ingestion batch domain aggregate.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.AggregateRoot;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCorrelationId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceId;

/**
 * Core telemetry ingestion batch aggregate.
 *
 * <p>Business role:
 * Represents one telemetry ingestion execution and its counters for received, accepted, rejected,
 * duplicate, and quarantined readings.
 *
 * <p>Architecture role:
 * Tracks acquisition audit without containing raw build logs, unresolved stack traces, or analytics
 * calculations.
 */
public final class TelemetryIngestionBatch implements AggregateRoot<TelemetryIngestionBatchId> {

    private final TelemetryIngestionBatchId id;
    private final TelemetrySourceId sourceId;
    private final TelemetryCorrelationId correlationId;
    private final TelemetryIngestionBatchStatus status;
    private final int receivedCount;
    private final int acceptedCount;
    private final int rejectedCount;
    private final int duplicateCount;
    private final int quarantinedCount;
    private final Instant startedAt;
    private final Instant completedAt;
    private final String failureReason;

    private TelemetryIngestionBatch(
            TelemetryIngestionBatchId id,
            TelemetrySourceId sourceId,
            TelemetryCorrelationId correlationId,
            TelemetryIngestionBatchStatus status,
            int receivedCount,
            int acceptedCount,
            int rejectedCount,
            int duplicateCount,
            int quarantinedCount,
            Instant startedAt,
            Instant completedAt,
            String failureReason) {

        this.id = Objects.requireNonNull(id, "Telemetry ingestion batch id must not be null.");
        this.sourceId = Objects.requireNonNull(sourceId, "Telemetry ingestion batch sourceId must not be null.");
        this.correlationId = correlationId;
        this.status = Objects.requireNonNull(status, "Telemetry ingestion batch status must not be null.");
        this.receivedCount = requireNonNegative(receivedCount, "receivedCount");
        this.acceptedCount = requireNonNegative(acceptedCount, "acceptedCount");
        this.rejectedCount = requireNonNegative(rejectedCount, "rejectedCount");
        this.duplicateCount = requireNonNegative(duplicateCount, "duplicateCount");
        this.quarantinedCount = requireNonNegative(quarantinedCount, "quarantinedCount");
        this.startedAt = Objects.requireNonNull(startedAt, "Telemetry ingestion batch startedAt must not be null.");
        this.completedAt = completedAt;
        this.failureReason = normalizeFailureReason(failureReason);

        if (completedAt != null && completedAt.isBefore(startedAt)) {
            throw new BusinessRuleViolationException("Telemetry ingestion batch completedAt must not be before startedAt.");
        }
        if (TelemetryIngestionBatchStatus.FAILED.equals(status) && this.failureReason == null) {
            throw new BusinessRuleViolationException("Failed telemetry ingestion batch requires a failure reason.");
        }
    }

    public static TelemetryIngestionBatch start(TelemetrySourceId sourceId, TelemetryCorrelationId correlationId) {
        return new TelemetryIngestionBatch(
                TelemetryIngestionBatchId.newId(),
                sourceId,
                correlationId,
                TelemetryIngestionBatchStatus.RECEIVED,
                0,
                0,
                0,
                0,
                0,
                Instant.now(),
                null,
                null);
    }

    public static TelemetryIngestionBatch restore(
            TelemetryIngestionBatchId id,
            TelemetrySourceId sourceId,
            TelemetryCorrelationId correlationId,
            TelemetryIngestionBatchStatus status,
            int receivedCount,
            int acceptedCount,
            int rejectedCount,
            int duplicateCount,
            int quarantinedCount,
            Instant startedAt,
            Instant completedAt,
            String failureReason) {

        return new TelemetryIngestionBatch(
                id,
                sourceId,
                correlationId,
                status,
                receivedCount,
                acceptedCount,
                rejectedCount,
                duplicateCount,
                quarantinedCount,
                startedAt,
                completedAt,
                failureReason);
    }

    @Override
    public TelemetryIngestionBatchId id() {
        return id;
    }

    public TelemetrySourceId sourceId() {
        return sourceId;
    }

    public TelemetryCorrelationId correlationId() {
        return correlationId;
    }

    public TelemetryIngestionBatchStatus status() {
        return status;
    }

    public int receivedCount() {
        return receivedCount;
    }

    public int acceptedCount() {
        return acceptedCount;
    }

    public int rejectedCount() {
        return rejectedCount;
    }

    public int duplicateCount() {
        return duplicateCount;
    }

    public int quarantinedCount() {
        return quarantinedCount;
    }

    public Instant startedAt() {
        return startedAt;
    }

    public Instant completedAt() {
        return completedAt;
    }

    public String failureReason() {
        return failureReason;
    }

    public TelemetryIngestionBatch markProcessing() {
        return withStatus(TelemetryIngestionBatchStatus.PROCESSING, null, null);
    }

    public TelemetryIngestionBatch complete(
            int received,
            int accepted,
            int rejected,
            int duplicates,
            int quarantined) {

        TelemetryIngestionBatchStatus finalStatus = rejected > 0 || quarantined > 0
                ? TelemetryIngestionBatchStatus.COMPLETED_WITH_ERRORS
                : TelemetryIngestionBatchStatus.COMPLETED;

        return new TelemetryIngestionBatch(
                id,
                sourceId,
                correlationId,
                finalStatus,
                received,
                accepted,
                rejected,
                duplicates,
                quarantined,
                startedAt,
                Instant.now(),
                null);
    }

    public TelemetryIngestionBatch fail(String reason) {
        return withStatus(TelemetryIngestionBatchStatus.FAILED, Instant.now(), reason);
    }

    private TelemetryIngestionBatch withStatus(
            TelemetryIngestionBatchStatus newStatus,
            Instant completed,
            String reason) {

        return new TelemetryIngestionBatch(
                id,
                sourceId,
                correlationId,
                newStatus,
                receivedCount,
                acceptedCount,
                rejectedCount,
                duplicateCount,
                quarantinedCount,
                startedAt,
                completed,
                reason);
    }

    private static int requireNonNegative(int value, String fieldName) {
        if (value < 0) {
            throw new BusinessRuleViolationException("Telemetry ingestion batch " + fieldName + " must not be negative.");
        }
        return value;
    }

    private static String normalizeFailureReason(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim();

        if (normalized.length() > 500) {
            throw new BusinessRuleViolationException("Telemetry ingestion batch failure reason length must not exceed 500 characters.");
        }

        return normalized;
    }
}
