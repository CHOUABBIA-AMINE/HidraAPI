/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRecommendationSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.dto
 *
 * @Description : Simulation recommendation summary DTO.
 *
 */
package dz.sh.hidra.modules.simulation.application.dto;

import dz.sh.hidra.modules.simulation.domain.value.SimulationRecommendationStatus;

import java.time.Instant;

/**
 * Simulation recommendation summary DTO.
 */
public record SimulationRecommendationSummaryDto(
        String id,
        String runId,
        String candidateId,
        String recommendationTypeId,
        SimulationRecommendationStatus recommendationStatus,
        String title,
        String targetModule,
        String targetProposalReference,
        Instant publishedAt
) {
}
