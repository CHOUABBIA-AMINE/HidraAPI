/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityRegisteredEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.event
 *
 * @Description : FacilityRegisteredEvent event.
 *
 */
package dz.sh.hidra.modules.topology.domain.event;

import java.time.Instant;
public record FacilityRegisteredEvent(String eventId, String facilityId, Instant occurredAt) implements TopologyDomainEvent { public String eventType() { return "FacilityRegisteredEvent"; } }
