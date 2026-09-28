/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationProfileEntry
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.model
 *
 * @Description : Profile entry linking profile to value or override.
 *
 */
package dz.sh.hidra.modules.configuration.domain.model;

import dz.sh.hidra.modules.configuration.domain.exception.InvalidConfigurationValueException;
import java.time.Instant;

    /**
     * Profile entry linking profile to value or override.
     *
         * @param id id
     * @param profileId profileId
     * @param definitionId definitionId
     * @param configurationValueId configurationValueId
     * @param scopedOverrideId scopedOverrideId
     * @param priorityOrder priorityOrder
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ConfigurationProfileEntry(
            String id,
        String profileId,
        String definitionId,
        String configurationValueId,
        String scopedOverrideId,
        int priorityOrder,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ConfigurationProfileEntry {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidConfigurationValueException("ConfigurationProfileEntry id must not be blank.");
        }
        // HRA-051 required: profileId
        if (profileId == null || profileId.isBlank()) {
            throw new InvalidConfigurationValueException("ConfigurationProfileEntry profile id must not be blank.");
        }
        // HRA-051 required: definitionId
        if (definitionId == null || definitionId.isBlank()) {
            throw new InvalidConfigurationValueException("ConfigurationProfileEntry definition id must not be blank.");
        }

        id = normalize(id);
        profileId = normalize(profileId);
        definitionId = normalize(definitionId);
        configurationValueId = normalize(configurationValueId);
        scopedOverrideId = normalize(scopedOverrideId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
