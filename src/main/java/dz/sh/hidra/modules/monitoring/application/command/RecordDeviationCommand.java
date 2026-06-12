/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecordDeviationCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.command
 *
 * @Description : Command to record a plan/actual deviation.
 *
 */
package dz.sh.hidra.modules.monitoring.application.command;

import dz.sh.hidra.modules.monitoring.domain.value.DeviationSeverity;

import java.math.BigDecimal;

/**
 * Command to record a plan/actual deviation.
 */
public record RecordDeviationCommand(
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
