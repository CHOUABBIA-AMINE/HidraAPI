/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationMessageVariable
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Resolved variable used in message rendering.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import dz.sh.hidra.modules.notification.domain.value.*;
import java.time.Instant;

    /**
     * Resolved variable used in message rendering.
     *
         * @param id id
     * @param messageId messageId
     * @param variableName variableName
     * @param valueType valueType
     * @param valueSnapshot valueSnapshot
     * @param masked masked
     * @param createdAt createdAt
     */
    public record NotificationMessageVariable(
            String id,
        String messageId,
        String variableName,
        NotificationValueType valueType,
        String valueSnapshot,
        boolean masked,
        Instant createdAt
    ) {

        public NotificationMessageVariable {
        id = normalize(id);
        messageId = normalize(messageId);
        variableName = normalize(variableName);
        valueSnapshot = normalize(valueSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
