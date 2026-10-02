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
import dz.sh.hidra.modules.simulation.api.rest.request.PublishSimulationRecommendationRequest;
import dz.sh.hidra.modules.simulation.api.rest.request.QueueSimulationRunRequest;
import dz.sh.hidra.modules.simulation.api.rest.response.SimulationModelResponse;
import dz.sh.hidra.modules.simulation.api.rest.response.SimulationRecommendationResponse;
import dz.sh.hidra.modules.simulation.api.rest.response.SimulationRunResponse;
import dz.sh.hidra.modules.simulation.api.rest.response.SimulationScenarioResponse;
import dz.sh.hidra.modules.simulation.application.port.in.CreateSimulationModelUseCase;
import dz.sh.hidra.modules.simulation.application.port.in.CreateSimulationScenarioUseCase;
import dz.sh.hidra.modules.simulation.application.port.in.PublishSimulationRecommendationUseCase;
import dz.sh.hidra.modules.simulation.application.port.in.QueueSimulationRunUseCase;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring MVC adapter exposing simulation REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/simulation")
public class SpringSimulationController implements SimulationController {

    private final CreateSimulationModelUseCase createSimulationModelUseCase;
    private final CreateSimulationScenarioUseCase createSimulationScenarioUseCase;
    private final PublishSimulationRecommendationUseCase publishSimulationRecommendationUseCase;
    private final QueueSimulationRunUseCase queueSimulationRunUseCase;

    public SpringSimulationController(
            CreateSimulationModelUseCase createSimulationModelUseCase,
            CreateSimulationScenarioUseCase createSimulationScenarioUseCase,
            PublishSimulationRecommendationUseCase publishSimulationRecommendationUseCase,
            QueueSimulationRunUseCase queueSimulationRunUseCase
    ) {
        this.createSimulationModelUseCase = Objects.requireNonNull(createSimulationModelUseCase, "CreateSimulationModelUseCase must not be null.");
        this.createSimulationScenarioUseCase = Objects.requireNonNull(createSimulationScenarioUseCase, "CreateSimulationScenarioUseCase must not be null.");
        this.publishSimulationRecommendationUseCase = Objects.requireNonNull(publishSimulationRecommendationUseCase, "PublishSimulationRecommendationUseCase must not be null.");
        this.queueSimulationRunUseCase = Objects.requireNonNull(queueSimulationRunUseCase, "QueueSimulationRunUseCase must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "simulation",
                "mission", "Support simulation models, scenarios, runs, and recommendations for better decisions.",
                "objectives", List.of(
                "Create simulation models.",
                "Create simulation scenarios.",
                "Queue simulation runs.",
                "Publish recommendations."
        ),
                "operations", List.of(
                "createSimulationModel",
                "createSimulationScenario",
                "publishSimulationRecommendation",
                "queueSimulationRun"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/simulation/models",
                "POST /api/v1/simulation/scenarios",
                "POST /api/v1/simulation/recommendations",
                "POST /api/v1/simulation/runs"
        )
        );
    }

    @Override
    @PostMapping({"/create-simulation-model", "/models"})
    public SimulationModelResponse createSimulationModel(@Valid @RequestBody CreateSimulationModelRequest request) {
        Objects.requireNonNull(request, "CreateSimulationModelRequest must not be null.");
        return SimulationRestMapper.toResponse(createSimulationModelUseCase.createSimulationModel(SimulationRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/create-simulation-scenario", "/scenarios"})
    public SimulationScenarioResponse createSimulationScenario(@Valid @RequestBody CreateSimulationScenarioRequest request) {
        Objects.requireNonNull(request, "CreateSimulationScenarioRequest must not be null.");
        return SimulationRestMapper.toResponse(createSimulationScenarioUseCase.createSimulationScenario(SimulationRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/publish-simulation-recommendation", "/recommendations"})
    public SimulationRecommendationResponse publishSimulationRecommendation(@Valid @RequestBody PublishSimulationRecommendationRequest request) {
        Objects.requireNonNull(request, "PublishSimulationRecommendationRequest must not be null.");
        return SimulationRestMapper.toResponse(publishSimulationRecommendationUseCase.publishSimulationRecommendation(SimulationRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/queue-simulation-run", "/runs"})
    public SimulationRunResponse queueSimulationRun(@Valid @RequestBody QueueSimulationRunRequest request) {
        Objects.requireNonNull(request, "QueueSimulationRunRequest must not be null.");
        return SimulationRestMapper.toResponse(queueSimulationRunUseCase.queueSimulationRun(SimulationRestMapper.toCommand(request)));
    }

}
