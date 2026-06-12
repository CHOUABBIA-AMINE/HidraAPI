/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.service
 *
 * @Description : Application service for simulation models, scenarios, runs, and recommendations.
 *
 */
package dz.sh.hidra.modules.simulation.application.service;

import dz.sh.hidra.modules.simulation.application.command.CreateSimulationModelCommand;
import dz.sh.hidra.modules.simulation.application.command.CreateSimulationScenarioCommand;
import dz.sh.hidra.modules.simulation.application.command.PublishSimulationRecommendationCommand;
import dz.sh.hidra.modules.simulation.application.command.QueueSimulationRunCommand;
import dz.sh.hidra.modules.simulation.application.dto.SimulationModelSummaryDto;
import dz.sh.hidra.modules.simulation.application.dto.SimulationRecommendationSummaryDto;
import dz.sh.hidra.modules.simulation.application.dto.SimulationRunSummaryDto;
import dz.sh.hidra.modules.simulation.application.dto.SimulationScenarioSummaryDto;
import dz.sh.hidra.modules.simulation.application.mapper.SimulationApplicationMapper;
import dz.sh.hidra.modules.simulation.application.port.in.CreateSimulationModelUseCase;
import dz.sh.hidra.modules.simulation.application.port.in.CreateSimulationScenarioUseCase;
import dz.sh.hidra.modules.simulation.application.port.in.PublishSimulationRecommendationUseCase;
import dz.sh.hidra.modules.simulation.application.port.in.QueueSimulationRunUseCase;
import dz.sh.hidra.modules.simulation.application.port.out.SimulationModelRepositoryPort;
import dz.sh.hidra.modules.simulation.application.port.out.SimulationRecommendationRepositoryPort;
import dz.sh.hidra.modules.simulation.application.port.out.SimulationRunRepositoryPort;
import dz.sh.hidra.modules.simulation.application.port.out.SimulationScenarioRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.model.SimulationModel;
import dz.sh.hidra.modules.simulation.domain.model.SimulationRecommendation;
import dz.sh.hidra.modules.simulation.domain.model.SimulationRun;
import dz.sh.hidra.modules.simulation.domain.model.SimulationScenario;
import dz.sh.hidra.modules.simulation.domain.value.SimulationId;
import dz.sh.hidra.modules.simulation.domain.value.SimulationModelStatus;
import dz.sh.hidra.modules.simulation.domain.value.SimulationRecommendationStatus;
import dz.sh.hidra.modules.simulation.domain.value.SimulationRunStatus;
import dz.sh.hidra.modules.simulation.domain.value.SimulationScenarioStatus;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for simulation models, scenarios, runs, and recommendations.
 */
public final class SimulationApplicationService implements CreateSimulationModelUseCase, CreateSimulationScenarioUseCase, QueueSimulationRunUseCase, PublishSimulationRecommendationUseCase {

    private final SimulationModelRepositoryPort modelRepositoryPort;
    private final SimulationScenarioRepositoryPort scenarioRepositoryPort;
    private final SimulationRunRepositoryPort runRepositoryPort;
    private final SimulationRecommendationRepositoryPort recommendationRepositoryPort;

    public SimulationApplicationService(
            SimulationModelRepositoryPort modelRepositoryPort,
            SimulationScenarioRepositoryPort scenarioRepositoryPort,
            SimulationRunRepositoryPort runRepositoryPort,
            SimulationRecommendationRepositoryPort recommendationRepositoryPort
    ) {
        this.modelRepositoryPort = Objects.requireNonNull(modelRepositoryPort, "Simulation model repository port must not be null.");
        this.scenarioRepositoryPort = Objects.requireNonNull(scenarioRepositoryPort, "Simulation scenario repository port must not be null.");
        this.runRepositoryPort = Objects.requireNonNull(runRepositoryPort, "Simulation run repository port must not be null.");
        this.recommendationRepositoryPort = Objects.requireNonNull(recommendationRepositoryPort, "Simulation recommendation repository port must not be null.");
    }

    @Override
    public SimulationModelSummaryDto createSimulationModel(CreateSimulationModelCommand command) {
        Objects.requireNonNull(command, "Create simulation model command must not be null.");
        Instant now = Instant.now();
        SimulationModel model = new SimulationModel(
                SimulationId.newId().value(),
                command.code(),
                command.nameAr(),
                command.nameFr(),
                command.nameEn(),
                command.modelTypeId(),
                command.topologyScopeType(),
                command.topologyScopeId(),
                SimulationModelStatus.DRAFT,
                command.description(),
                now,
                now
        );
        return SimulationApplicationMapper.toSummary(modelRepositoryPort.save(model));
    }

    @Override
    public SimulationScenarioSummaryDto createSimulationScenario(CreateSimulationScenarioCommand command) {
        Objects.requireNonNull(command, "Create simulation scenario command must not be null.");
        Instant now = Instant.now();
        SimulationScenario scenario = new SimulationScenario(
                SimulationId.newId().value(),
                command.code(),
                command.nameAr(),
                command.nameFr(),
                command.nameEn(),
                command.scenarioTypeId(),
                command.modelId(),
                command.modelVersionId(),
                command.topologySnapshotId(),
                command.planningReferenceId(),
                command.monitoringContextId(),
                SimulationScenarioStatus.DRAFT,
                command.createdByActorId(),
                command.createdByDisplayNameSnapshot(),
                now,
                now
        );
        return SimulationApplicationMapper.toSummary(scenarioRepositoryPort.save(scenario));
    }

    @Override
    public SimulationRunSummaryDto queueSimulationRun(QueueSimulationRunCommand command) {
        Objects.requireNonNull(command, "Queue simulation run command must not be null.");
        Instant now = Instant.now();
        SimulationRun run = new SimulationRun(
                SimulationId.newId().value(),
                command.scenarioId(),
                command.modelVersionId(),
                command.inputSnapshotId(),
                command.runTypeId(),
                SimulationRunStatus.QUEUED,
                command.requestedByActorId(),
                command.requestedByDisplayNameSnapshot(),
                now,
                null,
                null,
                null,
                command.solverProfileId(),
                command.correlationId(),
                null,
                now
        );
        return SimulationApplicationMapper.toSummary(runRepositoryPort.save(run));
    }

    @Override
    public SimulationRecommendationSummaryDto publishSimulationRecommendation(PublishSimulationRecommendationCommand command) {
        Objects.requireNonNull(command, "Publish simulation recommendation command must not be null.");
        Instant now = Instant.now();
        SimulationRecommendation recommendation = new SimulationRecommendation(
                SimulationId.newId().value(),
                command.runId(),
                command.candidateId(),
                command.recommendationTypeId(),
                SimulationRecommendationStatus.PUBLISHED,
                command.title(),
                command.description(),
                command.confidenceLevelId(),
                command.targetModule(),
                command.targetProposalReference(),
                command.publishedByActorId(),
                now,
                now
        );
        return SimulationApplicationMapper.toSummary(recommendationRepositoryPort.save(recommendation));
    }
}
