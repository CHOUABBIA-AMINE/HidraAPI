/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationSchedule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Scheduled delivery, reminder, digest, escalation, or retry.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import dz.sh.hidra.modules.notification.domain.value.*;
import java.time.Instant;

    /**
     * Scheduled delivery, reminder, digest, escalation, or retry.
     *
         * @param id id
     * @param requestId requestId
     * @param messageId messageId
     * @param scheduleType scheduleType
     * @param scheduledAt scheduledAt
     * @param timezone timezone
     * @param recurrenceRule recurrenceRule
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record NotificationSchedule(
            String id,
        String requestId,
        String messageId,
        NotificationScheduleType scheduleType,
        Instant scheduledAt,
        String timezone,
        String recurrenceRule,
        NotificationScheduleStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public NotificationSchedule {
        id = normalize(id);
        requestId = normalize(requestId);
        messageId = normalize(messageId);
        timezone = normalize(timezone);
        recurrenceRule = normalize(recurrenceRule);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
