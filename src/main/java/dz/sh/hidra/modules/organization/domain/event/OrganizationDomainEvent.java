/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationDomainEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.event
 *
 * @Description : Organization domain event contract.
 *
 */
package dz.sh.hidra.modules.organization.domain.event;

import java.time.Instant;

/**
 * Organization domain event contract.
 */
public interface OrganizationDomainEvent {

    String eventId();

    String eventType();

    Instant occurredAt();
}
