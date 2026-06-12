/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationOptimizationCandidate
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Candidate network or operating configuration produced by optimization.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Candidate network or operating configuration produced by optimization.
     *
         * @param id id
     * @param runId runId
     * @param candidateNumber candidateNumber
     * @param candidateStatus candidateStatus
     * @param feasible feasible
     * @param objectiveScore objectiveScore
     * @param rank rank
     * @param summaryText summaryText
     * @param selectedByActorId selectedByActorId
     * @param selectedAt selectedAt
     * @param createdAt createdAt
     */
    public record SimulationOptimizationCandidate(
            String id,
        String runId,
        int candidateNumber,
        SimulationCandidateStatus candidateStatus,
        boolean feasible,
        BigDecimal objectiveScore,
        Integer rank,
        String summaryText,
        String selectedByActorId,
        Instant selectedAt,
        Instant createdAt
    ) {

        public SimulationOptimizationCandidate {
        id = normalize(id);
        runId = normalize(runId);
        summaryText = normalize(summaryText);
        selectedByActorId = normalize(selectedByActorId);
        }
        public boolean officialTopologyState() {
            return false;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
