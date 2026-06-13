/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssignEmployeeRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.request
 *
 * @Description : REST request for assign employee.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.request;

import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import java.time.Instant;

/**
 * REST request for assign employee.
 */
public record AssignEmployeeRequest(
        String employeeId,
        String organizationUnitId,
        String positionId,
        AssignmentType assignmentType,
        String operationalScopeType,
        String operationalScopeId,
        String operationalScopeCode,
        String operationalScopeName,
        Instant validFrom,
        Instant validTo
) {
}
