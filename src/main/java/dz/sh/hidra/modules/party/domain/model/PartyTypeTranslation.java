/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyTypeTranslation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
