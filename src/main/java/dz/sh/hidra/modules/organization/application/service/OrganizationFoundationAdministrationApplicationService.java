/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationFoundationAdministrationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Coordinates the bounded Organization foundation contract.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.port.in.*;
import dz.sh.hidra.modules.organization.application.port.out.*;
import dz.sh.hidra.modules.organization.application.command.*;
import dz.sh.hidra.modules.organization.application.dto.*;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
@Service
@Transactional(readOnly=true)
public class OrganizationFoundationAdministrationApplicationService implements OrganizationFoundationAdministrationUseCase {
    private final OrganizationFoundationAuthorizationPort authority;
    private final EmployeeRepositoryPort employees;
    private final RegisterEmployeeUseCase registration;
    private final CreateOrganizationUnitUseCase units;
    private final OrganizationAdministrationQueryUseCase queries;
    public OrganizationFoundationAdministrationApplicationService(OrganizationFoundationAuthorizationPort authority,
            EmployeeRepositoryPort employees,RegisterEmployeeUseCase registration,CreateOrganizationUnitUseCase units,
            OrganizationAdministrationQueryUseCase queries) {
        this.authority=java.util.Objects.requireNonNull(authority);this.employees=java.util.Objects.requireNonNull(employees);
        this.registration=java.util.Objects.requireNonNull(registration);this.units=java.util.Objects.requireNonNull(units);this.queries=java.util.Objects.requireNonNull(queries);
    }
    @Override @Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class)
    public OrganizationUnitSummaryDto createUnit(CreateOrganizationUnitCommand command) {
        authority.administrator();var result=units.createOrganizationUnit(command);authority.administrator();return result;
    }
    @Override @Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class)
    public EmployeeSummaryDto registerEmployee(RegisterEmployeeCommand command) {
        authority.administrator();java.util.Objects.requireNonNull(command);authority.requireActiveIdentity(command.identityUserReference());
        var result=registration.registerEmployee(command);authority.administrator();return result;
    }
    @Override @Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class)
    public EmployeeState activate(String employeeId,Instant expectedUpdatedAt,String reason) {
        String actor=authority.administrator();Employee before=employees.lockForActivation(employeeId);
        authority.administrator();authority.requireActiveIdentity(before.identityUserReference());
        Employee after=before.activate(expectedUpdatedAt,employees.nextActivationTime(before),reason);
        Employee saved=employees.storeActivation(before,after,actor,reason.trim());
        authority.requireActiveIdentity(saved.identityUserReference());authority.administrator();return view(saved);
    }
    @Override public EmployeeState state(String id) {
        authority.administrator();var employee=employees.findById(id).orElseThrow(()->new java.util.NoSuchElementException("Employee unavailable"));
        authority.administrator();return view(employee);
    }
    @Override public List<ActivationEvent> history(String id) {
        authority.administrator();employees.findById(id).orElseThrow(()->new java.util.NoSuchElementException("Employee unavailable"));
        var result=List.copyOf(employees.activationHistory(id));authority.administrator();return result;
    }
    @Override public List<Choice> unitTypes(){authority.administrator();return queries.unitTypes();}
    @Override public List<Choice> positions(){authority.administrator();return queries.positions();}
    private static EmployeeState view(Employee e){return new EmployeeState(e.id(),e.status().name(),e.hiredAt(),e.updatedAt());}
}
