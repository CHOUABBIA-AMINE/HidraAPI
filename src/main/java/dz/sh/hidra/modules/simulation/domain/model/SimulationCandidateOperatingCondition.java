/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationCandidateOperatingCondition
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Expected operating state if a candidate is adopted.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Expected operating state if a candidate is adopted.
     *
         * @param id id
     * @param candidateId candidateId
     * @param targetType targetType
     * @param targetId targetId
     * @param metricCode metricCode
     * @param expectedValue expectedValue
     * @param unitCode unitCode
     * @param timeOffsetSeconds timeOffsetSeconds
     * @param createdAt createdAt
     */
    public record SimulationCandidateOperatingCondition(
            String id,
        String candidateId,
        String targetType,
        String targetId,
        String metricCode,
        BigDecimal expectedValue,
        String unitCode,
        Long timeOffsetSeconds,
        Instant createdAt
    ) {

        public SimulationCandidateOperatingCondition {
        id = normalize(id);
        candidateId = normalize(candidateId);
        targetType = normalize(targetType);
        targetId = normalize(targetId);
        metricCode = normalize(metricCode);
        unitCode = normalize(unitCode);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
