/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationPersistenceMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
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

        public static SimulationModelVersionJpaEntity toEntity(SimulationModelVersion model) {
            return new SimulationModelVersionJpaEntity(
                        model.id(),
                        model.modelId(),
                        model.versionNumber(),
                        model.solverProfileId(),
                        model.modelDefinitionHash(),
                        model.compatibleTopologyVersion(),
                        model.status(),
                        model.activatedAt(),
                        model.retiredAt(),
                        model.createdAt()
            );
        }

        public static SimulationModelVersion toDomain(SimulationModelVersionJpaEntity entity) {
            return new SimulationModelVersion(
                        entity.id(),
                        entity.modelId(),
                        entity.versionNumber(),
                        entity.solverProfileId(),
                        entity.modelDefinitionHash(),
                        entity.compatibleTopologyVersion(),
                        entity.status(),
                        entity.activatedAt(),
                        entity.retiredAt(),
                        entity.createdAt()
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

        public static SimulationScenarioAssumptionJpaEntity toEntity(SimulationScenarioAssumption model) {
            return new SimulationScenarioAssumptionJpaEntity(
                        model.id(),
                        model.scenarioId(),
                        model.assumptionTypeId(),
                        model.targetType(),
                        model.targetId(),
                        model.parameterCode(),
                        model.valueType(),
                        model.valueText(),
                        model.unitCode(),
                        model.confidenceLevelId(),
                        model.sourceNote(),
                        model.createdAt()
            );
        }

        public static SimulationScenarioAssumption toDomain(SimulationScenarioAssumptionJpaEntity entity) {
            return new SimulationScenarioAssumption(
                        entity.id(),
                        entity.scenarioId(),
                        entity.assumptionTypeId(),
                        entity.targetType(),
                        entity.targetId(),
                        entity.parameterCode(),
                        entity.valueType(),
                        entity.valueText(),
                        entity.unitCode(),
                        entity.confidenceLevelId(),
                        entity.sourceNote(),
                        entity.createdAt()
            );
        }

        public static SimulationInputSnapshotJpaEntity toEntity(SimulationInputSnapshot model) {
            return new SimulationInputSnapshotJpaEntity(
                        model.id(),
                        model.scenarioId(),
                        model.topologySnapshotId(),
                        model.telemetrySnapshotReference(),
                        model.planningSnapshotReference(),
                        model.monitoringSnapshotReference(),
                        model.integritySnapshotReference(),
                        model.assetAvailabilitySnapshotReference(),
                        model.capturedAt(),
                        model.captureHash()
            );
        }

        public static SimulationInputSnapshot toDomain(SimulationInputSnapshotJpaEntity entity) {
            return new SimulationInputSnapshot(
                        entity.id(),
                        entity.scenarioId(),
                        entity.topologySnapshotId(),
                        entity.telemetrySnapshotReference(),
                        entity.planningSnapshotReference(),
                        entity.monitoringSnapshotReference(),
                        entity.integritySnapshotReference(),
                        entity.assetAvailabilitySnapshotReference(),
                        entity.capturedAt(),
                        entity.captureHash()
            );
        }

        public static SimulationInputDatasetJpaEntity toEntity(SimulationInputDataset model) {
            return new SimulationInputDatasetJpaEntity(
                        model.id(),
                        model.inputSnapshotId(),
                        model.datasetTypeId(),
                        model.sourceModule(),
                        model.sourceReference(),
                        model.recordCount(),
                        model.checksum(),
                        model.createdAt()
            );
        }

        public static SimulationInputDataset toDomain(SimulationInputDatasetJpaEntity entity) {
            return new SimulationInputDataset(
                        entity.id(),
                        entity.inputSnapshotId(),
                        entity.datasetTypeId(),
                        entity.sourceModule(),
                        entity.sourceReference(),
                        entity.recordCount(),
                        entity.checksum(),
                        entity.createdAt()
            );
        }

        public static SimulationConstraintJpaEntity toEntity(SimulationConstraint model) {
            return new SimulationConstraintJpaEntity(
                        model.id(),
                        model.scenarioId(),
                        model.constraintTypeId(),
                        model.targetType(),
                        model.targetId(),
                        model.expressionText(),
                        model.limitValue(),
                        model.unitCode(),
                        model.severityId(),
                        model.active(),
                        model.createdAt()
            );
        }

        public static SimulationConstraint toDomain(SimulationConstraintJpaEntity entity) {
            return new SimulationConstraint(
                        entity.id(),
                        entity.scenarioId(),
                        entity.constraintTypeId(),
                        entity.targetType(),
                        entity.targetId(),
                        entity.expressionText(),
                        entity.limitValue(),
                        entity.unitCode(),
                        entity.severityId(),
                        entity.active(),
                        entity.createdAt()
            );
        }

        public static SimulationObjectiveJpaEntity toEntity(SimulationObjective model) {
            return new SimulationObjectiveJpaEntity(
                        model.id(),
                        model.scenarioId(),
                        model.objectiveTypeId(),
                        model.weight(),
                        model.priorityOrder(),
                        model.targetType(),
                        model.targetId(),
                        model.expressionText(),
                        model.createdAt()
            );
        }

        public static SimulationObjective toDomain(SimulationObjectiveJpaEntity entity) {
            return new SimulationObjective(
                        entity.id(),
                        entity.scenarioId(),
                        entity.objectiveTypeId(),
                        entity.weight(),
                        entity.priorityOrder(),
                        entity.targetType(),
                        entity.targetId(),
                        entity.expressionText(),
                        entity.createdAt()
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

        public static SimulationRunStepJpaEntity toEntity(SimulationRunStep model) {
            return new SimulationRunStepJpaEntity(
                        model.id(),
                        model.runId(),
                        model.stepOrder(),
                        model.stepCode(),
                        model.status(),
                        model.startedAt(),
                        model.completedAt(),
                        model.message()
            );
        }

        public static SimulationRunStep toDomain(SimulationRunStepJpaEntity entity) {
            return new SimulationRunStep(
                        entity.id(),
                        entity.runId(),
                        entity.stepOrder(),
                        entity.stepCode(),
                        entity.status(),
                        entity.startedAt(),
                        entity.completedAt(),
                        entity.message()
            );
        }

        public static SimulationSolverTraceJpaEntity toEntity(SimulationSolverTrace model) {
            return new SimulationSolverTraceJpaEntity(
                        model.id(),
                        model.runId(),
                        model.iterationNumber(),
                        model.traceLevel(),
                        model.metricCode(),
                        model.metricValue(),
                        model.message(),
                        model.recordedAt()
            );
        }

        public static SimulationSolverTrace toDomain(SimulationSolverTraceJpaEntity entity) {
            return new SimulationSolverTrace(
                        entity.id(),
                        entity.runId(),
                        entity.iterationNumber(),
                        entity.traceLevel(),
                        entity.metricCode(),
                        entity.metricValue(),
                        entity.message(),
                        entity.recordedAt()
            );
        }

        public static SimulationResultSummaryJpaEntity toEntity(SimulationResultSummary model) {
            return new SimulationResultSummaryJpaEntity(
                        model.id(),
                        model.runId(),
                        model.feasible(),
                        model.objectiveScore(),
                        model.constraintViolationCount(),
                        model.warningCount(),
                        model.resultStatusId(),
                        model.summaryText(),
                        model.createdAt()
            );
        }

        public static SimulationResultSummary toDomain(SimulationResultSummaryJpaEntity entity) {
            return new SimulationResultSummary(
                        entity.id(),
                        entity.runId(),
                        entity.feasible(),
                        entity.objectiveScore(),
                        entity.constraintViolationCount(),
                        entity.warningCount(),
                        entity.resultStatusId(),
                        entity.summaryText(),
                        entity.createdAt()
            );
        }

        public static SimulationResultValueJpaEntity toEntity(SimulationResultValue model) {
            return new SimulationResultValueJpaEntity(
                        model.id(),
                        model.runId(),
                        model.targetType(),
                        model.targetId(),
                        model.metricCode(),
                        model.value(),
                        model.unitCode(),
                        model.timeOffsetSeconds(),
                        model.recordedAt()
            );
        }

        public static SimulationResultValue toDomain(SimulationResultValueJpaEntity entity) {
            return new SimulationResultValue(
                        entity.id(),
                        entity.runId(),
                        entity.targetType(),
                        entity.targetId(),
                        entity.metricCode(),
                        entity.value(),
                        entity.unitCode(),
                        entity.timeOffsetSeconds(),
                        entity.recordedAt()
            );
        }

        public static SimulationResultSeriesReferenceJpaEntity toEntity(SimulationResultSeriesReference model) {
            return new SimulationResultSeriesReferenceJpaEntity(
                        model.id(),
                        model.runId(),
                        model.seriesTypeId(),
                        model.targetType(),
                        model.targetId(),
                        model.storageLocation(),
                        model.checksum(),
                        model.createdAt()
            );
        }

        public static SimulationResultSeriesReference toDomain(SimulationResultSeriesReferenceJpaEntity entity) {
            return new SimulationResultSeriesReference(
                        entity.id(),
                        entity.runId(),
                        entity.seriesTypeId(),
                        entity.targetType(),
                        entity.targetId(),
                        entity.storageLocation(),
                        entity.checksum(),
                        entity.createdAt()
            );
        }

        public static SimulationConstraintEvaluationJpaEntity toEntity(SimulationConstraintEvaluation model) {
            return new SimulationConstraintEvaluationJpaEntity(
                        model.id(),
                        model.runId(),
                        model.constraintId(),
                        model.status(),
                        model.observedValue(),
                        model.limitValue(),
                        model.unitCode(),
                        model.severityId(),
                        model.explanation(),
                        model.createdAt()
            );
        }

        public static SimulationConstraintEvaluation toDomain(SimulationConstraintEvaluationJpaEntity entity) {
            return new SimulationConstraintEvaluation(
                        entity.id(),
                        entity.runId(),
                        entity.constraintId(),
                        entity.status(),
                        entity.observedValue(),
                        entity.limitValue(),
                        entity.unitCode(),
                        entity.severityId(),
                        entity.explanation(),
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

        public static SimulationCandidateOperatingConditionJpaEntity toEntity(SimulationCandidateOperatingCondition model) {
            return new SimulationCandidateOperatingConditionJpaEntity(
                        model.id(),
                        model.candidateId(),
                        model.targetType(),
                        model.targetId(),
                        model.metricCode(),
                        model.expectedValue(),
                        model.unitCode(),
                        model.timeOffsetSeconds(),
                        model.createdAt()
            );
        }

        public static SimulationCandidateOperatingCondition toDomain(SimulationCandidateOperatingConditionJpaEntity entity) {
            return new SimulationCandidateOperatingCondition(
                        entity.id(),
                        entity.candidateId(),
                        entity.targetType(),
                        entity.targetId(),
                        entity.metricCode(),
                        entity.expectedValue(),
                        entity.unitCode(),
                        entity.timeOffsetSeconds(),
                        entity.createdAt()
            );
        }

        public static SimulationCandidateScoreJpaEntity toEntity(SimulationCandidateScore model) {
            return new SimulationCandidateScoreJpaEntity(
                        model.id(),
                        model.candidateId(),
                        model.objectiveId(),
                        model.scoreCode(),
                        model.scoreValue(),
                        model.weight(),
                        model.rankContribution(),
                        model.explanation()
            );
        }

        public static SimulationCandidateScore toDomain(SimulationCandidateScoreJpaEntity entity) {
            return new SimulationCandidateScore(
                        entity.id(),
                        entity.candidateId(),
                        entity.objectiveId(),
                        entity.scoreCode(),
                        entity.scoreValue(),
                        entity.weight(),
                        entity.rankContribution(),
                        entity.explanation()
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

        public static SimulationValidationFindingJpaEntity toEntity(SimulationValidationFinding model) {
            return new SimulationValidationFindingJpaEntity(
                        model.id(),
                        model.scenarioId(),
                        model.runId(),
                        model.findingTypeId(),
                        model.severityId(),
                        model.targetType(),
                        model.targetId(),
                        model.message(),
                        model.resolved(),
                        model.resolvedAt(),
                        model.createdAt()
            );
        }

        public static SimulationValidationFinding toDomain(SimulationValidationFindingJpaEntity entity) {
            return new SimulationValidationFinding(
                        entity.id(),
                        entity.scenarioId(),
                        entity.runId(),
                        entity.findingTypeId(),
                        entity.severityId(),
                        entity.targetType(),
                        entity.targetId(),
                        entity.message(),
                        entity.resolved(),
                        entity.resolvedAt(),
                        entity.createdAt()
            );
        }

        public static SimulationSensitivityAnalysisJpaEntity toEntity(SimulationSensitivityAnalysis model) {
            return new SimulationSensitivityAnalysisJpaEntity(
                        model.id(),
                        model.scenarioId(),
                        model.baseRunId(),
                        model.parameterCode(),
                        model.parameterRangeText(),
                        model.resultMetricCode(),
                        model.sensitivityScore(),
                        model.summaryText(),
                        model.createdAt()
            );
        }

        public static SimulationSensitivityAnalysis toDomain(SimulationSensitivityAnalysisJpaEntity entity) {
            return new SimulationSensitivityAnalysis(
                        entity.id(),
                        entity.scenarioId(),
                        entity.baseRunId(),
                        entity.parameterCode(),
                        entity.parameterRangeText(),
                        entity.resultMetricCode(),
                        entity.sensitivityScore(),
                        entity.summaryText(),
                        entity.createdAt()
            );
        }

        public static SimulationEvidenceLinkJpaEntity toEntity(SimulationEvidenceLink model) {
            return new SimulationEvidenceLinkJpaEntity(
                        model.id(),
                        model.ownerType(),
                        model.ownerId(),
                        model.evidenceType(),
                        model.evidenceReference(),
                        model.label(),
                        model.createdAt()
            );
        }

        public static SimulationEvidenceLink toDomain(SimulationEvidenceLinkJpaEntity entity) {
            return new SimulationEvidenceLink(
                        entity.id(),
                        entity.ownerType(),
                        entity.ownerId(),
                        entity.evidenceType(),
                        entity.evidenceReference(),
                        entity.label(),
                        entity.createdAt()
            );
        }

        public static SimulationCatalogEntryJpaEntity toEntity(SimulationCatalogEntry model) {
            return new SimulationCatalogEntryJpaEntity(
                        model.id(),
                        model.catalogName(),
                        model.code(),
                        model.active(),
                        model.sortOrder(),
                        model.systemDefined(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static SimulationCatalogEntry toDomain(SimulationCatalogEntryJpaEntity entity) {
            return new SimulationCatalogEntry(
                        entity.id(),
                        entity.catalogName(),
                        entity.code(),
                        entity.active(),
                        entity.sortOrder(),
                        entity.systemDefined(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static SimulationCatalogTranslationJpaEntity toEntity(SimulationCatalogTranslation model) {
            return new SimulationCatalogTranslationJpaEntity(
                        model.id(),
                        model.catalogEntryId(),
                        model.locale(),
                        model.name(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static SimulationCatalogTranslation toDomain(SimulationCatalogTranslationJpaEntity entity) {
            return new SimulationCatalogTranslation(
                        entity.id(),
                        entity.catalogEntryId(),
                        entity.locale(),
                        entity.name(),
                        entity.description(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

}
