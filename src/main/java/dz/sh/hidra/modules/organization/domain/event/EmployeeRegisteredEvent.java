/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeRegisteredEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.event
 *
 * @Description : Published when an employee is registered.
 *
 */
package dz.sh.hidra.modules.organization.domain.event;

import java.time.Instant;

/**
 * Published when an employee is registered.
 *
     * @param eventId eventId
 * @param employeeId employeeId
 * @param occurredAt occurredAt
 */
public record EmployeeRegisteredEvent(
        String eventId,
    String employeeId,
    Instant occurredAt
) implements OrganizationDomainEvent {

    @Override
    public String eventType() {
        return "EmployeeRegisteredEvent";
    }
}
