/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationDeliveryAttemptSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.dto
 *
 * @Description : Notification delivery attempt summary DTO.
 *
 */
package dz.sh.hidra.modules.notification.application.dto;

import dz.sh.hidra.modules.notification.domain.value.DeliveryAttemptStatus;

import java.time.Instant;

/**
 * Notification delivery attempt summary DTO.
 */
public record NotificationDeliveryAttemptSummaryDto(
        String id,
        String messageId,
        int attemptNumber,
        String channelId,
        DeliveryAttemptStatus attemptStatus,
        Instant attemptedAt,
        Instant completedAt,
        Instant nextRetryAt
) {
}
