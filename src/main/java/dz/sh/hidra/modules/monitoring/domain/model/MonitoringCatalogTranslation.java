/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringCatalogTranslation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.model
 *
 * @Description : Multilingual labels for monitoring catalog entries.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.model;

import dz.sh.hidra.modules.monitoring.domain.exception.InvalidMonitoringValueException;
import java.time.Instant;

    /**
     * Multilingual labels for monitoring catalog entries.
     *
         * @param id id
     * @param catalogEntryId catalogEntryId
     * @param locale locale
     * @param name name
     * @param description description
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record MonitoringCatalogTranslation(
            String id,
        String catalogEntryId,
        String locale,
        String name,
        String description,
        Instant createdAt,
        Instant updatedAt
    ) {

        public MonitoringCatalogTranslation {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidMonitoringValueException("MonitoringCatalogTranslation id must not be blank.");
        }
        // HRA-051 required: catalogEntryId
        if (catalogEntryId == null || catalogEntryId.isBlank()) {
            throw new InvalidMonitoringValueException("MonitoringCatalogTranslation catalog entry id must not be blank.");
        }
        // HRA-051 required: locale
        if (locale == null || locale.isBlank()) {
            throw new InvalidMonitoringValueException("MonitoringCatalogTranslation locale must not be blank.");
        }

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
