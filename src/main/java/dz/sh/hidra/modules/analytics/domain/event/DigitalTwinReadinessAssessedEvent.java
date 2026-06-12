/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DigitalTwinReadinessAssessedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.event
 *
 * @Description : Published when digital-twin readiness is assessed.
 *
 */
package dz.sh.hidra.modules.analytics.domain.event;

import java.time.Instant;

/**
 * Published when digital-twin readiness is assessed.
 */
public record DigitalTwinReadinessAssessedEvent(
        String eventId,
    String aggregateId,
    String aggregateType,
    Instant occurredAt,
    String correlationId,
    String actorId
) implements AnalyticsDomainEvent {

    @Override
    public String eventType() {
        return "DigitalTwinReadinessAssessedEvent";
    }
}
