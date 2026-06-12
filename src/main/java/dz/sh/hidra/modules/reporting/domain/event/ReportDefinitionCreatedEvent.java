/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportDefinitionCreatedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.event
 *
 * @Description : Published when a report definition is created.
 *
 */
package dz.sh.hidra.modules.reporting.domain.event;

import java.time.Instant;

/**
 * Published when a report definition is created.
 */
public record ReportDefinitionCreatedEvent(
        String eventId,
    String aggregateId,
    String aggregateType,
    Instant occurredAt,
    String correlationId,
    String actorId
) implements ReportingDomainEvent {

    @Override
    public String eventType() {
        return "ReportDefinitionCreatedEvent";
    }
}
