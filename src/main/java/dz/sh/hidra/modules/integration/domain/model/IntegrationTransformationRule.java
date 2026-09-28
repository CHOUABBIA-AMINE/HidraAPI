/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationTransformationRule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Deterministic transformation rule.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
import java.time.Instant;

    /**
     * Deterministic transformation rule.
     *
         * @param id id
     * @param mappingProfileId mappingProfileId
     * @param code code
     * @param ruleTypeId ruleTypeId
     * @param expression expression
     * @param configurationJson configurationJson
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record IntegrationTransformationRule(
            String id,
        String mappingProfileId,
        String code,
        String ruleTypeId,
        String expression,
        String configurationJson,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public IntegrationTransformationRule {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationTransformationRule id must not be blank.");
        }
        // HRA-051 required: mappingProfileId
        if (mappingProfileId == null || mappingProfileId.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationTransformationRule mapping profile id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationTransformationRule code must not be blank.");
        }
        // HRA-051 required: ruleTypeId
        if (ruleTypeId == null || ruleTypeId.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationTransformationRule rule type id must not be blank.");
        }

        id = normalize(id);
        mappingProfileId = normalize(mappingProfileId);
        code = normalize(code);
        ruleTypeId = normalize(ruleTypeId);
        expression = normalize(expression);
        configurationJson = normalize(configurationJson);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
