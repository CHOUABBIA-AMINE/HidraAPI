/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowOrganizationQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.query
 *
 * @Description : Enforces owner-validated Workflow execution and immutable evidence.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.query;

import dz.sh.hidra.modules.organization.application.contract.workflow.WorkflowOrganizationContract;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepositoryPort;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.EmployeeAssignmentJpaRepository;
import dz.sh.hidra.modules.organization.domain.value.*;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
@Component
public class WorkflowOrganizationQueryAdapter implements WorkflowOrganizationContract {
    private final OrganizationUnitRepositoryPort units;
    private final EmployeeRepositoryPort employees;
    private final EmployeeAssignmentJpaRepository assignments;
    public WorkflowOrganizationQueryAdapter(OrganizationUnitRepositoryPort units, EmployeeRepositoryPort employees,
            EmployeeAssignmentJpaRepository assignments) {
        this.units=Objects.requireNonNull(units);this.employees=Objects.requireNonNull(employees);
        this.assignments=Objects.requireNonNull(assignments);
    }
    public Optional<Unit> availableUnit(String id,Instant at) {
        if(id==null || id.isBlank() || at==null) return Optional.empty();
        return units.findById(id.trim()).filter(u->u.status()==OrganizationUnitStatus.ACTIVE
            && !at.isBefore(u.validFrom()) && (u.validTo()==null || at.isBefore(u.validTo())))
            .map(u->new Unit(u.id(),u.nameFr()!=null?u.nameFr():u.nameEn()!=null?u.nameEn():u.code()));
    }
    public boolean eligibleMember(String employeeId,String unitId,Instant at) {
        if(employeeId==null || availableUnit(unitId,at).isEmpty()
            || employees.findById(employeeId).filter(e->e.status()==EmployeeStatus.ACTIVE).isEmpty()) return false;
        return assignments.findAll().stream().anyMatch(a->employeeId.equals(a.employeeId())
            && unitId.equals(a.organizationUnitId()) && a.status()==AssignmentStatus.ACTIVE
            && !at.isBefore(a.validFrom()) && (a.validTo()==null || at.isBefore(a.validTo())));
    }
}
