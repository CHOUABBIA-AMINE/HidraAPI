/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentOpenedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.event
 *
 * @Description : Published when an incident is opened.
 *
 */
package dz.sh.hidra.modules.incident.domain.event;

import java.time.Instant;

/**
 * Published when an incident is opened.
 */
public record IncidentOpenedEvent(
        String eventId,
    String incidentId,
    String incidentNumber,
    Instant occurredAt
) implements IncidentDomainEvent {

    @Override
    public String eventType() {
        return "IncidentOpenedEvent";
    }
}
