/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UpdateEmployeeCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.command
 *
 * @Description : Application command for updating employee basic information.
 *
 */
package dz.sh.hidra.modules.organization.application.command;

import java.util.Objects;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.organization.domain.value.EmployeeEmail;
import dz.sh.hidra.modules.organization.domain.value.EmployeeFullName;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;

/**
 * Carries input required to update employee basic information.
 *
 * <p>Business role:
 * This command requests an update to the descriptive information of a real operational employee.
 * It does not modify identity credentials, roles, permissions, or topology assets.
 *
 * <p>Architecture role:
 * This is an application command consumed by organization use cases. It must not depend on REST
 * DTOs, JPA entities, Spring, identity, topology, platform, or infrastructure code.
 *
 * <p>Validation:
 * Employee id and full name are mandatory. Email is optional. Value objects validate their own
 * business formats.
 *
 * <p>Usage:
 * Use this command from API mappers or other application orchestrators when employee basic
 * information must change.
 *
 * @param employeeId employee identifier
 * @param fullName new employee full name
 * @param email optional new professional email
 */
public record UpdateEmployeeCommand(
        EmployeeId employeeId,
        EmployeeFullName fullName,
        EmployeeEmail email) implements Command {

    public UpdateEmployeeCommand {
        Objects.requireNonNull(employeeId, "Employee id must not be null.");
        Objects.requireNonNull(fullName, "Employee full name must not be null.");
    }
}
