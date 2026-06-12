/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FeatureFlagRule
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.model
 *
 * @Description : Feature flag enablement rule.
 *
 */
package dz.sh.hidra.modules.configuration.domain.model;

import dz.sh.hidra.modules.configuration.domain.value.*;
import java.time.Instant;

    /**
     * Feature flag enablement rule.
     *
         * @param id id
     * @param featureFlagId featureFlagId
     * @param ruleName ruleName
     * @param scopeType scopeType
     * @param scopeId scopeId
     * @param conditionExpression conditionExpression
     * @param percentage percentage
     * @param enabled enabled
     * @param priorityOrder priorityOrder
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record FeatureFlagRule(
            String id,
        String featureFlagId,
        String ruleName,
        ConfigurationScopeType scopeType,
        String scopeId,
        String conditionExpression,
        Integer percentage,
        boolean enabled,
        int priorityOrder,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public FeatureFlagRule {
        id = normalize(id);
        featureFlagId = normalize(featureFlagId);
        ruleName = normalize(ruleName);
        scopeId = normalize(scopeId);
        conditionExpression = normalize(conditionExpression);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
