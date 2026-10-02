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
    }
}
