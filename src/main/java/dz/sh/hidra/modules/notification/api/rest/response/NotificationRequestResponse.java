/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRequestResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.api.rest.response
 *
 * @Description : REST response for notification request.
 *
 */
package dz.sh.hidra.modules.notification.api.rest.response;

import dz.sh.hidra.modules.notification.domain.value.NotificationRequestStatus;

import java.time.Instant;

/**
 * REST response for notification request.
 */
public record NotificationRequestResponse(
        String id,
        String sourceModule,
        String sourceEventType,
        String sourceEventId,
        String targetType,
        String targetId,
        NotificationRequestStatus status,
        String correlationId,
        Instant requestedAt
) {
}
