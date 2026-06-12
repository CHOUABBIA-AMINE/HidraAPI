/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakLocalizationEstimate
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.model
 *
 * @Description : Estimated leak location and uncertainty.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Estimated leak location and uncertainty.
     *
         * @param id id
     * @param candidateId candidateId
     * @param topologyAssetType topologyAssetType
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCode topologyAssetCode
     * @param estimatedKilometerPoint estimatedKilometerPoint
     * @param uncertaintyRadiusMeters uncertaintyRadiusMeters
     * @param latitude latitude
     * @param longitude longitude
     * @param confidenceScore confidenceScore
     * @param methodId methodId
     * @param estimatedAt estimatedAt
     * @param notes notes
     */
    public record LeakLocalizationEstimate(
            String id,
        String candidateId,
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        BigDecimal estimatedKilometerPoint,
        BigDecimal uncertaintyRadiusMeters,
        BigDecimal latitude,
        BigDecimal longitude,
        BigDecimal confidenceScore,
        String methodId,
        Instant estimatedAt,
        String notes
    ) {

        public LeakLocalizationEstimate {
        id = normalize(id);
        candidateId = normalize(candidateId);
        topologyAssetType = normalize(topologyAssetType);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCode = normalize(topologyAssetCode);
        methodId = normalize(methodId);
        notes = normalize(notes);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
