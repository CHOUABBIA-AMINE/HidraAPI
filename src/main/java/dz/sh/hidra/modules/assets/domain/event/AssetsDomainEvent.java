/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsDomainEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.event
 *
 * @Description : Assets domain event contract.
 *
 */
package dz.sh.hidra.modules.assets.domain.event;

import java.time.Instant;

/**
 * Assets domain event contract.
 */
public interface AssetsDomainEvent {

    String eventId();

    String eventType();

    Instant occurredAt();
}
