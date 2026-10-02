/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HsePersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.mapper
 *
 * @Description : Maps HSE domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.hse.domain.model.*;
import dz.sh.hidra.modules.hse.infrastructure.persistence.entity.*;

/**
 * Maps HSE domain models to JPA entities.
 */
public final class HsePersistenceMapper {

    private HsePersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static HseCaseJpaEntity toEntity(HseCase model) {
            return new HseCaseJpaEntity(
                        model.id(),
                        model.caseNumber(),
                        model.title(),
                        model.description(),
                        model.caseTypeId(),
                        model.severityId(),
                        model.priorityId(),
                        model.status(),
                        model.sourceType(),
                        model.incidentReferenceId(),
                        model.incidentCodeSnapshot(),
                        model.incidentTitleSnapshot(),
                        model.targetModule(),
                        model.targetTypeCode(),
                        model.targetId(),
                        model.targetCodeSnapshot(),
                        model.targetLabelSnapshot(),
                        model.occurredAt(),
                        model.reportedAt(),
                        model.reportedByActorId(),
                        model.reportedByDisplayNameSnapshot(),
                        model.responsibleOrganizationUnitId(),
                        model.responsibleOrganizationUnitNameSnapshot(),
                        model.workflowInstanceId(),
                        model.auditReferenceId(),
                        model.controlledAt(),
                        model.resolvedAt(),
                        model.closedAt(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static HseCase toDomain(HseCaseJpaEntity entity) {
            return new HseCase(
                        entity.id(),
                        entity.caseNumber(),
                        entity.title(),
                        entity.description(),
                        entity.caseTypeId(),
                        entity.severityId(),
                        entity.priorityId(),
                        entity.status(),
                        entity.sourceType(),
                        entity.incidentReferenceId(),
                        entity.incidentCodeSnapshot(),
                        entity.incidentTitleSnapshot(),
                        entity.targetModule(),
                        entity.targetTypeCode(),
                        entity.targetId(),
                        entity.targetCodeSnapshot(),
                        entity.targetLabelSnapshot(),
                        entity.occurredAt(),
                        entity.reportedAt(),
                        entity.reportedByActorId(),
                        entity.reportedByDisplayNameSnapshot(),
                        entity.responsibleOrganizationUnitId(),
                        entity.responsibleOrganizationUnitNameSnapshot(),
                        entity.workflowInstanceId(),
                        entity.auditReferenceId(),
                        entity.controlledAt(),
                        entity.resolvedAt(),
                        entity.closedAt(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }
        public static HseCorrectivePreventiveActionJpaEntity toEntity(HseCorrectivePreventiveAction model) {
            return new HseCorrectivePreventiveActionJpaEntity(
                        model.id(),
                        model.hseCaseId(),
                        model.actionNumber(),
                        model.actionTypeId(),
                        model.title(),
                        model.description(),
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
                        model.workflowTaskId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static HseCorrectivePreventiveAction toDomain(HseCorrectivePreventiveActionJpaEntity entity) {
            return new HseCorrectivePreventiveAction(
                        entity.id(),
                        entity.hseCaseId(),
                        entity.actionNumber(),
                        entity.actionTypeId(),
                        entity.title(),
                        entity.description(),
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
                        entity.workflowTaskId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static HseClosureJpaEntity toEntity(HseClosure model) {
            return new HseClosureJpaEntity(
                        model.id(),
                        model.hseCaseId(),
                        model.closureSummary(),
                        model.impactAssessed(),
                        model.capaCompleted(),
                        model.evidenceReviewed(),
                        model.regulatoryReviewed(),
                        model.closedByActorId(),
                        model.closedByDisplayNameSnapshot(),
                        model.closedAt(),
                        model.workflowInstanceId()
            );
        }

        public static HseClosure toDomain(HseClosureJpaEntity entity) {
            return new HseClosure(
                        entity.id(),
                        entity.hseCaseId(),
                        entity.closureSummary(),
                        entity.impactAssessed(),
                        entity.capaCompleted(),
                        entity.evidenceReviewed(),
                        entity.regulatoryReviewed(),
                        entity.closedByActorId(),
                        entity.closedByDisplayNameSnapshot(),
                        entity.closedAt(),
                        entity.workflowInstanceId()
            );
        }
        public static PermitToWorkJpaEntity toEntity(PermitToWork model) {
            return new PermitToWorkJpaEntity(
                        model.id(),
                        model.permitNumber(),
                        model.permitTypeId(),
                        model.title(),
                        model.description(),
                        model.targetModule(),
                        model.targetTypeCode(),
                        model.targetId(),
                        model.requestedByActorId(),
                        model.approvedByActorId(),
                        model.validFrom(),
                        model.validTo(),
                        model.status(),
                        model.workflowInstanceId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PermitToWork toDomain(PermitToWorkJpaEntity entity) {
            return new PermitToWork(
                        entity.id(),
                        entity.permitNumber(),
                        entity.permitTypeId(),
                        entity.title(),
                        entity.description(),
                        entity.targetModule(),
                        entity.targetTypeCode(),
                        entity.targetId(),
                        entity.requestedByActorId(),
                        entity.approvedByActorId(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.status(),
                        entity.workflowInstanceId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }
}
