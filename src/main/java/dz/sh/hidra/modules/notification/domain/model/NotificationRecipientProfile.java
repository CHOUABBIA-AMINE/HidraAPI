/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRecipientProfile
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Notification-specific recipient profile.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import dz.sh.hidra.modules.notification.domain.value.*;
import java.time.Instant;

    /**
     * Notification-specific recipient profile.
     *
         * @param id id
     * @param recipientType recipientType
     * @param recipientReferenceId recipientReferenceId
     * @param recipientCodeSnapshot recipientCodeSnapshot
     * @param recipientDisplayNameSnapshot recipientDisplayNameSnapshot
     * @param preferredLocale preferredLocale
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record NotificationRecipientProfile(
            String id,
        NotificationRecipientType recipientType,
        String recipientReferenceId,
        String recipientCodeSnapshot,
        String recipientDisplayNameSnapshot,
        String preferredLocale,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public NotificationRecipientProfile {
        id = normalize(id);
        recipientReferenceId = normalize(recipientReferenceId);
        recipientCodeSnapshot = normalize(recipientCodeSnapshot);
        recipientDisplayNameSnapshot = normalize(recipientDisplayNameSnapshot);
        preferredLocale = normalize(preferredLocale);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
