/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentStorageObject
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.model
 *
 * @Description : Pointer to the physical binary object in a storage backend.
 *
 */
package dz.sh.hidra.modules.documents.domain.model;

import dz.sh.hidra.modules.documents.domain.exception.InvalidDocumentValueException;
import dz.sh.hidra.modules.documents.domain.value.*;
import java.time.Instant;

    /**
     * Pointer to the physical binary object in a storage backend.
     *
         * @param id id
     * @param storageProviderId storageProviderId
     * @param bucketOrContainer bucketOrContainer
     * @param objectKey objectKey
     * @param objectUri objectUri
     * @param encrypted encrypted
     * @param encryptionKeyReference encryptionKeyReference
     * @param contentLengthBytes contentLengthBytes
     * @param contentType contentType
     * @param checksumAlgorithm checksumAlgorithm
     * @param checksumValue checksumValue
     * @param storageStatus storageStatus
     * @param createdAt createdAt
     * @param verifiedAt verifiedAt
     */
    public record DocumentStorageObject(
            String id,
        String storageProviderId,
        String bucketOrContainer,
        String objectKey,
        String objectUri,
        boolean encrypted,
        String encryptionKeyReference,
        long contentLengthBytes,
        String contentType,
        String checksumAlgorithm,
        String checksumValue,
        DocumentStorageStatus storageStatus,
        Instant createdAt,
        Instant verifiedAt
    ) {

        public DocumentStorageObject {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidDocumentValueException("DocumentStorageObject id must not be blank.");
        }
        // HRA-051 required: storageProviderId
        if (storageProviderId == null || storageProviderId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentStorageObject storage provider id must not be blank.");
        }
        // HRA-051 required: objectKey
        if (objectKey == null || objectKey.isBlank()) {
            throw new InvalidDocumentValueException("DocumentStorageObject object key must not be blank.");
        }
        // HRA-051 required: storageStatus
        if (storageStatus == null) {
            throw new InvalidDocumentValueException("DocumentStorageObject storage status must not be null.");
        }

        id = normalize(id);
        storageProviderId = normalize(storageProviderId);
        bucketOrContainer = normalize(bucketOrContainer);
        objectKey = normalize(objectKey);
        objectUri = normalize(objectUri);
        encryptionKeyReference = normalize(encryptionKeyReference);
        contentType = normalize(contentType);
        checksumAlgorithm = normalize(checksumAlgorithm);
        checksumValue = normalize(checksumValue);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
