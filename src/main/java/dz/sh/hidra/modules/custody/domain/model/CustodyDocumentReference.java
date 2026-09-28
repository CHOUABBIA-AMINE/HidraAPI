/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyDocumentReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.model
 *
 * @Description : Document reference.
 *
 */
package dz.sh.hidra.modules.custody.domain.model;

import dz.sh.hidra.modules.custody.domain.exception.InvalidCustodyValueException;
import dz.sh.hidra.modules.custody.domain.value.*;
import java.time.Instant;

    /**
     * Document reference.
     *
         * @param id id
     * @param targetType targetType
     * @param targetId targetId
     * @param documentType documentType
     * @param documentReferenceId documentReferenceId
     * @param documentCodeSnapshot documentCodeSnapshot
     * @param documentTitleSnapshot documentTitleSnapshot
     * @param attachedAt attachedAt
     * @param attachedByActorId attachedByActorId
     */
    public record CustodyDocumentReference(
            String id,
        String targetType,
        String targetId,
        CustodyDocumentType documentType,
        String documentReferenceId,
        String documentCodeSnapshot,
        String documentTitleSnapshot,
        Instant attachedAt,
        String attachedByActorId
    ) {

        public CustodyDocumentReference {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidCustodyValueException("CustodyDocumentReference id must not be blank.");
        }
        // HRA-051 required: targetId
        if (targetId == null || targetId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyDocumentReference target id must not be blank.");
        }
        // HRA-051 required: documentType
        if (documentType == null) {
            throw new InvalidCustodyValueException("CustodyDocumentReference document type must not be null.");
        }
        // HRA-051 required: documentReferenceId
        if (documentReferenceId == null || documentReferenceId.isBlank()) {
            throw new InvalidCustodyValueException("CustodyDocumentReference document reference id must not be blank.");
        }
        // HRA-051 required: attachedAt
        if (attachedAt == null) {
            throw new InvalidCustodyValueException("CustodyDocumentReference attached at must not be null.");
        }

        id = normalize(id);
        targetType = normalize(targetType);
        targetId = normalize(targetId);
        documentReferenceId = normalize(documentReferenceId);
        documentCodeSnapshot = normalize(documentCodeSnapshot);
        documentTitleSnapshot = normalize(documentTitleSnapshot);
        attachedByActorId = normalize(attachedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
