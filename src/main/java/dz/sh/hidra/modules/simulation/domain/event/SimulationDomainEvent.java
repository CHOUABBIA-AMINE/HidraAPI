/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationDomainEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.event
 *
 * @Description : Simulation domain event contract.
 *
 */
package dz.sh.hidra.modules.simulation.domain.event;

import java.time.Instant;

/**
 * Simulation domain event contract.
 */
public interface SimulationDomainEvent {

    String eventId();

    String eventType();

    Instant occurredAt();
}
