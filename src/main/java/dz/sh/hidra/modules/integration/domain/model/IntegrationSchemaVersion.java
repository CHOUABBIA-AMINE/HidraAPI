/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationSchemaVersion
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Versioned schema definition for a data contract.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
import dz.sh.hidra.modules.integration.domain.value.*;
import java.time.Instant;

    /**
     * Versioned schema definition for a data contract.
     *
         * @param id id
     * @param dataContractId dataContractId
     * @param versionNumber versionNumber
     * @param schemaDefinition schemaDefinition
     * @param checksum checksum
     * @param status status
     * @param effectiveFrom effectiveFrom
     * @param effectiveTo effectiveTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record IntegrationSchemaVersion(
            String id,
        String dataContractId,
        int versionNumber,
        String schemaDefinition,
        String checksum,
        SchemaVersionStatus status,
        Instant effectiveFrom,
        Instant effectiveTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public IntegrationSchemaVersion {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationSchemaVersion id must not be blank.");
        }
        // HRA-051 required: dataContractId
        if (dataContractId == null || dataContractId.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationSchemaVersion data contract id must not be blank.");
        }
        // HRA-051 required: checksum
        if (checksum == null || checksum.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationSchemaVersion checksum must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIntegrationValueException("IntegrationSchemaVersion status must not be null.");
        }
        // HRA-051 order: effectiveFrom <= effectiveTo
        if (effectiveFrom != null && effectiveTo != null && effectiveTo.isBefore(effectiveFrom)) {
            throw new InvalidIntegrationValueException("IntegrationSchemaVersion effective to must not be before effective from.");
        }

        id = normalize(id);
        dataContractId = normalize(dataContractId);
        schemaDefinition = normalize(schemaDefinition);
        checksum = normalize(checksum);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
