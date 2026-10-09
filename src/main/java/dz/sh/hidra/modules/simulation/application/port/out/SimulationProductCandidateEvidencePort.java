/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationProductCandidateEvidencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.out
 *
 * @Description : Retains catalogue evidence with explicit unassessed eligibility and missing fluid input.
 *
 */
package dz.sh.hidra.modules.simulation.application.port.out;

import java.time.Instant;
import java.util.Optional;

public interface SimulationProductCandidateEvidencePort {
    Optional<CandidateEvidence> resolve(String id);

    enum EligibilityStatus { UNASSESSED }
    enum PhysicalInputStatus { MISSING_VERSIONED_FLUID_SOURCE }

    /** Live catalogue evidence has neither qualified product policy nor versioned fluid properties. */
    record CandidateEvidence(String id, String catalogName, String code, boolean active,
            Instant createdAt, Instant updatedAt) {
        public EligibilityStatus simulationEligibility() {
            return EligibilityStatus.UNASSESSED;
        }

        public PhysicalInputStatus physicalInputStatus() {
            return PhysicalInputStatus.MISSING_VERSIONED_FLUID_SOURCE;
        }
    }
}
