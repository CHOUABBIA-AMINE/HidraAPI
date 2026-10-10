/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyStoredPhysicalNetworkRevisionQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.integration
 *
 * @Description : Maintains immutable governed equipment source evidence.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.integration;

import dz.sh.hidra.modules.topology.application.contract.simulation.SimulationPhysicalNetworkRevisionContract;
import dz.sh.hidra.modules.simulation.application.port.out.SimulationStoredPhysicalNetworkRevisionPort;
import java.time.Instant;
import java.util.Optional;
import org.springframework.stereotype.Component;
@Component
public class TopologyStoredPhysicalNetworkRevisionQueryAdapter implements SimulationStoredPhysicalNetworkRevisionPort {
    private final SimulationPhysicalNetworkRevisionContract owner;
    public TopologyStoredPhysicalNetworkRevisionQueryAdapter(SimulationPhysicalNetworkRevisionContract owner){this.owner=owner;}
    @Override public Optional<Revision> find(String source,String revision){return owner.find(source,revision).map(this::map);}
    private SimulationStoredPhysicalNetworkRevisionPort.Revision map(SimulationPhysicalNetworkRevisionContract.Revision v){return new SimulationStoredPhysicalNetworkRevisionPort.Revision(v.sourceId(),v.revisionId(),v.scopeType(),v.scopeId(),v.recordedAt(),v.effectiveFrom(),v.effectiveUntil(),v.origin(),v.evidenceReference(),v.nodes() .stream().map(this::map).toList(),v.pipeSegments() .stream().map(this::map).toList(),v.equipmentLinks() .stream().map(this::map).toList(),v.payloadFormat(),v.sha256());}
    private SimulationStoredPhysicalNetworkRevisionPort.Node map(SimulationPhysicalNetworkRevisionContract.Node v){return new SimulationStoredPhysicalNetworkRevisionPort.Node(v.id(),v.elevationMeters());}
    private SimulationStoredPhysicalNetworkRevisionPort.PipeSegment map(SimulationPhysicalNetworkRevisionContract.PipeSegment v){return new SimulationStoredPhysicalNetworkRevisionPort.PipeSegment(v.id(),v.fromNodeId(),v.toNodeId(),v.lengthMeters(),v.internalDiameterMeters(),v.absoluteRoughnessMeters());}
    private SimulationStoredPhysicalNetworkRevisionPort.EquipmentLink map(SimulationPhysicalNetworkRevisionContract.EquipmentLink v){return new SimulationStoredPhysicalNetworkRevisionPort.EquipmentLink(v.id(),v.fromNodeId(),v.toNodeId(),v.kind());}
}
