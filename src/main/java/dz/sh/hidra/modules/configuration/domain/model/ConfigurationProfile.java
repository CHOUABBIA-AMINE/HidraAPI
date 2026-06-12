/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationProfile
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.model
 *
 * @Description : Group of settings for an environment or operational mode.
 *
 */
package dz.sh.hidra.modules.configuration.domain.model;

import dz.sh.hidra.modules.configuration.domain.value.*;
import java.time.Instant;

    /**
     * Group of settings for an environment or operational mode.
     *
         * @param id id
     * @param code code
     * @param nameFr nameFr
     * @param nameAr nameAr
     * @param nameEn nameEn
     * @param environment environment
     * @param status status
     * @param description description
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ConfigurationProfile(
            String id,
        String code,
        String nameFr,
        String nameAr,
        String nameEn,
        String environment,
        ConfigurationNamespaceStatus status,
        String description,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ConfigurationProfile {
        id = normalize(id);
        code = normalize(code);
        nameFr = normalize(nameFr);
        nameAr = normalize(nameAr);
        nameEn = normalize(nameEn);
        environment = normalize(environment);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
