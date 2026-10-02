/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringQueryUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.port.in
 *
 * @Description : Provides monitoring-rule and plan-versus-actual deviation read models.
 *
 */
package dz.sh.hidra.modules.monitoring.application.port.in;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface MonitoringQueryUseCase {

    Page<MonitoringRuleView> rules(String status, String topologyAssetId, String telemetryPointId, int page, int size);

    MonitoringRuleView rule(String id);

    Page<DeviationView> deviations(
            String planTargetId,
            String status,
            String severity,
            String topologyAssetId,
            String telemetryPointId,
            Instant from,
            Instant to,
            int page,
            int size
    );

    DeviationView deviation(String id);

    record Page<T>(List<T> content, int page, int size, long totalElements, int totalPages, boolean hasNext) { }

    record MonitoringRuleView(
            String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String ruleType,
            String evaluationFrequencyId,
            String topologyAssetType,
            String topologyAssetId,
            String topologyAssetCode,
            String telemetryPointId,
            String planningTargetTypeId,
            String status,
            String createdByActorId,
            Instant createdAt,
            Instant updatedAt
    ) { }

    record DeviationView(
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
            String severity,
            String status,
            Instant detectedAt,
            Instant resolvedAt,
            String reasonCode,
            String reasonMessage
    ) { }
}
