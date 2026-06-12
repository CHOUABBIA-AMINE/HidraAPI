/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationSolverTrace
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Diagnostic trace for solver execution.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Diagnostic trace for solver execution.
     *
         * @param id id
     * @param runId runId
     * @param iterationNumber iterationNumber
     * @param traceLevel traceLevel
     * @param metricCode metricCode
     * @param metricValue metricValue
     * @param message message
     * @param recordedAt recordedAt
     */
    public record SimulationSolverTrace(
            String id,
        String runId,
        Integer iterationNumber,
        SimulationTraceLevel traceLevel,
        String metricCode,
        BigDecimal metricValue,
        String message,
        Instant recordedAt
    ) {

        public SimulationSolverTrace {
        id = normalize(id);
        runId = normalize(runId);
        metricCode = normalize(metricCode);
        message = normalize(message);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
