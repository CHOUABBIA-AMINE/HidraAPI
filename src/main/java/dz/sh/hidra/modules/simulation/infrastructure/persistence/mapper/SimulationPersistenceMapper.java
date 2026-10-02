/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper
 *
 * @Description : Maps simulation domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.simulation.domain.model.*;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.entity.*;

/**
 * Maps simulation domain models to JPA entities.
 */
public final class SimulationPersistenceMapper {

    private SimulationPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static SimulationModelJpaEntity toEntity(SimulationModel model) {
            return new SimulationModelJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.modelTypeId(),
                        model.topologyScopeType(),
                        model.topologyScopeId(),
                        model.status(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static SimulationModel toDomain(SimulationModelJpaEntity entity) {
            return new SimulationModel(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.modelTypeId(),
                        entity.topologyScopeType(),
                        entity.topologyScopeId(),
                        entity.status(),
                        entity.description(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }
        public static SimulationScenarioJpaEntity toEntity(SimulationScenario model) {
            return new SimulationScenarioJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.scenarioTypeId(),
                        model.modelId(),
                        model.modelVersionId(),
                        model.topologySnapshotId(),
                        model.planningReferenceId(),
                        model.monitoringContextId(),
                        model.status(),
                        model.createdByActorId(),
                        model.createdByDisplayNameSnapshot(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static SimulationScenario toDomain(SimulationScenarioJpaEntity entity) {
            return new SimulationScenario(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.scenarioTypeId(),
                        entity.modelId(),
                        entity.modelVersionId(),
                        entity.topologySnapshotId(),
                        entity.planningReferenceId(),
                        entity.monitoringContextId(),
                        entity.status(),
                        entity.createdByActorId(),
                        entity.createdByDisplayNameSnapshot(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }
        public static SimulationRunJpaEntity toEntity(SimulationRun model) {
            return new SimulationRunJpaEntity(
                        model.id(),
                        model.scenarioId(),
                        model.modelVersionId(),
                        model.inputSnapshotId(),
                        model.runTypeId(),
                        model.status(),
                        model.requestedByActorId(),
                        model.requestedByDisplayNameSnapshot(),
                        model.queuedAt(),
                        model.startedAt(),
                        model.completedAt(),
                        model.durationMillis(),
                        model.solverProfileId(),
                        model.correlationId(),
                        model.failureReason(),
                        model.createdAt()
            );
        }

        public static SimulationRun toDomain(SimulationRunJpaEntity entity) {
            return new SimulationRun(
                        entity.id(),
                        entity.scenarioId(),
                        entity.modelVersionId(),
                        entity.inputSnapshotId(),
                        entity.runTypeId(),
                        entity.status(),
                        entity.requestedByActorId(),
                        entity.requestedByDisplayNameSnapshot(),
                        entity.queuedAt(),
                        entity.startedAt(),
                        entity.completedAt(),
                        entity.durationMillis(),
                        entity.solverProfileId(),
                        entity.correlationId(),
                        entity.failureReason(),
                        entity.createdAt()
            );
        }
        public static SimulationOptimizationCandidateJpaEntity toEntity(SimulationOptimizationCandidate model) {
            return new SimulationOptimizationCandidateJpaEntity(
                        model.id(),
                        model.runId(),
                        model.candidateNumber(),
                        model.candidateStatus(),
                        model.feasible(),
                        model.objectiveScore(),
                        model.rank(),
                        model.summaryText(),
                        model.selectedByActorId(),
                        model.selectedAt(),
                        model.createdAt()
            );
        }

        public static SimulationOptimizationCandidate toDomain(SimulationOptimizationCandidateJpaEntity entity) {
            return new SimulationOptimizationCandidate(
                        entity.id(),
                        entity.runId(),
                        entity.candidateNumber(),
                        entity.candidateStatus(),
                        entity.feasible(),
                        entity.objectiveScore(),
                        entity.rank(),
                        entity.summaryText(),
                        entity.selectedByActorId(),
                        entity.selectedAt(),
                        entity.createdAt()
            );
        }

        public static SimulationCandidateChangeJpaEntity toEntity(SimulationCandidateChange model) {
            return new SimulationCandidateChangeJpaEntity(
                        model.id(),
                        model.candidateId(),
                        model.changeTypeId(),
                        model.targetType(),
                        model.targetId(),
                        model.beforeValue(),
                        model.afterValue(),
                        model.unitCode(),
                        model.requiresTopologyChange(),
                        model.requiresOperationalProcedure(),
                        model.safetyCritical(),
                        model.explanation(),
                        model.createdAt()
            );
        }

        public static SimulationCandidateChange toDomain(SimulationCandidateChangeJpaEntity entity) {
            return new SimulationCandidateChange(
                        entity.id(),
                        entity.candidateId(),
                        entity.changeTypeId(),
                        entity.targetType(),
                        entity.targetId(),
                        entity.beforeValue(),
                        entity.afterValue(),
                        entity.unitCode(),
                        entity.requiresTopologyChange(),
                        entity.requiresOperationalProcedure(),
                        entity.safetyCritical(),
                        entity.explanation(),
                        entity.createdAt()
            );
        }
        public static SimulationRecommendationJpaEntity toEntity(SimulationRecommendation model) {
            return new SimulationRecommendationJpaEntity(
                        model.id(),
                        model.runId(),
                        model.candidateId(),
                        model.recommendationTypeId(),
                        model.recommendationStatus(),
                        model.title(),
                        model.description(),
                        model.confidenceLevelId(),
                        model.targetModule(),
                        model.targetProposalReference(),
                        model.publishedByActorId(),
                        model.publishedAt(),
                        model.createdAt()
            );
        }

        public static SimulationRecommendation toDomain(SimulationRecommendationJpaEntity entity) {
            return new SimulationRecommendation(
                        entity.id(),
                        entity.runId(),
                        entity.candidateId(),
                        entity.recommendationTypeId(),
                        entity.recommendationStatus(),
                        entity.title(),
                        entity.description(),
                        entity.confidenceLevelId(),
                        entity.targetModule(),
                        entity.targetProposalReference(),
                        entity.publishedByActorId(),
                        entity.publishedAt(),
                        entity.createdAt()
            );
        }
}
