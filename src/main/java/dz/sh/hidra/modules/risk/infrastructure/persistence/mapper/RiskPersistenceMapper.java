/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
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
}
