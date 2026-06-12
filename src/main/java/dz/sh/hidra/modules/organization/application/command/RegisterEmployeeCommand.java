/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterEmployeeCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
        String emailAddress,
        String mobileNumber,
        EmployeeType employeeType,
        String identityUserReference
) {
}
