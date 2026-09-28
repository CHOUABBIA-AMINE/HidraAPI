/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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
}
