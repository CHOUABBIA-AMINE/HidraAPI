/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecordDeliveryAttemptRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.api.rest.request
 *
 * @Description : REST request for record delivery attempt.
 *
 */
package dz.sh.hidra.modules.notification.api.rest.request;

import dz.sh.hidra.modules.notification.domain.value.DeliveryAttemptStatus;
import java.time.Instant;

/**
 * REST request for record delivery attempt.
 */
public record RecordDeliveryAttemptRequest(
        String messageId,
        int attemptNumber,
        String channelId,
        String providerReference,
        String providerMessageId,
        DeliveryAttemptStatus attemptStatus,
        Instant attemptedAt,
        Instant completedAt,
        String failureCode,
        String failureMessage,
        Instant nextRetryAt,
        String correlationId
) {
}
