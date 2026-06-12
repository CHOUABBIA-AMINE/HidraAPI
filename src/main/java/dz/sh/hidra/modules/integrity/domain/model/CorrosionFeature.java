/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CorrosionFeature
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.model
 *
 * @Description : Corrosion feature.
 *
 */
package dz.sh.hidra.modules.integrity.domain.model;

import dz.sh.hidra.modules.integrity.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Corrosion feature.
     *
         * @param id id
     * @param defectId defectId
     * @param corrosionTypeId corrosionTypeId
     * @param topologyAssetTypeCode topologyAssetTypeCode
     * @param topologyAssetId topologyAssetId
     * @param kilometerPoint kilometerPoint
     * @param length length
     * @param width width
     * @param depth depth
     * @param dimensionUnitId dimensionUnitId
     * @param severity severity
     * @param observedAt observedAt
     * @param notes notes
     */
    public record CorrosionFeature(
            String id,
        String defectId,
        String corrosionTypeId,
        String topologyAssetTypeCode,
        String topologyAssetId,
        BigDecimal kilometerPoint,
        BigDecimal length,
        BigDecimal width,
        BigDecimal depth,
        String dimensionUnitId,
        FindingSeverity severity,
        Instant observedAt,
        String notes
    ) {

        public CorrosionFeature {
        id = normalize(id);
        defectId = normalize(defectId);
        corrosionTypeId = normalize(corrosionTypeId);
        topologyAssetTypeCode = normalize(topologyAssetTypeCode);
        topologyAssetId = normalize(topologyAssetId);
        dimensionUnitId = normalize(dimensionUnitId);
        notes = normalize(notes);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
