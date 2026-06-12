/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanRevisionApprovedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.event
 *
 * @Description : Published when a plan revision is approved.
 *
 */
package dz.sh.hidra.modules.planning.domain.event;

import java.time.Instant;

/**
 * Published when a plan revision is approved.
 */
public record PlanRevisionApprovedEvent(
        String eventId,
    String revisionId,
    String planId,
    Instant occurredAt
) implements PlanningDomainEvent {

    @Override
    public String eventType() {
        return "PlanRevisionApprovedEvent";
    }
}
