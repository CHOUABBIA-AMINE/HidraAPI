/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationValidationRule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.configuration.domain.exception.InvalidConfigurationValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidConfigurationValueException("ConfigurationValidationRule id must not be blank.");
        }
        // HRA-051 required: definitionId
        if (definitionId == null || definitionId.isBlank()) {
            throw new InvalidConfigurationValueException("ConfigurationValidationRule definition id must not be blank.");
        }
        // HRA-051 required: ruleCode
        if (ruleCode == null || ruleCode.isBlank()) {
            throw new InvalidConfigurationValueException("ConfigurationValidationRule rule code must not be blank.");
        }
        // HRA-051 required: ruleTypeId
        if (ruleTypeId == null || ruleTypeId.isBlank()) {
            throw new InvalidConfigurationValueException("ConfigurationValidationRule rule type id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidConfigurationValueException("ConfigurationValidationRule status must not be null.");
        }

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
