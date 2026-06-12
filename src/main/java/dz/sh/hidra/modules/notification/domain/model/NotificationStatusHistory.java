/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationStatusHistory
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Append-only status transitions for notification request or message.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import java.time.Instant;

    /**
     * Append-only status transitions for notification request or message.
     *
         * @param id id
     * @param entityType entityType
     * @param entityId entityId
     * @param fromStatus fromStatus
     * @param toStatus toStatus
     * @param reasonId reasonId
     * @param reasonText reasonText
     * @param changedByActorId changedByActorId
     * @param changedByDisplayNameSnapshot changedByDisplayNameSnapshot
     * @param changedAt changedAt
     * @param correlationId correlationId
     */
    public record NotificationStatusHistory(
            String id,
        String entityType,
        String entityId,
        String fromStatus,
        String toStatus,
        String reasonId,
        String reasonText,
        String changedByActorId,
        String changedByDisplayNameSnapshot,
        Instant changedAt,
        String correlationId
    ) {

        public NotificationStatusHistory {
        id = normalize(id);
        entityType = normalize(entityType);
        entityId = normalize(entityId);
        fromStatus = normalize(fromStatus);
        toStatus = normalize(toStatus);
        reasonId = normalize(reasonId);
        reasonText = normalize(reasonText);
        changedByActorId = normalize(changedByActorId);
        changedByDisplayNameSnapshot = normalize(changedByDisplayNameSnapshot);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
