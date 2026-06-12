/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRecommendationPublishedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.event
 *
 * @Description : Published when a simulation recommendation is published.
 *
 */
package dz.sh.hidra.modules.simulation.domain.event;

import java.time.Instant;

/**
 * Published when a simulation recommendation is published.
 */
public record SimulationRecommendationPublishedEvent(
        String eventId,
    String recommendationId,
    String runId,
    Instant occurredAt
) implements SimulationDomainEvent {

    @Override
    public String eventType() {
        return "SimulationRecommendationPublishedEvent";
    }
}
