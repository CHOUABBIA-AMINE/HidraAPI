/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationDefinitionVersion
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.model
 *
 * @Description : Versioned setting definition metadata.
 *
 */
package dz.sh.hidra.modules.configuration.domain.model;

import dz.sh.hidra.modules.configuration.domain.exception.InvalidConfigurationValueException;
import java.time.Instant;

    /**
     * Versioned setting definition metadata.
     *
         * @param id id
     * @param definitionId definitionId
     * @param versionNumber versionNumber
     * @param schemaJson schemaJson
     * @param defaultValue defaultValue
     * @param validationSummary validationSummary
     * @param createdByActorId createdByActorId
     * @param createdAt createdAt
     * @param active active
     */
    public record ConfigurationDefinitionVersion(
            String id,
        String definitionId,
        int versionNumber,
        String schemaJson,
        String defaultValue,
        String validationSummary,
        String createdByActorId,
        Instant createdAt,
        boolean active
    ) {

        public ConfigurationDefinitionVersion {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidConfigurationValueException("ConfigurationDefinitionVersion id must not be blank.");
        }
        // HRA-051 required: definitionId
        if (definitionId == null || definitionId.isBlank()) {
            throw new InvalidConfigurationValueException("ConfigurationDefinitionVersion definition id must not be blank.");
        }

        id = normalize(id);
        definitionId = normalize(definitionId);
        schemaJson = normalize(schemaJson);
        defaultValue = normalize(defaultValue);
        validationSummary = normalize(validationSummary);
        createdByActorId = normalize(createdByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
