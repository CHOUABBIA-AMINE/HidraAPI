/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.mapper
 *
 * @Description : Maps reporting domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.reporting.domain.model.*;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.entity.*;

/**
 * Maps reporting domain models to JPA entities.
 */
public final class ReportingPersistenceMapper {

    private ReportingPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static ReportDefinitionJpaEntity toEntity(ReportDefinition model) {
            return new ReportDefinitionJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.reportCategoryId(),
                        model.ownerModule(),
                        model.description(),
                        model.status(),
                        model.currentTemplateVersionId(),
                        model.requiresApproval(),
                        model.restricted(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ReportDefinition toDomain(ReportDefinitionJpaEntity entity) {
            return new ReportDefinition(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.reportCategoryId(),
                        entity.ownerModule(),
                        entity.description(),
                        entity.status(),
                        entity.currentTemplateVersionId(),
                        entity.requiresApproval(),
                        entity.restricted(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }
        public static ReportRequestJpaEntity toEntity(ReportRequest model) {
            return new ReportRequestJpaEntity(
                        model.id(),
                        model.reportDefinitionId(),
                        model.requestedByActorId(),
                        model.requestedByUsernameSnapshot(),
                        model.requestedByDisplayNameSnapshot(),
                        model.requestedByRoleCodeSnapshot(),
                        model.organizationUnitId(),
                        model.organizationUnitNameSnapshot(),
                        model.requestedAt(),
                        model.purpose(),
                        model.status(),
                        model.correlationId(),
                        model.workflowReferenceId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ReportRequest toDomain(ReportRequestJpaEntity entity) {
            return new ReportRequest(
                        entity.id(),
                        entity.reportDefinitionId(),
                        entity.requestedByActorId(),
                        entity.requestedByUsernameSnapshot(),
                        entity.requestedByDisplayNameSnapshot(),
                        entity.requestedByRoleCodeSnapshot(),
                        entity.organizationUnitId(),
                        entity.organizationUnitNameSnapshot(),
                        entity.requestedAt(),
                        entity.purpose(),
                        entity.status(),
                        entity.correlationId(),
                        entity.workflowReferenceId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }
        public static ReportRunJpaEntity toEntity(ReportRun model) {
            return new ReportRunJpaEntity(
                        model.id(),
                        model.reportRequestId(),
                        model.reportDefinitionId(),
                        model.templateVersionId(),
                        model.status(),
                        model.runMode(),
                        model.queuedAt(),
                        model.startedAt(),
                        model.completedAt(),
                        model.failedAt(),
                        model.failureReason(),
                        model.recordCount(),
                        model.outputCount(),
                        model.executionDurationMs(),
                        model.correlationId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ReportRun toDomain(ReportRunJpaEntity entity) {
            return new ReportRun(
                        entity.id(),
                        entity.reportRequestId(),
                        entity.reportDefinitionId(),
                        entity.templateVersionId(),
                        entity.status(),
                        entity.runMode(),
                        entity.queuedAt(),
                        entity.startedAt(),
                        entity.completedAt(),
                        entity.failedAt(),
                        entity.failureReason(),
                        entity.recordCount(),
                        entity.outputCount(),
                        entity.executionDurationMs(),
                        entity.correlationId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }
        public static ReportOutputArtifactJpaEntity toEntity(ReportOutputArtifact model) {
            return new ReportOutputArtifactJpaEntity(
                        model.id(),
                        model.reportRunId(),
                        model.artifactType(),
                        model.format(),
                        model.fileName(),
                        model.mimeType(),
                        model.storageObjectReferenceId(),
                        model.documentReferenceId(),
                        model.checksum(),
                        model.sizeBytes(),
                        model.generatedAt(),
                        model.expiresAt(),
                        model.createdAt()
            );
        }

        public static ReportOutputArtifact toDomain(ReportOutputArtifactJpaEntity entity) {
            return new ReportOutputArtifact(
                        entity.id(),
                        entity.reportRunId(),
                        entity.artifactType(),
                        entity.format(),
                        entity.fileName(),
                        entity.mimeType(),
                        entity.storageObjectReferenceId(),
                        entity.documentReferenceId(),
                        entity.checksum(),
                        entity.sizeBytes(),
                        entity.generatedAt(),
                        entity.expiresAt(),
                        entity.createdAt()
            );
        }
}
