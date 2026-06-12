/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationDefinitionResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.api.rest.response
 *
 * @Description : REST response for configuration definition.
 *
 */
package dz.sh.hidra.modules.configuration.api.rest.response;

import dz.sh.hidra.modules.configuration.domain.value.ConfigurationDefinitionStatus;
import dz.sh.hidra.modules.configuration.domain.value.ConfigurationSensitivity;
import dz.sh.hidra.modules.configuration.domain.value.ConfigurationValueType;

/**
 * REST response for configuration definition.
 */
public record ConfigurationDefinitionResponse(
        String id,
        String namespaceId,
        String key,
        String displayNameFr,
        ConfigurationValueType valueType,
        ConfigurationSensitivity sensitivity,
        ConfigurationDefinitionStatus status,
        boolean scoped,
        boolean requiresApproval
) {
}
