/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateConfigurationDefinitionRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.api.rest.request
 *
 * @Description : REST request to create configuration definition.
 *
 */
package dz.sh.hidra.modules.configuration.api.rest.request;

import dz.sh.hidra.modules.configuration.domain.value.ConfigurationSensitivity;
import dz.sh.hidra.modules.configuration.domain.value.ConfigurationValueType;

/**
 * REST request to create configuration definition.
 */
public record CreateConfigurationDefinitionRequest(
        String namespaceId,
        String key,
        String displayNameFr,
        String displayNameAr,
        String displayNameEn,
        ConfigurationValueType valueType,
        ConfigurationSensitivity sensitivity,
        boolean scoped,
        boolean requiresApproval,
        String defaultValue,
        String description
) {
}
