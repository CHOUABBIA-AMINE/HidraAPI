/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingDomainService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.service
 *
 * @Description : Domain service for telemetry reading lifecycle workflows.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.service;

import java.util.Objects;

import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryPointPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryReadingPolicy;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCorrelationId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryQualityCodeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingValue;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTimestamp;

/**
 * Domain service for telemetry reading lifecycle workflows.
 *
 * <p>Business role:
 * Receives readings and coordinates accepted, rejected, duplicate, and quarantined state changes.
 *
 * <p>Architecture role:
 * Pure domain service. It does not persist readings, calculate flow, aggregate analytics, or access
 * external acquisition protocols.
 */
public final class TelemetryReadingDomainService {

    private final TelemetryPointPolicy pointPolicy;
    private final TelemetryReadingPolicy readingPolicy;

    public TelemetryReadingDomainService(
            TelemetryPointPolicy pointPolicy,
            TelemetryReadingPolicy readingPolicy) {

        this.pointPolicy = Objects.requireNonNull(pointPolicy, "TelemetryPointPolicy must not be null.");
        this.readingPolicy = Objects.requireNonNull(readingPolicy, "TelemetryReadingPolicy must not be null.");
    }

    public TelemetryReading receiveReading(
            TelemetryPoint point,
            TelemetryReadingValue value,
            TelemetryQualityCodeReference qualityCode,
            TelemetryTimestamp sourceTimestamp,
            TelemetryIngestionBatchId ingestionBatchId,
            TelemetryCorrelationId correlationId) {

        pointPolicy.requirePointCanAcceptReadings(point);
        readingPolicy.requirePointCanReceiveReading(point);

        return TelemetryReading.receive(
                point.id(),
                value,
                qualityCode,
                sourceTimestamp,
                ingestionBatchId,
                correlationId);
    }

    public TelemetryReading acceptReading(TelemetryPoint point, TelemetryReading reading) {
        readingPolicy.requireReadingBelongsToPoint(point, reading);
        readingPolicy.requireReadingCanBeAccepted(reading);
        return reading.accept();
    }

    public TelemetryReading rejectReading(TelemetryPoint point, TelemetryReading reading, String reason) {
        readingPolicy.requireReadingBelongsToPoint(point, reading);
        readingPolicy.requireReadingCanBeRejected(reading, reason);
        return reading.reject(reason);
    }

    public TelemetryReading markDuplicate(TelemetryPoint point, TelemetryReading reading) {
        readingPolicy.requireReadingBelongsToPoint(point, reading);
        readingPolicy.requireReadingCanBeMarkedDuplicate(reading);
        return reading.markDuplicate();
    }

    public TelemetryReading quarantineReading(TelemetryPoint point, TelemetryReading reading, String reason) {
        readingPolicy.requireReadingBelongsToPoint(point, reading);
        readingPolicy.requireReadingCanBeQuarantined(reading, reason);
        return reading.quarantine(reason);
    }
}
