/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskSignal
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.monitoring.domain.exception.InvalidMonitoringValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidMonitoringValueException("RiskSignal id must not be blank.");
        }
        // HRA-051 required: riskTypeId
        if (riskTypeId == null || riskTypeId.isBlank()) {
            throw new InvalidMonitoringValueException("RiskSignal risk type id must not be blank.");
        }
        // HRA-051 required: riskLevel
        if (riskLevel == null) {
            throw new InvalidMonitoringValueException("RiskSignal risk level must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidMonitoringValueException("RiskSignal status must not be null.");
        }
        // HRA-051 required: raisedAt
        if (raisedAt == null) {
            throw new InvalidMonitoringValueException("RiskSignal raised at must not be null.");
        }

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
