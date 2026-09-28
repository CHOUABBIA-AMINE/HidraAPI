/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakLocalizationEstimate
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakLocalizationEstimate id must not be blank.");
        }
        // HRA-051 required: candidateId
        if (candidateId == null || candidateId.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakLocalizationEstimate candidate id must not be blank.");
        }
        // HRA-051 required: topologyAssetId
        if (topologyAssetId == null || topologyAssetId.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakLocalizationEstimate topology asset id must not be blank.");
        }
        // HRA-051 required: topologyAssetCode
        if (topologyAssetCode == null || topologyAssetCode.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakLocalizationEstimate topology asset code must not be blank.");
        }
        // HRA-051 required: confidenceScore
        if (confidenceScore == null) {
            throw new InvalidLeakDetectionValueException("LeakLocalizationEstimate confidence score must not be null.");
        }
        // HRA-051 required: methodId
        if (methodId == null || methodId.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakLocalizationEstimate method id must not be blank.");
        }
        // HRA-051 required: estimatedAt
        if (estimatedAt == null) {
            throw new InvalidLeakDetectionValueException("LeakLocalizationEstimate estimated at must not be null.");
        }

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
