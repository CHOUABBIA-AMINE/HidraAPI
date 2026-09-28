/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanActualDeviation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.model
 *
 * @Description : Actual-vs-expected deviation.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.model;

import dz.sh.hidra.modules.monitoring.domain.exception.InvalidMonitoringValueException;
import dz.sh.hidra.modules.monitoring.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Actual-vs-expected deviation.
     *
         * @param id id
     * @param evaluationId evaluationId
     * @param planTargetId planTargetId
     * @param expectedFlowStateId expectedFlowStateId
     * @param trustedTelemetryReadingId trustedTelemetryReadingId
     * @param telemetryPointId telemetryPointId
     * @param topologyAssetType topologyAssetType
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCode topologyAssetCode
     * @param actualValue actualValue
     * @param expectedValue expectedValue
     * @param differenceValue differenceValue
     * @param differencePercent differencePercent
     * @param unitId unitId
     * @param severity severity
     * @param status status
     * @param detectedAt detectedAt
     * @param resolvedAt resolvedAt
     * @param reasonCode reasonCode
     * @param reasonMessage reasonMessage
     */
    public record PlanActualDeviation(
            String id,
        String evaluationId,
        String planTargetId,
        String expectedFlowStateId,
        String trustedTelemetryReadingId,
        String telemetryPointId,
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        BigDecimal actualValue,
        BigDecimal expectedValue,
        BigDecimal differenceValue,
        BigDecimal differencePercent,
        String unitId,
        DeviationSeverity severity,
        DeviationStatus status,
        Instant detectedAt,
        Instant resolvedAt,
        String reasonCode,
        String reasonMessage
    ) {

        public PlanActualDeviation {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidMonitoringValueException("PlanActualDeviation id must not be blank.");
        }
        // HRA-051 required: planTargetId
        if (planTargetId == null || planTargetId.isBlank()) {
            throw new InvalidMonitoringValueException("PlanActualDeviation plan target id must not be blank.");
        }
        // HRA-051 required: topologyAssetId
        if (topologyAssetId == null || topologyAssetId.isBlank()) {
            throw new InvalidMonitoringValueException("PlanActualDeviation topology asset id must not be blank.");
        }
        // HRA-051 required: severity
        if (severity == null) {
            throw new InvalidMonitoringValueException("PlanActualDeviation severity must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidMonitoringValueException("PlanActualDeviation status must not be null.");
        }
        // HRA-051 required: detectedAt
        if (detectedAt == null) {
            throw new InvalidMonitoringValueException("PlanActualDeviation detected at must not be null.");
        }

        id = normalize(id);
        evaluationId = normalize(evaluationId);
        planTargetId = normalize(planTargetId);
        expectedFlowStateId = normalize(expectedFlowStateId);
        trustedTelemetryReadingId = normalize(trustedTelemetryReadingId);
        telemetryPointId = normalize(telemetryPointId);
        topologyAssetType = normalize(topologyAssetType);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCode = normalize(topologyAssetCode);
        unitId = normalize(unitId);
        reasonCode = normalize(reasonCode);
        reasonMessage = normalize(reasonMessage);
        }
        public boolean open() {
            return status == DeviationStatus.OPEN;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
