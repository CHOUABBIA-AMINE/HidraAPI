/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ScopedConfigurationOverride
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.configuration.domain.exception.InvalidConfigurationValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidConfigurationValueException("ScopedConfigurationOverride id must not be blank.");
        }
        // HRA-051 required: configurationValueId
        if (configurationValueId == null || configurationValueId.isBlank()) {
            throw new InvalidConfigurationValueException("ScopedConfigurationOverride configuration value id must not be blank.");
        }
        // HRA-051 required: definitionId
        if (definitionId == null || definitionId.isBlank()) {
            throw new InvalidConfigurationValueException("ScopedConfigurationOverride definition id must not be blank.");
        }
        // HRA-051 required: scopeType
        if (scopeType == null) {
            throw new InvalidConfigurationValueException("ScopedConfigurationOverride scope type must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidConfigurationValueException("ScopedConfigurationOverride status must not be null.");
        }
        // HRA-051 order: effectiveFrom <= effectiveTo
        if (effectiveFrom != null && effectiveTo != null && effectiveTo.isBefore(effectiveFrom)) {
            throw new InvalidConfigurationValueException("ScopedConfigurationOverride effective to must not be before effective from.");
        }

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
