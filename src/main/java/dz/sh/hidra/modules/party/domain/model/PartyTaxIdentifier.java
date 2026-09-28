/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyTaxIdentifier
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Tax or fiscal identifier reference.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException;
import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Tax or fiscal identifier reference.
     *
         * @param id id
     * @param partyId partyId
     * @param taxIdentifierType taxIdentifierType
     * @param identifierValue identifierValue
     * @param countryCode countryCode
     * @param primaryIdentifier primaryIdentifier
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PartyTaxIdentifier(
            String id,
        String partyId,
        TaxIdentifierType taxIdentifierType,
        String identifierValue,
        String countryCode,
        boolean primaryIdentifier,
        PartyCatalogStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PartyTaxIdentifier {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPartyValueException("PartyTaxIdentifier id must not be blank.");
        }
        // HRA-051 required: partyId
        if (partyId == null || partyId.isBlank()) {
            throw new InvalidPartyValueException("PartyTaxIdentifier party id must not be blank.");
        }
        // HRA-051 required: taxIdentifierType
        if (taxIdentifierType == null) {
            throw new InvalidPartyValueException("PartyTaxIdentifier tax identifier type must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidPartyValueException("PartyTaxIdentifier status must not be null.");
        }

        id = normalize(id);
        partyId = normalize(partyId);
        identifierValue = normalize(identifierValue);
        countryCode = normalize(countryCode);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
