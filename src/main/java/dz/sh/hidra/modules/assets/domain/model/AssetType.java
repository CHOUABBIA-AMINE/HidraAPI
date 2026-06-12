/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetType
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Catalog of maintainable asset types.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import java.time.Instant;

    /**
     * Catalog of maintainable asset types.
     *
         * @param id id
     * @param code code
     * @param parentTypeId parentTypeId
     * @param active active
     * @param sortOrder sortOrder
     * @param systemDefined systemDefined
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AssetType(
            String id,
        String code,
        String parentTypeId,
        boolean active,
        int sortOrder,
        boolean systemDefined,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AssetType {
        id = normalize(id);
        code = normalize(code);
        parentTypeId = normalize(parentTypeId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
