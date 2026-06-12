/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationApplicationMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.mapper
 *
 * @Description : Maps simulation domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.simulation.application.mapper;

import dz.sh.hidra.modules.simulation.application.dto.SimulationModelSummaryDto;
import dz.sh.hidra.modules.simulation.application.dto.SimulationRecommendationSummaryDto;
import dz.sh.hidra.modules.simulation.application.dto.SimulationRunSummaryDto;
import dz.sh.hidra.modules.simulation.application.dto.SimulationScenarioSummaryDto;
import dz.sh.hidra.modules.simulation.domain.model.SimulationModel;
import dz.sh.hidra.modules.simulation.domain.model.SimulationRecommendation;
import dz.sh.hidra.modules.simulation.domain.model.SimulationRun;
import dz.sh.hidra.modules.simulation.domain.model.SimulationScenario;

/**
 * Maps simulation domain models to DTOs.
 */
public final class SimulationApplicationMapper {

    private SimulationApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static SimulationModelSummaryDto toSummary(SimulationModel model) {
        return new SimulationModelSummaryDto(model.id(), model.code(), model.nameFr(), model.modelTypeId(), model.topologyScopeType(), model.status(), model.createdAt());
    }

    public static SimulationScenarioSummaryDto toSummary(SimulationScenario scenario) {
        return new SimulationScenarioSummaryDto(scenario.id(), scenario.code(), scenario.nameFr(), scenario.scenarioTypeId(), scenario.modelId(), scenario.modelVersionId(), scenario.topologySnapshotId(), scenario.status(), scenario.createdAt());
    }

    public static SimulationRunSummaryDto toSummary(SimulationRun run) {
        return new SimulationRunSummaryDto(run.id(), run.scenarioId(), run.modelVersionId(), run.inputSnapshotId(), run.runTypeId(), run.status(), run.correlationId(), run.queuedAt(), run.completedAt());
    }

    public static SimulationRecommendationSummaryDto toSummary(SimulationRecommendation recommendation) {
        return new SimulationRecommendationSummaryDto(recommendation.id(), recommendation.runId(), recommendation.candidateId(), recommendation.recommendationTypeId(), recommendation.recommendationStatus(), recommendation.title(), recommendation.targetModule(), recommendation.targetProposalReference(), recommendation.publishedAt());
    }
}
