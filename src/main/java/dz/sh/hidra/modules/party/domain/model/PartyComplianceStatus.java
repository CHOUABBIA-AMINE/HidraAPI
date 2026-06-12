/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyComplianceStatus
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Compliance screening or eligibility status.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Compliance screening or eligibility status.
     *
         * @param id id
     * @param partyId partyId
     * @param complianceStatus complianceStatus
     * @param screeningSource screeningSource
     * @param screeningReference screeningReference
     * @param checkedAt checkedAt
     * @param validUntil validUntil
     * @param notes notes
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PartyComplianceStatus(
            String id,
        String partyId,
        ComplianceStatus complianceStatus,
        String screeningSource,
        String screeningReference,
        Instant checkedAt,
        Instant validUntil,
        String notes,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PartyComplianceStatus {
        id = normalize(id);
        partyId = normalize(partyId);
        screeningSource = normalize(screeningSource);
        screeningReference = normalize(screeningReference);
        notes = normalize(notes);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
