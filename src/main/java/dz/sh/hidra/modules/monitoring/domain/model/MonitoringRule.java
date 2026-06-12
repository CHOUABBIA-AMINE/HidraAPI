/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRule
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.model
 *
 * @Description : Rule for operational evaluation.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.model;

import dz.sh.hidra.modules.monitoring.domain.value.*;
import java.time.Instant;

    /**
     * Rule for operational evaluation.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param ruleType ruleType
     * @param evaluationFrequencyId evaluationFrequencyId
     * @param topologyAssetType topologyAssetType
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCode topologyAssetCode
     * @param telemetryPointId telemetryPointId
     * @param planningTargetTypeId planningTargetTypeId
     * @param expression expression
     * @param status status
     * @param createdByActorId createdByActorId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record MonitoringRule(
            String id,
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
        MonitoringLifecycleStatus status,
        String createdByActorId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public MonitoringRule {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        evaluationFrequencyId = normalize(evaluationFrequencyId);
        topologyAssetType = normalize(topologyAssetType);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCode = normalize(topologyAssetCode);
        telemetryPointId = normalize(telemetryPointId);
        planningTargetTypeId = normalize(planningTargetTypeId);
        expression = normalize(expression);
        createdByActorId = normalize(createdByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
