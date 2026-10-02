/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationOptimizationCandidate
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidSimulationValueException("SimulationOptimizationCandidate id must not be blank.");
        }
        // HRA-051 required: runId
        if (runId == null || runId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationOptimizationCandidate run id must not be blank.");
        }
        // HRA-051 required: candidateStatus
        if (candidateStatus == null) {
            throw new InvalidSimulationValueException("SimulationOptimizationCandidate candidate status must not be null.");
        }

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
