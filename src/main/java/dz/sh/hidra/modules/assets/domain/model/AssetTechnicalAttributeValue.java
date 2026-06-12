/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetTechnicalAttributeValue
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
