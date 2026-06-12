/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportArtifactReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.value
 *
 * @Description : Generated report artifact reference.
 *
 */
package dz.sh.hidra.modules.reporting.domain.value;

/**
 * Generated report artifact reference.
 *
 * @param storageObjectReferenceId storage object reference
 * @param documentReferenceId document metadata reference
 * @param checksum checksum
 * @param fileName generated file name
 */
public record ReportArtifactReference(
        String storageObjectReferenceId,
        String documentReferenceId,
        String checksum,
        String fileName
) {
}
