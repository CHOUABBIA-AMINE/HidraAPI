/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationDomainEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.event
 *
 * @Description : Integration domain event contract.
 *
 */
package dz.sh.hidra.modules.integration.domain.event;

import java.time.Instant;

/**
 * Integration domain event contract.
 */
public interface IntegrationDomainEvent {

    String eventId();

    String eventType();

    Instant occurredAt();
}
