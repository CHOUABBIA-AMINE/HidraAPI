/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAssignedToUnitEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.event
 *
 * @Description : Domain event raised when an employee is assigned to an organization unit and position.
 *
 */
package dz.sh.hidra.modules.organization.domain.event;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.kernel.domain.event.DomainEventId;
import dz.sh.hidra.modules.organization.domain.value.AssignmentId;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.domain.value.PositionId;

/**
 * Domain event raised when an employee is assigned to an organization unit and position.
 *
 * <p>Business role:
 * Captures the business fact that an employee received an operational assignment to an organization unit and position, such as station team leader in a station organization unit.
 *
 * <p>Architecture role:
 * This is an immutable organization domain event. It implements the kernel domain event contract
 * and must not depend on platform event infrastructure, persistence entities, REST DTOs, identity, topology,
 * Spring, JPA, or infrastructure code.
 *
 * <p>Validation:
 * Event id, occurrence instant, assignment id, employee id, organization unit id, and position id are required.
 *
 * <p>Usage:
 * Raise this event from organization aggregate or application behavior when the related business
 * fact occurs. Keep payloads limited to safe identifiers, codes, and non-secret business context.
 *
 * @param assignmentId created assignment identifier
 * @param employeeId assigned employee identifier
 * @param organizationUnitId target organization unit identifier
 * @param positionId assigned position identifier
 */
public record EmployeeAssignedToUnitEvent(
        DomainEventId eventId,
        Instant occurredAt,
        AssignmentId assignmentId,
        EmployeeId employeeId,
        OrganizationUnitId organizationUnitId,
        PositionId positionId) implements DomainEvent {

    /**
     * Event type emitted for this business fact.
     */
    public static final String TYPE = "organization.employee.assigned-to-unit";

    public EmployeeAssignedToUnitEvent {
        Objects.requireNonNull(eventId, "eventId must not be null.");
        Objects.requireNonNull(occurredAt, "occurredAt must not be null.");
        Objects.requireNonNull(assignmentId, "assignmentId must not be null.");
        Objects.requireNonNull(employeeId, "employeeId must not be null.");
        Objects.requireNonNull(organizationUnitId, "organizationUnitId must not be null.");
        Objects.requireNonNull(positionId, "positionId must not be null.");
    }

    /**
     * Creates a new domain event with generated event id and current occurrence instant.
     *
     * @param assignmentId created assignment identifier
     * @param employeeId assigned employee identifier
     * @param organizationUnitId target organization unit identifier
     * @param positionId assigned position identifier
     * @return created domain event
     */
    public static EmployeeAssignedToUnitEvent occurred(
            AssignmentId assignmentId,
            EmployeeId employeeId,
            OrganizationUnitId organizationUnitId,
            PositionId positionId) {

        return new EmployeeAssignedToUnitEvent(
                DomainEventId.newId(),
                Instant.now(),
                assignmentId,
                employeeId,
                organizationUnitId,
                positionId);
    }

    @Override
    public String eventType() {
        return TYPE;
    }
}
