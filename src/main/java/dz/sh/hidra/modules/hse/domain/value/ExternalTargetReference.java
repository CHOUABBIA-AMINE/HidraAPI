/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalTargetReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.value
 *
 * @Description : Neutral cross-module target reference.
 *
 */
package dz.sh.hidra.modules.hse.domain.value;

/**
 * Neutral cross-module target reference.
 *
 * @param targetModule source module
 * @param targetTypeCode target type code
 * @param targetId target identifier
 * @param targetCodeSnapshot target code snapshot
 * @param targetLabelSnapshot target label snapshot
 */
public record ExternalTargetReference(
        String targetModule,
        String targetTypeCode,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot
) {
}
