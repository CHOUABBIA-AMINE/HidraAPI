/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyAssetReference
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.value
 *
 * @Description : Neutral topology asset reference for integrity.
 *
 */
package dz.sh.hidra.modules.integrity.domain.value;

/**
 * Neutral topology asset reference for integrity.
 *
 * @param topologyAssetTypeCode topology asset type code
 * @param topologyAssetId topology asset identifier
 * @param topologyAssetCodeSnapshot topology asset code snapshot
 * @param topologyAssetNameSnapshot topology asset name snapshot
 */
public record TopologyAssetReference(
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCodeSnapshot,
        String topologyAssetNameSnapshot
) {
}
