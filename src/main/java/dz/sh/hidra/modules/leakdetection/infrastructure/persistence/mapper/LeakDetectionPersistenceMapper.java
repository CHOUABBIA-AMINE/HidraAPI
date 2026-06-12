/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionPersistenceMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.mapper
 *
 * @Description : Maps leak detection domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.leakdetection.domain.model.*;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity.*;

/**
 * Maps leak detection domain models to JPA entities.
 */
public final class LeakDetectionPersistenceMapper {

    private LeakDetectionPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static LeakDetectionProfileJpaEntity toEntity(LeakDetectionProfile model) {
            return new LeakDetectionProfileJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.topologyAssetType(),
                        model.topologyAssetId(),
                        model.topologyAssetCode(),
                        model.topologyAssetNameSnapshot(),
                        model.methodId(),
                        model.configurationJson(),
                        model.status(),
                        model.createdByActorId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static LeakDetectionProfile toDomain(LeakDetectionProfileJpaEntity entity) {
            return new LeakDetectionProfile(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.topologyAssetType(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCode(),
                        entity.topologyAssetNameSnapshot(),
                        entity.methodId(),
                        entity.configurationJson(),
                        entity.status(),
                        entity.createdByActorId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static LeakDetectionMethodCatalogJpaEntity toEntity(LeakDetectionMethodCatalog model) {
            return new LeakDetectionMethodCatalogJpaEntity(
                        model.id(),
                        model.code(),
                        model.methodFamily(),
                        model.description(),
                        model.status(),
                        model.systemDefined(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static LeakDetectionMethodCatalog toDomain(LeakDetectionMethodCatalogJpaEntity entity) {
            return new LeakDetectionMethodCatalog(
                        entity.id(),
                        entity.code(),
                        entity.methodFamily(),
                        entity.description(),
                        entity.status(),
                        entity.systemDefined(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static LeakDetectionMethodTranslationJpaEntity toEntity(LeakDetectionMethodTranslation model) {
            return new LeakDetectionMethodTranslationJpaEntity(
                        model.id(),
                        model.methodId(),
                        model.locale(),
                        model.name(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static LeakDetectionMethodTranslation toDomain(LeakDetectionMethodTranslationJpaEntity entity) {
            return new LeakDetectionMethodTranslation(
                        entity.id(),
                        entity.methodId(),
                        entity.locale(),
                        entity.name(),
                        entity.description(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static LeakDetectionRuleJpaEntity toEntity(LeakDetectionRule model) {
            return new LeakDetectionRuleJpaEntity(
                        model.id(),
                        model.profileId(),
                        model.methodId(),
                        model.code(),
                        model.nameFr(),
                        model.ruleType(),
                        model.expression(),
                        model.parameterJson(),
                        model.thresholdValue(),
                        model.unitId(),
                        model.status(),
                        model.validFrom(),
                        model.validTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static LeakDetectionRule toDomain(LeakDetectionRuleJpaEntity entity) {
            return new LeakDetectionRule(
                        entity.id(),
                        entity.profileId(),
                        entity.methodId(),
                        entity.code(),
                        entity.nameFr(),
                        entity.ruleType(),
                        entity.expression(),
                        entity.parameterJson(),
                        entity.thresholdValue(),
                        entity.unitId(),
                        entity.status(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static LeakDetectionRunJpaEntity toEntity(LeakDetectionRun model) {
            return new LeakDetectionRunJpaEntity(
                        model.id(),
                        model.profileId(),
                        model.methodId(),
                        model.runCode(),
                        model.evaluationStart(),
                        model.evaluationEnd(),
                        model.status(),
                        model.candidateCount(),
                        model.failureReason(),
                        model.correlationId(),
                        model.createdAt()
            );
        }

        public static LeakDetectionRun toDomain(LeakDetectionRunJpaEntity entity) {
            return new LeakDetectionRun(
                        entity.id(),
                        entity.profileId(),
                        entity.methodId(),
                        entity.runCode(),
                        entity.evaluationStart(),
                        entity.evaluationEnd(),
                        entity.status(),
                        entity.candidateCount(),
                        entity.failureReason(),
                        entity.correlationId(),
                        entity.createdAt()
            );
        }

        public static LeakCandidateJpaEntity toEntity(LeakCandidate model) {
            return new LeakCandidateJpaEntity(
                        model.id(),
                        model.runId(),
                        model.profileId(),
                        model.candidateNumber(),
                        model.topologyAssetType(),
                        model.topologyAssetId(),
                        model.topologyAssetCode(),
                        model.topologyAssetNameSnapshot(),
                        model.suspectedAt(),
                        model.firstEvidenceAt(),
                        model.confidenceScore(),
                        model.severityLevel(),
                        model.status(),
                        model.summary(),
                        model.correlationId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static LeakCandidate toDomain(LeakCandidateJpaEntity entity) {
            return new LeakCandidate(
                        entity.id(),
                        entity.runId(),
                        entity.profileId(),
                        entity.candidateNumber(),
                        entity.topologyAssetType(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCode(),
                        entity.topologyAssetNameSnapshot(),
                        entity.suspectedAt(),
                        entity.firstEvidenceAt(),
                        entity.confidenceScore(),
                        entity.severityLevel(),
                        entity.status(),
                        entity.summary(),
                        entity.correlationId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static LeakEvidenceLinkJpaEntity toEntity(LeakEvidenceLink model) {
            return new LeakEvidenceLinkJpaEntity(
                        model.id(),
                        model.candidateId(),
                        model.evidenceType(),
                        model.evidenceReferenceId(),
                        model.evidenceCodeSnapshot(),
                        model.evidenceNameSnapshot(),
                        model.evidenceDirection(),
                        model.weight(),
                        model.description(),
                        model.createdAt()
            );
        }

        public static LeakEvidenceLink toDomain(LeakEvidenceLinkJpaEntity entity) {
            return new LeakEvidenceLink(
                        entity.id(),
                        entity.candidateId(),
                        entity.evidenceType(),
                        entity.evidenceReferenceId(),
                        entity.evidenceCodeSnapshot(),
                        entity.evidenceNameSnapshot(),
                        entity.evidenceDirection(),
                        entity.weight(),
                        entity.description(),
                        entity.createdAt()
            );
        }

        public static LeakLocalizationEstimateJpaEntity toEntity(LeakLocalizationEstimate model) {
            return new LeakLocalizationEstimateJpaEntity(
                        model.id(),
                        model.candidateId(),
                        model.topologyAssetType(),
                        model.topologyAssetId(),
                        model.topologyAssetCode(),
                        model.estimatedKilometerPoint(),
                        model.uncertaintyRadiusMeters(),
                        model.latitude(),
                        model.longitude(),
                        model.confidenceScore(),
                        model.methodId(),
                        model.estimatedAt(),
                        model.notes()
            );
        }

        public static LeakLocalizationEstimate toDomain(LeakLocalizationEstimateJpaEntity entity) {
            return new LeakLocalizationEstimate(
                        entity.id(),
                        entity.candidateId(),
                        entity.topologyAssetType(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCode(),
                        entity.estimatedKilometerPoint(),
                        entity.uncertaintyRadiusMeters(),
                        entity.latitude(),
                        entity.longitude(),
                        entity.confidenceScore(),
                        entity.methodId(),
                        entity.estimatedAt(),
                        entity.notes()
            );
        }

        public static LeakSeverityAssessmentJpaEntity toEntity(LeakSeverityAssessment model) {
            return new LeakSeverityAssessmentJpaEntity(
                        model.id(),
                        model.candidateId(),
                        model.severityLevel(),
                        model.confidenceScore(),
                        model.estimatedLeakRate(),
                        model.leakRateUnitId(),
                        model.estimatedVolumeLoss(),
                        model.volumeUnitId(),
                        model.assessmentReason(),
                        model.assessedByActorId(),
                        model.assessedAt()
            );
        }

        public static LeakSeverityAssessment toDomain(LeakSeverityAssessmentJpaEntity entity) {
            return new LeakSeverityAssessment(
                        entity.id(),
                        entity.candidateId(),
                        entity.severityLevel(),
                        entity.confidenceScore(),
                        entity.estimatedLeakRate(),
                        entity.leakRateUnitId(),
                        entity.estimatedVolumeLoss(),
                        entity.volumeUnitId(),
                        entity.assessmentReason(),
                        entity.assessedByActorId(),
                        entity.assessedAt()
            );
        }

        public static LeakVerificationActionJpaEntity toEntity(LeakVerificationAction model) {
            return new LeakVerificationActionJpaEntity(
                        model.id(),
                        model.candidateId(),
                        model.caseId(),
                        model.actionType(),
                        model.assignedOrganizationUnitId(),
                        model.assignedActorId(),
                        model.status(),
                        model.requestedAt(),
                        model.startedAt(),
                        model.completedAt(),
                        model.resultText(),
                        model.correlationId()
            );
        }

        public static LeakVerificationAction toDomain(LeakVerificationActionJpaEntity entity) {
            return new LeakVerificationAction(
                        entity.id(),
                        entity.candidateId(),
                        entity.caseId(),
                        entity.actionType(),
                        entity.assignedOrganizationUnitId(),
                        entity.assignedActorId(),
                        entity.status(),
                        entity.requestedAt(),
                        entity.startedAt(),
                        entity.completedAt(),
                        entity.resultText(),
                        entity.correlationId()
            );
        }

        public static LeakDetectionCaseJpaEntity toEntity(LeakDetectionCase model) {
            return new LeakDetectionCaseJpaEntity(
                        model.id(),
                        model.caseNumber(),
                        model.primaryCandidateId(),
                        model.topologyAssetType(),
                        model.topologyAssetId(),
                        model.topologyAssetCode(),
                        model.owningOrganizationUnitId(),
                        model.status(),
                        model.severityLevel(),
                        model.confidenceScore(),
                        model.openedAt(),
                        model.closedAt(),
                        model.openedByActorId(),
                        model.closedByActorId(),
                        model.closureReasonId(),
                        model.correlationId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static LeakDetectionCase toDomain(LeakDetectionCaseJpaEntity entity) {
            return new LeakDetectionCase(
                        entity.id(),
                        entity.caseNumber(),
                        entity.primaryCandidateId(),
                        entity.topologyAssetType(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCode(),
                        entity.owningOrganizationUnitId(),
                        entity.status(),
                        entity.severityLevel(),
                        entity.confidenceScore(),
                        entity.openedAt(),
                        entity.closedAt(),
                        entity.openedByActorId(),
                        entity.closedByActorId(),
                        entity.closureReasonId(),
                        entity.correlationId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static LeakCaseStatusHistoryJpaEntity toEntity(LeakCaseStatusHistory model) {
            return new LeakCaseStatusHistoryJpaEntity(
                        model.id(),
                        model.caseId(),
                        model.oldStatus(),
                        model.newStatus(),
                        model.reasonId(),
                        model.reasonText(),
                        model.changedByActorId(),
                        model.changedAt(),
                        model.correlationId()
            );
        }

        public static LeakCaseStatusHistory toDomain(LeakCaseStatusHistoryJpaEntity entity) {
            return new LeakCaseStatusHistory(
                        entity.id(),
                        entity.caseId(),
                        entity.oldStatus(),
                        entity.newStatus(),
                        entity.reasonId(),
                        entity.reasonText(),
                        entity.changedByActorId(),
                        entity.changedAt(),
                        entity.correlationId()
            );
        }

        public static LeakEscalationReferenceJpaEntity toEntity(LeakEscalationReference model) {
            return new LeakEscalationReferenceJpaEntity(
                        model.id(),
                        model.caseId(),
                        model.candidateId(),
                        model.targetType(),
                        model.targetReferenceId(),
                        model.targetCodeSnapshot(),
                        model.targetNameSnapshot(),
                        model.status(),
                        model.escalatedByActorId(),
                        model.escalatedAt(),
                        model.reasonText(),
                        model.correlationId()
            );
        }

        public static LeakEscalationReference toDomain(LeakEscalationReferenceJpaEntity entity) {
            return new LeakEscalationReference(
                        entity.id(),
                        entity.caseId(),
                        entity.candidateId(),
                        entity.targetType(),
                        entity.targetReferenceId(),
                        entity.targetCodeSnapshot(),
                        entity.targetNameSnapshot(),
                        entity.status(),
                        entity.escalatedByActorId(),
                        entity.escalatedAt(),
                        entity.reasonText(),
                        entity.correlationId()
            );
        }

        public static LeakDismissalReasonJpaEntity toEntity(LeakDismissalReason model) {
            return new LeakDismissalReasonJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.description(),
                        model.active(),
                        model.sortOrder(),
                        model.systemDefined(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static LeakDismissalReason toDomain(LeakDismissalReasonJpaEntity entity) {
            return new LeakDismissalReason(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.description(),
                        entity.active(),
                        entity.sortOrder(),
                        entity.systemDefined(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

}
