/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
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
}
