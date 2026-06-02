/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateEmployeeService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Application service implementing employee creation use case.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.organization.application.command.CreateEmployeeCommand;
import dz.sh.hidra.modules.organization.application.dto.EmployeeDto;
import dz.sh.hidra.modules.organization.application.mapper.OrganizationApplicationMapper;
import dz.sh.hidra.modules.organization.application.port.in.CreateEmployeeUseCase;
import dz.sh.hidra.modules.organization.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepository;
import dz.sh.hidra.modules.organization.domain.event.EmployeeCreatedEvent;
import dz.sh.hidra.modules.organization.domain.model.Employee;

/**
 * Implements the employee creation use case.
 *
 * <p>Business role:
 * This service creates a real operational employee in the organization module. It does not create
 * an identity user, identity role, permission, credential, or topology asset.
 *
 * <p>Architecture role:
 * This is an application service implementing an inbound port. It coordinates domain aggregates,
 * outbound ports, mapping, and domain event publication without depending on REST controllers,
 * JPA entities, Spring annotations, identity, topology, platform, or infrastructure adapters.
 *
 * <p>Validation:
 * The command is required. Employee-number uniqueness is checked through the outbound repository
 * port. Domain value objects and the employee aggregate protect the remaining invariants.
 *
 * <p>Usage:
 * Wire this service later from configuration and expose it through the API layer by depending on
 * the CreateEmployeeUseCase interface.
 */
public final class CreateEmployeeService implements CreateEmployeeUseCase {

    private final EmployeeRepository employeeRepository;
    private final DomainEventPublisherPort domainEventPublisher;
    private final OrganizationApplicationMapper mapper;

    public CreateEmployeeService(
            EmployeeRepository employeeRepository,
            DomainEventPublisherPort domainEventPublisher,
            OrganizationApplicationMapper mapper) {

        this.employeeRepository = Objects.requireNonNull(employeeRepository, "Employee repository must not be null.");
        this.domainEventPublisher = Objects.requireNonNull(domainEventPublisher, "Domain event publisher must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "Organization application mapper must not be null.");
    }

    @Override
    public EmployeeDto createEmployee(CreateEmployeeCommand command) {
        Objects.requireNonNull(command, "Create employee command must not be null.");

        if (employeeRepository.existsByEmployeeNumber(command.employeeNumber())) {
            throw new BusinessRuleViolationException("Employee number already exists.");
        }

        Employee employee = Employee.register(
                command.employeeNumber(),
                command.fullName(),
                command.email(),
                command.identityUserReference());

        Employee savedEmployee = employeeRepository.save(employee);
        domainEventPublisher.publish(EmployeeCreatedEvent.occurred(
                savedEmployee.id(),
                savedEmployee.employeeNumber(),
                savedEmployee.status()));

        return mapper.toDto(savedEmployee);
    }
}
