/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentExternalReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.model
 *
 * @Description : External DMS/archive/file-system reference metadata.
 *
 */
package dz.sh.hidra.modules.documents.domain.model;

import dz.sh.hidra.modules.documents.domain.exception.InvalidDocumentValueException;
import dz.sh.hidra.modules.documents.domain.value.*;
import java.time.Instant;

    /**
     * External DMS/archive/file-system reference metadata.
     *
         * @param id id
     * @param documentId documentId
     * @param documentVersionId documentVersionId
     * @param externalSystemId externalSystemId
     * @param externalObjectType externalObjectType
     * @param externalObjectId externalObjectId
     * @param externalObjectCode externalObjectCode
     * @param externalUrlReference externalUrlReference
     * @param syncStatus syncStatus
     * @param lastSyncedAt lastSyncedAt
     */
    public record DocumentExternalReference(
            String id,
        String documentId,
        String documentVersionId,
        String externalSystemId,
        String externalObjectType,
        String externalObjectId,
        String externalObjectCode,
        String externalUrlReference,
        DocumentExternalSyncStatus syncStatus,
        Instant lastSyncedAt
    ) {

        public DocumentExternalReference {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidDocumentValueException("DocumentExternalReference id must not be blank.");
        }
        // HRA-051 required: documentId
        if (documentId == null || documentId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentExternalReference document id must not be blank.");
        }
        // HRA-051 required: externalSystemId
        if (externalSystemId == null || externalSystemId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentExternalReference external system id must not be blank.");
        }
        // HRA-051 required: externalObjectId
        if (externalObjectId == null || externalObjectId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentExternalReference external object id must not be blank.");
        }
        // HRA-051 required: syncStatus
        if (syncStatus == null) {
            throw new InvalidDocumentValueException("DocumentExternalReference sync status must not be null.");
        }

        id = normalize(id);
        documentId = normalize(documentId);
        documentVersionId = normalize(documentVersionId);
        externalSystemId = normalize(externalSystemId);
        externalObjectType = normalize(externalObjectType);
        externalObjectId = normalize(externalObjectId);
        externalObjectCode = normalize(externalObjectCode);
        externalUrlReference = normalize(externalUrlReference);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
