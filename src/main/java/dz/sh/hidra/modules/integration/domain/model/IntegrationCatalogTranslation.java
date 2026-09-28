/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationCatalogTranslation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Multilingual integration catalog labels.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
import java.time.Instant;

    /**
     * Multilingual integration catalog labels.
     *
         * @param id id
     * @param catalogEntryId catalogEntryId
     * @param locale locale
     * @param name name
     * @param description description
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record IntegrationCatalogTranslation(
            String id,
        String catalogEntryId,
        String locale,
        String name,
        String description,
        Instant createdAt,
        Instant updatedAt
    ) {

        public IntegrationCatalogTranslation {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationCatalogTranslation id must not be blank.");
        }
        // HRA-051 required: catalogEntryId
        if (catalogEntryId == null || catalogEntryId.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationCatalogTranslation catalog entry id must not be blank.");
        }
        // HRA-051 required: locale
        if (locale == null || locale.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationCatalogTranslation locale must not be blank.");
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
