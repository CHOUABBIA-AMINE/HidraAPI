/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRunStep
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Execution step inside a simulation run.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.simulation.domain.value.*;
import java.time.Instant;

    /**
     * Execution step inside a simulation run.
     *
         * @param id id
     * @param runId runId
     * @param stepOrder stepOrder
     * @param stepCode stepCode
     * @param status status
     * @param startedAt startedAt
     * @param completedAt completedAt
     * @param message message
     */
    public record SimulationRunStep(
            String id,
        String runId,
        int stepOrder,
        String stepCode,
        SimulationRunStepStatus status,
        Instant startedAt,
        Instant completedAt,
        String message
    ) {

        public SimulationRunStep {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidSimulationValueException("SimulationRunStep id must not be blank.");
        }
        // HRA-051 required: runId
        if (runId == null || runId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationRunStep run id must not be blank.");
        }
        // HRA-051 required: stepCode
        if (stepCode == null || stepCode.isBlank()) {
            throw new InvalidSimulationValueException("SimulationRunStep step code must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidSimulationValueException("SimulationRunStep status must not be null.");
        }

        id = normalize(id);
        runId = normalize(runId);
        stepCode = normalize(stepCode);
        message = normalize(message);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
