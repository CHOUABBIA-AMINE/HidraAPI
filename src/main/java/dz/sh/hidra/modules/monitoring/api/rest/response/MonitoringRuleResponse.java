/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRuleResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.api.rest.response
 *
 * @Description : REST response for monitoring rule.
 *
 */
package dz.sh.hidra.modules.monitoring.api.rest.response;

import dz.sh.hidra.modules.monitoring.domain.value.MonitoringLifecycleStatus;
import dz.sh.hidra.modules.monitoring.domain.value.MonitoringRuleType;

/**
 * REST response for monitoring rule.
 */
public record MonitoringRuleResponse(
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
