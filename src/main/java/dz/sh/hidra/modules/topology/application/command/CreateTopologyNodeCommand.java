/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateTopologyNodeCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.command
 *
 * @Description : Application command for creating a topology node.
 *
 */
package dz.sh.hidra.modules.topology.application.command;

import java.math.BigDecimal;
import java.util.Objects;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.topology.domain.value.FacilityId;
import dz.sh.hidra.modules.topology.domain.value.GeoCoordinate;
import dz.sh.hidra.modules.topology.domain.value.NodeType;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceId;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;

/**
 * Carries input required to create a topology node.
 *
 * <p>Business role:
 * This command creates a physical graph vertex such as a facility inlet/outlet, junction, valve
 * point, injection point, extraction point, purge point, vent, drain, scraper point, receipt point,
 * or delivery point.
 *
 * <p>Architecture role:
 * This is an application command and remains framework-independent.
 *
 * <p>Validation:
 * Code, name, and node type are mandatory. Facility id, appurtenance id, coordinate, and elevation
 * are optional.
 *
 * @param code business code
 * @param name display name
 * @param nodeType node type
 * @param facilityId optional facility identifier
 * @param pipelineAppurtenanceId optional pipeline appurtenance identifier
 * @param coordinate optional coordinate
 * @param elevationMeters optional elevation in meters
 */
public record CreateTopologyNodeCommand(
        TopologyCode code,
        TopologyName name,
        NodeType nodeType,
        FacilityId facilityId,
        PipelineAppurtenanceId pipelineAppurtenanceId,
        GeoCoordinate coordinate,
        BigDecimal elevationMeters) implements Command {

    public CreateTopologyNodeCommand {
        Objects.requireNonNull(code, "Topology node code must not be null.");
        Objects.requireNonNull(name, "Topology node name must not be null.");
        Objects.requireNonNull(nodeType, "Topology node type must not be null.");

        if (elevationMeters != null) {
            elevationMeters = elevationMeters.stripTrailingZeros();
        }
    }
}
