/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : QueueSimulationRunUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.in
 *
 * @Description : Use case for queuing simulation runs.
 *
 */
package dz.sh.hidra.modules.simulation.application.port.in;

import dz.sh.hidra.modules.simulation.application.command.QueueSimulationRunCommand;
import dz.sh.hidra.modules.simulation.application.dto.SimulationRunSummaryDto;

/**
 * Use case for queuing simulation runs.
 */
public interface QueueSimulationRunUseCase {

    SimulationRunSummaryDto queueSimulationRun(QueueSimulationRunCommand command);
}
