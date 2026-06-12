/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationTransformationRule
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
