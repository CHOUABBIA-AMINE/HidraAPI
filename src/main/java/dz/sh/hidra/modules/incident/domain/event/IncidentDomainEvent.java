/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentDomainEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.event
 *
 * @Description : Incident domain event contract.
 *
 */
package dz.sh.hidra.modules.incident.domain.event;

import java.time.Instant;

/**
 * Incident domain event contract.
 */
public interface IncidentDomainEvent {

    String eventId();

    String eventType();

    Instant occurredAt();
}
