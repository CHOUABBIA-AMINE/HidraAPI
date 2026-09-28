/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
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
}
