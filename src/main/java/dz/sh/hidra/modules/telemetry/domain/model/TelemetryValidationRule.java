/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryValidationRule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.model
 *
 * @Description : Configurable validation rule for readings, points, or sources.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.model;

import dz.sh.hidra.modules.telemetry.domain.value.*;
import java.time.Instant;

    /**
     * Configurable validation rule for readings, points, or sources.
     *
         * @param id id
     * @param code code
     * @param name name
     * @param scopeType scopeType
     * @param scopeReferenceId scopeReferenceId
     * @param ruleTypeId ruleTypeId
     * @param severity severity
     * @param actionOnFailure actionOnFailure
     * @param expression expression
     * @param configurationJson configurationJson
     * @param active active
     * @param validFrom validFrom
     * @param validTo validTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record TelemetryValidationRule(
            String id,
        String code,
        String name,
        ValidationScopeType scopeType,
        String scopeReferenceId,
        String ruleTypeId,
        ValidationSeverity severity,
        ValidationFailureAction actionOnFailure,
        String expression,
        String configurationJson,
        boolean active,
        Instant validFrom,
        Instant validTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public TelemetryValidationRule {
        id = normalize(id);
        code = normalize(code);
        name = normalize(name);
        scopeReferenceId = normalize(scopeReferenceId);
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
