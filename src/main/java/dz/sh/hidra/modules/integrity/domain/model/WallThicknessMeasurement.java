/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WallThicknessMeasurement
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.model
 *
 * @Description : Wall-thickness measurement.
 *
 */
package dz.sh.hidra.modules.integrity.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Wall-thickness measurement.
     *
         * @param id id
     * @param inspectionRunId inspectionRunId
     * @param topologyAssetTypeCode topologyAssetTypeCode
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCodeSnapshot topologyAssetCodeSnapshot
     * @param kilometerPoint kilometerPoint
     * @param nominalThickness nominalThickness
     * @param measuredThickness measuredThickness
     * @param thicknessUnitId thicknessUnitId
     * @param metalLossPercent metalLossPercent
     * @param measuredAt measuredAt
     * @param measurementMethodId measurementMethodId
     */
    public record WallThicknessMeasurement(
            String id,
        String inspectionRunId,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCodeSnapshot,
        BigDecimal kilometerPoint,
        BigDecimal nominalThickness,
        BigDecimal measuredThickness,
        String thicknessUnitId,
        BigDecimal metalLossPercent,
        Instant measuredAt,
        String measurementMethodId
    ) {

        public WallThicknessMeasurement {
        id = normalize(id);
        inspectionRunId = normalize(inspectionRunId);
        topologyAssetTypeCode = normalize(topologyAssetTypeCode);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCodeSnapshot = normalize(topologyAssetCodeSnapshot);
        thicknessUnitId = normalize(thicknessUnitId);
        measurementMethodId = normalize(measurementMethodId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
