/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationDefinition
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.model
 *
 * @Description : Definition of a governed runtime setting.
 *
 */
package dz.sh.hidra.modules.configuration.domain.model;

import dz.sh.hidra.modules.configuration.domain.value.*;
import java.time.Instant;

    /**
     * Definition of a governed runtime setting.
     *
         * @param id id
     * @param namespaceId namespaceId
     * @param key key
     * @param displayNameFr displayNameFr
     * @param displayNameAr displayNameAr
     * @param displayNameEn displayNameEn
     * @param valueType valueType
     * @param sensitivity sensitivity
     * @param status status
     * @param scoped scoped
     * @param requiresApproval requiresApproval
     * @param defaultValue defaultValue
     * @param description description
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ConfigurationDefinition(
            String id,
        String namespaceId,
        String key,
        String displayNameFr,
        String displayNameAr,
        String displayNameEn,
        ConfigurationValueType valueType,
        ConfigurationSensitivity sensitivity,
        ConfigurationDefinitionStatus status,
        boolean scoped,
        boolean requiresApproval,
        String defaultValue,
        String description,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ConfigurationDefinition {
        id = normalize(id);
        namespaceId = normalize(namespaceId);
        key = normalize(key);
        displayNameFr = normalize(displayNameFr);
        displayNameAr = normalize(displayNameAr);
        displayNameEn = normalize(displayNameEn);
        defaultValue = normalize(defaultValue);
        description = normalize(description);
        }
        public boolean secretReferenceOnly() {
            return sensitivity == ConfigurationSensitivity.SECRET_REFERENCE_ONLY;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
