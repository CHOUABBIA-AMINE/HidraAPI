/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetSparePartCompatibility
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Compatibility between asset and spare part.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import dz.sh.hidra.modules.assets.domain.value.*;
import java.time.Instant;

    /**
     * Compatibility between asset and spare part.
     *
         * @param id id
     * @param maintainableAssetId maintainableAssetId
     * @param assetTypeId assetTypeId
     * @param assetModelId assetModelId
     * @param sparePartId sparePartId
     * @param compatibilityRule compatibilityRule
     * @param status status
     * @param effectiveFrom effectiveFrom
     * @param effectiveTo effectiveTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AssetSparePartCompatibility(
            String id,
        String maintainableAssetId,
        String assetTypeId,
        String assetModelId,
        String sparePartId,
        String compatibilityRule,
        CompatibilityStatus status,
        Instant effectiveFrom,
        Instant effectiveTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AssetSparePartCompatibility {
        id = normalize(id);
        maintainableAssetId = normalize(maintainableAssetId);
        assetTypeId = normalize(assetTypeId);
        assetModelId = normalize(assetModelId);
        sparePartId = normalize(sparePartId);
        compatibilityRule = normalize(compatibilityRule);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
