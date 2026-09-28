/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRecommendation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidSimulationValueException("SimulationRecommendation id must not be blank.");
        }
        // HRA-051 required: runId
        if (runId == null || runId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationRecommendation run id must not be blank.");
        }
        // HRA-051 required: recommendationTypeId
        if (recommendationTypeId == null || recommendationTypeId.isBlank()) {
            throw new InvalidSimulationValueException("SimulationRecommendation recommendation type id must not be blank.");
        }
        // HRA-051 required: recommendationStatus
        if (recommendationStatus == null) {
            throw new InvalidSimulationValueException("SimulationRecommendation recommendation status must not be null.");
        }

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
