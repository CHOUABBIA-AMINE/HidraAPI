/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDatasetPublishedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.event
 *
 * @Description : Published when an analytics dataset version is published.
 *
 */
package dz.sh.hidra.modules.analytics.domain.event;

import java.time.Instant;

/**
 * Published when an analytics dataset version is published.
 */
public record AnalyticsDatasetPublishedEvent(
        String eventId,
    String aggregateId,
    String aggregateType,
    Instant occurredAt,
    String correlationId,
    String actorId
) implements AnalyticsDomainEvent {

    @Override
    public String eventType() {
        return "AnalyticsDatasetPublishedEvent";
    }
}
