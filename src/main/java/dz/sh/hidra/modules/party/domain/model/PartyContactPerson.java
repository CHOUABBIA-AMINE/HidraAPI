/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyContactPerson
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Contact person for a party, not an internal employee.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Contact person for a party, not an internal employee.
     *
         * @param id id
     * @param partyId partyId
     * @param fullName fullName
     * @param jobTitle jobTitle
     * @param emailAddress emailAddress
     * @param phoneNumber phoneNumber
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PartyContactPerson(
            String id,
        String partyId,
        String fullName,
        String jobTitle,
        String emailAddress,
        String phoneNumber,
        PartyContactPersonStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PartyContactPerson {
        id = normalize(id);
        partyId = normalize(partyId);
        fullName = normalize(fullName);
        jobTitle = normalize(jobTitle);
        emailAddress = normalize(emailAddress);
        phoneNumber = normalize(phoneNumber);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
