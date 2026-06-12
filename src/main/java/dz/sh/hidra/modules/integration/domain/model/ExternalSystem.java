/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalSystem
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : External system known to Hidra.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.value.*;
import java.time.Instant;

    /**
     * External system known to Hidra.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param systemTypeId systemTypeId
     * @param ownerOrganizationUnitId ownerOrganizationUnitId
     * @param environment environment
     * @param criticality criticality
     * @param status status
     * @param description description
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ExternalSystem(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String systemTypeId,
        String ownerOrganizationUnitId,
        IntegrationEnvironment environment,
        IntegrationCriticality criticality,
        ExternalSystemStatus status,
        String description,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ExternalSystem {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        systemTypeId = normalize(systemTypeId);
        ownerOrganizationUnitId = normalize(ownerOrganizationUnitId);
        description = normalize(description);
        }
        public boolean canRunProductionJobs() {
            return status == ExternalSystemStatus.ACTIVE
                    && environment == IntegrationEnvironment.PRODUCTION;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
