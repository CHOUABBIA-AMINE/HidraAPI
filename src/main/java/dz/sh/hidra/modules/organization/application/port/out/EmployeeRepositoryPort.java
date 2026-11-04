/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Repository port for Employee.
 *
 */
package dz.sh.hidra.modules.organization.application.port.out;

import dz.sh.hidra.modules.organization.domain.model.Employee;

import java.util.Optional;

/**
 * Repository port for Employee.
 */
public interface EmployeeRepositoryPort {

    Employee save(Employee model);

    Optional<Employee> findById(String id);

    default Employee lockForActivation(String id) { throw new UnsupportedOperationException("Activation storage unavailable"); }
    default java.time.Instant nextActivationTime(Employee current) { throw new UnsupportedOperationException("Activation storage unavailable"); }
    default Employee storeActivation(Employee before,Employee after,String actor,String reason) { throw new UnsupportedOperationException("Activation storage unavailable"); }
    default java.util.List<dz.sh.hidra.modules.organization.application.port.in.OrganizationFoundationAdministrationUseCase.ActivationEvent> activationHistory(String employeeId) { throw new UnsupportedOperationException("Activation storage unavailable"); }
}

