/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterEmployeeCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.command
 *
 * @Description : Command to register an employee.
 *
 */
package dz.sh.hidra.modules.organization.application.command;

import dz.sh.hidra.modules.organization.domain.value.EmployeeType;

import java.time.LocalDate;

/**
 * Command to register an employee.
 */
public record RegisterEmployeeCommand(
        String employeeNumber,
        String firstNameAr,
        String lastNameAr,
        String firstNameLt,
        String lastNameLt,
        String displayNameAr,
        String displayNameLt,
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
