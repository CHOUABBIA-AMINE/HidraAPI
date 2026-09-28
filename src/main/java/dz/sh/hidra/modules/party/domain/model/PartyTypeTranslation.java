/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyTypeTranslation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Multilingual labels for party types.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException;
import java.time.Instant;

    /**
     * Multilingual labels for party types.
     *
         * @param id id
     * @param partyTypeId partyTypeId
     * @param languageCode languageCode
     * @param label label
     * @param description description
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PartyTypeTranslation(
            String id,
        String partyTypeId,
        String languageCode,
        String label,
        String description,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PartyTypeTranslation {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPartyValueException("PartyTypeTranslation id must not be blank.");
        }
        // HRA-051 required: partyTypeId
        if (partyTypeId == null || partyTypeId.isBlank()) {
            throw new InvalidPartyValueException("PartyTypeTranslation party type id must not be blank.");
        }
        // HRA-051 required: languageCode
        if (languageCode == null || languageCode.isBlank()) {
            throw new InvalidPartyValueException("PartyTypeTranslation language code must not be blank.");
        }

        id = normalize(id);
        partyTypeId = normalize(partyTypeId);
        languageCode = normalize(languageCode);
        label = normalize(label);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
