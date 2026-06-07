/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterTelemetryDeviceCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.command
 *
 * @Description : Command to register a telemetry device under a source.
 *
 */
package dz.sh.hidra.modules.telemetry.application.command;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryExternalReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryLocalizedName;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceId;
import java.util.Objects;

/**
 * Command to register a telemetry device under a source.
 *
 * <p>Architecture role:
 * Application-layer command contract for the telemetry module. It carries validated
 * domain value objects and catalog references only. It must not depend on persistence, REST, Spring,
 * JPA, topology implementation classes, flow, risk, analytics, workflow, reporting, or notification.
 */
public record RegisterTelemetryDeviceCommand(
        TelemetrySourceId sourceId,
        TelemetryCode code,
        TelemetryLocalizedName name,
        TelemetryDeviceTypeReference deviceType,
        TelemetryExternalReference externalReference) implements Command {

    public RegisterTelemetryDeviceCommand {
        sourceId = Objects.requireNonNull(sourceId, "RegisterTelemetryDeviceCommand sourceId must not be null.");
        code = Objects.requireNonNull(code, "RegisterTelemetryDeviceCommand code must not be null.");
        name = Objects.requireNonNull(name, "RegisterTelemetryDeviceCommand name must not be null.");
        deviceType = Objects.requireNonNull(deviceType, "RegisterTelemetryDeviceCommand deviceType must not be null.");
    }
}
