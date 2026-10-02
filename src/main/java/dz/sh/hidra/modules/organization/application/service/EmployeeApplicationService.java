/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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
import org.springframework.transaction.annotation.Transactional;

import dz.sh.hidra.modules.organization.application.command.CreateOrganizationContactPointCommand;
import dz.sh.hidra.modules.organization.application.command.RegisterEmployeeCommand;
import dz.sh.hidra.modules.organization.application.dto.EmployeeSummaryDto;
import dz.sh.hidra.modules.organization.application.mapper.OrganizationApplicationMapper;
import dz.sh.hidra.modules.organization.application.port.in.CreateOrganizationContactPointUseCase;
import dz.sh.hidra.modules.organization.application.port.in.RegisterEmployeeUseCase;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.value.ContactPointTargetReference;
import dz.sh.hidra.modules.organization.domain.value.ContactPointTargetType;
import dz.sh.hidra.modules.organization.domain.value.ContactPointType;
import dz.sh.hidra.modules.organization.domain.value.EmployeeStatus;
import dz.sh.hidra.modules.organization.domain.value.EmployeeType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationId;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for employees.
 */
@Service
public class EmployeeApplicationService implements RegisterEmployeeUseCase {

    private final EmployeeRepositoryPort employeeRepositoryPort;
    private final CreateOrganizationContactPointUseCase createContactPointUseCase;

    public EmployeeApplicationService(
            EmployeeRepositoryPort employeeRepositoryPort,
            CreateOrganizationContactPointUseCase createContactPointUseCase
    ) {
        this.employeeRepositoryPort = Objects.requireNonNull(
                employeeRepositoryPort,
                "Employee repository port must not be null."
        );
        this.createContactPointUseCase = Objects.requireNonNull(
                createContactPointUseCase,
                "CreateOrganizationContactPointUseCase must not be null."
        );
    }

    @Override
    @Transactional
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
                null,
                null,
                command.dateOfBirth(),
                command.birthLocalityId(),
                command.birthPlaceAr(),
                command.birthPlaceFr(),
                command.birthPlaceEn(),
                null,
                null,
                command.employeeType() == null ? EmployeeType.PERMANENT : command.employeeType(),
                EmployeeStatus.REGISTERED,
                command.identityUserReference(),
                null,
                null,
                now,
                now
        );
        Employee saved = employeeRepositoryPort.save(employee);
        writeRegistrationContacts(saved.id(), command.emailAddress(), command.mobileNumber());
        return OrganizationApplicationMapper.toSummary(saved);
    }

    private void writeRegistrationContacts(
            String employeeId,
            String emailAddress,
            String mobileNumber
    ) {
        ContactPointTargetReference target = new ContactPointTargetReference(
                ContactPointTargetType.EMPLOYEE,
                employeeId
        );

        if (emailAddress != null && !emailAddress.isBlank()) {
            createContactPointUseCase.createContactPoint(
                    new CreateOrganizationContactPointCommand(
                            ContactPointType.EMAIL,
                            target,
                            null,
                            emailAddress,
                            false,
                            false,
                            true
                    )
            );
        }

        if (mobileNumber != null && !mobileNumber.isBlank()) {
            createContactPointUseCase.createContactPoint(
                    new CreateOrganizationContactPointCommand(
                            ContactPointType.MOBILE,
                            target,
                            null,
                            mobileNumber,
                            false,
                            false,
                            true
                    )
            );
        }
    }
}
