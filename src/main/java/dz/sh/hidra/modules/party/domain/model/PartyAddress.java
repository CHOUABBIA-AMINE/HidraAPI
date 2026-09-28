/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyAddress
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Legal, billing, shipping, operational, or correspondence address.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException;
import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Legal, billing, shipping, operational, or correspondence address.
     *
         * @param id id
     * @param partyId partyId
     * @param addressType addressType
     * @param countryCode countryCode
     * @param stateOrRegion stateOrRegion
     * @param city city
     * @param postalCode postalCode
     * @param addressLine1 addressLine1
     * @param addressLine2 addressLine2
     * @param primaryAddress primaryAddress
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PartyAddress(
            String id,
        String partyId,
        PartyAddressType addressType,
        String countryCode,
        String stateOrRegion,
        String city,
        String postalCode,
        String addressLine1,
        String addressLine2,
        boolean primaryAddress,
        PartyCatalogStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PartyAddress {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPartyValueException("PartyAddress id must not be blank.");
        }
        // HRA-051 required: partyId
        if (partyId == null || partyId.isBlank()) {
            throw new InvalidPartyValueException("PartyAddress party id must not be blank.");
        }
        // HRA-051 required: addressType
        if (addressType == null) {
            throw new InvalidPartyValueException("PartyAddress address type must not be null.");
        }
        // HRA-051 required: countryCode
        if (countryCode == null || countryCode.isBlank()) {
            throw new InvalidPartyValueException("PartyAddress country code must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidPartyValueException("PartyAddress status must not be null.");
        }

        id = normalize(id);
        partyId = normalize(partyId);
        countryCode = normalize(countryCode);
        stateOrRegion = normalize(stateOrRegion);
        city = normalize(city);
        postalCode = normalize(postalCode);
        addressLine1 = normalize(addressLine1);
        addressLine2 = normalize(addressLine2);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
