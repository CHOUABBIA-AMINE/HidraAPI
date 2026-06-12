/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationJobRunStartedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.event
 *
 * @Description : Published when an integration job run starts.
 *
 */
package dz.sh.hidra.modules.integration.domain.event;

import java.time.Instant;

/**
 * Published when an integration job run starts.
 */
public record IntegrationJobRunStartedEvent(
        String eventId,
    String jobRunId,
    String jobDefinitionId,
    Instant occurredAt
) implements IntegrationDomainEvent {

    @Override
    public String eventType() {
        return "IntegrationJobRunStartedEvent";
    }
}
