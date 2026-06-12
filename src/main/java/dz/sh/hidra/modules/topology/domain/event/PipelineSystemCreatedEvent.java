/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystemCreatedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.event
 *
 * @Description : PipelineSystemCreatedEvent event.
 *
 */
package dz.sh.hidra.modules.topology.domain.event;

import java.time.Instant;
public record PipelineSystemCreatedEvent(String eventId, String pipelineSystemId, Instant occurredAt) implements TopologyDomainEvent { public String eventType() { return "PipelineSystemCreatedEvent"; } }
