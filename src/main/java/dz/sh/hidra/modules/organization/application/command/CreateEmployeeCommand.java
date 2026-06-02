/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateEmployeeCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.command
 *
 * @Description : Application command for creating an organization employee.
 *
 */
package dz.sh.hidra.modules.organization.application.command;

import java.util.Objects;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.organization.domain.value.EmployeeEmail;
import dz.sh.hidra.modules.organization.domain.value.EmployeeFullName;
import dz.sh.hidra.modules.organization.domain.value.EmployeeNumber;
import dz.sh.hidra.modules.organization.domain.value.IdentityUserReference;

/**
 * Carries input required to create an employee.
 *
 * <p>Business role:
 * This command requests creation of a real operational employee in the organization module.
 * It does not create an identity user, role, permission, or login account.
 *
 * <p>Architecture role:
 * This is an application command consumed by organization use cases. It may use domain value
 * objects but must not depend on REST DTOs, persistence entities, Spring, JPA, identity, topology,
 * platform, or infrastructure code.
 *
 * <p>Validation:
 * Employee number and full name are mandatory. Email and identity user reference are optional.
 * Value object constructors validate business formats.
 *
 * <p>Usage:
 * API mappers should translate REST request data into this command before calling an inbound
 * application port.
 *
 * @param employeeNumber unique business employee number
 * @param fullName employee full name
 * @param email optional professional email
 * @param identityUserReference optional neutral identity user reference
 */
public record CreateEmployeeCommand(
        EmployeeNumber employeeNumber,
        EmployeeFullName fullName,
        EmployeeEmail email,
        IdentityUserReference identityUserReference) implements Command {

    public CreateEmployeeCommand {
        Objects.requireNonNull(employeeNumber, "Employee number must not be null.");
        Objects.requireNonNull(fullName, "Employee full name must not be null.");
    }
}
