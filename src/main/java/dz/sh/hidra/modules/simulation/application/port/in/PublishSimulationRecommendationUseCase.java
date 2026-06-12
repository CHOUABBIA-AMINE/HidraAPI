/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PublishSimulationRecommendationUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.in
 *
 * @Description : Use case for publishing simulation recommendations.
 *
 */
package dz.sh.hidra.modules.simulation.application.port.in;

import dz.sh.hidra.modules.simulation.application.command.PublishSimulationRecommendationCommand;
import dz.sh.hidra.modules.simulation.application.dto.SimulationRecommendationSummaryDto;

/**
 * Use case for publishing simulation recommendations.
 */
public interface PublishSimulationRecommendationUseCase {

    SimulationRecommendationSummaryDto publishSimulationRecommendation(PublishSimulationRecommendationCommand command);
}
