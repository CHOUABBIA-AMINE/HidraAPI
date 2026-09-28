/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import java.time.LocalDate;

/**
 * Employee summary DTO.
 */
public record EmployeeSummaryDto(
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
