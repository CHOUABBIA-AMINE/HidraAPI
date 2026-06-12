/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReceiveNotificationRequestRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.api.rest.request
 *
 * @Description : REST request to receive notification request.
 *
 */
package dz.sh.hidra.modules.notification.api.rest.request;

import java.time.Instant;

/**
 * REST request to receive notification request.
 */
public record ReceiveNotificationRequestRequest(
        String sourceModule,
        String sourceEventType,
        String sourceEventId,
        String targetType,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot,
        String categoryId,
        String priorityId,
        String policyId,
        String templateId,
        String templateVersionId,
        String requestedByActorId,
        String requestedByDisplayNameSnapshot,
        String correlationId,
        String requestId,
        Instant expiresAt
) {
}
