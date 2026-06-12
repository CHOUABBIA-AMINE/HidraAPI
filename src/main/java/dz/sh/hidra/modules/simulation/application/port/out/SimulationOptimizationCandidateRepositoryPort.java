/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationOptimizationCandidateRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.out
 *
 * @Description : Repository port for SimulationOptimizationCandidate.
 *
 */
package dz.sh.hidra.modules.simulation.application.port.out;

import dz.sh.hidra.modules.simulation.domain.model.SimulationOptimizationCandidate;

import java.util.Optional;

/**
 * Repository port for SimulationOptimizationCandidate.
 */
public interface SimulationOptimizationCandidateRepositoryPort {

    SimulationOptimizationCandidate save(SimulationOptimizationCandidate model);

    Optional<SimulationOptimizationCandidate> findById(String id);
}
