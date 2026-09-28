/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationCatalogEntry
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Notification controlled vocabulary entry.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import dz.sh.hidra.modules.notification.domain.exception.InvalidNotificationValueException;
import java.time.Instant;

    /**
     * Notification controlled vocabulary entry.
     *
         * @param id id
     * @param catalogName catalogName
     * @param code code
     * @param active active
     * @param sortOrder sortOrder
     * @param systemDefined systemDefined
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record NotificationCatalogEntry(
            String id,
        String catalogName,
        String code,
        boolean active,
        int sortOrder,
        boolean systemDefined,
        Instant createdAt,
        Instant updatedAt
    ) {

        public NotificationCatalogEntry {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidNotificationValueException("NotificationCatalogEntry id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidNotificationValueException("NotificationCatalogEntry code must not be blank.");
        }

        id = normalize(id);
        catalogName = normalize(catalogName);
        code = normalize(code);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
