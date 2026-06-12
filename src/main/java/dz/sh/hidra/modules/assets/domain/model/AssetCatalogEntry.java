/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetCatalogEntry
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Asset-owned catalog entry.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import java.time.Instant;

    /**
     * Asset-owned catalog entry.
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
    public record AssetCatalogEntry(
            String id,
        String catalogName,
        String code,
        boolean active,
        int sortOrder,
        boolean systemDefined,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AssetCatalogEntry {
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
