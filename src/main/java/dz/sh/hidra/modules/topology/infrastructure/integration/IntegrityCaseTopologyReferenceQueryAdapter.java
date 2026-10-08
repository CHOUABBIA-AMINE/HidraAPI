/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityCaseTopologyReferenceQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.integration
 *
 * @Description : Validates IntegrityCase provenance through explicit owner-controlled references.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.integration;

import dz.sh.hidra.modules.topology.application.contract.integrity.IntegrityCaseTopologyReferenceContract;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.*;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public final class IntegrityCaseTopologyReferenceQueryAdapter implements IntegrityCaseTopologyReferenceContract {
    private final PipelineJpaRepository pipeline;
    private final PipelineSegmentJpaRepository pipelineSegment;
    private final FacilityJpaRepository facility;
    private final EquipmentJpaRepository equipment;
    private final TopologyNodeJpaRepository topologyNode;
    private final TopologyConnectionJpaRepository topologyConnection;

    public IntegrityCaseTopologyReferenceQueryAdapter(
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
    public java.util.Optional<Asset> resolve(String type,String id) {
        if(type==null || type.isBlank() || id==null || id.isBlank()) return java.util.Optional.empty();
        String key=id.trim();
        return switch(type.trim()) {
            case "PIPELINE" -> pipeline.findById(key).map(x -> new Asset(x.id(),x.code(),x.nameFr()));
            case "SEGMENT" -> pipelineSegment.findById(key).map(x -> new Asset(x.id(),x.code(),null));
            case "FACILITY" -> facility.findById(key).map(x -> new Asset(x.id(),x.code(),x.nameFr()));
            case "EQUIPMENT" -> equipment.findById(key).map(x -> new Asset(x.id(),x.code(),x.name()));
            case "NODE" -> topologyNode.findById(key).map(x -> new Asset(x.id(),x.code(),x.name()));
            case "CONNECTION" -> topologyConnection.findById(key).map(x -> new Asset(x.id(),x.code(),null));
            default -> java.util.Optional.empty();
        };
    }
}
