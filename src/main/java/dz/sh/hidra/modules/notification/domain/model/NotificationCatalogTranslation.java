/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationCatalogTranslation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Multilingual notification catalog labels.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import java.time.Instant;

    /**
     * Multilingual notification catalog labels.
     *
         * @param id id
     * @param catalogEntryId catalogEntryId
     * @param locale locale
     * @param name name
     * @param description description
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record NotificationCatalogTranslation(
            String id,
        String catalogEntryId,
        String locale,
        String name,
        String description,
        Instant createdAt,
        Instant updatedAt
    ) {

        public NotificationCatalogTranslation {
        id = normalize(id);
        catalogEntryId = normalize(catalogEntryId);
        locale = normalize(locale);
        name = normalize(name);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
