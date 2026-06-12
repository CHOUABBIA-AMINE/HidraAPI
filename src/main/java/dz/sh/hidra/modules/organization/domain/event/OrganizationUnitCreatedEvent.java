/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitCreatedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.event
 *
 * @Description : Published when an organization unit is created.
 *
 */
package dz.sh.hidra.modules.organization.domain.event;

import java.time.Instant;

/**
 * Published when an organization unit is created.
 *
     * @param eventId eventId
 * @param organizationUnitId organizationUnitId
 * @param occurredAt occurredAt
 */
public record OrganizationUnitCreatedEvent(
        String eventId,
    String organizationUnitId,
    Instant occurredAt
) implements OrganizationDomainEvent {

    @Override
    public String eventType() {
        return "OrganizationUnitCreatedEvent";
    }
}
