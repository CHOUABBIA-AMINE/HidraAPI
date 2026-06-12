/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringThreshold
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.model
 *
 * @Description : Threshold bound to rule or scope.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.model;

import dz.sh.hidra.modules.monitoring.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Threshold bound to rule or scope.
     *
         * @param id id
     * @param ruleId ruleId
     * @param thresholdDirection thresholdDirection
     * @param lowValue lowValue
     * @param highValue highValue
     * @param expectedTextValue expectedTextValue
     * @param unitId unitId
     * @param severity severity
     * @param validFrom validFrom
     * @param validTo validTo
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record MonitoringThreshold(
            String id,
        String ruleId,
        MonitoringThresholdDirection thresholdDirection,
        BigDecimal lowValue,
        BigDecimal highValue,
        String expectedTextValue,
        String unitId,
        DeviationSeverity severity,
        Instant validFrom,
        Instant validTo,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public MonitoringThreshold {
        id = normalize(id);
        ruleId = normalize(ruleId);
        expectedTextValue = normalize(expectedTextValue);
        unitId = normalize(unitId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
