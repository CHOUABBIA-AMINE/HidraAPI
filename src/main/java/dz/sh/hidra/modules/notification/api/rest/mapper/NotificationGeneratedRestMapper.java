/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.api.rest.mapper
 *
 * @Description : Generates exact notification API/application boundary mappings at compile time.
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
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact notification boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface NotificationGeneratedRestMapper {

    NotificationGeneratedRestMapper INSTANCE = Mappers.getMapper(NotificationGeneratedRestMapper.class);

    CreateNotificationMessageCommand toCommand(CreateNotificationMessageRequest request);

    ReceiveNotificationRequestCommand toCommand(ReceiveNotificationRequestRequest request);

    RecordDeliveryAttemptCommand toCommand(RecordDeliveryAttemptRequest request);

    NotificationDeliveryAttemptResponse toResponse(NotificationDeliveryAttemptSummaryDto dto);

    NotificationMessageResponse toResponse(NotificationMessageSummaryDto dto);

    NotificationRequestResponse toResponse(NotificationRequestSummaryDto dto);
}
