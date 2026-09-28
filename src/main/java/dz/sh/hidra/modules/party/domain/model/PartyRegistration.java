/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyRegistration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPartyValueException("PartyRegistration id must not be blank.");
        }
        // HRA-051 required: partyId
        if (partyId == null || partyId.isBlank()) {
            throw new InvalidPartyValueException("PartyRegistration party id must not be blank.");
        }
        // HRA-051 required: registrationType
        if (registrationType == null) {
            throw new InvalidPartyValueException("PartyRegistration registration type must not be null.");
        }
        // HRA-051 required: registrationNumber
        if (registrationNumber == null || registrationNumber.isBlank()) {
            throw new InvalidPartyValueException("PartyRegistration registration number must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidPartyValueException("PartyRegistration status must not be null.");
        }
        // HRA-051 order: issuedAt <= expiresAt
        if (issuedAt != null && expiresAt != null && expiresAt.isBefore(issuedAt)) {
            throw new InvalidPartyValueException("PartyRegistration expires at must not be before issued at.");
        }

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
