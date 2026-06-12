/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateMonitoringRuleCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.command
 *
 * @Description : Command to create a monitoring rule.
 *
 */
package dz.sh.hidra.modules.monitoring.application.command;

import dz.sh.hidra.modules.monitoring.domain.value.MonitoringRuleType;

/**
 * Command to create a monitoring rule.
 */
public record CreateMonitoringRuleCommand(
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
