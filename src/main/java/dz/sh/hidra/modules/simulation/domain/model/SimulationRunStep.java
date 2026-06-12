/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRunStep
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
