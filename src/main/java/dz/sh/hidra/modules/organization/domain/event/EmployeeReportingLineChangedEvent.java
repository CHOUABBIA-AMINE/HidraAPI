/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeReportingLineChangedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.event
 *
 * @Description : Domain event raised when an employee reporting line changes.
 *
 */
package dz.sh.hidra.modules.organization.domain.event;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.kernel.domain.event.DomainEventId;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;
import dz.sh.hidra.modules.organization.domain.value.ReportingLineId;
import dz.sh.hidra.modules.organization.domain.value.ReportingLineType;

/**
 * Domain event raised when an employee reporting line changes.
 *
 * <p>Business role:
 * Captures the business fact that an employee reporting relationship changed, including LINE, OPERATIONAL, FUNCTIONAL, ADMINISTRATIVE, TECHNICAL, or DOTTED_LINE matrix reporting.
 *
 * <p>Architecture role:
 * This is an immutable organization domain event. It implements the kernel domain event contract
 * and must not depend on platform event infrastructure, persistence entities, REST DTOs, identity, topology,
 * Spring, JPA, or infrastructure code.
 *
 * <p>Validation:
 * Event id, occurrence instant, reporting line id, employee id, manager id, and reporting line type are required.
 *
 * <p>Usage:
 * Raise this event from organization aggregate or application behavior when the related business
 * fact occurs. Keep payloads limited to safe identifiers, codes, and non-secret business context.
 *
 * @param reportingLineId reporting line identifier
 * @param employeeId employee that reports to the manager
 * @param managerEmployeeId manager employee identifier
 * @param reportingLineType type of reporting relationship
 */
public record EmployeeReportingLineChangedEvent(
        DomainEventId eventId,
        Instant occurredAt,
        ReportingLineId reportingLineId,
        EmployeeId employeeId,
        EmployeeId managerEmployeeId,
        ReportingLineType reportingLineType) implements DomainEvent {

    /**
     * Event type emitted for this business fact.
     */
    public static final String TYPE = "organization.employee.reporting-line-changed";

    public EmployeeReportingLineChangedEvent {
        Objects.requireNonNull(eventId, "eventId must not be null.");
        Objects.requireNonNull(occurredAt, "occurredAt must not be null.");
        Objects.requireNonNull(reportingLineId, "reportingLineId must not be null.");
        Objects.requireNonNull(employeeId, "employeeId must not be null.");
        Objects.requireNonNull(managerEmployeeId, "managerEmployeeId must not be null.");
        Objects.requireNonNull(reportingLineType, "reportingLineType must not be null.");
    }

    /**
     * Creates a new domain event with generated event id and current occurrence instant.
     *
     * @param reportingLineId reporting line identifier
     * @param employeeId employee that reports to the manager
     * @param managerEmployeeId manager employee identifier
     * @param reportingLineType type of reporting relationship
     * @return created domain event
     */
    public static EmployeeReportingLineChangedEvent occurred(
            ReportingLineId reportingLineId,
            EmployeeId employeeId,
            EmployeeId managerEmployeeId,
            ReportingLineType reportingLineType) {

        return new EmployeeReportingLineChangedEvent(
                DomainEventId.newId(),
                Instant.now(),
                reportingLineId,
                employeeId,
                managerEmployeeId,
                reportingLineType);
    }

    @Override
    public String eventType() {
        return TYPE;
    }
}
