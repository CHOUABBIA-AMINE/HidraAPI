/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationAcknowledgement
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.notification.domain.exception.InvalidNotificationValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidNotificationValueException("NotificationAcknowledgement id must not be blank.");
        }
        // HRA-051 required: messageId
        if (messageId == null || messageId.isBlank()) {
            throw new InvalidNotificationValueException("NotificationAcknowledgement message id must not be blank.");
        }
        // HRA-051 required: recipientId
        if (recipientId == null || recipientId.isBlank()) {
            throw new InvalidNotificationValueException("NotificationAcknowledgement recipient id must not be blank.");
        }
        // HRA-051 required: acknowledgementStatus
        if (acknowledgementStatus == null) {
            throw new InvalidNotificationValueException("NotificationAcknowledgement acknowledgement status must not be null.");
        }

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
