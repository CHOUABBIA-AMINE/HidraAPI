/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListEmployeesUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.in
 *
 * @Description : Inbound port for listing employees.
 *
 */
package dz.sh.hidra.modules.organization.application.port.in;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.organization.application.dto.EmployeeDto;
import dz.sh.hidra.modules.organization.application.query.ListEmployeesQuery;

/**
 * Inbound port for listing employees.
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
public interface ListEmployeesUseCase {

    /**
     * Lists employees according to supplied filtering and pagination criteria.
     *
     * @param query use-case input
     * @return paged employee DTO result
     */
    PageResult<EmployeeDto> listEmployees(ListEmployeesQuery query);
}
