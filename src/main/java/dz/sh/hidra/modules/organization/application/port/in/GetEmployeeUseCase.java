/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GetEmployeeUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.in
 *
 * @Description : Inbound port for retrieving one employee.
 *
 */
package dz.sh.hidra.modules.organization.application.port.in;

import java.util.Optional;

import dz.sh.hidra.modules.organization.application.dto.EmployeeDto;
import dz.sh.hidra.modules.organization.application.query.GetEmployeeByIdQuery;

/**
 * Inbound port for retrieving one employee.
 *
 * <p>Business role:
 * This inbound port exposes an organization use case to callers without exposing domain aggregates,
 * persistence entities, REST DTOs, identity implementation classes, or topology assets.
 *
 * <p>Architecture role:
 * This is an application-layer inbound port. API controllers and other callers may depend on this
 * interface; implementation belongs to application services in a later task.
 *
 * <p>Validation:
 * Input validation is expected before or inside the use-case implementation. Domain invariants
 * remain protected by domain value objects, aggregates, policies, and services.
 *
 * <p>Usage:
 * Depend on this interface from the API layer. Do not implement business logic in controllers.
 */
public interface GetEmployeeUseCase {

    /**
     * Retrieves one employee by identifier.
     *
     * @param query use-case input
     * @return employee DTO when found
     */
    Optional<EmployeeDto> getEmployee(GetEmployeeByIdQuery query);
}
