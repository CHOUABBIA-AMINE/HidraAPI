/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
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

/**
 * Maps simulation REST models to application models.
 */
public final class SimulationRestMapper {

    private SimulationRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateSimulationModelCommand toCommand(CreateSimulationModelRequest request) {
        return new CreateSimulationModelCommand(
                request.code(),
                request.nameAr(),
                request.nameFr(),
                request.nameEn(),
                request.modelTypeId(),
                request.topologyScopeType(),
                request.topologyScopeId(),
                request.description()
        );
    }

    public static CreateSimulationScenarioCommand toCommand(CreateSimulationScenarioRequest request) {
        return new CreateSimulationScenarioCommand(
                request.code(),
                request.nameAr(),
                request.nameFr(),
                request.nameEn(),
                request.scenarioTypeId(),
                request.modelId(),
                request.modelVersionId(),
                request.topologySnapshotId(),
                request.planningReferenceId(),
                request.monitoringContextId(),
                request.createdByActorId(),
                request.createdByDisplayNameSnapshot()
        );
    }

    public static PublishSimulationRecommendationCommand toCommand(PublishSimulationRecommendationRequest request) {
        return new PublishSimulationRecommendationCommand(
                request.runId(),
                request.candidateId(),
                request.recommendationTypeId(),
                request.title(),
                request.description(),
                request.confidenceLevelId(),
                request.targetModule(),
                request.targetProposalReference(),
                request.publishedByActorId()
        );
    }

    public static QueueSimulationRunCommand toCommand(QueueSimulationRunRequest request) {
        return new QueueSimulationRunCommand(
                request.scenarioId(),
                request.modelVersionId(),
                request.inputSnapshotId(),
                request.runTypeId(),
                request.requestedByActorId(),
                request.requestedByDisplayNameSnapshot(),
                request.solverProfileId(),
                request.correlationId()
        );
    }

    public static SimulationModelResponse toResponse(SimulationModelSummaryDto dto) {
        return new SimulationModelResponse(
                dto.id(),
                dto.code(),
                dto.nameFr(),
                dto.modelTypeId(),
                dto.topologyScopeType(),
                dto.status(),
                dto.createdAt()
        );
    }

    public static SimulationScenarioResponse toResponse(SimulationScenarioSummaryDto dto) {
        return new SimulationScenarioResponse(
                dto.id(),
                dto.code(),
                dto.nameFr(),
                dto.scenarioTypeId(),
                dto.modelId(),
                dto.modelVersionId(),
                dto.topologySnapshotId(),
                dto.status(),
                dto.createdAt()
        );
    }

    public static SimulationRecommendationResponse toResponse(SimulationRecommendationSummaryDto dto) {
        return new SimulationRecommendationResponse(
                dto.id(),
                dto.runId(),
                dto.candidateId(),
                dto.recommendationTypeId(),
                dto.recommendationStatus(),
                dto.title(),
                dto.targetModule(),
                dto.targetProposalReference(),
                dto.publishedAt()
        );
    }

    public static SimulationRunResponse toResponse(SimulationRunSummaryDto dto) {
        return new SimulationRunResponse(
                dto.id(),
                dto.scenarioId(),
                dto.modelVersionId(),
                dto.inputSnapshotId(),
                dto.runTypeId(),
                dto.status(),
                dto.correlationId(),
                dto.queuedAt(),
                dto.completedAt()
        );
    }
}
