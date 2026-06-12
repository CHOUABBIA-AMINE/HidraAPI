/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ScopedConfigurationOverride
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.model
 *
 * @Description : Scoped override by module, organization, actor, environment, or target context.
 *
 */
package dz.sh.hidra.modules.configuration.domain.model;

import dz.sh.hidra.modules.configuration.domain.value.*;
import java.time.Instant;

    /**
     * Scoped override by module, organization, actor, environment, or target context.
     *
         * @param id id
     * @param configurationValueId configurationValueId
     * @param definitionId definitionId
     * @param scopeType scopeType
     * @param scopeId scopeId
     * @param moduleName moduleName
     * @param organizationUnitId organizationUnitId
     * @param overrideValue overrideValue
     * @param overrideJsonValue overrideJsonValue
     * @param status status
     * @param effectiveFrom effectiveFrom
     * @param effectiveTo effectiveTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ScopedConfigurationOverride(
            String id,
        String configurationValueId,
        String definitionId,
        ConfigurationScopeType scopeType,
        String scopeId,
        String moduleName,
        String organizationUnitId,
        String overrideValue,
        String overrideJsonValue,
        ConfigurationValueStatus status,
        Instant effectiveFrom,
        Instant effectiveTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ScopedConfigurationOverride {
        id = normalize(id);
        configurationValueId = normalize(configurationValueId);
        definitionId = normalize(definitionId);
        scopeId = normalize(scopeId);
        moduleName = normalize(moduleName);
        organizationUnitId = normalize(organizationUnitId);
        overrideValue = normalize(overrideValue);
        overrideJsonValue = normalize(overrideJsonValue);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
