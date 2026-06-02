/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAssignmentDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.dto
 *
 * @Description : Application DTO representing an employee assignment.
 *
 */
package dz.sh.hidra.modules.organization.application.dto;

import java.time.LocalDate;

/**
 * Represents employee assignment data returned by organization use cases.
 *
 * <p>Business role:
 * This DTO describes an employee assignment to an organization unit and position, optionally
 * connected to a neutral operational scope such as a future topology station reference.
 *
 * <p>Architecture role:
 * This application DTO crosses application use-case boundaries. It must not contain REST
 * annotations, persistence annotations, Spring dependencies, identity domain objects, topology
 * domain objects, or JPA entities.
 *
 * <p>Validation:
 * This DTO is an output projection. Domain validation is performed by domain value objects,
 * aggregates, policies, and services before this DTO is created.
 *
 * <p>Usage:
 * Use this DTO as an application output and map it to REST response DTOs later in the API layer.
 *
 * @param assignmentId assignment identifier
 * @param employeeId assigned employee identifier
 * @param organizationUnitId target organization unit identifier
 * @param positionId target position identifier
 * @param operationalScopeType optional neutral operational scope type
 * @param operationalScopeId optional neutral operational scope identifier
 * @param operationalScopeCode optional neutral operational scope business code
 * @param operationalScopeName optional neutral operational scope display name
 * @param effectiveFrom assignment start date
 * @param effectiveTo optional assignment end date
 */
public record EmployeeAssignmentDto(
        String assignmentId,
        String employeeId,
        String organizationUnitId,
        String positionId,
        String operationalScopeType,
        String operationalScopeId,
        String operationalScopeCode,
        String operationalScopeName,
        LocalDate effectiveFrom,
        LocalDate effectiveTo) {
}
