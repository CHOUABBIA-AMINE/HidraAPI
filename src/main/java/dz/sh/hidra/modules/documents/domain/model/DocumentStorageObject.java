/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentStorageObject
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
