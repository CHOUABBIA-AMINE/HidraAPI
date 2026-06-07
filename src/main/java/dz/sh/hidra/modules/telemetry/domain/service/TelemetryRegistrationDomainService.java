/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryRegistrationDomainService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.service
 *
 * @Description : Domain service for telemetry source, device, and point registration workflows.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.service;

import java.util.Objects;

import dz.sh.hidra.modules.telemetry.domain.model.TelemetryDevice;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetrySource;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetryPointPolicy;
import dz.sh.hidra.modules.telemetry.domain.policy.TelemetrySourcePolicy;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryAggregationMethodReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryEndpointUri;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryExternalReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryLocalizedName;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryProtocolReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySamplingPeriodSeconds;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySignalTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryUnitReference;

/**
 * Domain service for telemetry source, device, and point registration workflows.
 *
 * <p>Business role:
 * Coordinates creation and lifecycle changes for sources, devices, and points while reusing domain
 * policies.
 *
 * <p>Architecture role:
 * Pure domain service. It does not check uniqueness, load parents, persist aggregates, expose REST,
 * or call external telemetry protocols. Application services own repository orchestration.
 */
public final class TelemetryRegistrationDomainService {

    private final TelemetrySourcePolicy sourcePolicy;
    private final TelemetryPointPolicy pointPolicy;

    public TelemetryRegistrationDomainService(
            TelemetrySourcePolicy sourcePolicy,
            TelemetryPointPolicy pointPolicy) {

        this.sourcePolicy = Objects.requireNonNull(sourcePolicy, "TelemetrySourcePolicy must not be null.");
        this.pointPolicy = Objects.requireNonNull(pointPolicy, "TelemetryPointPolicy must not be null.");
    }

    public TelemetrySource registerSource(
            TelemetryCode code,
            TelemetryLocalizedName name,
            TelemetrySourceTypeReference sourceType,
            TelemetryProtocolReference protocol,
            TelemetryEndpointUri endpointUri,
            TelemetryExternalReference externalReference) {

        return TelemetrySource.create(code, name, sourceType, protocol, endpointUri, externalReference);
    }

    public TelemetryDevice registerDevice(
            TelemetrySource source,
            TelemetryCode code,
            TelemetryLocalizedName name,
            TelemetryDeviceTypeReference deviceType,
            TelemetryExternalReference externalReference) {

        sourcePolicy.requireSourceCanRegisterDevices(source);

        return TelemetryDevice.create(
                source.id(),
                code,
                name,
                deviceType,
                externalReference);
    }

    public TelemetryPoint registerPoint(
            TelemetryDevice device,
            TelemetryCode code,
            TelemetryLocalizedName name,
            TelemetryPointTypeReference pointType,
            TelemetrySignalTypeReference signalType,
            TelemetryUnitReference unit,
            TelemetryAggregationMethodReference defaultAggregationMethod,
            TelemetrySamplingPeriodSeconds samplingPeriod,
            TelemetryExternalReference externalReference) {

        pointPolicy.requireDeviceCanRegisterPoints(device);

        TelemetryPoint point = TelemetryPoint.create(
                device.id(),
                code,
                name,
                pointType,
                signalType,
                unit,
                defaultAggregationMethod,
                samplingPeriod,
                externalReference);

        pointPolicy.requireUnitForNumericPoint(point);

        return point;
    }

    public TelemetryDevice activateDevice(TelemetrySource source, TelemetryDevice device) {
        sourcePolicy.requireDeviceCanBeActivated(source, device);
        return device.activate();
    }

    public TelemetryPoint activatePoint(TelemetryDevice device, TelemetryPoint point) {
        pointPolicy.requirePointCanBeActivated(device, point);
        pointPolicy.requireUnitForNumericPoint(point);
        return point.activate();
    }

    public TelemetryPoint suspendPoint(TelemetryPoint point) {
        Objects.requireNonNull(point, "Telemetry point must not be null.");
        return point.suspend();
    }
}
