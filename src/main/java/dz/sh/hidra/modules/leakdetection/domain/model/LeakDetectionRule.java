/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionRule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
