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
import dz.sh.hidra.modules.topology.domain.value.NodeTypeReference;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceId;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;

/**
 * Carries input required to create a topology node.
 */
public record CreateTopologyNodeCommand(
        TopologyCode code,
        TopologyName name,
        NodeTypeReference nodeType,
        FacilityId facilityId,
        PipelineAppurtenanceId pipelineAppurtenanceId,
        GeoCoordinate coordinate,
        BigDecimal elevationMeters) implements Command {

    public CreateTopologyNodeCommand {
        Objects.requireNonNull(code, "Topology node code must not be null.");
        Objects.requireNonNull(name, "Topology node name must not be null.");
        Objects.requireNonNull(nodeType, "Topology node type reference must not be null.");
        if (elevationMeters != null) { elevationMeters = elevationMeters.stripTrailingZeros(); }
    }

    @Deprecated(forRemoval = true)
    public CreateTopologyNodeCommand(
            TopologyCode code,
            TopologyName name,
            NodeType nodeType,
            FacilityId facilityId,
            PipelineAppurtenanceId pipelineAppurtenanceId,
            GeoCoordinate coordinate,
            BigDecimal elevationMeters) {

        this(code, name, NodeTypeReference.from(nodeType), facilityId, pipelineAppurtenanceId, coordinate, elevationMeters);
    }
}
