/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateNotificationMessageRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.api.rest.request
 *
 * @Description : REST request to create notification message.
 *
 */
package dz.sh.hidra.modules.notification.api.rest.request;

import java.time.Instant;

/**
 * REST request to create notification message.
 */
public record CreateNotificationMessageRequest(
        String requestId,
        String recipientId,
        String channelId,
        String templateId,
        String templateVersionId,
        String locale,
        String subjectRendered,
        String bodyRendered,
        String shortTextRendered,
        String payloadHash,
        String priorityId,
        Instant scheduledAt,
        Instant expiresAt
) {
}
