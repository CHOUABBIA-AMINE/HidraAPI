/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationValue
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
