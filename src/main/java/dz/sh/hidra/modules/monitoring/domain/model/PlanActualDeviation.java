/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanActualDeviation
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
