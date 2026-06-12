/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakCandidateCreatedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.event
 *
 * @Description : Published when a leak candidate is created.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.event;

import java.time.Instant;

/**
 * Published when a leak candidate is created.
 */
public record LeakCandidateCreatedEvent(
        String eventId,
    String candidateId,
    Instant occurredAt
) implements LeakDetectionDomainEvent {

    @Override
    public String eventType() {
        return "LeakCandidateCreatedEvent";
    }
}
