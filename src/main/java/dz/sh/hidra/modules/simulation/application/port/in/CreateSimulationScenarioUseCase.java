/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateSimulationScenarioUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.in
 *
 * @Description : Use case for creating simulation scenarios.
 *
 */
package dz.sh.hidra.modules.simulation.application.port.in;

import dz.sh.hidra.modules.simulation.application.command.CreateSimulationScenarioCommand;
import dz.sh.hidra.modules.simulation.application.dto.SimulationScenarioSummaryDto;

/**
 * Use case for creating simulation scenarios.
 */
public interface CreateSimulationScenarioUseCase {

    SimulationScenarioSummaryDto createSimulationScenario(CreateSimulationScenarioCommand command);
}
