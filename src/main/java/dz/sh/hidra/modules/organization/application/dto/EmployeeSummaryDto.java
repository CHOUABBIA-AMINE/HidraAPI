/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.dto
 *
 * @Description : Employee summary DTO.
 *
 */
package dz.sh.hidra.modules.organization.application.dto;

import dz.sh.hidra.modules.organization.domain.value.EmployeeStatus;
import dz.sh.hidra.modules.organization.domain.value.EmployeeType;

/**
 * Employee summary DTO.
 */
public record EmployeeSummaryDto(
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
