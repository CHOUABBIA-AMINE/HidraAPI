/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationSchemaVersion
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
