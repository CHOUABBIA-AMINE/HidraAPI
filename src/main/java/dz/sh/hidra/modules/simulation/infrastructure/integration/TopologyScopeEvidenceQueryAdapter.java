/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyScopeEvidenceQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.integration
 *
 * @Description : Preserves scope resolution flags with an explicit missing physical source.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.integration;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationTopologyScopeEvidencePort;
import dz.sh.hidra.modules.topology.application.contract.simulation.SimulationTopologyScopeContract;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public final class TopologyScopeEvidenceQueryAdapter implements SimulationTopologyScopeEvidencePort {
    private final SimulationTopologyScopeContract scopes;

    public TopologyScopeEvidenceQueryAdapter(SimulationTopologyScopeContract scopes) {
        this.scopes = Objects.requireNonNull(scopes, "Topology scope contract must not be null.");
    }

    @Override
    public Optional<ScopeEvidence> resolve(String scopeType, String scopeId) {
        if (scopeType == null || scopeType.isBlank() || scopeId == null || scopeId.isBlank()) {
            return Optional.empty();
        }
        String type = scopeType.trim();
        String id = scopeId.trim();
        var result = Objects.requireNonNull(scopes.resolve(type, id), "Owner scope result must not be null.");
        return Optional.of(new ScopeEvidence(type, id, result.supported(), result.exists(), result.eligible()));
    }
}
