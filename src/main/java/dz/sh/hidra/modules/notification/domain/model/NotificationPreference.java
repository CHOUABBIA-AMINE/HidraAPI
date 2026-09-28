/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationPreference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Recipient preference for non-critical notifications.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import dz.sh.hidra.modules.notification.domain.exception.InvalidNotificationValueException;
import java.time.Instant;

    /**
     * Recipient preference for non-critical notifications.
     *
         * @param id id
     * @param recipientProfileId recipientProfileId
     * @param channelId channelId
     * @param categoryId categoryId
     * @param enabled enabled
     * @param quietHoursEnabled quietHoursEnabled
     * @param quietHoursStart quietHoursStart
     * @param quietHoursEnd quietHoursEnd
     * @param timezone timezone
     * @param maxFrequencyPerHour maxFrequencyPerHour
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record NotificationPreference(
            String id,
        String recipientProfileId,
        String channelId,
        String categoryId,
        boolean enabled,
        boolean quietHoursEnabled,
        String quietHoursStart,
        String quietHoursEnd,
        String timezone,
        Integer maxFrequencyPerHour,
        Instant createdAt,
        Instant updatedAt
    ) {

        public NotificationPreference {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidNotificationValueException("NotificationPreference id must not be blank.");
        }
        // HRA-051 required: recipientProfileId
        if (recipientProfileId == null || recipientProfileId.isBlank()) {
            throw new InvalidNotificationValueException("NotificationPreference recipient profile id must not be blank.");
        }
        // HRA-051 required: channelId
        if (channelId == null || channelId.isBlank()) {
            throw new InvalidNotificationValueException("NotificationPreference channel id must not be blank.");
        }
        // HRA-051 required: categoryId
        if (categoryId == null || categoryId.isBlank()) {
            throw new InvalidNotificationValueException("NotificationPreference category id must not be blank.");
        }

        id = normalize(id);
        recipientProfileId = normalize(recipientProfileId);
        channelId = normalize(channelId);
        categoryId = normalize(categoryId);
        quietHoursStart = normalize(quietHoursStart);
        quietHoursEnd = normalize(quietHoursEnd);
        timezone = normalize(timezone);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
