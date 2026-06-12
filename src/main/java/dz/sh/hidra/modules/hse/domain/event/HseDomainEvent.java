/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseDomainEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.event
 *
 * @Description : HSE domain event contract.
 *
 */
package dz.sh.hidra.modules.hse.domain.event;

import java.time.Instant;

/**
 * HSE domain event contract.
 */
public interface HseDomainEvent {

    String eventId();

    String eventType();

    Instant occurredAt();
}
