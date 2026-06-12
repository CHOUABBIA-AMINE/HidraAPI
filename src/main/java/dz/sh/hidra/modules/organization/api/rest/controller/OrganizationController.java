/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.controller
 *
 * @Description : Framework-neutral organization controller contract.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.controller;

import dz.sh.hidra.modules.organization.api.rest.request.CreateOrganizationUnitRequest;
import dz.sh.hidra.modules.organization.api.rest.request.RegisterEmployeeRequest;
import dz.sh.hidra.modules.organization.api.rest.response.EmployeeResponse;
import dz.sh.hidra.modules.organization.api.rest.response.OrganizationUnitResponse;

/**
 * Framework-neutral organization controller contract.
 */
public interface OrganizationController {

    OrganizationUnitResponse createOrganizationUnit(CreateOrganizationUnitRequest request);

    EmployeeResponse registerEmployee(RegisterEmployeeRequest request);
}
