/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationValidationRule
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.model
 *
 * @Description : Validation rule for configuration values.
 *
 */
package dz.sh.hidra.modules.configuration.domain.model;

import dz.sh.hidra.modules.configuration.domain.value.*;
import java.time.Instant;

    /**
     * Validation rule for configuration values.
     *
         * @param id id
     * @param definitionId definitionId
     * @param ruleCode ruleCode
     * @param ruleTypeId ruleTypeId
     * @param expression expression
     * @param configurationJson configurationJson
     * @param status status
     * @param failureMessage failureMessage
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ConfigurationValidationRule(
            String id,
        String definitionId,
        String ruleCode,
        String ruleTypeId,
        String expression,
        String configurationJson,
        ValidationRuleStatus status,
        String failureMessage,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ConfigurationValidationRule {
        id = normalize(id);
        definitionId = normalize(definitionId);
        ruleCode = normalize(ruleCode);
        ruleTypeId = normalize(ruleTypeId);
        expression = normalize(expression);
        configurationJson = normalize(configurationJson);
        failureMessage = normalize(failureMessage);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
