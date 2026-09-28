/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRequestRecipient
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Resolved recipient for a notification request.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import dz.sh.hidra.modules.notification.domain.exception.InvalidNotificationValueException;
import dz.sh.hidra.modules.notification.domain.value.*;
import java.time.Instant;

    /**
     * Resolved recipient for a notification request.
     *
         * @param id id
     * @param requestId requestId
     * @param recipientType recipientType
     * @param recipientReferenceId recipientReferenceId
     * @param recipientDisplayNameSnapshot recipientDisplayNameSnapshot
     * @param recipientLocale recipientLocale
     * @param resolvedFromType resolvedFromType
     * @param resolvedFromReferenceId resolvedFromReferenceId
     * @param resolutionStatus resolutionStatus
     * @param createdAt createdAt
     */
    public record NotificationRequestRecipient(
            String id,
        String requestId,
        NotificationRecipientType recipientType,
        String recipientReferenceId,
        String recipientDisplayNameSnapshot,
        String recipientLocale,
        String resolvedFromType,
        String resolvedFromReferenceId,
        RecipientResolutionStatus resolutionStatus,
        Instant createdAt
    ) {

        public NotificationRequestRecipient {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidNotificationValueException("NotificationRequestRecipient id must not be blank.");
        }
        // HRA-051 required: requestId
        if (requestId == null || requestId.isBlank()) {
            throw new InvalidNotificationValueException("NotificationRequestRecipient request id must not be blank.");
        }
        // HRA-051 required: recipientType
        if (recipientType == null) {
            throw new InvalidNotificationValueException("NotificationRequestRecipient recipient type must not be null.");
        }
        // HRA-051 required: recipientReferenceId
        if (recipientReferenceId == null || recipientReferenceId.isBlank()) {
            throw new InvalidNotificationValueException("NotificationRequestRecipient recipient reference id must not be blank.");
        }
        // HRA-051 required: resolutionStatus
        if (resolutionStatus == null) {
            throw new InvalidNotificationValueException("NotificationRequestRecipient resolution status must not be null.");
        }

        id = normalize(id);
        requestId = normalize(requestId);
        recipientReferenceId = normalize(recipientReferenceId);
        recipientDisplayNameSnapshot = normalize(recipientDisplayNameSnapshot);
        recipientLocale = normalize(recipientLocale);
        resolvedFromType = normalize(resolvedFromType);
        resolvedFromReferenceId = normalize(resolvedFromReferenceId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
