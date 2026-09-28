/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringThreshold
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.monitoring.domain.exception.InvalidMonitoringValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidMonitoringValueException("MonitoringThreshold id must not be blank.");
        }
        // HRA-051 required: ruleId
        if (ruleId == null || ruleId.isBlank()) {
            throw new InvalidMonitoringValueException("MonitoringThreshold rule id must not be blank.");
        }
        // HRA-051 required: thresholdDirection
        if (thresholdDirection == null) {
            throw new InvalidMonitoringValueException("MonitoringThreshold threshold direction must not be null.");
        }
        // HRA-051 required: severity
        if (severity == null) {
            throw new InvalidMonitoringValueException("MonitoringThreshold severity must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidMonitoringValueException("MonitoringThreshold valid to must not be before valid from.");
        }

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
