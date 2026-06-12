/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationDeliveryAttempt
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : One attempt to deliver a message through a channel or provider.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import dz.sh.hidra.modules.notification.domain.value.*;
import java.time.Instant;

    /**
     * One attempt to deliver a message through a channel or provider.
     *
         * @param id id
     * @param messageId messageId
     * @param attemptNumber attemptNumber
     * @param channelId channelId
     * @param providerReference providerReference
     * @param providerMessageId providerMessageId
     * @param attemptStatus attemptStatus
     * @param attemptedAt attemptedAt
     * @param completedAt completedAt
     * @param failureCode failureCode
     * @param failureMessage failureMessage
     * @param nextRetryAt nextRetryAt
     * @param correlationId correlationId
     * @param createdAt createdAt
     */
    public record NotificationDeliveryAttempt(
            String id,
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
        String correlationId,
        Instant createdAt
    ) {

        public NotificationDeliveryAttempt {
        id = normalize(id);
        messageId = normalize(messageId);
        channelId = normalize(channelId);
        providerReference = normalize(providerReference);
        providerMessageId = normalize(providerMessageId);
        failureCode = normalize(failureCode);
        failureMessage = normalize(failureMessage);
        correlationId = normalize(correlationId);
        }
        public boolean permanentFailure() {
            return attemptStatus == DeliveryAttemptStatus.FAILED_PERMANENT
                    || attemptStatus == DeliveryAttemptStatus.CANCELLED;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
