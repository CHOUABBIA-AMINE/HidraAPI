/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAssignmentResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.response
 *
 * @Description : REST response representing an employee assignment.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.response;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response representing an employee assignment.
 *
 * @param assignmentId assignment identifier
 * @param employeeId employee identifier
 * @param organizationUnitId organization unit identifier
 * @param positionId position identifier
 * @param operationalScopeType optional neutral operational scope type
 * @param operationalScopeId optional neutral operational scope identifier
 * @param operationalScopeCode optional neutral operational scope code
 * @param operationalScopeName optional neutral operational scope name
 * @param effectiveFrom assignment start date
 * @param effectiveTo optional assignment end date
 */
@Schema(name = "EmployeeAssignmentResponse", description = "REST response representing an employee assignment.")
public record EmployeeAssignmentResponse(
        @Schema(description = "Assignment identifier.", example = "asg_550e8400-e29b-41d4-a716-446655440000")
        String assignmentId,
        @Schema(description = "Employee identifier.", example = "emp_550e8400-e29b-41d4-a716-446655440000")
        String employeeId,
        @Schema(description = "Organization unit identifier.", example = "ou_550e8400-e29b-41d4-a716-446655440000")
        String organizationUnitId,
        @Schema(description = "Position identifier.", example = "pos_550e8400-e29b-41d4-a716-446655440000")
        String positionId,
        @Schema(description = "Optional neutral operational scope type.", example = "TOPOLOGY_COMPRESSION_STATION")
        String operationalScopeType,
        @Schema(description = "Optional neutral operational scope identifier.", example = "station_001")
        String operationalScopeId,
        @Schema(description = "Optional neutral operational scope code.", example = "CS-EAST-01")
        String operationalScopeCode,
        @Schema(description = "Optional neutral operational scope name.", example = "Compression Station East 01")
        String operationalScopeName,
        @Schema(description = "Assignment start date.", type = "string", format = "date")
        LocalDate effectiveFrom,
        @Schema(description = "Assignment end date.", type = "string", format = "date")
        LocalDate effectiveTo) {
}
