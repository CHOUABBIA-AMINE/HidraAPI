/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : KpiEvaluatedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.event
 *
 * @Description : Published when a KPI is evaluated.
 *
 */
package dz.sh.hidra.modules.analytics.domain.event;

import java.time.Instant;

/**
 * Published when a KPI is evaluated.
 */
public record KpiEvaluatedEvent(
        String eventId,
    String aggregateId,
    String aggregateType,
    Instant occurredAt,
    String correlationId,
    String actorId
) implements AnalyticsDomainEvent {

    @Override
    public String eventType() {
        return "KpiEvaluatedEvent";
    }
}
