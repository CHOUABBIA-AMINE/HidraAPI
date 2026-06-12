/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityDomainEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.event
 *
 * @Description : Integrity domain event contract.
 *
 */
package dz.sh.hidra.modules.integrity.domain.event;

import java.time.Instant;

/**
 * Integrity domain event contract.
 */
public interface IntegrityDomainEvent {

    String eventId();

    String eventType();

    Instant occurredAt();
}
