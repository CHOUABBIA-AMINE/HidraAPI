/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyRegistration
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Commercial, regulatory, or legal registration.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Commercial, regulatory, or legal registration.
     *
         * @param id id
     * @param partyId partyId
     * @param registrationType registrationType
     * @param registrationNumber registrationNumber
     * @param issuingAuthority issuingAuthority
     * @param countryCode countryCode
     * @param issuedAt issuedAt
     * @param expiresAt expiresAt
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PartyRegistration(
            String id,
        String partyId,
        PartyRegistrationType registrationType,
        String registrationNumber,
        String issuingAuthority,
        String countryCode,
        Instant issuedAt,
        Instant expiresAt,
        PartyCatalogStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PartyRegistration {
        id = normalize(id);
        partyId = normalize(partyId);
        registrationNumber = normalize(registrationNumber);
        issuingAuthority = normalize(issuingAuthority);
        countryCode = normalize(countryCode);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
