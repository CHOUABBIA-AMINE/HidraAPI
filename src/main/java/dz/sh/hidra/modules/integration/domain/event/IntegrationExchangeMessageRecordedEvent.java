/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationExchangeMessageRecordedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.event
 *
 * @Description : Published when an exchange message is recorded.
 *
 */
package dz.sh.hidra.modules.integration.domain.event;

import java.time.Instant;

/**
 * Published when an exchange message is recorded.
 */
public record IntegrationExchangeMessageRecordedEvent(
        String eventId,
    String exchangeMessageId,
    String externalSystemId,
    Instant occurredAt
) implements IntegrationDomainEvent {

    @Override
    public String eventType() {
        return "IntegrationExchangeMessageRecordedEvent";
    }
}
