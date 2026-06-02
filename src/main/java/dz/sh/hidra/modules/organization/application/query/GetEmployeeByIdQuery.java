/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GetEmployeeByIdQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.query
 *
 * @Description : Application query for retrieving an employee by identifier.
 *
 */
package dz.sh.hidra.modules.organization.application.query;

import java.util.Objects;

import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;

/**
 * Carries input required to retrieve a single employee by identifier.
 *
 * <p>Business role:
 * This query requests one real operational employee from the organization module.
 *
 * <p>Architecture role:
 * This is an application query consumed by organization read use cases. It must not depend on API,
 * persistence, identity, topology, platform, Spring, or JPA code.
 *
 * <p>Validation:
 * Employee id is mandatory.
 *
 * <p>Usage:
 * Use this query from inbound ports or API mappers when a client asks for one employee.
 *
 * @param employeeId employee identifier
 */
public record GetEmployeeByIdQuery(EmployeeId employeeId) implements Query {

    public GetEmployeeByIdQuery {
        Objects.requireNonNull(employeeId, "Employee id must not be null.");
    }
}
