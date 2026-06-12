/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakTopologyScopeReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.value
 *
 * @Description : Neutral topology scope reference used by leak detection.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.value;

/**
 * Neutral topology scope reference used by leak detection.
 *
 * @param topologyAssetType topology asset type
 * @param topologyAssetId topology asset identifier
 * @param topologyAssetCode topology asset code snapshot
 * @param topologyAssetNameSnapshot topology asset name snapshot
 */
public record LeakTopologyScopeReference(
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        String topologyAssetNameSnapshot
) {
}
