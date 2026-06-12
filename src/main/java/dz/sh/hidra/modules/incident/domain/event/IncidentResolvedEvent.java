/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentResolvedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.event
 *
 * @Description : Published when an incident is resolved.
 *
 */
package dz.sh.hidra.modules.incident.domain.event;

import java.time.Instant;

/**
 * Published when an incident is resolved.
 */
public record IncidentResolvedEvent(
        String eventId,
    String incidentId,
    String resolutionId,
    Instant occurredAt
) implements IncidentDomainEvent {

    @Override
    public String eventType() {
        return "IncidentResolvedEvent";
    }
}
