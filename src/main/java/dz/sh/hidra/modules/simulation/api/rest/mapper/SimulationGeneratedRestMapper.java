/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.api.rest.mapper
 *
 * @Description : Generates exact simulation API/application boundary mappings at compile time.
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
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact simulation boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface SimulationGeneratedRestMapper {

    SimulationGeneratedRestMapper INSTANCE = Mappers.getMapper(SimulationGeneratedRestMapper.class);

    CreateSimulationModelCommand toCommand(CreateSimulationModelRequest request);

    CreateSimulationScenarioCommand toCommand(CreateSimulationScenarioRequest request);

    PublishSimulationRecommendationCommand toCommand(PublishSimulationRecommendationRequest request);

    QueueSimulationRunCommand toCommand(QueueSimulationRunRequest request);

    SimulationModelResponse toResponse(SimulationModelSummaryDto dto);

    SimulationRecommendationResponse toResponse(SimulationRecommendationSummaryDto dto);

    SimulationRunResponse toResponse(SimulationRunSummaryDto dto);

    SimulationScenarioResponse toResponse(SimulationScenarioSummaryDto dto);
}
