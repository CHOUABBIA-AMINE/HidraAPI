/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityEmployeeReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Enforces the admitted Batch 5 IdentityEmployeeReferenceQueryService contract.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.contract.identity.IdentityEmployeeReferenceContract;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepositoryPort;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public final class IdentityEmployeeReferenceQueryService implements IdentityEmployeeReferenceContract {
    private final EmployeeRepositoryPort employees;
    public IdentityEmployeeReferenceQueryService(EmployeeRepositoryPort employees) {
        this.employees = Objects.requireNonNull(employees);
    }
    @Override
    public boolean exists(String id) {
        return id != null && !id.isBlank() && employees.findById(id.trim()).isPresent();
    }
}
