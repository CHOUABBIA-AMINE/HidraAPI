/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.policy
 *
 * @Description : Domain policy for telemetry point lifecycle and signal consistency.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.policy;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryDevice;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointStatus;

/**
 * Domain policy for telemetry point lifecycle and signal consistency.
 *
 * <p>Business role:
 * Guards telemetry point registration and lifecycle operations under telemetry devices.
 *
 * <p>Architecture role:
 * Pure domain policy. It does not load devices or points from repositories.
 */
public final class TelemetryPointPolicy {

    /**
     * Ensures a device can accept telemetry point registration.
     *
     * @param device telemetry device
     */
    public void requireDeviceCanRegisterPoints(TelemetryDevice device) {
        Objects.requireNonNull(device, "Telemetry device must not be null.");

        if (TelemetryDeviceStatus.RETIRED.equals(device.status())) {
            throw new BusinessRuleViolationException("Retired telemetry device cannot register points.");
        }

        if (TelemetryDeviceStatus.INACTIVE.equals(device.status())) {
            throw new BusinessRuleViolationException("Inactive telemetry device cannot register points.");
        }
    }

    /**
     * Ensures a point belongs to the supplied device.
     *
     * @param device telemetry device
     * @param point telemetry point
     */
    public void requirePointBelongsToDevice(TelemetryDevice device, TelemetryPoint point) {
        Objects.requireNonNull(device, "Telemetry device must not be null.");
        Objects.requireNonNull(point, "Telemetry point must not be null.");

        if (!device.id().equals(point.deviceId())) {
            throw new BusinessRuleViolationException("Telemetry point does not belong to the supplied telemetry device.");
        }
    }

    /**
     * Ensures a point can be activated under its device.
     *
     * @param device telemetry device
     * @param point telemetry point
     */
    public void requirePointCanBeActivated(TelemetryDevice device, TelemetryPoint point) {
        requirePointBelongsToDevice(device, point);

        if (!TelemetryDeviceStatus.ACTIVE.equals(device.status())) {
            throw new BusinessRuleViolationException("Telemetry point can be activated only under an active device.");
        }

        if (TelemetryPointStatus.RETIRED.equals(point.status())) {
            throw new BusinessRuleViolationException("Retired telemetry point cannot be activated.");
        }
    }

    /**
     * Ensures a point can accept readings.
     *
     * @param point telemetry point
     */
    public void requirePointCanAcceptReadings(TelemetryPoint point) {
        Objects.requireNonNull(point, "Telemetry point must not be null.");

        if (!TelemetryPointStatus.ACTIVE.equals(point.status())) {
            throw new BusinessRuleViolationException("Telemetry point can accept readings only when active.");
        }
    }

    /**
     * Ensures numeric unit presence for numeric points when the point type requires a unit.
     *
     * @param point telemetry point
     */
    public void requireUnitForNumericPoint(TelemetryPoint point) {
        Objects.requireNonNull(point, "Telemetry point must not be null.");

        if (point.signalType().is("NUMERIC") && point.unit() == null) {
            throw new BusinessRuleViolationException("Numeric telemetry point requires a unit reference.");
        }
    }
}
