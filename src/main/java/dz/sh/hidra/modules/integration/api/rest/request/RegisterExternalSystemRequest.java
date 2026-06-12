/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterExternalSystemRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.api.rest.request
 *
 * @Description : REST request to register external system.
 *
 */
package dz.sh.hidra.modules.integration.api.rest.request;

import dz.sh.hidra.modules.integration.domain.value.IntegrationCriticality;
import dz.sh.hidra.modules.integration.domain.value.IntegrationEnvironment;

/**
 * REST request to register external system.
 */
public record RegisterExternalSystemRequest(
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
