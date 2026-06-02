/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListEmployeesService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Application service implementing employee listing use case.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.organization.application.dto.EmployeeDto;
import dz.sh.hidra.modules.organization.application.mapper.OrganizationApplicationMapper;
import dz.sh.hidra.modules.organization.application.port.in.ListEmployeesUseCase;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepository;
import dz.sh.hidra.modules.organization.application.query.ListEmployeesQuery;
import dz.sh.hidra.modules.organization.domain.model.Employee;

/**
 * Implements the employee listing use case.
 *
 * <p>Business role:
 * This service lists operational employees and can filter employees already loaded by organization
 * unit, status, and search text.
 *
 * <p>Architecture role:
 * This is an application service. It uses only the outbound repository methods defined by ORG-011
 * and does not invent persistence queries or depend on API, Spring, JPA, identity, topology,
 * platform, or infrastructure code.
 *
 * <p>Validation:
 * The query and page request are required. Because ORG-011 does not yet define a broad
 * find-all employee port method, unscoped listing returns an empty page until persistence query
 * ports are expanded later.
 *
 * <p>Usage:
 * Use this service through ListEmployeesUseCase. Add richer search repository methods in a future
 * roadmap task if full employee search is required.
 */
public final class ListEmployeesService implements ListEmployeesUseCase {

    private final EmployeeRepository employeeRepository;
    private final OrganizationApplicationMapper mapper;

    public ListEmployeesService(EmployeeRepository employeeRepository, OrganizationApplicationMapper mapper) {
        this.employeeRepository = Objects.requireNonNull(employeeRepository, "Employee repository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "Organization application mapper must not be null.");
    }

    @Override
    public PageResult<EmployeeDto> listEmployees(ListEmployeesQuery query) {
        Objects.requireNonNull(query, "List employees query must not be null.");

        PageRequest pageRequest = query.pageRequest();

        if (query.organizationUnitId() == null) {
            return PageResult.empty(pageRequest.page(), pageRequest.size());
        }

        List<EmployeeDto> filteredEmployees = employeeRepository.findAssignedToOrganizationUnit(query.organizationUnitId()).stream()
                .filter(employee -> query.status() == null || employee.status() == query.status())
                .filter(employee -> matchesSearchText(employee, query.searchText()))
                .map(mapper::toDto)
                .toList();

        return page(filteredEmployees, pageRequest);
    }

    private static boolean matchesSearchText(Employee employee, String searchText) {
        if (searchText == null || searchText.isBlank()) {
            return true;
        }

        String normalized = searchText.toLowerCase(Locale.ROOT);
        return employee.employeeNumber().value().toLowerCase(Locale.ROOT).contains(normalized)
                || employee.fullName().value().toLowerCase(Locale.ROOT).contains(normalized)
                || employee.email().map(email -> email.value().toLowerCase(Locale.ROOT).contains(normalized)).orElse(false);
    }

    private static PageResult<EmployeeDto> page(List<EmployeeDto> items, PageRequest pageRequest) {
        int fromIndex = Math.min(pageRequest.page() * pageRequest.size(), items.size());
        int toIndex = Math.min(fromIndex + pageRequest.size(), items.size());
        return PageResult.of(items.subList(fromIndex, toIndex), pageRequest.page(), pageRequest.size(), items.size());
    }
}
