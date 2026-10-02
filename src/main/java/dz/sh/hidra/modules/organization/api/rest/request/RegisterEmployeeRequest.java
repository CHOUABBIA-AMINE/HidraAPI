/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterEmployeeRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import java.time.LocalDate;

/**
 * REST request to register employee.
 *
 * <p>Display names are not accepted as input. They are derived from the structured
 * Arabic and Latin first/last name components at read boundaries.</p>
 */
public record RegisterEmployeeRequest(
        String employeeNumber,
        String firstNameAr,
        String lastNameAr,
        String firstNameLt,
        String lastNameLt,
        LocalDate dateOfBirth,
        String birthLocalityId,
        String birthPlaceAr,
        String birthPlaceFr,
        String birthPlaceEn,
        String emailAddress,
        String mobileNumber,
        EmployeeType employeeType,
        String identityUserReference
) {
}
