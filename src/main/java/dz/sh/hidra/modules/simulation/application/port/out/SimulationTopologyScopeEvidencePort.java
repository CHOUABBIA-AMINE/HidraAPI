/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationTopologyScopeEvidencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.out
 *
 * @Description : Retains scope eligibility without implying versioned physical topology.
 *
 */
package dz.sh.hidra.modules.simulation.application.port.out;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import java.util.Optional;

public interface SimulationTopologyScopeEvidencePort {
    Optional<ScopeEvidence> resolve(String scopeType, String scopeId);

    enum PhysicalInputStatus { MISSING_VERSIONED_TOPOLOGY_SOURCE }

    /** Scope identity echoes the request; eligibility does not imply an immutable physical revision. */
    record ScopeEvidence(String scopeType, String scopeId, boolean supported, boolean exists, boolean eligible) {
        public ScopeEvidence {
            if (scopeType == null || scopeType.isBlank() || scopeId == null || scopeId.isBlank()) {
                throw new InvalidSimulationValueException("Scope evidence requires type and id.");
            }
            scopeType = scopeType.trim();
            scopeId = scopeId.trim();
            if ((exists && !supported) || (eligible && (!supported || !exists))) {
                throw new InvalidSimulationValueException("Scope evidence flags must be coherent.");
            }
        }

        public PhysicalInputStatus physicalInputStatus() {
            return PhysicalInputStatus.MISSING_VERSIONED_TOPOLOGY_SOURCE;
        }
    }
}
