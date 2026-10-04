/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentBinaryStoragePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-13
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.out
 *
 * @Description : Outbound port for storing and retrieving document binary content.
 *
 */
package dz.sh.hidra.modules.documents.application.port.out;

import java.io.InputStream;

/**
 * Outbound document-binary storage port owned by the documents module.
 */
public interface DocumentBinaryStoragePort {

    StoredBinary store(String referenceId, InputStream content);

    InputStream open(String referenceId);

    boolean available(String referenceId);

    void delete(String referenceId);

    record StoredBinary(
            String storageProviderId,
            String bucketOrContainer,
            String objectKey,
            boolean encrypted,
            String encryptionKeyReference,
            long contentLengthBytes,
            String checksumAlgorithm,
            String checksumValue
    ) {
        public StoredBinary {
            storageProviderId = requireText(storageProviderId, "Storage provider id");
            objectKey = requireText(objectKey, "Object key");
            checksumAlgorithm = requireText(checksumAlgorithm, "Checksum algorithm");
            checksumValue = requireText(checksumValue, "Checksum value");
            bucketOrContainer = normalize(bucketOrContainer);
            encryptionKeyReference = normalize(encryptionKeyReference);
            if (contentLengthBytes < 0L) {
                throw new IllegalArgumentException("Content length must not be negative.");
            }
            if (containsSecretMaterial(objectKey, true)) {
                throw new IllegalArgumentException("Object key must be opaque and must not contain credentials or signed-URL material.");
            }
            if (encryptionKeyReference != null && containsSecretMaterial(encryptionKeyReference, false)) {
                throw new IllegalArgumentException("Encryption key metadata must be reference-only and must not contain credentials.");
            }
        }

        private static String requireText(String value, String label) {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException(label + " must not be blank.");
            }
            return value.trim();
        }

        private static String normalize(String value) {
            return value == null || value.isBlank() ? null : value.trim();
        }

        private static boolean containsSecretMaterial(String value, boolean rejectUri) {
            String normalized = value.toLowerCase(java.util.Locale.ROOT);
            if (rejectUri && (normalized.contains("://") || normalized.contains("?"))) {
                return true;
            }
            return normalized.contains("x-amz-signature=")
                    || normalized.contains("x-amz-credential=")
                    || normalized.contains("access_token=")
                    || normalized.contains("signature=")
                    || normalized.contains("credential=")
                    || normalized.contains("password=")
                    || normalized.contains("secret=");
        }
    }
}
