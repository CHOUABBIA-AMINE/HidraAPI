/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetTechnicalAttributeDefinition
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Technical attribute definition.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import dz.sh.hidra.modules.assets.domain.value.*;
import java.time.Instant;

    /**
     * Technical attribute definition.
     *
         * @param id id
     * @param assetTypeId assetTypeId
     * @param code code
     * @param name name
     * @param dataType dataType
     * @param unitId unitId
     * @param required required
     * @param active active
     * @param sortOrder sortOrder
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AssetTechnicalAttributeDefinition(
            String id,
        String assetTypeId,
        String code,
        String name,
        TechnicalAttributeDataType dataType,
        String unitId,
        boolean required,
        boolean active,
        int sortOrder,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AssetTechnicalAttributeDefinition {
        id = normalize(id);
        assetTypeId = normalize(assetTypeId);
        code = normalize(code);
        name = normalize(name);
        unitId = normalize(unitId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
