/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityDomainEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.event
 *
 * @Description : Defines identity domain event contract.
 *
 */
package dz.sh.hidra.modules.identity.domain.event;

import java.time.Instant;

/**
 * Identity domain event contract.
 */
public interface IdentityDomainEvent {

    String eventId();

    String eventType();

    Instant occurredAt();
}
