/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GetEmployeeService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Application service implementing employee retrieval use case.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import java.util.Objects;
import java.util.Optional;

import dz.sh.hidra.modules.organization.application.dto.EmployeeDto;
import dz.sh.hidra.modules.organization.application.mapper.OrganizationApplicationMapper;
import dz.sh.hidra.modules.organization.application.port.in.GetEmployeeUseCase;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepository;
import dz.sh.hidra.modules.organization.application.query.GetEmployeeByIdQuery;

/**
 * Implements the employee retrieval use case.
 *
 * <p>Business role:
 * This service retrieves a real operational employee by identifier without exposing identity
 * accounts, topology assets, or persistence entities.
 *
 * <p>Architecture role:
 * This is an application service implementing an inbound port. It depends on outbound ports and
 * application mapping only.
 *
 * <p>Validation:
 * The query is required. Repository lookup is performed through the outbound employee repository
 * port. Missing employees are represented as Optional.empty().
 *
 * <p>Usage:
 * API controllers should call this service through the GetEmployeeUseCase interface.
 */
public final class GetEmployeeService implements GetEmployeeUseCase {

    private final EmployeeRepository employeeRepository;
    private final OrganizationApplicationMapper mapper;

    public GetEmployeeService(EmployeeRepository employeeRepository, OrganizationApplicationMapper mapper) {
        this.employeeRepository = Objects.requireNonNull(employeeRepository, "Employee repository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "Organization application mapper must not be null.");
    }

    @Override
    public Optional<EmployeeDto> getEmployee(GetEmployeeByIdQuery query) {
        Objects.requireNonNull(query, "Get employee query must not be null.");

        return employeeRepository.findById(query.employeeId())
                .map(mapper::toDto);
    }
}
