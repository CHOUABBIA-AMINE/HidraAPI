/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditPersistenceMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.mapper
 *
 * @Description : Maps audit domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.audit.domain.model.*;
import dz.sh.hidra.modules.audit.infrastructure.persistence.entity.*;

/**
 * Maps audit domain models to JPA entities.
 */
public final class AuditPersistenceMapper {

    private AuditPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static AuditEventJpaEntity toEntity(AuditEvent model) {
            return new AuditEventJpaEntity(
                        model.id(),
                        model.eventTypeId(),
                        model.eventCategoryId(),
                        model.severityId(),
                        model.sourceModule(),
                        model.sourceComponent(),
                        model.sourceEventId(),
                        model.actionCode(),
                        model.actionLabelSnapshot(),
                        model.eventStatus(),
                        model.actorId(),
                        model.actorType(),
                        model.actorDisplayNameSnapshot(),
                        model.actorUsernameSnapshot(),
                        model.actorRoleCodeSnapshot(),
                        model.organizationUnitId(),
                        model.organizationUnitCodeSnapshot(),
                        model.organizationUnitNameSnapshot(),
                        model.targetModule(),
                        model.targetType(),
                        model.targetId(),
                        model.targetCodeSnapshot(),
                        model.targetLabelSnapshot(),
                        model.operation(),
                        model.decisionCode(),
                        model.reasonId(),
                        model.reasonText(),
                        model.commentText(),
                        model.workflowInstanceId(),
                        model.workflowTaskId(),
                        model.workflowActionId(),
                        model.workflowFromState(),
                        model.workflowToState(),
                        model.requestId(),
                        model.correlationId(),
                        model.causationId(),
                        model.ipAddressMasked(),
                        model.userAgentSnapshot(),
                        model.sourceSystemCode(),
                        model.occurredAt(),
                        model.recordedAt(),
                        model.retentionPolicyId(),
                        model.hashValue(),
                        model.previousHashValue(),
                        model.payloadJson()
            );
        }

        public static AuditEvent toDomain(AuditEventJpaEntity entity) {
            return new AuditEvent(
                        entity.id(),
                        entity.eventTypeId(),
                        entity.eventCategoryId(),
                        entity.severityId(),
                        entity.sourceModule(),
                        entity.sourceComponent(),
                        entity.sourceEventId(),
                        entity.actionCode(),
                        entity.actionLabelSnapshot(),
                        entity.eventStatus(),
                        entity.actorId(),
                        entity.actorType(),
                        entity.actorDisplayNameSnapshot(),
                        entity.actorUsernameSnapshot(),
                        entity.actorRoleCodeSnapshot(),
                        entity.organizationUnitId(),
                        entity.organizationUnitCodeSnapshot(),
                        entity.organizationUnitNameSnapshot(),
                        entity.targetModule(),
                        entity.targetType(),
                        entity.targetId(),
                        entity.targetCodeSnapshot(),
                        entity.targetLabelSnapshot(),
                        entity.operation(),
                        entity.decisionCode(),
                        entity.reasonId(),
                        entity.reasonText(),
                        entity.commentText(),
                        entity.workflowInstanceId(),
                        entity.workflowTaskId(),
                        entity.workflowActionId(),
                        entity.workflowFromState(),
                        entity.workflowToState(),
                        entity.requestId(),
                        entity.correlationId(),
                        entity.causationId(),
                        entity.ipAddressMasked(),
                        entity.userAgentSnapshot(),
                        entity.sourceSystemCode(),
                        entity.occurredAt(),
                        entity.recordedAt(),
                        entity.retentionPolicyId(),
                        entity.hashValue(),
                        entity.previousHashValue(),
                        entity.payloadJson()
            );
        }

        public static AuditActorSnapshotJpaEntity toEntity(AuditActorSnapshot model) {
            return new AuditActorSnapshotJpaEntity(
                        model.id(),
                        model.auditEventId(),
                        model.actorId(),
                        model.actorType(),
                        model.usernameSnapshot(),
                        model.displayNameSnapshot(),
                        model.emailMasked(),
                        model.roleCodeSnapshot(),
                        model.employeeId(),
                        model.employeeNumberSnapshot(),
                        model.organizationUnitId(),
                        model.organizationUnitCodeSnapshot(),
                        model.organizationUnitNameSnapshot(),
                        model.positionCodeSnapshot(),
                        model.capturedAt()
            );
        }

        public static AuditActorSnapshot toDomain(AuditActorSnapshotJpaEntity entity) {
            return new AuditActorSnapshot(
                        entity.id(),
                        entity.auditEventId(),
                        entity.actorId(),
                        entity.actorType(),
                        entity.usernameSnapshot(),
                        entity.displayNameSnapshot(),
                        entity.emailMasked(),
                        entity.roleCodeSnapshot(),
                        entity.employeeId(),
                        entity.employeeNumberSnapshot(),
                        entity.organizationUnitId(),
                        entity.organizationUnitCodeSnapshot(),
                        entity.organizationUnitNameSnapshot(),
                        entity.positionCodeSnapshot(),
                        entity.capturedAt()
            );
        }

        public static AuditTargetReferenceJpaEntity toEntity(AuditTargetReference model) {
            return new AuditTargetReferenceJpaEntity(
                        model.id(),
                        model.auditEventId(),
                        model.targetModule(),
                        model.targetType(),
                        model.targetId(),
                        model.targetCodeSnapshot(),
                        model.targetLabelSnapshot(),
                        model.targetVersion(),
                        model.topologyAssetTypeCode(),
                        model.topologyAssetId(),
                        model.topologyAssetCodeSnapshot(),
                        model.capturedAt()
            );
        }

        public static AuditTargetReference toDomain(AuditTargetReferenceJpaEntity entity) {
            return new AuditTargetReference(
                        entity.id(),
                        entity.auditEventId(),
                        entity.targetModule(),
                        entity.targetType(),
                        entity.targetId(),
                        entity.targetCodeSnapshot(),
                        entity.targetLabelSnapshot(),
                        entity.targetVersion(),
                        entity.topologyAssetTypeCode(),
                        entity.topologyAssetId(),
                        entity.topologyAssetCodeSnapshot(),
                        entity.capturedAt()
            );
        }

        public static AuditActionReferenceJpaEntity toEntity(AuditActionReference model) {
            return new AuditActionReferenceJpaEntity(
                        model.id(),
                        model.auditEventId(),
                        model.actionCode(),
                        model.actionTypeId(),
                        model.operation(),
                        model.commandName(),
                        model.resultStatus(),
                        model.failureReasonCode(),
                        model.capturedAt()
            );
        }

        public static AuditActionReference toDomain(AuditActionReferenceJpaEntity entity) {
            return new AuditActionReference(
                        entity.id(),
                        entity.auditEventId(),
                        entity.actionCode(),
                        entity.actionTypeId(),
                        entity.operation(),
                        entity.commandName(),
                        entity.resultStatus(),
                        entity.failureReasonCode(),
                        entity.capturedAt()
            );
        }

        public static AuditDecisionContextJpaEntity toEntity(AuditDecisionContext model) {
            return new AuditDecisionContextJpaEntity(
                        model.id(),
                        model.auditEventId(),
                        model.decisionCode(),
                        model.decisionTypeId(),
                        model.reasonId(),
                        model.reasonText(),
                        model.commentText(),
                        model.policyCode(),
                        model.workflowInstanceId(),
                        model.workflowTaskId(),
                        model.workflowActionId(),
                        model.fromState(),
                        model.toState(),
                        model.decidedAt()
            );
        }

        public static AuditDecisionContext toDomain(AuditDecisionContextJpaEntity entity) {
            return new AuditDecisionContext(
                        entity.id(),
                        entity.auditEventId(),
                        entity.decisionCode(),
                        entity.decisionTypeId(),
                        entity.reasonId(),
                        entity.reasonText(),
                        entity.commentText(),
                        entity.policyCode(),
                        entity.workflowInstanceId(),
                        entity.workflowTaskId(),
                        entity.workflowActionId(),
                        entity.fromState(),
                        entity.toState(),
                        entity.decidedAt()
            );
        }

        public static AuditBeforeAfterValueJpaEntity toEntity(AuditBeforeAfterValue model) {
            return new AuditBeforeAfterValueJpaEntity(
                        model.id(),
                        model.auditEventId(),
                        model.fieldPath(),
                        model.fieldLabelSnapshot(),
                        model.valueType(),
                        model.beforeValueText(),
                        model.afterValueText(),
                        model.beforeValueHash(),
                        model.afterValueHash(),
                        model.masked(),
                        model.maskReasonId(),
                        model.changed(),
                        model.recordedAt()
            );
        }

        public static AuditBeforeAfterValue toDomain(AuditBeforeAfterValueJpaEntity entity) {
            return new AuditBeforeAfterValue(
                        entity.id(),
                        entity.auditEventId(),
                        entity.fieldPath(),
                        entity.fieldLabelSnapshot(),
                        entity.valueType(),
                        entity.beforeValueText(),
                        entity.afterValueText(),
                        entity.beforeValueHash(),
                        entity.afterValueHash(),
                        entity.masked(),
                        entity.maskReasonId(),
                        entity.changed(),
                        entity.recordedAt()
            );
        }

        public static AuditCorrelationContextJpaEntity toEntity(AuditCorrelationContext model) {
            return new AuditCorrelationContextJpaEntity(
                        model.id(),
                        model.auditEventId(),
                        model.correlationId(),
                        model.requestId(),
                        model.causationId(),
                        model.sessionIdHash(),
                        model.traceId(),
                        model.spanId(),
                        model.sourceSystemCode(),
                        model.sourceMessageId(),
                        model.capturedAt()
            );
        }

        public static AuditCorrelationContext toDomain(AuditCorrelationContextJpaEntity entity) {
            return new AuditCorrelationContext(
                        entity.id(),
                        entity.auditEventId(),
                        entity.correlationId(),
                        entity.requestId(),
                        entity.causationId(),
                        entity.sessionIdHash(),
                        entity.traceId(),
                        entity.spanId(),
                        entity.sourceSystemCode(),
                        entity.sourceMessageId(),
                        entity.capturedAt()
            );
        }

        public static AuditEvidenceLinkJpaEntity toEntity(AuditEvidenceLink model) {
            return new AuditEvidenceLinkJpaEntity(
                        model.id(),
                        model.auditEventId(),
                        model.evidenceTypeId(),
                        model.referenceModule(),
                        model.referenceType(),
                        model.referenceId(),
                        model.referenceCodeSnapshot(),
                        model.referenceLabelSnapshot(),
                        model.externalUriMasked(),
                        model.checksum(),
                        model.linkedAt()
            );
        }

        public static AuditEvidenceLink toDomain(AuditEvidenceLinkJpaEntity entity) {
            return new AuditEvidenceLink(
                        entity.id(),
                        entity.auditEventId(),
                        entity.evidenceTypeId(),
                        entity.referenceModule(),
                        entity.referenceType(),
                        entity.referenceId(),
                        entity.referenceCodeSnapshot(),
                        entity.referenceLabelSnapshot(),
                        entity.externalUriMasked(),
                        entity.checksum(),
                        entity.linkedAt()
            );
        }

        public static AuditSearchProjectionJpaEntity toEntity(AuditSearchProjection model) {
            return new AuditSearchProjectionJpaEntity(
                        model.id(),
                        model.auditEventId(),
                        model.sourceModule(),
                        model.eventCategoryCode(),
                        model.eventTypeCode(),
                        model.actionCode(),
                        model.actorId(),
                        model.actorDisplayNameSearch(),
                        model.organizationUnitId(),
                        model.targetModule(),
                        model.targetType(),
                        model.targetId(),
                        model.targetSearchText(),
                        model.decisionCode(),
                        model.correlationId(),
                        model.requestId(),
                        model.occurredAt(),
                        model.recordedAt(),
                        model.indexedAt()
            );
        }

        public static AuditSearchProjection toDomain(AuditSearchProjectionJpaEntity entity) {
            return new AuditSearchProjection(
                        entity.id(),
                        entity.auditEventId(),
                        entity.sourceModule(),
                        entity.eventCategoryCode(),
                        entity.eventTypeCode(),
                        entity.actionCode(),
                        entity.actorId(),
                        entity.actorDisplayNameSearch(),
                        entity.organizationUnitId(),
                        entity.targetModule(),
                        entity.targetType(),
                        entity.targetId(),
                        entity.targetSearchText(),
                        entity.decisionCode(),
                        entity.correlationId(),
                        entity.requestId(),
                        entity.occurredAt(),
                        entity.recordedAt(),
                        entity.indexedAt()
            );
        }

        public static AuditIntegritySealJpaEntity toEntity(AuditIntegritySeal model) {
            return new AuditIntegritySealJpaEntity(
                        model.id(),
                        model.sealTypeId(),
                        model.auditEventId(),
                        model.fromRecordedAt(),
                        model.toRecordedAt(),
                        model.eventCount(),
                        model.hashAlgorithm(),
                        model.rootHash(),
                        model.previousSealHash(),
                        model.sealedByActorId(),
                        model.sealedAt(),
                        model.verificationStatus(),
                        model.verifiedAt()
            );
        }

        public static AuditIntegritySeal toDomain(AuditIntegritySealJpaEntity entity) {
            return new AuditIntegritySeal(
                        entity.id(),
                        entity.sealTypeId(),
                        entity.auditEventId(),
                        entity.fromRecordedAt(),
                        entity.toRecordedAt(),
                        entity.eventCount(),
                        entity.hashAlgorithm(),
                        entity.rootHash(),
                        entity.previousSealHash(),
                        entity.sealedByActorId(),
                        entity.sealedAt(),
                        entity.verificationStatus(),
                        entity.verifiedAt()
            );
        }

        public static AuditRetentionPolicyJpaEntity toEntity(AuditRetentionPolicy model) {
            return new AuditRetentionPolicyJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.eventCategoryId(),
                        model.retentionDays(),
                        model.archiveAfterDays(),
                        model.legalHoldSupported(),
                        model.purgeAllowed(),
                        model.active(),
                        model.validFrom(),
                        model.validTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AuditRetentionPolicy toDomain(AuditRetentionPolicyJpaEntity entity) {
            return new AuditRetentionPolicy(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.eventCategoryId(),
                        entity.retentionDays(),
                        entity.archiveAfterDays(),
                        entity.legalHoldSupported(),
                        entity.purgeAllowed(),
                        entity.active(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static AuditExportRequestJpaEntity toEntity(AuditExportRequest model) {
            return new AuditExportRequestJpaEntity(
                        model.id(),
                        model.requestedByActorId(),
                        model.requestedByDisplayNameSnapshot(),
                        model.purposeId(),
                        model.filterJson(),
                        model.format(),
                        model.status(),
                        model.workflowInstanceId(),
                        model.resultDocumentReferenceId(),
                        model.recordCount(),
                        model.checksum(),
                        model.requestedAt(),
                        model.completedAt(),
                        model.expiresAt()
            );
        }

        public static AuditExportRequest toDomain(AuditExportRequestJpaEntity entity) {
            return new AuditExportRequest(
                        entity.id(),
                        entity.requestedByActorId(),
                        entity.requestedByDisplayNameSnapshot(),
                        entity.purposeId(),
                        entity.filterJson(),
                        entity.format(),
                        entity.status(),
                        entity.workflowInstanceId(),
                        entity.resultDocumentReferenceId(),
                        entity.recordCount(),
                        entity.checksum(),
                        entity.requestedAt(),
                        entity.completedAt(),
                        entity.expiresAt()
            );
        }

        public static AuditAccessRecordJpaEntity toEntity(AuditAccessRecord model) {
            return new AuditAccessRecordJpaEntity(
                        model.id(),
                        model.actorId(),
                        model.actorDisplayNameSnapshot(),
                        model.accessType(),
                        model.auditEventId(),
                        model.searchFilterHash(),
                        model.exportRequestId(),
                        model.resultCount(),
                        model.purposeText(),
                        model.accessedAt(),
                        model.correlationId()
            );
        }

        public static AuditAccessRecord toDomain(AuditAccessRecordJpaEntity entity) {
            return new AuditAccessRecord(
                        entity.id(),
                        entity.actorId(),
                        entity.actorDisplayNameSnapshot(),
                        entity.accessType(),
                        entity.auditEventId(),
                        entity.searchFilterHash(),
                        entity.exportRequestId(),
                        entity.resultCount(),
                        entity.purposeText(),
                        entity.accessedAt(),
                        entity.correlationId()
            );
        }

        public static AuditCatalogEntryJpaEntity toEntity(AuditCatalogEntry model) {
            return new AuditCatalogEntryJpaEntity(
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

        public static AuditCatalogEntry toDomain(AuditCatalogEntryJpaEntity entity) {
            return new AuditCatalogEntry(
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

        public static AuditCatalogTranslationJpaEntity toEntity(AuditCatalogTranslation model) {
            return new AuditCatalogTranslationJpaEntity(
                        model.id(),
                        model.catalogEntryId(),
                        model.locale(),
                        model.name(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static AuditCatalogTranslation toDomain(AuditCatalogTranslationJpaEntity entity) {
            return new AuditCatalogTranslation(
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
