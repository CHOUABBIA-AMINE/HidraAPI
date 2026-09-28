/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyExternalReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Reference to ERP, procurement, registry, or external master-data system.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException;
import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Reference to ERP, procurement, registry, or external master-data system.
     *
         * @param id id
     * @param partyId partyId
     * @param externalSystemType externalSystemType
     * @param externalSystemCode externalSystemCode
     * @param externalReference externalReference
     * @param externalLabelSnapshot externalLabelSnapshot
     * @param status status
     * @param lastSynchronizedAt lastSynchronizedAt
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PartyExternalReference(
            String id,
        String partyId,
        ExternalSystemType externalSystemType,
        String externalSystemCode,
        String externalReference,
        String externalLabelSnapshot,
        PartyCatalogStatus status,
        Instant lastSynchronizedAt,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PartyExternalReference {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPartyValueException("PartyExternalReference id must not be blank.");
        }
        // HRA-051 required: partyId
        if (partyId == null || partyId.isBlank()) {
            throw new InvalidPartyValueException("PartyExternalReference party id must not be blank.");
        }
        // HRA-051 required: externalSystemType
        if (externalSystemType == null) {
            throw new InvalidPartyValueException("PartyExternalReference external system type must not be null.");
        }
        // HRA-051 required: externalSystemCode
        if (externalSystemCode == null || externalSystemCode.isBlank()) {
            throw new InvalidPartyValueException("PartyExternalReference external system code must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidPartyValueException("PartyExternalReference status must not be null.");
        }

        id = normalize(id);
        partyId = normalize(partyId);
        externalSystemCode = normalize(externalSystemCode);
        externalReference = normalize(externalReference);
        externalLabelSnapshot = normalize(externalLabelSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
