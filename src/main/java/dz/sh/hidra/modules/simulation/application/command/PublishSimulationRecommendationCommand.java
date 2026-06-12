/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PublishSimulationRecommendationCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.command
 *
 * @Description : Command to publish a simulation recommendation.
 *
 */
package dz.sh.hidra.modules.simulation.application.command;

/**
 * Command to publish a simulation recommendation.
 */
public record PublishSimulationRecommendationCommand(
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
