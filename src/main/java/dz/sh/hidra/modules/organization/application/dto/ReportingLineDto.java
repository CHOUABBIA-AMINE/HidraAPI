/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLineDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.dto
 *
 * @Description : Application DTO representing an employee reporting line.
 *
 */
package dz.sh.hidra.modules.organization.application.dto;

import java.time.LocalDate;

/**
 * Represents matrix reporting line data returned by organization use cases.
 *
 * <p>Business role:
 * This DTO describes an employee-to-manager reporting relationship, including LINE,
 * OPERATIONAL, FUNCTIONAL, ADMINISTRATIVE, TECHNICAL, and DOTTED_LINE relationships.
 *
 * <p>Architecture role:
 * This application DTO is framework-independent and must not contain REST annotations, JPA
 * annotations, identity domain objects, topology domain objects, or infrastructure details.
 *
 * <p>Validation:
 * This DTO is an output projection. Reporting-line rules are enforced by the domain model and
 * reporting-line policy before this DTO is created.
 *
 * <p>Usage:
 * Use this DTO as an application output and convert it to REST responses in the API layer later.
 *
 * @param reportingLineId reporting line identifier
 * @param employeeId employee that reports to the manager
 * @param managerEmployeeId manager employee identifier
 * @param reportingLineType reporting line type
 * @param primaryLine whether this is the primary reporting line
 * @param effectiveFrom reporting line start date
 * @param effectiveTo optional reporting line end date
 * @param description optional business description
 */
public record ReportingLineDto(
        String reportingLineId,
        String employeeId,
        String managerEmployeeId,
        String reportingLineType,
        boolean primaryLine,
        LocalDate effectiveFrom,
        LocalDate effectiveTo,
        String description) {
}
