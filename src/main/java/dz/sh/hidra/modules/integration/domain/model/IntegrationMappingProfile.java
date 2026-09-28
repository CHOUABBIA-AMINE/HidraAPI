/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationMappingProfile
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationMappingProfile id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationMappingProfile code must not be blank.");
        }
        // HRA-051 required: externalSystemId
        if (externalSystemId == null || externalSystemId.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationMappingProfile external system id must not be blank.");
        }
        // HRA-051 required: dataContractId
        if (dataContractId == null || dataContractId.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationMappingProfile data contract id must not be blank.");
        }
        // HRA-051 required: targetTypeCode
        if (targetTypeCode == null || targetTypeCode.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationMappingProfile target type code must not be blank.");
        }
        // HRA-051 required: direction
        if (direction == null) {
            throw new InvalidIntegrationValueException("IntegrationMappingProfile direction must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIntegrationValueException("IntegrationMappingProfile status must not be null.");
        }
        // HRA-051 required: validationMode
        if (validationMode == null) {
            throw new InvalidIntegrationValueException("IntegrationMappingProfile validation mode must not be null.");
        }

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
