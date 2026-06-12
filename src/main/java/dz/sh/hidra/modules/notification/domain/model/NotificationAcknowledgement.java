/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationAcknowledgement
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Recipient acknowledgement for a message.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import dz.sh.hidra.modules.notification.domain.value.*;
import java.time.Instant;

    /**
     * Recipient acknowledgement for a message.
     *
         * @param id id
     * @param messageId messageId
     * @param recipientId recipientId
     * @param acknowledgementStatus acknowledgementStatus
     * @param acknowledgedByActorId acknowledgedByActorId
     * @param acknowledgedByDisplayNameSnapshot acknowledgedByDisplayNameSnapshot
     * @param acknowledgedAt acknowledgedAt
     * @param commentText commentText
     * @param createdAt createdAt
     */
    public record NotificationAcknowledgement(
            String id,
        String messageId,
        String recipientId,
        NotificationAcknowledgementStatus acknowledgementStatus,
        String acknowledgedByActorId,
        String acknowledgedByDisplayNameSnapshot,
        Instant acknowledgedAt,
        String commentText,
        Instant createdAt
    ) {

        public NotificationAcknowledgement {
        id = normalize(id);
        messageId = normalize(messageId);
        recipientId = normalize(recipientId);
        acknowledgedByActorId = normalize(acknowledgedByActorId);
        acknowledgedByDisplayNameSnapshot = normalize(acknowledgedByDisplayNameSnapshot);
        commentText = normalize(commentText);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
