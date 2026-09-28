/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionRule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.model
 *
 * @Description : Rule or parameter attached to a profile and method.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.model;

import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;
import dz.sh.hidra.modules.leakdetection.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Rule or parameter attached to a profile and method.
     *
         * @param id id
     * @param profileId profileId
     * @param methodId methodId
     * @param code code
     * @param nameFr nameFr
     * @param ruleType ruleType
     * @param expression expression
     * @param parameterJson parameterJson
     * @param thresholdValue thresholdValue
     * @param unitId unitId
     * @param status status
     * @param validFrom validFrom
     * @param validTo validTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record LeakDetectionRule(
            String id,
        String profileId,
        String methodId,
        String code,
        String nameFr,
        String ruleType,
        String expression,
        String parameterJson,
        BigDecimal thresholdValue,
        String unitId,
        LeakDetectionRuleStatus status,
        Instant validFrom,
        Instant validTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public LeakDetectionRule {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakDetectionRule id must not be blank.");
        }
        // HRA-051 required: profileId
        if (profileId == null || profileId.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakDetectionRule profile id must not be blank.");
        }
        // HRA-051 required: methodId
        if (methodId == null || methodId.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakDetectionRule method id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakDetectionRule code must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidLeakDetectionValueException("LeakDetectionRule status must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidLeakDetectionValueException("LeakDetectionRule valid to must not be before valid from.");
        }

        id = normalize(id);
        profileId = normalize(profileId);
        methodId = normalize(methodId);
        code = normalize(code);
        nameFr = normalize(nameFr);
        ruleType = normalize(ruleType);
        expression = normalize(expression);
        parameterJson = normalize(parameterJson);
        unitId = normalize(unitId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
