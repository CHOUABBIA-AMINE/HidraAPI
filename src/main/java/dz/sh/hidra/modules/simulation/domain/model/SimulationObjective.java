/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationObjective
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Optimization objective.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Optimization objective.
     *
         * @param id id
     * @param scenarioId scenarioId
     * @param objectiveTypeId objectiveTypeId
     * @param weight weight
     * @param priorityOrder priorityOrder
     * @param targetType targetType
     * @param targetId targetId
     * @param expressionText expressionText
     * @param createdAt createdAt
     */
    public record SimulationObjective(
            String id,
        String scenarioId,
        String objectiveTypeId,
        BigDecimal weight,
        int priorityOrder,
        String targetType,
        String targetId,
        String expressionText,
        Instant createdAt
    ) {

        public SimulationObjective {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidSimulationValueException("SimulationObjective id must not be blank.");
        }
        // HRA-051 required: scenarioId
        if (scenarioId == null || scenarioId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationObjective scenario id must not be blank.");
        }
        // HRA-051 required: objectiveTypeId
        if (objectiveTypeId == null || objectiveTypeId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationObjective objective type id must not be blank.");
        }
        // HRA-051 required: weight
        if (weight == null) {
            throw new InvalidSimulationValueException("SimulationObjective weight must not be null.");
        }

        id = normalize(id);
        scenarioId = normalize(scenarioId);
        objectiveTypeId = normalize(objectiveTypeId);
        targetType = normalize(targetType);
        targetId = normalize(targetId);
        expressionText = normalize(expressionText);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
