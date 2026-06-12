/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationResultSummary
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : High-level result of a simulation run.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

    /**
     * High-level result of a simulation run.
     *
         * @param id id
     * @param runId runId
     * @param feasible feasible
     * @param objectiveScore objectiveScore
     * @param constraintViolationCount constraintViolationCount
     * @param warningCount warningCount
     * @param resultStatusId resultStatusId
     * @param summaryText summaryText
     * @param createdAt createdAt
     */
    public record SimulationResultSummary(
            String id,
        String runId,
        boolean feasible,
        BigDecimal objectiveScore,
        int constraintViolationCount,
        int warningCount,
        String resultStatusId,
        String summaryText,
        Instant createdAt
    ) {

        public SimulationResultSummary {
        id = normalize(id);
        runId = normalize(runId);
        resultStatusId = normalize(resultStatusId);
        summaryText = normalize(summaryText);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
