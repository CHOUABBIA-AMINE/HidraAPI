/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetTypeTranslation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Multilingual type labels.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import java.time.Instant;

    /**
     * Multilingual type labels.
     *
         * @param id id
     * @param assetTypeId assetTypeId
     * @param locale locale
     * @param name name
     * @param description description
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AssetTypeTranslation(
            String id,
        String assetTypeId,
        String locale,
        String name,
        String description,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AssetTypeTranslation {
        id = normalize(id);
        assetTypeId = normalize(assetTypeId);
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
