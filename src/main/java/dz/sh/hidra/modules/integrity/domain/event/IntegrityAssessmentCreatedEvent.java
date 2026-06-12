/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityAssessmentCreatedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.event
 *
 * @Description : Published when an integrity assessment is created.
 *
 */
package dz.sh.hidra.modules.integrity.domain.event;

import java.time.Instant;

/**
 * Published when an integrity assessment is created.
 */
public record IntegrityAssessmentCreatedEvent(
        String eventId,
    String assessmentId,
    Instant occurredAt
) implements IntegrityDomainEvent {

    @Override
    public String eventType() {
        return "IntegrityAssessmentCreatedEvent";
    }
}
