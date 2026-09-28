/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyContactPoint
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Phone, email, website, EDI, or other contact channel.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException;
import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Phone, email, website, EDI, or other contact channel.
     *
         * @param id id
     * @param partyId partyId
     * @param contactPointType contactPointType
     * @param label label
     * @param value value
     * @param primaryContact primaryContact
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PartyContactPoint(
            String id,
        String partyId,
        PartyContactPointType contactPointType,
        String label,
        String value,
        boolean primaryContact,
        PartyCatalogStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PartyContactPoint {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPartyValueException("PartyContactPoint id must not be blank.");
        }
        // HRA-051 required: partyId
        if (partyId == null || partyId.isBlank()) {
            throw new InvalidPartyValueException("PartyContactPoint party id must not be blank.");
        }
        // HRA-051 required: contactPointType
        if (contactPointType == null) {
            throw new InvalidPartyValueException("PartyContactPoint contact point type must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidPartyValueException("PartyContactPoint status must not be null.");
        }

        id = normalize(id);
        partyId = normalize(partyId);
        label = normalize(label);
        value = normalize(value);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
