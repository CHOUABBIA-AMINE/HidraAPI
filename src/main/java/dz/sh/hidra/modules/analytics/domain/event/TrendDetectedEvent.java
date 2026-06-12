/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TrendDetectedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.event
 *
 * @Description : Published when an analytical trend is detected.
 *
 */
package dz.sh.hidra.modules.analytics.domain.event;

import java.time.Instant;

/**
 * Published when an analytical trend is detected.
 */
public record TrendDetectedEvent(
        String eventId,
    String aggregateId,
    String aggregateType,
    Instant occurredAt,
    String correlationId,
    String actorId
) implements AnalyticsDomainEvent {

    @Override
    public String eventType() {
        return "TrendDetectedEvent";
    }
}
