/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentTargetReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.value
 *
 * @Description : Neutral business object reference for document links.
 *
 */
package dz.sh.hidra.modules.documents.domain.value;

/**
 * Neutral business object reference for document links.
 *
 * @param targetModule target module
 * @param targetTypeCode target type code
 * @param targetId target identifier
 * @param targetCodeSnapshot target code snapshot
 * @param targetLabelSnapshot target label snapshot
 */
public record DocumentTargetReference(
        String targetModule,
        String targetTypeCode,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot
) {
}
