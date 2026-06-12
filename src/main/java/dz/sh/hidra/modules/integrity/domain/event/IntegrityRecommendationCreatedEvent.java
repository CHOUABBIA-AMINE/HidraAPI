/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityRecommendationCreatedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.event
 *
 * @Description : Published when an integrity recommendation is created.
 *
 */
package dz.sh.hidra.modules.integrity.domain.event;

import java.time.Instant;

/**
 * Published when an integrity recommendation is created.
 */
public record IntegrityRecommendationCreatedEvent(
        String eventId,
    String recommendationId,
    Instant occurredAt
) implements IntegrityDomainEvent {

    @Override
    public String eventType() {
        return "IntegrityRecommendationCreatedEvent";
    }
}
