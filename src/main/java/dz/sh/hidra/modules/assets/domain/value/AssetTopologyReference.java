/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetTopologyReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.value
 *
 * @Description : Neutral topology reference for maintainable assets.
 *
 */
package dz.sh.hidra.modules.assets.domain.value;

/**
 * Neutral topology reference for maintainable assets.
 *
 * @param topologyAssetTypeCode topology asset type code
 * @param topologyAssetId topology asset identifier
 * @param topologyAssetCodeSnapshot topology asset code snapshot
 * @param topologyAssetNameSnapshot topology asset name snapshot
 */
public record AssetTopologyReference(
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCodeSnapshot,
        String topologyAssetNameSnapshot
) {
}
