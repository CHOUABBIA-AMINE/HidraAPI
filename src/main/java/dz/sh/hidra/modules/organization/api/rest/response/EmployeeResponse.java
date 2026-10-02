/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import java.time.LocalDate;

/**
 * REST response for employee.
 */
public record EmployeeResponse(
        String id,
        String employeeNumber,
        String displayNameAr,
        String displayNameLt,
        LocalDate dateOfBirth,
        String birthLocalityId,
        String birthPlaceAr,
        String birthPlaceFr,
        String birthPlaceEn,
        String emailAddress,
        EmployeeType employeeType,
        EmployeeStatus status,
        String identityUserReference
) {
}
