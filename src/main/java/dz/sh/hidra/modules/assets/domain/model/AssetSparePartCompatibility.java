/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetSparePartCompatibility
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAssetsValueException("AssetSparePartCompatibility id must not be blank.");
        }
        // HRA-051 required: sparePartId
        if (sparePartId == null || sparePartId.isBlank()) {
            throw new InvalidAssetsValueException("AssetSparePartCompatibility spare part id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidAssetsValueException("AssetSparePartCompatibility status must not be null.");
        }
        // HRA-051 order: effectiveFrom <= effectiveTo
        if (effectiveFrom != null && effectiveTo != null && effectiveTo.isBefore(effectiveFrom)) {
            throw new InvalidAssetsValueException("AssetSparePartCompatibility effective to must not be before effective from.");
        }

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
