/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.api.rest.mapper
 *
 * @Description : Maps simulation REST models to application models.
 *
 */
package dz.sh.hidra.modules.simulation.api.rest.mapper;
import dz.sh.hidra.modules.simulation.api.rest.request.CreateSimulationModelRequest;
import dz.sh.hidra.modules.simulation.api.rest.request.CreateSimulationScenarioRequest;
import dz.sh.hidra.modules.simulation.api.rest.request.PublishSimulationRecommendationRequest;
import dz.sh.hidra.modules.simulation.api.rest.request.QueueSimulationRunRequest;
import dz.sh.hidra.modules.simulation.api.rest.response.SimulationModelResponse;
import dz.sh.hidra.modules.simulation.api.rest.response.SimulationRecommendationResponse;
import dz.sh.hidra.modules.simulation.api.rest.response.SimulationRunResponse;
import dz.sh.hidra.modules.simulation.api.rest.response.SimulationScenarioResponse;
import dz.sh.hidra.modules.simulation.application.command.CreateSimulationModelCommand;
import dz.sh.hidra.modules.simulation.application.command.CreateSimulationScenarioCommand;
import dz.sh.hidra.modules.simulation.application.command.PublishSimulationRecommendationCommand;
import dz.sh.hidra.modules.simulation.application.command.QueueSimulationRunCommand;
import dz.sh.hidra.modules.simulation.application.dto.SimulationModelSummaryDto;
import dz.sh.hidra.modules.simulation.application.dto.SimulationRecommendationSummaryDto;
import dz.sh.hidra.modules.simulation.application.dto.SimulationRunSummaryDto;
import dz.sh.hidra.modules.simulation.application.dto.SimulationScenarioSummaryDto;
import java.util.Objects;

/**
 * Maps simulation REST models to application models.
 */
public final class SimulationRestMapper {

    private static final SimulationGeneratedRestMapper GENERATED = SimulationGeneratedRestMapper.INSTANCE;

    private SimulationRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateSimulationModelCommand toCommand(CreateSimulationModelRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateSimulationModelRequest must not be null."));
    }

    public static CreateSimulationScenarioCommand toCommand(CreateSimulationScenarioRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateSimulationScenarioRequest must not be null."));
    }

    public static PublishSimulationRecommendationCommand toCommand(PublishSimulationRecommendationRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "PublishSimulationRecommendationRequest must not be null."));
    }

    public static QueueSimulationRunCommand toCommand(QueueSimulationRunRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "QueueSimulationRunRequest must not be null."));
    }

    public static SimulationModelResponse toResponse(SimulationModelSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "SimulationModelSummaryDto must not be null."));
    }

    public static SimulationScenarioResponse toResponse(SimulationScenarioSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "SimulationScenarioSummaryDto must not be null."));
    }

    public static SimulationRecommendationResponse toResponse(SimulationRecommendationSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "SimulationRecommendationSummaryDto must not be null."));
    }

    public static SimulationRunResponse toResponse(SimulationRunSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "SimulationRunSummaryDto must not be null."));
    }
}
