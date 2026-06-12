/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRestMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : API
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.api.rest.mapper
 *
 * @Description : Maps notification REST models to application models.
 *
 */
package dz.sh.hidra.modules.notification.api.rest.mapper;

import dz.sh.hidra.modules.notification.api.rest.request.CreateNotificationMessageRequest;
import dz.sh.hidra.modules.notification.api.rest.request.ReceiveNotificationRequestRequest;
import dz.sh.hidra.modules.notification.api.rest.response.NotificationMessageResponse;
import dz.sh.hidra.modules.notification.api.rest.response.NotificationRequestResponse;
import dz.sh.hidra.modules.notification.application.command.CreateNotificationMessageCommand;
import dz.sh.hidra.modules.notification.application.command.ReceiveNotificationRequestCommand;
import dz.sh.hidra.modules.notification.application.dto.NotificationMessageSummaryDto;
import dz.sh.hidra.modules.notification.application.dto.NotificationRequestSummaryDto;

/**
 * Maps notification REST models to application models.
 */
public final class NotificationRestMapper {

    private NotificationRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static ReceiveNotificationRequestCommand toCommand(ReceiveNotificationRequestRequest request) {
        return new ReceiveNotificationRequestCommand(request.sourceModule(), request.sourceEventType(), request.sourceEventId(), request.targetType(), request.targetId(), request.targetCodeSnapshot(), request.targetLabelSnapshot(), request.categoryId(), request.priorityId(), request.policyId(), request.templateId(), request.templateVersionId(), request.requestedByActorId(), request.requestedByDisplayNameSnapshot(), request.correlationId(), request.requestId(), request.expiresAt());
    }

    public static CreateNotificationMessageCommand toCommand(CreateNotificationMessageRequest request) {
        return new CreateNotificationMessageCommand(request.requestId(), request.recipientId(), request.channelId(), request.templateId(), request.templateVersionId(), request.locale(), request.subjectRendered(), request.bodyRendered(), request.shortTextRendered(), request.payloadHash(), request.priorityId(), request.scheduledAt(), request.expiresAt());
    }

    public static NotificationRequestResponse toResponse(NotificationRequestSummaryDto dto) {
        return new NotificationRequestResponse(dto.id(), dto.sourceModule(), dto.sourceEventType(), dto.sourceEventId(), dto.targetType(), dto.targetId(), dto.status(), dto.correlationId(), dto.requestedAt());
    }

    public static NotificationMessageResponse toResponse(NotificationMessageSummaryDto dto) {
        return new NotificationMessageResponse(dto.id(), dto.requestId(), dto.recipientId(), dto.channelId(), dto.templateVersionId(), dto.locale(), dto.status(), dto.scheduledAt(), dto.createdAt());
    }
}
