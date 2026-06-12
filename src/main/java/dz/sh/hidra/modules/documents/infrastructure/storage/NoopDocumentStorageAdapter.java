/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopDocumentStorageAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.storage
 *
 * @Description : No-op storage adapter for generated baseline.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.storage;

/**
 * No-op storage adapter for generated baseline.
 */
public class NoopDocumentStorageAdapter implements DocumentStorageAdapter {

    @Override
    public boolean objectExists(String storageProviderId, String bucketOrContainer, String objectKey) {
        return true;
    }

    @Override
    public boolean checksumMatches(String storageProviderId, String bucketOrContainer, String objectKey, String checksumValue) {
        return true;
    }
}
