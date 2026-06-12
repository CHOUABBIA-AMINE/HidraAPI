/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.mapper
 *
 * @Description : Maps notification domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.notification.domain.model.*;
import dz.sh.hidra.modules.notification.infrastructure.persistence.entity.*;

/**
 * Maps notification domain models to JPA entities.
 */
public final class NotificationPersistenceMapper {

    private NotificationPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static NotificationTemplateJpaEntity toEntity(NotificationTemplate model) {
            return new NotificationTemplateJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.templateTypeId(),
                        model.categoryId(),
                        model.defaultChannelId(),
                        model.status(),
                        model.currentVersion(),
                        model.systemDefined(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static NotificationTemplate toDomain(NotificationTemplateJpaEntity entity) {
            return new NotificationTemplate(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.templateTypeId(),
                        entity.categoryId(),
                        entity.defaultChannelId(),
                        entity.status(),
                        entity.currentVersion(),
                        entity.systemDefined(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static NotificationTemplateVersionJpaEntity toEntity(NotificationTemplateVersion model) {
            return new NotificationTemplateVersionJpaEntity(
                        model.id(),
                        model.templateId(),
                        model.versionNumber(),
                        model.status(),
                        model.subjectTemplate(),
                        model.bodyTemplate(),
                        model.contentFormat(),
                        model.variableSchemaJson(),
                        model.createdByActorId(),
                        model.createdByDisplayNameSnapshot(),
                        model.createdAt(),
                        model.activatedAt(),
                        model.retiredAt()
            );
        }

        public static NotificationTemplateVersion toDomain(NotificationTemplateVersionJpaEntity entity) {
            return new NotificationTemplateVersion(
                        entity.id(),
                        entity.templateId(),
                        entity.versionNumber(),
                        entity.status(),
                        entity.subjectTemplate(),
                        entity.bodyTemplate(),
                        entity.contentFormat(),
                        entity.variableSchemaJson(),
                        entity.createdByActorId(),
                        entity.createdByDisplayNameSnapshot(),
                        entity.createdAt(),
                        entity.activatedAt(),
                        entity.retiredAt()
            );
        }

        public static NotificationTemplateTranslationJpaEntity toEntity(NotificationTemplateTranslation model) {
            return new NotificationTemplateTranslationJpaEntity(
                        model.id(),
                        model.templateVersionId(),
                        model.locale(),
                        model.subject(),
                        model.body(),
                        model.shortText(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static NotificationTemplateTranslation toDomain(NotificationTemplateTranslationJpaEntity entity) {
            return new NotificationTemplateTranslation(
                        entity.id(),
                        entity.templateVersionId(),
                        entity.locale(),
                        entity.subject(),
                        entity.body(),
                        entity.shortText(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static NotificationChannelJpaEntity toEntity(NotificationChannel model) {
            return new NotificationChannelJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.channelType(),
                        model.active(),
                        model.providerReference(),
                        model.supportsDeliveryReceipt(),
                        model.supportsReadReceipt(),
                        model.supportsHtml(),
                        model.supportsAttachments(),
                        model.maxPayloadSize(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static NotificationChannel toDomain(NotificationChannelJpaEntity entity) {
            return new NotificationChannel(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.channelType(),
                        entity.active(),
                        entity.providerReference(),
                        entity.supportsDeliveryReceipt(),
                        entity.supportsReadReceipt(),
                        entity.supportsHtml(),
                        entity.supportsAttachments(),
                        entity.maxPayloadSize(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static NotificationRecipientProfileJpaEntity toEntity(NotificationRecipientProfile model) {
            return new NotificationRecipientProfileJpaEntity(
                        model.id(),
                        model.recipientType(),
                        model.recipientReferenceId(),
                        model.recipientCodeSnapshot(),
                        model.recipientDisplayNameSnapshot(),
                        model.preferredLocale(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static NotificationRecipientProfile toDomain(NotificationRecipientProfileJpaEntity entity) {
            return new NotificationRecipientProfile(
                        entity.id(),
                        entity.recipientType(),
                        entity.recipientReferenceId(),
                        entity.recipientCodeSnapshot(),
                        entity.recipientDisplayNameSnapshot(),
                        entity.preferredLocale(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static NotificationContactPointJpaEntity toEntity(NotificationContactPoint model) {
            return new NotificationContactPointJpaEntity(
                        model.id(),
                        model.recipientProfileId(),
                        model.channelId(),
                        model.addressValue(),
                        model.addressLabel(),
                        model.verified(),
                        model.primaryForChannel(),
                        model.active(),
                        model.validFrom(),
                        model.validTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static NotificationContactPoint toDomain(NotificationContactPointJpaEntity entity) {
            return new NotificationContactPoint(
                        entity.id(),
                        entity.recipientProfileId(),
                        entity.channelId(),
                        entity.addressValue(),
                        entity.addressLabel(),
                        entity.verified(),
                        entity.primaryForChannel(),
                        entity.active(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static NotificationRecipientGroupJpaEntity toEntity(NotificationRecipientGroup model) {
            return new NotificationRecipientGroupJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.groupTypeId(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static NotificationRecipientGroup toDomain(NotificationRecipientGroupJpaEntity entity) {
            return new NotificationRecipientGroup(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.groupTypeId(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static NotificationRecipientGroupMemberJpaEntity toEntity(NotificationRecipientGroupMember model) {
            return new NotificationRecipientGroupMemberJpaEntity(
                        model.id(),
                        model.groupId(),
                        model.memberType(),
                        model.memberReferenceId(),
                        model.memberLabelSnapshot(),
                        model.active(),
                        model.validFrom(),
                        model.validTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static NotificationRecipientGroupMember toDomain(NotificationRecipientGroupMemberJpaEntity entity) {
            return new NotificationRecipientGroupMember(
                        entity.id(),
                        entity.groupId(),
                        entity.memberType(),
                        entity.memberReferenceId(),
                        entity.memberLabelSnapshot(),
                        entity.active(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static NotificationPreferenceJpaEntity toEntity(NotificationPreference model) {
            return new NotificationPreferenceJpaEntity(
                        model.id(),
                        model.recipientProfileId(),
                        model.channelId(),
                        model.categoryId(),
                        model.enabled(),
                        model.quietHoursEnabled(),
                        model.quietHoursStart(),
                        model.quietHoursEnd(),
                        model.timezone(),
                        model.maxFrequencyPerHour(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static NotificationPreference toDomain(NotificationPreferenceJpaEntity entity) {
            return new NotificationPreference(
                        entity.id(),
                        entity.recipientProfileId(),
                        entity.channelId(),
                        entity.categoryId(),
                        entity.enabled(),
                        entity.quietHoursEnabled(),
                        entity.quietHoursStart(),
                        entity.quietHoursEnd(),
                        entity.timezone(),
                        entity.maxFrequencyPerHour(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static NotificationPolicyJpaEntity toEntity(NotificationPolicy model) {
            return new NotificationPolicyJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.sourceModule(),
                        model.categoryId(),
                        model.priorityId(),
                        model.defaultTemplateId(),
                        model.defaultChannelId(),
                        model.recipientResolutionMode(),
                        model.allowPreferenceOverride(),
                        model.allowQuietHourDelay(),
                        model.requiresAcknowledgement(),
                        model.maxRetryCount(),
                        model.retryPolicyId(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static NotificationPolicy toDomain(NotificationPolicyJpaEntity entity) {
            return new NotificationPolicy(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.sourceModule(),
                        entity.categoryId(),
                        entity.priorityId(),
                        entity.defaultTemplateId(),
                        entity.defaultChannelId(),
                        entity.recipientResolutionMode(),
                        entity.allowPreferenceOverride(),
                        entity.allowQuietHourDelay(),
                        entity.requiresAcknowledgement(),
                        entity.maxRetryCount(),
                        entity.retryPolicyId(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static NotificationRequestJpaEntity toEntity(NotificationRequest model) {
            return new NotificationRequestJpaEntity(
                        model.id(),
                        model.sourceModule(),
                        model.sourceEventType(),
                        model.sourceEventId(),
                        model.targetType(),
                        model.targetId(),
                        model.targetCodeSnapshot(),
                        model.targetLabelSnapshot(),
                        model.categoryId(),
                        model.priorityId(),
                        model.policyId(),
                        model.templateId(),
                        model.templateVersionId(),
                        model.requestedByActorId(),
                        model.requestedByDisplayNameSnapshot(),
                        model.requestedAt(),
                        model.correlationId(),
                        model.requestId(),
                        model.status(),
                        model.expiresAt(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static NotificationRequest toDomain(NotificationRequestJpaEntity entity) {
            return new NotificationRequest(
                        entity.id(),
                        entity.sourceModule(),
                        entity.sourceEventType(),
                        entity.sourceEventId(),
                        entity.targetType(),
                        entity.targetId(),
                        entity.targetCodeSnapshot(),
                        entity.targetLabelSnapshot(),
                        entity.categoryId(),
                        entity.priorityId(),
                        entity.policyId(),
                        entity.templateId(),
                        entity.templateVersionId(),
                        entity.requestedByActorId(),
                        entity.requestedByDisplayNameSnapshot(),
                        entity.requestedAt(),
                        entity.correlationId(),
                        entity.requestId(),
                        entity.status(),
                        entity.expiresAt(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static NotificationRequestRecipientJpaEntity toEntity(NotificationRequestRecipient model) {
            return new NotificationRequestRecipientJpaEntity(
                        model.id(),
                        model.requestId(),
                        model.recipientType(),
                        model.recipientReferenceId(),
                        model.recipientDisplayNameSnapshot(),
                        model.recipientLocale(),
                        model.resolvedFromType(),
                        model.resolvedFromReferenceId(),
                        model.resolutionStatus(),
                        model.createdAt()
            );
        }

        public static NotificationRequestRecipient toDomain(NotificationRequestRecipientJpaEntity entity) {
            return new NotificationRequestRecipient(
                        entity.id(),
                        entity.requestId(),
                        entity.recipientType(),
                        entity.recipientReferenceId(),
                        entity.recipientDisplayNameSnapshot(),
                        entity.recipientLocale(),
                        entity.resolvedFromType(),
                        entity.resolvedFromReferenceId(),
                        entity.resolutionStatus(),
                        entity.createdAt()
            );
        }

        public static NotificationMessageJpaEntity toEntity(NotificationMessage model) {
            return new NotificationMessageJpaEntity(
                        model.id(),
                        model.requestId(),
                        model.recipientId(),
                        model.channelId(),
                        model.templateId(),
                        model.templateVersionId(),
                        model.locale(),
                        model.subjectRendered(),
                        model.bodyRendered(),
                        model.shortTextRendered(),
                        model.payloadHash(),
                        model.priorityId(),
                        model.status(),
                        model.scheduledAt(),
                        model.expiresAt(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static NotificationMessage toDomain(NotificationMessageJpaEntity entity) {
            return new NotificationMessage(
                        entity.id(),
                        entity.requestId(),
                        entity.recipientId(),
                        entity.channelId(),
                        entity.templateId(),
                        entity.templateVersionId(),
                        entity.locale(),
                        entity.subjectRendered(),
                        entity.bodyRendered(),
                        entity.shortTextRendered(),
                        entity.payloadHash(),
                        entity.priorityId(),
                        entity.status(),
                        entity.scheduledAt(),
                        entity.expiresAt(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static NotificationMessageVariableJpaEntity toEntity(NotificationMessageVariable model) {
            return new NotificationMessageVariableJpaEntity(
                        model.id(),
                        model.messageId(),
                        model.variableName(),
                        model.valueType(),
                        model.valueSnapshot(),
                        model.masked(),
                        model.createdAt()
            );
        }

        public static NotificationMessageVariable toDomain(NotificationMessageVariableJpaEntity entity) {
            return new NotificationMessageVariable(
                        entity.id(),
                        entity.messageId(),
                        entity.variableName(),
                        entity.valueType(),
                        entity.valueSnapshot(),
                        entity.masked(),
                        entity.createdAt()
            );
        }

        public static NotificationBatchJpaEntity toEntity(NotificationBatch model) {
            return new NotificationBatchJpaEntity(
                        model.id(),
                        model.requestId(),
                        model.batchType(),
                        model.status(),
                        model.messageCount(),
                        model.successCount(),
                        model.failureCount(),
                        model.createdAt(),
                        model.completedAt()
            );
        }

        public static NotificationBatch toDomain(NotificationBatchJpaEntity entity) {
            return new NotificationBatch(
                        entity.id(),
                        entity.requestId(),
                        entity.batchType(),
                        entity.status(),
                        entity.messageCount(),
                        entity.successCount(),
                        entity.failureCount(),
                        entity.createdAt(),
                        entity.completedAt()
            );
        }

        public static NotificationDeliveryAttemptJpaEntity toEntity(NotificationDeliveryAttempt model) {
            return new NotificationDeliveryAttemptJpaEntity(
                        model.id(),
                        model.messageId(),
                        model.attemptNumber(),
                        model.channelId(),
                        model.providerReference(),
                        model.providerMessageId(),
                        model.attemptStatus(),
                        model.attemptedAt(),
                        model.completedAt(),
                        model.failureCode(),
                        model.failureMessage(),
                        model.nextRetryAt(),
                        model.correlationId(),
                        model.createdAt()
            );
        }

        public static NotificationDeliveryAttempt toDomain(NotificationDeliveryAttemptJpaEntity entity) {
            return new NotificationDeliveryAttempt(
                        entity.id(),
                        entity.messageId(),
                        entity.attemptNumber(),
                        entity.channelId(),
                        entity.providerReference(),
                        entity.providerMessageId(),
                        entity.attemptStatus(),
                        entity.attemptedAt(),
                        entity.completedAt(),
                        entity.failureCode(),
                        entity.failureMessage(),
                        entity.nextRetryAt(),
                        entity.correlationId(),
                        entity.createdAt()
            );
        }

        public static NotificationStatusHistoryJpaEntity toEntity(NotificationStatusHistory model) {
            return new NotificationStatusHistoryJpaEntity(
                        model.id(),
                        model.entityType(),
                        model.entityId(),
                        model.fromStatus(),
                        model.toStatus(),
                        model.reasonId(),
                        model.reasonText(),
                        model.changedByActorId(),
                        model.changedByDisplayNameSnapshot(),
                        model.changedAt(),
                        model.correlationId()
            );
        }

        public static NotificationStatusHistory toDomain(NotificationStatusHistoryJpaEntity entity) {
            return new NotificationStatusHistory(
                        entity.id(),
                        entity.entityType(),
                        entity.entityId(),
                        entity.fromStatus(),
                        entity.toStatus(),
                        entity.reasonId(),
                        entity.reasonText(),
                        entity.changedByActorId(),
                        entity.changedByDisplayNameSnapshot(),
                        entity.changedAt(),
                        entity.correlationId()
            );
        }

        public static NotificationRetryPolicyJpaEntity toEntity(NotificationRetryPolicy model) {
            return new NotificationRetryPolicyJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameAr(),
                        model.nameFr(),
                        model.nameEn(),
                        model.maxAttempts(),
                        model.initialDelaySeconds(),
                        model.maxDelaySeconds(),
                        model.backoffStrategy(),
                        model.retryOnTemporaryFailure(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static NotificationRetryPolicy toDomain(NotificationRetryPolicyJpaEntity entity) {
            return new NotificationRetryPolicy(
                        entity.id(),
                        entity.code(),
                        entity.nameAr(),
                        entity.nameFr(),
                        entity.nameEn(),
                        entity.maxAttempts(),
                        entity.initialDelaySeconds(),
                        entity.maxDelaySeconds(),
                        entity.backoffStrategy(),
                        entity.retryOnTemporaryFailure(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static NotificationSuppressionRuleJpaEntity toEntity(NotificationSuppressionRule model) {
            return new NotificationSuppressionRuleJpaEntity(
                        model.id(),
                        model.code(),
                        model.sourceModule(),
                        model.categoryId(),
                        model.priorityId(),
                        model.channelId(),
                        model.recipientType(),
                        model.recipientReferenceId(),
                        model.reasonId(),
                        model.active(),
                        model.validFrom(),
                        model.validTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static NotificationSuppressionRule toDomain(NotificationSuppressionRuleJpaEntity entity) {
            return new NotificationSuppressionRule(
                        entity.id(),
                        entity.code(),
                        entity.sourceModule(),
                        entity.categoryId(),
                        entity.priorityId(),
                        entity.channelId(),
                        entity.recipientType(),
                        entity.recipientReferenceId(),
                        entity.reasonId(),
                        entity.active(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static NotificationScheduleJpaEntity toEntity(NotificationSchedule model) {
            return new NotificationScheduleJpaEntity(
                        model.id(),
                        model.requestId(),
                        model.messageId(),
                        model.scheduleType(),
                        model.scheduledAt(),
                        model.timezone(),
                        model.recurrenceRule(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static NotificationSchedule toDomain(NotificationScheduleJpaEntity entity) {
            return new NotificationSchedule(
                        entity.id(),
                        entity.requestId(),
                        entity.messageId(),
                        entity.scheduleType(),
                        entity.scheduledAt(),
                        entity.timezone(),
                        entity.recurrenceRule(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static NotificationAcknowledgementJpaEntity toEntity(NotificationAcknowledgement model) {
            return new NotificationAcknowledgementJpaEntity(
                        model.id(),
                        model.messageId(),
                        model.recipientId(),
                        model.acknowledgementStatus(),
                        model.acknowledgedByActorId(),
                        model.acknowledgedByDisplayNameSnapshot(),
                        model.acknowledgedAt(),
                        model.commentText(),
                        model.createdAt()
            );
        }

        public static NotificationAcknowledgement toDomain(NotificationAcknowledgementJpaEntity entity) {
            return new NotificationAcknowledgement(
                        entity.id(),
                        entity.messageId(),
                        entity.recipientId(),
                        entity.acknowledgementStatus(),
                        entity.acknowledgedByActorId(),
                        entity.acknowledgedByDisplayNameSnapshot(),
                        entity.acknowledgedAt(),
                        entity.commentText(),
                        entity.createdAt()
            );
        }

        public static NotificationEvidenceLinkJpaEntity toEntity(NotificationEvidenceLink model) {
            return new NotificationEvidenceLinkJpaEntity(
                        model.id(),
                        model.notificationRequestId(),
                        model.messageId(),
                        model.evidenceType(),
                        model.referenceModule(),
                        model.referenceType(),
                        model.referenceId(),
                        model.referenceCodeSnapshot(),
                        model.createdAt()
            );
        }

        public static NotificationEvidenceLink toDomain(NotificationEvidenceLinkJpaEntity entity) {
            return new NotificationEvidenceLink(
                        entity.id(),
                        entity.notificationRequestId(),
                        entity.messageId(),
                        entity.evidenceType(),
                        entity.referenceModule(),
                        entity.referenceType(),
                        entity.referenceId(),
                        entity.referenceCodeSnapshot(),
                        entity.createdAt()
            );
        }

        public static NotificationCatalogEntryJpaEntity toEntity(NotificationCatalogEntry model) {
            return new NotificationCatalogEntryJpaEntity(
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

        public static NotificationCatalogEntry toDomain(NotificationCatalogEntryJpaEntity entity) {
            return new NotificationCatalogEntry(
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

        public static NotificationCatalogTranslationJpaEntity toEntity(NotificationCatalogTranslation model) {
            return new NotificationCatalogTranslationJpaEntity(
                        model.id(),
                        model.catalogEntryId(),
                        model.locale(),
                        model.name(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static NotificationCatalogTranslation toDomain(NotificationCatalogTranslationJpaEntity entity) {
            return new NotificationCatalogTranslation(
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
