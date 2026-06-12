/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsPersistenceMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper
 *
 * @Description : Maps analytics domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.analytics.domain.model.*;
import dz.sh.hidra.modules.analytics.infrastructure.persistence.entity.*;

/**
 * Maps analytics domain models to JPA entities.
 */
public final class AnalyticsPersistenceMapper {

    private AnalyticsPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static AnalyticsSubjectAreaJpaEntity toEntity(AnalyticsSubjectArea model) {
            return new AnalyticsSubjectAreaJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.description(),
                        model.ownerModule(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AnalyticsSubjectArea toDomain(AnalyticsSubjectAreaJpaEntity entity) {
            return new AnalyticsSubjectArea(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.description(),
                        entity.ownerModule(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AnalyticsDataSourceReferenceJpaEntity toEntity(AnalyticsDataSourceReference model) {
            return new AnalyticsDataSourceReferenceJpaEntity(
                        model.id(),
                        model.sourceModule(),
                        model.sourceType(),
                        model.sourceName(),
                        model.sourceVersion(),
                        model.accessMode(),
                        model.refreshMode(),
                        model.trusted(),
                        model.lastAvailableAt(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AnalyticsDataSourceReference toDomain(AnalyticsDataSourceReferenceJpaEntity entity) {
            return new AnalyticsDataSourceReference(
                        entity.id(),
                        entity.sourceModule(),
                        entity.sourceType(),
                        entity.sourceName(),
                        entity.sourceVersion(),
                        entity.accessMode(),
                        entity.refreshMode(),
                        entity.trusted(),
                        entity.lastAvailableAt(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AnalyticsDatasetJpaEntity toEntity(AnalyticsDataset model) {
            return new AnalyticsDatasetJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.subjectAreaId(),
                        model.datasetType(),
                        model.refreshMode(),
                        model.lineageStatus(),
                        model.qualityStatus(),
                        model.schemaVersion(),
                        model.createdFrom(),
                        model.validFrom(),
                        model.validTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AnalyticsDataset toDomain(AnalyticsDatasetJpaEntity entity) {
            return new AnalyticsDataset(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.subjectAreaId(),
                        entity.datasetType(),
                        entity.refreshMode(),
                        entity.lineageStatus(),
                        entity.qualityStatus(),
                        entity.schemaVersion(),
                        entity.createdFrom(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AnalyticsDatasetVersionJpaEntity toEntity(AnalyticsDatasetVersion model) {
            return new AnalyticsDatasetVersionJpaEntity(
                        model.id(),
                        model.datasetId(),
                        model.versionNumber(),
                        model.schemaHash(),
                        model.dataHash(),
                        model.rowCount(),
                        model.periodStart(),
                        model.periodEnd(),
                        model.qualityScore(),
                        model.published(),
                        model.publishedAt(),
                        model.publishedByActorId(),
                        model.createdAt()
            );
        }

        public static AnalyticsDatasetVersion toDomain(AnalyticsDatasetVersionJpaEntity entity) {
            return new AnalyticsDatasetVersion(
                        entity.id(),
                        entity.datasetId(),
                        entity.versionNumber(),
                        entity.schemaHash(),
                        entity.dataHash(),
                        entity.rowCount(),
                        entity.periodStart(),
                        entity.periodEnd(),
                        entity.qualityScore(),
                        entity.published(),
                        entity.publishedAt(),
                        entity.publishedByActorId(),
                        entity.createdAt()
            );
        }

        public static AnalyticsDatasetLineageJpaEntity toEntity(AnalyticsDatasetLineage model) {
            return new AnalyticsDatasetLineageJpaEntity(
                        model.id(),
                        model.datasetVersionId(),
                        model.sourceModule(),
                        model.sourceObjectType(),
                        model.sourceObjectId(),
                        model.sourceSnapshotId(),
                        model.sourceVersion(),
                        model.sourcePeriodStart(),
                        model.sourcePeriodEnd(),
                        model.lineageRole(),
                        model.createdAt()
            );
        }

        public static AnalyticsDatasetLineage toDomain(AnalyticsDatasetLineageJpaEntity entity) {
            return new AnalyticsDatasetLineage(
                        entity.id(),
                        entity.datasetVersionId(),
                        entity.sourceModule(),
                        entity.sourceObjectType(),
                        entity.sourceObjectId(),
                        entity.sourceSnapshotId(),
                        entity.sourceVersion(),
                        entity.sourcePeriodStart(),
                        entity.sourcePeriodEnd(),
                        entity.lineageRole(),
                        entity.createdAt()
            );
        }

        public static AnalyticsProjectionDefinitionJpaEntity toEntity(AnalyticsProjectionDefinition model) {
            return new AnalyticsProjectionDefinitionJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.subjectAreaId(),
                        model.projectionType(),
                        model.calculationPolicy(),
                        model.refreshPolicy(),
                        model.retentionPolicy(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AnalyticsProjectionDefinition toDomain(AnalyticsProjectionDefinitionJpaEntity entity) {
            return new AnalyticsProjectionDefinition(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.subjectAreaId(),
                        entity.projectionType(),
                        entity.calculationPolicy(),
                        entity.refreshPolicy(),
                        entity.retentionPolicy(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AnalyticsProjectionRunJpaEntity toEntity(AnalyticsProjectionRun model) {
            return new AnalyticsProjectionRunJpaEntity(
                        model.id(),
                        model.projectionDefinitionId(),
                        model.runStatus(),
                        model.runMode(),
                        model.periodStart(),
                        model.periodEnd(),
                        model.startedAt(),
                        model.completedAt(),
                        model.sourceWatermark(),
                        model.recordsRead(),
                        model.recordsWritten(),
                        model.errorCode(),
                        model.errorMessage(),
                        model.correlationId(),
                        model.createdAt()
            );
        }

        public static AnalyticsProjectionRun toDomain(AnalyticsProjectionRunJpaEntity entity) {
            return new AnalyticsProjectionRun(
                        entity.id(),
                        entity.projectionDefinitionId(),
                        entity.runStatus(),
                        entity.runMode(),
                        entity.periodStart(),
                        entity.periodEnd(),
                        entity.startedAt(),
                        entity.completedAt(),
                        entity.sourceWatermark(),
                        entity.recordsRead(),
                        entity.recordsWritten(),
                        entity.errorCode(),
                        entity.errorMessage(),
                        entity.correlationId(),
                        entity.createdAt()
            );
        }

        public static AnalyticsProjectionSnapshotJpaEntity toEntity(AnalyticsProjectionSnapshot model) {
            return new AnalyticsProjectionSnapshotJpaEntity(
                        model.id(),
                        model.projectionDefinitionId(),
                        model.projectionRunId(),
                        model.snapshotCode(),
                        model.periodStart(),
                        model.periodEnd(),
                        model.snapshotStatus(),
                        model.publishedAt(),
                        model.publishedByActorId(),
                        model.createdAt()
            );
        }

        public static AnalyticsProjectionSnapshot toDomain(AnalyticsProjectionSnapshotJpaEntity entity) {
            return new AnalyticsProjectionSnapshot(
                        entity.id(),
                        entity.projectionDefinitionId(),
                        entity.projectionRunId(),
                        entity.snapshotCode(),
                        entity.periodStart(),
                        entity.periodEnd(),
                        entity.snapshotStatus(),
                        entity.publishedAt(),
                        entity.publishedByActorId(),
                        entity.createdAt()
            );
        }

        public static MetricDefinitionJpaEntity toEntity(MetricDefinition model) {
            return new MetricDefinitionJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.subjectAreaId(),
                        model.metricType(),
                        model.formulaExpression(),
                        model.unitId(),
                        model.aggregationMethod(),
                        model.periodGranularity(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static MetricDefinition toDomain(MetricDefinitionJpaEntity entity) {
            return new MetricDefinition(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.subjectAreaId(),
                        entity.metricType(),
                        entity.formulaExpression(),
                        entity.unitId(),
                        entity.aggregationMethod(),
                        entity.periodGranularity(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static MetricDefinitionVersionJpaEntity toEntity(MetricDefinitionVersion model) {
            return new MetricDefinitionVersionJpaEntity(
                        model.id(),
                        model.metricDefinitionId(),
                        model.versionNumber(),
                        model.formulaExpression(),
                        model.calculationDescription(),
                        model.validFrom(),
                        model.validTo(),
                        model.createdByActorId(),
                        model.createdAt()
            );
        }

        public static MetricDefinitionVersion toDomain(MetricDefinitionVersionJpaEntity entity) {
            return new MetricDefinitionVersion(
                        entity.id(),
                        entity.metricDefinitionId(),
                        entity.versionNumber(),
                        entity.formulaExpression(),
                        entity.calculationDescription(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.createdByActorId(),
                        entity.createdAt()
            );
        }

        public static MetricEvaluationRunJpaEntity toEntity(MetricEvaluationRun model) {
            return new MetricEvaluationRunJpaEntity(
                        model.id(),
                        model.metricDefinitionVersionId(),
                        model.runStatus(),
                        model.periodStart(),
                        model.periodEnd(),
                        model.scopeType(),
                        model.scopeId(),
                        model.startedAt(),
                        model.completedAt(),
                        model.recordsRead(),
                        model.recordsProduced(),
                        model.errorCode(),
                        model.errorMessage(),
                        model.correlationId(),
                        model.createdAt()
            );
        }

        public static MetricEvaluationRun toDomain(MetricEvaluationRunJpaEntity entity) {
            return new MetricEvaluationRun(
                        entity.id(),
                        entity.metricDefinitionVersionId(),
                        entity.runStatus(),
                        entity.periodStart(),
                        entity.periodEnd(),
                        entity.scopeType(),
                        entity.scopeId(),
                        entity.startedAt(),
                        entity.completedAt(),
                        entity.recordsRead(),
                        entity.recordsProduced(),
                        entity.errorCode(),
                        entity.errorMessage(),
                        entity.correlationId(),
                        entity.createdAt()
            );
        }

        public static MetricValueJpaEntity toEntity(MetricValue model) {
            return new MetricValueJpaEntity(
                        model.id(),
                        model.metricEvaluationRunId(),
                        model.metricDefinitionId(),
                        model.metricDefinitionVersionId(),
                        model.scopeType(),
                        model.scopeId(),
                        model.periodStart(),
                        model.periodEnd(),
                        model.valueNumeric(),
                        model.valueText(),
                        model.unitId(),
                        model.qualityStatus(),
                        model.calculatedAt()
            );
        }

        public static MetricValue toDomain(MetricValueJpaEntity entity) {
            return new MetricValue(
                        entity.id(),
                        entity.metricEvaluationRunId(),
                        entity.metricDefinitionId(),
                        entity.metricDefinitionVersionId(),
                        entity.scopeType(),
                        entity.scopeId(),
                        entity.periodStart(),
                        entity.periodEnd(),
                        entity.valueNumeric(),
                        entity.valueText(),
                        entity.unitId(),
                        entity.qualityStatus(),
                        entity.calculatedAt()
            );
        }

        public static KpiDefinitionJpaEntity toEntity(KpiDefinition model) {
            return new KpiDefinitionJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.subjectAreaId(),
                        model.primaryMetricDefinitionId(),
                        model.kpiCategoryId(),
                        model.displayUnitId(),
                        model.defaultGranularity(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static KpiDefinition toDomain(KpiDefinitionJpaEntity entity) {
            return new KpiDefinition(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.subjectAreaId(),
                        entity.primaryMetricDefinitionId(),
                        entity.kpiCategoryId(),
                        entity.displayUnitId(),
                        entity.defaultGranularity(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static KpiBandJpaEntity toEntity(KpiBand model) {
            return new KpiBandJpaEntity(
                        model.id(),
                        model.kpiDefinitionId(),
                        model.bandCode(),
                        model.labelAr(),
                        model.labelFr(),
                        model.labelEn(),
                        model.minValue(),
                        model.maxValue(),
                        model.severityId(),
                        model.sortOrder(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static KpiBand toDomain(KpiBandJpaEntity entity) {
            return new KpiBand(
                        entity.id(),
                        entity.kpiDefinitionId(),
                        entity.bandCode(),
                        entity.labelAr(),
                        entity.labelFr(),
                        entity.labelEn(),
                        entity.minValue(),
                        entity.maxValue(),
                        entity.severityId(),
                        entity.sortOrder(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static KpiEvaluationJpaEntity toEntity(KpiEvaluation model) {
            return new KpiEvaluationJpaEntity(
                        model.id(),
                        model.kpiDefinitionId(),
                        model.metricValueId(),
                        model.scopeType(),
                        model.scopeId(),
                        model.periodStart(),
                        model.periodEnd(),
                        model.value(),
                        model.unitId(),
                        model.bandId(),
                        model.trendDirection(),
                        model.evaluatedAt()
            );
        }

        public static KpiEvaluation toDomain(KpiEvaluationJpaEntity entity) {
            return new KpiEvaluation(
                        entity.id(),
                        entity.kpiDefinitionId(),
                        entity.metricValueId(),
                        entity.scopeType(),
                        entity.scopeId(),
                        entity.periodStart(),
                        entity.periodEnd(),
                        entity.value(),
                        entity.unitId(),
                        entity.bandId(),
                        entity.trendDirection(),
                        entity.evaluatedAt()
            );
        }

        public static TrendAnalysisJpaEntity toEntity(TrendAnalysis model) {
            return new TrendAnalysisJpaEntity(
                        model.id(),
                        model.subjectAreaId(),
                        model.trendType(),
                        model.scopeType(),
                        model.scopeId(),
                        model.metricDefinitionId(),
                        model.periodStart(),
                        model.periodEnd(),
                        model.trendDirection(),
                        model.confidenceScore(),
                        model.strengthScore(),
                        model.detectedAt(),
                        model.createdAt()
            );
        }

        public static TrendAnalysis toDomain(TrendAnalysisJpaEntity entity) {
            return new TrendAnalysis(
                        entity.id(),
                        entity.subjectAreaId(),
                        entity.trendType(),
                        entity.scopeType(),
                        entity.scopeId(),
                        entity.metricDefinitionId(),
                        entity.periodStart(),
                        entity.periodEnd(),
                        entity.trendDirection(),
                        entity.confidenceScore(),
                        entity.strengthScore(),
                        entity.detectedAt(),
                        entity.createdAt()
            );
        }

        public static TrendPointJpaEntity toEntity(TrendPoint model) {
            return new TrendPointJpaEntity(
                        model.id(),
                        model.trendAnalysisId(),
                        model.periodStart(),
                        model.periodEnd(),
                        model.value(),
                        model.unitId(),
                        model.sourceMetricValueId(),
                        model.createdAt()
            );
        }

        public static TrendPoint toDomain(TrendPointJpaEntity entity) {
            return new TrendPoint(
                        entity.id(),
                        entity.trendAnalysisId(),
                        entity.periodStart(),
                        entity.periodEnd(),
                        entity.value(),
                        entity.unitId(),
                        entity.sourceMetricValueId(),
                        entity.createdAt()
            );
        }

        public static AnalyticsModelJpaEntity toEntity(AnalyticsModel model) {
            return new AnalyticsModelJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.modelType(),
                        model.subjectAreaId(),
                        model.ownerModule(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AnalyticsModel toDomain(AnalyticsModelJpaEntity entity) {
            return new AnalyticsModel(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.modelType(),
                        entity.subjectAreaId(),
                        entity.ownerModule(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AnalyticsModelVersionJpaEntity toEntity(AnalyticsModelVersion model) {
            return new AnalyticsModelVersionJpaEntity(
                        model.id(),
                        model.analyticsModelId(),
                        model.versionNumber(),
                        model.modelArtifactReference(),
                        model.trainingDatasetVersionId(),
                        model.validationDatasetVersionId(),
                        model.modelParametersJson(),
                        model.performanceSummaryJson(),
                        model.status(),
                        model.createdByActorId(),
                        model.createdAt()
            );
        }

        public static AnalyticsModelVersion toDomain(AnalyticsModelVersionJpaEntity entity) {
            return new AnalyticsModelVersion(
                        entity.id(),
                        entity.analyticsModelId(),
                        entity.versionNumber(),
                        entity.modelArtifactReference(),
                        entity.trainingDatasetVersionId(),
                        entity.validationDatasetVersionId(),
                        entity.modelParametersJson(),
                        entity.performanceSummaryJson(),
                        entity.status(),
                        entity.createdByActorId(),
                        entity.createdAt()
            );
        }

        public static AnalyticsModelRunJpaEntity toEntity(AnalyticsModelRun model) {
            return new AnalyticsModelRunJpaEntity(
                        model.id(),
                        model.analyticsModelVersionId(),
                        model.runType(),
                        model.runStatus(),
                        model.inputDatasetVersionId(),
                        model.scopeType(),
                        model.scopeId(),
                        model.periodStart(),
                        model.periodEnd(),
                        model.startedAt(),
                        model.completedAt(),
                        model.outputDatasetVersionId(),
                        model.errorCode(),
                        model.errorMessage(),
                        model.correlationId(),
                        model.createdAt()
            );
        }

        public static AnalyticsModelRun toDomain(AnalyticsModelRunJpaEntity entity) {
            return new AnalyticsModelRun(
                        entity.id(),
                        entity.analyticsModelVersionId(),
                        entity.runType(),
                        entity.runStatus(),
                        entity.inputDatasetVersionId(),
                        entity.scopeType(),
                        entity.scopeId(),
                        entity.periodStart(),
                        entity.periodEnd(),
                        entity.startedAt(),
                        entity.completedAt(),
                        entity.outputDatasetVersionId(),
                        entity.errorCode(),
                        entity.errorMessage(),
                        entity.correlationId(),
                        entity.createdAt()
            );
        }

        public static AnalyticsFeatureSetJpaEntity toEntity(AnalyticsFeatureSet model) {
            return new AnalyticsFeatureSetJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.subjectAreaId(),
                        model.sourceDatasetId(),
                        model.featureSchemaVersion(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AnalyticsFeatureSet toDomain(AnalyticsFeatureSetJpaEntity entity) {
            return new AnalyticsFeatureSet(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.subjectAreaId(),
                        entity.sourceDatasetId(),
                        entity.featureSchemaVersion(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AnalyticsFeatureValueJpaEntity toEntity(AnalyticsFeatureValue model) {
            return new AnalyticsFeatureValueJpaEntity(
                        model.id(),
                        model.featureSetId(),
                        model.datasetVersionId(),
                        model.scopeType(),
                        model.scopeId(),
                        model.featureName(),
                        model.featureValueNumeric(),
                        model.featureValueText(),
                        model.featureValueBoolean(),
                        model.periodStart(),
                        model.periodEnd(),
                        model.calculatedAt()
            );
        }

        public static AnalyticsFeatureValue toDomain(AnalyticsFeatureValueJpaEntity entity) {
            return new AnalyticsFeatureValue(
                        entity.id(),
                        entity.featureSetId(),
                        entity.datasetVersionId(),
                        entity.scopeType(),
                        entity.scopeId(),
                        entity.featureName(),
                        entity.featureValueNumeric(),
                        entity.featureValueText(),
                        entity.featureValueBoolean(),
                        entity.periodStart(),
                        entity.periodEnd(),
                        entity.calculatedAt()
            );
        }

        public static AnalyticsInsightJpaEntity toEntity(AnalyticsInsight model) {
            return new AnalyticsInsightJpaEntity(
                        model.id(),
                        model.insightType(),
                        model.subjectAreaId(),
                        model.scopeType(),
                        model.scopeId(),
                        model.title(),
                        model.summary(),
                        model.severityId(),
                        model.confidenceScore(),
                        model.sourceProjectionSnapshotId(),
                        model.sourceTrendAnalysisId(),
                        model.sourceModelRunId(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AnalyticsInsight toDomain(AnalyticsInsightJpaEntity entity) {
            return new AnalyticsInsight(
                        entity.id(),
                        entity.insightType(),
                        entity.subjectAreaId(),
                        entity.scopeType(),
                        entity.scopeId(),
                        entity.title(),
                        entity.summary(),
                        entity.severityId(),
                        entity.confidenceScore(),
                        entity.sourceProjectionSnapshotId(),
                        entity.sourceTrendAnalysisId(),
                        entity.sourceModelRunId(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AnalyticsInsightEvidenceJpaEntity toEntity(AnalyticsInsightEvidence model) {
            return new AnalyticsInsightEvidenceJpaEntity(
                        model.id(),
                        model.analyticsInsightId(),
                        model.evidenceType(),
                        model.sourceModule(),
                        model.sourceObjectType(),
                        model.sourceObjectId(),
                        model.sourceLabelSnapshot(),
                        model.weight(),
                        model.createdAt()
            );
        }

        public static AnalyticsInsightEvidence toDomain(AnalyticsInsightEvidenceJpaEntity entity) {
            return new AnalyticsInsightEvidence(
                        entity.id(),
                        entity.analyticsInsightId(),
                        entity.evidenceType(),
                        entity.sourceModule(),
                        entity.sourceObjectType(),
                        entity.sourceObjectId(),
                        entity.sourceLabelSnapshot(),
                        entity.weight(),
                        entity.createdAt()
            );
        }

        public static DigitalTwinReadinessAssessmentJpaEntity toEntity(DigitalTwinReadinessAssessment model) {
            return new DigitalTwinReadinessAssessmentJpaEntity(
                        model.id(),
                        model.scopeType(),
                        model.scopeId(),
                        model.topologySnapshotId(),
                        model.assessmentPeriodStart(),
                        model.assessmentPeriodEnd(),
                        model.telemetryCompletenessScore(),
                        model.telemetryQualityScore(),
                        model.topologyCompletenessScore(),
                        model.modelAvailabilityScore(),
                        model.lineageCompletenessScore(),
                        model.overallReadinessScore(),
                        model.readinessStatus(),
                        model.assessedAt(),
                        model.createdAt()
            );
        }

        public static DigitalTwinReadinessAssessment toDomain(DigitalTwinReadinessAssessmentJpaEntity entity) {
            return new DigitalTwinReadinessAssessment(
                        entity.id(),
                        entity.scopeType(),
                        entity.scopeId(),
                        entity.topologySnapshotId(),
                        entity.assessmentPeriodStart(),
                        entity.assessmentPeriodEnd(),
                        entity.telemetryCompletenessScore(),
                        entity.telemetryQualityScore(),
                        entity.topologyCompletenessScore(),
                        entity.modelAvailabilityScore(),
                        entity.lineageCompletenessScore(),
                        entity.overallReadinessScore(),
                        entity.readinessStatus(),
                        entity.assessedAt(),
                        entity.createdAt()
            );
        }

        public static AnalyticsAccessPolicyJpaEntity toEntity(AnalyticsAccessPolicy model) {
            return new AnalyticsAccessPolicyJpaEntity(
                        model.id(),
                        model.analyticsObjectType(),
                        model.analyticsObjectId(),
                        model.accessScopeType(),
                        model.accessScopeId(),
                        model.permissionCode(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AnalyticsAccessPolicy toDomain(AnalyticsAccessPolicyJpaEntity entity) {
            return new AnalyticsAccessPolicy(
                        entity.id(),
                        entity.analyticsObjectType(),
                        entity.analyticsObjectId(),
                        entity.accessScopeType(),
                        entity.accessScopeId(),
                        entity.permissionCode(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AnalyticsCatalogEntryJpaEntity toEntity(AnalyticsCatalogEntry model) {
            return new AnalyticsCatalogEntryJpaEntity(
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

        public static AnalyticsCatalogEntry toDomain(AnalyticsCatalogEntryJpaEntity entity) {
            return new AnalyticsCatalogEntry(
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

        public static AnalyticsCatalogTranslationJpaEntity toEntity(AnalyticsCatalogTranslation model) {
            return new AnalyticsCatalogTranslationJpaEntity(
                        model.id(),
                        model.catalogEntryId(),
                        model.locale(),
                        model.name(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AnalyticsCatalogTranslation toDomain(AnalyticsCatalogTranslationJpaEntity entity) {
            return new AnalyticsCatalogTranslation(
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
