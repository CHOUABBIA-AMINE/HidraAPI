/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RestrictedReportAccessedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.event
 *
 * @Description : Published when a restricted report is accessed.
 *
 */
package dz.sh.hidra.modules.reporting.domain.event;

import java.time.Instant;

/**
 * Published when a restricted report is accessed.
 */
public record RestrictedReportAccessedEvent(
        String eventId,
    String aggregateId,
    String aggregateType,
    Instant occurredAt,
    String correlationId,
    String actorId
) implements ReportingDomainEvent {

    @Override
    public String eventType() {
        return "RestrictedReportAccessedEvent";
    }
}
