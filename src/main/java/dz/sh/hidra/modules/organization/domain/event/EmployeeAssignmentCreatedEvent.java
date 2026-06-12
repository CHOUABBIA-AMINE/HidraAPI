/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAssignmentCreatedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.event
 *
 * @Description : Published when an employee assignment is created.
 *
 */
package dz.sh.hidra.modules.organization.domain.event;

import java.time.Instant;

/**
 * Published when an employee assignment is created.
 *
     * @param eventId eventId
 * @param assignmentId assignmentId
 * @param employeeId employeeId
 * @param occurredAt occurredAt
 */
public record EmployeeAssignmentCreatedEvent(
        String eventId,
    String assignmentId,
    String employeeId,
    Instant occurredAt
) implements OrganizationDomainEvent {

    @Override
    public String eventType() {
        return "EmployeeAssignmentCreatedEvent";
    }
}
