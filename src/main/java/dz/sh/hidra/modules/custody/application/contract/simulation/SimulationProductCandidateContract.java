/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationProductCandidateContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.contract.simulation
 *
 * @Description : Exports raw catalogue candidates without Simulation classification.
 *
 */
package dz.sh.hidra.modules.custody.application.contract.simulation;

import java.time.Instant;
import java.util.Optional;

/** Catalogue evidence for assessment; presence is not Simulation product approval. */
public interface SimulationProductCandidateContract {
    Optional<Candidate> resolve(String id);

    record Candidate(String id, String catalogName, String code, boolean active,
            Instant createdAt, Instant updatedAt) { }
}
