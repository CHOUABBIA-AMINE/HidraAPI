/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyDomainEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.event
 *
 * @Description : Party domain event contract.
 *
 */
package dz.sh.hidra.modules.party.domain.event;

import java.time.Instant;

/**
 * Party domain event contract.
 */
public interface PartyDomainEvent {

    String eventId();

    String eventType();

    Instant occurredAt();
}
