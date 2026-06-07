/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryDevice
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Core telemetry device domain entity.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryExternalReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryLocalizedName;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceId;

/**
 * Core telemetry device domain entity.
 *
 * <p>Business role:
 * Represents a device, gateway, PLC endpoint, RTU, meter, transmitter, or acquisition node managed
 * by a telemetry source.
 *
 * <p>Architecture role:
 * Pure domain entity linked to a telemetry source by id only.
 */
public final class TelemetryDevice implements Entity<TelemetryDeviceId> {

    private final TelemetryDeviceId id;
    private final TelemetrySourceId sourceId;
    private final TelemetryCode code;
    private final TelemetryLocalizedName name;
    private final TelemetryDeviceTypeReference deviceType;
    private final TelemetryExternalReference externalReference;
    private final TelemetryDeviceStatus status;
    private final Instant createdAt;
    private final Instant updatedAt;

    private TelemetryDevice(
            TelemetryDeviceId id,
            TelemetrySourceId sourceId,
            TelemetryCode code,
            TelemetryLocalizedName name,
            TelemetryDeviceTypeReference deviceType,
            TelemetryExternalReference externalReference,
            TelemetryDeviceStatus status,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Telemetry device id must not be null.");
        this.sourceId = Objects.requireNonNull(sourceId, "Telemetry device sourceId must not be null.");
        this.code = Objects.requireNonNull(code, "Telemetry device code must not be null.");
        this.name = Objects.requireNonNull(name, "Telemetry device name must not be null.");
        this.deviceType = Objects.requireNonNull(deviceType, "Telemetry device type must not be null.");
        this.externalReference = externalReference;
        this.status = Objects.requireNonNull(status, "Telemetry device status must not be null.");
        this.createdAt = Objects.requireNonNull(createdAt, "Telemetry device createdAt must not be null.");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Telemetry device updatedAt must not be null.");

        if (updatedAt.isBefore(createdAt)) {
            throw new BusinessRuleViolationException("Telemetry device updatedAt must not be before createdAt.");
        }
    }

    public static TelemetryDevice create(
            TelemetrySourceId sourceId,
            TelemetryCode code,
            TelemetryLocalizedName name,
            TelemetryDeviceTypeReference deviceType,
            TelemetryExternalReference externalReference) {

        Instant now = Instant.now();
        return new TelemetryDevice(
                TelemetryDeviceId.newId(),
                sourceId,
                code,
                name,
                deviceType,
                externalReference,
                TelemetryDeviceStatus.PLANNED,
                now,
                now);
    }

    public static TelemetryDevice restore(
            TelemetryDeviceId id,
            TelemetrySourceId sourceId,
            TelemetryCode code,
            TelemetryLocalizedName name,
            TelemetryDeviceTypeReference deviceType,
            TelemetryExternalReference externalReference,
            TelemetryDeviceStatus status,
            Instant createdAt,
            Instant updatedAt) {

        return new TelemetryDevice(
                id,
                sourceId,
                code,
                name,
                deviceType,
                externalReference,
                status,
                createdAt,
                updatedAt);
    }

    @Override
    public TelemetryDeviceId id() {
        return id;
    }

    public TelemetrySourceId sourceId() {
        return sourceId;
    }

    public TelemetryCode code() {
        return code;
    }

    public TelemetryLocalizedName name() {
        return name;
    }

    public TelemetryDeviceTypeReference deviceType() {
        return deviceType;
    }

    public TelemetryExternalReference externalReference() {
        return externalReference;
    }

    public TelemetryDeviceStatus status() {
        return status;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public boolean active() {
        return TelemetryDeviceStatus.ACTIVE.equals(status);
    }

    public TelemetryDevice activate() {
        if (TelemetryDeviceStatus.RETIRED.equals(status)) {
            throw new BusinessRuleViolationException("Retired telemetry device cannot be activated.");
        }
        return withStatus(TelemetryDeviceStatus.ACTIVE);
    }

    public TelemetryDevice deactivate() {
        if (TelemetryDeviceStatus.RETIRED.equals(status)) {
            throw new BusinessRuleViolationException("Retired telemetry device cannot be deactivated.");
        }
        return withStatus(TelemetryDeviceStatus.INACTIVE);
    }

    public TelemetryDevice retire() {
        return withStatus(TelemetryDeviceStatus.RETIRED);
    }

    private TelemetryDevice withStatus(TelemetryDeviceStatus newStatus) {
        return new TelemetryDevice(
                id,
                sourceId,
                code,
                name,
                deviceType,
                externalReference,
                newStatus,
                createdAt,
                Instant.now());
    }
}
