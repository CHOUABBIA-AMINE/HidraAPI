/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskPersistenceMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.mapper
 *
 * @Description : Maps risk domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.risk.domain.model.*;
import dz.sh.hidra.modules.risk.infrastructure.persistence.entity.*;

/**
 * Maps risk domain models to JPA entities.
 */
public final class RiskPersistenceMapper {

    private RiskPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static RiskRegisterJpaEntity toEntity(RiskRegister model) {
            return new RiskRegisterJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.description(),
                        model.registerTypeId(),
                        model.ownerOrganizationUnitId(),
                        model.ownerOrganizationUnitNameSnapshot(),
                        model.scopeType(),
                        model.scopeId(),
                        model.scopeCodeSnapshot(),
                        model.scopeLabelSnapshot(),
                        model.status(),
                        model.reviewFrequencyId(),
                        model.effectiveFrom(),
                        model.effectiveTo(),
                        model.createdByActorId(),
                        model.createdByDisplayNameSnapshot(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static RiskRegister toDomain(RiskRegisterJpaEntity entity) {
            return new RiskRegister(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.description(),
                        entity.registerTypeId(),
                        entity.ownerOrganizationUnitId(),
                        entity.ownerOrganizationUnitNameSnapshot(),
                        entity.scopeType(),
                        entity.scopeId(),
                        entity.scopeCodeSnapshot(),
                        entity.scopeLabelSnapshot(),
                        entity.status(),
                        entity.reviewFrequencyId(),
                        entity.effectiveFrom(),
                        entity.effectiveTo(),
                        entity.createdByActorId(),
                        entity.createdByDisplayNameSnapshot(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static RiskAssessmentJpaEntity toEntity(RiskAssessment model) {
            return new RiskAssessmentJpaEntity(
                        model.id(),
                        model.riskRegisterId(),
                        model.assessmentNumber(),
                        model.title(),
                        model.description(),
                        model.assessmentTypeId(),
                        model.methodologyId(),
                        model.scopeId(),
                        model.riskScenarioId(),
                        model.status(),
                        model.assessmentDate(),
                        model.validFrom(),
                        model.validTo(),
                        model.assessedByActorId(),
                        model.assessedByDisplayNameSnapshot(),
                        model.reviewedByActorId(),
                        model.reviewedByDisplayNameSnapshot(),
                        model.approvedByActorId(),
                        model.approvedByDisplayNameSnapshot(),
                        model.approvedAt(),
                        model.inherentLikelihoodId(),
                        model.inherentConsequenceId(),
                        model.inherentScore(),
                        model.inherentRatingId(),
                        model.residualLikelihoodId(),
                        model.residualConsequenceId(),
                        model.residualScore(),
                        model.residualRatingId(),
                        model.confidenceLevelId(),
                        model.uncertaintyNote(),
                        model.workflowReferenceId(),
                        model.auditReferenceId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static RiskAssessment toDomain(RiskAssessmentJpaEntity entity) {
            return new RiskAssessment(
                        entity.id(),
                        entity.riskRegisterId(),
                        entity.assessmentNumber(),
                        entity.title(),
                        entity.description(),
                        entity.assessmentTypeId(),
                        entity.methodologyId(),
                        entity.scopeId(),
                        entity.riskScenarioId(),
                        entity.status(),
                        entity.assessmentDate(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.assessedByActorId(),
                        entity.assessedByDisplayNameSnapshot(),
                        entity.reviewedByActorId(),
                        entity.reviewedByDisplayNameSnapshot(),
                        entity.approvedByActorId(),
                        entity.approvedByDisplayNameSnapshot(),
                        entity.approvedAt(),
                        entity.inherentLikelihoodId(),
                        entity.inherentConsequenceId(),
                        entity.inherentScore(),
                        entity.inherentRatingId(),
                        entity.residualLikelihoodId(),
                        entity.residualConsequenceId(),
                        entity.residualScore(),
                        entity.residualRatingId(),
                        entity.confidenceLevelId(),
                        entity.uncertaintyNote(),
                        entity.workflowReferenceId(),
                        entity.auditReferenceId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static RiskAssessmentScopeJpaEntity toEntity(RiskAssessmentScope model) {
            return new RiskAssessmentScopeJpaEntity(
                        model.id(),
                        model.riskAssessmentId(),
                        model.scopeType(),
                        model.scopeId(),
                        model.scopeCodeSnapshot(),
                        model.scopeLabelSnapshot(),
                        model.topologySnapshotId(),
                        model.operationalPeriodStart(),
                        model.operationalPeriodEnd(),
                        model.included(),
                        model.scopeNote(),
                        model.createdAt()
            );
        }

        public static RiskAssessmentScope toDomain(RiskAssessmentScopeJpaEntity entity) {
            return new RiskAssessmentScope(
                        entity.id(),
                        entity.riskAssessmentId(),
                        entity.scopeType(),
                        entity.scopeId(),
                        entity.scopeCodeSnapshot(),
                        entity.scopeLabelSnapshot(),
                        entity.topologySnapshotId(),
                        entity.operationalPeriodStart(),
                        entity.operationalPeriodEnd(),
                        entity.included(),
                        entity.scopeNote(),
                        entity.createdAt()
            );
        }

        public static RiskSourceJpaEntity toEntity(RiskSource model) {
            return new RiskSourceJpaEntity(
                        model.id(),
                        model.riskAssessmentId(),
                        model.sourceModule(),
                        model.sourceType(),
                        model.sourceId(),
                        model.sourceCodeSnapshot(),
                        model.sourceLabelSnapshot(),
                        model.sourceObservedAt(),
                        model.sourceSeveritySnapshot(),
                        model.sourceConfidenceSnapshot(),
                        model.createdAt()
            );
        }

        public static RiskSource toDomain(RiskSourceJpaEntity entity) {
            return new RiskSource(
                        entity.id(),
                        entity.riskAssessmentId(),
                        entity.sourceModule(),
                        entity.sourceType(),
                        entity.sourceId(),
                        entity.sourceCodeSnapshot(),
                        entity.sourceLabelSnapshot(),
                        entity.sourceObservedAt(),
                        entity.sourceSeveritySnapshot(),
                        entity.sourceConfidenceSnapshot(),
                        entity.createdAt()
            );
        }

        public static RiskScenarioJpaEntity toEntity(RiskScenario model) {
            return new RiskScenarioJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.description(),
                        model.scenarioTypeId(),
                        model.threatId(),
                        model.primaryConsequenceCategoryId(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static RiskScenario toDomain(RiskScenarioJpaEntity entity) {
            return new RiskScenario(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.description(),
                        entity.scenarioTypeId(),
                        entity.threatId(),
                        entity.primaryConsequenceCategoryId(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static RiskThreatJpaEntity toEntity(RiskThreat model) {
            return new RiskThreatJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.description(),
                        model.threatCategoryId(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static RiskThreat toDomain(RiskThreatJpaEntity entity) {
            return new RiskThreat(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.description(),
                        entity.threatCategoryId(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static RiskConsequenceJpaEntity toEntity(RiskConsequence model) {
            return new RiskConsequenceJpaEntity(
                        model.id(),
                        model.riskAssessmentId(),
                        model.categoryId(),
                        model.consequenceLevelId(),
                        model.description(),
                        model.peopleImpactLevelId(),
                        model.environmentImpactLevelId(),
                        model.productionImpactLevelId(),
                        model.assetImpactLevelId(),
                        model.financialImpactLevelId(),
                        model.reputationImpactLevelId(),
                        model.complianceImpactLevelId(),
                        model.estimatedCost(),
                        model.currencyCode(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static RiskConsequence toDomain(RiskConsequenceJpaEntity entity) {
            return new RiskConsequence(
                        entity.id(),
                        entity.riskAssessmentId(),
                        entity.categoryId(),
                        entity.consequenceLevelId(),
                        entity.description(),
                        entity.peopleImpactLevelId(),
                        entity.environmentImpactLevelId(),
                        entity.productionImpactLevelId(),
                        entity.assetImpactLevelId(),
                        entity.financialImpactLevelId(),
                        entity.reputationImpactLevelId(),
                        entity.complianceImpactLevelId(),
                        entity.estimatedCost(),
                        entity.currencyCode(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static RiskLikelihoodJpaEntity toEntity(RiskLikelihood model) {
            return new RiskLikelihoodJpaEntity(
                        model.id(),
                        model.riskAssessmentId(),
                        model.likelihoodLevelId(),
                        model.probabilityValue(),
                        model.frequencyEstimate(),
                        model.frequencyUnitId(),
                        model.likelihoodBasisId(),
                        model.confidenceLevelId(),
                        model.evidenceSummary(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static RiskLikelihood toDomain(RiskLikelihoodJpaEntity entity) {
            return new RiskLikelihood(
                        entity.id(),
                        entity.riskAssessmentId(),
                        entity.likelihoodLevelId(),
                        entity.probabilityValue(),
                        entity.frequencyEstimate(),
                        entity.frequencyUnitId(),
                        entity.likelihoodBasisId(),
                        entity.confidenceLevelId(),
                        entity.evidenceSummary(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static RiskExposureJpaEntity toEntity(RiskExposure model) {
            return new RiskExposureJpaEntity(
                        model.id(),
                        model.riskAssessmentId(),
                        model.exposureTypeId(),
                        model.exposedObjectType(),
                        model.exposedObjectId(),
                        model.exposedObjectCodeSnapshot(),
                        model.exposedObjectLabelSnapshot(),
                        model.exposureStart(),
                        model.exposureEnd(),
                        model.exposureMagnitude(),
                        model.exposureUnitId(),
                        model.populationExposure(),
                        model.environmentalSensitivityId(),
                        model.productionCriticalityId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static RiskExposure toDomain(RiskExposureJpaEntity entity) {
            return new RiskExposure(
                        entity.id(),
                        entity.riskAssessmentId(),
                        entity.exposureTypeId(),
                        entity.exposedObjectType(),
                        entity.exposedObjectId(),
                        entity.exposedObjectCodeSnapshot(),
                        entity.exposedObjectLabelSnapshot(),
                        entity.exposureStart(),
                        entity.exposureEnd(),
                        entity.exposureMagnitude(),
                        entity.exposureUnitId(),
                        entity.populationExposure(),
                        entity.environmentalSensitivityId(),
                        entity.productionCriticalityId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static RiskMatrixJpaEntity toEntity(RiskMatrix model) {
            return new RiskMatrixJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.description(),
                        model.matrixTypeId(),
                        model.version(),
                        model.status(),
                        model.validFrom(),
                        model.validTo(),
                        model.createdByActorId(),
                        model.approvedByActorId(),
                        model.approvedAt(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static RiskMatrix toDomain(RiskMatrixJpaEntity entity) {
            return new RiskMatrix(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.description(),
                        entity.matrixTypeId(),
                        entity.version(),
                        entity.status(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.createdByActorId(),
                        entity.approvedByActorId(),
                        entity.approvedAt(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static RiskMatrixCellJpaEntity toEntity(RiskMatrixCell model) {
            return new RiskMatrixCellJpaEntity(
                        model.id(),
                        model.riskMatrixId(),
                        model.likelihoodLevelId(),
                        model.consequenceLevelId(),
                        model.scoreValue(),
                        model.ratingId(),
                        model.colorCode(),
                        model.requiresTreatment(),
                        model.requiresApproval(),
                        model.requiresExecutiveAcceptance(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static RiskMatrixCell toDomain(RiskMatrixCellJpaEntity entity) {
            return new RiskMatrixCell(
                        entity.id(),
                        entity.riskMatrixId(),
                        entity.likelihoodLevelId(),
                        entity.consequenceLevelId(),
                        entity.scoreValue(),
                        entity.ratingId(),
                        entity.colorCode(),
                        entity.requiresTreatment(),
                        entity.requiresApproval(),
                        entity.requiresExecutiveAcceptance(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static RiskScoreJpaEntity toEntity(RiskScore model) {
            return new RiskScoreJpaEntity(
                        model.id(),
                        model.riskAssessmentId(),
                        model.scoreType(),
                        model.riskMatrixId(),
                        model.likelihoodLevelId(),
                        model.consequenceLevelId(),
                        model.scoreValue(),
                        model.ratingId(),
                        model.ratingLabelSnapshot(),
                        model.calculatedAt(),
                        model.calculationMethod(),
                        model.explanation(),
                        model.createdAt()
            );
        }

        public static RiskScore toDomain(RiskScoreJpaEntity entity) {
            return new RiskScore(
                        entity.id(),
                        entity.riskAssessmentId(),
                        entity.scoreType(),
                        entity.riskMatrixId(),
                        entity.likelihoodLevelId(),
                        entity.consequenceLevelId(),
                        entity.scoreValue(),
                        entity.ratingId(),
                        entity.ratingLabelSnapshot(),
                        entity.calculatedAt(),
                        entity.calculationMethod(),
                        entity.explanation(),
                        entity.createdAt()
            );
        }

        public static RiskRatingJpaEntity toEntity(RiskRating model) {
            return new RiskRatingJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.description(),
                        model.severityOrder(),
                        model.requiresTreatment(),
                        model.requiresApproval(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static RiskRating toDomain(RiskRatingJpaEntity entity) {
            return new RiskRating(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.description(),
                        entity.severityOrder(),
                        entity.requiresTreatment(),
                        entity.requiresApproval(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static RiskControlJpaEntity toEntity(RiskControl model) {
            return new RiskControlJpaEntity(
                        model.id(),
                        model.riskAssessmentId(),
                        model.controlCode(),
                        model.controlName(),
                        model.controlTypeId(),
                        model.controlOwnerOrganizationUnitId(),
                        model.controlOwnerNameSnapshot(),
                        model.effectivenessLevelId(),
                        model.effectivenessJustification(),
                        model.verified(),
                        model.verifiedByActorId(),
                        model.verifiedAt(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static RiskControl toDomain(RiskControlJpaEntity entity) {
            return new RiskControl(
                        entity.id(),
                        entity.riskAssessmentId(),
                        entity.controlCode(),
                        entity.controlName(),
                        entity.controlTypeId(),
                        entity.controlOwnerOrganizationUnitId(),
                        entity.controlOwnerNameSnapshot(),
                        entity.effectivenessLevelId(),
                        entity.effectivenessJustification(),
                        entity.verified(),
                        entity.verifiedByActorId(),
                        entity.verifiedAt(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static RiskTreatmentPlanJpaEntity toEntity(RiskTreatmentPlan model) {
            return new RiskTreatmentPlanJpaEntity(
                        model.id(),
                        model.riskAssessmentId(),
                        model.treatmentStrategyId(),
                        model.title(),
                        model.description(),
                        model.ownerOrganizationUnitId(),
                        model.ownerOrganizationUnitNameSnapshot(),
                        model.ownerActorId(),
                        model.ownerDisplayNameSnapshot(),
                        model.status(),
                        model.targetResidualRatingId(),
                        model.targetCompletionDate(),
                        model.workflowReferenceId(),
                        model.auditReferenceId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static RiskTreatmentPlan toDomain(RiskTreatmentPlanJpaEntity entity) {
            return new RiskTreatmentPlan(
                        entity.id(),
                        entity.riskAssessmentId(),
                        entity.treatmentStrategyId(),
                        entity.title(),
                        entity.description(),
                        entity.ownerOrganizationUnitId(),
                        entity.ownerOrganizationUnitNameSnapshot(),
                        entity.ownerActorId(),
                        entity.ownerDisplayNameSnapshot(),
                        entity.status(),
                        entity.targetResidualRatingId(),
                        entity.targetCompletionDate(),
                        entity.workflowReferenceId(),
                        entity.auditReferenceId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static RiskTreatmentActionJpaEntity toEntity(RiskTreatmentAction model) {
            return new RiskTreatmentActionJpaEntity(
                        model.id(),
                        model.riskTreatmentPlanId(),
                        model.actionCode(),
                        model.title(),
                        model.description(),
                        model.actionTypeId(),
                        model.ownerActorId(),
                        model.ownerDisplayNameSnapshot(),
                        model.ownerOrganizationUnitId(),
                        model.ownerOrganizationUnitNameSnapshot(),
                        model.targetDate(),
                        model.completedAt(),
                        model.verificationRequired(),
                        model.verifiedByActorId(),
                        model.verifiedAt(),
                        model.status(),
                        model.linkedWorkOrderId(),
                        model.linkedWorkflowTaskId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static RiskTreatmentAction toDomain(RiskTreatmentActionJpaEntity entity) {
            return new RiskTreatmentAction(
                        entity.id(),
                        entity.riskTreatmentPlanId(),
                        entity.actionCode(),
                        entity.title(),
                        entity.description(),
                        entity.actionTypeId(),
                        entity.ownerActorId(),
                        entity.ownerDisplayNameSnapshot(),
                        entity.ownerOrganizationUnitId(),
                        entity.ownerOrganizationUnitNameSnapshot(),
                        entity.targetDate(),
                        entity.completedAt(),
                        entity.verificationRequired(),
                        entity.verifiedByActorId(),
                        entity.verifiedAt(),
                        entity.status(),
                        entity.linkedWorkOrderId(),
                        entity.linkedWorkflowTaskId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static RiskMitigationMeasureJpaEntity toEntity(RiskMitigationMeasure model) {
            return new RiskMitigationMeasureJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.description(),
                        model.mitigationTypeId(),
                        model.applicableThreatTypeId(),
                        model.applicableAssetTypeId(),
                        model.expectedEffectOnLikelihood(),
                        model.expectedEffectOnConsequence(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static RiskMitigationMeasure toDomain(RiskMitigationMeasureJpaEntity entity) {
            return new RiskMitigationMeasure(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.description(),
                        entity.mitigationTypeId(),
                        entity.applicableThreatTypeId(),
                        entity.applicableAssetTypeId(),
                        entity.expectedEffectOnLikelihood(),
                        entity.expectedEffectOnConsequence(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static RiskAcceptanceJpaEntity toEntity(RiskAcceptance model) {
            return new RiskAcceptanceJpaEntity(
                        model.id(),
                        model.riskAssessmentId(),
                        model.acceptanceNumber(),
                        model.acceptedRatingId(),
                        model.acceptedScore(),
                        model.acceptanceReasonId(),
                        model.acceptanceJustification(),
                        model.acceptedByActorId(),
                        model.acceptedByDisplayNameSnapshot(),
                        model.acceptedByOrganizationUnitId(),
                        model.acceptedByOrganizationUnitNameSnapshot(),
                        model.acceptedAt(),
                        model.validUntil(),
                        model.reviewRequired(),
                        model.status(),
                        model.workflowReferenceId(),
                        model.auditReferenceId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static RiskAcceptance toDomain(RiskAcceptanceJpaEntity entity) {
            return new RiskAcceptance(
                        entity.id(),
                        entity.riskAssessmentId(),
                        entity.acceptanceNumber(),
                        entity.acceptedRatingId(),
                        entity.acceptedScore(),
                        entity.acceptanceReasonId(),
                        entity.acceptanceJustification(),
                        entity.acceptedByActorId(),
                        entity.acceptedByDisplayNameSnapshot(),
                        entity.acceptedByOrganizationUnitId(),
                        entity.acceptedByOrganizationUnitNameSnapshot(),
                        entity.acceptedAt(),
                        entity.validUntil(),
                        entity.reviewRequired(),
                        entity.status(),
                        entity.workflowReferenceId(),
                        entity.auditReferenceId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ResidualRiskAssessmentJpaEntity toEntity(ResidualRiskAssessment model) {
            return new ResidualRiskAssessmentJpaEntity(
                        model.id(),
                        model.riskAssessmentId(),
                        model.treatmentPlanId(),
                        model.reassessmentDate(),
                        model.residualLikelihoodId(),
                        model.residualConsequenceId(),
                        model.residualScore(),
                        model.residualRatingId(),
                        model.residualConfidenceLevelId(),
                        model.residualJustification(),
                        model.assessedByActorId(),
                        model.approvedByActorId(),
                        model.approvedAt(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ResidualRiskAssessment toDomain(ResidualRiskAssessmentJpaEntity entity) {
            return new ResidualRiskAssessment(
                        entity.id(),
                        entity.riskAssessmentId(),
                        entity.treatmentPlanId(),
                        entity.reassessmentDate(),
                        entity.residualLikelihoodId(),
                        entity.residualConsequenceId(),
                        entity.residualScore(),
                        entity.residualRatingId(),
                        entity.residualConfidenceLevelId(),
                        entity.residualJustification(),
                        entity.assessedByActorId(),
                        entity.approvedByActorId(),
                        entity.approvedAt(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static RiskReviewJpaEntity toEntity(RiskReview model) {
            return new RiskReviewJpaEntity(
                        model.id(),
                        model.riskAssessmentId(),
                        model.reviewTypeId(),
                        model.reviewStatus(),
                        model.reviewDueDate(),
                        model.reviewedAt(),
                        model.reviewedByActorId(),
                        model.reviewedByDisplayNameSnapshot(),
                        model.reviewFinding(),
                        model.ratingChanged(),
                        model.previousRatingId(),
                        model.newRatingId(),
                        model.nextReviewDueDate(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static RiskReview toDomain(RiskReviewJpaEntity entity) {
            return new RiskReview(
                        entity.id(),
                        entity.riskAssessmentId(),
                        entity.reviewTypeId(),
                        entity.reviewStatus(),
                        entity.reviewDueDate(),
                        entity.reviewedAt(),
                        entity.reviewedByActorId(),
                        entity.reviewedByDisplayNameSnapshot(),
                        entity.reviewFinding(),
                        entity.ratingChanged(),
                        entity.previousRatingId(),
                        entity.newRatingId(),
                        entity.nextReviewDueDate(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static RiskEvidenceLinkJpaEntity toEntity(RiskEvidenceLink model) {
            return new RiskEvidenceLinkJpaEntity(
                        model.id(),
                        model.riskAssessmentId(),
                        model.evidenceModule(),
                        model.evidenceType(),
                        model.evidenceId(),
                        model.evidenceCodeSnapshot(),
                        model.evidenceLabelSnapshot(),
                        model.evidenceTimestamp(),
                        model.evidenceHash(),
                        model.evidenceSummary(),
                        model.createdAt()
            );
        }

        public static RiskEvidenceLink toDomain(RiskEvidenceLinkJpaEntity entity) {
            return new RiskEvidenceLink(
                        entity.id(),
                        entity.riskAssessmentId(),
                        entity.evidenceModule(),
                        entity.evidenceType(),
                        entity.evidenceId(),
                        entity.evidenceCodeSnapshot(),
                        entity.evidenceLabelSnapshot(),
                        entity.evidenceTimestamp(),
                        entity.evidenceHash(),
                        entity.evidenceSummary(),
                        entity.createdAt()
            );
        }

        public static RiskAggregationSnapshotJpaEntity toEntity(RiskAggregationSnapshot model) {
            return new RiskAggregationSnapshotJpaEntity(
                        model.id(),
                        model.scopeType(),
                        model.scopeId(),
                        model.scopeCodeSnapshot(),
                        model.scopeLabelSnapshot(),
                        model.snapshotDate(),
                        model.riskMatrixId(),
                        model.totalRiskCount(),
                        model.criticalRiskCount(),
                        model.highRiskCount(),
                        model.mediumRiskCount(),
                        model.lowRiskCount(),
                        model.averageRiskScore(),
                        model.maximumRiskScore(),
                        model.openTreatmentCount(),
                        model.overdueTreatmentCount(),
                        model.acceptedRiskCount(),
                        model.createdAt()
            );
        }

        public static RiskAggregationSnapshot toDomain(RiskAggregationSnapshotJpaEntity entity) {
            return new RiskAggregationSnapshot(
                        entity.id(),
                        entity.scopeType(),
                        entity.scopeId(),
                        entity.scopeCodeSnapshot(),
                        entity.scopeLabelSnapshot(),
                        entity.snapshotDate(),
                        entity.riskMatrixId(),
                        entity.totalRiskCount(),
                        entity.criticalRiskCount(),
                        entity.highRiskCount(),
                        entity.mediumRiskCount(),
                        entity.lowRiskCount(),
                        entity.averageRiskScore(),
                        entity.maximumRiskScore(),
                        entity.openTreatmentCount(),
                        entity.overdueTreatmentCount(),
                        entity.acceptedRiskCount(),
                        entity.createdAt()
            );
        }

        public static RiskCatalogEntryJpaEntity toEntity(RiskCatalogEntry model) {
            return new RiskCatalogEntryJpaEntity(
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

        public static RiskCatalogEntry toDomain(RiskCatalogEntryJpaEntity entity) {
            return new RiskCatalogEntry(
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

        public static RiskCatalogTranslationJpaEntity toEntity(RiskCatalogTranslation model) {
            return new RiskCatalogTranslationJpaEntity(
                        model.id(),
                        model.catalogEntryId(),
                        model.locale(),
                        model.name(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static RiskCatalogTranslation toDomain(RiskCatalogTranslationJpaEntity entity) {
            return new RiskCatalogTranslation(
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
