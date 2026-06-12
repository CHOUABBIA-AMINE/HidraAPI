/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDomainEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.event
 *
 * @Description : Analytics domain event contract.
 *
 */
package dz.sh.hidra.modules.analytics.domain.event;

import java.time.Instant;

/**
 * Analytics domain event contract.
 */
public interface AnalyticsDomainEvent {

    String eventId();

    String aggregateId();

    String aggregateType();

    String eventType();

    Instant occurredAt();

    String correlationId();

    String actorId();
}
