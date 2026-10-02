/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.api.rest.controller
 *
 * @Description : Framework-neutral simulation controller contract.
 *
 */
package dz.sh.hidra.modules.simulation.api.rest.controller;
import dz.sh.hidra.modules.simulation.api.rest.request.*;
import dz.sh.hidra.modules.simulation.api.rest.response.*;

/**
 * Framework-neutral simulation controller contract.
 */
public interface SimulationController {
    SimulationModelResponse createSimulationModel(CreateSimulationModelRequest request);
    SimulationScenarioResponse createSimulationScenario(CreateSimulationScenarioRequest request);
    SimulationRecommendationResponse publishSimulationRecommendation(PublishSimulationRecommendationRequest request);
    SimulationRunResponse queueSimulationRun(QueueSimulationRunRequest request);
}
