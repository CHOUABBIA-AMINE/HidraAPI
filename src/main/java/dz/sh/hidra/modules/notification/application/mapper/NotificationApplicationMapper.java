/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationApplicationMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.mapper
 *
 * @Description : Maps notification domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.notification.application.mapper;

import dz.sh.hidra.modules.notification.application.dto.NotificationDeliveryAttemptSummaryDto;
import dz.sh.hidra.modules.notification.application.dto.NotificationMessageSummaryDto;
import dz.sh.hidra.modules.notification.application.dto.NotificationRequestSummaryDto;
import dz.sh.hidra.modules.notification.domain.model.NotificationDeliveryAttempt;
import dz.sh.hidra.modules.notification.domain.model.NotificationMessage;
import dz.sh.hidra.modules.notification.domain.model.NotificationRequest;

/**
 * Maps notification domain models to DTOs.
 */
public final class NotificationApplicationMapper {

    private NotificationApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static NotificationRequestSummaryDto toSummary(NotificationRequest request) {
        return new NotificationRequestSummaryDto(request.id(), request.sourceModule(), request.sourceEventType(), request.sourceEventId(), request.targetType(), request.targetId(), request.status(), request.correlationId(), request.requestedAt());
    }

    public static NotificationMessageSummaryDto toSummary(NotificationMessage message) {
        return new NotificationMessageSummaryDto(message.id(), message.requestId(), message.recipientId(), message.channelId(), message.templateVersionId(), message.locale(), message.status(), message.scheduledAt(), message.createdAt());
    }

    public static NotificationDeliveryAttemptSummaryDto toSummary(NotificationDeliveryAttempt attempt) {
        return new NotificationDeliveryAttemptSummaryDto(attempt.id(), attempt.messageId(), attempt.attemptNumber(), attempt.channelId(), attempt.attemptStatus(), attempt.attemptedAt(), attempt.completedAt(), attempt.nextRetryAt());
    }
}
