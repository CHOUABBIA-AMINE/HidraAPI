/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentStorageAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.storage
 *
 * @Description : Storage adapter contract for binary document content.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.storage;

/**
 * Storage adapter contract for binary document content.
 */
public interface DocumentStorageAdapter {

    boolean objectExists(String storageProviderId, String bucketOrContainer, String objectKey);

    boolean checksumMatches(String storageProviderId, String bucketOrContainer, String objectKey, String checksumValue);
}
