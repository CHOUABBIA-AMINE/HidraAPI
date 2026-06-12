/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.response
 *
 * @Description : REST response for employee.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.response;

import dz.sh.hidra.modules.organization.domain.value.EmployeeStatus;
import dz.sh.hidra.modules.organization.domain.value.EmployeeType;

/**
 * REST response for employee.
 */
public record EmployeeResponse(
        String id,
        String employeeNumber,
        String displayNameAr,
        String displayNameLt,
        String emailAddress,
        EmployeeType employeeType,
        EmployeeStatus status,
        String identityUserReference
) {
}
