/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetTechnicalAttributeValue
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Technical attribute value.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Technical attribute value.
     *
         * @param id id
     * @param maintainableAssetId maintainableAssetId
     * @param attributeDefinitionId attributeDefinitionId
     * @param textValue textValue
     * @param numericValue numericValue
     * @param booleanValue booleanValue
     * @param dateValue dateValue
     * @param catalogValueId catalogValueId
     * @param unitId unitId
     * @param effectiveFrom effectiveFrom
     * @param effectiveTo effectiveTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AssetTechnicalAttributeValue(
            String id,
        String maintainableAssetId,
        String attributeDefinitionId,
        String textValue,
        BigDecimal numericValue,
        boolean booleanValue,
        Instant dateValue,
        String catalogValueId,
        String unitId,
        Instant effectiveFrom,
        Instant effectiveTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AssetTechnicalAttributeValue {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAssetsValueException("AssetTechnicalAttributeValue id must not be blank.");
        }
        // HRA-051 required: maintainableAssetId
        if (maintainableAssetId == null || maintainableAssetId.isBlank()) {
            throw new InvalidAssetsValueException("AssetTechnicalAttributeValue maintainable asset id must not be blank.");
        }
        // HRA-051 required: attributeDefinitionId
        if (attributeDefinitionId == null || attributeDefinitionId.isBlank()) {
            throw new InvalidAssetsValueException("AssetTechnicalAttributeValue attribute definition id must not be blank.");
        }
        // HRA-051 order: effectiveFrom <= effectiveTo
        if (effectiveFrom != null && effectiveTo != null && effectiveTo.isBefore(effectiveFrom)) {
            throw new InvalidAssetsValueException("AssetTechnicalAttributeValue effective to must not be before effective from.");
        }

        id = normalize(id);
        maintainableAssetId = normalize(maintainableAssetId);
        attributeDefinitionId = normalize(attributeDefinitionId);
        textValue = normalize(textValue);
        catalogValueId = normalize(catalogValueId);
        unitId = normalize(unitId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
