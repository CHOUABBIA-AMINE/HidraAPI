/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateMonitoringRuleRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.api.rest.request
 *
 * @Description : REST request to create monitoring rule.
 *
 */
package dz.sh.hidra.modules.monitoring.api.rest.request;

import dz.sh.hidra.modules.monitoring.domain.value.MonitoringRuleType;

/**
 * REST request to create monitoring rule.
 */
public record CreateMonitoringRuleRequest(
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        MonitoringRuleType ruleType,
        String evaluationFrequencyId,
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        String telemetryPointId,
        String planningTargetTypeId,
        String expression,
        String createdByActorId
) {
}
