/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetTechnicalAttributeDefinition
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAssetsValueException("AssetTechnicalAttributeDefinition id must not be blank.");
        }
        // HRA-051 required: assetTypeId
        if (assetTypeId == null || assetTypeId.isBlank()) {
            throw new InvalidAssetsValueException("AssetTechnicalAttributeDefinition asset type id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidAssetsValueException("AssetTechnicalAttributeDefinition code must not be blank.");
        }
        // HRA-051 required: dataType
        if (dataType == null) {
            throw new InvalidAssetsValueException("AssetTechnicalAttributeDefinition data type must not be null.");
        }

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
