/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskSignal
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.model
 *
 * @Description : Monitoring-derived risk signal.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.model;

import dz.sh.hidra.modules.monitoring.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Monitoring-derived risk signal.
     *
         * @param id id
     * @param sourceDeviationId sourceDeviationId
     * @param sourceEvaluationId sourceEvaluationId
     * @param topologyAssetType topologyAssetType
     * @param topologyAssetId topologyAssetId
     * @param riskTypeId riskTypeId
     * @param riskLevel riskLevel
     * @param riskScore riskScore
     * @param signalPayload signalPayload
     * @param status status
     * @param raisedAt raisedAt
     * @param expiresAt expiresAt
     * @param correlationId correlationId
     */
    public record RiskSignal(
            String id,
        String sourceDeviationId,
        String sourceEvaluationId,
        String topologyAssetType,
        String topologyAssetId,
        String riskTypeId,
        RiskSignalLevel riskLevel,
        BigDecimal riskScore,
        String signalPayload,
        RiskSignalStatus status,
        Instant raisedAt,
        Instant expiresAt,
        String correlationId
    ) {

        public RiskSignal {
        id = normalize(id);
        sourceDeviationId = normalize(sourceDeviationId);
        sourceEvaluationId = normalize(sourceEvaluationId);
        topologyAssetType = normalize(topologyAssetType);
        topologyAssetId = normalize(topologyAssetId);
        riskTypeId = normalize(riskTypeId);
        signalPayload = normalize(signalPayload);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
