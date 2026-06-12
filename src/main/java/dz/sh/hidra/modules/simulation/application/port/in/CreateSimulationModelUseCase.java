/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateSimulationModelUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.in
 *
 * @Description : Use case for creating simulation models.
 *
 */
package dz.sh.hidra.modules.simulation.application.port.in;

import dz.sh.hidra.modules.simulation.application.command.CreateSimulationModelCommand;
import dz.sh.hidra.modules.simulation.application.dto.SimulationModelSummaryDto;

/**
 * Use case for creating simulation models.
 */
public interface CreateSimulationModelUseCase {

    SimulationModelSummaryDto createSimulationModel(CreateSimulationModelCommand command);
}
