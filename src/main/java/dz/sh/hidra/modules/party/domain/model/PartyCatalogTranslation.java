/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyCatalogTranslation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Multilingual labels for party-owned catalog entries.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import java.time.Instant;

    /**
     * Multilingual labels for party-owned catalog entries.
     *
         * @param id id
     * @param catalogEntryId catalogEntryId
     * @param languageCode languageCode
     * @param label label
     * @param description description
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PartyCatalogTranslation(
            String id,
        String catalogEntryId,
        String languageCode,
        String label,
        String description,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PartyCatalogTranslation {
        id = normalize(id);
        catalogEntryId = normalize(catalogEntryId);
        languageCode = normalize(languageCode);
        label = normalize(label);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
