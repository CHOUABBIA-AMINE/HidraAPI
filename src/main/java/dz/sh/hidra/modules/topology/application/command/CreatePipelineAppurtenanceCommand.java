/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreatePipelineAppurtenanceCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.command
 *
 * @Description : Application command for creating a pipeline appurtenance.
 *
 */
package dz.sh.hidra.modules.topology.application.command;

import java.util.Objects;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.topology.domain.value.GeoCoordinate;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceType;
import dz.sh.hidra.modules.topology.domain.value.PipelineId;
import dz.sh.hidra.modules.topology.domain.value.PipelineKilometerPoint;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;
import dz.sh.hidra.modules.topology.domain.value.TopologyNodeId;
import dz.sh.hidra.modules.topology.domain.value.ValveType;

/**
 * Carries input required to create a pipeline appurtenance.
 *
 * <p>Business role:
 * This command creates point assets installed along a pipeline such as valves, injection points,
 * extraction points, purge points, vents, drains, scraper launchers, scraper receivers, hot taps,
 * bypass points, metering points, sampling points, and connection points.
 *
 * <p>Architecture role:
 * This is an application command. It must not implement operation history, permits, telemetry,
 * hydraulic calculations, risk, maintenance, or workflow behavior.
 *
 * <p>Validation:
 * Pipeline id, node id, code, name, appurtenance type, and KP are mandatory. Valve type is required
 * only when appurtenance type is VALVE and forbidden otherwise.
 *
 * @param pipelineId parent pipeline identifier
 * @param nodeId topology node identifier
 * @param code business code
 * @param name display name
 * @param appurtenanceType appurtenance type
 * @param valveType optional valve type
 * @param pipelineKilometerPoint KP/PK/chainage
 * @param coordinate optional coordinate
 * @param description optional description
 */
public record CreatePipelineAppurtenanceCommand(
        PipelineId pipelineId,
        TopologyNodeId nodeId,
        TopologyCode code,
        TopologyName name,
        PipelineAppurtenanceType appurtenanceType,
        ValveType valveType,
        PipelineKilometerPoint pipelineKilometerPoint,
        GeoCoordinate coordinate,
        String description) implements Command {

    public CreatePipelineAppurtenanceCommand {
        Objects.requireNonNull(pipelineId, "Pipeline id must not be null.");
        Objects.requireNonNull(nodeId, "Pipeline appurtenance node id must not be null.");
        Objects.requireNonNull(code, "Pipeline appurtenance code must not be null.");
        Objects.requireNonNull(name, "Pipeline appurtenance name must not be null.");
        Objects.requireNonNull(appurtenanceType, "Pipeline appurtenance type must not be null.");
        Objects.requireNonNull(pipelineKilometerPoint, "Pipeline appurtenance KP must not be null.");

        if (appurtenanceType.isValve() && valveType == null) {
            throw new IllegalArgumentException("Valve type is required when appurtenance type is VALVE.");
        }

        if (!appurtenanceType.isValve() && valveType != null) {
            throw new IllegalArgumentException("Valve type must be null when appurtenance type is not VALVE.");
        }

        description = normalizeOptional(description, "Pipeline appurtenance description", 500);
    }

    private static String normalizeOptional(String value, String label, int maxLength) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim();
        if (normalized.length() > maxLength) {
            throw new IllegalArgumentException(label + " must not exceed " + maxLength + " characters.");
        }
        return normalized;
    }

}
