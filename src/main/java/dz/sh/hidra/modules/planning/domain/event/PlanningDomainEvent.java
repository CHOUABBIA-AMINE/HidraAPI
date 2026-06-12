/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningDomainEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.event
 *
 * @Description : Planning domain event contract.
 *
 */
package dz.sh.hidra.modules.planning.domain.event;

import java.time.Instant;

/**
 * Planning domain event contract.
 */
public interface PlanningDomainEvent {

    String eventId();

    String eventType();

    Instant occurredAt();
}
