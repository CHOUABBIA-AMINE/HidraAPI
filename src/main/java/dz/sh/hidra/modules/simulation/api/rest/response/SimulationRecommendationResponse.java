/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRecommendationResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.api.rest.response
 *
 * @Description : REST response for simulation recommendation.
 *
 */
package dz.sh.hidra.modules.simulation.api.rest.response;

import dz.sh.hidra.modules.simulation.domain.value.SimulationRecommendationStatus;
import java.time.Instant;

/**
 * REST response for simulation recommendation.
 */
public record SimulationRecommendationResponse(
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
