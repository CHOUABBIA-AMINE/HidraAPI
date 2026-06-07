/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : BindTelemetryPointCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.command
 *
 * @Description : Command to bind a telemetry point to a topology asset.
 *
 */
package dz.sh.hidra.modules.telemetry.application.command;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryBindingRoleReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointId;
import dz.sh.hidra.modules.telemetry.domain.value.TopologyAssetReference;
import java.util.Objects;

/**
 * Command to bind a telemetry point to a topology asset.
 *
 * <p>Architecture role:
 * Application-layer command contract for the telemetry module. It carries validated
 * domain value objects and catalog references only. It must not depend on persistence, REST, Spring,
 * JPA, topology implementation classes, flow, risk, analytics, workflow, reporting, or notification.
 */
public record BindTelemetryPointCommand(
        TelemetryPointId pointId,
        TopologyAssetReference topologyAssetReference,
        TelemetryBindingRoleReference bindingRole,
        java.time.Instant validFrom) implements Command {

    public BindTelemetryPointCommand {
        pointId = Objects.requireNonNull(pointId, "BindTelemetryPointCommand pointId must not be null.");
        topologyAssetReference = Objects.requireNonNull(topologyAssetReference, "BindTelemetryPointCommand topologyAssetReference must not be null.");
        bindingRole = Objects.requireNonNull(bindingRole, "BindTelemetryPointCommand bindingRole must not be null.");
    }
}
