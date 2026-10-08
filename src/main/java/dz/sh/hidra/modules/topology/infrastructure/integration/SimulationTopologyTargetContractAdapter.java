/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationTopologyTargetContractAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.integration
 *
 * @Description : Enforces the accepted Simulation owner boundary.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.integration;

import dz.sh.hidra.modules.topology.application.contract.simulation.SimulationTopologyTargetContract;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.*;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public final class SimulationTopologyTargetContractAdapter implements SimulationTopologyTargetContract {
    private final PipelineJpaRepository pipeline;
    private final PipelineSegmentJpaRepository pipelineSegment;
    private final FacilityJpaRepository facility;
    private final EquipmentJpaRepository equipment;
    private final TopologyNodeJpaRepository topologyNode;
    private final TopologyConnectionJpaRepository topologyConnection;

    public SimulationTopologyTargetContractAdapter(
            PipelineJpaRepository pipeline,
            PipelineSegmentJpaRepository pipelineSegment,
            FacilityJpaRepository facility,
            EquipmentJpaRepository equipment,
            TopologyNodeJpaRepository topologyNode,
            TopologyConnectionJpaRepository topologyConnection
    ) {
        this.pipeline = Objects.requireNonNull(pipeline);
        this.pipelineSegment = Objects.requireNonNull(pipelineSegment);
        this.facility = Objects.requireNonNull(facility);
        this.equipment = Objects.requireNonNull(equipment);
        this.topologyNode = Objects.requireNonNull(topologyNode);
        this.topologyConnection = Objects.requireNonNull(topologyConnection);
    }

    @Override
    public boolean exists(String targetType, String targetId) {
        if (targetType == null || targetType.isBlank() || targetId == null || targetId.isBlank()) {
            return false;
        }
        String id = targetId.trim();
        return switch (targetType.trim()) {
            case "PIPELINE" -> pipeline.existsById(id);
            case "SEGMENT" -> pipelineSegment.existsById(id);
            case "FACILITY" -> facility.existsById(id);
            case "EQUIPMENT" -> equipment.existsById(id);
            case "NODE" -> topologyNode.existsById(id);
            case "CONNECTION" -> topologyConnection.existsById(id);
            default -> false;
        };
    }
}
