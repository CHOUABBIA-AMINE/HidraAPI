/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationCandidateScore
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Detailed score component for an optimization candidate.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import java.math.BigDecimal;

    /**
     * Detailed score component for an optimization candidate.
     *
         * @param id id
     * @param candidateId candidateId
     * @param objectiveId objectiveId
     * @param scoreCode scoreCode
     * @param scoreValue scoreValue
     * @param weight weight
     * @param rankContribution rankContribution
     * @param explanation explanation
     */
    public record SimulationCandidateScore(
            String id,
        String candidateId,
        String objectiveId,
        String scoreCode,
        BigDecimal scoreValue,
        BigDecimal weight,
        BigDecimal rankContribution,
        String explanation
    ) {

        public SimulationCandidateScore {
        id = normalize(id);
        candidateId = normalize(candidateId);
        objectiveId = normalize(objectiveId);
        scoreCode = normalize(scoreCode);
        explanation = normalize(explanation);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
