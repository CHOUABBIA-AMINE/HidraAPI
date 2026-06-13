/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Application service for employees.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import org.springframework.stereotype.Service;

import dz.sh.hidra.modules.organization.application.command.RegisterEmployeeCommand;
import dz.sh.hidra.modules.organization.application.dto.EmployeeSummaryDto;
import dz.sh.hidra.modules.organization.application.mapper.OrganizationApplicationMapper;
import dz.sh.hidra.modules.organization.application.port.in.RegisterEmployeeUseCase;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.value.EmployeeStatus;
import dz.sh.hidra.modules.organization.domain.value.EmployeeType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationId;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for employees.
 */
@Service
public final class EmployeeApplicationService implements RegisterEmployeeUseCase {

    private final EmployeeRepositoryPort employeeRepositoryPort;

    public EmployeeApplicationService(EmployeeRepositoryPort employeeRepositoryPort) {
        this.employeeRepositoryPort = Objects.requireNonNull(employeeRepositoryPort, "Employee repository port must not be null.");
    }

    @Override
    public EmployeeSummaryDto registerEmployee(RegisterEmployeeCommand command) {
        Objects.requireNonNull(command, "Register employee command must not be null.");
        Instant now = Instant.now();
        Employee employee = new Employee(
                OrganizationId.newId().value(),
                command.employeeNumber(),
                command.firstNameAr(),
                command.lastNameAr(),
                command.firstNameLt(),
                command.lastNameLt(),
                command.displayNameAr(),
                command.displayNameLt(),
                command.emailAddress(),
                command.mobileNumber(),
                command.employeeType() == null ? EmployeeType.PERMANENT : command.employeeType(),
                EmployeeStatus.REGISTERED,
                command.identityUserReference(),
                null,
                null,
                now,
                now
        );
        return OrganizationApplicationMapper.toSummary(employeeRepositoryPort.save(employee));
    }
}
