/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryValidationRule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryValidationRule id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryValidationRule code must not be blank.");
        }
        // HRA-051 required: scopeType
        if (scopeType == null) {
            throw new InvalidTelemetryValueException("TelemetryValidationRule scope type must not be null.");
        }
        // HRA-051 required: ruleTypeId
        if (ruleTypeId == null || ruleTypeId.isBlank()) {
            throw new InvalidTelemetryValueException("TelemetryValidationRule rule type id must not be blank.");
        }
        // HRA-051 required: severity
        if (severity == null) {
            throw new InvalidTelemetryValueException("TelemetryValidationRule severity must not be null.");
        }
        // HRA-051 required: actionOnFailure
        if (actionOnFailure == null) {
            throw new InvalidTelemetryValueException("TelemetryValidationRule action on failure must not be null.");
        }
        // HRA-051 required: validFrom
        if (validFrom == null) {
            throw new InvalidTelemetryValueException("TelemetryValidationRule valid from must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidTelemetryValueException("TelemetryValidationRule valid to must not be before valid from.");
        }

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
