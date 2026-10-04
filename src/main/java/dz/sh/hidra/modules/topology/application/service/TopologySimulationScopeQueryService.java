/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologySimulationScopeQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Topology-owned Simulation scope resolver.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.contract.simulation.SimulationTopologyScopeContract;
import dz.sh.hidra.modules.topology.application.port.out.PipelineRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSystemRepositoryPort;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public final class TopologySimulationScopeQueryService implements SimulationTopologyScopeContract {

    private final PipelineSystemRepositoryPort pipelineSystemRepositoryPort;
    private final PipelineRepositoryPort pipelineRepositoryPort;

    public TopologySimulationScopeQueryService(
            PipelineSystemRepositoryPort pipelineSystemRepositoryPort,
            PipelineRepositoryPort pipelineRepositoryPort
    ) {
        this.pipelineSystemRepositoryPort = Objects.requireNonNull(
                pipelineSystemRepositoryPort,
                "PipelineSystemRepositoryPort must not be null."
        );
        this.pipelineRepositoryPort = Objects.requireNonNull(
                pipelineRepositoryPort,
                "PipelineRepositoryPort must not be null."
        );
    }

    @Override
    public ScopeResolution resolve(String scopeType, String scopeId) {
        if (scopeType == null || scopeType.isBlank() || scopeId == null || scopeId.isBlank()) {
            return ScopeResolution.missing();
        }
        String normalizedType = scopeType.trim();
        String normalizedId = scopeId.trim();

        return switch (normalizedType) {
            case "PIPELINE_SYSTEM" -> pipelineSystemRepositoryPort.findById(normalizedId)
                    .map(model -> ScopeResolution.resolved(model.status() == TopologyStatus.ACTIVE))
                    .orElseGet(ScopeResolution::missing);
            case "PIPELINE" -> pipelineRepositoryPort.findById(normalizedId)
                    .map(model -> ScopeResolution.resolved(model.status() == TopologyStatus.ACTIVE))
                    .orElseGet(ScopeResolution::missing);
            case "SEGMENT_GROUP", "FACILITY_NETWORK" -> ScopeResolution.unsupported();
            default -> ScopeResolution.unsupported();
        };
    }
}
