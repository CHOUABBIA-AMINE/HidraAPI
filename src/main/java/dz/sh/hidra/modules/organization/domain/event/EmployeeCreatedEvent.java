/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeCreatedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.event
 *
 * @Description : Domain event raised when an organization employee is created.
 *
 */
package dz.sh.hidra.modules.organization.domain.event;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.kernel.domain.event.DomainEventId;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;
import dz.sh.hidra.modules.organization.domain.value.EmployeeNumber;
import dz.sh.hidra.modules.organization.domain.value.EmploymentStatus;

/**
 * Domain event raised when an organization employee is created.
 *
 * <p>Business role:
 * Captures the business fact that a real operational employee was created in the organization bounded context. It does not represent identity user creation.
 *
 * <p>Architecture role:
 * This is an immutable organization domain event. It implements the kernel domain event contract
 * and must not depend on platform event infrastructure, persistence entities, REST DTOs, identity, topology,
 * Spring, JPA, or infrastructure code.
 *
 * <p>Validation:
 * Event id, occurrence instant, employee id, employee number, and status are required.
 *
 * <p>Usage:
 * Raise this event from organization aggregate or application behavior when the related business
 * fact occurs. Keep payloads limited to safe identifiers, codes, and non-secret business context.
 *
 * @param employeeId created employee identifier
 * @param employeeNumber created employee business number
 * @param status initial employee lifecycle status
 */
public record EmployeeCreatedEvent(
        DomainEventId eventId,
        Instant occurredAt,
        EmployeeId employeeId,
        EmployeeNumber employeeNumber,
        EmploymentStatus status) implements DomainEvent {

    /**
     * Event type emitted for this business fact.
     */
    public static final String TYPE = "organization.employee.created";

    public EmployeeCreatedEvent {
        Objects.requireNonNull(eventId, "eventId must not be null.");
        Objects.requireNonNull(occurredAt, "occurredAt must not be null.");
        Objects.requireNonNull(employeeId, "employeeId must not be null.");
        Objects.requireNonNull(employeeNumber, "employeeNumber must not be null.");
        Objects.requireNonNull(status, "status must not be null.");
    }

    /**
     * Creates a new domain event with generated event id and current occurrence instant.
     *
     * @param employeeId created employee identifier
     * @param employeeNumber created employee business number
     * @param status initial employee lifecycle status
     * @return created domain event
     */
    public static EmployeeCreatedEvent occurred(
            EmployeeId employeeId,
            EmployeeNumber employeeNumber,
            EmploymentStatus status) {

        return new EmployeeCreatedEvent(
                DomainEventId.newId(),
                Instant.now(),
                employeeId,
                employeeNumber,
                status);
    }

    @Override
    public String eventType() {
        return TYPE;
    }
}
