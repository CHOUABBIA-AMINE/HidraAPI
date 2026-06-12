/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRecommendation
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Human-facing recommendation derived from a run or candidate.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.value.*;
import java.time.Instant;

    /**
     * Human-facing recommendation derived from a run or candidate.
     *
         * @param id id
     * @param runId runId
     * @param candidateId candidateId
     * @param recommendationTypeId recommendationTypeId
     * @param recommendationStatus recommendationStatus
     * @param title title
     * @param description description
     * @param confidenceLevelId confidenceLevelId
     * @param targetModule targetModule
     * @param targetProposalReference targetProposalReference
     * @param publishedByActorId publishedByActorId
     * @param publishedAt publishedAt
     * @param createdAt createdAt
     */
    public record SimulationRecommendation(
            String id,
        String runId,
        String candidateId,
        String recommendationTypeId,
        SimulationRecommendationStatus recommendationStatus,
        String title,
        String description,
        String confidenceLevelId,
        String targetModule,
        String targetProposalReference,
        String publishedByActorId,
        Instant publishedAt,
        Instant createdAt
    ) {

        public SimulationRecommendation {
        id = normalize(id);
        runId = normalize(runId);
        candidateId = normalize(candidateId);
        recommendationTypeId = normalize(recommendationTypeId);
        title = normalize(title);
        description = normalize(description);
        confidenceLevelId = normalize(confidenceLevelId);
        targetModule = normalize(targetModule);
        targetProposalReference = normalize(targetProposalReference);
        publishedByActorId = normalize(publishedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
