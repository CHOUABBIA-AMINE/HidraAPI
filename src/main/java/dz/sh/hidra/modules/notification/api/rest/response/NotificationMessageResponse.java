/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationMessageResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.api.rest.response
 *
 * @Description : REST response for notification message.
 *
 */
package dz.sh.hidra.modules.notification.api.rest.response;

import dz.sh.hidra.modules.notification.domain.value.NotificationMessageStatus;

import java.time.Instant;

/**
 * REST response for notification message.
 */
public record NotificationMessageResponse(
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
