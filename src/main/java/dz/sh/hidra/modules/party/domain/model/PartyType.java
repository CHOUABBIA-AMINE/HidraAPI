/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Catalog of party types.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException;
import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Catalog of party types.
     *
         * @param id id
     * @param code code
     * @param regulatoryClassCode regulatoryClassCode
     * @param description description
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PartyType(
            String id,
        String code,
        String regulatoryClassCode,
        String description,
        PartyCatalogStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PartyType {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPartyValueException("PartyType id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidPartyValueException("PartyType code must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidPartyValueException("PartyType status must not be null.");
        }

        id = normalize(id);
        code = normalize(code);
        regulatoryClassCode = normalize(regulatoryClassCode);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
