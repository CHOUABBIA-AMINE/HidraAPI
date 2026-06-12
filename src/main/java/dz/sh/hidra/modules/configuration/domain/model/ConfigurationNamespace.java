/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationNamespace
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.domain.model
 *
 * @Description : Namespace for governed runtime settings.
 *
 */
package dz.sh.hidra.modules.configuration.domain.model;

import dz.sh.hidra.modules.configuration.domain.value.*;
import java.time.Instant;

    /**
     * Namespace for governed runtime settings.
     *
         * @param id id
     * @param code code
     * @param nameFr nameFr
     * @param nameAr nameAr
     * @param nameEn nameEn
     * @param ownerModule ownerModule
     * @param ownerTeam ownerTeam
     * @param status status
     * @param description description
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ConfigurationNamespace(
            String id,
        String code,
        String nameFr,
        String nameAr,
        String nameEn,
        String ownerModule,
        String ownerTeam,
        ConfigurationNamespaceStatus status,
        String description,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ConfigurationNamespace {
        id = normalize(id);
        code = normalize(code);
        nameFr = normalize(nameFr);
        nameAr = normalize(nameAr);
        nameEn = normalize(nameEn);
        ownerModule = normalize(ownerModule);
        ownerTeam = normalize(ownerTeam);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
