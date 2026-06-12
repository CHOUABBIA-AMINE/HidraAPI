/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationCandidateSubmittedToTopologyEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.event
 *
 * @Description : Published when a simulation candidate is submitted to topology.
 *
 */
package dz.sh.hidra.modules.simulation.domain.event;

import java.time.Instant;

/**
 * Published when a simulation candidate is submitted to topology.
 */
public record SimulationCandidateSubmittedToTopologyEvent(
        String eventId,
    String candidateId,
    String targetProposalReference,
    Instant occurredAt
) implements SimulationDomainEvent {

    @Override
    public String eventType() {
        return "SimulationCandidateSubmittedToTopologyEvent";
    }
}
