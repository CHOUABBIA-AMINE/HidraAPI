/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRuleSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.dto
 *
 * @Description : Monitoring rule summary DTO.
 *
 */
package dz.sh.hidra.modules.monitoring.application.dto;

import dz.sh.hidra.modules.monitoring.domain.value.MonitoringLifecycleStatus;
import dz.sh.hidra.modules.monitoring.domain.value.MonitoringRuleType;

/**
 * Monitoring rule summary DTO.
 */
public record MonitoringRuleSummaryDto(
        String id,
        String code,
        String nameFr,
        MonitoringRuleType ruleType,
        MonitoringLifecycleStatus status,
        String topologyAssetType,
        String topologyAssetId,
        String telemetryPointId
) {
}
