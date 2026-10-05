/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationValue
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.model
 *
 * @Description : Effective configuration value.
 *
 */
package dz.sh.hidra.modules.configuration.domain.model;

import dz.sh.hidra.modules.configuration.domain.exception.InvalidConfigurationValueException;
import dz.sh.hidra.modules.configuration.domain.value.*;
import java.time.Instant;

    /**
     * Effective configuration value.
     *
         * @param id id
     * @param definitionId definitionId
     * @param definitionVersionId definitionVersionId
     * @param environment environment
     * @param rawValue rawValue
     * @param jsonValue jsonValue
     * @param secretReference secretReference
     * @param status status
     * @param effectiveFrom effectiveFrom
     * @param effectiveTo effectiveTo
     * @param createdByActorId createdByActorId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ConfigurationValue(
            String id,
        String definitionId,
        String definitionVersionId,
        String environment,
        String rawValue,
        String jsonValue,
        String secretReference,
        ConfigurationValueStatus status,
        Instant effectiveFrom,
        Instant effectiveTo,
        String createdByActorId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ConfigurationValue {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidConfigurationValueException("ConfigurationValue id must not be blank.");
        }
        // HRA-051 required: definitionId
        if (definitionId == null || definitionId.isBlank()) {
            throw new InvalidConfigurationValueException("ConfigurationValue definition id must not be blank.");
        }
        // HMR-039 required: environment
        if (environment == null || environment.isBlank()) {
            throw new InvalidConfigurationValueException("ConfigurationValue environment must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidConfigurationValueException("ConfigurationValue status must not be null.");
        }
        // HRA-051 order: effectiveFrom <= effectiveTo
        if (effectiveFrom != null && effectiveTo != null && effectiveTo.isBefore(effectiveFrom)) {
            throw new InvalidConfigurationValueException("ConfigurationValue effective to must not be before effective from.");
        }

        id = normalize(id);
        definitionId = normalize(definitionId);
        definitionVersionId = normalize(definitionVersionId);
        environment = normalize(environment);
        rawValue = normalize(rawValue);
        jsonValue = normalize(jsonValue);
        secretReference = normalize(secretReference);
        createdByActorId = normalize(createdByActorId);
        }
        public boolean activeValue() {
            return status == ConfigurationValueStatus.ACTIVE;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
