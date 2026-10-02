/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateOrganizationUnitUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.in
 *
 * @Description : Use case for creating organization units.
 *
 */
package dz.sh.hidra.modules.organization.application.port.in;

import dz.sh.hidra.modules.organization.application.command.CreateOrganizationUnitCommand;
import dz.sh.hidra.modules.organization.application.dto.OrganizationUnitSummaryDto;

/**
 * Use case for creating organization units.
 */
public interface CreateOrganizationUnitUseCase {

    OrganizationUnitSummaryDto createOrganizationUnit(CreateOrganizationUnitCommand command);
}
