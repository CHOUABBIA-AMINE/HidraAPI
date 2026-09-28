/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper
 *
 * @Description : Maps integrity domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.integrity.domain.model.*;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.entity.*;

/**
 * Maps integrity domain models to JPA entities.
 */
public final class IntegrityPersistenceMapper {

    private IntegrityPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static IntegrityProgramJpaEntity toEntity(IntegrityProgram model) {
            return new IntegrityProgramJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.description(),
                        model.programTypeId(),
                        model.ownerOrganizationUnitId(),
                        model.ownerOrganizationUnitNameSnapshot(),
                        model.status(),
                        model.plannedStartAt(),
                        model.plannedEndAt(),
                        model.actualStartAt(),
                        model.actualEndAt(),
                        model.createdByActorId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static IntegrityProgram toDomain(IntegrityProgramJpaEntity entity) {
            return new IntegrityProgram(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.description(),
                        entity.programTypeId(),
                        entity.ownerOrganizationUnitId(),
                        entity.ownerOrganizationUnitNameSnapshot(),
                        entity.status(),
                        entity.plannedStartAt(),
                        entity.plannedEndAt(),
                        entity.actualStartAt(),
                        entity.actualEndAt(),
                        entity.createdByActorId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static IntegrityAssessmentJpaEntity toEntity(IntegrityAssessment model) {
            return new IntegrityAssessmentJpaEntity(
                        model.id(),
                        model.programId(),
                        model.assessmentNumber(),
                        model.title(),
                        model.description(),
                        model.assessmentTypeId(),
                        model.methodologyId(),
                        model.status(),
                        model.assessmentDate(),
                        model.assessedByActorId(),
                        model.reviewedByActorId(),
                        model.approvedByActorId(),
                        model.approvedAt(),
                        model.workflowInstanceId(),
                        model.auditReferenceId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static IntegrityAssessment toDomain(IntegrityAssessmentJpaEntity entity) {
            return new IntegrityAssessment(
                        entity.id(),
                        entity.programId(),
                        entity.assessmentNumber(),
                        entity.title(),
                        entity.description(),
                        entity.assessmentTypeId(),
                        entity.methodologyId(),
                        entity.status(),
                        entity.assessmentDate(),
                        entity.assessedByActorId(),
                        entity.reviewedByActorId(),
                        entity.approvedByActorId(),
                        entity.approvedAt(),
                        entity.workflowInstanceId(),
                        entity.auditReferenceId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }
        public static PipelineDefectJpaEntity toEntity(PipelineDefect model) {
            return new PipelineDefectJpaEntity(
                        model.id(),
                        model.defectNumber(),
                        model.defectTypeId(),
                        model.threatType(),
                        model.status(),
                        model.severity(),
                        model.topologyAssetTypeCode(),
                        model.topologyAssetId(),
                        model.topologyAssetCodeSnapshot(),
                        model.kilometerPoint(),
                        model.latitude(),
                        model.longitude(),
                        model.description(),
                        model.detectedAt(),
                        model.closedAt(),
                        model.sourceFindingId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PipelineDefect toDomain(PipelineDefectJpaEntity entity) {
            return new PipelineDefect(
                        entity.id(),
                        entity.defectNumber(),
                        entity.defectTypeId(),
                        entity.threatType(),
                        entity.status(),
                        entity.severity(),
                        entity.topologyAssetTypeCode(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCodeSnapshot(),
                        entity.kilometerPoint(),
                        entity.latitude(),
                        entity.longitude(),
                        entity.description(),
                        entity.detectedAt(),
                        entity.closedAt(),
                        entity.sourceFindingId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }
        public static IntegrityCaseJpaEntity toEntity(IntegrityCase model) {
            return new IntegrityCaseJpaEntity(
                        model.id(),
                        model.caseNumber(),
                        model.title(),
                        model.description(),
                        model.caseTypeId(),
                        model.status(),
                        model.severityId(),
                        model.topologyAssetTypeCode(),
                        model.topologyAssetId(),
                        model.topologyAssetCodeSnapshot(),
                        model.primaryDefectId(),
                        model.sourceIncidentId(),
                        model.sourceHseCaseId(),
                        model.responsibleOrganizationUnitId(),
                        model.workflowInstanceId(),
                        model.openedAt(),
                        model.closedAt(),
                        model.openedByActorId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static IntegrityCase toDomain(IntegrityCaseJpaEntity entity) {
            return new IntegrityCase(
                        entity.id(),
                        entity.caseNumber(),
                        entity.title(),
                        entity.description(),
                        entity.caseTypeId(),
                        entity.status(),
                        entity.severityId(),
                        entity.topologyAssetTypeCode(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCodeSnapshot(),
                        entity.primaryDefectId(),
                        entity.sourceIncidentId(),
                        entity.sourceHseCaseId(),
                        entity.responsibleOrganizationUnitId(),
                        entity.workflowInstanceId(),
                        entity.openedAt(),
                        entity.closedAt(),
                        entity.openedByActorId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }
}
