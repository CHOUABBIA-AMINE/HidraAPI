/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
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
        public static AnalyticsProjectionRunJpaEntity toEntity(AnalyticsProjectionRun model) {
            return new AnalyticsProjectionRunJpaEntity(
                        model.id(),
                        model.projectionDefinitionId(),
                        model.projectionDefinitionVersion(),
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
                        entity.projectionDefinitionVersion(),
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
}
