/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringApplicationMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.mapper
 *
 * @Description : Maps monitoring domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.monitoring.application.mapper;

import dz.sh.hidra.modules.monitoring.application.dto.DeviationSummaryDto;
import dz.sh.hidra.modules.monitoring.application.dto.MonitoringRuleSummaryDto;
import dz.sh.hidra.modules.monitoring.domain.model.MonitoringRule;
import dz.sh.hidra.modules.monitoring.domain.model.PlanActualDeviation;

/**
 * Maps monitoring domain models to DTOs.
 */
public final class MonitoringApplicationMapper {

    private MonitoringApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static MonitoringRuleSummaryDto toSummary(MonitoringRule rule) {
        return new MonitoringRuleSummaryDto(
                rule.id(),
                rule.code(),
                rule.nameFr(),
                rule.ruleType(),
                rule.status(),
                rule.topologyAssetType(),
                rule.topologyAssetId(),
                rule.telemetryPointId()
        );
    }

    public static DeviationSummaryDto toSummary(PlanActualDeviation deviation) {
        return new DeviationSummaryDto(
                deviation.id(),
                deviation.planTargetId(),
                deviation.topologyAssetType(),
                deviation.topologyAssetId(),
                deviation.actualValue(),
                deviation.expectedValue(),
                deviation.differencePercent(),
                deviation.severity(),
                deviation.status(),
                deviation.detectedAt()
        );
    }
}
