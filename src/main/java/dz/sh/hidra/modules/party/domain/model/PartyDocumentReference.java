/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyDocumentReference
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
