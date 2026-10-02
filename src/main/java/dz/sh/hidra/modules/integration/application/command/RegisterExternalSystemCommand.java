/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterExternalSystemCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.command
 *
 * @Description : Command to register an external system.
 *
 */
package dz.sh.hidra.modules.integration.application.command;

import dz.sh.hidra.modules.integration.domain.value.IntegrationCriticality;
import dz.sh.hidra.modules.integration.domain.value.IntegrationEnvironment;

/**
 * Command to register an external system.
 */
public record RegisterExternalSystemCommand(
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String systemTypeId,
        String ownerOrganizationUnitId,
        IntegrationEnvironment environment,
        IntegrationCriticality criticality,
        String description
) {
}
