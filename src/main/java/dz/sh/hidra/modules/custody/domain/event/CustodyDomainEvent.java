/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyDomainEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.event
 *
 * @Description : Custody domain event contract.
 *
 */
package dz.sh.hidra.modules.custody.domain.event;

import java.time.Instant;

/**
 * Custody domain event contract.
 */
public interface CustodyDomainEvent {

    String eventId();

    String eventType();

    Instant occurredAt();
}
