/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRequestRecipient
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
