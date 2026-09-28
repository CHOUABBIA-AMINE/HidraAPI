/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyDocumentReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.model
 *
 * @Description : Neutral reference to document metadata.
 *
 */
package dz.sh.hidra.modules.party.domain.model;

import dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException;
import dz.sh.hidra.modules.party.domain.value.*;
import java.time.Instant;

    /**
     * Neutral reference to document metadata.
     *
         * @param id id
     * @param partyId partyId
     * @param documentReferenceType documentReferenceType
     * @param documentId documentId
     * @param documentCodeSnapshot documentCodeSnapshot
     * @param documentTitleSnapshot documentTitleSnapshot
     * @param validFrom validFrom
     * @param validTo validTo
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record PartyDocumentReference(
            String id,
        String partyId,
        DocumentReferenceType documentReferenceType,
        String documentId,
        String documentCodeSnapshot,
        String documentTitleSnapshot,
        Instant validFrom,
        Instant validTo,
        PartyCatalogStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public PartyDocumentReference {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPartyValueException("PartyDocumentReference id must not be blank.");
        }
        // HRA-051 required: partyId
        if (partyId == null || partyId.isBlank()) {
            throw new InvalidPartyValueException("PartyDocumentReference party id must not be blank.");
        }
        // HRA-051 required: documentReferenceType
        if (documentReferenceType == null) {
            throw new InvalidPartyValueException("PartyDocumentReference document reference type must not be null.");
        }
        // HRA-051 required: documentId
        if (documentId == null || documentId.isBlank()) {
            throw new InvalidPartyValueException("PartyDocumentReference document id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidPartyValueException("PartyDocumentReference status must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidPartyValueException("PartyDocumentReference valid to must not be before valid from.");
        }

        id = normalize(id);
        partyId = normalize(partyId);
        documentId = normalize(documentId);
        documentCodeSnapshot = normalize(documentCodeSnapshot);
        documentTitleSnapshot = normalize(documentTitleSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
