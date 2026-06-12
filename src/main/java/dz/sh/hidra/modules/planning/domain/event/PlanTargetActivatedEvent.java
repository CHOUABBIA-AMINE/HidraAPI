/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanTargetActivatedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.event
 *
 * @Description : Published when a plan target is activated.
 *
 */
package dz.sh.hidra.modules.planning.domain.event;

import java.time.Instant;

/**
 * Published when a plan target is activated.
 */
public record PlanTargetActivatedEvent(
        String eventId,
    String planTargetId,
    Instant occurredAt
) implements PlanningDomainEvent {

    @Override
    public String eventType() {
        return "PlanTargetActivatedEvent";
    }
}
