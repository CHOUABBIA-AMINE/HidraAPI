/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationScenarioAssumption
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Explicit assumption used by a simulation scenario.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.value.*;
import java.time.Instant;

    /**
     * Explicit assumption used by a simulation scenario.
     *
         * @param id id
     * @param scenarioId scenarioId
     * @param assumptionTypeId assumptionTypeId
     * @param targetType targetType
     * @param targetId targetId
     * @param parameterCode parameterCode
     * @param valueType valueType
     * @param valueText valueText
     * @param unitCode unitCode
     * @param confidenceLevelId confidenceLevelId
     * @param sourceNote sourceNote
     * @param createdAt createdAt
     */
    public record SimulationScenarioAssumption(
            String id,
        String scenarioId,
        String assumptionTypeId,
        String targetType,
        String targetId,
        String parameterCode,
        SimulationValueType valueType,
        String valueText,
        String unitCode,
        String confidenceLevelId,
        String sourceNote,
        Instant createdAt
    ) {

        public SimulationScenarioAssumption {
        id = normalize(id);
        scenarioId = normalize(scenarioId);
        assumptionTypeId = normalize(assumptionTypeId);
        targetType = normalize(targetType);
        targetId = normalize(targetId);
        parameterCode = normalize(parameterCode);
        valueText = normalize(valueText);
        unitCode = normalize(unitCode);
        confidenceLevelId = normalize(confidenceLevelId);
        sourceNote = normalize(sourceNote);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
