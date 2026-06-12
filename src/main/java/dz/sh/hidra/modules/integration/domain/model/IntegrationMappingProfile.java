/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationMappingProfile
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Mapping profile from external payload to target module DTO.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.value.*;
import java.time.Instant;

    /**
     * Mapping profile from external payload to target module DTO.
     *
         * @param id id
     * @param code code
     * @param externalSystemId externalSystemId
     * @param dataContractId dataContractId
     * @param schemaVersionId schemaVersionId
     * @param targetModule targetModule
     * @param targetTypeCode targetTypeCode
     * @param direction direction
     * @param status status
     * @param validationMode validationMode
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record IntegrationMappingProfile(
            String id,
        String code,
        String externalSystemId,
        String dataContractId,
        String schemaVersionId,
        String targetModule,
        String targetTypeCode,
        IntegrationDirection direction,
        MappingProfileStatus status,
        ValidationMode validationMode,
        Instant createdAt,
        Instant updatedAt
    ) {

        public IntegrationMappingProfile {
        id = normalize(id);
        code = normalize(code);
        externalSystemId = normalize(externalSystemId);
        dataContractId = normalize(dataContractId);
        schemaVersionId = normalize(schemaVersionId);
        targetModule = normalize(targetModule);
        targetTypeCode = normalize(targetTypeCode);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
