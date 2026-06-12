/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterEmployeeRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.request
 *
 * @Description : REST request to register employee.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.request;

import dz.sh.hidra.modules.organization.domain.value.EmployeeType;

/**
 * REST request to register employee.
 */
public record RegisterEmployeeRequest(
        String employeeNumber,
        String firstNameAr,
        String lastNameAr,
        String firstNameLt,
        String lastNameLt,
        String displayNameAr,
        String displayNameLt,
        String emailAddress,
        String mobileNumber,
        EmployeeType employeeType,
        String identityUserReference
) {
}
