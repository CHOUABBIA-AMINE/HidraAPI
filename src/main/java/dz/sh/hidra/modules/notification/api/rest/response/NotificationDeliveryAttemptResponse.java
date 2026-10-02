/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationDeliveryAttemptResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.api.rest.response
 *
 * @Description : REST response for notification delivery attempt.
 *
 */
package dz.sh.hidra.modules.notification.api.rest.response;

import dz.sh.hidra.modules.notification.domain.value.DeliveryAttemptStatus;
import java.time.Instant;

/**
 * REST response for notification delivery attempt.
 */
public record NotificationDeliveryAttemptResponse(
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
