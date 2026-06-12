/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentStoragePointer
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.value
 *
 * @Description : Safe storage pointer without credentials or signed URLs.
 *
 */
package dz.sh.hidra.modules.documents.domain.value;

/**
 * Safe storage pointer without credentials or signed URLs.
 *
 * @param storageProviderId storage provider reference
 * @param bucketOrContainer bucket or container
 * @param objectKey provider object key
 * @param checksumAlgorithm checksum algorithm
 * @param checksumValue checksum value
 */
public record DocumentStoragePointer(
        String storageProviderId,
        String bucketOrContainer,
        String objectKey,
        String checksumAlgorithm,
        String checksumValue
) {
}
