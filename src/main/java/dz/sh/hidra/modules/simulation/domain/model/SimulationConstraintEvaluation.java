/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationConstraintEvaluation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Outcome of a simulation constraint check.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Outcome of a simulation constraint check.
     *
         * @param id id
     * @param runId runId
     * @param constraintId constraintId
     * @param status status
     * @param observedValue observedValue
     * @param limitValue limitValue
     * @param unitCode unitCode
     * @param severityId severityId
     * @param explanation explanation
     * @param createdAt createdAt
     */
    public record SimulationConstraintEvaluation(
            String id,
        String runId,
        String constraintId,
        SimulationConstraintEvaluationStatus status,
        BigDecimal observedValue,
        BigDecimal limitValue,
        String unitCode,
        String severityId,
        String explanation,
        Instant createdAt
    ) {

        public SimulationConstraintEvaluation {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidSimulationValueException("SimulationConstraintEvaluation id must not be blank.");
        }
        // HRA-051 required: runId
        if (runId == null || runId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationConstraintEvaluation run id must not be blank.");
        }
        // HRA-051 required: constraintId
        if (constraintId == null || constraintId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationConstraintEvaluation constraint id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidSimulationValueException("SimulationConstraintEvaluation status must not be null.");
        }
        // HRA-051 required: severityId
        if (severityId == null || severityId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationConstraintEvaluation severity id must not be blank.");
        }

        id = normalize(id);
        runId = normalize(runId);
        constraintId = normalize(constraintId);
        unitCode = normalize(unitCode);
        severityId = normalize(severityId);
        explanation = normalize(explanation);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
