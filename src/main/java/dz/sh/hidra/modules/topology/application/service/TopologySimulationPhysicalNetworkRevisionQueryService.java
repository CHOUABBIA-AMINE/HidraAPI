/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologySimulationPhysicalNetworkRevisionQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Maps verified exact owner revisions into the public Simulation query contract.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.contract.simulation.SimulationPhysicalNetworkRevisionContract;
import dz.sh.hidra.modules.topology.application.port.out.TopologyPhysicalNetworkRevisionRepositoryPort;
import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TopologySimulationPhysicalNetworkRevisionQueryService implements SimulationPhysicalNetworkRevisionContract {
    private final TopologyPhysicalNetworkRevisionRepositoryPort repository;

    public TopologySimulationPhysicalNetworkRevisionQueryService(TopologyPhysicalNetworkRevisionRepositoryPort repository) {
        this.repository = Objects.requireNonNull(repository, "Topology physical revision repository");
    }

    @Override
    public Optional<Revision> find(String sourceId, String revisionId) {
        return repository.findStored(identity(sourceId), identity(revisionId)).map(stored -> {
            var value = stored.revision();
            return new Revision(value.sourceId(), value.revisionId(), value.scopeType().name(), value.scopeId(),
                    value.recordedAt(), value.effectiveFrom(), value.effectiveUntil(), value.origin().name(),
                    value.evidenceReference(), value.nodes().stream()
                            .map(n -> new Node(n.id(), n.elevationMeters())).toList(),
                    value.pipeSegments().stream().map(p -> new PipeSegment(p.id(), p.fromNodeId(), p.toNodeId(),
                            p.lengthMeters(), p.internalDiameterMeters(), p.absoluteRoughnessMeters())).toList(),
                    value.equipmentLinks().stream()
                            .map(e -> new EquipmentLink(e.id(), e.fromNodeId(), e.toNodeId(), e.kind().name())).toList(),
                    stored.payloadFormat(), stored.sha256());
        });
    }

    private static String identity(String value) {
        if (value == null || value.isBlank() || value.trim().isEmpty()) {
            throw new InvalidTopologyValueException("Physical revision lookup identity must not be blank.");
        }
        return value.trim();
    }
}
