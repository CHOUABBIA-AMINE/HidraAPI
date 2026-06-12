/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityProgramCreatedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.event
 *
 * @Description : Published when an integrity program is created.
 *
 */
package dz.sh.hidra.modules.integrity.domain.event;

import java.time.Instant;

/**
 * Published when an integrity program is created.
 */
public record IntegrityProgramCreatedEvent(
        String eventId,
    String programId,
    Instant occurredAt
) implements IntegrityDomainEvent {

    @Override
    public String eventType() {
        return "IntegrityProgramCreatedEvent";
    }
}
