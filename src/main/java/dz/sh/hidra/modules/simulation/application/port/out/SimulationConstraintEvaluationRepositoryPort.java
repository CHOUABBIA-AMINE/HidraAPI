/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationConstraintEvaluationRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.out
 *
 * @Description : Repository port for SimulationConstraintEvaluation.
 *
 */
package dz.sh.hidra.modules.simulation.application.port.out;

import dz.sh.hidra.modules.simulation.domain.model.SimulationConstraintEvaluation;

import java.util.Optional;

/**
 * Repository port for SimulationConstraintEvaluation.
 */
public interface SimulationConstraintEvaluationRepositoryPort {

    SimulationConstraintEvaluation save(SimulationConstraintEvaluation model);

    Optional<SimulationConstraintEvaluation> findById(String id);
}
