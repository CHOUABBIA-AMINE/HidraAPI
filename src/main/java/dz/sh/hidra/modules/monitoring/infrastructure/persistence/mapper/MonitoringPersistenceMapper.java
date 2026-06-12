/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.mapper
 *
 * @Description : Maps monitoring domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.monitoring.domain.model.*;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity.*;

/**
 * Maps monitoring domain models to JPA entities.
 */
public final class MonitoringPersistenceMapper {

    private MonitoringPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static MonitoringRuleJpaEntity toEntity(MonitoringRule model) {
            return new MonitoringRuleJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.ruleType(),
                        model.evaluationFrequencyId(),
                        model.topologyAssetType(),
                        model.topologyAssetId(),
                        model.topologyAssetCode(),
                        model.telemetryPointId(),
                        model.planningTargetTypeId(),
                        model.expression(),
                        model.status(),
                        model.createdByActorId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static MonitoringRule toDomain(MonitoringRuleJpaEntity entity) {
            return new MonitoringRule(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.ruleType(),
                        entity.evaluationFrequencyId(),
                        entity.topologyAssetType(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCode(),
                        entity.telemetryPointId(),
                        entity.planningTargetTypeId(),
                        entity.expression(),
                        entity.status(),
                        entity.createdByActorId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static MonitoringThresholdJpaEntity toEntity(MonitoringThreshold model) {
            return new MonitoringThresholdJpaEntity(
                        model.id(),
                        model.ruleId(),
                        model.thresholdDirection(),
                        model.lowValue(),
                        model.highValue(),
                        model.expectedTextValue(),
                        model.unitId(),
                        model.severity(),
                        model.validFrom(),
                        model.validTo(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static MonitoringThreshold toDomain(MonitoringThresholdJpaEntity entity) {
            return new MonitoringThreshold(
                        entity.id(),
                        entity.ruleId(),
                        entity.thresholdDirection(),
                        entity.lowValue(),
                        entity.highValue(),
                        entity.expectedTextValue(),
                        entity.unitId(),
                        entity.severity(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static MonitoringEvaluationJpaEntity toEntity(MonitoringEvaluation model) {
            return new MonitoringEvaluationJpaEntity(
                        model.id(),
                        model.ruleId(),
                        model.periodId(),
                        model.planRevisionId(),
                        model.topologyAssetType(),
                        model.topologyAssetId(),
                        model.telemetryPointId(),
                        model.status(),
                        model.result(),
                        model.evaluationStart(),
                        model.evaluationEnd(),
                        model.actualReadingCount(),
                        model.deviationCount(),
                        model.failureReason(),
                        model.correlationId(),
                        model.createdAt()
            );
        }

        public static MonitoringEvaluation toDomain(MonitoringEvaluationJpaEntity entity) {
            return new MonitoringEvaluation(
                        entity.id(),
                        entity.ruleId(),
                        entity.periodId(),
                        entity.planRevisionId(),
                        entity.topologyAssetType(),
                        entity.topologyAssetId(),
                        entity.telemetryPointId(),
                        entity.status(),
                        entity.result(),
                        entity.evaluationStart(),
                        entity.evaluationEnd(),
                        entity.actualReadingCount(),
                        entity.deviationCount(),
                        entity.failureReason(),
                        entity.correlationId(),
                        entity.createdAt()
            );
        }

        public static OperationalStateJpaEntity toEntity(OperationalState model) {
            return new OperationalStateJpaEntity(
                        model.id(),
                        model.topologyAssetType(),
                        model.topologyAssetId(),
                        model.topologyAssetCode(),
                        model.telemetryPointId(),
                        model.stateValue(),
                        model.severity(),
                        model.reasonCode(),
                        model.reasonMessage(),
                        model.lastTrustedReadingId(),
                        model.lastPlanTargetId(),
                        model.stateAt(),
                        model.updatedAt()
            );
        }

        public static OperationalState toDomain(OperationalStateJpaEntity entity) {
            return new OperationalState(
                        entity.id(),
                        entity.topologyAssetType(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCode(),
                        entity.telemetryPointId(),
                        entity.stateValue(),
                        entity.severity(),
                        entity.reasonCode(),
                        entity.reasonMessage(),
                        entity.lastTrustedReadingId(),
                        entity.lastPlanTargetId(),
                        entity.stateAt(),
                        entity.updatedAt()
            );
        }

        public static OperationalStateSnapshotJpaEntity toEntity(OperationalStateSnapshot model) {
            return new OperationalStateSnapshotJpaEntity(
                        model.id(),
                        model.operationalStateId(),
                        model.topologyAssetType(),
                        model.topologyAssetId(),
                        model.telemetryPointId(),
                        model.stateValue(),
                        model.severity(),
                        model.snapshotPayload(),
                        model.capturedAt(),
                        model.correlationId()
            );
        }

        public static OperationalStateSnapshot toDomain(OperationalStateSnapshotJpaEntity entity) {
            return new OperationalStateSnapshot(
                        entity.id(),
                        entity.operationalStateId(),
                        entity.topologyAssetType(),
                        entity.topologyAssetId(),
                        entity.telemetryPointId(),
                        entity.stateValue(),
                        entity.severity(),
                        entity.snapshotPayload(),
                        entity.capturedAt(),
                        entity.correlationId()
            );
        }

        public static PlanActualDeviationJpaEntity toEntity(PlanActualDeviation model) {
            return new PlanActualDeviationJpaEntity(
                        model.id(),
                        model.evaluationId(),
                        model.planTargetId(),
                        model.expectedFlowStateId(),
                        model.trustedTelemetryReadingId(),
                        model.telemetryPointId(),
                        model.topologyAssetType(),
                        model.topologyAssetId(),
                        model.topologyAssetCode(),
                        model.actualValue(),
                        model.expectedValue(),
                        model.differenceValue(),
                        model.differencePercent(),
                        model.unitId(),
                        model.severity(),
                        model.status(),
                        model.detectedAt(),
                        model.resolvedAt(),
                        model.reasonCode(),
                        model.reasonMessage()
            );
        }

        public static PlanActualDeviation toDomain(PlanActualDeviationJpaEntity entity) {
            return new PlanActualDeviation(
                        entity.id(),
                        entity.evaluationId(),
                        entity.planTargetId(),
                        entity.expectedFlowStateId(),
                        entity.trustedTelemetryReadingId(),
                        entity.telemetryPointId(),
                        entity.topologyAssetType(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCode(),
                        entity.actualValue(),
                        entity.expectedValue(),
                        entity.differenceValue(),
                        entity.differencePercent(),
                        entity.unitId(),
                        entity.severity(),
                        entity.status(),
                        entity.detectedAt(),
                        entity.resolvedAt(),
                        entity.reasonCode(),
                        entity.reasonMessage()
            );
        }

        public static MonitoringAlertCandidateJpaEntity toEntity(MonitoringAlertCandidate model) {
            return new MonitoringAlertCandidateJpaEntity(
                        model.id(),
                        model.deviationId(),
                        model.evaluationId(),
                        model.ruleId(),
                        model.candidateCode(),
                        model.candidateTypeId(),
                        model.severity(),
                        model.topologyAssetType(),
                        model.topologyAssetId(),
                        model.telemetryPointId(),
                        model.summary(),
                        model.lifecycleStatus(),
                        model.candidateStatus(),
                        model.escalationReferenceId(),
                        model.createdAt(),
                        model.expiresAt()
            );
        }

        public static MonitoringAlertCandidate toDomain(MonitoringAlertCandidateJpaEntity entity) {
            return new MonitoringAlertCandidate(
                        entity.id(),
                        entity.deviationId(),
                        entity.evaluationId(),
                        entity.ruleId(),
                        entity.candidateCode(),
                        entity.candidateTypeId(),
                        entity.severity(),
                        entity.topologyAssetType(),
                        entity.topologyAssetId(),
                        entity.telemetryPointId(),
                        entity.summary(),
                        entity.lifecycleStatus(),
                        entity.candidateStatus(),
                        entity.escalationReferenceId(),
                        entity.createdAt(),
                        entity.expiresAt()
            );
        }

        public static MonitoringAcknowledgementJpaEntity toEntity(MonitoringAcknowledgement model) {
            return new MonitoringAcknowledgementJpaEntity(
                        model.id(),
                        model.targetType(),
                        model.targetId(),
                        model.acknowledgementStatus(),
                        model.acknowledgedByActorId(),
                        model.acknowledgedAt(),
                        model.comment(),
                        model.workflowInstanceId(),
                        model.correlationId()
            );
        }

        public static MonitoringAcknowledgement toDomain(MonitoringAcknowledgementJpaEntity entity) {
            return new MonitoringAcknowledgement(
                        entity.id(),
                        entity.targetType(),
                        entity.targetId(),
                        entity.acknowledgementStatus(),
                        entity.acknowledgedByActorId(),
                        entity.acknowledgedAt(),
                        entity.comment(),
                        entity.workflowInstanceId(),
                        entity.correlationId()
            );
        }

        public static RiskSignalJpaEntity toEntity(RiskSignal model) {
            return new RiskSignalJpaEntity(
                        model.id(),
                        model.sourceDeviationId(),
                        model.sourceEvaluationId(),
                        model.topologyAssetType(),
                        model.topologyAssetId(),
                        model.riskTypeId(),
                        model.riskLevel(),
                        model.riskScore(),
                        model.signalPayload(),
                        model.status(),
                        model.raisedAt(),
                        model.expiresAt(),
                        model.correlationId()
            );
        }

        public static RiskSignal toDomain(RiskSignalJpaEntity entity) {
            return new RiskSignal(
                        entity.id(),
                        entity.sourceDeviationId(),
                        entity.sourceEvaluationId(),
                        entity.topologyAssetType(),
                        entity.topologyAssetId(),
                        entity.riskTypeId(),
                        entity.riskLevel(),
                        entity.riskScore(),
                        entity.signalPayload(),
                        entity.status(),
                        entity.raisedAt(),
                        entity.expiresAt(),
                        entity.correlationId()
            );
        }

        public static MonitoringCatalogEntryJpaEntity toEntity(MonitoringCatalogEntry model) {
            return new MonitoringCatalogEntryJpaEntity(
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

        public static MonitoringCatalogEntry toDomain(MonitoringCatalogEntryJpaEntity entity) {
            return new MonitoringCatalogEntry(
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

        public static MonitoringCatalogTranslationJpaEntity toEntity(MonitoringCatalogTranslation model) {
            return new MonitoringCatalogTranslationJpaEntity(
                        model.id(),
                        model.catalogEntryId(),
                        model.locale(),
                        model.name(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static MonitoringCatalogTranslation toDomain(MonitoringCatalogTranslationJpaEntity entity) {
            return new MonitoringCatalogTranslation(
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
