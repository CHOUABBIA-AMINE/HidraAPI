/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationBatch
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Groups messages generated together.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import dz.sh.hidra.modules.notification.domain.exception.InvalidNotificationValueException;
import dz.sh.hidra.modules.notification.domain.value.*;
import java.time.Instant;

    /**
     * Groups messages generated together.
     *
         * @param id id
     * @param requestId requestId
     * @param batchType batchType
     * @param status status
     * @param messageCount messageCount
     * @param successCount successCount
     * @param failureCount failureCount
     * @param createdAt createdAt
     * @param completedAt completedAt
     */
    public record NotificationBatch(
            String id,
        String requestId,
        NotificationBatchType batchType,
        NotificationBatchStatus status,
        int messageCount,
        int successCount,
        int failureCount,
        Instant createdAt,
        Instant completedAt
    ) {

        public NotificationBatch {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidNotificationValueException("NotificationBatch id must not be blank.");
        }
        // HRA-051 required: batchType
        if (batchType == null) {
            throw new InvalidNotificationValueException("NotificationBatch batch type must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidNotificationValueException("NotificationBatch status must not be null.");
        }

        id = normalize(id);
        requestId = normalize(requestId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
