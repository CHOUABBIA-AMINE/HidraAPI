/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationDataContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.model
 *
 * @Description : Expected data shape exchanged with an external system.
 *
 */
package dz.sh.hidra.modules.integration.domain.model;

import dz.sh.hidra.modules.integration.domain.exception.InvalidIntegrationValueException;
import dz.sh.hidra.modules.integration.domain.value.*;
import java.time.Instant;

    /**
     * Expected data shape exchanged with an external system.
     *
         * @param id id
     * @param code code
     * @param nameFr nameFr
     * @param nameAr nameAr
     * @param nameEn nameEn
     * @param contractTypeId contractTypeId
     * @param payloadFormatId payloadFormatId
     * @param owningTargetModule owningTargetModule
     * @param description description
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record IntegrationDataContract(
            String id,
        String code,
        String nameFr,
        String nameAr,
        String nameEn,
        String contractTypeId,
        String payloadFormatId,
        String owningTargetModule,
        String description,
        ContractStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public IntegrationDataContract {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationDataContract id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationDataContract code must not be blank.");
        }
        // HRA-051 required: contractTypeId
        if (contractTypeId == null || contractTypeId.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationDataContract contract type id must not be blank.");
        }
        // HRA-051 required: payloadFormatId
        if (payloadFormatId == null || payloadFormatId.isBlank()) {
            throw new InvalidIntegrationValueException("IntegrationDataContract payload format id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidIntegrationValueException("IntegrationDataContract status must not be null.");
        }

        id = normalize(id);
        code = normalize(code);
        nameFr = normalize(nameFr);
        nameAr = normalize(nameAr);
        nameEn = normalize(nameEn);
        contractTypeId = normalize(contractTypeId);
        payloadFormatId = normalize(payloadFormatId);
        owningTargetModule = normalize(owningTargetModule);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
