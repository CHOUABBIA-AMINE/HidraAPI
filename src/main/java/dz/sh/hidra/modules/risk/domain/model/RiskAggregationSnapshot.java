/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAggregationSnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Versioned aggregation of risk posture for a scope and time.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Versioned aggregation of risk posture for a scope and time.
     *
         * @param id id
     * @param scopeType scopeType
     * @param scopeId scopeId
     * @param scopeCodeSnapshot scopeCodeSnapshot
     * @param scopeLabelSnapshot scopeLabelSnapshot
     * @param snapshotDate snapshotDate
     * @param riskMatrixId riskMatrixId
     * @param totalRiskCount totalRiskCount
     * @param criticalRiskCount criticalRiskCount
     * @param highRiskCount highRiskCount
     * @param mediumRiskCount mediumRiskCount
     * @param lowRiskCount lowRiskCount
     * @param averageRiskScore averageRiskScore
     * @param maximumRiskScore maximumRiskScore
     * @param openTreatmentCount openTreatmentCount
     * @param overdueTreatmentCount overdueTreatmentCount
     * @param acceptedRiskCount acceptedRiskCount
     * @param createdAt createdAt
     */
    public record RiskAggregationSnapshot(
            String id,
        String scopeType,
        String scopeId,
        String scopeCodeSnapshot,
        String scopeLabelSnapshot,
        Instant snapshotDate,
        String riskMatrixId,
        int totalRiskCount,
        int criticalRiskCount,
        int highRiskCount,
        int mediumRiskCount,
        int lowRiskCount,
        BigDecimal averageRiskScore,
        BigDecimal maximumRiskScore,
        int openTreatmentCount,
        int overdueTreatmentCount,
        int acceptedRiskCount,
        Instant createdAt
    ) {

        public RiskAggregationSnapshot {
        id = normalize(id);
        scopeType = normalize(scopeType);
        scopeId = normalize(scopeId);
        scopeCodeSnapshot = normalize(scopeCodeSnapshot);
        scopeLabelSnapshot = normalize(scopeLabelSnapshot);
        riskMatrixId = normalize(riskMatrixId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
