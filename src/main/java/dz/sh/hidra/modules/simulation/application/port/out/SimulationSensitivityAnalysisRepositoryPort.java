/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationSensitivityAnalysisRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.out
 *
 * @Description : Repository port for SimulationSensitivityAnalysis.
 *
 */
package dz.sh.hidra.modules.simulation.application.port.out;

import dz.sh.hidra.modules.simulation.domain.model.SimulationSensitivityAnalysis;

import java.util.Optional;

/**
 * Repository port for SimulationSensitivityAnalysis.
 */
public interface SimulationSensitivityAnalysisRepositoryPort {

    SimulationSensitivityAnalysis save(SimulationSensitivityAnalysis model);

    Optional<SimulationSensitivityAnalysis> findById(String id);
}
