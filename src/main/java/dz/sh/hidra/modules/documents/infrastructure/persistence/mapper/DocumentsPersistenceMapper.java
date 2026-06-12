/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsPersistenceMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.mapper
 *
 * @Description : Maps documents domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.documents.domain.model.*;
import dz.sh.hidra.modules.documents.infrastructure.persistence.entity.*;

/**
 * Maps documents domain models to JPA entities.
 */
public final class DocumentsPersistenceMapper {

    private DocumentsPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static DocumentJpaEntity toEntity(Document model) {
            return new DocumentJpaEntity(
                        model.id(),
                        model.code(),
                        model.titleAr(),
                        model.titleFr(),
                        model.titleEn(),
                        model.documentTypeId(),
                        model.documentCategoryId(),
                        model.classificationId(),
                        model.confidentialityLevel(),
                        model.status(),
                        model.currentVersionId(),
                        model.ownerModule(),
                        model.ownerTargetTypeCode(),
                        model.ownerTargetId(),
                        model.ownerTargetCodeSnapshot(),
                        model.ownerTargetLabelSnapshot(),
                        model.createdByActorId(),
                        model.createdByDisplayNameSnapshot(),
                        model.createdAt(),
                        model.updatedAt(),
                        model.archivedAt()
            );
        }

        public static Document toDomain(DocumentJpaEntity entity) {
            return new Document(
                        entity.id(),
                        entity.code(),
                        entity.titleAr(),
                        entity.titleFr(),
                        entity.titleEn(),
                        entity.documentTypeId(),
                        entity.documentCategoryId(),
                        entity.classificationId(),
                        entity.confidentialityLevel(),
                        entity.status(),
                        entity.currentVersionId(),
                        entity.ownerModule(),
                        entity.ownerTargetTypeCode(),
                        entity.ownerTargetId(),
                        entity.ownerTargetCodeSnapshot(),
                        entity.ownerTargetLabelSnapshot(),
                        entity.createdByActorId(),
                        entity.createdByDisplayNameSnapshot(),
                        entity.createdAt(),
                        entity.updatedAt(),
                        entity.archivedAt()
            );
        }

        public static DocumentVersionJpaEntity toEntity(DocumentVersion model) {
            return new DocumentVersionJpaEntity(
                        model.id(),
                        model.documentId(),
                        model.versionNumber(),
                        model.versionLabel(),
                        model.titleAr(),
                        model.titleFr(),
                        model.titleEn(),
                        model.description(),
                        model.storageObjectId(),
                        model.mimeType(),
                        model.originalFilename(),
                        model.fileExtension(),
                        model.fileSizeBytes(),
                        model.checksumAlgorithm(),
                        model.checksumValue(),
                        model.languageCode(),
                        model.documentDate(),
                        model.effectiveFrom(),
                        model.effectiveTo(),
                        model.versionStatus(),
                        model.uploadedByActorId(),
                        model.uploadedByDisplayNameSnapshot(),
                        model.uploadedAt(),
                        model.approvedByWorkflowInstanceId(),
                        model.approvedAt(),
                        model.supersededByVersionId()
            );
        }

        public static DocumentVersion toDomain(DocumentVersionJpaEntity entity) {
            return new DocumentVersion(
                        entity.id(),
                        entity.documentId(),
                        entity.versionNumber(),
                        entity.versionLabel(),
                        entity.titleAr(),
                        entity.titleFr(),
                        entity.titleEn(),
                        entity.description(),
                        entity.storageObjectId(),
                        entity.mimeType(),
                        entity.originalFilename(),
                        entity.fileExtension(),
                        entity.fileSizeBytes(),
                        entity.checksumAlgorithm(),
                        entity.checksumValue(),
                        entity.languageCode(),
                        entity.documentDate(),
                        entity.effectiveFrom(),
                        entity.effectiveTo(),
                        entity.versionStatus(),
                        entity.uploadedByActorId(),
                        entity.uploadedByDisplayNameSnapshot(),
                        entity.uploadedAt(),
                        entity.approvedByWorkflowInstanceId(),
                        entity.approvedAt(),
                        entity.supersededByVersionId()
            );
        }

        public static DocumentStorageObjectJpaEntity toEntity(DocumentStorageObject model) {
            return new DocumentStorageObjectJpaEntity(
                        model.id(),
                        model.storageProviderId(),
                        model.bucketOrContainer(),
                        model.objectKey(),
                        model.objectUri(),
                        model.encrypted(),
                        model.encryptionKeyReference(),
                        model.contentLengthBytes(),
                        model.contentType(),
                        model.checksumAlgorithm(),
                        model.checksumValue(),
                        model.storageStatus(),
                        model.createdAt(),
                        model.verifiedAt()
            );
        }

        public static DocumentStorageObject toDomain(DocumentStorageObjectJpaEntity entity) {
            return new DocumentStorageObject(
                        entity.id(),
                        entity.storageProviderId(),
                        entity.bucketOrContainer(),
                        entity.objectKey(),
                        entity.objectUri(),
                        entity.encrypted(),
                        entity.encryptionKeyReference(),
                        entity.contentLengthBytes(),
                        entity.contentType(),
                        entity.checksumAlgorithm(),
                        entity.checksumValue(),
                        entity.storageStatus(),
                        entity.createdAt(),
                        entity.verifiedAt()
            );
        }

        public static DocumentTargetLinkJpaEntity toEntity(DocumentTargetLink model) {
            return new DocumentTargetLinkJpaEntity(
                        model.id(),
                        model.documentId(),
                        model.documentVersionId(),
                        model.targetModule(),
                        model.targetTypeCode(),
                        model.targetId(),
                        model.targetCodeSnapshot(),
                        model.targetLabelSnapshot(),
                        model.linkRoleId(),
                        model.primaryLink(),
                        model.linkedByActorId(),
                        model.linkedAt(),
                        model.unlinkedAt(),
                        model.active()
            );
        }

        public static DocumentTargetLink toDomain(DocumentTargetLinkJpaEntity entity) {
            return new DocumentTargetLink(
                        entity.id(),
                        entity.documentId(),
                        entity.documentVersionId(),
                        entity.targetModule(),
                        entity.targetTypeCode(),
                        entity.targetId(),
                        entity.targetCodeSnapshot(),
                        entity.targetLabelSnapshot(),
                        entity.linkRoleId(),
                        entity.primaryLink(),
                        entity.linkedByActorId(),
                        entity.linkedAt(),
                        entity.unlinkedAt(),
                        entity.active()
            );
        }

        public static DocumentAccessGrantJpaEntity toEntity(DocumentAccessGrant model) {
            return new DocumentAccessGrantJpaEntity(
                        model.id(),
                        model.documentId(),
                        model.documentVersionId(),
                        model.principalType(),
                        model.principalId(),
                        model.principalLabelSnapshot(),
                        model.accessLevel(),
                        model.grantedByActorId(),
                        model.grantedAt(),
                        model.validFrom(),
                        model.validTo(),
                        model.revokedAt(),
                        model.active()
            );
        }

        public static DocumentAccessGrant toDomain(DocumentAccessGrantJpaEntity entity) {
            return new DocumentAccessGrant(
                        entity.id(),
                        entity.documentId(),
                        entity.documentVersionId(),
                        entity.principalType(),
                        entity.principalId(),
                        entity.principalLabelSnapshot(),
                        entity.accessLevel(),
                        entity.grantedByActorId(),
                        entity.grantedAt(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.revokedAt(),
                        entity.active()
            );
        }

        public static DocumentReviewReferenceJpaEntity toEntity(DocumentReviewReference model) {
            return new DocumentReviewReferenceJpaEntity(
                        model.id(),
                        model.documentId(),
                        model.documentVersionId(),
                        model.workflowInstanceId(),
                        model.reviewTypeId(),
                        model.reviewStatus(),
                        model.requestedByActorId(),
                        model.requestedAt(),
                        model.completedAt(),
                        model.decisionReasonId(),
                        model.decisionComment()
            );
        }

        public static DocumentReviewReference toDomain(DocumentReviewReferenceJpaEntity entity) {
            return new DocumentReviewReference(
                        entity.id(),
                        entity.documentId(),
                        entity.documentVersionId(),
                        entity.workflowInstanceId(),
                        entity.reviewTypeId(),
                        entity.reviewStatus(),
                        entity.requestedByActorId(),
                        entity.requestedAt(),
                        entity.completedAt(),
                        entity.decisionReasonId(),
                        entity.decisionComment()
            );
        }

        public static DocumentRetentionRecordJpaEntity toEntity(DocumentRetentionRecord model) {
            return new DocumentRetentionRecordJpaEntity(
                        model.id(),
                        model.documentId(),
                        model.retentionPolicyId(),
                        model.retentionClassId(),
                        model.retainUntil(),
                        model.legalHold(),
                        model.legalHoldReason(),
                        model.archivedAt(),
                        model.archiveStorageObjectId(),
                        model.disposalAllowedFrom(),
                        model.disposedAt(),
                        model.disposedByActorId()
            );
        }

        public static DocumentRetentionRecord toDomain(DocumentRetentionRecordJpaEntity entity) {
            return new DocumentRetentionRecord(
                        entity.id(),
                        entity.documentId(),
                        entity.retentionPolicyId(),
                        entity.retentionClassId(),
                        entity.retainUntil(),
                        entity.legalHold(),
                        entity.legalHoldReason(),
                        entity.archivedAt(),
                        entity.archiveStorageObjectId(),
                        entity.disposalAllowedFrom(),
                        entity.disposedAt(),
                        entity.disposedByActorId()
            );
        }

        public static DocumentExtractionRecordJpaEntity toEntity(DocumentExtractionRecord model) {
            return new DocumentExtractionRecordJpaEntity(
                        model.id(),
                        model.documentVersionId(),
                        model.extractionType(),
                        model.extractionStatus(),
                        model.extractedTextRef(),
                        model.extractedMetadataJson(),
                        model.confidenceScore(),
                        model.languageDetected(),
                        model.startedAt(),
                        model.completedAt(),
                        model.failureReason()
            );
        }

        public static DocumentExtractionRecord toDomain(DocumentExtractionRecordJpaEntity entity) {
            return new DocumentExtractionRecord(
                        entity.id(),
                        entity.documentVersionId(),
                        entity.extractionType(),
                        entity.extractionStatus(),
                        entity.extractedTextRef(),
                        entity.extractedMetadataJson(),
                        entity.confidenceScore(),
                        entity.languageDetected(),
                        entity.startedAt(),
                        entity.completedAt(),
                        entity.failureReason()
            );
        }

        public static DocumentExternalReferenceJpaEntity toEntity(DocumentExternalReference model) {
            return new DocumentExternalReferenceJpaEntity(
                        model.id(),
                        model.documentId(),
                        model.documentVersionId(),
                        model.externalSystemId(),
                        model.externalObjectType(),
                        model.externalObjectId(),
                        model.externalObjectCode(),
                        model.externalUrlReference(),
                        model.syncStatus(),
                        model.lastSyncedAt()
            );
        }

        public static DocumentExternalReference toDomain(DocumentExternalReferenceJpaEntity entity) {
            return new DocumentExternalReference(
                        entity.id(),
                        entity.documentId(),
                        entity.documentVersionId(),
                        entity.externalSystemId(),
                        entity.externalObjectType(),
                        entity.externalObjectId(),
                        entity.externalObjectCode(),
                        entity.externalUrlReference(),
                        entity.syncStatus(),
                        entity.lastSyncedAt()
            );
        }

        public static DocumentCatalogEntryJpaEntity toEntity(DocumentCatalogEntry model) {
            return new DocumentCatalogEntryJpaEntity(
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

        public static DocumentCatalogEntry toDomain(DocumentCatalogEntryJpaEntity entity) {
            return new DocumentCatalogEntry(
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

        public static DocumentCatalogTranslationJpaEntity toEntity(DocumentCatalogTranslation model) {
            return new DocumentCatalogTranslationJpaEntity(
                        model.id(),
                        model.catalogEntryId(),
                        model.locale(),
                        model.name(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static DocumentCatalogTranslation toDomain(DocumentCatalogTranslationJpaEntity entity) {
            return new DocumentCatalogTranslation(
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
