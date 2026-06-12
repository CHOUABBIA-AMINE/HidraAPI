/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentExternalReference
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
