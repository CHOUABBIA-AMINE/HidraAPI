/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationDeadLetterOpenedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.event
 *
 * @Description : Published when a dead-letter record is opened.
 *
 */
package dz.sh.hidra.modules.integration.domain.event;

import java.time.Instant;

/**
 * Published when a dead-letter record is opened.
 */
public record IntegrationDeadLetterOpenedEvent(
        String eventId,
    String deadLetterRecordId,
    Instant occurredAt
) implements IntegrationDomainEvent {

    @Override
    public String eventType() {
        return "IntegrationDeadLetterOpenedEvent";
    }
}
