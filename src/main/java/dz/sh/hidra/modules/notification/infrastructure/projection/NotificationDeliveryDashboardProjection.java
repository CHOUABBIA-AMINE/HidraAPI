/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationDeliveryDashboardProjection
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.projection
 *
 * @Description : Notification delivery dashboard projection.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.projection;

import dz.sh.hidra.modules.notification.domain.value.NotificationMessageStatus;

import java.time.Instant;

/**
 * Notification delivery dashboard projection.
 */
public record NotificationDeliveryDashboardProjection(
        String messageId,
        String requestId,
        String sourceModule,
        String targetType,
        String targetId,
        String channelId,
        NotificationMessageStatus messageStatus,
        int attemptCount,
        Instant lastAttemptAt
) {
}
