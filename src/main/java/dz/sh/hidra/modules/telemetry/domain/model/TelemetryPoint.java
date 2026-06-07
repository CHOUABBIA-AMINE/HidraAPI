/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPoint
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Core telemetry point domain entity.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryAggregationMethodReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryExternalReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryLocalizedName;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySamplingPeriodSeconds;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySignalTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryUnitReference;

/**
 * Core telemetry point domain entity.
 *
 * <p>Business role:
 * Represents a measurable or observable signal/tag/point provided by a telemetry device.
 *
 * <p>Architecture role:
 * Pure telemetry point definition. It does not contain readings and does not reference topology
 * implementation classes directly.
 */
public final class TelemetryPoint implements Entity<TelemetryPointId> {

    private final TelemetryPointId id;
    private final TelemetryDeviceId deviceId;
    private final TelemetryCode code;
    private final TelemetryLocalizedName name;
    private final TelemetryPointTypeReference pointType;
    private final TelemetrySignalTypeReference signalType;
    private final TelemetryUnitReference unit;
    private final TelemetryAggregationMethodReference defaultAggregationMethod;
    private final TelemetrySamplingPeriodSeconds samplingPeriod;
    private final TelemetryExternalReference externalReference;
    private final TelemetryPointStatus status;
    private final Instant createdAt;
    private final Instant updatedAt;

    private TelemetryPoint(
            TelemetryPointId id,
            TelemetryDeviceId deviceId,
            TelemetryCode code,
            TelemetryLocalizedName name,
            TelemetryPointTypeReference pointType,
            TelemetrySignalTypeReference signalType,
            TelemetryUnitReference unit,
            TelemetryAggregationMethodReference defaultAggregationMethod,
            TelemetrySamplingPeriodSeconds samplingPeriod,
            TelemetryExternalReference externalReference,
            TelemetryPointStatus status,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Telemetry point id must not be null.");
        this.deviceId = Objects.requireNonNull(deviceId, "Telemetry point deviceId must not be null.");
        this.code = Objects.requireNonNull(code, "Telemetry point code must not be null.");
        this.name = Objects.requireNonNull(name, "Telemetry point name must not be null.");
        this.pointType = Objects.requireNonNull(pointType, "Telemetry point type must not be null.");
        this.signalType = Objects.requireNonNull(signalType, "Telemetry point signal type must not be null.");
        this.unit = unit;
        this.defaultAggregationMethod = defaultAggregationMethod;
        this.samplingPeriod = samplingPeriod;
        this.externalReference = externalReference;
        this.status = Objects.requireNonNull(status, "Telemetry point status must not be null.");
        this.createdAt = Objects.requireNonNull(createdAt, "Telemetry point createdAt must not be null.");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Telemetry point updatedAt must not be null.");

        if (updatedAt.isBefore(createdAt)) {
            throw new BusinessRuleViolationException("Telemetry point updatedAt must not be before createdAt.");
        }
    }

    public static TelemetryPoint create(
            TelemetryDeviceId deviceId,
            TelemetryCode code,
            TelemetryLocalizedName name,
            TelemetryPointTypeReference pointType,
            TelemetrySignalTypeReference signalType,
            TelemetryUnitReference unit,
            TelemetryAggregationMethodReference defaultAggregationMethod,
            TelemetrySamplingPeriodSeconds samplingPeriod,
            TelemetryExternalReference externalReference) {

        Instant now = Instant.now();
        return new TelemetryPoint(
                TelemetryPointId.newId(),
                deviceId,
                code,
                name,
                pointType,
                signalType,
                unit,
                defaultAggregationMethod,
                samplingPeriod,
                externalReference,
                TelemetryPointStatus.PLANNED,
                now,
                now);
    }

    public static TelemetryPoint restore(
            TelemetryPointId id,
            TelemetryDeviceId deviceId,
            TelemetryCode code,
            TelemetryLocalizedName name,
            TelemetryPointTypeReference pointType,
            TelemetrySignalTypeReference signalType,
            TelemetryUnitReference unit,
            TelemetryAggregationMethodReference defaultAggregationMethod,
            TelemetrySamplingPeriodSeconds samplingPeriod,
            TelemetryExternalReference externalReference,
            TelemetryPointStatus status,
            Instant createdAt,
            Instant updatedAt) {

        return new TelemetryPoint(
                id,
                deviceId,
                code,
                name,
                pointType,
                signalType,
                unit,
                defaultAggregationMethod,
                samplingPeriod,
                externalReference,
                status,
                createdAt,
                updatedAt);
    }

    @Override
    public TelemetryPointId id() {
        return id;
    }

    public TelemetryDeviceId deviceId() {
        return deviceId;
    }

    public TelemetryCode code() {
        return code;
    }

    public TelemetryLocalizedName name() {
        return name;
    }

    public TelemetryPointTypeReference pointType() {
        return pointType;
    }

    public TelemetrySignalTypeReference signalType() {
        return signalType;
    }

    public TelemetryUnitReference unit() {
        return unit;
    }

    public TelemetryAggregationMethodReference defaultAggregationMethod() {
        return defaultAggregationMethod;
    }

    public TelemetrySamplingPeriodSeconds samplingPeriod() {
        return samplingPeriod;
    }

    public TelemetryExternalReference externalReference() {
        return externalReference;
    }

    public TelemetryPointStatus status() {
        return status;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public boolean active() {
        return TelemetryPointStatus.ACTIVE.equals(status);
    }

    public TelemetryPoint activate() {
        if (TelemetryPointStatus.RETIRED.equals(status)) {
            throw new BusinessRuleViolationException("Retired telemetry point cannot be activated.");
        }
        return withStatus(TelemetryPointStatus.ACTIVE);
    }

    public TelemetryPoint suspend() {
        if (TelemetryPointStatus.RETIRED.equals(status)) {
            throw new BusinessRuleViolationException("Retired telemetry point cannot be suspended.");
        }
        return withStatus(TelemetryPointStatus.SUSPENDED);
    }

    public TelemetryPoint retire() {
        return withStatus(TelemetryPointStatus.RETIRED);
    }

    private TelemetryPoint withStatus(TelemetryPointStatus newStatus) {
        return new TelemetryPoint(
                id,
                deviceId,
                code,
                name,
                pointType,
                signalType,
                unit,
                defaultAggregationMethod,
                samplingPeriod,
                externalReference,
                newStatus,
                createdAt,
                Instant.now());
    }
}
