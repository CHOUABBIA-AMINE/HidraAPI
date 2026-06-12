/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseImpactAssessedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.event
 *
 * @Description : Published when an HSE impact assessment is recorded.
 *
 */
package dz.sh.hidra.modules.hse.domain.event;

import java.time.Instant;

/**
 * Published when an HSE impact assessment is recorded.
 */
public record HseImpactAssessedEvent(
        String eventId,
    String hseCaseId,
    String impactAssessmentId,
    Instant occurredAt
) implements HseDomainEvent {

    @Override
    public String eventType() {
        return "HseImpactAssessedEvent";
    }
}
