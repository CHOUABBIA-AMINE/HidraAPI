/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTopologyReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.value
 *
 * @Description : Immutable topology snapshot for custody decisions.
 *
 */
package dz.sh.hidra.modules.custody.domain.value;

/**
 * Immutable topology snapshot for custody decisions.
 *
 * @param topologyAssetTypeCode topology asset type code
 * @param topologyAssetId topology asset identifier
 * @param topologyAssetCodeSnapshot topology asset code snapshot
 * @param topologyAssetNameSnapshot topology asset name snapshot
 */
public record CustodyTopologyReference(
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCodeSnapshot,
        String topologyAssetNameSnapshot
) {
}
