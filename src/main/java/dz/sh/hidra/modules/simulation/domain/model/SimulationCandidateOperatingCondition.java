/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationCandidateOperatingCondition
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidSimulationValueException("SimulationCandidateOperatingCondition id must not be blank.");
        }
        // HRA-051 required: candidateId
        if (candidateId == null || candidateId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationCandidateOperatingCondition candidate id must not be blank.");
        }
        // HRA-051 required: targetId
        if (targetId == null || targetId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationCandidateOperatingCondition target id must not be blank.");
        }
        // HRA-051 required: metricCode
        if (metricCode == null || metricCode.isBlank()) {
            throw new InvalidSimulationValueException("SimulationCandidateOperatingCondition metric code must not be blank.");
        }
        // HRA-051 required: expectedValue
        if (expectedValue == null) {
            throw new InvalidSimulationValueException("SimulationCandidateOperatingCondition expected value must not be null.");
        }

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
