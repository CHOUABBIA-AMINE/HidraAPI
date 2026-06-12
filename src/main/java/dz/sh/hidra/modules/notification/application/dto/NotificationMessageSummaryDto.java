/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationMessageSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.dto
 *
 * @Description : Notification message summary DTO.
 *
 */
package dz.sh.hidra.modules.notification.application.dto;

import dz.sh.hidra.modules.notification.domain.value.NotificationMessageStatus;

import java.time.Instant;

/**
 * Notification message summary DTO.
 */
public record NotificationMessageSummaryDto(
        String id,
        String requestId,
        String recipientId,
        String channelId,
        String templateVersionId,
        String locale,
        NotificationMessageStatus status,
        Instant scheduledAt,
        Instant createdAt
) {
}
