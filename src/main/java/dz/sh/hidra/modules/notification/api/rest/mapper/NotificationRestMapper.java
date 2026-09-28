/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
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
import dz.sh.hidra.modules.notification.api.rest.request.RecordDeliveryAttemptRequest;
import dz.sh.hidra.modules.notification.api.rest.response.NotificationDeliveryAttemptResponse;
import dz.sh.hidra.modules.notification.api.rest.response.NotificationMessageResponse;
import dz.sh.hidra.modules.notification.api.rest.response.NotificationRequestResponse;
import dz.sh.hidra.modules.notification.application.command.CreateNotificationMessageCommand;
import dz.sh.hidra.modules.notification.application.command.ReceiveNotificationRequestCommand;
import dz.sh.hidra.modules.notification.application.command.RecordDeliveryAttemptCommand;
import dz.sh.hidra.modules.notification.application.dto.NotificationDeliveryAttemptSummaryDto;
import dz.sh.hidra.modules.notification.application.dto.NotificationMessageSummaryDto;
import dz.sh.hidra.modules.notification.application.dto.NotificationRequestSummaryDto;
import java.util.Objects;

/**
 * Maps notification REST models to application models.
 */
public final class NotificationRestMapper {

    private static final NotificationGeneratedRestMapper GENERATED = NotificationGeneratedRestMapper.INSTANCE;

    private NotificationRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateNotificationMessageCommand toCommand(CreateNotificationMessageRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateNotificationMessageRequest must not be null."));
    }

    public static ReceiveNotificationRequestCommand toCommand(ReceiveNotificationRequestRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "ReceiveNotificationRequestRequest must not be null."));
    }

    public static RecordDeliveryAttemptCommand toCommand(RecordDeliveryAttemptRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "RecordDeliveryAttemptRequest must not be null."));
    }

    public static NotificationMessageResponse toResponse(NotificationMessageSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "NotificationMessageSummaryDto must not be null."));
    }

    public static NotificationRequestResponse toResponse(NotificationRequestSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "NotificationRequestSummaryDto must not be null."));
    }

    public static NotificationDeliveryAttemptResponse toResponse(NotificationDeliveryAttemptSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "NotificationDeliveryAttemptSummaryDto must not be null."));
    }
}
