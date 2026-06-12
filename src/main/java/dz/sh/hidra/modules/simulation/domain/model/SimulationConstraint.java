/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationConstraint
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Constraint checked during simulation or optimization.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Constraint checked during simulation or optimization.
     *
         * @param id id
     * @param scenarioId scenarioId
     * @param constraintTypeId constraintTypeId
     * @param targetType targetType
     * @param targetId targetId
     * @param expressionText expressionText
     * @param limitValue limitValue
     * @param unitCode unitCode
     * @param severityId severityId
     * @param active active
     * @param createdAt createdAt
     */
    public record SimulationConstraint(
            String id,
        String scenarioId,
        String constraintTypeId,
        String targetType,
        String targetId,
        String expressionText,
        BigDecimal limitValue,
        String unitCode,
        String severityId,
        boolean active,
        Instant createdAt
    ) {

        public SimulationConstraint {
        id = normalize(id);
        scenarioId = normalize(scenarioId);
        constraintTypeId = normalize(constraintTypeId);
        targetType = normalize(targetType);
        targetId = normalize(targetId);
        expressionText = normalize(expressionText);
        unitCode = normalize(unitCode);
        severityId = normalize(severityId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
