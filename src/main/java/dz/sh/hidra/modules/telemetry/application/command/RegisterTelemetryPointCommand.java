/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterTelemetryPointCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.command
 *
 * @Description : Command to register a telemetry point under a device.
 *
 */
package dz.sh.hidra.modules.telemetry.application.command;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryAggregationMethodReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryExternalReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryLocalizedName;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySamplingPeriodSeconds;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySignalTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryUnitReference;
import java.util.Objects;

/**
 * Command to register a telemetry point under a device.
 *
 * <p>Architecture role:
 * Application-layer command contract for the telemetry module. It carries validated
 * domain value objects and catalog references only. It must not depend on persistence, REST, Spring,
 * JPA, topology implementation classes, flow, risk, analytics, workflow, reporting, or notification.
 */
public record RegisterTelemetryPointCommand(
        TelemetryDeviceId deviceId,
        TelemetryCode code,
        TelemetryLocalizedName name,
        TelemetryPointTypeReference pointType,
        TelemetrySignalTypeReference signalType,
        TelemetryUnitReference unit,
        TelemetryAggregationMethodReference defaultAggregationMethod,
        TelemetrySamplingPeriodSeconds samplingPeriod,
        TelemetryExternalReference externalReference) implements Command {

    public RegisterTelemetryPointCommand {
        deviceId = Objects.requireNonNull(deviceId, "RegisterTelemetryPointCommand deviceId must not be null.");
        code = Objects.requireNonNull(code, "RegisterTelemetryPointCommand code must not be null.");
        name = Objects.requireNonNull(name, "RegisterTelemetryPointCommand name must not be null.");
        pointType = Objects.requireNonNull(pointType, "RegisterTelemetryPointCommand pointType must not be null.");
        signalType = Objects.requireNonNull(signalType, "RegisterTelemetryPointCommand signalType must not be null.");
    }
}
