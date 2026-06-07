/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySourcePolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.policy
 *
 * @Description : Domain policy for telemetry source and device lifecycle compatibility.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.policy;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryDevice;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetrySource;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceStatus;

/**
 * Domain policy for telemetry source and device lifecycle compatibility.
 *
 * <p>Business role:
 * Guards telemetry device registration and activation against invalid source/device states.
 *
 * <p>Architecture role:
 * Pure domain policy with no persistence, REST, JPA, or external module dependencies.
 */
public final class TelemetrySourcePolicy {

    /**
     * Ensures a telemetry source can accept device registration.
     *
     * @param source telemetry source
     */
    public void requireSourceCanRegisterDevices(TelemetrySource source) {
        Objects.requireNonNull(source, "Telemetry source must not be null.");

        if (TelemetrySourceStatus.RETIRED.equals(source.status())) {
            throw new BusinessRuleViolationException("Retired telemetry source cannot register devices.");
        }

        if (TelemetrySourceStatus.INACTIVE.equals(source.status())) {
            throw new BusinessRuleViolationException("Inactive telemetry source cannot register devices.");
        }
    }

    /**
     * Ensures a device belongs to the supplied source.
     *
     * @param source telemetry source
     * @param device telemetry device
     */
    public void requireDeviceBelongsToSource(TelemetrySource source, TelemetryDevice device) {
        Objects.requireNonNull(source, "Telemetry source must not be null.");
        Objects.requireNonNull(device, "Telemetry device must not be null.");

        if (!source.id().equals(device.sourceId())) {
            throw new BusinessRuleViolationException("Telemetry device does not belong to the supplied telemetry source.");
        }
    }

    /**
     * Ensures a device can be activated under its source.
     *
     * @param source telemetry source
     * @param device telemetry device
     */
    public void requireDeviceCanBeActivated(TelemetrySource source, TelemetryDevice device) {
        requireDeviceBelongsToSource(source, device);

        if (!TelemetrySourceStatus.ACTIVE.equals(source.status())) {
            throw new BusinessRuleViolationException("Telemetry device can be activated only under an active source.");
        }

        if (TelemetryDeviceStatus.RETIRED.equals(device.status())) {
            throw new BusinessRuleViolationException("Retired telemetry device cannot be activated.");
        }
    }
}
