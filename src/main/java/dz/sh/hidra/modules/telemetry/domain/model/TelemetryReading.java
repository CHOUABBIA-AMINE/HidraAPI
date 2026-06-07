/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReading
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Core telemetry reading domain entity.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCorrelationId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryQualityCodeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingState;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingValue;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTimestamp;

/**
 * Core telemetry reading domain entity.
 *
 * <p>Business role:
 * Represents one captured raw telemetry value with source time, received time, quality, processing
 * state, and optional ingestion batch/correlation references.
 *
 * <p>Architecture role:
 * Pure domain entity. It does not perform flow calculation or analytics aggregation.
 */
public final class TelemetryReading implements Entity<TelemetryReadingId> {

    private final TelemetryReadingId id;
    private final TelemetryPointId pointId;
    private final TelemetryReadingValue value;
    private final TelemetryQualityCodeReference qualityCode;
    private final TelemetryTimestamp sourceTimestamp;
    private final TelemetryTimestamp receivedAt;
    private final TelemetryReadingState state;
    private final TelemetryIngestionBatchId ingestionBatchId;
    private final TelemetryCorrelationId correlationId;
    private final String rejectionReason;

    private TelemetryReading(
            TelemetryReadingId id,
            TelemetryPointId pointId,
            TelemetryReadingValue value,
            TelemetryQualityCodeReference qualityCode,
            TelemetryTimestamp sourceTimestamp,
            TelemetryTimestamp receivedAt,
            TelemetryReadingState state,
            TelemetryIngestionBatchId ingestionBatchId,
            TelemetryCorrelationId correlationId,
            String rejectionReason) {

        this.id = Objects.requireNonNull(id, "Telemetry reading id must not be null.");
        this.pointId = Objects.requireNonNull(pointId, "Telemetry reading pointId must not be null.");
        this.value = Objects.requireNonNull(value, "Telemetry reading value must not be null.");
        this.qualityCode = Objects.requireNonNull(qualityCode, "Telemetry reading qualityCode must not be null.");
        this.sourceTimestamp = Objects.requireNonNull(sourceTimestamp, "Telemetry reading sourceTimestamp must not be null.");
        this.receivedAt = Objects.requireNonNull(receivedAt, "Telemetry reading receivedAt must not be null.");
        this.state = Objects.requireNonNull(state, "Telemetry reading state must not be null.");
        this.ingestionBatchId = ingestionBatchId;
        this.correlationId = correlationId;
        this.rejectionReason = normalizeReason(rejectionReason);

        if (receivedAt.value().isBefore(sourceTimestamp.value())) {
            throw new BusinessRuleViolationException("Telemetry reading receivedAt must not be before sourceTimestamp.");
        }
        if (TelemetryReadingState.REJECTED.equals(state) && this.rejectionReason == null) {
            throw new BusinessRuleViolationException("Rejected telemetry reading requires a rejection reason.");
        }
    }

    public static TelemetryReading receive(
            TelemetryPointId pointId,
            TelemetryReadingValue value,
            TelemetryQualityCodeReference qualityCode,
            TelemetryTimestamp sourceTimestamp,
            TelemetryIngestionBatchId ingestionBatchId,
            TelemetryCorrelationId correlationId) {

        return new TelemetryReading(
                TelemetryReadingId.newId(),
                pointId,
                value,
                qualityCode,
                sourceTimestamp,
                TelemetryTimestamp.now(),
                TelemetryReadingState.RECEIVED,
                ingestionBatchId,
                correlationId,
                null);
    }

    public static TelemetryReading restore(
            TelemetryReadingId id,
            TelemetryPointId pointId,
            TelemetryReadingValue value,
            TelemetryQualityCodeReference qualityCode,
            TelemetryTimestamp sourceTimestamp,
            TelemetryTimestamp receivedAt,
            TelemetryReadingState state,
            TelemetryIngestionBatchId ingestionBatchId,
            TelemetryCorrelationId correlationId,
            String rejectionReason) {

        return new TelemetryReading(
                id,
                pointId,
                value,
                qualityCode,
                sourceTimestamp,
                receivedAt,
                state,
                ingestionBatchId,
                correlationId,
                rejectionReason);
    }

    @Override
    public TelemetryReadingId id() {
        return id;
    }

    public TelemetryPointId pointId() {
        return pointId;
    }

    public TelemetryReadingValue value() {
        return value;
    }

    public TelemetryQualityCodeReference qualityCode() {
        return qualityCode;
    }

    public TelemetryTimestamp sourceTimestamp() {
        return sourceTimestamp;
    }

    public TelemetryTimestamp receivedAt() {
        return receivedAt;
    }

    public TelemetryReadingState state() {
        return state;
    }

    public TelemetryIngestionBatchId ingestionBatchId() {
        return ingestionBatchId;
    }

    public TelemetryCorrelationId correlationId() {
        return correlationId;
    }

    public String rejectionReason() {
        return rejectionReason;
    }

    public TelemetryReading accept() {
        return withState(TelemetryReadingState.ACCEPTED, null);
    }

    public TelemetryReading markDuplicate() {
        return withState(TelemetryReadingState.DUPLICATE, null);
    }

    public TelemetryReading quarantine(String reason) {
        return withState(TelemetryReadingState.QUARANTINED, normalizeReason(reason));
    }

    public TelemetryReading reject(String reason) {
        return withState(TelemetryReadingState.REJECTED, normalizeReason(reason));
    }

    private TelemetryReading withState(TelemetryReadingState newState, String reason) {
        return new TelemetryReading(
                id,
                pointId,
                value,
                qualityCode,
                sourceTimestamp,
                receivedAt,
                newState,
                ingestionBatchId,
                correlationId,
                reason);
    }

    private static String normalizeReason(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim();

        if (normalized.length() > 500) {
            throw new BusinessRuleViolationException("Telemetry reading reason length must not exceed 500 characters.");
        }

        return normalized;
    }
}
