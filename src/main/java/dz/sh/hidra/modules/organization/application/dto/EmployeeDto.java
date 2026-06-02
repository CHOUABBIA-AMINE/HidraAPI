/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.dto
 *
 * @Description : Application DTO representing an employee.
 *
 */
package dz.sh.hidra.modules.organization.application.dto;

import java.time.Instant;
import java.util.List;

/**
 * Represents employee data returned by organization use cases.
 *
 * <p>Business role:
 * This DTO describes a real operational employee, including assignments, reporting lines, status,
 * and an optional neutral identity-user reference.
 *
 * <p>Architecture role:
 * This application DTO is returned by inbound use-case ports. It must not contain REST annotations,
 * JPA annotations, identity domain objects, topology domain objects, or persistence entities.
 *
 * <p>Validation:
 * This DTO is an output projection. Employee validation is enforced by the employee aggregate and
 * organization domain policies before this DTO is created.
 *
 * <p>Usage:
 * Use this DTO inside application and API mapping code. Do not return domain aggregates directly
 * from REST controllers.
 *
 * @param employeeId employee identifier
 * @param employeeNumber employee business number
 * @param fullName employee full name
 * @param email optional professional email
 * @param status employment status
 * @param identityUserReference optional neutral identity user reference
 * @param assignments employee assignments
 * @param reportingLines employee reporting lines
 * @param createdAt creation instant
 * @param activatedAt optional activation instant
 * @param suspendedAt optional suspension instant
 * @param disabledAt optional disabled instant
 * @param updatedAt last update instant
 */
public record EmployeeDto(
        String employeeId,
        String employeeNumber,
        String fullName,
        String email,
        String status,
        String identityUserReference,
        List<EmployeeAssignmentDto> assignments,
        List<ReportingLineDto> reportingLines,
        Instant createdAt,
        Instant activatedAt,
        Instant suspendedAt,
        Instant disabledAt,
        Instant updatedAt) {

    public EmployeeDto {
        assignments = assignments == null ? List.of() : List.copyOf(assignments);
        reportingLines = reportingLines == null ? List.of() : List.copyOf(reportingLines);
    }
}
