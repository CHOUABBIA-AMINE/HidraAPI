/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTransferPoint
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Official transfer point.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import dz.sh.hidra.modules.custody.domain.value.*;
import java.time.Instant;

    /**
     * Official transfer point.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param topologyAssetTypeCode topologyAssetTypeCode
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCodeSnapshot topologyAssetCodeSnapshot
     * @param topologyAssetNameSnapshot topologyAssetNameSnapshot
     * @param direction direction
     * @param productTypeId productTypeId
     * @param measurementLocationId measurementLocationId
     * @param status status
     * @param effectiveFrom effectiveFrom
     * @param effectiveTo effectiveTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record CustodyTransferPoint(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCodeSnapshot,
        String topologyAssetNameSnapshot,
        CustodyDirection direction,
        String productTypeId,
        String measurementLocationId,
        CustodyPointStatus status,
        Instant effectiveFrom,
        Instant effectiveTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public CustodyTransferPoint {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        topologyAssetTypeCode = normalize(topologyAssetTypeCode);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCodeSnapshot = normalize(topologyAssetCodeSnapshot);
        topologyAssetNameSnapshot = normalize(topologyAssetNameSnapshot);
        productTypeId = normalize(productTypeId);
        measurementLocationId = normalize(measurementLocationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
