/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SetEmployeeReportingLineService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Application service implementing employee reporting line use case.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import java.time.LocalDate;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.organization.application.command.SetEmployeeReportingLineCommand;
import dz.sh.hidra.modules.organization.application.dto.ReportingLineDto;
import dz.sh.hidra.modules.organization.application.mapper.OrganizationApplicationMapper;
import dz.sh.hidra.modules.organization.application.port.in.SetEmployeeReportingLineUseCase;
import dz.sh.hidra.modules.organization.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepository;
import dz.sh.hidra.modules.organization.domain.event.EmployeeReportingLineChangedEvent;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.model.ReportingLine;
import dz.sh.hidra.modules.organization.domain.service.ReportingLineDomainService;

/**
 * Implements the employee reporting-line use case.
 *
 * <p>Business role:
 * This service creates matrix-capable employee reporting relationships such as LINE, OPERATIONAL,
 * FUNCTIONAL, ADMINISTRATIVE, TECHNICAL, or DOTTED_LINE reporting.
 *
 * <p>Architecture role:
 * This application service coordinates employee loading, reporting-line domain validation,
 * aggregate update, event publication, and mapping without depending on API, Spring, JPA,
 * persistence entities, identity, topology, platform, or infrastructure adapters.
 *
 * <p>Validation:
 * The command is required. Employee and manager must exist. Reporting-line rules are delegated to
 * ReportingLineDomainService.
 *
 * <p>Usage:
 * Wire this service later as the SetEmployeeReportingLineUseCase implementation.
 */
public final class SetEmployeeReportingLineService implements SetEmployeeReportingLineUseCase {

    private final EmployeeRepository employeeRepository;
    private final ReportingLineDomainService reportingLineDomainService;
    private final DomainEventPublisherPort domainEventPublisher;
    private final OrganizationApplicationMapper mapper;

    public SetEmployeeReportingLineService(
            EmployeeRepository employeeRepository,
            ReportingLineDomainService reportingLineDomainService,
            DomainEventPublisherPort domainEventPublisher,
            OrganizationApplicationMapper mapper) {

        this.employeeRepository = Objects.requireNonNull(employeeRepository, "Employee repository must not be null.");
        this.reportingLineDomainService = Objects.requireNonNull(
                reportingLineDomainService,
                "Reporting line domain service must not be null.");
        this.domainEventPublisher = Objects.requireNonNull(domainEventPublisher, "Domain event publisher must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "Organization application mapper must not be null.");
    }

    @Override
    public ReportingLineDto setEmployeeReportingLine(SetEmployeeReportingLineCommand command) {
        Objects.requireNonNull(command, "Set employee reporting line command must not be null.");

        Employee employee = employeeRepository.findById(command.employeeId())
                .orElseThrow(() -> new BusinessRuleViolationException("Employee not found."));

        Employee manager = employeeRepository.findById(command.managerEmployeeId())
                .orElseThrow(() -> new BusinessRuleViolationException("Manager employee not found."));

        ReportingLine reportingLine = ReportingLine.create(
                command.employeeId(),
                command.managerEmployeeId(),
                command.reportingLineType(),
                command.primaryLine(),
                command.effectiveFrom(),
                command.description());

        Employee updatedEmployee = reportingLineDomainService.addReportingLine(
                employee,
                manager,
                reportingLine,
                LocalDate.now());

        employeeRepository.save(updatedEmployee);

        domainEventPublisher.publish(EmployeeReportingLineChangedEvent.occurred(
                reportingLine.id(),
                reportingLine.employeeId(),
                reportingLine.managerEmployeeId(),
                reportingLine.type()));

        return mapper.toDto(reportingLine);
    }
}
