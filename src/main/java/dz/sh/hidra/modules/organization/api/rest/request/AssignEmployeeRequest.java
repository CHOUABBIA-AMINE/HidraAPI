/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssignEmployeeRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.request
 *
 * @Description : REST request for assigning an employee to a unit and position.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.request;

import dz.sh.hidra.modules.organization.domain.value.AssignmentType;

import java.time.Instant;

/**
 * REST request for assigning an employee to a unit and position.
 *
 * <p>Operational scope is intentionally absent. Scope responsibility is managed
 * through dedicated responsibility-assignment use cases.</p>
 */
public record AssignEmployeeRequest(
        String employeeId,
        String organizationUnitId,
        String positionId,
        AssignmentType assignmentType,
        Instant validFrom,
        Instant validTo
) {
}
