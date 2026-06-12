/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationDomainEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.event
 *
 * @Description : Configuration domain event contract.
 *
 */
package dz.sh.hidra.modules.configuration.domain.event;

import java.time.Instant;

/**
 * Configuration domain event contract.
 */
public interface ConfigurationDomainEvent {

    String eventId();

    String eventType();

    Instant occurredAt();
}
