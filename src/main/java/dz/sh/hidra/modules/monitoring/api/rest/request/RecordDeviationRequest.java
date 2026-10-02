/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecordDeviationRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.api.rest.request
 *
 * @Description : REST request to record monitoring deviation.
 *
 */
package dz.sh.hidra.modules.monitoring.api.rest.request;

import dz.sh.hidra.modules.monitoring.domain.value.DeviationSeverity;

import java.math.BigDecimal;

/**
 * REST request to record monitoring deviation.
 */
public record RecordDeviationRequest(
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
        String reasonCode,
        String reasonMessage
) {
}
