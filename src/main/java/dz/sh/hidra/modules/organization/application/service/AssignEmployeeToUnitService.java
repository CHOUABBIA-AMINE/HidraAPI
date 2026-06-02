/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssignEmployeeToUnitService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Application service implementing employee assignment use case.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.organization.application.command.AssignEmployeeToUnitCommand;
import dz.sh.hidra.modules.organization.application.dto.EmployeeAssignmentDto;
import dz.sh.hidra.modules.organization.application.mapper.OrganizationApplicationMapper;
import dz.sh.hidra.modules.organization.application.port.in.AssignEmployeeToUnitUseCase;
import dz.sh.hidra.modules.organization.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepository;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepository;
import dz.sh.hidra.modules.organization.application.port.out.PositionRepository;
import dz.sh.hidra.modules.organization.domain.event.EmployeeAssignedToUnitEvent;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAssignment;
import dz.sh.hidra.modules.organization.domain.model.OperationalScopeReference;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.model.Position;
import dz.sh.hidra.modules.organization.domain.service.EmployeeAssignmentDomainService;

/**
 * Implements the employee assignment use case.
 *
 * <p>Business role:
 * This service assigns an employee to an organization unit and position, such as assigning a team
 * leader to a station organization unit.
 *
 * <p>Architecture role:
 * This application service coordinates outbound repositories, domain service validation, aggregate
 * update, event publication, and mapping without depending on API, persistence entities, Spring,
 * JPA, identity, topology, platform, or infrastructure adapters.
 *
 * <p>Validation:
 * The command is required. Employee, organization unit, and position must exist. Assignment rules
 * are delegated to EmployeeAssignmentDomainService.
 *
 * <p>Usage:
 * Wire this service later as the AssignEmployeeToUnitUseCase implementation.
 */
public final class AssignEmployeeToUnitService implements AssignEmployeeToUnitUseCase {

    private final EmployeeRepository employeeRepository;
    private final OrganizationUnitRepository organizationUnitRepository;
    private final PositionRepository positionRepository;
    private final EmployeeAssignmentDomainService employeeAssignmentDomainService;
    private final DomainEventPublisherPort domainEventPublisher;
    private final OrganizationApplicationMapper mapper;

    public AssignEmployeeToUnitService(
            EmployeeRepository employeeRepository,
            OrganizationUnitRepository organizationUnitRepository,
            PositionRepository positionRepository,
            EmployeeAssignmentDomainService employeeAssignmentDomainService,
            DomainEventPublisherPort domainEventPublisher,
            OrganizationApplicationMapper mapper) {

        this.employeeRepository = Objects.requireNonNull(employeeRepository, "Employee repository must not be null.");
        this.organizationUnitRepository = Objects.requireNonNull(
                organizationUnitRepository,
                "Organization unit repository must not be null.");
        this.positionRepository = Objects.requireNonNull(positionRepository, "Position repository must not be null.");
        this.employeeAssignmentDomainService = Objects.requireNonNull(
                employeeAssignmentDomainService,
                "Employee assignment domain service must not be null.");
        this.domainEventPublisher = Objects.requireNonNull(domainEventPublisher, "Domain event publisher must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "Organization application mapper must not be null.");
    }

    @Override
    public EmployeeAssignmentDto assignEmployeeToUnit(AssignEmployeeToUnitCommand command) {
        Objects.requireNonNull(command, "Assign employee to unit command must not be null.");

        Employee employee = employeeRepository.findById(command.employeeId())
                .orElseThrow(() -> new BusinessRuleViolationException("Employee not found."));

        OrganizationUnit organizationUnit = organizationUnitRepository.findById(command.organizationUnitId())
                .orElseThrow(() -> new BusinessRuleViolationException("Organization unit not found."));

        Position position = positionRepository.findById(command.positionId())
                .orElseThrow(() -> new BusinessRuleViolationException("Position not found."));

        OperationalScopeReference scopeReference = toOperationalScopeReference(
                command.operationalScopeType(),
                command.operationalScopeId(),
                command.operationalScopeCode(),
                command.operationalScopeName());

        EmployeeAssignment assignment = EmployeeAssignment.create(
                command.employeeId(),
                command.organizationUnitId(),
                command.positionId(),
                scopeReference,
                command.effectiveFrom());

        Employee updatedEmployee = employeeAssignmentDomainService.assignEmployeeToUnit(
                employee,
                organizationUnit,
                position,
                assignment);

        employeeRepository.save(updatedEmployee);

        domainEventPublisher.publish(EmployeeAssignedToUnitEvent.occurred(
                assignment.id(),
                assignment.employeeId(),
                assignment.organizationUnitId(),
                assignment.positionId()));

        return mapper.toDto(assignment);
    }

    private static OperationalScopeReference toOperationalScopeReference(
            dz.sh.hidra.modules.organization.domain.value.OperationalScopeType type,
            String scopeId,
            String scopeCode,
            String scopeName) {

        if (type == null && scopeCode == null) {
            return null;
        }

        if (type == null || scopeCode == null) {
            throw new BusinessRuleViolationException("Operational scope type and code must be provided together.");
        }

        return new OperationalScopeReference(type, scopeId, scopeCode, scopeName);
    }
}
