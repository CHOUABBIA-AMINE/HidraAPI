/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PublishSimulationRecommendationRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.api.rest.request
 *
 * @Description : REST request for publish simulation recommendation.
 *
 */
package dz.sh.hidra.modules.simulation.api.rest.request;

/**
 * REST request for publish simulation recommendation.
 */
public record PublishSimulationRecommendationRequest(
        String runId,
        String candidateId,
        String recommendationTypeId,
        String title,
        String description,
        String confidenceLevelId,
        String targetModule,
        String targetProposalReference,
        String publishedByActorId
) {
}
