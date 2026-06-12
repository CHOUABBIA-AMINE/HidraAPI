/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationMessage
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Concrete message for one recipient and one channel.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import dz.sh.hidra.modules.notification.domain.value.*;
import java.time.Instant;

    /**
     * Concrete message for one recipient and one channel.
     *
         * @param id id
     * @param requestId requestId
     * @param recipientId recipientId
     * @param channelId channelId
     * @param templateId templateId
     * @param templateVersionId templateVersionId
     * @param locale locale
     * @param subjectRendered subjectRendered
     * @param bodyRendered bodyRendered
     * @param shortTextRendered shortTextRendered
     * @param payloadHash payloadHash
     * @param priorityId priorityId
     * @param status status
     * @param scheduledAt scheduledAt
     * @param expiresAt expiresAt
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record NotificationMessage(
            String id,
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
        NotificationMessageStatus status,
        Instant scheduledAt,
        Instant expiresAt,
        Instant createdAt,
        Instant updatedAt
    ) {

        public NotificationMessage {
        id = normalize(id);
        requestId = normalize(requestId);
        recipientId = normalize(recipientId);
        channelId = normalize(channelId);
        templateId = normalize(templateId);
        templateVersionId = normalize(templateVersionId);
        locale = normalize(locale);
        subjectRendered = normalize(subjectRendered);
        bodyRendered = normalize(bodyRendered);
        shortTextRendered = normalize(shortTextRendered);
        payloadHash = normalize(payloadHash);
        priorityId = normalize(priorityId);
        }
        public boolean terminalStatus() {
            return status == NotificationMessageStatus.DELIVERED
                    || status == NotificationMessageStatus.ACKNOWLEDGED
                    || status == NotificationMessageStatus.FAILED
                    || status == NotificationMessageStatus.SUPPRESSED
                    || status == NotificationMessageStatus.CANCELLED
                    || status == NotificationMessageStatus.EXPIRED;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
