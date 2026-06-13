/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringSimulationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing simulation REST endpoints.
 *
 */
package dz.sh.hidra.modules.simulation.api.rest.controller;

import dz.sh.hidra.modules.simulation.api.rest.mapper.SimulationRestMapper;
import dz.sh.hidra.modules.simulation.api.rest.request.CreateSimulationModelRequest;
import dz.sh.hidra.modules.simulation.api.rest.request.CreateSimulationScenarioRequest;
import dz.sh.hidra.modules.simulation.api.rest.response.SimulationModelResponse;
import dz.sh.hidra.modules.simulation.api.rest.response.SimulationScenarioResponse;
import dz.sh.hidra.modules.simulation.application.port.in.CreateSimulationModelUseCase;
import dz.sh.hidra.modules.simulation.application.port.in.CreateSimulationScenarioUseCase;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * Spring MVC adapter exposing simulation REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/simulation")
public class SpringSimulationController implements SimulationController {

    private final CreateSimulationModelUseCase createSimulationModelUseCase;
    private final CreateSimulationScenarioUseCase createSimulationScenarioUseCase;

    public SpringSimulationController(
            CreateSimulationModelUseCase createSimulationModelUseCase,
            CreateSimulationScenarioUseCase createSimulationScenarioUseCase
    ) {
        this.createSimulationModelUseCase = Objects.requireNonNull(createSimulationModelUseCase, "CreateSimulationModelUseCase must not be null.");
        this.createSimulationScenarioUseCase = Objects.requireNonNull(createSimulationScenarioUseCase, "CreateSimulationScenarioUseCase must not be null.");
    }


    @Override
    @PostMapping("/create-simulation-model")
    public SimulationModelResponse createSimulationModel(@Valid @RequestBody CreateSimulationModelRequest request) {
        Objects.requireNonNull(request, "CreateSimulationModelRequest must not be null.");
        return SimulationRestMapper.toResponse(createSimulationModelUseCase.createSimulationModel(SimulationRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping("/create-simulation-scenario")
    public SimulationScenarioResponse createSimulationScenario(@Valid @RequestBody CreateSimulationScenarioRequest request) {
        Objects.requireNonNull(request, "CreateSimulationScenarioRequest must not be null.");
        return SimulationRestMapper.toResponse(createSimulationScenarioUseCase.createSimulationScenario(SimulationRestMapper.toCommand(request)));
    }

}
